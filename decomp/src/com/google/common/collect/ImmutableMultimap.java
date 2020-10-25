/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ @GwtCompatible
/*     */ public abstract class ImmutableMultimap<K, V>
/*     */   extends Object
/*     */   implements Multimap<K, V>, Serializable
/*     */ {
/*     */   final ImmutableMap<K, ? extends ImmutableCollection<V>> map;
/*     */   final int size;
/*     */   private ImmutableCollection<Map.Entry<K, V>> entries;
/*     */   private ImmutableMultiset<K> keys;
/*     */   private ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  55 */   public static <K, V> ImmutableMultimap<K, V> of() { return ImmutableListMultimap.of(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1) { return ImmutableListMultimap.of(k1, v1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2) { return ImmutableListMultimap.of(k1, v1, k2, v2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) { return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) { return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) { return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static <K, V> Builder<K, V> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class BuilderMultimap<K, V>
/*     */     extends AbstractMultimap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/* 113 */     BuilderMultimap() { super(new LinkedHashMap()); }
/*     */ 
/*     */     
/* 116 */     Collection<V> createCollection() { return Lists.newArrayList(); }
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
/*     */   public static class Builder<K, V>
/*     */     extends Object
/*     */   {
/* 138 */     private final Multimap<K, V> builderMultimap = new ImmutableMultimap.BuilderMultimap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> put(K key, V value) {
/* 150 */       this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
/* 151 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
/* 162 */       Collection<V> valueList = this.builderMultimap.get(Preconditions.checkNotNull(key));
/* 163 */       for (V value : values) {
/* 164 */         valueList.add(Preconditions.checkNotNull(value));
/*     */       }
/* 166 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     public Builder<K, V> putAll(K key, V... values) { return putAll(key, Arrays.asList(values)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
/* 190 */       for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
/* 191 */         putAll(entry.getKey(), (Iterable)entry.getValue());
/*     */       }
/* 193 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     public ImmutableMultimap<K, V> build() { return ImmutableMultimap.copyOf(this.builderMultimap); }
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
/*     */   public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 218 */     if (multimap instanceof ImmutableMultimap)
/*     */     {
/* 220 */       return (ImmutableMultimap)multimap;
/*     */     }
/*     */ 
/*     */     
/* 224 */     return ImmutableListMultimap.copyOf(multimap);
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
/*     */   static class FieldSettersHolder
/*     */   {
/* 238 */     static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> map, int size) {
/* 249 */     this.map = map;
/* 250 */     this.size = size;
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
/* 261 */   public ImmutableCollection<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public ImmutableCollection<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public boolean put(K key, V value) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public boolean putAll(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public boolean remove(Object key, Object value) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/* 330 */     Collection<V> values = (Collection)this.map.get(key);
/* 331 */     return (values != null && values.contains(value));
/*     */   }
/*     */ 
/*     */   
/* 335 */   public boolean containsKey(@Nullable Object key) { return this.map.containsKey(key); }
/*     */ 
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 339 */     for (Collection<V> valueCollection : this.map.values()) {
/* 340 */       if (valueCollection.contains(value)) {
/* 341 */         return true;
/*     */       }
/*     */     } 
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 348 */   public boolean isEmpty() { return (this.size == 0); }
/*     */ 
/*     */ 
/*     */   
/* 352 */   public int size() { return this.size; }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 356 */     if (object instanceof Multimap) {
/* 357 */       Multimap<?, ?> that = (Multimap)object;
/* 358 */       return this.map.equals(that.asMap());
/*     */     } 
/* 360 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 364 */   public int hashCode() { return this.map.hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 368 */   public String toString() { return this.map.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   public ImmutableSet<K> keySet() { return this.map.keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public ImmutableMap<K, Collection<V>> asMap() { return this.map; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<Map.Entry<K, V>> entries() {
/* 399 */     ImmutableCollection<Map.Entry<K, V>> result = this.entries;
/* 400 */     return (result == null) ? (this.entries = new EntryCollection(this)) : result;
/*     */   }
/*     */   
/*     */   private static class EntryCollection<K, V>
/*     */     extends ImmutableCollection<Map.Entry<K, V>>
/*     */   {
/*     */     final ImmutableMultimap<K, V> multimap;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 409 */     EntryCollection(ImmutableMultimap<K, V> multimap) { this.multimap = multimap; }
/*     */ 
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
/* 414 */       final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> mapIterator = this.multimap.map.entrySet().iterator();
/*     */       
/* 416 */       return new UnmodifiableIterator<Map.Entry<K, V>>()
/*     */         {
/*     */           K key;
/*     */           Iterator<V> valueIterator;
/*     */           
/* 421 */           public boolean hasNext() { return ((super.key != null && super.valueIterator.hasNext()) || mapIterator.hasNext()); }
/*     */ 
/*     */ 
/*     */           
/*     */           public Map.Entry<K, V> next() {
/* 426 */             if (super.key == null || !super.valueIterator.hasNext()) {
/* 427 */               Map.Entry<K, ? extends ImmutableCollection<V>> entry = (Map.Entry)mapIterator.next();
/*     */               
/* 429 */               super.key = entry.getKey();
/* 430 */               super.valueIterator = ((ImmutableCollection)entry.getValue()).iterator();
/*     */             } 
/* 432 */             return Maps.immutableEntry(super.key, super.valueIterator.next());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 438 */     public int size() { return this.multimap.size(); }
/*     */ 
/*     */     
/*     */     public boolean contains(Object object) {
/* 442 */       if (object instanceof Map.Entry) {
/* 443 */         Map.Entry<?, ?> entry = (Map.Entry)object;
/* 444 */         return this.multimap.containsEntry(entry.getKey(), entry.getValue());
/*     */       } 
/* 446 */       return false;
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
/*     */   public ImmutableMultiset<K> keys() {
/* 461 */     ImmutableMultiset<K> result = this.keys;
/* 462 */     return (result == null) ? (this.keys = createKeys()) : result;
/*     */   }
/*     */   
/*     */   private ImmutableMultiset<K> createKeys() {
/* 466 */     ImmutableMultiset.Builder<K> builder = ImmutableMultiset.builder();
/*     */     
/* 468 */     for (Map.Entry<K, ? extends ImmutableCollection<V>> entry : this.map.entrySet()) {
/* 469 */       builder.addCopies(entry.getKey(), ((ImmutableCollection)entry.getValue()).size());
/*     */     }
/* 471 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/* 482 */     ImmutableCollection<V> result = this.values;
/* 483 */     return (result == null) ? (this.values = new Values(this)) : result;
/*     */   }
/*     */   
/*     */   public abstract ImmutableCollection<V> get(K paramK);
/*     */   
/*     */   private static class Values<V> extends ImmutableCollection<V> { final Multimap<?, V> multimap;
/*     */     
/* 490 */     Values(Multimap<?, V> multimap) { this.multimap = multimap; }
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 494 */       final Iterator<? extends Map.Entry<?, V>> entryIterator = this.multimap.entries().iterator();
/*     */       
/* 496 */       return new UnmodifiableIterator<V>()
/*     */         {
/* 498 */           public boolean hasNext() { return entryIterator.hasNext(); }
/*     */ 
/*     */           
/* 501 */           public V next() { return (V)((Map.Entry)entryIterator.next()).getValue(); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 507 */     public int size() { return this.multimap.size(); } }
/*     */ 
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */