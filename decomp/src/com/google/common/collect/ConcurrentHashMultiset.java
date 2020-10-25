/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public final class ConcurrentHashMultiset<E>
/*     */   extends AbstractMultiset<E>
/*     */   implements Serializable
/*     */ {
/*     */   private final ConcurrentMap<E, Integer> countMap;
/*     */   private EntrySet entrySet;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   private static class FieldSettersHolder
/*     */   {
/*  64 */     static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static <E> ConcurrentHashMultiset<E> create() { return new ConcurrentHashMultiset(new ConcurrentHashMap()); }
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
/*     */   public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
/*  86 */     ConcurrentHashMultiset<E> multiset = create();
/*  87 */     Iterables.addAll(multiset, elements);
/*  88 */     return multiset;
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
/*     */   @VisibleForTesting
/*     */   ConcurrentHashMultiset(ConcurrentMap<E, Integer> countMap) {
/* 104 */     Preconditions.checkArgument(countMap.isEmpty());
/* 105 */     this.countMap = countMap;
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
/*     */   public int count(@Nullable Object element) {
/*     */     try {
/* 118 */       return unbox((Integer)this.countMap.get(element));
/* 119 */     } catch (NullPointerException e) {
/* 120 */       return 0;
/* 121 */     } catch (ClassCastException e) {
/* 122 */       return 0;
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
/*     */   public int size() {
/* 134 */     long sum = 0L;
/* 135 */     for (Integer value : this.countMap.values()) {
/* 136 */       sum += value.intValue();
/*     */     }
/* 138 */     return (int)Math.min(sum, 2147483647L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public Object[] toArray() { return snapshot().toArray(); }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public <T> T[] toArray(T[] array) { return (T[])snapshot().toArray(array); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<E> snapshot() {
/* 159 */     List<E> list = Lists.newArrayListWithExpectedSize(size());
/* 160 */     for (Multiset.Entry<E> entry : entrySet()) {
/* 161 */       E element = (E)entry.getElement();
/* 162 */       for (int i = entry.getCount(); i > 0; i--) {
/* 163 */         list.add(element);
/*     */       }
/*     */     } 
/* 166 */     return list;
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
/*     */   public int add(E element, int occurrences) {
/*     */     int current;
/* 182 */     if (occurrences == 0) {
/* 183 */       return count(element);
/*     */     }
/* 185 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/*     */     while (true)
/* 188 */     { current = count(element);
/* 189 */       if (current == 0) {
/* 190 */         if (this.countMap.putIfAbsent(element, Integer.valueOf(occurrences)) == null)
/* 191 */           return 0; 
/*     */         continue;
/*     */       } 
/* 194 */       Preconditions.checkArgument((occurrences <= Integer.MAX_VALUE - current), "Overflow adding %s occurrences to a count of %s", new Object[] { Integer.valueOf(occurrences), Integer.valueOf(current) });
/*     */ 
/*     */       
/* 197 */       int next = current + occurrences;
/* 198 */       if (this.countMap.replace(element, Integer.valueOf(current), Integer.valueOf(next)))
/* 199 */         break;  }  return current;
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
/*     */   
/*     */   public int remove(@Nullable Object element, int occurrences) {
/*     */     int current;
/* 217 */     if (occurrences == 0) {
/* 218 */       return count(element);
/*     */     }
/* 220 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/*     */     while (true)
/* 223 */     { current = count(element);
/* 224 */       if (current == 0) {
/* 225 */         return 0;
/*     */       }
/* 227 */       if (occurrences >= current) {
/* 228 */         if (this.countMap.remove(element, Integer.valueOf(current))) {
/* 229 */           return current;
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 234 */       E casted = (E)element;
/*     */       
/* 236 */       if (this.countMap.replace(casted, Integer.valueOf(current), Integer.valueOf(current - occurrences)))
/* 237 */         break;  }  return current;
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
/*     */   private int removeAllOccurrences(@Nullable Object element) {
/*     */     try {
/* 254 */       return unbox((Integer)this.countMap.remove(element));
/* 255 */     } catch (NullPointerException e) {
/* 256 */       return 0;
/* 257 */     } catch (ClassCastException e) {
/* 258 */       return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeExactly(@Nullable Object element, int occurrences) {
/* 275 */     if (occurrences == 0) {
/* 276 */       return true;
/*     */     }
/* 278 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/*     */     while (true)
/* 281 */     { int current = count(element);
/* 282 */       if (occurrences > current) {
/* 283 */         return false;
/*     */       }
/* 285 */       if (occurrences == current) {
/* 286 */         if (this.countMap.remove(element, Integer.valueOf(occurrences))) {
/* 287 */           return true;
/*     */         }
/*     */         continue;
/*     */       } 
/* 291 */       E casted = (E)element;
/* 292 */       if (this.countMap.replace(casted, Integer.valueOf(current), Integer.valueOf(current - occurrences)))
/* 293 */         break;  }  return true;
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
/*     */   public int setCount(E element, int count) {
/* 308 */     Multisets.checkNonnegative(count, "count");
/* 309 */     return (count == 0) ? removeAllOccurrences(element) : unbox((Integer)this.countMap.put(element, Integer.valueOf(count)));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setCount(E element, int oldCount, int newCount) {
/* 328 */     Multisets.checkNonnegative(oldCount, "oldCount");
/* 329 */     Multisets.checkNonnegative(newCount, "newCount");
/* 330 */     if (newCount == 0) {
/* 331 */       if (oldCount == 0)
/*     */       {
/* 333 */         return !this.countMap.containsKey(element);
/*     */       }
/* 335 */       return this.countMap.remove(element, Integer.valueOf(oldCount));
/*     */     } 
/*     */     
/* 338 */     if (oldCount == 0) {
/* 339 */       return (this.countMap.putIfAbsent(element, Integer.valueOf(newCount)) == null);
/*     */     }
/* 341 */     return this.countMap.replace(element, Integer.valueOf(oldCount), Integer.valueOf(newCount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Set<E> createElementSet() {
/* 347 */     final Set<E> delegate = this.countMap.keySet();
/* 348 */     return new ForwardingSet<E>()
/*     */       {
/* 350 */         protected Set<E> delegate() { return delegate; }
/*     */         
/*     */         public boolean remove(Object object) {
/*     */           try {
/* 354 */             return delegate.remove(object);
/* 355 */           } catch (NullPointerException e) {
/* 356 */             return false;
/* 357 */           } catch (ClassCastException e) {
/* 358 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/* 367 */     EntrySet result = this.entrySet;
/* 368 */     if (result == null) {
/* 369 */       this.entrySet = result = new EntrySet(null);
/*     */     }
/* 371 */     return result;
/*     */   }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Multiset.Entry<E>> { private EntrySet() {}
/*     */     
/* 376 */     public int size() { return ConcurrentHashMultiset.this.countMap.size(); }
/*     */ 
/*     */ 
/*     */     
/* 380 */     public boolean isEmpty() { return ConcurrentHashMultiset.this.countMap.isEmpty(); }
/*     */ 
/*     */     
/*     */     public boolean contains(Object object) {
/* 384 */       if (object instanceof Multiset.Entry) {
/* 385 */         Multiset.Entry<?> entry = (Multiset.Entry)object;
/* 386 */         Object element = entry.getElement();
/* 387 */         int entryCount = entry.getCount();
/* 388 */         return (entryCount > 0 && ConcurrentHashMultiset.this.count(element) == entryCount);
/*     */       } 
/* 390 */       return false;
/*     */     }
/*     */     
/*     */     public Iterator<Multiset.Entry<E>> iterator() {
/* 394 */       final Iterator<Map.Entry<E, Integer>> backingIterator = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
/*     */       
/* 396 */       return new Iterator<Multiset.Entry<E>>()
/*     */         {
/* 398 */           public boolean hasNext() { return backingIterator.hasNext(); }
/*     */ 
/*     */           
/*     */           public Multiset.Entry<E> next() {
/* 402 */             Map.Entry<E, Integer> backingEntry = (Map.Entry)backingIterator.next();
/* 403 */             return Multisets.immutableEntry(backingEntry.getKey(), ((Integer)backingEntry.getValue()).intValue());
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 408 */           public void remove() { backingIterator.remove(); }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     public Object[] toArray() { return snapshot().toArray(); }
/*     */ 
/*     */ 
/*     */     
/* 423 */     public <T> T[] toArray(T[] array) { return (T[])snapshot().toArray(array); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List<Multiset.Entry<E>> snapshot() {
/* 431 */       List<Multiset.Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
/* 432 */       for (Multiset.Entry<E> entry : this) {
/* 433 */         list.add(entry);
/*     */       }
/* 435 */       return list;
/*     */     }
/*     */     
/*     */     public boolean remove(Object object) {
/* 439 */       if (object instanceof Multiset.Entry) {
/* 440 */         Multiset.Entry<?> entry = (Multiset.Entry)object;
/* 441 */         Object element = entry.getElement();
/* 442 */         int entryCount = entry.getCount();
/* 443 */         return ConcurrentHashMultiset.this.countMap.remove(element, Integer.valueOf(entryCount));
/*     */       } 
/* 445 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 449 */     public void clear() { ConcurrentHashMultiset.this.countMap.clear(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     public int hashCode() { return ConcurrentHashMultiset.this.countMap.hashCode(); } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 464 */   private static int unbox(Integer i) { return (i == null) ? 0 : i.intValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 472 */     stream.defaultWriteObject();
/*     */     
/* 474 */     Serialization.writeMultiset(HashMultiset.create(this), stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 479 */     stream.defaultReadObject();
/* 480 */     FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, new ConcurrentHashMap());
/*     */     
/* 482 */     Serialization.populateMultiset(this, stream);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ConcurrentHashMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */