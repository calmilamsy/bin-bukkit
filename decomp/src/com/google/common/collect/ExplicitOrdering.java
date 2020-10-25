/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
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
/*    */ final class ExplicitOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final ImmutableMap<T, Integer> rankMap;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 32 */   ExplicitOrdering(List<T> valuesInOrder) { this(buildRankMap(valuesInOrder)); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   ExplicitOrdering(ImmutableMap<T, Integer> rankMap) { this.rankMap = rankMap; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public int compare(T left, T right) { return rank(left) - rank(right); }
/*    */ 
/*    */   
/*    */   private int rank(T value) {
/* 44 */     Integer rank = (Integer)this.rankMap.get(value);
/* 45 */     if (rank == null) {
/* 46 */       throw new Ordering.IncomparableValueException(value);
/*    */     }
/* 48 */     return rank.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> ImmutableMap<T, Integer> buildRankMap(List<T> valuesInOrder) {
/* 53 */     ImmutableMap.Builder<T, Integer> builder = ImmutableMap.builder();
/* 54 */     int rank = 0;
/* 55 */     for (T value : valuesInOrder) {
/* 56 */       builder.put(value, Integer.valueOf(rank++));
/*    */     }
/* 58 */     return builder.build();
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 62 */     if (object instanceof ExplicitOrdering) {
/* 63 */       ExplicitOrdering<?> that = (ExplicitOrdering)object;
/* 64 */       return this.rankMap.equals(that.rankMap);
/*    */     } 
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 70 */   public int hashCode() { return this.rankMap.hashCode(); }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public String toString() { return "Ordering.explicit(" + this.rankMap.keySet() + ")"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ExplicitOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */