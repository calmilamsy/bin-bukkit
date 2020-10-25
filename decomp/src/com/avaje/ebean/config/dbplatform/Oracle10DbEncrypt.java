/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Oracle10DbEncrypt
/*     */   extends AbstractDbEncrypt
/*     */ {
/*     */   public Oracle10DbEncrypt() {
/*  75 */     String encryptfunction = GlobalProperties.get("ebean.oracle.encryptfunction", "eb_encrypt");
/*  76 */     String decryptfunction = GlobalProperties.get("ebean.oracle.decryptfunction", "eb_decrypt");
/*     */     
/*  78 */     this.varcharEncryptFunction = new OraVarcharFunction(encryptfunction, decryptfunction);
/*  79 */     this.dateEncryptFunction = new OraDateFunction(encryptfunction, decryptfunction);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class OraVarcharFunction
/*     */     implements DbEncryptFunction
/*     */   {
/*     */     private final String encryptfunction;
/*     */     
/*     */     private final String decryptfunction;
/*     */     
/*     */     public OraVarcharFunction(String encryptfunction, String decryptfunction) {
/*  91 */       this.encryptfunction = encryptfunction;
/*  92 */       this.decryptfunction = decryptfunction;
/*     */     }
/*     */ 
/*     */     
/*  96 */     public String getDecryptSql(String columnWithTableAlias) { return this.decryptfunction + "(" + columnWithTableAlias + ",?)"; }
/*     */ 
/*     */ 
/*     */     
/* 100 */     public String getEncryptBindSql() { return this.encryptfunction + "(?,?)"; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class OraDateFunction
/*     */     implements DbEncryptFunction
/*     */   {
/*     */     private final String encryptfunction;
/*     */     
/*     */     private final String decryptfunction;
/*     */ 
/*     */     
/*     */     public OraDateFunction(String encryptfunction, String decryptfunction) {
/* 114 */       this.encryptfunction = encryptfunction;
/* 115 */       this.decryptfunction = decryptfunction;
/*     */     }
/*     */ 
/*     */     
/* 119 */     public String getDecryptSql(String columnWithTableAlias) { return "to_date(" + this.decryptfunction + "(" + columnWithTableAlias + ",?),'YYYYMMDD')"; }
/*     */ 
/*     */ 
/*     */     
/* 123 */     public String getEncryptBindSql() { return this.encryptfunction + "(to_char(?,'YYYYMMDD'),?)"; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\Oracle10DbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */