/*     */ package com.avaje.ebeaninternal.server.transaction.log;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileTransactionLogger
/*     */   implements Runnable, TransactionLogWriter
/*     */ {
/*  52 */   private static final Logger logger = Logger.getLogger(FileTransactionLogger.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String atString = "        at ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String newLinePlaceholder = "\\r\\n";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxStackTraceLines = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ConcurrentLinkedQueue<TransactionLogBuffer> logBufferQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Thread logWriterThread;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String threadName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String filepath;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String deliminator = ", ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String logFileName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String logFileSuffix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintStream out;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String currentPath;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fileCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long maxBytesPerFile;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long bytesWritten;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public FileTransactionLogger(String threadName, String dir, String logFileName, int maxBytesPerFile) { this(threadName, dir, logFileName, "log", maxBytesPerFile); } public FileTransactionLogger(String threadName, String dir, String logFileName, String suffix, int maxBytesPerFile) { this.newLinePlaceholder = "\\r\\n";
/*     */     this.maxStackTraceLines = 5;
/*     */     this.logBufferQueue = new ConcurrentLinkedQueue();
/*     */     this.deliminator = ", ";
/* 139 */     this.threadName = threadName;
/* 140 */     this.logFileName = logFileName;
/* 141 */     this.logFileSuffix = "." + suffix;
/* 142 */     this.maxBytesPerFile = maxBytesPerFile;
/*     */ 
/*     */     
/*     */     try {
/* 146 */       this.filepath = makeDirIfRequired(dir);
/*     */       
/* 148 */       switchFile(LogTime.nextDay());
/*     */     }
/* 150 */     catch (Exception e) {
/* 151 */       System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 152 */       System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 153 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 156 */     this.logWriterThread = new Thread(this, threadName);
/* 157 */     this.logWriterThread.setDaemon(true); }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 161 */     close();
/* 162 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/* 166 */   public void start() throws Throwable { this.logWriterThread.start(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() throws Throwable {
/* 171 */     this.shutdown = true;
/*     */     
/* 173 */     synchronized (this.logWriterThread) {
/*     */       
/*     */       try {
/* 176 */         this.logWriterThread.wait(20000L);
/* 177 */         logger.fine("Shutdown LogBufferWriter " + this.threadName + " shutdownComplete:" + this.shutdownComplete);
/*     */       }
/* 179 */       catch (InterruptedException e) {
/* 180 */         logger.fine("InterruptedException:" + e);
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     if (!this.shutdownComplete) {
/* 185 */       String m = "WARNING: Shutdown of LogBufferWriter " + this.threadName + " not completed.";
/* 186 */       System.err.println(m);
/* 187 */       logger.warning(m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() throws Throwable {
/* 194 */     int missCount = 0;
/*     */     
/* 196 */     while (!this.shutdown || missCount < 10) {
/* 197 */       if (missCount > 50) {
/*     */         
/* 199 */         if (this.out != null) {
/* 200 */           this.out.flush();
/*     */         }
/*     */         try {
/* 203 */           Thread.sleep(20L);
/* 204 */         } catch (InterruptedException e) {
/* 205 */           logger.log(Level.INFO, "Interrupted TxnLogBufferWriter", e);
/*     */         } 
/*     */       } 
/* 208 */       synchronized (this.logBufferQueue) {
/* 209 */         if (this.logBufferQueue.isEmpty()) {
/* 210 */           missCount++;
/*     */         } else {
/* 212 */           TransactionLogBuffer buffer = (TransactionLogBuffer)this.logBufferQueue.remove();
/* 213 */           write(buffer);
/* 214 */           missCount = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     close();
/* 220 */     this.shutdownComplete = true;
/*     */     
/* 222 */     synchronized (this.logWriterThread) {
/* 223 */       this.logWriterThread.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 228 */   public void log(TransactionLogBuffer logBuffer) { this.logBufferQueue.add(logBuffer); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(TransactionLogBuffer logBuffer) {
/* 234 */     LogTime logTime = LogTime.get();
/* 235 */     if (logTime.isNextDay()) {
/* 236 */       logTime = LogTime.nextDay();
/* 237 */       switchFile(logTime);
/*     */     } 
/*     */     
/* 240 */     if (this.bytesWritten > this.maxBytesPerFile) {
/* 241 */       this.fileCounter++;
/* 242 */       switchFile(logTime);
/*     */     } 
/*     */     
/* 245 */     String txnId = logBuffer.getTransactionId();
/*     */     
/* 247 */     List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
/* 248 */     for (int i = 0; i < messages.size(); i++) {
/* 249 */       TransactionLogBuffer.LogEntry msg = (TransactionLogBuffer.LogEntry)messages.get(i);
/* 250 */       printMessage(logTime, txnId, msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printMessage(LogTime logTime, String txnId, TransactionLogBuffer.LogEntry logEntry) {
/* 256 */     String msg = logEntry.getMsg();
/* 257 */     int len = msg.length();
/* 258 */     if (len == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 263 */     this.bytesWritten += 16L;
/* 264 */     this.bytesWritten += len;
/*     */     
/* 266 */     if (txnId != null) {
/* 267 */       this.bytesWritten += 7L;
/* 268 */       this.bytesWritten += txnId.length();
/* 269 */       this.out.append("txn[");
/* 270 */       this.out.append(txnId);
/* 271 */       this.out.append("]");
/* 272 */       this.out.append(", ");
/*     */     } 
/*     */     
/* 275 */     this.out.append(logTime.getTimestamp(logEntry.getTimestamp()));
/* 276 */     this.out.append(", ");
/*     */     
/* 278 */     if (msg != null) {
/* 279 */       this.out.append(msg).append(" ");
/*     */     }
/* 281 */     this.out.append("\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void printThrowable(StringBuilder sb, Throwable e, boolean isCause) {
/* 295 */     if (e != null) {
/* 296 */       if (isCause) {
/* 297 */         sb.append("Caused by: ");
/*     */       }
/* 299 */       sb.append(e.getClass().getName());
/* 300 */       sb.append(":");
/* 301 */       sb.append(e.getMessage()).append("\\r\\n");
/*     */       
/* 303 */       StackTraceElement[] ste = e.getStackTrace();
/* 304 */       int outputStackLines = ste.length;
/* 305 */       int notShownCount = 0;
/* 306 */       if (ste.length > 5) {
/* 307 */         outputStackLines = 5;
/* 308 */         notShownCount = ste.length - outputStackLines;
/*     */       } 
/* 310 */       for (i = 0; i < outputStackLines; i++) {
/* 311 */         sb.append("        at ");
/* 312 */         sb.append(ste[i].toString()).append("\\r\\n");
/*     */       } 
/* 314 */       if (notShownCount > 0) {
/* 315 */         sb.append("        ... ");
/* 316 */         sb.append(notShownCount);
/* 317 */         sb.append(" more").append("\\r\\n");
/*     */       } 
/* 319 */       Throwable cause = e.getCause();
/* 320 */       if (cause != null) {
/* 321 */         printThrowable(sb, cause, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 327 */   private String newFileName(LogTime logTime) { return this.filepath + File.separator + this.logFileName + logTime.getYMD() + "-" + this.fileCounter + this.logFileSuffix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void switchFile(LogTime logTime) {
/*     */     try {
/* 336 */       long currentFileLength = 0L;
/* 337 */       String newFilePath = null;
/*     */ 
/*     */       
/*     */       do {
/* 341 */         newFilePath = newFileName(logTime);
/* 342 */         File f = new File(newFilePath);
/* 343 */         if (!f.exists()) {
/* 344 */           currentFileLength = 0L;
/*     */         }
/* 346 */         else if (f.length() < this.maxBytesPerFile * 0.8D) {
/* 347 */           currentFileLength = f.length();
/*     */         } else {
/* 349 */           this.fileCounter++;
/* 350 */           newFilePath = null;
/*     */         }
/*     */       
/* 353 */       } while (newFilePath == null);
/*     */       
/* 355 */       if (!newFilePath.equals(this.currentPath)) {
/* 356 */         PrintStream newOut = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, true)));
/*     */         
/* 358 */         close();
/*     */         
/* 360 */         this.bytesWritten = currentFileLength;
/* 361 */         this.currentPath = newFilePath;
/* 362 */         this.out = newOut;
/*     */       }
/*     */     
/* 365 */     } catch (IOException e) {
/* 366 */       e.printStackTrace();
/* 367 */       logger.log(Level.SEVERE, "Error switch log file", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close() throws Throwable {
/* 375 */     if (this.out != null) {
/* 376 */       this.out.flush();
/* 377 */       this.out.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String makeDirIfRequired(String dir) {
/* 386 */     File f = new File(dir);
/* 387 */     if (f.exists()) {
/* 388 */       if (!f.isDirectory()) {
/* 389 */         String msg = "Transaction logs directory is a file? " + dir;
/* 390 */         throw new PersistenceException(msg);
/*     */       }
/*     */     
/* 393 */     } else if (!f.mkdirs()) {
/* 394 */       String msg = "Failed to create transaction logs directory " + dir;
/* 395 */       logger.log(Level.SEVERE, msg);
/*     */     } 
/*     */     
/* 398 */     return dir;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\log\FileTransactionLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */