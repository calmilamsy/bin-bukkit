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
/*    */ public class JsonElementNull
/*    */   implements JsonElement
/*    */ {
/* 36 */   public static final JsonElementNull NULL = new JsonElementNull();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String getValue() { return "null"; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public String toString() { return "json null"; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public boolean isPrimitive() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String toPrimitiveString() { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonElementNull.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */