/*    */ package com.avaje.ebean;
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
/*    */ public class ValuePair
/*    */ {
/*    */   final Object value1;
/*    */   final Object value2;
/*    */   
/*    */   public ValuePair(Object value1, Object value2) {
/* 32 */     this.value1 = value1;
/* 33 */     this.value2 = value2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public Object getValue1() { return this.value1; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Object getValue2() { return this.value2; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public String toString() { return this.value1 + "," + this.value2; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\ValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */