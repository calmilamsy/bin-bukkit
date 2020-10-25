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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class NullsFirstOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Ordering<? super T> ordering;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 31 */   NullsFirstOrdering(Ordering<? super T> ordering) { this.ordering = ordering; }
/*    */ 
/*    */   
/*    */   public int compare(T left, T right) {
/* 35 */     if (left == right) {
/* 36 */       return 0;
/*    */     }
/* 38 */     if (left == null) {
/* 39 */       return -1;
/*    */     }
/* 41 */     if (right == null) {
/* 42 */       return 1;
/*    */     }
/* 44 */     return this.ordering.compare(left, right);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public <S extends T> Ordering<S> reverse() { return this.ordering.reverse().nullsLast(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public <S extends T> Ordering<S> nullsFirst() { return this; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public <S extends T> Ordering<S> nullsLast() { return this.ordering.nullsLast(); }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 62 */     if (object == this) {
/* 63 */       return true;
/*    */     }
/* 65 */     if (object instanceof NullsFirstOrdering) {
/* 66 */       NullsFirstOrdering<?> that = (NullsFirstOrdering)object;
/* 67 */       return this.ordering.equals(that.ordering);
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 73 */   public int hashCode() { return this.ordering.hashCode() ^ 0x39153A74; }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public String toString() { return this.ordering + ".nullsFirst()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\NullsFirstOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */