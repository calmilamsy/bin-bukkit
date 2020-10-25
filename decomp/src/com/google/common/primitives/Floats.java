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
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Floats
/*     */ {
/*  55 */   public static int hashCode(float value) { return Float.valueOf(value).hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static int compare(float a, float b) { return Float.compare(a, b); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(float[] array, float target) {
/*  83 */     for (float value : array) {
/*  84 */       if (value == target) {
/*  85 */         return true;
/*     */       }
/*     */     } 
/*  88 */     return false;
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
/* 102 */   public static int indexOf(float[] array, float target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(float[] array, float target, int start, int end) {
/* 108 */     for (int i = start; i < end; i++) {
/* 109 */       if (array[i] == target) {
/* 110 */         return i;
/*     */       }
/*     */     } 
/* 113 */     return -1;
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
/*     */   public static int indexOf(float[] array, float[] target) {
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
/*     */   
/* 160 */   public static int lastIndexOf(float[] array, float target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(float[] array, float target, int start, int end) {
/* 166 */     for (int i = end - 1; i >= start; i--) {
/* 167 */       if (array[i] == target) {
/* 168 */         return i;
/*     */       }
/*     */     } 
/* 171 */     return -1;
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
/*     */   public static float min(float... array) {
/* 184 */     Preconditions.checkArgument((array.length > 0));
/* 185 */     float min = array[0];
/* 186 */     for (int i = 1; i < array.length; i++) {
/* 187 */       min = Math.min(min, array[i]);
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
/*     */   
/*     */   public static float max(float... array) {
/* 202 */     Preconditions.checkArgument((array.length > 0));
/* 203 */     float max = array[0];
/* 204 */     for (int i = 1; i < array.length; i++) {
/* 205 */       max = Math.max(max, array[i]);
/*     */     }
/* 207 */     return max;
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
/*     */   public static float[] concat(float[]... arrays) {
/* 220 */     int length = 0;
/* 221 */     for (float[] array : arrays) {
/* 222 */       length += array.length;
/*     */     }
/* 224 */     float[] result = new float[length];
/* 225 */     int pos = 0;
/* 226 */     for (float[] array : arrays) {
/* 227 */       System.arraycopy(array, 0, result, pos, array.length);
/* 228 */       pos += array.length;
/*     */     } 
/* 230 */     return result;
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
/*     */   public static float[] ensureCapacity(float[] array, int minLength, int padding) {
/* 251 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 252 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 253 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] copyOf(float[] original, int length) {
/* 260 */     float[] copy = new float[length];
/* 261 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 262 */     return copy;
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
/*     */   public static String join(String separator, float... array) {
/* 276 */     Preconditions.checkNotNull(separator);
/* 277 */     if (array.length == 0) {
/* 278 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 282 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 283 */     builder.append(array[0]);
/* 284 */     for (int i = 1; i < array.length; i++) {
/* 285 */       builder.append(separator).append(array[i]);
/*     */     }
/* 287 */     return builder.toString();
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
/* 307 */   public static Comparator<float[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<float[]> {
/* 311 */     INSTANCE;
/*     */     
/*     */     public int compare(float[] left, float[] right) {
/* 314 */       int minLength = Math.min(left.length, right.length);
/* 315 */       for (int i = 0; i < minLength; i++) {
/* 316 */         int result = Floats.compare(left[i], right[i]);
/* 317 */         if (result != 0) {
/* 318 */           return result;
/*     */         }
/*     */       } 
/* 321 */       return left.length - right.length;
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
/*     */   public static float[] toArray(Collection<Float> collection) {
/* 340 */     if (collection instanceof FloatArrayAsList) {
/* 341 */       return ((FloatArrayAsList)collection).toFloatArray();
/*     */     }
/*     */     
/* 344 */     Object[] boxedArray = collection.toArray();
/* 345 */     int len = boxedArray.length;
/* 346 */     float[] array = new float[len];
/* 347 */     for (int i = 0; i < len; i++) {
/* 348 */       array[i] = ((Float)boxedArray[i]).floatValue();
/*     */     }
/* 350 */     return array;
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
/*     */   public static List<Float> asList(float... backingArray) {
/* 371 */     if (backingArray.length == 0) {
/* 372 */       return Collections.emptyList();
/*     */     }
/* 374 */     return new FloatArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class FloatArrayAsList
/*     */     extends AbstractList<Float> implements RandomAccess, Serializable {
/*     */     final float[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 385 */     FloatArrayAsList(float[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     FloatArrayAsList(float[] array, int start, int end) {
/* 389 */       this.array = array;
/* 390 */       this.start = start;
/* 391 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 395 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 399 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Float get(int index) {
/* 403 */       Preconditions.checkElementIndex(index, size());
/* 404 */       return Float.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 409 */     public boolean contains(Object target) { return (target instanceof Float && Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 415 */       if (target instanceof Float) {
/* 416 */         int i = Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 417 */         if (i >= 0) {
/* 418 */           return i - this.start;
/*     */         }
/*     */       } 
/* 421 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 426 */       if (target instanceof Float) {
/* 427 */         int i = Floats.lastIndexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 428 */         if (i >= 0) {
/* 429 */           return i - this.start;
/*     */         }
/*     */       } 
/* 432 */       return -1;
/*     */     }
/*     */     
/*     */     public Float set(int index, Float element) {
/* 436 */       Preconditions.checkElementIndex(index, size());
/* 437 */       float oldValue = this.array[this.start + index];
/* 438 */       this.array[this.start + index] = element.floatValue();
/* 439 */       return Float.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Float> subList(int fromIndex, int toIndex) {
/* 444 */       int size = size();
/* 445 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 446 */       if (fromIndex == toIndex) {
/* 447 */         return Collections.emptyList();
/*     */       }
/* 449 */       return new FloatArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 453 */       if (object == this) {
/* 454 */         return true;
/*     */       }
/* 456 */       if (object instanceof FloatArrayAsList) {
/* 457 */         FloatArrayAsList that = (FloatArrayAsList)object;
/* 458 */         int size = size();
/* 459 */         if (that.size() != size) {
/* 460 */           return false;
/*     */         }
/* 462 */         for (int i = 0; i < size; i++) {
/* 463 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 464 */             return false;
/*     */           }
/*     */         } 
/* 467 */         return true;
/*     */       } 
/* 469 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 473 */       int result = 1;
/* 474 */       for (int i = this.start; i < this.end; i++) {
/* 475 */         result = 31 * result + Floats.hashCode(this.array[i]);
/*     */       }
/* 477 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 481 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 482 */       builder.append('[').append(this.array[this.start]);
/* 483 */       for (int i = this.start + 1; i < this.end; i++) {
/* 484 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 486 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     float[] toFloatArray() {
/* 491 */       int size = size();
/* 492 */       float[] result = new float[size];
/* 493 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 494 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Floats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */