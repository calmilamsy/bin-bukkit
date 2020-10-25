/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
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
/*     */ public class BeanPropertyCompoundScalar
/*     */   extends BeanProperty
/*     */ {
/*     */   private final BeanPropertyCompoundRoot rootProperty;
/*     */   private final CtCompoundProperty ctProperty;
/*     */   private final ScalarTypeConverter typeConverter;
/*     */   
/*     */   public BeanPropertyCompoundScalar(BeanPropertyCompoundRoot rootProperty, DeployBeanProperty scalarDeploy, CtCompoundProperty ctProperty, ScalarTypeConverter<?, ?> typeConverter) {
/*  44 */     super(scalarDeploy);
/*  45 */     this.rootProperty = rootProperty;
/*  46 */     this.ctProperty = ctProperty;
/*  47 */     this.typeConverter = typeConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(Object valueObject) {
/*  53 */     if (this.typeConverter != null) {
/*  54 */       valueObject = this.typeConverter.unwrapValue(valueObject);
/*     */     }
/*  56 */     return this.ctProperty.getValue(valueObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public void setValue(Object bean, Object value) { setValueInCompound(bean, value, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueInCompound(Object bean, Object value, boolean intercept) {
/*  67 */     Object compoundValue = this.ctProperty.setValue(bean, value);
/*     */     
/*  69 */     if (compoundValue != null) {
/*  70 */       if (this.typeConverter != null) {
/*  71 */         compoundValue = this.typeConverter.wrapValue(compoundValue);
/*     */       }
/*     */ 
/*     */       
/*  75 */       if (intercept) {
/*  76 */         this.rootProperty.setRootValueIntercept(bean, compoundValue);
/*     */       } else {
/*  78 */         this.rootProperty.setRootValue(bean, compoundValue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setValueIntercept(Object bean, Object value) { setValueInCompound(bean, value, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public Object getValueIntercept(Object bean) { return getValue(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Object elGetReference(Object bean) { return getValue(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public Object elGetValue(Object bean) { return getValue(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void elSetReference(Object bean) { super.elSetReference(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) { super.elSetValue(bean, value, populate, reference); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyCompoundScalar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */