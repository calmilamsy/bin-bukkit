/*    */ package com.mysql.jdbc.exceptions.jdbc4;
/*    */ 
/*    */ import com.mysql.jdbc.exceptions.DeadlockTimeoutRollbackMarker;
/*    */ import java.sql.SQLTransactionRollbackException;
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
/*    */ public class MySQLTransactionRollbackException
/*    */   extends SQLTransactionRollbackException
/*    */   implements DeadlockTimeoutRollbackMarker
/*    */ {
/* 35 */   public MySQLTransactionRollbackException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public MySQLTransactionRollbackException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public MySQLTransactionRollbackException(String reason) { super(reason); }
/*    */   
/*    */   public MySQLTransactionRollbackException() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\jdbc4\MySQLTransactionRollbackException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */