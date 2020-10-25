/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public final class Primitives
/*     */ {
/*     */   private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
/*     */   private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;
/*     */   
/*     */   static  {
/*  45 */     primToWrap = new HashMap(16);
/*  46 */     Map<Class<?>, Class<?>> wrapToPrim = new HashMap<Class<?>, Class<?>>(16);
/*     */     
/*  48 */     add(primToWrap, wrapToPrim, boolean.class, Boolean.class);
/*  49 */     add(primToWrap, wrapToPrim, byte.class, Byte.class);
/*  50 */     add(primToWrap, wrapToPrim, char.class, Character.class);
/*  51 */     add(primToWrap, wrapToPrim, double.class, Double.class);
/*  52 */     add(primToWrap, wrapToPrim, float.class, Float.class);
/*  53 */     add(primToWrap, wrapToPrim, int.class, Integer.class);
/*  54 */     add(primToWrap, wrapToPrim, long.class, Long.class);
/*  55 */     add(primToWrap, wrapToPrim, short.class, Short.class);
/*  56 */     add(primToWrap, wrapToPrim, void.class, Void.class);
/*     */     
/*  58 */     PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
/*  59 */     WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(wrapToPrim);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void add(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
/*  64 */     forward.put(key, value);
/*  65 */     backward.put(value, key);
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
/*  76 */   public static Set<Class<?>> allPrimitiveTypes() { return PRIMITIVE_TO_WRAPPER_TYPE.keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static Set<Class<?>> allWrapperTypes() { return WRAPPER_TO_PRIMITIVE_TYPE.keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static boolean isWrapperType(Class<?> type) { return WRAPPER_TO_PRIMITIVE_TYPE.containsKey(Preconditions.checkNotNull(type)); }
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
/*     */   public static <T> Class<T> wrap(Class<T> type) {
/* 109 */     Preconditions.checkNotNull(type);
/*     */ 
/*     */ 
/*     */     
/* 113 */     Class<T> wrapped = (Class)PRIMITIVE_TO_WRAPPER_TYPE.get(type);
/* 114 */     return (wrapped == null) ? type : wrapped;
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
/*     */   public static <T> Class<T> unwrap(Class<T> type) {
/* 127 */     Preconditions.checkNotNull(type);
/*     */ 
/*     */ 
/*     */     
/* 131 */     Class<T> unwrapped = (Class)WRAPPER_TO_PRIMITIVE_TYPE.get(type);
/* 132 */     return (unwrapped == null) ? type : unwrapped;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Primitives.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */