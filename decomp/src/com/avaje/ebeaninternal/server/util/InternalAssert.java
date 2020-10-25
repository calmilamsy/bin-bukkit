/*    */ package com.avaje.ebeaninternal.server.util;
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
/*    */ public class InternalAssert
/*    */ {
/*    */   public static void notNull(Object o, String msg) {
/* 31 */     if (o == null) {
/* 32 */       throw new IllegalStateException(msg);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void isTrue(boolean b, String msg) {
/* 40 */     if (!b)
/* 41 */       throw new IllegalStateException(msg); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\InternalAssert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */