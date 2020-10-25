/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
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
/*     */ public abstract class DeployBeanPropertyAssoc<T>
/*     */   extends DeployBeanProperty
/*     */ {
/*     */   Class<T> targetType;
/*  38 */   BeanCascadeInfo cascadeInfo = new BeanCascadeInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BeanTable beanTable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   DeployTableJoin tableJoin = new DeployTableJoin();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOuterJoin = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String extraWhere;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String mappedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanPropertyAssoc(DeployBeanDescriptor<?> desc, Class<T> targetType) {
/*  69 */     super(desc, targetType, null, null);
/*  70 */     this.targetType = targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public boolean isScalar() { return false; }
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
/*  89 */   public Class<T> getTargetType() { return this.targetType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public boolean isOuterJoin() { return this.isOuterJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void setOuterJoin(boolean isOuterJoin) { this.isOuterJoin = isOuterJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public String getExtraWhere() { return this.extraWhere; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void setExtraWhere(String extraWhere) { this.extraWhere = extraWhere; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public DeployTableJoin getTableJoin() { return this.tableJoin; }
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
/* 137 */   public BeanTable getBeanTable() { return this.beanTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeanTable(BeanTable beanTable) {
/* 144 */     this.beanTable = beanTable;
/* 145 */     getTableJoin().setTable(beanTable.getBaseTable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public BeanCascadeInfo getCascadeInfo() { return this.cascadeInfo; }
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
/* 164 */   public String getMappedBy() { return this.mappedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMappedBy(String mappedBy) {
/* 171 */     if (!"".equals(mappedBy))
/* 172 */       this.mappedBy = mappedBy; 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssoc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */