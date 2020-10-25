/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ 
/*     */ public class ScalarTypeLdapTimestamp<T>
/*     */   extends Object
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
/*     */   private final ScalarType<T> baseType;
/*     */   
/*  47 */   public ScalarTypeLdapTimestamp(ScalarType<T> baseType) { this.baseType = baseType; }
/*     */ 
/*     */   
/*     */   public T toBeanType(Object value) {
/*  51 */     if (value == null) {
/*  52 */       return null;
/*     */     }
/*  54 */     if (!(value instanceof String)) {
/*  55 */       String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
/*  56 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     try {
/*  59 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  60 */       Date date = sdf.parse((String)value);
/*     */       
/*  62 */       return (T)this.baseType.parseDateTime(date.getTime());
/*     */     }
/*  64 */     catch (Exception e) {
/*  65 */       String msg = "Error parsing LDAP timestamp " + value;
/*  66 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  72 */     if (value == null) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     Object ts = this.baseType.toJdbcType(value);
/*  77 */     if (!(ts instanceof Timestamp)) {
/*  78 */       String msg = "Expecting a Timestamp type but got " + value.getClass() + " value[" + value + "]";
/*  79 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/*  82 */     Timestamp t = (Timestamp)ts;
/*  83 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  84 */     return sdf.format(t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void bind(DataBind b, T value) throws SQLException { this.baseType.bind(b, value); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public int getJdbcType() { return 12; }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public int getLength() { return this.baseType.getLength(); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Class<T> getType() { return this.baseType.getType(); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public boolean isDateTimeCapable() { return this.baseType.isDateTimeCapable(); }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public boolean isJdbcNative() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void loadIgnore(DataReader dataReader) { this.baseType.loadIgnore(dataReader); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String format(Object v) { return this.baseType.format(v); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public String formatValue(T t) { return this.baseType.formatValue(t); }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public T parse(String value) { return (T)this.baseType.parse(value); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   public T parseDateTime(long systemTimeMillis) { return (T)this.baseType.parseDateTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public T read(DataReader dataReader) throws SQLException { return (T)this.baseType.read(dataReader); }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { this.baseType.accumulateScalarTypes(propName, list); }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public String jsonToString(T value, JsonValueAdapter ctx) { return this.baseType.jsonToString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 145 */   public T jsonFromString(String value, JsonValueAdapter ctx) { return (T)this.baseType.jsonFromString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 149 */   public Object readData(DataInput dataInput) throws IOException { return this.baseType.readData(dataInput); }
/*     */ 
/*     */ 
/*     */   
/* 153 */   public void writeData(DataOutput dataOutput, Object v) throws IOException { this.baseType.writeData(dataOutput, v); }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public int getLuceneType() { return this.baseType.getLuceneType(); }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public Object luceneFromIndexValue(Object value) { return this.baseType.luceneFromIndexValue(value); }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public Object luceneToIndexValue(Object value) { return this.baseType.luceneToIndexValue(value); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLdapTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */