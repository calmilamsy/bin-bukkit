/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true)
/*    */ final class ByFunctionOrdering<F, T>
/*    */   extends Ordering<F>
/*    */   implements Serializable
/*    */ {
/*    */   final Function<F, ? extends T> function;
/*    */   final Ordering<T> ordering;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
/* 40 */     this.function = (Function)Preconditions.checkNotNull(function);
/* 41 */     this.ordering = (Ordering)Preconditions.checkNotNull(ordering);
/*    */   }
/*    */ 
/*    */   
/* 45 */   public int compare(F left, F right) { return this.ordering.compare(this.function.apply(left), this.function.apply(right)); }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 49 */     if (object == this) {
/* 50 */       return true;
/*    */     }
/* 52 */     if (object instanceof ByFunctionOrdering) {
/* 53 */       ByFunctionOrdering<?, ?> that = (ByFunctionOrdering)object;
/* 54 */       return (this.function.equals(that.function) && this.ordering.equals(that.ordering));
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 61 */   public int hashCode() { return Objects.hashCode(new Object[] { this.function, this.ordering }); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public String toString() { return this.ordering + ".onResultOf(" + this.function + ")"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ByFunctionOrdering.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */