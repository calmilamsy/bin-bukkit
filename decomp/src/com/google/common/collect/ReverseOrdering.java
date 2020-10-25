/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
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
/*    */ final class ReverseOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Ordering<? super T> forwardOrder;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 32 */   ReverseOrdering(Ordering<? super T> forwardOrder) { this.forwardOrder = (Ordering)Preconditions.checkNotNull(forwardOrder); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public int compare(T a, T b) { return this.forwardOrder.compare(b, a); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public <S extends T> Ordering<S> reverse() { return this.forwardOrder; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public <E extends T> E min(E a, E b) { return (E)this.forwardOrder.max(a, b); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public <E extends T> E min(E a, E b, E c, E... rest) { return (E)this.forwardOrder.max(a, b, c, rest); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public <E extends T> E min(Iterable<E> iterable) { return (E)this.forwardOrder.max(iterable); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public <E extends T> E max(E a, E b) { return (E)this.forwardOrder.min(a, b); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public <E extends T> E max(E a, E b, E c, E... rest) { return (E)this.forwardOrder.min(a, b, c, rest); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public <E extends T> E max(Iterable<E> iterable) { return (E)this.forwardOrder.min(iterable); }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public int hashCode() { return -this.forwardOrder.hashCode(); }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 75 */     if (object == this) {
/* 76 */       return true;
/*    */     }
/* 78 */     if (object instanceof ReverseOrdering) {
/* 79 */       ReverseOrdering<?> that = (ReverseOrdering)object;
/* 80 */       return this.forwardOrder.equals(that.forwardOrder);
/*    */     } 
/* 82 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 86 */   public String toString() { return this.forwardOrder + ".reverse()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ReverseOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */