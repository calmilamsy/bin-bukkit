/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
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
/*     */ public final class UnsignedBytes
/*     */ {
/*     */   public static byte checkedCast(long value) {
/*  48 */     Preconditions.checkArgument((value >> 8 == 0L), "out of range: %s", new Object[] { Long.valueOf(value) });
/*  49 */     return (byte)(int)value;
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
/*     */   public static byte saturatedCast(long value) {
/*  61 */     if (value > 255L) {
/*  62 */       return -1;
/*     */     }
/*  64 */     if (value < 0L) {
/*  65 */       return 0;
/*     */     }
/*  67 */     return (byte)(int)value;
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
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static int compare(byte a, byte b) { return (a & 0xFF) - (b & 0xFF); }
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
/*     */   public static byte min(byte... array) {
/*  94 */     Preconditions.checkArgument((array.length > 0));
/*  95 */     int min = array[0] & 0xFF;
/*  96 */     for (int i = 1; i < array.length; i++) {
/*  97 */       int next = array[i] & 0xFF;
/*  98 */       if (next < min) {
/*  99 */         min = next;
/*     */       }
/*     */     } 
/* 102 */     return (byte)min;
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
/*     */   public static byte max(byte... array) {
/* 114 */     Preconditions.checkArgument((array.length > 0));
/* 115 */     int max = array[0] & 0xFF;
/* 116 */     for (int i = 1; i < array.length; i++) {
/* 117 */       int next = array[i] & 0xFF;
/* 118 */       if (next > max) {
/* 119 */         max = next;
/*     */       }
/*     */     } 
/* 122 */     return (byte)max;
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
/*     */   public static String join(String separator, byte... array) {
/* 135 */     Preconditions.checkNotNull(separator);
/* 136 */     if (array.length == 0) {
/* 137 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 141 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 142 */     builder.append(array[0] & 0xFF);
/* 143 */     for (int i = 1; i < array.length; i++) {
/* 144 */       builder.append(separator).append(array[i] & 0xFF);
/*     */     }
/* 146 */     return builder.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static Comparator<byte[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<byte[]> {
/* 170 */     INSTANCE;
/*     */     
/*     */     public int compare(byte[] left, byte[] right) {
/* 173 */       int minLength = Math.min(left.length, right.length);
/* 174 */       for (int i = 0; i < minLength; i++) {
/* 175 */         int result = UnsignedBytes.compare(left[i], right[i]);
/* 176 */         if (result != 0) {
/* 177 */           return result;
/*     */         }
/*     */       } 
/* 180 */       return left.length - right.length;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\UnsignedBytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */