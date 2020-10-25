/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
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
/*     */ public class ScalarTypeUtilDate
/*     */ {
/*     */   public static class TimestampType
/*     */     extends ScalarTypeBaseDateTime<Date>
/*     */   {
/*  37 */     public TimestampType() { super(Date.class, false, 93); }
/*     */ 
/*     */     
/*     */     public Date read(DataReader dataReader) throws SQLException {
/*  41 */       Timestamp timestamp = dataReader.getTimestamp();
/*  42 */       if (timestamp == null) {
/*  43 */         return null;
/*     */       }
/*  45 */       return new Date(timestamp.getTime());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void bind(DataBind b, Date value) throws SQLException {
/*  51 */       if (value == null) {
/*  52 */         b.setNull(93);
/*     */       } else {
/*     */         
/*  55 */         Timestamp timestamp = new Timestamp(value.getTime());
/*  56 */         b.setTimestamp(timestamp);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  61 */     public Object toJdbcType(Object value) { return BasicTypeConverter.toTimestamp(value); }
/*     */ 
/*     */ 
/*     */     
/*  65 */     public Date toBeanType(Object value) { return BasicTypeConverter.toUtilDate(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     public Date convertFromTimestamp(Timestamp ts) { return new Date(ts.getTime()); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     public Timestamp convertToTimestamp(Date t) { return new Timestamp(t.getTime()); }
/*     */ 
/*     */ 
/*     */     
/*  81 */     public Date parseDateTime(long systemTimeMillis) { return new Date(systemTimeMillis); }
/*     */ 
/*     */     
/*     */     public Object luceneFromIndexValue(Object value) {
/*  85 */       Long l = (Long)value;
/*  86 */       return new Date(l.longValue());
/*     */     }
/*     */ 
/*     */     
/*  90 */     public Object luceneToIndexValue(Object value) { return Long.valueOf(((Date)value).getTime()); }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DateType
/*     */     extends ScalarTypeBaseDate<Date>
/*     */   {
/*  97 */     public DateType() { super(Date.class, false, 91); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     public Date convertFromDate(Date ts) { return new Date(ts.getTime()); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     public Date convertToDate(Date t) { return new Date(t.getTime()); }
/*     */ 
/*     */ 
/*     */     
/* 111 */     public Object toJdbcType(Object value) { return BasicTypeConverter.toDate(value); }
/*     */ 
/*     */ 
/*     */     
/* 115 */     public Date toBeanType(Object value) { return BasicTypeConverter.toUtilDate(value); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeUtilDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */