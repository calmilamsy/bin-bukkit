/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true)
/*    */ public final class HashMultiset<E>
/*    */   extends AbstractMapBasedMultiset<E>
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 42 */   public static <E> HashMultiset<E> create() { return new HashMultiset(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public static <E> HashMultiset<E> create(int distinctElements) { return new HashMultiset(distinctElements); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E> HashMultiset<E> create(Iterable<? extends E> elements) {
/* 62 */     HashMultiset<E> multiset = create(Multisets.inferDistinctElements(elements));
/*    */     
/* 64 */     Iterables.addAll(multiset, elements);
/* 65 */     return multiset;
/*    */   }
/*    */ 
/*    */   
/* 69 */   private HashMultiset() { super(new HashMap()); }
/*    */ 
/*    */ 
/*    */   
/* 73 */   private HashMultiset(int distinctElements) { super(new HashMap(Maps.capacity(distinctElements))); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 81 */     stream.defaultWriteObject();
/* 82 */     Serialization.writeMultiset(this, stream);
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 87 */     stream.defaultReadObject();
/* 88 */     int distinctElements = Serialization.readCount(stream);
/* 89 */     setBackingMap(Maps.newHashMapWithExpectedSize(distinctElements));
/*    */     
/* 91 */     Serialization.populateMultiset(this, stream, distinctElements);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\HashMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */