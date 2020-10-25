/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class ComparatorOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Comparator<T> comparator;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 35 */   ComparatorOrdering(Comparator<T> comparator) { this.comparator = (Comparator)Preconditions.checkNotNull(comparator); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public int compare(T a, T b) { return this.comparator.compare(a, b); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int binarySearch(List<? extends T> sortedList, T key) { return Collections.binarySearch(sortedList, key, this.comparator); }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
/* 49 */     List<E> list = Lists.newArrayList(iterable);
/* 50 */     Collections.sort(list, this.comparator);
/* 51 */     return list;
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 55 */     if (object == this) {
/* 56 */       return true;
/*    */     }
/* 58 */     if (object instanceof ComparatorOrdering) {
/* 59 */       ComparatorOrdering<?> that = (ComparatorOrdering)object;
/* 60 */       return this.comparator.equals(that.comparator);
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 66 */   public int hashCode() { return this.comparator.hashCode(); }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String toString() { return this.comparator.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ComparatorOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */