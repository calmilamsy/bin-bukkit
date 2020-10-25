/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TransactionScopeManager
/*    */   implements SpiTransactionScopeManager
/*    */ {
/*    */   protected final TransactionManager transactionManager;
/*    */   protected final String serverName;
/*    */   
/*    */   public TransactionScopeManager(TransactionManager transactionManager) {
/* 16 */     this.transactionManager = transactionManager;
/* 17 */     this.serverName = transactionManager.getServerName();
/*    */   }
/*    */   
/*    */   public abstract SpiTransaction get();
/*    */   
/*    */   public abstract void set(SpiTransaction paramSpiTransaction);
/*    */   
/*    */   public abstract void commit();
/*    */   
/*    */   public abstract void rollback();
/*    */   
/*    */   public abstract void end();
/*    */   
/*    */   public abstract void replace(SpiTransaction paramSpiTransaction);
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */