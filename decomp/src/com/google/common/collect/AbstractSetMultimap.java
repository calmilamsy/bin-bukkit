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
/*    */ @GwtCompatible
/*    */ abstract class AbstractSetMultimap<K, V>
/*    */   extends AbstractMultimap<K, V>
/*    */   implements SetMultimap<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = 7431625294878419160L;
/*    */   
/* 44 */   protected AbstractSetMultimap(Map<K, Collection<V>> map) { super(map); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Set<V> get(@Nullable K key) { return (Set)super.get(key); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Set<Map.Entry<K, V>> entries() { return (Set)super.entries(); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public Set<V> removeAll(@Nullable Object key) { return (Set)super.removeAll(key); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) { return (Set)super.replaceValues(key, values); }
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
/* 80 */   public boolean put(K key, V value) { return super.put(key, value); }
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
/* 91 */   public boolean equals(@Nullable Object object) { return super.equals(object); }
/*    */   
/*    */   abstract Set<V> createCollection();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */