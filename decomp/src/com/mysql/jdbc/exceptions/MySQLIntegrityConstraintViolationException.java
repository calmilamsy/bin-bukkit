/*    */ package com.mysql.jdbc.exceptions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MySQLIntegrityConstraintViolationException
/*    */   extends MySQLNonTransientException
/*    */ {
/*    */   public MySQLIntegrityConstraintViolationException() {}
/*    */   
/* 35 */   public MySQLIntegrityConstraintViolationException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public MySQLIntegrityConstraintViolationException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public MySQLIntegrityConstraintViolationException(String reason) { super(reason); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLIntegrityConstraintViolationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */