/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class AnnotationInfo {
/*    */   final HashMap<String, Object> valueMap;
/*    */   AnnotationInfo parent;
/*    */   
/*    */   public AnnotationInfo(AnnotationInfo parent) {
/* 11 */     this.valueMap = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 20 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public String toString() { return this.valueMap.toString(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public AnnotationInfo getParent() { return this.parent; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setParent(AnnotationInfo parent) { this.parent = parent; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(String prefix, String name, Object value) {
/* 41 */     if (name == null) {
/*    */       
/* 43 */       ArrayList<Object> list = (ArrayList)this.valueMap.get(prefix);
/* 44 */       if (list == null) {
/* 45 */         list = new ArrayList<Object>();
/* 46 */         this.valueMap.put(prefix, list);
/*    */       } 
/*    */       
/* 49 */       list.add(value);
/*    */     } else {
/*    */       
/* 52 */       String key = getKey(prefix, name);
/*    */       
/* 54 */       this.valueMap.put(key, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public void addEnum(String prefix, String name, String desc, String value) { add(prefix, name, value); }
/*    */ 
/*    */   
/*    */   private String getKey(String prefix, String name) {
/* 67 */     if (prefix == null) {
/* 68 */       return name;
/*    */     }
/* 70 */     return prefix + "." + name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue(String key) {
/* 78 */     Object o = this.valueMap.get(key);
/* 79 */     if (o == null && this.parent != null)
/*    */     {
/* 81 */       o = this.parent.getValue(key);
/*    */     }
/* 83 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\AnnotationInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */