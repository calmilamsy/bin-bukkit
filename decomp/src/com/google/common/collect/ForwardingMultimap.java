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
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingMultimap<K, V>
/*     */   extends ForwardingObject
/*     */   implements Multimap<K, V>
/*     */ {
/*  45 */   public Map<K, Collection<V>> asMap() { return delegate().asMap(); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public void clear() { delegate().clear(); }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) { return delegate().containsEntry(key, value); }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public boolean containsKey(@Nullable Object key) { return delegate().containsKey(key); }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public boolean containsValue(@Nullable Object value) { return delegate().containsValue(value); }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public Collection<Map.Entry<K, V>> entries() { return delegate().entries(); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public Collection<V> get(@Nullable K key) { return delegate().get(key); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public boolean isEmpty() { return delegate().isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public Multiset<K> keys() { return delegate().keys(); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public Set<K> keySet() { return delegate().keySet(); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean put(K key, V value) { return delegate().put(key, value); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean putAll(K key, Iterable<? extends V> values) { return delegate().putAll(key, values); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) { return delegate().putAll(multimap); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean remove(@Nullable Object key, @Nullable Object value) { return delegate().remove(key, value); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Collection<V> removeAll(@Nullable Object key) { return delegate().removeAll(key); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public Collection<V> replaceValues(K key, Iterable<? extends V> values) { return delegate().replaceValues(key, values); }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public int size() { return delegate().size(); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public Collection<V> values() { return delegate().values(); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public boolean equals(@Nullable Object object) { return (object == this || delegate().equals(object)); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public int hashCode() { return delegate().hashCode(); }
/*     */   
/*     */   protected abstract Multimap<K, V> delegate();
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */