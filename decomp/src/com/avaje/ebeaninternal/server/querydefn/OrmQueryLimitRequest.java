/*    */ package com.avaje.ebeaninternal.server.querydefn;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ 
/*    */ 
/*    */ public class OrmQueryLimitRequest
/*    */   implements SqlLimitRequest
/*    */ {
/*    */   final SpiQuery<?> ormQuery;
/*    */   final String sql;
/*    */   final String sqlOrderBy;
/*    */   
/*    */   public OrmQueryLimitRequest(String sql, String sqlOrderBy, SpiQuery<?> ormQuery) {
/* 15 */     this.sql = sql;
/* 16 */     this.sqlOrderBy = sqlOrderBy;
/* 17 */     this.ormQuery = ormQuery;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public String getDbOrderBy() { return this.sqlOrderBy; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public String getDbSql() { return this.sql; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public int getFirstRow() { return this.ormQuery.getFirstRow(); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public int getMaxRows() { return this.ormQuery.getMaxRows(); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isDistinct() { return this.ormQuery.isDistinct(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\OrmQueryLimitRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */