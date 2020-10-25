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
/*    */ @GwtCompatible(serializable = true, emulated = true)
/*    */ final class EmptyImmutableMap
/*    */   extends ImmutableMap<Object, Object>
/*    */ {
/* 33 */   static final EmptyImmutableMap INSTANCE = new EmptyImmutableMap();
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */ 
/*    */   
/* 38 */   public Object get(Object key) { return null; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public int size() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public boolean isEmpty() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public boolean containsKey(Object key) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean containsValue(Object value) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public ImmutableSet<Map.Entry<Object, Object>> entrySet() { return ImmutableSet.of(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public ImmutableSet<Object> keySet() { return ImmutableSet.of(); }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public ImmutableCollection<Object> values() { return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION; }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 70 */     if (object instanceof Map) {
/* 71 */       Map<?, ?> that = (Map)object;
/* 72 */       return that.isEmpty();
/*    */     } 
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 78 */   public int hashCode() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 82 */   public String toString() { return "{}"; }
/*    */ 
/*    */ 
/*    */   
/* 86 */   Object readResolve() { return INSTANCE; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */