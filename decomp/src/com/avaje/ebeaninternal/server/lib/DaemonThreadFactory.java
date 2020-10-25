/*    */ package com.avaje.ebeaninternal.server.lib;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
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
/*    */ public class DaemonThreadFactory
/*    */   implements ThreadFactory
/*    */ {
/* 42 */   private static final AtomicInteger poolNumber = new AtomicInteger(true);
/*    */   
/*    */   private final ThreadGroup group;
/*    */   
/* 46 */   private final AtomicInteger threadNumber = new AtomicInteger(true);
/*    */   
/*    */   private final String namePrefix;
/*    */   
/*    */   public DaemonThreadFactory(String namePrefix) {
/* 51 */     SecurityManager s = System.getSecurityManager();
/* 52 */     this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 53 */     this.namePrefix = (namePrefix != null) ? namePrefix : ("pool-" + poolNumber.getAndIncrement() + "-thread-");
/*    */   }
/*    */ 
/*    */   
/*    */   public Thread newThread(Runnable r) {
/* 58 */     Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
/*    */     
/* 60 */     t.setDaemon(true);
/*    */     
/* 62 */     if (t.getPriority() != 5) {
/* 63 */       t.setPriority(5);
/*    */     }
/*    */     
/* 66 */     return t;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\DaemonThreadFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */