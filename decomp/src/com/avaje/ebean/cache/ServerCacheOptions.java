/*     */ package com.avaje.ebean.cache;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CacheTuning;
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
/*     */ public class ServerCacheOptions
/*     */ {
/*     */   private int maxSize;
/*     */   private int maxIdleSecs;
/*     */   private int maxSecsToLive;
/*     */   
/*     */   public ServerCacheOptions() {}
/*     */   
/*     */   public ServerCacheOptions(CacheTuning cacheTuning) {
/*  44 */     this.maxSize = cacheTuning.maxSize();
/*  45 */     this.maxIdleSecs = cacheTuning.maxIdleSecs();
/*  46 */     this.maxSecsToLive = cacheTuning.maxSecsToLive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheOptions(ServerCacheOptions d) {
/*  53 */     this.maxSize = d.getMaxSize();
/*  54 */     this.maxIdleSecs = d.getMaxIdleSecs();
/*  55 */     this.maxSecsToLive = d.getMaxIdleSecs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyDefaults(ServerCacheOptions defaults) {
/*  63 */     if (this.maxSize == 0) {
/*  64 */       this.maxSize = defaults.getMaxSize();
/*     */     }
/*  66 */     if (this.maxIdleSecs == 0) {
/*  67 */       this.maxIdleSecs = defaults.getMaxIdleSecs();
/*     */     }
/*  69 */     if (this.maxSecsToLive == 0) {
/*  70 */       this.maxSecsToLive = defaults.getMaxSecsToLive();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCacheOptions copy() {
/*  79 */     ServerCacheOptions copy = new ServerCacheOptions();
/*  80 */     copy.maxSize = this.maxSize;
/*  81 */     copy.maxIdleSecs = this.maxIdleSecs;
/*  82 */     copy.maxSecsToLive = this.maxSecsToLive;
/*     */     
/*  84 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public int getMaxSize() { return this.maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public int getMaxIdleSecs() { return this.maxIdleSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public void setMaxIdleSecs(int maxIdleSecs) { this.maxIdleSecs = maxIdleSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public int getMaxSecsToLive() { return this.maxSecsToLive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void setMaxSecsToLive(int maxSecsToLive) { this.maxSecsToLive = maxSecsToLive; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\cache\ServerCacheOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */