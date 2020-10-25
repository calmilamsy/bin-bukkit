/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
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
/*    */ public abstract class LoadRequest
/*    */ {
/*    */   protected final boolean lazy;
/*    */   protected final int batchSize;
/*    */   protected final Transaction transaction;
/*    */   
/*    */   public LoadRequest(Transaction transaction, int batchSize, boolean lazy) {
/* 37 */     this.transaction = transaction;
/* 38 */     this.batchSize = batchSize;
/* 39 */     this.lazy = lazy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public boolean isLazy() { return this.lazy; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public int getBatchSize() { return this.batchSize; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public Transaction getTransaction() { return this.transaction; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */