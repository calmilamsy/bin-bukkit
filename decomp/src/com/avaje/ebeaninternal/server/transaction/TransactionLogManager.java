/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.server.transaction.log.FileTransactionLoggerWrapper;
/*    */ import com.avaje.ebeaninternal.server.transaction.log.JuliTransactionLogger;
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
/*    */ public class TransactionLogManager
/*    */ {
/*    */   private final TransactionLogWriter logWriter;
/*    */   
/*    */   public TransactionLogManager(ServerConfig serverConfig) {
/* 43 */     if (serverConfig.isLoggingToJavaLogger()) {
/* 44 */       this.logWriter = new JuliTransactionLogger();
/*    */     } else {
/* 46 */       this.logWriter = new FileTransactionLoggerWrapper(serverConfig);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 51 */   public void shutdown() { this.logWriter.shutdown(); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void log(TransactionLogBuffer logBuffer) { this.logWriter.log(logBuffer); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */