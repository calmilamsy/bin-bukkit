/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public final class BeanList<E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements List<E>, BeanCollectionAdd
/*     */ {
/*     */   private List<E> list;
/*     */   
/*  50 */   public BeanList(List<E> list) { this.list = list; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public BeanList() { this(new ArrayList()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public BeanList(BeanCollectionLoader loader, Object ownerBean, String propertyName) { super(loader, ownerBean, propertyName); }
/*     */ 
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  68 */     if (SerializeControl.isVanillaCollections()) {
/*  69 */       return this.list;
/*     */     }
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  75 */     if (SerializeControl.isVanillaCollections()) {
/*  76 */       return this.list;
/*     */     }
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void addBean(Object bean) { this.list.add(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void internalAdd(Object bean) { this.list.add(bean); }
/*     */ 
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/*  92 */     if (this.list == null) {
/*  93 */       this.list = new ArrayList();
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 101 */     synchronized (this) {
/* 102 */       if (this.list == null) {
/* 103 */         if (this.modifyListening) {
/* 104 */           lazyLoadCollection(true);
/*     */         } else {
/* 106 */           this.list = new ArrayList();
/*     */         } 
/*     */       }
/* 109 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 114 */     synchronized (this) {
/* 115 */       if (this.list == null) {
/* 116 */         lazyLoadCollection(false);
/*     */       }
/* 118 */       touched();
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
/* 130 */   public void setActualList(List<?> list) { this.list = list; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public List<E> getActualList() { return this.list; }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public Collection<E> getActualDetails() { return this.list; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public Object getActualCollection() throws ObjectStreamException { return this.list; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public boolean isPopulated() { return (this.list != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public boolean isReference() { return (this.list == null); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     StringBuffer sb = new StringBuffer();
/* 168 */     sb.append("BeanList ");
/* 169 */     if (isSharedInstance()) {
/* 170 */       sb.append("sharedInstance ");
/* 171 */     } else if (isReadOnly()) {
/* 172 */       sb.append("readOnly ");
/*     */     } 
/* 174 */     if (this.list == null) {
/* 175 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 178 */       sb.append("size[").append(this.list.size()).append("] ");
/* 179 */       sb.append("hasMoreRows[").append(this.hasMoreRows).append("] ");
/* 180 */       sb.append("list").append(this.list).append("");
/*     */     } 
/* 182 */     return sb.toString();
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
/*     */   public boolean equals(Object obj) {
/* 194 */     init();
/* 195 */     return this.list.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 199 */     init();
/* 200 */     return this.list.hashCode();
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
/*     */   public void add(int index, E element) {
/* 215 */     checkReadOnly();
/* 216 */     init();
/* 217 */     if (this.modifyAddListening) {
/* 218 */       modifyAddition(element);
/*     */     }
/* 220 */     this.list.add(index, element);
/*     */   }
/*     */   
/*     */   public boolean add(E o) {
/* 224 */     checkReadOnly();
/* 225 */     init();
/* 226 */     if (this.modifyAddListening) {
/* 227 */       if (this.list.add(o)) {
/* 228 */         modifyAddition(o);
/* 229 */         return true;
/*     */       } 
/* 231 */       return false;
/*     */     } 
/*     */     
/* 234 */     return this.list.add(o);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 238 */     checkReadOnly();
/* 239 */     init();
/* 240 */     if (this.modifyAddListening)
/*     */     {
/* 242 */       getModifyHolder().modifyAdditionAll(c);
/*     */     }
/* 244 */     return this.list.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> c) {
/* 248 */     checkReadOnly();
/* 249 */     init();
/* 250 */     if (this.modifyAddListening)
/*     */     {
/* 252 */       getModifyHolder().modifyAdditionAll(c);
/*     */     }
/* 254 */     return this.list.addAll(index, c);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 258 */     checkReadOnly();
/*     */ 
/*     */     
/* 261 */     initClear();
/* 262 */     if (this.modifyRemoveListening) {
/* 263 */       for (int i = 0; i < this.list.size(); i++) {
/* 264 */         getModifyHolder().modifyRemoval(this.list.get(i));
/*     */       }
/*     */     }
/* 267 */     this.list.clear();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 271 */     init();
/* 272 */     return this.list.contains(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 276 */     init();
/* 277 */     return this.list.containsAll(c);
/*     */   }
/*     */   
/*     */   public E get(int index) {
/* 281 */     init();
/* 282 */     return (E)this.list.get(index);
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/* 286 */     init();
/* 287 */     return this.list.indexOf(o);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 291 */     init();
/* 292 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 296 */     init();
/* 297 */     if (isReadOnly()) {
/* 298 */       return new ReadOnlyListIterator(this.list.listIterator());
/*     */     }
/* 300 */     if (this.modifyListening) {
/* 301 */       Iterator<E> it = this.list.iterator();
/* 302 */       return new ModifyIterator(this, it);
/*     */     } 
/* 304 */     return this.list.iterator();
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 308 */     init();
/* 309 */     return this.list.lastIndexOf(o);
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator() {
/* 313 */     init();
/* 314 */     if (isReadOnly()) {
/* 315 */       return new ReadOnlyListIterator(this.list.listIterator());
/*     */     }
/* 317 */     if (this.modifyListening) {
/* 318 */       ListIterator<E> it = this.list.listIterator();
/* 319 */       return new ModifyListIterator(this, it);
/*     */     } 
/* 321 */     return this.list.listIterator();
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator(int index) {
/* 325 */     init();
/* 326 */     if (isReadOnly()) {
/* 327 */       return new ReadOnlyListIterator(this.list.listIterator(index));
/*     */     }
/* 329 */     if (this.modifyListening) {
/* 330 */       ListIterator<E> it = this.list.listIterator(index);
/* 331 */       return new ModifyListIterator(this, it);
/*     */     } 
/* 333 */     return this.list.listIterator(index);
/*     */   }
/*     */   
/*     */   public E remove(int index) {
/* 337 */     checkReadOnly();
/* 338 */     init();
/* 339 */     if (this.modifyRemoveListening) {
/* 340 */       E o = (E)this.list.remove(index);
/* 341 */       modifyRemoval(o);
/* 342 */       return o;
/*     */     } 
/* 344 */     return (E)this.list.remove(index);
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/* 348 */     checkReadOnly();
/* 349 */     init();
/* 350 */     if (this.modifyRemoveListening) {
/* 351 */       boolean isRemove = this.list.remove(o);
/* 352 */       if (isRemove) {
/* 353 */         modifyRemoval(o);
/*     */       }
/* 355 */       return isRemove;
/*     */     } 
/* 357 */     return this.list.remove(o);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 361 */     checkReadOnly();
/* 362 */     init();
/* 363 */     if (this.modifyRemoveListening) {
/* 364 */       boolean changed = false;
/* 365 */       Iterator<?> it = c.iterator();
/* 366 */       while (it.hasNext()) {
/* 367 */         Object o = it.next();
/* 368 */         if (this.list.remove(o)) {
/* 369 */           modifyRemoval(o);
/* 370 */           changed = true;
/*     */         } 
/*     */       } 
/* 373 */       return changed;
/*     */     } 
/* 375 */     return this.list.removeAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 379 */     checkReadOnly();
/* 380 */     init();
/* 381 */     if (this.modifyRemoveListening) {
/* 382 */       boolean changed = false;
/* 383 */       Iterator<E> it = this.list.iterator();
/* 384 */       while (it.hasNext()) {
/* 385 */         Object o = it.next();
/* 386 */         if (!c.contains(o)) {
/* 387 */           it.remove();
/* 388 */           modifyRemoval(o);
/* 389 */           changed = true;
/*     */         } 
/*     */       } 
/* 392 */       return changed;
/*     */     } 
/* 394 */     return this.list.retainAll(c);
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/* 398 */     checkReadOnly();
/* 399 */     init();
/* 400 */     if (this.modifyListening) {
/* 401 */       E o = (E)this.list.set(index, element);
/* 402 */       modifyAddition(element);
/* 403 */       modifyRemoval(o);
/* 404 */       return o;
/*     */     } 
/* 406 */     return (E)this.list.set(index, element);
/*     */   }
/*     */   
/*     */   public int size() {
/* 410 */     init();
/* 411 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public List<E> subList(int fromIndex, int toIndex) {
/* 415 */     init();
/* 416 */     if (isReadOnly()) {
/* 417 */       return Collections.unmodifiableList(this.list.subList(fromIndex, toIndex));
/*     */     }
/* 419 */     if (this.modifyListening) {
/* 420 */       return new ModifyList(this, this.list.subList(fromIndex, toIndex));
/*     */     }
/* 422 */     return this.list.subList(fromIndex, toIndex);
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 426 */     init();
/* 427 */     return this.list.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 431 */     init();
/* 432 */     return (T[])this.list.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReadOnlyListIterator<E>
/*     */     extends Object
/*     */     implements ListIterator<E>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 3097271091406323699L;
/*     */     private final ListIterator<E> i;
/*     */     
/* 443 */     ReadOnlyListIterator(ListIterator<E> i) { this.i = i; }
/*     */ 
/*     */     
/* 446 */     public void add(E o) { throw new IllegalStateException("This collection is in ReadOnly mode"); }
/*     */ 
/*     */     
/* 449 */     public void remove() { throw new IllegalStateException("This collection is in ReadOnly mode"); }
/*     */ 
/*     */     
/* 452 */     public void set(E o) { throw new IllegalStateException("This collection is in ReadOnly mode"); }
/*     */ 
/*     */     
/* 455 */     public boolean hasNext() { return this.i.hasNext(); }
/*     */ 
/*     */     
/* 458 */     public boolean hasPrevious() { return this.i.hasPrevious(); }
/*     */ 
/*     */     
/* 461 */     public E next() { return (E)this.i.next(); }
/*     */ 
/*     */     
/* 464 */     public int nextIndex() { return this.i.nextIndex(); }
/*     */ 
/*     */     
/* 467 */     public E previous() { return (E)this.i.previous(); }
/*     */ 
/*     */     
/* 470 */     public int previousIndex() { return this.i.previousIndex(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\BeanList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */