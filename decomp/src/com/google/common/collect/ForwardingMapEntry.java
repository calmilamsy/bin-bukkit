/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
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
/*    */ public abstract class ForwardingMapEntry<K, V>
/*    */   extends ForwardingObject
/*    */   implements Map.Entry<K, V>
/*    */ {
/* 42 */   public K getKey() { return (K)delegate().getKey(); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public V getValue() { return (V)delegate().getValue(); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public V setValue(V value) { return (V)delegate().setValue(value); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean equals(@Nullable Object object) { return delegate().equals(object); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public int hashCode() { return delegate().hashCode(); }
/*    */   
/*    */   protected abstract Map.Entry<K, V> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingMapEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */