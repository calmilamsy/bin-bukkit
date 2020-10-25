/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
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
/*     */ public class ScalarTypeShort
/*     */   extends ScalarTypeBase<Short>
/*     */ {
/*  38 */   public ScalarTypeShort() { super(Short.class, true, 5); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Short value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(5);
/*     */     } else {
/*  45 */       b.setShort(value.shortValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public Short read(DataReader dataReader) throws SQLException { return dataReader.getShort(); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toShort(value); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Short toBeanType(Object value) { return BasicTypeConverter.toShort(value); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String formatValue(Short v) { return v.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public Short parse(String value) { return Short.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public Short parseDateTime(long systemTimeMillis) { throw new TextException("Not Supported"); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int getLuceneType() { return 1; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public Object luceneFromIndexValue(Object value) { return Short.valueOf(value.toString()); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public Object luceneToIndexValue(Object value) { return Integer.valueOf(((Short)value).intValue()); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  91 */     if (!dataInput.readBoolean()) {
/*  92 */       return null;
/*     */     }
/*  94 */     short val = dataInput.readShort();
/*  95 */     return Short.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 101 */     Short value = (Short)v;
/* 102 */     if (value == null) {
/* 103 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 105 */       dataOutput.writeBoolean(true);
/* 106 */       dataOutput.writeShort(value.shortValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeShort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */