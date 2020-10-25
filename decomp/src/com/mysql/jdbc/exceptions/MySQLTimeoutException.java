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
/*    */ public class MySQLTimeoutException
/*    */   extends MySQLTransientException
/*    */ {
/* 30 */   public MySQLTimeoutException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public MySQLTimeoutException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public MySQLTimeoutException(String reason) { super(reason); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public MySQLTimeoutException() { super("Statement cancelled due to timeout or client request"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLTimeoutException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */