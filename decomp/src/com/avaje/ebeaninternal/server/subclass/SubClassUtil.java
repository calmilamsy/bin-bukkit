/*    */ package com.avaje.ebeaninternal.server.subclass;
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
/*    */ public class SubClassUtil
/*    */   implements GenSuffix
/*    */ {
/* 33 */   public static boolean isSubClass(String className) { return (className.lastIndexOf("$$EntityBean") != -1); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSuperClassName(String className) {
/* 40 */     int dPos = className.lastIndexOf("$$EntityBean");
/* 41 */     if (dPos > -1) {
/* 42 */       return className.substring(0, dPos);
/*    */     }
/* 44 */     return className;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\SubClassUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */