/*     */ package com.avaje.ebean.config.dbplatform;
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
/*     */ public class DbIdentity
/*     */ {
/*     */   private boolean supportsSequence;
/*     */   private boolean supportsIdentity;
/*     */   private boolean supportsGetGeneratedKeys;
/*     */   private String selectLastInsertedIdTemplate;
/*  19 */   private IdType idType = IdType.IDENTITY;
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
/*  31 */   public boolean isSupportsGetGeneratedKeys() { return this.supportsGetGeneratedKeys; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public void setSupportsGetGeneratedKeys(boolean supportsGetGeneratedKeys) { this.supportsGetGeneratedKeys = supportsGetGeneratedKeys; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectLastInsertedId(String table) {
/*  49 */     if (this.selectLastInsertedIdTemplate == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     return this.selectLastInsertedIdTemplate.replace("{table}", table);
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
/*  67 */   public void setSelectLastInsertedIdTemplate(String selectLastInsertedIdTemplate) { this.selectLastInsertedIdTemplate = selectLastInsertedIdTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public boolean isSupportsSequence() { return this.supportsSequence; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void setSupportsSequence(boolean supportsSequence) { this.supportsSequence = supportsSequence; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isSupportsIdentity() { return this.supportsIdentity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void setSupportsIdentity(boolean supportsIdentity) { this.supportsIdentity = supportsIdentity; }
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
/* 110 */   public IdType getIdType() { return this.idType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public void setIdType(IdType idType) { this.idType = idType; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\DbIdentity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */