/*     */ package com.mysql.jdbc;
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
/*     */ public class Constants
/*     */ {
/*  39 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final String MILLIS_I18N = Messages.getString("Milliseconds");
/*     */   
/*  46 */   public static final byte[] SLASH_STAR_SPACE_AS_BYTES = { 47, 42, 32 };
/*     */ 
/*     */   
/*  49 */   public static final byte[] SPACE_STAR_SLASH_SPACE_AS_BYTES = { 32, 42, 47, 32 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final Character[] CHARACTER_CACHE = new Character[128];
/*     */   
/*     */   private static final int BYTE_CACHE_OFFSET = 128;
/*     */   
/*  59 */   private static final Byte[] BYTE_CACHE = new Byte[256];
/*     */   
/*     */   private static final int INTEGER_CACHE_OFFSET = 128;
/*     */   
/*  63 */   private static final Integer[] INTEGER_CACHE = new Integer[256];
/*     */   
/*     */   private static final int SHORT_CACHE_OFFSET = 128;
/*     */   
/*  67 */   private static final Short[] SHORT_CACHE = new Short[256];
/*     */   
/*  69 */   private static final Long[] LONG_CACHE = new Long[256];
/*     */   
/*     */   private static final int LONG_CACHE_OFFSET = 128;
/*     */   
/*     */   static  {
/*  74 */     for (i = 0; i < CHARACTER_CACHE.length; i++) {
/*  75 */       CHARACTER_CACHE[i] = new Character((char)i);
/*     */     }
/*     */     
/*  78 */     for (i = 0; i < INTEGER_CACHE.length; i++) {
/*  79 */       INTEGER_CACHE[i] = new Integer(i - 128);
/*     */     }
/*     */     
/*  82 */     for (i = 0; i < SHORT_CACHE.length; i++) {
/*  83 */       SHORT_CACHE[i] = new Short((short)(i - 128));
/*     */     }
/*     */     
/*  86 */     for (i = 0; i < LONG_CACHE.length; i++) {
/*  87 */       LONG_CACHE[i] = new Long((i - 128));
/*     */     }
/*     */     
/*  90 */     for (i = 0; i < BYTE_CACHE.length; i++) {
/*  91 */       BYTE_CACHE[i] = new Byte((byte)(i - 128));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Character characterValueOf(char c) {
/*  97 */     if (c <= '') {
/*  98 */       return CHARACTER_CACHE[c];
/*     */     }
/*     */     
/* 101 */     return new Character(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final Byte byteValueOf(byte b) { return BYTE_CACHE[b + 128]; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Integer integerValueOf(int i) {
/* 113 */     if (i >= -128 && i <= 127) {
/* 114 */       return INTEGER_CACHE[i + 128];
/*     */     }
/*     */     
/* 117 */     return new Integer(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Short shortValueOf(short s) {
/* 124 */     if (s >= -128 && s <= 127) {
/* 125 */       return SHORT_CACHE[s + 128];
/*     */     }
/*     */     
/* 128 */     return new Short(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Long longValueOf(long l) {
/* 134 */     if (l >= -128L && l <= 127L) {
/* 135 */       return LONG_CACHE[(int)l + 128];
/*     */     }
/*     */     
/* 138 */     return new Long(l);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */