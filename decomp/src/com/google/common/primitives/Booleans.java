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
/*     */ public final class Booleans
/*     */ {
/*  55 */   public static int hashCode(boolean value) { return value ? 1231 : 1237; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static int compare(boolean a, boolean b) { return (a == b) ? 0 : (a ? 1 : -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(boolean[] array, boolean target) {
/*  87 */     for (boolean value : array) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static int indexOf(boolean[] array, boolean target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(boolean[] array, boolean target, int start, int end) {
/* 115 */     for (int i = start; i < end; i++) {
/* 116 */       if (array[i] == target) {
/* 117 */         return i;
/*     */       }
/*     */     } 
/* 120 */     return -1;
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
/*     */   public static int indexOf(boolean[] array, boolean[] target) {
/* 135 */     Preconditions.checkNotNull(array, "array");
/* 136 */     Preconditions.checkNotNull(target, "target");
/* 137 */     if (target.length == 0) {
/* 138 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 142 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 143 */       int j = 0; while (true) { if (j < target.length) {
/* 144 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 148 */         return i; }
/*     */     
/* 150 */     }  return -1;
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
/* 163 */   public static int lastIndexOf(boolean[] array, boolean target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(boolean[] array, boolean target, int start, int end) {
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
/*     */   public static boolean[] concat(boolean[]... arrays) {
/* 187 */     int length = 0;
/* 188 */     for (boolean[] array : arrays) {
/* 189 */       length += array.length;
/*     */     }
/* 191 */     boolean[] result = new boolean[length];
/* 192 */     int pos = 0;
/* 193 */     for (boolean[] array : arrays) {
/* 194 */       System.arraycopy(array, 0, result, pos, array.length);
/* 195 */       pos += array.length;
/*     */     } 
/* 197 */     return result;
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
/*     */   public static boolean[] ensureCapacity(boolean[] array, int minLength, int padding) {
/* 218 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 219 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 220 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] copyOf(boolean[] original, int length) {
/* 227 */     boolean[] copy = new boolean[length];
/* 228 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 229 */     return copy;
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
/*     */   public static String join(String separator, boolean... array) {
/* 242 */     Preconditions.checkNotNull(separator);
/* 243 */     if (array.length == 0) {
/* 244 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 248 */     StringBuilder builder = new StringBuilder(array.length * 7);
/* 249 */     builder.append(array[0]);
/* 250 */     for (int i = 1; i < array.length; i++) {
/* 251 */       builder.append(separator).append(array[i]);
/*     */     }
/* 253 */     return builder.toString();
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
/* 273 */   public static Comparator<boolean[]> lexicographicalComparator() { return LexicographicalComparator.INSTANCE; }
/*     */   
/*     */   private enum LexicographicalComparator
/*     */     implements Comparator<boolean[]> {
/* 277 */     INSTANCE;
/*     */     
/*     */     public int compare(boolean[] left, boolean[] right) {
/* 280 */       int minLength = Math.min(left.length, right.length);
/* 281 */       for (int i = 0; i < minLength; i++) {
/* 282 */         int result = Booleans.compare(left[i], right[i]);
/* 283 */         if (result != 0) {
/* 284 */           return result;
/*     */         }
/*     */       } 
/* 287 */       return left.length - right.length;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] toArray(Collection<Boolean> collection) {
/* 309 */     if (collection instanceof BooleanArrayAsList) {
/* 310 */       return ((BooleanArrayAsList)collection).toBooleanArray();
/*     */     }
/*     */     
/* 313 */     Object[] boxedArray = collection.toArray();
/* 314 */     int len = boxedArray.length;
/* 315 */     boolean[] array = new boolean[len];
/* 316 */     for (int i = 0; i < len; i++) {
/* 317 */       array[i] = ((Boolean)boxedArray[i]).booleanValue();
/*     */     }
/* 319 */     return array;
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
/*     */   public static List<Boolean> asList(boolean... backingArray) {
/* 337 */     if (backingArray.length == 0) {
/* 338 */       return Collections.emptyList();
/*     */     }
/* 340 */     return new BooleanArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class BooleanArrayAsList
/*     */     extends AbstractList<Boolean> implements RandomAccess, Serializable {
/*     */     final boolean[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 351 */     BooleanArrayAsList(boolean[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     BooleanArrayAsList(boolean[] array, int start, int end) {
/* 355 */       this.array = array;
/* 356 */       this.start = start;
/* 357 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 361 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 365 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Boolean get(int index) {
/* 369 */       Preconditions.checkElementIndex(index, size());
/* 370 */       return Boolean.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 375 */     public boolean contains(Object target) { return (target instanceof Boolean && Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 381 */       if (target instanceof Boolean) {
/* 382 */         int i = Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 383 */         if (i >= 0) {
/* 384 */           return i - this.start;
/*     */         }
/*     */       } 
/* 387 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 392 */       if (target instanceof Boolean) {
/* 393 */         int i = Booleans.lastIndexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 394 */         if (i >= 0) {
/* 395 */           return i - this.start;
/*     */         }
/*     */       } 
/* 398 */       return -1;
/*     */     }
/*     */     
/*     */     public Boolean set(int index, Boolean element) {
/* 402 */       Preconditions.checkElementIndex(index, size());
/* 403 */       boolean oldValue = this.array[this.start + index];
/* 404 */       this.array[this.start + index] = element.booleanValue();
/* 405 */       return Boolean.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Boolean> subList(int fromIndex, int toIndex) {
/* 410 */       int size = size();
/* 411 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 412 */       if (fromIndex == toIndex) {
/* 413 */         return Collections.emptyList();
/*     */       }
/* 415 */       return new BooleanArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 419 */       if (object == this) {
/* 420 */         return true;
/*     */       }
/* 422 */       if (object instanceof BooleanArrayAsList) {
/* 423 */         BooleanArrayAsList that = (BooleanArrayAsList)object;
/* 424 */         int size = size();
/* 425 */         if (that.size() != size) {
/* 426 */           return false;
/*     */         }
/* 428 */         for (int i = 0; i < size; i++) {
/* 429 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 430 */             return false;
/*     */           }
/*     */         } 
/* 433 */         return true;
/*     */       } 
/* 435 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 439 */       int result = 1;
/* 440 */       for (int i = this.start; i < this.end; i++) {
/* 441 */         result = 31 * result + Booleans.hashCode(this.array[i]);
/*     */       }
/* 443 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 447 */       StringBuilder builder = new StringBuilder(size() * 7);
/* 448 */       builder.append(this.array[this.start] ? "[true" : "[false");
/* 449 */       for (int i = this.start + 1; i < this.end; i++) {
/* 450 */         builder.append(this.array[i] ? ", true" : ", false");
/*     */       }
/* 452 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean[] toBooleanArray() {
/* 457 */       int size = size();
/* 458 */       boolean[] result = new boolean[size];
/* 459 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 460 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Booleans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */