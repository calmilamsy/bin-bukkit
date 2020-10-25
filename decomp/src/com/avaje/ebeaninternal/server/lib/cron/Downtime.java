/*     */ package com.avaje.ebeaninternal.server.lib.cron;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
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
/*     */ public class Downtime
/*     */   implements Runnable
/*     */ {
/*  51 */   private static final Logger logger = Logger.getLogger(Downtime.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private CronManager manager;
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Downtime(CronManager manager) { this.manager = manager; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  67 */     String downtime = GlobalProperties.get("system.downtime.duration", null);
/*  68 */     if (downtime == null) {
/*  69 */       logger.info("system.downtime not set");
/*     */     } else {
/*     */       
/*  72 */       int downTimeSecs = Integer.parseInt(downtime);
/*     */       
/*  74 */       int offsetSecs = 2;
/*     */       
/*  76 */       long offsetTime = System.currentTimeMillis() + (offsetSecs * 1000);
/*  77 */       long endTime = System.currentTimeMillis() + (downTimeSecs * 1000);
/*     */       
/*     */       try {
/*  80 */         boolean isFinished = false;
/*  81 */         if (offsetSecs > 0)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  86 */           while (!isFinished) {
/*  87 */             Thread.sleep(500L);
/*  88 */             if (System.currentTimeMillis() >= offsetTime) {
/*  89 */               isFinished = true;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  96 */         this.manager.setDowntime(true);
/*  97 */         isFinished = false;
/*     */         
/*  99 */         while (!isFinished) {
/* 100 */           Thread.sleep(500L);
/* 101 */           if (System.currentTimeMillis() >= endTime) {
/* 102 */             isFinished = true;
/*     */           }
/*     */         } 
/* 105 */       } catch (InterruptedException ex) {
/* 106 */         logger.log(Level.SEVERE, "", ex);
/*     */       } 
/*     */ 
/*     */       
/* 110 */       this.manager.setDowntime(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public String toString() { return "System Downtime"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\cron\Downtime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */