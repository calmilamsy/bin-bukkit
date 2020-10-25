/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.joda.time.LocalDateTime;
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
/*    */ public class ScalarTypeJodaLocalDateTime
/*    */   extends ScalarTypeBaseDateTime<LocalDateTime>
/*    */ {
/* 35 */   public ScalarTypeJodaLocalDateTime() { super(LocalDateTime.class, false, 93); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public LocalDateTime convertFromTimestamp(Timestamp ts) { return new LocalDateTime(ts.getTime()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public Timestamp convertToTimestamp(LocalDateTime t) { return new Timestamp(t.toDateTime().getMillis()); }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof LocalDateTime) {
/* 50 */       return new Timestamp(((LocalDateTime)value).toDateTime().getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */   
/*    */   public LocalDateTime toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new LocalDateTime(((Date)value).getTime());
/*    */     }
/* 59 */     return (LocalDateTime)value;
/*    */   }
/*    */ 
/*    */   
/* 63 */   public LocalDateTime parseDateTime(long systemTimeMillis) { return new LocalDateTime(systemTimeMillis); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */