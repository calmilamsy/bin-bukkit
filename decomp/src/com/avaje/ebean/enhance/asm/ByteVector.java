/*     */ package com.avaje.ebean.enhance.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteVector
/*     */ {
/*     */   byte[] data;
/*     */   int length;
/*     */   
/*  55 */   public ByteVector() { this.data = new byte[64]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public ByteVector(int initialSize) { this.data = new byte[initialSize]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector putByte(int b) {
/*  76 */     int length = this.length;
/*  77 */     if (length + 1 > this.data.length) {
/*  78 */       enlarge(1);
/*     */     }
/*  80 */     this.data[length++] = (byte)b;
/*  81 */     this.length = length;
/*  82 */     return this;
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
/*     */   ByteVector put11(int b1, int b2) {
/*  94 */     int length = this.length;
/*  95 */     if (length + 2 > this.data.length) {
/*  96 */       enlarge(2);
/*     */     }
/*  98 */     byte[] data = this.data;
/*  99 */     data[length++] = (byte)b1;
/* 100 */     data[length++] = (byte)b2;
/* 101 */     this.length = length;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector putShort(int s) {
/* 113 */     int length = this.length;
/* 114 */     if (length + 2 > this.data.length) {
/* 115 */       enlarge(2);
/*     */     }
/* 117 */     byte[] data = this.data;
/* 118 */     data[length++] = (byte)(s >>> 8);
/* 119 */     data[length++] = (byte)s;
/* 120 */     this.length = length;
/* 121 */     return this;
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
/*     */   ByteVector put12(int b, int s) {
/* 133 */     int length = this.length;
/* 134 */     if (length + 3 > this.data.length) {
/* 135 */       enlarge(3);
/*     */     }
/* 137 */     byte[] data = this.data;
/* 138 */     data[length++] = (byte)b;
/* 139 */     data[length++] = (byte)(s >>> 8);
/* 140 */     data[length++] = (byte)s;
/* 141 */     this.length = length;
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector putInt(int i) {
/* 153 */     int length = this.length;
/* 154 */     if (length + 4 > this.data.length) {
/* 155 */       enlarge(4);
/*     */     }
/* 157 */     byte[] data = this.data;
/* 158 */     data[length++] = (byte)(i >>> 24);
/* 159 */     data[length++] = (byte)(i >>> 16);
/* 160 */     data[length++] = (byte)(i >>> 8);
/* 161 */     data[length++] = (byte)i;
/* 162 */     this.length = length;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector putLong(long l) {
/* 174 */     int length = this.length;
/* 175 */     if (length + 8 > this.data.length) {
/* 176 */       enlarge(8);
/*     */     }
/* 178 */     byte[] data = this.data;
/* 179 */     int i = (int)(l >>> 32);
/* 180 */     data[length++] = (byte)(i >>> 24);
/* 181 */     data[length++] = (byte)(i >>> 16);
/* 182 */     data[length++] = (byte)(i >>> 8);
/* 183 */     data[length++] = (byte)i;
/* 184 */     i = (int)l;
/* 185 */     data[length++] = (byte)(i >>> 24);
/* 186 */     data[length++] = (byte)(i >>> 16);
/* 187 */     data[length++] = (byte)(i >>> 8);
/* 188 */     data[length++] = (byte)i;
/* 189 */     this.length = length;
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector putUTF8(String s) {
/* 201 */     int charLength = s.length();
/* 202 */     if (this.length + 2 + charLength > this.data.length) {
/* 203 */       enlarge(2 + charLength);
/*     */     }
/* 205 */     int len = this.length;
/* 206 */     byte[] data = this.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     data[len++] = (byte)(charLength >>> 8);
/* 214 */     data[len++] = (byte)charLength;
/* 215 */     for (int i = 0; i < charLength; i++) {
/* 216 */       char c = s.charAt(i);
/* 217 */       if (c >= '\001' && c <= '') {
/* 218 */         data[len++] = (byte)c;
/*     */       } else {
/* 220 */         int byteLength = i;
/* 221 */         for (j = i; j < charLength; j++) {
/* 222 */           c = s.charAt(j);
/* 223 */           if (c >= '\001' && c <= '') {
/* 224 */             byteLength++;
/* 225 */           } else if (c > '߿') {
/* 226 */             byteLength += 3;
/*     */           } else {
/* 228 */             byteLength += 2;
/*     */           } 
/*     */         } 
/* 231 */         data[this.length] = (byte)(byteLength >>> 8);
/* 232 */         data[this.length + 1] = (byte)byteLength;
/* 233 */         if (this.length + 2 + byteLength > data.length) {
/* 234 */           this.length = len;
/* 235 */           enlarge(2 + byteLength);
/* 236 */           data = this.data;
/*     */         } 
/* 238 */         for (int j = i; j < charLength; j++) {
/* 239 */           c = s.charAt(j);
/* 240 */           if (c >= '\001' && c <= '') {
/* 241 */             data[len++] = (byte)c;
/* 242 */           } else if (c > '߿') {
/* 243 */             data[len++] = (byte)(0xE0 | c >> '\f' & 0xF);
/* 244 */             data[len++] = (byte)(0x80 | c >> '\006' & 0x3F);
/* 245 */             data[len++] = (byte)(0x80 | c & 0x3F);
/*     */           } else {
/* 247 */             data[len++] = (byte)(0xC0 | c >> '\006' & 0x1F);
/* 248 */             data[len++] = (byte)(0x80 | c & 0x3F);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/* 254 */     this.length = len;
/* 255 */     return this;
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
/*     */   public ByteVector putByteArray(byte[] b, int off, int len) {
/* 270 */     if (this.length + len > this.data.length) {
/* 271 */       enlarge(len);
/*     */     }
/* 273 */     if (b != null) {
/* 274 */       System.arraycopy(b, off, this.data, this.length, len);
/*     */     }
/* 276 */     this.length += len;
/* 277 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enlarge(int size) {
/* 287 */     int length1 = 2 * this.data.length;
/* 288 */     int length2 = this.length + size;
/* 289 */     byte[] newData = new byte[(length1 > length2) ? length1 : length2];
/* 290 */     System.arraycopy(this.data, 0, newData, 0, this.length);
/* 291 */     this.data = newData;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\ByteVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */