/*     */ package com.avaje.ebean.meta;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
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
/*     */ @Entity
/*     */ public class MetaQueryStatistic
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8746524372894472583L;
/*     */   boolean autofetchTuned;
/*     */   String beanType;
/*     */   int origQueryPlanHash;
/*     */   int finalQueryPlanHash;
/*     */   String sql;
/*     */   int executionCount;
/*     */   int totalLoadedBeans;
/*     */   int totalTimeMicros;
/*     */   long collectionStart;
/*     */   long lastQueryTime;
/*     */   int avgTimeMicros;
/*     */   int avgLoadedBeans;
/*     */   
/*     */   public MetaQueryStatistic() {}
/*     */   
/*     */   public MetaQueryStatistic(boolean autofetchTuned, String beanType, int plan, String sql, int executionCount, int totalLoadedBeans, int totalTimeMicros, long collectionStart, long lastQueryTime) {
/*  55 */     this.autofetchTuned = autofetchTuned;
/*  56 */     this.beanType = beanType;
/*  57 */     this.finalQueryPlanHash = plan;
/*  58 */     this.sql = sql;
/*  59 */     this.executionCount = executionCount;
/*  60 */     this.totalLoadedBeans = totalLoadedBeans;
/*  61 */     this.totalTimeMicros = totalTimeMicros;
/*  62 */     this.collectionStart = collectionStart;
/*     */     
/*  64 */     this.lastQueryTime = lastQueryTime;
/*  65 */     this.avgTimeMicros = (executionCount == 0) ? 0 : (totalTimeMicros / executionCount);
/*  66 */     this.avgLoadedBeans = (executionCount == 0) ? 0 : (totalLoadedBeans / executionCount);
/*     */   }
/*     */ 
/*     */   
/*  70 */   public String toString() { return "type=" + this.beanType + " tuned:" + this.autofetchTuned + " origHash=" + this.origQueryPlanHash + " count=" + this.executionCount + " avgMicros=" + getAvgTimeMicros(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean isAutofetchTuned() { return this.autofetchTuned; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public int getOrigQueryPlanHash() { return this.origQueryPlanHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int getFinalQueryPlanHash() { return this.finalQueryPlanHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public String getBeanType() { return this.beanType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public String getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public int getExecutionCount() { return this.executionCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public int getTotalLoadedBeans() { return this.totalLoadedBeans; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public int getTotalTimeMicros() { return this.totalTimeMicros; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public long getCollectionStart() { return this.collectionStart; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public long getLastQueryTime() { return this.lastQueryTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public int getAvgTimeMicros() { return this.avgTimeMicros; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public int getAvgLoadedBeans() { return this.avgLoadedBeans; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\meta\MetaQueryStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */