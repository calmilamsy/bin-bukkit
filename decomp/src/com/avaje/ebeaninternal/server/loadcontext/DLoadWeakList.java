/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class DLoadWeakList<T>
/*     */   extends Object
/*     */ {
/*  28 */   protected final ArrayList<WeakReference<T>> list = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   protected int removedFromTop;
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(T e) {
/*  37 */     synchronized (this) {
/*  38 */       int i = this.list.size();
/*  39 */       this.list.add(new WeakReference(e));
/*  40 */       return i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> getNextBatch(int batchSize) {
/*  48 */     synchronized (this) {
/*  49 */       return getBatch(0, batchSize);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> getLoadBatch(int position, int batchSize) {
/*  57 */     synchronized (this) {
/*  58 */       if (batchSize < 1) {
/*  59 */         throw new RuntimeException("batchSize " + batchSize + " < 1 ??!!");
/*     */       }
/*     */       
/*  62 */       int relativePos = position - this.removedFromTop;
/*  63 */       if (relativePos - batchSize < 0) {
/*  64 */         relativePos = 0;
/*     */       }
/*  66 */       if (relativePos > 0 && relativePos + batchSize > this.list.size()) {
/*  67 */         relativePos = this.list.size() - batchSize;
/*  68 */         if (relativePos < 0) {
/*  69 */           relativePos = 0;
/*     */         }
/*     */       } 
/*     */       
/*  73 */       return getBatch(relativePos, batchSize);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<T> getBatch(int relativePos, int batchSize) {
/*  79 */     ArrayList<T> batch = new ArrayList<T>();
/*     */     
/*  81 */     int count = 0;
/*  82 */     boolean removeFromTop = (relativePos == 0);
/*     */     
/*  84 */     while (count < batchSize && 
/*  85 */       !this.list.isEmpty()) {
/*     */       WeakReference<T> weakEntry;
/*     */ 
/*     */       
/*  89 */       if (removeFromTop) {
/*  90 */         weakEntry = (WeakReference)this.list.remove(relativePos);
/*  91 */         this.removedFromTop++;
/*     */       } else {
/*     */         
/*  94 */         if (relativePos >= this.list.size()) {
/*     */           break;
/*     */         }
/*  97 */         weakEntry = (WeakReference)this.list.get(relativePos);
/*  98 */         this.list.set(relativePos, null);
/*  99 */         relativePos++;
/*     */       } 
/* 101 */       T ebi = (T)((weakEntry == null) ? null : weakEntry.get());
/*     */       
/* 103 */       if (ebi != null) {
/* 104 */         batch.add(ebi);
/* 105 */         count++;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return batch;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadWeakList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */