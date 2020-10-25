/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.SqlQuery;
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public final class RelationalQueryRequest
/*     */ {
/*     */   private final SpiSqlQuery query;
/*     */   private final RelationalQueryEngine queryEngine;
/*     */   private final SpiEbeanServer ebeanServer;
/*     */   private SpiTransaction trans;
/*     */   private boolean createdTransaction;
/*     */   private Query.Type queryType;
/*     */   
/*     */   public RelationalQueryRequest(SpiEbeanServer server, RelationalQueryEngine engine, SqlQuery q, Transaction t) {
/*  56 */     this.ebeanServer = server;
/*  57 */     this.queryEngine = engine;
/*  58 */     this.query = (SpiSqlQuery)q;
/*  59 */     this.trans = (SpiTransaction)t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollbackTransIfRequired() {
/*  66 */     if (this.createdTransaction) {
/*  67 */       this.trans.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTransIfRequired() {
/*  75 */     if (this.trans == null) {
/*  76 */       this.trans = this.ebeanServer.getCurrentServerTransaction();
/*  77 */       if (this.trans == null || !this.trans.isActive()) {
/*     */         
/*  79 */         this.trans = this.ebeanServer.createServerTransaction(false, -1);
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
/*     */   public void endTransIfRequired() {
/*  93 */     if (this.createdTransaction)
/*     */     {
/*  95 */       this.trans.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SqlRow> findList() {
/* 101 */     this.queryType = Query.Type.LIST;
/* 102 */     return (List)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<SqlRow> findSet() {
/* 107 */     this.queryType = Query.Type.SET;
/* 108 */     return (Set)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<?, SqlRow> findMap() {
/* 113 */     this.queryType = Query.Type.MAP;
/* 114 */     return (Map)this.queryEngine.findMany(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public SpiSqlQuery getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public Query.Type getQueryType() { return this.queryType; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public EbeanServer getEbeanServer() { return this.ebeanServer; }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public SpiTransaction getTransaction() { return this.trans; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public boolean isLogSql() { return this.trans.isLogSql(); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public boolean isLogSummary() { return this.trans.isLogSummary(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\RelationalQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */