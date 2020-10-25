/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
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
/*    */ public abstract class BaseFuture<T>
/*    */   extends Object
/*    */   implements Future<T>
/*    */ {
/*    */   private final FutureTask<T> futureTask;
/*    */   
/* 40 */   public BaseFuture(FutureTask<T> futureTask) { this.futureTask = futureTask; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean cancel(boolean mayInterruptIfRunning) { return this.futureTask.cancel(mayInterruptIfRunning); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public T get() throws InterruptedException, ExecutionException { return (T)this.futureTask.get(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException { return (T)this.futureTask.get(timeout, unit); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public boolean isCancelled() { return this.futureTask.isCancelled(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isDone() { return this.futureTask.isDone(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\BaseFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */