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
/*    */ public class ScalarTypeBytesBlob
/*    */   extends ScalarTypeBytesBase
/*    */ {
/* 31 */   public ScalarTypeBytesBlob() { super(true, 2004); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public byte[] read(DataReader dataReader) throws SQLException { return dataReader.getBlobBytes(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */