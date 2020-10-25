/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true)
/*     */ public class ImmutableMultiset<E>
/*     */   extends ImmutableCollection<E>
/*     */   implements Multiset<E>
/*     */ {
/*     */   private final ImmutableMap<E, Integer> map;
/*     */   private final int size;
/*     */   private ImmutableSet<Multiset.Entry<E>> entrySet;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  54 */   public static <E> ImmutableMultiset<E> of() { return EmptyImmutableMultiset.INSTANCE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static <E> ImmutableMultiset<E> of(E... elements) { return copyOf(Arrays.asList(elements)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> elements) {
/*  92 */     if (elements instanceof ImmutableMultiset)
/*     */     {
/*  94 */       return (ImmutableMultiset)elements;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  99 */     Multiset<? extends E> multiset = (elements instanceof Multiset) ? (Multiset)elements : LinkedHashMultiset.create(elements);
/*     */ 
/*     */ 
/*     */     
/* 103 */     return copyOfInternal(multiset);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> ImmutableMultiset<E> copyOfInternal(Multiset<? extends E> multiset) {
/* 108 */     long size = 0L;
/* 109 */     ImmutableMap.Builder<E, Integer> builder = ImmutableMap.builder();
/*     */     
/* 111 */     for (Multiset.Entry<? extends E> entry : multiset.entrySet()) {
/* 112 */       int count = entry.getCount();
/* 113 */       if (count > 0) {
/*     */ 
/*     */         
/* 116 */         builder.put(entry.getElement(), Integer.valueOf(count));
/* 117 */         size += count;
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (size == 0L) {
/* 122 */       return of();
/*     */     }
/* 124 */     return new ImmutableMultiset(builder.build(), (int)Math.min(size, 2147483647L));
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
/*     */   public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> elements) {
/* 140 */     Multiset<E> multiset = LinkedHashMultiset.create();
/* 141 */     Iterators.addAll(multiset, elements);
/* 142 */     return copyOfInternal(multiset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FieldSettersHolder
/*     */   {
/* 154 */     static final Serialization.FieldSetter<ImmutableMultiset> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultiset.class, "map");
/*     */     
/* 156 */     static final Serialization.FieldSetter<ImmutableMultiset> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultiset.class, "size");
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMultiset(ImmutableMap<E, Integer> map, int size) {
/* 161 */     this.map = map;
/* 162 */     this.size = size;
/*     */   }
/*     */   
/*     */   public int count(@Nullable Object element) {
/* 166 */     Integer value = (Integer)this.map.get(element);
/* 167 */     return (value == null) ? 0 : value.intValue();
/*     */   }
/*     */   
/*     */   public UnmodifiableIterator<E> iterator() {
/* 171 */     final Iterator<Map.Entry<E, Integer>> mapIterator = this.map.entrySet().iterator();
/*     */ 
/*     */     
/* 174 */     return new UnmodifiableIterator<E>()
/*     */       {
/*     */         int remaining;
/*     */         E element;
/*     */         
/* 179 */         public boolean hasNext() { return (super.remaining > 0 || mapIterator.hasNext()); }
/*     */ 
/*     */         
/*     */         public E next() {
/* 183 */           if (super.remaining <= 0) {
/* 184 */             Map.Entry<E, Integer> entry = (Map.Entry)mapIterator.next();
/* 185 */             super.element = entry.getKey();
/* 186 */             super.remaining = ((Integer)entry.getValue()).intValue();
/*     */           } 
/* 188 */           super.remaining--;
/* 189 */           return (E)super.element;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/* 195 */   public int size() { return this.size; }
/*     */ 
/*     */ 
/*     */   
/* 199 */   public boolean contains(@Nullable Object element) { return this.map.containsKey(element); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public int add(E element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public int remove(Object element, int occurrences) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public int setCount(E element, int count) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   public boolean setCount(E element, int oldCount, int newCount) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 239 */     if (object == this) {
/* 240 */       return true;
/*     */     }
/* 242 */     if (object instanceof Multiset) {
/* 243 */       Multiset<?> that = (Multiset)object;
/* 244 */       if (size() != that.size()) {
/* 245 */         return false;
/*     */       }
/* 247 */       for (Multiset.Entry<?> entry : that.entrySet()) {
/* 248 */         if (count(entry.getElement()) != entry.getCount()) {
/* 249 */           return false;
/*     */         }
/*     */       } 
/* 252 */       return true;
/*     */     } 
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 259 */   public int hashCode() { return this.map.hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 263 */   public String toString() { return entrySet().toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public Set<E> elementSet() { return this.map.keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/* 277 */     ImmutableSet<Multiset.Entry<E>> es = this.entrySet;
/* 278 */     return (es == null) ? (this.entrySet = new EntrySet(this)) : es;
/*     */   }
/*     */   
/*     */   private static class EntrySet<E> extends ImmutableSet<Multiset.Entry<E>> {
/*     */     final ImmutableMultiset<E> multiset;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 285 */     public EntrySet(ImmutableMultiset<E> multiset) { this.multiset = multiset; }
/*     */ 
/*     */     
/*     */     public UnmodifiableIterator<Multiset.Entry<E>> iterator() {
/* 289 */       final Iterator<Map.Entry<E, Integer>> mapIterator = this.multiset.map.entrySet().iterator();
/*     */       
/* 291 */       return new UnmodifiableIterator<Multiset.Entry<E>>()
/*     */         {
/* 293 */           public boolean hasNext() { return mapIterator.hasNext(); }
/*     */           
/*     */           public Multiset.Entry<E> next() {
/* 296 */             Map.Entry<E, Integer> mapEntry = (Map.Entry)mapIterator.next();
/* 297 */             return Multisets.immutableEntry(mapEntry.getKey(), ((Integer)mapEntry.getValue()).intValue());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 304 */     public int size() { return this.multiset.map.size(); }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 308 */       if (o instanceof Multiset.Entry) {
/* 309 */         Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 310 */         if (entry.getCount() <= 0) {
/* 311 */           return false;
/*     */         }
/* 313 */         int count = this.multiset.count(entry.getElement());
/* 314 */         return (count == entry.getCount());
/*     */       } 
/* 316 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 321 */       Object[] newArray = new Object[size()];
/* 322 */       return toArray(newArray);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] other) {
/* 327 */       int size = size();
/* 328 */       if (other.length < size) {
/* 329 */         other = (T[])ObjectArrays.newArray(other, size);
/* 330 */       } else if (other.length > size) {
/* 331 */         other[size] = null;
/*     */       } 
/*     */ 
/*     */       
/* 335 */       T[] arrayOfT = other;
/* 336 */       int index = 0;
/* 337 */       for (Multiset.Entry<?> element : this) {
/* 338 */         arrayOfT[index++] = element;
/*     */       }
/* 340 */       return other;
/*     */     }
/*     */ 
/*     */     
/* 344 */     public int hashCode() { return this.multiset.map.hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     Object writeReplace() { return this; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 360 */     stream.defaultWriteObject();
/* 361 */     Serialization.writeMultiset(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 366 */     stream.defaultReadObject();
/* 367 */     int entryCount = stream.readInt();
/* 368 */     ImmutableMap.Builder<E, Integer> builder = ImmutableMap.builder();
/* 369 */     long tmpSize = 0L;
/* 370 */     for (int i = 0; i < entryCount; i++) {
/*     */       
/* 372 */       E element = (E)stream.readObject();
/* 373 */       int count = stream.readInt();
/* 374 */       if (count <= 0) {
/* 375 */         throw new InvalidObjectException("Invalid count " + count);
/*     */       }
/* 377 */       builder.put(element, Integer.valueOf(count));
/* 378 */       tmpSize += count;
/*     */     } 
/*     */     
/* 381 */     FieldSettersHolder.MAP_FIELD_SETTER.set(this, builder.build());
/* 382 */     FieldSettersHolder.SIZE_FIELD_SETTER.set(this, (int)Math.min(tmpSize, 2147483647L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   Object writeReplace() { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 398 */   public static <E> Builder<E> builder() { return new Builder(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<E>
/*     */     extends ImmutableCollection.Builder<E>
/*     */   {
/* 420 */     private final Multiset<E> contents = LinkedHashMultiset.create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> add(E element) {
/* 436 */       this.contents.add(Preconditions.checkNotNull(element));
/* 437 */       return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<E> addCopies(E element, int occurrences) {
/* 454 */       this.contents.add(Preconditions.checkNotNull(element), occurrences);
/* 455 */       return this;
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
/*     */     
/*     */     public Builder<E> setCount(E element, int count) {
/* 469 */       this.contents.setCount(Preconditions.checkNotNull(element), count);
/* 470 */       return this;
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
/*     */     public Builder<E> add(E... elements) {
/* 482 */       super.add(elements);
/* 483 */       return this;
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
/*     */     public Builder<E> addAll(Iterable<? extends E> elements) {
/* 496 */       if (elements instanceof Multiset) {
/*     */         
/* 498 */         Multiset<? extends E> multiset = (Multiset)elements;
/* 499 */         for (Multiset.Entry<? extends E> entry : multiset.entrySet()) {
/* 500 */           addCopies(entry.getElement(), entry.getCount());
/*     */         }
/*     */       } else {
/* 503 */         super.addAll(elements);
/*     */       } 
/* 505 */       return this;
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
/*     */     public Builder<E> addAll(Iterator<? extends E> elements) {
/* 517 */       super.addAll(elements);
/* 518 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 526 */     public ImmutableMultiset<E> build() { return ImmutableMultiset.copyOf(this.contents); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */