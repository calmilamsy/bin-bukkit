/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
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
/*     */ public abstract class ScalarTypeBaseDateTime<T>
/*     */   extends ScalarTypeBase<T>
/*     */ {
/*  38 */   public ScalarTypeBaseDateTime(Class<T> type, boolean jdbcNative, int jdbcType) { super(type, jdbcNative, jdbcType); }
/*     */ 
/*     */   
/*     */   public abstract Timestamp convertToTimestamp(T paramT);
/*     */   
/*     */   public abstract T convertFromTimestamp(Timestamp paramTimestamp);
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  46 */     if (value == null) {
/*  47 */       b.setNull(93);
/*     */     } else {
/*  49 */       Timestamp ts = convertToTimestamp(value);
/*  50 */       b.setTimestamp(ts);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  56 */     Timestamp ts = dataReader.getTimestamp();
/*  57 */     if (ts == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     return (T)convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatValue(T t) {
/*  65 */     Timestamp ts = convertToTimestamp(t);
/*  66 */     return ts.toString();
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/*  70 */     Timestamp ts = Timestamp.valueOf(value);
/*  71 */     return (T)convertFromTimestamp(ts);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/*  75 */     Timestamp ts = new Timestamp(systemTimeMillis);
/*  76 */     return (T)convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*  80 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/*  85 */     Timestamp ts = convertToTimestamp(value);
/*  86 */     return ctx.jsonFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/*  91 */     Timestamp ts = ctx.jsonToTimestamp(value);
/*  92 */     return (T)convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*  96 */   public int getLuceneType() { return 6; }
/*     */ 
/*     */   
/*     */   public Object luceneFromIndexValue(Object value) {
/* 100 */     Long l = (Long)value;
/* 101 */     Timestamp ts = new Timestamp(l.longValue());
/* 102 */     return convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object luceneToIndexValue(Object value) {
/* 107 */     Timestamp ts = convertToTimestamp(value);
/* 108 */     return Long.valueOf(ts.getTime());
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 112 */     if (!dataInput.readBoolean()) {
/* 113 */       return null;
/*     */     }
/* 115 */     long val = dataInput.readLong();
/* 116 */     Timestamp ts = new Timestamp(val);
/* 117 */     return convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 124 */     T value = (T)v;
/* 125 */     if (value == null) {
/* 126 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 128 */       dataOutput.writeBoolean(true);
/* 129 */       Timestamp ts = convertToTimestamp(value);
/* 130 */       dataOutput.writeLong(ts.getTime());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBaseDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */