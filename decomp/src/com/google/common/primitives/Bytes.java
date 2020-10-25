/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ @GwtCompatible
/*     */ public final class Bytes
/*     */ {
/*  56 */   public static int hashCode(byte value) { return value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(byte[] array, byte target) {
/*  69 */     for (byte value : array) {
/*  70 */       if (value == target) {
/*  71 */         return true;
/*     */       }
/*     */     } 
/*  74 */     return false;
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
/*  87 */   public static int indexOf(byte[] array, byte target) { return indexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(byte[] array, byte target, int start, int end) {
/*  93 */     for (int i = start; i < end; i++) {
/*  94 */       if (array[i] == target) {
/*  95 */         return i;
/*     */       }
/*     */     } 
/*  98 */     return -1;
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
/*     */   public static int indexOf(byte[] array, byte[] target) {
/* 113 */     Preconditions.checkNotNull(array, "array");
/* 114 */     Preconditions.checkNotNull(target, "target");
/* 115 */     if (target.length == 0) {
/* 116 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 120 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 121 */       int j = 0; while (true) { if (j < target.length) {
/* 122 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 126 */         return i; }
/*     */     
/* 128 */     }  return -1;
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
/* 141 */   public static int lastIndexOf(byte[] array, byte target) { return lastIndexOf(array, target, 0, array.length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(byte[] array, byte target, int start, int end) {
/* 147 */     for (int i = end - 1; i >= start; i--) {
/* 148 */       if (array[i] == target) {
/* 149 */         return i;
/*     */       }
/*     */     } 
/* 152 */     return -1;
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
/*     */   public static byte[] concat(byte[]... arrays) {
/* 165 */     int length = 0;
/* 166 */     for (byte[] array : arrays) {
/* 167 */       length += array.length;
/*     */     }
/* 169 */     byte[] result = new byte[length];
/* 170 */     int pos = 0;
/* 171 */     for (byte[] array : arrays) {
/* 172 */       System.arraycopy(array, 0, result, pos, array.length);
/* 173 */       pos += array.length;
/*     */     } 
/* 175 */     return result;
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
/*     */   public static byte[] ensureCapacity(byte[] array, int minLength, int padding) {
/* 196 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 197 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 198 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] copyOf(byte[] original, int length) {
/* 205 */     byte[] copy = new byte[length];
/* 206 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 207 */     return copy;
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
/*     */   public static byte[] toArray(Collection<Byte> collection) {
/* 225 */     if (collection instanceof ByteArrayAsList) {
/* 226 */       return ((ByteArrayAsList)collection).toByteArray();
/*     */     }
/*     */     
/* 229 */     Object[] boxedArray = collection.toArray();
/* 230 */     int len = boxedArray.length;
/* 231 */     byte[] array = new byte[len];
/* 232 */     for (int i = 0; i < len; i++) {
/* 233 */       array[i] = ((Byte)boxedArray[i]).byteValue();
/*     */     }
/* 235 */     return array;
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
/*     */   public static List<Byte> asList(byte... backingArray) {
/* 253 */     if (backingArray.length == 0) {
/* 254 */       return Collections.emptyList();
/*     */     }
/* 256 */     return new ByteArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class ByteArrayAsList
/*     */     extends AbstractList<Byte> implements RandomAccess, Serializable {
/*     */     final byte[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 267 */     ByteArrayAsList(byte[] array) { this(array, 0, array.length); }
/*     */ 
/*     */     
/*     */     ByteArrayAsList(byte[] array, int start, int end) {
/* 271 */       this.array = array;
/* 272 */       this.start = start;
/* 273 */       this.end = end;
/*     */     }
/*     */ 
/*     */     
/* 277 */     public int size() { return this.end - this.start; }
/*     */ 
/*     */ 
/*     */     
/* 281 */     public boolean isEmpty() { return false; }
/*     */ 
/*     */     
/*     */     public Byte get(int index) {
/* 285 */       Preconditions.checkElementIndex(index, size());
/* 286 */       return Byte.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 291 */     public boolean contains(Object target) { return (target instanceof Byte && Bytes.indexOf(this.array, ((Byte)target).byteValue(), this.start, this.end) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 297 */       if (target instanceof Byte) {
/* 298 */         int i = Bytes.indexOf(this.array, ((Byte)target).byteValue(), this.start, this.end);
/* 299 */         if (i >= 0) {
/* 300 */           return i - this.start;
/*     */         }
/*     */       } 
/* 303 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 308 */       if (target instanceof Byte) {
/* 309 */         int i = Bytes.lastIndexOf(this.array, ((Byte)target).byteValue(), this.start, this.end);
/* 310 */         if (i >= 0) {
/* 311 */           return i - this.start;
/*     */         }
/*     */       } 
/* 314 */       return -1;
/*     */     }
/*     */     
/*     */     public Byte set(int index, Byte element) {
/* 318 */       Preconditions.checkElementIndex(index, size());
/* 319 */       byte oldValue = this.array[this.start + index];
/* 320 */       this.array[this.start + index] = element.byteValue();
/* 321 */       return Byte.valueOf(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Byte> subList(int fromIndex, int toIndex) {
/* 326 */       int size = size();
/* 327 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 328 */       if (fromIndex == toIndex) {
/* 329 */         return Collections.emptyList();
/*     */       }
/* 331 */       return new ByteArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 335 */       if (object == this) {
/* 336 */         return true;
/*     */       }
/* 338 */       if (object instanceof ByteArrayAsList) {
/* 339 */         ByteArrayAsList that = (ByteArrayAsList)object;
/* 340 */         int size = size();
/* 341 */         if (that.size() != size) {
/* 342 */           return false;
/*     */         }
/* 344 */         for (int i = 0; i < size; i++) {
/* 345 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 346 */             return false;
/*     */           }
/*     */         } 
/* 349 */         return true;
/*     */       } 
/* 351 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 355 */       int result = 1;
/* 356 */       for (int i = this.start; i < this.end; i++) {
/* 357 */         result = 31 * result + Bytes.hashCode(this.array[i]);
/*     */       }
/* 359 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 363 */       StringBuilder builder = new StringBuilder(size() * 5);
/* 364 */       builder.append('[').append(this.array[this.start]);
/* 365 */       for (int i = this.start + 1; i < this.end; i++) {
/* 366 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 368 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     byte[] toByteArray() {
/* 373 */       int size = size();
/* 374 */       byte[] result = new byte[size];
/* 375 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 376 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\primitives\Bytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */