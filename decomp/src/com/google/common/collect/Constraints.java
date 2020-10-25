/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public final class Constraints
/*     */ {
/*     */   private enum NotNullConstraint
/*     */     implements Constraint<Object>
/*     */   {
/*  45 */     INSTANCE;
/*     */ 
/*     */     
/*  48 */     public Object checkElement(Object element) { return Preconditions.checkNotNull(element); }
/*     */ 
/*     */ 
/*     */     
/*  52 */     public String toString() { return "Not null"; }
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
/*  64 */   public static final <E> Constraint<E> notNull() { return NotNullConstraint.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static <E> Collection<E> constrainedCollection(Collection<E> collection, Constraint<? super E> constraint) { return new ConstrainedCollection(collection, constraint); }
/*     */ 
/*     */   
/*     */   static class ConstrainedCollection<E>
/*     */     extends ForwardingCollection<E>
/*     */   {
/*     */     private final Collection<E> delegate;
/*     */     private final Constraint<? super E> constraint;
/*     */     
/*     */     public ConstrainedCollection(Collection<E> delegate, Constraint<? super E> constraint) {
/*  91 */       this.delegate = (Collection)Preconditions.checkNotNull(delegate);
/*  92 */       this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/*  95 */     protected Collection<E> delegate() { return this.delegate; }
/*     */     
/*     */     public boolean add(E element) {
/*  98 */       this.constraint.checkElement(element);
/*  99 */       return this.delegate.add(element);
/*     */     }
/*     */     
/* 102 */     public boolean addAll(Collection<? extends E> elements) { return this.delegate.addAll(Constraints.checkElements(elements, this.constraint)); }
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
/* 120 */   public static <E> Set<E> constrainedSet(Set<E> set, Constraint<? super E> constraint) { return new ConstrainedSet(set, constraint); }
/*     */   
/*     */   static class ConstrainedSet<E>
/*     */     extends ForwardingSet<E>
/*     */   {
/*     */     private final Set<E> delegate;
/*     */     private final Constraint<? super E> constraint;
/*     */     
/*     */     public ConstrainedSet(Set<E> delegate, Constraint<? super E> constraint) {
/* 129 */       this.delegate = (Set)Preconditions.checkNotNull(delegate);
/* 130 */       this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/* 133 */     protected Set<E> delegate() { return this.delegate; }
/*     */     
/*     */     public boolean add(E element) {
/* 136 */       this.constraint.checkElement(element);
/* 137 */       return this.delegate.add(element);
/*     */     }
/*     */     
/* 140 */     public boolean addAll(Collection<? extends E> elements) { return this.delegate.addAll(Constraints.checkElements(elements, this.constraint)); }
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
/* 158 */   public static <E> SortedSet<E> constrainedSortedSet(SortedSet<E> sortedSet, Constraint<? super E> constraint) { return new ConstrainedSortedSet(sortedSet, constraint); }
/*     */ 
/*     */   
/*     */   private static class ConstrainedSortedSet<E>
/*     */     extends ForwardingSortedSet<E>
/*     */   {
/*     */     final SortedSet<E> delegate;
/*     */     final Constraint<? super E> constraint;
/*     */     
/*     */     ConstrainedSortedSet(SortedSet<E> delegate, Constraint<? super E> constraint) {
/* 168 */       this.delegate = (SortedSet)Preconditions.checkNotNull(delegate);
/* 169 */       this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/* 172 */     protected SortedSet<E> delegate() { return this.delegate; }
/*     */ 
/*     */     
/* 175 */     public SortedSet<E> headSet(E toElement) { return Constraints.constrainedSortedSet(this.delegate.headSet(toElement), this.constraint); }
/*     */ 
/*     */     
/* 178 */     public SortedSet<E> subSet(E fromElement, E toElement) { return Constraints.constrainedSortedSet(this.delegate.subSet(fromElement, toElement), this.constraint); }
/*     */ 
/*     */ 
/*     */     
/* 182 */     public SortedSet<E> tailSet(E fromElement) { return Constraints.constrainedSortedSet(this.delegate.tailSet(fromElement), this.constraint); }
/*     */     
/*     */     public boolean add(E element) {
/* 185 */       this.constraint.checkElement(element);
/* 186 */       return this.delegate.add(element);
/*     */     }
/*     */     
/* 189 */     public boolean addAll(Collection<? extends E> elements) { return this.delegate.addAll(Constraints.checkElements(elements, this.constraint)); }
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
/* 208 */   public static <E> List<E> constrainedList(List<E> list, Constraint<? super E> constraint) { return (list instanceof RandomAccess) ? new ConstrainedRandomAccessList(list, constraint) : new ConstrainedList(list, constraint); }
/*     */ 
/*     */   
/*     */   @GwtCompatible
/*     */   private static class ConstrainedList<E>
/*     */     extends ForwardingList<E>
/*     */   {
/*     */     final List<E> delegate;
/*     */     
/*     */     final Constraint<? super E> constraint;
/*     */     
/*     */     ConstrainedList(List<E> delegate, Constraint<? super E> constraint) {
/* 220 */       this.delegate = (List)Preconditions.checkNotNull(delegate);
/* 221 */       this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/* 224 */     protected List<E> delegate() { return this.delegate; }
/*     */ 
/*     */     
/*     */     public boolean add(E element) {
/* 228 */       this.constraint.checkElement(element);
/* 229 */       return this.delegate.add(element);
/*     */     }
/*     */     public void add(int index, E element) {
/* 232 */       this.constraint.checkElement(element);
/* 233 */       this.delegate.add(index, element);
/*     */     }
/*     */     
/* 236 */     public boolean addAll(Collection<? extends E> elements) { return this.delegate.addAll(Constraints.checkElements(elements, this.constraint)); }
/*     */ 
/*     */ 
/*     */     
/* 240 */     public boolean addAll(int index, Collection<? extends E> elements) { return this.delegate.addAll(index, Constraints.checkElements(elements, this.constraint)); }
/*     */ 
/*     */     
/* 243 */     public ListIterator<E> listIterator() { return Constraints.constrainedListIterator(this.delegate.listIterator(), this.constraint); }
/*     */ 
/*     */     
/* 246 */     public ListIterator<E> listIterator(int index) { return Constraints.constrainedListIterator(this.delegate.listIterator(index), this.constraint); }
/*     */     
/*     */     public E set(int index, E element) {
/* 249 */       this.constraint.checkElement(element);
/* 250 */       return (E)this.delegate.set(index, element);
/*     */     }
/*     */     
/* 253 */     public List<E> subList(int fromIndex, int toIndex) { return Constraints.constrainedList(this.delegate.subList(fromIndex, toIndex), this.constraint); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ConstrainedRandomAccessList<E>
/*     */     extends ConstrainedList<E>
/*     */     implements RandomAccess
/*     */   {
/* 263 */     ConstrainedRandomAccessList(List<E> delegate, Constraint<? super E> constraint) { super(delegate, constraint); }
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
/* 279 */   private static <E> ListIterator<E> constrainedListIterator(ListIterator<E> listIterator, Constraint<? super E> constraint) { return new ConstrainedListIterator(listIterator, constraint); }
/*     */ 
/*     */   
/*     */   static class ConstrainedListIterator<E>
/*     */     extends ForwardingListIterator<E>
/*     */   {
/*     */     private final ListIterator<E> delegate;
/*     */     private final Constraint<? super E> constraint;
/*     */     
/*     */     public ConstrainedListIterator(ListIterator<E> delegate, Constraint<? super E> constraint) {
/* 289 */       this.delegate = delegate;
/* 290 */       this.constraint = constraint;
/*     */     }
/*     */     
/* 293 */     protected ListIterator<E> delegate() { return this.delegate; }
/*     */ 
/*     */     
/*     */     public void add(E element) {
/* 297 */       this.constraint.checkElement(element);
/* 298 */       this.delegate.add(element);
/*     */     }
/*     */     public void set(E element) {
/* 301 */       this.constraint.checkElement(element);
/* 302 */       this.delegate.set(element);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static <E> Collection<E> constrainedTypePreservingCollection(Collection<E> collection, Constraint<E> constraint) {
/* 308 */     if (collection instanceof SortedSet)
/* 309 */       return constrainedSortedSet((SortedSet)collection, constraint); 
/* 310 */     if (collection instanceof Set)
/* 311 */       return constrainedSet((Set)collection, constraint); 
/* 312 */     if (collection instanceof List) {
/* 313 */       return constrainedList((List)collection, constraint);
/*     */     }
/* 315 */     return constrainedCollection(collection, constraint);
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
/* 333 */   public static <E> Multiset<E> constrainedMultiset(Multiset<E> multiset, Constraint<? super E> constraint) { return new ConstrainedMultiset(multiset, constraint); }
/*     */ 
/*     */   
/*     */   static class ConstrainedMultiset<E>
/*     */     extends ForwardingMultiset<E>
/*     */   {
/*     */     private Multiset<E> delegate;
/*     */     private final Constraint<? super E> constraint;
/*     */     
/*     */     public ConstrainedMultiset(Multiset<E> delegate, Constraint<? super E> constraint) {
/* 343 */       this.delegate = (Multiset)Preconditions.checkNotNull(delegate);
/* 344 */       this.constraint = (Constraint)Preconditions.checkNotNull(constraint);
/*     */     }
/*     */     
/* 347 */     protected Multiset<E> delegate() { return this.delegate; }
/*     */ 
/*     */     
/*     */     public boolean add(E element) {
/* 351 */       this.constraint.checkElement(element);
/* 352 */       return this.delegate.add(element);
/*     */     }
/*     */     
/* 355 */     public boolean addAll(Collection<? extends E> elements) { return this.delegate.addAll(Constraints.checkElements(elements, this.constraint)); }
/*     */     
/*     */     public int add(E element, int occurrences) {
/* 358 */       this.constraint.checkElement(element);
/* 359 */       return this.delegate.add(element, occurrences);
/*     */     }
/*     */     public int setCount(E element, int count) {
/* 362 */       this.constraint.checkElement(element);
/* 363 */       return this.delegate.setCount(element, count);
/*     */     }
/*     */     public boolean setCount(E element, int oldCount, int newCount) {
/* 366 */       this.constraint.checkElement(element);
/* 367 */       return this.delegate.setCount(element, oldCount, newCount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E> Collection<E> checkElements(Collection<E> elements, Constraint<? super E> constraint) {
/* 376 */     Collection<E> copy = Lists.newArrayList(elements);
/* 377 */     for (E element : copy) {
/* 378 */       constraint.checkElement(element);
/*     */     }
/* 380 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Constraints.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */