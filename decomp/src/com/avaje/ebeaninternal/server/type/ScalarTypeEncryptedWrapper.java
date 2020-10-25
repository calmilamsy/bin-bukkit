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
/*     */ public class ScalarTypeEncryptedWrapper<T>
/*     */   extends Object
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private final ScalarType<T> wrapped;
/*     */   private final DataEncryptSupport dataEncryptSupport;
/*     */   private final ScalarTypeBytesBase byteArrayType;
/*     */   
/*     */   public ScalarTypeEncryptedWrapper(ScalarType<T> wrapped, ScalarTypeBytesBase byteArrayType, DataEncryptSupport dataEncryptSupport) {
/*  38 */     this.wrapped = wrapped;
/*  39 */     this.byteArrayType = byteArrayType;
/*  40 */     this.dataEncryptSupport = dataEncryptSupport;
/*     */   }
/*     */ 
/*     */   
/*  44 */   public Object readData(DataInput dataInput) throws IOException { return this.wrapped.readData(dataInput); }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public void writeData(DataOutput dataOutput, Object v) throws IOException { this.wrapped.writeData(dataOutput, v); }
/*     */ 
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  53 */     byte[] data = dataReader.getBytes();
/*  54 */     String formattedValue = this.dataEncryptSupport.decryptObject(data);
/*  55 */     if (formattedValue == null) {
/*  56 */       return null;
/*     */     }
/*  58 */     return (T)this.wrapped.parse(formattedValue);
/*     */   }
/*     */   
/*     */   private byte[] encrypt(T value) {
/*  62 */     String formatValue = this.wrapped.formatValue(value);
/*  63 */     return this.dataEncryptSupport.encryptObject(formatValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  68 */     byte[] encryptedValue = encrypt(value);
/*  69 */     this.byteArrayType.bind(b, encryptedValue);
/*     */   }
/*     */ 
/*     */   
/*  73 */   public int getJdbcType() { return this.byteArrayType.getJdbcType(); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public int getLength() { return this.byteArrayType.getLength(); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public Class<T> getType() { return this.wrapped.getType(); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isDateTimeCapable() { return this.wrapped.isDateTimeCapable(); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean isJdbcNative() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public void loadIgnore(DataReader dataReader) { this.wrapped.loadIgnore(dataReader); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String format(Object v) { return formatValue(v); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public String formatValue(T v) { return this.wrapped.formatValue(v); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public T parse(String value) { return (T)this.wrapped.parse(value); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public T parseDateTime(long systemTimeMillis) { return (T)this.wrapped.parseDateTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public T toBeanType(Object value) { return (T)this.wrapped.toBeanType(value); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public Object toJdbcType(Object value) { return this.wrapped.toJdbcType(value); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { this.wrapped.accumulateScalarTypes(propName, list); }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String jsonToString(T value, JsonValueAdapter ctx) { return this.wrapped.jsonToString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public T jsonFromString(String value, JsonValueAdapter ctx) { return (T)this.wrapped.jsonFromString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public int getLuceneType() { return this.wrapped.getLuceneType(); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public Object luceneFromIndexValue(Object value) { return this.wrapped.luceneFromIndexValue(value); }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public Object luceneToIndexValue(Object value) { return this.wrapped.luceneToIndexValue(value); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEncryptedWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */