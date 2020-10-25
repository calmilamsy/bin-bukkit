/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public abstract class ImmutableSet<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements Set<E>
/*     */ {
/*  78 */   public static <E> ImmutableSet<E> of() { return EmptyImmutableSet.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static <E> ImmutableSet<E> of(E element) { return new SingletonImmutableSet(element); }
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
/* 100 */   public static <E> ImmutableSet<E> of(E e1, E e2) { return create(new Object[] { e1, e2 }); }
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
/* 112 */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3) { return create(new Object[] { e1, e2, e3 }); }
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
/* 124 */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4) { return create(new Object[] { e1, e2, e3, e4 }); }
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
/* 136 */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5) { return create(new Object[] { e1, e2, e3, e4, e5 }); }
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
/*     */   public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
/* 149 */     int size = others.length + 6;
/* 150 */     List<E> all = new ArrayList<E>(size);
/* 151 */     Collections.addAll(all, new Object[] { e1, e2, e3, e4, e5, e6 });
/* 152 */     Collections.addAll(all, others);
/* 153 */     return create(all, size);
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
/*     */   @Deprecated
/* 167 */   public static <E> ImmutableSet<E> of(E[] elements) { return copyOf(elements); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> copyOf(E[] elements) {
/* 178 */     switch (elements.length) {
/*     */       case 0:
/* 180 */         return of();
/*     */       case 1:
/* 182 */         return of(elements[0]);
/*     */     } 
/* 184 */     return create(elements);
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
/*     */   public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> elements) {
/* 207 */     if (elements instanceof ImmutableSet && !(elements instanceof ImmutableSortedSet))
/*     */     {
/*     */       
/* 210 */       return (ImmutableSet)elements;
/*     */     }
/*     */     
/* 213 */     return copyOfInternal(Collections2.toCollection(elements));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> elements) {
/* 224 */     Collection<E> list = Lists.newArrayList(elements);
/* 225 */     return copyOfInternal(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSet<E> copyOfInternal(Collection<? extends E> collection) {
/* 232 */     switch (collection.size()) {
/*     */       case 0:
/* 234 */         return of();
/*     */       
/*     */       case 1:
/* 237 */         return of(collection.iterator().next());
/*     */     } 
/* 239 */     return create(collection, collection.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   boolean isHashCodeFast() { return false; }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 251 */     if (object == this) {
/* 252 */       return true;
/*     */     }
/* 254 */     if (object instanceof ImmutableSet && isHashCodeFast() && ((ImmutableSet)object).isHashCodeFast() && hashCode() != object.hashCode())
/*     */     {
/*     */ 
/*     */       
/* 258 */       return false;
/*     */     }
/* 260 */     return Collections2.setEquals(this, object);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 264 */     int hashCode = 0;
/* 265 */     for (Object o : this) {
/* 266 */       hashCode += o.hashCode();
/*     */     }
/* 268 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   private static <E> ImmutableSet<E> create(E... elements) { return create(Arrays.asList(elements), elements.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> ImmutableSet<E> create(Iterable<? extends E> iterable, int count) {
/* 282 */     int tableSize = Hashing.chooseTableSize(count);
/* 283 */     Object[] table = new Object[tableSize];
/* 284 */     int mask = tableSize - 1;
/*     */     
/* 286 */     List<E> elements = new ArrayList<E>(count);
/* 287 */     int hashCode = 0;
/*     */     
/* 289 */     label21: for (E element : iterable) {
/* 290 */       int hash = element.hashCode();
/* 291 */       for (int i = Hashing.smear(hash);; i++) {
/* 292 */         int index = i & mask;
/* 293 */         Object value = table[index];
/* 294 */         if (value == null) {
/*     */           
/* 296 */           table[index] = element;
/* 297 */           elements.add(element);
/* 298 */           hashCode += hash; continue label21;
/*     */         } 
/* 300 */         if (value.equals(element)) {
/*     */           continue label21;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     if (elements.size() == 1)
/*     */     {
/* 308 */       return new SingletonImmutableSet(elements.get(0), hashCode); } 
/* 309 */     if (tableSize > Hashing.chooseTableSize(elements.size()))
/*     */     {
/* 311 */       return create(elements, elements.size());
/*     */     }
/* 313 */     return new RegularImmutableSet(elements.toArray(), hashCode, table, mask);
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class ArrayImmutableSet<E>
/*     */     extends ImmutableSet<E>
/*     */   {
/*     */     final Object[] elements;
/*     */ 
/*     */     
/* 323 */     ArrayImmutableSet(Object[] elements) { this.elements = elements; }
/*     */ 
/*     */ 
/*     */     
/* 327 */     public int size() { return this.elements.length; }
/*     */ 
/*     */ 
/*     */     
/* 331 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     public UnmodifiableIterator<E> iterator() { return Iterators.forArray(this.elements); }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 344 */       Object[] array = new Object[size()];
/* 345 */       System.arraycopy(this.elements, 0, array, 0, size());
/* 346 */       return array;
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 350 */       int size = size();
/* 351 */       if (array.length < size) {
/* 352 */         array = (T[])ObjectArrays.newArray(array, size);
/* 353 */       } else if (array.length > size) {
/* 354 */         array[size] = null;
/*     */       } 
/* 356 */       System.arraycopy(this.elements, 0, array, 0, size);
/* 357 */       return array;
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> targets) {
/* 361 */       if (targets == this) {
/* 362 */         return true;
/*     */       }
/* 364 */       if (!(targets instanceof ArrayImmutableSet)) {
/* 365 */         return super.containsAll(targets);
/*     */       }
/* 367 */       if (targets.size() > size()) {
/* 368 */         return false;
/*     */       }
/* 370 */       for (Object target : ((ArrayImmutableSet)targets).elements) {
/* 371 */         if (!contains(target)) {
/* 372 */           return false;
/*     */         }
/*     */       } 
/* 375 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 379 */     ImmutableList<E> createAsList() { return new ImmutableAsList(this.elements, this); }
/*     */   }
/*     */   
/*     */   static abstract class TransformedImmutableSet<D, E>
/*     */     extends ImmutableSet<E>
/*     */   {
/*     */     final D[] source;
/*     */     final int hashCode;
/*     */     
/*     */     TransformedImmutableSet(D[] source, int hashCode) {
/* 389 */       this.source = source;
/* 390 */       this.hashCode = hashCode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     public int size() { return this.source.length; }
/*     */ 
/*     */ 
/*     */     
/* 400 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<E> iterator() {
/* 404 */       return new AbstractIterator<E>() {
/*     */           int index;
/*     */           
/* 407 */           protected E computeNext() { return (E)((super.index < ImmutableSet.TransformedImmutableSet.this.source.length) ? super.this$0.transform(ImmutableSet.TransformedImmutableSet.this.source[super.index++]) : endOfData()); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     public Object[] toArray() { return toArray(new Object[size()]); }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 419 */       int size = size();
/* 420 */       if (array.length < size) {
/* 421 */         array = (T[])ObjectArrays.newArray(array, size);
/* 422 */       } else if (array.length > size) {
/* 423 */         array[size] = null;
/*     */       } 
/*     */ 
/*     */       
/* 427 */       T[] arrayOfT = array;
/* 428 */       for (int i = 0; i < this.source.length; i++) {
/* 429 */         arrayOfT[i] = transform(this.source[i]);
/*     */       }
/* 431 */       return array;
/*     */     }
/*     */ 
/*     */     
/* 435 */     public final int hashCode() { return this.hashCode; }
/*     */ 
/*     */ 
/*     */     
/* 439 */     boolean isHashCodeFast() { return true; }
/*     */ 
/*     */     
/*     */     abstract E transform(D param1D);
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
/* 453 */     SerializedForm(Object[] elements) { this.elements = elements; }
/*     */ 
/*     */     
/* 456 */     Object readResolve() { return ImmutableSet.copyOf(this.elements); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 462 */   Object writeReplace() { return new SerializedForm(toArray()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   public static <E> Builder<E> builder() { return new Builder(); }
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
/*     */   public static class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/* 491 */     final ArrayList<E> contents = Lists.newArrayList();
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
/*     */     public Builder<E> add(E element) {
/* 509 */       this.contents.add(Preconditions.checkNotNull(element));
/* 510 */       return this;
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
/*     */     public Builder<E> add(E... elements) {
/* 523 */       this.contents.ensureCapacity(this.contents.size() + elements.length);
/* 524 */       super.add(elements);
/* 525 */       return this;
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
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 538 */       if (elements instanceof Collection) {
/* 539 */         Collection<?> collection = (Collection)elements;
/* 540 */         this.contents.ensureCapacity(this.contents.size() + collection.size());
/*     */       } 
/* 542 */       super.addAll(elements);
/* 543 */       return this;
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
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 556 */       super.addAll(elements);
/* 557 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 565 */     public ImmutableSet<E> build() { return ImmutableSet.copyOf(this.contents); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */