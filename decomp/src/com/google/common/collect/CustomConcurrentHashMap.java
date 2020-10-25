/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*      */ import java.util.concurrent.locks.ReentrantLock;
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
/*      */ final class CustomConcurrentHashMap
/*      */ {
/*      */   static final class Builder
/*      */   {
/*      */     private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */     private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */     private static final int UNSET_INITIAL_CAPACITY = -1;
/*      */     private static final int UNSET_CONCURRENCY_LEVEL = -1;
/*  145 */     int initialCapacity = -1;
/*  146 */     int concurrencyLevel = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder initialCapacity(int initialCapacity) {
/*  157 */       if (this.initialCapacity != -1) {
/*  158 */         throw new IllegalStateException("initial capacity was already set to " + this.initialCapacity);
/*      */       }
/*      */       
/*  161 */       if (initialCapacity < 0) {
/*  162 */         throw new IllegalArgumentException();
/*      */       }
/*  164 */       this.initialCapacity = initialCapacity;
/*  165 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder concurrencyLevel(int concurrencyLevel) {
/*  185 */       if (this.concurrencyLevel != -1) {
/*  186 */         throw new IllegalStateException("concurrency level was already set to " + this.concurrencyLevel);
/*      */       }
/*      */       
/*  189 */       if (concurrencyLevel <= 0) {
/*  190 */         throw new IllegalArgumentException();
/*      */       }
/*  192 */       this.concurrencyLevel = concurrencyLevel;
/*  193 */       return this;
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
/*      */ 
/*      */ 
/*      */     
/*      */     public <K, V, E> ConcurrentMap<K, V> buildMap(CustomConcurrentHashMap.Strategy<K, V, E> strategy) {
/*  208 */       if (strategy == null) {
/*  209 */         throw new NullPointerException("strategy");
/*      */       }
/*  211 */       return new CustomConcurrentHashMap.Impl(strategy, this);
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
/*      */     public <K, V, E> ConcurrentMap<K, V> buildComputingMap(CustomConcurrentHashMap.ComputingStrategy<K, V, E> strategy, Function<? super K, ? extends V> computer) {
/*  254 */       if (strategy == null) {
/*  255 */         throw new NullPointerException("strategy");
/*      */       }
/*  257 */       if (computer == null) {
/*  258 */         throw new NullPointerException("computer");
/*      */       }
/*      */       
/*  261 */       return new CustomConcurrentHashMap.ComputingImpl(strategy, this, computer);
/*      */     }
/*      */ 
/*      */     
/*  265 */     int getInitialCapacity() { return (this.initialCapacity == -1) ? 16 : this.initialCapacity; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     int getConcurrencyLevel() { return (this.concurrencyLevel == -1) ? 16 : this.concurrencyLevel; }
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
/*      */   private static int rehash(int h) {
/*  527 */     h += (h << 15 ^ 0xFFFFCD7D);
/*  528 */     h ^= h >>> 10;
/*  529 */     h += (h << 3);
/*  530 */     h ^= h >>> 6;
/*  531 */     h += (h << 2) + (h << 14);
/*  532 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */   
/*      */   public static interface Strategy<K, V, E>
/*      */   {
/*      */     E newEntry(K param1K, int param1Int, E param1E);
/*      */ 
/*      */     
/*      */     E copyEntry(K param1K, E param1E1, E param1E2);
/*      */     
/*      */     void setValue(E param1E, V param1V);
/*      */     
/*      */     V getValue(E param1E) throws InterruptedException;
/*      */     
/*      */     boolean equalKeys(K param1K, Object param1Object);
/*      */     
/*      */     boolean equalValues(V param1V, Object param1Object);
/*      */     
/*      */     int hashKey(Object param1Object);
/*      */     
/*      */     K getKey(E param1E);
/*      */     
/*      */     E getNext(E param1E);
/*      */     
/*      */     int getHash(E param1E);
/*      */     
/*      */     void setInternals(CustomConcurrentHashMap.Internals<K, V, E> param1Internals);
/*      */   }
/*      */   
/*      */   public static interface Internals<K, V, E>
/*      */   {
/*      */     E getEntry(K param1K);
/*      */     
/*      */     boolean removeEntry(E param1E, @Nullable V param1V);
/*      */     
/*      */     boolean removeEntry(E param1E);
/*      */   }
/*      */   
/*      */   public static interface ComputingStrategy<K, V, E>
/*      */     extends Strategy<K, V, E>
/*      */   {
/*      */     V compute(K param1K, E param1E, Function<? super K, ? extends V> param1Function);
/*      */     
/*      */     V waitForValue(E param1E) throws InterruptedException;
/*      */   }
/*      */   
/*      */   static class Impl<K, V, E>
/*      */     extends AbstractMap<K, V>
/*      */     implements ConcurrentMap<K, V>, Serializable
/*      */   {
/*      */     static final int MAXIMUM_CAPACITY = 1073741824;
/*      */     static final int MAX_SEGMENTS = 65536;
/*      */     static final int RETRIES_BEFORE_LOCK = 2;
/*      */     final CustomConcurrentHashMap.Strategy<K, V, E> strategy;
/*      */     final int segmentMask;
/*      */     final int segmentShift;
/*      */     final Segment[] segments;
/*      */     Set<K> keySet;
/*      */     Collection<V> values;
/*      */     Set<Map.Entry<K, V>> entrySet;
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     Impl(CustomConcurrentHashMap.Strategy<K, V, E> strategy, CustomConcurrentHashMap.Builder builder) {
/*  596 */       int concurrencyLevel = builder.getConcurrencyLevel();
/*  597 */       int initialCapacity = builder.getInitialCapacity();
/*      */       
/*  599 */       if (concurrencyLevel > 65536) {
/*  600 */         concurrencyLevel = 65536;
/*      */       }
/*      */ 
/*      */       
/*  604 */       int segmentShift = 0;
/*  605 */       int segmentCount = 1;
/*  606 */       while (segmentCount < concurrencyLevel) {
/*  607 */         segmentShift++;
/*  608 */         segmentCount <<= 1;
/*      */       } 
/*  610 */       this.segmentShift = 32 - segmentShift;
/*  611 */       this.segmentMask = segmentCount - 1;
/*  612 */       this.segments = newSegmentArray(segmentCount);
/*      */       
/*  614 */       if (initialCapacity > 1073741824) {
/*  615 */         initialCapacity = 1073741824;
/*      */       }
/*      */       
/*  618 */       int segmentCapacity = initialCapacity / segmentCount;
/*  619 */       if (segmentCapacity * segmentCount < initialCapacity) {
/*  620 */         segmentCapacity++;
/*      */       }
/*      */       
/*  623 */       int segmentSize = 1;
/*  624 */       while (segmentSize < segmentCapacity) {
/*  625 */         segmentSize <<= 1;
/*      */       }
/*  627 */       for (int i = 0; i < this.segments.length; i++) {
/*  628 */         this.segments[i] = new Segment(segmentSize);
/*      */       }
/*      */       
/*  631 */       this.strategy = strategy;
/*      */       
/*  633 */       strategy.setInternals(new InternalsImpl());
/*      */     }
/*      */     
/*      */     int hash(Object key) {
/*  637 */       int h = this.strategy.hashKey(key);
/*  638 */       return CustomConcurrentHashMap.rehash(h);
/*      */     }
/*      */     
/*      */     class InternalsImpl
/*      */       extends Object implements CustomConcurrentHashMap.Internals<K, V, E>, Serializable {
/*      */       static final long serialVersionUID = 0L;
/*      */       
/*      */       public E getEntry(K key) {
/*  646 */         if (key == null) {
/*  647 */           throw new NullPointerException("key");
/*      */         }
/*  649 */         int hash = CustomConcurrentHashMap.Impl.this.hash(key);
/*  650 */         return (E)CustomConcurrentHashMap.Impl.this.segmentFor(hash).getEntry(key, hash);
/*      */       }
/*      */       
/*      */       public boolean removeEntry(E entry, V value) {
/*  654 */         if (entry == null) {
/*  655 */           throw new NullPointerException("entry");
/*      */         }
/*  657 */         int hash = CustomConcurrentHashMap.Impl.this.strategy.getHash(entry);
/*  658 */         return CustomConcurrentHashMap.Impl.this.segmentFor(hash).removeEntry(entry, hash, value);
/*      */       }
/*      */       
/*      */       public boolean removeEntry(E entry) {
/*  662 */         if (entry == null) {
/*  663 */           throw new NullPointerException("entry");
/*      */         }
/*  665 */         int hash = CustomConcurrentHashMap.Impl.this.strategy.getHash(entry);
/*  666 */         return CustomConcurrentHashMap.Impl.this.segmentFor(hash).removeEntry(entry, hash);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  677 */     Segment[] newSegmentArray(int ssize) { return (Segment[])Array.newInstance(Segment.class, ssize); }
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
/*  689 */     Segment segmentFor(int hash) { return this.segments[hash >>> this.segmentShift & this.segmentMask]; }
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
/*      */     final class Segment
/*      */       extends ReentrantLock
/*      */     {
/*      */       int modCount;
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
/*      */       int threshold;
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
/*  765 */       Segment(int initialCapacity) { setTable(newEntryArray(initialCapacity)); }
/*      */ 
/*      */ 
/*      */       
/*  769 */       AtomicReferenceArray<E> newEntryArray(int size) { return new AtomicReferenceArray(size); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void setTable(AtomicReferenceArray<E> newTable) {
/*  777 */         this.threshold = newTable.length() * 3 / 4;
/*  778 */         this.table = newTable;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       E getFirst(int hash) {
/*  785 */         AtomicReferenceArray<E> table = this.table;
/*  786 */         return (E)table.get(hash & table.length() - 1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public E getEntry(Object key, int hash) {
/*  792 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  793 */         if (this.count != 0)
/*  794 */           for (E e = (E)getFirst(hash); e != null; e = (E)s.getNext(e)) {
/*  795 */             if (s.getHash(e) == hash) {
/*      */ 
/*      */ 
/*      */               
/*  799 */               K entryKey = (K)s.getKey(e);
/*  800 */               if (entryKey != null)
/*      */               {
/*      */ 
/*      */                 
/*  804 */                 if (s.equalKeys(entryKey, key)) {
/*  805 */                   return e;
/*      */                 }
/*      */               }
/*      */             } 
/*      */           }  
/*  810 */         return null;
/*      */       }
/*      */       
/*      */       V get(Object key, int hash) {
/*  814 */         E entry = (E)getEntry(key, hash);
/*  815 */         if (entry == null) {
/*  816 */           return null;
/*      */         }
/*      */         
/*  819 */         return (V)CustomConcurrentHashMap.Impl.this.strategy.getValue(entry);
/*      */       }
/*      */       
/*      */       boolean containsKey(Object key, int hash) {
/*  823 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  824 */         if (this.count != 0)
/*  825 */           for (E e = (E)getFirst(hash); e != null; e = (E)s.getNext(e)) {
/*  826 */             if (s.getHash(e) == hash) {
/*      */ 
/*      */ 
/*      */               
/*  830 */               K entryKey = (K)s.getKey(e);
/*  831 */               if (entryKey != null)
/*      */               {
/*      */ 
/*      */                 
/*  835 */                 if (s.equalKeys(entryKey, key))
/*      */                 {
/*  837 */                   return (s.getValue(e) != null);
/*      */                 }
/*      */               }
/*      */             } 
/*      */           }  
/*  842 */         return false;
/*      */       }
/*      */       
/*      */       boolean containsValue(Object value) {
/*  846 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  847 */         if (this.count != 0) {
/*  848 */           AtomicReferenceArray<E> table = this.table;
/*  849 */           int length = table.length();
/*  850 */           for (int i = 0; i < length; i++) {
/*  851 */             for (E e = (E)table.get(i); e != null; e = (E)s.getNext(e)) {
/*  852 */               V entryValue = (V)s.getValue(e);
/*      */ 
/*      */ 
/*      */               
/*  856 */               if (entryValue != null)
/*      */               {
/*      */ 
/*      */                 
/*  860 */                 if (s.equalValues(entryValue, value)) {
/*  861 */                   return true;
/*      */                 }
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  867 */         return false;
/*      */       }
/*      */       
/*      */       boolean replace(K key, int hash, V oldValue, V newValue) {
/*  871 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  872 */         lock();
/*      */         try {
/*  874 */           for (e = (E)getFirst(hash); e != null; e = (E)s.getNext(e)) {
/*  875 */             K entryKey = (K)s.getKey(e);
/*  876 */             if (s.getHash(e) == hash && entryKey != null && s.equalKeys(key, entryKey)) {
/*      */ 
/*      */ 
/*      */               
/*  880 */               V entryValue = (V)s.getValue(e);
/*  881 */               if (entryValue == null) {
/*  882 */                 return false;
/*      */               }
/*      */               
/*  885 */               if (s.equalValues(entryValue, oldValue)) {
/*  886 */                 s.setValue(e, newValue);
/*  887 */                 return true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  892 */           return false;
/*      */         } finally {
/*  894 */           unlock();
/*      */         } 
/*      */       }
/*      */       
/*      */       V replace(K key, int hash, V newValue) {
/*  899 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  900 */         lock();
/*      */         
/*  902 */         try { for (e = (E)getFirst(hash); e != null; e = (E)s.getNext(e))
/*  903 */           { K entryKey = (K)s.getKey(e);
/*  904 */             if (s.getHash(e) == hash && entryKey != null && s.equalKeys(key, entryKey))
/*      */             
/*      */             { 
/*      */               
/*  908 */               V entryValue = (V)s.getValue(e);
/*  909 */               if (entryValue == null)
/*  910 */               { object = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  920 */                 return (V)object; }  s.setValue(e, newValue); return entryValue; }  }  e = null; return (V)e; } finally { unlock(); }
/*      */       
/*      */       }
/*      */       
/*      */       V put(K key, int hash, V value, boolean onlyIfAbsent) {
/*  925 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  926 */         lock();
/*      */         
/*  928 */         try { int count = this.count;
/*  929 */           if (count++ > this.threshold) {
/*  930 */             expand();
/*      */           }
/*      */           
/*  933 */           AtomicReferenceArray<E> table = this.table;
/*  934 */           int index = hash & table.length() - 1;
/*      */           
/*  936 */           E first = (E)table.get(index);
/*      */ 
/*      */           
/*  939 */           for (E e = first; e != null; e = (E)s.getNext(e)) {
/*  940 */             K entryKey = (K)s.getKey(e);
/*  941 */             if (s.getHash(e) == hash && entryKey != null && s.equalKeys(key, entryKey)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  947 */               V entryValue = (V)s.getValue(e);
/*  948 */               if (onlyIfAbsent && entryValue != null) {
/*  949 */                 return entryValue;
/*      */               }
/*      */               
/*  952 */               s.setValue(e, value);
/*  953 */               return entryValue;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  958 */           this.modCount++;
/*  959 */           E newEntry = (E)s.newEntry(key, hash, first);
/*  960 */           s.setValue(newEntry, value);
/*  961 */           table.set(index, newEntry);
/*  962 */           this.count = count;
/*  963 */           object = null;
/*      */           
/*  965 */           return (V)object; } finally { unlock(); }
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void expand() {
/*  973 */         AtomicReferenceArray<E> oldTable = this.table;
/*  974 */         int oldCapacity = oldTable.length();
/*  975 */         if (oldCapacity >= 1073741824) {
/*      */           return;
/*      */         }
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
/*  993 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/*  994 */         AtomicReferenceArray<E> newTable = newEntryArray(oldCapacity << 1);
/*  995 */         this.threshold = newTable.length() * 3 / 4;
/*  996 */         int newMask = newTable.length() - 1;
/*  997 */         for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
/*      */ 
/*      */           
/* 1000 */           E head = (E)oldTable.get(oldIndex);
/*      */           
/* 1002 */           if (head != null) {
/* 1003 */             E next = (E)s.getNext(head);
/* 1004 */             int headIndex = s.getHash(head) & newMask;
/*      */ 
/*      */             
/* 1007 */             if (next == null) {
/* 1008 */               newTable.set(headIndex, head);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1013 */               E tail = head;
/* 1014 */               int tailIndex = headIndex;
/* 1015 */               for (last = next; last != null; last = (E)s.getNext(last)) {
/* 1016 */                 int newIndex = s.getHash(last) & newMask;
/* 1017 */                 if (newIndex != tailIndex) {
/*      */                   
/* 1019 */                   tailIndex = newIndex;
/* 1020 */                   tail = last;
/*      */                 } 
/*      */               } 
/* 1023 */               newTable.set(tailIndex, tail);
/*      */ 
/*      */               
/* 1026 */               for (E e = head; e != tail; e = (E)s.getNext(e)) {
/* 1027 */                 K key = (K)s.getKey(e);
/* 1028 */                 if (key != null) {
/* 1029 */                   int newIndex = s.getHash(e) & newMask;
/* 1030 */                   E newNext = (E)newTable.get(newIndex);
/* 1031 */                   newTable.set(newIndex, s.copyEntry(key, e, newNext));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1039 */         this.table = newTable;
/*      */       }
/*      */       
/*      */       V remove(Object key, int hash) {
/* 1043 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1044 */         lock();
/*      */         
/* 1046 */         try { int count = this.count - 1;
/* 1047 */           AtomicReferenceArray<E> table = this.table;
/* 1048 */           int index = hash & table.length() - 1;
/* 1049 */           E first = (E)table.get(index);
/*      */           
/* 1051 */           for (e = first; e != null; e = (E)s.getNext(e))
/* 1052 */           { K entryKey = (K)s.getKey(e);
/* 1053 */             if (s.getHash(e) == hash && entryKey != null && s.equalKeys(entryKey, key))
/*      */             
/* 1055 */             { V entryValue = (V)CustomConcurrentHashMap.Impl.this.strategy.getValue(e);
/*      */ 
/*      */ 
/*      */               
/* 1059 */               this.modCount++;
/* 1060 */               E newFirst = (E)s.getNext(e);
/* 1061 */               for (p = first; p != e; p = (E)s.getNext(p)) {
/* 1062 */                 K pKey = (K)s.getKey(p);
/* 1063 */                 if (pKey != null) {
/* 1064 */                   newFirst = (E)s.copyEntry(pKey, p, newFirst);
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/* 1069 */               table.set(index, newFirst);
/* 1070 */               this.count = count;
/* 1071 */               p = (E)entryValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1077 */               return (V)p; }  }  e = null; return (V)e; } finally { unlock(); }
/*      */       
/*      */       }
/*      */       
/*      */       boolean remove(Object key, int hash, Object value) {
/* 1082 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1083 */         lock();
/*      */         try {
/* 1085 */           int count = this.count - 1;
/* 1086 */           AtomicReferenceArray<E> table = this.table;
/* 1087 */           int index = hash & table.length() - 1;
/* 1088 */           E first = (E)table.get(index);
/*      */           
/* 1090 */           for (e = first; e != null; e = (E)s.getNext(e)) {
/* 1091 */             K entryKey = (K)s.getKey(e);
/* 1092 */             if (s.getHash(e) == hash && entryKey != null && s.equalKeys(entryKey, key)) {
/*      */               
/* 1094 */               V entryValue = (V)CustomConcurrentHashMap.Impl.this.strategy.getValue(e);
/* 1095 */               if (value == entryValue || (value != null && entryValue != null && s.equalValues(entryValue, value))) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1100 */                 this.modCount++;
/* 1101 */                 E newFirst = (E)s.getNext(e);
/* 1102 */                 for (p = first; p != e; p = (E)s.getNext(p)) {
/* 1103 */                   K pKey = (K)s.getKey(p);
/* 1104 */                   if (pKey != null) {
/* 1105 */                     newFirst = (E)s.copyEntry(pKey, p, newFirst);
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/* 1110 */                 table.set(index, newFirst);
/* 1111 */                 this.count = count;
/* 1112 */                 return true;
/*      */               } 
/* 1114 */               return false;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1119 */           return false;
/*      */         } finally {
/* 1121 */           unlock();
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean removeEntry(E entry, int hash, V value) {
/* 1126 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1127 */         lock();
/*      */         try {
/* 1129 */           int count = this.count - 1;
/* 1130 */           AtomicReferenceArray<E> table = this.table;
/* 1131 */           int index = hash & table.length() - 1;
/* 1132 */           E first = (E)table.get(index);
/*      */           
/* 1134 */           for (e = first; e != null; e = (E)s.getNext(e)) {
/* 1135 */             if (s.getHash(e) == hash && entry.equals(e)) {
/* 1136 */               V entryValue = (V)s.getValue(e);
/* 1137 */               if (entryValue == value || (value != null && s.equalValues(entryValue, value))) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1142 */                 this.modCount++;
/* 1143 */                 E newFirst = (E)s.getNext(e);
/* 1144 */                 for (p = first; p != e; p = (E)s.getNext(p)) {
/* 1145 */                   K pKey = (K)s.getKey(p);
/* 1146 */                   if (pKey != null) {
/* 1147 */                     newFirst = (E)s.copyEntry(pKey, p, newFirst);
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/* 1152 */                 table.set(index, newFirst);
/* 1153 */                 this.count = count;
/* 1154 */                 return true;
/*      */               } 
/* 1156 */               return false;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1161 */           return false;
/*      */         } finally {
/* 1163 */           unlock();
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean removeEntry(E entry, int hash) {
/* 1168 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1169 */         lock();
/*      */         try {
/* 1171 */           int count = this.count - 1;
/* 1172 */           AtomicReferenceArray<E> table = this.table;
/* 1173 */           int index = hash & table.length() - 1;
/* 1174 */           E first = (E)table.get(index);
/*      */           
/* 1176 */           for (e = first; e != null; e = (E)s.getNext(e)) {
/* 1177 */             if (s.getHash(e) == hash && entry.equals(e)) {
/*      */ 
/*      */ 
/*      */               
/* 1181 */               this.modCount++;
/* 1182 */               E newFirst = (E)s.getNext(e);
/* 1183 */               for (p = first; p != e; p = (E)s.getNext(p)) {
/* 1184 */                 K pKey = (K)s.getKey(p);
/* 1185 */                 if (pKey != null) {
/* 1186 */                   newFirst = (E)s.copyEntry(pKey, p, newFirst);
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/* 1191 */               table.set(index, newFirst);
/* 1192 */               this.count = count;
/* 1193 */               return true;
/*      */             } 
/*      */           } 
/*      */           
/* 1197 */           return false;
/*      */         } finally {
/* 1199 */           unlock();
/*      */         } 
/*      */       }
/*      */       
/*      */       void clear() {
/* 1204 */         if (this.count != 0) {
/* 1205 */           lock();
/*      */           try {
/* 1207 */             AtomicReferenceArray<E> table = this.table;
/* 1208 */             for (int i = 0; i < table.length(); i++) {
/* 1209 */               table.set(i, null);
/*      */             }
/* 1211 */             this.modCount++;
/* 1212 */             this.count = 0;
/*      */           } finally {
/* 1214 */             unlock();
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1228 */       Segment[] arrayOfSegment = this.segments;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1238 */       int[] mc = new int[arrayOfSegment.length];
/* 1239 */       int mcsum = 0;
/* 1240 */       for (int i = 0; i < arrayOfSegment.length; i++) {
/* 1241 */         if ((arrayOfSegment[i]).count != 0) {
/* 1242 */           return false;
/*      */         }
/* 1244 */         mc[i] = (arrayOfSegment[i]).modCount; mcsum += (arrayOfSegment[i]).modCount;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1250 */       if (mcsum != 0) {
/* 1251 */         for (int i = 0; i < arrayOfSegment.length; i++) {
/* 1252 */           if ((arrayOfSegment[i]).count != 0 || mc[i] != (arrayOfSegment[i]).modCount)
/*      */           {
/* 1254 */             return false;
/*      */           }
/*      */         } 
/*      */       }
/* 1258 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1269 */       Segment[] arrayOfSegment = this.segments;
/* 1270 */       long sum = 0L;
/* 1271 */       long check = 0L;
/* 1272 */       int[] mc = new int[arrayOfSegment.length];
/*      */ 
/*      */       
/* 1275 */       for (int k = 0; k < 2; k++) {
/* 1276 */         check = 0L;
/* 1277 */         sum = 0L;
/* 1278 */         int mcsum = 0;
/* 1279 */         for (int i = 0; i < arrayOfSegment.length; i++) {
/* 1280 */           sum += (arrayOfSegment[i]).count;
/* 1281 */           mc[i] = (arrayOfSegment[i]).modCount; mcsum += (arrayOfSegment[i]).modCount;
/*      */         } 
/* 1283 */         if (mcsum != 0) {
/* 1284 */           for (int i = 0; i < arrayOfSegment.length; i++) {
/* 1285 */             check += (arrayOfSegment[i]).count;
/* 1286 */             if (mc[i] != (arrayOfSegment[i]).modCount) {
/* 1287 */               check = -1L;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/* 1292 */         if (check == sum) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1296 */       if (check != sum) {
/* 1297 */         sum = 0L;
/* 1298 */         for (Segment segment : arrayOfSegment) {
/* 1299 */           segment.lock();
/*      */         }
/* 1301 */         for (Segment segment : arrayOfSegment) {
/* 1302 */           sum += segment.count;
/*      */         }
/* 1304 */         for (Segment segment : arrayOfSegment) {
/* 1305 */           segment.unlock();
/*      */         }
/*      */       } 
/* 1308 */       if (sum > 2147483647L) {
/* 1309 */         return Integer.MAX_VALUE;
/*      */       }
/* 1311 */       return (int)sum;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object key) {
/* 1327 */       if (key == null) {
/* 1328 */         throw new NullPointerException("key");
/*      */       }
/* 1330 */       int hash = hash(key);
/* 1331 */       return (V)segmentFor(hash).get(key, hash);
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
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1344 */       if (key == null) {
/* 1345 */         throw new NullPointerException("key");
/*      */       }
/* 1347 */       int hash = hash(key);
/* 1348 */       return segmentFor(hash).containsKey(key, hash);
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
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object value) {
/* 1362 */       if (value == null) {
/* 1363 */         throw new NullPointerException("value");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1368 */       arrayOfSegment = this.segments;
/* 1369 */       int[] mc = new int[arrayOfSegment.length];
/*      */ 
/*      */       
/* 1372 */       for (k = 0; k < 2; k++) {
/* 1373 */         int mcsum = 0;
/* 1374 */         for (int i = 0; i < arrayOfSegment.length; i++) {
/*      */           
/* 1376 */           int c = (arrayOfSegment[i]).count;
/* 1377 */           mc[i] = (arrayOfSegment[i]).modCount; mcsum += (arrayOfSegment[i]).modCount;
/* 1378 */           if (arrayOfSegment[i].containsValue(value)) {
/* 1379 */             return true;
/*      */           }
/*      */         } 
/* 1382 */         boolean cleanSweep = true;
/* 1383 */         if (mcsum != 0) {
/* 1384 */           for (int i = 0; i < arrayOfSegment.length; i++) {
/*      */             
/* 1386 */             int c = (arrayOfSegment[i]).count;
/* 1387 */             if (mc[i] != (arrayOfSegment[i]).modCount) {
/* 1388 */               cleanSweep = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/* 1393 */         if (cleanSweep) {
/* 1394 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 1398 */       for (Segment segment : arrayOfSegment) {
/* 1399 */         segment.lock();
/*      */       }
/* 1401 */       boolean found = false;
/*      */       try {
/* 1403 */         for (Segment segment : arrayOfSegment) {
/* 1404 */           if (segment.containsValue(value)) {
/* 1405 */             found = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1410 */         for (Segment segment : arrayOfSegment) {
/* 1411 */           segment.unlock();
/*      */         }
/*      */       } 
/* 1414 */       return found;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V put(K key, V value) {
/* 1431 */       if (key == null) {
/* 1432 */         throw new NullPointerException("key");
/*      */       }
/* 1434 */       if (value == null) {
/* 1435 */         throw new NullPointerException("value");
/*      */       }
/* 1437 */       int hash = hash(key);
/* 1438 */       return (V)segmentFor(hash).put(key, hash, value, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K key, V value) {
/* 1449 */       if (key == null) {
/* 1450 */         throw new NullPointerException("key");
/*      */       }
/* 1452 */       if (value == null) {
/* 1453 */         throw new NullPointerException("value");
/*      */       }
/* 1455 */       int hash = hash(key);
/* 1456 */       return (V)segmentFor(hash).put(key, hash, value, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> m) {
/* 1467 */       for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 1468 */         put(e.getKey(), e.getValue());
/*      */       }
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
/*      */     
/*      */     public V remove(Object key) {
/* 1482 */       if (key == null) {
/* 1483 */         throw new NullPointerException("key");
/*      */       }
/* 1485 */       int hash = hash(key);
/* 1486 */       return (V)segmentFor(hash).remove(key, hash);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object key, Object value) {
/* 1495 */       if (key == null) {
/* 1496 */         throw new NullPointerException("key");
/*      */       }
/* 1498 */       int hash = hash(key);
/* 1499 */       return segmentFor(hash).remove(key, hash, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean replace(K key, V oldValue, V newValue) {
/* 1508 */       if (key == null) {
/* 1509 */         throw new NullPointerException("key");
/*      */       }
/* 1511 */       if (oldValue == null) {
/* 1512 */         throw new NullPointerException("oldValue");
/*      */       }
/* 1514 */       if (newValue == null) {
/* 1515 */         throw new NullPointerException("newValue");
/*      */       }
/* 1517 */       int hash = hash(key);
/* 1518 */       return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V replace(K key, V value) {
/* 1529 */       if (key == null) {
/* 1530 */         throw new NullPointerException("key");
/*      */       }
/* 1532 */       if (value == null) {
/* 1533 */         throw new NullPointerException("value");
/*      */       }
/* 1535 */       int hash = hash(key);
/* 1536 */       return (V)segmentFor(hash).replace(key, hash, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1543 */       for (Segment segment : this.segments) {
/* 1544 */         segment.clear();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1566 */       Set<K> ks = this.keySet;
/* 1567 */       return (ks != null) ? ks : (this.keySet = new KeySet());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1588 */       Collection<V> vs = this.values;
/* 1589 */       return (vs != null) ? vs : (this.values = new Values());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1610 */       Set<Map.Entry<K, V>> es = this.entrySet;
/* 1611 */       return (es != null) ? es : (this.entrySet = new EntrySet());
/*      */     }
/*      */ 
/*      */     
/*      */     abstract class HashIterator
/*      */     {
/*      */       int nextSegmentIndex;
/*      */       
/*      */       int nextTableIndex;
/*      */       AtomicReferenceArray<E> currentTable;
/*      */       E nextEntry;
/*      */       CustomConcurrentHashMap.Impl<K, V, E>.WriteThroughEntry nextExternal;
/*      */       CustomConcurrentHashMap.Impl<K, V, E>.WriteThroughEntry lastReturned;
/*      */       
/*      */       HashIterator() {
/* 1626 */         this.nextSegmentIndex = CustomConcurrentHashMap.Impl.this.segments.length - 1;
/* 1627 */         this.nextTableIndex = -1;
/* 1628 */         advance();
/*      */       }
/*      */ 
/*      */       
/* 1632 */       public boolean hasMoreElements() { return hasNext(); }
/*      */ 
/*      */       
/*      */       final void advance() {
/* 1636 */         this.nextExternal = null;
/*      */         
/* 1638 */         if (nextInChain()) {
/*      */           return;
/*      */         }
/*      */         
/* 1642 */         if (nextInTable()) {
/*      */           return;
/*      */         }
/*      */         
/* 1646 */         while (this.nextSegmentIndex >= 0) {
/* 1647 */           CustomConcurrentHashMap.Impl<K, V, E>.Segment seg = CustomConcurrentHashMap.Impl.this.segments[this.nextSegmentIndex--];
/* 1648 */           if (seg.count != 0) {
/* 1649 */             this.currentTable = seg.table;
/* 1650 */             this.nextTableIndex = this.currentTable.length() - 1;
/* 1651 */             if (nextInTable()) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean nextInChain() {
/* 1663 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1664 */         if (this.nextEntry != null) {
/* 1665 */           for (this.nextEntry = s.getNext(this.nextEntry); this.nextEntry != null; 
/* 1666 */             this.nextEntry = s.getNext(this.nextEntry)) {
/* 1667 */             if (advanceTo(this.nextEntry)) {
/* 1668 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/* 1672 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean nextInTable() {
/* 1680 */         while (this.nextTableIndex >= 0) {
/* 1681 */           if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) != null && (
/* 1682 */             advanceTo(this.nextEntry) || nextInChain())) {
/* 1683 */             return true;
/*      */           }
/*      */         } 
/*      */         
/* 1687 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean advanceTo(E entry) {
/* 1695 */         CustomConcurrentHashMap.Strategy<K, V, E> s = CustomConcurrentHashMap.Impl.this.strategy;
/* 1696 */         K key = (K)s.getKey(entry);
/* 1697 */         V value = (V)s.getValue(entry);
/* 1698 */         if (key != null && value != null) {
/* 1699 */           this.nextExternal = new CustomConcurrentHashMap.Impl.WriteThroughEntry(CustomConcurrentHashMap.Impl.this, key, value);
/* 1700 */           return true;
/*      */         } 
/*      */         
/* 1703 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1708 */       public boolean hasNext() { return (this.nextExternal != null); }
/*      */ 
/*      */       
/*      */       CustomConcurrentHashMap.Impl<K, V, E>.WriteThroughEntry nextEntry() {
/* 1712 */         if (this.nextExternal == null) {
/* 1713 */           throw new NoSuchElementException();
/*      */         }
/* 1715 */         this.lastReturned = this.nextExternal;
/* 1716 */         advance();
/* 1717 */         return this.lastReturned;
/*      */       }
/*      */       
/*      */       public void remove() {
/* 1721 */         if (this.lastReturned == null) {
/* 1722 */           throw new IllegalStateException();
/*      */         }
/* 1724 */         CustomConcurrentHashMap.Impl.this.remove(this.lastReturned.getKey());
/* 1725 */         this.lastReturned = null;
/*      */       } }
/*      */     
/*      */     final class KeyIterator extends HashIterator implements Iterator<K> { KeyIterator() {
/* 1729 */         super(CustomConcurrentHashMap.Impl.this);
/*      */       }
/*      */       
/* 1732 */       public K next() { return (K)nextEntry().getKey(); } }
/*      */     
/*      */     final class ValueIterator extends HashIterator implements Iterator<V> {
/*      */       ValueIterator() {
/* 1736 */         super(CustomConcurrentHashMap.Impl.this);
/*      */       }
/*      */       
/* 1739 */       public V next() { return (V)nextEntry().getValue(); }
/*      */     }
/*      */ 
/*      */     
/*      */     final class WriteThroughEntry
/*      */       extends AbstractMapEntry<K, V>
/*      */     {
/*      */       final K key;
/*      */       
/*      */       V value;
/*      */ 
/*      */       
/*      */       WriteThroughEntry(K key, V value) {
/* 1752 */         this.key = key;
/* 1753 */         this.value = value;
/*      */       }
/*      */ 
/*      */       
/* 1757 */       public K getKey() { return (K)this.key; }
/*      */ 
/*      */ 
/*      */       
/* 1761 */       public V getValue() { return (V)this.value; }
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
/*      */       public V setValue(V value) {
/* 1773 */         if (value == null) {
/* 1774 */           throw new NullPointerException();
/*      */         }
/* 1776 */         V oldValue = (V)CustomConcurrentHashMap.Impl.this.put(getKey(), value);
/* 1777 */         this.value = value;
/* 1778 */         return oldValue;
/*      */       } }
/*      */     
/*      */     final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> { EntryIterator() {
/* 1782 */         super(CustomConcurrentHashMap.Impl.this);
/*      */       }
/*      */ 
/*      */       
/* 1786 */       public Map.Entry<K, V> next() { return nextEntry(); } }
/*      */ 
/*      */ 
/*      */     
/*      */     final class KeySet
/*      */       extends AbstractSet<K>
/*      */     {
/* 1793 */       public Iterator<K> iterator() { return new CustomConcurrentHashMap.Impl.KeyIterator(CustomConcurrentHashMap.Impl.this); }
/*      */ 
/*      */ 
/*      */       
/* 1797 */       public int size() { return CustomConcurrentHashMap.Impl.this.size(); }
/*      */ 
/*      */ 
/*      */       
/* 1801 */       public boolean isEmpty() { return CustomConcurrentHashMap.Impl.this.isEmpty(); }
/*      */ 
/*      */ 
/*      */       
/* 1805 */       public boolean contains(Object o) { return CustomConcurrentHashMap.Impl.this.containsKey(o); }
/*      */ 
/*      */ 
/*      */       
/* 1809 */       public boolean remove(Object o) { return (CustomConcurrentHashMap.Impl.this.remove(o) != null); }
/*      */ 
/*      */ 
/*      */       
/* 1813 */       public void clear() { CustomConcurrentHashMap.Impl.this.clear(); }
/*      */     }
/*      */ 
/*      */     
/*      */     final class Values
/*      */       extends AbstractCollection<V>
/*      */     {
/* 1820 */       public Iterator<V> iterator() { return new CustomConcurrentHashMap.Impl.ValueIterator(CustomConcurrentHashMap.Impl.this); }
/*      */ 
/*      */ 
/*      */       
/* 1824 */       public int size() { return CustomConcurrentHashMap.Impl.this.size(); }
/*      */ 
/*      */ 
/*      */       
/* 1828 */       public boolean isEmpty() { return CustomConcurrentHashMap.Impl.this.isEmpty(); }
/*      */ 
/*      */ 
/*      */       
/* 1832 */       public boolean contains(Object o) { return CustomConcurrentHashMap.Impl.this.containsValue(o); }
/*      */ 
/*      */ 
/*      */       
/* 1836 */       public void clear() { CustomConcurrentHashMap.Impl.this.clear(); }
/*      */     }
/*      */ 
/*      */     
/*      */     final class EntrySet
/*      */       extends AbstractSet<Map.Entry<K, V>>
/*      */     {
/* 1843 */       public Iterator<Map.Entry<K, V>> iterator() { return new CustomConcurrentHashMap.Impl.EntryIterator(CustomConcurrentHashMap.Impl.this); }
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/* 1847 */         if (!(o instanceof Map.Entry)) {
/* 1848 */           return false;
/*      */         }
/* 1850 */         Map.Entry<?, ?> e = (Map.Entry)o;
/* 1851 */         Object key = e.getKey();
/* 1852 */         if (key == null) {
/* 1853 */           return false;
/*      */         }
/* 1855 */         V v = (V)CustomConcurrentHashMap.Impl.this.get(key);
/*      */         
/* 1857 */         return (v != null && CustomConcurrentHashMap.Impl.this.strategy.equalValues(v, e.getValue()));
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 1861 */         if (!(o instanceof Map.Entry)) {
/* 1862 */           return false;
/*      */         }
/* 1864 */         Map.Entry<?, ?> e = (Map.Entry)o;
/* 1865 */         Object key = e.getKey();
/* 1866 */         return (key != null && CustomConcurrentHashMap.Impl.this.remove(key, e.getValue()));
/*      */       }
/*      */ 
/*      */       
/* 1870 */       public int size() { return CustomConcurrentHashMap.Impl.this.size(); }
/*      */ 
/*      */ 
/*      */       
/* 1874 */       public boolean isEmpty() { return CustomConcurrentHashMap.Impl.this.isEmpty(); }
/*      */ 
/*      */ 
/*      */       
/* 1878 */       public void clear() { CustomConcurrentHashMap.Impl.this.clear(); }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream out) throws IOException {
/* 1888 */       out.writeInt(size());
/* 1889 */       out.writeInt(this.segments.length);
/* 1890 */       out.writeObject(this.strategy);
/* 1891 */       for (Map.Entry<K, V> entry : entrySet()) {
/* 1892 */         out.writeObject(entry.getKey());
/* 1893 */         out.writeObject(entry.getValue());
/*      */       } 
/* 1895 */       out.writeObject(null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class Fields
/*      */     {
/* 1905 */       static final Field segmentShift = findField("segmentShift");
/* 1906 */       static final Field segmentMask = findField("segmentMask");
/* 1907 */       static final Field segments = findField("segments");
/* 1908 */       static final Field strategy = findField("strategy");
/*      */       
/*      */       static Field findField(String name) {
/*      */         try {
/* 1912 */           Field f = CustomConcurrentHashMap.Impl.class.getDeclaredField(name);
/* 1913 */           f.setAccessible(true);
/* 1914 */           return f;
/* 1915 */         } catch (NoSuchFieldException e) {
/* 1916 */           throw new AssertionError(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*      */       try {
/* 1925 */         int initialCapacity = in.readInt();
/* 1926 */         int concurrencyLevel = in.readInt();
/* 1927 */         CustomConcurrentHashMap.Strategy<K, V, E> strategy = (CustomConcurrentHashMap.Strategy)in.readObject();
/*      */         
/* 1929 */         if (concurrencyLevel > 65536) {
/* 1930 */           concurrencyLevel = 65536;
/*      */         }
/*      */ 
/*      */         
/* 1934 */         int segmentShift = 0;
/* 1935 */         int segmentCount = 1;
/* 1936 */         while (segmentCount < concurrencyLevel) {
/* 1937 */           segmentShift++;
/* 1938 */           segmentCount <<= 1;
/*      */         } 
/* 1940 */         Fields.segmentShift.set(this, Integer.valueOf(32 - segmentShift));
/* 1941 */         Fields.segmentMask.set(this, Integer.valueOf(segmentCount - 1));
/* 1942 */         Fields.segments.set(this, newSegmentArray(segmentCount));
/*      */         
/* 1944 */         if (initialCapacity > 1073741824) {
/* 1945 */           initialCapacity = 1073741824;
/*      */         }
/*      */         
/* 1948 */         int segmentCapacity = initialCapacity / segmentCount;
/* 1949 */         if (segmentCapacity * segmentCount < initialCapacity) {
/* 1950 */           segmentCapacity++;
/*      */         }
/*      */         
/* 1953 */         int segmentSize = 1;
/* 1954 */         while (segmentSize < segmentCapacity) {
/* 1955 */           segmentSize <<= 1;
/*      */         }
/* 1957 */         for (int i = 0; i < this.segments.length; i++) {
/* 1958 */           this.segments[i] = new Segment(segmentSize);
/*      */         }
/*      */         
/* 1961 */         Fields.strategy.set(this, strategy);
/*      */         
/*      */         while (true) {
/* 1964 */           K key = (K)in.readObject();
/* 1965 */           if (key == null) {
/*      */             break;
/*      */           }
/* 1968 */           V value = (V)in.readObject();
/* 1969 */           put(key, value);
/*      */         } 
/* 1971 */       } catch (IllegalAccessException e) {
/* 1972 */         throw new AssertionError(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class ComputingImpl<K, V, E>
/*      */     extends Impl<K, V, E>
/*      */   {
/*      */     static final long serialVersionUID = 0L;
/*      */     
/*      */     final CustomConcurrentHashMap.ComputingStrategy<K, V, E> computingStrategy;
/*      */     
/*      */     final Function<? super K, ? extends V> computer;
/*      */ 
/*      */     
/*      */     ComputingImpl(CustomConcurrentHashMap.ComputingStrategy<K, V, E> strategy, CustomConcurrentHashMap.Builder builder, Function<? super K, ? extends V> computer) {
/* 1990 */       super(strategy, builder);
/* 1991 */       this.computingStrategy = strategy;
/* 1992 */       this.computer = computer;
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
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 2006 */       K key = (K)k;
/*      */       
/* 2008 */       if (key == null) {
/* 2009 */         throw new NullPointerException("key");
/*      */       }
/*      */       
/* 2012 */       hash = hash(key);
/* 2013 */       segment = segmentFor(hash);
/*      */       label53: while (true) {
/* 2015 */         entry = (E)segment.getEntry(key, hash);
/* 2016 */         if (entry == null) {
/* 2017 */           boolean created = false;
/* 2018 */           segment.lock();
/*      */           
/*      */           try {
/* 2021 */             entry = (E)segment.getEntry(key, hash);
/* 2022 */             if (entry == null) {
/*      */               
/* 2024 */               created = true;
/* 2025 */               int count = segment.count;
/* 2026 */               if (count++ > segment.threshold) {
/* 2027 */                 segment.expand();
/*      */               }
/* 2029 */               AtomicReferenceArray<E> table = segment.table;
/* 2030 */               int index = hash & table.length() - 1;
/* 2031 */               E first = (E)table.get(index);
/* 2032 */               segment.modCount++;
/* 2033 */               entry = (E)this.computingStrategy.newEntry(key, hash, first);
/* 2034 */               table.set(index, entry);
/* 2035 */               segment.count = count;
/*      */             } 
/*      */           } finally {
/* 2038 */             segment.unlock();
/*      */           } 
/*      */           
/* 2041 */           if (created) {
/*      */             
/* 2043 */             success = false;
/*      */             try {
/* 2045 */               V value = (V)this.computingStrategy.compute(key, entry, this.computer);
/* 2046 */               if (value == null) {
/* 2047 */                 throw new NullPointerException("compute() returned null unexpectedly");
/*      */               }
/*      */               
/* 2050 */               success = true;
/* 2051 */               return value;
/*      */             } finally {
/* 2053 */               if (!success) {
/* 2054 */                 segment.removeEntry(entry, hash);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2061 */         interrupted = false;
/*      */         
/*      */         while (true) {
/*      */           
/* 2065 */           try { V value = (V)this.computingStrategy.waitForValue(entry);
/* 2066 */             if (value == null)
/*      */             
/* 2068 */             { segment.removeEntry(entry, hash);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2077 */               if (interrupted)
/* 2078 */               { Thread.currentThread().interrupt(); continue label53; }  continue label53; }  return value; } catch (InterruptedException e) {  } finally { if (interrupted) Thread.currentThread().interrupt();
/*      */              }
/*      */         
/*      */         } 
/*      */         break;
/*      */       } 
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
/*      */   static class SimpleStrategy<K, V>
/*      */     extends Object
/*      */     implements Strategy<K, V, SimpleInternalEntry<K, V>>
/*      */   {
/* 2108 */     public CustomConcurrentHashMap.SimpleInternalEntry<K, V> newEntry(K key, int hash, CustomConcurrentHashMap.SimpleInternalEntry<K, V> next) { return new CustomConcurrentHashMap.SimpleInternalEntry(key, hash, null, next); }
/*      */ 
/*      */ 
/*      */     
/* 2112 */     public CustomConcurrentHashMap.SimpleInternalEntry<K, V> copyEntry(K key, CustomConcurrentHashMap.SimpleInternalEntry<K, V> original, CustomConcurrentHashMap.SimpleInternalEntry<K, V> next) { return new CustomConcurrentHashMap.SimpleInternalEntry(key, original.hash, original.value, next); }
/*      */ 
/*      */ 
/*      */     
/* 2116 */     public void setValue(CustomConcurrentHashMap.SimpleInternalEntry<K, V> entry, V value) { entry.value = value; }
/*      */ 
/*      */     
/* 2119 */     public V getValue(CustomConcurrentHashMap.SimpleInternalEntry<K, V> entry) { return (V)entry.value; }
/*      */ 
/*      */     
/* 2122 */     public boolean equalKeys(K a, Object b) { return a.equals(b); }
/*      */ 
/*      */     
/* 2125 */     public boolean equalValues(V a, Object b) { return a.equals(b); }
/*      */ 
/*      */     
/* 2128 */     public int hashKey(Object key) { return key.hashCode(); }
/*      */ 
/*      */     
/* 2131 */     public K getKey(CustomConcurrentHashMap.SimpleInternalEntry<K, V> entry) { return (K)entry.key; }
/*      */ 
/*      */     
/* 2134 */     public CustomConcurrentHashMap.SimpleInternalEntry<K, V> getNext(CustomConcurrentHashMap.SimpleInternalEntry<K, V> entry) { return entry.next; }
/*      */ 
/*      */     
/* 2137 */     public int getHash(CustomConcurrentHashMap.SimpleInternalEntry<K, V> entry) { return entry.hash; }
/*      */ 
/*      */     
/*      */     public void setInternals(CustomConcurrentHashMap.Internals<K, V, CustomConcurrentHashMap.SimpleInternalEntry<K, V>> internals) {}
/*      */   }
/*      */ 
/*      */   
/*      */   static class SimpleInternalEntry<K, V>
/*      */     extends Object
/*      */   {
/*      */     final K key;
/*      */     
/*      */     final int hash;
/*      */     
/*      */     final SimpleInternalEntry<K, V> next;
/*      */ 
/*      */     
/*      */     SimpleInternalEntry(K key, int hash, @Nullable V value, SimpleInternalEntry<K, V> next) {
/* 2155 */       this.key = key;
/* 2156 */       this.hash = hash;
/* 2157 */       this.value = value;
/* 2158 */       this.next = next;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\CustomConcurrentHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */