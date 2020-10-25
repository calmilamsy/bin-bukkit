/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumMap;
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
/*    */ @GwtCompatible
/*    */ public final class EnumMultiset<E extends Enum<E>>
/*    */   extends AbstractMapBasedMultiset<E>
/*    */ {
/*    */   private Class<E> type;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 40 */   public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> type) { return new EnumMultiset(type); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> elements) {
/* 51 */     Iterator<E> iterator = elements.iterator();
/* 52 */     Preconditions.checkArgument(iterator.hasNext(), "EnumMultiset constructor passed empty Iterable");
/*    */     
/* 54 */     EnumMultiset<E> multiset = new EnumMultiset<E>(((Enum)iterator.next()).getDeclaringClass());
/*    */     
/* 56 */     Iterables.addAll(multiset, elements);
/* 57 */     return multiset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private EnumMultiset(Class<E> type) {
/* 64 */     super(new EnumMap(type));
/* 65 */     this.type = type;
/*    */   }
/*    */   
/*    */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 69 */     stream.defaultWriteObject();
/* 70 */     stream.writeObject(this.type);
/* 71 */     Serialization.writeMultiset(this, stream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 81 */     stream.defaultReadObject();
/*    */     
/* 83 */     Class<E> localType = (Class)stream.readObject();
/* 84 */     this.type = localType;
/* 85 */     setBackingMap(new EnumMap(this.type));
/* 86 */     Serialization.populateMultiset(this, stream);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\EnumMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */