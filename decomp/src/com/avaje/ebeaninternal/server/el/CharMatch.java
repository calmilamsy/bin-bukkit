/*    */ package com.avaje.ebeaninternal.server.el;
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
/*    */ public final class CharMatch
/*    */ {
/*    */   private final char[] upperChars;
/*    */   private final int maxLength;
/*    */   
/*    */   public CharMatch(String s) {
/* 35 */     this.upperChars = s.toUpperCase().toCharArray();
/* 36 */     this.maxLength = this.upperChars.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean startsWith(String other) {
/* 41 */     if (other == null || other.length() < this.maxLength) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     char[] ta = other.toCharArray();
/*    */     
/* 47 */     int pos = -1;
/* 48 */     while (++pos < this.maxLength) {
/* 49 */       char c1 = this.upperChars[pos];
/* 50 */       char c2 = Character.toUpperCase(ta[pos]);
/* 51 */       if (c1 != c2) {
/* 52 */         return false;
/*    */       }
/*    */     } 
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean endsWith(String other) {
/* 60 */     if (other == null || other.length() < this.maxLength) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     char[] ta = other.toCharArray();
/*    */     
/* 66 */     int offset = ta.length - this.maxLength;
/* 67 */     int pos = this.maxLength;
/* 68 */     while (--pos >= 0) {
/* 69 */       char c1 = this.upperChars[pos];
/* 70 */       char c2 = Character.toUpperCase(ta[offset + pos]);
/* 71 */       if (c1 != c2) {
/* 72 */         return false;
/*    */       }
/*    */     } 
/* 75 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\CharMatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */