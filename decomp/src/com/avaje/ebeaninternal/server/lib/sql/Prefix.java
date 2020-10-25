/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Prefix
/*     */ {
/*  28 */   private static final Logger logger = Logger.getLogger(Prefix.class.getName());
/*     */   private static final int[] oa = { 
/*  30 */       50, 12, 4, 6, 8, 10, 7, 23, 45, 23, 6, 9, 12, 2, 8, 34 };
/*     */   
/*     */   public static String getProp(String prop) {
/*  33 */     String v = dec(prop);
/*  34 */     int p = v.indexOf(":");
/*  35 */     return v.substring(1, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  40 */     String m = e(args[0]);
/*  41 */     logger.info("[" + m + "]");
/*  42 */     String o = getProp(m);
/*  43 */     logger.info("[" + o + "]");
/*     */   }
/*     */   
/*     */   public static String e(String msg) {
/*  47 */     msg = elen(msg, 40);
/*  48 */     return enc(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte az(byte c, int offset) {
/*  53 */     int z = c + offset;
/*  54 */     if (z > 122)
/*     */     {
/*  56 */       z = z - 122 + 48 - 1;
/*     */     }
/*     */     
/*  59 */     return (byte)z;
/*     */   }
/*     */   
/*     */   public static byte bz(byte c, int offset) {
/*  63 */     int z = c - offset;
/*  64 */     if (z < 48)
/*     */     {
/*  66 */       z = z + 122 - 48 + 1;
/*     */     }
/*  68 */     return (byte)z;
/*     */   }
/*     */   
/*     */   public static String enc(String msg) {
/*  72 */     byte[] msgbytes = msg.getBytes();
/*  73 */     byte[] encbytes = new byte[msgbytes.length + 1];
/*  74 */     Random r = new Random();
/*  75 */     int key = r.nextInt(70);
/*     */     
/*  77 */     char k = (char)(key + 48);
/*     */     
/*  79 */     encbytes[0] = az((byte)k, oa[0]);
/*     */     
/*  81 */     int ios = key;
/*  82 */     for (int i = 1; i < msgbytes.length + 1; i++) {
/*  83 */       encbytes[i] = az(msgbytes[i - 1], oa[(i + ios) % oa.length]);
/*     */     }
/*  85 */     return new String(encbytes);
/*     */   }
/*     */   
/*     */   public static String dec(String msg) {
/*  89 */     byte[] msgbytes = msg.getBytes();
/*  90 */     byte[] encbytes = new byte[msgbytes.length];
/*     */     
/*  92 */     encbytes[0] = bz(msgbytes[0], oa[0]);
/*  93 */     byte key = encbytes[0];
/*  94 */     int ios = key - 48;
/*  95 */     for (int i = 1; i < msgbytes.length; i++) {
/*  96 */       encbytes[i] = bz(msgbytes[i], oa[(i + ios) % oa.length]);
/*     */     }
/*  98 */     return new String(encbytes);
/*     */   }
/*     */   
/*     */   public static String elen(String msg, int len) {
/* 102 */     Random r = new Random();
/* 103 */     if (msg.length() < len) {
/* 104 */       int max = len - msg.length();
/* 105 */       StringBuilder sb = new StringBuilder();
/* 106 */       sb.append(msg).append(":");
/* 107 */       for (int i = 1; i < max; i++) {
/* 108 */         int bc = r.nextInt(74);
/* 109 */         sb.append(Character.toString((char)(bc + 48)));
/*     */       } 
/* 111 */       return sb.toString();
/*     */     } 
/* 113 */     return msg;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\Prefix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */