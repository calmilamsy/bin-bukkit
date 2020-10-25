/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
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
/*     */ public abstract class ImmutableList<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements List<E>, RandomAccess
/*     */ {
/*  67 */   public static <E> ImmutableList<E> of() { return EmptyImmutableList.INSTANCE; }
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
/*  79 */   public static <E> ImmutableList<E> of(E element) { return new SingletonImmutableList(element); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static <E> ImmutableList<E> of(E e1, E e2) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2 })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3 })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4 })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5 })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6 })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6, e7 })); }
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
/* 145 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8 })); }
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
/* 156 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9 })); }
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
/* 167 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10 })); }
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
/* 178 */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) { return new RegularImmutableList(copyIntoArray(new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11 })); }
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
/*     */   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E... others) {
/* 193 */     int paramCount = 12;
/* 194 */     Object[] array = new Object[12 + others.length];
/* 195 */     copyIntoArray(array, 0, new Object[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12 });
/* 196 */     copyIntoArray(array, 12, others);
/* 197 */     return new RegularImmutableList(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <E> ImmutableList<E> of(E[] elements) {
/* 208 */     switch (elements.length) {
/*     */       case 0:
/* 210 */         return of();
/*     */       case 1:
/* 212 */         return new SingletonImmutableList(elements[0]);
/*     */     } 
/* 214 */     return new RegularImmutableList(copyIntoArray(elements));
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
/*     */   public static <E> ImmutableList<E> copyOf(Iterable<? extends E> elements) {
/* 228 */     Preconditions.checkNotNull(elements);
/* 229 */     return (elements instanceof Collection) ? copyOf((Collection)elements) : copyOf(elements.iterator());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements) {
/* 255 */     if (elements instanceof ImmutableCollection) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       ImmutableCollection<E> list = (ImmutableCollection)elements;
/* 262 */       return list.asList();
/*     */     } 
/* 264 */     return copyFromCollection(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public static <E> ImmutableList<E> copyOf(Iterator<? extends E> elements) { return copyFromCollection(Lists.newArrayList(elements)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableList<E> copyOf(E[] elements) {
/* 283 */     switch (elements.length) {
/*     */       case 0:
/* 285 */         return of();
/*     */       case 1:
/* 287 */         return new SingletonImmutableList(elements[0]);
/*     */     } 
/* 289 */     return new RegularImmutableList(copyIntoArray(elements));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableList<E> copyFromCollection(Collection<? extends E> collection) {
/* 295 */     Object[] elements = collection.toArray();
/* 296 */     switch (elements.length) {
/*     */       case 0:
/* 298 */         return of();
/*     */       
/*     */       case 1:
/* 301 */         return new SingletonImmutableList<E>(elements[0]);
/*     */     } 
/*     */     
/* 304 */     return new RegularImmutableList(copyIntoArray(elements));
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
/* 336 */   public final boolean addAll(int index, Collection<? extends E> newElements) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public final E set(int index, E element) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 354 */   public final void add(int index, E element) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 363 */   public final E remove(int index) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */   
/* 367 */   private static Object[] copyIntoArray(Object... source) { return copyIntoArray(new Object[source.length], 0, source); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object[] copyIntoArray(Object[] dest, int pos, Object... source) {
/* 372 */     int index = pos;
/* 373 */     for (Object element : source) {
/* 374 */       if (element == null) {
/* 375 */         throw new NullPointerException("at index " + index);
/*     */       }
/* 377 */       dest[index++] = element;
/*     */     } 
/* 379 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public ImmutableList<E> asList() { return this; }
/*     */ 
/*     */   
/*     */   private static class SerializedForm
/*     */     implements Serializable
/*     */   {
/*     */     final Object[] elements;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 398 */     SerializedForm(Object[] elements) { this.elements = elements; }
/*     */ 
/*     */     
/* 401 */     Object readResolve() { return ImmutableList.copyOf(this.elements); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 408 */   private void readObject(ObjectInputStream stream) throws InvalidObjectException { throw new InvalidObjectException("Use SerializedForm"); }
/*     */ 
/*     */ 
/*     */   
/* 412 */   Object writeReplace() { return new SerializedForm(toArray()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   public static <E> Builder<E> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int indexOf(@Nullable Object paramObject);
/*     */ 
/*     */   
/*     */   public abstract int lastIndexOf(@Nullable Object paramObject);
/*     */ 
/*     */   
/*     */   public abstract ImmutableList<E> subList(int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public static final class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/* 440 */     private final ArrayList<E> contents = Lists.newArrayList();
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
/*     */     public Builder<E> add(E element) {
/* 456 */       this.contents.add(Preconditions.checkNotNull(element));
/* 457 */       return this;
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
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 469 */       if (elements instanceof Collection) {
/* 470 */         Collection<?> collection = (Collection)elements;
/* 471 */         this.contents.ensureCapacity(this.contents.size() + collection.size());
/*     */       } 
/* 473 */       super.addAll(elements);
/* 474 */       return this;
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
/*     */     public Builder<E> add(E... elements) {
/* 486 */       this.contents.ensureCapacity(this.contents.size() + elements.length);
/* 487 */       super.add(elements);
/* 488 */       return this;
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
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 500 */       super.addAll(elements);
/* 501 */       return this;
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
/* 513 */     public ImmutableList<E> build() { return ImmutableList.copyOf(this.contents); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */