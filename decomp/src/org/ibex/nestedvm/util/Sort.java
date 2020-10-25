/*    */ package org.ibex.nestedvm.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Sort
/*    */ {
/*  9 */   private static final CompareFunc comparableCompareFunc = new CompareFunc() {
/* 10 */       public int compare(Object param1Object1, Object param1Object2) { return ((Sort.Comparable)param1Object1).compareTo(param1Object2); }
/*    */     }; public static interface CompareFunc {
/*    */     int compare(Object param1Object1, Object param1Object2); }
/* 13 */   public static void sort(Comparable[] paramArrayOfComparable) { sort(paramArrayOfComparable, comparableCompareFunc); } public static interface Comparable {
/* 14 */     int compareTo(Object param1Object); } public static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc) { sort(paramArrayOfObject, paramCompareFunc, 0, paramArrayOfObject.length - 1); }
/*    */ 
/*    */   
/*    */   private static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc, int paramInt1, int paramInt2) {
/* 18 */     if (paramInt1 >= paramInt2)
/* 19 */       return;  if (paramInt2 - paramInt1 <= 6) {
/* 20 */       for (int k = paramInt1 + 1; k <= paramInt2; k++) {
/* 21 */         Object object = paramArrayOfObject[k];
/*    */         int m;
/* 23 */         for (m = k - 1; m >= paramInt1 && 
/* 24 */           paramCompareFunc.compare(paramArrayOfObject[m], object) > 0; m--) {
/* 25 */           paramArrayOfObject[m + 1] = paramArrayOfObject[m];
/*    */         }
/* 27 */         paramArrayOfObject[m + 1] = object;
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/* 32 */     Object object2 = paramArrayOfObject[paramInt2];
/* 33 */     int i = paramInt1 - 1;
/* 34 */     int j = paramInt2;
/*    */     
/*    */     while (true) {
/* 37 */       if (i < j && paramCompareFunc.compare(paramArrayOfObject[++i], object2) < 0)
/* 38 */         continue;  while (j > i && paramCompareFunc.compare(paramArrayOfObject[--j], object2) > 0);
/* 39 */       Object object = paramArrayOfObject[i]; paramArrayOfObject[i] = paramArrayOfObject[j]; paramArrayOfObject[j] = object;
/* 40 */       if (i >= j)
/*    */         break; 
/* 42 */     }  Object object1 = paramArrayOfObject[i]; paramArrayOfObject[i] = paramArrayOfObject[paramInt2]; paramArrayOfObject[paramInt2] = object1;
/*    */     
/* 44 */     sort(paramArrayOfObject, paramCompareFunc, paramInt1, i - 1);
/* 45 */     sort(paramArrayOfObject, paramCompareFunc, i + 1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedv\\util\Sort.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */