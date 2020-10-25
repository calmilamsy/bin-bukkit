/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Supplier;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
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
/*      */ 
/*      */ @GwtCompatible
/*      */ public final class Multimaps
/*      */ {
/*   99 */   public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory) { return new CustomMultimap(map, factory); }
/*      */   
/*      */   private static class CustomMultimap<K, V>
/*      */     extends AbstractMultimap<K, V> {
/*      */     Supplier<? extends Collection<V>> factory;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory) {
/*  107 */       super(map);
/*  108 */       this.factory = (Supplier)Preconditions.checkNotNull(factory);
/*      */     }
/*      */ 
/*      */     
/*  112 */     protected Collection<V> createCollection() { return (Collection)this.factory.get(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  120 */       stream.defaultWriteObject();
/*  121 */       stream.writeObject(this.factory);
/*  122 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  128 */       stream.defaultReadObject();
/*  129 */       this.factory = (Supplier)stream.readObject();
/*  130 */       Map<K, Collection<V>> map = (Map)stream.readObject();
/*  131 */       setMap(map);
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
/*      */ 
/*      */   
/*  176 */   public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory) { return new CustomListMultimap(map, factory); }
/*      */   
/*      */   private static class CustomListMultimap<K, V>
/*      */     extends AbstractListMultimap<K, V>
/*      */   {
/*      */     Supplier<? extends List<V>> factory;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory) {
/*  185 */       super(map);
/*  186 */       this.factory = (Supplier)Preconditions.checkNotNull(factory);
/*      */     }
/*      */ 
/*      */     
/*  190 */     protected List<V> createCollection() { return (List)this.factory.get(); }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  195 */       stream.defaultWriteObject();
/*  196 */       stream.writeObject(this.factory);
/*  197 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  203 */       stream.defaultReadObject();
/*  204 */       this.factory = (Supplier)stream.readObject();
/*  205 */       Map<K, Collection<V>> map = (Map)stream.readObject();
/*  206 */       setMap(map);
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
/*      */ 
/*      */   
/*  251 */   public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory) { return new CustomSetMultimap(map, factory); }
/*      */   
/*      */   private static class CustomSetMultimap<K, V>
/*      */     extends AbstractSetMultimap<K, V>
/*      */   {
/*      */     Supplier<? extends Set<V>> factory;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory) {
/*  260 */       super(map);
/*  261 */       this.factory = (Supplier)Preconditions.checkNotNull(factory);
/*      */     }
/*      */ 
/*      */     
/*  265 */     protected Set<V> createCollection() { return (Set)this.factory.get(); }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  270 */       stream.defaultWriteObject();
/*  271 */       stream.writeObject(this.factory);
/*  272 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  278 */       stream.defaultReadObject();
/*  279 */       this.factory = (Supplier)stream.readObject();
/*  280 */       Map<K, Collection<V>> map = (Map)stream.readObject();
/*  281 */       setMap(map);
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
/*      */ 
/*      */   
/*  326 */   public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory) { return new CustomSortedSetMultimap(map, factory); }
/*      */   
/*      */   private static class CustomSortedSetMultimap<K, V>
/*      */     extends AbstractSortedSetMultimap<K, V>
/*      */   {
/*      */     Supplier<? extends SortedSet<V>> factory;
/*      */     Comparator<? super V> valueComparator;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory) {
/*  336 */       super(map);
/*  337 */       this.factory = (Supplier)Preconditions.checkNotNull(factory);
/*  338 */       this.valueComparator = ((SortedSet)factory.get()).comparator();
/*      */     }
/*      */ 
/*      */     
/*  342 */     protected SortedSet<V> createCollection() { return (SortedSet)this.factory.get(); }
/*      */ 
/*      */ 
/*      */     
/*  346 */     public Comparator<? super V> valueComparator() { return this.valueComparator; }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  351 */       stream.defaultWriteObject();
/*  352 */       stream.writeObject(this.factory);
/*  353 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  359 */       stream.defaultReadObject();
/*  360 */       this.factory = (Supplier)stream.readObject();
/*  361 */       this.valueComparator = ((SortedSet)this.factory.get()).comparator();
/*  362 */       Map<K, Collection<V>> map = (Map)stream.readObject();
/*  363 */       setMap(map);
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
/*      */   public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> source, M dest) {
/*  379 */     Preconditions.checkNotNull(dest);
/*  380 */     for (Map.Entry<? extends V, ? extends K> entry : source.entries()) {
/*  381 */       dest.put(entry.getValue(), entry.getKey());
/*      */     }
/*  383 */     return dest;
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
/*  421 */   public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) { return Synchronized.multimap(multimap, null); }
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
/*  443 */   public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> delegate) { return new UnmodifiableMultimap(delegate); }
/*      */   
/*      */   private static class UnmodifiableMultimap<K, V>
/*      */     extends ForwardingMultimap<K, V>
/*      */     implements Serializable {
/*      */     final Multimap<K, V> delegate;
/*      */     Collection<Map.Entry<K, V>> entries;
/*      */     Multiset<K> keys;
/*      */     Set<K> keySet;
/*      */     Collection<V> values;
/*      */     Map<K, Collection<V>> map;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  456 */     UnmodifiableMultimap(Multimap<K, V> delegate) { this.delegate = (Multimap)Preconditions.checkNotNull(delegate); }
/*      */ 
/*      */ 
/*      */     
/*  460 */     protected Multimap<K, V> delegate() { return this.delegate; }
/*      */ 
/*      */ 
/*      */     
/*  464 */     public void clear() { throw new UnsupportedOperationException(); }
/*      */ 
/*      */     
/*      */     public Map<K, Collection<V>> asMap() {
/*  468 */       Map<K, Collection<V>> result = this.map;
/*  469 */       if (result == null) {
/*  470 */         final Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap(this.delegate.asMap());
/*      */         
/*  472 */         this.map = result = new ForwardingMap<K, Collection<V>>()
/*      */           {
/*  474 */             protected Map<K, Collection<V>> delegate() { return unmodifiableMap; }
/*      */             
/*      */             Set<Map.Entry<K, Collection<V>>> entrySet;
/*      */             Collection<Collection<V>> asMapValues;
/*      */             
/*      */             public Set<Map.Entry<K, Collection<V>>> entrySet() {
/*  480 */               Set<Map.Entry<K, Collection<V>>> result = super.entrySet;
/*  481 */               return (result == null) ? (super.entrySet = Multimaps.unmodifiableAsMapEntries(unmodifiableMap.entrySet())) : result;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public Collection<V> get(Object key) {
/*  488 */               Collection<V> collection = (Collection)unmodifiableMap.get(key);
/*  489 */               return (collection == null) ? null : Multimaps.unmodifiableValueCollection(collection);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public Collection<Collection<V>> values() {
/*  496 */               Collection<Collection<V>> result = super.asMapValues;
/*  497 */               return (result == null) ? (super.asMapValues = new Multimaps.UnmodifiableAsMapValues(unmodifiableMap.values())) : result;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean containsValue(Object o) {
/*  504 */               return super.values().contains(o);
/*      */             }
/*      */           };
/*      */       } 
/*  508 */       return result;
/*      */     }
/*      */     
/*      */     public Collection<Map.Entry<K, V>> entries() {
/*  512 */       Collection<Map.Entry<K, V>> result = this.entries;
/*  513 */       if (result == null) {
/*  514 */         this.entries = result = Multimaps.unmodifiableEntries(this.delegate.entries());
/*      */       }
/*  516 */       return result;
/*      */     }
/*      */ 
/*      */     
/*  520 */     public Collection<V> get(K key) { return Multimaps.unmodifiableValueCollection(this.delegate.get(key)); }
/*      */ 
/*      */     
/*      */     public Multiset<K> keys() {
/*  524 */       Multiset<K> result = this.keys;
/*  525 */       if (result == null) {
/*  526 */         this.keys = result = Multisets.unmodifiableMultiset(this.delegate.keys());
/*      */       }
/*  528 */       return result;
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/*  532 */       Set<K> result = this.keySet;
/*  533 */       if (result == null) {
/*  534 */         this.keySet = result = Collections.unmodifiableSet(this.delegate.keySet());
/*      */       }
/*  536 */       return result;
/*      */     }
/*      */ 
/*      */     
/*  540 */     public boolean put(K key, V value) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  545 */     public boolean putAll(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  550 */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/*  554 */     public boolean remove(Object key, Object value) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/*  558 */     public Collection<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  563 */     public Collection<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/*  567 */       Collection<V> result = this.values;
/*  568 */       if (result == null) {
/*  569 */         this.values = result = Collections.unmodifiableCollection(this.delegate.values());
/*      */       }
/*  571 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class UnmodifiableAsMapValues<V>
/*      */     extends ForwardingCollection<Collection<V>>
/*      */   {
/*      */     final Collection<Collection<V>> delegate;
/*      */     
/*  581 */     UnmodifiableAsMapValues(Collection<Collection<V>> delegate) { this.delegate = Collections.unmodifiableCollection(delegate); }
/*      */ 
/*      */     
/*  584 */     protected Collection<Collection<V>> delegate() { return this.delegate; }
/*      */     
/*      */     public Iterator<Collection<V>> iterator() {
/*  587 */       final Iterator<Collection<V>> iterator = this.delegate.iterator();
/*  588 */       return new Iterator<Collection<V>>()
/*      */         {
/*  590 */           public boolean hasNext() { return iterator.hasNext(); }
/*      */ 
/*      */           
/*  593 */           public Collection<V> next() { return Multimaps.unmodifiableValueCollection((Collection)iterator.next()); }
/*      */ 
/*      */           
/*  596 */           public void remove() { throw new UnsupportedOperationException(); }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*  601 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*      */ 
/*      */     
/*  604 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*      */ 
/*      */     
/*  607 */     public boolean contains(Object o) { return Iterators.contains(iterator(), o); }
/*      */ 
/*      */     
/*  610 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableListMultimap<K, V>
/*      */     extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  617 */     UnmodifiableListMultimap(ListMultimap<K, V> delegate) { super(delegate); }
/*      */ 
/*      */     
/*  620 */     public ListMultimap<K, V> delegate() { return (ListMultimap)super.delegate(); }
/*      */ 
/*      */     
/*  623 */     public List<V> get(K key) { return Collections.unmodifiableList(delegate().get(key)); }
/*      */ 
/*      */     
/*  626 */     public List<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/*  630 */     public List<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableSetMultimap<K, V>
/*      */     extends UnmodifiableMultimap<K, V>
/*      */     implements SetMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  638 */     UnmodifiableSetMultimap(SetMultimap<K, V> delegate) { super(delegate); }
/*      */ 
/*      */     
/*  641 */     public SetMultimap<K, V> delegate() { return (SetMultimap)super.delegate(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  648 */     public Set<V> get(K key) { return Collections.unmodifiableSet(delegate().get(key)); }
/*      */ 
/*      */     
/*  651 */     public Set<Map.Entry<K, V>> entries() { return Maps.unmodifiableEntrySet(delegate().entries()); }
/*      */ 
/*      */     
/*  654 */     public Set<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/*  658 */     public Set<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableSortedSetMultimap<K, V>
/*      */     extends UnmodifiableSetMultimap<K, V>
/*      */     implements SortedSetMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*  666 */     UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) { super(delegate); }
/*      */ 
/*      */     
/*  669 */     public SortedSetMultimap<K, V> delegate() { return (SortedSetMultimap)super.delegate(); }
/*      */ 
/*      */     
/*  672 */     public SortedSet<V> get(K key) { return Collections.unmodifiableSortedSet(delegate().get(key)); }
/*      */ 
/*      */     
/*  675 */     public SortedSet<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/*  679 */     public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */     
/*  682 */     public Comparator<? super V> valueComparator() { return delegate().valueComparator(); }
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
/*  701 */   public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> multimap) { return Synchronized.setMultimap(multimap, null); }
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
/*  724 */   public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> delegate) { return new UnmodifiableSetMultimap(delegate); }
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
/*  741 */   public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> multimap) { return Synchronized.sortedSetMultimap(multimap, null); }
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
/*  764 */   public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) { return new UnmodifiableSortedSetMultimap(delegate); }
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
/*  778 */   public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> multimap) { return Synchronized.listMultimap(multimap, null); }
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
/*  801 */   public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> delegate) { return new UnmodifiableListMultimap(delegate); }
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
/*      */   private static <V> Collection<V> unmodifiableValueCollection(Collection<V> collection) {
/*  814 */     if (collection instanceof SortedSet)
/*  815 */       return Collections.unmodifiableSortedSet((SortedSet)collection); 
/*  816 */     if (collection instanceof Set)
/*  817 */       return Collections.unmodifiableSet((Set)collection); 
/*  818 */     if (collection instanceof List) {
/*  819 */       return Collections.unmodifiableList((List)collection);
/*      */     }
/*  821 */     return Collections.unmodifiableCollection(collection);
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
/*      */   private static <K, V> Map.Entry<K, Collection<V>> unmodifiableAsMapEntry(final Map.Entry<K, Collection<V>> entry) {
/*  837 */     Preconditions.checkNotNull(entry);
/*  838 */     return new AbstractMapEntry<K, Collection<V>>()
/*      */       {
/*  840 */         public K getKey() { return (K)entry.getKey(); }
/*      */ 
/*      */ 
/*      */         
/*  844 */         public Collection<V> getValue() { return Multimaps.unmodifiableValueCollection((Collection)entry.getValue()); }
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
/*      */   private static <K, V> Collection<Map.Entry<K, V>> unmodifiableEntries(Collection<Map.Entry<K, V>> entries) {
/*  860 */     if (entries instanceof Set) {
/*  861 */       return Maps.unmodifiableEntrySet((Set)entries);
/*      */     }
/*  863 */     return new Maps.UnmodifiableEntries(Collections.unmodifiableCollection(entries));
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
/*  879 */   private static <K, V> Set<Map.Entry<K, Collection<V>>> unmodifiableAsMapEntries(Set<Map.Entry<K, Collection<V>>> asMapEntries) { return new UnmodifiableAsMapEntries(Collections.unmodifiableSet(asMapEntries)); }
/*      */ 
/*      */   
/*      */   static class UnmodifiableAsMapEntries<K, V>
/*      */     extends ForwardingSet<Map.Entry<K, Collection<V>>>
/*      */   {
/*      */     private final Set<Map.Entry<K, Collection<V>>> delegate;
/*      */ 
/*      */     
/*  888 */     UnmodifiableAsMapEntries(Set<Map.Entry<K, Collection<V>>> delegate) { this.delegate = delegate; }
/*      */ 
/*      */ 
/*      */     
/*  892 */     protected Set<Map.Entry<K, Collection<V>>> delegate() { return this.delegate; }
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/*  896 */       final Iterator<Map.Entry<K, Collection<V>>> iterator = this.delegate.iterator();
/*  897 */       return new ForwardingIterator<Map.Entry<K, Collection<V>>>()
/*      */         {
/*  899 */           protected Iterator<Map.Entry<K, Collection<V>>> delegate() { return iterator; }
/*      */ 
/*      */           
/*  902 */           public Map.Entry<K, Collection<V>> next() { return Multimaps.unmodifiableAsMapEntry((Map.Entry)iterator.next()); }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  908 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*      */ 
/*      */ 
/*      */     
/*  912 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*      */ 
/*      */ 
/*      */     
/*  916 */     public boolean contains(Object o) { return Maps.containsEntryImpl(delegate(), o); }
/*      */ 
/*      */ 
/*      */     
/*  920 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*      */ 
/*      */ 
/*      */     
/*  924 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
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
/*  946 */   public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) { return new MapMultimap(map); }
/*      */ 
/*      */   
/*      */   private static class MapMultimap<K, V>
/*      */     extends Object
/*      */     implements SetMultimap<K, V>, Serializable
/*      */   {
/*      */     final Map<K, V> map;
/*      */     Map<K, Collection<V>> asMap;
/*      */     
/*  956 */     MapMultimap(Map<K, V> map) { this.map = (Map)Preconditions.checkNotNull(map); }
/*      */ 
/*      */ 
/*      */     
/*  960 */     public int size() { return this.map.size(); }
/*      */ 
/*      */ 
/*      */     
/*  964 */     public boolean isEmpty() { return this.map.isEmpty(); }
/*      */ 
/*      */ 
/*      */     
/*  968 */     public boolean containsKey(Object key) { return this.map.containsKey(key); }
/*      */ 
/*      */ 
/*      */     
/*  972 */     public boolean containsValue(Object value) { return this.map.containsValue(value); }
/*      */ 
/*      */ 
/*      */     
/*  976 */     public boolean containsEntry(Object key, Object value) { return this.map.entrySet().contains(Maps.immutableEntry(key, value)); }
/*      */ 
/*      */     
/*      */     public Set<V> get(final K key) {
/*  980 */       return new AbstractSet<V>() {
/*      */           public Iterator<V> iterator() {
/*  982 */             return new Iterator<V>()
/*      */               {
/*      */                 int i;
/*      */                 
/*  986 */                 public boolean hasNext() { return (super.i == 0 && Multimaps.MapMultimap.this.map.containsKey(key)); }
/*      */ 
/*      */                 
/*      */                 public V next() {
/*  990 */                   if (!super.hasNext()) {
/*  991 */                     throw new NoSuchElementException();
/*      */                   }
/*  993 */                   super.i++;
/*  994 */                   return (V)Multimaps.MapMultimap.this.map.get(key);
/*      */                 }
/*      */                 
/*      */                 public void remove() {
/*  998 */                   Preconditions.checkState((super.i == 1));
/*  999 */                   super.i = -1;
/* 1000 */                   Multimaps.MapMultimap.this.map.remove(key);
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/* 1006 */           public int size() { return Multimaps.MapMultimap.this.map.containsKey(key) ? 1 : 0; }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1012 */     public boolean put(K key, V value) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/* 1016 */     public boolean putAll(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/* 1020 */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/* 1024 */     public Set<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */ 
/*      */     
/* 1028 */     public boolean remove(Object key, Object value) { return this.map.entrySet().remove(Maps.immutableEntry(key, value)); }
/*      */ 
/*      */     
/*      */     public Set<V> removeAll(Object key) {
/* 1032 */       Set<V> values = new HashSet<V>(2);
/* 1033 */       if (!this.map.containsKey(key)) {
/* 1034 */         return values;
/*      */       }
/* 1036 */       values.add(this.map.remove(key));
/* 1037 */       return values;
/*      */     }
/*      */ 
/*      */     
/* 1041 */     public void clear() { this.map.clear(); }
/*      */ 
/*      */ 
/*      */     
/* 1045 */     public Set<K> keySet() { return this.map.keySet(); }
/*      */ 
/*      */ 
/*      */     
/* 1049 */     public Multiset<K> keys() { return Multisets.forSet(this.map.keySet()); }
/*      */ 
/*      */ 
/*      */     
/* 1053 */     public Collection<V> values() { return this.map.values(); }
/*      */ 
/*      */ 
/*      */     
/* 1057 */     public Set<Map.Entry<K, V>> entries() { return this.map.entrySet(); }
/*      */ 
/*      */     
/*      */     public Map<K, Collection<V>> asMap() {
/* 1061 */       Map<K, Collection<V>> result = this.asMap;
/* 1062 */       if (result == null) {
/* 1063 */         this.asMap = result = new AsMap<K, Collection<V>>();
/*      */       }
/* 1065 */       return result;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 1069 */       if (object == this) {
/* 1070 */         return true;
/*      */       }
/* 1072 */       if (object instanceof Multimap) {
/* 1073 */         Multimap<?, ?> that = (Multimap)object;
/* 1074 */         return (size() == that.size() && asMap().equals(that.asMap()));
/*      */       } 
/* 1076 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1080 */     public int hashCode() { return this.map.hashCode(); }
/*      */ 
/*      */     
/* 1083 */     private static final Joiner.MapJoiner joiner = Joiner.on("], ").withKeyValueSeparator("=[").useForNull("null");
/*      */     private static final long serialVersionUID = 7845222491160860175L;
/*      */     
/*      */     public String toString() {
/* 1087 */       if (this.map.isEmpty()) {
/* 1088 */         return "{}";
/*      */       }
/* 1090 */       StringBuilder builder = (new StringBuilder(this.map.size() * 16)).append('{');
/* 1091 */       joiner.appendTo(builder, this.map);
/* 1092 */       return builder.append("]}").toString();
/*      */     }
/*      */     
/*      */     class AsMapEntries
/*      */       extends AbstractSet<Map.Entry<K, Collection<V>>>
/*      */     {
/* 1098 */       public int size() { return Multimaps.MapMultimap.this.map.size(); }
/*      */ 
/*      */       
/*      */       public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 1102 */         return new Iterator<Map.Entry<K, Collection<V>>>()
/*      */           {
/*      */             final Iterator<K> keys;
/*      */             
/* 1106 */             public boolean hasNext() { return super.keys.hasNext(); }
/*      */             
/*      */             public Map.Entry<K, Collection<V>> next() {
/* 1109 */               final K key = (K)super.keys.next();
/* 1110 */               return new AbstractMapEntry<K, Collection<V>>()
/*      */                 {
/* 1112 */                   public K getKey() { return (K)key; }
/*      */ 
/*      */                   
/* 1115 */                   public Collection<V> getValue() { return Multimaps.MapMultimap.AsMapEntries.this.this$0.get(key); }
/*      */                 };
/*      */             }
/*      */ 
/*      */             
/* 1120 */             public void remove() { super.keys.remove(); }
/*      */           };
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/* 1126 */         if (!(o instanceof Map.Entry)) {
/* 1127 */           return false;
/*      */         }
/* 1129 */         Map.Entry<?, ?> entry = (Map.Entry)o;
/* 1130 */         if (!(entry.getValue() instanceof Set)) {
/* 1131 */           return false;
/*      */         }
/* 1133 */         Set<?> set = (Set)entry.getValue();
/* 1134 */         return (set.size() == 1 && Multimaps.MapMultimap.this.containsEntry(entry.getKey(), set.iterator().next()));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1139 */         if (!(o instanceof Map.Entry)) {
/* 1140 */           return false;
/*      */         }
/* 1142 */         Map.Entry<?, ?> entry = (Map.Entry)o;
/* 1143 */         if (!(entry.getValue() instanceof Set)) {
/* 1144 */           return false;
/*      */         }
/* 1146 */         Set<?> set = (Set)entry.getValue();
/* 1147 */         return (set.size() == 1 && Multimaps.MapMultimap.this.map.entrySet().remove(Maps.immutableEntry(entry.getKey(), set.iterator().next())));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     class AsMap
/*      */       extends Maps.ImprovedAbstractMap<K, Collection<V>>
/*      */     {
/* 1156 */       protected Set<Map.Entry<K, Collection<V>>> createEntrySet() { return new Multimaps.MapMultimap.AsMapEntries(Multimaps.MapMultimap.this); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1162 */       public boolean containsKey(Object key) { return Multimaps.MapMultimap.this.map.containsKey(key); }
/*      */ 
/*      */ 
/*      */       
/*      */       public Collection<V> get(Object key) {
/* 1167 */         Collection<V> collection = Multimaps.MapMultimap.this.get(key);
/* 1168 */         return collection.isEmpty() ? null : collection;
/*      */       }
/*      */       
/*      */       public Collection<V> remove(Object key) {
/* 1172 */         Collection<V> collection = Multimaps.MapMultimap.this.removeAll(key);
/* 1173 */         return collection.isEmpty() ? null : collection;
/*      */       }
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
/*      */ 
/*      */   
/*      */   public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> values, Function<? super V, K> keyFunction) {
/* 1220 */     Preconditions.checkNotNull(keyFunction);
/* 1221 */     ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
/*      */     
/* 1223 */     for (V value : values) {
/* 1224 */       Preconditions.checkNotNull(value, values);
/* 1225 */       builder.put(keyFunction.apply(value), value);
/*      */     } 
/* 1227 */     return builder.build();
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Multimaps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */