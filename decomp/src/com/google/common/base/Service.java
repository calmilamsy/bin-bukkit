/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public interface Service
/*     */ {
/*     */   Future<State> start();
/*     */   
/*     */   State startAndWait();
/*     */   
/*     */   boolean isRunning();
/*     */   
/*     */   State state();
/*     */   
/*     */   Future<State> stop();
/*     */   
/*     */   State stopAndWait();
/*     */   
/*     */   public enum State
/*     */   {
/* 131 */     NEW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     STARTING,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     RUNNING,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     STOPPING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     TERMINATED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     FAILED;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */