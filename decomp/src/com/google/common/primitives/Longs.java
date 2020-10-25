/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Longs
/*     */ {
/*     */   public static final int BYTES = 8;
/*     */   
/*  61 */   public static int hashCode(long value) { return (int)(value ^ value >>> 32); }
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
/*  74 */   public static int compare(long a, long b) { return (a < b) ? -1 : ((a > b) ? 1 : 0); }
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
/*     */   public static boolean contains(long[] array, long target) {
/*  87 */     for (long value : array) {
/*  88 */       if (value == target) {
/*  89 */         return true;
/*     */       }
/*     */     } 
/*  92 */     return false;
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
/* 105 */   public static int indexOf(long[] array, long target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(long[] array, long target, int start, int end) {
/* 111 */     for (int i = start; i < end; i++) {
/* 112 */       if (array[i] == target) {
/* 113 */         return i;
/*     */       }
/*     */     } 
/* 116 */     return -1;
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
/*     */   public static int indexOf(long[] array, long[] target) {
/* 131 */     Preconditions.checkNotNull(array, "array");
/* 132 */     Preconditions.checkNotNull(target, "target");
/* 133 */     if (target.length == 0) {
/* 134 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 138 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 139 */       int j = 0; while (true) { if (j < target.length) {
/* 140 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 144 */         return i; }
/*     */     
/* 146 */     }  return -1;
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
/* 159 */   public static int lastIndexOf(long[] array, long target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(long[] array, long target, int start, int end) {
/* 165 */     for (int i = end - 1; i >= start; i--) {
/* 166 */       if (array[i] == target) {
/* 167 */         return i;
/*     */       }
/*     */     } 
/* 170 */     return -1;
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
/*     */   public static long min(long... array) {
/* 182 */     Preconditions.checkArgument((array.length > 0));
/* 183 */     long min = array[0];
/* 184 */     for (int i = 1; i < array.length; i++) {
/* 185 */       if (array[i] < min) {
/* 186 */         min = array[i];
/*     */       }
/*     */     } 
/* 189 */     return min;
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
/*     */   public static long max(long... array) {
/* 201 */     Preconditions.checkArgument((array.length > 0));
/* 202 */     long max = array[0];
/* 203 */     for (int i = 1; i < array.length; i++) {
/* 204 */       if (array[i] > max) {
/* 205 */         max = array[i];
/*     */       }
/*     */     } 
/* 208 */     return max;
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
/*     */   public static long[] concat(long[]... arrays) {
/* 221 */     int length = 0;
/* 222 */     for (long[] array : arrays) {
/* 223 */       length += array.length;
/*     */     }
/* 225 */     long[] result = new long[length];
/* 226 */     int pos = 0;
/* 227 */     for (long[] array : arrays) {
/* 228 */       System.arraycopy(array, 0, result, pos, array.length);
/* 229 */       pos += array.length;
/*     */     } 
/* 231 */     return result;
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
/*     */   @GwtIncompatible("doesn't work")
/* 249 */   public static byte[] toByteArray(long value) { return new byte[] { (byte)(int)(value >> 56), (byte)(int)(value >> 48), (byte)(int)(value >> 40), (byte)(int)(value >> 32), (byte)(int)(value >> 24), (byte)(int)(value >> 16), (byte)(int)(value >> 8), (byte)(int)value }; }
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static long fromByteArray(byte[] bytes) {
/* 277 */     Preconditions.checkArgument((bytes.length >= 8), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(8) });
/*     */     
/* 279 */     return (bytes[0] & 0xFFL) << 56 | (bytes[1] & 0xFFL) << 48 | (bytes[2] & 0xFFL) << 40 | (bytes[3] & 0xFFL) << 32 | (bytes[4] & 0xFFL) << 24 | (bytes[5] & 0xFFL) << 16 | (bytes[6] & 0xFFL) << 8 | bytes[7] & 0xFFL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] ensureCapacity(long[] array, int minLength, int padding) {
/* 307 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 308 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 309 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long[] copyOf(long[] original, int length) {
/* 316 */     long[] copy = new long[length];
/* 317 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 318 */     return copy;
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
/*     */   public static String join(String separator, long... array) {
/* 331 */     Preconditions.checkNotNull(separator);
/* 332 */     if (array.length == 0) {
/* 333 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 337 */     StringBuilder builder = new StringBuilder(array.length * 10);
/* 338 */     builder.append(array[0]);
/* 339 */     for (int i = 1; i < array.length; i++) {
/* 340 */       builder.append(separator).append(array[i]);
/*     */     }
/* 342 */     return builder.toString();
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
/* 362 */   public static Comparator<long[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<long[]> {
/* 366 */     INSTANCE;
/*     */     
/*     */     public int compare(long[] left, long[] right) {
/* 369 */       int minLength = Math.min(left.length, right.length);
/* 370 */       for (int i = 0; i < minLength; i++) {
/* 371 */         int result = Longs.compare(left[i], right[i]);
/* 372 */         if (result != 0) {
/* 373 */           return result;
/*     */         }
/*     */       } 
/* 376 */       return left.length - right.length;
/*     */     }
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
/*     */   public static long[] toArray(Collection<Long> collection) {
/* 395 */     if (collection instanceof LongArrayAsList) {
/* 396 */       return ((LongArrayAsList)collection).toLongArray();
/*     */     }
/*     */     
/* 399 */     Object[] boxedArray = collection.toArray();
/* 400 */     int len = boxedArray.length;
/* 401 */     long[] array = new long[len];
/* 402 */     for (int i = 0; i < len; i++) {
/* 403 */       array[i] = ((Long)boxedArray[i]).longValue();
/*     */     }
/* 405 */     return array;
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
/*     */   public static List<Long> asList(long... backingArray) {
/* 423 */     if (backingArray.length == 0) {
/* 424 */       return Collections.emptyList();
/*     */     }
/* 426 */     return new LongArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class LongArrayAsList
/*     */     extends AbstractList<Long> implements RandomAccess, Serializable {
/*     */     final long[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 437 */     LongArrayAsList(long[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     LongArrayAsList(long[] array, int start, int end) {
/* 441 */       this.array = array;
/* 442 */       this.start = start;
/* 443 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 447 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 451 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Long get(int index) {
/* 455 */       Preconditions.checkElementIndex(index, size());
/* 456 */       return Long.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 461 */     public boolean contains(Object target) { return (target instanceof Long && Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 467 */       if (target instanceof Long) {
/* 468 */         int i = Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 469 */         if (i >= 0) {
/* 470 */           return i - this.start;
/*     */         }
/*     */       } 
/* 473 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 478 */       if (target instanceof Long) {
/* 479 */         int i = Longs.lastIndexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 480 */         if (i >= 0) {
/* 481 */           return i - this.start;
/*     */         }
/*     */       } 
/* 484 */       return -1;
/*     */     }
/*     */     
/*     */     public Long set(int index, Long element) {
/* 488 */       Preconditions.checkElementIndex(index, size());
/* 489 */       long oldValue = this.array[this.start + index];
/* 490 */       this.array[this.start + index] = element.longValue();
/* 491 */       return Long.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Long> subList(int fromIndex, int toIndex) {
/* 496 */       int size = size();
/* 497 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 498 */       if (fromIndex == toIndex) {
/* 499 */         return Collections.emptyList();
/*     */       }
/* 501 */       return new LongArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 505 */       if (object == this) {
/* 506 */         return true;
/*     */       }
/* 508 */       if (object instanceof LongArrayAsList) {
/* 509 */         LongArrayAsList that = (LongArrayAsList)object;
/* 510 */         int size = size();
/* 511 */         if (that.size() != size) {
/* 512 */           return false;
/*     */         }
/* 514 */         for (int i = 0; i < size; i++) {
/* 515 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 516 */             return false;
/*     */           }
/*     */         } 
/* 519 */         return true;
/*     */       } 
/* 521 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 525 */       int result = 1;
/* 526 */       for (int i = this.start; i < this.end; i++) {
/* 527 */         result = 31 * result + Longs.hashCode(this.array[i]);
/*     */       }
/* 529 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 533 */       StringBuilder builder = new StringBuilder(size() * 10);
/* 534 */       builder.append('[').append(this.array[this.start]);
/* 535 */       for (int i = this.start + 1; i < this.end; i++) {
/* 536 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 538 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     long[] toLongArray() {
/* 543 */       int size = size();
/* 544 */       long[] result = new long[size];
/* 545 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 546 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Longs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */