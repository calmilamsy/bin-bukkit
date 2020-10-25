/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PastValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 12 */   Validator DATE = new DateValidator(null);
/* 13 */   Validator CALENDAR = new CalendarValidator(null);
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 16 */     if (Date.class.isAssignableFrom(type)) {
/* 17 */       return this.DATE;
/*    */     }
/* 19 */     if (Calendar.class.isAssignableFrom(type)) {
/* 20 */       return this.CALENDAR;
/*    */     }
/* 22 */     String msg = "Can not use @Past on type " + type;
/* 23 */     throw new RuntimeException(msg);
/*    */   }
/*    */   
/*    */   private static class DateValidator extends NoAttributesValidator {
/*    */     private DateValidator() {}
/*    */     
/* 29 */     public String getKey() { return "past"; }
/*    */ 
/*    */     
/*    */     public boolean isValid(Object value) {
/* 33 */       if (value == null) {
/* 34 */         return true;
/*    */       }
/*    */       
/* 37 */       Date date = (Date)value;
/* 38 */       return date.before(new Date());
/*    */     }
/*    */   }
/*    */   
/*    */   private static class CalendarValidator extends NoAttributesValidator {
/*    */     private CalendarValidator() {}
/*    */     
/* 45 */     public String getKey() { return "past"; }
/*    */ 
/*    */     
/*    */     public boolean isValid(Object value) {
/* 49 */       if (value == null) {
/* 50 */         return true;
/*    */       }
/*    */       
/* 53 */       Calendar cal = (Calendar)value;
/* 54 */       return cal.before(Calendar.getInstance());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\PastValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */