/*     */ package com.google.gdata.util.common.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class UnicodeEscaper
/*     */   implements Escaper
/*     */ {
/*     */   private static final int DEST_PAD = 32;
/*     */   
/*     */   protected abstract char[] escape(int paramInt);
/*     */   
/*     */   protected int nextEscapeIndex(CharSequence csq, int start, int end) {
/* 106 */     int index = start;
/* 107 */     while (index < end) {
/* 108 */       int cp = codePointAt(csq, index, end);
/* 109 */       if (cp < 0 || escape(cp) != null) {
/*     */         break;
/*     */       }
/* 112 */       index += (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
/*     */     } 
/* 114 */     return index;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String escape(String string) {
/* 141 */     int end = string.length();
/* 142 */     int index = nextEscapeIndex(string, 0, end);
/* 143 */     return (index == end) ? string : escapeSlow(string, index);
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
/*     */   protected final String escapeSlow(String s, int index) {
/* 164 */     int end = s.length();
/*     */ 
/*     */     
/* 167 */     char[] dest = (char[])DEST_TL.get();
/* 168 */     int destIndex = 0;
/* 169 */     int unescapedChunkStart = 0;
/*     */     
/* 171 */     while (index < end) {
/* 172 */       int cp = codePointAt(s, index, end);
/* 173 */       if (cp < 0) {
/* 174 */         throw new IllegalArgumentException("Trailing high surrogate at end of input");
/*     */       }
/*     */       
/* 177 */       char[] escaped = escape(cp);
/* 178 */       if (escaped != null) {
/* 179 */         int charsSkipped = index - unescapedChunkStart;
/*     */ 
/*     */ 
/*     */         
/* 183 */         int sizeNeeded = destIndex + charsSkipped + escaped.length;
/* 184 */         if (dest.length < sizeNeeded) {
/* 185 */           int destLength = sizeNeeded + end - index + 32;
/* 186 */           dest = growBuffer(dest, destIndex, destLength);
/*     */         } 
/*     */         
/* 189 */         if (charsSkipped > 0) {
/* 190 */           s.getChars(unescapedChunkStart, index, dest, destIndex);
/* 191 */           destIndex += charsSkipped;
/*     */         } 
/* 193 */         if (escaped.length > 0) {
/* 194 */           System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
/* 195 */           destIndex += escaped.length;
/*     */         } 
/*     */       } 
/* 198 */       unescapedChunkStart = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
/*     */       
/* 200 */       index = nextEscapeIndex(s, unescapedChunkStart, end);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 205 */     int charsSkipped = end - unescapedChunkStart;
/* 206 */     if (charsSkipped > 0) {
/* 207 */       int endIndex = destIndex + charsSkipped;
/* 208 */       if (dest.length < endIndex) {
/* 209 */         dest = growBuffer(dest, destIndex, endIndex);
/*     */       }
/* 211 */       s.getChars(unescapedChunkStart, end, dest, destIndex);
/* 212 */       destIndex = endIndex;
/*     */     } 
/* 214 */     return new String(dest, false, destIndex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Appendable escape(final Appendable out) {
/* 252 */     assert out != null;
/*     */     
/* 254 */     return new Appendable() {
/* 255 */         int pendingHighSurrogate = -1;
/* 256 */         char[] decodedChars = new char[2];
/*     */ 
/*     */         
/* 259 */         public Appendable append(CharSequence csq) throws IOException { return append(csq, 0, csq.length()); }
/*     */ 
/*     */ 
/*     */         
/*     */         public Appendable append(CharSequence csq, int start, int end) throws IOException {
/* 264 */           int index = start;
/* 265 */           if (index < end) {
/*     */ 
/*     */ 
/*     */             
/* 269 */             int unescapedChunkStart = index;
/* 270 */             if (this.pendingHighSurrogate != -1) {
/*     */ 
/*     */               
/* 273 */               char c = csq.charAt(index++);
/* 274 */               if (!Character.isLowSurrogate(c)) {
/* 275 */                 throw new IllegalArgumentException("Expected low surrogate character but got " + c);
/*     */               }
/*     */               
/* 278 */               char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
/*     */               
/* 280 */               if (escaped != null) {
/*     */ 
/*     */                 
/* 283 */                 outputChars(escaped, escaped.length);
/* 284 */                 unescapedChunkStart++;
/*     */               }
/*     */               else {
/*     */                 
/* 288 */                 out.append((char)this.pendingHighSurrogate);
/*     */               } 
/* 290 */               this.pendingHighSurrogate = -1;
/*     */             } 
/*     */             
/*     */             while (true) {
/* 294 */               index = UnicodeEscaper.this.nextEscapeIndex(csq, index, end);
/* 295 */               if (index > unescapedChunkStart) {
/* 296 */                 out.append(csq, unescapedChunkStart, index);
/*     */               }
/* 298 */               if (index == end) {
/*     */                 break;
/*     */               }
/*     */               
/* 302 */               int cp = UnicodeEscaper.codePointAt(csq, index, end);
/* 303 */               if (cp < 0) {
/*     */ 
/*     */                 
/* 306 */                 this.pendingHighSurrogate = -cp;
/*     */                 
/*     */                 break;
/*     */               } 
/* 310 */               char[] escaped = UnicodeEscaper.this.escape(cp);
/* 311 */               if (escaped != null) {
/* 312 */                 outputChars(escaped, escaped.length);
/*     */               }
/*     */               else {
/*     */                 
/* 316 */                 int len = Character.toChars(cp, this.decodedChars, 0);
/* 317 */                 outputChars(this.decodedChars, len);
/*     */               } 
/*     */               
/* 320 */               index += (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
/* 321 */               unescapedChunkStart = index;
/*     */             } 
/*     */           } 
/* 324 */           return this;
/*     */         }
/*     */         
/*     */         public Appendable append(char c) throws IOException {
/* 328 */           if (this.pendingHighSurrogate != -1) {
/*     */ 
/*     */             
/* 331 */             if (!Character.isLowSurrogate(c)) {
/* 332 */               throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
/*     */             }
/*     */ 
/*     */             
/* 336 */             char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
/*     */             
/* 338 */             if (escaped != null) {
/* 339 */               outputChars(escaped, escaped.length);
/*     */             } else {
/* 341 */               out.append((char)this.pendingHighSurrogate);
/* 342 */               out.append(c);
/*     */             } 
/* 344 */             this.pendingHighSurrogate = -1;
/* 345 */           } else if (Character.isHighSurrogate(c)) {
/*     */             
/* 347 */             this.pendingHighSurrogate = c;
/*     */           } else {
/* 349 */             if (Character.isLowSurrogate(c)) {
/* 350 */               throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 355 */             char[] escaped = UnicodeEscaper.this.escape(c);
/* 356 */             if (escaped != null) {
/* 357 */               outputChars(escaped, escaped.length);
/*     */             } else {
/* 359 */               out.append(c);
/*     */             } 
/*     */           } 
/* 362 */           return this;
/*     */         }
/*     */         
/*     */         private void outputChars(char[] chars, int len) throws IOException {
/* 366 */           for (int n = 0; n < len; n++) {
/* 367 */             out.append(chars[n]);
/*     */           }
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int codePointAt(CharSequence seq, int index, int end) {
/* 406 */     if (index < end) {
/* 407 */       char c1 = seq.charAt(index++);
/* 408 */       if (c1 < '?' || c1 > '?')
/*     */       {
/*     */         
/* 411 */         return c1; } 
/* 412 */       if (c1 <= '?') {
/*     */         
/* 414 */         if (index == end) {
/* 415 */           return -c1;
/*     */         }
/*     */         
/* 418 */         char c2 = seq.charAt(index);
/* 419 */         if (Character.isLowSurrogate(c2)) {
/* 420 */           return Character.toCodePoint(c1, c2);
/*     */         }
/* 422 */         throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index);
/*     */       } 
/*     */ 
/*     */       
/* 426 */       throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index - 1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 431 */     throw new IndexOutOfBoundsException("Index exceeds specified range");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char[] growBuffer(char[] dest, int index, int size) {
/* 440 */     char[] copy = new char[size];
/* 441 */     if (index > 0) {
/* 442 */       System.arraycopy(dest, 0, copy, 0, index);
/*     */     }
/* 444 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 452 */   private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>()
/*     */     {
/*     */       protected char[] initialValue() {
/* 455 */         return new char[1024];
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\gdat\\util\common\base\UnicodeEscaper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */