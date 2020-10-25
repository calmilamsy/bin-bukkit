/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
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
/*    */ final class EmptyImmutableSet
/*    */   extends ImmutableSet<Object>
/*    */ {
/* 33 */   static final EmptyImmutableSet INSTANCE = new EmptyImmutableSet();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public int size() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean isEmpty() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public boolean contains(Object target) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public UnmodifiableIterator<Object> iterator() { return Iterators.emptyIterator(); }
/*    */ 
/*    */   
/* 53 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 56 */   public Object[] toArray() { return EMPTY_ARRAY; }
/*    */ 
/*    */   
/*    */   public <T> T[] toArray(T[] a) {
/* 60 */     if (a.length > 0) {
/* 61 */       a[0] = null;
/*    */     }
/* 63 */     return a;
/*    */   }
/*    */ 
/*    */   
/* 67 */   public boolean containsAll(Collection<?> targets) { return targets.isEmpty(); }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 71 */     if (object instanceof Set) {
/* 72 */       Set<?> that = (Set)object;
/* 73 */       return that.isEmpty();
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 79 */   public final int hashCode() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 83 */   boolean isHashCodeFast() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 87 */   public String toString() { return "[]"; }
/*    */ 
/*    */ 
/*    */   
/* 91 */   Object readResolve() { return INSTANCE; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EmptyImmutableSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */