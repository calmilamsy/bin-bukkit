/*     */ package com.avaje.ebeaninternal.server.lib.thread;
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
/*     */ public class Work
/*     */ {
/*     */   private Runnable runnable;
/*     */   private long exitQueueTime;
/*     */   private long enterQueueTime;
/*     */   private long startTime;
/*     */   
/*  34 */   public Work(Runnable runnable) { this.runnable = runnable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public Runnable getRunnable() { return this.runnable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public long getStartTime() { return this.startTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public void setStartTime(long startTime) { this.startTime = startTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public long getEnterQueueTime() { return this.enterQueueTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public void setEnterQueueTime(long enterQueueTime) { this.enterQueueTime = enterQueueTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public long getExitQueueTime() { return this.exitQueueTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void setExitQueueTime(long exitQueueTime) { this.exitQueueTime = exitQueueTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public String toString() { return getDescription(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("Work[");
/* 100 */     if (this.runnable != null) {
/* 101 */       sb.append(this.runnable.toString());
/*     */     }
/* 103 */     sb.append("]");
/* 104 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\thread\Work.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */