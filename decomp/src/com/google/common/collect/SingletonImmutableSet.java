/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableSet<E>
/*     */   extends ImmutableSet<E>
/*     */ {
/*     */   final E element;
/*     */   private Integer cachedHashCode;
/*     */   
/*  44 */   SingletonImmutableSet(E element) { this.element = Preconditions.checkNotNull(element); }
/*     */ 
/*     */ 
/*     */   
/*     */   SingletonImmutableSet(E element, int hashCode) {
/*  49 */     this.element = element;
/*  50 */     this.cachedHashCode = Integer.valueOf(hashCode);
/*     */   }
/*     */ 
/*     */   
/*  54 */   public int size() { return 1; }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public boolean contains(Object target) { return this.element.equals(target); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public UnmodifiableIterator<E> iterator() { return Iterators.singletonIterator(this.element); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public Object[] toArray() { return new Object[] { this.element }; }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/*  75 */     if (array.length == 0) {
/*  76 */       array = (T[])ObjectArrays.newArray(array, 1);
/*  77 */     } else if (array.length > 1) {
/*  78 */       array[1] = null;
/*     */     } 
/*     */     
/*  81 */     T[] arrayOfT = array;
/*  82 */     arrayOfT[0] = this.element;
/*  83 */     return array;
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  87 */     if (object == this) {
/*  88 */       return true;
/*     */     }
/*  90 */     if (object instanceof Set) {
/*  91 */       Set<?> that = (Set)object;
/*  92 */       return (that.size() == 1 && this.element.equals(that.iterator().next()));
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*  98 */     Integer code = this.cachedHashCode;
/*  99 */     if (code == null) {
/* 100 */       return (this.cachedHashCode = Integer.valueOf(this.element.hashCode())).intValue();
/*     */     }
/* 102 */     return code.intValue();
/*     */   }
/*     */ 
/*     */   
/* 106 */   boolean isHashCodeFast() { return false; }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     String elementToString = this.element.toString();
/* 111 */     return (elementToString.length() + 2).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\SingletonImmutableSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */