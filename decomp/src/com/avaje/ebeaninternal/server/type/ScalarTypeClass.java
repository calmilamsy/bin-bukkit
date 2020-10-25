/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class ScalarTypeClass
/*    */   extends ScalarTypeBaseVarchar<Class>
/*    */ {
/* 34 */   public ScalarTypeClass() { super(Class.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public int getLength() { return 255; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public Class<?> convertFromDbString(String dbValue) { return parse(dbValue); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public String convertToDbString(Class beanValue) { return beanValue.getCanonicalName(); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String formatValue(Class v) { return v.getCanonicalName(); }
/*    */ 
/*    */   
/*    */   public Class<?> parse(String value) {
/*    */     try {
/* 58 */       return Class.forName(value);
/* 59 */     } catch (Exception e) {
/* 60 */       String msg = "Unable to find Class " + value;
/* 61 */       throw new PersistenceException(msg, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */