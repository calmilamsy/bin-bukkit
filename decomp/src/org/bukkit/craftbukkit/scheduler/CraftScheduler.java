/*     */ package org.bukkit.craftbukkit.scheduler;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.scheduler.BukkitWorker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftScheduler
/*     */   implements BukkitScheduler, Runnable
/*     */ {
/*  27 */   private static final Logger logger = Logger.getLogger("Minecraft");
/*     */   
/*     */   private final CraftServer server;
/*     */   
/*     */   private final CraftThreadManager craftThreadManager;
/*     */   
/*     */   private final LinkedList<CraftTask> mainThreadQueue;
/*     */   
/*     */   private final LinkedList<CraftTask> syncedTasks;
/*     */   
/*     */   private final TreeMap<CraftTask, Boolean> schedulerQueue;
/*     */   
/*     */   private final Object currentTickSync;
/*     */   
/*     */   private Long currentTick;
/*     */   
/*     */   private final Lock mainThreadLock;
/*     */   private final Lock syncedTasksLock;
/*     */   
/*     */   public void run() {
/*     */     while (true) {
/*  48 */       boolean stop = false;
/*  49 */       long firstTick = -1L;
/*  50 */       long currentTick = -1L;
/*  51 */       CraftTask first = null;
/*     */       do {
/*  53 */         synchronized (this.schedulerQueue) {
/*  54 */           first = null;
/*  55 */           if (!this.schedulerQueue.isEmpty()) {
/*  56 */             first = (CraftTask)this.schedulerQueue.firstKey();
/*  57 */             if (first != null) {
/*  58 */               currentTick = getCurrentTick();
/*     */               
/*  60 */               firstTick = first.getExecutionTick();
/*     */               
/*  62 */               if (currentTick >= firstTick) {
/*  63 */                 this.schedulerQueue.remove(first);
/*  64 */                 processTask(first);
/*  65 */                 if (first.getPeriod() >= 0L) {
/*  66 */                   first.updateExecution();
/*  67 */                   this.schedulerQueue.put(first, Boolean.valueOf(first.isSync()));
/*     */                 } 
/*     */               } else {
/*  70 */                 stop = true;
/*     */               } 
/*     */             } else {
/*  73 */               stop = true;
/*     */             } 
/*     */           } else {
/*  76 */             stop = true;
/*     */           } 
/*     */         } 
/*  79 */       } while (!stop);
/*     */       
/*  81 */       long sleepTime = 0L;
/*  82 */       if (first == null) {
/*  83 */         sleepTime = 60000L;
/*     */       } else {
/*  85 */         currentTick = getCurrentTick();
/*  86 */         sleepTime = (firstTick - currentTick) * 50L + 25L;
/*     */       } 
/*     */       
/*  89 */       if (sleepTime < 50L) {
/*  90 */         sleepTime = 50L;
/*  91 */       } else if (sleepTime > 60000L) {
/*  92 */         sleepTime = 60000L;
/*     */       } 
/*     */       
/*  95 */       synchronized (this.schedulerQueue) {
/*     */         try {
/*  97 */           this.schedulerQueue.wait(sleepTime);
/*  98 */         } catch (InterruptedException ie) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void processTask(CraftTask task) {
/* 104 */     if (task.isSync()) {
/* 105 */       addToMainThreadQueue(task);
/*     */     } else {
/* 107 */       this.craftThreadManager.executeTask(task.getTask(), task.getOwner(), task.getIdNumber());
/*     */     }  } public CraftScheduler(CraftServer server) { this.craftThreadManager = new CraftThreadManager(); this.mainThreadQueue = new LinkedList(); this.syncedTasks = new LinkedList(); this.schedulerQueue = new TreeMap(); this.currentTickSync = new Object();
/*     */     this.currentTick = Long.valueOf(0L);
/*     */     this.mainThreadLock = new ReentrantLock();
/*     */     this.syncedTasksLock = new ReentrantLock();
/* 112 */     this.server = server;
/*     */     
/* 114 */     Thread t = new Thread(this);
/* 115 */     t.start(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mainThreadHeartbeat(long currentTick) {
/* 121 */     if (this.syncedTasksLock.tryLock()) {
/*     */       try {
/* 123 */         if (this.mainThreadLock.tryLock()) {
/*     */           try {
/* 125 */             this.currentTick = Long.valueOf(currentTick);
/* 126 */             while (!this.mainThreadQueue.isEmpty()) {
/* 127 */               this.syncedTasks.addLast(this.mainThreadQueue.removeFirst());
/*     */             }
/*     */           } finally {
/* 130 */             this.mainThreadLock.unlock();
/*     */           } 
/*     */         }
/* 133 */         long breakTime = System.currentTimeMillis() + 35L;
/* 134 */         while (!this.syncedTasks.isEmpty() && System.currentTimeMillis() <= breakTime) {
/* 135 */           CraftTask task = (CraftTask)this.syncedTasks.removeFirst();
/*     */           try {
/* 137 */             task.getTask().run();
/* 138 */           } catch (Throwable t) {
/*     */             
/* 140 */             logger.log(Level.WARNING, "Task of '" + task.getOwner().getDescription().getName() + "' generated an exception", t);
/* 141 */             synchronized (this.schedulerQueue) {
/* 142 */               this.schedulerQueue.remove(task);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 147 */         this.syncedTasksLock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   long getCurrentTick() {
/* 153 */     this.mainThreadLock.lock();
/* 154 */     long tempTick = 0L;
/*     */     try {
/* 156 */       tempTick = this.currentTick.longValue();
/*     */     } finally {
/* 158 */       this.mainThreadLock.unlock();
/*     */     } 
/* 160 */     return tempTick;
/*     */   }
/*     */   
/*     */   void addToMainThreadQueue(CraftTask task) {
/* 164 */     this.mainThreadLock.lock();
/*     */     try {
/* 166 */       this.mainThreadQueue.addLast(task);
/*     */     } finally {
/* 168 */       this.mainThreadLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void wipeSyncedTasks() {
/* 173 */     this.syncedTasksLock.lock();
/*     */     try {
/* 175 */       this.syncedTasks.clear();
/*     */     } finally {
/* 177 */       this.syncedTasksLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void wipeMainThreadQueue() {
/* 182 */     this.mainThreadLock.lock();
/*     */     try {
/* 184 */       this.mainThreadQueue.clear();
/*     */     } finally {
/* 186 */       this.mainThreadLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 191 */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) { return scheduleSyncRepeatingTask(plugin, task, delay, -1L); }
/*     */ 
/*     */ 
/*     */   
/* 195 */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) { return scheduleSyncDelayedTask(plugin, task, 0L); }
/*     */ 
/*     */   
/*     */   public int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
/* 199 */     if (plugin == null) {
/* 200 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 202 */     if (task == null) {
/* 203 */       throw new IllegalArgumentException("Task cannot be null");
/*     */     }
/* 205 */     if (delay < 0L) {
/* 206 */       throw new IllegalArgumentException("Delay cannot be less than 0");
/*     */     }
/*     */     
/* 209 */     CraftTask newTask = new CraftTask(plugin, task, true, getCurrentTick() + delay, period);
/*     */     
/* 211 */     synchronized (this.schedulerQueue) {
/* 212 */       this.schedulerQueue.put(newTask, Boolean.valueOf(true));
/* 213 */       this.schedulerQueue.notify();
/*     */     } 
/* 215 */     return newTask.getIdNumber();
/*     */   }
/*     */ 
/*     */   
/* 219 */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) { return scheduleAsyncRepeatingTask(plugin, task, delay, -1L); }
/*     */ 
/*     */ 
/*     */   
/* 223 */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) { return scheduleAsyncDelayedTask(plugin, task, 0L); }
/*     */ 
/*     */   
/*     */   public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
/* 227 */     if (plugin == null) {
/* 228 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 230 */     if (task == null) {
/* 231 */       throw new IllegalArgumentException("Task cannot be null");
/*     */     }
/* 233 */     if (delay < 0L) {
/* 234 */       throw new IllegalArgumentException("Delay cannot be less than 0");
/*     */     }
/*     */     
/* 237 */     CraftTask newTask = new CraftTask(plugin, task, false, getCurrentTick() + delay, period);
/*     */     
/* 239 */     synchronized (this.schedulerQueue) {
/* 240 */       this.schedulerQueue.put(newTask, Boolean.valueOf(false));
/* 241 */       this.schedulerQueue.notify();
/*     */     } 
/* 243 */     return newTask.getIdNumber();
/*     */   }
/*     */   
/*     */   public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
/* 247 */     CraftFuture<T> craftFuture = new CraftFuture<T>(this, task);
/* 248 */     synchronized (craftFuture) {
/* 249 */       int taskId = scheduleSyncDelayedTask(plugin, craftFuture);
/* 250 */       craftFuture.setTaskId(taskId);
/*     */     } 
/* 252 */     return craftFuture;
/*     */   }
/*     */   
/*     */   public void cancelTask(int taskId) {
/* 256 */     this.syncedTasksLock.lock();
/*     */     try {
/* 258 */       synchronized (this.schedulerQueue) {
/* 259 */         this.mainThreadLock.lock();
/*     */         try {
/* 261 */           Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
/* 262 */           while (itr.hasNext()) {
/* 263 */             CraftTask current = (CraftTask)itr.next();
/* 264 */             if (current.getIdNumber() == taskId) {
/* 265 */               itr.remove();
/*     */             }
/*     */           } 
/* 268 */           itr = this.mainThreadQueue.iterator();
/* 269 */           while (itr.hasNext()) {
/* 270 */             CraftTask current = (CraftTask)itr.next();
/* 271 */             if (current.getIdNumber() == taskId) {
/* 272 */               itr.remove();
/*     */             }
/*     */           } 
/* 275 */           itr = this.syncedTasks.iterator();
/* 276 */           while (itr.hasNext()) {
/* 277 */             CraftTask current = (CraftTask)itr.next();
/* 278 */             if (current.getIdNumber() == taskId) {
/* 279 */               itr.remove();
/*     */             }
/*     */           } 
/*     */         } finally {
/* 283 */           this.mainThreadLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 287 */       this.syncedTasksLock.unlock();
/*     */     } 
/*     */     
/* 290 */     this.craftThreadManager.interruptTask(taskId);
/*     */   }
/*     */   
/*     */   public void cancelTasks(Plugin plugin) {
/* 294 */     this.syncedTasksLock.lock();
/*     */     try {
/* 296 */       synchronized (this.schedulerQueue) {
/* 297 */         this.mainThreadLock.lock();
/*     */         try {
/* 299 */           Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
/* 300 */           while (itr.hasNext()) {
/* 301 */             CraftTask current = (CraftTask)itr.next();
/* 302 */             if (current.getOwner().equals(plugin)) {
/* 303 */               itr.remove();
/*     */             }
/*     */           } 
/* 306 */           itr = this.mainThreadQueue.iterator();
/* 307 */           while (itr.hasNext()) {
/* 308 */             CraftTask current = (CraftTask)itr.next();
/* 309 */             if (current.getOwner().equals(plugin)) {
/* 310 */               itr.remove();
/*     */             }
/*     */           } 
/* 313 */           itr = this.syncedTasks.iterator();
/* 314 */           while (itr.hasNext()) {
/* 315 */             CraftTask current = (CraftTask)itr.next();
/* 316 */             if (current.getOwner().equals(plugin)) {
/* 317 */               itr.remove();
/*     */             }
/*     */           } 
/*     */         } finally {
/* 321 */           this.mainThreadLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 325 */       this.syncedTasksLock.unlock();
/*     */     } 
/*     */     
/* 328 */     this.craftThreadManager.interruptTasks(plugin);
/*     */   }
/*     */   
/*     */   public void cancelAllTasks() {
/* 332 */     synchronized (this.schedulerQueue) {
/* 333 */       this.schedulerQueue.clear();
/*     */     } 
/* 335 */     wipeMainThreadQueue();
/* 336 */     wipeSyncedTasks();
/*     */     
/* 338 */     this.craftThreadManager.interruptAllTasks();
/*     */   }
/*     */ 
/*     */   
/* 342 */   public boolean isCurrentlyRunning(int taskId) { return this.craftThreadManager.isAlive(taskId); }
/*     */ 
/*     */   
/*     */   public boolean isQueued(int taskId) {
/* 346 */     synchronized (this.schedulerQueue) {
/* 347 */       Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
/* 348 */       while (itr.hasNext()) {
/* 349 */         CraftTask current = (CraftTask)itr.next();
/* 350 */         if (current.getIdNumber() == taskId) {
/* 351 */           return true;
/*     */         }
/*     */       } 
/* 354 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<BukkitWorker> getActiveWorkers() {
/* 359 */     synchronized (this.craftThreadManager.workers) {
/* 360 */       List<BukkitWorker> workerList = new ArrayList<BukkitWorker>(this.craftThreadManager.workers.size());
/* 361 */       Iterator<CraftWorker> itr = this.craftThreadManager.workers.iterator();
/*     */       
/* 363 */       while (itr.hasNext()) {
/* 364 */         workerList.add((BukkitWorker)itr.next());
/*     */       }
/* 366 */       return workerList;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<BukkitTask> getPendingTasks() {
/* 371 */     List<CraftTask> taskList = null;
/* 372 */     this.syncedTasksLock.lock();
/*     */     try {
/* 374 */       synchronized (this.schedulerQueue) {
/* 375 */         this.mainThreadLock.lock();
/*     */         try {
/* 377 */           taskList = new ArrayList<CraftTask>(this.mainThreadQueue.size() + this.syncedTasks.size() + this.schedulerQueue.size());
/* 378 */           taskList.addAll(this.mainThreadQueue);
/* 379 */           taskList.addAll(this.syncedTasks);
/* 380 */           taskList.addAll(this.schedulerQueue.keySet());
/*     */         } finally {
/* 382 */           this.mainThreadLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 386 */       this.syncedTasksLock.unlock();
/*     */     } 
/* 388 */     List<BukkitTask> newTaskList = new ArrayList<BukkitTask>(taskList.size());
/*     */     
/* 390 */     for (CraftTask craftTask : taskList) {
/* 391 */       newTaskList.add(craftTask);
/*     */     }
/* 393 */     return newTaskList;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\CraftScheduler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */