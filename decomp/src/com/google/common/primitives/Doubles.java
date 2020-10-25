/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
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
/*     */ @GwtCompatible
/*     */ public final class Doubles
/*     */ {
/*  54 */   public static int hashCode(double value) { return Double.valueOf(value).hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static int compare(double a, double b) { return Double.compare(a, b); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(double[] array, double target) {
/*  86 */     for (double value : array) {
/*  87 */       if (value == target) {
/*  88 */         return true;
/*     */       }
/*     */     } 
/*  91 */     return false;
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
/* 105 */   public static int indexOf(double[] array, double target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(double[] array, double target, int start, int end) {
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOf(double[] array, double[] target) {
/* 134 */     Preconditions.checkNotNull(array, "array");
/* 135 */     Preconditions.checkNotNull(target, "target");
/* 136 */     if (target.length == 0) {
/* 137 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 141 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 142 */       int j = 0; while (true) { if (j < target.length) {
/* 143 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 147 */         return i; }
/*     */     
/* 149 */     }  return -1;
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
/* 163 */   public static int lastIndexOf(double[] array, double target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(double[] array, double target, int start, int end) {
/* 169 */     for (int i = end - 1; i >= start; i--) {
/* 170 */       if (array[i] == target) {
/* 171 */         return i;
/*     */       }
/*     */     } 
/* 174 */     return -1;
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
/*     */   public static double min(double... array) {
/* 187 */     Preconditions.checkArgument((array.length > 0));
/* 188 */     double min = array[0];
/* 189 */     for (int i = 1; i < array.length; i++) {
/* 190 */       min = Math.min(min, array[i]);
/*     */     }
/* 192 */     return min;
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
/*     */   public static double max(double... array) {
/* 205 */     Preconditions.checkArgument((array.length > 0));
/* 206 */     double max = array[0];
/* 207 */     for (int i = 1; i < array.length; i++) {
/* 208 */       max = Math.max(max, array[i]);
/*     */     }
/* 210 */     return max;
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
/*     */   public static double[] concat(double[]... arrays) {
/* 223 */     int length = 0;
/* 224 */     for (double[] array : arrays) {
/* 225 */       length += array.length;
/*     */     }
/* 227 */     double[] result = new double[length];
/* 228 */     int pos = 0;
/* 229 */     for (double[] array : arrays) {
/* 230 */       System.arraycopy(array, 0, result, pos, array.length);
/* 231 */       pos += array.length;
/*     */     } 
/* 233 */     return result;
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
/*     */   public static double[] ensureCapacity(double[] array, int minLength, int padding) {
/* 254 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 255 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 256 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] copyOf(double[] original, int length) {
/* 263 */     double[] copy = new double[length];
/* 264 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 265 */     return copy;
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
/*     */   public static String join(String separator, double... array) {
/* 279 */     Preconditions.checkNotNull(separator);
/* 280 */     if (array.length == 0) {
/* 281 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 285 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 286 */     builder.append(array[0]);
/* 287 */     for (int i = 1; i < array.length; i++) {
/* 288 */       builder.append(separator).append(array[i]);
/*     */     }
/* 290 */     return builder.toString();
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
/* 310 */   public static Comparator<double[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<double[]> {
/* 314 */     INSTANCE;
/*     */     
/*     */     public int compare(double[] left, double[] right) {
/* 317 */       int minLength = Math.min(left.length, right.length);
/* 318 */       for (int i = 0; i < minLength; i++) {
/* 319 */         int result = Doubles.compare(left[i], right[i]);
/* 320 */         if (result != 0) {
/* 321 */           return result;
/*     */         }
/*     */       } 
/* 324 */       return left.length - right.length;
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
/*     */   public static double[] toArray(Collection<Double> collection) {
/* 343 */     if (collection instanceof DoubleArrayAsList) {
/* 344 */       return ((DoubleArrayAsList)collection).toDoubleArray();
/*     */     }
/*     */     
/* 347 */     Object[] boxedArray = collection.toArray();
/* 348 */     int len = boxedArray.length;
/* 349 */     double[] array = new double[len];
/* 350 */     for (int i = 0; i < len; i++) {
/* 351 */       array[i] = ((Double)boxedArray[i]).doubleValue();
/*     */     }
/* 353 */     return array;
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
/*     */   public static List<Double> asList(double... backingArray) {
/* 374 */     if (backingArray.length == 0) {
/* 375 */       return Collections.emptyList();
/*     */     }
/* 377 */     return new DoubleArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class DoubleArrayAsList
/*     */     extends AbstractList<Double> implements RandomAccess, Serializable {
/*     */     final double[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 388 */     DoubleArrayAsList(double[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     DoubleArrayAsList(double[] array, int start, int end) {
/* 392 */       this.array = array;
/* 393 */       this.start = start;
/* 394 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 398 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 402 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Double get(int index) {
/* 406 */       Preconditions.checkElementIndex(index, size());
/* 407 */       return Double.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 412 */     public boolean contains(Object target) { return (target instanceof Double && Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 418 */       if (target instanceof Double) {
/* 419 */         int i = Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 420 */         if (i >= 0) {
/* 421 */           return i - this.start;
/*     */         }
/*     */       } 
/* 424 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 429 */       if (target instanceof Double) {
/* 430 */         int i = Doubles.lastIndexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 431 */         if (i >= 0) {
/* 432 */           return i - this.start;
/*     */         }
/*     */       } 
/* 435 */       return -1;
/*     */     }
/*     */     
/*     */     public Double set(int index, Double element) {
/* 439 */       Preconditions.checkElementIndex(index, size());
/* 440 */       double oldValue = this.array[this.start + index];
/* 441 */       this.array[this.start + index] = element.doubleValue();
/* 442 */       return Double.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Double> subList(int fromIndex, int toIndex) {
/* 447 */       int size = size();
/* 448 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 449 */       if (fromIndex == toIndex) {
/* 450 */         return Collections.emptyList();
/*     */       }
/* 452 */       return new DoubleArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 456 */       if (object == this) {
/* 457 */         return true;
/*     */       }
/* 459 */       if (object instanceof DoubleArrayAsList) {
/* 460 */         DoubleArrayAsList that = (DoubleArrayAsList)object;
/* 461 */         int size = size();
/* 462 */         if (that.size() != size) {
/* 463 */           return false;
/*     */         }
/* 465 */         for (int i = 0; i < size; i++) {
/* 466 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 467 */             return false;
/*     */           }
/*     */         } 
/* 470 */         return true;
/*     */       } 
/* 472 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 476 */       int result = 1;
/* 477 */       for (int i = this.start; i < this.end; i++) {
/* 478 */         result = 31 * result + Doubles.hashCode(this.array[i]);
/*     */       }
/* 480 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 484 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 485 */       builder.append('[').append(this.array[this.start]);
/* 486 */       for (int i = this.start + 1; i < this.end; i++) {
/* 487 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 489 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     double[] toDoubleArray() {
/* 494 */       int size = size();
/* 495 */       double[] result = new double[size];
/* 496 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 497 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Doubles.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */