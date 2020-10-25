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
/*    */ public class ScalarTypeChar
/*    */   extends ScalarTypeBaseVarchar<Character>
/*    */ {
/* 34 */   public ScalarTypeChar() { super(char.class, false, 12); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Character convertFromDbString(String dbValue) { return Character.valueOf(dbValue.charAt(0)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public String convertToDbString(Character beanValue) { return beanValue.toString(); }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Character value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(12);
/*    */     } else {
/* 51 */       String s = BasicTypeConverter.toString(value);
/* 52 */       b.setString(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Character read(DataReader dataReader) throws SQLException {
/* 57 */     String string = dataReader.getString();
/* 58 */     if (string == null || string.length() == 0) {
/* 59 */       return null;
/*    */     }
/* 61 */     return Character.valueOf(string.charAt(0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toString(value); }
/*    */ 
/*    */   
/*    */   public Character toBeanType(Object value) {
/* 70 */     String s = BasicTypeConverter.toString(value);
/* 71 */     return Character.valueOf(s.charAt(0));
/*    */   }
/*    */ 
/*    */   
/* 75 */   public String formatValue(Character t) { return t.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 79 */   public Character parse(String value) { return Character.valueOf(value.charAt(0)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public Character jsonFromString(String value, JsonValueAdapter ctx) { return Character.valueOf(value.charAt(0)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 89 */   public String jsonToString(Character value, JsonValueAdapter ctx) { return EscapeJson.escapeQuote(value.toString()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeChar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */