/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public abstract class ImmutableBiMap<K, V>
/*     */   extends ImmutableMap<K, V>
/*     */   implements BiMap<K, V>
/*     */ {
/*  46 */   private static final ImmutableBiMap<Object, Object> EMPTY_IMMUTABLE_BIMAP = new EmptyBiMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static <K, V> ImmutableBiMap<K, V> of() { return EMPTY_IMMUTABLE_BIMAP; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1) { return new RegularImmutableBiMap(ImmutableMap.of(k1, v1)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2) { return new RegularImmutableBiMap(ImmutableMap.of(k1, v1, k2, v2)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) { return new RegularImmutableBiMap(ImmutableMap.of(k1, v1, k2, v2, k3, v3)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) { return new RegularImmutableBiMap(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) { return new RegularImmutableBiMap(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static <K, V> Builder<K, V> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     public Builder<K, V> put(K key, V value) {
/* 148 */       super.put(key, value);
/* 149 */       return this;
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
/* 160 */       super.putAll(map);
/* 161 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableBiMap<K, V> build() {
/* 170 */       ImmutableMap<K, V> map = super.build();
/* 171 */       if (map.isEmpty()) {
/* 172 */         return ImmutableBiMap.of();
/*     */       }
/* 174 */       return new RegularImmutableBiMap(map);
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
/*     */   public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
/* 192 */     if (map instanceof ImmutableBiMap)
/*     */     {
/* 194 */       return (ImmutableBiMap)map;
/*     */     }
/*     */ 
/*     */     
/* 198 */     if (map.isEmpty()) {
/* 199 */       return of();
/*     */     }
/*     */     
/* 202 */     ImmutableMap<K, V> immutableMap = ImmutableMap.copyOf(map);
/* 203 */     return new RegularImmutableBiMap(immutableMap);
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
/* 219 */   public boolean containsKey(@Nullable Object key) { return delegate().containsKey(key); }
/*     */ 
/*     */ 
/*     */   
/* 223 */   public boolean containsValue(@Nullable Object value) { return inverse().containsKey(value); }
/*     */ 
/*     */ 
/*     */   
/* 227 */   public ImmutableSet<Map.Entry<K, V>> entrySet() { return delegate().entrySet(); }
/*     */ 
/*     */ 
/*     */   
/* 231 */   public V get(@Nullable Object key) { return (V)delegate().get(key); }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public ImmutableSet<K> keySet() { return delegate().keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public ImmutableSet<V> values() { return inverse().keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public V forcePut(K key, V value) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */   
/* 256 */   public boolean isEmpty() { return delegate().isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/* 260 */   public int size() { return delegate().size(); }
/*     */ 
/*     */ 
/*     */   
/* 264 */   public boolean equals(@Nullable Object object) { return (object == this || delegate().equals(object)); }
/*     */ 
/*     */ 
/*     */   
/* 268 */   public int hashCode() { return delegate().hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 272 */   public String toString() { return delegate().toString(); }
/*     */ 
/*     */ 
/*     */   
/*     */   static class EmptyBiMap
/*     */     extends ImmutableBiMap<Object, Object>
/*     */   {
/* 279 */     ImmutableMap<Object, Object> delegate() { return ImmutableMap.of(); }
/*     */ 
/*     */     
/* 282 */     public ImmutableBiMap<Object, Object> inverse() { return this; }
/*     */ 
/*     */     
/* 285 */     Object readResolve() { return EMPTY_IMMUTABLE_BIMAP; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializedForm
/*     */     extends ImmutableMap.SerializedForm
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     SerializedForm(ImmutableBiMap<?, ?> bimap) { super(bimap); }
/*     */     
/*     */     Object readResolve() {
/* 303 */       ImmutableBiMap.Builder<Object, Object> builder = new ImmutableBiMap.Builder<Object, Object>();
/* 304 */       return createMap(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 310 */   Object writeReplace() { return new SerializedForm(this); }
/*     */   
/*     */   abstract ImmutableMap<K, V> delegate();
/*     */   
/*     */   public abstract ImmutableBiMap<V, K> inverse();
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */