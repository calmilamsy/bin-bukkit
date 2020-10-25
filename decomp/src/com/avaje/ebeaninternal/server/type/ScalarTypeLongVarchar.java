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
/*    */ public class ScalarTypeLongVarchar
/*    */   extends ScalarTypeClob
/*    */ {
/* 31 */   public ScalarTypeLongVarchar() { super(true, -1); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public String read(DataReader dataReader) throws SQLException { return dataReader.getStringFromStream(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLongVarchar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */