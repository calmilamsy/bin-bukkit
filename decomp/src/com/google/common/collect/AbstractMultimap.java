/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.base.Preconditions;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible
/*      */ abstract class AbstractMultimap<K, V>
/*      */   extends Object
/*      */   implements Multimap<K, V>, Serializable
/*      */ {
/*      */   private Map<K, Collection<V>> map;
/*      */   private int totalSize;
/*      */   private Set<K> keySet;
/*      */   private Multiset<K> multiset;
/*      */   private Collection<V> valuesCollection;
/*      */   private Collection<Map.Entry<K, V>> entries;
/*      */   private Map<K, Collection<V>> asMap;
/*      */   private static final long serialVersionUID = 2447537837011683357L;
/*      */   
/*      */   protected AbstractMultimap(Map<K, Collection<V>> map) {
/*  118 */     Preconditions.checkArgument(map.isEmpty());
/*  119 */     this.map = map;
/*      */   }
/*      */ 
/*      */   
/*      */   final void setMap(Map<K, Collection<V>> map) {
/*  124 */     this.map = map;
/*  125 */     this.totalSize = 0;
/*  126 */     for (Collection<V> values : map.values()) {
/*  127 */       Preconditions.checkArgument(!values.isEmpty());
/*  128 */       this.totalSize += values.size();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   Collection<V> createCollection(@Nullable K key) { return createCollection(); }
/*      */ 
/*      */ 
/*      */   
/*  159 */   Map<K, Collection<V>> backingMap() { return this.map; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public int size() { return this.totalSize; }
/*      */ 
/*      */ 
/*      */   
/*  169 */   public boolean isEmpty() { return (this.totalSize == 0); }
/*      */ 
/*      */ 
/*      */   
/*  173 */   public boolean containsKey(@Nullable Object key) { return this.map.containsKey(key); }
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/*  177 */     for (Collection<V> collection : this.map.values()) {
/*  178 */       if (collection.contains(value)) {
/*  179 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  183 */     return false;
/*      */   }
/*      */   
/*      */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/*  187 */     Collection<V> collection = (Collection)this.map.get(key);
/*  188 */     return (collection != null && collection.contains(value));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean put(@Nullable K key, @Nullable V value) {
/*  194 */     Collection<V> collection = getOrCreateCollection(key);
/*      */     
/*  196 */     if (collection.add(value)) {
/*  197 */       this.totalSize++;
/*  198 */       return true;
/*      */     } 
/*  200 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<V> getOrCreateCollection(@Nullable K key) {
/*  205 */     Collection<V> collection = (Collection)this.map.get(key);
/*  206 */     if (collection == null) {
/*  207 */       collection = createCollection(key);
/*  208 */       this.map.put(key, collection);
/*      */     } 
/*  210 */     return collection;
/*      */   }
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/*  214 */     Collection<V> collection = (Collection)this.map.get(key);
/*  215 */     if (collection == null) {
/*  216 */       return false;
/*      */     }
/*      */     
/*  219 */     boolean changed = collection.remove(value);
/*  220 */     if (changed) {
/*  221 */       this.totalSize--;
/*  222 */       if (collection.isEmpty()) {
/*  223 */         this.map.remove(key);
/*      */       }
/*      */     } 
/*  226 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
/*  232 */     if (!values.iterator().hasNext()) {
/*  233 */       return false;
/*      */     }
/*  235 */     Collection<V> collection = getOrCreateCollection(key);
/*  236 */     int oldSize = collection.size();
/*      */     
/*  238 */     boolean changed = false;
/*  239 */     if (values instanceof Collection) {
/*      */       
/*  241 */       Collection<? extends V> c = (Collection)values;
/*  242 */       changed = collection.addAll(c);
/*      */     } else {
/*  244 */       for (V value : values) {
/*  245 */         changed |= collection.add(value);
/*      */       }
/*      */     } 
/*      */     
/*  249 */     this.totalSize += collection.size() - oldSize;
/*  250 */     return changed;
/*      */   }
/*      */   
/*      */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/*  254 */     boolean changed = false;
/*  255 */     for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
/*  256 */       changed |= put(entry.getKey(), entry.getValue());
/*      */     }
/*  258 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/*  268 */     Iterator<? extends V> iterator = values.iterator();
/*  269 */     if (!iterator.hasNext()) {
/*  270 */       return removeAll(key);
/*      */     }
/*      */     
/*  273 */     Collection<V> collection = getOrCreateCollection(key);
/*  274 */     Collection<V> oldValues = createCollection();
/*  275 */     oldValues.addAll(collection);
/*      */     
/*  277 */     this.totalSize -= collection.size();
/*  278 */     collection.clear();
/*      */     
/*  280 */     while (iterator.hasNext()) {
/*  281 */       if (collection.add(iterator.next())) {
/*  282 */         this.totalSize++;
/*      */       }
/*      */     } 
/*      */     
/*  286 */     return unmodifiableCollectionSubclass(oldValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> removeAll(@Nullable Object key) {
/*  295 */     Collection<V> collection = (Collection)this.map.remove(key);
/*  296 */     Collection<V> output = createCollection();
/*      */     
/*  298 */     if (collection != null) {
/*  299 */       output.addAll(collection);
/*  300 */       this.totalSize -= collection.size();
/*  301 */       collection.clear();
/*      */     } 
/*      */     
/*  304 */     return unmodifiableCollectionSubclass(output);
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<V> unmodifiableCollectionSubclass(Collection<V> collection) {
/*  309 */     if (collection instanceof SortedSet)
/*  310 */       return Collections.unmodifiableSortedSet((SortedSet)collection); 
/*  311 */     if (collection instanceof Set)
/*  312 */       return Collections.unmodifiableSet((Set)collection); 
/*  313 */     if (collection instanceof List) {
/*  314 */       return Collections.unmodifiableList((List)collection);
/*      */     }
/*  316 */     return Collections.unmodifiableCollection(collection);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  322 */     for (Collection<V> collection : this.map.values()) {
/*  323 */       collection.clear();
/*      */     }
/*  325 */     this.map.clear();
/*  326 */     this.totalSize = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> get(@Nullable K key) {
/*  337 */     Collection<V> collection = (Collection)this.map.get(key);
/*  338 */     if (collection == null) {
/*  339 */       collection = createCollection(key);
/*      */     }
/*  341 */     return wrapCollection(key, collection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<V> wrapCollection(@Nullable K key, Collection<V> collection) {
/*  351 */     if (collection instanceof SortedSet)
/*  352 */       return new WrappedSortedSet(key, (SortedSet)collection, null); 
/*  353 */     if (collection instanceof Set)
/*  354 */       return new WrappedSet(key, (Set)collection); 
/*  355 */     if (collection instanceof List) {
/*  356 */       return wrapList(key, (List)collection, null);
/*      */     }
/*  358 */     return new WrappedCollection(key, collection, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  364 */   private List<V> wrapList(K key, List<V> list, @Nullable WrappedCollection ancestor) { return (list instanceof RandomAccess) ? new RandomAccessWrappedList(key, list, ancestor) : new WrappedList(key, list, ancestor); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class WrappedCollection
/*      */     extends AbstractCollection<V>
/*      */   {
/*      */     final K key;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Collection<V> delegate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final WrappedCollection ancestor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Collection<V> ancestorDelegate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     WrappedCollection(K key, @Nullable Collection<V> delegate, WrappedCollection ancestor) {
/*  394 */       this.key = key;
/*  395 */       this.delegate = delegate;
/*  396 */       this.ancestor = ancestor;
/*  397 */       this.ancestorDelegate = (ancestor == null) ? null : ancestor.getDelegate();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void refreshIfEmpty() {
/*  409 */       if (this.ancestor != null) {
/*  410 */         this.ancestor.refreshIfEmpty();
/*  411 */         if (this.ancestor.getDelegate() != this.ancestorDelegate) {
/*  412 */           throw new ConcurrentModificationException();
/*      */         }
/*  414 */       } else if (this.delegate.isEmpty()) {
/*  415 */         Collection<V> newDelegate = (Collection)AbstractMultimap.this.map.get(this.key);
/*  416 */         if (newDelegate != null) {
/*  417 */           this.delegate = newDelegate;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void removeIfEmpty() {
/*  427 */       if (this.ancestor != null) {
/*  428 */         this.ancestor.removeIfEmpty();
/*  429 */       } else if (this.delegate.isEmpty()) {
/*  430 */         AbstractMultimap.this.map.remove(this.key);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  435 */     K getKey() { return (K)this.key; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addToMap() {
/*  446 */       if (this.ancestor != null) {
/*  447 */         this.ancestor.addToMap();
/*      */       } else {
/*  449 */         AbstractMultimap.this.map.put(this.key, this.delegate);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  454 */       refreshIfEmpty();
/*  455 */       return this.delegate.size();
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  459 */       if (object == this) {
/*  460 */         return true;
/*      */       }
/*  462 */       refreshIfEmpty();
/*  463 */       return this.delegate.equals(object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  467 */       refreshIfEmpty();
/*  468 */       return this.delegate.hashCode();
/*      */     }
/*      */     
/*      */     public String toString() {
/*  472 */       refreshIfEmpty();
/*  473 */       return this.delegate.toString();
/*      */     }
/*      */ 
/*      */     
/*  477 */     Collection<V> getDelegate() { return this.delegate; }
/*      */ 
/*      */     
/*      */     public Iterator<V> iterator() {
/*  481 */       refreshIfEmpty();
/*  482 */       return new WrappedIterator();
/*      */     }
/*      */     
/*      */     class WrappedIterator extends Object implements Iterator<V> { final Iterator<V> delegateIterator;
/*      */       
/*      */       WrappedIterator() {
/*  488 */         super.originalDelegate = AbstractMultimap.WrappedCollection.this.delegate;
/*      */ 
/*      */         
/*  491 */         super.delegateIterator = AbstractMultimap.WrappedCollection.this.this$0.iteratorOrListIterator(AbstractMultimap.WrappedCollection.this.delegate);
/*      */       } final Collection<V> originalDelegate;
/*      */       WrappedIterator(Iterator<V> delegateIterator) {
/*      */         super.originalDelegate = AbstractMultimap.WrappedCollection.this.delegate;
/*  495 */         super.delegateIterator = delegateIterator;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void validateIterator() {
/*  503 */         super.this$1.refreshIfEmpty();
/*  504 */         if (AbstractMultimap.WrappedCollection.this.delegate != super.originalDelegate) {
/*  505 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */       
/*      */       public boolean hasNext() {
/*  510 */         super.validateIterator();
/*  511 */         return super.delegateIterator.hasNext();
/*      */       }
/*      */       
/*      */       public V next() {
/*  515 */         super.validateIterator();
/*  516 */         return (V)super.delegateIterator.next();
/*      */       }
/*      */       
/*      */       public void remove() {
/*  520 */         super.delegateIterator.remove();
/*  521 */         AbstractMultimap.WrappedCollection.this.this$0.totalSize--;
/*  522 */         super.this$1.removeIfEmpty();
/*      */       }
/*      */       
/*      */       Iterator<V> getDelegateIterator() {
/*  526 */         super.validateIterator();
/*  527 */         return super.delegateIterator;
/*      */       } }
/*      */ 
/*      */     
/*      */     public boolean add(V value) {
/*  532 */       refreshIfEmpty();
/*  533 */       boolean wasEmpty = this.delegate.isEmpty();
/*  534 */       boolean changed = this.delegate.add(value);
/*  535 */       if (changed) {
/*  536 */         AbstractMultimap.this.totalSize++;
/*  537 */         if (wasEmpty) {
/*  538 */           addToMap();
/*      */         }
/*      */       } 
/*  541 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*  545 */     WrappedCollection getAncestor() { return this.ancestor; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends V> collection) {
/*  551 */       if (collection.isEmpty()) {
/*  552 */         return false;
/*      */       }
/*  554 */       int oldSize = size();
/*  555 */       boolean changed = this.delegate.addAll(collection);
/*  556 */       if (changed) {
/*  557 */         int newSize = this.delegate.size();
/*  558 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  559 */         if (oldSize == 0) {
/*  560 */           addToMap();
/*      */         }
/*      */       } 
/*  563 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/*  567 */       refreshIfEmpty();
/*  568 */       return this.delegate.contains(o);
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  572 */       refreshIfEmpty();
/*  573 */       return this.delegate.containsAll(c);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  577 */       int oldSize = size();
/*  578 */       if (oldSize == 0) {
/*      */         return;
/*      */       }
/*  581 */       this.delegate.clear();
/*  582 */       AbstractMultimap.this.totalSize -= oldSize;
/*  583 */       removeIfEmpty();
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  587 */       refreshIfEmpty();
/*  588 */       boolean changed = this.delegate.remove(o);
/*  589 */       if (changed) {
/*  590 */         AbstractMultimap.this.totalSize--;
/*  591 */         removeIfEmpty();
/*      */       } 
/*  593 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  597 */       if (c.isEmpty()) {
/*  598 */         return false;
/*      */       }
/*  600 */       int oldSize = size();
/*  601 */       boolean changed = this.delegate.removeAll(c);
/*  602 */       if (changed) {
/*  603 */         int newSize = this.delegate.size();
/*  604 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  605 */         removeIfEmpty();
/*      */       } 
/*  607 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  611 */       Preconditions.checkNotNull(c);
/*  612 */       int oldSize = size();
/*  613 */       boolean changed = this.delegate.retainAll(c);
/*  614 */       if (changed) {
/*  615 */         int newSize = this.delegate.size();
/*  616 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  617 */         removeIfEmpty();
/*      */       } 
/*  619 */       return changed;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  624 */   private Iterator<V> iteratorOrListIterator(Collection<V> collection) { return (collection instanceof List) ? ((List)collection).listIterator() : collection.iterator(); }
/*      */ 
/*      */ 
/*      */   
/*      */   private class WrappedSet
/*      */     extends WrappedCollection
/*      */     implements Set<V>
/*      */   {
/*  632 */     WrappedSet(K key, Set<V> delegate) { super(AbstractMultimap.this, key, delegate, null); }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class WrappedSortedSet
/*      */     extends WrappedCollection
/*      */     implements SortedSet<V>
/*      */   {
/*  643 */     WrappedSortedSet(K key, @Nullable SortedSet<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) { super(AbstractMultimap.this, key, delegate, ancestor); }
/*      */ 
/*      */ 
/*      */     
/*  647 */     SortedSet<V> getSortedSetDelegate() { return (SortedSet)getDelegate(); }
/*      */ 
/*      */ 
/*      */     
/*  651 */     public Comparator<? super V> comparator() { return getSortedSetDelegate().comparator(); }
/*      */ 
/*      */     
/*      */     public V first() {
/*  655 */       refreshIfEmpty();
/*  656 */       return (V)getSortedSetDelegate().first();
/*      */     }
/*      */     
/*      */     public V last() {
/*  660 */       refreshIfEmpty();
/*  661 */       return (V)getSortedSetDelegate().last();
/*      */     }
/*      */     
/*      */     public SortedSet<V> headSet(V toElement) {
/*  665 */       refreshIfEmpty();
/*  666 */       return new WrappedSortedSet(AbstractMultimap.this, getKey(), getSortedSetDelegate().headSet(toElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<V> subSet(V fromElement, V toElement) {
/*  672 */       refreshIfEmpty();
/*  673 */       return new WrappedSortedSet(AbstractMultimap.this, getKey(), getSortedSetDelegate().subSet(fromElement, toElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<V> tailSet(V fromElement) {
/*  679 */       refreshIfEmpty();
/*  680 */       return new WrappedSortedSet(AbstractMultimap.this, getKey(), getSortedSetDelegate().tailSet(fromElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class WrappedList
/*      */     extends WrappedCollection
/*      */     implements List<V>
/*      */   {
/*  689 */     WrappedList(K key, @Nullable List<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) { super(AbstractMultimap.this, key, delegate, ancestor); }
/*      */ 
/*      */ 
/*      */     
/*  693 */     List<V> getListDelegate() { return (List)getDelegate(); }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends V> c) {
/*  697 */       if (c.isEmpty()) {
/*  698 */         return false;
/*      */       }
/*  700 */       int oldSize = size();
/*  701 */       boolean changed = getListDelegate().addAll(index, c);
/*  702 */       if (changed) {
/*  703 */         int newSize = getDelegate().size();
/*  704 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  705 */         if (oldSize == 0) {
/*  706 */           addToMap();
/*      */         }
/*      */       } 
/*  709 */       return changed;
/*      */     }
/*      */     
/*      */     public V get(int index) {
/*  713 */       refreshIfEmpty();
/*  714 */       return (V)getListDelegate().get(index);
/*      */     }
/*      */     
/*      */     public V set(int index, V element) {
/*  718 */       refreshIfEmpty();
/*  719 */       return (V)getListDelegate().set(index, element);
/*      */     }
/*      */     
/*      */     public void add(int index, V element) {
/*  723 */       refreshIfEmpty();
/*  724 */       boolean wasEmpty = getDelegate().isEmpty();
/*  725 */       getListDelegate().add(index, element);
/*  726 */       AbstractMultimap.this.totalSize++;
/*  727 */       if (wasEmpty) {
/*  728 */         addToMap();
/*      */       }
/*      */     }
/*      */     
/*      */     public V remove(int index) {
/*  733 */       refreshIfEmpty();
/*  734 */       V value = (V)getListDelegate().remove(index);
/*  735 */       AbstractMultimap.this.totalSize--;
/*  736 */       removeIfEmpty();
/*  737 */       return value;
/*      */     }
/*      */     
/*      */     public int indexOf(Object o) {
/*  741 */       refreshIfEmpty();
/*  742 */       return getListDelegate().indexOf(o);
/*      */     }
/*      */     
/*      */     public int lastIndexOf(Object o) {
/*  746 */       refreshIfEmpty();
/*  747 */       return getListDelegate().lastIndexOf(o);
/*      */     }
/*      */     
/*      */     public ListIterator<V> listIterator() {
/*  751 */       refreshIfEmpty();
/*  752 */       return new WrappedListIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<V> listIterator(int index) {
/*  756 */       refreshIfEmpty();
/*  757 */       return new WrappedListIterator(index);
/*      */     }
/*      */     
/*      */     public List<V> subList(int fromIndex, int toIndex) {
/*  761 */       refreshIfEmpty();
/*  762 */       return AbstractMultimap.this.wrapList(getKey(), getListDelegate().subList(fromIndex, toIndex), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */     
/*      */     private class WrappedListIterator
/*      */       extends AbstractMultimap<K, V>.WrappedCollection.WrappedIterator
/*      */       implements ListIterator<V>
/*      */     {
/*      */       WrappedListIterator() {
/*  770 */         super(AbstractMultimap.WrappedList.this);
/*      */       }
/*      */       
/*  773 */       public WrappedListIterator(int index) { super(AbstractMultimap.WrappedList.this, this$0.getListDelegate().listIterator(index)); }
/*      */ 
/*      */ 
/*      */       
/*  777 */       private ListIterator<V> getDelegateListIterator() { return (ListIterator)getDelegateIterator(); }
/*      */ 
/*      */ 
/*      */       
/*  781 */       public boolean hasPrevious() { return super.getDelegateListIterator().hasPrevious(); }
/*      */ 
/*      */ 
/*      */       
/*  785 */       public V previous() { return (V)super.getDelegateListIterator().previous(); }
/*      */ 
/*      */ 
/*      */       
/*  789 */       public int nextIndex() { return super.getDelegateListIterator().nextIndex(); }
/*      */ 
/*      */ 
/*      */       
/*  793 */       public int previousIndex() { return super.getDelegateListIterator().previousIndex(); }
/*      */ 
/*      */ 
/*      */       
/*  797 */       public void set(V value) { super.getDelegateListIterator().set(value); }
/*      */ 
/*      */       
/*      */       public void add(V value) {
/*  801 */         boolean wasEmpty = super.this$1.isEmpty();
/*  802 */         super.getDelegateListIterator().add(value);
/*  803 */         AbstractMultimap.WrappedList.this.this$0.totalSize++;
/*  804 */         if (wasEmpty) {
/*  805 */           super.this$1.addToMap();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class RandomAccessWrappedList
/*      */     extends WrappedList
/*      */     implements RandomAccess
/*      */   {
/*  819 */     RandomAccessWrappedList(K key, @Nullable List<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) { super(AbstractMultimap.this, key, delegate, ancestor); }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/*  826 */     Set<K> result = this.keySet;
/*  827 */     return (result == null) ? (this.keySet = createKeySet()) : result;
/*      */   }
/*      */ 
/*      */   
/*  831 */   private Set<K> createKeySet() { return (this.map instanceof SortedMap) ? new SortedKeySet((SortedMap)this.map) : new KeySet(this.map); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class KeySet
/*      */     extends AbstractSet<K>
/*      */   {
/*      */     final Map<K, Collection<V>> subMap;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  844 */     KeySet(Map<K, Collection<V>> subMap) { this.subMap = subMap; }
/*      */ 
/*      */ 
/*      */     
/*  848 */     public int size() { return this.subMap.size(); }
/*      */ 
/*      */     
/*      */     public Iterator<K> iterator() {
/*  852 */       return new Iterator<K>()
/*      */         {
/*      */           final Iterator<Map.Entry<K, Collection<V>>> entryIterator;
/*      */           
/*      */           Map.Entry<K, Collection<V>> entry;
/*      */           
/*  858 */           public boolean hasNext() { return super.entryIterator.hasNext(); }
/*      */           
/*      */           public K next() {
/*  861 */             super.entry = (Map.Entry)super.entryIterator.next();
/*  862 */             return (K)super.entry.getKey();
/*      */           }
/*      */           public void remove() {
/*  865 */             Preconditions.checkState((super.entry != null));
/*  866 */             Collection<V> collection = (Collection)super.entry.getValue();
/*  867 */             super.entryIterator.remove();
/*  868 */             AbstractMultimap.KeySet.this.this$0.totalSize -= collection.size();
/*  869 */             collection.clear();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     public boolean contains(Object key) { return this.subMap.containsKey(key); }
/*      */ 
/*      */     
/*      */     public boolean remove(Object key) {
/*  881 */       int count = 0;
/*  882 */       Collection<V> collection = (Collection)this.subMap.remove(key);
/*  883 */       if (collection != null) {
/*  884 */         count = collection.size();
/*  885 */         collection.clear();
/*  886 */         AbstractMultimap.this.totalSize -= count;
/*      */       } 
/*  888 */       return (count > 0);
/*      */     }
/*      */ 
/*      */     
/*  892 */     public boolean containsAll(Collection<?> c) { return this.subMap.keySet().containsAll(c); }
/*      */ 
/*      */ 
/*      */     
/*  896 */     public boolean equals(@Nullable Object object) { return (this == object || this.subMap.keySet().equals(object)); }
/*      */ 
/*      */ 
/*      */     
/*  900 */     public int hashCode() { return this.subMap.keySet().hashCode(); }
/*      */   }
/*      */   
/*      */   private class SortedKeySet
/*      */     extends KeySet
/*      */     implements SortedSet<K>
/*      */   {
/*  907 */     SortedKeySet(SortedMap<K, Collection<V>> subMap) { super(AbstractMultimap.this, subMap); }
/*      */ 
/*      */ 
/*      */     
/*  911 */     SortedMap<K, Collection<V>> sortedMap() { return (SortedMap)this.subMap; }
/*      */ 
/*      */ 
/*      */     
/*  915 */     public Comparator<? super K> comparator() { return sortedMap().comparator(); }
/*      */ 
/*      */ 
/*      */     
/*  919 */     public K first() { return (K)sortedMap().firstKey(); }
/*      */ 
/*      */ 
/*      */     
/*  923 */     public SortedSet<K> headSet(K toElement) { return new SortedKeySet(AbstractMultimap.this, sortedMap().headMap(toElement)); }
/*      */ 
/*      */ 
/*      */     
/*  927 */     public K last() { return (K)sortedMap().lastKey(); }
/*      */ 
/*      */ 
/*      */     
/*  931 */     public SortedSet<K> subSet(K fromElement, K toElement) { return new SortedKeySet(AbstractMultimap.this, sortedMap().subMap(fromElement, toElement)); }
/*      */ 
/*      */ 
/*      */     
/*  935 */     public SortedSet<K> tailSet(K fromElement) { return new SortedKeySet(AbstractMultimap.this, sortedMap().tailMap(fromElement)); }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Multiset<K> keys() {
/*  942 */     Multiset<K> result = this.multiset;
/*  943 */     return (result == null) ? (this.multiset = new MultisetView(null)) : result;
/*      */   }
/*      */   
/*      */   private class MultisetView extends AbstractMultiset<K> { Set<Multiset.Entry<K>> entrySet;
/*      */     
/*      */     public int remove(Object key, int occurrences) {
/*      */       Collection<V> collection;
/*  950 */       if (occurrences == 0) {
/*  951 */         return count(key);
/*      */       }
/*  953 */       Preconditions.checkArgument((occurrences > 0));
/*      */ 
/*      */       
/*      */       try {
/*  957 */         collection = (Collection)AbstractMultimap.this.map.get(key);
/*  958 */       } catch (NullPointerException e) {
/*  959 */         return 0;
/*  960 */       } catch (ClassCastException e) {
/*  961 */         return 0;
/*      */       } 
/*      */       
/*  964 */       if (collection == null) {
/*  965 */         return 0;
/*      */       }
/*  967 */       int count = collection.size();
/*      */       
/*  969 */       if (occurrences >= count) {
/*  970 */         return AbstractMultimap.this.removeValuesForKey(key);
/*      */       }
/*      */       
/*  973 */       Iterator<V> iterator = collection.iterator();
/*  974 */       for (int i = 0; i < occurrences; i++) {
/*  975 */         iterator.next();
/*  976 */         iterator.remove();
/*      */       } 
/*  978 */       AbstractMultimap.this.totalSize -= occurrences;
/*  979 */       return count;
/*      */     }
/*      */     private MultisetView() {}
/*      */     
/*  983 */     public Set<K> elementSet() { return AbstractMultimap.this.keySet(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Multiset.Entry<K>> entrySet() {
/*  989 */       Set<Multiset.Entry<K>> result = this.entrySet;
/*  990 */       return (result == null) ? (this.entrySet = new EntrySet(null)) : result;
/*      */     }
/*      */     
/*      */     private class EntrySet extends AbstractSet<Multiset.Entry<K>> { private EntrySet() {}
/*      */       
/*  995 */       public Iterator<Multiset.Entry<K>> iterator() { return new AbstractMultimap.MultisetEntryIterator(AbstractMultimap.MultisetView.this.this$0, null); }
/*      */ 
/*      */       
/*  998 */       public int size() { return AbstractMultimap.MultisetView.this.this$0.map.size(); }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/* 1004 */         if (!(o instanceof Multiset.Entry)) {
/* 1005 */           return false;
/*      */         }
/* 1007 */         Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 1008 */         Collection<V> collection = (Collection)AbstractMultimap.MultisetView.this.this$0.map.get(entry.getElement());
/* 1009 */         return (collection != null && collection.size() == entry.getCount());
/*      */       }
/*      */ 
/*      */       
/* 1013 */       public void clear() { AbstractMultimap.MultisetView.this.this$0.clear(); }
/*      */ 
/*      */       
/* 1016 */       public boolean remove(Object o) { return (super.contains(o) && AbstractMultimap.MultisetView.this.this$0.removeValuesForKey(((Multiset.Entry)o).getElement()) > 0); } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     public Iterator<K> iterator() { return new AbstractMultimap.MultisetKeyIterator(null); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int count(Object key) {
/*      */       try {
/* 1029 */         Collection<V> collection = (Collection)AbstractMultimap.this.map.get(key);
/* 1030 */         return (collection == null) ? 0 : collection.size();
/* 1031 */       } catch (NullPointerException e) {
/* 1032 */         return 0;
/* 1033 */       } catch (ClassCastException e) {
/* 1034 */         return 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1039 */     public int size() { return AbstractMultimap.this.totalSize; }
/*      */ 
/*      */ 
/*      */     
/* 1043 */     public void clear() { AbstractMultimap.this.clear(); } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int removeValuesForKey(Object key) {
/*      */     Collection<V> collection;
/*      */     try {
/* 1054 */       collection = (Collection)this.map.remove(key);
/* 1055 */     } catch (NullPointerException e) {
/* 1056 */       return 0;
/* 1057 */     } catch (ClassCastException e) {
/* 1058 */       return 0;
/*      */     } 
/*      */     
/* 1061 */     int count = 0;
/* 1062 */     if (collection != null) {
/* 1063 */       count = collection.size();
/* 1064 */       collection.clear();
/* 1065 */       this.totalSize -= count;
/*      */     } 
/* 1067 */     return count;
/*      */   }
/*      */   
/*      */   private class MultisetEntryIterator
/*      */     extends Object implements Iterator<Multiset.Entry<K>> {
/* 1072 */     final Iterator<Map.Entry<K, Collection<V>>> asMapIterator = AbstractMultimap.this.asMap().entrySet().iterator();
/*      */ 
/*      */ 
/*      */     
/* 1076 */     public boolean hasNext() { return this.asMapIterator.hasNext(); }
/*      */ 
/*      */     
/* 1079 */     public Multiset.Entry<K> next() { return new AbstractMultimap.MultisetEntry(AbstractMultimap.this, (Map.Entry)this.asMapIterator.next()); }
/*      */ 
/*      */     
/* 1082 */     public void remove() { this.asMapIterator.remove(); }
/*      */     
/*      */     private MultisetEntryIterator() {}
/*      */   }
/*      */   
/*      */   private class MultisetEntry extends Multisets.AbstractEntry<K> {
/*      */     final Map.Entry<K, Collection<V>> entry;
/*      */     
/* 1090 */     public MultisetEntry(Map.Entry<K, Collection<V>> entry) { this.entry = entry; }
/*      */ 
/*      */     
/* 1093 */     public K getElement() { return (K)this.entry.getKey(); }
/*      */ 
/*      */     
/* 1096 */     public int getCount() { return ((Collection)this.entry.getValue()).size(); }
/*      */   }
/*      */   
/*      */   private class MultisetKeyIterator
/*      */     extends Object
/*      */     implements Iterator<K> {
/* 1102 */     final Iterator<Map.Entry<K, V>> entryIterator = AbstractMultimap.this.entries().iterator();
/*      */ 
/*      */     
/* 1105 */     public boolean hasNext() { return this.entryIterator.hasNext(); }
/*      */ 
/*      */     
/* 1108 */     public K next() { return (K)((Map.Entry)this.entryIterator.next()).getKey(); }
/*      */ 
/*      */     
/* 1111 */     public void remove() { this.entryIterator.remove(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MultisetKeyIterator() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 1124 */     Collection<V> result = this.valuesCollection;
/* 1125 */     return (result == null) ? (this.valuesCollection = new Values(null)) : result;
/*      */   }
/*      */   
/*      */   private class Values extends AbstractCollection<V> { private Values() {}
/*      */     
/* 1130 */     public Iterator<V> iterator() { return new AbstractMultimap.ValueIterator(AbstractMultimap.this, null); }
/*      */ 
/*      */     
/* 1133 */     public int size() { return AbstractMultimap.this.totalSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1139 */     public void clear() { AbstractMultimap.this.clear(); }
/*      */ 
/*      */ 
/*      */     
/* 1143 */     public boolean contains(Object value) { return AbstractMultimap.this.containsValue(value); } }
/*      */ 
/*      */   
/*      */   private class ValueIterator
/*      */     extends Object
/*      */     implements Iterator<V> {
/* 1149 */     final Iterator<Map.Entry<K, V>> entryIterator = AbstractMultimap.this.createEntryIterator();
/*      */ 
/*      */     
/* 1152 */     public boolean hasNext() { return this.entryIterator.hasNext(); }
/*      */ 
/*      */     
/* 1155 */     public V next() { return (V)((Map.Entry)this.entryIterator.next()).getValue(); }
/*      */ 
/*      */     
/* 1158 */     public void remove() { this.entryIterator.remove(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ValueIterator() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<Map.Entry<K, V>> entries() {
/* 1179 */     Collection<Map.Entry<K, V>> result = this.entries;
/* 1180 */     return (result == null) ? (this.entries = createEntries()) : result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1185 */   private Collection<Map.Entry<K, V>> createEntries() { return (this instanceof SetMultimap) ? new EntrySet(null) : new Entries(null); }
/*      */   
/*      */   private class Entries
/*      */     extends AbstractCollection<Map.Entry<K, V>> {
/*      */     private Entries() {}
/*      */     
/* 1191 */     public Iterator<Map.Entry<K, V>> iterator() { return AbstractMultimap.this.createEntryIterator(); }
/*      */ 
/*      */     
/* 1194 */     public int size() { return AbstractMultimap.this.totalSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1200 */       if (!(o instanceof Map.Entry)) {
/* 1201 */         return false;
/*      */       }
/* 1203 */       Map.Entry<?, ?> entry = (Map.Entry)o;
/* 1204 */       return AbstractMultimap.this.containsEntry(entry.getKey(), entry.getValue());
/*      */     }
/*      */ 
/*      */     
/* 1208 */     public void clear() { AbstractMultimap.this.clear(); }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1212 */       if (!(o instanceof Map.Entry)) {
/* 1213 */         return false;
/*      */       }
/* 1215 */       Map.Entry<?, ?> entry = (Map.Entry)o;
/* 1216 */       return AbstractMultimap.this.remove(entry.getKey(), entry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1229 */   Iterator<Map.Entry<K, V>> createEntryIterator() { return new EntryIterator(); }
/*      */   
/*      */   private class EntryIterator
/*      */     extends Object
/*      */     implements Iterator<Map.Entry<K, V>> {
/*      */     final Iterator<Map.Entry<K, Collection<V>>> keyIterator;
/*      */     K key;
/*      */     Collection<V> collection;
/*      */     Iterator<V> valueIterator;
/*      */     
/*      */     EntryIterator() {
/* 1240 */       this.keyIterator = this$0.map.entrySet().iterator();
/* 1241 */       if (this.keyIterator.hasNext()) {
/* 1242 */         findValueIteratorAndKey();
/*      */       } else {
/* 1244 */         this.valueIterator = Iterators.emptyModifiableIterator();
/*      */       } 
/*      */     }
/*      */     
/*      */     void findValueIteratorAndKey() {
/* 1249 */       Map.Entry<K, Collection<V>> entry = (Map.Entry)this.keyIterator.next();
/* 1250 */       this.key = entry.getKey();
/* 1251 */       this.collection = (Collection)entry.getValue();
/* 1252 */       this.valueIterator = this.collection.iterator();
/*      */     }
/*      */ 
/*      */     
/* 1256 */     public boolean hasNext() { return (this.keyIterator.hasNext() || this.valueIterator.hasNext()); }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> next() {
/* 1260 */       if (!this.valueIterator.hasNext()) {
/* 1261 */         findValueIteratorAndKey();
/*      */       }
/* 1263 */       return Maps.immutableEntry(this.key, this.valueIterator.next());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1267 */       this.valueIterator.remove();
/* 1268 */       if (this.collection.isEmpty()) {
/* 1269 */         this.keyIterator.remove();
/*      */       }
/* 1271 */       AbstractMultimap.this.totalSize--;
/*      */     }
/*      */   }
/*      */   
/*      */   private class EntrySet extends Entries implements Set<Map.Entry<K, V>> {
/* 1276 */     private EntrySet() { super(AbstractMultimap.this, null); }
/*      */     
/* 1278 */     public boolean equals(@Nullable Object object) { return Collections2.setEquals(this, object); }
/*      */ 
/*      */     
/* 1281 */     public int hashCode() { return Sets.hashCodeImpl(this); }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<K, Collection<V>> asMap() {
/* 1288 */     Map<K, Collection<V>> result = this.asMap;
/* 1289 */     return (result == null) ? (this.asMap = createAsMap()) : result;
/*      */   }
/*      */ 
/*      */   
/* 1293 */   private Map<K, Collection<V>> createAsMap() { return (this.map instanceof SortedMap) ? new SortedAsMap((SortedMap)this.map) : new AsMap(this.map); }
/*      */ 
/*      */ 
/*      */   
/*      */   private class AsMap
/*      */     extends AbstractMap<K, Collection<V>>
/*      */   {
/*      */     final Map<K, Collection<V>> submap;
/*      */     
/*      */     Set<Map.Entry<K, Collection<V>>> entrySet;
/*      */ 
/*      */     
/* 1305 */     AsMap(Map<K, Collection<V>> submap) { this.submap = submap; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 1311 */       Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
/* 1312 */       return (result == null) ? (this.entrySet = new AsMapEntries()) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1318 */     public boolean containsKey(Object key) { return Maps.safeContainsKey(this.submap, key); }
/*      */ 
/*      */     
/*      */     public Collection<V> get(Object key) {
/* 1322 */       Collection<V> collection = (Collection)Maps.safeGet(this.submap, key);
/* 1323 */       if (collection == null) {
/* 1324 */         return null;
/*      */       }
/*      */       
/* 1327 */       K k = (K)key;
/* 1328 */       return AbstractMultimap.this.wrapCollection(k, collection);
/*      */     }
/*      */ 
/*      */     
/* 1332 */     public Set<K> keySet() { return AbstractMultimap.this.keySet(); }
/*      */ 
/*      */     
/*      */     public Collection<V> remove(Object key) {
/* 1336 */       Collection<V> collection = (Collection)this.submap.remove(key);
/* 1337 */       if (collection == null) {
/* 1338 */         return null;
/*      */       }
/*      */       
/* 1341 */       Collection<V> output = AbstractMultimap.this.createCollection();
/* 1342 */       output.addAll(collection);
/* 1343 */       AbstractMultimap.this.totalSize -= collection.size();
/* 1344 */       collection.clear();
/* 1345 */       return output;
/*      */     }
/*      */ 
/*      */     
/* 1349 */     public boolean equals(@Nullable Object object) { return (this == object || this.submap.equals(object)); }
/*      */ 
/*      */ 
/*      */     
/* 1353 */     public int hashCode() { return this.submap.hashCode(); }
/*      */ 
/*      */ 
/*      */     
/* 1357 */     public String toString() { return this.submap.toString(); }
/*      */     
/*      */     class AsMapEntries
/*      */       extends AbstractSet<Map.Entry<K, Collection<V>>>
/*      */     {
/* 1362 */       public Iterator<Map.Entry<K, Collection<V>>> iterator() { return new AbstractMultimap.AsMap.AsMapIterator(super.this$1); }
/*      */ 
/*      */ 
/*      */       
/* 1366 */       public int size() { return AbstractMultimap.AsMap.this.submap.size(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1372 */       public boolean contains(Object o) { return Collections2.safeContains(AbstractMultimap.AsMap.this.submap.entrySet(), o); }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1376 */         if (!super.contains(o)) {
/* 1377 */           return false;
/*      */         }
/* 1379 */         Map.Entry<?, ?> entry = (Map.Entry)o;
/* 1380 */         AbstractMultimap.AsMap.this.this$0.removeValuesForKey(entry.getKey());
/* 1381 */         return true;
/*      */       } }
/*      */     
/*      */     class AsMapIterator extends Object implements Iterator<Map.Entry<K, Collection<V>>> { final Iterator<Map.Entry<K, Collection<V>>> delegateIterator;
/*      */       Collection<V> collection;
/*      */       
/* 1387 */       AsMapIterator() { super.delegateIterator = AbstractMultimap.AsMap.this.submap.entrySet().iterator(); }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1392 */       public boolean hasNext() { return super.delegateIterator.hasNext(); }
/*      */ 
/*      */       
/*      */       public Map.Entry<K, Collection<V>> next() {
/* 1396 */         Map.Entry<K, Collection<V>> entry = (Map.Entry)super.delegateIterator.next();
/* 1397 */         K key = (K)entry.getKey();
/* 1398 */         super.collection = (Collection)entry.getValue();
/* 1399 */         return Maps.immutableEntry(key, AbstractMultimap.AsMap.this.this$0.wrapCollection(key, super.collection));
/*      */       }
/*      */       
/*      */       public void remove() {
/* 1403 */         super.delegateIterator.remove();
/* 1404 */         AbstractMultimap.AsMap.this.this$0.totalSize -= super.collection.size();
/* 1405 */         super.collection.clear();
/*      */       } }
/*      */   
/*      */   }
/*      */   
/*      */   private class SortedAsMap extends AsMap implements SortedMap<K, Collection<V>> {
/*      */     SortedSet<K> sortedKeySet;
/*      */     
/* 1413 */     SortedAsMap(SortedMap<K, Collection<V>> submap) { super(AbstractMultimap.this, submap); }
/*      */ 
/*      */ 
/*      */     
/* 1417 */     SortedMap<K, Collection<V>> sortedMap() { return (SortedMap)this.submap; }
/*      */ 
/*      */ 
/*      */     
/* 1421 */     public Comparator<? super K> comparator() { return sortedMap().comparator(); }
/*      */ 
/*      */ 
/*      */     
/* 1425 */     public K firstKey() { return (K)sortedMap().firstKey(); }
/*      */ 
/*      */ 
/*      */     
/* 1429 */     public K lastKey() { return (K)sortedMap().lastKey(); }
/*      */ 
/*      */ 
/*      */     
/* 1433 */     public SortedMap<K, Collection<V>> headMap(K toKey) { return new SortedAsMap(AbstractMultimap.this, sortedMap().headMap(toKey)); }
/*      */ 
/*      */ 
/*      */     
/* 1437 */     public SortedMap<K, Collection<V>> subMap(K fromKey, K toKey) { return new SortedAsMap(AbstractMultimap.this, sortedMap().subMap(fromKey, toKey)); }
/*      */ 
/*      */ 
/*      */     
/* 1441 */     public SortedMap<K, Collection<V>> tailMap(K fromKey) { return new SortedAsMap(AbstractMultimap.this, sortedMap().tailMap(fromKey)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<K> keySet() {
/* 1449 */       SortedSet<K> result = this.sortedKeySet;
/* 1450 */       return (result == null) ? (this.sortedKeySet = new AbstractMultimap.SortedKeySet(AbstractMultimap.this, sortedMap())) : result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object object) {
/* 1458 */     if (object == this) {
/* 1459 */       return true;
/*      */     }
/* 1461 */     if (object instanceof Multimap) {
/* 1462 */       Multimap<?, ?> that = (Multimap)object;
/* 1463 */       return this.map.equals(that.asMap());
/*      */     } 
/* 1465 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1477 */   public int hashCode() { return this.map.hashCode(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1487 */   public String toString() { return this.map.toString(); }
/*      */   
/*      */   abstract Collection<V> createCollection();
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */