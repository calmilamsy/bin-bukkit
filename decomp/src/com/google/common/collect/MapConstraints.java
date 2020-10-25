/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public final class MapConstraints
/*     */ {
/*  52 */   public static MapConstraint<Object, Object> notNull() { return NotNullMapConstraint.INSTANCE; }
/*     */   
/*     */   private enum NotNullMapConstraint
/*     */     implements MapConstraint<Object, Object>
/*     */   {
/*  57 */     INSTANCE;
/*     */     
/*     */     public void checkKeyValue(Object key, Object value) {
/*  60 */       Preconditions.checkNotNull(key);
/*  61 */       Preconditions.checkNotNull(value);
/*     */     }
/*     */ 
/*     */     
/*  65 */     public String toString() { return "Not null"; }
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
/*  83 */   public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedMap(map, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static <K, V> Multimap<K, V> constrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedMultimap(multimap, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedListMultimap(multimap, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static <K, V> SetMultimap<K, V> constrainedSetMultimap(SetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedSetMultimap(multimap, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static <K, V> SortedSetMultimap<K, V> constrainedSortedSetMultimap(SortedSetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedSortedSetMultimap(multimap, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> Map.Entry<K, V> constrainedEntry(final Map.Entry<K, V> entry, final MapConstraint<? super K, ? super V> constraint) {
/* 183 */     Preconditions.checkNotNull(entry);
/* 184 */     Preconditions.checkNotNull(constraint);
/* 185 */     return new ForwardingMapEntry<K, V>()
/*     */       {
/* 187 */         protected Map.Entry<K, V> delegate() { return entry; }
/*     */         
/*     */         public V setValue(V value) {
/* 190 */           constraint.checkKeyValue(getKey(), value);
/* 191 */           return (V)entry.setValue(value);
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
/*     */   private static <K, V> Map.Entry<K, Collection<V>> constrainedAsMapEntry(final Map.Entry<K, Collection<V>> entry, final MapConstraint<? super K, ? super V> constraint) {
/* 209 */     Preconditions.checkNotNull(entry);
/* 210 */     Preconditions.checkNotNull(constraint);
/* 211 */     return new ForwardingMapEntry<K, Collection<V>>()
/*     */       {
/* 213 */         protected Map.Entry<K, Collection<V>> delegate() { return entry; }
/*     */         
/*     */         public Collection<V> getValue() {
/* 216 */           return Constraints.constrainedTypePreservingCollection((Collection)entry.getValue(), new Constraint<V>()
/*     */               {
/*     */                 public V checkElement(V value) {
/* 219 */                   constraint.checkKeyValue(MapConstraints.null.this.getKey(), value);
/* 220 */                   return value;
/*     */                 }
/*     */               });
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
/*     */ 
/*     */   
/* 242 */   private static <K, V> Set<Map.Entry<K, Collection<V>>> constrainedAsMapEntries(Set<Map.Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedAsMapEntries(entries, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> Collection<Map.Entry<K, V>> constrainedEntries(Collection<Map.Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
/* 260 */     if (entries instanceof Set) {
/* 261 */       return constrainedEntrySet((Set)entries, constraint);
/*     */     }
/* 263 */     return new ConstrainedEntries(entries, constraint);
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
/* 283 */   private static <K, V> Set<Map.Entry<K, V>> constrainedEntrySet(Set<Map.Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedEntrySet(entries, constraint); }
/*     */ 
/*     */   
/*     */   static class ConstrainedMap<K, V>
/*     */     extends ForwardingMap<K, V>
/*     */   {
/*     */     private final Map<K, V> delegate;
/*     */     final MapConstraint<? super K, ? super V> constraint;
/*     */     private Set<Map.Entry<K, V>> entrySet;
/*     */     
/*     */     ConstrainedMap(Map<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
/* 294 */       this.delegate = (Map)Preconditions.checkNotNull(delegate);
/* 295 */       this.constraint = (MapConstraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/* 298 */     protected Map<K, V> delegate() { return this.delegate; }
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 301 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 302 */       if (result == null) {
/* 303 */         this.entrySet = result = MapConstraints.constrainedEntrySet(this.delegate.entrySet(), this.constraint);
/*     */       }
/*     */       
/* 306 */       return result;
/*     */     }
/*     */     public V put(K key, V value) {
/* 309 */       this.constraint.checkKeyValue(key, value);
/* 310 */       return (V)this.delegate.put(key, value);
/*     */     }
/*     */     
/* 313 */     public void putAll(Map<? extends K, ? extends V> map) { this.delegate.putAll(MapConstraints.checkMap(map, this.constraint)); }
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
/* 330 */   public static <K, V> BiMap<K, V> constrainedBiMap(BiMap<K, V> map, MapConstraint<? super K, ? super V> constraint) { return new ConstrainedBiMap(map, null, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ConstrainedBiMap<K, V>
/*     */     extends ConstrainedMap<K, V>
/*     */     implements BiMap<K, V>
/*     */   {
/*     */     ConstrainedBiMap(BiMap<K, V> delegate, @Nullable BiMap<V, K> inverse, MapConstraint<? super K, ? super V> constraint) {
/* 352 */       super(delegate, constraint);
/* 353 */       this.inverse = inverse;
/*     */     }
/*     */ 
/*     */     
/* 357 */     protected BiMap<K, V> delegate() { return (BiMap)super.delegate(); }
/*     */ 
/*     */     
/*     */     public V forcePut(K key, V value) {
/* 361 */       this.constraint.checkKeyValue(key, value);
/* 362 */       return (V)delegate().forcePut(key, value);
/*     */     }
/*     */     
/*     */     public BiMap<V, K> inverse() {
/* 366 */       if (this.inverse == null) {
/* 367 */         this.inverse = new ConstrainedBiMap(delegate().inverse(), this, new MapConstraints.InverseConstraint(this.constraint));
/*     */       }
/*     */       
/* 370 */       return this.inverse;
/*     */     }
/*     */ 
/*     */     
/* 374 */     public Set<V> values() { return delegate().values(); }
/*     */   }
/*     */   
/*     */   private static class InverseConstraint<K, V>
/*     */     extends Object
/*     */     implements MapConstraint<K, V>
/*     */   {
/*     */     final MapConstraint<? super V, ? super K> constraint;
/*     */     
/* 383 */     public InverseConstraint(MapConstraint<? super V, ? super K> constraint) { this.constraint = (MapConstraint)Preconditions.checkNotNull(constraint); }
/*     */ 
/*     */     
/* 386 */     public void checkKeyValue(K key, V value) { this.constraint.checkKeyValue(value, key); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstrainedMultimap<K, V>
/*     */     extends ForwardingMultimap<K, V>
/*     */   {
/*     */     final MapConstraint<? super K, ? super V> constraint;
/*     */     
/*     */     final Multimap<K, V> delegate;
/*     */     Collection<Map.Entry<K, V>> entries;
/*     */     Map<K, Collection<V>> asMap;
/*     */     
/*     */     public ConstrainedMultimap(Multimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
/* 400 */       this.delegate = (Multimap)Preconditions.checkNotNull(delegate);
/* 401 */       this.constraint = (MapConstraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */ 
/*     */     
/* 405 */     protected Multimap<K, V> delegate() { return this.delegate; }
/*     */ 
/*     */     
/*     */     public Map<K, Collection<V>> asMap() {
/* 409 */       Map<K, Collection<V>> result = this.asMap;
/* 410 */       if (result == null) {
/* 411 */         final Map<K, Collection<V>> asMapDelegate = this.delegate.asMap();
/*     */         
/* 413 */         this.asMap = result = new ForwardingMap<K, Collection<V>>()
/*     */           {
/*     */             Set<Map.Entry<K, Collection<V>>> entrySet;
/*     */             Collection<Collection<V>> values;
/*     */             
/* 418 */             protected Map<K, Collection<V>> delegate() { return asMapDelegate; }
/*     */ 
/*     */             
/*     */             public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 422 */               Set<Map.Entry<K, Collection<V>>> result = super.entrySet;
/* 423 */               if (result == null) {
/* 424 */                 super.entrySet = result = MapConstraints.constrainedAsMapEntries(asMapDelegate.entrySet(), MapConstraints.ConstrainedMultimap.this.constraint);
/*     */               }
/*     */               
/* 427 */               return result;
/*     */             }
/*     */ 
/*     */             
/*     */             public Collection<V> get(Object key) {
/*     */               try {
/* 433 */                 Collection<V> collection = super.this$0.get(key);
/* 434 */                 return collection.isEmpty() ? null : collection;
/* 435 */               } catch (ClassCastException e) {
/* 436 */                 return null;
/*     */               } 
/*     */             }
/*     */             
/*     */             public Collection<Collection<V>> values() {
/* 441 */               Collection<Collection<V>> result = super.values;
/* 442 */               if (result == null) {
/* 443 */                 super.values = result = new MapConstraints.ConstrainedAsMapValues<Collection<V>>(super.delegate().values(), super.entrySet());
/*     */               }
/*     */               
/* 446 */               return result;
/*     */             }
/*     */             
/*     */             public boolean containsValue(Object o) {
/* 450 */               return super.values().contains(o);
/*     */             }
/*     */           };
/*     */       } 
/* 454 */       return result;
/*     */     }
/*     */     
/*     */     public Collection<Map.Entry<K, V>> entries() {
/* 458 */       Collection<Map.Entry<K, V>> result = this.entries;
/* 459 */       if (result == null) {
/* 460 */         this.entries = result = MapConstraints.constrainedEntries(this.delegate.entries(), this.constraint);
/*     */       }
/* 462 */       return result;
/*     */     }
/*     */     
/*     */     public Collection<V> get(final K key) {
/* 466 */       return Constraints.constrainedTypePreservingCollection(this.delegate.get(key), new Constraint<V>()
/*     */           {
/*     */             public V checkElement(V value) {
/* 469 */               MapConstraints.ConstrainedMultimap.this.constraint.checkKeyValue(key, value);
/* 470 */               return value;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public boolean put(K key, V value) {
/* 476 */       this.constraint.checkKeyValue(key, value);
/* 477 */       return this.delegate.put(key, value);
/*     */     }
/*     */ 
/*     */     
/* 481 */     public boolean putAll(K key, Iterable<? extends V> values) { return this.delegate.putAll(key, MapConstraints.checkValues(key, values, this.constraint)); }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/* 486 */       boolean changed = false;
/* 487 */       for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
/* 488 */         changed |= put(entry.getKey(), entry.getValue());
/*     */       }
/* 490 */       return changed;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 495 */     public Collection<V> replaceValues(K key, Iterable<? extends V> values) { return this.delegate.replaceValues(key, MapConstraints.checkValues(key, values, this.constraint)); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ConstrainedAsMapValues<K, V>
/*     */     extends ForwardingCollection<Collection<V>>
/*     */   {
/*     */     final Collection<Collection<V>> delegate;
/*     */ 
/*     */     
/*     */     final Set<Map.Entry<K, Collection<V>>> entrySet;
/*     */ 
/*     */ 
/*     */     
/*     */     ConstrainedAsMapValues(Collection<Collection<V>> delegate, Set<Map.Entry<K, Collection<V>>> entrySet) {
/* 511 */       this.delegate = delegate;
/* 512 */       this.entrySet = entrySet;
/*     */     }
/*     */     
/* 515 */     protected Collection<Collection<V>> delegate() { return this.delegate; }
/*     */ 
/*     */     
/*     */     public Iterator<Collection<V>> iterator() {
/* 519 */       final Iterator<Map.Entry<K, Collection<V>>> iterator = this.entrySet.iterator();
/* 520 */       return new Iterator<Collection<V>>()
/*     */         {
/* 522 */           public boolean hasNext() { return iterator.hasNext(); }
/*     */ 
/*     */           
/* 525 */           public Collection<V> next() { return (Collection)((Map.Entry)iterator.next()).getValue(); }
/*     */ 
/*     */           
/* 528 */           public void remove() { iterator.remove(); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 534 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*     */ 
/*     */     
/* 537 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*     */ 
/*     */     
/* 540 */     public boolean contains(Object o) { return Iterators.contains(iterator(), o); }
/*     */ 
/*     */     
/* 543 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*     */ 
/*     */     
/* 546 */     public boolean remove(Object o) { return Iterables.remove(this, o); }
/*     */ 
/*     */     
/* 549 */     public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */     
/* 552 */     public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstrainedEntries<K, V>
/*     */     extends ForwardingCollection<Map.Entry<K, V>>
/*     */   {
/*     */     final MapConstraint<? super K, ? super V> constraint;
/*     */     
/*     */     final Collection<Map.Entry<K, V>> entries;
/*     */     
/*     */     ConstrainedEntries(Collection<Map.Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
/* 564 */       this.entries = entries;
/* 565 */       this.constraint = constraint;
/*     */     }
/*     */     
/* 568 */     protected Collection<Map.Entry<K, V>> delegate() { return this.entries; }
/*     */ 
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 572 */       final Iterator<Map.Entry<K, V>> iterator = this.entries.iterator();
/* 573 */       return new ForwardingIterator<Map.Entry<K, V>>()
/*     */         {
/* 575 */           public Map.Entry<K, V> next() { return MapConstraints.constrainedEntry((Map.Entry)iterator.next(), MapConstraints.ConstrainedEntries.this.constraint); }
/*     */ 
/*     */           
/* 578 */           protected Iterator<Map.Entry<K, V>> delegate() { return iterator; }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 586 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*     */ 
/*     */     
/* 589 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*     */ 
/*     */     
/* 592 */     public boolean contains(Object o) { return Maps.containsEntryImpl(delegate(), o); }
/*     */ 
/*     */     
/* 595 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*     */ 
/*     */     
/* 598 */     public boolean remove(Object o) { return Maps.removeEntryImpl(delegate(), o); }
/*     */ 
/*     */     
/* 601 */     public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */     
/* 604 */     public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ConstrainedEntrySet<K, V>
/*     */     extends ConstrainedEntries<K, V>
/*     */     implements Set<Map.Entry<K, V>>
/*     */   {
/* 613 */     ConstrainedEntrySet(Set<Map.Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) { super(entries, constraint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 619 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
/*     */ 
/*     */ 
/*     */     
/* 623 */     public int hashCode() { return Sets.hashCodeImpl(this); }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ConstrainedAsMapEntries<K, V>
/*     */     extends ForwardingSet<Map.Entry<K, Collection<V>>>
/*     */   {
/*     */     private final MapConstraint<? super K, ? super V> constraint;
/*     */     
/*     */     private final Set<Map.Entry<K, Collection<V>>> entries;
/*     */     
/*     */     ConstrainedAsMapEntries(Set<Map.Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
/* 635 */       this.entries = entries;
/* 636 */       this.constraint = constraint;
/*     */     }
/*     */ 
/*     */     
/* 640 */     protected Set<Map.Entry<K, Collection<V>>> delegate() { return this.entries; }
/*     */ 
/*     */     
/*     */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 644 */       final Iterator<Map.Entry<K, Collection<V>>> iterator = this.entries.iterator();
/* 645 */       return new ForwardingIterator<Map.Entry<K, Collection<V>>>()
/*     */         {
/* 647 */           public Map.Entry<K, Collection<V>> next() { return MapConstraints.constrainedAsMapEntry((Map.Entry)iterator.next(), super.this$0.constraint); }
/*     */ 
/*     */           
/* 650 */           protected Iterator<Map.Entry<K, Collection<V>>> delegate() { return iterator; }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 658 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*     */ 
/*     */ 
/*     */     
/* 662 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*     */ 
/*     */ 
/*     */     
/* 666 */     public boolean contains(Object o) { return Maps.containsEntryImpl(delegate(), o); }
/*     */ 
/*     */ 
/*     */     
/* 670 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*     */ 
/*     */ 
/*     */     
/* 674 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
/*     */ 
/*     */ 
/*     */     
/* 678 */     public int hashCode() { return Sets.hashCodeImpl(this); }
/*     */ 
/*     */ 
/*     */     
/* 682 */     public boolean remove(Object o) { return Maps.removeEntryImpl(delegate(), o); }
/*     */ 
/*     */ 
/*     */     
/* 686 */     public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */ 
/*     */     
/* 690 */     public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstrainedListMultimap<K, V>
/*     */     extends ConstrainedMultimap<K, V>
/*     */     implements ListMultimap<K, V>
/*     */   {
/* 698 */     ConstrainedListMultimap(ListMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) { super(delegate, constraint); }
/*     */ 
/*     */     
/* 701 */     public List<V> get(K key) { return (List)super.get(key); }
/*     */ 
/*     */     
/* 704 */     public List<V> removeAll(Object key) { return (List)super.removeAll(key); }
/*     */ 
/*     */ 
/*     */     
/* 708 */     public List<V> replaceValues(K key, Iterable<? extends V> values) { return (List)super.replaceValues(key, values); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstrainedSetMultimap<K, V>
/*     */     extends ConstrainedMultimap<K, V>
/*     */     implements SetMultimap<K, V>
/*     */   {
/* 716 */     ConstrainedSetMultimap(SetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) { super(delegate, constraint); }
/*     */ 
/*     */     
/* 719 */     public Set<V> get(K key) { return (Set)super.get(key); }
/*     */ 
/*     */     
/* 722 */     public Set<Map.Entry<K, V>> entries() { return (Set)super.entries(); }
/*     */ 
/*     */     
/* 725 */     public Set<V> removeAll(Object key) { return (Set)super.removeAll(key); }
/*     */ 
/*     */ 
/*     */     
/* 729 */     public Set<V> replaceValues(K key, Iterable<? extends V> values) { return (Set)super.replaceValues(key, values); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstrainedSortedSetMultimap<K, V>
/*     */     extends ConstrainedSetMultimap<K, V>
/*     */     implements SortedSetMultimap<K, V>
/*     */   {
/* 737 */     ConstrainedSortedSetMultimap(SortedSetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) { super(delegate, constraint); }
/*     */ 
/*     */     
/* 740 */     public SortedSet<V> get(K key) { return (SortedSet)super.get(key); }
/*     */ 
/*     */     
/* 743 */     public SortedSet<V> removeAll(Object key) { return (SortedSet)super.removeAll(key); }
/*     */ 
/*     */ 
/*     */     
/* 747 */     public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) { return (SortedSet)super.replaceValues(key, values); }
/*     */ 
/*     */     
/* 750 */     public Comparator<? super V> valueComparator() { return ((SortedSetMultimap)delegate()).valueComparator(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> Collection<V> checkValues(K key, Iterable<? extends V> values, MapConstraint<? super K, ? super V> constraint) {
/* 757 */     Collection<V> copy = Lists.newArrayList(values);
/* 758 */     for (V value : copy) {
/* 759 */       constraint.checkKeyValue(key, value);
/*     */     }
/* 761 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> Map<K, V> checkMap(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> constraint) {
/* 766 */     Map<K, V> copy = new LinkedHashMap<K, V>(map);
/* 767 */     for (Map.Entry<K, V> entry : copy.entrySet()) {
/* 768 */       constraint.checkKeyValue(entry.getKey(), entry.getValue());
/*     */     }
/* 770 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\MapConstraints.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */