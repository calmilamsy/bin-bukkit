/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
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
/*     */ @GwtCompatible
/*     */ public final class Collections2
/*     */ {
/*     */   static boolean containsAll(Collection<?> self, Collection<?> c) {
/*  60 */     Preconditions.checkNotNull(self);
/*  61 */     for (Object o : c) {
/*  62 */       if (!self.contains(o)) {
/*  63 */         return false;
/*     */       }
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   static <E> Collection<E> toCollection(Iterable<E> iterable) { return (iterable instanceof Collection) ? (Collection)iterable : Lists.newArrayList(iterable.iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {
/* 104 */     if (unfiltered instanceof FilteredCollection)
/*     */     {
/*     */       
/* 107 */       return ((FilteredCollection)unfiltered).createCombined(predicate);
/*     */     }
/*     */     
/* 110 */     return new FilteredCollection((Collection)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean safeContains(Collection<?> collection, Object object) {
/*     */     try {
/* 120 */       return collection.contains(object);
/* 121 */     } catch (ClassCastException e) {
/* 122 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static class FilteredCollection<E>
/*     */     extends Object implements Collection<E> {
/*     */     final Collection<E> unfiltered;
/*     */     final Predicate<? super E> predicate;
/*     */     
/*     */     FilteredCollection(Collection<E> unfiltered, Predicate<? super E> predicate) {
/* 132 */       this.unfiltered = unfiltered;
/* 133 */       this.predicate = predicate;
/*     */     }
/*     */ 
/*     */     
/* 137 */     FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) { return new FilteredCollection(this.unfiltered, Predicates.and(this.predicate, newPredicate)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean add(E element) {
/* 143 */       Preconditions.checkArgument(this.predicate.apply(element));
/* 144 */       return this.unfiltered.add(element);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends E> collection) {
/* 148 */       for (E element : collection) {
/* 149 */         Preconditions.checkArgument(this.predicate.apply(element));
/*     */       }
/* 151 */       return this.unfiltered.addAll(collection);
/*     */     }
/*     */ 
/*     */     
/* 155 */     public void clear() { Iterables.removeIf(this.unfiltered, this.predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object element) {
/*     */       try {
/* 163 */         E e = (E)element;
/* 164 */         return (this.predicate.apply(e) && this.unfiltered.contains(element));
/* 165 */       } catch (NullPointerException e) {
/* 166 */         return false;
/* 167 */       } catch (ClassCastException e) {
/* 168 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> collection) {
/* 173 */       for (Object element : collection) {
/* 174 */         if (!contains(element)) {
/* 175 */           return false;
/*     */         }
/*     */       } 
/* 178 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 182 */     public boolean isEmpty() { return !Iterators.any(this.unfiltered.iterator(), this.predicate); }
/*     */ 
/*     */ 
/*     */     
/* 186 */     public Iterator<E> iterator() { return Iterators.filter(this.unfiltered.iterator(), this.predicate); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object element) {
/*     */       try {
/* 194 */         E e = (E)element;
/* 195 */         return (this.predicate.apply(e) && this.unfiltered.remove(element));
/* 196 */       } catch (NullPointerException e) {
/* 197 */         return false;
/* 198 */       } catch (ClassCastException e) {
/* 199 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(final Collection<?> collection) {
/* 204 */       Preconditions.checkNotNull(collection);
/* 205 */       Predicate<E> combinedPredicate = new Predicate<E>() {
/*     */           public boolean apply(E input) {
/* 207 */             return (Collections2.FilteredCollection.this.predicate.apply(input) && collection.contains(input));
/*     */           }
/*     */         };
/* 210 */       return Iterables.removeIf(this.unfiltered, combinedPredicate);
/*     */     }
/*     */     
/*     */     public boolean retainAll(final Collection<?> collection) {
/* 214 */       Preconditions.checkNotNull(collection);
/* 215 */       Predicate<E> combinedPredicate = new Predicate<E>() {
/*     */           public boolean apply(E input) {
/* 217 */             return (Collections2.FilteredCollection.this.predicate.apply(input) && !collection.contains(input));
/*     */           }
/*     */         };
/* 220 */       return Iterables.removeIf(this.unfiltered, combinedPredicate);
/*     */     }
/*     */ 
/*     */     
/* 224 */     public int size() { return Iterators.size(iterator()); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     public Object[] toArray() { return Lists.newArrayList(iterator()).toArray(); }
/*     */ 
/*     */ 
/*     */     
/* 233 */     public <T> T[] toArray(T[] array) { return (T[])Lists.newArrayList(iterator()).toArray(array); }
/*     */ 
/*     */ 
/*     */     
/* 237 */     public String toString() { return Iterators.toString(iterator()); }
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
/* 258 */   public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) { return new TransformedCollection(fromCollection, function); }
/*     */   
/*     */   static class TransformedCollection<F, T>
/*     */     extends AbstractCollection<T>
/*     */   {
/*     */     final Collection<F> fromCollection;
/*     */     final Function<? super F, ? extends T> function;
/*     */     
/*     */     TransformedCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
/* 267 */       this.fromCollection = (Collection)Preconditions.checkNotNull(fromCollection);
/* 268 */       this.function = (Function)Preconditions.checkNotNull(function);
/*     */     }
/*     */ 
/*     */     
/* 272 */     public void clear() { this.fromCollection.clear(); }
/*     */ 
/*     */ 
/*     */     
/* 276 */     public boolean isEmpty() { return this.fromCollection.isEmpty(); }
/*     */ 
/*     */ 
/*     */     
/* 280 */     public Iterator<T> iterator() { return Iterators.transform(this.fromCollection.iterator(), this.function); }
/*     */ 
/*     */ 
/*     */     
/* 284 */     public int size() { return this.fromCollection.size(); }
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean setEquals(Set<?> thisSet, @Nullable Object object) {
/* 289 */     if (object == thisSet) {
/* 290 */       return true;
/*     */     }
/* 292 */     if (object instanceof Set) {
/* 293 */       Set<?> thatSet = (Set)object;
/* 294 */       return (thisSet.size() == thatSet.size() && thisSet.containsAll(thatSet));
/*     */     } 
/*     */     
/* 297 */     return false;
/*     */   }
/*     */   
/* 300 */   static final Joiner standardJoiner = Joiner.on(", ");
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Collections2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */