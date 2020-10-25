/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ public final class LinkedHashMultimap<K, V>
/*     */   extends AbstractSetMultimap<K, V>
/*     */ {
/*     */   private static final int DEFAULT_VALUES_PER_KEY = 8;
/*     */   @VisibleForTesting
/*  73 */   int expectedValuesPerKey = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Collection<Map.Entry<K, V>> linkedEntries;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static <K, V> LinkedHashMultimap<K, V> create() { return new LinkedHashMultimap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static <K, V> LinkedHashMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) { return new LinkedHashMultimap(expectedKeys, expectedValuesPerKey); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) { return new LinkedHashMultimap(multimap); }
/*     */ 
/*     */   
/*     */   private LinkedHashMultimap() {
/* 120 */     super(new LinkedHashMap());
/* 121 */     this.linkedEntries = Sets.newLinkedHashSet();
/*     */   }
/*     */   
/*     */   private LinkedHashMultimap(int expectedKeys, int expectedValuesPerKey) {
/* 125 */     super(new LinkedHashMap(expectedKeys));
/* 126 */     Preconditions.checkArgument((expectedValuesPerKey >= 0));
/* 127 */     this.expectedValuesPerKey = expectedValuesPerKey;
/* 128 */     this.linkedEntries = new LinkedHashSet(expectedKeys * expectedValuesPerKey);
/*     */   }
/*     */ 
/*     */   
/*     */   private LinkedHashMultimap(Multimap<? extends K, ? extends V> multimap) {
/* 133 */     super(new LinkedHashMap(Maps.capacity(multimap.keySet().size())));
/*     */     
/* 135 */     this.linkedEntries = new LinkedHashSet(Maps.capacity(multimap.size()));
/*     */     
/* 137 */     putAll(multimap);
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
/* 150 */   Set<V> createCollection() { return new LinkedHashSet(Maps.capacity(this.expectedValuesPerKey)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   Collection<V> createCollection(@Nullable K key) { return new SetDecorator(key, createCollection()); }
/*     */   
/*     */   private class SetDecorator
/*     */     extends ForwardingSet<V> {
/*     */     final Set<V> delegate;
/*     */     final K key;
/*     */     
/*     */     SetDecorator(K key, Set<V> delegate) {
/* 172 */       this.delegate = delegate;
/* 173 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/* 177 */     protected Set<V> delegate() { return this.delegate; }
/*     */ 
/*     */ 
/*     */     
/* 181 */     <E> Map.Entry<K, E> createEntry(@Nullable E value) { return Maps.immutableEntry(this.key, value); }
/*     */ 
/*     */ 
/*     */     
/*     */     <E> Collection<Map.Entry<K, E>> createEntries(Collection<E> values) {
/* 186 */       Collection<Map.Entry<K, E>> entries = Lists.newArrayListWithExpectedSize(values.size());
/*     */       
/* 188 */       for (E value : values) {
/* 189 */         entries.add(createEntry(value));
/*     */       }
/* 191 */       return entries;
/*     */     }
/*     */     
/*     */     public boolean add(@Nullable V value) {
/* 195 */       boolean changed = this.delegate.add(value);
/* 196 */       if (changed) {
/* 197 */         LinkedHashMultimap.this.linkedEntries.add(createEntry(value));
/*     */       }
/* 199 */       return changed;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends V> values) {
/* 203 */       boolean changed = this.delegate.addAll(values);
/* 204 */       if (changed) {
/* 205 */         LinkedHashMultimap.this.linkedEntries.addAll(createEntries(delegate()));
/*     */       }
/* 207 */       return changed;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 211 */       LinkedHashMultimap.this.linkedEntries.removeAll(createEntries(delegate()));
/* 212 */       this.delegate.clear();
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 216 */       final Iterator<V> delegateIterator = this.delegate.iterator();
/* 217 */       return new Iterator<V>()
/*     */         {
/*     */           V value;
/*     */           
/* 221 */           public boolean hasNext() { return delegateIterator.hasNext(); }
/*     */           
/*     */           public V next() {
/* 224 */             super.value = delegateIterator.next();
/* 225 */             return (V)super.value;
/*     */           }
/*     */           public void remove() {
/* 228 */             delegateIterator.remove();
/* 229 */             LinkedHashMultimap.this.linkedEntries.remove(super.this$1.createEntry(super.value));
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean remove(@Nullable Object value) {
/* 235 */       boolean changed = this.delegate.remove(value);
/* 236 */       if (changed)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 241 */         LinkedHashMultimap.this.linkedEntries.remove(createEntry(value));
/*     */       }
/* 243 */       return changed;
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> values) {
/* 247 */       boolean changed = this.delegate.removeAll(values);
/* 248 */       if (changed) {
/* 249 */         LinkedHashMultimap.this.linkedEntries.removeAll(createEntries(values));
/*     */       }
/* 251 */       return changed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> values) {
/* 259 */       boolean changed = false;
/* 260 */       Iterator<V> iterator = this.delegate.iterator();
/* 261 */       while (iterator.hasNext()) {
/* 262 */         V value = (V)iterator.next();
/* 263 */         if (!values.contains(value)) {
/* 264 */           iterator.remove();
/* 265 */           LinkedHashMultimap.this.linkedEntries.remove(Maps.immutableEntry(this.key, value));
/* 266 */           changed = true;
/*     */         } 
/*     */       } 
/* 269 */       return changed;
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
/*     */   Iterator<Map.Entry<K, V>> createEntryIterator() {
/* 282 */     final Iterator<Map.Entry<K, V>> delegateIterator = this.linkedEntries.iterator();
/*     */     
/* 284 */     return new Iterator<Map.Entry<K, V>>()
/*     */       {
/*     */         Map.Entry<K, V> entry;
/*     */         
/* 288 */         public boolean hasNext() { return delegateIterator.hasNext(); }
/*     */ 
/*     */         
/*     */         public Map.Entry<K, V> next() {
/* 292 */           super.entry = (Map.Entry)delegateIterator.next();
/* 293 */           return super.entry;
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 298 */           delegateIterator.remove();
/* 299 */           super.this$0.remove(super.entry.getKey(), super.entry.getValue());
/*     */         }
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
/* 314 */   public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) { return super.replaceValues(key, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public Set<Map.Entry<K, V>> entries() { return super.entries(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 341 */   public Collection<V> values() { return super.values(); }
/*     */ 
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
/* 353 */     stream.defaultWriteObject();
/* 354 */     stream.writeInt(this.expectedValuesPerKey);
/* 355 */     Serialization.writeMultimap(this, stream);
/* 356 */     for (Map.Entry<K, V> entry : this.linkedEntries) {
/* 357 */       stream.writeObject(entry.getKey());
/* 358 */       stream.writeObject(entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 364 */     stream.defaultReadObject();
/* 365 */     this.expectedValuesPerKey = stream.readInt();
/* 366 */     int distinctKeys = Serialization.readCount(stream);
/* 367 */     setMap(new LinkedHashMap(Maps.capacity(distinctKeys)));
/* 368 */     this.linkedEntries = new LinkedHashSet(distinctKeys * this.expectedValuesPerKey);
/*     */     
/* 370 */     Serialization.populateMultimap(this, stream, distinctKeys);
/* 371 */     this.linkedEntries.clear();
/* 372 */     for (int i = 0; i < size(); i++) {
/*     */       
/* 374 */       K key = (K)stream.readObject();
/*     */       
/* 376 */       V value = (V)stream.readObject();
/* 377 */       this.linkedEntries.add(Maps.immutableEntry(key, value));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\LinkedHashMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */