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
/*    */ public class AssertTrueValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator ASSERT_TRUE = new AssertTrueValidator();
/*    */ 
/*    */   
/* 18 */   public Validator create(Annotation annotation, Class<?> type) { return ASSERT_TRUE; }
/*    */ 
/*    */   
/*    */   public static class AssertTrueValidator
/*    */     extends NoAttributesValidator
/*    */   {
/* 24 */     public String getKey() { return "asserttrue"; }
/*    */ 
/*    */     
/*    */     public boolean isValid(Object value) {
/* 28 */       if (value == null) {
/* 29 */         return true;
/*    */       }
/* 31 */       return ((Boolean)value).booleanValue();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\AssertTrueValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */