/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanSqlSelectFactory
/*    */ {
/*    */   public static BeanSqlSelect create(String sql) {
/*  9 */     sql = trim(sql);
/*    */     
/* 11 */     BeanSqlSelect.PredicatesType predicatesType = determinePredicatesType(sql);
/* 12 */     boolean hasOrderBy = determineHasOrderBy(sql);
/*    */     
/* 14 */     return new BeanSqlSelect(sql, predicatesType, hasOrderBy);
/*    */   }
/*    */ 
/*    */   
/* 18 */   private static boolean determineHasOrderBy(String sql) { return (sql.indexOf("${ORDER_BY}") > 0); }
/*    */ 
/*    */   
/*    */   private static BeanSqlSelect.PredicatesType determinePredicatesType(String sql) {
/* 22 */     if (sql.indexOf("${HAVING_PREDICATES}") > 0) {
/* 23 */       return BeanSqlSelect.PredicatesType.HAVING;
/*    */     }
/* 25 */     if (sql.indexOf("${WHERE_PREDICATES}") > 0) {
/* 26 */       return BeanSqlSelect.PredicatesType.WHERE;
/*    */     }
/* 28 */     if (sql.indexOf("${AND_PREDICATES}") > 0) {
/* 29 */       return BeanSqlSelect.PredicatesType.AND;
/*    */     }
/* 31 */     return BeanSqlSelect.PredicatesType.NONE;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String trim(String sql) {
/* 36 */     boolean removeWhitespace = false;
/*    */     
/* 38 */     int length = sql.length();
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     for (int i = 0; i < length; i++) {
/* 41 */       char c = sql.charAt(i);
/* 42 */       if (removeWhitespace) {
/* 43 */         if (!Character.isWhitespace(c)) {
/* 44 */           sb.append(c);
/* 45 */           removeWhitespace = false;
/*    */         }
/*    */       
/* 48 */       } else if (c == '\r' || c == '\n') {
/* 49 */         sb.append('\n');
/* 50 */         removeWhitespace = true;
/*    */       } else {
/* 52 */         sb.append(c);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 57 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanSqlSelectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */