/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Map;
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
/*    */ @GwtCompatible(serializable = true, emulated = true)
/*    */ class RegularImmutableBiMap<K, V>
/*    */   extends ImmutableBiMap<K, V>
/*    */ {
/*    */   final ImmutableMap<K, V> delegate;
/*    */   final ImmutableBiMap<V, K> inverse;
/*    */   
/*    */   RegularImmutableBiMap(ImmutableMap<K, V> delegate) {
/* 33 */     this.delegate = delegate;
/*    */     
/* 35 */     ImmutableMap.Builder<V, K> builder = ImmutableMap.builder();
/* 36 */     for (Map.Entry<K, V> entry : delegate.entrySet()) {
/* 37 */       builder.put(entry.getValue(), entry.getKey());
/*    */     }
/* 39 */     ImmutableMap<V, K> backwardMap = builder.build();
/* 40 */     this.inverse = new RegularImmutableBiMap(backwardMap, this);
/*    */   }
/*    */ 
/*    */   
/*    */   RegularImmutableBiMap(ImmutableMap<K, V> delegate, ImmutableBiMap<V, K> inverse) {
/* 45 */     this.delegate = delegate;
/* 46 */     this.inverse = inverse;
/*    */   }
/*    */ 
/*    */   
/* 50 */   ImmutableMap<K, V> delegate() { return this.delegate; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public ImmutableBiMap<V, K> inverse() { return this.inverse; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\RegularImmutableBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */