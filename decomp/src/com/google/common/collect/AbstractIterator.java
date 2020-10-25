/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible
/*     */ public abstract class AbstractIterator<T>
/*     */   extends UnmodifiableIterator<T>
/*     */ {
/*     */   private T next;
/*     */   
/*     */   protected abstract T computeNext();
/*     */   
/*  62 */   private State state = State.NOT_READY;
/*     */   
/*     */   private enum State
/*     */   {
/*  66 */     READY,
/*     */ 
/*     */     
/*  69 */     NOT_READY,
/*     */ 
/*     */     
/*  72 */     DONE,
/*     */ 
/*     */     
/*  75 */     FAILED;
/*     */   }
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
/*     */   protected final T endOfData() {
/* 118 */     this.state = State.DONE;
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   public final boolean hasNext() {
/* 123 */     Preconditions.checkState((this.state != State.FAILED));
/* 124 */     switch (this.state) {
/*     */       case DONE:
/* 126 */         return false;
/*     */       case READY:
/* 128 */         return true;
/*     */     } 
/*     */     
/* 131 */     return tryToComputeNext();
/*     */   }
/*     */   
/*     */   private boolean tryToComputeNext() {
/* 135 */     this.state = State.FAILED;
/* 136 */     this.next = computeNext();
/* 137 */     if (this.state != State.DONE) {
/* 138 */       this.state = State.READY;
/* 139 */       return true;
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public final T next() {
/* 145 */     if (!hasNext()) {
/* 146 */       throw new NoSuchElementException();
/*     */     }
/* 148 */     this.state = State.NOT_READY;
/* 149 */     return (T)this.next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T peek() {
/* 160 */     if (!hasNext()) {
/* 161 */       throw new NoSuchElementException();
/*     */     }
/* 163 */     return (T)this.next;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */