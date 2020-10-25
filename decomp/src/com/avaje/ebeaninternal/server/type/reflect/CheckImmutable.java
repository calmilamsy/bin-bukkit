/*     */ package com.avaje.ebeaninternal.server.type.reflect;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ public class CheckImmutable
/*     */ {
/*  11 */   private static Logger logger = Logger.getLogger(CheckImmutable.class.getName());
/*     */   
/*     */   private final KnownImmutable knownImmutable;
/*     */ 
/*     */   
/*  16 */   public CheckImmutable(KnownImmutable knownImmutable) { this.knownImmutable = knownImmutable; }
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckImmutableResponse checkImmutable(Class<?> cls) {
/*  21 */     CheckImmutableResponse res = new CheckImmutableResponse();
/*     */     
/*  23 */     isImmutable(cls, res);
/*     */     
/*  25 */     if (res.isImmutable()) {
/*  26 */       res.setCompoundType(isCompoundType(cls));
/*     */     }
/*     */     
/*  29 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isCompoundType(Class<?> cls) {
/*  34 */     int maxLength = 0;
/*  35 */     Constructor<?> chosen = null;
/*     */ 
/*     */     
/*  38 */     Constructor[] constructors = cls.getConstructors();
/*  39 */     for (int i = 0; i < constructors.length; i++) {
/*  40 */       Class[] parameterTypes = constructors[i].getParameterTypes();
/*  41 */       if (parameterTypes.length > maxLength) {
/*  42 */         maxLength = parameterTypes.length;
/*  43 */         chosen = constructors[i];
/*     */       } 
/*     */     } 
/*     */     
/*  47 */     logger.fine("checkImmutable " + cls + " constructor " + chosen);
/*     */     
/*  49 */     return (maxLength > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isImmutable(Class<?> cls, CheckImmutableResponse res) {
/*  55 */     if (this.knownImmutable.isKnownImmutable(cls)) {
/*  56 */       return true;
/*     */     }
/*     */     
/*  59 */     if (cls.isArray()) {
/*  60 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  64 */     if (hasDefaultConstructor(cls)) {
/*     */       
/*  66 */       res.setReasonNotImmutable(cls + " has a default constructor");
/*  67 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     Class<?> superClass = cls.getSuperclass();
/*     */     
/*  73 */     if (!isImmutable(superClass, res)) {
/*  74 */       res.setReasonNotImmutable("Super not Immutable " + superClass);
/*  75 */       return false;
/*     */     } 
/*     */     
/*  78 */     if (!hasAllFinalFields(cls, res)) {
/*  79 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasAllFinalFields(Class<?> cls, CheckImmutableResponse res) {
/*  89 */     Field[] objFields = cls.getDeclaredFields();
/*  90 */     for (int i = 0; i < objFields.length; i++) {
/*  91 */       if (!Modifier.isStatic(objFields[i].getModifiers())) {
/*     */ 
/*     */         
/*  94 */         if (!Modifier.isFinal(objFields[i].getModifiers())) {
/*  95 */           res.setReasonNotImmutable("Non final field " + cls + "." + objFields[i].getName());
/*  96 */           return false;
/*     */         } 
/*  98 */         if (!isImmutable(objFields[i].getType(), res)) {
/*  99 */           res.setReasonNotImmutable("Non Immutable field type " + objFields[i].getType());
/* 100 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasDefaultConstructor(Class<?> cls) {
/* 111 */     Class[] noParams = new Class[0];
/*     */     try {
/* 113 */       cls.getDeclaredConstructor(noParams);
/* 114 */       return true;
/*     */     }
/* 116 */     catch (SecurityException e) {
/*     */       
/* 118 */       return false;
/*     */     }
/* 120 */     catch (NoSuchMethodException e) {
/*     */       
/* 122 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\CheckImmutable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */