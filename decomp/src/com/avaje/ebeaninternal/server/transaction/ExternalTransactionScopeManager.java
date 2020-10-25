/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.config.ExternalTransactionManager;
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
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
/*    */ public class ExternalTransactionScopeManager
/*    */   extends TransactionScopeManager
/*    */ {
/*    */   final ExternalTransactionManager externalManager;
/*    */   
/*    */   public ExternalTransactionScopeManager(TransactionManager transactionManager, ExternalTransactionManager externalManager) {
/* 38 */     super(transactionManager);
/* 39 */     this.externalManager = externalManager;
/*    */   }
/*    */ 
/*    */   
/* 43 */   public void commit() { DefaultTransactionThreadLocal.commit(this.serverName); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void end() { DefaultTransactionThreadLocal.end(this.serverName); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public SpiTransaction get() { return (SpiTransaction)this.externalManager.getCurrentTransaction(); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void replace(SpiTransaction trans) { DefaultTransactionThreadLocal.replace(this.serverName, trans); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public void rollback() { DefaultTransactionThreadLocal.rollback(this.serverName); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public void set(SpiTransaction trans) { DefaultTransactionThreadLocal.set(this.serverName, trans); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\ExternalTransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */