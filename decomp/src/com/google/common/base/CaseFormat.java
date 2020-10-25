/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
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
/*     */ public static enum CaseFormat
/*     */ {
/*  35 */   LOWER_HYPHEN(CharMatcher.is('-'), "-"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   LOWER_UNDERSCORE(CharMatcher.is('_'), "_"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   UPPER_UNDERSCORE(CharMatcher.is('_'), "_");
/*     */   
/*     */   private final CharMatcher wordBoundary;
/*     */   private final String wordSeparator;
/*     */   
/*     */   CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
/*  61 */     this.wordBoundary = wordBoundary;
/*  62 */     this.wordSeparator = wordSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String to(CaseFormat format, String s) {
/*  72 */     if (format == null) {
/*  73 */       throw new NullPointerException();
/*     */     }
/*  75 */     if (s == null) {
/*  76 */       throw new NullPointerException();
/*     */     }
/*     */     
/*  79 */     if (format == this) {
/*  80 */       return s;
/*     */     }
/*     */ 
/*     */     
/*  84 */     switch (this) {
/*     */       case LOWER_HYPHEN:
/*  86 */         switch (format)
/*     */         { case LOWER_UNDERSCORE:
/*  88 */             return s.replace('-', '_');
/*     */           case UPPER_UNDERSCORE: case null:
/*  90 */             break; }  return toUpperCaseAscii(s.replace('-', '_'));
/*     */ 
/*     */       
/*     */       case LOWER_UNDERSCORE:
/*  94 */         switch (format)
/*     */         { case LOWER_HYPHEN:
/*  96 */             return s.replace('_', '-');
/*     */           case UPPER_UNDERSCORE: case null:
/*  98 */             break; }  return toUpperCaseAscii(s);
/*     */ 
/*     */       
/*     */       case UPPER_UNDERSCORE:
/* 102 */         switch (format)
/*     */         { case LOWER_HYPHEN:
/* 104 */             return toLowerCaseAscii(s.replace('_', '-'));
/*     */           case LOWER_UNDERSCORE: case null:
/* 106 */             break; }  return toLowerCaseAscii(s);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     StringBuilder out = null;
/* 113 */     int i = 0;
/* 114 */     int j = -1;
/* 115 */     while ((j = this.wordBoundary.indexIn(s, ++j)) != -1) {
/* 116 */       if (i == 0) {
/*     */         
/* 118 */         out = new StringBuilder(s.length() + 4 * this.wordSeparator.length());
/* 119 */         out.append(format.normalizeFirstWord(s.substring(i, j)));
/*     */       } else {
/* 121 */         out.append(format.normalizeWord(s.substring(i, j)));
/*     */       } 
/* 123 */       out.append(format.wordSeparator);
/* 124 */       i = j + this.wordSeparator.length();
/*     */     } 
/* 126 */     if (i == 0) {
/* 127 */       return format.normalizeFirstWord(s);
/*     */     }
/* 129 */     out.append(format.normalizeWord(s.substring(i)));
/* 130 */     return out.toString();
/*     */   }
/*     */   
/*     */   private String normalizeFirstWord(String word) {
/* 134 */     switch (this) { case LOWER_CAMEL:
/* 135 */         return toLowerCaseAscii(word); }
/* 136 */      return normalizeWord(word);
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalizeWord(String word) {
/* 141 */     switch (this) { case LOWER_HYPHEN:
/* 142 */         return toLowerCaseAscii(word);
/* 143 */       case LOWER_UNDERSCORE: return toLowerCaseAscii(word);
/* 144 */       case LOWER_CAMEL: return firstCharOnlyToUpper(word);
/* 145 */       case UPPER_CAMEL: return firstCharOnlyToUpper(word);
/* 146 */       case UPPER_UNDERSCORE: return toUpperCaseAscii(word); }
/*     */     
/* 148 */     throw new RuntimeException("unknown case: " + this);
/*     */   }
/*     */   
/*     */   private static String firstCharOnlyToUpper(String word) {
/* 152 */     int length = word.length();
/* 153 */     return (length == 0) ? word : length.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static String toUpperCaseAscii(String string) {
/* 160 */     int length = string.length();
/* 161 */     StringBuilder builder = new StringBuilder(length);
/* 162 */     for (int i = 0; i < length; i++) {
/* 163 */       builder.append(charToUpperCaseAscii(string.charAt(i)));
/*     */     }
/* 165 */     return builder.toString();
/*     */   }
/*     */   @VisibleForTesting
/*     */   static String toLowerCaseAscii(String string) {
/* 169 */     int length = string.length();
/* 170 */     StringBuilder builder = new StringBuilder(length);
/* 171 */     for (int i = 0; i < length; i++) {
/* 172 */       builder.append(charToLowerCaseAscii(string.charAt(i)));
/*     */     }
/* 174 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/* 178 */   private static char charToUpperCaseAscii(char c) { return isLowerCase(c) ? (char)(c & 0x5F) : c; }
/*     */ 
/*     */ 
/*     */   
/* 182 */   private static char charToLowerCaseAscii(char c) { return isUpperCase(c) ? (char)(c ^ 0x20) : c; }
/*     */ 
/*     */ 
/*     */   
/* 186 */   private static boolean isLowerCase(char c) { return (c >= 'a' && c <= 'z'); }
/*     */ 
/*     */ 
/*     */   
/* 190 */   private static boolean isUpperCase(char c) { return (c >= 'A' && c <= 'Z'); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\CaseFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */