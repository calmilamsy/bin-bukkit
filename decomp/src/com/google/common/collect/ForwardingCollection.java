/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
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
/*    */ public abstract class ForwardingCollection<E>
/*    */   extends ForwardingObject
/*    */   implements Collection<E>
/*    */ {
/* 41 */   public Iterator<E> iterator() { return delegate().iterator(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int size() { return delegate().size(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean removeAll(Collection<?> collection) { return delegate().removeAll(collection); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean isEmpty() { return delegate().isEmpty(); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean contains(Object object) { return delegate().contains(object); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Object[] toArray() { return delegate().toArray(); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public <T> T[] toArray(T[] array) { return (T[])delegate().toArray(array); }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public boolean add(E element) { return delegate().add(element); }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public boolean remove(Object object) { return delegate().remove(object); }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public boolean containsAll(Collection<?> collection) { return delegate().containsAll(collection); }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public boolean addAll(Collection<? extends E> collection) { return delegate().addAll(collection); }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public boolean retainAll(Collection<?> collection) { return delegate().retainAll(collection); }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public void clear() { delegate().clear(); }
/*    */   
/*    */   protected abstract Collection<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */