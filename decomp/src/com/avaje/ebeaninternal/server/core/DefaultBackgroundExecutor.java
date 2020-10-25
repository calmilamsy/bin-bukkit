/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebeaninternal.server.lib.DaemonScheduleThreadPool;
/*    */ import com.avaje.ebeaninternal.server.lib.DaemonThreadPool;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultBackgroundExecutor
/*    */   implements BackgroundExecutor
/*    */ {
/*    */   private final DaemonThreadPool pool;
/*    */   private final DaemonScheduleThreadPool schedulePool;
/*    */   
/*    */   public DefaultBackgroundExecutor(int mainPoolSize, int schedulePoolSize, long keepAliveSecs, int shutdownWaitSeconds, String namePrefix) {
/* 51 */     this.pool = new DaemonThreadPool(mainPoolSize, keepAliveSecs, shutdownWaitSeconds, namePrefix);
/* 52 */     this.schedulePool = new DaemonScheduleThreadPool(schedulePoolSize, shutdownWaitSeconds, namePrefix + "-periodic-");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void execute(Runnable r) { this.pool.execute(r); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public void executePeriodically(Runnable r, long delay, TimeUnit unit) { this.schedulePool.scheduleWithFixedDelay(r, delay, delay, unit); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultBackgroundExecutor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */