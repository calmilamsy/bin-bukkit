/*     */ package com.google.gdata.util.common.base;
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
/*     */ 
/*     */ 
/*     */ public class PercentEscaper
/*     */   extends UnicodeEscaper
/*     */ {
/*     */   public static final String SAFECHARS_URLENCODER = "-_.*";
/*     */   public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
/*     */   public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
/*  86 */   private static final char[] URI_ESCAPED_SPACE = { '+' };
/*     */   
/*  88 */   private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean plusForSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean[] safeOctets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PercentEscaper(String safeChars, boolean plusForSpace) {
/* 116 */     if (safeChars.matches(".*[0-9A-Za-z].*")) {
/* 117 */       throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (plusForSpace && safeChars.contains(" ")) {
/* 124 */       throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
/*     */     }
/*     */     
/* 127 */     if (safeChars.contains("%")) {
/* 128 */       throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
/*     */     }
/*     */     
/* 131 */     this.plusForSpace = plusForSpace;
/* 132 */     this.safeOctets = createSafeOctets(safeChars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] createSafeOctets(String safeChars) {
/* 141 */     int maxChar = 122;
/* 142 */     char[] safeCharArray = safeChars.toCharArray();
/* 143 */     for (char c : safeCharArray) {
/* 144 */       maxChar = Math.max(c, maxChar);
/*     */     }
/* 146 */     boolean[] octets = new boolean[maxChar + 1];
/* 147 */     for (c = 48; c <= 57; c++) {
/* 148 */       octets[c] = true;
/*     */     }
/* 150 */     for (c = 65; c <= 90; c++) {
/* 151 */       octets[c] = true;
/*     */     }
/* 153 */     for (c = 97; c <= 122; c++) {
/* 154 */       octets[c] = true;
/*     */     }
/* 156 */     for (char c : safeCharArray) {
/* 157 */       octets[c] = true;
/*     */     }
/* 159 */     return octets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextEscapeIndex(CharSequence csq, int index, int end) {
/* 169 */     for (; index < end; index++) {
/* 170 */       char c = csq.charAt(index);
/* 171 */       if (c >= this.safeOctets.length || !this.safeOctets[c]) {
/*     */         break;
/*     */       }
/*     */     } 
/* 175 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String escape(String s) {
/* 185 */     int slen = s.length();
/* 186 */     for (int index = 0; index < slen; index++) {
/* 187 */       char c = s.charAt(index);
/* 188 */       if (c >= this.safeOctets.length || !this.safeOctets[c]) {
/* 189 */         return escapeSlow(s, index);
/*     */       }
/*     */     } 
/* 192 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected char[] escape(int cp) {
/* 202 */     if (cp < this.safeOctets.length && this.safeOctets[cp])
/* 203 */       return null; 
/* 204 */     if (cp == 32 && this.plusForSpace)
/* 205 */       return URI_ESCAPED_SPACE; 
/* 206 */     if (cp <= 127) {
/*     */ 
/*     */       
/* 209 */       char[] dest = new char[3];
/* 210 */       dest[0] = '%';
/* 211 */       dest[2] = UPPER_HEX_DIGITS[cp & 0xF];
/* 212 */       dest[1] = UPPER_HEX_DIGITS[cp >>> 4];
/* 213 */       return dest;
/* 214 */     }  if (cp <= 2047) {
/*     */ 
/*     */       
/* 217 */       char[] dest = new char[6];
/* 218 */       dest[0] = '%';
/* 219 */       dest[3] = '%';
/* 220 */       dest[5] = UPPER_HEX_DIGITS[cp & 0xF];
/* 221 */       cp >>>= 4;
/* 222 */       dest[4] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 223 */       cp >>>= 2;
/* 224 */       dest[2] = UPPER_HEX_DIGITS[cp & 0xF];
/* 225 */       cp >>>= 4;
/* 226 */       dest[1] = UPPER_HEX_DIGITS[0xC | cp];
/* 227 */       return dest;
/* 228 */     }  if (cp <= 65535) {
/*     */ 
/*     */       
/* 231 */       char[] dest = new char[9];
/* 232 */       dest[0] = '%';
/* 233 */       dest[1] = 'E';
/* 234 */       dest[3] = '%';
/* 235 */       dest[6] = '%';
/* 236 */       dest[8] = UPPER_HEX_DIGITS[cp & 0xF];
/* 237 */       cp >>>= 4;
/* 238 */       dest[7] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 239 */       cp >>>= 2;
/* 240 */       dest[5] = UPPER_HEX_DIGITS[cp & 0xF];
/* 241 */       cp >>>= 4;
/* 242 */       dest[4] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 243 */       cp >>>= 2;
/* 244 */       dest[2] = UPPER_HEX_DIGITS[cp];
/* 245 */       return dest;
/* 246 */     }  if (cp <= 1114111) {
/* 247 */       char[] dest = new char[12];
/*     */ 
/*     */       
/* 250 */       dest[0] = '%';
/* 251 */       dest[1] = 'F';
/* 252 */       dest[3] = '%';
/* 253 */       dest[6] = '%';
/* 254 */       dest[9] = '%';
/* 255 */       dest[11] = UPPER_HEX_DIGITS[cp & 0xF];
/* 256 */       cp >>>= 4;
/* 257 */       dest[10] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 258 */       cp >>>= 2;
/* 259 */       dest[8] = UPPER_HEX_DIGITS[cp & 0xF];
/* 260 */       cp >>>= 4;
/* 261 */       dest[7] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 262 */       cp >>>= 2;
/* 263 */       dest[5] = UPPER_HEX_DIGITS[cp & 0xF];
/* 264 */       cp >>>= 4;
/* 265 */       dest[4] = UPPER_HEX_DIGITS[0x8 | cp & 0x3];
/* 266 */       cp >>>= 2;
/* 267 */       dest[2] = UPPER_HEX_DIGITS[cp & 0x7];
/* 268 */       return dest;
/*     */     } 
/*     */     
/* 271 */     throw new IllegalArgumentException("Invalid unicode character value " + cp);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\gdat\\util\common\base\PercentEscaper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */