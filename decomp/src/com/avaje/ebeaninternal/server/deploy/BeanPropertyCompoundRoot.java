/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class BeanPropertyCompoundRoot
/*     */ {
/*     */   private final BeanReflectSetter setter;
/*     */   private final Method writeMethod;
/*     */   private final String name;
/*     */   private final String fullBeanName;
/*     */   private final LinkedHashMap<String, BeanPropertyCompoundScalar> propMap;
/*     */   private final ArrayList<BeanPropertyCompoundScalar> propList;
/*     */   private List<CtCompoundProperty> nonScalarProperties;
/*     */   
/*     */   public BeanPropertyCompoundRoot(DeployBeanProperty deploy) {
/*  61 */     this.fullBeanName = deploy.getFullBeanName();
/*  62 */     this.name = deploy.getName();
/*  63 */     this.setter = deploy.getSetter();
/*  64 */     this.writeMethod = deploy.getWriteMethod();
/*  65 */     this.propList = new ArrayList();
/*  66 */     this.propMap = new LinkedHashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public BeanProperty[] getScalarProperties() { return (BeanProperty[])this.propList.toArray(new BeanProperty[this.propList.size()]); }
/*     */ 
/*     */   
/*     */   public void register(BeanPropertyCompoundScalar prop) {
/*  75 */     this.propList.add(prop);
/*  76 */     this.propMap.put(prop.getName(), prop);
/*     */   }
/*     */ 
/*     */   
/*  80 */   public BeanPropertyCompoundScalar getCompoundScalarProperty(String propName) { return (BeanPropertyCompoundScalar)this.propMap.get(propName); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public List<CtCompoundProperty> getNonScalarProperties() { return this.nonScalarProperties; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setNonScalarProperties(List<CtCompoundProperty> nonScalarProperties) { this.nonScalarProperties = nonScalarProperties; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRootValue(Object bean, Object value) {
/*     */     try {
/*  97 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  98 */         this.setter.set(bean, value);
/*     */       } else {
/* 100 */         Object[] args = new Object[1];
/* 101 */         args[0] = value;
/* 102 */         this.writeMethod.invoke(bean, args);
/*     */       } 
/* 104 */     } catch (Exception ex) {
/* 105 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/* 106 */       String msg = "set " + this.name + " with arg[" + value + "] on [" + this.fullBeanName + "] with type[" + beanType + "] threw error";
/* 107 */       throw new RuntimeException(msg, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRootValueIntercept(Object bean, Object value) {
/*     */     try {
/* 116 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/* 117 */         this.setter.setIntercept(bean, value);
/*     */       } else {
/* 119 */         Object[] args = new Object[1];
/* 120 */         args[0] = value;
/* 121 */         this.writeMethod.invoke(bean, args);
/*     */       } 
/* 123 */     } catch (Exception ex) {
/* 124 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/* 125 */       String msg = "setIntercept " + this.name + " arg[" + value + "] on [" + this.fullBeanName + "] with type[" + beanType + "] threw error";
/* 126 */       throw new RuntimeException(msg, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyCompoundRoot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */