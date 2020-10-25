/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.bean.EntityBeanIntercept;
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
/*    */ public class LoadBeanRequest
/*    */   extends LoadRequest
/*    */ {
/*    */   private final List<EntityBeanIntercept> batch;
/*    */   private final LoadBeanContext loadContext;
/*    */   private final String lazyLoadProperty;
/*    */   
/*    */   public LoadBeanRequest(LoadBeanContext loadContext, List<EntityBeanIntercept> batch, Transaction transaction, int batchSize, boolean lazy, String lazyLoadProperty) {
/* 41 */     super(transaction, batchSize, lazy);
/* 42 */     this.loadContext = loadContext;
/* 43 */     this.batch = batch;
/* 44 */     this.lazyLoadProperty = lazyLoadProperty;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 48 */     String fullPath = this.loadContext.getFullPath();
/* 49 */     return "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public List<EntityBeanIntercept> getBatch() { return this.batch; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   public LoadBeanContext getLoadContext() { return this.loadContext; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String getLazyLoadProperty() { return this.lazyLoadProperty; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadBeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */