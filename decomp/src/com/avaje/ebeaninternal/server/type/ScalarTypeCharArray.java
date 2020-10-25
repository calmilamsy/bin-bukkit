/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
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
/*    */ public class ScalarTypeCharArray
/*    */   extends ScalarTypeBaseVarchar<char[]>
/*    */ {
/* 34 */   public ScalarTypeCharArray() { super(char[].class, false, 12); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public char[] convertFromDbString(String dbValue) { return dbValue.toCharArray(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public String convertToDbString(char[] beanValue) { return new String(beanValue); }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, char[] value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(12);
/*    */     } else {
/* 51 */       String s = BasicTypeConverter.toString(value);
/* 52 */       b.setString(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public char[] read(DataReader dataReader) throws SQLException {
/* 57 */     String string = dataReader.getString();
/* 58 */     if (string == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     return string.toCharArray();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toString(value); }
/*    */ 
/*    */   
/*    */   public char[] toBeanType(Object value) {
/* 70 */     String s = BasicTypeConverter.toString(value);
/* 71 */     return s.toCharArray();
/*    */   }
/*    */ 
/*    */   
/* 75 */   public String formatValue(char[] t) { return String.valueOf(t); }
/*    */ 
/*    */ 
/*    */   
/* 79 */   public char[] parse(String value) { return value.toCharArray(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public char[] jsonFromString(String value, JsonValueAdapter ctx) { return value.toCharArray(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 89 */   public String jsonToString(char[] value, JsonValueAdapter ctx) { return EscapeJson.escapeQuote(String.valueOf(value)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCharArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */