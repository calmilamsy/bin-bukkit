/*    */ package com.avaje.ebean.text.json;
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
/*    */ 
/*    */ 
/*    */ public class JsonElementNumber
/*    */   implements JsonElement
/*    */ {
/*    */   private final String value;
/*    */   
/* 39 */   public JsonElementNumber(String value) { this.value = value; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public String getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public String toString() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public boolean isPrimitive() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toPrimitiveString() { return this.value; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonElementNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */