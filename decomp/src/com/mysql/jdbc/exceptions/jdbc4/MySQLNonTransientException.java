/*    */ package com.mysql.jdbc.exceptions.jdbc4;
/*    */ 
/*    */ import java.sql.SQLNonTransientException;
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
/*    */ public class MySQLNonTransientException
/*    */   extends SQLNonTransientException
/*    */ {
/*    */   public MySQLNonTransientException() {}
/*    */   
/* 37 */   public MySQLNonTransientException(String reason, String SQLState, int vendorCode) { super(reason, SQLState, vendorCode); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public MySQLNonTransientException(String reason, String SQLState) { super(reason, SQLState); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public MySQLNonTransientException(String reason) { super(reason); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\jdbc4\MySQLNonTransientException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */