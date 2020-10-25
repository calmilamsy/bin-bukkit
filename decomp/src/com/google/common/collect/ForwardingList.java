/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingList<E>
/*    */   extends ForwardingCollection<E>
/*    */   implements List<E>
/*    */ {
/* 47 */   public void add(int index, E element) { delegate().add(index, element); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public boolean addAll(int index, Collection<? extends E> elements) { return delegate().addAll(index, elements); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public E get(int index) { return (E)delegate().get(index); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int indexOf(Object element) { return delegate().indexOf(element); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public int lastIndexOf(Object element) { return delegate().lastIndexOf(element); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public ListIterator<E> listIterator() { return delegate().listIterator(); }
/*    */ 
/*    */ 
/*    */   
/* 71 */   public ListIterator<E> listIterator(int index) { return delegate().listIterator(index); }
/*    */ 
/*    */ 
/*    */   
/* 75 */   public E remove(int index) { return (E)delegate().remove(index); }
/*    */ 
/*    */ 
/*    */   
/* 79 */   public E set(int index, E element) { return (E)delegate().set(index, element); }
/*    */ 
/*    */ 
/*    */   
/* 83 */   public List<E> subList(int fromIndex, int toIndex) { return delegate().subList(fromIndex, toIndex); }
/*    */ 
/*    */ 
/*    */   
/* 87 */   public boolean equals(@Nullable Object object) { return (object == this || delegate().equals(object)); }
/*    */ 
/*    */ 
/*    */   
/* 91 */   public int hashCode() { return delegate().hashCode(); }
/*    */   
/*    */   protected abstract List<E> delegate();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ForwardingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */