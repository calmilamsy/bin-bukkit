/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class RawSql
/*     */ {
/*     */   private final Sql sql;
/*     */   private final ColumnMapping columnMapping;
/*     */   
/*     */   protected RawSql(Sql sql, ColumnMapping columnMapping) {
/* 170 */     this.sql = sql;
/* 171 */     this.columnMapping = columnMapping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public Sql getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public ColumnMapping getColumnMapping() { return this.columnMapping; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int queryHash() { return 31 * this.sql.queryHash() + this.columnMapping.queryHash(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Sql
/*     */   {
/*     */     private final boolean parsed;
/*     */ 
/*     */     
/*     */     private final String unparsedSql;
/*     */ 
/*     */     
/*     */     private final String preFrom;
/*     */ 
/*     */     
/*     */     private final String preWhere;
/*     */ 
/*     */     
/*     */     private final boolean andWhereExpr;
/*     */ 
/*     */     
/*     */     private final String preHaving;
/*     */ 
/*     */     
/*     */     private final boolean andHavingExpr;
/*     */     
/*     */     private final String orderBy;
/*     */     
/*     */     private final int queryHashCode;
/*     */ 
/*     */     
/*     */     protected Sql(String unparsedSql) {
/* 224 */       this.queryHashCode = unparsedSql.hashCode();
/* 225 */       this.parsed = false;
/* 226 */       this.unparsedSql = unparsedSql;
/* 227 */       this.preFrom = null;
/* 228 */       this.preHaving = null;
/* 229 */       this.preWhere = null;
/* 230 */       this.andHavingExpr = false;
/* 231 */       this.andWhereExpr = false;
/* 232 */       this.orderBy = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Sql(int queryHashCode, String preFrom, String preWhere, boolean andWhereExpr, String preHaving, boolean andHavingExpr, String orderBy) {
/* 241 */       this.queryHashCode = queryHashCode;
/* 242 */       this.parsed = true;
/* 243 */       this.unparsedSql = null;
/* 244 */       this.preFrom = preFrom;
/* 245 */       this.preHaving = preHaving;
/* 246 */       this.preWhere = preWhere;
/* 247 */       this.andHavingExpr = andHavingExpr;
/* 248 */       this.andWhereExpr = andWhereExpr;
/* 249 */       this.orderBy = orderBy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     public int queryHash() { return this.queryHashCode; }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 260 */       if (!this.parsed) {
/* 261 */         return "unparsed[" + this.unparsedSql + "]";
/*     */       }
/* 263 */       return "select[" + this.preFrom + "] preWhere[" + this.preWhere + "] preHaving[" + this.preHaving + "] orderBy[" + this.orderBy + "]";
/*     */     }
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
/* 275 */     public boolean isParsed() { return this.parsed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     public String getUnparsedSql() { return this.unparsedSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     public String getPreFrom() { return this.preFrom; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     public String getPreWhere() { return this.preWhere; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     public boolean isAndWhereExpr() { return this.andWhereExpr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     public String getPreHaving() { return this.preHaving; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     public boolean isAndHavingExpr() { return this.andHavingExpr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     public String getOrderBy() { return this.orderBy; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ColumnMapping
/*     */   {
/*     */     private final LinkedHashMap<String, Column> dbColumnMap;
/*     */ 
/*     */     
/*     */     private final Map<String, String> propertyMap;
/*     */ 
/*     */     
/*     */     private final Map<String, Column> propertyColumnMap;
/*     */ 
/*     */     
/*     */     private final boolean parsed;
/*     */ 
/*     */     
/*     */     private final boolean immutable;
/*     */     
/*     */     private final int queryHashCode;
/*     */ 
/*     */     
/*     */     protected ColumnMapping(List<Column> columns) {
/* 351 */       this.queryHashCode = 0;
/* 352 */       this.immutable = false;
/* 353 */       this.parsed = true;
/* 354 */       this.propertyMap = null;
/* 355 */       this.propertyColumnMap = null;
/* 356 */       this.dbColumnMap = new LinkedHashMap();
/* 357 */       for (int i = 0; i < columns.size(); i++) {
/* 358 */         Column c = (Column)columns.get(i);
/* 359 */         this.dbColumnMap.put(c.getDbColumn(), c);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping() {
/* 367 */       this.queryHashCode = 0;
/* 368 */       this.immutable = false;
/* 369 */       this.parsed = false;
/* 370 */       this.propertyMap = null;
/* 371 */       this.propertyColumnMap = null;
/* 372 */       this.dbColumnMap = new LinkedHashMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping(boolean parsed, LinkedHashMap<String, Column> dbColumnMap) {
/* 379 */       this.immutable = true;
/* 380 */       this.parsed = parsed;
/* 381 */       this.dbColumnMap = dbColumnMap;
/*     */       
/* 383 */       int hc = ColumnMapping.class.getName().hashCode();
/*     */       
/* 385 */       HashMap<String, Column> pcMap = new HashMap<String, Column>();
/* 386 */       HashMap<String, String> pMap = new HashMap<String, String>();
/*     */       
/* 388 */       for (Column c : dbColumnMap.values()) {
/* 389 */         pMap.put(c.getPropertyName(), c.getDbColumn());
/* 390 */         pcMap.put(c.getPropertyName(), c);
/*     */         
/* 392 */         hc = ((31 * hc) + c.getPropertyName() == null) ? 0 : c.getPropertyName().hashCode();
/* 393 */         hc = ((31 * hc) + c.getDbColumn() == null) ? 0 : c.getDbColumn().hashCode();
/*     */       } 
/* 395 */       this.propertyMap = Collections.unmodifiableMap(pMap);
/* 396 */       this.propertyColumnMap = Collections.unmodifiableMap(pcMap);
/* 397 */       this.queryHashCode = hc;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping createImmutableCopy() {
/* 408 */       for (Column c : this.dbColumnMap.values()) {
/* 409 */         c.checkMapping();
/*     */       }
/*     */       
/* 412 */       return new ColumnMapping(this.parsed, this.dbColumnMap);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void columnMapping(String dbColumn, String propertyName) {
/* 417 */       if (this.immutable) {
/* 418 */         throw new IllegalStateException("Should never happen");
/*     */       }
/* 420 */       if (!this.parsed) {
/* 421 */         int pos = this.dbColumnMap.size();
/* 422 */         this.dbColumnMap.put(dbColumn, new Column(pos, dbColumn, null, propertyName, null));
/*     */       } else {
/* 424 */         Column column = (Column)this.dbColumnMap.get(dbColumn);
/* 425 */         if (column == null) {
/* 426 */           String msg = "DB Column [" + dbColumn + "] not found in mapping. Expecting one of [" + this.dbColumnMap.keySet() + "]";
/*     */           
/* 428 */           throw new IllegalArgumentException(msg);
/*     */         } 
/* 430 */         column.setPropertyName(propertyName);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int queryHash() {
/* 438 */       if (this.queryHashCode == 0) {
/* 439 */         throw new RuntimeException("Bug: queryHashCode == 0");
/*     */       }
/* 441 */       return this.queryHashCode;
/*     */     }
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
/* 454 */     public boolean isParsed() { return this.parsed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     public int size() { return this.dbColumnMap.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     protected Map<String, Column> mapping() { return this.dbColumnMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 475 */     public Map<String, String> getMapping() { return this.propertyMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndexPosition(String property) {
/* 482 */       Column c = (Column)this.propertyColumnMap.get(property);
/* 483 */       return (c == null) ? -1 : c.getIndexPos();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     public Iterator<Column> getColumns() { return this.dbColumnMap.values().iterator(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public static class Column
/*     */     {
/*     */       private final int indexPos;
/*     */ 
/*     */       
/*     */       private final String dbColumn;
/*     */ 
/*     */       
/*     */       private final String dbAlias;
/*     */ 
/*     */       
/*     */       private String propertyName;
/*     */ 
/*     */ 
/*     */       
/* 509 */       public Column(int indexPos, String dbColumn, String dbAlias) { this(indexPos, dbColumn, dbAlias, null); }
/*     */ 
/*     */       
/*     */       private Column(int indexPos, String dbColumn, String dbAlias, String propertyName) {
/* 513 */         this.indexPos = indexPos;
/* 514 */         this.dbColumn = dbColumn;
/* 515 */         this.dbAlias = dbAlias;
/* 516 */         if (propertyName == null && dbAlias != null) {
/* 517 */           this.propertyName = dbAlias;
/*     */         } else {
/* 519 */           this.propertyName = propertyName;
/*     */         } 
/*     */       }
/*     */       
/*     */       private void checkMapping() {
/* 524 */         if (this.propertyName == null) {
/* 525 */           String msg = "No propertyName defined (Column mapping) for dbColumn [" + this.dbColumn + "]";
/* 526 */           throw new IllegalStateException(msg);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 531 */       public String toString() { return this.dbColumn + "->" + this.propertyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 538 */       public int getIndexPos() { return this.indexPos; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 545 */       public String getDbColumn() { return this.dbColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 552 */       public String getDbAlias() { return this.dbAlias; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 559 */       public String getPropertyName() { return this.propertyName; }
/*     */ 
/*     */ 
/*     */       
/* 563 */       private void setPropertyName(String propertyName) { this.propertyName = propertyName; }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\RawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */