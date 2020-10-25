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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class H2DbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/* 39 */   public boolean isBindEncryptDataFirst() { return false; }
/*    */   
/*    */   private static class H2VarcharFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private H2VarcharFunction() {}
/*    */     
/* 46 */     public String getDecryptSql(String columnWithTableAlias) { return "TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + ")))"; }
/*    */ 
/*    */ 
/*    */     
/* 50 */     public String getEncryptBindSql() { return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(?))"; }
/*    */   }
/*    */   
/*    */   private static class H2DateFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private H2DateFunction() {}
/*    */     
/* 58 */     public String getDecryptSql(String columnWithTableAlias) { return "PARSEDATETIME(TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + "))),'yyyyMMdd')"; }
/*    */ 
/*    */ 
/*    */     
/* 62 */     public String getEncryptBindSql() { return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(FORMATDATETIME(?,'yyyyMMdd')))"; }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\H2DbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */