/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class ImmutableSortedSet<E>
/*     */   extends ImmutableSortedSetFauxverideShim<E>
/*     */   implements SortedSet<E>
/*     */ {
/*  92 */   private static final Comparator NATURAL_ORDER = Ordering.natural();
/*     */ 
/*     */   
/*  95 */   private static final ImmutableSortedSet<Object> NATURAL_EMPTY_SET = new EmptyImmutableSortedSet(NATURAL_ORDER);
/*     */   
/*     */   final Comparator<? super E> comparator;
/*     */ 
/*     */   
/* 100 */   private static <E> ImmutableSortedSet<E> emptySet() { return NATURAL_EMPTY_SET; }
/*     */ 
/*     */ 
/*     */   
/*     */   static <E> ImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
/* 105 */     if (NATURAL_ORDER.equals(comparator)) {
/* 106 */       return emptySet();
/*     */     }
/* 108 */     return new EmptyImmutableSortedSet(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static <E> ImmutableSortedSet<E> of() { return emptySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E element) {
/* 124 */     Object[] array = { Preconditions.checkNotNull(element) };
/* 125 */     return new RegularImmutableSortedSet(array, Ordering.natural());
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
/* 138 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2) { return ofInternal(Ordering.natural(), new Object[] { e1, e2 }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3) { return ofInternal(Ordering.natural(), new Object[] { e1, e2, e3 }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4) { return ofInternal(Ordering.natural(), new Object[] { e1, e2, e3, e4 }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4, E e5) { return ofInternal(Ordering.natural(), new Object[] { e1, e2, e3, e4, e5 }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... remaining) {
/* 190 */     int size = remaining.length + 6;
/* 191 */     List<E> all = new ArrayList<E>(size);
/* 192 */     Collections.addAll(all, new Comparable[] { e1, e2, e3, e4, e5, e6 });
/* 193 */     Collections.addAll(all, remaining);
/*     */     
/* 195 */     return ofInternal(Ordering.natural(), (Object[])all.toArray(new Comparable[0]));
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
/*     */   @Deprecated
/* 213 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E[] elements) { return copyOf(elements); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] elements) { return ofInternal(Ordering.natural(), (Object[])elements); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedSet<E> ofInternal(Comparator<? super E> comparator, Object... elements) {
/* 230 */     switch (elements.length) {
/*     */       case 0:
/* 232 */         return emptySet(comparator);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     Object[] array = new Object[elements.length];
/* 240 */     for (int i = 0; i < elements.length; i++) {
/* 241 */       array[i] = Preconditions.checkNotNull(elements[i]);
/*     */     }
/* 243 */     sort(array, comparator);
/* 244 */     array = removeDupes(array, comparator);
/* 245 */     return new RegularImmutableSortedSet(array, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   private static <E> void sort(Object[] array, Comparator<? super E> comparator) { Arrays.sort(array, comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> Object[] removeDupes(Object[] array, Comparator<? super E> comparator) {
/* 263 */     int size = 1;
/* 264 */     for (int i = 1; i < array.length; i++) {
/* 265 */       Object element = array[i];
/* 266 */       if (unsafeCompare(comparator, array[size - 1], element) != 0) {
/* 267 */         array[size] = element;
/* 268 */         size++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if (size == array.length) {
/* 274 */       return array;
/*     */     }
/* 276 */     Object[] copy = new Object[size];
/* 277 */     Platform.unsafeArrayCopy(array, 0, copy, 0, size);
/* 278 */     return copy;
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
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> elements) {
/* 311 */     Ordering<E> naturalOrder = Ordering.natural();
/* 312 */     return copyOfInternal(naturalOrder, elements, false);
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
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> elements) {
/* 331 */     Ordering<E> naturalOrder = Ordering.natural();
/* 332 */     return copyOfInternal(naturalOrder, elements);
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
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
/* 349 */     Preconditions.checkNotNull(comparator);
/* 350 */     return copyOfInternal(comparator, elements, false);
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
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> elements) {
/* 364 */     Preconditions.checkNotNull(comparator);
/* 365 */     return copyOfInternal(comparator, elements);
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
/*     */   public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
/* 382 */     Comparator<? super E> comparator = sortedSet.comparator();
/* 383 */     if (comparator == null) {
/* 384 */       comparator = NATURAL_ORDER;
/*     */     }
/* 386 */     return copyOfInternal(comparator, sortedSet, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> comparator, Iterable<? extends E> elements, boolean fromSortedSet) {
/* 392 */     boolean hasSameComparator = (fromSortedSet || hasSameComparator(elements, comparator));
/*     */ 
/*     */     
/* 395 */     if (hasSameComparator && elements instanceof ImmutableSortedSet) {
/*     */       
/* 397 */       ImmutableSortedSet<E> result = (ImmutableSortedSet)elements;
/* 398 */       if (!result.hasPartialArray()) {
/* 399 */         return result;
/*     */       }
/*     */     } 
/*     */     
/* 403 */     Object[] array = newObjectArray(elements);
/* 404 */     if (array.length == 0) {
/* 405 */       return emptySet(comparator);
/*     */     }
/*     */     
/* 408 */     for (Object e : array) {
/* 409 */       Preconditions.checkNotNull(e);
/*     */     }
/* 411 */     if (!hasSameComparator) {
/* 412 */       sort(array, comparator);
/* 413 */       array = removeDupes(array, comparator);
/*     */     } 
/* 415 */     return new RegularImmutableSortedSet(array, comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> Object[] newObjectArray(Iterable<T> iterable) {
/* 420 */     Collection<T> collection = Collections2.toCollection(iterable);
/* 421 */     Object[] array = new Object[collection.size()];
/* 422 */     return collection.toArray(array);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> comparator, Iterator<? extends E> elements) {
/* 427 */     if (!elements.hasNext()) {
/* 428 */       return emptySet(comparator);
/*     */     }
/* 430 */     List<E> list = Lists.newArrayList();
/* 431 */     while (elements.hasNext()) {
/* 432 */       list.add(Preconditions.checkNotNull(elements.next()));
/*     */     }
/* 434 */     Object[] array = list.toArray();
/* 435 */     sort(array, comparator);
/* 436 */     array = removeDupes(array, comparator);
/* 437 */     return new RegularImmutableSortedSet(array, comparator);
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
/*     */   static boolean hasSameComparator(Iterable<?> elements, Comparator<?> comparator) {
/* 449 */     if (elements instanceof SortedSet) {
/* 450 */       SortedSet<?> sortedSet = (SortedSet)elements;
/* 451 */       Comparator<?> comparator2 = sortedSet.comparator();
/* 452 */       return (comparator2 == null) ? ((comparator == Ordering.natural())) : comparator.equals(comparator2);
/*     */     } 
/*     */ 
/*     */     
/* 456 */     return false;
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
/*     */   @Beta
/* 471 */   public static <E> ImmutableSortedSet<E> withExplicitOrder(List<E> elements) { return ExplicitOrderedImmutableSortedSet.create(elements); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/* 491 */   public static <E> ImmutableSortedSet<E> withExplicitOrder(E firstElement, E... remainingElementsInOrder) { return withExplicitOrder(Lists.asList(firstElement, remainingElementsInOrder)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 504 */   public static <E> Builder<E> orderedBy(Comparator<E> comparator) { return new Builder(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 517 */   public static <E extends Comparable<E>> Builder<E> reverseOrder() { return new Builder(Ordering.natural().reverse()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 533 */   public static <E extends Comparable<E>> Builder<E> naturalOrder() { return new Builder(Ordering.natural()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<E>
/*     */     extends ImmutableSet.Builder<E>
/*     */   {
/*     */     private final Comparator<? super E> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     public Builder(Comparator<? super E> comparator) { this.comparator = (Comparator)Preconditions.checkNotNull(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E element) {
/* 575 */       super.add(element);
/* 576 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E... elements) {
/* 588 */       super.add(elements);
/* 589 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 601 */       super.addAll(elements);
/* 602 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 614 */       super.addAll(elements);
/* 615 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 623 */     public ImmutableSortedSet<E> build() { return ImmutableSortedSet.copyOfInternal(this.comparator, this.contents.iterator()); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 628 */   int unsafeCompare(Object a, Object b) { return unsafeCompare(this.comparator, a, b); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int unsafeCompare(Comparator<?> comparator, Object a, Object b) {
/* 637 */     Comparator<Object> unsafeComparator = comparator;
/* 638 */     return unsafeComparator.compare(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 644 */   ImmutableSortedSet(Comparator<? super E> comparator) { this.comparator = comparator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 655 */   public Comparator<? super E> comparator() { return this.comparator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 670 */   public ImmutableSortedSet<E> headSet(E toElement) { return headSetImpl(Preconditions.checkNotNull(toElement)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedSet<E> subSet(E fromElement, E toElement) {
/* 687 */     Preconditions.checkNotNull(fromElement);
/* 688 */     Preconditions.checkNotNull(toElement);
/* 689 */     Preconditions.checkArgument((this.comparator.compare(fromElement, toElement) <= 0));
/* 690 */     return subSetImpl(fromElement, toElement);
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
/* 705 */   public ImmutableSortedSet<E> tailSet(E fromElement) { return tailSetImpl(Preconditions.checkNotNull(fromElement)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializedForm<E>
/*     */     extends Object
/*     */     implements Serializable
/*     */   {
/*     */     final Comparator<? super E> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final Object[] elements;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SerializedForm(Comparator<? super E> comparator, Object[] elements) {
/* 735 */       this.comparator = comparator;
/* 736 */       this.elements = elements;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 741 */     Object readResolve() { return (new ImmutableSortedSet.Builder(this.comparator)).add((Object[])this.elements).build(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 749 */   private void readObject(ObjectInputStream stream) throws InvalidObjectException { throw new InvalidObjectException("Use SerializedForm"); }
/*     */ 
/*     */ 
/*     */   
/* 753 */   Object writeReplace() { return new SerializedForm(this.comparator, toArray()); }
/*     */   
/*     */   abstract ImmutableSortedSet<E> headSetImpl(E paramE);
/*     */   
/*     */   abstract ImmutableSortedSet<E> subSetImpl(E paramE1, E paramE2);
/*     */   
/*     */   abstract ImmutableSortedSet<E> tailSetImpl(E paramE);
/*     */   
/*     */   abstract boolean hasPartialArray();
/*     */   
/*     */   abstract int indexOf(Object paramObject);
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableSortedSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */