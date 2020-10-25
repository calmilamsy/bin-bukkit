/*     */ package com.avaje.ebean.text.csv;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Transaction;
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
/*     */ public class DefaultCsvCallback<T>
/*     */   extends Object
/*     */   implements CsvCallback<T>
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(DefaultCsvCallback.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Transaction transaction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean createdTransaction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EbeanServer server;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int logInfoFrequency;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int persistBatchSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long exeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public DefaultCsvCallback() { this(30, 1000); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultCsvCallback(int persistBatchSize, int logInfoFrequency) {
/*  94 */     this.persistBatchSize = persistBatchSize;
/*  95 */     this.logInfoFrequency = logInfoFrequency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void begin(EbeanServer server) {
/* 102 */     this.server = server;
/* 103 */     this.startTime = System.currentTimeMillis();
/*     */     
/* 105 */     initTransactionIfRequired();
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
/*     */ 
/*     */   
/*     */   public void readHeader(String[] line) {}
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
/* 136 */   public boolean processLine(int row, String[] line) { return true; }
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
/*     */   public void processBean(int row, String[] line, T bean) {
/* 151 */     this.server.save(bean, this.transaction);
/*     */     
/* 153 */     if (this.logInfoFrequency > 0 && row % this.logInfoFrequency == 0) {
/* 154 */       logger.info("processed " + row + " rows");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end(int row) {
/* 163 */     commitTransactionIfCreated();
/*     */     
/* 165 */     this.exeTime = System.currentTimeMillis() - this.startTime;
/* 166 */     logger.info("Csv finished, rows[" + row + "] exeMillis[" + this.exeTime + "]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public void endWithError(int row, Exception e) { rollbackTransactionIfCreated(e); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initTransactionIfRequired() {
/* 182 */     this.transaction = this.server.currentTransaction();
/* 183 */     if (this.transaction == null || !this.transaction.isActive()) {
/*     */       
/* 185 */       this.transaction = this.server.beginTransaction();
/* 186 */       this.createdTransaction = true;
/* 187 */       if (this.persistBatchSize > 1) {
/* 188 */         logger.info("Creating transaction, batchSize[" + this.persistBatchSize + "]");
/* 189 */         this.transaction.setBatchMode(true);
/* 190 */         this.transaction.setBatchSize(this.persistBatchSize);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 195 */         this.transaction.setBatchMode(false);
/* 196 */         logger.info("Creating transaction with no JDBC batching");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void commitTransactionIfCreated() {
/* 206 */     if (this.createdTransaction) {
/* 207 */       this.transaction.commit();
/* 208 */       logger.info("Committed transaction");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rollbackTransactionIfCreated(Throwable e) {
/* 217 */     if (this.createdTransaction) {
/* 218 */       this.transaction.rollback(e);
/* 219 */       logger.info("Rolled back transaction");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\csv\DefaultCsvCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */