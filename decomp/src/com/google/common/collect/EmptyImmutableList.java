/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class EmptyImmutableList
/*     */   extends ImmutableList<Object>
/*     */ {
/*  39 */   static final EmptyImmutableList INSTANCE = new EmptyImmutableList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public int size() { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public boolean isEmpty() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public boolean contains(Object target) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public UnmodifiableIterator<Object> iterator() { return Iterators.emptyIterator(); }
/*     */ 
/*     */   
/*  59 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  62 */   public Object[] toArray() { return EMPTY_ARRAY; }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/*  66 */     if (a.length > 0) {
/*  67 */       a[0] = null;
/*     */     }
/*  69 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/*  74 */     Preconditions.checkElementIndex(index, 0);
/*  75 */     throw new AssertionError("unreachable");
/*     */   }
/*     */ 
/*     */   
/*  79 */   public int indexOf(Object target) { return -1; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public int lastIndexOf(Object target) { return -1; }
/*     */ 
/*     */   
/*     */   public ImmutableList<Object> subList(int fromIndex, int toIndex) {
/*  87 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, 0);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  92 */   public ListIterator<Object> listIterator() { return Collections.emptyList().listIterator(); }
/*     */ 
/*     */   
/*     */   public ListIterator<Object> listIterator(int start) {
/*  96 */     Preconditions.checkPositionIndex(start, 0);
/*  97 */     return Collections.emptyList().listIterator();
/*     */   }
/*     */ 
/*     */   
/* 101 */   public boolean containsAll(Collection<?> targets) { return targets.isEmpty(); }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 105 */     if (object instanceof List) {
/* 106 */       List<?> that = (List)object;
/* 107 */       return that.isEmpty();
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 113 */   public int hashCode() { return 1; }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String toString() { return "[]"; }
/*     */ 
/*     */ 
/*     */   
/* 121 */   Object readResolve() { return INSTANCE; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */