/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class ObjectArrays
/*     */ {
/*     */   @GwtIncompatible("Array.newInstance(Class, int)")
/*  45 */   public static <T> T[] newArray(Class<T> type, int length) { return (T[])Platform.newArray(type, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static <T> T[] newArray(T[] reference, int length) { return (T[])Platform.newArray(reference, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Array.newInstance(Class, int)")
/*     */   public static <T> T[] concat(T[] first, T[] second, Class<T> type) {
/*  68 */     T[] result = (T[])newArray(type, first.length + second.length);
/*  69 */     Platform.unsafeArrayCopy(first, 0, result, 0, first.length);
/*  70 */     Platform.unsafeArrayCopy(second, 0, result, first.length, second.length);
/*  71 */     return result;
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
/*     */   public static <T> T[] concat(@Nullable T element, T[] array) {
/*  84 */     T[] result = (T[])newArray(array, array.length + 1);
/*  85 */     result[0] = element;
/*  86 */     Platform.unsafeArrayCopy(array, 0, result, 1, array.length);
/*  87 */     return result;
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
/*     */   public static <T> T[] concat(T[] array, @Nullable T element) {
/* 100 */     T[] result = (T[])arraysCopyOf(array, array.length + 1);
/* 101 */     result[array.length] = element;
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] arraysCopyOf(T[] original, int newLength) {
/* 107 */     T[] copy = (T[])newArray(original, newLength);
/* 108 */     Platform.unsafeArrayCopy(original, 0, copy, 0, Math.min(original.length, newLength));
/*     */     
/* 110 */     return copy;
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
/*     */   
/*     */   static <T> T[] toArrayImpl(Collection<?> c, T[] array) {
/* 139 */     int size = c.size();
/* 140 */     if (array.length < size) {
/* 141 */       array = (T[])newArray(array, size);
/*     */     }
/* 143 */     fillArray(c, array);
/* 144 */     if (array.length > size) {
/* 145 */       array[size] = null;
/*     */     }
/* 147 */     return array;
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
/* 166 */   static Object[] toArrayImpl(Collection<?> c) { return fillArray(c, new Object[c.size()]); }
/*     */ 
/*     */   
/*     */   private static Object[] fillArray(Iterable<?> elements, Object[] array) {
/* 170 */     int i = 0;
/* 171 */     for (Object element : elements) {
/* 172 */       array[i++] = element;
/*     */     }
/* 174 */     return array;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ObjectArrays.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */