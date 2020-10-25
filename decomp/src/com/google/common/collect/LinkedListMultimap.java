/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true)
/*     */ public final class LinkedListMultimap<K, V>
/*     */   extends Object
/*     */   implements ListMultimap<K, V>, Serializable
/*     */ {
/*     */   private Node<K, V> head;
/*     */   private Node<K, V> tail;
/*     */   private Multiset<K> keyCount;
/*     */   private Map<K, Node<K, V>> keyToKeyHead;
/*     */   private Map<K, Node<K, V>> keyToKeyTail;
/*     */   private Set<K> keySet;
/*     */   private Multiset<K> keys;
/*     */   private Collection<V> valuesCollection;
/*     */   private Collection<Map.Entry<K, V>> entries;
/*     */   private Map<K, Collection<V>> map;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   private static final class Node<K, V>
/*     */     extends Object
/*     */   {
/*     */     final K key;
/*     */     V value;
/*     */     Node<K, V> next;
/*     */     Node<K, V> previous;
/*     */     Node<K, V> nextSibling;
/*     */     Node<K, V> previousSibling;
/*     */     
/*     */     Node(@Nullable K key, @Nullable V value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/* 119 */     public String toString() { return this.key + "=" + this.value; }
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
/* 134 */   public static <K, V> LinkedListMultimap<K, V> create() { return new LinkedListMultimap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static <K, V> LinkedListMultimap<K, V> create(int expectedKeys) { return new LinkedListMultimap(expectedKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) { return new LinkedListMultimap(multimap); }
/*     */ 
/*     */   
/*     */   private LinkedListMultimap() {
/* 161 */     this.keyCount = LinkedHashMultiset.create();
/* 162 */     this.keyToKeyHead = Maps.newHashMap();
/* 163 */     this.keyToKeyTail = Maps.newHashMap();
/*     */   }
/*     */   
/*     */   private LinkedListMultimap(int expectedKeys) {
/* 167 */     this.keyCount = LinkedHashMultiset.create(expectedKeys);
/* 168 */     this.keyToKeyHead = Maps.newHashMapWithExpectedSize(expectedKeys);
/* 169 */     this.keyToKeyTail = Maps.newHashMapWithExpectedSize(expectedKeys);
/*     */   }
/*     */   
/*     */   private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
/* 173 */     this(multimap.keySet().size());
/* 174 */     putAll(multimap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Node<K, V> addNode(@Nullable K key, @Nullable V value, @Nullable Node<K, V> nextSibling) {
/* 185 */     Node<K, V> node = new Node<K, V>(key, value);
/* 186 */     if (this.head == null) {
/* 187 */       this.head = this.tail = node;
/* 188 */       this.keyToKeyHead.put(key, node);
/* 189 */       this.keyToKeyTail.put(key, node);
/* 190 */     } else if (nextSibling == null) {
/* 191 */       this.tail.next = node;
/* 192 */       node.previous = this.tail;
/* 193 */       Node<K, V> keyTail = (Node)this.keyToKeyTail.get(key);
/* 194 */       if (keyTail == null) {
/* 195 */         this.keyToKeyHead.put(key, node);
/*     */       } else {
/* 197 */         keyTail.nextSibling = node;
/* 198 */         node.previousSibling = keyTail;
/*     */       } 
/* 200 */       this.keyToKeyTail.put(key, node);
/* 201 */       this.tail = node;
/*     */     } else {
/* 203 */       node.previous = nextSibling.previous;
/* 204 */       node.previousSibling = nextSibling.previousSibling;
/* 205 */       node.next = nextSibling;
/* 206 */       node.nextSibling = nextSibling;
/* 207 */       if (nextSibling.previousSibling == null) {
/* 208 */         this.keyToKeyHead.put(key, node);
/*     */       } else {
/* 210 */         nextSibling.previousSibling.nextSibling = node;
/*     */       } 
/* 212 */       if (nextSibling.previous == null) {
/* 213 */         this.head = node;
/*     */       } else {
/* 215 */         nextSibling.previous.next = node;
/*     */       } 
/* 217 */       nextSibling.previous = node;
/* 218 */       nextSibling.previousSibling = node;
/*     */     } 
/* 220 */     this.keyCount.add(key);
/* 221 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeNode(Node<K, V> node) {
/* 230 */     if (node.previous != null) {
/* 231 */       node.previous.next = node.next;
/*     */     } else {
/* 233 */       this.head = node.next;
/*     */     } 
/* 235 */     if (node.next != null) {
/* 236 */       node.next.previous = node.previous;
/*     */     } else {
/* 238 */       this.tail = node.previous;
/*     */     } 
/* 240 */     if (node.previousSibling != null) {
/* 241 */       node.previousSibling.nextSibling = node.nextSibling;
/* 242 */     } else if (node.nextSibling != null) {
/* 243 */       this.keyToKeyHead.put(node.key, node.nextSibling);
/*     */     } else {
/* 245 */       this.keyToKeyHead.remove(node.key);
/*     */     } 
/* 247 */     if (node.nextSibling != null) {
/* 248 */       node.nextSibling.previousSibling = node.previousSibling;
/* 249 */     } else if (node.previousSibling != null) {
/* 250 */       this.keyToKeyTail.put(node.key, node.previousSibling);
/*     */     } else {
/* 252 */       this.keyToKeyTail.remove(node.key);
/*     */     } 
/* 254 */     this.keyCount.remove(node.key);
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeAllNodes(@Nullable Object key) {
/* 259 */     for (Iterator<V> i = new ValueForKeyIterator<V>(key); i.hasNext(); ) {
/* 260 */       i.next();
/* 261 */       i.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkElement(@Nullable Object node) {
/* 267 */     if (node == null)
/* 268 */       throw new NoSuchElementException(); 
/*     */   }
/*     */   
/*     */   private class NodeIterator
/*     */     extends Object
/*     */     implements Iterator<Node<K, V>> {
/* 274 */     LinkedListMultimap.Node<K, V> next = LinkedListMultimap.this.head;
/*     */     
/*     */     LinkedListMultimap.Node<K, V> current;
/*     */     
/* 278 */     public boolean hasNext() { return (this.next != null); }
/*     */     
/*     */     public LinkedListMultimap.Node<K, V> next() {
/* 281 */       LinkedListMultimap.checkElement(this.next);
/* 282 */       this.current = this.next;
/* 283 */       this.next = this.next.next;
/* 284 */       return this.current;
/*     */     }
/*     */     public void remove() {
/* 287 */       Preconditions.checkState((this.current != null));
/* 288 */       LinkedListMultimap.this.removeNode(this.current);
/* 289 */       this.current = null;
/*     */     }
/*     */     
/*     */     private NodeIterator() {} }
/*     */   
/*     */   private class DistinctKeyIterator extends Object implements Iterator<K> {
/* 295 */     final Set<K> seenKeys = new HashSet(Maps.capacity(LinkedListMultimap.this.keySet().size()));
/* 296 */     LinkedListMultimap.Node<K, V> next = LinkedListMultimap.this.head;
/*     */     
/*     */     LinkedListMultimap.Node<K, V> current;
/*     */     
/* 300 */     public boolean hasNext() { return (this.next != null); }
/*     */     
/*     */     public K next() {
/* 303 */       LinkedListMultimap.checkElement(this.next);
/* 304 */       this.current = this.next;
/* 305 */       this.seenKeys.add(this.current.key);
/*     */       do {
/* 307 */         this.next = this.next.next;
/* 308 */       } while (this.next != null && !this.seenKeys.add(this.next.key));
/* 309 */       return (K)this.current.key;
/*     */     }
/*     */     public void remove() {
/* 312 */       Preconditions.checkState((this.current != null));
/* 313 */       LinkedListMultimap.this.removeAllNodes(this.current.key);
/* 314 */       this.current = null;
/*     */     }
/*     */     
/*     */     private DistinctKeyIterator() {}
/*     */   }
/*     */   
/*     */   private class ValueForKeyIterator extends Object implements ListIterator<V> {
/*     */     final Object key;
/*     */     int nextIndex;
/*     */     LinkedListMultimap.Node<K, V> next;
/*     */     LinkedListMultimap.Node<K, V> current;
/*     */     LinkedListMultimap.Node<K, V> previous;
/*     */     
/*     */     ValueForKeyIterator(Object key) {
/* 328 */       this.key = key;
/* 329 */       this.next = (LinkedListMultimap.Node)this$0.keyToKeyHead.get(key);
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
/*     */     public ValueForKeyIterator(Object key, int index) {
/* 342 */       int size = this$0.keyCount.count(key);
/* 343 */       Preconditions.checkPositionIndex(index, size);
/* 344 */       if (index >= size / 2) {
/* 345 */         this.previous = (LinkedListMultimap.Node)this$0.keyToKeyTail.get(key);
/* 346 */         this.nextIndex = size;
/* 347 */         while (index++ < size) {
/* 348 */           previous();
/*     */         }
/*     */       } else {
/* 351 */         this.next = (LinkedListMultimap.Node)this$0.keyToKeyHead.get(key);
/* 352 */         while (index-- > 0) {
/* 353 */           next();
/*     */         }
/*     */       } 
/* 356 */       this.key = key;
/* 357 */       this.current = null;
/*     */     }
/*     */ 
/*     */     
/* 361 */     public boolean hasNext() { return (this.next != null); }
/*     */ 
/*     */     
/*     */     public V next() {
/* 365 */       LinkedListMultimap.checkElement(this.next);
/* 366 */       this.previous = this.current = this.next;
/* 367 */       this.next = this.next.nextSibling;
/* 368 */       this.nextIndex++;
/* 369 */       return (V)this.current.value;
/*     */     }
/*     */ 
/*     */     
/* 373 */     public boolean hasPrevious() { return (this.previous != null); }
/*     */ 
/*     */     
/*     */     public V previous() {
/* 377 */       LinkedListMultimap.checkElement(this.previous);
/* 378 */       this.next = this.current = this.previous;
/* 379 */       this.previous = this.previous.previousSibling;
/* 380 */       this.nextIndex--;
/* 381 */       return (V)this.current.value;
/*     */     }
/*     */ 
/*     */     
/* 385 */     public int nextIndex() { return this.nextIndex; }
/*     */ 
/*     */ 
/*     */     
/* 389 */     public int previousIndex() { return this.nextIndex - 1; }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 393 */       Preconditions.checkState((this.current != null));
/* 394 */       if (this.current != this.next) {
/* 395 */         this.previous = this.current.previousSibling;
/* 396 */         this.nextIndex--;
/*     */       } else {
/* 398 */         this.next = this.current.nextSibling;
/*     */       } 
/* 400 */       LinkedListMultimap.this.removeNode(this.current);
/* 401 */       this.current = null;
/*     */     }
/*     */     
/*     */     public void set(V value) {
/* 405 */       Preconditions.checkState((this.current != null));
/* 406 */       this.current.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(V value) {
/* 411 */       this.previous = LinkedListMultimap.this.addNode(this.key, value, this.next);
/* 412 */       this.nextIndex++;
/* 413 */       this.current = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   public int size() { return this.keyCount.size(); }
/*     */ 
/*     */ 
/*     */   
/* 424 */   public boolean isEmpty() { return (this.head == null); }
/*     */ 
/*     */ 
/*     */   
/* 428 */   public boolean containsKey(@Nullable Object key) { return this.keyToKeyHead.containsKey(key); }
/*     */ 
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 432 */     for (Iterator<Node<K, V>> i = new NodeIterator<Node<K, V>>(null); i.hasNext();) {
/* 433 */       if (Objects.equal(((Node)i.next()).value, value)) {
/* 434 */         return true;
/*     */       }
/*     */     } 
/* 437 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/* 441 */     for (Iterator<V> i = new ValueForKeyIterator<V>(key); i.hasNext();) {
/* 442 */       if (Objects.equal(i.next(), value)) {
/* 443 */         return true;
/*     */       }
/*     */     } 
/* 446 */     return false;
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
/*     */   public boolean put(@Nullable K key, @Nullable V value) {
/* 459 */     addNode(key, value, null);
/* 460 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/* 464 */     Iterator<V> values = new ValueForKeyIterator<V>(key);
/* 465 */     while (values.hasNext()) {
/* 466 */       if (Objects.equal(values.next(), value)) {
/* 467 */         values.remove();
/* 468 */         return true;
/*     */       } 
/*     */     } 
/* 471 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
/* 477 */     boolean changed = false;
/* 478 */     for (V value : values) {
/* 479 */       changed |= put(key, value);
/*     */     }
/* 481 */     return changed;
/*     */   }
/*     */   
/*     */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/* 485 */     boolean changed = false;
/* 486 */     for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
/* 487 */       changed |= put(entry.getKey(), entry.getValue());
/*     */     }
/* 489 */     return changed;
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
/*     */   public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/* 503 */     List<V> oldValues = getCopy(key);
/* 504 */     ListIterator<V> keyValues = new ValueForKeyIterator<V>(key);
/* 505 */     Iterator<? extends V> newValues = values.iterator();
/*     */ 
/*     */     
/* 508 */     while (keyValues.hasNext() && newValues.hasNext()) {
/* 509 */       keyValues.next();
/* 510 */       keyValues.set(newValues.next());
/*     */     } 
/*     */ 
/*     */     
/* 514 */     while (keyValues.hasNext()) {
/* 515 */       keyValues.next();
/* 516 */       keyValues.remove();
/*     */     } 
/*     */ 
/*     */     
/* 520 */     while (newValues.hasNext()) {
/* 521 */       keyValues.add(newValues.next());
/*     */     }
/*     */     
/* 524 */     return oldValues;
/*     */   }
/*     */ 
/*     */   
/* 528 */   private List<V> getCopy(@Nullable Object key) { return Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(key))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<V> removeAll(@Nullable Object key) {
/* 538 */     List<V> oldValues = getCopy(key);
/* 539 */     removeAllNodes(key);
/* 540 */     return oldValues;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 544 */     this.head = null;
/* 545 */     this.tail = null;
/* 546 */     this.keyCount.clear();
/* 547 */     this.keyToKeyHead.clear();
/* 548 */     this.keyToKeyTail.clear();
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
/*     */   public List<V> get(@Nullable final K key) {
/* 563 */     return new AbstractSequentialList<V>()
/*     */       {
/* 565 */         public int size() { return super.this$0.keyCount.count(key); }
/*     */ 
/*     */         
/* 568 */         public ListIterator<V> listIterator(int index) { return new LinkedListMultimap.ValueForKeyIterator(super.this$0, key, index); }
/*     */ 
/*     */         
/* 571 */         public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */         
/* 574 */         public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 582 */     Set<K> result = this.keySet;
/* 583 */     if (result == null) {
/* 584 */       this.keySet = result = new AbstractSet<K>()
/*     */         {
/* 586 */           public int size() { return super.this$0.keyCount.elementSet().size(); }
/*     */ 
/*     */           
/* 589 */           public Iterator<K> iterator() { return new LinkedListMultimap.DistinctKeyIterator(super.this$0, null); }
/*     */           
/*     */           public boolean contains(Object key) {
/* 592 */             return super.this$0.keyCount.contains(key);
/*     */           }
/*     */         };
/*     */     }
/* 596 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Multiset<K> keys() {
/* 602 */     Multiset<K> result = this.keys;
/* 603 */     if (result == null) {
/* 604 */       this.keys = result = new MultisetView<K>(null);
/*     */     }
/* 606 */     return result;
/*     */   }
/*     */   
/*     */   private class MultisetView
/*     */     extends AbstractCollection<K> implements Multiset<K> {
/*     */     private MultisetView() {}
/*     */     
/* 613 */     public int size() { return LinkedListMultimap.this.keyCount.size(); }
/*     */ 
/*     */     
/*     */     public Iterator<K> iterator() {
/* 617 */       final Iterator<LinkedListMultimap.Node<K, V>> nodes = new LinkedListMultimap.NodeIterator<LinkedListMultimap.Node<K, V>>(LinkedListMultimap.this, null);
/* 618 */       return new Iterator<K>()
/*     */         {
/* 620 */           public boolean hasNext() { return nodes.hasNext(); }
/*     */ 
/*     */           
/* 623 */           public K next() { return (K)((LinkedListMultimap.Node)super.val$nodes.next()).key; }
/*     */ 
/*     */           
/* 626 */           public void remove() { nodes.remove(); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 632 */     public int count(@Nullable Object key) { return LinkedListMultimap.this.keyCount.count(key); }
/*     */ 
/*     */ 
/*     */     
/* 636 */     public int add(@Nullable K key, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */     
/*     */     public int remove(@Nullable Object key, int occurrences) {
/* 640 */       Preconditions.checkArgument((occurrences >= 0));
/* 641 */       int oldCount = count(key);
/* 642 */       Iterator<V> values = new LinkedListMultimap.ValueForKeyIterator<V>(LinkedListMultimap.this, key);
/* 643 */       while (occurrences-- > 0 && values.hasNext()) {
/* 644 */         values.next();
/* 645 */         values.remove();
/*     */       } 
/* 647 */       return oldCount;
/*     */     }
/*     */ 
/*     */     
/* 651 */     public int setCount(K element, int count) { return Multisets.setCountImpl(this, element, count); }
/*     */ 
/*     */ 
/*     */     
/* 655 */     public boolean setCount(K element, int oldCount, int newCount) { return Multisets.setCountImpl(this, element, oldCount, newCount); }
/*     */ 
/*     */ 
/*     */     
/* 659 */     public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */ 
/*     */     
/* 663 */     public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */ 
/*     */ 
/*     */     
/* 667 */     public Set<K> elementSet() { return LinkedListMultimap.this.keySet(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Multiset.Entry<K>> entrySet() {
/* 672 */       return new AbstractSet<Multiset.Entry<K>>()
/*     */         {
/* 674 */           public int size() { return LinkedListMultimap.MultisetView.this.this$0.keyCount.elementSet().size(); }
/*     */ 
/*     */           
/*     */           public Iterator<Multiset.Entry<K>> iterator() {
/* 678 */             final Iterator<K> keyIterator = new LinkedListMultimap.DistinctKeyIterator<K>(LinkedListMultimap.MultisetView.this.this$0, null);
/* 679 */             return new Iterator<Multiset.Entry<K>>()
/*     */               {
/* 681 */                 public boolean hasNext() { return keyIterator.hasNext(); }
/*     */                 
/*     */                 public Multiset.Entry<K> next() {
/* 684 */                   final K key = (K)keyIterator.next();
/* 685 */                   return new Multisets.AbstractEntry<K>()
/*     */                     {
/* 687 */                       public K getElement() { return (K)key; }
/*     */ 
/*     */                       
/* 690 */                       public int getCount() { return LinkedListMultimap.MultisetView.this.this$0.keyCount.count(key); }
/*     */                     };
/*     */                 }
/*     */ 
/*     */                 
/* 695 */                 public void remove() { keyIterator.remove(); }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 703 */     public boolean equals(@Nullable Object object) { return LinkedListMultimap.this.keyCount.equals(object); }
/*     */ 
/*     */ 
/*     */     
/* 707 */     public int hashCode() { return LinkedListMultimap.this.keyCount.hashCode(); }
/*     */ 
/*     */ 
/*     */     
/* 711 */     public String toString() { return LinkedListMultimap.this.keyCount.toString(); }
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
/*     */   public Collection<V> values() {
/* 724 */     Collection<V> result = this.valuesCollection;
/* 725 */     if (result == null) {
/* 726 */       this.valuesCollection = result = new AbstractCollection<V>()
/*     */         {
/* 728 */           public int size() { return super.this$0.keyCount.size(); }
/*     */           
/*     */           public Iterator<V> iterator() {
/* 731 */             final Iterator<LinkedListMultimap.Node<K, V>> nodes = new LinkedListMultimap.NodeIterator<LinkedListMultimap.Node<K, V>>(null);
/* 732 */             return new Iterator<V>()
/*     */               {
/* 734 */                 public boolean hasNext() { return nodes.hasNext(); }
/*     */ 
/*     */                 
/* 737 */                 public V next() { return (V)((LinkedListMultimap.Node)super.val$nodes.next()).value; }
/*     */                 
/*     */                 public void remove() {
/* 740 */                   nodes.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/* 746 */     return result;
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
/*     */   public Collection<Map.Entry<K, V>> entries() {
/* 767 */     Collection<Map.Entry<K, V>> result = this.entries;
/* 768 */     if (result == null) {
/* 769 */       this.entries = result = new AbstractCollection<Map.Entry<K, V>>()
/*     */         {
/* 771 */           public int size() { return super.this$0.keyCount.size(); }
/*     */ 
/*     */           
/*     */           public Iterator<Map.Entry<K, V>> iterator() {
/* 775 */             final Iterator<LinkedListMultimap.Node<K, V>> nodes = new LinkedListMultimap.NodeIterator<LinkedListMultimap.Node<K, V>>(null);
/* 776 */             return new Iterator<Map.Entry<K, V>>()
/*     */               {
/* 778 */                 public boolean hasNext() { return nodes.hasNext(); }
/*     */ 
/*     */                 
/*     */                 public Map.Entry<K, V> next() {
/* 782 */                   final LinkedListMultimap.Node<K, V> node = (LinkedListMultimap.Node)nodes.next();
/* 783 */                   return new AbstractMapEntry<K, V>()
/*     */                     {
/* 785 */                       public K getKey() { return (K)super.val$node.key; }
/*     */ 
/*     */                       
/* 788 */                       public V getValue() { return (V)super.val$node.value; }
/*     */                       
/*     */                       public V setValue(V value) {
/* 791 */                         V oldValue = (V)super.val$node.value;
/* 792 */                         super.val$node.value = value;
/* 793 */                         return oldValue;
/*     */                       }
/*     */                     };
/*     */                 }
/*     */                 
/*     */                 public void remove() {
/* 799 */                   nodes.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/* 805 */     return result;
/*     */   }
/*     */   
/*     */   private class AsMapEntries
/*     */     extends AbstractSet<Map.Entry<K, Collection<V>>>
/*     */   {
/*     */     private AsMapEntries() {}
/*     */     
/* 813 */     public int size() { return LinkedListMultimap.this.keyCount.elementSet().size(); }
/*     */ 
/*     */     
/*     */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 817 */       final Iterator<K> keyIterator = new LinkedListMultimap.DistinctKeyIterator<K>(LinkedListMultimap.this, null);
/* 818 */       return new Iterator<Map.Entry<K, Collection<V>>>()
/*     */         {
/* 820 */           public boolean hasNext() { return keyIterator.hasNext(); }
/*     */ 
/*     */           
/*     */           public Map.Entry<K, Collection<V>> next() {
/* 824 */             final K key = (K)keyIterator.next();
/* 825 */             return new AbstractMapEntry<K, Collection<V>>()
/*     */               {
/* 827 */                 public K getKey() { return (K)key; }
/*     */ 
/*     */ 
/*     */                 
/* 831 */                 public Collection<V> getValue() { return LinkedListMultimap.AsMapEntries.this.this$0.get(key); }
/*     */               };
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 837 */           public void remove() { keyIterator.remove(); }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<K, Collection<V>> asMap() {
/* 846 */     Map<K, Collection<V>> result = this.map;
/* 847 */     if (result == null) {
/* 848 */       this.map = result = new AbstractMap<K, Collection<V>>() {
/*     */           Set<Map.Entry<K, Collection<V>>> entrySet;
/*     */           
/*     */           public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 852 */             Set<Map.Entry<K, Collection<V>>> result = super.entrySet;
/* 853 */             if (result == null) {
/* 854 */               super.entrySet = result = new LinkedListMultimap.AsMapEntries<Map.Entry<K, Collection<V>>>(super.this$0, null);
/*     */             }
/* 856 */             return result;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 862 */           public boolean containsKey(@Nullable Object key) { return super.this$0.containsKey(key); }
/*     */ 
/*     */ 
/*     */           
/*     */           public Collection<V> get(@Nullable Object key) {
/* 867 */             Collection<V> collection = super.this$0.get(key);
/* 868 */             return collection.isEmpty() ? null : collection;
/*     */           }
/*     */           
/*     */           public Collection<V> remove(@Nullable Object key) {
/* 872 */             Collection<V> collection = super.this$0.removeAll(key);
/* 873 */             return collection.isEmpty() ? null : collection;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/* 878 */     return result;
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
/*     */   public boolean equals(@Nullable Object other) {
/* 891 */     if (other == this) {
/* 892 */       return true;
/*     */     }
/* 894 */     if (other instanceof Multimap) {
/* 895 */       Multimap<?, ?> that = (Multimap)other;
/* 896 */       return asMap().equals(that.asMap());
/*     */     } 
/* 898 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 908 */   public int hashCode() { return asMap().hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 918 */   public String toString() { return asMap().toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 927 */     stream.defaultWriteObject();
/* 928 */     stream.writeInt(size());
/* 929 */     for (Map.Entry<K, V> entry : entries()) {
/* 930 */       stream.writeObject(entry.getKey());
/* 931 */       stream.writeObject(entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 937 */     stream.defaultReadObject();
/* 938 */     this.keyCount = LinkedHashMultiset.create();
/* 939 */     this.keyToKeyHead = Maps.newHashMap();
/* 940 */     this.keyToKeyTail = Maps.newHashMap();
/* 941 */     int size = stream.readInt();
/* 942 */     for (int i = 0; i < size; i++) {
/*     */       
/* 944 */       K key = (K)stream.readObject();
/*     */       
/* 946 */       V value = (V)stream.readObject();
/* 947 */       put(key, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\LinkedListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */