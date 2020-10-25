/*    */ package com.mysql.jdbc.exceptions.jdbc4;
/*    */ 
/*    */ import java.sql.SQLTimeoutException;
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
/*    */   extends SQLTimeoutException
/*    */ {
/* 32 */   public MySQLTimeoutException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public MySQLTimeoutException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public MySQLTimeoutException(String reason) { super(reason); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public MySQLTimeoutException() { super("Statement cancelled due to timeout or client request"); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public int getErrorCode() { return super.getErrorCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\jdbc4\MySQLTimeoutException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */