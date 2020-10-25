/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public final class Strings
/*     */ {
/*     */   public static final String EMPTY = "";
/*     */   public static final String SINGLE_QUOTE = "'";
/*  40 */   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
/*     */   
/*     */   static  {
/*  43 */     new Strings();
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
/*     */   public static String repeat(char ch, int count) {
/*  59 */     StringBuilder buffer = new StringBuilder();
/*     */     
/*  61 */     for (int i = 0; i < count; i++) {
/*  62 */       buffer.append(ch);
/*     */     }
/*  64 */     return buffer.toString();
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
/*  75 */   public static boolean isNullOrEmpty(String target) { return (target == null || "".equals(target)); }
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
/*  89 */   public static String surround(String target, char begin, char end) { return begin + target + end; }
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
/* 101 */   public static String join(String[] pieces, String separator) { return join(Arrays.asList(pieces), separator); }
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
/*     */   public static String join(List<String> pieces, String separator) {
/* 113 */     StringBuilder buffer = new StringBuilder();
/*     */     
/* 115 */     for (Iterator<String> iter = pieces.iterator(); iter.hasNext(); ) {
/* 116 */       buffer.append((String)iter.next());
/*     */       
/* 118 */       if (iter.hasNext()) {
/* 119 */         buffer.append(separator);
/*     */       }
/*     */     } 
/* 122 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\Strings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */