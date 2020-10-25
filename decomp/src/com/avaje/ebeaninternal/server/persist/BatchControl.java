/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class BatchControl
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(BatchControl.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final BatchDepthComparator depthComparator = new BatchDepthComparator();
/*     */   
/*     */   private final SpiTransaction transaction;
/*     */   
/*     */   private final BatchedPstmtHolder pstmtHolder;
/*     */   
/*     */   private int batchSize;
/*     */   private boolean getGeneratedKeys;
/*     */   private boolean batchFlushOnMixed;
/*     */   private final BatchedBeanControl beanControl;
/*     */   
/*     */   public BatchControl(SpiTransaction t, int batchSize, boolean getGenKeys) {
/*  66 */     this.pstmtHolder = new BatchedPstmtHolder();
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
/*  81 */     this.batchFlushOnMixed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.transaction = t;
/*  91 */     this.batchSize = batchSize;
/*  92 */     this.getGeneratedKeys = getGenKeys;
/*  93 */     this.beanControl = new BatchedBeanControl(t, this);
/*  94 */     this.transaction.setBatchControl(this);
/*     */   }
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
/* 108 */   public void setBatchFlushOnMixed(boolean flushBatchOnMixed) { this.batchFlushOnMixed = flushBatchOnMixed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public int getBatchSize() { return this.batchSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBatchSize(int batchSize) {
/* 125 */     if (batchSize > 1) {
/* 126 */       this.batchSize = batchSize;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGetGeneratedKeys(Boolean getGeneratedKeys) {
/* 137 */     if (getGeneratedKeys != null) {
/* 138 */       this.getGeneratedKeys = getGeneratedKeys.booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeStatementOrBatch(PersistRequest request, boolean batch) {
/* 151 */     if (!batch || (this.batchFlushOnMixed && !this.beanControl.isEmpty()))
/*     */     {
/* 153 */       flush();
/*     */     }
/* 155 */     if (!batch)
/*     */     {
/* 157 */       return request.executeNow();
/*     */     }
/*     */     
/* 160 */     if (this.pstmtHolder.getMaxSize() >= this.batchSize) {
/* 161 */       flush();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 166 */     request.executeNow();
/* 167 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeOrQueue(PersistRequestBean<?> request, boolean batch) {
/* 177 */     if (!batch || (this.batchFlushOnMixed && !this.pstmtHolder.isEmpty()))
/*     */     {
/* 179 */       flush();
/*     */     }
/* 181 */     if (!batch) {
/* 182 */       return request.executeNow();
/*     */     }
/*     */ 
/*     */     
/* 186 */     ArrayList<PersistRequest> persistList = this.beanControl.getPersistList(request);
/* 187 */     if (persistList == null) {
/*     */ 
/*     */       
/* 190 */       if (logger.isLoggable(Level.FINE)) {
/* 191 */         logger.fine("Bean instance already in this batch: " + request.getBean());
/*     */       }
/* 193 */       return -1;
/*     */     } 
/*     */     
/* 196 */     if (persistList.size() >= this.batchSize) {
/*     */       
/* 198 */       flush();
/*     */ 
/*     */ 
/*     */       
/* 202 */       persistList = this.beanControl.getPersistList(request);
/*     */     } 
/*     */     
/* 205 */     persistList.add(request);
/*     */     
/* 207 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public BatchedPstmtHolder getPstmtHolder() { return this.pstmtHolder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   public boolean isEmpty() { return (this.beanControl.isEmpty() && this.pstmtHolder.isEmpty()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   protected void flushPstmtHolder() { this.pstmtHolder.flush(this.getGeneratedKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeNow(ArrayList<PersistRequest> list) {
/* 235 */     for (int i = 0; i < list.size(); i++) {
/* 236 */       PersistRequest request = (PersistRequest)list.get(i);
/* 237 */       request.executeNow();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 246 */     if (!this.pstmtHolder.isEmpty())
/*     */     {
/* 248 */       flushPstmtHolder();
/*     */     }
/* 250 */     if (this.beanControl.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 256 */     BatchedBeanHolder[] bsArray = this.beanControl.getArray();
/*     */ 
/*     */     
/* 259 */     Arrays.sort(bsArray, depthComparator);
/*     */     
/* 261 */     if (this.transaction.isLogSummary()) {
/* 262 */       this.transaction.logInternal("BatchControl flush " + Arrays.toString(bsArray));
/*     */     }
/* 264 */     for (int i = 0; i < bsArray.length; i++) {
/* 265 */       BatchedBeanHolder bs = bsArray[i];
/* 266 */       bs.executeNow();
/*     */       
/* 268 */       flushPstmtHolder();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BatchControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */