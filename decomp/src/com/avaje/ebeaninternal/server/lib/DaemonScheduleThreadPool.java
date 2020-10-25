/*    */ package com.avaje.ebeaninternal.server.lib;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.Monitor;
/*    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public final class DaemonScheduleThreadPool
/*    */   extends ScheduledThreadPoolExecutor
/*    */ {
/* 40 */   private static final Logger logger = Logger.getLogger(DaemonScheduleThreadPool.class.getName());
/*    */   
/* 42 */   private final Monitor monitor = new Monitor();
/*    */ 
/*    */   
/*    */   private int shutdownWaitSeconds;
/*    */ 
/*    */ 
/*    */   
/*    */   public DaemonScheduleThreadPool(int coreSize, int shutdownWaitSeconds, String namePrefix) {
/* 50 */     super(coreSize, new DaemonThreadFactory(namePrefix));
/* 51 */     this.shutdownWaitSeconds = shutdownWaitSeconds;
/*    */ 
/*    */ 
/*    */     
/* 55 */     Runtime.getRuntime().addShutdownHook(new ShutdownHook(null));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void shutdown() {
/* 66 */     synchronized (this.monitor) {
/* 67 */       if (isShutdown()) {
/* 68 */         logger.fine("... DaemonScheduleThreadPool already shut down");
/*    */         return;
/*    */       } 
/*    */       try {
/* 72 */         logger.fine("DaemonScheduleThreadPool shutting down...");
/* 73 */         super.shutdown();
/* 74 */         if (!awaitTermination(this.shutdownWaitSeconds, TimeUnit.SECONDS)) {
/* 75 */           logger.info("ScheduleService shut down timeout exceeded. Terminating running threads.");
/* 76 */           shutdownNow();
/*    */         }
/*    */       
/* 79 */       } catch (Exception e) {
/* 80 */         String msg = "Error during shutdown";
/* 81 */         logger.log(Level.SEVERE, msg, e);
/* 82 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private class ShutdownHook
/*    */     extends Thread
/*    */   {
/*    */     private ShutdownHook() {}
/*    */     
/* 93 */     public void run() { DaemonScheduleThreadPool.this.shutdown(); }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\DaemonScheduleThreadPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */