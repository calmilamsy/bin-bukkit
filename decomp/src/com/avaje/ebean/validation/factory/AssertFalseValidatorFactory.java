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
/*    */ public class AssertFalseValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator ASSERT_FALSE = new AssertFalseValidator();
/*    */ 
/*    */   
/* 18 */   public Validator create(Annotation annotation, Class<?> type) { return ASSERT_FALSE; }
/*    */ 
/*    */   
/*    */   public static class AssertFalseValidator
/*    */     extends NoAttributesValidator
/*    */   {
/* 24 */     public String getKey() { return "assertfalse"; }
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


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\AssertFalseValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */