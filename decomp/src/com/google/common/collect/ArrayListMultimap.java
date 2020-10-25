/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public final class ArrayListMultimap<K, V>
/*     */   extends AbstractListMultimap<K, V>
/*     */ {
/*     */   private static final int DEFAULT_VALUES_PER_KEY = 10;
/*     */   @VisibleForTesting
/*     */   int expectedValuesPerKey;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  72 */   public static <K, V> ArrayListMultimap<K, V> create() { return new ArrayListMultimap(); }
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
/*  86 */   public static <K, V> ArrayListMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) { return new ArrayListMultimap(expectedKeys, expectedValuesPerKey); }
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
/*  97 */   public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) { return new ArrayListMultimap(multimap); }
/*     */ 
/*     */   
/*     */   private ArrayListMultimap() {
/* 101 */     super(new HashMap());
/* 102 */     this.expectedValuesPerKey = 10;
/*     */   }
/*     */   
/*     */   private ArrayListMultimap(int expectedKeys, int expectedValuesPerKey) {
/* 106 */     super(Maps.newHashMapWithExpectedSize(expectedKeys));
/* 107 */     Preconditions.checkArgument((expectedValuesPerKey >= 0));
/* 108 */     this.expectedValuesPerKey = expectedValuesPerKey;
/*     */   }
/*     */   
/*     */   private ArrayListMultimap(Multimap<? extends K, ? extends V> multimap) {
/* 112 */     this(multimap.keySet().size(), (multimap instanceof ArrayListMultimap) ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 10);
/*     */ 
/*     */ 
/*     */     
/* 116 */     putAll(multimap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   List<V> createCollection() { return new ArrayList(this.expectedValuesPerKey); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 131 */     for (Collection<V> collection : backingMap().values()) {
/* 132 */       ArrayList<V> arrayList = (ArrayList)collection;
/* 133 */       arrayList.trimToSize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 143 */     stream.defaultWriteObject();
/* 144 */     stream.writeInt(this.expectedValuesPerKey);
/* 145 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 150 */     stream.defaultReadObject();
/* 151 */     this.expectedValuesPerKey = stream.readInt();
/* 152 */     int distinctKeys = Serialization.readCount(stream);
/* 153 */     Map<K, Collection<V>> map = Maps.newHashMapWithExpectedSize(distinctKeys);
/* 154 */     setMap(map);
/* 155 */     Serialization.populateMultimap(this, stream, distinctKeys);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ArrayListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */