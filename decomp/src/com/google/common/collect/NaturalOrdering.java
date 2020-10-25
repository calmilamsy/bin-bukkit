/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ final class NaturalOrdering
/*    */   extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 31 */   static final NaturalOrdering INSTANCE = new NaturalOrdering();
/*    */   
/*    */   public int compare(Comparable left, Comparable right) {
/* 34 */     Preconditions.checkNotNull(right);
/* 35 */     if (left == right) {
/* 36 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 40 */     return left.compareTo(right);
/*    */   }
/*    */ 
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 46 */   public <S extends Comparable> Ordering<S> reverse() { return ReverseNaturalOrdering.INSTANCE; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public int binarySearch(List<? extends Comparable> sortedList, Comparable key) { return Collections.binarySearch(sortedList, key); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends Comparable> List<E> sortedCopy(Iterable<E> iterable) {
/* 59 */     List<E> list = Lists.newArrayList(iterable);
/* 60 */     Collections.sort(list);
/* 61 */     return list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 66 */   private Object readResolve() { return INSTANCE; }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String toString() { return "Ordering.natural()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\NaturalOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */