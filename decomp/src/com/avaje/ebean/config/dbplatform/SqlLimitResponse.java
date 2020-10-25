/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlLimitResponse
/*    */ {
/*    */   final String sql;
/*    */   final boolean includesRowNumberColumn;
/*    */   
/*    */   public SqlLimitResponse(String sql, boolean includesRowNumberColumn) {
/* 16 */     this.sql = sql;
/* 17 */     this.includesRowNumberColumn = includesRowNumberColumn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getSql() { return this.sql; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean isIncludesRowNumberColumn() { return this.includesRowNumberColumn; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\SqlLimitResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */