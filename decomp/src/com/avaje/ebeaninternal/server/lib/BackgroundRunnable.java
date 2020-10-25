/*     */ package com.avaje.ebeaninternal.server.lib;
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
/*     */ public class BackgroundRunnable
/*     */ {
/*     */   Runnable runnable;
/*     */   int freqInSecs;
/*     */   int runCount;
/*     */   long totalRunTime;
/*     */   long startTimeTemp;
/*     */   long startAfter;
/*     */   boolean isActive;
/*     */   
/*  59 */   public BackgroundRunnable(Runnable runnable, int freqInSecs) { this(runnable, freqInSecs, System.currentTimeMillis() + (1000 * (freqInSecs + 10))); } public BackgroundRunnable(Runnable runnable, int freqInSecs, long startAfter) {
/*     */     this.runCount = 0;
/*     */     this.totalRunTime = 0L;
/*     */     this.isActive = true;
/*  63 */     this.runnable = runnable;
/*  64 */     this.freqInSecs = freqInSecs;
/*  65 */     this.startAfter = startAfter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean runNow(long now) { return (now > this.startAfter); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public boolean isActive() { return this.isActive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setActive(boolean isActive) { this.isActive = isActive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   protected void runStart() { this.startTimeTemp = System.currentTimeMillis(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runEnd() {
/* 104 */     this.runCount++;
/* 105 */     long exeTime = System.currentTimeMillis() - this.startTimeTemp;
/* 106 */     this.totalRunTime += exeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public int getRunCount() { return this.runCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAverageRunTime() {
/* 120 */     if (this.runCount == 0) {
/* 121 */       return 0L;
/*     */     }
/* 123 */     return this.totalRunTime / this.runCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public int getFreqInSecs() { return this.freqInSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public void setFreqInSecs(int freqInSecs) { this.freqInSecs = freqInSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public Runnable getRunnable() { return this.runnable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void setRunnable(Runnable runnable) { this.runnable = runnable; }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 155 */     StringBuffer sb = new StringBuffer();
/* 156 */     sb.append("[");
/* 157 */     sb.append(this.runnable.getClass().getName());
/* 158 */     sb.append(" freq:").append(this.freqInSecs);
/* 159 */     sb.append(" count:").append(getRunCount());
/* 160 */     sb.append(" avgTime:").append(getAverageRunTime());
/* 161 */     sb.append("]");
/* 162 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\BackgroundRunnable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */