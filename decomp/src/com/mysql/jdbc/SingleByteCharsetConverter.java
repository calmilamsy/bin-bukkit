/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class SingleByteCharsetConverter
/*     */ {
/*     */   private static final int BYTE_RANGE = 256;
/*  45 */   private static byte[] allBytes = new byte[256];
/*  46 */   private static final Map CONVERTER_MAP = new HashMap();
/*     */   
/*  48 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static byte[] unknownCharsMap = new byte[65536];
/*     */   
/*     */   static  {
/*  56 */     for (i = -128; i <= 127; i++) {
/*  57 */       allBytes[i - -128] = (byte)i;
/*     */     }
/*     */     
/*  60 */     for (i = 0; i < unknownCharsMap.length; i++) {
/*  61 */       unknownCharsMap[i] = 63;
/*     */     }
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
/*     */   public static SingleByteCharsetConverter getInstance(String encodingName, Connection conn) throws UnsupportedEncodingException, SQLException {
/*  82 */     SingleByteCharsetConverter instance = (SingleByteCharsetConverter)CONVERTER_MAP.get(encodingName);
/*     */ 
/*     */     
/*  85 */     if (instance == null) {
/*  86 */       instance = initCharset(encodingName);
/*     */     }
/*     */     
/*  89 */     return instance;
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
/*     */   public static SingleByteCharsetConverter initCharset(String javaEncodingName) throws UnsupportedEncodingException, SQLException {
/* 104 */     if (CharsetMapping.isMultibyteCharset(javaEncodingName)) {
/* 105 */       return null;
/*     */     }
/*     */     
/* 108 */     SingleByteCharsetConverter converter = new SingleByteCharsetConverter(javaEncodingName);
/*     */ 
/*     */     
/* 111 */     CONVERTER_MAP.put(javaEncodingName, converter);
/*     */     
/* 113 */     return converter;
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
/* 133 */   public static String toStringDefaultEncoding(byte[] buffer, int startPos, int length) { return new String(buffer, startPos, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private char[] byteToChars = new char[256];
/*     */   
/* 141 */   private byte[] charToByteMap = new byte[65536];
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
/*     */   private SingleByteCharsetConverter(String encodingName) throws UnsupportedEncodingException {
/* 153 */     String allBytesString = new String(allBytes, false, 'Ā', encodingName);
/*     */     
/* 155 */     int allBytesLen = allBytesString.length();
/*     */     
/* 157 */     System.arraycopy(unknownCharsMap, 0, this.charToByteMap, 0, this.charToByteMap.length);
/*     */ 
/*     */     
/* 160 */     for (int i = 0; i < 256 && i < allBytesLen; i++) {
/* 161 */       char c = allBytesString.charAt(i);
/* 162 */       this.byteToChars[i] = c;
/* 163 */       this.charToByteMap[c] = allBytes[i];
/*     */     } 
/*     */   }
/*     */   
/*     */   public final byte[] toBytes(char[] c) {
/* 168 */     if (c == null) {
/* 169 */       return null;
/*     */     }
/*     */     
/* 172 */     int length = c.length;
/* 173 */     byte[] bytes = new byte[length];
/*     */     
/* 175 */     for (int i = 0; i < length; i++) {
/* 176 */       bytes[i] = this.charToByteMap[c[i]];
/*     */     }
/*     */     
/* 179 */     return bytes;
/*     */   }
/*     */   
/*     */   public final byte[] toBytesWrapped(char[] c, char beginWrap, char endWrap) {
/* 183 */     if (c == null) {
/* 184 */       return null;
/*     */     }
/*     */     
/* 187 */     int length = c.length + 2;
/* 188 */     int charLength = c.length;
/*     */     
/* 190 */     byte[] bytes = new byte[length];
/* 191 */     bytes[0] = this.charToByteMap[beginWrap];
/*     */     
/* 193 */     for (int i = 0; i < charLength; i++) {
/* 194 */       bytes[i + 1] = this.charToByteMap[c[i]];
/*     */     }
/*     */     
/* 197 */     bytes[length - 1] = this.charToByteMap[endWrap];
/*     */     
/* 199 */     return bytes;
/*     */   }
/*     */   
/*     */   public final byte[] toBytes(char[] chars, int offset, int length) {
/* 203 */     if (chars == null) {
/* 204 */       return null;
/*     */     }
/*     */     
/* 207 */     if (length == 0) {
/* 208 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 211 */     byte[] bytes = new byte[length];
/*     */     
/* 213 */     for (int i = 0; i < length; i++) {
/* 214 */       bytes[i] = this.charToByteMap[chars[i + offset]];
/*     */     }
/*     */     
/* 217 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] toBytes(String s) {
/* 228 */     if (s == null) {
/* 229 */       return null;
/*     */     }
/*     */     
/* 232 */     int length = s.length();
/* 233 */     byte[] bytes = new byte[length];
/*     */     
/* 235 */     for (int i = 0; i < length; i++) {
/* 236 */       bytes[i] = this.charToByteMap[s.charAt(i)];
/*     */     }
/*     */     
/* 239 */     return bytes;
/*     */   }
/*     */   
/*     */   public final byte[] toBytesWrapped(String s, char beginWrap, char endWrap) {
/* 243 */     if (s == null) {
/* 244 */       return null;
/*     */     }
/*     */     
/* 247 */     int stringLength = s.length();
/*     */     
/* 249 */     int length = stringLength + 2;
/*     */     
/* 251 */     byte[] bytes = new byte[length];
/*     */     
/* 253 */     bytes[0] = this.charToByteMap[beginWrap];
/*     */     
/* 255 */     for (int i = 0; i < stringLength; i++) {
/* 256 */       bytes[i + 1] = this.charToByteMap[s.charAt(i)];
/*     */     }
/*     */     
/* 259 */     bytes[length - 1] = this.charToByteMap[endWrap];
/*     */     
/* 261 */     return bytes;
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
/*     */   public final byte[] toBytes(String s, int offset, int length) {
/* 277 */     if (s == null) {
/* 278 */       return null;
/*     */     }
/*     */     
/* 281 */     if (length == 0) {
/* 282 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 285 */     byte[] bytes = new byte[length];
/*     */     
/* 287 */     for (int i = 0; i < length; i++) {
/* 288 */       char c = s.charAt(i + offset);
/* 289 */       bytes[i] = this.charToByteMap[c];
/*     */     } 
/*     */     
/* 292 */     return bytes;
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
/* 304 */   public final String toString(byte[] buffer) { return toString(buffer, 0, buffer.length); }
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
/*     */   public final String toString(byte[] buffer, int startPos, int length) {
/* 320 */     char[] charArray = new char[length];
/* 321 */     int readpoint = startPos;
/*     */     
/* 323 */     for (int i = 0; i < length; i++) {
/* 324 */       charArray[i] = this.byteToChars[buffer[readpoint] - Byte.MIN_VALUE];
/* 325 */       readpoint++;
/*     */     } 
/*     */     
/* 328 */     return new String(charArray);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\SingleByteCharsetConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */