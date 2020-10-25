/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BackgroundThread
/*     */ {
/*     */   private Vector<BackgroundRunnable> list;
/*     */   private final Object monitor;
/*     */   private final Thread thread;
/*     */   private long sleepTime;
/*     */   private long count;
/*     */   private long exeTime;
/*     */   private boolean stopped;
/*     */   private Object threadMonitor;
/*  46 */   private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
/*     */   
/*     */   private static class Single {
/*  49 */     private static BackgroundThread me = new BackgroundThread(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BackgroundThread() {
/*  55 */     this.list = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.sleepTime = 1000L;
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
/*  90 */     this.threadMonitor = new Object();
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.thread = new Thread(new Runner(this, null), "EbeanBackgroundThread");
/*  95 */     this.thread.setDaemon(true);
/*  96 */     this.thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static void add(int freqInSecs, Runnable runnable) { add(new BackgroundRunnable(runnable, freqInSecs)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public static void add(BackgroundRunnable backgroundRunnable) { me.addTask(backgroundRunnable); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static void shutdown() { me.stop(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator<BackgroundRunnable> runnables() {
/* 124 */     synchronized (me.monitor) {
/* 125 */       return me.list.iterator();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addTask(BackgroundRunnable backgroundRunnable) {
/* 130 */     synchronized (this.monitor) {
/* 131 */       this.list.add(backgroundRunnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void stop() {
/* 141 */     this.stopped = true;
/* 142 */     synchronized (this.threadMonitor) {
/*     */       try {
/* 144 */         this.threadMonitor.wait(10000L);
/* 145 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Runner
/*     */     implements Runnable
/*     */   {
/*     */     private Runner() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 159 */       if (ShutdownManager.isStopping()) {
/*     */         return;
/*     */       }
/*     */       
/* 163 */       while (!BackgroundThread.this.stopped) {
/*     */         
/*     */         try {
/* 166 */           long actualSleep = BackgroundThread.this.sleepTime - BackgroundThread.this.exeTime;
/* 167 */           if (actualSleep < 0L) {
/* 168 */             actualSleep = BackgroundThread.this.sleepTime;
/*     */           }
/* 170 */           Thread.sleep(actualSleep);
/* 171 */           synchronized (BackgroundThread.this.monitor) {
/* 172 */             runJobs();
/*     */           }
/*     */         
/* 175 */         } catch (InterruptedException e) {
/* 176 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 181 */       synchronized (BackgroundThread.this.threadMonitor) {
/* 182 */         BackgroundThread.this.threadMonitor.notifyAll();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void runJobs() {
/* 188 */       long startTime = System.currentTimeMillis();
/*     */ 
/*     */       
/* 191 */       Iterator<BackgroundRunnable> it = BackgroundThread.this.list.iterator();
/* 192 */       while (it.hasNext()) {
/* 193 */         BackgroundRunnable bgr = (BackgroundRunnable)it.next();
/* 194 */         if (bgr.isActive()) {
/*     */           
/* 196 */           int freqInSecs = bgr.getFreqInSecs();
/*     */           
/* 198 */           if (BackgroundThread.this.count % freqInSecs == 0L) {
/* 199 */             Runnable runable = bgr.getRunnable();
/* 200 */             if (bgr.runNow(startTime)) {
/* 201 */               bgr.runStart();
/* 202 */               if (logger.isLoggable(Level.FINER)) {
/* 203 */                 String msg = BackgroundThread.this.count + " BGRunnable running [" + runable.getClass().getName() + "]";
/*     */                 
/* 205 */                 logger.finer(msg);
/*     */               } 
/*     */               
/* 208 */               runable.run();
/* 209 */               bgr.runEnd();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 214 */       BackgroundThread.this.exeTime = System.currentTimeMillis() - startTime;
/* 215 */       BackgroundThread.this.count++;
/*     */       
/* 217 */       if (BackgroundThread.this.count == 86400L)
/*     */       {
/* 219 */         BackgroundThread.this.count = 0L;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 225 */     synchronized (this.monitor) {
/* 226 */       StringBuffer sb = new StringBuffer();
/*     */       
/* 228 */       Iterator<BackgroundRunnable> it = runnables();
/* 229 */       while (it.hasNext()) {
/* 230 */         BackgroundRunnable bgr = (BackgroundRunnable)it.next();
/* 231 */         sb.append(bgr);
/*     */       } 
/*     */       
/* 234 */       return sb.toString();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\BackgroundThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */