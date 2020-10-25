/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible
/*    */ class ImmutableEntry<K, V>
/*    */   extends AbstractMapEntry<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   private final K key;
/*    */   private final V value;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ImmutableEntry(@Nullable K key, @Nullable V value) {
/* 35 */     this.key = key;
/* 36 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 40 */   public K getKey() { return (K)this.key; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public V getValue() { return (V)this.value; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */