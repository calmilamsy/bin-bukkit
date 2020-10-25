/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import java.util.List;
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
/*     */ public class DeployBeanTable
/*     */ {
/*     */   private final Class<?> beanType;
/*     */   private String baseTable;
/*     */   private List<DeployBeanProperty> idProperties;
/*     */   
/*  53 */   public DeployBeanTable(Class<?> beanType) { this.beanType = beanType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public String getBaseTable() { return this.baseTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public void setBaseTable(String baseTable) { this.baseTable = baseTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] createIdProperties(BeanDescriptorMap owner) {
/*  76 */     BeanProperty[] props = new BeanProperty[this.idProperties.size()];
/*  77 */     for (int i = 0; i < this.idProperties.size(); i++) {
/*  78 */       props[i] = createProperty(owner, (DeployBeanProperty)this.idProperties.get(i));
/*     */     }
/*  80 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty createProperty(BeanDescriptorMap owner, DeployBeanProperty prop) {
/*  86 */     if (prop instanceof DeployBeanPropertyAssocOne) {
/*  87 */       return new BeanPropertyAssocOne(owner, (DeployBeanPropertyAssocOne)prop);
/*     */     }
/*     */     
/*  90 */     return new BeanProperty(prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void setIdProperties(List<DeployBeanProperty> idProperties) { this.idProperties = idProperties; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public Class<?> getBeanType() { return this.beanType; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */