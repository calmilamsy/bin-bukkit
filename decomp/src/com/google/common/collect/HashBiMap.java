/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
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
/*     */ @GwtCompatible
/*     */ public final class HashBiMap<K, V>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  45 */   public static <K, V> HashBiMap<K, V> create() { return new HashBiMap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static <K, V> HashBiMap<K, V> create(int expectedSize) { return new HashBiMap(expectedSize); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
/*  66 */     HashBiMap<K, V> bimap = create(map.size());
/*  67 */     bimap.putAll(map);
/*  68 */     return bimap;
/*     */   }
/*     */ 
/*     */   
/*  72 */   private HashBiMap() { super(new HashMap(), new HashMap()); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   private HashBiMap(int expectedSize) { super(new HashMap(Maps.capacity(expectedSize)), new HashMap(Maps.capacity(expectedSize))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public V put(@Nullable K key, @Nullable V value) { return (V)super.put(key, value); }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public V forcePut(@Nullable K key, @Nullable V value) { return (V)super.forcePut(key, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/*  95 */     stream.defaultWriteObject();
/*  96 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 101 */     stream.defaultReadObject();
/* 102 */     int size = Serialization.readCount(stream);
/* 103 */     setDelegates(Maps.newHashMapWithExpectedSize(size), Maps.newHashMapWithExpectedSize(size));
/*     */     
/* 105 */     Serialization.populateMap(this, stream, size);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\HashBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */