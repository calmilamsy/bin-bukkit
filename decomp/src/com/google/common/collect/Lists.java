/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.RandomAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Lists
/*     */ {
/*     */   @GwtCompatible(serializable = true)
/*  65 */   public static <E> ArrayList<E> newArrayList() { return new ArrayList(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <E> ArrayList<E> newArrayList(E... elements) {
/*  80 */     Preconditions.checkNotNull(elements);
/*     */     
/*  82 */     int capacity = computeArrayListCapacity(elements.length);
/*  83 */     ArrayList<E> list = new ArrayList<E>(capacity);
/*  84 */     Collections.addAll(list, elements);
/*  85 */     return list;
/*     */   }
/*     */   @VisibleForTesting
/*     */   static int computeArrayListCapacity(int arraySize) {
/*  89 */     Preconditions.checkArgument((arraySize >= 0));
/*     */ 
/*     */     
/*  92 */     return (int)Math.min(5L + arraySize + (arraySize / 10), 2147483647L);
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
/* 107 */     Preconditions.checkNotNull(elements);
/*     */     
/* 109 */     if (elements instanceof Collection) {
/*     */       
/* 111 */       Collection<? extends E> collection = (Collection)elements;
/* 112 */       return new ArrayList(collection);
/*     */     } 
/* 114 */     return newArrayList(elements.iterator());
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
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
/* 130 */     Preconditions.checkNotNull(elements);
/* 131 */     ArrayList<E> list = newArrayList();
/* 132 */     while (elements.hasNext()) {
/* 133 */       list.add(elements.next());
/*     */     }
/* 135 */     return list;
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
/*     */   @GwtCompatible(serializable = true)
/* 161 */   public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) { return new ArrayList(initialArraySize); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/* 182 */   public static <E> ArrayList<E> newArrayListWithExpectedSize(int estimatedSize) { return new ArrayList(computeArrayListCapacity(estimatedSize)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/* 197 */   public static <E> LinkedList<E> newLinkedList() { return new LinkedList(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*     */   public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
/* 209 */     LinkedList<E> list = newLinkedList();
/* 210 */     for (E element : elements) {
/* 211 */       list.add(element);
/*     */     }
/* 213 */     return list;
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
/* 233 */   public static <E> List<E> asList(@Nullable E first, E[] rest) { return new OnePlusArrayList(first, rest); }
/*     */   
/*     */   private static class OnePlusArrayList<E>
/*     */     extends AbstractList<E>
/*     */     implements Serializable, RandomAccess {
/*     */     final E first;
/*     */     final E[] rest;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     OnePlusArrayList(@Nullable E first, E[] rest) {
/* 243 */       this.first = first;
/* 244 */       this.rest = (Object[])Preconditions.checkNotNull(rest);
/*     */     }
/*     */     
/* 247 */     public int size() { return this.rest.length + 1; }
/*     */ 
/*     */     
/*     */     public E get(int index) {
/* 251 */       Preconditions.checkElementIndex(index, size());
/* 252 */       return (E)((index == 0) ? this.first : this.rest[index - 1]);
/*     */     }
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
/* 276 */   public static <E> List<E> asList(@Nullable E first, @Nullable E second, E[] rest) { return new TwoPlusArrayList(first, second, rest); }
/*     */   
/*     */   private static class TwoPlusArrayList<E>
/*     */     extends AbstractList<E>
/*     */     implements Serializable, RandomAccess {
/*     */     final E first;
/*     */     final E second;
/*     */     final E[] rest;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     TwoPlusArrayList(@Nullable E first, @Nullable E second, E[] rest) {
/* 287 */       this.first = first;
/* 288 */       this.second = second;
/* 289 */       this.rest = (Object[])Preconditions.checkNotNull(rest);
/*     */     }
/*     */     
/* 292 */     public int size() { return this.rest.length + 2; }
/*     */     
/*     */     public E get(int index) {
/* 295 */       switch (index) {
/*     */         case 0:
/* 297 */           return (E)this.first;
/*     */         case 1:
/* 299 */           return (E)this.second;
/*     */       } 
/*     */       
/* 302 */       Preconditions.checkElementIndex(index, size());
/* 303 */       return (E)this.rest[index - 2];
/*     */     }
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
/* 335 */   public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) { return (fromList instanceof RandomAccess) ? new TransformingRandomAccessList(fromList, function) : new TransformingSequentialList(fromList, function); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TransformingSequentialList<F, T>
/*     */     extends AbstractSequentialList<T>
/*     */     implements Serializable
/*     */   {
/*     */     final List<F> fromList;
/*     */ 
/*     */     
/*     */     final Function<? super F, ? extends T> function;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     TransformingSequentialList(List<F> fromList, Function<? super F, ? extends T> function) {
/* 352 */       this.fromList = (List)Preconditions.checkNotNull(fromList);
/* 353 */       this.function = (Function)Preconditions.checkNotNull(function);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     public void clear() { this.fromList.clear(); }
/*     */ 
/*     */     
/* 364 */     public int size() { return this.fromList.size(); }
/*     */     
/*     */     public ListIterator<T> listIterator(int index) {
/* 367 */       final ListIterator<F> delegate = this.fromList.listIterator(index);
/* 368 */       return new ListIterator<T>()
/*     */         {
/* 370 */           public void add(T e) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */           
/* 374 */           public boolean hasNext() { return delegate.hasNext(); }
/*     */ 
/*     */ 
/*     */           
/* 378 */           public boolean hasPrevious() { return delegate.hasPrevious(); }
/*     */ 
/*     */ 
/*     */           
/* 382 */           public T next() { return (T)Lists.TransformingSequentialList.this.function.apply(delegate.next()); }
/*     */ 
/*     */ 
/*     */           
/* 386 */           public int nextIndex() { return delegate.nextIndex(); }
/*     */ 
/*     */ 
/*     */           
/* 390 */           public T previous() { return (T)Lists.TransformingSequentialList.this.function.apply(delegate.previous()); }
/*     */ 
/*     */ 
/*     */           
/* 394 */           public int previousIndex() { return delegate.previousIndex(); }
/*     */ 
/*     */ 
/*     */           
/* 398 */           public void remove() { delegate.remove(); }
/*     */ 
/*     */ 
/*     */           
/* 402 */           public void set(T e) { throw new UnsupportedOperationException("not supported"); }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TransformingRandomAccessList<F, T>
/*     */     extends AbstractList<T>
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     final List<F> fromList;
/*     */ 
/*     */     
/*     */     final Function<? super F, ? extends T> function;
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */     
/*     */     TransformingRandomAccessList(List<F> fromList, Function<? super F, ? extends T> function) {
/* 425 */       this.fromList = (List)Preconditions.checkNotNull(fromList);
/* 426 */       this.function = (Function)Preconditions.checkNotNull(function);
/*     */     }
/*     */     
/* 429 */     public void clear() { this.fromList.clear(); }
/*     */ 
/*     */     
/* 432 */     public T get(int index) { return (T)this.function.apply(this.fromList.get(index)); }
/*     */ 
/*     */     
/* 435 */     public boolean isEmpty() { return this.fromList.isEmpty(); }
/*     */ 
/*     */     
/* 438 */     public T remove(int index) { return (T)this.function.apply(this.fromList.remove(index)); }
/*     */ 
/*     */     
/* 441 */     public int size() { return this.fromList.size(); }
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
/*     */   public static <T> List<List<T>> partition(List<T> list, int size) {
/* 465 */     Preconditions.checkNotNull(list);
/* 466 */     Preconditions.checkArgument((size > 0));
/* 467 */     return (list instanceof RandomAccess) ? new RandomAccessPartition(list, size) : new Partition(list, size);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Partition<T>
/*     */     extends AbstractList<List<T>>
/*     */   {
/*     */     final List<T> list;
/*     */     
/*     */     final int size;
/*     */     
/*     */     Partition(List<T> list, int size) {
/* 479 */       this.list = list;
/* 480 */       this.size = size;
/*     */     }
/*     */     
/*     */     public List<T> get(int index) {
/* 484 */       int listSize = size();
/* 485 */       Preconditions.checkElementIndex(index, listSize);
/* 486 */       int start = index * this.size;
/* 487 */       int end = Math.min(start + this.size, this.list.size());
/* 488 */       return this.list.subList(start, end);
/*     */     }
/*     */ 
/*     */     
/* 492 */     public int size() { return (this.list.size() + this.size - 1) / this.size; }
/*     */ 
/*     */ 
/*     */     
/* 496 */     public boolean isEmpty() { return this.list.isEmpty(); }
/*     */   }
/*     */   
/*     */   private static class RandomAccessPartition<T>
/*     */     extends Partition<T>
/*     */     implements RandomAccess
/*     */   {
/* 503 */     RandomAccessPartition(List<T> list, int size) { super(list, size); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Lists.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */