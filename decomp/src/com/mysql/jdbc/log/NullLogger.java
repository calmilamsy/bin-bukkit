/*    */ package com.mysql.jdbc.log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullLogger
/*    */   implements Log
/*    */ {
/*    */   public NullLogger(String instanceName) {}
/*    */   
/* 52 */   public boolean isDebugEnabled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public boolean isErrorEnabled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public boolean isFatalEnabled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   public boolean isInfoEnabled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public boolean isTraceEnabled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   public boolean isWarnEnabled() { return false; }
/*    */   
/*    */   public void logDebug(Object msg) {}
/*    */   
/*    */   public void logDebug(Object msg, Throwable thrown) {}
/*    */   
/*    */   public void logError(Object msg) {}
/*    */   
/*    */   public void logError(Object msg, Throwable thrown) {}
/*    */   
/*    */   public void logFatal(Object msg) {}
/*    */   
/*    */   public void logFatal(Object msg, Throwable thrown) {}
/*    */   
/*    */   public void logInfo(Object msg) {}
/*    */   
/*    */   public void logInfo(Object msg, Throwable thrown) {}
/*    */   
/*    */   public void logTrace(Object msg) {}
/*    */   
/*    */   public void logTrace(Object msg, Throwable thrown) {}
/*    */   
/*    */   public void logWarn(Object msg) {}
/*    */   
/*    */   public void logWarn(Object msg, Throwable thrown) {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\log\NullLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */