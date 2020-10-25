/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParserRawSql
/*    */   extends DeployParser
/*    */ {
/*    */   private final DRawSqlSelect rawSqlSelect;
/*    */   
/* 16 */   public DeployPropertyParserRawSql(DRawSqlSelect rawSqlSelect) { this.rawSqlSelect = rawSqlSelect; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Set<String> getIncludes() { return null; }
/*    */ 
/*    */   
/*    */   public String convertWord() {
/* 27 */     String r = getDeployWord(this.word);
/* 28 */     return (r == null) ? this.word : r;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 33 */     DRawSqlColumnInfo columnInfo = this.rawSqlSelect.getRawSqlColumnInfo(expression);
/* 34 */     if (columnInfo == null) {
/* 35 */       return null;
/*    */     }
/* 37 */     return columnInfo.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParserRawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */