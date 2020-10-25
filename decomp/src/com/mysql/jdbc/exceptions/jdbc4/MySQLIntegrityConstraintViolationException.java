/*    */ package com.mysql.jdbc.exceptions.jdbc4;
/*    */ 
/*    */ import java.sql.SQLIntegrityConstraintViolationException;
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
/*    */   extends SQLIntegrityConstraintViolationException
/*    */ {
/*    */   public MySQLIntegrityConstraintViolationException() {}
/*    */   
/* 37 */   public MySQLIntegrityConstraintViolationException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public MySQLIntegrityConstraintViolationException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public MySQLIntegrityConstraintViolationException(String reason) { super(reason); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\jdbc4\MySQLIntegrityConstraintViolationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */