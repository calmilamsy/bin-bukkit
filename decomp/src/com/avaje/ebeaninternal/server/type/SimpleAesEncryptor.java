/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.config.EncryptKey;
/*     */ import com.avaje.ebean.config.Encryptor;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ public class SimpleAesEncryptor
/*     */   implements Encryptor
/*     */ {
/*     */   private static final String AES_CIPHER = "AES/CBC/PKCS5Padding";
/*     */   private static final String padding = "asldkalsdkadsdfkjsldfjl";
/*     */   
/*     */   private String paddKey(EncryptKey encryptKey) {
/*  47 */     String key = encryptKey.getStringValue();
/*  48 */     int addChars = 16 - key.length();
/*  49 */     if (addChars < 0)
/*  50 */       return key.substring(0, 16); 
/*  51 */     if (addChars > 0) {
/*  52 */       return key + "asldkalsdkadsdfkjsldfjl".substring(0, addChars);
/*     */     }
/*  54 */     return key;
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getKeyBytes(String skey) {
/*     */     try {
/*  60 */       return skey.getBytes("UTF-8");
/*  61 */     } catch (UnsupportedEncodingException e) {
/*  62 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  67 */   private IvParameterSpec getIvParameterSpec(String initialVector) { return new IvParameterSpec(initialVector.getBytes()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] data, EncryptKey encryptKey) {
/*  72 */     if (data == null) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     String key = paddKey(encryptKey);
/*     */ 
/*     */     
/*     */     try {
/*  80 */       byte[] keyBytes = getKeyBytes(key);
/*  81 */       IvParameterSpec iv = getIvParameterSpec(key);
/*     */       
/*  83 */       SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
/*  84 */       Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*     */       
/*  86 */       c.init(2, sks, iv);
/*     */       
/*  88 */       return c.doFinal(data);
/*     */     }
/*  90 */     catch (Exception e) {
/*  91 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] data, EncryptKey encryptKey) {
/*  97 */     if (data == null) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     String key = paddKey(encryptKey);
/*     */     
/*     */     try {
/* 104 */       byte[] keyBytes = getKeyBytes(key);
/* 105 */       IvParameterSpec iv = getIvParameterSpec(key);
/*     */       
/* 107 */       SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
/* 108 */       Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*     */       
/* 110 */       c.init(1, sks, iv);
/*     */       
/* 112 */       return c.doFinal(data);
/*     */     }
/* 114 */     catch (Exception e) {
/* 115 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String decryptString(byte[] data, EncryptKey key) {
/* 120 */     if (data == null) {
/* 121 */       return null;
/*     */     }
/*     */     
/* 124 */     byte[] bytes = decrypt(data, key);
/*     */     try {
/* 126 */       return new String(bytes, "UTF-8");
/*     */     }
/* 128 */     catch (UnsupportedEncodingException e) {
/* 129 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encryptString(String valueFormatValue, EncryptKey key) {
/* 135 */     if (valueFormatValue == null) {
/* 136 */       return null;
/*     */     }
/*     */     try {
/* 139 */       byte[] d = valueFormatValue.getBytes("UTF-8");
/* 140 */       return encrypt(d, key);
/*     */     }
/* 142 */     catch (UnsupportedEncodingException e) {
/* 143 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\SimpleAesEncryptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */