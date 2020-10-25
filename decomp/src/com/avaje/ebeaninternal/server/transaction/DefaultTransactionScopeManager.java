/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTransactionScopeManager
/*    */   extends TransactionScopeManager
/*    */ {
/* 12 */   public DefaultTransactionScopeManager(TransactionManager transactionManager) { super(transactionManager); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public void commit() { DefaultTransactionThreadLocal.commit(this.serverName); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void end() { DefaultTransactionThreadLocal.end(this.serverName); }
/*    */ 
/*    */   
/*    */   public SpiTransaction get() {
/* 24 */     SpiTransaction t = DefaultTransactionThreadLocal.get(this.serverName);
/* 25 */     if (t == null || !t.isActive()) {
/* 26 */       return null;
/*    */     }
/* 28 */     return t;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void replace(SpiTransaction trans) { DefaultTransactionThreadLocal.replace(this.serverName, trans); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void rollback() { DefaultTransactionThreadLocal.rollback(this.serverName); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void set(SpiTransaction trans) { DefaultTransactionThreadLocal.set(this.serverName, trans); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\DefaultTransactionScopeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */