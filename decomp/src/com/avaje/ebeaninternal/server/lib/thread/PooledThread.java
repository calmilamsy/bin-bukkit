/*     */ package com.avaje.ebeaninternal.server.lib.thread;
/*     */ 
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
/*     */ public class PooledThread
/*     */   implements Runnable
/*     */ {
/*  29 */   private static final Logger logger = Logger.getLogger(PooledThread.class.getName());
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
/*     */   private boolean wasInterrupted;
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
/*     */   private long lastUsedTime;
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
/*     */   private Work work;
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
/*     */   private boolean isStopping;
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
/*     */   private boolean isStopped;
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
/*     */   private Thread thread;
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
/*     */   private ThreadPool threadPool;
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
/*     */   private String name;
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
/*     */   private Object threadMonitor;
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
/*     */   private Object workMonitor;
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
/*     */   private int totalWorkCount;
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
/*     */   private long totalWorkExecutionTime;
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
/*     */   protected PooledThread(ThreadPool threadPool, String name, boolean isDaemon, Integer threadPriority) {
/* 220 */     this.wasInterrupted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     this.work = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.isStopping = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.isStopped = false;
/*     */ 
/*     */ 
/*     */     
/* 244 */     this.thread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     this.name = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     this.threadMonitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     this.workMonitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     this.totalWorkCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     this.totalWorkExecutionTime = 0L;
/*     */     this.name = name;
/*     */     this.threadPool = threadPool;
/*     */     this.lastUsedTime = System.currentTimeMillis();
/*     */     this.thread = new Thread(this, name);
/*     */     this.thread.setDaemon(isDaemon);
/*     */     if (threadPriority != null)
/*     */       this.thread.setPriority(threadPriority.intValue()); 
/*     */   }
/*     */   
/*     */   protected void start() { this.thread.start(); }
/*     */   
/*     */   public boolean assignWork(Work work) {
/*     */     synchronized (this.workMonitor) {
/*     */       this.work = work;
/*     */       this.workMonitor.notifyAll();
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     synchronized (this.workMonitor) {
/*     */       while (!this.isStopping) {
/*     */         try {
/*     */           if (this.work == null)
/*     */             this.workMonitor.wait(); 
/*     */         } catch (InterruptedException e) {}
/*     */         doTheWork();
/*     */       } 
/*     */     } 
/*     */     synchronized (this.threadMonitor) {
/*     */       this.threadMonitor.notifyAll();
/*     */     } 
/*     */     this.isStopped = true;
/*     */   }
/*     */   
/*     */   private void doTheWork() {
/*     */     if (this.isStopping)
/*     */       return; 
/*     */     long startTime = System.currentTimeMillis();
/*     */     if (this.work != null)
/*     */       try {
/*     */         this.work.setStartTime(startTime);
/*     */         this.work.getRunnable().run();
/*     */       } catch (Throwable ex) {
/*     */         logger.log(Level.SEVERE, null, ex);
/*     */         if (this.wasInterrupted) {
/*     */           this.isStopping = true;
/*     */           this.threadPool.removeThread(this);
/*     */           logger.info("PooledThread [" + this.name + "] removed due to interrupt");
/*     */           try {
/*     */             this.thread.interrupt();
/*     */           } catch (Exception e) {
/*     */             String msg = "Error interrupting PooledThead[" + this.name + "]";
/*     */             logger.log(Level.SEVERE, msg, e);
/*     */           } 
/*     */           return;
/*     */         } 
/*     */       }  
/*     */     this.lastUsedTime = System.currentTimeMillis();
/*     */     this.totalWorkCount++;
/*     */     this.totalWorkExecutionTime = this.totalWorkExecutionTime + this.lastUsedTime - startTime;
/*     */     this.work = null;
/*     */     this.threadPool.returnThread(this);
/*     */   }
/*     */   
/*     */   public void interrupt() {
/*     */     this.wasInterrupted = true;
/*     */     try {
/*     */       this.thread.interrupt();
/*     */     } catch (SecurityException ex) {
/*     */       this.wasInterrupted = false;
/*     */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStopped() { return this.isStopped; }
/*     */   
/*     */   protected void stop() {
/*     */     this.isStopping = true;
/*     */     synchronized (this.threadMonitor) {
/*     */       assignWork(null);
/*     */       try {
/*     */         this.threadMonitor.wait(10000L);
/*     */       } catch (InterruptedException e) {}
/*     */     } 
/*     */     this.thread = null;
/*     */     this.threadPool.removeThread(this);
/*     */   }
/*     */   
/*     */   public String getName() { return this.name; }
/*     */   
/*     */   public Work getWork() { return this.work; }
/*     */   
/*     */   public int getTotalWorkCount() { return this.totalWorkCount; }
/*     */   
/*     */   public long getTotalWorkExecutionTime() { return this.totalWorkExecutionTime; }
/*     */   
/*     */   public long getLastUsedTime() { return this.lastUsedTime; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\thread\PooledThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */