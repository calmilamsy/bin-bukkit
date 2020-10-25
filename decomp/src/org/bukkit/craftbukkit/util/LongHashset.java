/*     */ package org.bukkit.craftbukkit.util;
/*     */ 
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ public class LongHashset
/*     */   extends LongHash
/*     */ {
/*   9 */   long[][][] values = new long[256][][];
/*  10 */   int count = 0;
/*  11 */   ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
/*  12 */   ReentrantReadWriteLock.ReadLock rl = this.rwl.readLock();
/*  13 */   ReentrantReadWriteLock.WriteLock wl = this.rwl.writeLock();
/*     */   
/*     */   public boolean isEmpty() {
/*  16 */     this.rl.lock();
/*     */     try {
/*  18 */       return (this.count == 0);
/*     */     } finally {
/*  20 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  25 */   public void add(int msw, int lsw) { add(toLong(msw, lsw)); }
/*     */ 
/*     */   
/*     */   public void add(long key) {
/*  29 */     this.wl.lock();
/*     */     try {
/*  31 */       int mainIdx = (int)(key & 0xFFL);
/*  32 */       long[][] outer = this.values[mainIdx];
/*  33 */       if (outer == null) this.values[mainIdx] = outer = new long[256][];
/*     */       
/*  35 */       int outerIdx = (int)(key >> 32 & 0xFFL);
/*  36 */       long[] inner = outer[outerIdx];
/*     */       
/*  38 */       if (inner == null) {
/*  39 */         synchronized (this) {
/*  40 */           outer[outerIdx] = inner = new long[1];
/*  41 */           inner[0] = key;
/*  42 */           this.count++;
/*     */         } 
/*     */       } else {
/*     */         int i;
/*  46 */         for (i = 0; i < inner.length; i++) {
/*  47 */           if (inner[i] == key) {
/*     */             return;
/*     */           }
/*     */         } 
/*  51 */         inner = Java15Compat.Arrays_copyOf(inner, i + 1);
/*  52 */         outer[outerIdx] = inner;
/*  53 */         inner[i] = key;
/*  54 */         this.count++;
/*     */       } 
/*     */     } finally {
/*  57 */       this.wl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsKey(long key) {
/*  62 */     this.rl.lock();
/*     */     try {
/*  64 */       long[][] outer = this.values[(int)(key & 0xFFL)];
/*  65 */       if (outer == null) return false;
/*     */       
/*  67 */       long[] inner = outer[(int)(key >> 32 & 0xFFL)];
/*  68 */       if (inner == null) return false;
/*     */       
/*  70 */       for (long entry : inner) {
/*  71 */         if (entry == key) return true; 
/*     */       } 
/*  73 */       return false;
/*     */     } finally {
/*  75 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(long key) {
/*  80 */     this.wl.lock();
/*     */     try {
/*  82 */       long[][] outer = this.values[(int)(key & 0xFFL)];
/*  83 */       if (outer == null)
/*     */         return; 
/*  85 */       long[] inner = outer[(int)(key >> 32 & 0xFFL)];
/*  86 */       if (inner == null)
/*     */         return; 
/*  88 */       int max = inner.length - 1;
/*  89 */       for (int i = 0; i <= max; i++) {
/*  90 */         if (inner[i] == key) {
/*  91 */           this.count--;
/*  92 */           if (i != max) {
/*  93 */             inner[i] = inner[max];
/*     */           }
/*     */           
/*  96 */           outer[(int)(key >> 32 & 0xFFL)] = (max == 0) ? null : Java15Compat.Arrays_copyOf(inner, max);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 101 */       this.wl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long popFirst() {
/* 106 */     this.wl.lock();
/*     */     try {
/* 108 */       for (long[][] outer : this.values) {
/* 109 */         if (outer != null)
/*     */         {
/* 111 */           for (int i = 0; i < outer.length; ) {
/* 112 */             long[] inner = outer[i];
/* 113 */             if (inner == null || inner.length == 0) {
/*     */               i++; continue;
/* 115 */             }  this.count--;
/* 116 */             long ret = inner[inner.length - 1];
/* 117 */             outer[i] = Java15Compat.Arrays_copyOf(inner, inner.length - 1);
/*     */             
/* 119 */             return ret;
/*     */           }  } 
/*     */       } 
/*     */     } finally {
/* 123 */       this.wl.unlock();
/*     */     } 
/* 125 */     return 0L;
/*     */   }
/*     */   
/*     */   public long[] keys() {
/* 129 */     int index = 0;
/* 130 */     this.rl.lock();
/*     */     try {
/* 132 */       long[] ret = new long[this.count];
/* 133 */       for (long[][] outer : this.values) {
/* 134 */         if (outer != null)
/*     */         {
/* 136 */           for (long[] inner : outer) {
/* 137 */             if (inner != null)
/*     */             {
/* 139 */               for (long entry : inner)
/* 140 */                 ret[index++] = entry;  } 
/*     */           } 
/*     */         }
/*     */       } 
/* 144 */       return ret;
/*     */     } finally {
/* 146 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\LongHashset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */