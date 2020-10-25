/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
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
/*     */ public class ScalarTypeInteger
/*     */   extends ScalarTypeBase<Integer>
/*     */ {
/*  39 */   public ScalarTypeInteger() { super(Integer.class, true, 4); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Integer value) throws SQLException {
/*  43 */     if (value == null) {
/*  44 */       b.setNull(4);
/*     */     } else {
/*  46 */       b.setInt(value.intValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public Integer read(DataReader dataReader) throws SQLException { return dataReader.getInt(); }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public Object readData(DataInput dataInput) throws IOException { return Integer.valueOf(dataInput.readInt()); }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public void writeData(DataOutput dataOutput, Object v) throws IOException { dataOutput.writeInt(((Integer)v).intValue()); }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toInteger(value); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public Integer toBeanType(Object value) { return BasicTypeConverter.toInteger(value); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String formatValue(Integer v) { return v.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public Integer parse(String value) { return Integer.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public Integer parseDateTime(long systemTimeMillis) { throw new TextException("Not Supported"); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public String jsonToString(Integer value, JsonValueAdapter ctx) { return value.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public Integer jsonFromString(String value, JsonValueAdapter ctx) { return Integer.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public int getLuceneType() { return 1; }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeInteger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */