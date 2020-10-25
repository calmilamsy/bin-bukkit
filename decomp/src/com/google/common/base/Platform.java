/*    */ package com.google.common.base;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ final class Platform
/*    */ {
/* 36 */   static boolean isInstance(Class<?> clazz, Object obj) { return clazz.isInstance(obj); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   static char[] charBufferFromThreadLocal() { return (char[])DEST_TL.get(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>()
/*    */     {
/*    */       protected char[] initialValue() {
/* 52 */         return new char[1024];
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 57 */   static CharMatcher precomputeCharMatcher(CharMatcher matcher) { return matcher.precomputedInternal(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */