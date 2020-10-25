/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.Date;
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
/*    */ public class GeneratedInsertDate
/*    */   implements GeneratedProperty
/*    */ {
/* 35 */   public Object getInsertValue(BeanProperty prop, Object bean) { return new Date(System.currentTimeMillis()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public Object getUpdateValue(BeanProperty prop, Object bean) { return prop.getValue(bean); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean includeInUpdate() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean includeInInsert() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public boolean isDDLNotNullable() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedInsertDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */