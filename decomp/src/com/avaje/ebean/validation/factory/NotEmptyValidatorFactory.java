/*     */ package com.avaje.ebean.validation.factory;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotEmptyValidatorFactory
/*     */   implements ValidatorFactory
/*     */ {
/*  21 */   static final Logger logger = Logger.getLogger(NotEmptyValidatorFactory.class.getName());
/*     */   
/*  23 */   static final Validator STRING = new StringNotEmptyValidator(null);
/*     */   
/*  25 */   static final Validator ARRAY = new ArrayValidator(null);
/*  26 */   static final Validator LIST = new ListNotEmptyValidator(null);
/*  27 */   static final Validator SET = new SetNotEmptyValidator(null);
/*  28 */   static final Validator MAP = new MapNotEmptyValidator(null);
/*  29 */   static final Validator COLLECTION = new CollectionNotEmptyValidator(null);
/*     */ 
/*     */ 
/*     */   
/*     */   public Validator create(Annotation annotation, Class<?> type) {
/*  34 */     if (type.equals(String.class)) {
/*  35 */       return STRING;
/*     */     }
/*  37 */     if (type.isArray()) {
/*  38 */       return ARRAY;
/*     */     }
/*  40 */     if (java.util.List.class.isAssignableFrom(type)) {
/*  41 */       return LIST;
/*     */     }
/*  43 */     if (java.util.Set.class.isAssignableFrom(type)) {
/*  44 */       return SET;
/*     */     }
/*  46 */     if (Collection.class.isAssignableFrom(type)) {
/*  47 */       return COLLECTION;
/*     */     }
/*  49 */     if (Map.class.isAssignableFrom(type)) {
/*  50 */       return MAP;
/*     */     }
/*     */     
/*  53 */     String msg = "@NotEmpty not assignable to type " + type;
/*  54 */     logger.log(Level.SEVERE, msg);
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   private static class StringNotEmptyValidator extends NoAttributesValidator {
/*     */     private StringNotEmptyValidator() {}
/*     */     
/*  61 */     public String getKey() { return "notempty.string"; }
/*     */ 
/*     */     
/*     */     public boolean isValid(Object value) {
/*  65 */       if (value == null) {
/*  66 */         return false;
/*     */       }
/*  68 */       String s = (String)value;
/*  69 */       return (s.length() > 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MapNotEmptyValidator extends NoAttributesValidator {
/*     */     private MapNotEmptyValidator() {}
/*     */     
/*  76 */     public String getKey() { return "notempty.map"; }
/*     */ 
/*     */     
/*     */     public boolean isValid(Object value) {
/*  80 */       if (value == null) {
/*  81 */         return false;
/*     */       }
/*  83 */       Map<?, ?> map = (Map)value;
/*  84 */       return (map.size() > 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ArrayValidator extends NoAttributesValidator {
/*     */     private ArrayValidator() {}
/*     */     
/*  91 */     public String getKey() { return "notempty.array"; }
/*     */ 
/*     */     
/*     */     public boolean isValid(Object value) {
/*  95 */       if (value == null) {
/*  96 */         return false;
/*     */       }
/*  98 */       return (Array.getLength(value) > 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SetNotEmptyValidator
/*     */     extends CollectionNotEmptyValidator
/*     */   {
/* 105 */     private SetNotEmptyValidator() { super(null); }
/*     */ 
/*     */     
/* 108 */     public String getKey() { return "notempty.set"; }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ListNotEmptyValidator
/*     */     extends CollectionNotEmptyValidator
/*     */   {
/* 115 */     private ListNotEmptyValidator() { super(null); }
/*     */ 
/*     */     
/* 118 */     public String getKey() { return "notempty.list"; }
/*     */   }
/*     */   
/*     */   private static class CollectionNotEmptyValidator
/*     */     extends NoAttributesValidator {
/*     */     private CollectionNotEmptyValidator() {}
/*     */     
/* 125 */     public String getKey() { return "notempty.collection"; }
/*     */ 
/*     */     
/*     */     public boolean isValid(Object value) {
/* 129 */       if (value == null) {
/* 130 */         return false;
/*     */       }
/* 132 */       Collection<?> c = (Collection)value;
/* 133 */       return (c.size() > 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\NotEmptyValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */