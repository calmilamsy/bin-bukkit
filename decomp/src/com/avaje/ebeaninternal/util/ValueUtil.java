/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueUtil
/*    */ {
/*    */   public static boolean areEqual(Object obj1, Object obj2) {
/* 13 */     if (obj1 == null) {
/* 14 */       return (obj2 == null);
/*    */     }
/* 16 */     if (obj2 == null) {
/* 17 */       return false;
/*    */     }
/* 19 */     if (obj1 == obj2) {
/* 20 */       return true;
/*    */     }
/* 22 */     if (obj1 instanceof java.math.BigDecimal) {
/*    */ 
/*    */       
/* 25 */       if (obj2 instanceof java.math.BigDecimal) {
/* 26 */         Comparable<Object> com1 = (Comparable)obj1;
/* 27 */         return (com1.compareTo(obj2) == 0);
/*    */       } 
/*    */       
/* 30 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     if (obj1 instanceof java.net.URL)
/*    */     {
/* 36 */       return obj1.toString().equals(obj2.toString());
/*    */     }
/* 38 */     if (obj1 instanceof byte[] && obj2 instanceof byte[]) {
/* 39 */       return areEqualBytes((byte[])obj1, (byte[])obj2);
/*    */     }
/* 41 */     if (obj1 instanceof char[] && obj2 instanceof char[]) {
/* 42 */       return areEqualChars((char[])obj1, (char[])obj2);
/*    */     }
/* 44 */     return obj1.equals(obj2);
/*    */   }
/*    */   
/*    */   private static boolean areEqualBytes(byte[] b1, byte[] b2) {
/* 48 */     if (b1.length != b2.length) {
/* 49 */       return false;
/*    */     }
/* 51 */     for (int i = 0; i < b1.length; i++) {
/* 52 */       if (b1[i] != b2[i]) {
/* 53 */         return false;
/*    */       }
/*    */     } 
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   private static boolean areEqualChars(char[] b1, char[] b2) {
/* 60 */     if (b1.length != b2.length) {
/* 61 */       return false;
/*    */     }
/* 63 */     for (int i = 0; i < b1.length; i++) {
/* 64 */       if (b1[i] != b2[i]) {
/* 65 */         return false;
/*    */       }
/*    */     } 
/* 68 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\ValueUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */