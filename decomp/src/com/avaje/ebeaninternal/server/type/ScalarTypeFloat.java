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
/*     */ public class ScalarTypeFloat
/*     */   extends ScalarTypeBase<Float>
/*     */ {
/*  37 */   public ScalarTypeFloat() { super(Float.class, true, 7); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Float value) throws SQLException {
/*  41 */     if (value == null) {
/*  42 */       b.setNull(7);
/*     */     } else {
/*  44 */       b.setFloat(value.floatValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public Float read(DataReader dataReader) throws SQLException { return dataReader.getFloat(); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toFloat(value); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public Float toBeanType(Object value) { return BasicTypeConverter.toFloat(value); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public String formatValue(Float t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public Float parse(String value) { return Float.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public Float parseDateTime(long systemTimeMillis) { return Float.valueOf((float)systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */   
/*     */   public String toJsonString(Float value) {
/*  78 */     if (value.isInfinite() || value.isNaN()) {
/*  79 */       return "null";
/*     */     }
/*  81 */     return value.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public int getLuceneType() { return 4; }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  98 */     if (!dataInput.readBoolean()) {
/*  99 */       return null;
/*     */     }
/* 101 */     float val = dataInput.readFloat();
/* 102 */     return Float.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 108 */     Float value = (Float)v;
/* 109 */     if (value == null) {
/* 110 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 112 */       dataOutput.writeBoolean(true);
/* 113 */       dataOutput.writeFloat(value.floatValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeFloat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */