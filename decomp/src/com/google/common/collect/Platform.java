/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ class Platform
/*     */ {
/*  32 */   private static final Logger logger = Logger.getLogger(Platform.class.getCanonicalName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Class.isInstance")
/*  41 */   static boolean isInstance(Class<?> clazz, Object obj) { return clazz.isInstance(obj); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   static <T> T[] clone(T[] array) { return (T[])(Object[])array.clone(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   static void unsafeArrayCopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) { System.arraycopy(src, srcPos, dest, destPos, length); }
/*     */ 
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
/*  78 */   static <T> T[] newArray(Class<T> type, int length) { return (T[])(Object[])Array.newInstance(type, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T[] newArray(T[] reference, int length) {
/*  89 */     Class<?> type = reference.getClass().getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return (T[])(Object[])Array.newInstance(type, length);
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
/* 105 */   static MapMaker tryWeakKeys(MapMaker mapMaker) { return mapMaker.weakKeys(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */