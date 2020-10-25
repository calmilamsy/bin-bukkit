/*     */ package com.avaje.ebeaninternal.server.reflect;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Arrays;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public final class EnhanceBeanReflect
/*     */   implements BeanReflect
/*     */ {
/*  23 */   private static final Object[] constuctorArgs = new Object[0];
/*     */   
/*     */   private final Class<?> clazz;
/*     */   private final EntityBean entityBean;
/*     */   private final Constructor<?> constructor;
/*     */   private final Constructor<?> vanillaConstructor;
/*     */   private final boolean hasNewInstanceMethod;
/*     */   private final boolean vanillaOnly;
/*     */   
/*     */   public EnhanceBeanReflect(Class<?> vanillaType, Class<?> clazz) {
/*     */     try {
/*  34 */       this.clazz = clazz;
/*  35 */       if (Modifier.isAbstract(clazz.getModifiers())) {
/*  36 */         this.entityBean = null;
/*  37 */         this.constructor = null;
/*  38 */         this.vanillaConstructor = null;
/*  39 */         this.hasNewInstanceMethod = false;
/*  40 */         this.vanillaOnly = false;
/*     */       } else {
/*  42 */         this.vanillaConstructor = defaultConstructor(vanillaType);
/*  43 */         this.constructor = defaultConstructor(clazz);
/*     */         
/*  45 */         Object newInstance = clazz.newInstance();
/*  46 */         if (newInstance instanceof EntityBean) {
/*  47 */           this.entityBean = (EntityBean)newInstance;
/*  48 */           this.vanillaOnly = false;
/*  49 */           this.hasNewInstanceMethod = hasNewInstanceMethod(clazz);
/*     */         } else {
/*     */           
/*  52 */           this.entityBean = null;
/*  53 */           this.vanillaOnly = true;
/*  54 */           this.hasNewInstanceMethod = false;
/*     */         } 
/*     */       } 
/*  57 */     } catch (InstantiationException e) {
/*  58 */       throw new PersistenceException(e);
/*  59 */     } catch (IllegalAccessException e) {
/*  60 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Constructor<?> defaultConstructor(Class<?> cls) {
/*     */     try {
/*  66 */       Class[] params = new Class[0];
/*  67 */       return cls.getDeclaredConstructor(params);
/*  68 */     } catch (Exception ex) {
/*  69 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasNewInstanceMethod(Class<?> clazz) {
/*  74 */     Class[] params = new Class[0];
/*     */     try {
/*  76 */       Method method = clazz.getMethod("_ebean_newInstance", params);
/*  77 */       if (method == null) {
/*  78 */         return false;
/*     */       }
/*     */       try {
/*  81 */         Object o = this.constructor.newInstance(constuctorArgs);
/*  82 */         method.invoke(o, new Object[0]);
/*  83 */         return true;
/*     */       }
/*  85 */       catch (AbstractMethodError e) {
/*  86 */         return false;
/*     */       }
/*  88 */       catch (InvocationTargetException e) {
/*  89 */         return false;
/*     */       }
/*  91 */       catch (Exception e) {
/*  92 */         throw new RuntimeException("Unexpected? ", e);
/*     */       } 
/*  94 */     } catch (SecurityException e) {
/*  95 */       return false;
/*  96 */     } catch (NoSuchMethodException e) {
/*  97 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public boolean isVanillaOnly() { return this.vanillaOnly; }
/*     */ 
/*     */   
/*     */   public Object createEntityBean() {
/* 108 */     if (this.hasNewInstanceMethod) {
/* 109 */       return this.entityBean._ebean_newInstance();
/*     */     }
/*     */     try {
/* 112 */       return this.constructor.newInstance(constuctorArgs);
/* 113 */     } catch (Exception ex) {
/* 114 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createVanillaBean() {
/*     */     try {
/* 121 */       return this.vanillaConstructor.newInstance(constuctorArgs);
/* 122 */     } catch (Exception ex) {
/* 123 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getFieldIndex(String fieldName) {
/* 128 */     if (this.entityBean == null) {
/* 129 */       throw new RuntimeException("Trying to get fieldName on abstract class " + this.clazz);
/*     */     }
/* 131 */     String[] fields = this.entityBean._ebean_getFieldNames();
/* 132 */     for (int i = 0; i < fields.length; i++) {
/* 133 */       if (fieldName.equals(fields[i])) {
/* 134 */         return i;
/*     */       }
/*     */     } 
/* 137 */     String fieldList = Arrays.toString(fields);
/* 138 */     String msg = "field [" + fieldName + "] not found in [" + this.clazz.getName() + "]" + fieldList;
/* 139 */     throw new IllegalArgumentException(msg);
/*     */   }
/*     */   
/*     */   public BeanReflectGetter getGetter(String name) {
/* 143 */     int i = getFieldIndex(name);
/* 144 */     return new Getter(i, this.entityBean);
/*     */   }
/*     */   
/*     */   public BeanReflectSetter getSetter(String name) {
/* 148 */     int i = getFieldIndex(name);
/* 149 */     return new Setter(i, this.entityBean);
/*     */   }
/*     */   
/*     */   static final class Getter implements BeanReflectGetter {
/*     */     private final int fieldIndex;
/*     */     private final EntityBean entityBean;
/*     */     
/*     */     Getter(int fieldIndex, EntityBean entityBean) {
/* 157 */       this.fieldIndex = fieldIndex;
/* 158 */       this.entityBean = entityBean;
/*     */     }
/*     */ 
/*     */     
/* 162 */     public Object get(Object bean) { return this.entityBean._ebean_getField(this.fieldIndex, bean); }
/*     */ 
/*     */ 
/*     */     
/* 166 */     public Object getIntercept(Object bean) { return this.entityBean._ebean_getFieldIntercept(this.fieldIndex, bean); }
/*     */   }
/*     */   
/*     */   static final class Setter
/*     */     implements BeanReflectSetter {
/*     */     private final int fieldIndex;
/*     */     private final EntityBean entityBean;
/*     */     
/*     */     Setter(int fieldIndex, EntityBean entityBean) {
/* 175 */       this.fieldIndex = fieldIndex;
/* 176 */       this.entityBean = entityBean;
/*     */     }
/*     */ 
/*     */     
/* 180 */     public void set(Object bean, Object value) { this.entityBean._ebean_setField(this.fieldIndex, bean, value); }
/*     */ 
/*     */ 
/*     */     
/* 184 */     public void setIntercept(Object bean, Object value) { this.entityBean._ebean_setFieldIntercept(this.fieldIndex, bean, value); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\reflect\EnhanceBeanReflect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */