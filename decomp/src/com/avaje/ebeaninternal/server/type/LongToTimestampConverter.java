/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import java.sql.Timestamp;
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
/*    */ public class LongToTimestampConverter
/*    */   extends Object
/*    */   implements ScalarTypeConverter<Long, Timestamp>
/*    */ {
/* 29 */   public Long getNullValue() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Timestamp unwrapValue(Long beanType) { return new Timestamp(beanType.longValue()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Long wrapValue(Timestamp scalarType) { return Long.valueOf(scalarType.getTime()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\LongToTimestampConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */