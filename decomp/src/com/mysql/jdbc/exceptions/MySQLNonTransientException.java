/*    */ package com.mysql.jdbc.exceptions;
/*    */ 
/*    */ import java.sql.SQLException;
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
/*    */ public class MySQLNonTransientException
/*    */   extends SQLException
/*    */ {
/*    */   public MySQLNonTransientException() {}
/*    */   
/* 36 */   public MySQLNonTransientException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public MySQLNonTransientException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public MySQLNonTransientException(String reason) { super(reason); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\MySQLNonTransientException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */