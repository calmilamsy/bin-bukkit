/*    */ package com.avaje.ebeaninternal.server.lucene;
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
/*    */ public final class LIndexWork
/*    */ {
/*    */   private final WorkType workType;
/*    */   private final LIndexUpdateFuture future;
/*    */   private final IndexUpdates indexUpdates;
/*    */   
/*    */   public enum WorkType
/*    */   {
/* 30 */     TXN_UPDATE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     QUERY_UPDATE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     REBUILD;
/*    */   }
/*    */ 
/*    */   
/* 44 */   public static LIndexWork newRebuild(LIndexUpdateFuture future) { return new LIndexWork(WorkType.REBUILD, future, null); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public static LIndexWork newQueryUpdate(LIndexUpdateFuture future) { return new LIndexWork(WorkType.QUERY_UPDATE, future, null); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static LIndexWork newTxnUpdate(LIndexUpdateFuture future, IndexUpdates indexUpdates) { return new LIndexWork(WorkType.TXN_UPDATE, future, indexUpdates); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private LIndexWork(WorkType workType, LIndexUpdateFuture future, IndexUpdates indexUpdates) {
/* 62 */     this.workType = workType;
/* 63 */     this.future = future;
/* 64 */     this.indexUpdates = indexUpdates;
/*    */   }
/*    */ 
/*    */   
/* 68 */   public WorkType getWorkType() { return this.workType; }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public IndexUpdates getIndexUpdates() { return this.indexUpdates; }
/*    */ 
/*    */ 
/*    */   
/* 76 */   public LIndexUpdateFuture getFuture() { return this.future; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexWork.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */