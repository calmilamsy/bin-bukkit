/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible
/*     */ abstract class AbstractMultiset<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Multiset<E>
/*     */ {
/*     */   private Set<E> elementSet;
/*     */   
/*     */   public abstract Set<Multiset.Entry<E>> entrySet();
/*     */   
/*     */   public int size() {
/*  56 */     long sum = 0L;
/*  57 */     for (Multiset.Entry<E> entry : entrySet()) {
/*  58 */       sum += entry.getCount();
/*     */     }
/*  60 */     return (int)Math.min(sum, 2147483647L);
/*     */   }
/*     */ 
/*     */   
/*  64 */   public boolean isEmpty() { return entrySet().isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public boolean contains(@Nullable Object element) { return elementSet().contains(element); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public Iterator<E> iterator() { return new MultisetIterator(); }
/*     */ 
/*     */   
/*     */   private class MultisetIterator
/*     */     extends Object
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final Iterator<Multiset.Entry<E>> entryIterator;
/*     */     private Multiset.Entry<E> currentEntry;
/*     */     private int laterCount;
/*     */     private int totalCount;
/*     */     private boolean canRemove;
/*     */     
/*  85 */     MultisetIterator() { this.entryIterator = this$0.entrySet().iterator(); }
/*     */ 
/*     */ 
/*     */     
/*  89 */     public boolean hasNext() { return (this.laterCount > 0 || this.entryIterator.hasNext()); }
/*     */ 
/*     */     
/*     */     public E next() {
/*  93 */       if (!hasNext()) {
/*  94 */         throw new NoSuchElementException();
/*     */       }
/*  96 */       if (this.laterCount == 0) {
/*  97 */         this.currentEntry = (Multiset.Entry)this.entryIterator.next();
/*  98 */         this.totalCount = this.laterCount = this.currentEntry.getCount();
/*     */       } 
/* 100 */       this.laterCount--;
/* 101 */       this.canRemove = true;
/* 102 */       return (E)this.currentEntry.getElement();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 106 */       Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
/*     */       
/* 108 */       if (this.totalCount == 1) {
/* 109 */         this.entryIterator.remove();
/*     */       } else {
/* 111 */         AbstractMultiset.this.remove(this.currentEntry.getElement());
/*     */       } 
/* 113 */       this.totalCount--;
/* 114 */       this.canRemove = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public int count(Object element) {
/* 119 */     for (Multiset.Entry<E> entry : entrySet()) {
/* 120 */       if (Objects.equal(entry.getElement(), element)) {
/* 121 */         return entry.getCount();
/*     */       }
/*     */     } 
/* 124 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(@Nullable E element) {
/* 130 */     add(element, 1);
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 135 */   public int add(E element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public boolean remove(Object element) { return (remove(element, 1) > 0); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public int remove(Object element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public int setCount(E element, int count) { return Multisets.setCountImpl(this, element, count); }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public boolean setCount(E element, int oldCount, int newCount) { return Multisets.setCountImpl(this, element, oldCount, newCount); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public boolean containsAll(Collection<?> elements) { return elementSet().containsAll(elements); }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> elementsToAdd) {
/* 161 */     if (elementsToAdd.isEmpty()) {
/* 162 */       return false;
/*     */     }
/* 164 */     if (elementsToAdd instanceof Multiset) {
/*     */       
/* 166 */       Multiset<? extends E> that = (Multiset)elementsToAdd;
/* 167 */       for (Multiset.Entry<? extends E> entry : that.entrySet()) {
/* 168 */         add(entry.getElement(), entry.getCount());
/*     */       }
/*     */     } else {
/* 171 */       super.addAll(elementsToAdd);
/*     */     } 
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> elementsToRemove) {
/* 177 */     Collection<?> collection = (elementsToRemove instanceof Multiset) ? ((Multiset)elementsToRemove).elementSet() : elementsToRemove;
/*     */ 
/*     */     
/* 180 */     return elementSet().removeAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> elementsToRetain) {
/* 185 */     Preconditions.checkNotNull(elementsToRetain);
/* 186 */     Iterator<Multiset.Entry<E>> entries = entrySet().iterator();
/* 187 */     boolean modified = false;
/* 188 */     while (entries.hasNext()) {
/* 189 */       Multiset.Entry<E> entry = (Multiset.Entry)entries.next();
/* 190 */       if (!elementsToRetain.contains(entry.getElement())) {
/* 191 */         entries.remove();
/* 192 */         modified = true;
/*     */       } 
/*     */     } 
/* 195 */     return modified;
/*     */   }
/*     */ 
/*     */   
/* 199 */   public void clear() { entrySet().clear(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<E> elementSet() {
/* 207 */     Set<E> result = this.elementSet;
/* 208 */     if (result == null) {
/* 209 */       this.elementSet = result = createElementSet();
/*     */     }
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   Set<E> createElementSet() { return new ElementSet(null); }
/*     */   
/*     */   private class ElementSet
/*     */     extends AbstractSet<E> {
/*     */     public Iterator<E> iterator() {
/* 224 */       final Iterator<Multiset.Entry<E>> entryIterator = AbstractMultiset.this.entrySet().iterator();
/* 225 */       return new Iterator<E>()
/*     */         {
/* 227 */           public boolean hasNext() { return entryIterator.hasNext(); }
/*     */ 
/*     */           
/* 230 */           public E next() { return (E)((Multiset.Entry)entryIterator.next()).getElement(); }
/*     */ 
/*     */           
/* 233 */           public void remove() { entryIterator.remove(); }
/*     */         };
/*     */     }
/*     */     private ElementSet() {}
/*     */     
/* 238 */     public int size() { return AbstractMultiset.this.entrySet().size(); }
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
/*     */   public boolean equals(@Nullable Object object) {
/* 252 */     if (object == this) {
/* 253 */       return true;
/*     */     }
/* 255 */     if (object instanceof Multiset) {
/* 256 */       Multiset<?> that = (Multiset)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 263 */       if (size() != that.size()) {
/* 264 */         return false;
/*     */       }
/* 266 */       for (Multiset.Entry<?> entry : that.entrySet()) {
/* 267 */         if (count(entry.getElement()) != entry.getCount()) {
/* 268 */           return false;
/*     */         }
/*     */       } 
/* 271 */       return true;
/*     */     } 
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public int hashCode() { return entrySet().hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public String toString() { return entrySet().toString(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */