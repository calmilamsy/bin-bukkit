/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.EnumMap;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.ConcurrentMap;
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
/*      */ @GwtCompatible
/*      */ public final class Maps
/*      */ {
/*   77 */   public static <K, V> HashMap<K, V> newHashMap() { return new HashMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) { return new HashMap(capacity(expectedSize)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int capacity(int expectedSize) {
/*  108 */     Preconditions.checkArgument((expectedSize >= 0));
/*  109 */     return Math.max(expectedSize * 2, 16);
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
/*  128 */   public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) { return new HashMap(map); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() { return new LinkedHashMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) { return new LinkedHashMap(map); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  176 */   public static <K, V> ConcurrentMap<K, V> newConcurrentMap() { return (new MapMaker()).makeMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() { return new TreeMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) { return new TreeMap(map); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  226 */   public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> comparator) { return new TreeMap(comparator); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  236 */   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) { return new EnumMap((Class)Preconditions.checkNotNull(type)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  250 */   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) { return new EnumMap(map); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  259 */   public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() { return new IdentityHashMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> bimap) { return Synchronized.biMap(bimap, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
/*  312 */     Map<K, V> onlyOnLeft = newHashMap();
/*  313 */     Map<K, V> onlyOnRight = new HashMap<K, V>(right);
/*  314 */     Map<K, V> onBoth = newHashMap();
/*  315 */     Map<K, MapDifference.ValueDifference<V>> differences = newHashMap();
/*  316 */     boolean eq = true;
/*      */     
/*  318 */     for (Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
/*  319 */       K leftKey = (K)entry.getKey();
/*  320 */       V leftValue = (V)entry.getValue();
/*  321 */       if (right.containsKey(leftKey)) {
/*  322 */         V rightValue = (V)onlyOnRight.remove(leftKey);
/*  323 */         if (Objects.equal(leftValue, rightValue)) {
/*  324 */           onBoth.put(leftKey, leftValue); continue;
/*      */         } 
/*  326 */         eq = false;
/*  327 */         differences.put(leftKey, new ValueDifferenceImpl(leftValue, rightValue));
/*      */         
/*      */         continue;
/*      */       } 
/*  331 */       eq = false;
/*  332 */       onlyOnLeft.put(leftKey, leftValue);
/*      */     } 
/*      */ 
/*      */     
/*  336 */     boolean areEqual = (eq && onlyOnRight.isEmpty());
/*  337 */     return new MapDifferenceImpl(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class MapDifferenceImpl<K, V>
/*      */     extends Object
/*      */     implements MapDifference<K, V>
/*      */   {
/*      */     final boolean areEqual;
/*      */     final Map<K, V> onlyOnLeft;
/*      */     final Map<K, V> onlyOnRight;
/*      */     final Map<K, V> onBoth;
/*      */     final Map<K, MapDifference.ValueDifference<V>> differences;
/*      */     
/*      */     MapDifferenceImpl(boolean areEqual, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
/*  352 */       this.areEqual = areEqual;
/*  353 */       this.onlyOnLeft = Collections.unmodifiableMap(onlyOnLeft);
/*  354 */       this.onlyOnRight = Collections.unmodifiableMap(onlyOnRight);
/*  355 */       this.onBoth = Collections.unmodifiableMap(onBoth);
/*  356 */       this.differences = Collections.unmodifiableMap(differences);
/*      */     }
/*      */ 
/*      */     
/*  360 */     public boolean areEqual() { return this.areEqual; }
/*      */ 
/*      */ 
/*      */     
/*  364 */     public Map<K, V> entriesOnlyOnLeft() { return this.onlyOnLeft; }
/*      */ 
/*      */ 
/*      */     
/*  368 */     public Map<K, V> entriesOnlyOnRight() { return this.onlyOnRight; }
/*      */ 
/*      */ 
/*      */     
/*  372 */     public Map<K, V> entriesInCommon() { return this.onBoth; }
/*      */ 
/*      */ 
/*      */     
/*  376 */     public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() { return this.differences; }
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  380 */       if (object == this) {
/*  381 */         return true;
/*      */       }
/*  383 */       if (object instanceof MapDifference) {
/*  384 */         MapDifference<?, ?> other = (MapDifference)object;
/*  385 */         return (entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) && entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) && entriesInCommon().equals(other.entriesInCommon()) && entriesDiffering().equals(other.entriesDiffering()));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  390 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  394 */     public int hashCode() { return Objects.hashCode(new Object[] { entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering() }); }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  399 */       if (this.areEqual) {
/*  400 */         return "equal";
/*      */       }
/*      */       
/*  403 */       StringBuilder result = new StringBuilder("not equal");
/*  404 */       if (!this.onlyOnLeft.isEmpty()) {
/*  405 */         result.append(": only on left=").append(this.onlyOnLeft);
/*      */       }
/*  407 */       if (!this.onlyOnRight.isEmpty()) {
/*  408 */         result.append(": only on right=").append(this.onlyOnRight);
/*      */       }
/*  410 */       if (!this.differences.isEmpty()) {
/*  411 */         result.append(": value differences=").append(this.differences);
/*      */       }
/*  413 */       return result.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   static class ValueDifferenceImpl<V>
/*      */     extends Object
/*      */     implements MapDifference.ValueDifference<V> {
/*      */     private final V left;
/*      */     private final V right;
/*      */     
/*      */     ValueDifferenceImpl(@Nullable V left, @Nullable V right) {
/*  424 */       this.left = left;
/*  425 */       this.right = right;
/*      */     }
/*      */ 
/*      */     
/*  429 */     public V leftValue() { return (V)this.left; }
/*      */ 
/*      */ 
/*      */     
/*  433 */     public V rightValue() { return (V)this.right; }
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  437 */       if (object instanceof MapDifference.ValueDifference) {
/*  438 */         MapDifference.ValueDifference<?> that = (MapDifference.ValueDifference)object;
/*      */         
/*  440 */         return (Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue()));
/*      */       } 
/*      */       
/*  443 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  447 */     public int hashCode() { return Objects.hashCode(new Object[] { this.left, this.right }); }
/*      */ 
/*      */ 
/*      */     
/*  451 */     public String toString() { return "(" + this.left + ", " + this.right + ")"; }
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
/*      */   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
/*  473 */     Preconditions.checkNotNull(keyFunction);
/*  474 */     ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
/*  475 */     for (V value : values) {
/*  476 */       builder.put(keyFunction.apply(value), value);
/*      */     }
/*  478 */     return builder.build();
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
/*      */   public static ImmutableMap<String, String> fromProperties(Properties properties) {
/*  497 */     ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
/*      */     
/*  499 */     for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
/*  500 */       String key = (String)e.nextElement();
/*  501 */       builder.put(key, properties.getProperty(key));
/*      */     } 
/*      */     
/*  504 */     return builder.build();
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
/*  518 */   public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K key, @Nullable V value) { return new ImmutableEntry(key, value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  531 */   static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> entrySet) { return new UnmodifiableEntrySet(Collections.unmodifiableSet(entrySet)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<K, V> entry) {
/*  546 */     Preconditions.checkNotNull(entry);
/*  547 */     return new AbstractMapEntry<K, V>()
/*      */       {
/*  549 */         public K getKey() { return (K)entry.getKey(); }
/*      */ 
/*      */         
/*  552 */         public V getValue() { return (V)entry.getValue(); }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableEntries<K, V>
/*      */     extends ForwardingCollection<Map.Entry<K, V>>
/*      */   {
/*      */     private final Collection<Map.Entry<K, V>> entries;
/*      */ 
/*      */     
/*  563 */     UnmodifiableEntries(Collection<Map.Entry<K, V>> entries) { this.entries = entries; }
/*      */ 
/*      */ 
/*      */     
/*  567 */     protected Collection<Map.Entry<K, V>> delegate() { return this.entries; }
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/*  571 */       final Iterator<Map.Entry<K, V>> delegate = super.iterator();
/*  572 */       return new ForwardingIterator<Map.Entry<K, V>>()
/*      */         {
/*  574 */           public Map.Entry<K, V> next() { return Maps.unmodifiableEntry((Map.Entry)super.next()); }
/*      */ 
/*      */           
/*  577 */           protected Iterator<Map.Entry<K, V>> delegate() { return delegate; }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  585 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*      */ 
/*      */ 
/*      */     
/*  589 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*      */ 
/*      */ 
/*      */     
/*  593 */     public boolean contains(Object o) { return Maps.containsEntryImpl(delegate(), o); }
/*      */ 
/*      */ 
/*      */     
/*  597 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableEntrySet<K, V>
/*      */     extends UnmodifiableEntries<K, V>
/*      */     implements Set<Map.Entry<K, V>>
/*      */   {
/*  606 */     UnmodifiableEntrySet(Set<Map.Entry<K, V>> entries) { super(entries); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
/*      */ 
/*      */ 
/*      */     
/*  616 */     public int hashCode() { return Sets.hashCodeImpl(this); }
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
/*  635 */   public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> bimap) { return new UnmodifiableBiMap(bimap, null); }
/*      */ 
/*      */   
/*      */   private static class UnmodifiableBiMap<K, V>
/*      */     extends ForwardingMap<K, V>
/*      */     implements BiMap<K, V>, Serializable
/*      */   {
/*      */     final Map<K, V> unmodifiableMap;
/*      */     final BiMap<? extends K, ? extends V> delegate;
/*      */     BiMap<V, K> inverse;
/*      */     Set<V> values;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableBiMap(BiMap<? extends K, ? extends V> delegate, @Nullable BiMap<V, K> inverse) {
/*  649 */       this.unmodifiableMap = Collections.unmodifiableMap(delegate);
/*  650 */       this.delegate = delegate;
/*  651 */       this.inverse = inverse;
/*      */     }
/*      */ 
/*      */     
/*  655 */     protected Map<K, V> delegate() { return this.unmodifiableMap; }
/*      */ 
/*      */ 
/*      */     
/*  659 */     public V forcePut(K key, V value) { throw new UnsupportedOperationException(); }
/*      */ 
/*      */     
/*      */     public BiMap<V, K> inverse() {
/*  663 */       BiMap<V, K> result = this.inverse;
/*  664 */       return (result == null) ? (this.inverse = new UnmodifiableBiMap(this.delegate.inverse(), this)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<V> values() {
/*  670 */       Set<V> result = this.values;
/*  671 */       return (result == null) ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
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
/*      */   static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
/*  693 */     if (!(o instanceof Map.Entry)) {
/*  694 */       return false;
/*      */     }
/*  696 */     return c.contains(unmodifiableEntry((Map.Entry)o));
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
/*      */   static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
/*  713 */     if (!(o instanceof Map.Entry)) {
/*  714 */       return false;
/*      */     }
/*  716 */     return c.remove(unmodifiableEntry((Map.Entry)o));
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
/*  757 */   public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, Function<? super V1, V2> function) { return new TransformedValuesMap(fromMap, function); }
/*      */   
/*      */   private static class TransformedValuesMap<K, V1, V2>
/*      */     extends AbstractMap<K, V2>
/*      */   {
/*      */     final Map<K, V1> fromMap;
/*      */     final Function<? super V1, V2> function;
/*      */     EntrySet entrySet;
/*      */     
/*      */     TransformedValuesMap(Map<K, V1> fromMap, Function<? super V1, V2> function) {
/*  767 */       this.fromMap = (Map)Preconditions.checkNotNull(fromMap);
/*  768 */       this.function = (Function)Preconditions.checkNotNull(function);
/*      */     }
/*      */ 
/*      */     
/*  772 */     public int size() { return this.fromMap.size(); }
/*      */ 
/*      */ 
/*      */     
/*  776 */     public boolean containsKey(Object key) { return this.fromMap.containsKey(key); }
/*      */ 
/*      */     
/*      */     public V2 get(Object key) {
/*  780 */       V1 value = (V1)this.fromMap.get(key);
/*  781 */       return (V2)((value != null || this.fromMap.containsKey(key)) ? this.function.apply(value) : null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  786 */     public V2 remove(Object key) { return (V2)(this.fromMap.containsKey(key) ? this.function.apply(this.fromMap.remove(key)) : null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  792 */     public void clear() { this.fromMap.clear(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V2>> entrySet() {
/*  798 */       EntrySet result = this.entrySet;
/*  799 */       if (result == null) {
/*  800 */         this.entrySet = result = new EntrySet();
/*      */       }
/*  802 */       return result;
/*      */     }
/*      */     
/*      */     class EntrySet
/*      */       extends AbstractSet<Map.Entry<K, V2>> {
/*  807 */       public int size() { return Maps.TransformedValuesMap.this.size(); }
/*      */ 
/*      */       
/*      */       public Iterator<Map.Entry<K, V2>> iterator() {
/*  811 */         final Iterator<Map.Entry<K, V1>> mapIterator = Maps.TransformedValuesMap.this.fromMap.entrySet().iterator();
/*      */ 
/*      */         
/*  814 */         return new Iterator<Map.Entry<K, V2>>()
/*      */           {
/*  816 */             public boolean hasNext() { return mapIterator.hasNext(); }
/*      */ 
/*      */             
/*      */             public Map.Entry<K, V2> next() {
/*  820 */               final Map.Entry<K, V1> entry = (Map.Entry)mapIterator.next();
/*  821 */               return new AbstractMapEntry<K, V2>()
/*      */                 {
/*  823 */                   public K getKey() { return (K)entry.getKey(); }
/*      */ 
/*      */                   
/*  826 */                   public V2 getValue() { return (V2)Maps.TransformedValuesMap.this.function.apply(entry.getValue()); }
/*      */                 };
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  832 */             public void remove() { mapIterator.remove(); }
/*      */           };
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  838 */       public void clear() { Maps.TransformedValuesMap.this.fromMap.clear(); }
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/*  842 */         if (!(o instanceof Map.Entry)) {
/*  843 */           return false;
/*      */         }
/*  845 */         Map.Entry<?, ?> entry = (Map.Entry)o;
/*  846 */         Object entryKey = entry.getKey();
/*  847 */         Object entryValue = entry.getValue();
/*  848 */         V2 mapValue = (V2)Maps.TransformedValuesMap.this.get(entryKey);
/*  849 */         if (mapValue != null) {
/*  850 */           return mapValue.equals(entryValue);
/*      */         }
/*  852 */         return (entryValue == null && Maps.TransformedValuesMap.this.containsKey(entryKey));
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/*  856 */         if (contains(o)) {
/*  857 */           Map.Entry<?, ?> entry = (Map.Entry)o;
/*  858 */           Object key = entry.getKey();
/*  859 */           Maps.TransformedValuesMap.this.fromMap.remove(key);
/*  860 */           return true;
/*      */         } 
/*  862 */         return false;
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
/*      */   public static <K, V> Map<K, V> filterKeys(Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
/*  893 */     Preconditions.checkNotNull(keyPredicate);
/*  894 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
/*      */         public boolean apply(Map.Entry<K, V> input) {
/*  896 */           return keyPredicate.apply(input.getKey());
/*      */         }
/*      */       };
/*  899 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredKeyMap((Map)Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate);
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
/*      */   public static <K, V> Map<K, V> filterValues(Map<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
/*  931 */     Preconditions.checkNotNull(valuePredicate);
/*  932 */     Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
/*      */         public boolean apply(Map.Entry<K, V> input) {
/*  934 */           return valuePredicate.apply(input.getValue());
/*      */         }
/*      */       };
/*  937 */     return filterEntries(unfiltered, entryPredicate);
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
/*      */   public static <K, V> Map<K, V> filterEntries(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/*  968 */     Preconditions.checkNotNull(entryPredicate);
/*  969 */     return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredEntryMap((Map)Preconditions.checkNotNull(unfiltered), entryPredicate);
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
/*      */   private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/*  981 */     Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
/*      */     
/*  983 */     return new FilteredEntryMap(map.unfiltered, predicate);
/*      */   }
/*      */   
/*      */   private static abstract class AbstractFilteredMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */   {
/*      */     final Map<K, V> unfiltered;
/*      */     final Predicate<? super Map.Entry<K, V>> predicate;
/*      */     Collection<V> values;
/*      */     
/*      */     AbstractFilteredMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
/*  994 */       this.unfiltered = unfiltered;
/*  995 */       this.predicate = predicate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean apply(Object key, V value) {
/* 1002 */       K k = (K)key;
/* 1003 */       return this.predicate.apply(Maps.immutableEntry(k, value));
/*      */     }
/*      */     
/*      */     public V put(K key, V value) {
/* 1007 */       Preconditions.checkArgument(apply(key, value));
/* 1008 */       return (V)this.unfiltered.put(key, value);
/*      */     }
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> map) {
/* 1012 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 1013 */         Preconditions.checkArgument(apply(entry.getKey(), entry.getValue()));
/*      */       }
/* 1015 */       this.unfiltered.putAll(map);
/*      */     }
/*      */ 
/*      */     
/* 1019 */     public boolean containsKey(Object key) { return (this.unfiltered.containsKey(key) && apply(key, this.unfiltered.get(key))); }
/*      */ 
/*      */     
/*      */     public V get(Object key) {
/* 1023 */       V value = (V)this.unfiltered.get(key);
/* 1024 */       return (value != null && apply(key, value)) ? value : null;
/*      */     }
/*      */ 
/*      */     
/* 1028 */     public boolean isEmpty() { return entrySet().isEmpty(); }
/*      */ 
/*      */ 
/*      */     
/* 1032 */     public V remove(Object key) { return (V)(containsKey(key) ? this.unfiltered.remove(key) : null); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1038 */       Collection<V> result = this.values;
/* 1039 */       return (result == null) ? (this.values = new Values()) : result;
/*      */     }
/*      */     
/*      */     class Values extends AbstractCollection<V> {
/*      */       public Iterator<V> iterator() {
/* 1044 */         final Iterator<Map.Entry<K, V>> entryIterator = Maps.AbstractFilteredMap.this.entrySet().iterator();
/* 1045 */         return new UnmodifiableIterator<V>()
/*      */           {
/* 1047 */             public boolean hasNext() { return entryIterator.hasNext(); }
/*      */ 
/*      */             
/* 1050 */             public V next() { return (V)((Map.Entry)entryIterator.next()).getValue(); }
/*      */           };
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1056 */       public int size() { return Maps.AbstractFilteredMap.this.entrySet().size(); }
/*      */ 
/*      */ 
/*      */       
/* 1060 */       public void clear() { Maps.AbstractFilteredMap.this.entrySet().clear(); }
/*      */ 
/*      */ 
/*      */       
/* 1064 */       public boolean isEmpty() { return Maps.AbstractFilteredMap.this.entrySet().isEmpty(); }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1068 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1069 */         while (iterator.hasNext()) {
/* 1070 */           Map.Entry<K, V> entry = (Map.Entry)iterator.next();
/* 1071 */           if (Objects.equal(o, entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/* 1072 */             iterator.remove();
/* 1073 */             return true;
/*      */           } 
/*      */         } 
/* 1076 */         return false;
/*      */       }
/*      */       
/*      */       public boolean removeAll(Collection<?> collection) {
/* 1080 */         Preconditions.checkNotNull(collection);
/* 1081 */         boolean changed = false;
/* 1082 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1083 */         while (iterator.hasNext()) {
/* 1084 */           Map.Entry<K, V> entry = (Map.Entry)iterator.next();
/* 1085 */           if (collection.contains(entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/* 1086 */             iterator.remove();
/* 1087 */             changed = true;
/*      */           } 
/*      */         } 
/* 1090 */         return changed;
/*      */       }
/*      */       
/*      */       public boolean retainAll(Collection<?> collection) {
/* 1094 */         Preconditions.checkNotNull(collection);
/* 1095 */         boolean changed = false;
/* 1096 */         Iterator<Map.Entry<K, V>> iterator = Maps.AbstractFilteredMap.this.unfiltered.entrySet().iterator();
/* 1097 */         while (iterator.hasNext()) {
/* 1098 */           Map.Entry<K, V> entry = (Map.Entry)iterator.next();
/* 1099 */           if (!collection.contains(entry.getValue()) && Maps.AbstractFilteredMap.this.predicate.apply(entry)) {
/*      */             
/* 1101 */             iterator.remove();
/* 1102 */             changed = true;
/*      */           } 
/*      */         } 
/* 1105 */         return changed;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1110 */       public Object[] toArray() { return Lists.newArrayList(iterator()).toArray(); }
/*      */ 
/*      */ 
/*      */       
/* 1114 */       public <T> T[] toArray(T[] array) { return (T[])Lists.newArrayList(iterator()).toArray(array); }
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
/*      */     Predicate<? super K> keyPredicate;
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     Set<K> keySet;
/*      */     
/*      */     FilteredKeyMap(Map<K, V> unfiltered, Predicate<? super K> keyPredicate, Predicate<Map.Entry<K, V>> entryPredicate) {
/* 1124 */       super(unfiltered, entryPredicate);
/* 1125 */       this.keyPredicate = keyPredicate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1131 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1132 */       return (result == null) ? (this.entrySet = Sets.filter(this.unfiltered.entrySet(), this.predicate)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1140 */       Set<K> result = this.keySet;
/* 1141 */       return (result == null) ? (this.keySet = Sets.filter(this.unfiltered.keySet(), this.keyPredicate)) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1150 */     public boolean containsKey(Object key) { return (this.unfiltered.containsKey(key) && this.keyPredicate.apply(key)); }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class FilteredEntryMap<K, V>
/*      */     extends AbstractFilteredMap<K, V>
/*      */   {
/*      */     final Set<Map.Entry<K, V>> filteredEntrySet;
/*      */     
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     
/*      */     Set<K> keySet;
/*      */     
/*      */     FilteredEntryMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
/* 1164 */       super(unfiltered, entryPredicate);
/* 1165 */       this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1171 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1172 */       return (result == null) ? (this.entrySet = new EntrySet(null)) : result;
/*      */     }
/*      */     
/*      */     private class EntrySet extends ForwardingSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */       
/* 1177 */       protected Set<Map.Entry<K, V>> delegate() { return Maps.FilteredEntryMap.this.filteredEntrySet; }
/*      */ 
/*      */       
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1181 */         final Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.filteredEntrySet.iterator();
/* 1182 */         return new UnmodifiableIterator<Map.Entry<K, V>>()
/*      */           {
/* 1184 */             public boolean hasNext() { return iterator.hasNext(); }
/*      */             
/*      */             public Map.Entry<K, V> next() {
/* 1187 */               final Map.Entry<K, V> entry = (Map.Entry)iterator.next();
/* 1188 */               return new ForwardingMapEntry<K, V>()
/*      */                 {
/* 1190 */                   protected Map.Entry<K, V> delegate() { return entry; }
/*      */                   
/*      */                   public V setValue(V value) {
/* 1193 */                     Preconditions.checkArgument(Maps.FilteredEntryMap.EntrySet.this.this$0.apply(entry.getKey(), value));
/* 1194 */                     return (V)super.setValue(value);
/*      */                   }
/*      */                 };
/*      */             }
/*      */           };
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1205 */       Set<K> result = this.keySet;
/* 1206 */       return (result == null) ? (this.keySet = new KeySet(null)) : result;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractSet<K> {
/*      */       public Iterator<K> iterator() {
/* 1211 */         final Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.filteredEntrySet.iterator();
/* 1212 */         return new UnmodifiableIterator<K>()
/*      */           {
/* 1214 */             public boolean hasNext() { return iterator.hasNext(); }
/*      */ 
/*      */             
/* 1217 */             public K next() { return (K)((Map.Entry)iterator.next()).getKey(); }
/*      */           };
/*      */       }
/*      */       
/*      */       private KeySet() {}
/*      */       
/* 1223 */       public int size() { return Maps.FilteredEntryMap.this.filteredEntrySet.size(); }
/*      */ 
/*      */ 
/*      */       
/* 1227 */       public void clear() { Maps.FilteredEntryMap.this.filteredEntrySet.clear(); }
/*      */ 
/*      */ 
/*      */       
/* 1231 */       public boolean contains(Object o) { return Maps.FilteredEntryMap.this.containsKey(o); }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1235 */         if (Maps.FilteredEntryMap.this.containsKey(o)) {
/* 1236 */           Maps.FilteredEntryMap.this.unfiltered.remove(o);
/* 1237 */           return true;
/*      */         } 
/* 1239 */         return false;
/*      */       }
/*      */       
/*      */       public boolean removeAll(Collection<?> collection) {
/* 1243 */         Preconditions.checkNotNull(collection);
/* 1244 */         boolean changed = false;
/* 1245 */         for (Object obj : collection) {
/* 1246 */           changed |= remove(obj);
/*      */         }
/* 1248 */         return changed;
/*      */       }
/*      */       
/*      */       public boolean retainAll(Collection<?> collection) {
/* 1252 */         Preconditions.checkNotNull(collection);
/* 1253 */         boolean changed = false;
/* 1254 */         Iterator<Map.Entry<K, V>> iterator = Maps.FilteredEntryMap.this.unfiltered.entrySet().iterator();
/* 1255 */         while (iterator.hasNext()) {
/* 1256 */           Map.Entry<K, V> entry = (Map.Entry)iterator.next();
/* 1257 */           if (!collection.contains(entry.getKey()) && Maps.FilteredEntryMap.this.predicate.apply(entry)) {
/* 1258 */             iterator.remove();
/* 1259 */             changed = true;
/*      */           } 
/*      */         } 
/* 1262 */         return changed;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1267 */       public Object[] toArray() { return Lists.newArrayList(iterator()).toArray(); }
/*      */ 
/*      */ 
/*      */       
/* 1271 */       public <T> T[] toArray(T[] array) { return (T[])Lists.newArrayList(iterator()).toArray(array); }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtCompatible
/*      */   static abstract class ImprovedAbstractMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */   {
/*      */     private Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */     
/*      */     private Set<K> keySet;
/*      */ 
/*      */     
/*      */     private Collection<V> values;
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract Set<Map.Entry<K, V>> createEntrySet();
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1296 */       Set<Map.Entry<K, V>> result = this.entrySet;
/* 1297 */       if (result == null) {
/* 1298 */         this.entrySet = result = createEntrySet();
/*      */       }
/* 1300 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1306 */       Set<K> result = this.keySet;
/* 1307 */       if (result == null) {
/* 1308 */         final Set<K> delegate = super.keySet();
/* 1309 */         this.keySet = result = new ForwardingSet<K>()
/*      */           {
/* 1311 */             protected Set<K> delegate() { return delegate; }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1315 */               return super.this$0.isEmpty();
/*      */             }
/*      */           };
/*      */       } 
/* 1319 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1325 */       Collection<V> result = this.values;
/* 1326 */       if (result == null) {
/* 1327 */         final Collection<V> delegate = super.values();
/* 1328 */         this.values = result = new ForwardingCollection<V>()
/*      */           {
/* 1330 */             protected Collection<V> delegate() { return delegate; }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1334 */               return super.this$0.isEmpty();
/*      */             }
/*      */           };
/*      */       } 
/* 1338 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1349 */     public boolean isEmpty() { return entrySet().isEmpty(); }
/*      */   }
/*      */ 
/*      */   
/* 1353 */   static final Joiner.MapJoiner standardJoiner = Collections2.standardJoiner.withKeyValueSeparator("=");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <V> V safeGet(Map<?, V> map, Object key) {
/*      */     try {
/* 1362 */       return (V)map.get(key);
/* 1363 */     } catch (ClassCastException e) {
/* 1364 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean safeContainsKey(Map<?, ?> map, Object key) {
/*      */     try {
/* 1374 */       return map.containsKey(key);
/* 1375 */     } catch (ClassCastException e) {
/* 1376 */       return false;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Maps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */