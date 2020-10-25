/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
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
/*     */ public class ScalarTypeLdapDate<T>
/*     */   extends Object
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
/*     */   private final ScalarType<T> baseType;
/*     */   
/*  46 */   public ScalarTypeLdapDate(ScalarType<T> baseType) { this.baseType = baseType; }
/*     */ 
/*     */   
/*     */   public T toBeanType(Object value) {
/*  50 */     if (value == null) {
/*  51 */       return null;
/*     */     }
/*  53 */     if (!(value instanceof String)) {
/*  54 */       String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
/*  55 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     try {
/*  58 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  59 */       Date date = sdf.parse((String)value);
/*     */       
/*  61 */       return (T)this.baseType.parseDateTime(date.getTime());
/*     */     }
/*  63 */     catch (Exception e) {
/*  64 */       String msg = "Error parsing LDAP timestamp " + value;
/*  65 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  71 */     if (value == null) {
/*  72 */       return null;
/*     */     }
/*     */     
/*  75 */     Object ts = this.baseType.toJdbcType(value);
/*  76 */     if (!(ts instanceof Date)) {
/*  77 */       String msg = "Expecting a java.sql.Date type but got " + value.getClass() + " value[" + value + "]";
/*  78 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/*  81 */     Date t = (Date)ts;
/*  82 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  83 */     return sdf.format(t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void bind(DataBind b, T value) throws SQLException { this.baseType.bind(b, value); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public int getJdbcType() { return 12; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public int getLength() { return this.baseType.getLength(); }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public Class<T> getType() { return this.baseType.getType(); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public boolean isDateTimeCapable() { return this.baseType.isDateTimeCapable(); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public boolean isJdbcNative() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public void loadIgnore(DataReader dataReader) { this.baseType.loadIgnore(dataReader); }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public String format(Object v) { return this.baseType.format(v); }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String formatValue(T t) { return this.baseType.formatValue(t); }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public T parse(String value) { return (T)this.baseType.parse(value); }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public T parseDateTime(long systemTimeMillis) { return (T)this.baseType.parseDateTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public T read(DataReader dataReader) throws SQLException { return (T)this.baseType.read(dataReader); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) { this.baseType.accumulateScalarTypes(propName, list); }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String jsonToString(T value, JsonValueAdapter ctx) { return this.baseType.jsonToString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public T jsonFromString(String value, JsonValueAdapter ctx) { return (T)this.baseType.jsonFromString(value, ctx); }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public Object readData(DataInput dataInput) throws IOException { return this.baseType.readData(dataInput); }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public void writeData(DataOutput dataOutput, Object v) throws IOException { this.baseType.writeData(dataOutput, v); }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public int getLuceneType() { return this.baseType.getLuceneType(); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public Object luceneFromIndexValue(Object value) { return this.baseType.luceneFromIndexValue(value); }
/*     */ 
/*     */ 
/*     */   
/* 164 */   public Object luceneToIndexValue(Object value) { return this.baseType.luceneToIndexValue(value); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLdapDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */