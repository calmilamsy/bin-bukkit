/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
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
/*     */ @GwtCompatible
/*     */ public final class SignedBytes
/*     */ {
/*     */   public static byte checkedCast(long value) {
/*  48 */     byte result = (byte)(int)value;
/*  49 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  50 */     return result;
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
/*  62 */     if (value > 127L) {
/*  63 */       return Byte.MAX_VALUE;
/*     */     }
/*  65 */     if (value < -128L) {
/*  66 */       return Byte.MIN_VALUE;
/*     */     }
/*  68 */     return (byte)(int)value;
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
/*  81 */   public static int compare(byte a, byte b) { return a - b; }
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
/*  93 */     Preconditions.checkArgument((array.length > 0));
/*  94 */     byte min = array[0];
/*  95 */     for (int i = 1; i < array.length; i++) {
/*  96 */       if (array[i] < min) {
/*  97 */         min = array[i];
/*     */       }
/*     */     } 
/* 100 */     return min;
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
/* 112 */     Preconditions.checkArgument((array.length > 0));
/* 113 */     byte max = array[0];
/* 114 */     for (int i = 1; i < array.length; i++) {
/* 115 */       if (array[i] > max) {
/* 116 */         max = array[i];
/*     */       }
/*     */     } 
/* 119 */     return max;
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
/* 132 */     Preconditions.checkNotNull(separator);
/* 133 */     if (array.length == 0) {
/* 134 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 138 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 139 */     builder.append(array[0]);
/* 140 */     for (int i = 1; i < array.length; i++) {
/* 141 */       builder.append(separator).append(array[i]);
/*     */     }
/* 143 */     return builder.toString();
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
/* 163 */   public static Comparator<byte[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<byte[]> {
/* 167 */     INSTANCE;
/*     */     
/*     */     public int compare(byte[] left, byte[] right) {
/* 170 */       int minLength = Math.min(left.length, right.length);
/* 171 */       for (int i = 0; i < minLength; i++) {
/* 172 */         int result = SignedBytes.compare(left[i], right[i]);
/* 173 */         if (result != 0) {
/* 174 */           return result;
/*     */         }
/*     */       } 
/* 177 */       return left.length - right.length;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\SignedBytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */