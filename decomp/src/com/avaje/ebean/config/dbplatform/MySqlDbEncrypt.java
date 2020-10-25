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
/*    */ public class MySqlDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   private static class MyVarcharFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private MyVarcharFunction() {}
/*    */     
/* 38 */     public String getDecryptSql(String columnWithTableAlias) { return "CONVERT(AES_DECRYPT(" + columnWithTableAlias + ",?) USING UTF8)"; }
/*    */ 
/*    */ 
/*    */     
/* 42 */     public String getEncryptBindSql() { return "AES_ENCRYPT(?,?)"; }
/*    */   }
/*    */   
/*    */   private static class MyDateFunction
/*    */     implements DbEncryptFunction {
/*    */     private MyDateFunction() {}
/*    */     
/* 49 */     public String getDecryptSql(String columnWithTableAlias) { return "STR_TO_DATE(AES_DECRYPT(" + columnWithTableAlias + ",?),'%Y%d%m')"; }
/*    */ 
/*    */ 
/*    */     
/* 53 */     public String getEncryptBindSql() { return "AES_ENCRYPT(DATE_FORMAT(?,'%Y%d%m'),?)"; }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\MySqlDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */