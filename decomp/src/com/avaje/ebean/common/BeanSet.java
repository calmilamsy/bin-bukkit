/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public final class BeanSet<E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements Set<E>, BeanCollectionAdd
/*     */ {
/*     */   private Set<E> set;
/*     */   
/*  47 */   public BeanSet(Set<E> set) { this.set = set; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public BeanSet() { this(new LinkedHashSet()); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public BeanSet(BeanCollectionLoader loader, Object ownerBean, String propertyName) { super(loader, ownerBean, propertyName); }
/*     */ 
/*     */ 
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  63 */     if (SerializeControl.isVanillaCollections()) {
/*  64 */       return this.set;
/*     */     }
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  70 */     if (SerializeControl.isVanillaCollections()) {
/*  71 */       return this.set;
/*     */     }
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void addBean(Object bean) { this.set.add(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void internalAdd(Object bean) { this.set.add(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public boolean isPopulated() { return (this.set != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public boolean isReference() { return (this.set == null); }
/*     */ 
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/* 102 */     if (this.set == null) {
/* 103 */       this.set = new LinkedHashSet();
/* 104 */       return true;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 111 */     synchronized (this) {
/* 112 */       if (this.set == null) {
/* 113 */         if (this.modifyListening) {
/* 114 */           lazyLoadCollection(true);
/*     */         } else {
/* 116 */           this.set = new LinkedHashSet();
/*     */         } 
/*     */       }
/* 119 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 124 */     synchronized (this) {
/* 125 */       if (this.set == null) {
/* 126 */         lazyLoadCollection(true);
/*     */       }
/* 128 */       touched();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public void setActualSet(Set<?> set) { this.set = set; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public Set<E> getActualSet() { return this.set; }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public Collection<E> getActualDetails() { return this.set; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public Object getActualCollection() throws ObjectStreamException { return this.set; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     StringBuffer sb = new StringBuffer();
/* 161 */     sb.append("BeanSet ");
/* 162 */     if (isSharedInstance()) {
/* 163 */       sb.append("sharedInstance ");
/* 164 */     } else if (isReadOnly()) {
/* 165 */       sb.append("readOnly ");
/*     */     } 
/* 167 */     if (this.set == null) {
/* 168 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 171 */       sb.append("size[").append(this.set.size()).append("]");
/* 172 */       sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
/* 173 */       sb.append(" set").append(this.set);
/*     */     } 
/* 175 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 182 */     init();
/* 183 */     return this.set.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 187 */     init();
/* 188 */     return this.set.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(E o) {
/* 197 */     checkReadOnly();
/* 198 */     init();
/* 199 */     if (this.modifyAddListening) {
/* 200 */       if (this.set.add(o)) {
/* 201 */         modifyAddition(o);
/* 202 */         return true;
/*     */       } 
/* 204 */       return false;
/*     */     } 
/*     */     
/* 207 */     return this.set.add(o);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 211 */     checkReadOnly();
/* 212 */     init();
/* 213 */     if (this.modifyAddListening) {
/* 214 */       boolean changed = false;
/* 215 */       Iterator<? extends E> it = c.iterator();
/* 216 */       while (it.hasNext()) {
/* 217 */         E o = (E)it.next();
/* 218 */         if (this.set.add(o)) {
/* 219 */           modifyAddition(o);
/* 220 */           changed = true;
/*     */         } 
/*     */       } 
/* 223 */       return changed;
/*     */     } 
/* 225 */     return this.set.addAll(c);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 229 */     checkReadOnly();
/* 230 */     initClear();
/* 231 */     if (this.modifyRemoveListening) {
/* 232 */       Iterator<E> it = this.set.iterator();
/* 233 */       while (it.hasNext()) {
/* 234 */         E e = (E)it.next();
/* 235 */         modifyRemoval(e);
/*     */       } 
/*     */     } 
/* 238 */     this.set.clear();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 242 */     init();
/* 243 */     return this.set.contains(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 247 */     init();
/* 248 */     return this.set.containsAll(c);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 252 */     init();
/* 253 */     return this.set.isEmpty();
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 257 */     init();
/* 258 */     if (isReadOnly()) {
/* 259 */       return new ReadOnlyIterator(this.set.iterator());
/*     */     }
/* 261 */     if (this.modifyListening) {
/* 262 */       return new ModifyIterator(this, this.set.iterator());
/*     */     }
/* 264 */     return this.set.iterator();
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/* 268 */     checkReadOnly();
/* 269 */     init();
/* 270 */     if (this.modifyRemoveListening) {
/* 271 */       if (this.set.remove(o)) {
/* 272 */         modifyRemoval(o);
/* 273 */         return true;
/*     */       } 
/* 275 */       return false;
/*     */     } 
/* 277 */     return this.set.remove(o);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 281 */     checkReadOnly();
/* 282 */     init();
/* 283 */     if (this.modifyRemoveListening) {
/* 284 */       boolean changed = false;
/* 285 */       Iterator<?> it = c.iterator();
/* 286 */       while (it.hasNext()) {
/* 287 */         Object o = it.next();
/* 288 */         if (this.set.remove(o)) {
/* 289 */           modifyRemoval(o);
/* 290 */           changed = true;
/*     */         } 
/*     */       } 
/* 293 */       return changed;
/*     */     } 
/* 295 */     return this.set.removeAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 299 */     checkReadOnly();
/* 300 */     init();
/* 301 */     if (this.modifyRemoveListening) {
/* 302 */       boolean changed = false;
/* 303 */       Iterator<?> it = this.set.iterator();
/* 304 */       while (it.hasNext()) {
/* 305 */         Object o = it.next();
/* 306 */         if (!c.contains(o)) {
/* 307 */           it.remove();
/* 308 */           modifyRemoval(o);
/* 309 */           changed = true;
/*     */         } 
/*     */       } 
/* 312 */       return changed;
/*     */     } 
/* 314 */     return this.set.retainAll(c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 318 */     init();
/* 319 */     return this.set.size();
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 323 */     init();
/* 324 */     return this.set.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 328 */     init();
/* 329 */     return (T[])this.set.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReadOnlyIterator<E>
/*     */     extends Object
/*     */     implements Iterator<E>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2577697326745352605L;
/*     */     private final Iterator<E> it;
/*     */     
/* 340 */     ReadOnlyIterator(Iterator<E> it) { this.it = it; }
/*     */ 
/*     */     
/* 343 */     public boolean hasNext() { return this.it.hasNext(); }
/*     */ 
/*     */ 
/*     */     
/* 347 */     public E next() { return (E)this.it.next(); }
/*     */ 
/*     */ 
/*     */     
/* 351 */     public void remove() { throw new IllegalStateException("This collection is in ReadOnly mode"); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\BeanSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */