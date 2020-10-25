/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
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
/*    */ public class ScalarTypeDate
/*    */   extends ScalarTypeBaseDate<Date>
/*    */ {
/* 34 */   public ScalarTypeDate() { super(Date.class, true, 91); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Date convertFromDate(Date date) { return date; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public Date convertToDate(Date t) { return t; }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Date value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(91);
/*    */     } else {
/* 51 */       b.setDate(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 56 */   public Date read(DataReader dataReader) throws SQLException { return dataReader.getDate(); }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toDate(value); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public Date toBeanType(Object value) { return BasicTypeConverter.toDate(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */