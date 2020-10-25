/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
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
/*     */ public class DeployBeanPropertyAssocOne<T>
/*     */   extends DeployBeanPropertyAssoc<T>
/*     */ {
/*     */   boolean oneToOne;
/*     */   boolean oneToOneExported;
/*     */   boolean importedPrimaryKey;
/*     */   DeployBeanEmbedded deployEmbedded;
/*     */   
/*  42 */   public DeployBeanPropertyAssocOne(DeployBeanDescriptor<?> desc, Class<T> targetType) { super(desc, targetType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanEmbedded getDeployEmbedded() {
/*  51 */     if (this.deployEmbedded == null) {
/*  52 */       this.deployEmbedded = new DeployBeanEmbedded();
/*     */     }
/*  54 */     return this.deployEmbedded;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDbColumn() {
/*  59 */     DeployTableJoinColumn[] columns = this.tableJoin.columns();
/*  60 */     if (columns.length == 1) {
/*  61 */       return columns[0].getLocalDbColumn();
/*     */     }
/*  63 */     return super.getDbColumn();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getElPlaceHolder(BeanDescriptor.EntityType et) { return super.getElPlaceHolder(et); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isOneToOne() { return this.oneToOne; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void setOneToOne(boolean oneToOne) { this.oneToOne = oneToOne; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean isOneToOneExported() { return this.oneToOneExported; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public void setOneToOneExported(boolean oneToOneExported) { this.oneToOneExported = oneToOneExported; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public boolean isImportedPrimaryKey() { return this.importedPrimaryKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void setImportedPrimaryKey(boolean importedPrimaryKey) { this.importedPrimaryKey = importedPrimaryKey; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */