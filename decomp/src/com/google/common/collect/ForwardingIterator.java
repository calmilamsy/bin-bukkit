/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingIterator<T>
/*    */   extends ForwardingObject
/*    */   implements Iterator<T>
/*    */ {
/* 40 */   public boolean hasNext() { return delegate().hasNext(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public T next() { return (T)delegate().next(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void remove() { delegate().remove(); }
/*    */   
/*    */   protected abstract Iterator<T> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */