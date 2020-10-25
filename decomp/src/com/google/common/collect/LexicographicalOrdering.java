/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
/*    */ import java.util.Iterator;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class LexicographicalOrdering<T>
/*    */   extends Ordering<Iterable<T>>
/*    */   implements Serializable
/*    */ {
/*    */   final Ordering<? super T> elementOrder;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 36 */   LexicographicalOrdering(Ordering<? super T> elementOrder) { this.elementOrder = elementOrder; }
/*    */ 
/*    */   
/*    */   public int compare(Iterable<T> leftIterable, Iterable<T> rightIterable) {
/* 40 */     Iterator<T> left = leftIterable.iterator();
/* 41 */     Iterator<T> right = rightIterable.iterator();
/* 42 */     while (left.hasNext()) {
/* 43 */       if (!right.hasNext()) {
/* 44 */         return 1;
/*    */       }
/* 46 */       int result = this.elementOrder.compare(left.next(), right.next());
/* 47 */       if (result != 0) {
/* 48 */         return result;
/*    */       }
/*    */     } 
/* 51 */     if (right.hasNext()) {
/* 52 */       return -1;
/*    */     }
/* 54 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 58 */     if (object == this) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (object instanceof LexicographicalOrdering) {
/* 62 */       LexicographicalOrdering<?> that = (LexicographicalOrdering)object;
/* 63 */       return this.elementOrder.equals(that.elementOrder);
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 69 */   public int hashCode() { return this.elementOrder.hashCode() ^ 0x7BB78CF5; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public String toString() { return this.elementOrder + ".lexicographical()"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\LexicographicalOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */