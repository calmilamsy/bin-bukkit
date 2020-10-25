/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class SingletonImmutableMap<K, V>
/*     */   extends ImmutableMap<K, V>
/*     */ {
/*     */   final K singleKey;
/*     */   final V singleValue;
/*     */   private Map.Entry<K, V> entry;
/*     */   private ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private ImmutableSet<K> keySet;
/*     */   private ImmutableCollection<V> values;
/*     */   
/*     */   SingletonImmutableMap(K singleKey, V singleValue) {
/*  41 */     this.singleKey = singleKey;
/*  42 */     this.singleValue = singleValue;
/*     */   }
/*     */   
/*     */   SingletonImmutableMap(Map.Entry<K, V> entry) {
/*  46 */     this.entry = entry;
/*  47 */     this.singleKey = entry.getKey();
/*  48 */     this.singleValue = entry.getValue();
/*     */   }
/*     */   
/*     */   private Map.Entry<K, V> entry() {
/*  52 */     Map.Entry<K, V> e = this.entry;
/*  53 */     return (e == null) ? (this.entry = Maps.immutableEntry(this.singleKey, this.singleValue)) : e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public V get(Object key) { return (V)(this.singleKey.equals(key) ? this.singleValue : null); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int size() { return 1; }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public boolean containsKey(Object key) { return this.singleKey.equals(key); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public boolean containsValue(Object value) { return this.singleValue.equals(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/*  80 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/*  81 */     return (es == null) ? (this.entrySet = ImmutableSet.of(entry())) : es;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<K> keySet() {
/*  87 */     ImmutableSet<K> ks = this.keySet;
/*  88 */     return (ks == null) ? (this.keySet = ImmutableSet.of(this.singleKey)) : ks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/*  94 */     ImmutableCollection<V> v = this.values;
/*  95 */     return (v == null) ? (this.values = new Values(this.singleValue)) : v;
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V>
/*     */   {
/*     */     final V singleValue;
/*     */     
/* 103 */     Values(V singleValue) { this.singleValue = singleValue; }
/*     */ 
/*     */ 
/*     */     
/* 107 */     public boolean contains(Object object) { return this.singleValue.equals(object); }
/*     */ 
/*     */ 
/*     */     
/* 111 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */     
/* 115 */     public int size() { return 1; }
/*     */ 
/*     */ 
/*     */     
/* 119 */     public UnmodifiableIterator<V> iterator() { return Iterators.singletonIterator(this.singleValue); }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 124 */     if (object == this) {
/* 125 */       return true;
/*     */     }
/* 127 */     if (object instanceof Map) {
/* 128 */       Map<?, ?> that = (Map)object;
/* 129 */       if (that.size() != 1) {
/* 130 */         return false;
/*     */       }
/* 132 */       Map.Entry<?, ?> entry = (Map.Entry)that.entrySet().iterator().next();
/* 133 */       return (this.singleKey.equals(entry.getKey()) && this.singleValue.equals(entry.getValue()));
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 140 */   public int hashCode() { return this.singleKey.hashCode() ^ this.singleValue.hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public String toString() { return '{' + this.singleKey.toString() + '=' + this.singleValue.toString() + '}'; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\SingletonImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */