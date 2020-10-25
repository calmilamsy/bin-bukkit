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
/*     */ public final class Shorts
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   
/*  61 */   public static int hashCode(short value) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short checkedCast(long value) {
/*  74 */     short result = (short)(int)value;
/*  75 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  76 */     return result;
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
/*     */   public static short saturatedCast(long value) {
/*  88 */     if (value > 32767L) {
/*  89 */       return Short.MAX_VALUE;
/*     */     }
/*  91 */     if (value < -32768L) {
/*  92 */       return Short.MIN_VALUE;
/*     */     }
/*  94 */     return (short)(int)value;
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
/* 107 */   public static int compare(short a, short b) { return a - b; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(short[] array, short target) {
/* 120 */     for (short value : array) {
/* 121 */       if (value == target) {
/* 122 */         return true;
/*     */       }
/*     */     } 
/* 125 */     return false;
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
/* 138 */   public static int indexOf(short[] array, short target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(short[] array, short target, int start, int end) {
/* 144 */     for (int i = start; i < end; i++) {
/* 145 */       if (array[i] == target) {
/* 146 */         return i;
/*     */       }
/*     */     } 
/* 149 */     return -1;
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
/*     */   public static int indexOf(short[] array, short[] target) {
/* 164 */     Preconditions.checkNotNull(array, "array");
/* 165 */     Preconditions.checkNotNull(target, "target");
/* 166 */     if (target.length == 0) {
/* 167 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 171 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 172 */       int j = 0; while (true) { if (j < target.length) {
/* 173 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 177 */         return i; }
/*     */     
/* 179 */     }  return -1;
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
/* 192 */   public static int lastIndexOf(short[] array, short target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(short[] array, short target, int start, int end) {
/* 198 */     for (int i = end - 1; i >= start; i--) {
/* 199 */       if (array[i] == target) {
/* 200 */         return i;
/*     */       }
/*     */     } 
/* 203 */     return -1;
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
/*     */   public static short min(short... array) {
/* 215 */     Preconditions.checkArgument((array.length > 0));
/* 216 */     short min = array[0];
/* 217 */     for (int i = 1; i < array.length; i++) {
/* 218 */       if (array[i] < min) {
/* 219 */         min = array[i];
/*     */       }
/*     */     } 
/* 222 */     return min;
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
/*     */   public static short max(short... array) {
/* 234 */     Preconditions.checkArgument((array.length > 0));
/* 235 */     short max = array[0];
/* 236 */     for (int i = 1; i < array.length; i++) {
/* 237 */       if (array[i] > max) {
/* 238 */         max = array[i];
/*     */       }
/*     */     } 
/* 241 */     return max;
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
/*     */   public static short[] concat(short[]... arrays) {
/* 254 */     int length = 0;
/* 255 */     for (short[] array : arrays) {
/* 256 */       length += array.length;
/*     */     }
/* 258 */     short[] result = new short[length];
/* 259 */     int pos = 0;
/* 260 */     for (short[] array : arrays) {
/* 261 */       System.arraycopy(array, 0, result, pos, array.length);
/* 262 */       pos += array.length;
/*     */     } 
/* 264 */     return result;
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
/*     */   @GwtIncompatible("doesn't work")
/* 280 */   public static byte[] toByteArray(short value) { return new byte[] { (byte)(value >> 8), (byte)value }; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static short fromByteArray(byte[] bytes) {
/* 299 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 301 */     return (short)(bytes[0] << 8 | bytes[1] & 0xFF);
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
/*     */   public static short[] ensureCapacity(short[] array, int minLength, int padding) {
/* 322 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 323 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 324 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short[] copyOf(short[] original, int length) {
/* 331 */     short[] copy = new short[length];
/* 332 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 333 */     return copy;
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
/*     */   public static String join(String separator, short... array) {
/* 346 */     Preconditions.checkNotNull(separator);
/* 347 */     if (array.length == 0) {
/* 348 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 352 */     StringBuilder builder = new StringBuilder(array.length * 6);
/* 353 */     builder.append(array[0]);
/* 354 */     for (int i = 1; i < array.length; i++) {
/* 355 */       builder.append(separator).append(array[i]);
/*     */     }
/* 357 */     return builder.toString();
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
/* 377 */   public static Comparator<short[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<short[]> {
/* 381 */     INSTANCE;
/*     */     
/*     */     public int compare(short[] left, short[] right) {
/* 384 */       int minLength = Math.min(left.length, right.length);
/* 385 */       for (int i = 0; i < minLength; i++) {
/* 386 */         int result = Shorts.compare(left[i], right[i]);
/* 387 */         if (result != 0) {
/* 388 */           return result;
/*     */         }
/*     */       } 
/* 391 */       return left.length - right.length;
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
/*     */   public static short[] toArray(Collection<Short> collection) {
/* 410 */     if (collection instanceof ShortArrayAsList) {
/* 411 */       return ((ShortArrayAsList)collection).toShortArray();
/*     */     }
/*     */     
/* 414 */     Object[] boxedArray = collection.toArray();
/* 415 */     int len = boxedArray.length;
/* 416 */     short[] array = new short[len];
/* 417 */     for (int i = 0; i < len; i++) {
/* 418 */       array[i] = ((Short)boxedArray[i]).shortValue();
/*     */     }
/* 420 */     return array;
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
/*     */   public static List<Short> asList(short... backingArray) {
/* 438 */     if (backingArray.length == 0) {
/* 439 */       return Collections.emptyList();
/*     */     }
/* 441 */     return new ShortArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class ShortArrayAsList
/*     */     extends AbstractList<Short> implements RandomAccess, Serializable {
/*     */     final short[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 452 */     ShortArrayAsList(short[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     ShortArrayAsList(short[] array, int start, int end) {
/* 456 */       this.array = array;
/* 457 */       this.start = start;
/* 458 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 462 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 466 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Short get(int index) {
/* 470 */       Preconditions.checkElementIndex(index, size());
/* 471 */       return Short.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 476 */     public boolean contains(Object target) { return (target instanceof Short && Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 482 */       if (target instanceof Short) {
/* 483 */         int i = Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 484 */         if (i >= 0) {
/* 485 */           return i - this.start;
/*     */         }
/*     */       } 
/* 488 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 493 */       if (target instanceof Short) {
/* 494 */         int i = Shorts.lastIndexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 495 */         if (i >= 0) {
/* 496 */           return i - this.start;
/*     */         }
/*     */       } 
/* 499 */       return -1;
/*     */     }
/*     */     
/*     */     public Short set(int index, Short element) {
/* 503 */       Preconditions.checkElementIndex(index, size());
/* 504 */       short oldValue = this.array[this.start + index];
/* 505 */       this.array[this.start + index] = element.shortValue();
/* 506 */       return Short.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Short> subList(int fromIndex, int toIndex) {
/* 511 */       int size = size();
/* 512 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 513 */       if (fromIndex == toIndex) {
/* 514 */         return Collections.emptyList();
/*     */       }
/* 516 */       return new ShortArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 520 */       if (object == this) {
/* 521 */         return true;
/*     */       }
/* 523 */       if (object instanceof ShortArrayAsList) {
/* 524 */         ShortArrayAsList that = (ShortArrayAsList)object;
/* 525 */         int size = size();
/* 526 */         if (that.size() != size) {
/* 527 */           return false;
/*     */         }
/* 529 */         for (int i = 0; i < size; i++) {
/* 530 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 531 */             return false;
/*     */           }
/*     */         } 
/* 534 */         return true;
/*     */       } 
/* 536 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 540 */       int result = 1;
/* 541 */       for (int i = this.start; i < this.end; i++) {
/* 542 */         result = 31 * result + Shorts.hashCode(this.array[i]);
/*     */       }
/* 544 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 548 */       StringBuilder builder = new StringBuilder(size() * 6);
/* 549 */       builder.append('[').append(this.array[this.start]);
/* 550 */       for (int i = this.start + 1; i < this.end; i++) {
/* 551 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 553 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     short[] toShortArray() {
/* 558 */       int size = size();
/* 559 */       short[] result = new short[size];
/* 560 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 561 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Shorts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */