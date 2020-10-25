/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
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
/*     */ public class ScalarTypeMathBigInteger
/*     */   extends ScalarTypeBase<BigInteger>
/*     */ {
/*  38 */   public ScalarTypeMathBigInteger() { super(BigInteger.class, false, -5); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, BigInteger value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(-5);
/*     */     } else {
/*  45 */       b.setLong(value.longValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BigInteger read(DataReader dataReader) throws SQLException {
/*  51 */     Long l = dataReader.getLong();
/*  52 */     if (l == null) {
/*  53 */       return null;
/*     */     }
/*  55 */     return new BigInteger(String.valueOf(l));
/*     */   }
/*     */ 
/*     */   
/*  59 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toLong(value); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public BigInteger toBeanType(Object value) { return BasicTypeConverter.toMathBigInteger(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String formatValue(BigInteger v) { return v.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public BigInteger parse(String value) { return new BigInteger(value); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public BigInteger parseDateTime(long systemTimeMillis) { return BigInteger.valueOf(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public int getLuceneType() { return 2; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public Object luceneFromIndexValue(Object value) { return BigInteger.valueOf(((Long)value).longValue()); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public Object luceneToIndexValue(Object value) { return Long.valueOf(((BigInteger)value).longValue()); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  96 */     if (!dataInput.readBoolean()) {
/*  97 */       return null;
/*     */     }
/*  99 */     long val = dataInput.readLong();
/* 100 */     return Long.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 106 */     Long value = (Long)v;
/* 107 */     if (value == null) {
/* 108 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 110 */       dataOutput.writeBoolean(true);
/* 111 */       dataOutput.writeLong(value.longValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeMathBigInteger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */