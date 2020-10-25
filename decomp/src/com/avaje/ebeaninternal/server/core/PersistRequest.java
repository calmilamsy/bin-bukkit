/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchControl;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchPostExecute;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
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
/*     */ public abstract class PersistRequest
/*     */   extends BeanRequest
/*     */   implements BatchPostExecute
/*     */ {
/*     */   boolean persistCascade;
/*     */   Type type;
/*     */   final PersistExecute persistExecute;
/*     */   
/*     */   public enum Type
/*     */   {
/*  34 */     INSERT, UPDATE, DELETE, ORMUPDATE, UPDATESQL, CALLABLESQL;
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
/*     */   public PersistRequest(SpiEbeanServer server, SpiTransaction t, PersistExecute persistExecute) {
/*  50 */     super(server, t);
/*  51 */     this.persistExecute = persistExecute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int executeOrQueue();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int executeNow();
/*     */ 
/*     */ 
/*     */   
/*  65 */   public PstmtBatch getPstmtBatch() { return this.ebeanServer.getPstmtBatch(); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean isLogSql() { return this.transaction.isLogSql(); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public boolean isLogSummary() { return this.transaction.isLogSummary(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeStatement() {
/*     */     int rows;
/*  81 */     boolean batch = this.transaction.isBatchThisRequest();
/*     */ 
/*     */     
/*  84 */     BatchControl control = this.transaction.getBatchControl();
/*  85 */     if (control != null) {
/*  86 */       rows = control.executeStatementOrBatch(this, batch);
/*     */     }
/*  88 */     else if (batch) {
/*     */       
/*  90 */       control = this.persistExecute.createBatchControl(this.transaction);
/*  91 */       rows = control.executeStatementOrBatch(this, batch);
/*     */     } else {
/*  93 */       rows = executeNow();
/*     */     } 
/*     */     
/*  96 */     return rows;
/*     */   }
/*     */   
/*     */   public void initTransIfRequired() {
/* 100 */     createImplicitTransIfRequired(false);
/* 101 */     this.persistCascade = this.transaction.isPersistCascade();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public Type getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public void setType(Type type) { this.type = type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public boolean isPersistCascade() { return this.persistCascade; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\PersistRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */