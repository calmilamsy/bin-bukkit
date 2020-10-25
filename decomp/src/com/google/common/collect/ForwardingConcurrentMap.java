/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public abstract class ForwardingConcurrentMap<K, V>
/*    */   extends ForwardingMap<K, V>
/*    */   implements ConcurrentMap<K, V>
/*    */ {
/* 40 */   public V putIfAbsent(K key, V value) { return (V)delegate().putIfAbsent(key, value); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean remove(Object key, Object value) { return delegate().remove(key, value); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public V replace(K key, V value) { return (V)delegate().replace(key, value); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean replace(K key, V oldValue, V newValue) { return delegate().replace(key, oldValue, newValue); }
/*    */   
/*    */   protected abstract ConcurrentMap<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingConcurrentMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */