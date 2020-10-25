/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ final class ExplicitOrderedImmutableSortedSet<E>
/*     */   extends ImmutableSortedSet<E>
/*     */ {
/*     */   private final Object[] elements;
/*     */   private final int fromIndex;
/*     */   private final int toIndex;
/*     */   
/*     */   static <E> ImmutableSortedSet<E> create(List<E> list) {
/*  42 */     ExplicitOrdering<E> ordering = new ExplicitOrdering<E>(list);
/*  43 */     if (ordering.rankMap.isEmpty()) {
/*  44 */       return emptySet(ordering);
/*     */     }
/*     */     
/*  47 */     Object[] elements = ordering.rankMap.keySet().toArray();
/*  48 */     return new ExplicitOrderedImmutableSortedSet(elements, ordering);
/*     */   }
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
/*  65 */   ExplicitOrderedImmutableSortedSet(Object[] elements, Comparator<? super E> comparator) { this(elements, comparator, 0, elements.length); }
/*     */ 
/*     */ 
/*     */   
/*     */   ExplicitOrderedImmutableSortedSet(Object[] elements, Comparator<? super E> comparator, int fromIndex, int toIndex) {
/*  70 */     super(comparator);
/*  71 */     this.elements = elements;
/*  72 */     this.fromIndex = fromIndex;
/*  73 */     this.toIndex = toIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private ImmutableMap<E, Integer> rankMap() { return ((ExplicitOrdering)comparator()).rankMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public UnmodifiableIterator<E> iterator() { return Iterators.forArray(this.elements, this.fromIndex, size()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int size() { return this.toIndex - this.fromIndex; }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  98 */     Integer index = (Integer)rankMap().get(o);
/*  99 */     return (index != null && index.intValue() >= this.fromIndex && index.intValue() < this.toIndex);
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 103 */     Object[] array = new Object[size()];
/* 104 */     Platform.unsafeArrayCopy(this.elements, this.fromIndex, array, 0, size());
/* 105 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 110 */     int size = size();
/* 111 */     if (array.length < size) {
/* 112 */       array = (T[])ObjectArrays.newArray(array, size);
/* 113 */     } else if (array.length > size) {
/* 114 */       array[size] = null;
/*     */     } 
/* 116 */     Platform.unsafeArrayCopy(this.elements, this.fromIndex, array, 0, size);
/* 117 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     int hash = 0;
/* 123 */     for (int i = this.fromIndex; i < this.toIndex; i++) {
/* 124 */       hash += this.elements[i].hashCode();
/*     */     }
/* 126 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public E first() { return (E)this.elements[this.fromIndex]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public E last() { return (E)this.elements[this.toIndex - 1]; }
/*     */ 
/*     */ 
/*     */   
/* 142 */   ImmutableSortedSet<E> headSetImpl(E toElement) { return createSubset(this.fromIndex, findSubsetIndex(toElement)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   ImmutableSortedSet<E> subSetImpl(E fromElement, E toElement) { return createSubset(findSubsetIndex(fromElement), findSubsetIndex(toElement)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   ImmutableSortedSet<E> tailSetImpl(E fromElement) { return createSubset(findSubsetIndex(fromElement), this.toIndex); }
/*     */ 
/*     */   
/*     */   private int findSubsetIndex(E element) {
/* 156 */     Integer index = (Integer)rankMap().get(element);
/* 157 */     if (index == null)
/*     */     {
/* 159 */       throw new ClassCastException();
/*     */     }
/* 161 */     if (index.intValue() <= this.fromIndex)
/* 162 */       return this.fromIndex; 
/* 163 */     if (index.intValue() >= this.toIndex) {
/* 164 */       return this.toIndex;
/*     */     }
/* 166 */     return index.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ImmutableSortedSet<E> createSubset(int newFromIndex, int newToIndex) {
/* 172 */     if (newFromIndex < newToIndex) {
/* 173 */       return new ExplicitOrderedImmutableSortedSet(this.elements, this.comparator, newFromIndex, newToIndex);
/*     */     }
/*     */     
/* 176 */     return emptySet(this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 181 */   boolean hasPartialArray() { return (this.fromIndex != 0 || this.toIndex != this.elements.length); }
/*     */ 
/*     */   
/*     */   int indexOf(Object target) {
/* 185 */     Integer index = (Integer)rankMap().get(target);
/* 186 */     return (index != null && index.intValue() >= this.fromIndex && index.intValue() < this.toIndex) ? (index.intValue() - this.fromIndex) : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   ImmutableList<E> createAsList() { return new ImmutableSortedAsList(this.elements, this.fromIndex, size(), this); }
/*     */ 
/*     */   
/*     */   private static class SerializedForm<E>
/*     */     extends Object
/*     */     implements Serializable
/*     */   {
/*     */     final Object[] elements;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 205 */     public SerializedForm(Object[] elements) { this.elements = elements; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     Object readResolve() { return ImmutableSortedSet.withExplicitOrder(Arrays.asList(this.elements)); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   private void readObject(ObjectInputStream stream) throws InvalidObjectException { throw new InvalidObjectException("Use SerializedForm"); }
/*     */ 
/*     */ 
/*     */   
/* 222 */   Object writeReplace() { return new SerializedForm(toArray()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ExplicitOrderedImmutableSortedSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */