/*     */ package com.avaje.ebean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawSqlBuilder
/*     */ {
/*     */   public static final String IGNORE_COLUMN = "$$_IGNORE_COLUMN_$$";
/*     */   private final RawSql.Sql sql;
/*     */   private final RawSql.ColumnMapping columnMapping;
/*     */   
/*     */   public static RawSqlBuilder unparsed(String sql) {
/*  54 */     RawSql.Sql s = new RawSql.Sql(sql);
/*  55 */     return new RawSqlBuilder(s, new RawSql.ColumnMapping());
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
/*     */   
/*     */   public static RawSqlBuilder parse(String sql) {
/*  73 */     RawSql.Sql sql2 = DRawSqlParser.parse(sql);
/*  74 */     String select = sql2.getPreFrom();
/*     */     
/*  76 */     RawSql.ColumnMapping mapping = DRawSqlColumnsParser.parse(select);
/*  77 */     return new RawSqlBuilder(sql2, mapping);
/*     */   }
/*     */   
/*     */   private RawSqlBuilder(RawSql.Sql sql, RawSql.ColumnMapping columnMapping) {
/*  81 */     this.sql = sql;
/*  82 */     this.columnMapping = columnMapping;
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
/*     */   public RawSqlBuilder columnMapping(String dbColumn, String propertyName) {
/*  98 */     this.columnMapping.columnMapping(dbColumn, propertyName);
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public RawSqlBuilder columnMappingIgnore(String dbColumn) { return columnMapping(dbColumn, "$$_IGNORE_COLUMN_$$"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public RawSql create() { return new RawSql(this.sql, this.columnMapping.createImmutableCopy()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   protected RawSql.Sql getSql() { return this.sql; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\RawSqlBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */