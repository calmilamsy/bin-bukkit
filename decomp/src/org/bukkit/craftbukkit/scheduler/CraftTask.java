/*     */ package org.bukkit.craftbukkit.scheduler;
/*     */ 
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ 
/*     */ public class CraftTask
/*     */   extends Object
/*     */   implements Comparable<Object>, BukkitTask
/*     */ {
/*     */   private final Runnable task;
/*     */   private final boolean syncTask;
/*     */   private long executionTick;
/*     */   private final long period;
/*     */   private final Plugin owner;
/*     */   private final int idNumber;
/*  17 */   private static Integer idCounter = Integer.valueOf(1);
/*  18 */   private static Object idCounterSync = new Object();
/*     */ 
/*     */   
/*  21 */   CraftTask(Plugin owner, Runnable task, boolean syncTask) { this(owner, task, syncTask, -1L, -1L); }
/*     */ 
/*     */ 
/*     */   
/*  25 */   CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick) { this(owner, task, syncTask, executionTick, -1L); }
/*     */ 
/*     */   
/*     */   CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick, long period) {
/*  29 */     this.task = task;
/*  30 */     this.syncTask = syncTask;
/*  31 */     this.executionTick = executionTick;
/*  32 */     this.period = period;
/*  33 */     this.owner = owner;
/*  34 */     this.idNumber = getNextId();
/*     */   }
/*     */   
/*     */   static int getNextId() {
/*  38 */     synchronized (idCounterSync) {
/*  39 */       Integer integer1, integer2 = idCounter = (integer1 = idCounter).valueOf(idCounter.intValue() + 1); integer1;
/*  40 */       return idCounter.intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  45 */   Runnable getTask() { return this.task; }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public boolean isSync() { return this.syncTask; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   long getExecutionTick() { return this.executionTick; }
/*     */ 
/*     */ 
/*     */   
/*  57 */   long getPeriod() { return this.period; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public Plugin getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   void updateExecution() { this.executionTick += this.period; }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public int getTaskId() { return getIdNumber(); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   int getIdNumber() { return this.idNumber; }
/*     */ 
/*     */   
/*     */   public int compareTo(Object other) {
/*  77 */     if (!(other instanceof CraftTask)) {
/*  78 */       return 0;
/*     */     }
/*  80 */     CraftTask o = (CraftTask)other;
/*  81 */     long timeDiff = this.executionTick - o.getExecutionTick();
/*  82 */     if (timeDiff > 0L)
/*  83 */       return 1; 
/*  84 */     if (timeDiff < 0L) {
/*  85 */       return -1;
/*     */     }
/*  87 */     CraftTask otherCraftTask = (CraftTask)other;
/*  88 */     return getIdNumber() - otherCraftTask.getIdNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  96 */     if (other == null) {
/*  97 */       return false;
/*     */     }
/*     */     
/* 100 */     if (!(other instanceof CraftTask)) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     CraftTask otherCraftTask = (CraftTask)other;
/* 105 */     return (otherCraftTask.getIdNumber() == getIdNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int hashCode() { return getIdNumber(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\CraftTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */