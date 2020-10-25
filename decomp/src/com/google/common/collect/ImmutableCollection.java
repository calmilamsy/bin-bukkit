/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public abstract class ImmutableCollection<E>
/*     */   extends Object
/*     */   implements Collection<E>, Serializable
/*     */ {
/*  44 */   static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION = new EmptyImmutableCollection(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ImmutableList<E> asList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  55 */     Object[] newArray = new Object[size()];
/*  56 */     return toArray(newArray);
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] other) {
/*  60 */     int size = size();
/*  61 */     if (other.length < size) {
/*  62 */       other = (T[])ObjectArrays.newArray(other, size);
/*  63 */     } else if (other.length > size) {
/*  64 */       other[size] = null;
/*     */     } 
/*     */ 
/*     */     
/*  68 */     T[] arrayOfT = other;
/*  69 */     int index = 0;
/*  70 */     for (E element : this) {
/*  71 */       arrayOfT[index++] = element;
/*     */     }
/*  73 */     return other;
/*     */   }
/*     */   
/*     */   public boolean contains(@Nullable Object object) {
/*  77 */     if (object == null) {
/*  78 */       return false;
/*     */     }
/*  80 */     for (E element : this) {
/*  81 */       if (element.equals(object)) {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> targets) {
/*  89 */     for (Object target : targets) {
/*  90 */       if (!contains(target)) {
/*  91 */         return false;
/*     */       }
/*     */     } 
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*  98 */   public boolean isEmpty() { return (size() == 0); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     StringBuilder sb = (new StringBuilder(size() * 16)).append('[');
/* 103 */     Collections2.standardJoiner.appendTo(sb, this);
/* 104 */     return sb.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public final boolean add(E e) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public final boolean remove(Object object) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public final boolean addAll(Collection<? extends E> newElements) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public final boolean removeAll(Collection<?> oldElements) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public final boolean retainAll(Collection<?> elementsToKeep) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public final void clear() { throw new UnsupportedOperationException(); }
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
/*     */   @GwtCompatible(serializable = false)
/*     */   public ImmutableList<E> asList() {
/* 172 */     ImmutableList<E> list = this.asList;
/* 173 */     return (list == null) ? (this.asList = createAsList()) : list;
/*     */   }
/*     */   
/*     */   ImmutableList<E> createAsList() {
/* 177 */     switch (size()) {
/*     */       case 0:
/* 179 */         return ImmutableList.of();
/*     */       case 1:
/* 181 */         return ImmutableList.of(iterator().next());
/*     */     } 
/* 183 */     return new ImmutableAsList(toArray(), this);
/*     */   }
/*     */   
/*     */   private static class EmptyImmutableCollection
/*     */     extends ImmutableCollection<Object> {
/*     */     private EmptyImmutableCollection() {}
/*     */     
/* 190 */     public int size() { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 194 */     public boolean isEmpty() { return true; }
/*     */ 
/*     */ 
/*     */     
/* 198 */     public boolean contains(@Nullable Object object) { return false; }
/*     */ 
/*     */ 
/*     */     
/* 202 */     public UnmodifiableIterator<Object> iterator() { return Iterators.EMPTY_ITERATOR; }
/*     */ 
/*     */     
/* 205 */     private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */ 
/*     */     
/* 208 */     public Object[] toArray() { return EMPTY_ARRAY; }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 212 */       if (array.length > 0) {
/* 213 */         array[0] = null;
/*     */       }
/* 215 */       return array;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ArrayImmutableCollection<E>
/*     */     extends ImmutableCollection<E>
/*     */   {
/*     */     private final E[] elements;
/*     */     
/* 224 */     ArrayImmutableCollection(E[] elements) { this.elements = elements; }
/*     */ 
/*     */ 
/*     */     
/* 228 */     public int size() { return this.elements.length; }
/*     */ 
/*     */ 
/*     */     
/* 232 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */     
/* 236 */     public UnmodifiableIterator<E> iterator() { return Iterators.forArray(this.elements); }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     final Object[] elements;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 247 */     SerializedForm(Object[] elements) { this.elements = elements; }
/*     */ 
/*     */     
/* 250 */     Object readResolve() { return (this.elements.length == 0) ? ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION : new ImmutableCollection.ArrayImmutableCollection(Platform.clone(this.elements)); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   Object writeReplace() { return new SerializedForm(toArray()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Builder<E>
/*     */     extends Object
/*     */   {
/*     */     public abstract Builder<E> add(E param1E);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E... elements) {
/* 290 */       for (E element : elements) {
/* 291 */         add(element);
/*     */       }
/* 293 */       return this;
/*     */     }
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
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 309 */       for (E element : elements) {
/* 310 */         add(element);
/*     */       }
/* 312 */       return this;
/*     */     }
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
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 328 */       while (elements.hasNext()) {
/* 329 */         add(elements.next());
/*     */       }
/* 331 */       return this;
/*     */     }
/*     */     
/*     */     public abstract ImmutableCollection<E> build();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */