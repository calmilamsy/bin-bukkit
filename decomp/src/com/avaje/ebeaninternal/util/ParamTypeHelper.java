/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParamTypeHelper
/*     */ {
/*     */   public enum ManyType
/*     */   {
/*  12 */     LIST, SET, MAP, NONE;
/*     */   }
/*     */   
/*     */   public static class TypeInfo
/*     */   {
/*     */     private final ParamTypeHelper.ManyType manyType;
/*     */     private final Class<?> beanType;
/*     */     
/*     */     private TypeInfo(ParamTypeHelper.ManyType manyType, Class<?> beanType) {
/*  21 */       this.manyType = manyType;
/*  22 */       this.beanType = beanType;
/*     */     }
/*     */ 
/*     */     
/*  26 */     public boolean isManyType() { return !ParamTypeHelper.ManyType.NONE.equals(this.manyType); }
/*     */ 
/*     */ 
/*     */     
/*  30 */     public ParamTypeHelper.ManyType getManyType() { return this.manyType; }
/*     */ 
/*     */ 
/*     */     
/*  34 */     public Class<?> getBeanType() { return this.beanType; }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  38 */       if (isManyType()) {
/*  39 */         return this.manyType + " " + this.beanType;
/*     */       }
/*  41 */       return this.beanType.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeInfo getTypeInfo(Type genericType) {
/*  49 */     if (genericType instanceof ParameterizedType) {
/*  50 */       return getParamTypeInfo((ParameterizedType)genericType);
/*     */     }
/*     */     
/*  53 */     Class<?> entityType = getBeanType(genericType);
/*  54 */     if (entityType != null) {
/*  55 */       return new TypeInfo(ManyType.NONE, entityType, null);
/*     */     }
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TypeInfo getParamTypeInfo(ParameterizedType paramType) {
/*  65 */     Type rawType = paramType.getRawType();
/*     */     
/*  67 */     ManyType manyType = getManyType(rawType);
/*  68 */     if (ManyType.NONE.equals(manyType)) {
/*  69 */       return null;
/*     */     }
/*     */     
/*  72 */     Type[] typeArguments = paramType.getActualTypeArguments();
/*     */     
/*  74 */     if (typeArguments.length == 1) {
/*  75 */       Type argType = typeArguments[0];
/*  76 */       Class<?> beanType = getBeanType(argType);
/*  77 */       if (beanType != null) {
/*  78 */         return new TypeInfo(manyType, beanType, null);
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   private static Class<?> getBeanType(Type argType) {
/*  86 */     if (argType instanceof Class) {
/*  87 */       return (Class)argType;
/*     */     }
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   private static ManyType getManyType(Type rawType) {
/*  93 */     if (java.util.List.class.equals(rawType)) {
/*  94 */       return ManyType.LIST;
/*     */     }
/*  96 */     if (java.util.Set.class.equals(rawType)) {
/*  97 */       return ManyType.SET;
/*     */     }
/*  99 */     if (java.util.Map.class.equals(rawType)) {
/* 100 */       return ManyType.MAP;
/*     */     }
/* 102 */     return ManyType.NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\ParamTypeHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */