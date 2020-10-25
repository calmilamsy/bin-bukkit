/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
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
/*     */ @GwtCompatible
/*     */ public final class Iterables
/*     */ {
/*     */   public static <T> Iterable<T> unmodifiableIterable(final Iterable<T> iterable) {
/*  60 */     Preconditions.checkNotNull(iterable);
/*  61 */     return new Iterable<T>()
/*     */       {
/*  63 */         public Iterator<T> iterator() { return Iterators.unmodifiableIterator(iterable.iterator()); }
/*     */ 
/*     */         
/*  66 */         public String toString() { return iterable.toString(); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static int size(Iterable<?> iterable) { return (iterable instanceof Collection) ? ((Collection)iterable).size() : Iterators.size(iterable.iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(Iterable<?> iterable, @Nullable Object element) {
/*  87 */     if (iterable instanceof Collection) {
/*  88 */       Collection<?> collection = (Collection)iterable;
/*     */       try {
/*  90 */         return collection.contains(element);
/*  91 */       } catch (NullPointerException e) {
/*  92 */         return false;
/*  93 */       } catch (ClassCastException e) {
/*  94 */         return false;
/*     */       } 
/*     */     } 
/*  97 */     return Iterators.contains(iterable.iterator(), element);
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
/* 113 */   public static boolean removeAll(Iterable<?> removeFrom, Collection<?> elementsToRemove) { return (removeFrom instanceof Collection) ? ((Collection)removeFrom).removeAll((Collection)Preconditions.checkNotNull(elementsToRemove)) : Iterators.removeAll(removeFrom.iterator(), elementsToRemove); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public static boolean retainAll(Iterable<?> removeFrom, Collection<?> elementsToRetain) { return (removeFrom instanceof Collection) ? ((Collection)removeFrom).retainAll((Collection)Preconditions.checkNotNull(elementsToRetain)) : Iterators.retainAll(removeFrom.iterator(), elementsToRetain); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> boolean removeIf(Iterable<T> removeFrom, Predicate<? super T> predicate) {
/* 151 */     if (removeFrom instanceof java.util.RandomAccess && removeFrom instanceof List) {
/* 152 */       return removeIfFromRandomAccessList((List)removeFrom, (Predicate)Preconditions.checkNotNull(predicate));
/*     */     }
/*     */     
/* 155 */     return Iterators.removeIf(removeFrom.iterator(), predicate);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> boolean removeIfFromRandomAccessList(List<T> list, Predicate<? super T> predicate) {
/* 160 */     int from = 0;
/* 161 */     int to = 0;
/*     */     
/* 163 */     for (; from < list.size(); from++) {
/* 164 */       T element = (T)list.get(from);
/* 165 */       if (!predicate.apply(element)) {
/* 166 */         if (from > to) {
/* 167 */           list.set(to, element);
/*     */         }
/* 169 */         to++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 174 */     list.subList(to, list.size()).clear();
/* 175 */     return (from != to);
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
/* 187 */   public static boolean elementsEqual(Iterable<?> iterable1, Iterable<?> iterable2) { return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static String toString(Iterable<?> iterable) { return Iterators.toString(iterable.iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public static <T> T getOnlyElement(Iterable<T> iterable) { return (T)Iterators.getOnlyElement(iterable.iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public static <T> T getOnlyElement(Iterable<T> iterable, @Nullable T defaultValue) { return (T)Iterators.getOnlyElement(iterable.iterator(), defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Array.newInstance(Class, int)")
/*     */   public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> type) {
/* 231 */     Collection<? extends T> collection = Collections2.toCollection(iterable);
/* 232 */     T[] array = (T[])ObjectArrays.newArray(type, collection.size());
/* 233 */     return (T[])collection.toArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
/* 244 */     if (elementsToAdd instanceof Collection) {
/*     */       
/* 246 */       Collection<? extends T> c = (Collection)elementsToAdd;
/* 247 */       return addTo.addAll(c);
/*     */     } 
/* 249 */     return Iterators.addAll(addTo, elementsToAdd.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int frequency(Iterable<?> iterable, @Nullable Object element) {
/* 259 */     if (iterable instanceof Multiset) {
/* 260 */       return ((Multiset)iterable).count(element);
/*     */     }
/* 262 */     if (iterable instanceof Set) {
/* 263 */       return ((Set)iterable).contains(element) ? 1 : 0;
/*     */     }
/* 265 */     return Iterators.frequency(iterable.iterator(), element);
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
/*     */   public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
/* 286 */     Preconditions.checkNotNull(iterable);
/* 287 */     return new Iterable<T>()
/*     */       {
/* 289 */         public Iterator<T> iterator() { return Iterators.cycle(iterable); }
/*     */ 
/*     */         
/* 292 */         public String toString() { return iterable.toString() + " (cycled)"; }
/*     */       };
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
/* 316 */   public static <T> Iterable<T> cycle(T... elements) { return cycle(Lists.newArrayList(elements)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b) {
/* 330 */     Preconditions.checkNotNull(a);
/* 331 */     Preconditions.checkNotNull(b);
/* 332 */     return concat(Arrays.asList(new Iterable[] { a, b }));
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
/*     */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c) {
/* 347 */     Preconditions.checkNotNull(a);
/* 348 */     Preconditions.checkNotNull(b);
/* 349 */     Preconditions.checkNotNull(c);
/* 350 */     return concat(Arrays.asList(new Iterable[] { a, b, c }));
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
/*     */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c, Iterable<? extends T> d) {
/* 367 */     Preconditions.checkNotNull(a);
/* 368 */     Preconditions.checkNotNull(b);
/* 369 */     Preconditions.checkNotNull(c);
/* 370 */     Preconditions.checkNotNull(d);
/* 371 */     return concat(Arrays.asList(new Iterable[] { a, b, c, d }));
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
/* 385 */   public static <T> Iterable<T> concat(Iterable... inputs) { return concat(ImmutableList.copyOf(inputs)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> inputs) {
/* 413 */     Function<Iterable<? extends T>, Iterator<? extends T>> function = new Function<Iterable<? extends T>, Iterator<? extends T>>()
/*     */       {
/*     */         public Iterator<? extends T> apply(Iterable<? extends T> from) {
/* 416 */           return from.iterator();
/*     */         }
/*     */       };
/* 419 */     final Iterable<Iterator<? extends T>> iterators = transform(inputs, function);
/*     */     
/* 421 */     return new IterableWithToString<T>()
/*     */       {
/* 423 */         public Iterator<T> iterator() { return Iterators.concat(iterators.iterator()); }
/*     */       };
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
/*     */   public static <T> Iterable<List<T>> partition(final Iterable<T> iterable, final int size) {
/* 450 */     Preconditions.checkNotNull(iterable);
/* 451 */     Preconditions.checkArgument((size > 0));
/* 452 */     return new IterableWithToString<List<T>>()
/*     */       {
/* 454 */         public Iterator<List<T>> iterator() { return Iterators.partition(iterable.iterator(), size); }
/*     */       };
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
/*     */   public static <T> Iterable<List<T>> paddedPartition(final Iterable<T> iterable, final int size) {
/* 478 */     Preconditions.checkNotNull(iterable);
/* 479 */     Preconditions.checkArgument((size > 0));
/* 480 */     return new IterableWithToString<List<T>>()
/*     */       {
/* 482 */         public Iterator<List<T>> iterator() { return Iterators.paddedPartition(iterable.iterator(), size); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Iterable<T> filter(final Iterable<T> unfiltered, final Predicate<? super T> predicate) {
/* 493 */     Preconditions.checkNotNull(unfiltered);
/* 494 */     Preconditions.checkNotNull(predicate);
/* 495 */     return new IterableWithToString<T>()
/*     */       {
/* 497 */         public Iterator<T> iterator() { return Iterators.filter(unfiltered.iterator(), predicate); }
/*     */       };
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
/*     */   @GwtIncompatible("Class.isInstance")
/*     */   public static <T> Iterable<T> filter(final Iterable<?> unfiltered, final Class<T> type) {
/* 516 */     Preconditions.checkNotNull(unfiltered);
/* 517 */     Preconditions.checkNotNull(type);
/* 518 */     return new IterableWithToString<T>()
/*     */       {
/* 520 */         public Iterator<T> iterator() { return Iterators.filter(unfiltered.iterator(), type); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 531 */   public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) { return Iterators.any(iterable.iterator(), predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 540 */   public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) { return Iterators.all(iterable.iterator(), predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 552 */   public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate) { return (T)Iterators.find(iterable.iterator(), predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 568 */   public static <T> int indexOf(Iterable<T> iterable, Predicate<? super T> predicate) { return Iterators.indexOf(iterable.iterator(), predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <F, T> Iterable<T> transform(final Iterable<F> fromIterable, final Function<? super F, ? extends T> function) {
/* 581 */     Preconditions.checkNotNull(fromIterable);
/* 582 */     Preconditions.checkNotNull(function);
/* 583 */     return new IterableWithToString<T>()
/*     */       {
/* 585 */         public Iterator<T> iterator() { return Iterators.transform(fromIterable.iterator(), function); }
/*     */       };
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
/*     */   public static <T> T get(Iterable<T> iterable, int position) {
/* 599 */     Preconditions.checkNotNull(iterable);
/* 600 */     if (iterable instanceof List) {
/* 601 */       return (T)((List)iterable).get(position);
/*     */     }
/*     */     
/* 604 */     if (iterable instanceof Collection) {
/*     */       
/* 606 */       Collection<T> collection = (Collection)iterable;
/* 607 */       Preconditions.checkElementIndex(position, collection.size());
/*     */     } else {
/*     */       
/* 610 */       checkNonnegativeIndex(position);
/*     */     } 
/* 612 */     return (T)Iterators.get(iterable.iterator(), position);
/*     */   }
/*     */   
/*     */   private static void checkNonnegativeIndex(int position) {
/* 616 */     if (position < 0) {
/* 617 */       throw new IndexOutOfBoundsException("position cannot be negative: " + position);
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
/*     */   public static <T> T getLast(Iterable<T> iterable) {
/* 629 */     if (iterable instanceof List) {
/* 630 */       List<T> list = (List)iterable;
/*     */ 
/*     */       
/* 633 */       if (list.isEmpty()) {
/* 634 */         throw new NoSuchElementException();
/*     */       }
/* 636 */       return (T)getLastInNonemptyList(list);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     if (iterable instanceof SortedSet) {
/* 643 */       SortedSet<T> sortedSet = (SortedSet)iterable;
/* 644 */       return (T)sortedSet.last();
/*     */     } 
/*     */     
/* 647 */     return (T)Iterators.getLast(iterable.iterator());
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
/*     */   public static <T> T getLast(Iterable<T> iterable, @Nullable T defaultValue) {
/* 659 */     if (iterable instanceof Collection) {
/* 660 */       Collection<T> collection = (Collection)iterable;
/* 661 */       if (collection.isEmpty()) {
/* 662 */         return defaultValue;
/*     */       }
/*     */     } 
/*     */     
/* 666 */     if (iterable instanceof List) {
/* 667 */       List<T> list = (List)iterable;
/* 668 */       return (T)getLastInNonemptyList(list);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 674 */     if (iterable instanceof SortedSet) {
/* 675 */       SortedSet<T> sortedSet = (SortedSet)iterable;
/* 676 */       return (T)sortedSet.last();
/*     */     } 
/*     */     
/* 679 */     return (T)Iterators.getLast(iterable.iterator(), defaultValue);
/*     */   }
/*     */ 
/*     */   
/* 683 */   private static <T> T getLastInNonemptyList(List<T> list) { return (T)list.get(list.size() - 1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static <T> Iterable<T> skip(final Iterable<T> iterable, final int numberToSkip) {
/* 709 */     Preconditions.checkNotNull(iterable);
/* 710 */     Preconditions.checkArgument((numberToSkip >= 0), "number to skip cannot be negative");
/*     */     
/* 712 */     if (iterable instanceof List) {
/* 713 */       final List<T> list = (List)iterable;
/* 714 */       return new IterableWithToString<T>()
/*     */         {
/*     */           public Iterator<T> iterator()
/*     */           {
/* 718 */             return (numberToSkip >= list.size()) ? Iterators.emptyIterator() : list.subList(numberToSkip, list.size()).iterator();
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 725 */     return new IterableWithToString<T>() {
/*     */         public Iterator<T> iterator() {
/* 727 */           final Iterator<T> iterator = iterable.iterator();
/*     */           
/* 729 */           Iterators.skip(iterator, numberToSkip);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 736 */           return new Iterator<T>()
/*     */             {
/*     */               boolean atStart = true;
/*     */               
/* 740 */               public boolean hasNext() { return iterator.hasNext(); }
/*     */ 
/*     */               
/*     */               public T next() {
/* 744 */                 if (!hasNext()) {
/* 745 */                   throw new NoSuchElementException();
/*     */                 }
/*     */ 
/*     */                 
/* 749 */                 try { object = iterator.next();
/*     */                   
/* 751 */                   return (T)object; } finally { this.atStart = false; }
/*     */               
/*     */               }
/*     */               
/*     */               public void remove() {
/* 756 */                 if (this.atStart) {
/* 757 */                   throw new IllegalStateException();
/*     */                 }
/* 759 */                 iterator.remove();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   @Beta
/*     */   public static <T> Iterable<T> limit(final Iterable<T> iterable, final int limitSize) {
/* 781 */     Preconditions.checkNotNull(iterable);
/* 782 */     Preconditions.checkArgument((limitSize >= 0), "limit is negative");
/* 783 */     return new IterableWithToString<T>()
/*     */       {
/* 785 */         public Iterator<T> iterator() { return Iterators.limit(iterable.iterator(), limitSize); }
/*     */       };
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
/*     */   @Beta
/*     */   public static <T> Iterable<T> consumingIterable(final Iterable<T> iterable) {
/* 803 */     Preconditions.checkNotNull(iterable);
/* 804 */     return new Iterable<T>()
/*     */       {
/* 806 */         public Iterator<T> iterator() { return Iterators.consumingIterator(iterable.iterator()); }
/*     */       };
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
/*     */   public static <T> Iterable<T> reverse(final List<T> list) {
/* 829 */     Preconditions.checkNotNull(list);
/* 830 */     return new IterableWithToString<T>() {
/*     */         public Iterator<T> iterator() {
/* 832 */           final ListIterator<T> listIter = list.listIterator(list.size());
/* 833 */           return new Iterator<T>()
/*     */             {
/* 835 */               public boolean hasNext() { return listIter.hasPrevious(); }
/*     */ 
/*     */               
/* 838 */               public T next() { return (T)listIter.previous(); }
/*     */ 
/*     */               
/* 841 */               public void remove() { listIter.remove(); }
/*     */             };
/*     */         }
/*     */       };
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
/* 858 */   public static <T> boolean isEmpty(Iterable<T> iterable) { return !iterable.iterator().hasNext(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean remove(Iterable<?> iterable, @Nullable Object o) {
/* 882 */     Iterator<?> i = iterable.iterator();
/* 883 */     while (i.hasNext()) {
/* 884 */       if (Objects.equal(i.next(), o)) {
/* 885 */         i.remove();
/* 886 */         return true;
/*     */       } 
/*     */     } 
/* 889 */     return false;
/*     */   }
/*     */   
/*     */   static abstract class IterableWithToString<E>
/*     */     extends Object implements Iterable<E> {
/* 894 */     public String toString() { return Iterables.toString(this); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Iterables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */