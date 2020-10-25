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
/*    */ public abstract class AbstractDbEncrypt
/*    */   implements DbEncrypt
/*    */ {
/*    */   protected DbEncryptFunction varcharEncryptFunction;
/*    */   protected DbEncryptFunction dateEncryptFunction;
/*    */   protected DbEncryptFunction timestampEncryptFunction;
/*    */   
/*    */   public DbEncryptFunction getDbEncryptFunction(int jdbcType) {
/* 62 */     switch (jdbcType) {
/*    */       case 12:
/* 64 */         return this.varcharEncryptFunction;
/*    */       case 2005:
/* 66 */         return this.varcharEncryptFunction;
/*    */       case 1:
/* 68 */         return this.varcharEncryptFunction;
/*    */       case -1:
/* 70 */         return this.varcharEncryptFunction;
/*    */       
/*    */       case 91:
/* 73 */         return this.dateEncryptFunction;
/*    */       
/*    */       case 93:
/* 76 */         return this.timestampEncryptFunction;
/*    */     } 
/*    */     
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 87 */   public int getEncryptDbType() { return -3; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 94 */   public boolean isBindEncryptDataFirst() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\AbstractDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */