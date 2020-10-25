/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.LocalTime;
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
/*     */ public class ScalarTypeJodaLocalTime
/*     */   extends ScalarTypeBase<LocalTime>
/*     */ {
/*  41 */   public ScalarTypeJodaLocalTime() { super(LocalTime.class, false, 92); }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, LocalTime value) throws SQLException {
/*  45 */     if (value == null) {
/*  46 */       b.setNull(92);
/*     */     } else {
/*  48 */       Time sqlTime = new Time(value.getMillisOfDay());
/*  49 */       b.setTime(sqlTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalTime read(DataReader dataReader) throws SQLException {
/*  55 */     Time sqlTime = dataReader.getTime();
/*  56 */     if (sqlTime == null) {
/*  57 */       return null;
/*     */     }
/*  59 */     return new LocalTime(sqlTime, DateTimeZone.UTC);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  64 */     if (value instanceof LocalTime) {
/*  65 */       return new Time(((LocalTime)value).getMillisOfDay());
/*     */     }
/*  67 */     return BasicTypeConverter.toTime(value);
/*     */   }
/*     */   
/*     */   public LocalTime toBeanType(Object value) {
/*  71 */     if (value instanceof java.util.Date) {
/*  72 */       return new LocalTime(value, DateTimeZone.UTC);
/*     */     }
/*  74 */     return (LocalTime)value;
/*     */   }
/*     */ 
/*     */   
/*  78 */   public String formatValue(LocalTime v) { return v.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public LocalTime parse(String value) { return new LocalTime(value); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public LocalTime parseDateTime(long systemTimeMillis) { return new LocalTime(systemTimeMillis); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public boolean isDateTimeCapable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int getLuceneType() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public Object luceneFromIndexValue(Object value) { return parse((String)value); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public Object luceneToIndexValue(Object value) { return format(value); }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 106 */     if (!dataInput.readBoolean()) {
/* 107 */       return null;
/*     */     }
/* 109 */     String val = dataInput.readUTF();
/* 110 */     return parse(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 116 */     Time value = (Time)v;
/* 117 */     if (value == null) {
/* 118 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 120 */       dataOutput.writeBoolean(true);
/* 121 */       dataOutput.writeUTF(format(value));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */