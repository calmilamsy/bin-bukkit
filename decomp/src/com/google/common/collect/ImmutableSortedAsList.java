/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.List;
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
/*    */ final class ImmutableSortedAsList<E>
/*    */   extends RegularImmutableList<E>
/*    */ {
/*    */   private final ImmutableSortedSet<E> set;
/*    */   
/*    */   ImmutableSortedAsList(Object[] array, int offset, int size, ImmutableSortedSet<E> set) {
/* 33 */     super(array, offset, size);
/* 34 */     this.set = set;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean contains(Object target) { return (this.set.indexOf(target) >= 0); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int indexOf(Object target) { return this.set.indexOf(target); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public int lastIndexOf(Object target) { return this.set.indexOf(target); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 55 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
/* 56 */     return (fromIndex == toIndex) ? ImmutableList.of() : (new RegularImmutableSortedSet(array(), this.set.comparator(), offset() + fromIndex, offset() + toIndex)).asList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   Object writeReplace() { return new ImmutableAsList.SerializedForm(this.set); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableSortedAsList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */