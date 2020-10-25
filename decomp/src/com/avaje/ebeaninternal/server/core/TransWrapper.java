/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
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
/*    */ final class TransWrapper
/*    */ {
/*    */   final SpiTransaction transaction;
/*    */   private final boolean wasCreated;
/*    */   
/*    */   TransWrapper(SpiTransaction t, boolean created) {
/* 22 */     this.transaction = t;
/* 23 */     this.wasCreated = created;
/*    */   }
/*    */   
/*    */   void commitIfCreated() {
/* 27 */     if (this.wasCreated) {
/* 28 */       this.transaction.commit();
/*    */     }
/*    */   }
/*    */   
/*    */   void rollbackIfCreated() {
/* 33 */     if (this.wasCreated) {
/* 34 */       this.transaction.rollback();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   boolean wasCreated() { return this.wasCreated; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\TransWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */