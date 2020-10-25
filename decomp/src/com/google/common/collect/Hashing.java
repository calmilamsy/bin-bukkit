/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
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
/*    */ @GwtCompatible
/*    */ final class Hashing
/*    */ {
/*    */   private static final int MAX_TABLE_SIZE = 1073741824;
/*    */   private static final int CUTOFF = 536870912;
/*    */   
/*    */   static int smear(int hashCode) {
/* 38 */     hashCode ^= hashCode >>> 20 ^ hashCode >>> 12;
/* 39 */     return hashCode ^ hashCode >>> 7 ^ hashCode >>> 4;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static int chooseTableSize(int setSize) {
/* 50 */     if (setSize < 536870912) {
/* 51 */       return Integer.highestOneBit(setSize) << 2;
/*    */     }
/*    */ 
/*    */     
/* 55 */     Preconditions.checkArgument((setSize < 1073741824), "collection too large");
/* 56 */     return 1073741824;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Hashing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */