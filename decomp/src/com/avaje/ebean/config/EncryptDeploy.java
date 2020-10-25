/*     */ package com.avaje.ebean.config;
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
/*     */ public class EncryptDeploy
/*     */ {
/*  38 */   public static final EncryptDeploy NO_ENCRYPT = new EncryptDeploy(Mode.MODE_NO_ENCRYPT, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final EncryptDeploy ANNOTATION = new EncryptDeploy(Mode.MODE_ANNOTATION, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final EncryptDeploy ENCRYPT_DB = new EncryptDeploy(Mode.MODE_ENCRYPT, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final EncryptDeploy ENCRYPT_CLIENT = new EncryptDeploy(Mode.MODE_ENCRYPT, false, false);
/*     */   
/*     */   private final Mode mode;
/*     */   
/*     */   private final boolean dbEncrypt;
/*     */   
/*     */   private final int dbLength;
/*     */   
/*     */   public enum Mode
/*     */   {
/*  63 */     MODE_ENCRYPT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     MODE_NO_ENCRYPT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     MODE_ANNOTATION;
/*     */   }
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
/*     */   public EncryptDeploy(Mode mode, boolean dbEncrypt, int dbLength) {
/*  92 */     this.mode = mode;
/*  93 */     this.dbEncrypt = dbEncrypt;
/*  94 */     this.dbLength = dbLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Mode getMode() { return this.mode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public boolean isDbEncrypt() { return this.dbEncrypt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public int getDbLength() { return this.dbLength; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\EncryptDeploy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */