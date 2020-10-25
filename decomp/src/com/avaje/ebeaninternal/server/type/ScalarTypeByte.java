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
/*     */ public class ScalarTypeByte
/*     */   extends ScalarTypeBase<Byte>
/*     */ {
/*  38 */   public ScalarTypeByte() { super(Byte.class, true, -6); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, Byte value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(-6);
/*     */     } else {
/*  45 */       b.setByte(value.byteValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  50 */   public Byte read(DataReader dataReader) throws SQLException { return dataReader.getByte(); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Object toJdbcType(Object value) { return BasicTypeConverter.toByte(value); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public Byte toBeanType(Object value) { return BasicTypeConverter.toByte(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String formatValue(Byte t) { return t.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public Byte parse(String value) { throw new TextException("Not supported"); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public Byte parseDateTime(long systemTimeMillis) { throw new TextException("Not Supported"); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int getLuceneType() { return 7; }
/*     */ 
/*     */   
/*     */   public Object luceneFromIndexValue(Object value) {
/*  83 */     byte[] ba = new byte[1];
/*  84 */     ba[0] = ((Byte)value).byteValue();
/*  85 */     return ba;
/*     */   }
/*     */ 
/*     */   
/*  89 */   public Object luceneToIndexValue(Object value) { return Byte.valueOf((byte[])value[0]); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  93 */     if (!dataInput.readBoolean()) {
/*  94 */       return null;
/*     */     }
/*  96 */     byte val = dataInput.readByte();
/*  97 */     return Byte.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 103 */     Byte val = (Byte)v;
/* 104 */     if (val == null) {
/* 105 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 107 */       dataOutput.writeBoolean(true);
/* 108 */       dataOutput.writeByte(val.byteValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeByte.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */