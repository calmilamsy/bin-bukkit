/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Queue;
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
/*    */ public abstract class ForwardingQueue<E>
/*    */   extends ForwardingCollection<E>
/*    */   implements Queue<E>
/*    */ {
/* 40 */   public boolean offer(E o) { return delegate().offer(o); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public E poll() { return (E)delegate().poll(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public E remove() { return (E)delegate().remove(); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public E peek() { return (E)delegate().peek(); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public E element() { return (E)delegate().element(); }
/*    */   
/*    */   protected abstract Queue<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */