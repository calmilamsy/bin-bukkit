/*    */ package com.avaje.ebeaninternal.server.autofetch;
/*    */ 
/*    */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatisticsQuery
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1133958958072778811L;
/*    */   private final String path;
/*    */   private int exeCount;
/*    */   private int totalBeanLoaded;
/*    */   private int totalMicros;
/*    */   
/* 23 */   public StatisticsQuery(String path) { this.path = path; }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public MetaAutoFetchStatistic.QueryStats createPublicMeta() { return new MetaAutoFetchStatistic.QueryStats(this.path, this.exeCount, this.totalBeanLoaded, this.totalMicros); }
/*    */ 
/*    */   
/*    */   public void add(int beansLoaded, int micros) {
/* 31 */     this.exeCount++;
/* 32 */     this.totalBeanLoaded += beansLoaded;
/* 33 */     this.totalMicros += micros;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     long avgMicros = (this.exeCount == 0) ? 0L : (this.totalMicros / this.exeCount);
/*    */     
/* 39 */     return "queryExe path[" + this.path + "] count[" + this.exeCount + "] totalBeansLoaded[" + this.totalBeanLoaded + "] avgMicros[" + avgMicros + "] totalMicros[" + this.totalMicros + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\autofetch\StatisticsQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */