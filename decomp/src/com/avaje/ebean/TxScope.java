/*     */ package com.avaje.ebean;
/*     */ 
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
/*     */ public final class TxScope
/*     */ {
/*     */   TxType type;
/*     */   String serverName;
/*     */   TxIsolation isolation;
/*     */   boolean readOnly;
/*     */   ArrayList<Class<? extends Throwable>> rollbackFor;
/*     */   ArrayList<Class<? extends Throwable>> noRollbackFor;
/*     */   
/*  40 */   public static TxScope required() { return new TxScope(TxType.REQUIRED); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static TxScope requiresNew() { return new TxScope(TxType.REQUIRES_NEW); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static TxScope mandatory() { return new TxScope(TxType.MANDATORY); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static TxScope supports() { return new TxScope(TxType.SUPPORTS); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static TxScope notSupported() { return new TxScope(TxType.NOT_SUPPORTED); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static TxScope never() { return new TxScope(TxType.NEVER); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public TxScope() { this.type = TxType.REQUIRED; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public TxScope(TxType type) { this.type = type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String toString() { return "TxScope[" + this.type + "] readOnly[" + this.readOnly + "] isolation[" + this.isolation + "] serverName[" + this.serverName + "] rollbackFor[" + this.rollbackFor + "] noRollbackFor[" + this.noRollbackFor + "]"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public TxType getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setType(TxType type) {
/* 111 */     this.type = type;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public boolean isReadonly() { return this.readOnly; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setReadOnly(boolean readOnly) {
/* 126 */     this.readOnly = readOnly;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public TxIsolation getIsolation() { return this.isolation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setIsolation(TxIsolation isolation) {
/* 141 */     this.isolation = isolation;
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public String getServerName() { return this.serverName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setServerName(String serverName) {
/* 160 */     this.serverName = serverName;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public ArrayList<Class<? extends Throwable>> getRollbackFor() { return this.rollbackFor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setRollbackFor(Class<? extends Throwable> rollbackThrowable) {
/* 175 */     if (this.rollbackFor == null) {
/* 176 */       this.rollbackFor = new ArrayList(2);
/*     */     }
/* 178 */     this.rollbackFor.add(rollbackThrowable);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setRollbackFor(Class[] rollbackThrowables) {
/* 187 */     if (this.rollbackFor == null) {
/* 188 */       this.rollbackFor = new ArrayList(rollbackThrowables.length);
/*     */     }
/* 190 */     for (int i = 0; i < rollbackThrowables.length; i++) {
/* 191 */       this.rollbackFor.add(rollbackThrowables[i]);
/*     */     }
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public ArrayList<Class<? extends Throwable>> getNoRollbackFor() { return this.noRollbackFor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setNoRollbackFor(Class<? extends Throwable> noRollback) {
/* 209 */     if (this.noRollbackFor == null) {
/* 210 */       this.noRollbackFor = new ArrayList(2);
/*     */     }
/* 212 */     this.noRollbackFor.add(noRollback);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setNoRollbackFor(Class[] noRollbacks) {
/* 221 */     if (this.noRollbackFor == null) {
/* 222 */       this.noRollbackFor = new ArrayList(noRollbacks.length);
/*     */     }
/* 224 */     for (int i = 0; i < noRollbacks.length; i++) {
/* 225 */       this.noRollbackFor.add(noRollbacks[i]);
/*     */     }
/* 227 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\TxScope.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */