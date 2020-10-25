/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
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
/*    */ public abstract class ForwardingMultiset<E>
/*    */   extends ForwardingCollection<E>
/*    */   implements Multiset<E>
/*    */ {
/* 42 */   public int count(Object element) { return delegate().count(element); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public int add(E element, int occurrences) { return delegate().add(element, occurrences); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public int remove(Object element, int occurrences) { return delegate().remove(element, occurrences); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Set<E> elementSet() { return delegate().elementSet(); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public Set<Multiset.Entry<E>> entrySet() { return delegate().entrySet(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean equals(@Nullable Object object) { return (object == this || delegate().equals(object)); }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public int hashCode() { return delegate().hashCode(); }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public int setCount(E element, int count) { return delegate().setCount(element, count); }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public boolean setCount(E element, int oldCount, int newCount) { return delegate().setCount(element, oldCount, newCount); }
/*    */   
/*    */   protected abstract Multiset<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */