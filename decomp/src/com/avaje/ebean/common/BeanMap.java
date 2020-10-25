/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanMap<K, E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements Map<K, E>
/*     */ {
/*     */   private Map<K, E> map;
/*     */   
/*  47 */   public BeanMap(Map<K, E> map) { this.map = map; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public BeanMap() { this(new LinkedHashMap()); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public BeanMap(BeanCollectionLoader ebeanServer, Object ownerBean, String propertyName) { super(ebeanServer, ownerBean, propertyName); }
/*     */ 
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  62 */     if (SerializeControl.isVanillaCollections()) {
/*  63 */       return this.map;
/*     */     }
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  69 */     if (SerializeControl.isVanillaCollections()) {
/*  70 */       return this.map;
/*     */     }
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  76 */   public void internalAdd(Object bean) { throw new RuntimeException("Not allowed for map"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public boolean isPopulated() { return (this.map != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public boolean isReference() { return (this.map == null); }
/*     */ 
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/*  96 */     if (this.map == null) {
/*  97 */       this.map = new LinkedHashMap();
/*  98 */       return true;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 105 */     synchronized (this) {
/* 106 */       if (this.map == null) {
/* 107 */         if (this.modifyListening) {
/* 108 */           lazyLoadCollection(true);
/*     */         } else {
/* 110 */           this.map = new LinkedHashMap();
/*     */         } 
/*     */       }
/* 113 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 118 */     synchronized (this) {
/* 119 */       if (this.map == null) {
/* 120 */         lazyLoadCollection(false);
/*     */       }
/* 122 */       touched();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public void setActualMap(Map<?, ?> map) { this.map = map; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public Map<K, E> getActualMap() { return this.map; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public Collection<E> getActualDetails() { return this.map.values(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public Object getActualCollection() throws ObjectStreamException { return this.map; }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     StringBuffer sb = new StringBuffer();
/* 161 */     sb.append("BeanMap ");
/* 162 */     if (isSharedInstance()) {
/* 163 */       sb.append("sharedInstance ");
/* 164 */     } else if (isReadOnly()) {
/* 165 */       sb.append("readOnly ");
/*     */     } 
/* 167 */     if (this.map == null) {
/* 168 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 171 */       sb.append("size[").append(this.map.size()).append("]");
/* 172 */       sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
/* 173 */       sb.append(" map").append(this.map);
/*     */     } 
/* 175 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 182 */     init();
/* 183 */     return this.map.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 187 */     init();
/* 188 */     return this.map.hashCode();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 192 */     checkReadOnly();
/* 193 */     initClear();
/* 194 */     if (this.modifyRemoveListening) {
/* 195 */       for (K key : this.map.keySet()) {
/* 196 */         E o = (E)this.map.remove(key);
/* 197 */         modifyRemoval(o);
/*     */       } 
/*     */     }
/* 200 */     this.map.clear();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 204 */     init();
/* 205 */     return this.map.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 209 */     init();
/* 210 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, E>> entrySet() {
/* 215 */     init();
/* 216 */     if (isReadOnly()) {
/* 217 */       return Collections.unmodifiableSet(this.map.entrySet());
/*     */     }
/* 219 */     if (this.modifyListening) {
/* 220 */       Set<Map.Entry<K, E>> s = this.map.entrySet();
/* 221 */       return new ModifySet(this, s);
/*     */     } 
/* 223 */     return this.map.entrySet();
/*     */   }
/*     */   
/*     */   public E get(Object key) {
/* 227 */     init();
/* 228 */     return (E)this.map.get(key);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 232 */     init();
/* 233 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 237 */     init();
/* 238 */     if (isReadOnly()) {
/* 239 */       return Collections.unmodifiableSet(this.map.keySet());
/*     */     }
/*     */     
/* 242 */     return this.map.keySet();
/*     */   }
/*     */   
/*     */   public E put(K key, E value) {
/* 246 */     checkReadOnly();
/* 247 */     init();
/* 248 */     if (this.modifyListening) {
/* 249 */       Object o = this.map.put(key, value);
/* 250 */       modifyAddition(value);
/* 251 */       modifyRemoval(o);
/*     */     } 
/* 253 */     return (E)this.map.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends E> t) {
/* 258 */     checkReadOnly();
/* 259 */     init();
/* 260 */     if (this.modifyListening) {
/* 261 */       Iterator it = t.entrySet().iterator();
/* 262 */       while (it.hasNext()) {
/* 263 */         Map.Entry entry = (Map.Entry)it.next();
/* 264 */         Object o = this.map.put(entry.getKey(), entry.getValue());
/* 265 */         modifyAddition(entry.getValue());
/* 266 */         modifyRemoval(o);
/*     */       } 
/*     */     } 
/* 269 */     this.map.putAll(t);
/*     */   }
/*     */   
/*     */   public E remove(Object key) {
/* 273 */     checkReadOnly();
/* 274 */     init();
/* 275 */     if (this.modifyRemoveListening) {
/* 276 */       E o = (E)this.map.remove(key);
/* 277 */       modifyRemoval(o);
/* 278 */       return o;
/*     */     } 
/* 280 */     return (E)this.map.remove(key);
/*     */   }
/*     */   
/*     */   public int size() {
/* 284 */     init();
/* 285 */     return this.map.size();
/*     */   }
/*     */   
/*     */   public Collection<E> values() {
/* 289 */     init();
/* 290 */     if (isReadOnly()) {
/* 291 */       return Collections.unmodifiableCollection(this.map.values());
/*     */     }
/* 293 */     if (this.modifyListening) {
/* 294 */       Collection<E> c = this.map.values();
/* 295 */       return new ModifyCollection(this, c);
/*     */     } 
/* 297 */     return this.map.values();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\BeanMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */