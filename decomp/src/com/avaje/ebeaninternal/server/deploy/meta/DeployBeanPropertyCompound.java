/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundRoot;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundScalar;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundType;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DeployBeanPropertyCompound
/*     */   extends DeployBeanProperty
/*     */ {
/*     */   final CtCompoundType<?> compoundType;
/*     */   final ScalarTypeConverter<?, ?> typeConverter;
/*     */   DeployBeanEmbedded deployEmbedded;
/*     */   
/*     */   public DeployBeanPropertyCompound(DeployBeanDescriptor<?> desc, Class<?> targetType, CtCompoundType<?> compoundType, ScalarTypeConverter<?, ?> typeConverter) {
/*  55 */     super(desc, targetType, null, null);
/*  56 */     this.compoundType = compoundType;
/*  57 */     this.typeConverter = typeConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyCompoundRoot getFlatProperties(BeanDescriptorMap owner, BeanDescriptor<?> descriptor) {
/*  66 */     BeanPropertyCompoundRoot rootProperty = new BeanPropertyCompoundRoot(this);
/*     */ 
/*     */ 
/*     */     
/*  70 */     CtCompoundTypeScalarList ctMeta = new CtCompoundTypeScalarList();
/*     */     
/*  72 */     this.compoundType.accumulateScalarTypes(null, ctMeta);
/*     */     
/*  74 */     List<BeanProperty> beanPropertyList = new ArrayList<BeanProperty>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     for (Map.Entry<String, ScalarType<?>> entry : ctMeta.entries()) {
/*     */       
/*  83 */       String relativePropertyName = (String)entry.getKey();
/*  84 */       ScalarType<?> scalarType = (ScalarType)entry.getValue();
/*     */       
/*  86 */       CtCompoundProperty ctProp = ctMeta.getCompoundType(relativePropertyName);
/*     */ 
/*     */       
/*  89 */       String dbColumn = relativePropertyName.replace(".", "_");
/*  90 */       dbColumn = getDbColumn(relativePropertyName, dbColumn);
/*     */       
/*  92 */       DeployBeanProperty deploy = new DeployBeanProperty(null, scalarType.getType(), scalarType, null);
/*  93 */       deploy.setScalarType(scalarType);
/*  94 */       deploy.setDbColumn(dbColumn);
/*  95 */       deploy.setName(relativePropertyName);
/*  96 */       deploy.setDbInsertable(true);
/*  97 */       deploy.setDbUpdateable(true);
/*  98 */       deploy.setDbRead(true);
/*     */       
/* 100 */       BeanPropertyCompoundScalar bp = new BeanPropertyCompoundScalar(rootProperty, deploy, ctProp, this.typeConverter);
/* 101 */       beanPropertyList.add(bp);
/*     */       
/* 103 */       rootProperty.register(bp);
/*     */     } 
/*     */     
/* 106 */     rootProperty.setNonScalarProperties(ctMeta.getNonScalarProperties());
/* 107 */     return rootProperty;
/*     */   }
/*     */   
/*     */   private String getDbColumn(String propName, String defaultDbColumn) {
/* 111 */     if (this.deployEmbedded == null) {
/* 112 */       return defaultDbColumn;
/*     */     }
/* 114 */     String dbColumn = (String)this.deployEmbedded.getPropertyColumnMap().get(propName);
/* 115 */     return (dbColumn == null) ? defaultDbColumn : dbColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanEmbedded getDeployEmbedded() {
/* 124 */     if (this.deployEmbedded == null) {
/* 125 */       this.deployEmbedded = new DeployBeanEmbedded();
/*     */     }
/* 127 */     return this.deployEmbedded;
/*     */   }
/*     */ 
/*     */   
/* 131 */   public ScalarTypeConverter<?, ?> getTypeConverter() { return this.typeConverter; }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public CtCompoundType<?> getCompoundType() { return this.compoundType; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyCompound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */