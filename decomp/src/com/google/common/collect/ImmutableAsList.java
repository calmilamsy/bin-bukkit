/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.Serializable;
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
/*    */ final class ImmutableAsList<E>
/*    */   extends RegularImmutableList<E>
/*    */ {
/*    */   private final ImmutableCollection<E> collection;
/*    */   
/*    */   ImmutableAsList(Object[] array, ImmutableCollection<E> collection) {
/* 34 */     super(array, 0, array.length);
/* 35 */     this.collection = collection;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean contains(Object target) { return this.collection.contains(target); }
/*    */ 
/*    */   
/*    */   static class SerializedForm
/*    */     implements Serializable
/*    */   {
/*    */     final ImmutableCollection<?> collection;
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/* 50 */     SerializedForm(ImmutableCollection<?> collection) { this.collection = collection; }
/*    */ 
/*    */     
/* 53 */     Object readResolve() { return this.collection.asList(); }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   private void readObject(ObjectInputStream stream) throws InvalidObjectException { throw new InvalidObjectException("Use SerializedForm"); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   Object writeReplace() { return new SerializedForm(this.collection); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableAsList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */