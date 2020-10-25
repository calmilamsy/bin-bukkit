/*     */ package com.mysql.jdbc.log;
/*     */ 
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardLogger
/*     */   implements Log
/*     */ {
/*     */   private static final int FATAL = 0;
/*     */   private static final int ERROR = 1;
/*     */   private static final int WARN = 2;
/*     */   private static final int INFO = 3;
/*     */   private static final int DEBUG = 4;
/*     */   private static final int TRACE = 5;
/*  54 */   public static StringBuffer bufferedLog = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean logLocationInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public StandardLogger(String name) { this(name, false); }
/*     */   
/*     */   public StandardLogger(String name, boolean logLocationInfo) {
/*     */     this.logLocationInfo = true;
/*  69 */     this.logLocationInfo = logLocationInfo;
/*     */   }
/*     */   
/*     */   public static void saveLogsToBuffer() {
/*  73 */     if (bufferedLog == null) {
/*  74 */       bufferedLog = new StringBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public boolean isDebugEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean isErrorEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public boolean isFatalEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean isInfoEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public boolean isTraceEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public boolean isWarnEnabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void logDebug(Object message) { logInternal(4, message, null); }
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
/* 139 */   public void logDebug(Object message, Throwable exception) { logInternal(4, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public void logError(Object message) { logInternal(1, message, null); }
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
/* 161 */   public void logError(Object message, Throwable exception) { logInternal(1, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public void logFatal(Object message) { logInternal(0, message, null); }
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
/* 183 */   public void logFatal(Object message, Throwable exception) { logInternal(0, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void logInfo(Object message) { logInternal(3, message, null); }
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
/* 205 */   public void logInfo(Object message, Throwable exception) { logInternal(3, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   public void logTrace(Object message) { logInternal(5, message, null); }
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
/* 227 */   public void logTrace(Object message, Throwable exception) { logInternal(5, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public void logWarn(Object message) { logInternal(2, message, null); }
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
/* 249 */   public void logWarn(Object message, Throwable exception) { logInternal(2, message, exception); }
/*     */ 
/*     */   
/*     */   private void logInternal(int level, Object msg, Throwable exception) {
/* 253 */     StringBuffer msgBuf = new StringBuffer();
/* 254 */     msgBuf.append((new Date()).toString());
/* 255 */     msgBuf.append(" ");
/*     */     
/* 257 */     switch (level) {
/*     */       case 0:
/* 259 */         msgBuf.append("FATAL: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 264 */         msgBuf.append("ERROR: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 269 */         msgBuf.append("WARN: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 274 */         msgBuf.append("INFO: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 279 */         msgBuf.append("DEBUG: ");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 284 */         msgBuf.append("TRACE: ");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (msg instanceof com.mysql.jdbc.profiler.ProfilerEvent) {
/* 290 */       msgBuf.append(LogUtils.expandProfilerEventIfNecessary(msg));
/*     */     } else {
/*     */       
/* 293 */       if (this.logLocationInfo && level != 5) {
/* 294 */         Throwable locationException = new Throwable();
/* 295 */         msgBuf.append(LogUtils.findCallingClassAndMethod(locationException));
/*     */         
/* 297 */         msgBuf.append(" ");
/*     */       } 
/*     */       
/* 300 */       if (msg != null) {
/* 301 */         msgBuf.append(String.valueOf(msg));
/*     */       }
/*     */     } 
/*     */     
/* 305 */     if (exception != null) {
/* 306 */       msgBuf.append("\n");
/* 307 */       msgBuf.append("\n");
/* 308 */       msgBuf.append("EXCEPTION STACK TRACE:");
/* 309 */       msgBuf.append("\n");
/* 310 */       msgBuf.append("\n");
/* 311 */       msgBuf.append(Util.stackTraceToString(exception));
/*     */     } 
/*     */     
/* 314 */     String messageAsString = msgBuf.toString();
/*     */     
/* 316 */     System.err.println(messageAsString);
/*     */     
/* 318 */     if (bufferedLog != null)
/* 319 */       bufferedLog.append(messageAsString); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\log\StandardLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */