/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableList<E>
/*     */   extends ImmutableList<E>
/*     */ {
/*     */   final E element;
/*     */   
/*  42 */   SingletonImmutableList(E element) { this.element = Preconditions.checkNotNull(element); }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/*  46 */     Preconditions.checkElementIndex(index, 1);
/*  47 */     return (E)this.element;
/*     */   }
/*     */ 
/*     */   
/*  51 */   public int indexOf(@Nullable Object object) { return this.element.equals(object) ? 0 : -1; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public UnmodifiableIterator<E> iterator() { return Iterators.singletonIterator(this.element); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int lastIndexOf(@Nullable Object object) { return this.element.equals(object) ? 0 : -1; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public ListIterator<E> listIterator() { return listIterator(0); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public ListIterator<E> listIterator(int start) { return Collections.singletonList(this.element).listIterator(start); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public int size() { return 1; }
/*     */ 
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/*  76 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
/*  77 */     return (fromIndex == toIndex) ? ImmutableList.of() : this;
/*     */   }
/*     */ 
/*     */   
/*  81 */   public boolean contains(@Nullable Object object) { return this.element.equals(object); }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  85 */     if (object == this) {
/*  86 */       return true;
/*     */     }
/*  88 */     if (object instanceof List) {
/*  89 */       List<?> that = (List)object;
/*  90 */       return (that.size() == 1 && this.element.equals(that.get(0)));
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public int hashCode() { return 31 + this.element.hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public Object[] toArray() { return new Object[] { this.element }; }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 110 */     if (array.length == 0) {
/* 111 */       array = (T[])ObjectArrays.newArray(array, 1);
/* 112 */     } else if (array.length > 1) {
/* 113 */       array[1] = null;
/*     */     } 
/*     */     
/* 116 */     T[] arrayOfT = array;
/* 117 */     arrayOfT[0] = this.element;
/* 118 */     return array;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\SingletonImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */