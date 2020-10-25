/*    */ package com.avaje.ebeaninternal.server.jmx;
/*    */ 
/*    */ import com.avaje.ebean.AdminLogging;
/*    */ import com.avaje.ebean.LogLevel;
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
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
/*    */ public class MAdminLogging
/*    */   implements MAdminLoggingMBean, AdminLogging
/*    */ {
/*    */   private final TransactionManager transactionManager;
/*    */   private boolean debugSql;
/*    */   private boolean debugLazyLoad;
/*    */   
/*    */   public MAdminLogging(ServerConfig serverConfig, TransactionManager txManager) {
/* 46 */     this.transactionManager = txManager;
/* 47 */     this.debugSql = serverConfig.isDebugSql();
/* 48 */     this.debugLazyLoad = serverConfig.isDebugLazyLoad();
/*    */   }
/*    */ 
/*    */   
/* 52 */   public void setLogLevel(LogLevel logLevel) { this.transactionManager.setTransactionLogLevel(logLevel); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public LogLevel getLogLevel() { return this.transactionManager.getTransactionLogLevel(); }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public boolean isDebugGeneratedSql() { return this.debugSql; }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public void setDebugGeneratedSql(boolean debugSql) { this.debugSql = debugSql; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public boolean isDebugLazyLoad() { return this.debugLazyLoad; }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public void setDebugLazyLoad(boolean debugLazyLoad) { this.debugLazyLoad = debugLazyLoad; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\jmx\MAdminLogging.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */