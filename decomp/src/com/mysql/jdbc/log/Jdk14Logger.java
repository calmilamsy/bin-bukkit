/*     */ package com.mysql.jdbc.log;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Jdk14Logger
/*     */   implements Log
/*     */ {
/*  41 */   private static final Level DEBUG = Level.FINE;
/*     */   
/*  43 */   private static final Level ERROR = Level.SEVERE;
/*     */   
/*  45 */   private static final Level FATAL = Level.SEVERE;
/*     */   
/*  47 */   private static final Level INFO = Level.INFO;
/*     */   
/*  49 */   private static final Level TRACE = Level.FINEST;
/*     */   
/*  51 */   private static final Level WARN = Level.WARNING;
/*     */   
/*     */   protected Logger jdkLogger;
/*     */   
/*     */   public Jdk14Logger(String name) {
/*  56 */     this.jdkLogger = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     this.jdkLogger = Logger.getLogger(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean isDebugEnabled() { return this.jdkLogger.isLoggable(Level.FINE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean isErrorEnabled() { return this.jdkLogger.isLoggable(Level.SEVERE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public boolean isFatalEnabled() { return this.jdkLogger.isLoggable(Level.SEVERE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public boolean isInfoEnabled() { return this.jdkLogger.isLoggable(Level.INFO); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public boolean isTraceEnabled() { return this.jdkLogger.isLoggable(Level.FINEST); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public boolean isWarnEnabled() { return this.jdkLogger.isLoggable(Level.WARNING); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public void logDebug(Object message) { logInternal(DEBUG, message, null); }
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
/* 129 */   public void logDebug(Object message, Throwable exception) { logInternal(DEBUG, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void logError(Object message) { logInternal(ERROR, message, null); }
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
/* 151 */   public void logError(Object message, Throwable exception) { logInternal(ERROR, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public void logFatal(Object message) { logInternal(FATAL, message, null); }
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
/* 173 */   public void logFatal(Object message, Throwable exception) { logInternal(FATAL, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public void logInfo(Object message) { logInternal(INFO, message, null); }
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
/* 195 */   public void logInfo(Object message, Throwable exception) { logInternal(INFO, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public void logTrace(Object message) { logInternal(TRACE, message, null); }
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
/* 217 */   public void logTrace(Object message, Throwable exception) { logInternal(TRACE, message, exception); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public void logWarn(Object message) { logInternal(WARN, message, null); }
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
/* 239 */   public void logWarn(Object message, Throwable exception) { logInternal(WARN, message, exception); }
/*     */ 
/*     */   
/*     */   private static final int findCallerStackDepth(StackTraceElement[] stackTrace) {
/* 243 */     int numFrames = stackTrace.length;
/*     */     
/* 245 */     for (int i = 0; i < numFrames; i++) {
/* 246 */       String callerClassName = stackTrace[i].getClassName();
/*     */       
/* 248 */       if (!callerClassName.startsWith("com.mysql.jdbc") || callerClassName.startsWith("com.mysql.jdbc.compliance"))
/*     */       {
/* 250 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logInternal(Level level, Object msg, Throwable exception) {
/* 263 */     if (this.jdkLogger.isLoggable(level)) {
/* 264 */       String messageAsString = null;
/* 265 */       String callerMethodName = "N/A";
/* 266 */       String callerClassName = "N/A";
/* 267 */       int lineNumber = 0;
/* 268 */       String fileName = "N/A";
/*     */       
/* 270 */       if (msg instanceof com.mysql.jdbc.profiler.ProfilerEvent) {
/* 271 */         messageAsString = LogUtils.expandProfilerEventIfNecessary(msg).toString();
/*     */       } else {
/*     */         
/* 274 */         Throwable locationException = new Throwable();
/* 275 */         StackTraceElement[] locations = locationException.getStackTrace();
/*     */ 
/*     */         
/* 278 */         int frameIdx = findCallerStackDepth(locations);
/*     */         
/* 280 */         if (frameIdx != 0) {
/* 281 */           callerClassName = locations[frameIdx].getClassName();
/* 282 */           callerMethodName = locations[frameIdx].getMethodName();
/* 283 */           lineNumber = locations[frameIdx].getLineNumber();
/* 284 */           fileName = locations[frameIdx].getFileName();
/*     */         } 
/*     */         
/* 287 */         messageAsString = String.valueOf(msg);
/*     */       } 
/*     */       
/* 290 */       if (exception == null) {
/* 291 */         this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString);
/*     */       } else {
/*     */         
/* 294 */         this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\log\Jdk14Logger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */