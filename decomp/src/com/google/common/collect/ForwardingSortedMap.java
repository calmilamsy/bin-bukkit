/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Comparator;
/*    */ import java.util.Map;
/*    */ import java.util.SortedMap;
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
/*    */ public abstract class ForwardingSortedMap<K, V>
/*    */   extends ForwardingMap<K, V>
/*    */   implements SortedMap<K, V>
/*    */ {
/* 41 */   public Comparator<? super K> comparator() { return delegate().comparator(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public K firstKey() { return (K)delegate().firstKey(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public SortedMap<K, V> headMap(K toKey) { return delegate().headMap(toKey); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public K lastKey() { return (K)delegate().lastKey(); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public SortedMap<K, V> subMap(K fromKey, K toKey) { return delegate().subMap(fromKey, toKey); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public SortedMap<K, V> tailMap(K fromKey) { return delegate().tailMap(fromKey); }
/*    */   
/*    */   protected abstract SortedMap<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingSortedMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */