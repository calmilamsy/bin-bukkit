/*    */ package com.avaje.ebean;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationException
/*    */   extends PersistenceException
/*    */ {
/*    */   private static final long serialVersionUID = -6185355529565362494L;
/*    */   final InvalidValue invalid;
/*    */   
/*    */   public ValidationException(InvalidValue invalid) {
/* 17 */     super("validation failed for: " + invalid.getBeanType());
/* 18 */     this.invalid = invalid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public InvalidValue getInvalid() { return this.invalid; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public InvalidValue[] getErrors() { return this.invalid.getErrors(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public String toString() { return super.toString() + ": " + Arrays.toString(getErrors()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\ValidationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */