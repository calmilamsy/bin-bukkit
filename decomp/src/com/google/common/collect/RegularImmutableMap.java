/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ final class RegularImmutableMap<K, V>
/*     */   extends ImmutableMap<K, V>
/*     */ {
/*     */   private final Map.Entry<K, V>[] entries;
/*     */   private final Object[] table;
/*     */   private final int mask;
/*     */   private final int keySetHashCode;
/*     */   private ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private ImmutableSet<K> keySet;
/*     */   private ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   RegularImmutableMap(Entry... immutableEntries) {
/*  41 */     Entry[] tmp = (Entry[])immutableEntries;
/*  42 */     this.entries = tmp;
/*     */     
/*  44 */     int tableSize = Hashing.chooseTableSize(immutableEntries.length);
/*  45 */     this.table = new Object[tableSize * 2];
/*  46 */     this.mask = tableSize - 1;
/*     */     
/*  48 */     int keySetHashCodeMutable = 0;
/*  49 */     for (Map.Entry<K, V> entry : this.entries) {
/*  50 */       K key = (K)entry.getKey();
/*  51 */       int keyHashCode = key.hashCode();
/*  52 */       for (int i = Hashing.smear(keyHashCode);; i++) {
/*  53 */         int index = (i & this.mask) * 2;
/*  54 */         Object existing = this.table[index];
/*  55 */         if (existing == null) {
/*  56 */           V value = (V)entry.getValue();
/*  57 */           this.table[index] = key;
/*  58 */           this.table[index + 1] = value;
/*  59 */           keySetHashCodeMutable += keyHashCode; break;
/*     */         } 
/*  61 */         if (existing.equals(key)) {
/*  62 */           throw new IllegalArgumentException("duplicate key: " + key);
/*     */         }
/*     */       } 
/*     */     } 
/*  66 */     this.keySetHashCode = keySetHashCodeMutable;
/*     */   }
/*     */   
/*     */   public V get(Object key) {
/*  70 */     if (key == null) {
/*  71 */       return null;
/*     */     }
/*  73 */     for (int i = Hashing.smear(key.hashCode());; i++) {
/*  74 */       int index = (i & this.mask) * 2;
/*  75 */       Object candidate = this.table[index];
/*  76 */       if (candidate == null) {
/*  77 */         return null;
/*     */       }
/*  79 */       if (candidate.equals(key))
/*     */       {
/*     */         
/*  82 */         return (V)this.table[index + 1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public int size() { return this.entries.length; }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  97 */     if (value == null) {
/*  98 */       return false;
/*     */     }
/* 100 */     for (Map.Entry<K, V> entry : this.entries) {
/* 101 */       if (entry.getValue().equals(value)) {
/* 102 */         return true;
/*     */       }
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/* 116 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/* 117 */     return (es == null) ? (this.entrySet = new EntrySet(this)) : es;
/*     */   }
/*     */   
/*     */   private static class EntrySet<K, V>
/*     */     extends ImmutableSet.ArrayImmutableSet<Map.Entry<K, V>> {
/*     */     final RegularImmutableMap<K, V> map;
/*     */     
/*     */     EntrySet(RegularImmutableMap<K, V> map) {
/* 125 */       super(map.entries);
/* 126 */       this.map = map;
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 130 */       if (target instanceof Map.Entry) {
/* 131 */         Map.Entry<?, ?> entry = (Map.Entry)target;
/* 132 */         V mappedValue = (V)this.map.get(entry.getKey());
/* 133 */         return (mappedValue != null && mappedValue.equals(entry.getValue()));
/*     */       } 
/* 135 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<K> keySet() {
/* 142 */     ImmutableSet<K> ks = this.keySet;
/* 143 */     return (ks == null) ? (this.keySet = new KeySet(this)) : ks;
/*     */   }
/*     */   
/*     */   private static class KeySet<K, V>
/*     */     extends ImmutableSet.TransformedImmutableSet<Map.Entry<K, V>, K>
/*     */   {
/*     */     final RegularImmutableMap<K, V> map;
/*     */     
/*     */     KeySet(RegularImmutableMap<K, V> map) {
/* 152 */       super(map.entries, map.keySetHashCode);
/* 153 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/* 157 */     K transform(Map.Entry<K, V> element) { return (K)element.getKey(); }
/*     */ 
/*     */ 
/*     */     
/* 161 */     public boolean contains(Object target) { return this.map.containsKey(target); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/* 168 */     ImmutableCollection<V> v = this.values;
/* 169 */     return (v == null) ? (this.values = new Values(this)) : v;
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V>
/*     */   {
/*     */     final RegularImmutableMap<?, V> map;
/*     */     
/* 177 */     Values(RegularImmutableMap<?, V> map) { this.map = map; }
/*     */ 
/*     */ 
/*     */     
/* 181 */     public int size() { return this.map.entries.length; }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 185 */       return new AbstractIterator<V>() {
/*     */           int index;
/*     */           
/* 188 */           protected V computeNext() { return (V)((super.index < RegularImmutableMap.Values.this.map.entries.length) ? RegularImmutableMap.Values.this.map.entries[super.index++].getValue() : endOfData()); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     public boolean contains(Object target) { return this.map.containsValue(target); }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 201 */     StringBuilder result = (new StringBuilder(size() * 16)).append('{');
/* 202 */     Collections2.standardJoiner.appendTo(result, this.entries);
/* 203 */     return result.append('}').toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\RegularImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */