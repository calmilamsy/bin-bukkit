/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.TimeZone;
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
/*    */ public class ScalarTypeTimeZone
/*    */   extends ScalarTypeBaseVarchar<TimeZone>
/*    */ {
/* 30 */   public ScalarTypeTimeZone() { super(TimeZone.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public int getLength() { return 20; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public TimeZone convertFromDbString(String dbValue) { return TimeZone.getTimeZone(dbValue); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public String convertToDbString(TimeZone beanValue) { return beanValue.getID(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public String formatValue(TimeZone v) { return v.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public TimeZone parse(String value) { return TimeZone.getTimeZone(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */