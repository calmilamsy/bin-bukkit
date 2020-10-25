/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Calendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScalarTypeCalendar
/*    */   extends ScalarTypeBaseDateTime<Calendar>
/*    */ {
/* 36 */   public ScalarTypeCalendar(int jdbcType) { super(Calendar.class, false, jdbcType); }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Calendar value) throws SQLException {
/* 40 */     if (value == null) {
/* 41 */       b.setNull(93);
/*    */     } else {
/* 43 */       Calendar date = value;
/* 44 */       if (this.jdbcType == 93) {
/* 45 */         Timestamp timestamp = new Timestamp(date.getTimeInMillis());
/* 46 */         b.setTimestamp(timestamp);
/*    */       } else {
/* 48 */         Date d = new Date(date.getTimeInMillis());
/* 49 */         b.setDate(d);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Calendar convertFromTimestamp(Timestamp ts) {
/* 56 */     Calendar calendar = Calendar.getInstance();
/* 57 */     calendar.setTimeInMillis(ts.getTime());
/* 58 */     return calendar;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public Timestamp convertToTimestamp(Calendar t) { return new Timestamp(t.getTimeInMillis()); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public Object toJdbcType(Object value) { return BasicTypeConverter.convert(value, this.jdbcType); }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public Calendar toBeanType(Object value) { return BasicTypeConverter.toCalendar(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCalendar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */