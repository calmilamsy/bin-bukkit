/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public abstract class ImmutableMap<K, V>
/*     */   extends Object
/*     */   implements Map<K, V>, Serializable
/*     */ {
/*  62 */   public static <K, V> ImmutableMap<K, V> of() { return EmptyImmutableMap.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1) { return new SingletonImmutableMap(Preconditions.checkNotNull(k1), Preconditions.checkNotNull(v1)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) { return new RegularImmutableMap(new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) { return new RegularImmutableMap(new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) { return new RegularImmutableMap(new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) { return new RegularImmutableMap(new Map.Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static <K, V> Builder<K, V> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   static <K, V> Map.Entry<K, V> entryOf(K key, V value) { return Maps.immutableEntry(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 158 */     final List<Map.Entry<K, V>> entries = Lists.newArrayList();
/*     */ 
/*     */ 
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
/* 171 */       this.entries.add(ImmutableMap.entryOf(key, value));
/* 172 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
/* 182 */       for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 183 */         put(entry.getKey(), entry.getValue());
/*     */       }
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
/*     */ 
/*     */     
/* 197 */     public ImmutableMap<K, V> build() { return fromEntryList(this.entries); }
/*     */ 
/*     */ 
/*     */     
/*     */     private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> entries) {
/* 202 */       int size = entries.size();
/* 203 */       switch (size) {
/*     */         case 0:
/* 205 */           return ImmutableMap.of();
/*     */         case 1:
/* 207 */           return new SingletonImmutableMap((Map.Entry)Iterables.getOnlyElement(entries));
/*     */       } 
/* 209 */       Entry[] entryArray = (Entry[])entries.toArray(new Map.Entry[entries.size()]);
/*     */       
/* 211 */       return new RegularImmutableMap(entryArray);
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
/*     */   public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
/* 230 */     if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap))
/*     */     {
/* 232 */       return (ImmutableMap)map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     Entry[] entries = (Entry[])map.entrySet().toArray(new Map.Entry[0]);
/* 238 */     switch (entries.length) {
/*     */       case 0:
/* 240 */         return of();
/*     */       case 1:
/* 242 */         return new SingletonImmutableMap(entryOf(entries[0].getKey(), entries[0].getValue()));
/*     */     } 
/*     */     
/* 245 */     for (int i = 0; i < entries.length; i++) {
/* 246 */       K k = (K)entries[i].getKey();
/* 247 */       V v = (V)entries[i].getValue();
/* 248 */       entries[i] = entryOf(k, v);
/*     */     } 
/* 250 */     return new RegularImmutableMap(entries);
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
/* 262 */   public final V put(K k, V v) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public final V remove(Object o) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public final void putAll(Map<? extends K, ? extends V> map) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   public final void clear() { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */   
/* 293 */   public boolean isEmpty() { return (size() == 0); }
/*     */ 
/*     */ 
/*     */   
/* 297 */   public boolean containsKey(@Nullable Object key) { return (get(key) != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 325 */     if (object == this) {
/* 326 */       return true;
/*     */     }
/* 328 */     if (object instanceof Map) {
/* 329 */       Map<?, ?> that = (Map)object;
/* 330 */       return entrySet().equals(that.entrySet());
/*     */     } 
/* 332 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public int hashCode() { return entrySet().hashCode(); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 342 */     StringBuilder result = (new StringBuilder(size() * 16)).append('{');
/* 343 */     Maps.standardJoiner.appendTo(result, this);
/* 344 */     return result.append('}').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     private final Object[] keys;
/*     */     private final Object[] values;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SerializedForm(ImmutableMap<?, ?> map) {
/* 356 */       this.keys = new Object[map.size()];
/* 357 */       this.values = new Object[map.size()];
/* 358 */       int i = 0;
/* 359 */       for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 360 */         this.keys[i] = entry.getKey();
/* 361 */         this.values[i] = entry.getValue();
/* 362 */         i++;
/*     */       } 
/*     */     }
/*     */     Object readResolve() {
/* 366 */       ImmutableMap.Builder<Object, Object> builder = new ImmutableMap.Builder<Object, Object>();
/* 367 */       return createMap(builder);
/*     */     }
/*     */     Object createMap(ImmutableMap.Builder<Object, Object> builder) {
/* 370 */       for (int i = 0; i < this.keys.length; i++) {
/* 371 */         builder.put(this.keys[i], this.values[i]);
/*     */       }
/* 373 */       return builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 379 */   Object writeReplace() { return new SerializedForm(this); }
/*     */   
/*     */   public abstract boolean containsValue(@Nullable Object paramObject);
/*     */   
/*     */   public abstract V get(@Nullable Object paramObject);
/*     */   
/*     */   public abstract ImmutableSet<Map.Entry<K, V>> entrySet();
/*     */   
/*     */   public abstract ImmutableSet<K> keySet();
/*     */   
/*     */   public abstract ImmutableCollection<V> values();
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */