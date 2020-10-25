/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ public final class HashMultimap<K, V>
/*     */   extends AbstractSetMultimap<K, V>
/*     */ {
/*     */   private static final int DEFAULT_VALUES_PER_KEY = 8;
/*     */   @VisibleForTesting
/*  52 */   int expectedValuesPerKey = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static <K, V> HashMultimap<K, V> create() { return new HashMultimap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static <K, V> HashMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) { return new HashMultimap(expectedKeys, expectedValuesPerKey); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static <K, V> HashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) { return new HashMultimap(multimap); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   private HashMultimap() { super(new HashMap()); }
/*     */ 
/*     */   
/*     */   private HashMultimap(int expectedKeys, int expectedValuesPerKey) {
/*  94 */     super(Maps.newHashMapWithExpectedSize(expectedKeys));
/*  95 */     Preconditions.checkArgument((expectedValuesPerKey >= 0));
/*  96 */     this.expectedValuesPerKey = expectedValuesPerKey;
/*     */   }
/*     */   
/*     */   private HashMultimap(Multimap<? extends K, ? extends V> multimap) {
/* 100 */     super(Maps.newHashMapWithExpectedSize(multimap.keySet().size()));
/*     */     
/* 102 */     putAll(multimap);
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
/* 113 */   Set<V> createCollection() { return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 122 */     stream.defaultWriteObject();
/* 123 */     stream.writeInt(this.expectedValuesPerKey);
/* 124 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 129 */     stream.defaultReadObject();
/* 130 */     this.expectedValuesPerKey = stream.readInt();
/* 131 */     int distinctKeys = Serialization.readCount(stream);
/* 132 */     Map<K, Collection<V>> map = Maps.newHashMapWithExpectedSize(distinctKeys);
/* 133 */     setMap(map);
/* 134 */     Serialization.populateMultimap(this, stream, distinctKeys);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\HashMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */