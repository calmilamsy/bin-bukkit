/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ @GwtCompatible
/*     */ public final class TreeMultiset<E>
/*     */   extends AbstractMapBasedMultiset<E>
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  71 */   public static <E extends Comparable> TreeMultiset<E> create() { return new TreeMultiset(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static <E> TreeMultiset<E> create(Comparator<? super E> comparator) { return new TreeMultiset(comparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> elements) {
/* 102 */     TreeMultiset<E> multiset = create();
/* 103 */     Iterables.addAll(multiset, elements);
/* 104 */     return multiset;
/*     */   }
/*     */ 
/*     */   
/* 108 */   private TreeMultiset() { super(new TreeMap()); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   private TreeMultiset(Comparator<? super E> comparator) { super(new TreeMap(comparator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public SortedSet<E> elementSet() { return (SortedSet)super.elementSet(); }
/*     */ 
/*     */   
/*     */   public int count(@Nullable Object element) {
/*     */     try {
/* 127 */       return super.count(element);
/* 128 */     } catch (NullPointerException e) {
/* 129 */       return 0;
/* 130 */     } catch (ClassCastException e) {
/* 131 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 136 */   Set<E> createElementSet() { return new SortedMapBasedElementSet((SortedMap)backingMap()); }
/*     */ 
/*     */ 
/*     */   
/*     */   private class SortedMapBasedElementSet
/*     */     extends AbstractMapBasedMultiset<E>.MapBasedElementSet
/*     */     implements SortedSet<E>
/*     */   {
/* 144 */     SortedMapBasedElementSet(SortedMap<E, AtomicInteger> map) { super(TreeMultiset.this, map); }
/*     */ 
/*     */ 
/*     */     
/* 148 */     SortedMap<E, AtomicInteger> sortedMap() { return (SortedMap)getMap(); }
/*     */ 
/*     */ 
/*     */     
/* 152 */     public Comparator<? super E> comparator() { return sortedMap().comparator(); }
/*     */ 
/*     */ 
/*     */     
/* 156 */     public E first() { return (E)sortedMap().firstKey(); }
/*     */ 
/*     */ 
/*     */     
/* 160 */     public E last() { return (E)sortedMap().lastKey(); }
/*     */ 
/*     */ 
/*     */     
/* 164 */     public SortedSet<E> headSet(E toElement) { return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().headMap(toElement)); }
/*     */ 
/*     */ 
/*     */     
/* 168 */     public SortedSet<E> subSet(E fromElement, E toElement) { return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().subMap(fromElement, toElement)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     public SortedSet<E> tailSet(E fromElement) { return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().tailMap(fromElement)); }
/*     */ 
/*     */     
/*     */     public boolean remove(Object element) {
/*     */       try {
/* 178 */         return super.remove(element);
/* 179 */       } catch (NullPointerException e) {
/* 180 */         return false;
/* 181 */       } catch (ClassCastException e) {
/* 182 */         return false;
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
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 199 */     stream.defaultWriteObject();
/* 200 */     stream.writeObject(elementSet().comparator());
/* 201 */     Serialization.writeMultiset(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 206 */     stream.defaultReadObject();
/*     */     
/* 208 */     Comparator<? super E> comparator = (Comparator)stream.readObject();
/*     */     
/* 210 */     setBackingMap(new TreeMap(comparator));
/* 211 */     Serialization.populateMultiset(this, stream);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\TreeMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */