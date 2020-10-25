/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  74 */   private static final byte[] a = new byte[4096];
/*     */   
/*     */   private final File b;
/*     */   
/*     */   private RandomAccessFile c;
/*     */   private final int[] d;
/*     */   
/*     */   public RegionFile(File paramFile) {
/*  82 */     this.h = 0L;
/*     */ 
/*     */     
/*  85 */     this.d = new int[1024];
/*  86 */     this.e = new int[1024];
/*     */     
/*  88 */     this.b = paramFile;
/*  89 */     b("REGION LOAD " + this.b);
/*     */     
/*  91 */     this.g = 0;
/*     */     
/*     */     try {
/*  94 */       if (paramFile.exists()) {
/*  95 */         this.h = paramFile.lastModified();
/*     */       }
/*     */       
/*  98 */       this.c = new RandomAccessFile(paramFile, "rw");
/*     */       
/* 100 */       if (this.c.length() < 4096L) {
/*     */         byte b2;
/* 102 */         for (b2 = 0; b2 < 'Ѐ'; b2++) {
/* 103 */           this.c.writeInt(0);
/*     */         }
/*     */         
/* 106 */         for (b2 = 0; b2 < 'Ѐ'; b2++) {
/* 107 */           this.c.writeInt(0);
/*     */         }
/*     */         
/* 110 */         this.g += 8192;
/*     */       } 
/*     */       
/* 113 */       if ((this.c.length() & 0xFFFL) != 0L)
/*     */       {
/* 115 */         for (byte b2 = 0; b2 < (this.c.length() & 0xFFFL); b2++) {
/* 116 */           this.c.write(0);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 121 */       int i = (int)this.c.length() / 4096;
/* 122 */       this.f = new ArrayList(i);
/*     */       byte b1;
/* 124 */       for (b1 = 0; b1 < i; b1++) {
/* 125 */         this.f.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/* 128 */       this.f.set(0, Boolean.valueOf(false));
/* 129 */       this.f.set(1, Boolean.valueOf(false));
/*     */       
/* 131 */       this.c.seek(0L);
/* 132 */       for (b1 = 0; b1 < 'Ѐ'; b1++) {
/* 133 */         int j = this.c.readInt();
/* 134 */         this.d[b1] = j;
/* 135 */         if (j != 0 && (j >> 8) + (j & 0xFF) <= this.f.size()) {
/* 136 */           for (int k = 0; k < (j & 0xFF); k++) {
/* 137 */             this.f.set((j >> 8) + k, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       } 
/* 141 */       for (b1 = 0; b1 < 'Ѐ'; b1++) {
/* 142 */         int j = this.c.readInt();
/* 143 */         this.e[b1] = j;
/*     */       } 
/* 145 */     } catch (IOException iOException) {
/* 146 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final int[] e;
/*     */   private ArrayList f;
/*     */   private int g;
/*     */   private long h;
/*     */   
/*     */   public int a() {
/* 157 */     int i = this.g;
/* 158 */     this.g = 0;
/* 159 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(String paramString) {}
/*     */ 
/*     */ 
/*     */   
/* 168 */   private void b(String paramString) { a(paramString + "\n"); }
/*     */ 
/*     */ 
/*     */   
/* 172 */   private void a(String paramString1, int paramInt1, int paramInt2, String paramString2) { a("REGION " + paramString1 + " " + this.b.getName() + "[" + paramInt1 + "," + paramInt2 + "] = " + paramString2); }
/*     */ 
/*     */ 
/*     */   
/* 176 */   private void a(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2) { a("REGION " + paramString1 + " " + this.b.getName() + "[" + paramInt1 + "," + paramInt2 + "] " + paramInt3 + "B = " + paramString2); }
/*     */ 
/*     */ 
/*     */   
/* 180 */   private void b(String paramString1, int paramInt1, int paramInt2, String paramString2) { a(paramString1, paramInt1, paramInt2, paramString2 + "\n"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataInputStream a(int paramInt1, int paramInt2) {
/* 188 */     if (d(paramInt1, paramInt2)) {
/* 189 */       b("READ", paramInt1, paramInt2, "out of bounds");
/* 190 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 194 */       int i = e(paramInt1, paramInt2);
/* 195 */       if (i == 0)
/*     */       {
/* 197 */         return null;
/*     */       }
/*     */       
/* 200 */       int j = i >> 8;
/* 201 */       int k = i & 0xFF;
/*     */       
/* 203 */       if (j + k > this.f.size()) {
/* 204 */         b("READ", paramInt1, paramInt2, "invalid sector");
/* 205 */         return null;
/*     */       } 
/*     */       
/* 208 */       this.c.seek((j * 4096));
/* 209 */       int m = this.c.readInt();
/*     */       
/* 211 */       if (m > 4096 * k) {
/* 212 */         b("READ", paramInt1, paramInt2, "invalid length: " + m + " > 4096 * " + k);
/* 213 */         return null;
/*     */       } 
/*     */       
/* 216 */       byte b1 = this.c.readByte();
/* 217 */       if (b1 == 1) {
/* 218 */         byte[] arrayOfByte = new byte[m - 1];
/* 219 */         this.c.read(arrayOfByte);
/* 220 */         return new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(arrayOfByte)));
/*     */       } 
/*     */       
/* 223 */       if (b1 == 2) {
/* 224 */         byte[] arrayOfByte = new byte[m - 1];
/* 225 */         this.c.read(arrayOfByte);
/* 226 */         return new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(arrayOfByte)));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 231 */       b("READ", paramInt1, paramInt2, "unknown version " + b1);
/* 232 */       return null;
/* 233 */     } catch (IOException iOException) {
/* 234 */       b("READ", paramInt1, paramInt2, "exception");
/* 235 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataOutputStream b(int paramInt1, int paramInt2) {
/* 240 */     if (d(paramInt1, paramInt2)) return null;
/*     */     
/* 242 */     return new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(this, paramInt1, paramInt2)));
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
/*     */   protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3) {
/*     */     try {
/* 266 */       int i = e(paramInt1, paramInt2);
/* 267 */       int j = i >> 8;
/* 268 */       int k = i & 0xFF;
/* 269 */       int m = (paramInt3 + 5) / 4096 + 1;
/*     */ 
/*     */       
/* 272 */       if (m >= 256) {
/*     */         return;
/*     */       }
/*     */       
/* 276 */       if (j != 0 && k == m) {
/*     */         
/* 278 */         a("SAVE", paramInt1, paramInt2, paramInt3, "rewrite");
/* 279 */         a(j, paramArrayOfByte, paramInt3);
/*     */       } else {
/*     */         int n;
/*     */ 
/*     */         
/* 284 */         for (n = 0; n < k; n++) {
/* 285 */           this.f.set(j + n, Boolean.valueOf(true));
/*     */         }
/*     */ 
/*     */         
/* 289 */         n = this.f.indexOf(Boolean.valueOf(true));
/* 290 */         byte b1 = 0;
/* 291 */         if (n != -1) {
/* 292 */           for (int i1 = n; i1 < this.f.size(); i1++) {
/* 293 */             if (b1) {
/* 294 */               if (((Boolean)this.f.get(i1)).booleanValue()) { b1++; }
/* 295 */               else { b1 = 0; } 
/* 296 */             } else if (((Boolean)this.f.get(i1)).booleanValue()) {
/* 297 */               n = i1;
/* 298 */               b1 = 1;
/*     */             } 
/* 300 */             if (b1 >= m) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 306 */         if (b1 >= m) {
/*     */           
/* 308 */           a("SAVE", paramInt1, paramInt2, paramInt3, "reuse");
/* 309 */           j = n;
/* 310 */           a(paramInt1, paramInt2, j << 8 | m);
/* 311 */           for (int i1 = 0; i1 < m; i1++) {
/* 312 */             this.f.set(j + i1, Boolean.valueOf(false));
/*     */           }
/* 314 */           a(j, paramArrayOfByte, paramInt3);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 320 */           a("SAVE", paramInt1, paramInt2, paramInt3, "grow");
/* 321 */           this.c.seek(this.c.length());
/* 322 */           j = this.f.size();
/* 323 */           for (byte b2 = 0; b2 < m; b2++) {
/* 324 */             this.c.write(a);
/* 325 */             this.f.add(Boolean.valueOf(false));
/*     */           } 
/* 327 */           this.g += 4096 * m;
/*     */           
/* 329 */           a(j, paramArrayOfByte, paramInt3);
/* 330 */           a(paramInt1, paramInt2, j << 8 | m);
/*     */         } 
/*     */       } 
/* 333 */       b(paramInt1, paramInt2, (int)(System.currentTimeMillis() / 1000L));
/* 334 */     } catch (IOException iOException) {
/* 335 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
/* 341 */     b(" " + paramInt1);
/* 342 */     this.c.seek((paramInt1 * 4096));
/* 343 */     this.c.writeInt(paramInt2 + 1);
/* 344 */     this.c.writeByte(2);
/* 345 */     this.c.write(paramArrayOfByte, 0, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 350 */   private boolean d(int paramInt1, int paramInt2) { return (paramInt1 < 0 || paramInt1 >= 32 || paramInt2 < 0 || paramInt2 >= 32); }
/*     */ 
/*     */ 
/*     */   
/* 354 */   private int e(int paramInt1, int paramInt2) { return this.d[paramInt1 + paramInt2 * 32]; }
/*     */ 
/*     */ 
/*     */   
/* 358 */   public boolean c(int paramInt1, int paramInt2) { return (e(paramInt1, paramInt2) != 0); }
/*     */ 
/*     */   
/*     */   private void a(int paramInt1, int paramInt2, int paramInt3) {
/* 362 */     this.d[paramInt1 + paramInt2 * 32] = paramInt3;
/* 363 */     this.c.seek(((paramInt1 + paramInt2 * 32) * 4));
/* 364 */     this.c.writeInt(paramInt3);
/*     */   }
/*     */   
/*     */   private void b(int paramInt1, int paramInt2, int paramInt3) {
/* 368 */     this.e[paramInt1 + paramInt2 * 32] = paramInt3;
/* 369 */     this.c.seek((4096 + (paramInt1 + paramInt2 * 32) * 4));
/* 370 */     this.c.writeInt(paramInt3);
/*     */   }
/*     */ 
/*     */   
/* 374 */   public void b() { this.c.close(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\RegionFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */