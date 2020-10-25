/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
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
/*    */ 
/*    */ 
/*    */ public class GeneratedCounterLong
/*    */   implements GeneratedProperty
/*    */ {
/* 37 */   public Object getInsertValue(BeanProperty prop, Object bean) { return Long.valueOf(1L); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean) {
/* 44 */     Long i = (Long)prop.getValue(bean);
/* 45 */     return Long.valueOf(i.longValue() + 1L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean includeInUpdate() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public boolean includeInInsert() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public boolean isDDLNotNullable() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedCounterLong.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */