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
/*     */ public class ScalarTypeDouble
/*     */   extends ScalarTypeBase<Double>
/*     */ {
/*  37 */   public ScalarTypeDouble() { super(Double.class, true, 8); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Double value) throws SQLException {
/*  41 */     if (value == null) {
/*  42 */       b.setNull(8);
/*     */     } else {
/*  44 */       b.setDouble(value.doubleValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public Double read(DataReader dataReader) throws SQLException { return dataReader.getDouble(); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toDouble(value); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public Double toBeanType(Object value) { return BasicTypeConverter.toDouble(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String formatValue(Double t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public Double parse(String value) { return Double.valueOf(value); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public Double parseDateTime(long systemTimeMillis) { return Double.valueOf(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */   
/*     */   public String toJsonString(Double value) {
/*  79 */     if (value.isInfinite() || value.isNaN()) {
/*  80 */       return "null";
/*     */     }
/*  82 */     return value.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public int getLuceneType() { return 3; }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  99 */     if (!dataInput.readBoolean()) {
/* 100 */       return null;
/*     */     }
/* 102 */     double val = dataInput.readDouble();
/* 103 */     return Double.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 109 */     Double value = (Double)v;
/* 110 */     if (value == null) {
/* 111 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 113 */       dataOutput.writeBoolean(true);
/* 114 */       dataOutput.writeDouble(value.doubleValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */