/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
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
/*    */ public class ScalarTypeTimestamp
/*    */   extends ScalarTypeBaseDateTime<Timestamp>
/*    */ {
/* 34 */   public ScalarTypeTimestamp() { super(Timestamp.class, true, 93); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Timestamp convertFromTimestamp(Timestamp ts) { return ts; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public Timestamp convertToTimestamp(Timestamp t) { return t; }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Timestamp value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(93);
/*    */     } else {
/* 51 */       b.setTimestamp(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public Timestamp read(DataReader dataReader) throws SQLException { return dataReader.getTimestamp(); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toTimestamp(value); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public Timestamp toBeanType(Object value) { return BasicTypeConverter.toTimestamp(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */