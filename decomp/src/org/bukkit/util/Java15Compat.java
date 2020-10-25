/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class Java15Compat
/*    */ {
/*    */   public static <T> T[] Arrays_copyOfRange(T[] original, int start, int end) {
/*  8 */     if (original.length >= start && 0 <= start) {
/*  9 */       if (start <= end) {
/* 10 */         int length = end - start;
/* 11 */         int copyLength = Math.min(length, original.length - start);
/* 12 */         T[] copy = (T[])(Object[])Array.newInstance(original.getClass().getComponentType(), length);
/*    */         
/* 14 */         System.arraycopy(original, start, copy, 0, copyLength);
/* 15 */         return copy;
/*    */       } 
/* 17 */       throw new IllegalArgumentException();
/*    */     } 
/* 19 */     throw new ArrayIndexOutOfBoundsException();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\Java15Compat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */