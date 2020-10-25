/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
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
/*     */ public abstract class ScalarTypeBytesBase
/*     */   extends ScalarTypeBase<byte[]>
/*     */ {
/*  36 */   protected ScalarTypeBytesBase(boolean jdbcNative, int jdbcType) { super(byte[].class, jdbcNative, jdbcType); }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public Object convertFromBytes(byte[] bytes) { return bytes; }
/*     */ 
/*     */ 
/*     */   
/*  44 */   public byte[] convertToBytes(Object value) { return (byte[])value; }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, byte[] value) throws SQLException {
/*  48 */     if (value == null) {
/*  49 */       b.setNull(this.jdbcType);
/*     */     } else {
/*  51 */       b.setBytes(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  56 */   public Object toJdbcType(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public byte[] toBeanType(Object value) { return (byte[])value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public String formatValue(byte[] t) { throw new TextException("Not supported"); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public byte[] parse(String value) { throw new TextException("Not supported"); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public byte[] parseDateTime(long systemTimeMillis) { throw new TextException("Not supported"); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean isDateTimeCapable() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public int getLuceneType() { return 7; }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public Object luceneFromIndexValue(Object value) { return value; }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public Object luceneToIndexValue(Object value) { return value; }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  93 */     if (!dataInput.readBoolean()) {
/*  94 */       return null;
/*     */     }
/*  96 */     int len = dataInput.readInt();
/*  97 */     byte[] buf = new byte[len];
/*  98 */     dataInput.readFully(buf, 0, buf.length);
/*  99 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 104 */     if (v == null) {
/* 105 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 107 */       byte[] bytes = convertToBytes(v);
/* 108 */       dataOutput.writeInt(bytes.length);
/* 109 */       dataOutput.write(bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */