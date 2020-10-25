/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ public class ImmutableListMultimap<K, V>
/*     */   extends ImmutableMultimap<K, V>
/*     */   implements ListMultimap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  58 */   public static <K, V> ImmutableListMultimap<K, V> of() { return EmptyImmutableListMultimap.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1) {
/*  65 */     Builder<K, V> builder = builder();
/*     */     
/*  67 */     builder.put(k1, v1);
/*  68 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2) {
/*  75 */     Builder<K, V> builder = builder();
/*     */     
/*  77 */     builder.put(k1, v1);
/*  78 */     builder.put(k2, v2);
/*  79 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  87 */     Builder<K, V> builder = builder();
/*     */     
/*  89 */     builder.put(k1, v1);
/*  90 */     builder.put(k2, v2);
/*  91 */     builder.put(k3, v3);
/*  92 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 100 */     Builder<K, V> builder = builder();
/*     */     
/* 102 */     builder.put(k1, v1);
/* 103 */     builder.put(k2, v2);
/* 104 */     builder.put(k3, v3);
/* 105 */     builder.put(k4, v4);
/* 106 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 114 */     Builder<K, V> builder = builder();
/*     */     
/* 116 */     builder.put(k1, v1);
/* 117 */     builder.put(k2, v2);
/* 118 */     builder.put(k3, v3);
/* 119 */     builder.put(k4, v4);
/* 120 */     builder.put(k5, v5);
/* 121 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public static <K, V> Builder<K, V> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     public Builder<K, V> put(K key, V value) {
/* 162 */       super.put(key, value);
/* 163 */       return this;
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
/* 174 */       super.putAll(key, values);
/* 175 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(K key, V... values) {
/* 185 */       super.putAll(key, values);
/* 186 */       return this;
/*     */     }
/*     */ 
/*     */ 
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
/* 200 */       super.putAll(multimap);
/* 201 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     public ImmutableListMultimap<K, V> build() { return (ImmutableListMultimap)super.build(); }
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
/*     */   public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 226 */     if (multimap.isEmpty()) {
/* 227 */       return of();
/*     */     }
/*     */     
/* 230 */     if (multimap instanceof ImmutableListMultimap)
/*     */     {
/* 232 */       return (ImmutableListMultimap)multimap;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     ImmutableMap.Builder<K, ImmutableList<V>> builder = ImmutableMap.builder();
/* 238 */     int size = 0;
/*     */ 
/*     */     
/* 241 */     for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
/* 242 */       ImmutableList<V> list = ImmutableList.copyOf((Collection)entry.getValue());
/* 243 */       if (!list.isEmpty()) {
/* 244 */         builder.put(entry.getKey(), list);
/* 245 */         size += list.size();
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     return new ImmutableListMultimap(builder.build(), size);
/*     */   }
/*     */ 
/*     */   
/* 253 */   ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> map, int size) { super(map, size); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<V> get(@Nullable K key) {
/* 266 */     ImmutableList<V> list = (ImmutableList)this.map.get(key);
/* 267 */     return (list == null) ? ImmutableList.of() : list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public ImmutableList<V> removeAll(Object key) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public ImmutableList<V> replaceValues(K key, Iterable<? extends V> values) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 294 */     stream.defaultWriteObject();
/* 295 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*     */     ImmutableMap<Object, ImmutableList<Object>> tmpMap;
/* 300 */     stream.defaultReadObject();
/* 301 */     int keyCount = stream.readInt();
/* 302 */     if (keyCount < 0) {
/* 303 */       throw new InvalidObjectException("Invalid key count " + keyCount);
/*     */     }
/* 305 */     ImmutableMap.Builder<Object, ImmutableList<Object>> builder = ImmutableMap.builder();
/*     */     
/* 307 */     int tmpSize = 0;
/*     */     
/* 309 */     for (int i = 0; i < keyCount; i++) {
/* 310 */       Object key = stream.readObject();
/* 311 */       int valueCount = stream.readInt();
/* 312 */       if (valueCount <= 0) {
/* 313 */         throw new InvalidObjectException("Invalid value count " + valueCount);
/*     */       }
/*     */       
/* 316 */       Object[] array = new Object[valueCount];
/* 317 */       for (int j = 0; j < valueCount; j++) {
/* 318 */         array[j] = stream.readObject();
/*     */       }
/* 320 */       builder.put(key, ImmutableList.copyOf(array));
/* 321 */       tmpSize += valueCount;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 326 */       tmpMap = builder.build();
/* 327 */     } catch (IllegalArgumentException e) {
/* 328 */       throw (InvalidObjectException)(new InvalidObjectException(e.getMessage())).initCause(e);
/*     */     } 
/*     */ 
/*     */     
/* 332 */     ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
/* 333 */     ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */