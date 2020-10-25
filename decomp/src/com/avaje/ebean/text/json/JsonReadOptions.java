/*    */ package com.avaje.ebean.text.json;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class JsonReadOptions
/*    */ {
/*    */   protected JsonValueAdapter valueAdapter;
/* 50 */   protected Map<String, JsonReadBeanVisitor<?>> visitorMap = new LinkedHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public JsonValueAdapter getValueAdapter() { return this.valueAdapter; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public Map<String, JsonReadBeanVisitor<?>> getVisitorMap() { return this.visitorMap; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonReadOptions setValueAdapter(JsonValueAdapter valueAdapter) {
/* 71 */     this.valueAdapter = valueAdapter;
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public JsonReadOptions addRootVisitor(JsonReadBeanVisitor<?> visitor) { return addVisitor(null, visitor); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonReadOptions addVisitor(String path, JsonReadBeanVisitor<?> visitor) {
/* 86 */     this.visitorMap.put(path, visitor);
/* 87 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonReadOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */