/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.joda.time.DateTime;
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
/*    */ public class ScalarTypeJodaDateTime
/*    */   extends ScalarTypeBaseDateTime<DateTime>
/*    */ {
/* 35 */   public ScalarTypeJodaDateTime() { super(DateTime.class, false, 93); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public DateTime convertFromTimestamp(Timestamp ts) { return new DateTime(ts.getTime()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public Timestamp convertToTimestamp(DateTime t) { return new Timestamp(t.getMillis()); }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof DateTime) {
/* 50 */       return new Timestamp(((DateTime)value).getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */   
/*    */   public DateTime toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new DateTime(((Date)value).getTime());
/*    */     }
/* 59 */     return (DateTime)value;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */