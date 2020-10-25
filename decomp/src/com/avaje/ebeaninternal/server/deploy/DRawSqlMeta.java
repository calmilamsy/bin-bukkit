/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.annotation.SqlSelect;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DRawSqlMeta
/*     */ {
/*     */   private String name;
/*     */   private String tableAlias;
/*     */   private String extend;
/*     */   private String query;
/*     */   private boolean debug;
/*     */   private String where;
/*     */   private String having;
/*     */   private String columnMapping;
/*     */   
/*     */   public DRawSqlMeta(SqlSelect sqlSelect) {
/*  24 */     this.debug = sqlSelect.debug();
/*  25 */     this.name = sqlSelect.name();
/*  26 */     this.tableAlias = toNull(sqlSelect.tableAlias());
/*  27 */     this.extend = toNull(sqlSelect.extend());
/*  28 */     this.having = toNull(sqlSelect.having());
/*  29 */     this.where = toNull(sqlSelect.where());
/*  30 */     this.columnMapping = toNull(sqlSelect.columnMapping());
/*  31 */     this.query = toNull(sqlSelect.query());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DRawSqlMeta(String name, String extend, String query, boolean debug, String where, String having, String columnMapping) {
/*  37 */     this.name = name;
/*  38 */     this.extend = extend;
/*  39 */     this.query = query;
/*  40 */     this.debug = debug;
/*  41 */     this.having = having;
/*  42 */     this.where = where;
/*  43 */     this.columnMapping = columnMapping;
/*     */   }
/*     */ 
/*     */   
/*  47 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public void setName(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public void setTableAlias(String tableAlias) { this.tableAlias = tableAlias; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public String getTableAlias() { return this.tableAlias; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String getExtend() { return this.extend; }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void setExtend(String extend) { this.extend = extend; }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public void setQuery(String query) { this.query = query; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean isDebug() { return this.debug; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void setDebug(boolean debug) { this.debug = debug; }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String getWhere() { return this.where; }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void setWhere(String where) { this.where = where; }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public String getHaving() { return this.having; }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void setHaving(String having) { this.having = having; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public String getColumnMapping() { return this.columnMapping; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public void setColumnMapping(String columnMapping) { this.columnMapping = columnMapping; }
/*     */ 
/*     */   
/*     */   public void extend(DRawSqlMeta parentQuery) {
/* 111 */     extendQuery(parentQuery.getQuery());
/* 112 */     extendColumnMapping(parentQuery.getColumnMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extendQuery(String parentSql) {
/* 119 */     if (this.query == null) {
/* 120 */       this.query = parentSql;
/*     */     } else {
/* 122 */       this.query = parentSql + " " + this.query;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extendColumnMapping(String parentColumnMapping) {
/* 127 */     if (this.columnMapping == null) {
/* 128 */       this.columnMapping = parentColumnMapping;
/*     */     }
/*     */   }
/*     */   
/*     */   private static String toNull(String s) {
/* 133 */     if (s != null && s.equals("")) {
/* 134 */       return null;
/*     */     }
/* 136 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */