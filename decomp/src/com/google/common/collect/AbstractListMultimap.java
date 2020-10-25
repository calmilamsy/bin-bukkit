/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ @GwtCompatible
/*    */ abstract class AbstractListMultimap<K, V>
/*    */   extends AbstractMultimap<K, V>
/*    */   implements ListMultimap<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = 6588350623831699109L;
/*    */   
/* 46 */   protected AbstractListMultimap(Map<K, Collection<V>> map) { super(map); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public List<V> get(@Nullable K key) { return (List)super.get(key); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public List<V> removeAll(@Nullable Object key) { return (List)super.removeAll(key); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) { return (List)super.replaceValues(key, values); }
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
/* 72 */   public boolean put(@Nullable K key, @Nullable V value) { return super.put(key, value); }
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
/* 83 */   public boolean equals(@Nullable Object object) { return super.equals(object); }
/*    */   
/*    */   abstract List<V> createCollection();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */