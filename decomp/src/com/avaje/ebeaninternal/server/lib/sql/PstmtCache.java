/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class PstmtCache
/*     */   extends LinkedHashMap<String, ExtendedPreparedStatement>
/*     */ {
/*  31 */   private static final Logger logger = Logger.getLogger(PstmtCache.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = -3096406924865550697L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String cacheName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int maxSize;
/*     */ 
/*     */ 
/*     */   
/*     */   int removeCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   int hitCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   int missCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   int putCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PstmtCache(String cacheName, int maxCacheSize) {
/*  68 */     super(maxCacheSize * 3, 0.75F, true);
/*  69 */     this.cacheName = cacheName;
/*  70 */     this.maxSize = maxCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public String getDescription() { return this.cacheName + " size:" + size() + " max:" + this.maxSize + " totalHits:" + this.hitCounter + " hitRatio:" + getHitRatio() + " removes:" + this.removeCounter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public int getMaxSize() { return this.maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHitRatio() {
/*  92 */     if (this.hitCounter == 0) {
/*  93 */       return 0;
/*     */     }
/*  95 */     return this.hitCounter * 100 / (this.hitCounter + this.missCounter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public int getHitCounter() { return this.hitCounter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int getMissCounter() { return this.missCounter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public int getPutCounter() { return this.putCounter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedPreparedStatement get(Object key) {
/* 125 */     ExtendedPreparedStatement o = (ExtendedPreparedStatement)super.get(key);
/* 126 */     if (o == null) {
/* 127 */       this.missCounter++;
/*     */     } else {
/* 129 */       this.hitCounter++;
/*     */     } 
/* 131 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedPreparedStatement remove(Object key) {
/* 139 */     ExtendedPreparedStatement o = (ExtendedPreparedStatement)super.remove(key);
/* 140 */     if (o == null) {
/* 141 */       this.missCounter++;
/*     */     } else {
/* 143 */       this.hitCounter++;
/*     */     } 
/* 145 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedPreparedStatement put(String key, ExtendedPreparedStatement value) {
/* 153 */     this.putCounter++;
/* 154 */     return (ExtendedPreparedStatement)super.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeEldestEntry(Map.Entry<String, ExtendedPreparedStatement> eldest) {
/* 164 */     if (size() < this.maxSize) {
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     this.removeCounter++;
/*     */     
/*     */     try {
/* 171 */       ExtendedPreparedStatement pstmt = (ExtendedPreparedStatement)eldest.getValue();
/* 172 */       pstmt.closeDestroy();
/* 173 */     } catch (SQLException e) {
/* 174 */       logger.log(Level.SEVERE, "Error closing ExtendedPreparedStatement", e);
/*     */     } 
/* 176 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\PstmtCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */