/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LimitOffsetSqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   private static final String LIMIT = "limit";
/*    */   private static final String OFFSET = "offset";
/*    */   
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 22 */     StringBuilder sb = new StringBuilder('Ȁ');
/* 23 */     sb.append("select ");
/* 24 */     if (request.isDistinct()) {
/* 25 */       sb.append("distinct ");
/*    */     }
/*    */     
/* 28 */     sb.append(request.getDbSql());
/*    */     
/* 30 */     int firstRow = request.getFirstRow();
/* 31 */     int maxRows = request.getMaxRows();
/* 32 */     if (maxRows > 0) {
/* 33 */       maxRows++;
/*    */     }
/*    */     
/* 36 */     sb.append(" ").append('\n').append("limit").append(" ");
/* 37 */     if (maxRows > 0) {
/* 38 */       sb.append(maxRows);
/*    */     }
/* 40 */     if (firstRow > 0) {
/* 41 */       sb.append(" ").append("offset").append(" ");
/* 42 */       sb.append(firstRow);
/*    */     } 
/*    */     
/* 45 */     return new SqlLimitResponse(sb.toString(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\LimitOffsetSqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */