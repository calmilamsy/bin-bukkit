/*    */ package org.bukkit.craftbukkit.scheduler;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitWorker;
/*    */ 
/*    */ public class CraftWorker
/*    */   implements Runnable, BukkitWorker {
/*  8 */   private static int hashIdCounter = 1;
/*  9 */   private static Object hashIdCounterSync = new Object();
/*    */   
/*    */   private final int hashId;
/*    */   
/*    */   private final Plugin owner;
/*    */   
/*    */   private final int taskId;
/*    */   
/*    */   private final Thread t;
/*    */   private final CraftThreadManager parent;
/*    */   private final Runnable task;
/*    */   
/*    */   CraftWorker(CraftThreadManager parent, Runnable task, Plugin owner, int taskId) {
/* 22 */     this.parent = parent;
/* 23 */     this.taskId = taskId;
/* 24 */     this.task = task;
/* 25 */     this.owner = owner;
/* 26 */     this.hashId = getNextHashId();
/* 27 */     this.t = new Thread(this);
/* 28 */     this.t.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 34 */       this.task.run();
/* 35 */     } catch (Exception e) {
/* 36 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 39 */     synchronized (this.parent.workers) {
/* 40 */       this.parent.workers.remove(this);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public int getTaskId() { return this.taskId; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Plugin getOwner() { return this.owner; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Thread getThread() { return this.t; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void interrupt() { this.t.interrupt(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isAlive() { return this.t.isAlive(); }
/*    */ 
/*    */   
/*    */   private static int getNextHashId() {
/* 66 */     synchronized (hashIdCounterSync) {
/* 67 */       return hashIdCounter++;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public int hashCode() { return this.hashId; }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 78 */     if (other == null) {
/* 79 */       return false;
/*    */     }
/*    */     
/* 82 */     if (!(other instanceof CraftWorker)) {
/* 83 */       return false;
/*    */     }
/*    */     
/* 86 */     CraftWorker otherCraftWorker = (CraftWorker)other;
/* 87 */     return (otherCraftWorker.hashCode() == this.hashId);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\CraftWorker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */