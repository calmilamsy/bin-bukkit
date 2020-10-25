/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class TreeMultimap<K, V>
/*     */   extends AbstractSortedSetMultimap<K, V>
/*     */ {
/*     */   private Comparator<? super K> keyComparator;
/*     */   private Comparator<? super V> valueComparator;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*  83 */   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() { return new TreeMultimap(Ordering.natural(), Ordering.natural()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> keyComparator, Comparator<? super V> valueComparator) { return new TreeMultimap((Comparator)Preconditions.checkNotNull(keyComparator), (Comparator)Preconditions.checkNotNull(valueComparator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) { return new TreeMultimap(Ordering.natural(), Ordering.natural(), multimap); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   TreeMultimap() { this(null, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TreeMultimap(@Nullable Comparator<? super K> keyComparator, @Nullable Comparator<? super V> valueComparator) {
/* 124 */     super((keyComparator == null) ? new TreeMap() : new TreeMap(keyComparator));
/*     */ 
/*     */     
/* 127 */     this.keyComparator = keyComparator;
/* 128 */     this.valueComparator = valueComparator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TreeMultimap(Comparator<? super K> keyComparator, Comparator<? super V> valueComparator, Multimap<? extends K, ? extends V> multimap) {
/* 134 */     this(keyComparator, valueComparator);
/* 135 */     putAll(multimap);
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
/* 147 */   SortedSet<V> createCollection() { return (this.valueComparator == null) ? new TreeSet() : new TreeSet(this.valueComparator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public Comparator<? super K> keyComparator() { return this.keyComparator; }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public Comparator<? super V> valueComparator() { return this.valueComparator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public SortedSet<K> keySet() { return (SortedSet)super.keySet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public SortedMap<K, Collection<V>> asMap() { return (SortedMap)super.asMap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 190 */     stream.defaultWriteObject();
/* 191 */     stream.writeObject(keyComparator());
/* 192 */     stream.writeObject(valueComparator());
/* 193 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 199 */     stream.defaultReadObject();
/* 200 */     this.keyComparator = (Comparator)stream.readObject();
/* 201 */     this.valueComparator = (Comparator)stream.readObject();
/* 202 */     setMap(new TreeMap(this.keyComparator));
/* 203 */     Serialization.populateMultimap(this, stream);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\TreeMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */