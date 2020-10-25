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
/*    */ public class MySQLInvalidAuthorizationSpecException
/*    */   extends MySQLNonTransientException
/*    */ {
/*    */   public MySQLInvalidAuthorizationSpecException() {}
/*    */   
/* 35 */   public MySQLInvalidAuthorizationSpecException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public MySQLInvalidAuthorizationSpecException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public MySQLInvalidAuthorizationSpecException(String reason) { super(reason); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLInvalidAuthorizationSpecException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */