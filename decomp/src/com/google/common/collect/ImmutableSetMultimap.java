/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true)
/*     */ public class ImmutableSetMultimap<K, V>
/*     */   extends ImmutableMultimap<K, V>
/*     */   implements SetMultimap<K, V>
/*     */ {
/*     */   private ImmutableSet<Map.Entry<K, V>> entries;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  61 */   public static <K, V> ImmutableSetMultimap<K, V> of() { return EmptyImmutableSetMultimap.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1) {
/*  68 */     Builder<K, V> builder = builder();
/*  69 */     builder.put(k1, v1);
/*  70 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2) {
/*  79 */     Builder<K, V> builder = builder();
/*  80 */     builder.put(k1, v1);
/*  81 */     builder.put(k2, v2);
/*  82 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  92 */     Builder<K, V> builder = builder();
/*  93 */     builder.put(k1, v1);
/*  94 */     builder.put(k2, v2);
/*  95 */     builder.put(k3, v3);
/*  96 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 106 */     Builder<K, V> builder = builder();
/* 107 */     builder.put(k1, v1);
/* 108 */     builder.put(k2, v2);
/* 109 */     builder.put(k3, v3);
/* 110 */     builder.put(k4, v4);
/* 111 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 121 */     Builder<K, V> builder = builder();
/* 122 */     builder.put(k1, v1);
/* 123 */     builder.put(k2, v2);
/* 124 */     builder.put(k3, v3);
/* 125 */     builder.put(k4, v4);
/* 126 */     builder.put(k5, v5);
/* 127 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static <K, V> Builder<K, V> builder() { return new Builder(); }
/*     */ 
/*     */   
/*     */   private static class BuilderMultimap<K, V>
/*     */     extends AbstractMultimap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/* 145 */     BuilderMultimap() { super(new LinkedHashMap()); }
/*     */ 
/*     */     
/* 148 */     Collection<V> createCollection() { return Sets.newLinkedHashSet(); }
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
/*     */   public static final class Builder<K, V>
/*     */     extends ImmutableMultimap.Builder<K, V>
/*     */   {
/* 171 */     private final Multimap<K, V> builderMultimap = new ImmutableSetMultimap.BuilderMultimap();
/*     */ 
/*     */ 
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
/* 184 */       this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
/* 185 */       return this;
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
/* 196 */       Collection<V> collection = this.builderMultimap.get(Preconditions.checkNotNull(key));
/* 197 */       for (V value : values) {
/* 198 */         collection.add(Preconditions.checkNotNull(value));
/*     */       }
/* 200 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     public Builder<K, V> putAll(K key, V... values) { return putAll(key, Arrays.asList(values)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 225 */       for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
/* 226 */         putAll(entry.getKey(), (Iterable)entry.getValue());
/*     */       }
/* 228 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     public ImmutableSetMultimap<K, V> build() { return ImmutableSetMultimap.copyOf(this.builderMultimap); }
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
/*     */   public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 255 */     if (multimap.isEmpty()) {
/* 256 */       return of();
/*     */     }
/*     */     
/* 259 */     if (multimap instanceof ImmutableSetMultimap)
/*     */     {
/* 261 */       return (ImmutableSetMultimap)multimap;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 266 */     ImmutableMap.Builder<K, ImmutableSet<V>> builder = ImmutableMap.builder();
/* 267 */     int size = 0;
/*     */ 
/*     */     
/* 270 */     for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
/* 271 */       K key = (K)entry.getKey();
/* 272 */       Collection<? extends V> values = (Collection)entry.getValue();
/* 273 */       ImmutableSet<V> set = ImmutableSet.copyOf(values);
/* 274 */       if (!set.isEmpty()) {
/* 275 */         builder.put(key, set);
/* 276 */         size += set.size();
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     return new ImmutableSetMultimap(builder.build(), size);
/*     */   }
/*     */ 
/*     */   
/* 284 */   ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> map, int size) { super(map, size); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<V> get(@Nullable K key) {
/* 297 */     ImmutableSet<V> set = (ImmutableSet)this.map.get(key);
/* 298 */     return (set == null) ? ImmutableSet.of() : set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public ImmutableSet<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public ImmutableSet<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entries() {
/* 329 */     ImmutableSet<Map.Entry<K, V>> result = this.entries;
/* 330 */     return (result == null) ? (this.entries = ImmutableSet.copyOf(super.entries())) : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 340 */     stream.defaultWriteObject();
/* 341 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*     */     ImmutableMap<Object, ImmutableSet<Object>> tmpMap;
/* 346 */     stream.defaultReadObject();
/* 347 */     int keyCount = stream.readInt();
/* 348 */     if (keyCount < 0) {
/* 349 */       throw new InvalidObjectException("Invalid key count " + keyCount);
/*     */     }
/* 351 */     ImmutableMap.Builder<Object, ImmutableSet<Object>> builder = ImmutableMap.builder();
/*     */     
/* 353 */     int tmpSize = 0;
/*     */     
/* 355 */     for (int i = 0; i < keyCount; i++) {
/* 356 */       Object key = stream.readObject();
/* 357 */       int valueCount = stream.readInt();
/* 358 */       if (valueCount <= 0) {
/* 359 */         throw new InvalidObjectException("Invalid value count " + valueCount);
/*     */       }
/*     */       
/* 362 */       Object[] array = new Object[valueCount];
/* 363 */       for (int j = 0; j < valueCount; j++) {
/* 364 */         array[j] = stream.readObject();
/*     */       }
/* 366 */       ImmutableSet<Object> valueSet = ImmutableSet.copyOf(array);
/* 367 */       if (valueSet.size() != array.length) {
/* 368 */         throw new InvalidObjectException("Duplicate key-value pairs exist for key " + key);
/*     */       }
/*     */       
/* 371 */       builder.put(key, valueSet);
/* 372 */       tmpSize += valueCount;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 377 */       tmpMap = builder.build();
/* 378 */     } catch (IllegalArgumentException e) {
/* 379 */       throw (InvalidObjectException)(new InvalidObjectException(e.getMessage())).initCause(e);
/*     */     } 
/*     */ 
/*     */     
/* 383 */     ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
/* 384 */     ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */