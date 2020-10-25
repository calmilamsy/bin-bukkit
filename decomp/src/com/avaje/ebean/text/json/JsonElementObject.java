/*    */ package com.avaje.ebean.text.json;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public class JsonElementObject
/*    */   implements JsonElement
/*    */ {
/* 40 */   private final Map<String, JsonElement> map = new LinkedHashMap();
/*    */ 
/*    */   
/* 43 */   public void put(String key, JsonElement value) { this.map.put(key, value); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public JsonElement get(String key) { return (JsonElement)this.map.get(key); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public JsonElement getValue(String key) { return (JsonElement)this.map.get(key); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public Set<String> keySet() { return this.map.keySet(); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public Set<Map.Entry<String, JsonElement>> entrySet() { return this.map.entrySet(); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public String toString() { return this.map.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public boolean isPrimitive() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public String toPrimitiveString() { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonElementObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */