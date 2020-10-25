/*    */ package com.avaje.ebean.text.json;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class JsonElementArray
/*    */   implements JsonElement
/*    */ {
/* 39 */   private final List<JsonElement> values = new ArrayList();
/*    */ 
/*    */   
/* 42 */   public List<JsonElement> getValues() { return this.values; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public void add(JsonElement value) { this.values.add(value); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String toString() { return this.values.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean isPrimitive() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public String toPrimitiveString() { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonElementArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */