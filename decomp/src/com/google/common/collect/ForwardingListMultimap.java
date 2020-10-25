/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
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
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingListMultimap<K, V>
/*    */   extends ForwardingMultimap<K, V>
/*    */   implements ListMultimap<K, V>
/*    */ {
/* 42 */   public List<V> get(@Nullable K key) { return delegate().get(key); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public List<V> removeAll(@Nullable Object key) { return delegate().removeAll(key); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public List<V> replaceValues(K key, Iterable<? extends V> values) { return delegate().replaceValues(key, values); }
/*    */   
/*    */   protected abstract ListMultimap<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */