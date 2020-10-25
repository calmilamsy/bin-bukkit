/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class Ordering<T>
/*     */   extends Object
/*     */   implements Comparator<T>
/*     */ {
/*     */   static final int LEFT_IS_GREATER = 1;
/*     */   static final int RIGHT_IS_GREATER = -1;
/*     */   
/*     */   @GwtCompatible(serializable = true)
/*  77 */   public static <C extends Comparable> Ordering<C> natural() { return NaturalOrdering.INSTANCE; }
/*     */ 
/*     */ 
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
/*  90 */   public static <T> Ordering<T> from(Comparator<T> comparator) { return (comparator instanceof Ordering) ? (Ordering)comparator : new ComparatorOrdering(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @GwtCompatible(serializable = true)
/* 102 */   public static <T> Ordering<T> from(Ordering<T> ordering) { return (Ordering)Preconditions.checkNotNull(ordering); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 128 */   public static <T> Ordering<T> explicit(List<T> valuesInOrder) { return new ExplicitOrdering(valuesInOrder); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 157 */   public static <T> Ordering<T> explicit(T leastValue, T... remainingValuesInOrder) { return explicit(Lists.asList(leastValue, remainingValuesInOrder)); }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static class IncomparableValueException
/*     */     extends ClassCastException
/*     */   {
/*     */     final Object value;
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */     
/*     */     IncomparableValueException(Object value) {
/* 173 */       super("Cannot compare value: " + value);
/* 174 */       this.value = value;
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
/* 197 */   public static Ordering<Object> arbitrary() { return ArbitraryOrderingHolder.ARBITRARY_ORDERING; }
/*     */   
/*     */   private static class ArbitraryOrderingHolder
/*     */   {
/* 201 */     static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering(); }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class ArbitraryOrdering extends Ordering<Object> {
/* 205 */     private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function<Object, Integer>()
/*     */         {
/*     */           
/* 208 */           final AtomicInteger counter = new AtomicInteger(false);
/*     */           
/* 210 */           public Integer apply(Object from) { return Integer.valueOf(this.counter.getAndIncrement()); }
/*     */         });
/*     */ 
/*     */     
/*     */     public int compare(Object left, Object right) {
/* 215 */       if (left == right) {
/* 216 */         return 0;
/*     */       }
/* 218 */       int leftCode = identityHashCode(left);
/* 219 */       int rightCode = identityHashCode(right);
/* 220 */       if (leftCode != rightCode) {
/* 221 */         return (leftCode < rightCode) ? -1 : 1;
/*     */       }
/*     */ 
/*     */       
/* 225 */       int result = ((Integer)this.uids.get(left)).compareTo((Integer)this.uids.get(right));
/* 226 */       if (result == 0) {
/* 227 */         throw new AssertionError();
/*     */       }
/* 229 */       return result;
/*     */     }
/*     */ 
/*     */     
/* 233 */     public String toString() { return "Ordering.arbitrary()"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     int identityHashCode(Object object) { return System.identityHashCode(object); }
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
/*     */   @GwtCompatible(serializable = true)
/* 258 */   public static Ordering<Object> usingToString() { return UsingToStringOrdering.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 279 */   public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> comparators) { return new CompoundOrdering(comparators); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 304 */   public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) { return new CompoundOrdering(this, (Comparator)Preconditions.checkNotNull(secondaryComparator)); }
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
/* 315 */   public <S extends T> Ordering<S> reverse() { return new ReverseOrdering(this); }
/*     */ 
/*     */ 
/*     */ 
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
/* 329 */   public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) { return new ByFunctionOrdering(function, this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 358 */   public <S extends T> Ordering<Iterable<S>> lexicographical() { return new LexicographicalOrdering(this); }
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
/* 369 */   public <S extends T> Ordering<S> nullsFirst() { return new NullsFirstOrdering(this); }
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
/* 380 */   public <S extends T> Ordering<S> nullsLast() { return new NullsLastOrdering(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 392 */   public int binarySearch(List<? extends T> sortedList, T key) { return Collections.binarySearch(sortedList, key, this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
/* 409 */     List<E> list = Lists.newArrayList(iterable);
/* 410 */     Collections.sort(list, this);
/* 411 */     return list;
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
/* 431 */   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) { return ImmutableList.copyOf(sortedCopy(iterable)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOrdered(Iterable<? extends T> iterable) {
/* 441 */     Iterator<? extends T> it = iterable.iterator();
/* 442 */     if (it.hasNext()) {
/* 443 */       T prev = (T)it.next();
/* 444 */       while (it.hasNext()) {
/* 445 */         T next = (T)it.next();
/* 446 */         if (compare(prev, next) > 0) {
/* 447 */           return false;
/*     */         }
/* 449 */         prev = next;
/*     */       } 
/*     */     } 
/* 452 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
/* 462 */     Iterator<? extends T> it = iterable.iterator();
/* 463 */     if (it.hasNext()) {
/* 464 */       T prev = (T)it.next();
/* 465 */       while (it.hasNext()) {
/* 466 */         T next = (T)it.next();
/* 467 */         if (compare(prev, next) >= 0) {
/* 468 */           return false;
/*     */         }
/* 470 */         prev = next;
/*     */       } 
/*     */     } 
/* 473 */     return true;
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
/*     */   public <E extends T> E max(Iterable<E> iterable) {
/* 486 */     Iterator<E> iterator = iterable.iterator();
/*     */ 
/*     */     
/* 489 */     E maxSoFar = (E)iterator.next();
/*     */     
/* 491 */     while (iterator.hasNext()) {
/* 492 */       maxSoFar = (E)max(maxSoFar, iterator.next());
/*     */     }
/*     */     
/* 495 */     return maxSoFar;
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
/*     */   public <E extends T> E max(E a, E b, E c, E... rest) {
/* 510 */     E maxSoFar = (E)max(max(a, b), c);
/*     */     
/* 512 */     for (E r : rest) {
/* 513 */       maxSoFar = (E)max(maxSoFar, r);
/*     */     }
/*     */     
/* 516 */     return maxSoFar;
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
/* 533 */   public <E extends T> E max(E a, E b) { return (compare(a, b) >= 0) ? a : b; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends T> E min(Iterable<E> iterable) {
/* 546 */     Iterator<E> iterator = iterable.iterator();
/*     */ 
/*     */     
/* 549 */     E minSoFar = (E)iterator.next();
/*     */     
/* 551 */     while (iterator.hasNext()) {
/* 552 */       minSoFar = (E)min(minSoFar, iterator.next());
/*     */     }
/*     */     
/* 555 */     return minSoFar;
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
/*     */   public <E extends T> E min(E a, E b, E c, E... rest) {
/* 570 */     E minSoFar = (E)min(min(a, b), c);
/*     */     
/* 572 */     for (E r : rest) {
/* 573 */       minSoFar = (E)min(minSoFar, r);
/*     */     }
/*     */     
/* 576 */     return minSoFar;
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
/* 593 */   public <E extends T> E min(E a, E b) { return (compare(a, b) <= 0) ? a : b; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Ordering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */