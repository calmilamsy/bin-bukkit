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
/*    */ public class MySQLTransactionRollbackException
/*    */   extends MySQLTransientException
/*    */   implements DeadlockTimeoutRollbackMarker
/*    */ {
/* 31 */   public MySQLTransactionRollbackException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public MySQLTransactionRollbackException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public MySQLTransactionRollbackException(String reason) { super(reason); }
/*    */   
/*    */   public MySQLTransactionRollbackException() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLTransactionRollbackException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */