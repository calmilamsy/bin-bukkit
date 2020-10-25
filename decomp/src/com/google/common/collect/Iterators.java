/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible
/*      */ public final class Iterators
/*      */ {
/*   55 */   static final UnmodifiableIterator<Object> EMPTY_ITERATOR = new UnmodifiableIterator<Object>()
/*      */     {
/*      */       public boolean hasNext() {
/*   58 */         return false;
/*      */       }
/*      */       
/*   61 */       public Object next() { throw new NoSuchElementException(); }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static <T> UnmodifiableIterator<T> emptyIterator() { return EMPTY_ITERATOR; }
/*      */ 
/*      */   
/*   77 */   private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator<Object>()
/*      */     {
/*      */       public boolean hasNext() {
/*   80 */         return false;
/*      */       }
/*      */ 
/*      */       
/*   84 */       public Object next() { throw new NoSuchElementException(); }
/*      */ 
/*      */ 
/*      */       
/*   88 */       public void remove() { throw new IllegalStateException(); }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   static <T> Iterator<T> emptyModifiableIterator() { return EMPTY_MODIFIABLE_ITERATOR; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<T> iterator) {
/*  107 */     Preconditions.checkNotNull(iterator);
/*  108 */     return new UnmodifiableIterator<T>()
/*      */       {
/*  110 */         public boolean hasNext() { return iterator.hasNext(); }
/*      */ 
/*      */         
/*  113 */         public T next() { return (T)iterator.next(); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int size(Iterator<?> iterator) {
/*  124 */     int count = 0;
/*  125 */     while (iterator.hasNext()) {
/*  126 */       iterator.next();
/*  127 */       count++;
/*      */     } 
/*  129 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(Iterator<?> iterator, @Nullable Object element) {
/*  137 */     if (element == null) {
/*  138 */       while (iterator.hasNext()) {
/*  139 */         if (iterator.next() == null) {
/*  140 */           return true;
/*      */         }
/*      */       } 
/*      */     } else {
/*  144 */       while (iterator.hasNext()) {
/*  145 */         if (element.equals(iterator.next())) {
/*  146 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  150 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
/*  164 */     Preconditions.checkNotNull(elementsToRemove);
/*  165 */     boolean modified = false;
/*  166 */     while (removeFrom.hasNext()) {
/*  167 */       if (elementsToRemove.contains(removeFrom.next())) {
/*  168 */         removeFrom.remove();
/*  169 */         modified = true;
/*      */       } 
/*      */     } 
/*  172 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean removeIf(Iterator<T> removeFrom, Predicate<? super T> predicate) {
/*  188 */     Preconditions.checkNotNull(predicate);
/*  189 */     boolean modified = false;
/*  190 */     while (removeFrom.hasNext()) {
/*  191 */       if (predicate.apply(removeFrom.next())) {
/*  192 */         removeFrom.remove();
/*  193 */         modified = true;
/*      */       } 
/*      */     } 
/*  196 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean retainAll(Iterator<?> removeFrom, Collection<?> elementsToRetain) {
/*  210 */     Preconditions.checkNotNull(elementsToRetain);
/*  211 */     boolean modified = false;
/*  212 */     while (removeFrom.hasNext()) {
/*  213 */       if (!elementsToRetain.contains(removeFrom.next())) {
/*  214 */         removeFrom.remove();
/*  215 */         modified = true;
/*      */       } 
/*      */     } 
/*  218 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
/*  233 */     while (iterator1.hasNext()) {
/*  234 */       if (!iterator2.hasNext()) {
/*  235 */         return false;
/*      */       }
/*  237 */       Object o1 = iterator1.next();
/*  238 */       Object o2 = iterator2.next();
/*  239 */       if (!Objects.equal(o1, o2)) {
/*  240 */         return false;
/*      */       }
/*      */     } 
/*  243 */     return !iterator2.hasNext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Iterator<?> iterator) {
/*  252 */     if (!iterator.hasNext()) {
/*  253 */       return "[]";
/*      */     }
/*  255 */     StringBuilder builder = new StringBuilder();
/*  256 */     builder.append('[').append(iterator.next());
/*  257 */     while (iterator.hasNext()) {
/*  258 */       builder.append(", ").append(iterator.next());
/*      */     }
/*  260 */     return builder.append(']').toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getOnlyElement(Iterator<T> iterator) {
/*  271 */     T first = (T)iterator.next();
/*  272 */     if (!iterator.hasNext()) {
/*  273 */       return first;
/*      */     }
/*      */     
/*  276 */     StringBuilder sb = new StringBuilder();
/*  277 */     sb.append("expected one element but was: <" + first);
/*  278 */     for (int i = 0; i < 4 && iterator.hasNext(); i++) {
/*  279 */       sb.append(", " + iterator.next());
/*      */     }
/*  281 */     if (iterator.hasNext()) {
/*  282 */       sb.append(", ...");
/*      */     }
/*  284 */     sb.append(">");
/*      */     
/*  286 */     throw new IllegalArgumentException(sb.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  298 */   public static <T> T getOnlyElement(Iterator<T> iterator, @Nullable T defaultValue) { return (T)(iterator.hasNext() ? getOnlyElement(iterator) : defaultValue); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("Array.newArray")
/*      */   public static <T> T[] toArray(Iterator<? extends T> iterator, Class<T> type) {
/*  313 */     List<T> list = Lists.newArrayList(iterator);
/*  314 */     return (T[])Iterables.toArray(list, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
/*  327 */     Preconditions.checkNotNull(addTo);
/*  328 */     boolean wasModified = false;
/*  329 */     while (iterator.hasNext()) {
/*  330 */       wasModified |= addTo.add(iterator.next());
/*      */     }
/*  332 */     return wasModified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int frequency(Iterator<?> iterator, @Nullable Object element) {
/*  343 */     int result = 0;
/*  344 */     if (element == null) {
/*  345 */       while (iterator.hasNext()) {
/*  346 */         if (iterator.next() == null) {
/*  347 */           result++;
/*      */         }
/*      */       } 
/*      */     } else {
/*  351 */       while (iterator.hasNext()) {
/*  352 */         if (element.equals(iterator.next())) {
/*  353 */           result++;
/*      */         }
/*      */       } 
/*      */     } 
/*  357 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
/*  375 */     Preconditions.checkNotNull(iterable);
/*  376 */     return new Iterator<T>() {
/*  377 */         Iterator<T> iterator = Iterators.emptyIterator();
/*      */         Iterator<T> removeFrom;
/*      */         
/*      */         public boolean hasNext() {
/*  381 */           if (!this.iterator.hasNext()) {
/*  382 */             this.iterator = iterable.iterator();
/*      */           }
/*  384 */           return this.iterator.hasNext();
/*      */         }
/*      */         public T next() {
/*  387 */           if (!hasNext()) {
/*  388 */             throw new NoSuchElementException();
/*      */           }
/*  390 */           this.removeFrom = this.iterator;
/*  391 */           return (T)this.iterator.next();
/*      */         }
/*      */         public void remove() {
/*  394 */           Preconditions.checkState((this.removeFrom != null), "no calls to next() since last call to remove()");
/*      */           
/*  396 */           this.removeFrom.remove();
/*  397 */           this.removeFrom = null;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  416 */   public static <T> Iterator<T> cycle(T... elements) { return cycle(Lists.newArrayList(elements)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
/*  430 */     Preconditions.checkNotNull(a);
/*  431 */     Preconditions.checkNotNull(b);
/*  432 */     return concat(Arrays.asList(new Iterator[] { a, b }).iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
/*  447 */     Preconditions.checkNotNull(a);
/*  448 */     Preconditions.checkNotNull(b);
/*  449 */     Preconditions.checkNotNull(c);
/*  450 */     return concat(Arrays.asList(new Iterator[] { a, b, c }).iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
/*  466 */     Preconditions.checkNotNull(a);
/*  467 */     Preconditions.checkNotNull(b);
/*  468 */     Preconditions.checkNotNull(c);
/*  469 */     Preconditions.checkNotNull(d);
/*  470 */     return concat(Arrays.asList(new Iterator[] { a, b, c, d }).iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  484 */   public static <T> Iterator<T> concat(Iterator... inputs) { return concat(ImmutableList.copyOf(inputs).iterator()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
/*  498 */     Preconditions.checkNotNull(inputs);
/*  499 */     return new Iterator<T>() {
/*  500 */         Iterator<? extends T> current = Iterators.emptyIterator();
/*      */         
/*      */         Iterator<? extends T> removeFrom;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*      */           boolean currentHasNext;
/*  507 */           while (!(currentHasNext = this.current.hasNext()) && inputs.hasNext()) {
/*  508 */             this.current = (Iterator)inputs.next();
/*      */           }
/*  510 */           return currentHasNext;
/*      */         }
/*      */         public T next() {
/*  513 */           if (!hasNext()) {
/*  514 */             throw new NoSuchElementException();
/*      */           }
/*  516 */           this.removeFrom = this.current;
/*  517 */           return (T)this.current.next();
/*      */         }
/*      */         public void remove() {
/*  520 */           Preconditions.checkState((this.removeFrom != null), "no calls to next() since last call to remove()");
/*      */           
/*  522 */           this.removeFrom.remove();
/*  523 */           this.removeFrom = null;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  545 */   public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator, int size) { return partitionImpl(iterator, size, false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  566 */   public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator, int size) { return partitionImpl(iterator, size, true); }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
/*  571 */     Preconditions.checkNotNull(iterator);
/*  572 */     Preconditions.checkArgument((size > 0));
/*  573 */     return new UnmodifiableIterator<List<T>>()
/*      */       {
/*  575 */         public boolean hasNext() { return iterator.hasNext(); }
/*      */         
/*      */         public List<T> next() {
/*  578 */           if (!hasNext()) {
/*  579 */             throw new NoSuchElementException();
/*      */           }
/*  581 */           Object[] array = new Object[size];
/*  582 */           int count = 0;
/*  583 */           for (; count < size && iterator.hasNext(); count++) {
/*  584 */             array[count] = iterator.next();
/*      */           }
/*      */ 
/*      */           
/*  588 */           List<T> list = Collections.unmodifiableList(Arrays.asList(array));
/*      */           
/*  590 */           return (pad || count == size) ? list : list.subList(0, count);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
/*  600 */     Preconditions.checkNotNull(unfiltered);
/*  601 */     Preconditions.checkNotNull(predicate);
/*  602 */     return new AbstractIterator<T>() {
/*      */         protected T computeNext() {
/*  604 */           while (unfiltered.hasNext()) {
/*  605 */             T element = (T)unfiltered.next();
/*  606 */             if (predicate.apply(element)) {
/*  607 */               return element;
/*      */             }
/*      */           } 
/*  610 */           return (T)endOfData();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("Class.isInstance")
/*  629 */   public static <T> UnmodifiableIterator<T> filter(Iterator<?> unfiltered, Class<T> type) { return filter(unfiltered, Predicates.instanceOf(type)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  639 */     Preconditions.checkNotNull(predicate);
/*  640 */     while (iterator.hasNext()) {
/*  641 */       T element = (T)iterator.next();
/*  642 */       if (predicate.apply(element)) {
/*  643 */         return true;
/*      */       }
/*      */     } 
/*  646 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  656 */     Preconditions.checkNotNull(predicate);
/*  657 */     while (iterator.hasNext()) {
/*  658 */       T element = (T)iterator.next();
/*  659 */       if (!predicate.apply(element)) {
/*  660 */         return false;
/*      */       }
/*      */     } 
/*  663 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  679 */   public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) { return (T)filter(iterator, predicate).next(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
/*  700 */     Preconditions.checkNotNull(predicate, "predicate");
/*  701 */     int i = 0;
/*  702 */     while (iterator.hasNext()) {
/*  703 */       T current = (T)iterator.next();
/*  704 */       if (predicate.apply(current)) {
/*  705 */         return i;
/*      */       }
/*  707 */       i++;
/*      */     } 
/*  709 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <F, T> Iterator<T> transform(final Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
/*  722 */     Preconditions.checkNotNull(fromIterator);
/*  723 */     Preconditions.checkNotNull(function);
/*  724 */     return new Iterator<T>()
/*      */       {
/*  726 */         public boolean hasNext() { return fromIterator.hasNext(); }
/*      */         
/*      */         public T next() {
/*  729 */           F from = (F)fromIterator.next();
/*  730 */           return (T)function.apply(from);
/*      */         }
/*      */         
/*  733 */         public void remove() { fromIterator.remove(); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T get(Iterator<T> iterator, int position) {
/*  749 */     checkNonnegative(position);
/*      */     
/*  751 */     int skipped = 0;
/*  752 */     while (iterator.hasNext()) {
/*  753 */       T t = (T)iterator.next();
/*  754 */       if (skipped++ == position) {
/*  755 */         return t;
/*      */       }
/*      */     } 
/*      */     
/*  759 */     throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkNonnegative(int position) {
/*  765 */     if (position < 0) {
/*  766 */       throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getLast(Iterator<T> iterator) {
/*      */     T current;
/*      */     do {
/*  779 */       current = (T)iterator.next();
/*  780 */     } while (iterator.hasNext());
/*  781 */     return current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  795 */   public static <T> T getLast(Iterator<T> iterator, @Nullable T defaultValue) { return (T)(iterator.hasNext() ? getLast(iterator) : defaultValue); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <T> int skip(Iterator<T> iterator, int numberToSkip) {
/*  807 */     Preconditions.checkNotNull(iterator);
/*  808 */     Preconditions.checkArgument((numberToSkip >= 0), "number to skip cannot be negative");
/*      */     
/*      */     int i;
/*  811 */     for (i = 0; i < numberToSkip && iterator.hasNext(); i++) {
/*  812 */       iterator.next();
/*      */     }
/*  814 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <T> Iterator<T> limit(final Iterator<T> iterator, final int limitSize) {
/*  832 */     Preconditions.checkNotNull(iterator);
/*  833 */     Preconditions.checkArgument((limitSize >= 0), "limit is negative");
/*  834 */     return new Iterator<T>()
/*      */       {
/*      */         private int count;
/*      */         
/*  838 */         public boolean hasNext() { return (this.count < limitSize && iterator.hasNext()); }
/*      */ 
/*      */         
/*      */         public T next() {
/*  842 */           if (!hasNext()) {
/*  843 */             throw new NoSuchElementException();
/*      */           }
/*  845 */           this.count++;
/*  846 */           return (T)iterator.next();
/*      */         }
/*      */ 
/*      */         
/*  850 */         public void remove() { iterator.remove(); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator) {
/*  870 */     Preconditions.checkNotNull(iterator);
/*  871 */     return new UnmodifiableIterator<T>()
/*      */       {
/*  873 */         public boolean hasNext() { return iterator.hasNext(); }
/*      */ 
/*      */         
/*      */         public T next() {
/*  877 */           T next = (T)iterator.next();
/*  878 */           iterator.remove();
/*  879 */           return next;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> forArray(T... array) {
/*  901 */     Preconditions.checkNotNull(array);
/*  902 */     return new UnmodifiableIterator<T>() {
/*  903 */         final int length = array.length;
/*  904 */         int i = 0;
/*      */         
/*  906 */         public boolean hasNext() { return (this.i < this.length); }
/*      */         
/*      */         public T next() {
/*  909 */           if (this.i < this.length) {
/*  910 */             return (T)array[this.i++];
/*      */           }
/*  912 */           throw new NoSuchElementException();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> UnmodifiableIterator<T> forArray(final T[] array, final int offset, int length) {
/*  935 */     Preconditions.checkArgument((length >= 0));
/*  936 */     final int end = offset + length;
/*      */ 
/*      */     
/*  939 */     Preconditions.checkPositionIndexes(offset, end, array.length);
/*      */ 
/*      */ 
/*      */     
/*  943 */     return new UnmodifiableIterator<T>() {
/*  944 */         int i = offset;
/*      */         
/*  946 */         public boolean hasNext() { return (this.i < end); }
/*      */         
/*      */         public T next() {
/*  949 */           if (!hasNext()) {
/*  950 */             throw new NoSuchElementException();
/*      */           }
/*  952 */           return (T)array[this.i++];
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable final T value) {
/*  965 */     return new UnmodifiableIterator<T>() {
/*      */         boolean done;
/*      */         
/*  968 */         public boolean hasNext() { return !this.done; }
/*      */         
/*      */         public T next() {
/*  971 */           if (this.done) {
/*  972 */             throw new NoSuchElementException();
/*      */           }
/*  974 */           this.done = true;
/*  975 */           return (T)value;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
/*  990 */     Preconditions.checkNotNull(enumeration);
/*  991 */     return new UnmodifiableIterator<T>()
/*      */       {
/*  993 */         public boolean hasNext() { return enumeration.hasMoreElements(); }
/*      */ 
/*      */         
/*  996 */         public T next() { return (T)enumeration.nextElement(); }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
/* 1009 */     Preconditions.checkNotNull(iterator);
/* 1010 */     return new Enumeration<T>()
/*      */       {
/* 1012 */         public boolean hasMoreElements() { return iterator.hasNext(); }
/*      */ 
/*      */         
/* 1015 */         public T nextElement() { return (T)iterator.next(); }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private static class PeekingImpl<E>
/*      */     extends Object
/*      */     implements PeekingIterator<E>
/*      */   {
/*      */     private final Iterator<? extends E> iterator;
/*      */     
/*      */     private boolean hasPeeked;
/*      */     
/*      */     private E peekedElement;
/*      */     
/* 1030 */     public PeekingImpl(Iterator<? extends E> iterator) { this.iterator = (Iterator)Preconditions.checkNotNull(iterator); }
/*      */ 
/*      */ 
/*      */     
/* 1034 */     public boolean hasNext() { return (this.hasPeeked || this.iterator.hasNext()); }
/*      */ 
/*      */     
/*      */     public E next() {
/* 1038 */       if (!this.hasPeeked) {
/* 1039 */         return (E)this.iterator.next();
/*      */       }
/* 1041 */       E result = (E)this.peekedElement;
/* 1042 */       this.hasPeeked = false;
/* 1043 */       this.peekedElement = null;
/* 1044 */       return result;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1048 */       Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
/* 1049 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public E peek() {
/* 1053 */       if (!this.hasPeeked) {
/* 1054 */         this.peekedElement = this.iterator.next();
/* 1055 */         this.hasPeeked = true;
/*      */       } 
/* 1057 */       return (E)this.peekedElement;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
/* 1101 */     if (iterator instanceof PeekingImpl)
/*      */     {
/*      */ 
/*      */       
/* 1105 */       return (PeekingImpl)iterator;
/*      */     }
/*      */     
/* 1108 */     return new PeekingImpl(iterator);
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Iterators.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */