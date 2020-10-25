/*     */ package com.avaje.ebeaninternal.server.transaction.log;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
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
/*     */ public class SimpleLogger
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(SimpleLogger.class.getName());
/*     */   
/*     */   private static final String atString = "        at ";
/*     */   
/*     */   private PrintStream out;
/*     */   
/*     */   private boolean doAppend;
/*     */   
/*     */   private boolean open;
/*     */   
/*     */   private String currentPath;
/*     */   private final String filepath;
/*     */   private final boolean useFileSwitching;
/*     */   
/*     */   public SimpleLogger(String dir, String logFileName, boolean useFileSwitching, String suffix) {
/*  58 */     this.doAppend = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.open = true;
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
/*  84 */     this.maxStackTraceLines = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.fileMonitor = new Object();
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
/* 109 */     this.newLineChar = "\\r\\n";
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
/* 124 */     this.logFileName = logFileName;
/* 125 */     this.useFileSwitching = useFileSwitching;
/* 126 */     this.logFileSuffix = "." + suffix;
/* 127 */     this.csv = "csv".equalsIgnoreCase(suffix);
/* 128 */     this.deliminator = this.csv ? "," : ", ";
/*     */ 
/*     */     
/*     */     try {
/* 132 */       this.filepath = makeDirIfRequired(dir);
/*     */       
/* 134 */       switchFile(LogTime.nextDay());
/*     */     }
/* 136 */     catch (Exception e) {
/*     */ 
/*     */       
/* 139 */       System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 140 */       System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 141 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   private final int maxStackTraceLines = 5; private final String deliminator; private final Object fileMonitor; private final String logFileName; private final String logFileSuffix; private final String newLineChar = "\\r\\n"; private final boolean csv;
/*     */   
/* 146 */   public SimpleLogger(String dir, String logFileName, boolean useFileSwitching) { this(dir, logFileName, useFileSwitching, "log"); }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 150 */     close();
/* 151 */     super.finalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws Throwable {
/* 158 */     if (this.open) {
/* 159 */       this.out.flush();
/* 160 */       this.out.close();
/* 161 */       this.open = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 166 */   public void log(String msg) { log(null, msg, null); }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void log(String msg, Throwable e) { log(null, msg, e); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(String transId, String msg, Throwable e) {
/* 179 */     LogTime logTime = LogTime.get();
/* 180 */     if (logTime.isNextDay()) {
/* 181 */       logTime = LogTime.nextDay();
/*     */       try {
/* 183 */         switchFile(logTime);
/* 184 */       } catch (Exception ex) {
/*     */ 
/*     */         
/* 187 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 192 */     int roughSize = 40;
/* 193 */     if (msg != null) {
/* 194 */       roughSize += msg.length();
/*     */     }
/* 196 */     if (e != null) {
/* 197 */       roughSize += 200;
/*     */     }
/*     */     
/* 200 */     StringBuilder line = new StringBuilder(roughSize);
/* 201 */     if (transId != null) {
/* 202 */       line.append("trans[").append(transId).append("]").append(this.deliminator);
/*     */     }
/*     */     
/* 205 */     if (this.csv) {
/* 206 */       line.append("\"'");
/*     */     }
/* 208 */     line.append(logTime.getNow());
/* 209 */     if (this.csv) {
/* 210 */       line.append("'\"");
/*     */     }
/* 212 */     line.append(this.deliminator);
/*     */     
/* 214 */     if (msg != null) {
/* 215 */       line.append(msg).append(" ");
/*     */     }
/*     */     
/* 218 */     printThrowable(line, e, false);
/*     */     
/* 220 */     String lineString = line.toString();
/*     */     
/* 222 */     synchronized (this.fileMonitor) {
/* 223 */       this.out.println(lineString);
/*     */       
/* 225 */       this.out.flush();
/*     */     } 
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
/* 240 */     if (e != null) {
/* 241 */       if (isCause) {
/* 242 */         sb.append("Caused by: ");
/*     */       }
/* 244 */       sb.append(e.getClass().getName());
/* 245 */       sb.append(":");
/* 246 */       sb.append(e.getMessage()).append("\\r\\n");
/*     */       
/* 248 */       StackTraceElement[] ste = e.getStackTrace();
/* 249 */       int outputStackLines = ste.length;
/* 250 */       int notShownCount = 0;
/* 251 */       if (ste.length > 5) {
/* 252 */         outputStackLines = 5;
/* 253 */         notShownCount = ste.length - outputStackLines;
/*     */       } 
/* 255 */       for (i = 0; i < outputStackLines; i++) {
/* 256 */         sb.append("        at ");
/* 257 */         sb.append(ste[i].toString()).append("\\r\\n");
/*     */       } 
/* 259 */       if (notShownCount > 0) {
/* 260 */         sb.append("        ... ");
/* 261 */         sb.append(notShownCount);
/* 262 */         sb.append(" more").append("\\r\\n");
/*     */       } 
/* 264 */       Throwable cause = e.getCause();
/* 265 */       if (cause != null) {
/* 266 */         printThrowable(sb, cause, true);
/*     */       }
/*     */     } 
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
/*     */   protected void switchFile(LogTime logTime) throws Exception {
/* 280 */     String newFilePath = this.filepath + File.separator + this.logFileName;
/*     */     
/* 282 */     if (this.useFileSwitching) {
/*     */       
/* 284 */       newFilePath = newFilePath + logTime.getYMD() + this.logFileSuffix;
/*     */     } else {
/*     */       
/* 287 */       newFilePath = newFilePath + this.logFileSuffix;
/*     */     } 
/*     */ 
/*     */     
/* 291 */     synchronized (this.fileMonitor) {
/*     */       
/* 293 */       if (!newFilePath.equals(this.currentPath)) {
/* 294 */         this.currentPath = newFilePath;
/*     */         
/* 296 */         this.out = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, this.doAppend)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String makeDirIfRequired(String dir) {
/* 307 */     File f = new File(dir);
/* 308 */     if (f.exists()) {
/* 309 */       if (!f.isDirectory()) {
/* 310 */         String msg = "Transaction logs directory is a file? " + dir;
/* 311 */         throw new PersistenceException(msg);
/*     */       }
/*     */     
/* 314 */     } else if (!f.mkdirs()) {
/* 315 */       String msg = "Failed to create transaction logs directory " + dir;
/* 316 */       logger.log(Level.SEVERE, msg);
/*     */     } 
/*     */     
/* 319 */     return dir;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\log\SimpleLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */