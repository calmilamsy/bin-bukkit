/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.CallableSql;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiCallableSql;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import java.io.Serializable;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.SQLException;
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
/*     */ public class DefaultCallableSql
/*     */   implements Serializable, SpiCallableSql
/*     */ {
/*     */   private static final long serialVersionUID = 8984272253185424701L;
/*     */   private final EbeanServer server;
/*     */   private String sql;
/*     */   private String label;
/*     */   private int timeout;
/*     */   private TransactionEventTable transactionEvent;
/*     */   private BindParams bindParameters;
/*     */   
/*     */   public DefaultCallableSql(EbeanServer server, String sql) {
/*  56 */     this.transactionEvent = new TransactionEventTable();
/*     */     
/*  58 */     this.bindParameters = new BindParams();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.server = server;
/*  65 */     this.sql = sql;
/*     */   }
/*     */ 
/*     */   
/*  69 */   public void execute() { this.server.execute(this, null); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public String getLabel() { return this.label; }
/*     */ 
/*     */   
/*     */   public CallableSql setLabel(String label) {
/*  77 */     this.label = label;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  82 */   public int getTimeout() { return this.timeout; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String getSql() { return this.sql; }
/*     */ 
/*     */   
/*     */   public CallableSql setTimeout(int secs) {
/*  90 */     this.timeout = secs;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql setSql(String sql) {
/*  95 */     this.sql = sql;
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql bind(int position, Object value) {
/* 100 */     this.bindParameters.setParameter(position, value);
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql setParameter(int position, Object value) {
/* 105 */     this.bindParameters.setParameter(position, value);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql registerOut(int position, int type) {
/* 110 */     this.bindParameters.registerOut(position, type);
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public Object getObject(int position) {
/* 115 */     BindParams.Param p = this.bindParameters.getParameter(position);
/* 116 */     return p.getOutValue();
/*     */   }
/*     */ 
/*     */   
/* 120 */   public boolean executeOverride(CallableStatement cstmt) throws SQLException { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableSql addModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
/* 126 */     this.transactionEvent.add(tableName, inserts, updates, deletes);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public TransactionEventTable getTransactionEventTable() { return this.transactionEvent; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public BindParams getBindParams() { return this.bindParameters; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */