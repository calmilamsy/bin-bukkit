/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebeaninternal.server.lib.DaemonScheduleThreadPool;
/*    */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TraditionalBackgroundExecutor
/*    */   implements BackgroundExecutor
/*    */ {
/*    */   private final ThreadPool pool;
/*    */   private final DaemonScheduleThreadPool schedulePool;
/*    */   
/*    */   public TraditionalBackgroundExecutor(ThreadPool pool, int schedulePoolSize, int shutdownWaitSeconds, String namePrefix) {
/* 43 */     this.pool = pool;
/* 44 */     this.schedulePool = new DaemonScheduleThreadPool(schedulePoolSize, shutdownWaitSeconds, namePrefix + "-periodic-");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public void execute(Runnable r) { this.pool.assign(r, true); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void executePeriodically(Runnable r, long delay, TimeUnit unit) { this.schedulePool.scheduleWithFixedDelay(r, delay, delay, unit); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\TraditionalBackgroundExecutor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */