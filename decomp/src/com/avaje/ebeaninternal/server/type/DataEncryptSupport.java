/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.EncryptKey;
/*    */ import com.avaje.ebean.config.EncryptKeyManager;
/*    */ import com.avaje.ebean.config.Encryptor;
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
/*    */ public class DataEncryptSupport
/*    */ {
/*    */   private final EncryptKeyManager encryptKeyManager;
/*    */   private final Encryptor encryptor;
/*    */   private final String table;
/*    */   private final String column;
/*    */   
/*    */   public DataEncryptSupport(EncryptKeyManager encryptKeyManager, Encryptor encryptor, String table, String column) {
/* 34 */     this.encryptKeyManager = encryptKeyManager;
/* 35 */     this.encryptor = encryptor;
/* 36 */     this.table = table;
/* 37 */     this.column = column;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] data) {
/* 42 */     EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
/* 43 */     return this.encryptor.encrypt(data, key);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] decrypt(byte[] data) {
/* 48 */     EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
/* 49 */     return this.encryptor.decrypt(data, key);
/*    */   }
/*    */   
/*    */   public String decryptObject(byte[] data) {
/* 53 */     EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
/* 54 */     return this.encryptor.decryptString(data, key);
/*    */   }
/*    */   
/*    */   public <T> byte[] encryptObject(String formattedValue) {
/* 58 */     EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
/* 59 */     return this.encryptor.encryptString(formattedValue, key);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\DataEncryptSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */