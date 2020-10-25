/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BatchedBeanControl
/*     */ {
/*     */   private final HashMap<String, BatchedBeanHolder> beanHoldMap;
/*     */   private final SpiTransaction transaction;
/*     */   private final BatchControl batchControl;
/*     */   private int topOrder;
/*     */   
/*     */   public BatchedBeanControl(SpiTransaction t, BatchControl batchControl) {
/*  44 */     this.beanHoldMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.transaction = t;
/*  54 */     this.batchControl = batchControl;
/*     */   }
/*     */   
/*     */   public ArrayList<PersistRequest> getPersistList(PersistRequestBean<?> request) {
/*  58 */     BatchedBeanHolder beanHolder = getBeanHolder(request);
/*  59 */     return beanHolder.getList(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BatchedBeanHolder getBeanHolder(PersistRequestBean<?> request) {
/*  68 */     BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
/*  69 */     BatchedBeanHolder batchBeanHolder = (BatchedBeanHolder)this.beanHoldMap.get(beanDescriptor.getFullName());
/*  70 */     if (batchBeanHolder == null) {
/*  71 */       int relativeDepth = this.transaction.depth(0);
/*     */       
/*  73 */       if (relativeDepth == 0) {
/*  74 */         this.topOrder++;
/*     */       }
/*  76 */       int stmtOrder = this.topOrder * 100 + relativeDepth;
/*     */       
/*  78 */       batchBeanHolder = new BatchedBeanHolder(this.batchControl, beanDescriptor, stmtOrder);
/*  79 */       this.beanHoldMap.put(beanDescriptor.getFullName(), batchBeanHolder);
/*     */     } 
/*  81 */     return batchBeanHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean isEmpty() { return this.beanHoldMap.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BatchedBeanHolder[] getArray() {
/*  98 */     BatchedBeanHolder[] bsArray = new BatchedBeanHolder[this.beanHoldMap.size()];
/*  99 */     this.beanHoldMap.values().toArray(bsArray);
/* 100 */     this.beanHoldMap.clear();
/* 101 */     this.topOrder = 0;
/* 102 */     return bsArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BatchedBeanControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */