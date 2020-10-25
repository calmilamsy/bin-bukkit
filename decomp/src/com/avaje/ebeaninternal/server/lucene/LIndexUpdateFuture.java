/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import com.avaje.ebean.config.lucene.IndexUpdateFuture;
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
/*    */ public class LIndexUpdateFuture
/*    */   extends Object
/*    */   implements Future<Integer>, IndexUpdateFuture
/*    */ {
/*    */   private final Class<?> beanType;
/*    */   private final Runnable commitRunnable;
/*    */   private final FutureTask<Void> commitFuture;
/*    */   private FutureTask<Integer> task;
/*    */   
/*    */   public LIndexUpdateFuture(Class<?> beanType) {
/* 38 */     this.beanType = beanType;
/* 39 */     this.commitRunnable = new DummyRunnable(null);
/* 40 */     this.commitFuture = new FutureTask(this.commitRunnable, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Class<?> getBeanType() { return this.beanType; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public Runnable getCommitRunnable() { return this.commitFuture; }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void setTask(FutureTask<Integer> task) { this.task = task; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isCancelled() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public boolean cancel(boolean mayInterruptIfRunning) { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer get() throws InterruptedException, ExecutionException {
/* 76 */     this.commitFuture.get();
/* 77 */     return (Integer)this.task.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 84 */     this.commitFuture.get(timeout, unit);
/* 85 */     return (Integer)this.task.get(0L, unit);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   public boolean isDone() { return this.commitFuture.isDone(); }
/*    */   
/*    */   private static class DummyRunnable implements Runnable {
/*    */     private DummyRunnable() {}
/*    */     
/* 97 */     public void run() { System.out.println("-- dummy runnable"); }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexUpdateFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */