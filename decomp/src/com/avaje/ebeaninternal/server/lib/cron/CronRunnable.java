/*     */ package com.avaje.ebeaninternal.server.lib.cron;
/*     */ 
/*     */ import java.util.Calendar;
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
/*     */ public class CronRunnable
/*     */ {
/*     */   boolean isEnabled;
/*     */   CronSchedule schedule;
/*     */   Runnable runnable;
/*     */   
/*  37 */   public CronRunnable(String schedule, Runnable runnable) { this(new CronSchedule(schedule), runnable); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CronRunnable(CronSchedule schedule, Runnable runnable) {
/*     */     this.isEnabled = true;
/*  44 */     this.schedule = schedule;
/*  45 */     this.runnable = runnable;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  49 */     if (obj == null) {
/*  50 */       return false;
/*     */     }
/*  52 */     if (obj instanceof CronRunnable) {
/*  53 */       return (hashCode() == obj.hashCode());
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  59 */     hc = CronRunnable.class.getName().hashCode();
/*  60 */     hc += 31 * hc + this.schedule.hashCode();
/*  61 */     return 31 * hc + this.runnable.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean isScheduledToRunNow(Calendar thisMinute) { return (this.isEnabled && this.schedule.isScheduledToRunNow(thisMinute)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void setSchedule(String scheduleLine) { this.schedule.setSchedule(scheduleLine); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String getSchedule() { return this.schedule.getSchedule(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public Runnable getRunnable() { return this.runnable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public void setRunnable(Runnable runnable) { this.runnable = runnable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public boolean isEnabled() { return this.isEnabled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String toString() { return "CronRunnable: isEnabled[" + this.isEnabled + "] sch[" + this.schedule.getSchedule() + "] [" + this.runnable.toString() + "]"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\cron\CronRunnable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */