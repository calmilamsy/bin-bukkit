/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ class EmptyImmutableSortedSet<E>
/*     */   extends ImmutableSortedSet<E>
/*     */ {
/*  37 */   EmptyImmutableSortedSet(Comparator<? super E> comparator) { super(comparator); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public int size() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public boolean isEmpty() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public boolean contains(Object target) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public UnmodifiableIterator<E> iterator() { return Iterators.emptyIterator(); }
/*     */ 
/*     */   
/*  56 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */ 
/*     */   
/*  59 */   public Object[] toArray() { return EMPTY_ARRAY; }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/*  63 */     if (a.length > 0) {
/*  64 */       a[0] = null;
/*     */     }
/*  66 */     return a;
/*     */   }
/*     */ 
/*     */   
/*  70 */   public boolean containsAll(Collection<?> targets) { return targets.isEmpty(); }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  74 */     if (object instanceof Set) {
/*  75 */       Set<?> that = (Set)object;
/*  76 */       return that.isEmpty();
/*     */     } 
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*  82 */   public int hashCode() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String toString() { return "[]"; }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public E first() { throw new NoSuchElementException(); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public E last() { throw new NoSuchElementException(); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   ImmutableSortedSet<E> headSetImpl(E toElement) { return this; }
/*     */ 
/*     */ 
/*     */   
/* 102 */   ImmutableSortedSet<E> subSetImpl(E fromElement, E toElement) { return this; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   ImmutableSortedSet<E> tailSetImpl(E fromElement) { return this; }
/*     */ 
/*     */ 
/*     */   
/* 110 */   boolean hasPartialArray() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 114 */   int indexOf(Object target) { return -1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableSortedSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */