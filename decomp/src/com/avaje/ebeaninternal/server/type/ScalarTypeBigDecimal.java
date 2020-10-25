/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
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
/*     */ public class ScalarTypeBigDecimal
/*     */   extends ScalarTypeBase<BigDecimal>
/*     */ {
/*  38 */   public ScalarTypeBigDecimal() { super(BigDecimal.class, true, 3); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  42 */     if (!dataInput.readBoolean()) {
/*  43 */       return null;
/*     */     }
/*  45 */     double val = dataInput.readDouble();
/*  46 */     return new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  52 */     BigDecimal b = (BigDecimal)v;
/*  53 */     if (b == null) {
/*  54 */       dataOutput.writeBoolean(false);
/*     */     } else {
/*  56 */       dataOutput.writeBoolean(true);
/*  57 */       dataOutput.writeDouble(b.doubleValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, BigDecimal value) throws SQLException {
/*  62 */     if (value == null) {
/*  63 */       b.setNull(3);
/*     */     } else {
/*  65 */       b.setBigDecimal(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public BigDecimal read(DataReader dataReader) throws SQLException { return dataReader.getBigDecimal(); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toBigDecimal(value); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public BigDecimal toBeanType(Object value) { return BasicTypeConverter.toBigDecimal(value); }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String formatValue(BigDecimal t) { return t.toPlainString(); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public BigDecimal parse(String value) { return new BigDecimal(value); }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public BigDecimal parseDateTime(long systemTimeMillis) { return BigDecimal.valueOf(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */   
/*     */   public Object luceneFromIndexValue(Object value) {
/*  99 */     Double v = (Double)value;
/* 100 */     return new BigDecimal(v.doubleValue());
/*     */   }
/*     */   
/*     */   public Object luceneToIndexValue(Object value) {
/* 104 */     BigDecimal v = (BigDecimal)value;
/* 105 */     return Double.valueOf(v.doubleValue());
/*     */   }
/*     */ 
/*     */   
/* 109 */   public int getLuceneType() { return 3; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBigDecimal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */