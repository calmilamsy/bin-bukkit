/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.sql.Connection;
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
/*     */ public abstract class BeanRequest
/*     */ {
/*     */   final SpiEbeanServer ebeanServer;
/*     */   final String serverName;
/*     */   SpiTransaction transaction;
/*     */   boolean createdTransaction;
/*     */   boolean readOnly;
/*     */   
/*     */   public BeanRequest(SpiEbeanServer ebeanServer, SpiTransaction t) {
/*  51 */     this.ebeanServer = ebeanServer;
/*  52 */     this.serverName = ebeanServer.getName();
/*  53 */     this.transaction = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void initTransIfRequired();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createImplicitTransIfRequired(boolean readOnlyTransaction) {
/*  73 */     if (this.transaction == null) {
/*  74 */       this.transaction = this.ebeanServer.getCurrentServerTransaction();
/*  75 */       if (this.transaction == null || !this.transaction.isActive()) {
/*     */         
/*  77 */         this.transaction = this.ebeanServer.createServerTransaction(false, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         this.createdTransaction = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commitTransIfRequired() {
/*  93 */     if (this.createdTransaction) {
/*  94 */       if (this.readOnly) {
/*  95 */         this.transaction.rollback();
/*     */       } else {
/*  97 */         this.transaction.commit();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollbackTransIfRequired() {
/* 106 */     if (this.createdTransaction) {
/* 107 */       this.transaction.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public EbeanServer getEbeanServer() { return this.ebeanServer; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public SpiTransaction getTransaction() { return this.transaction; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public Connection getConnection() { return this.transaction.getInternalConnection(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public boolean isLogSql() { return (this.transaction.getLogLevel().ordinal() >= LogLevel.SQL.ordinal()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public boolean isLogSummary() { return (this.transaction.getLogLevel().ordinal() >= LogLevel.SUMMARY.ordinal()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\BeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */