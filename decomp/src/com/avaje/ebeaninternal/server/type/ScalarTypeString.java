/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeString
/*     */   extends ScalarTypeBase<String>
/*     */ {
/*  38 */   public ScalarTypeString() { super(String.class, true, 12); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, String value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(12);
/*     */     } else {
/*  45 */       b.setString(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public String read(DataReader dataReader) throws SQLException { return dataReader.getString(); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toString(value); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public String toBeanType(Object value) { return BasicTypeConverter.toString(value); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String formatValue(String t) { return t; }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public String parse(String value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String parseDateTime(long systemTimeMillis) { return String.valueOf(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String jsonFromString(String value, JsonValueAdapter ctx) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public String jsonToString(String value, JsonValueAdapter ctx) { return EscapeJson.escapeQuote(value); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public int getLuceneType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 101 */     if (!dataInput.readBoolean()) {
/* 102 */       return null;
/*     */     }
/* 104 */     return dataInput.readUTF();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 110 */     String value = (String)v;
/* 111 */     if (value == null) {
/* 112 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 114 */       dataOutput.writeBoolean(true);
/* 115 */       dataOutput.writeUTF(value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */