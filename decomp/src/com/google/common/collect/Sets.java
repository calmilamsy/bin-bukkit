/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Sets
/*     */ {
/*     */   @GwtCompatible(serializable = true)
/*  80 */   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E anElement, E... otherElements) { return new ImmutableEnumSet(EnumSet.of(anElement, otherElements)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> elements) {
/*  98 */     Iterator<E> iterator = elements.iterator();
/*  99 */     if (!iterator.hasNext()) {
/* 100 */       return ImmutableSet.of();
/*     */     }
/* 102 */     if (elements instanceof EnumSet) {
/* 103 */       EnumSet<E> enumSetClone = EnumSet.copyOf((EnumSet)elements);
/* 104 */       return new ImmutableEnumSet(enumSetClone);
/*     */     } 
/* 106 */     E first = (E)(Enum)iterator.next();
/* 107 */     EnumSet<E> set = EnumSet.of(first);
/* 108 */     while (iterator.hasNext()) {
/* 109 */       set.add(iterator.next());
/*     */     }
/* 111 */     return new ImmutableEnumSet(set);
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
/*     */   public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> elementType) {
/* 135 */     Preconditions.checkNotNull(iterable);
/* 136 */     EnumSet<E> set = EnumSet.noneOf(elementType);
/* 137 */     Iterables.addAll(set, iterable);
/* 138 */     return set;
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
/* 155 */   public static <E> HashSet<E> newHashSet() { return new HashSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> HashSet<E> newHashSet(E... elements) {
/* 172 */     int capacity = Maps.capacity(elements.length);
/* 173 */     HashSet<E> set = new HashSet<E>(capacity);
/* 174 */     Collections.addAll(set, elements);
/* 175 */     return set;
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
/* 188 */   public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) { return new HashSet(Maps.capacity(expectedSize)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
/* 205 */     if (elements instanceof Collection) {
/*     */       
/* 207 */       Collection<? extends E> collection = (Collection)elements;
/* 208 */       return new HashSet(collection);
/*     */     } 
/* 210 */     return newHashSet(elements.iterator());
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
/*     */   public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
/* 228 */     HashSet<E> set = newHashSet();
/* 229 */     while (elements.hasNext()) {
/* 230 */       set.add(elements.next());
/*     */     }
/* 232 */     return set;
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
/* 246 */   public static <E> LinkedHashSet<E> newLinkedHashSet() { return new LinkedHashSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> elements) {
/* 262 */     if (elements instanceof Collection) {
/*     */       
/* 264 */       Collection<? extends E> collection = (Collection)elements;
/* 265 */       return new LinkedHashSet(collection);
/*     */     } 
/* 267 */     LinkedHashSet<E> set = newLinkedHashSet();
/* 268 */     for (E element : elements) {
/* 269 */       set.add(element);
/*     */     }
/* 271 */     return set;
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
/* 287 */   public static <E extends Comparable> TreeSet<E> newTreeSet() { return new TreeSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> elements) {
/* 308 */     TreeSet<E> set = newTreeSet();
/* 309 */     for (Iterator i$ = elements.iterator(); i$.hasNext(); ) { E element = (E)(Comparable)i$.next();
/* 310 */       set.add(element); }
/*     */     
/* 312 */     return set;
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
/* 327 */   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) { return new TreeSet((Comparator)Preconditions.checkNotNull(comparator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
/* 347 */     if (collection instanceof EnumSet) {
/* 348 */       return EnumSet.complementOf((EnumSet)collection);
/*     */     }
/* 350 */     Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
/*     */     
/* 352 */     Class<E> type = ((Enum)collection.iterator().next()).getDeclaringClass();
/* 353 */     return makeComplementByHand(collection, type);
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
/*     */   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> type) {
/* 370 */     Preconditions.checkNotNull(collection);
/* 371 */     return (collection instanceof EnumSet) ? EnumSet.complementOf((EnumSet)collection) : makeComplementByHand(collection, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> type) {
/* 378 */     EnumSet<E> result = EnumSet.allOf(type);
/* 379 */     result.removeAll(collection);
/* 380 */     return result;
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
/*     */   
/* 423 */   public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) { return new SetFromMap(map); }
/*     */   
/*     */   private static class SetFromMap<E>
/*     */     extends AbstractSet<E> implements Set<E>, Serializable {
/*     */     private final Map<E, Boolean> m;
/*     */     private Set<E> s;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SetFromMap(Map<E, Boolean> map) {
/* 432 */       Preconditions.checkArgument(map.isEmpty(), "Map is non-empty");
/* 433 */       this.m = map;
/* 434 */       this.s = map.keySet();
/*     */     }
/*     */ 
/*     */     
/* 438 */     public void clear() { this.m.clear(); }
/*     */ 
/*     */     
/* 441 */     public int size() { return this.m.size(); }
/*     */ 
/*     */     
/* 444 */     public boolean isEmpty() { return this.m.isEmpty(); }
/*     */ 
/*     */     
/* 447 */     public boolean contains(Object o) { return this.m.containsKey(o); }
/*     */ 
/*     */     
/* 450 */     public boolean remove(Object o) { return (this.m.remove(o) != null); }
/*     */ 
/*     */     
/* 453 */     public boolean add(E e) { return (this.m.put(e, Boolean.TRUE) == null); }
/*     */ 
/*     */     
/* 456 */     public Iterator<E> iterator() { return this.s.iterator(); }
/*     */ 
/*     */     
/* 459 */     public Object[] toArray() { return this.s.toArray(); }
/*     */ 
/*     */     
/* 462 */     public <T> T[] toArray(T[] a) { return (T[])this.s.toArray(a); }
/*     */ 
/*     */     
/* 465 */     public String toString() { return this.s.toString(); }
/*     */ 
/*     */     
/* 468 */     public int hashCode() { return this.s.hashCode(); }
/*     */ 
/*     */     
/* 471 */     public boolean equals(@Nullable Object object) { return (this == object || this.s.equals(object)); }
/*     */ 
/*     */     
/* 474 */     public boolean containsAll(Collection<?> c) { return this.s.containsAll(c); }
/*     */ 
/*     */     
/* 477 */     public boolean removeAll(Collection<?> c) { return this.s.removeAll(c); }
/*     */ 
/*     */     
/* 480 */     public boolean retainAll(Collection<?> c) { return this.s.retainAll(c); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 489 */       stream.defaultReadObject();
/* 490 */       this.s = this.m.keySet();
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
/*     */   public static abstract class SetView<E>
/*     */     extends AbstractSet<E>
/*     */   {
/*     */     private SetView() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     public ImmutableSet<E> immutableCopy() { return ImmutableSet.copyOf(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <S extends Set<E>> S copyInto(S set) {
/* 528 */       set.addAll(this);
/* 529 */       return set;
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
/*     */   public static <E> SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
/* 550 */     Preconditions.checkNotNull(set1, "set1");
/* 551 */     Preconditions.checkNotNull(set2, "set2");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 556 */     final Set<? extends E> set2minus1 = difference(set2, set1);
/*     */     
/* 558 */     return new SetView<E>()
/*     */       {
/* 560 */         public int size() { return set1.size() + set2minus1.size(); }
/*     */ 
/*     */         
/* 563 */         public boolean isEmpty() { return (set1.isEmpty() && set2.isEmpty()); }
/*     */ 
/*     */         
/* 566 */         public Iterator<E> iterator() { return Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator())); }
/*     */ 
/*     */ 
/*     */         
/* 570 */         public boolean contains(Object object) { return (set1.contains(object) || set2.contains(object)); }
/*     */         
/*     */         public <S extends Set<E>> S copyInto(S set) {
/* 573 */           set.addAll(set1);
/* 574 */           set.addAll(set2);
/* 575 */           return set;
/*     */         }
/*     */         
/* 578 */         public ImmutableSet<E> immutableCopy() { return (new ImmutableSet.Builder()).addAll(set1).addAll(set2).build(); }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> SetView<E> intersection(final Set<E> set1, final Set<?> set2) {
/* 612 */     Preconditions.checkNotNull(set1, "set1");
/* 613 */     Preconditions.checkNotNull(set2, "set2");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 618 */     final Predicate<Object> inSet2 = Predicates.in(set2);
/* 619 */     return new SetView<E>()
/*     */       {
/* 621 */         public Iterator<E> iterator() { return Iterators.filter(set1.iterator(), inSet2); }
/*     */ 
/*     */         
/* 624 */         public int size() { return Iterators.size(iterator()); }
/*     */ 
/*     */         
/* 627 */         public boolean isEmpty() { return !iterator().hasNext(); }
/*     */ 
/*     */         
/* 630 */         public boolean contains(Object object) { return (set1.contains(object) && set2.contains(object)); }
/*     */ 
/*     */         
/* 633 */         public boolean containsAll(Collection<?> collection) { return (set1.containsAll(collection) && set2.containsAll(collection)); }
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
/*     */   public static <E> SetView<E> difference(final Set<E> set1, final Set<?> set2) {
/* 652 */     Preconditions.checkNotNull(set1, "set1");
/* 653 */     Preconditions.checkNotNull(set2, "set2");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 658 */     final Predicate<Object> notInSet2 = Predicates.not(Predicates.in(set2));
/* 659 */     return new SetView<E>()
/*     */       {
/* 661 */         public Iterator<E> iterator() { return Iterators.filter(set1.iterator(), notInSet2); }
/*     */ 
/*     */         
/* 664 */         public int size() { return Iterators.size(iterator()); }
/*     */ 
/*     */         
/* 667 */         public boolean isEmpty() { return set2.containsAll(set1); }
/*     */ 
/*     */         
/* 670 */         public boolean contains(Object element) { return (set1.contains(element) && !set2.contains(element)); }
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
/*     */   @Beta
/*     */   public static <E> SetView<E> symmetricDifference(Set<? extends E> set1, Set<? extends E> set2) {
/* 690 */     Preconditions.checkNotNull(set1, "set1");
/* 691 */     Preconditions.checkNotNull(set2, "set2");
/*     */ 
/*     */     
/* 694 */     return difference(union(set1, set2), intersection(set1, set2));
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
/*     */   public static <E> Set<E> filter(Set<E> unfiltered, Predicate<? super E> predicate) {
/* 720 */     if (unfiltered instanceof FilteredSet) {
/*     */ 
/*     */       
/* 723 */       FilteredSet<E> filtered = (FilteredSet)unfiltered;
/* 724 */       Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
/*     */       
/* 726 */       return new FilteredSet((Set)filtered.unfiltered, combinedPredicate);
/*     */     } 
/*     */ 
/*     */     
/* 730 */     return new FilteredSet((Set)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
/*     */   }
/*     */   
/*     */   private static class FilteredSet<E>
/*     */     extends Collections2.FilteredCollection<E>
/*     */     implements Set<E>
/*     */   {
/* 737 */     FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) { super(unfiltered, predicate); }
/*     */ 
/*     */ 
/*     */     
/* 741 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
/*     */ 
/*     */ 
/*     */     
/* 745 */     public int hashCode() { return Sets.hashCodeImpl(this); }
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
/*     */ 
/*     */   
/*     */   public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> sets) {
/* 790 */     CartesianSet<B> cartesianSet = new CartesianSet<B>(sets);
/* 791 */     return cartesianSet.isEmpty() ? ImmutableSet.of() : cartesianSet;
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
/*     */ 
/*     */   
/* 835 */   public static <B> Set<List<B>> cartesianProduct(Set... sets) { return cartesianProduct(Arrays.asList(sets)); }
/*     */   
/*     */   private static class CartesianSet<B>
/*     */     extends AbstractSet<List<B>> {
/*     */     final ImmutableList<Axis> axes;
/*     */     final int size;
/*     */     
/*     */     CartesianSet(List<? extends Set<? extends B>> sets) {
/* 843 */       long dividend = 1L;
/* 844 */       ImmutableList.Builder<Axis> builder = ImmutableList.builder();
/* 845 */       for (Set<? extends B> set : sets) {
/* 846 */         Axis axis = new Axis(set, (int)dividend);
/* 847 */         builder.add(axis);
/* 848 */         dividend *= axis.size();
/*     */       } 
/* 850 */       this.axes = builder.build();
/* 851 */       this.size = Ints.checkedCast(dividend);
/*     */     }
/*     */ 
/*     */     
/* 855 */     public int size() { return this.size; }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<List<B>> iterator() {
/* 859 */       return new UnmodifiableIterator<List<B>>()
/*     */         {
/*     */           int index;
/*     */           
/* 863 */           public boolean hasNext() { return (super.index < Sets.CartesianSet.this.size); }
/*     */ 
/*     */           
/*     */           public List<B> next() {
/* 867 */             if (!super.hasNext()) {
/* 868 */               throw new NoSuchElementException();
/*     */             }
/*     */             
/* 871 */             Object[] tuple = new Object[Sets.CartesianSet.this.axes.size()];
/* 872 */             for (int i = 0; i < tuple.length; i++) {
/* 873 */               tuple[i] = ((Sets.CartesianSet.Axis)Sets.CartesianSet.this.axes.get(i)).getForIndex(super.index);
/*     */             }
/* 875 */             super.index++;
/*     */ 
/*     */             
/* 878 */             return ImmutableList.copyOf(tuple);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object element) {
/* 885 */       if (!(element instanceof List)) {
/* 886 */         return false;
/*     */       }
/* 888 */       List<?> tuple = (List)element;
/* 889 */       int dimensions = this.axes.size();
/* 890 */       if (tuple.size() != dimensions) {
/* 891 */         return false;
/*     */       }
/* 893 */       for (int i = 0; i < dimensions; i++) {
/* 894 */         if (!((Axis)this.axes.get(i)).contains(tuple.get(i))) {
/* 895 */           return false;
/*     */         }
/*     */       } 
/* 898 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 904 */       if (object instanceof CartesianSet) {
/* 905 */         CartesianSet<?> that = (CartesianSet)object;
/* 906 */         return this.axes.equals(that.axes);
/*     */       } 
/* 908 */       return super.equals(object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 916 */       int adjust = this.size - 1;
/* 917 */       for (int i = 0; i < this.axes.size(); i++) {
/* 918 */         adjust *= 31;
/*     */       }
/* 920 */       return this.axes.hashCode() + adjust;
/*     */     }
/*     */     
/*     */     private class Axis {
/*     */       final ImmutableSet<? extends B> choices;
/*     */       final int dividend;
/*     */       
/*     */       Axis(Set<? extends B> set, int dividend) {
/* 928 */         this.choices = ImmutableSet.copyOf(set);
/* 929 */         this.dividend = dividend;
/*     */       }
/*     */ 
/*     */       
/* 933 */       int size() { return this.choices.size(); }
/*     */ 
/*     */ 
/*     */       
/* 937 */       B getForIndex(int index) { return (B)this.choices.asList().get(index / this.dividend % size()); }
/*     */ 
/*     */ 
/*     */       
/* 941 */       boolean contains(Object target) { return this.choices.contains(target); }
/*     */ 
/*     */       
/*     */       public boolean equals(Object obj) {
/* 945 */         if (obj instanceof Axis) {
/* 946 */           Axis that = (Axis)obj;
/* 947 */           return this.choices.equals(that.choices);
/*     */         } 
/*     */         
/* 950 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 956 */       public int hashCode() { return Sets.CartesianSet.this.size / this.choices.size() * this.choices.hashCode(); }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int hashCodeImpl(Set<?> s) {
/* 965 */     int hashCode = 0;
/* 966 */     for (Object o : s) {
/* 967 */       hashCode += ((o != null) ? o.hashCode() : 0);
/*     */     }
/* 969 */     return hashCode;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Sets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */