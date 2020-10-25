/*     */ package com.avaje.ebeaninternal.server.lib.thread;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ThreadPoolManager
/*     */   implements Runnable
/*     */ {
/*     */   private static final class Single
/*     */   {
/*  32 */     private static final ThreadPoolManager me = new ThreadPoolManager(null);
/*     */   }
/*     */   
/*  35 */   private static int debugLevel = 0;
/*     */   
/*     */   private boolean isShuttingDown;
/*     */   
/*     */   private ThreadPoolManager() {
/*  40 */     this.isShuttingDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     this.threadPoolCache = new ConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     initialise();
/*     */   }
/*     */   private ConcurrentHashMap<String, ThreadPool> threadPoolCache; private long defaultIdleTime;
/*     */   
/*     */   private void initialise() {
/*  60 */     debugLevel = GlobalProperties.getInt("threadpool.debugLevel", 0);
/*     */     
/*  62 */     this.defaultIdleTime = (1000 * GlobalProperties.getInt("threadpool.idletime", 60));
/*     */     
/*  64 */     int freqIsSecs = GlobalProperties.getInt("threadpool.sleeptime", 30);
/*     */     
/*  66 */     BackgroundThread.add(freqIsSecs, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static void setDebugLevel(int level) { debugLevel = level; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static int getDebugLevel() { return debugLevel; }
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
/*     */   public void run() {
/*  94 */     if (!this.isShuttingDown) {
/*  95 */       maintainPoolSize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static ThreadPool getThreadPool(String poolName) { return me.getPool(poolName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadPool getPool(String poolName) {
/* 111 */     synchronized (this) {
/* 112 */       ThreadPool threadPool = (ThreadPool)this.threadPoolCache.get(poolName);
/* 113 */       if (threadPool == null) {
/* 114 */         threadPool = createThreadPool(poolName);
/* 115 */         this.threadPoolCache.put(poolName, threadPool);
/*     */       } 
/* 117 */       return threadPool;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static Iterator<ThreadPool> pools() { return me.threadPoolCache.values().iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maintainPoolSize() {
/* 137 */     if (this.isShuttingDown) {
/*     */       return;
/*     */     }
/* 140 */     synchronized (this) {
/*     */       
/* 142 */       Iterator<ThreadPool> e = pools();
/* 143 */       while (e.hasNext()) {
/* 144 */         ThreadPool pool = (ThreadPool)e.next();
/* 145 */         pool.maintainPoolSize();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static void shutdown() { me.shutdownPools(); }
/*     */ 
/*     */   
/*     */   private void shutdownPools() {
/* 159 */     synchronized (this) {
/* 160 */       this.isShuttingDown = true;
/* 161 */       Iterator<ThreadPool> i = pools();
/* 162 */       while (i.hasNext()) {
/* 163 */         ThreadPool pool = (ThreadPool)i.next();
/* 164 */         pool.shutdown();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ThreadPool createThreadPool(String poolName) {
/* 171 */     int min = GlobalProperties.getInt("threadpool." + poolName + ".min", 0);
/* 172 */     int max = GlobalProperties.getInt("threadpool." + poolName + ".max", 100);
/*     */     
/* 174 */     long idle = (1000 * GlobalProperties.getInt("threadpool." + poolName + ".idletime", -1));
/* 175 */     if (idle < 0L) {
/* 176 */       idle = this.defaultIdleTime;
/*     */     }
/*     */     
/* 179 */     boolean isDaemon = true;
/* 180 */     Integer priority = null;
/* 181 */     String threadPriority = GlobalProperties.get("threadpool." + poolName + ".priority", null);
/* 182 */     if (threadPriority != null) {
/* 183 */       priority = new Integer(threadPriority);
/*     */     }
/*     */     
/* 186 */     ThreadPool newPool = new ThreadPool(poolName, isDaemon, priority);
/* 187 */     newPool.setMaxSize(max);
/* 188 */     newPool.setMinSize(min);
/* 189 */     newPool.setMaxIdleTime(idle);
/*     */     
/* 191 */     return newPool;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\thread\ThreadPoolManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */