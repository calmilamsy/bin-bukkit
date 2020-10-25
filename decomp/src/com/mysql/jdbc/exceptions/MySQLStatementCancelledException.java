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
/*    */ public class MySQLStatementCancelledException
/*    */   extends MySQLNonTransientException
/*    */ {
/* 29 */   public MySQLStatementCancelledException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public MySQLStatementCancelledException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public MySQLStatementCancelledException(String reason) { super(reason); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public MySQLStatementCancelledException() { super("Statement cancelled due to client request"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLStatementCancelledException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */