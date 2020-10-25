/*     */ package com.google.common.collect;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EnumMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EnumHashBiMap<K extends Enum<K>, V>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   private Class<K> keyType;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  48 */   public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> keyType) { return new EnumHashBiMap(keyType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
/*  63 */     EnumHashBiMap<K, V> bimap = create(EnumBiMap.inferKeyType(map));
/*  64 */     bimap.putAll(map);
/*  65 */     return bimap;
/*     */   }
/*     */   
/*     */   private EnumHashBiMap(Class<K> keyType) {
/*  69 */     super(new EnumMap(keyType), Maps.newHashMapWithExpectedSize((Enum[])keyType.getEnumConstants().length));
/*     */     
/*  71 */     this.keyType = keyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public V put(K key, @Nullable V value) { return (V)super.put(key, value); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public V forcePut(K key, @Nullable V value) { return (V)super.forcePut(key, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public Class<K> keyType() { return this.keyType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/*  94 */     stream.defaultWriteObject();
/*  95 */     stream.writeObject(this.keyType);
/*  96 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 102 */     stream.defaultReadObject();
/* 103 */     this.keyType = (Class)stream.readObject();
/* 104 */     setDelegates(new EnumMap(this.keyType), new HashMap((Enum[])this.keyType.getEnumConstants().length * 3 / 2));
/*     */     
/* 106 */     Serialization.populateMap(this, stream);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EnumHashBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */