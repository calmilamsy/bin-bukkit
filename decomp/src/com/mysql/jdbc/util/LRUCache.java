/*    */ package com.mysql.jdbc.util;
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
/*    */ public class LRUCache
/*    */   extends LinkedHashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected int maxElements;
/*    */   
/*    */   public LRUCache(int maxSize) {
/* 40 */     super(maxSize);
/* 41 */     this.maxElements = maxSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   protected boolean removeEldestEntry(Map.Entry eldest) { return (size() > this.maxElements); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdb\\util\LRUCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */