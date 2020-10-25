/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class CompoundOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final ImmutableList<Comparator<? super T>> comparators;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 32 */   CompoundOrdering(Comparator<? super T> primary, Comparator<? super T> secondary) { this.comparators = ImmutableList.of(primary, secondary); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   CompoundOrdering(Iterable<? extends Comparator<? super T>> comparators) { this.comparators = ImmutableList.copyOf(comparators); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   CompoundOrdering(List<? extends Comparator<? super T>> comparators, Comparator<? super T> lastComparator) { this.comparators = (new ImmutableList.Builder()).addAll(comparators).add(lastComparator).build(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(T left, T right) {
/* 47 */     for (Comparator<? super T> comparator : this.comparators) {
/* 48 */       int result = comparator.compare(left, right);
/* 49 */       if (result != 0) {
/* 50 */         return result;
/*    */       }
/*    */     } 
/* 53 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 57 */     if (object == this) {
/* 58 */       return true;
/*    */     }
/* 60 */     if (object instanceof CompoundOrdering) {
/* 61 */       CompoundOrdering<?> that = (CompoundOrdering)object;
/* 62 */       return this.comparators.equals(that.comparators);
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 68 */   public int hashCode() { return this.comparators.hashCode(); }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String toString() { return "Ordering.compound(" + this.comparators + ")"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\CompoundOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */