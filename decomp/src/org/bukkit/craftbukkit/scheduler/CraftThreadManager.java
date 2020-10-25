/*    */ package org.bukkit.craftbukkit.scheduler;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class CraftThreadManager
/*    */ {
/* 10 */   final HashSet<CraftWorker> workers = new HashSet();
/*    */ 
/*    */   
/*    */   void executeTask(Runnable task, Plugin owner, int taskId) {
/* 14 */     CraftWorker craftWorker = new CraftWorker(this, task, owner, taskId);
/* 15 */     synchronized (this.workers) {
/* 16 */       this.workers.add(craftWorker);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   void interruptTask(int taskId) {
/* 22 */     synchronized (this.workers) {
/* 23 */       Iterator<CraftWorker> itr = this.workers.iterator();
/* 24 */       while (itr.hasNext()) {
/* 25 */         CraftWorker craftWorker = (CraftWorker)itr.next();
/* 26 */         if (craftWorker.getTaskId() == taskId) {
/* 27 */           craftWorker.interrupt();
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   void interruptTasks(Plugin owner) {
/* 34 */     synchronized (this.workers) {
/* 35 */       Iterator<CraftWorker> itr = this.workers.iterator();
/* 36 */       while (itr.hasNext()) {
/* 37 */         CraftWorker craftWorker = (CraftWorker)itr.next();
/* 38 */         if (craftWorker.getOwner().equals(owner)) {
/* 39 */           craftWorker.interrupt();
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   void interruptAllTasks() {
/* 46 */     synchronized (this.workers) {
/* 47 */       Iterator<CraftWorker> itr = this.workers.iterator();
/* 48 */       while (itr.hasNext()) {
/* 49 */         CraftWorker craftWorker = (CraftWorker)itr.next();
/* 50 */         craftWorker.interrupt();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   boolean isAlive(int taskId) {
/* 56 */     synchronized (this.workers) {
/* 57 */       Iterator<CraftWorker> itr = this.workers.iterator();
/* 58 */       while (itr.hasNext()) {
/* 59 */         CraftWorker craftWorker = (CraftWorker)itr.next();
/* 60 */         if (craftWorker.getTaskId() == taskId) {
/* 61 */           return craftWorker.isAlive();
/*    */         }
/*    */       } 
/*    */       
/* 65 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\CraftThreadManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */