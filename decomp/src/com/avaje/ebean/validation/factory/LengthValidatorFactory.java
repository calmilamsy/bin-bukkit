/*     */ package com.avaje.ebean.validation.factory;
/*     */ 
/*     */ import com.avaje.ebean.validation.Length;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.HashMap;
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
/*     */ public final class LengthValidatorFactory
/*     */   implements ValidatorFactory
/*     */ {
/*  23 */   private static final Map<String, Validator> cache = new HashMap();
/*     */ 
/*     */   
/*     */   public Validator create(Annotation annotation, Class<?> type) {
/*  27 */     if (!type.equals(String.class)) {
/*  28 */       String msg = "You can only specify @Length on String types";
/*  29 */       throw new RuntimeException(msg);
/*     */     } 
/*  31 */     Length length = (Length)annotation;
/*  32 */     return create(length.min(), length.max());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Validator create(int min, int max) {
/*  40 */     String key = min + ":" + max;
/*  41 */     Validator validator = (Validator)cache.get(key);
/*  42 */     if (validator == null) {
/*  43 */       validator = new LengthValidator(min, max, null);
/*  44 */       cache.put(key, validator);
/*     */     } 
/*  46 */     return validator;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class LengthValidator
/*     */     implements Validator
/*     */   {
/*     */     private final int min;
/*     */     
/*     */     private final int max;
/*     */     private final String key;
/*     */     private final Object[] attributes;
/*     */     
/*     */     private LengthValidator(int min, int max) {
/*  60 */       this.min = min;
/*  61 */       this.max = max;
/*  62 */       this.key = determineKey(min, max);
/*  63 */       this.attributes = determineAttributes(min, max);
/*     */     }
/*     */     
/*     */     private String determineKey(int min, int max) {
/*  67 */       if (min == 0 && max > 0)
/*  68 */         return "length.max"; 
/*  69 */       if (min > 0 && max == 0) {
/*  70 */         return "length.min";
/*     */       }
/*  72 */       return "length.minmax";
/*     */     }
/*     */ 
/*     */     
/*     */     private Object[] determineAttributes(int min, int max) {
/*  77 */       if (min == 0 && max > 0)
/*  78 */         return new Object[] { Integer.valueOf(max) }; 
/*  79 */       if (min > 0 && max == 0) {
/*  80 */         return new Object[] { Integer.valueOf(min) };
/*     */       }
/*  82 */       return new Object[] { Integer.valueOf(min), Integer.valueOf(max) };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     public Object[] getAttributes() { return this.attributes; }
/*     */ 
/*     */ 
/*     */     
/*  94 */     public String getKey() { return this.key; }
/*     */ 
/*     */     
/*     */     public boolean isValid(Object value) {
/*  98 */       if (value == null) {
/*  99 */         return true;
/*     */       }
/* 101 */       String s = (String)value;
/* 102 */       int len = s.length();
/* 103 */       return (len >= this.min && len <= this.max);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\LengthValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */