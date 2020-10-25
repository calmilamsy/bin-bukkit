/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible
/*     */ public final class Multisets
/*     */ {
/*  60 */   public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) { return new UnmodifiableMultiset((Multiset)Preconditions.checkNotNull(multiset)); }
/*     */   
/*     */   private static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
/*     */     final Multiset<? extends E> delegate;
/*     */     Set<E> elementSet;
/*     */     Set<Multiset.Entry<E>> entrySet;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*  68 */     UnmodifiableMultiset(Multiset<? extends E> delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     protected Multiset<E> delegate() { return this.delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<E> elementSet() {
/*  80 */       Set<E> es = this.elementSet;
/*  81 */       return (es == null) ? (this.elementSet = Collections.unmodifiableSet(this.delegate.elementSet())) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Multiset.Entry<E>> entrySet() {
/*  90 */       Set<Multiset.Entry<E>> es = this.entrySet;
/*  91 */       return (es == null) ? (this.entrySet = Collections.unmodifiableSet(this.delegate.entrySet())) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     public Iterator<E> iterator() { return Iterators.unmodifiableIterator(this.delegate.iterator()); }
/*     */ 
/*     */ 
/*     */     
/* 105 */     public boolean add(E element) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 109 */     public int add(E element, int occurences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 113 */     public boolean addAll(Collection<? extends E> elementsToAdd) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 117 */     public boolean remove(Object element) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 121 */     public int remove(Object element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 125 */     public boolean removeAll(Collection<?> elementsToRemove) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 129 */     public boolean retainAll(Collection<?> elementsToRetain) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 133 */     public void clear() { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 137 */     public int setCount(E element, int count) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 141 */     public boolean setCount(E element, int oldCount, int newCount) { throw new UnsupportedOperationException(); }
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
/*     */   public static <E> Multiset.Entry<E> immutableEntry(@Nullable final E e, final int n) {
/* 156 */     Preconditions.checkArgument((n >= 0));
/* 157 */     return new AbstractEntry<E>()
/*     */       {
/* 159 */         public E getElement() { return (E)e; }
/*     */ 
/*     */         
/* 162 */         public int getCount() { return n; }
/*     */       };
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
/* 185 */   static <E> Multiset<E> forSet(Set<E> set) { return new SetMultiset(set); }
/*     */   
/*     */   private static class SetMultiset<E>
/*     */     extends ForwardingCollection<E> implements Multiset<E>, Serializable {
/*     */     final Set<E> delegate;
/*     */     Set<E> elementSet;
/*     */     Set<Multiset.Entry<E>> entrySet;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 194 */     SetMultiset(Set<E> set) { this.delegate = (Set)Preconditions.checkNotNull(set); }
/*     */ 
/*     */ 
/*     */     
/* 198 */     protected Set<E> delegate() { return this.delegate; }
/*     */ 
/*     */ 
/*     */     
/* 202 */     public int count(Object element) { return this.delegate.contains(element) ? 1 : 0; }
/*     */ 
/*     */ 
/*     */     
/* 206 */     public int add(E element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */     
/*     */     public int remove(Object element, int occurrences) {
/* 210 */       if (occurrences == 0) {
/* 211 */         return count(element);
/*     */       }
/* 213 */       Preconditions.checkArgument((occurrences > 0));
/* 214 */       return this.delegate.remove(element) ? 1 : 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<E> elementSet() {
/* 220 */       Set<E> es = this.elementSet;
/* 221 */       return (es == null) ? (this.elementSet = new ElementSet()) : es;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Multiset.Entry<E>> entrySet() {
/* 227 */       Set<Multiset.Entry<E>> es = this.entrySet;
/* 228 */       return (es == null) ? (this.entrySet = new EntrySet()) : es;
/*     */     }
/*     */ 
/*     */     
/* 232 */     public boolean add(E o) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */     
/* 236 */     public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */     
/*     */     public int setCount(E element, int count) {
/* 240 */       Multisets.checkNonnegative(count, "count");
/*     */       
/* 242 */       if (count == count(element))
/* 243 */         return count; 
/* 244 */       if (count == 0) {
/* 245 */         remove(element);
/* 246 */         return 1;
/*     */       } 
/* 248 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 253 */     public boolean setCount(E element, int oldCount, int newCount) { return Multisets.setCountImpl(this, element, oldCount, newCount); }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 257 */       if (object instanceof Multiset) {
/* 258 */         Multiset<?> that = (Multiset)object;
/* 259 */         return (size() == that.size() && this.delegate.equals(that.elementSet()));
/*     */       } 
/* 261 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 265 */       int sum = 0;
/* 266 */       for (E e : this) {
/* 267 */         sum += (((e == null) ? 0 : e.hashCode()) ^ true);
/*     */       }
/* 269 */       return sum;
/*     */     }
/*     */     
/*     */     class ElementSet
/*     */       extends ForwardingSet<E>
/*     */     {
/* 275 */       protected Set<E> delegate() { return Multisets.SetMultiset.this.delegate; }
/*     */ 
/*     */ 
/*     */       
/* 279 */       public boolean add(E o) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */       
/* 283 */       public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
/*     */     }
/*     */ 
/*     */     
/*     */     class EntrySet
/*     */       extends AbstractSet<Multiset.Entry<E>>
/*     */     {
/* 290 */       public int size() { return Multisets.SetMultiset.this.delegate.size(); }
/*     */       
/*     */       public Iterator<Multiset.Entry<E>> iterator() {
/* 293 */         return new Iterator<Multiset.Entry<E>>()
/*     */           {
/*     */             final Iterator<E> elements;
/*     */             
/* 297 */             public boolean hasNext() { return super.elements.hasNext(); }
/*     */ 
/*     */             
/* 300 */             public Multiset.Entry<E> next() { return Multisets.immutableEntry(super.elements.next(), 1); }
/*     */ 
/*     */             
/* 303 */             public void remove() { super.elements.remove(); }
/*     */           };
/*     */       }
/*     */     }
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
/*     */   static int inferDistinctElements(Iterable<?> elements) {
/* 320 */     if (elements instanceof Multiset) {
/* 321 */       return ((Multiset)elements).elementSet().size();
/*     */     }
/* 323 */     return 11;
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
/*     */   public static <E> Multiset<E> intersection(final Multiset<E> multiset1, final Multiset<?> multiset2) {
/* 341 */     Preconditions.checkNotNull(multiset1);
/* 342 */     Preconditions.checkNotNull(multiset2);
/*     */     
/* 344 */     return new AbstractMultiset<E>() {
/*     */         public int count(Object element) {
/* 346 */           int count1 = multiset1.count(element);
/* 347 */           return (count1 == 0) ? 0 : Math.min(count1, multiset2.count(element));
/*     */         }
/*     */ 
/*     */         
/* 351 */         Set<E> createElementSet() { return Sets.intersection(multiset1.elementSet(), multiset2.elementSet()); }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 356 */         public Set<Multiset.Entry<E>> entrySet() { return this.entrySet; }
/*     */ 
/*     */         
/* 359 */         final Set<Multiset.Entry<E>> entrySet = new AbstractSet<Multiset.Entry<E>>() {
/*     */             public Iterator<Multiset.Entry<E>> iterator() {
/* 361 */               final Iterator<Multiset.Entry<E>> iterator1 = multiset1.entrySet().iterator();
/* 362 */               return new AbstractIterator<Multiset.Entry<E>>() {
/*     */                   protected Multiset.Entry<E> computeNext() {
/* 364 */                     while (iterator1.hasNext()) {
/* 365 */                       Multiset.Entry<E> entry1 = (Multiset.Entry)iterator1.next();
/* 366 */                       E element = (E)entry1.getElement();
/* 367 */                       int count = Math.min(entry1.getCount(), multiset2.count(element));
/*     */                       
/* 369 */                       if (count > 0) {
/* 370 */                         return Multisets.immutableEntry(element, count);
/*     */                       }
/*     */                     } 
/* 373 */                     return (Multiset.Entry)endOfData();
/*     */                   }
/*     */                 };
/*     */             }
/*     */ 
/*     */             
/* 379 */             public int size() { return Multisets.null.this.elementSet().size(); }
/*     */ 
/*     */             
/*     */             public boolean contains(Object o) {
/* 383 */               if (o instanceof Multiset.Entry) {
/* 384 */                 Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 385 */                 int entryCount = entry.getCount();
/* 386 */                 return (entryCount > 0 && Multisets.null.this.count(entry.getElement()) == entryCount);
/*     */               } 
/*     */               
/* 389 */               return false;
/*     */             }
/*     */ 
/*     */             
/* 393 */             public boolean isEmpty() { return Multisets.null.this.elementSet().isEmpty(); }
/*     */           };
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class AbstractEntry<E>
/*     */     extends Object
/*     */     implements Multiset.Entry<E>
/*     */   {
/*     */     public boolean equals(@Nullable Object object) {
/* 409 */       if (object instanceof Multiset.Entry) {
/* 410 */         Multiset.Entry<?> that = (Multiset.Entry)object;
/* 411 */         return (getCount() == that.getCount() && Objects.equal(getElement(), that.getElement()));
/*     */       } 
/*     */       
/* 414 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 422 */       E e = (E)getElement();
/* 423 */       return ((e == null) ? 0 : e.hashCode()) ^ getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 434 */       String text = String.valueOf(getElement());
/* 435 */       int n = getCount();
/* 436 */       return (n == 1) ? text : (text + " x " + n);
/*     */     }
/*     */   }
/*     */   
/*     */   static <E> int setCountImpl(Multiset<E> self, E element, int count) {
/* 441 */     checkNonnegative(count, "count");
/*     */     
/* 443 */     int oldCount = self.count(element);
/*     */     
/* 445 */     int delta = count - oldCount;
/* 446 */     if (delta > 0) {
/* 447 */       self.add(element, delta);
/* 448 */     } else if (delta < 0) {
/* 449 */       self.remove(element, -delta);
/*     */     } 
/*     */     
/* 452 */     return oldCount;
/*     */   }
/*     */ 
/*     */   
/*     */   static <E> boolean setCountImpl(Multiset<E> self, E element, int oldCount, int newCount) {
/* 457 */     checkNonnegative(oldCount, "oldCount");
/* 458 */     checkNonnegative(newCount, "newCount");
/*     */     
/* 460 */     if (self.count(element) == oldCount) {
/* 461 */       self.setCount(element, newCount);
/* 462 */       return true;
/*     */     } 
/* 464 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 469 */   static void checkNonnegative(int count, String name) { Preconditions.checkArgument((count >= 0), "%s cannot be negative: %s", new Object[] { name, Integer.valueOf(count) }); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Multisets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */