/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public class ImmutableSortedMap<K, V>
/*     */   extends ImmutableSortedMapFauxverideShim<K, V>
/*     */   implements SortedMap<K, V>
/*     */ {
/*  63 */   private static final Comparator NATURAL_ORDER = Ordering.natural();
/*  64 */   private static final Map.Entry<?, ?>[] EMPTY_ARRAY = new Map.Entry[0];
/*     */ 
/*     */   
/*  67 */   private static final ImmutableMap<Object, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap(EMPTY_ARRAY, NATURAL_ORDER); private final Map.Entry<K, V>[] entries;
/*     */   private final Comparator<? super K> comparator;
/*     */   private final int fromIndex;
/*     */   private final int toIndex;
/*     */   private ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private ImmutableSortedSet<K> keySet;
/*     */   private ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  76 */   public static <K, V> ImmutableSortedMap<K, V> of() { return (ImmutableSortedMap)NATURAL_EMPTY_MAP; }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
/*  81 */     if (NATURAL_ORDER.equals(comparator)) {
/*  82 */       return of();
/*     */     }
/*  84 */     return new ImmutableSortedMap(EMPTY_ARRAY, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1) {
/*  93 */     Map.Entry[] arrayOfEntry = { entryOf(k1, v1) };
/*  94 */     return new ImmutableSortedMap(arrayOfEntry, Ordering.natural());
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
/* 106 */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2) { return (new Builder(Ordering.natural())).put(k1, v1).put(k2, v2).build(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) { return (new Builder(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).build(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) { return (new Builder(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) { return (new Builder(Ordering.natural())).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
/* 170 */     Ordering<K> naturalOrder = Ordering.natural();
/* 171 */     return copyOfInternal(map, naturalOrder);
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
/* 187 */   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) { return copyOfInternal(map, (Comparator)Preconditions.checkNotNull(comparator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> map) {
/* 204 */     Comparator<? super K> comparator = (map.comparator() == null) ? NATURAL_ORDER : map.comparator();
/*     */     
/* 206 */     return copyOfInternal(map, comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
/* 211 */     boolean sameComparator = false;
/* 212 */     if (map instanceof SortedMap) {
/* 213 */       SortedMap<?, ?> sortedMap = (SortedMap)map;
/* 214 */       Comparator<?> comparator2 = sortedMap.comparator();
/* 215 */       sameComparator = (comparator2 == null) ? ((comparator == NATURAL_ORDER)) : comparator.equals(comparator2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 220 */     if (sameComparator && map instanceof ImmutableSortedMap)
/*     */     {
/*     */ 
/*     */       
/* 224 */       return (ImmutableSortedMap)map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 229 */     List<Map.Entry<?, ?>> list = Lists.newArrayListWithCapacity(map.size());
/* 230 */     for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 231 */       list.add(entryOf(entry.getKey(), entry.getValue()));
/*     */     }
/* 233 */     Entry[] entryArray = (Entry[])list.toArray(new Map.Entry[list.size()]);
/*     */     
/* 235 */     if (!sameComparator) {
/* 236 */       sortEntries(entryArray, comparator);
/* 237 */       validateEntries(entryArray, comparator);
/*     */     } 
/*     */     
/* 240 */     return new ImmutableSortedMap(entryArray, comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sortEntries(Entry[] entryArray, final Comparator<?> comparator) {
/* 245 */     Comparator<Map.Entry<?, ?>> entryComparator = new Comparator<Map.Entry<?, ?>>() {
/*     */         public int compare(Map.Entry<?, ?> entry1, Map.Entry<?, ?> entry2) {
/* 247 */           return ImmutableSortedSet.unsafeCompare(comparator, entry1.getKey(), entry2.getKey());
/*     */         }
/*     */       };
/*     */     
/* 251 */     Arrays.sort(entryArray, entryComparator);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateEntries(Entry[] entryArray, Comparator<?> comparator) {
/* 256 */     for (int i = 1; i < entryArray.length; i++) {
/* 257 */       if (ImmutableSortedSet.unsafeCompare(comparator, entryArray[i - 1].getKey(), entryArray[i].getKey()) == 0)
/*     */       {
/* 259 */         throw new IllegalArgumentException("Duplicate keys in mappings " + entryArray[i - 1] + " and " + entryArray[i]);
/*     */       }
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
/* 277 */   public static <K extends Comparable<K>, V> Builder<K, V> naturalOrder() { return new Builder(Ordering.natural()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) { return new Builder(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public static <K extends Comparable<K>, V> Builder<K, V> reverseOrder() { return new Builder(Ordering.natural().reverse()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<K, V>
/*     */     extends ImmutableMap.Builder<K, V>
/*     */   {
/*     */     private final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     public Builder(Comparator<? super K> comparator) { this.comparator = (Comparator)Preconditions.checkNotNull(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> put(K key, V value) {
/* 340 */       this.entries.add(ImmutableMap.entryOf(key, value));
/* 341 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
/* 352 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 353 */         put(entry.getKey(), entry.getValue());
/*     */       }
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableSortedMap<K, V> build() {
/* 365 */       Entry[] entryArray = (Entry[])this.entries.toArray(new Map.Entry[this.entries.size()]);
/*     */       
/* 367 */       ImmutableSortedMap.sortEntries(entryArray, this.comparator);
/* 368 */       ImmutableSortedMap.validateEntries(entryArray, this.comparator);
/* 369 */       return new ImmutableSortedMap(entryArray, this.comparator);
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
/*     */   private ImmutableSortedMap(Entry[] entries, Comparator<? super K> comparator, int fromIndex, int toIndex) {
/* 382 */     Entry[] tmp = (Entry[])entries;
/* 383 */     this.entries = tmp;
/* 384 */     this.comparator = comparator;
/* 385 */     this.fromIndex = fromIndex;
/* 386 */     this.toIndex = toIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 391 */   ImmutableSortedMap(Entry[] entries, Comparator<? super K> comparator) { this(entries, comparator, 0, entries.length); }
/*     */ 
/*     */ 
/*     */   
/* 395 */   public int size() { return this.toIndex - this.fromIndex; }
/*     */   
/*     */   public V get(@Nullable Object key) {
/*     */     int i;
/* 399 */     if (key == null) {
/* 400 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 404 */       i = binarySearch(key);
/* 405 */     } catch (ClassCastException e) {
/* 406 */       return null;
/*     */     } 
/* 408 */     return (V)((i >= 0) ? this.entries[i].getValue() : null);
/*     */   }
/*     */   
/*     */   private int binarySearch(Object key) {
/* 412 */     int lower = this.fromIndex;
/* 413 */     int upper = this.toIndex - 1;
/*     */     
/* 415 */     while (lower <= upper) {
/* 416 */       int middle = lower + (upper - lower) / 2;
/* 417 */       int c = ImmutableSortedSet.unsafeCompare(this.comparator, key, this.entries[middle].getKey());
/*     */       
/* 419 */       if (c < 0) {
/* 420 */         upper = middle - 1; continue;
/* 421 */       }  if (c > 0) {
/* 422 */         lower = middle + 1; continue;
/*     */       } 
/* 424 */       return middle;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     return -lower - 1;
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 432 */     if (value == null) {
/* 433 */       return false;
/*     */     }
/* 435 */     for (int i = this.fromIndex; i < this.toIndex; i++) {
/* 436 */       if (this.entries[i].getValue().equals(value)) {
/* 437 */         return true;
/*     */       }
/*     */     } 
/* 440 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/* 450 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/* 451 */     return (es == null) ? (this.entrySet = createEntrySet()) : es;
/*     */   }
/*     */ 
/*     */   
/* 455 */   private ImmutableSet<Map.Entry<K, V>> createEntrySet() { return isEmpty() ? ImmutableSet.of() : new EntrySet(this); }
/*     */ 
/*     */   
/*     */   private static class EntrySet<K, V>
/*     */     extends ImmutableSet<Map.Entry<K, V>>
/*     */   {
/*     */     final ImmutableSortedMap<K, V> map;
/*     */ 
/*     */     
/* 464 */     EntrySet(ImmutableSortedMap<K, V> map) { this.map = map; }
/*     */ 
/*     */ 
/*     */     
/* 468 */     public int size() { return this.map.size(); }
/*     */ 
/*     */ 
/*     */     
/* 472 */     public UnmodifiableIterator<Map.Entry<K, V>> iterator() { return Iterators.forArray(this.map.entries, this.map.fromIndex, size()); }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 476 */       if (target instanceof Map.Entry) {
/* 477 */         Map.Entry<?, ?> entry = (Map.Entry)target;
/* 478 */         V mappedValue = (V)this.map.get(entry.getKey());
/* 479 */         return (mappedValue != null && mappedValue.equals(entry.getValue()));
/*     */       } 
/* 481 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 485 */     Object writeReplace() { return new ImmutableSortedMap.EntrySetSerializedForm(this.map); }
/*     */   }
/*     */   
/*     */   private static class EntrySetSerializedForm<K, V> extends Object implements Serializable {
/*     */     final ImmutableSortedMap<K, V> map;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 492 */     EntrySetSerializedForm(ImmutableSortedMap<K, V> map) { this.map = map; }
/*     */ 
/*     */     
/* 495 */     Object readResolve() { return this.map.entrySet(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSortedSet<K> keySet() {
/* 506 */     ImmutableSortedSet<K> ks = this.keySet;
/* 507 */     return (ks == null) ? (this.keySet = createKeySet()) : ks;
/*     */   }
/*     */   
/*     */   private ImmutableSortedSet<K> createKeySet() {
/* 511 */     if (isEmpty()) {
/* 512 */       return ImmutableSortedSet.emptySet(this.comparator);
/*     */     }
/*     */ 
/*     */     
/* 516 */     Object[] array = new Object[size()];
/* 517 */     for (int i = this.fromIndex; i < this.toIndex; i++) {
/* 518 */       array[i - this.fromIndex] = this.entries[i].getKey();
/*     */     }
/* 520 */     return new RegularImmutableSortedSet(array, this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/* 530 */     ImmutableCollection<V> v = this.values;
/* 531 */     return (v == null) ? (this.values = new Values(this)) : v;
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V>
/*     */   {
/*     */     private final ImmutableSortedMap<?, V> map;
/*     */     
/* 539 */     Values(ImmutableSortedMap<?, V> map) { this.map = map; }
/*     */ 
/*     */ 
/*     */     
/* 543 */     public int size() { return this.map.size(); }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 547 */       return new AbstractIterator<V>() {
/*     */           int index;
/*     */           
/* 550 */           protected V computeNext() { return (V)((super.index < super.this$0.map.toIndex) ? super.this$0.map.entries[super.index++].getValue() : endOfData()); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 558 */     public boolean contains(Object target) { return this.map.containsValue(target); }
/*     */ 
/*     */ 
/*     */     
/* 562 */     Object writeReplace() { return new ImmutableSortedMap.ValuesSerializedForm(this.map); }
/*     */   }
/*     */   
/*     */   private static class ValuesSerializedForm<V> extends Object implements Serializable {
/*     */     final ImmutableSortedMap<?, V> map;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 569 */     ValuesSerializedForm(ImmutableSortedMap<?, V> map) { this.map = map; }
/*     */ 
/*     */     
/* 572 */     Object readResolve() { return this.map.values(); }
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
/* 584 */   public Comparator<? super K> comparator() { return this.comparator; }
/*     */ 
/*     */   
/*     */   public K firstKey() {
/* 588 */     if (isEmpty()) {
/* 589 */       throw new NoSuchElementException();
/*     */     }
/* 591 */     return (K)this.entries[this.fromIndex].getKey();
/*     */   }
/*     */   
/*     */   public K lastKey() {
/* 595 */     if (isEmpty()) {
/* 596 */       throw new NoSuchElementException();
/*     */     }
/* 598 */     return (K)this.entries[this.toIndex - 1].getKey();
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
/*     */   public ImmutableSortedMap<K, V> headMap(K toKey) {
/* 612 */     int newToIndex = findSubmapIndex(Preconditions.checkNotNull(toKey));
/* 613 */     return createSubmap(this.fromIndex, newToIndex);
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
/*     */   public ImmutableSortedMap<K, V> subMap(K fromKey, K toKey) {
/* 630 */     Preconditions.checkNotNull(fromKey);
/* 631 */     Preconditions.checkNotNull(toKey);
/* 632 */     Preconditions.checkArgument((this.comparator.compare(fromKey, toKey) <= 0));
/* 633 */     int newFromIndex = findSubmapIndex(fromKey);
/* 634 */     int newToIndex = findSubmapIndex(toKey);
/* 635 */     return createSubmap(newFromIndex, newToIndex);
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
/*     */   public ImmutableSortedMap<K, V> tailMap(K fromKey) {
/* 649 */     int newFromIndex = findSubmapIndex(Preconditions.checkNotNull(fromKey));
/* 650 */     return createSubmap(newFromIndex, this.toIndex);
/*     */   }
/*     */   
/*     */   private int findSubmapIndex(K key) {
/* 654 */     int index = binarySearch(key);
/* 655 */     return (index >= 0) ? index : (-index - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private ImmutableSortedMap<K, V> createSubmap(int newFromIndex, int newToIndex) {
/* 660 */     if (newFromIndex < newToIndex) {
/* 661 */       return new ImmutableSortedMap(this.entries, this.comparator, newFromIndex, newToIndex);
/*     */     }
/*     */     
/* 664 */     return emptyMap(this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializedForm
/*     */     extends ImmutableMap.SerializedForm
/*     */   {
/*     */     private final Comparator<Object> comparator;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
/* 678 */       super(sortedMap);
/* 679 */       this.comparator = sortedMap.comparator();
/*     */     }
/*     */     Object readResolve() {
/* 682 */       ImmutableSortedMap.Builder<Object, Object> builder = new ImmutableSortedMap.Builder<Object, Object>(this.comparator);
/* 683 */       return createMap(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 689 */   Object writeReplace() { return new SerializedForm(this); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableSortedMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */