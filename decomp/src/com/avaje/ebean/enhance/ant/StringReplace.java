/*     */ package com.avaje.ebean.enhance.ant;
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
/*     */ public class StringReplace
/*     */ {
/*  40 */   public static String replace(String source, String match, String replace) { return replaceString(source, match, replace, 30, 0, source.length()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String replaceString(String source, String match, String replace, int additionalSize, int startPos, int endPos) {
/*  51 */     char match0 = match.charAt(0);
/*     */     
/*  53 */     int matchLength = match.length();
/*     */     
/*  55 */     if (matchLength == 1 && replace.length() == 1) {
/*  56 */       char replace0 = replace.charAt(0);
/*  57 */       return source.replace(match0, replace0);
/*     */     } 
/*  59 */     if (matchLength >= replace.length()) {
/*  60 */       additionalSize = 0;
/*     */     }
/*     */ 
/*     */     
/*  64 */     int sourceLength = source.length();
/*  65 */     int lastMatch = endPos - matchLength;
/*     */     
/*  67 */     StringBuilder sb = new StringBuilder(sourceLength + additionalSize);
/*     */     
/*  69 */     if (startPos > 0) {
/*  70 */       sb.append(source.substring(0, startPos));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     for (int i = startPos; i < sourceLength; i++) {
/*  78 */       char sourceChar = source.charAt(i);
/*  79 */       if (i > lastMatch || sourceChar != match0) {
/*  80 */         sb.append(sourceChar);
/*     */       }
/*     */       else {
/*     */         
/*  84 */         boolean isMatch = true;
/*  85 */         int sourceMatchPos = i;
/*     */ 
/*     */         
/*  88 */         for (int j = 1; j < matchLength; j++) {
/*  89 */           sourceMatchPos++;
/*  90 */           if (source.charAt(sourceMatchPos) != match.charAt(j)) {
/*  91 */             isMatch = false;
/*     */             break;
/*     */           } 
/*     */         } 
/*  95 */         if (isMatch) {
/*  96 */           i = i + matchLength - 1;
/*  97 */           sb.append(replace);
/*     */         } else {
/*     */           
/* 100 */           sb.append(sourceChar);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\ant\StringReplace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */