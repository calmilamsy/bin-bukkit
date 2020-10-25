/*     */ package com.avaje.ebeaninternal.server.lib.cron;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ public final class CronManager
/*     */ {
/*     */   private boolean running;
/*     */   private ThreadPool threadPool;
/*     */   private Vector<CronRunnable> runList;
/*     */   private Thread backgroundThread;
/*     */   private boolean isDowntime;
/*     */   private static final long SMALL_DELAY = 10L;
/*  43 */   private static final Logger logger = Logger.getLogger(CronManager.class.getName());
/*     */   
/*     */   private static class CronManagerHolder {
/*  46 */     private static CronManager me = new CronManager(null); }
/*     */   
/*     */   private CronManager() {
/*  49 */     this.running = true;
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
/*  69 */     this.isDowntime = false;
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
/*  83 */     this.runList = new Vector();
/*  84 */     this.threadPool = ThreadPoolManager.getThreadPool("CronManager");
/*     */     
/*  86 */     this.backgroundThread = new Thread(new Runner(this, null), "CronManager Daemon");
/*  87 */     this.backgroundThread.setDaemon(true);
/*  88 */     this.backgroundThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/*  93 */     CronRunnable sr = new CronRunnable("* * * * *", new HelloWorld());
/*  94 */     sr.setEnabled(false);
/*  95 */     add(sr);
/*     */ 
/*     */     
/*  98 */     CronRunnable dt = new CronRunnable("25 23 * * *", new Downtime(this));
/*  99 */     dt.setEnabled(false);
/*     */     
/* 101 */     String downtimeSchedule = GlobalProperties.get("system.downtime.schedule", null);
/* 102 */     if (downtimeSchedule != null) {
/*     */       
/* 104 */       dt.setSchedule(downtimeSchedule);
/* 105 */       dt.setEnabled(true);
/*     */     } 
/* 107 */     add(dt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static boolean isDowntime() { return (getInstance()).isDowntime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDowntime(boolean isDowntime) {
/* 124 */     this.isDowntime = isDowntime;
/* 125 */     if (isDowntime) {
/* 126 */       String duration = GlobalProperties.get("system.downtime.duration", null);
/* 127 */       logger.warning("System downtime has started for [" + duration + "] seconds");
/*     */     } else {
/* 129 */       logger.warning("System downtime has finished.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static void setRunning(boolean running) { me.running = running; }
/*     */ 
/*     */   
/*     */   private void runScheduledJobs() {
/* 141 */     if (!this.running) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     Date nowDate = new Date((System.currentTimeMillis() + 5000L) / 60000L * 60000L);
/* 152 */     GregorianCalendar thisMinute = new GregorianCalendar();
/* 153 */     thisMinute.setTime(nowDate);
/*     */     
/* 155 */     Enumeration<CronRunnable> en = this.runList.elements();
/* 156 */     while (en.hasMoreElements()) {
/* 157 */       CronRunnable sr = (CronRunnable)en.nextElement();
/*     */       
/* 159 */       if (sr.isScheduledToRunNow(thisMinute)) {
/* 160 */         this.threadPool.assign(sr.getRunnable(), true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private static CronManager getInstance() { return me; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void add(String schedule, Runnable runnable) {
/* 178 */     CronRunnable sr = new CronRunnable(schedule, runnable);
/* 179 */     add(sr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static void add(CronRunnable runnable) { (getInstance()).runList.add(runnable); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static Iterator<CronRunnable> iterator() { return (getInstance()).runList.iterator(); }
/*     */   
/*     */   private class Runner implements Runnable {
/*     */     private Runner() {}
/*     */     
/*     */     public void run() {
/* 199 */       CronManager.this.init();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/*     */         try {
/* 206 */           long nextMinute = System.currentTimeMillis() / 60000L * 60000L + 60000L;
/* 207 */           long now = System.currentTimeMillis();
/* 208 */           long nextSleepTime = nextMinute - now + 10L;
/* 209 */           if (nextSleepTime > 0L)
/*     */           {
/*     */             
/* 212 */             Thread.sleep(nextSleepTime);
/*     */           }
/*     */ 
/*     */           
/* 216 */           long additionalDelay = nextMinute - System.currentTimeMillis();
/* 217 */           if (additionalDelay > 0L)
/*     */           {
/*     */             
/* 220 */             Thread.sleep(additionalDelay + 20L);
/*     */           }
/*     */ 
/*     */           
/* 224 */           boolean stopping = ShutdownManager.isStopping();
/* 225 */           if (!stopping) {
/* 226 */             CronManager.this.runScheduledJobs();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 234 */           Thread.sleep(5000L);
/*     */         }
/* 236 */         catch (InterruptedException e) {
/* 237 */           logger.log(Level.SEVERE, "", e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\cron\CronManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */