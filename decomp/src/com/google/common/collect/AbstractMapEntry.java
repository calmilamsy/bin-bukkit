/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Objects;
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
/*    */ @GwtCompatible
/*    */ abstract class AbstractMapEntry<K, V>
/*    */   extends Object
/*    */   implements Map.Entry<K, V>
/*    */ {
/*    */   public abstract K getKey();
/*    */   
/*    */   public abstract V getValue();
/*    */   
/* 40 */   public V setValue(V value) { throw new UnsupportedOperationException(); }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 44 */     if (object instanceof Map.Entry) {
/* 45 */       Map.Entry<?, ?> that = (Map.Entry)object;
/* 46 */       return (Objects.equal(getKey(), that.getKey()) && Objects.equal(getValue(), that.getValue()));
/*    */     } 
/*    */     
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 53 */     K k = (K)getKey();
/* 54 */     V v = (V)getValue();
/* 55 */     return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public String toString() { return getKey() + "=" + getValue(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractMapEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */