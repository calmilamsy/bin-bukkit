/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CQueryStats
/*    */ {
/*    */   private final int count;
/*    */   private final int totalLoadedBeanCount;
/*    */   private final int totalTimeMicros;
/*    */   private final long startCollecting;
/*    */   private final long lastQueryTime;
/*    */   
/*    */   public CQueryStats() {
/* 22 */     this.count = 0;
/* 23 */     this.totalLoadedBeanCount = 0;
/* 24 */     this.totalTimeMicros = 0;
/* 25 */     this.startCollecting = System.currentTimeMillis();
/* 26 */     this.lastQueryTime = 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CQueryStats(CQueryStats previous, int loadedBeanCount, int timeMicros) {
/* 33 */     previous.count++;
/* 34 */     previous.totalLoadedBeanCount += loadedBeanCount;
/* 35 */     previous.totalTimeMicros += timeMicros;
/* 36 */     this.startCollecting = previous.startCollecting;
/* 37 */     this.lastQueryTime = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/* 41 */   public CQueryStats add(int loadedBeanCount, int timeMicros) { return new CQueryStats(this, loadedBeanCount, timeMicros); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int getCount() { return this.count; }
/*    */ 
/*    */   
/*    */   public int getAverageTimeMicros() {
/* 49 */     if (this.count == 0) {
/* 50 */       return 0;
/*    */     }
/* 52 */     return this.totalTimeMicros / this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public int getTotalLoadedBeanCount() { return this.totalLoadedBeanCount; }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public int getTotalTimeMicros() { return this.totalTimeMicros; }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public long getStartCollecting() { return this.startCollecting; }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public long getLastQueryTime() { return this.lastQueryTime; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public MetaQueryStatistic createMetaQueryStatistic(String beanName, CQueryPlan qp) { return new MetaQueryStatistic(qp.isAutofetchTuned(), beanName, qp.getHash(), qp.getSql(), this.count, this.totalLoadedBeanCount, this.totalTimeMicros, this.startCollecting, this.lastQueryTime); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryStats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */