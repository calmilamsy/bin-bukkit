/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
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
/*     */ 
/*     */ public class ScalarTypeBytesEncrypted
/*     */   extends Object
/*     */   implements ScalarType<byte[]>
/*     */ {
/*     */   private final ScalarTypeBytesBase baseType;
/*     */   private final DataEncryptSupport dataEncryptSupport;
/*     */   
/*     */   public ScalarTypeBytesEncrypted(ScalarTypeBytesBase baseType, DataEncryptSupport dataEncryptSupport) {
/*  43 */     this.baseType = baseType;
/*  44 */     this.dataEncryptSupport = dataEncryptSupport;
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, byte[] value) throws SQLException {
/*  48 */     value = this.dataEncryptSupport.encrypt(value);
/*  49 */     this.baseType.bind(b, value);
/*     */   }
/*     */ 
/*     */   
/*  53 */   public int getJdbcType() { return this.baseType.getJdbcType(); }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public int getLength() { return this.baseType.getLength(); }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public Class<byte[]> getType() { return byte[].class; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public boolean isDateTimeCapable() { return this.baseType.isDateTimeCapable(); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean isJdbcNative() { return this.baseType.isJdbcNative(); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void loadIgnore(DataReader dataReader) { this.baseType.loadIgnore(dataReader); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public String format(Object v) { throw new RuntimeException("Not used"); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public String formatValue(byte[] v) { throw new RuntimeException("Not used"); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public byte[] parse(String value) { return this.baseType.parse(value); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public byte[] parseDateTime(long systemTimeMillis) { return this.baseType.parseDateTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] read(DataReader dataReader) throws SQLException {
/*  94 */     data = (byte[])this.baseType.read(dataReader);
/*  95 */     return this.dataEncryptSupport.decrypt(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public byte[] toBeanType(Object value) { return this.baseType.toBeanType(value); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public Object toJdbcType(Object value) { return this.baseType.toJdbcType(value); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { this.baseType.accumulateScalarTypes(propName, list); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public String jsonToString(byte[] value, JsonValueAdapter ctx) { return this.baseType.jsonToString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public byte[] jsonFromString(String value, JsonValueAdapter ctx) { return (byte[])this.baseType.jsonFromString(value, ctx); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 120 */     int len = dataInput.readInt();
/* 121 */     byte[] value = new byte[len];
/* 122 */     dataInput.readFully(value);
/* 123 */     return value;
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 127 */     byte[] value = (byte[])v;
/* 128 */     dataOutput.writeInt(value.length);
/* 129 */     dataOutput.write(value);
/*     */   }
/*     */ 
/*     */   
/* 133 */   public int getLuceneType() { return this.baseType.getLuceneType(); }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public Object luceneFromIndexValue(Object value) { return this.baseType.luceneFromIndexValue(value); }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public Object luceneToIndexValue(Object value) { return this.baseType.luceneToIndexValue(value); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesEncrypted.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */