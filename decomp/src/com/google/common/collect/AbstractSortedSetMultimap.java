/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.SortedSet;
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
/*    */ abstract class AbstractSortedSetMultimap<K, V>
/*    */   extends AbstractSetMultimap<K, V>
/*    */   implements SortedSetMultimap<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = 430848587173315748L;
/*    */   
/* 45 */   protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) { super(map); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public SortedSet<V> get(@Nullable K key) { return (SortedSet)super.get(key); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public SortedSet<V> removeAll(@Nullable Object key) { return (SortedSet)super.removeAll(key); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) { return (SortedSet)super.replaceValues(key, values); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public Collection<V> values() { return super.values(); }
/*    */   
/*    */   abstract SortedSet<V> createCollection();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractSortedSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */