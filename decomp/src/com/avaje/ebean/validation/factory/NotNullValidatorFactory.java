/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotNullValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator NOT_NULL = new NotNullValidator();
/*    */ 
/*    */   
/* 18 */   public Validator create(Annotation annotation, Class<?> type) { return NOT_NULL; }
/*    */ 
/*    */   
/*    */   public static class NotNullValidator
/*    */     extends NoAttributesValidator
/*    */   {
/* 24 */     public String getKey() { return "notnull"; }
/*    */ 
/*    */ 
/*    */     
/* 28 */     public boolean isValid(Object value) { return (value != null); }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\NotNullValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */