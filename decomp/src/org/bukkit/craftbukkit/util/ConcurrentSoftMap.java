/*     */ package org.bukkit.craftbukkit.util;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class ConcurrentSoftMap<K, V>
/*     */   extends Object
/*     */ {
/*     */   private final ConcurrentHashMap<K, SoftMapReference<K, V>> map;
/*     */   private final ReferenceQueue<SoftMapReference> queue;
/*     */   private final LinkedList<V> strongReferenceQueue;
/*     */   private final int strongReferenceSize;
/*     */   
/*  41 */   public ConcurrentSoftMap() { this(20); } public ConcurrentSoftMap(int size) {
/*     */     this.map = new ConcurrentHashMap();
/*     */     this.queue = new ReferenceQueue();
/*     */     this.strongReferenceQueue = new LinkedList();
/*  45 */     this.strongReferenceSize = size;
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
/*     */   private void emptyQueue() {
/*     */     SoftMapReference ref;
/*  58 */     while ((ref = (SoftMapReference)this.queue.poll()) != null) {
/*  59 */       this.map.remove(ref.key);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  64 */     synchronized (this.strongReferenceQueue) {
/*  65 */       this.strongReferenceQueue.clear();
/*     */     } 
/*  67 */     this.map.clear();
/*  68 */     emptyQueue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(K key) {
/*  74 */     emptyQueue();
/*  75 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(V value) {
/*  81 */     emptyQueue();
/*  82 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set entrySet() {
/*  88 */     emptyQueue();
/*  89 */     throw new UnsupportedOperationException("SoftMap does not support this operation, since it creates potentially stong references");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  95 */     emptyQueue();
/*  96 */     throw new UnsupportedOperationException("SoftMap doesn't support equals checks");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(K key) {
/* 102 */     emptyQueue();
/* 103 */     return (V)fastGet(key);
/*     */   }
/*     */   
/*     */   private V fastGet(K key) {
/* 107 */     SoftMapReference<K, V> ref = (SoftMapReference)this.map.get(key);
/*     */     
/* 109 */     if (ref == null) {
/* 110 */       return null;
/*     */     }
/* 112 */     V value = (V)ref.get();
/*     */     
/* 114 */     if (value != null) {
/* 115 */       synchronized (this.strongReferenceQueue) {
/* 116 */         this.strongReferenceQueue.addFirst(value);
/* 117 */         if (this.strongReferenceQueue.size() > this.strongReferenceSize) {
/* 118 */           this.strongReferenceQueue.removeLast();
/*     */         }
/*     */       } 
/*     */     }
/* 122 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     emptyQueue();
/* 129 */     throw new UnsupportedOperationException("SoftMap doesn't support hashCode");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 135 */     emptyQueue();
/* 136 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set keySet() {
/* 142 */     emptyQueue();
/* 143 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/* 149 */     emptyQueue();
/* 150 */     V old = (V)fastGet(key);
/* 151 */     fastPut(key, value);
/* 152 */     return old;
/*     */   }
/*     */   
/*     */   private void fastPut(K key, V value) {
/* 156 */     this.map.put(key, new SoftMapReference(key, value, this.queue));
/* 157 */     synchronized (this.strongReferenceQueue) {
/* 158 */       this.strongReferenceQueue.addFirst(value);
/* 159 */       if (this.strongReferenceQueue.size() > this.strongReferenceSize) {
/* 160 */         this.strongReferenceQueue.removeLast();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public V putIfAbsent(K key, V value) {
/* 166 */     emptyQueue();
/* 167 */     return (V)fastPutIfAbsent(key, value);
/*     */   }
/*     */   
/*     */   private V fastPutIfAbsent(K key, V value) {
/* 171 */     V ret = null;
/*     */     
/* 173 */     if (this.map.containsKey(key)) {
/* 174 */       SoftMapReference<K, V> current = (SoftMapReference)this.map.get(key);
/*     */       
/* 176 */       if (current != null) {
/* 177 */         ret = (V)current.get();
/*     */       }
/*     */     } 
/*     */     
/* 181 */     if (ret == null) {
/* 182 */       SoftMapReference<K, V> newValue = new SoftMapReference<K, V>(key, value, this.queue);
/* 183 */       boolean success = false;
/*     */       
/* 185 */       while (!success) {
/* 186 */         SoftMapReference<K, V> oldValue = (SoftMapReference)this.map.putIfAbsent(key, newValue);
/*     */         
/* 188 */         if (oldValue == null) {
/* 189 */           ret = null;
/* 190 */           success = true; continue;
/*     */         } 
/* 192 */         ret = (V)oldValue.get();
/* 193 */         if (ret == null) {
/* 194 */           success = this.map.replace(key, oldValue, newValue); continue;
/*     */         } 
/* 196 */         success = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (ret == null) {
/* 203 */       synchronized (this.strongReferenceQueue) {
/* 204 */         this.strongReferenceQueue.addFirst(value);
/* 205 */         if (this.strongReferenceQueue.size() > this.strongReferenceSize) {
/* 206 */           this.strongReferenceQueue.removeLast();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 211 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map other) {
/* 217 */     emptyQueue();
/* 218 */     Iterator<K> itr = other.keySet().iterator();
/* 219 */     while (itr.hasNext()) {
/* 220 */       K key = (K)itr.next();
/* 221 */       fastPut(key, other.get(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(K key) {
/* 228 */     emptyQueue();
/* 229 */     SoftMapReference<K, V> ref = (SoftMapReference)this.map.remove(key);
/*     */     
/* 231 */     if (ref != null) {
/* 232 */       return (V)ref.get();
/*     */     }
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 240 */     emptyQueue();
/* 241 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection values() {
/* 247 */     emptyQueue();
/* 248 */     throw new UnsupportedOperationException("SoftMap does not support this operation, since it creates potentially stong references");
/*     */   }
/*     */   
/*     */   private static class SoftMapReference<K, V> extends SoftReference<V> {
/*     */     K key;
/*     */     
/*     */     SoftMapReference(K key, V value, ReferenceQueue queue) {
/* 255 */       super(value, queue);
/* 256 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 261 */       if (o == null) {
/* 262 */         return false;
/*     */       }
/* 264 */       if (!(o instanceof SoftMapReference)) {
/* 265 */         return false;
/*     */       }
/* 267 */       SoftMapReference other = (SoftMapReference)o;
/* 268 */       return (other.get() == get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\ConcurrentSoftMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */