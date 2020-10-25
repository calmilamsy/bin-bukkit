/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InternString
/*    */ {
/* 14 */   private static HashMap<String, String> map = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String intern(String s) {
/* 22 */     if (s == null) {
/* 23 */       return null;
/*    */     }
/*    */     
/* 26 */     synchronized (map) {
/* 27 */       String v = (String)map.get(s);
/* 28 */       if (v != null) {
/* 29 */         return v;
/*    */       }
/* 31 */       map.put(s, s);
/* 32 */       return s;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\InternString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */