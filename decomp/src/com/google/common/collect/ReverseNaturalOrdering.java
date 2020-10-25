/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class ReverseNaturalOrdering
/*    */   extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 29 */   static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
/*    */   
/*    */   public int compare(Comparable left, Comparable right) {
/* 32 */     Preconditions.checkNotNull(left);
/* 33 */     if (left == right) {
/* 34 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 38 */     return right.compareTo(left);
/*    */   }
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 43 */   public <S extends Comparable> Ordering<S> reverse() { return Ordering.natural(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public <E extends Comparable> E min(E a, E b) { return (E)(Comparable)NaturalOrdering.INSTANCE.max(a, b); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public <E extends Comparable> E min(E a, E b, E c, E... rest) { return (E)(Comparable)NaturalOrdering.INSTANCE.max(a, b, c, rest); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public <E extends Comparable> E min(Iterable<E> iterable) { return (E)(Comparable)NaturalOrdering.INSTANCE.max(iterable); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public <E extends Comparable> E max(E a, E b) { return (E)(Comparable)NaturalOrdering.INSTANCE.min(a, b); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public <E extends Comparable> E max(E a, E b, E c, E... rest) { return (E)(Comparable)NaturalOrdering.INSTANCE.min(a, b, c, rest); }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public <E extends Comparable> E max(Iterable<E> iterable) { return (E)(Comparable)NaturalOrdering.INSTANCE.min(iterable); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   private Object readResolve() { return INSTANCE; }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public String toString() { return "Ordering.natural().reverse()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ReverseNaturalOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */