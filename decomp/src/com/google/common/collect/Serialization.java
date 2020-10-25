/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ final class Serialization
/*     */ {
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*  55 */   static int readCount(ObjectInputStream stream) throws IOException { return stream.readInt(); }
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
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   static <K, V> void writeMap(Map<K, V> map, ObjectOutputStream stream) throws IOException {
/*  69 */     stream.writeInt(map.size());
/*  70 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/*  71 */       stream.writeObject(entry.getKey());
/*  72 */       stream.writeObject(entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <K, V> void populateMap(Map<K, V> map, ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  83 */     int size = stream.readInt();
/*  84 */     populateMap(map, stream, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <K, V> void populateMap(Map<K, V> map, ObjectInputStream stream, int size) throws IOException, ClassNotFoundException {
/*  95 */     for (int i = 0; i < size; i++) {
/*     */       
/*  97 */       K key = (K)stream.readObject();
/*     */       
/*  99 */       V value = (V)stream.readObject();
/* 100 */       map.put(key, value);
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
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   static <E> void writeMultiset(Multiset<E> multiset, ObjectOutputStream stream) throws IOException {
/* 115 */     int entryCount = multiset.entrySet().size();
/* 116 */     stream.writeInt(entryCount);
/* 117 */     for (Multiset.Entry<E> entry : multiset.entrySet()) {
/* 118 */       stream.writeObject(entry.getElement());
/* 119 */       stream.writeInt(entry.getCount());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 131 */     int distinctElements = stream.readInt();
/* 132 */     populateMultiset(multiset, stream, distinctElements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream stream, int distinctElements) throws IOException, ClassNotFoundException {
/* 144 */     for (int i = 0; i < distinctElements; i++) {
/*     */       
/* 146 */       E element = (E)stream.readObject();
/* 147 */       int count = stream.readInt();
/* 148 */       multiset.add(element, count);
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
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   static <K, V> void writeMultimap(Multimap<K, V> multimap, ObjectOutputStream stream) throws IOException {
/* 165 */     stream.writeInt(multimap.asMap().size());
/* 166 */     for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
/* 167 */       stream.writeObject(entry.getKey());
/* 168 */       stream.writeInt(((Collection)entry.getValue()).size());
/* 169 */       for (V value : (Collection)entry.getValue()) {
/* 170 */         stream.writeObject(value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 183 */     int distinctKeys = stream.readInt();
/* 184 */     populateMultimap(multimap, stream, distinctKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream stream, int distinctKeys) throws IOException, ClassNotFoundException {
/* 196 */     for (int i = 0; i < distinctKeys; i++) {
/*     */       
/* 198 */       K key = (K)stream.readObject();
/* 199 */       Collection<V> values = multimap.get(key);
/* 200 */       int valueCount = stream.readInt();
/* 201 */       for (int j = 0; j < valueCount; j++) {
/*     */         
/* 203 */         V value = (V)stream.readObject();
/* 204 */         values.add(value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.lang.reflect.Field")
/*     */   static <T> FieldSetter<T> getFieldSetter(Class<T> clazz, String fieldName) {
/*     */     try {
/* 214 */       Field field = clazz.getDeclaredField(fieldName);
/* 215 */       return new FieldSetter(field, null);
/* 216 */     } catch (NoSuchFieldException e) {
/* 217 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @GwtCompatible(emulated = true)
/*     */   static final class FieldSetter<T>
/*     */     extends Object {
/*     */     private final Field field;
/*     */     
/*     */     private FieldSetter(Field field) {
/* 227 */       this.field = field;
/* 228 */       field.setAccessible(true);
/*     */     }
/*     */     
/*     */     @GwtIncompatible("java.lang.reflect.Field")
/*     */     void set(T instance, Object value) {
/*     */       try {
/* 234 */         this.field.set(instance, value);
/* 235 */       } catch (IllegalAccessException impossible) {
/* 236 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     @GwtIncompatible("java.lang.reflect.Field")
/*     */     void set(T instance, int value) {
/*     */       try {
/* 243 */         this.field.set(instance, Integer.valueOf(value));
/* 244 */       } catch (IllegalAccessException impossible) {
/* 245 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Serialization.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */