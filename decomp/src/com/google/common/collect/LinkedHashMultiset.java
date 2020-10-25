/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true)
/*    */ public final class LinkedHashMultiset<E>
/*    */   extends AbstractMapBasedMultiset<E>
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 48 */   public static <E> LinkedHashMultiset<E> create() { return new LinkedHashMultiset(); }
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
/* 59 */   public static <E> LinkedHashMultiset<E> create(int distinctElements) { return new LinkedHashMultiset(distinctElements); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E> LinkedHashMultiset<E> create(Iterable<? extends E> elements) {
/* 69 */     LinkedHashMultiset<E> multiset = create(Multisets.inferDistinctElements(elements));
/*    */     
/* 71 */     Iterables.addAll(multiset, elements);
/* 72 */     return multiset;
/*    */   }
/*    */ 
/*    */   
/* 76 */   private LinkedHashMultiset() { super(new LinkedHashMap()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   private LinkedHashMultiset(int distinctElements) { super(new LinkedHashMap(Maps.capacity(distinctElements))); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 89 */     stream.defaultWriteObject();
/* 90 */     Serialization.writeMultiset(this, stream);
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 95 */     stream.defaultReadObject();
/* 96 */     int distinctElements = Serialization.readCount(stream);
/* 97 */     setBackingMap(new LinkedHashMap(Maps.capacity(distinctElements)));
/*    */     
/* 99 */     Serialization.populateMultiset(this, stream, distinctElements);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\LinkedHashMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */