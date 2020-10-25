/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
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
/*    */ public class ScalarTypeLongToTimestamp
/*    */   extends ScalarTypeWrapper<Long, Timestamp>
/*    */ {
/* 27 */   public ScalarTypeLongToTimestamp() { super(Long.class, new ScalarTypeTimestamp(), new LongToTimestampConverter()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLongToTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */