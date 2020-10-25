/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.RawSql;
/*    */ import javax.persistence.NamedQuery;
/*    */ import javax.persistence.QueryHint;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployNamedQuery
/*    */ {
/*    */   private final String name;
/*    */   private final String query;
/*    */   private final QueryHint[] hints;
/*    */   private final DRawSqlSelect sqlSelect;
/*    */   private final RawSql rawSql;
/*    */   
/*    */   public DeployNamedQuery(NamedQuery namedQuery) {
/* 21 */     this.name = namedQuery.name();
/* 22 */     this.query = namedQuery.query();
/* 23 */     this.hints = namedQuery.hints();
/* 24 */     this.sqlSelect = null;
/* 25 */     this.rawSql = null;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(String name, String query, QueryHint[] hints) {
/* 29 */     this.name = name;
/* 30 */     this.query = query;
/* 31 */     this.hints = hints;
/* 32 */     this.sqlSelect = null;
/* 33 */     this.rawSql = null;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(String name, RawSql rawSql) {
/* 37 */     this.name = name;
/* 38 */     this.query = null;
/* 39 */     this.hints = null;
/* 40 */     this.sqlSelect = null;
/* 41 */     this.rawSql = rawSql;
/*    */   }
/*    */   
/*    */   public DeployNamedQuery(DRawSqlSelect sqlSelect) {
/* 45 */     this.name = sqlSelect.getName();
/* 46 */     this.query = null;
/* 47 */     this.hints = null;
/* 48 */     this.sqlSelect = sqlSelect;
/* 49 */     this.rawSql = null;
/*    */   }
/*    */ 
/*    */   
/* 53 */   public boolean isRawSql() { return (this.rawSql != null); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean isSqlSelect() { return (this.sqlSelect != null); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public String getQuery() { return this.query; }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public QueryHint[] getHints() { return this.hints; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public RawSql getRawSql() { return this.rawSql; }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public DRawSqlSelect getSqlSelect() { return this.sqlSelect; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployNamedQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */