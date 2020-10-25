/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebean.TxScope;
/*     */ import java.util.ArrayList;
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
/*     */ public class ScopeTrans
/*     */   implements Thread.UncaughtExceptionHandler
/*     */ {
/*     */   private static final int OPCODE_ATHROW = 191;
/*     */   private final SpiTransactionScopeManager scopeMgr;
/*     */   private final SpiTransaction suspendedTransaction;
/*     */   private final SpiTransaction transaction;
/*     */   private final boolean rollbackOnChecked;
/*     */   private final boolean created;
/*     */   private final ArrayList<Class<? extends Throwable>> noRollbackFor;
/*     */   private final ArrayList<Class<? extends Throwable>> rollbackFor;
/*     */   private final Thread.UncaughtExceptionHandler originalUncaughtHandler;
/*     */   private boolean rolledBack;
/*     */   
/*     */   public ScopeTrans(boolean rollbackOnChecked, boolean created, SpiTransaction transaction, TxScope txScope, SpiTransaction suspendedTransaction, SpiTransactionScopeManager scopeMgr) {
/*  79 */     this.rollbackOnChecked = rollbackOnChecked;
/*  80 */     this.created = created;
/*  81 */     this.transaction = transaction;
/*  82 */     this.suspendedTransaction = suspendedTransaction;
/*  83 */     this.scopeMgr = scopeMgr;
/*     */     
/*  85 */     this.noRollbackFor = txScope.getNoRollbackFor();
/*  86 */     this.rollbackFor = txScope.getRollbackFor();
/*     */     
/*  88 */     Thread t = Thread.currentThread();
/*  89 */     this.originalUncaughtHandler = t.getUncaughtExceptionHandler();
/*     */     
/*  91 */     t.setUncaughtExceptionHandler(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uncaughtException(Thread thread, Throwable e) {
/* 101 */     caughtThrowable(e);
/*     */ 
/*     */ 
/*     */     
/* 105 */     onFinally();
/*     */     
/* 107 */     if (this.originalUncaughtHandler != null) {
/* 108 */       this.originalUncaughtHandler.uncaughtException(thread, e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onExit(Object returnOrThrowable, int opCode) {
/* 119 */     if (opCode == 191)
/*     */     {
/* 121 */       caughtThrowable((Throwable)returnOrThrowable);
/*     */     }
/* 123 */     onFinally();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinally() {
/*     */     try {
/* 133 */       if (this.originalUncaughtHandler != null) {
/* 134 */         Thread.currentThread().setUncaughtExceptionHandler(this.originalUncaughtHandler);
/*     */       }
/*     */       
/* 137 */       if (!this.rolledBack && this.created) {
/* 138 */         this.transaction.commit();
/*     */       }
/*     */     } finally {
/*     */       
/* 142 */       if (this.suspendedTransaction != null)
/*     */       {
/*     */         
/* 145 */         this.scopeMgr.replace(this.suspendedTransaction);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Error caughtError(Error e) {
/* 155 */     rollback(e);
/* 156 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Throwable> T caughtThrowable(T e) {
/* 165 */     if (isRollbackThrowable(e)) {
/* 166 */       rollback(e);
/*     */     }
/* 168 */     return e;
/*     */   }
/*     */   
/*     */   private void rollback(Throwable e) {
/* 172 */     if (this.transaction != null && this.transaction.isActive())
/*     */     {
/*     */       
/* 175 */       this.transaction.rollback(e);
/*     */     }
/* 177 */     this.rolledBack = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRollbackThrowable(Throwable e) {
/* 185 */     if (e instanceof Error) {
/* 186 */       return true;
/*     */     }
/*     */     
/* 189 */     if (this.noRollbackFor != null) {
/* 190 */       for (int i = 0; i < this.noRollbackFor.size(); i++) {
/* 191 */         if (((Class)this.noRollbackFor.get(i)).equals(e.getClass()))
/*     */         {
/*     */           
/* 194 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 199 */     if (this.rollbackFor != null) {
/* 200 */       for (int i = 0; i < this.rollbackFor.size(); i++) {
/* 201 */         if (((Class)this.rollbackFor.get(i)).equals(e.getClass()))
/*     */         {
/* 203 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 209 */     if (e instanceof RuntimeException) {
/* 210 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     return this.rollbackOnChecked;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\ScopeTrans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */