/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
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
/*    */ public abstract class ForwardingSortedSetMultimap<K, V>
/*    */   extends ForwardingSetMultimap<K, V>
/*    */   implements SortedSetMultimap<K, V>
/*    */ {
/* 43 */   public SortedSet<V> get(@Nullable K key) { return delegate().get(key); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public SortedSet<V> removeAll(@Nullable Object key) { return delegate().removeAll(key); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) { return delegate().replaceValues(key, values); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public Comparator<? super V> valueComparator() { return delegate().valueComparator(); }
/*    */   
/*    */   protected abstract SortedSetMultimap<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingSortedSetMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */