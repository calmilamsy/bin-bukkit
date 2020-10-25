/*     */ package com.avaje.ebean;
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
/*     */ public static enum TxIsolation
/*     */ {
/*  43 */   READ_COMMITED(2),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   READ_UNCOMMITTED(true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   REPEATABLE_READ(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   SERIALIZABLE(8),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   NONE(false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   DEFAULT(-1);
/*     */   
/*     */   final int level;
/*     */ 
/*     */   
/*  74 */   TxIsolation(int level) { this.level = level; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public int getLevel() { return this.level; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxIsolation fromLevel(int connectionIsolationLevel) {
/*  95 */     switch (connectionIsolationLevel) {
/*     */       case 1:
/*  97 */         return READ_UNCOMMITTED;
/*     */       
/*     */       case 2:
/* 100 */         return READ_COMMITED;
/*     */       
/*     */       case 4:
/* 103 */         return REPEATABLE_READ;
/*     */       
/*     */       case 8:
/* 106 */         return SERIALIZABLE;
/*     */       
/*     */       case 0:
/* 109 */         return NONE;
/*     */       
/*     */       case -1:
/* 112 */         return DEFAULT;
/*     */     } 
/*     */     
/* 115 */     throw new RuntimeException("Unknown isolation level " + connectionIsolationLevel);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\TxIsolation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */