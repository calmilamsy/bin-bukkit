/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.Date;
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
/*     */ public abstract class ScalarTypeBaseDate<T>
/*     */   extends ScalarTypeBase<T>
/*     */ {
/*  38 */   public ScalarTypeBaseDate(Class<T> type, boolean jdbcNative, int jdbcType) { super(type, jdbcNative, jdbcType); }
/*     */ 
/*     */   
/*     */   public abstract Date convertToDate(T paramT);
/*     */   
/*     */   public abstract T convertFromDate(Date paramDate);
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  46 */     if (value == null) {
/*  47 */       b.setNull(91);
/*     */     } else {
/*  49 */       Date date = convertToDate(value);
/*  50 */       b.setDate(date);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  56 */     Date ts = dataReader.getDate();
/*  57 */     if (ts == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     return (T)convertFromDate(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatValue(T t) {
/*  65 */     Date date = convertToDate(t);
/*  66 */     return date.toString();
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/*  70 */     Date date = Date.valueOf(value);
/*  71 */     return (T)convertFromDate(date);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/*  75 */     Date ts = new Date(systemTimeMillis);
/*  76 */     return (T)convertFromDate(ts);
/*     */   }
/*     */ 
/*     */   
/*  80 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/*  85 */     Date date = convertToDate(value);
/*  86 */     return ctx.jsonFromDate(date);
/*     */   }
/*     */ 
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/*  91 */     Date ts = ctx.jsonToDate(value);
/*  92 */     return (T)convertFromDate(ts);
/*     */   }
/*     */ 
/*     */   
/*  96 */   public int getLuceneType() { return 5; }
/*     */ 
/*     */   
/*     */   public Object luceneFromIndexValue(Object value) {
/* 100 */     Long l = (Long)value;
/* 101 */     Date date = new Date(l.longValue());
/* 102 */     return convertFromDate(date);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object luceneToIndexValue(Object value) {
/* 107 */     Date date = convertToDate(value);
/* 108 */     return Long.valueOf(date.getTime());
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 112 */     if (!dataInput.readBoolean()) {
/* 113 */       return null;
/*     */     }
/* 115 */     long val = dataInput.readLong();
/* 116 */     Date date = new Date(val);
/* 117 */     return convertFromDate(date);
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
/* 129 */       Date date = convertToDate(value);
/* 130 */       dataOutput.writeLong(date.getTime());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBaseDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */