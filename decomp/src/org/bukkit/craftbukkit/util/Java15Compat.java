/*    */ package org.bukkit.craftbukkit.util;
/*    */ import java.lang.reflect.Array;
/*    */ import org.bukkit.util.Java15Compat;
/*    */ 
/*    */ public class Java15Compat {
/*    */   public static <T> T[] Arrays_copyOf(T[] original, int newLength) {
/*  7 */     if (0 <= newLength) {
/*  8 */       return (T[])Java15Compat.Arrays_copyOfRange(original, 0, newLength);
/*    */     }
/* 10 */     throw new NegativeArraySizeException();
/*    */   }
/*    */   
/*    */   public static long[] Arrays_copyOf(long[] original, int newLength) {
/* 14 */     if (0 <= newLength) {
/* 15 */       return Arrays_copyOfRange(original, 0, newLength);
/*    */     }
/* 17 */     throw new NegativeArraySizeException();
/*    */   }
/*    */   
/*    */   private static long[] Arrays_copyOfRange(long[] original, int start, int end) {
/* 21 */     if (original.length >= start && 0 <= start) {
/* 22 */       if (start <= end) {
/* 23 */         int length = end - start;
/* 24 */         int copyLength = Math.min(length, original.length - start);
/* 25 */         long[] copy = (long[])Array.newInstance(original.getClass().getComponentType(), length);
/* 26 */         System.arraycopy(original, start, copy, 0, copyLength);
/* 27 */         return copy;
/*    */       } 
/* 29 */       throw new IllegalArgumentException();
/*    */     } 
/* 31 */     throw new ArrayIndexOutOfBoundsException();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\Java15Compat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */