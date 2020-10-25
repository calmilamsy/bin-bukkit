/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public final class Strings
/*     */ {
/*  48 */   public static String nullToEmpty(@Nullable String string) { return (string == null) ? "" : string; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  59 */   public static String emptyToNull(@Nullable String string) { return isNullOrEmpty(string) ? null : string; }
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
/*  76 */   public static boolean isNullOrEmpty(@Nullable String string) { return (string == null || string.length() == 0); }
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
/*     */   public static String padStart(String string, int minLength, char padChar) {
/*  99 */     if (string.length() >= minLength) {
/* 100 */       return string;
/*     */     }
/* 102 */     StringBuilder sb = new StringBuilder(minLength);
/* 103 */     for (int i = string.length(); i < minLength; i++) {
/* 104 */       sb.append(padChar);
/*     */     }
/* 106 */     sb.append(string);
/* 107 */     return sb.toString();
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
/*     */   public static String padEnd(String string, int minLength, char padChar) {
/* 130 */     if (string.length() >= minLength) {
/* 131 */       return string;
/*     */     }
/* 133 */     StringBuilder sb = new StringBuilder(minLength);
/* 134 */     sb.append(string);
/* 135 */     for (int i = string.length(); i < minLength; i++) {
/* 136 */       sb.append(padChar);
/*     */     }
/* 138 */     return sb.toString();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String repeat(String string, int count) {
/* 153 */     Preconditions.checkArgument((count >= 0), "invalid count: %s", new Object[] { Integer.valueOf(count) });
/*     */ 
/*     */ 
/*     */     
/* 157 */     StringBuilder builder = new StringBuilder(string.length() * count);
/* 158 */     for (int i = 0; i < count; i++) {
/* 159 */       builder.append(string);
/*     */     }
/* 161 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Strings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */