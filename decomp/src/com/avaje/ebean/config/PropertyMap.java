/*    */ package com.avaje.ebean.config;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PropertyMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private LinkedHashMap<String, String> map = new LinkedHashMap();
/*    */ 
/*    */   
/* 19 */   public String toString() { return this.map.toString(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void evaluateProperties() {
/* 28 */     for (Map.Entry<String, String> e : entrySet()) {
/* 29 */       String key = (String)e.getKey();
/* 30 */       String val = (String)e.getValue();
/* 31 */       String eval = eval(val);
/* 32 */       if (eval != null && !eval.equals(val)) {
/* 33 */         put(key, eval);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 39 */   public String eval(String val) { return PropertyExpression.eval(val, this); }
/*    */ 
/*    */   
/*    */   public boolean getBoolean(String key, boolean defaultValue) {
/* 43 */     String value = get(key);
/* 44 */     if (value == null) {
/* 45 */       return defaultValue;
/*    */     }
/* 47 */     return Boolean.parseBoolean(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt(String key, int defaultValue) {
/* 52 */     String value = get(key);
/* 53 */     if (value == null) {
/* 54 */       return defaultValue;
/*    */     }
/* 56 */     return Integer.parseInt(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String get(String key, String defaultValue) {
/* 61 */     String value = (String)this.map.get(key.toLowerCase());
/* 62 */     return (value == null) ? defaultValue : value;
/*    */   }
/*    */ 
/*    */   
/* 66 */   public String get(String key) { return (String)this.map.get(key.toLowerCase()); }
/*    */ 
/*    */   
/*    */   void putAll(Map<String, String> keyValueMap) {
/* 70 */     Iterator<Map.Entry<String, String>> it = keyValueMap.entrySet().iterator();
/* 71 */     while (it.hasNext()) {
/* 72 */       Map.Entry<String, String> entry = (Map.Entry)it.next();
/* 73 */       put((String)entry.getKey(), (String)entry.getValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   String putEval(String key, String value) {
/* 78 */     value = PropertyExpression.eval(value, this);
/* 79 */     return (String)this.map.put(key.toLowerCase(), value);
/*    */   }
/*    */ 
/*    */   
/* 83 */   String put(String key, String value) { return (String)this.map.put(key.toLowerCase(), value); }
/*    */ 
/*    */ 
/*    */   
/* 87 */   String remove(String key) { return (String)this.map.remove(key.toLowerCase()); }
/*    */ 
/*    */ 
/*    */   
/* 91 */   Set<Map.Entry<String, String>> entrySet() { return this.map.entrySet(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\PropertyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */