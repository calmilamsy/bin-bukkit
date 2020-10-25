/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.util.Date;
/*    */ import org.joda.time.LocalDate;
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
/*    */ public class ScalarTypeJodaLocalDate
/*    */   extends ScalarTypeBaseDate<LocalDate>
/*    */ {
/* 35 */   public ScalarTypeJodaLocalDate() { super(LocalDate.class, false, 91); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public LocalDate convertFromDate(Date ts) { return new LocalDate(ts.getTime()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public Date convertToDate(LocalDate t) { return new Date(t.toDateMidnight().getMillis()); }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof LocalDate) {
/* 50 */       return new Date(((LocalDate)value).toDateMidnight().getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public LocalDate toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new LocalDate(((Date)value).getTime());
/*    */     }
/* 59 */     return (LocalDate)value;
/*    */   }
/*    */ 
/*    */   
/* 63 */   public LocalDate parseDateTime(long systemTimeMillis) { return new LocalDate(systemTimeMillis); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */