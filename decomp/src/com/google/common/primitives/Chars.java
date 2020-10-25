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
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Chars
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   
/*  64 */   public static int hashCode(char value) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char checkedCast(long value) {
/*  76 */     char result = (char)(int)value;
/*  77 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  78 */     return result;
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
/*     */   public static char saturatedCast(long value) {
/*  90 */     if (value > 65535L) {
/*  91 */       return Character.MAX_VALUE;
/*     */     }
/*  93 */     if (value < 0L) {
/*  94 */       return Character.MIN_VALUE;
/*     */     }
/*  96 */     return (char)(int)value;
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
/* 109 */   public static int compare(char a, char b) { return a - b; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(char[] array, char target) {
/* 122 */     for (char value : array) {
/* 123 */       if (value == target) {
/* 124 */         return true;
/*     */       }
/*     */     } 
/* 127 */     return false;
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
/* 140 */   public static int indexOf(char[] array, char target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(char[] array, char target, int start, int end) {
/* 146 */     for (int i = start; i < end; i++) {
/* 147 */       if (array[i] == target) {
/* 148 */         return i;
/*     */       }
/*     */     } 
/* 151 */     return -1;
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
/*     */   public static int indexOf(char[] array, char[] target) {
/* 166 */     Preconditions.checkNotNull(array, "array");
/* 167 */     Preconditions.checkNotNull(target, "target");
/* 168 */     if (target.length == 0) {
/* 169 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 173 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 174 */       int j = 0; while (true) { if (j < target.length) {
/* 175 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 179 */         return i; }
/*     */     
/* 181 */     }  return -1;
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
/* 194 */   public static int lastIndexOf(char[] array, char target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(char[] array, char target, int start, int end) {
/* 200 */     for (int i = end - 1; i >= start; i--) {
/* 201 */       if (array[i] == target) {
/* 202 */         return i;
/*     */       }
/*     */     } 
/* 205 */     return -1;
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
/*     */   public static char min(char... array) {
/* 217 */     Preconditions.checkArgument((array.length > 0));
/* 218 */     char min = array[0];
/* 219 */     for (int i = 1; i < array.length; i++) {
/* 220 */       if (array[i] < min) {
/* 221 */         min = array[i];
/*     */       }
/*     */     } 
/* 224 */     return min;
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
/*     */   public static char max(char... array) {
/* 236 */     Preconditions.checkArgument((array.length > 0));
/* 237 */     char max = array[0];
/* 238 */     for (int i = 1; i < array.length; i++) {
/* 239 */       if (array[i] > max) {
/* 240 */         max = array[i];
/*     */       }
/*     */     } 
/* 243 */     return max;
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
/*     */   public static char[] concat(char[]... arrays) {
/* 256 */     int length = 0;
/* 257 */     for (char[] array : arrays) {
/* 258 */       length += array.length;
/*     */     }
/* 260 */     char[] result = new char[length];
/* 261 */     int pos = 0;
/* 262 */     for (char[] array : arrays) {
/* 263 */       System.arraycopy(array, 0, result, pos, array.length);
/* 264 */       pos += array.length;
/*     */     } 
/* 266 */     return result;
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
/* 282 */   public static byte[] toByteArray(char value) { return new byte[] { (byte)(value >> '\b'), (byte)value }; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static char fromByteArray(byte[] bytes) {
/* 301 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 303 */     return (char)(bytes[0] << 8 | bytes[1] & 0xFF);
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
/*     */   public static char[] ensureCapacity(char[] array, int minLength, int padding) {
/* 324 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 325 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 326 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char[] copyOf(char[] original, int length) {
/* 333 */     char[] copy = new char[length];
/* 334 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 335 */     return copy;
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
/*     */   public static String join(String separator, char... array) {
/* 348 */     Preconditions.checkNotNull(separator);
/* 349 */     int len = array.length;
/* 350 */     if (len == 0) {
/* 351 */       return "";
/*     */     }
/*     */     
/* 354 */     StringBuilder builder = new StringBuilder(len + separator.length() * (len - 1));
/*     */     
/* 356 */     builder.append(array[0]);
/* 357 */     for (int i = 1; i < len; i++) {
/* 358 */       builder.append(separator).append(array[i]);
/*     */     }
/* 360 */     return builder.toString();
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
/* 380 */   public static Comparator<char[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<char[]> {
/* 384 */     INSTANCE;
/*     */     
/*     */     public int compare(char[] left, char[] right) {
/* 387 */       int minLength = Math.min(left.length, right.length);
/* 388 */       for (int i = 0; i < minLength; i++) {
/* 389 */         int result = Chars.compare(left[i], right[i]);
/* 390 */         if (result != 0) {
/* 391 */           return result;
/*     */         }
/*     */       } 
/* 394 */       return left.length - right.length;
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
/*     */   public static char[] toArray(Collection<Character> collection) {
/* 413 */     if (collection instanceof CharArrayAsList) {
/* 414 */       return ((CharArrayAsList)collection).toCharArray();
/*     */     }
/*     */     
/* 417 */     Object[] boxedArray = collection.toArray();
/* 418 */     int len = boxedArray.length;
/* 419 */     char[] array = new char[len];
/* 420 */     for (int i = 0; i < len; i++) {
/* 421 */       array[i] = ((Character)boxedArray[i]).charValue();
/*     */     }
/* 423 */     return array;
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
/*     */   public static List<Character> asList(char... backingArray) {
/* 441 */     if (backingArray.length == 0) {
/* 442 */       return Collections.emptyList();
/*     */     }
/* 444 */     return new CharArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class CharArrayAsList
/*     */     extends AbstractList<Character> implements RandomAccess, Serializable {
/*     */     final char[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 455 */     CharArrayAsList(char[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     CharArrayAsList(char[] array, int start, int end) {
/* 459 */       this.array = array;
/* 460 */       this.start = start;
/* 461 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 465 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 469 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Character get(int index) {
/* 473 */       Preconditions.checkElementIndex(index, size());
/* 474 */       return Character.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 479 */     public boolean contains(Object target) { return (target instanceof Character && Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 485 */       if (target instanceof Character) {
/* 486 */         int i = Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 487 */         if (i >= 0) {
/* 488 */           return i - this.start;
/*     */         }
/*     */       } 
/* 491 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 496 */       if (target instanceof Character) {
/* 497 */         int i = Chars.lastIndexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 498 */         if (i >= 0) {
/* 499 */           return i - this.start;
/*     */         }
/*     */       } 
/* 502 */       return -1;
/*     */     }
/*     */     
/*     */     public Character set(int index, Character element) {
/* 506 */       Preconditions.checkElementIndex(index, size());
/* 507 */       char oldValue = this.array[this.start + index];
/* 508 */       this.array[this.start + index] = element.charValue();
/* 509 */       return Character.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Character> subList(int fromIndex, int toIndex) {
/* 514 */       int size = size();
/* 515 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 516 */       if (fromIndex == toIndex) {
/* 517 */         return Collections.emptyList();
/*     */       }
/* 519 */       return new CharArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 523 */       if (object == this) {
/* 524 */         return true;
/*     */       }
/* 526 */       if (object instanceof CharArrayAsList) {
/* 527 */         CharArrayAsList that = (CharArrayAsList)object;
/* 528 */         int size = size();
/* 529 */         if (that.size() != size) {
/* 530 */           return false;
/*     */         }
/* 532 */         for (int i = 0; i < size; i++) {
/* 533 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 534 */             return false;
/*     */           }
/*     */         } 
/* 537 */         return true;
/*     */       } 
/* 539 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 543 */       int result = 1;
/* 544 */       for (int i = this.start; i < this.end; i++) {
/* 545 */         result = 31 * result + Chars.hashCode(this.array[i]);
/*     */       }
/* 547 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 551 */       StringBuilder builder = new StringBuilder(size() * 3);
/* 552 */       builder.append('[').append(this.array[this.start]);
/* 553 */       for (int i = this.start + 1; i < this.end; i++) {
/* 554 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 556 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     char[] toCharArray() {
/* 561 */       int size = size();
/* 562 */       char[] result = new char[size];
/* 563 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 564 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Chars.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */