/*     */ package org.bukkit.craftbukkit.scheduler;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ public class CraftFuture<T> extends Object implements Runnable, Future<T> {
/*     */   private final CraftScheduler craftScheduler;
/*     */   private final Callable<T> callable;
/*     */   private final ObjectContainer<T> returnStore;
/*     */   private boolean done;
/*     */   
/*     */   CraftFuture(CraftScheduler craftScheduler, Callable callable) {
/*  14 */     this.returnStore = new ObjectContainer();
/*  15 */     this.done = false;
/*  16 */     this.running = false;
/*  17 */     this.cancelled = false;
/*  18 */     this.e = null;
/*  19 */     this.taskId = -1;
/*     */ 
/*     */     
/*  22 */     this.callable = callable;
/*  23 */     this.craftScheduler = craftScheduler;
/*     */   }
/*     */   private boolean running; private boolean cancelled; private Exception e; private int taskId;
/*     */   public void run() {
/*  27 */     synchronized (this) {
/*  28 */       if (this.cancelled) {
/*     */         return;
/*     */       }
/*  31 */       this.running = true;
/*     */     } 
/*     */     try {
/*  34 */       this.returnStore.setObject(this.callable.call());
/*  35 */     } catch (Exception e) {
/*  36 */       this.e = e;
/*     */     } 
/*  38 */     synchronized (this) {
/*  39 */       this.running = false;
/*  40 */       this.done = true;
/*  41 */       notify();
/*     */     } 
/*     */   }
/*     */   
/*     */   public T get() throws InterruptedException, ExecutionException {
/*     */     try {
/*  47 */       return (T)get(0L, TimeUnit.MILLISECONDS);
/*  48 */     } catch (TimeoutException te) {
/*  49 */       return null;
/*     */     } 
/*     */   }
/*     */   public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  53 */     synchronized (this) {
/*  54 */       if (isDone()) {
/*  55 */         return (T)getResult();
/*     */       }
/*  57 */       wait(TimeUnit.MILLISECONDS.convert(timeout, unit));
/*  58 */       return (T)getResult();
/*     */     } 
/*     */   }
/*     */   
/*     */   public T getResult() throws InterruptedException, ExecutionException {
/*  63 */     if (this.cancelled) {
/*  64 */       throw new CancellationException();
/*     */     }
/*  66 */     if (this.e != null) {
/*  67 */       throw new ExecutionException(this.e);
/*     */     }
/*  69 */     return (T)this.returnStore.getObject();
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  73 */     synchronized (this) {
/*  74 */       return this.done;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  79 */     synchronized (this) {
/*  80 */       return this.cancelled;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/*  85 */     synchronized (this) {
/*  86 */       if (this.cancelled) {
/*  87 */         return false;
/*     */       }
/*  89 */       this.cancelled = true;
/*  90 */       if (this.taskId != -1) {
/*  91 */         this.craftScheduler.cancelTask(this.taskId);
/*     */       }
/*  93 */       if (!this.running && !this.done) {
/*  94 */         return true;
/*     */       }
/*  96 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTaskId(int taskId) {
/* 102 */     synchronized (this) {
/* 103 */       this.taskId = taskId;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\CraftFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */