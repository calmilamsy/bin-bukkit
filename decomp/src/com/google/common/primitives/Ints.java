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
/*     */ public final class Ints
/*     */ {
/*     */   public static final int BYTES = 4;
/*     */   
/*  61 */   public static int hashCode(int value) { return value; }
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
/*     */   public static int checkedCast(long value) {
/*  73 */     int result = (int)value;
/*  74 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  75 */     return result;
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
/*     */   public static int saturatedCast(long value) {
/*  87 */     if (value > 2147483647L) {
/*  88 */       return Integer.MAX_VALUE;
/*     */     }
/*  90 */     if (value < -2147483648L) {
/*  91 */       return Integer.MIN_VALUE;
/*     */     }
/*  93 */     return (int)value;
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
/* 106 */   public static int compare(int a, int b) { return (a < b) ? -1 : ((a > b) ? 1 : 0); }
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
/*     */   public static boolean contains(int[] array, int target) {
/* 119 */     for (int value : array) {
/* 120 */       if (value == target) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/* 124 */     return false;
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
/* 137 */   public static int indexOf(int[] array, int target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(int[] array, int target, int start, int end) {
/* 143 */     for (int i = start; i < end; i++) {
/* 144 */       if (array[i] == target) {
/* 145 */         return i;
/*     */       }
/*     */     } 
/* 148 */     return -1;
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
/*     */   public static int indexOf(int[] array, int[] target) {
/* 163 */     Preconditions.checkNotNull(array, "array");
/* 164 */     Preconditions.checkNotNull(target, "target");
/* 165 */     if (target.length == 0) {
/* 166 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 170 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 171 */       int j = 0; while (true) { if (j < target.length) {
/* 172 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 176 */         return i; }
/*     */     
/* 178 */     }  return -1;
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
/* 191 */   public static int lastIndexOf(int[] array, int target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(int[] array, int target, int start, int end) {
/* 197 */     for (int i = end - 1; i >= start; i--) {
/* 198 */       if (array[i] == target) {
/* 199 */         return i;
/*     */       }
/*     */     } 
/* 202 */     return -1;
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
/*     */   public static int min(int... array) {
/* 214 */     Preconditions.checkArgument((array.length > 0));
/* 215 */     int min = array[0];
/* 216 */     for (int i = 1; i < array.length; i++) {
/* 217 */       if (array[i] < min) {
/* 218 */         min = array[i];
/*     */       }
/*     */     } 
/* 221 */     return min;
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
/*     */   public static int max(int... array) {
/* 233 */     Preconditions.checkArgument((array.length > 0));
/* 234 */     int max = array[0];
/* 235 */     for (int i = 1; i < array.length; i++) {
/* 236 */       if (array[i] > max) {
/* 237 */         max = array[i];
/*     */       }
/*     */     } 
/* 240 */     return max;
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
/*     */   public static int[] concat(int[]... arrays) {
/* 253 */     int length = 0;
/* 254 */     for (int[] array : arrays) {
/* 255 */       length += array.length;
/*     */     }
/* 257 */     int[] result = new int[length];
/* 258 */     int pos = 0;
/* 259 */     for (int[] array : arrays) {
/* 260 */       System.arraycopy(array, 0, result, pos, array.length);
/* 261 */       pos += array.length;
/*     */     } 
/* 263 */     return result;
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
/* 281 */   public static byte[] toByteArray(int value) { return new byte[] { (byte)(value >> 24), (byte)(value >> 16), (byte)(value >> 8), (byte)value }; }
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
/*     */   public static int fromByteArray(byte[] bytes) {
/* 304 */     Preconditions.checkArgument((bytes.length >= 4), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(4) });
/*     */     
/* 306 */     return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF;
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
/*     */   public static int[] ensureCapacity(int[] array, int minLength, int padding) {
/* 330 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 331 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 332 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] copyOf(int[] original, int length) {
/* 339 */     int[] copy = new int[length];
/* 340 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 341 */     return copy;
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
/*     */   public static String join(String separator, int... array) {
/* 354 */     Preconditions.checkNotNull(separator);
/* 355 */     if (array.length == 0) {
/* 356 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 360 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 361 */     builder.append(array[0]);
/* 362 */     for (int i = 1; i < array.length; i++) {
/* 363 */       builder.append(separator).append(array[i]);
/*     */     }
/* 365 */     return builder.toString();
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
/* 384 */   public static Comparator<int[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<int[]> {
/* 388 */     INSTANCE;
/*     */     
/*     */     public int compare(int[] left, int[] right) {
/* 391 */       int minLength = Math.min(left.length, right.length);
/* 392 */       for (int i = 0; i < minLength; i++) {
/* 393 */         int result = Ints.compare(left[i], right[i]);
/* 394 */         if (result != 0) {
/* 395 */           return result;
/*     */         }
/*     */       } 
/* 398 */       return left.length - right.length;
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
/*     */   public static int[] toArray(Collection<Integer> collection) {
/* 417 */     if (collection instanceof IntArrayAsList) {
/* 418 */       return ((IntArrayAsList)collection).toIntArray();
/*     */     }
/*     */     
/* 421 */     Object[] boxedArray = collection.toArray();
/* 422 */     int len = boxedArray.length;
/* 423 */     int[] array = new int[len];
/* 424 */     for (int i = 0; i < len; i++) {
/* 425 */       array[i] = ((Integer)boxedArray[i]).intValue();
/*     */     }
/* 427 */     return array;
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
/*     */   public static List<Integer> asList(int... backingArray) {
/* 445 */     if (backingArray.length == 0) {
/* 446 */       return Collections.emptyList();
/*     */     }
/* 448 */     return new IntArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class IntArrayAsList
/*     */     extends AbstractList<Integer> implements RandomAccess, Serializable {
/*     */     final int[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 459 */     IntArrayAsList(int[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     IntArrayAsList(int[] array, int start, int end) {
/* 463 */       this.array = array;
/* 464 */       this.start = start;
/* 465 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 469 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 473 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Integer get(int index) {
/* 477 */       Preconditions.checkElementIndex(index, size());
/* 478 */       return Integer.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 483 */     public boolean contains(Object target) { return (target instanceof Integer && Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 489 */       if (target instanceof Integer) {
/* 490 */         int i = Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 491 */         if (i >= 0) {
/* 492 */           return i - this.start;
/*     */         }
/*     */       } 
/* 495 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 500 */       if (target instanceof Integer) {
/* 501 */         int i = Ints.lastIndexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 502 */         if (i >= 0) {
/* 503 */           return i - this.start;
/*     */         }
/*     */       } 
/* 506 */       return -1;
/*     */     }
/*     */     
/*     */     public Integer set(int index, Integer element) {
/* 510 */       Preconditions.checkElementIndex(index, size());
/* 511 */       int oldValue = this.array[this.start + index];
/* 512 */       this.array[this.start + index] = element.intValue();
/* 513 */       return Integer.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Integer> subList(int fromIndex, int toIndex) {
/* 518 */       int size = size();
/* 519 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 520 */       if (fromIndex == toIndex) {
/* 521 */         return Collections.emptyList();
/*     */       }
/* 523 */       return new IntArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 527 */       if (object == this) {
/* 528 */         return true;
/*     */       }
/* 530 */       if (object instanceof IntArrayAsList) {
/* 531 */         IntArrayAsList that = (IntArrayAsList)object;
/* 532 */         int size = size();
/* 533 */         if (that.size() != size) {
/* 534 */           return false;
/*     */         }
/* 536 */         for (int i = 0; i < size; i++) {
/* 537 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 538 */             return false;
/*     */           }
/*     */         } 
/* 541 */         return true;
/*     */       } 
/* 543 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 547 */       int result = 1;
/* 548 */       for (int i = this.start; i < this.end; i++) {
/* 549 */         result = 31 * result + Ints.hashCode(this.array[i]);
/*     */       }
/* 551 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 555 */       StringBuilder builder = new StringBuilder(size() * 5);
/* 556 */       builder.append('[').append(this.array[this.start]);
/* 557 */       for (int i = this.start + 1; i < this.end; i++) {
/* 558 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 560 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     int[] toIntArray() {
/* 565 */       int size = size();
/* 566 */       int[] result = new int[size];
/* 567 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 568 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Ints.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */