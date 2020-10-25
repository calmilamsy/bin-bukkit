/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.Set;
/*    */ import java.util.SortedSet;
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
/*    */ public abstract class ForwardingSortedSet<E>
/*    */   extends ForwardingSet<E>
/*    */   implements SortedSet<E>
/*    */ {
/* 41 */   public Comparator<? super E> comparator() { return delegate().comparator(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public E first() { return (E)delegate().first(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public SortedSet<E> headSet(E toElement) { return delegate().headSet(toElement); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public E last() { return (E)delegate().last(); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public SortedSet<E> subSet(E fromElement, E toElement) { return delegate().subSet(fromElement, toElement); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public SortedSet<E> tailSet(E fromElement) { return delegate().tailSet(fromElement); }
/*    */   
/*    */   protected abstract SortedSet<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingSortedSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */