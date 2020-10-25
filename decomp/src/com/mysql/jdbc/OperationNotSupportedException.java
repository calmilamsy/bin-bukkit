/*    */ package com.mysql.jdbc;
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
/*    */ class OperationNotSupportedException
/*    */   extends SQLException
/*    */ {
/* 28 */   OperationNotSupportedException() { super(Messages.getString("RowDataDynamic.10"), "S1009"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\OperationNotSupportedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */