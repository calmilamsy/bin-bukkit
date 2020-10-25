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
/*    */ public class JsonElementBoolean
/*    */   implements JsonElement
/*    */ {
/* 37 */   public static final JsonElementBoolean TRUE = new JsonElementBoolean(true);
/*    */   
/* 39 */   public static final JsonElementBoolean FALSE = new JsonElementBoolean(false);
/*    */   
/*    */   private final boolean value;
/*    */ 
/*    */   
/* 44 */   private JsonElementBoolean(boolean value) { this.value = value; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String toString() { return Boolean.toString(this.value); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean isPrimitive() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public String toPrimitiveString() { return Boolean.toString(this.value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonElementBoolean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */