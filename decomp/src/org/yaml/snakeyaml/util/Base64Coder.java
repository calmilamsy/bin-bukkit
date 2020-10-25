/*     */ package org.yaml.snakeyaml.util;
/*     */ 
/*     */ import org.yaml.snakeyaml.error.YAMLException;
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
/*     */ public abstract class Base64Coder
/*     */ {
/*  23 */   private static final char[] map1 = new char[64];
/*     */   static  {
/*  25 */     i = 0;
/*  26 */     for (c = 'A'; c <= 'Z'; c = (char)(c + '\001'))
/*  27 */       map1[i++] = c; 
/*  28 */     for (c = 'a'; c <= 'z'; c = (char)(c + '\001'))
/*  29 */       map1[i++] = c; 
/*  30 */     for (char c = '0'; c <= '9'; c = (char)(c + '\001'))
/*  31 */       map1[i++] = c; 
/*  32 */     map1[i++] = '+';
/*  33 */     map1[i++] = '/';
/*     */ 
/*     */ 
/*     */     
/*  37 */     map2 = new byte[128];
/*     */     
/*  39 */     for (i = 0; i < map2.length; i++)
/*  40 */       map2[i] = -1; 
/*  41 */     for (i = 0; i < 64; i++) {
/*  42 */       map2[map1[i]] = (byte)i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte[] map2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] encode(byte[] in) {
/*  54 */     int iLen = in.length;
/*  55 */     int oDataLen = (iLen * 4 + 2) / 3;
/*  56 */     int oLen = (iLen + 2) / 3 * 4;
/*  57 */     char[] out = new char[oLen];
/*  58 */     int ip = 0;
/*  59 */     int op = 0;
/*  60 */     while (ip < iLen) {
/*  61 */       int i0 = in[ip++] & 0xFF;
/*  62 */       int i1 = (ip < iLen) ? (in[ip++] & 0xFF) : 0;
/*  63 */       int i2 = (ip < iLen) ? (in[ip++] & 0xFF) : 0;
/*  64 */       int o0 = i0 >>> 2;
/*  65 */       int o1 = (i0 & 0x3) << 4 | i1 >>> 4;
/*  66 */       int o2 = (i1 & 0xF) << 2 | i2 >>> 6;
/*  67 */       int o3 = i2 & 0x3F;
/*  68 */       out[op++] = map1[o0];
/*  69 */       out[op++] = map1[o1];
/*  70 */       out[op] = (op < oDataLen) ? map1[o2] : '=';
/*  71 */       op++;
/*  72 */       out[op] = (op < oDataLen) ? map1[o3] : '=';
/*  73 */       op++;
/*     */     } 
/*  75 */     return out;
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
/*     */   public static byte[] decode(char[] in) {
/*  89 */     int iLen = in.length;
/*  90 */     if (iLen % 4 != 0)
/*  91 */       throw new YAMLException("Length of Base64 encoded input string is not a multiple of 4."); 
/*  92 */     while (iLen > 0 && in[iLen - 1] == '=')
/*  93 */       iLen--; 
/*  94 */     int oLen = iLen * 3 / 4;
/*  95 */     byte[] out = new byte[oLen];
/*  96 */     int ip = 0;
/*  97 */     int op = 0;
/*  98 */     while (ip < iLen) {
/*  99 */       int i0 = in[ip++];
/* 100 */       int i1 = in[ip++];
/* 101 */       int i2 = (ip < iLen) ? in[ip++] : 65;
/* 102 */       int i3 = (ip < iLen) ? in[ip++] : 65;
/* 103 */       if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
/* 104 */         throw new YAMLException("Illegal character in Base64 encoded data."); 
/* 105 */       int b0 = map2[i0];
/* 106 */       int b1 = map2[i1];
/* 107 */       int b2 = map2[i2];
/* 108 */       int b3 = map2[i3];
/* 109 */       if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
/* 110 */         throw new YAMLException("Illegal character in Base64 encoded data."); 
/* 111 */       int o0 = b0 << 2 | b1 >>> 4;
/* 112 */       int o1 = (b1 & 0xF) << 4 | b2 >>> 2;
/* 113 */       int o2 = (b2 & 0x3) << 6 | b3;
/* 114 */       out[op++] = (byte)o0;
/* 115 */       if (op < oLen)
/* 116 */         out[op++] = (byte)o1; 
/* 117 */       if (op < oLen)
/* 118 */         out[op++] = (byte)o2; 
/*     */     } 
/* 120 */     return out;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyam\\util\Base64Coder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */