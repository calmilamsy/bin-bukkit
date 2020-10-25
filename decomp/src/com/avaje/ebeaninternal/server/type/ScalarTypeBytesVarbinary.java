/*    */ package com.avaje.ebeaninternal.server.type;
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
/*    */ public class ScalarTypeBytesVarbinary
/*    */   extends ScalarTypeBytesBase
/*    */ {
/* 31 */   public ScalarTypeBytesVarbinary() { super(true, -3); }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public byte[] read(DataReader dataReader) throws SQLException { return dataReader.getBytes(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesVarbinary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */