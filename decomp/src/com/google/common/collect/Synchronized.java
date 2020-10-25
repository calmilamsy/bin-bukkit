/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Preconditions;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
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
/*      */ @GwtCompatible
/*      */ final class Synchronized
/*      */ {
/*      */   private static class SynchronizedObject
/*      */     implements Serializable
/*      */   {
/*      */     final Object delegate;
/*      */     final Object mutex;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     SynchronizedObject(Object delegate, @Nullable Object mutex) {
/*   61 */       this.delegate = Preconditions.checkNotNull(delegate);
/*   62 */       this.mutex = (mutex == null) ? this : mutex;
/*      */     }
/*      */ 
/*      */     
/*   66 */     Object delegate() { return this.delegate; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*   72 */       synchronized (this.mutex) {
/*   73 */         return this.delegate.toString();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*   83 */       synchronized (this.mutex) {
/*   84 */         stream.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   private static <E> Collection<E> collection(Collection<E> collection, @Nullable Object mutex) { return new SynchronizedCollection(collection, mutex, null); }
/*      */   
/*      */   @VisibleForTesting
/*      */   static class SynchronizedCollection<E>
/*      */     extends SynchronizedObject implements Collection<E> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  100 */     private SynchronizedCollection(Collection<E> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     Collection<E> delegate() { return (Collection)super.delegate(); }
/*      */ 
/*      */     
/*      */     public boolean add(E e) {
/*  109 */       synchronized (this.mutex) {
/*  110 */         return delegate().add(e);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> c) {
/*  115 */       synchronized (this.mutex) {
/*  116 */         return delegate().addAll(c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void clear() {
/*  121 */       synchronized (this.mutex) {
/*  122 */         delegate().clear();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/*  127 */       synchronized (this.mutex) {
/*  128 */         return delegate().contains(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  133 */       synchronized (this.mutex) {
/*  134 */         return delegate().containsAll(c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  139 */       synchronized (this.mutex) {
/*  140 */         return delegate().isEmpty();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  145 */     public Iterator<E> iterator() { return delegate().iterator(); }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  149 */       synchronized (this.mutex) {
/*  150 */         return delegate().remove(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  155 */       synchronized (this.mutex) {
/*  156 */         return delegate().removeAll(c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  161 */       synchronized (this.mutex) {
/*  162 */         return delegate().retainAll(c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  167 */       synchronized (this.mutex) {
/*  168 */         return delegate().size();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  173 */       synchronized (this.mutex) {
/*  174 */         return delegate().toArray();
/*      */       } 
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/*  179 */       synchronized (this.mutex) {
/*  180 */         return (T[])delegate().toArray(a);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*  188 */   static <E> Set<E> set(Set<E> set, @Nullable Object mutex) { return new SynchronizedSet(set, mutex, null); }
/*      */   
/*      */   @VisibleForTesting
/*      */   static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  194 */     private SynchronizedSet(Set<E> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*  198 */     Set<E> delegate() { return (Set)super.delegate(); }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  202 */       if (o == this) {
/*  203 */         return true;
/*      */       }
/*  205 */       synchronized (this.mutex) {
/*  206 */         return delegate().equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  211 */       synchronized (this.mutex) {
/*  212 */         return delegate().hashCode();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   private static <E> SortedSet<E> sortedSet(SortedSet<E> set, @Nullable Object mutex) { return new SynchronizedSortedSet(set, mutex); }
/*      */   
/*      */   private static class SynchronizedSortedSet<E>
/*      */     extends SynchronizedSet<E> implements SortedSet<E> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  227 */     SynchronizedSortedSet(SortedSet<E> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*  231 */     SortedSet<E> delegate() { return (SortedSet)super.delegate(); }
/*      */ 
/*      */     
/*      */     public Comparator<? super E> comparator() {
/*  235 */       synchronized (this.mutex) {
/*  236 */         return delegate().comparator();
/*      */       } 
/*      */     }
/*      */     
/*      */     public SortedSet<E> subSet(E fromElement, E toElement) {
/*  241 */       synchronized (this.mutex) {
/*  242 */         return Synchronized.sortedSet(delegate().subSet(fromElement, toElement), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public SortedSet<E> headSet(E toElement) {
/*  247 */       synchronized (this.mutex) {
/*  248 */         return Synchronized.sortedSet(delegate().headSet(toElement), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public SortedSet<E> tailSet(E fromElement) {
/*  253 */       synchronized (this.mutex) {
/*  254 */         return Synchronized.sortedSet(delegate().tailSet(fromElement), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public E first() {
/*  259 */       synchronized (this.mutex) {
/*  260 */         return (E)delegate().first();
/*      */       } 
/*      */     }
/*      */     
/*      */     public E last() {
/*  265 */       synchronized (this.mutex) {
/*  266 */         return (E)delegate().last();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  274 */   private static <E> List<E> list(List<E> list, @Nullable Object mutex) { return (list instanceof RandomAccess) ? new SynchronizedRandomAccessList(list, mutex) : new SynchronizedList(list, mutex); }
/*      */   
/*      */   private static class SynchronizedList<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  282 */     SynchronizedList(List<E> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*  286 */     List<E> delegate() { return (List)super.delegate(); }
/*      */ 
/*      */     
/*      */     public void add(int index, E element) {
/*  290 */       synchronized (this.mutex) {
/*  291 */         delegate().add(index, element);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends E> c) {
/*  296 */       synchronized (this.mutex) {
/*  297 */         return delegate().addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public E get(int index) {
/*  302 */       synchronized (this.mutex) {
/*  303 */         return (E)delegate().get(index);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int indexOf(Object o) {
/*  308 */       synchronized (this.mutex) {
/*  309 */         return delegate().indexOf(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int lastIndexOf(Object o) {
/*  314 */       synchronized (this.mutex) {
/*  315 */         return delegate().lastIndexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  320 */     public ListIterator<E> listIterator() { return delegate().listIterator(); }
/*      */ 
/*      */ 
/*      */     
/*  324 */     public ListIterator<E> listIterator(int index) { return delegate().listIterator(index); }
/*      */ 
/*      */     
/*      */     public E remove(int index) {
/*  328 */       synchronized (this.mutex) {
/*  329 */         return (E)delegate().remove(index);
/*      */       } 
/*      */     }
/*      */     
/*      */     public E set(int index, E element) {
/*  334 */       synchronized (this.mutex) {
/*  335 */         return (E)delegate().set(index, element);
/*      */       } 
/*      */     }
/*      */     
/*      */     public List<E> subList(int fromIndex, int toIndex) {
/*  340 */       synchronized (this.mutex) {
/*  341 */         return Synchronized.list(delegate().subList(fromIndex, toIndex), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  346 */       if (o == this) {
/*  347 */         return true;
/*      */       }
/*  349 */       synchronized (this.mutex) {
/*  350 */         return delegate().equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  355 */       synchronized (this.mutex) {
/*  356 */         return delegate().hashCode();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SynchronizedRandomAccessList<E>
/*      */     extends SynchronizedList<E>
/*      */     implements RandomAccess {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  366 */     SynchronizedRandomAccessList(List<E> list, @Nullable Object mutex) { super(list, mutex); }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  373 */   static <E> Multiset<E> multiset(Multiset<E> multiset, @Nullable Object mutex) { return new SynchronizedMultiset(multiset, mutex); }
/*      */   
/*      */   private static class SynchronizedMultiset<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements Multiset<E> {
/*      */     Set<E> elementSet;
/*      */     Set<Multiset.Entry<E>> entrySet;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  382 */     SynchronizedMultiset(Multiset<E> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*  386 */     Multiset<E> delegate() { return (Multiset)super.delegate(); }
/*      */ 
/*      */     
/*      */     public int count(Object o) {
/*  390 */       synchronized (this.mutex) {
/*  391 */         return delegate().count(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int add(E e, int n) {
/*  396 */       synchronized (this.mutex) {
/*  397 */         return delegate().add(e, n);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int remove(Object o, int n) {
/*  402 */       synchronized (this.mutex) {
/*  403 */         return delegate().remove(o, n);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int setCount(E element, int count) {
/*  408 */       synchronized (this.mutex) {
/*  409 */         return delegate().setCount(element, count);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean setCount(E element, int oldCount, int newCount) {
/*  414 */       synchronized (this.mutex) {
/*  415 */         return delegate().setCount(element, oldCount, newCount);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<E> elementSet() {
/*  420 */       synchronized (this.mutex) {
/*  421 */         if (this.elementSet == null) {
/*  422 */           this.elementSet = Synchronized.typePreservingSet(delegate().elementSet(), this.mutex);
/*      */         }
/*  424 */         return this.elementSet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<Multiset.Entry<E>> entrySet() {
/*  429 */       synchronized (this.mutex) {
/*  430 */         if (this.entrySet == null) {
/*  431 */           this.entrySet = Synchronized.typePreservingSet(delegate().entrySet(), this.mutex);
/*      */         }
/*  433 */         return this.entrySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  438 */       if (o == this) {
/*  439 */         return true;
/*      */       }
/*  441 */       synchronized (this.mutex) {
/*  442 */         return delegate().equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  447 */       synchronized (this.mutex) {
/*  448 */         return delegate().hashCode();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  457 */   static <K, V> Multimap<K, V> multimap(Multimap<K, V> multimap, @Nullable Object mutex) { return new SynchronizedMultimap(multimap, mutex); }
/*      */   
/*      */   private static class SynchronizedMultimap<K, V>
/*      */     extends SynchronizedObject
/*      */     implements Multimap<K, V>
/*      */   {
/*      */     Set<K> keySet;
/*      */     Collection<V> valuesCollection;
/*      */     Collection<Map.Entry<K, V>> entries;
/*      */     Map<K, Collection<V>> asMap;
/*      */     Multiset<K> keys;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  470 */     Multimap<K, V> delegate() { return (Multimap)super.delegate(); }
/*      */ 
/*      */ 
/*      */     
/*  474 */     SynchronizedMultimap(Multimap<K, V> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */     
/*      */     public int size() {
/*  478 */       synchronized (this.mutex) {
/*  479 */         return delegate().size();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  484 */       synchronized (this.mutex) {
/*  485 */         return delegate().isEmpty();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/*  490 */       synchronized (this.mutex) {
/*  491 */         return delegate().containsKey(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object value) {
/*  496 */       synchronized (this.mutex) {
/*  497 */         return delegate().containsValue(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsEntry(Object key, Object value) {
/*  502 */       synchronized (this.mutex) {
/*  503 */         return delegate().containsEntry(key, value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> get(K key) {
/*  508 */       synchronized (this.mutex) {
/*  509 */         return Synchronized.typePreservingCollection(delegate().get(key), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean put(K key, V value) {
/*  514 */       synchronized (this.mutex) {
/*  515 */         return delegate().put(key, value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean putAll(K key, Iterable<? extends V> values) {
/*  520 */       synchronized (this.mutex) {
/*  521 */         return delegate().putAll(key, values);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/*  526 */       synchronized (this.mutex) {
/*  527 */         return delegate().putAll(multimap);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
/*  532 */       synchronized (this.mutex) {
/*  533 */         return delegate().replaceValues(key, values);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object key, Object value) {
/*  538 */       synchronized (this.mutex) {
/*  539 */         return delegate().remove(key, value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> removeAll(Object key) {
/*  544 */       synchronized (this.mutex) {
/*  545 */         return delegate().removeAll(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void clear() {
/*  550 */       synchronized (this.mutex) {
/*  551 */         delegate().clear();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/*  556 */       synchronized (this.mutex) {
/*  557 */         if (this.keySet == null) {
/*  558 */           this.keySet = Synchronized.typePreservingSet(delegate().keySet(), this.mutex);
/*      */         }
/*  560 */         return this.keySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/*  565 */       synchronized (this.mutex) {
/*  566 */         if (this.valuesCollection == null) {
/*  567 */           this.valuesCollection = Synchronized.collection(delegate().values(), this.mutex);
/*      */         }
/*  569 */         return this.valuesCollection;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<Map.Entry<K, V>> entries() {
/*  574 */       synchronized (this.mutex) {
/*  575 */         if (this.entries == null) {
/*  576 */           this.entries = Synchronized.typePreservingCollection(delegate().entries(), this.mutex);
/*      */         }
/*  578 */         return this.entries;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Map<K, Collection<V>> asMap() {
/*  583 */       synchronized (this.mutex) {
/*  584 */         if (this.asMap == null) {
/*  585 */           this.asMap = new Synchronized.SynchronizedAsMap(delegate().asMap(), this.mutex);
/*      */         }
/*  587 */         return this.asMap;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Multiset<K> keys() {
/*  592 */       synchronized (this.mutex) {
/*  593 */         if (this.keys == null) {
/*  594 */           this.keys = Synchronized.multiset(delegate().keys(), this.mutex);
/*      */         }
/*  596 */         return this.keys;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  601 */       if (o == this) {
/*  602 */         return true;
/*      */       }
/*  604 */       synchronized (this.mutex) {
/*  605 */         return delegate().equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  610 */       synchronized (this.mutex) {
/*  611 */         return delegate().hashCode();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  620 */   static <K, V> ListMultimap<K, V> listMultimap(ListMultimap<K, V> multimap, @Nullable Object mutex) { return new SynchronizedListMultimap(multimap, mutex); }
/*      */   
/*      */   private static class SynchronizedListMultimap<K, V>
/*      */     extends SynchronizedMultimap<K, V>
/*      */     implements ListMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  627 */     SynchronizedListMultimap(ListMultimap<K, V> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */     
/*  630 */     ListMultimap<K, V> delegate() { return (ListMultimap)super.delegate(); }
/*      */     
/*      */     public List<V> get(K key) {
/*  633 */       synchronized (this.mutex) {
/*  634 */         return Synchronized.list(delegate().get(key), this.mutex);
/*      */       } 
/*      */     }
/*      */     public List<V> removeAll(Object key) {
/*  638 */       synchronized (this.mutex) {
/*  639 */         return delegate().removeAll(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public List<V> replaceValues(K key, Iterable<? extends V> values) {
/*  644 */       synchronized (this.mutex) {
/*  645 */         return delegate().replaceValues(key, values);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  653 */   static <K, V> SetMultimap<K, V> setMultimap(SetMultimap<K, V> multimap, @Nullable Object mutex) { return new SynchronizedSetMultimap(multimap, mutex); }
/*      */   
/*      */   private static class SynchronizedSetMultimap<K, V>
/*      */     extends SynchronizedMultimap<K, V>
/*      */     implements SetMultimap<K, V>
/*      */   {
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  662 */     SynchronizedSetMultimap(SetMultimap<K, V> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */     
/*  665 */     SetMultimap<K, V> delegate() { return (SetMultimap)super.delegate(); }
/*      */     
/*      */     public Set<V> get(K key) {
/*  668 */       synchronized (this.mutex) {
/*  669 */         return Synchronized.set(delegate().get(key), this.mutex);
/*      */       } 
/*      */     }
/*      */     public Set<V> removeAll(Object key) {
/*  673 */       synchronized (this.mutex) {
/*  674 */         return delegate().removeAll(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<V> replaceValues(K key, Iterable<? extends V> values) {
/*  679 */       synchronized (this.mutex) {
/*  680 */         return delegate().replaceValues(key, values);
/*      */       } 
/*      */     }
/*      */     public Set<Map.Entry<K, V>> entries() {
/*  684 */       synchronized (this.mutex) {
/*  685 */         if (this.entrySet == null) {
/*  686 */           this.entrySet = Synchronized.set(delegate().entries(), this.mutex);
/*      */         }
/*  688 */         return this.entrySet;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  696 */   static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> multimap, @Nullable Object mutex) { return new SynchronizedSortedSetMultimap(multimap, mutex); }
/*      */   
/*      */   private static class SynchronizedSortedSetMultimap<K, V>
/*      */     extends SynchronizedSetMultimap<K, V>
/*      */     implements SortedSetMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  703 */     SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */     
/*  706 */     SortedSetMultimap<K, V> delegate() { return (SortedSetMultimap)super.delegate(); }
/*      */     
/*      */     public SortedSet<V> get(K key) {
/*  709 */       synchronized (this.mutex) {
/*  710 */         return Synchronized.sortedSet(delegate().get(key), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedSet<V> removeAll(Object key) {
/*  714 */       synchronized (this.mutex) {
/*  715 */         return delegate().removeAll(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
/*  720 */       synchronized (this.mutex) {
/*  721 */         return delegate().replaceValues(key, values);
/*      */       } 
/*      */     }
/*      */     public Comparator<? super V> valueComparator() {
/*  725 */       synchronized (this.mutex) {
/*  726 */         return delegate().valueComparator();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <E> Collection<E> typePreservingCollection(Collection<E> collection, @Nullable Object mutex) {
/*  734 */     if (collection instanceof SortedSet) {
/*  735 */       return sortedSet((SortedSet)collection, mutex);
/*      */     }
/*  737 */     if (collection instanceof Set) {
/*  738 */       return set((Set)collection, mutex);
/*      */     }
/*  740 */     if (collection instanceof List) {
/*  741 */       return list((List)collection, mutex);
/*      */     }
/*  743 */     return collection(collection, mutex);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <E> Set<E> typePreservingSet(Set<E> set, @Nullable Object mutex) {
/*  748 */     if (set instanceof SortedSet) {
/*  749 */       return sortedSet((SortedSet)set, mutex);
/*      */     }
/*  751 */     return set(set, mutex);
/*      */   }
/*      */   
/*      */   private static class SynchronizedAsMapEntries<K, V>
/*      */     extends SynchronizedSet<Map.Entry<K, Collection<V>>>
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  759 */     SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/*  764 */       final Iterator<Map.Entry<K, Collection<V>>> iterator = super.iterator();
/*  765 */       return new ForwardingIterator<Map.Entry<K, Collection<V>>>()
/*      */         {
/*  767 */           protected Iterator<Map.Entry<K, Collection<V>>> delegate() { return iterator; }
/*      */ 
/*      */           
/*      */           public Map.Entry<K, Collection<V>> next() {
/*  771 */             final Map.Entry<K, Collection<V>> entry = (Map.Entry)iterator.next();
/*  772 */             return new ForwardingMapEntry<K, Collection<V>>()
/*      */               {
/*  774 */                 protected Map.Entry<K, Collection<V>> delegate() { return entry; }
/*      */ 
/*      */                 
/*  777 */                 public Collection<V> getValue() { return Synchronized.typePreservingCollection((Collection)entry.getValue(), Synchronized.SynchronizedAsMapEntries.this.mutex); }
/*      */               };
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object[] toArray() {
/*  787 */       synchronized (this.mutex) {
/*  788 */         return ObjectArrays.toArrayImpl(delegate());
/*      */       } 
/*      */     }
/*      */     public <T> T[] toArray(T[] array) {
/*  792 */       synchronized (this.mutex) {
/*  793 */         return (T[])ObjectArrays.toArrayImpl(delegate(), array);
/*      */       } 
/*      */     }
/*      */     public boolean contains(Object o) {
/*  797 */       synchronized (this.mutex) {
/*  798 */         return Maps.containsEntryImpl(delegate(), o);
/*      */       } 
/*      */     }
/*      */     public boolean containsAll(Collection<?> c) {
/*  802 */       synchronized (this.mutex) {
/*  803 */         return Collections2.containsAll(delegate(), c);
/*      */       } 
/*      */     }
/*      */     public boolean equals(Object o) {
/*  807 */       if (o == this) {
/*  808 */         return true;
/*      */       }
/*  810 */       synchronized (this.mutex) {
/*  811 */         return Collections2.setEquals(delegate(), o);
/*      */       } 
/*      */     }
/*      */     public boolean remove(Object o) {
/*  815 */       synchronized (this.mutex) {
/*  816 */         return Maps.removeEntryImpl(delegate(), o);
/*      */       } 
/*      */     }
/*      */     public boolean removeAll(Collection<?> c) {
/*  820 */       synchronized (this.mutex) {
/*  821 */         return Iterators.removeAll(delegate().iterator(), c);
/*      */       } 
/*      */     }
/*      */     public boolean retainAll(Collection<?> c) {
/*  825 */       synchronized (this.mutex) {
/*  826 */         return Iterators.retainAll(delegate().iterator(), c);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*  835 */   static <K, V> Map<K, V> map(Map<K, V> map, @Nullable Object mutex) { return new SynchronizedMap(map, mutex, null); }
/*      */   
/*      */   private static class SynchronizedMap<K, V>
/*      */     extends SynchronizedObject
/*      */     implements Map<K, V> {
/*      */     Set<K> keySet;
/*      */     Collection<V> values;
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  845 */     private SynchronizedMap(Map<K, V> delegate, @Nullable Object mutex) { super(delegate, mutex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  850 */     Map<K, V> delegate() { return (Map)super.delegate(); }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  854 */       synchronized (this.mutex) {
/*  855 */         delegate().clear();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/*  860 */       synchronized (this.mutex) {
/*  861 */         return delegate().containsKey(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object value) {
/*  866 */       synchronized (this.mutex) {
/*  867 */         return delegate().containsValue(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/*  872 */       synchronized (this.mutex) {
/*  873 */         if (this.entrySet == null) {
/*  874 */           this.entrySet = Synchronized.set(delegate().entrySet(), this.mutex);
/*      */         }
/*  876 */         return this.entrySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public V get(Object key) {
/*  881 */       synchronized (this.mutex) {
/*  882 */         return (V)delegate().get(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  887 */       synchronized (this.mutex) {
/*  888 */         return delegate().isEmpty();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/*  893 */       synchronized (this.mutex) {
/*  894 */         if (this.keySet == null) {
/*  895 */           this.keySet = Synchronized.set(delegate().keySet(), this.mutex);
/*      */         }
/*  897 */         return this.keySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public V put(K key, V value) {
/*  902 */       synchronized (this.mutex) {
/*  903 */         return (V)delegate().put(key, value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> map) {
/*  908 */       synchronized (this.mutex) {
/*  909 */         delegate().putAll(map);
/*      */       } 
/*      */     }
/*      */     
/*      */     public V remove(Object key) {
/*  914 */       synchronized (this.mutex) {
/*  915 */         return (V)delegate().remove(key);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  920 */       synchronized (this.mutex) {
/*  921 */         return delegate().size();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/*  926 */       synchronized (this.mutex) {
/*  927 */         if (this.values == null) {
/*  928 */           this.values = Synchronized.collection(delegate().values(), this.mutex);
/*      */         }
/*  930 */         return this.values;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  935 */       if (o == this) {
/*  936 */         return true;
/*      */       }
/*  938 */       synchronized (this.mutex) {
/*  939 */         return delegate().equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  944 */       synchronized (this.mutex) {
/*  945 */         return delegate().hashCode();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  953 */   static <K, V> BiMap<K, V> biMap(BiMap<K, V> bimap, @Nullable Object mutex) { return new SynchronizedBiMap(bimap, mutex, null, null); }
/*      */   
/*      */   @VisibleForTesting
/*      */   static class SynchronizedBiMap<K, V>
/*      */     extends SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {
/*      */     private Set<V> valueSet;
/*      */     private BiMap<V, K> inverse;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     private SynchronizedBiMap(BiMap<K, V> delegate, @Nullable Object mutex, @Nullable BiMap<V, K> inverse) {
/*  963 */       super(delegate, mutex, null);
/*  964 */       this.inverse = inverse;
/*      */     }
/*      */ 
/*      */     
/*  968 */     BiMap<K, V> delegate() { return (BiMap)super.delegate(); }
/*      */ 
/*      */     
/*      */     public Set<V> values() {
/*  972 */       synchronized (this.mutex) {
/*  973 */         if (this.valueSet == null) {
/*  974 */           this.valueSet = Synchronized.set(delegate().values(), this.mutex);
/*      */         }
/*  976 */         return this.valueSet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public V forcePut(K key, V value) {
/*  981 */       synchronized (this.mutex) {
/*  982 */         return (V)delegate().forcePut(key, value);
/*      */       } 
/*      */     }
/*      */     
/*      */     public BiMap<V, K> inverse() {
/*  987 */       synchronized (this.mutex) {
/*  988 */         if (this.inverse == null) {
/*  989 */           this.inverse = new SynchronizedBiMap(delegate().inverse(), this.mutex, this);
/*      */         }
/*      */         
/*  992 */         return this.inverse;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SynchronizedAsMap<K, V>
/*      */     extends SynchronizedMap<K, Collection<V>>
/*      */   {
/*      */     Set<Map.Entry<K, Collection<V>>> asMapEntrySet;
/*      */     Collection<Collection<V>> asMapValues;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/* 1005 */     SynchronizedAsMap(Map<K, Collection<V>> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */     
/*      */     public Collection<V> get(Object key) {
/* 1009 */       synchronized (this.mutex) {
/* 1010 */         Collection<V> collection = (Collection)super.get(key);
/* 1011 */         return (collection == null) ? null : Synchronized.typePreservingCollection(collection, this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 1017 */       synchronized (this.mutex) {
/* 1018 */         if (this.asMapEntrySet == null) {
/* 1019 */           this.asMapEntrySet = new Synchronized.SynchronizedAsMapEntries(delegate().entrySet(), this.mutex);
/*      */         }
/*      */         
/* 1022 */         return this.asMapEntrySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<Collection<V>> values() {
/* 1027 */       synchronized (this.mutex) {
/* 1028 */         if (this.asMapValues == null) {
/* 1029 */           this.asMapValues = new Synchronized.SynchronizedAsMapValues(delegate().values(), this.mutex);
/*      */         }
/*      */         
/* 1032 */         return this.asMapValues;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1038 */     public boolean containsValue(Object o) { return values().contains(o); }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SynchronizedAsMapValues<V>
/*      */     extends SynchronizedCollection<Collection<V>>
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */     
/* 1048 */     SynchronizedAsMapValues(Collection<Collection<V>> delegate, @Nullable Object mutex) { super(delegate, mutex, null); }
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<Collection<V>> iterator() {
/* 1053 */       final Iterator<Collection<V>> iterator = super.iterator();
/* 1054 */       return new ForwardingIterator<Collection<V>>()
/*      */         {
/* 1056 */           protected Iterator<Collection<V>> delegate() { return iterator; }
/*      */ 
/*      */           
/* 1059 */           public Collection<V> next() { return Synchronized.typePreservingCollection((Collection)iterator.next(), Synchronized.SynchronizedAsMapValues.this.mutex); }
/*      */         };
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Synchronized.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */