/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Iterator;
/*    */ import java.util.ListIterator;
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
/*    */ public abstract class ForwardingListIterator<E>
/*    */   extends ForwardingIterator<E>
/*    */   implements ListIterator<E>
/*    */ {
/* 40 */   public void add(E element) { delegate().add(element); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean hasPrevious() { return delegate().hasPrevious(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public int nextIndex() { return delegate().nextIndex(); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public E previous() { return (E)delegate().previous(); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public int previousIndex() { return delegate().previousIndex(); }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public void set(E element) { delegate().set(element); }
/*    */   
/*    */   protected abstract ListIterator<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingListIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */