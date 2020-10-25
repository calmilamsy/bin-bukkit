/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EnumMap;
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
/*     */ public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   private Class<K> keyType;
/*     */   private Class<V> valueType;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  49 */   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> keyType, Class<V> valueType) { return new EnumBiMap(keyType, valueType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
/*  64 */     EnumBiMap<K, V> bimap = create(inferKeyType(map), inferValueType(map));
/*  65 */     bimap.putAll(map);
/*  66 */     return bimap;
/*     */   }
/*     */   
/*     */   private EnumBiMap(Class<K> keyType, Class<V> valueType) {
/*  70 */     super(new EnumMap(keyType), new EnumMap(valueType));
/*  71 */     this.keyType = keyType;
/*  72 */     this.valueType = valueType;
/*     */   }
/*     */   
/*     */   static <K extends Enum<K>> Class<K> inferKeyType(Map<K, ?> map) {
/*  76 */     if (map instanceof EnumBiMap) {
/*  77 */       return ((EnumBiMap)map).keyType();
/*     */     }
/*  79 */     if (map instanceof EnumHashBiMap) {
/*  80 */       return ((EnumHashBiMap)map).keyType();
/*     */     }
/*  82 */     Preconditions.checkArgument(!map.isEmpty());
/*  83 */     return ((Enum)map.keySet().iterator().next()).getDeclaringClass();
/*     */   }
/*     */   
/*     */   private static <V extends Enum<V>> Class<V> inferValueType(Map<?, V> map) {
/*  87 */     if (map instanceof EnumBiMap) {
/*  88 */       return ((EnumBiMap)map).valueType;
/*     */     }
/*  90 */     Preconditions.checkArgument(!map.isEmpty());
/*  91 */     return ((Enum)map.values().iterator().next()).getDeclaringClass();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public Class<K> keyType() { return this.keyType; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Class<V> valueType() { return this.valueType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 109 */     stream.defaultWriteObject();
/* 110 */     stream.writeObject(this.keyType);
/* 111 */     stream.writeObject(this.valueType);
/* 112 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 118 */     stream.defaultReadObject();
/* 119 */     this.keyType = (Class)stream.readObject();
/* 120 */     this.valueType = (Class)stream.readObject();
/* 121 */     setDelegates(new EnumMap(this.keyType), new EnumMap(this.valueType));
/* 122 */     Serialization.populateMap(this, stream);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EnumBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */