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
/*    */ public class PostgresDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   private static class PgVarcharFunction
/*    */     implements DbEncryptFunction
/*    */   {
/*    */     private PgVarcharFunction() {}
/*    */     
/* 39 */     public String getDecryptSql(String columnWithTableAlias) { return "pgp_sym_decrypt(" + columnWithTableAlias + ",?)"; }
/*    */ 
/*    */ 
/*    */     
/* 43 */     public String getEncryptBindSql() { return "pgp_sym_encrypt(?,?)"; }
/*    */   }
/*    */   
/*    */   private static class PgDateFunction
/*    */     implements DbEncryptFunction {
/*    */     private PgDateFunction() {}
/*    */     
/* 50 */     public String getDecryptSql(String columnWithTableAlias) { return "to_date(pgp_sym_decrypt(" + columnWithTableAlias + ",?),'YYYYMMDD')"; }
/*    */ 
/*    */ 
/*    */     
/* 54 */     public String getEncryptBindSql() { return "pgp_sym_encrypt(to_char(?::date,'YYYYMMDD'),?)"; }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\PostgresDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */