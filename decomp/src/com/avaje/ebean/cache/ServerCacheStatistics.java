/*     */ package com.avaje.ebean.cache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerCacheStatistics
/*     */ {
/*     */   protected String cacheName;
/*     */   protected int maxSize;
/*     */   protected int size;
/*     */   protected int hitCount;
/*     */   protected int missCount;
/*     */   
/*  47 */   public String getCacheName() { return this.cacheName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public void setCacheName(String cacheName) { this.cacheName = cacheName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public int getHitCount() { return this.hitCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public void setHitCount(int hitCount) { this.hitCount = hitCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public int getMissCount() { return this.missCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void setMissCount(int missCount) { this.missCount = missCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public int getSize() { return this.size; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public void setSize(int size) { this.size = size; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public int getMaxSize() { return this.maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHitRatio() {
/* 125 */     int totalCount = this.hitCount + this.missCount;
/* 126 */     if (totalCount == 0) {
/* 127 */       return 0;
/*     */     }
/* 129 */     return this.hitCount * 100 / totalCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\cache\ServerCacheStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */