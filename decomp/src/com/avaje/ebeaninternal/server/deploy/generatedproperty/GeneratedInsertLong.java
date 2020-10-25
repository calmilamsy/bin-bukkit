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
/*    */ public class GeneratedInsertLong
/*    */   implements GeneratedProperty
/*    */ {
/* 33 */   public Object getInsertValue(BeanProperty prop, Object bean) { return Long.valueOf(System.currentTimeMillis()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public Object getUpdateValue(BeanProperty prop, Object bean) { return prop.getValue(bean); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public boolean includeInUpdate() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean includeInInsert() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public boolean isDDLNotNullable() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedInsertLong.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */