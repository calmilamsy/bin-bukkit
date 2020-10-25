/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible
/*     */ abstract class AbstractBiMap<K, V>
/*     */   extends ForwardingMap<K, V>
/*     */   implements BiMap<K, V>, Serializable
/*     */ {
/*     */   private Map<K, V> delegate;
/*     */   private AbstractBiMap<V, K> inverse;
/*     */   private Set<K> keySet;
/*     */   private Set<V> valueSet;
/*     */   private Set<Map.Entry<K, V>> entrySet;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  54 */   AbstractBiMap(Map<K, V> forward, Map<V, K> backward) { setDelegates(forward, backward); }
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractBiMap(Map<K, V> backward, AbstractBiMap<V, K> forward) {
/*  59 */     this.delegate = backward;
/*  60 */     this.inverse = forward;
/*     */   }
/*     */ 
/*     */   
/*  64 */   protected Map<K, V> delegate() { return this.delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDelegates(Map<K, V> forward, Map<V, K> backward) {
/*  72 */     Preconditions.checkState((this.delegate == null));
/*  73 */     Preconditions.checkState((this.inverse == null));
/*  74 */     Preconditions.checkArgument(forward.isEmpty());
/*  75 */     Preconditions.checkArgument(backward.isEmpty());
/*  76 */     Preconditions.checkArgument((forward != backward));
/*  77 */     this.delegate = forward;
/*  78 */     this.inverse = new Inverse(backward, this, null);
/*     */   }
/*     */ 
/*     */   
/*  82 */   void setInverse(AbstractBiMap<V, K> inverse) { this.inverse = inverse; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean containsValue(Object value) { return this.inverse.containsKey(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public V put(K key, V value) { return (V)putInBothMaps(key, value, false); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public V forcePut(K key, V value) { return (V)putInBothMaps(key, value, true); }
/*     */ 
/*     */   
/*     */   private V putInBothMaps(@Nullable K key, @Nullable V value, boolean force) {
/* 102 */     boolean containedKey = containsKey(key);
/* 103 */     if (containedKey && Objects.equal(value, get(key))) {
/* 104 */       return value;
/*     */     }
/* 106 */     if (force) {
/* 107 */       inverse().remove(value);
/*     */     } else {
/* 109 */       Preconditions.checkArgument(!containsValue(value), "value already present: %s", new Object[] { value });
/*     */     } 
/* 111 */     V oldValue = (V)this.delegate.put(key, value);
/* 112 */     updateInverseMap(key, containedKey, oldValue, value);
/* 113 */     return oldValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateInverseMap(K key, boolean containedKey, V oldValue, V newValue) {
/* 118 */     if (containedKey) {
/* 119 */       removeFromInverseMap(oldValue);
/*     */     }
/* 121 */     this.inverse.delegate.put(newValue, key);
/*     */   }
/*     */ 
/*     */   
/* 125 */   public V remove(Object key) { return (V)(containsKey(key) ? removeFromBothMaps(key) : null); }
/*     */ 
/*     */   
/*     */   private V removeFromBothMaps(Object key) {
/* 129 */     V oldValue = (V)this.delegate.remove(key);
/* 130 */     removeFromInverseMap(oldValue);
/* 131 */     return oldValue;
/*     */   }
/*     */ 
/*     */   
/* 135 */   private void removeFromInverseMap(V oldValue) { this.inverse.delegate.remove(oldValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> map) {
/* 141 */     for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 142 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 147 */     this.delegate.clear();
/* 148 */     this.inverse.delegate.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public BiMap<V, K> inverse() { return this.inverse; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 160 */     Set<K> result = this.keySet;
/* 161 */     return (result == null) ? (this.keySet = new KeySet(null)) : result;
/*     */   }
/*     */   
/*     */   private class KeySet
/*     */     extends ForwardingSet<K> {
/* 166 */     protected Set<K> delegate() { return AbstractBiMap.this.delegate.keySet(); }
/*     */     
/*     */     private KeySet() {}
/*     */     
/* 170 */     public void clear() { AbstractBiMap.this.clear(); }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key) {
/* 174 */       if (!contains(key)) {
/* 175 */         return false;
/*     */       }
/* 177 */       AbstractBiMap.this.removeFromBothMaps(key);
/* 178 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 182 */     public boolean removeAll(Collection<?> keysToRemove) { return Iterators.removeAll(iterator(), keysToRemove); }
/*     */ 
/*     */ 
/*     */     
/* 186 */     public boolean retainAll(Collection<?> keysToRetain) { return Iterators.retainAll(iterator(), keysToRetain); }
/*     */ 
/*     */     
/*     */     public Iterator<K> iterator() {
/* 190 */       final Iterator<Map.Entry<K, V>> iterator = AbstractBiMap.this.delegate.entrySet().iterator();
/* 191 */       return new Iterator<K>()
/*     */         {
/*     */           Map.Entry<K, V> entry;
/*     */           
/* 195 */           public boolean hasNext() { return iterator.hasNext(); }
/*     */           
/*     */           public K next() {
/* 198 */             super.entry = (Map.Entry)iterator.next();
/* 199 */             return (K)super.entry.getKey();
/*     */           }
/*     */           public void remove() {
/* 202 */             Preconditions.checkState((super.entry != null));
/* 203 */             V value = (V)super.entry.getValue();
/* 204 */             iterator.remove();
/* 205 */             AbstractBiMap.KeySet.this.this$0.removeFromInverseMap(value);
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<V> values() {
/* 218 */     Set<V> result = this.valueSet;
/* 219 */     return (result == null) ? (this.valueSet = new ValueSet(null)) : result;
/*     */   }
/*     */   
/*     */   private class ValueSet extends ForwardingSet<V> {
/* 223 */     final Set<V> valuesDelegate = AbstractBiMap.this.inverse.keySet();
/*     */ 
/*     */     
/* 226 */     protected Set<V> delegate() { return this.valuesDelegate; }
/*     */ 
/*     */     
/*     */     public Iterator<V> iterator() {
/* 230 */       final Iterator<V> iterator = AbstractBiMap.this.delegate.values().iterator();
/* 231 */       return new Iterator<V>()
/*     */         {
/*     */           V valueToRemove;
/*     */           
/* 235 */           public boolean hasNext() { return iterator.hasNext(); }
/*     */ 
/*     */ 
/*     */           
/* 239 */           public V next() { return (V)(super.valueToRemove = iterator.next()); }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 243 */             iterator.remove();
/* 244 */             AbstractBiMap.ValueSet.this.this$0.removeFromInverseMap(super.valueToRemove);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 250 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*     */ 
/*     */ 
/*     */     
/* 254 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*     */ 
/*     */ 
/*     */     
/* 258 */     public String toString() { return Iterators.toString(iterator()); }
/*     */ 
/*     */     
/*     */     private ValueSet() {}
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 265 */     Set<Map.Entry<K, V>> result = this.entrySet;
/* 266 */     return (result == null) ? (this.entrySet = new EntrySet(null)) : result;
/*     */   }
/*     */   
/*     */   private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
/* 270 */     final Set<Map.Entry<K, V>> esDelegate = AbstractBiMap.this.delegate.entrySet();
/*     */ 
/*     */     
/* 273 */     protected Set<Map.Entry<K, V>> delegate() { return this.esDelegate; }
/*     */ 
/*     */ 
/*     */     
/* 277 */     public void clear() { AbstractBiMap.this.clear(); }
/*     */ 
/*     */     
/*     */     public boolean remove(Object object) {
/* 281 */       if (!this.esDelegate.remove(object)) {
/* 282 */         return false;
/*     */       }
/* 284 */       Map.Entry<?, ?> entry = (Map.Entry)object;
/* 285 */       AbstractBiMap.this.inverse.delegate.remove(entry.getValue());
/* 286 */       return true;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 290 */       final Iterator<Map.Entry<K, V>> iterator = this.esDelegate.iterator();
/* 291 */       return new Iterator<Map.Entry<K, V>>()
/*     */         {
/*     */           Map.Entry<K, V> entry;
/*     */           
/* 295 */           public boolean hasNext() { return iterator.hasNext(); }
/*     */ 
/*     */           
/*     */           public Map.Entry<K, V> next() {
/* 299 */             super.entry = (Map.Entry)iterator.next();
/* 300 */             final Map.Entry<K, V> finalEntry = super.entry;
/*     */             
/* 302 */             return new ForwardingMapEntry<K, V>()
/*     */               {
/* 304 */                 protected Map.Entry<K, V> delegate() { return finalEntry; }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public V setValue(V value) {
/* 309 */                   Preconditions.checkState(AbstractBiMap.EntrySet.null.this.this$1.contains(this), "entry no longer in map");
/*     */                   
/* 311 */                   if (Objects.equal(value, getValue())) {
/* 312 */                     return value;
/*     */                   }
/* 314 */                   Preconditions.checkArgument(!AbstractBiMap.EntrySet.this.this$0.containsValue(value), "value already present: %s", new Object[] { value });
/*     */                   
/* 316 */                   V oldValue = (V)finalEntry.setValue(value);
/* 317 */                   Preconditions.checkState(Objects.equal(value, AbstractBiMap.EntrySet.this.this$0.get(getKey())), "entry no longer in map");
/*     */                   
/* 319 */                   AbstractBiMap.EntrySet.this.this$0.updateInverseMap(getKey(), true, oldValue, value);
/* 320 */                   return oldValue;
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public void remove() {
/* 326 */             Preconditions.checkState((super.entry != null));
/* 327 */             V value = (V)super.entry.getValue();
/* 328 */             iterator.remove();
/* 329 */             AbstractBiMap.EntrySet.this.this$0.removeFromInverseMap(value);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     public Object[] toArray() { return ObjectArrays.toArrayImpl(this); }
/*     */ 
/*     */     
/* 340 */     public <T> T[] toArray(T[] array) { return (T[])ObjectArrays.toArrayImpl(this, array); }
/*     */ 
/*     */     
/* 343 */     public boolean contains(Object o) { return Maps.containsEntryImpl(delegate(), o); }
/*     */ 
/*     */     
/* 346 */     public boolean containsAll(Collection<?> c) { return Collections2.containsAll(this, c); }
/*     */ 
/*     */     
/* 349 */     public boolean removeAll(Collection<?> c) { return Iterators.removeAll(iterator(), c); }
/*     */ 
/*     */     
/* 352 */     public boolean retainAll(Collection<?> c) { return Iterators.retainAll(iterator(), c); }
/*     */     
/*     */     private EntrySet() {} }
/*     */   
/*     */   private static class Inverse<K, V> extends AbstractBiMap<K, V> {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 359 */     private Inverse(Map<K, V> backward, AbstractBiMap<V, K> forward) { super(backward, forward, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void writeObject(ObjectOutputStream stream) throws IOException {
/* 375 */       stream.defaultWriteObject();
/* 376 */       stream.writeObject(inverse());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 382 */       stream.defaultReadObject();
/* 383 */       setInverse((AbstractBiMap)stream.readObject());
/*     */     }
/*     */ 
/*     */     
/* 387 */     Object readResolve() { return inverse().inverse(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\AbstractBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */