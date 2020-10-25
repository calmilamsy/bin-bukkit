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
/*    */ 
/*    */ 
/*    */ public class RowNumberSqlLimiter
/*    */   implements SqlLimiter
/*    */ {
/*    */   private static final String ROW_NUMBER_OVER = "row_number() over (order by ";
/*    */   private static final String ROW_NUMBER_AS = ") as rn, ";
/*    */   final String rowNumberWindowAlias;
/*    */   
/* 24 */   public RowNumberSqlLimiter(String rowNumberWindowAlias) { this.rowNumberWindowAlias = rowNumberWindowAlias; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public RowNumberSqlLimiter() { this("as limitresult"); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SqlLimitResponse limit(SqlLimitRequest request) {
/* 34 */     StringBuilder sb = new StringBuilder('Ǵ');
/*    */     
/* 36 */     int firstRow = request.getFirstRow();
/*    */     
/* 38 */     int lastRow = request.getMaxRows();
/* 39 */     if (lastRow > 0) {
/* 40 */       lastRow = lastRow + firstRow + 1;
/*    */     }
/*    */     
/* 43 */     sb.append("select * from (").append('\n');
/*    */ 
/*    */     
/* 46 */     sb.append("select ");
/* 47 */     if (request.isDistinct()) {
/* 48 */       sb.append("distinct ");
/*    */     }
/*    */     
/* 51 */     sb.append("row_number() over (order by ");
/* 52 */     sb.append(request.getDbOrderBy());
/* 53 */     sb.append(") as rn, ");
/*    */     
/* 55 */     sb.append(request.getDbSql());
/*    */     
/* 57 */     sb.append('\n').append(") ");
/* 58 */     sb.append(this.rowNumberWindowAlias);
/* 59 */     sb.append(" where ");
/* 60 */     if (firstRow > 0) {
/* 61 */       sb.append(" rn > ").append(firstRow);
/* 62 */       if (lastRow > 0) {
/* 63 */         sb.append(" and ");
/*    */       }
/*    */     } 
/* 66 */     if (lastRow > 0) {
/* 67 */       sb.append(" rn <= ").append(lastRow);
/*    */     }
/*    */     
/* 70 */     return new SqlLimitResponse(sb.toString(), true);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\RowNumberSqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */