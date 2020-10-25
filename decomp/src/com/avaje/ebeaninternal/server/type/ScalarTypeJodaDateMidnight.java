/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.util.Date;
/*    */ import org.joda.time.DateMidnight;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScalarTypeJodaDateMidnight
/*    */   extends ScalarTypeBaseDate<DateMidnight>
/*    */ {
/* 38 */   public ScalarTypeJodaDateMidnight() { super(DateMidnight.class, false, 91); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public DateMidnight convertFromDate(Date ts) { return new DateMidnight(ts.getTime()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public Date convertToDate(DateMidnight t) { return new Date(t.getMillis()); }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 52 */     if (value instanceof DateMidnight) {
/* 53 */       return new Date(((DateMidnight)value).getMillis());
/*    */     }
/* 55 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public DateMidnight toBeanType(Object value) {
/* 59 */     if (value instanceof Date) {
/* 60 */       return new DateMidnight(((Date)value).getTime());
/*    */     }
/* 62 */     return (DateMidnight)value;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaDateMidnight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */