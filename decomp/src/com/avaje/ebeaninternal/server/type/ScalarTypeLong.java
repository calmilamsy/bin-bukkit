/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
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
/*     */ public class ScalarTypeLong
/*     */   extends ScalarTypeBase<Long>
/*     */ {
/*  37 */   public ScalarTypeLong() { super(Long.class, true, -5); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Long value) throws SQLException {
/*  41 */     if (value == null) {
/*  42 */       b.setNull(-5);
/*     */     } else {
/*  44 */       b.setLong(value.longValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public Long read(DataReader dataReader) throws SQLException { return dataReader.getLong(); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toLong(value); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public Long toBeanType(Object value) { return BasicTypeConverter.toLong(value); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public String formatValue(Long t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public Long parse(String value) { return Long.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public Long parseDateTime(long systemTimeMillis) { return Long.valueOf(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public int getLuceneType() { return 2; }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  90 */     if (!dataInput.readBoolean()) {
/*  91 */       return null;
/*     */     }
/*  93 */     long val = dataInput.readLong();
/*  94 */     return Long.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 100 */     Long value = (Long)v;
/* 101 */     if (value == null) {
/* 102 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 104 */       dataOutput.writeBoolean(true);
/* 105 */       dataOutput.writeLong(value.longValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLong.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */