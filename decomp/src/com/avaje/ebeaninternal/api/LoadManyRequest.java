/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import java.util.List;
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
/*    */ public class LoadManyRequest
/*    */   extends LoadRequest
/*    */ {
/*    */   private final List<BeanCollection<?>> batch;
/*    */   private final LoadManyContext loadContext;
/*    */   private final boolean onlyIds;
/*    */   
/*    */   public LoadManyRequest(LoadManyContext loadContext, List<BeanCollection<?>> batch, Transaction transaction, int batchSize, boolean lazy, boolean onlyIds) {
/* 43 */     super(transaction, batchSize, lazy);
/* 44 */     this.loadContext = loadContext;
/* 45 */     this.batch = batch;
/* 46 */     this.onlyIds = onlyIds;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 50 */     String fullPath = this.loadContext.getFullPath();
/* 51 */     return "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public List<BeanCollection<?>> getBatch() { return this.batch; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public LoadManyContext getLoadContext() { return this.loadContext; }
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
/* 80 */   public boolean isOnlyIds() { return this.onlyIds; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadManyRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */