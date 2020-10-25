/*    */ package com.avaje.ebeaninternal.server.el;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public final class ElComparatorCompound<T>
/*    */   extends Object
/*    */   implements Comparator<T>, ElComparator<T>
/*    */ {
/*    */   private final ElComparator<T>[] array;
/*    */   
/* 35 */   public ElComparatorCompound(ElComparator[] array) { this.array = array; }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(T o1, T o2) {
/* 40 */     for (int i = 0; i < this.array.length; i++) {
/* 41 */       int ret = this.array[i].compare(o1, o2);
/* 42 */       if (ret != 0) {
/* 43 */         return ret;
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareValue(Object value, T o2) {
/* 52 */     for (int i = 0; i < this.array.length; i++) {
/* 53 */       int ret = this.array[i].compareValue(value, o2);
/* 54 */       if (ret != 0) {
/* 55 */         return ret;
/*    */       }
/*    */     } 
/*    */     
/* 59 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElComparatorCompound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */