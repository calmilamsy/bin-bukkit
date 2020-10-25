/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.util.UUID;
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
/*    */ public class ScalarTypeUUID
/*    */   extends ScalarTypeBaseVarchar<UUID>
/*    */ {
/* 32 */   public ScalarTypeUUID() { super(UUID.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public int getLength() { return 40; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public UUID convertFromDbString(String dbValue) { return UUID.fromString(dbValue); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public String convertToDbString(UUID beanValue) { return formatValue(beanValue); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public UUID toBeanType(Object value) { return BasicTypeConverter.toUUID(value); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public Object toJdbcType(Object value) { return BasicTypeConverter.convert(value, this.jdbcType); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public String formatValue(UUID v) { return v.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public UUID parse(String value) { return UUID.fromString(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeUUID.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */