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
/*    */ public class EmailValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator EMAIL = new EmailValidator();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 18 */     if (!type.equals(String.class)) {
/* 19 */       throw new RuntimeException("Can only apply this annotation to String types, not " + type);
/*    */     }
/*    */     
/* 22 */     return EMAIL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class EmailValidator
/*    */     extends NoAttributesValidator
/*    */   {
/* 31 */     private final EmailValidation emailValidation = EmailValidation.create(false, false);
/*    */ 
/*    */ 
/*    */     
/* 35 */     public String getKey() { return "email"; }
/*    */ 
/*    */     
/*    */     public boolean isValid(Object value) {
/* 39 */       if (value == null) {
/* 40 */         return true;
/*    */       }
/*    */       
/* 43 */       return this.emailValidation.isValid((String)value);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\EmailValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */