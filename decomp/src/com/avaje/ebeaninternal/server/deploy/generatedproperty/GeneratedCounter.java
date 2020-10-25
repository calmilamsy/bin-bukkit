/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
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
/*    */ public class GeneratedCounter
/*    */   implements GeneratedProperty
/*    */ {
/*    */   final int numberType;
/*    */   
/* 33 */   public GeneratedCounter(int numberType) { this.numberType = numberType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getInsertValue(BeanProperty prop, Object bean) {
/* 40 */     Integer i = Integer.valueOf(1);
/* 41 */     return BasicTypeConverter.convert(i, this.numberType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean) {
/* 48 */     Number currVal = (Number)prop.getValue(bean);
/* 49 */     Integer nextVal = Integer.valueOf(currVal.intValue() + 1);
/* 50 */     return BasicTypeConverter.convert(nextVal, this.numberType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean includeInUpdate() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public boolean includeInInsert() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public boolean isDDLNotNullable() { return true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedCounter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */