/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingMap<K, V>
/*    */   extends ForwardingObject
/*    */   implements Map<K, V>
/*    */ {
/* 45 */   public int size() { return delegate().size(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean isEmpty() { return delegate().isEmpty(); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public V remove(Object object) { return (V)delegate().remove(object); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void clear() { delegate().clear(); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public boolean containsKey(Object key) { return delegate().containsKey(key); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public boolean containsValue(Object value) { return delegate().containsValue(value); }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public V get(Object key) { return (V)delegate().get(key); }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public V put(K key, V value) { return (V)delegate().put(key, value); }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public void putAll(Map<? extends K, ? extends V> map) { delegate().putAll(map); }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public Set<K> keySet() { return delegate().keySet(); }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public Collection<V> values() { return delegate().values(); }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public Set<Map.Entry<K, V>> entrySet() { return delegate().entrySet(); }
/*    */ 
/*    */ 
/*    */   
/* 93 */   public boolean equals(@Nullable Object object) { return (object == this || delegate().equals(object)); }
/*    */ 
/*    */ 
/*    */   
/* 97 */   public int hashCode() { return delegate().hashCode(); }
/*    */   
/*    */   protected abstract Map<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */