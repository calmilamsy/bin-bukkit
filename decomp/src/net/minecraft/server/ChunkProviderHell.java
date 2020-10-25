/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderHell
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random h;
/*     */   private NoiseGeneratorOctaves i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGeneratorOctaves l;
/*     */   private NoiseGeneratorOctaves m;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   private World n;
/*     */   private double[] o;
/*     */   private double[] p;
/*     */   private double[] q;
/*     */   private double[] r;
/*     */   private MapGenBase s;
/*     */   double[] c;
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
/*     */     int i1 = 4;
/*     */     byte b1 = 32;
/*     */     byte b2 = i1 + 1;
/*     */     byte b3 = 17;
/*     */     byte b4 = i1 + 1;
/*     */     this.o = a(this.o, paramInt1 * i1, 0, paramInt2 * i1, b2, b3, b4);
/*     */     for (byte b5 = 0; b5 < i1; b5++) {
/*     */       for (byte b6 = 0; b6 < i1; b6++) {
/*     */         for (byte b7 = 0; b7 < 16; b7++) {
/*     */           double d1 = 0.125D;
/*     */           double d2 = this.o[((b5 + false) * b4 + b6 + false) * b3 + b7 + false];
/*     */           double d3 = this.o[((b5 + false) * b4 + b6 + true) * b3 + b7 + false];
/*     */           double d4 = this.o[((b5 + true) * b4 + b6 + false) * b3 + b7 + false];
/*     */           double d5 = this.o[((b5 + true) * b4 + b6 + true) * b3 + b7 + false];
/*     */           double d6 = (this.o[((b5 + false) * b4 + b6 + false) * b3 + b7 + true] - d2) * d1;
/*     */           double d7 = (this.o[((b5 + false) * b4 + b6 + true) * b3 + b7 + true] - d3) * d1;
/*     */           double d8 = (this.o[((b5 + true) * b4 + b6 + false) * b3 + b7 + true] - d4) * d1;
/*     */           double d9 = (this.o[((b5 + true) * b4 + b6 + true) * b3 + b7 + true] - d5) * d1;
/*     */           for (byte b8 = 0; b8 < 8; b8++) {
/*     */             double d10 = 0.25D;
/*     */             double d11 = d2;
/*     */             double d12 = d3;
/*     */             double d13 = (d4 - d2) * d10;
/*     */             double d14 = (d5 - d3) * d10;
/*     */             for (byte b9 = 0; b9 < 4; b9++) {
/*     */               char c1 = b9 + b5 * 4 << 11 | 0 + b6 * 4 << 7 | b7 * 8 + b8;
/*     */               char c2 = '';
/*     */               double d15 = 0.25D;
/*     */               double d16 = d11;
/*     */               double d17 = (d12 - d11) * d15;
/*     */               for (byte b10 = 0; b10 < 4; b10++) {
/*     */                 int i2 = 0;
/*     */                 if (b7 * 8 + b8 < b1)
/*     */                   i2 = Block.STATIONARY_LAVA.id; 
/*     */                 if (d16 > 0.0D)
/*     */                   i2 = Block.NETHERRACK.id; 
/*     */                 paramArrayOfByte[c1] = (byte)i2;
/*     */                 c1 += c2;
/*     */                 d16 += d17;
/*     */               } 
/*     */               d11 += d13;
/*     */               d12 += d14;
/*     */             } 
/*     */             d2 += d6;
/*     */             d3 += d7;
/*     */             d4 += d8;
/*     */             d5 += d9;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChunkProviderHell(World paramWorld, long paramLong) {
/* 117 */     this.p = new double[256];
/* 118 */     this.q = new double[256];
/* 119 */     this.r = new double[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.s = new MapGenCavesHell(); this.n = paramWorld; this.h = new Random(paramLong); this.i = new NoiseGeneratorOctaves(this.h, 16); this.j = new NoiseGeneratorOctaves(this.h, 16); this.k = new NoiseGeneratorOctaves(this.h, 8); this.l = new NoiseGeneratorOctaves(this.h, 4);
/*     */     this.m = new NoiseGeneratorOctaves(this.h, 4);
/*     */     this.a = new NoiseGeneratorOctaves(this.h, 10);
/* 185 */     this.b = new NoiseGeneratorOctaves(this.h, 16); } public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
/*     */   
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/* 189 */     this.h.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
/*     */     
/* 191 */     byte[] arrayOfByte = new byte[32768];
/*     */     
/* 193 */     a(paramInt1, paramInt2, arrayOfByte);
/* 194 */     b(paramInt1, paramInt2, arrayOfByte);
/*     */     
/* 196 */     this.s.a(this, this.n, paramInt1, paramInt2, arrayOfByte);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     return new Chunk(this.n, arrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 209 */     if (paramArrayOfDouble == null) {
/* 210 */       paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
/*     */     }
/*     */     
/* 213 */     double d1 = 684.412D;
/* 214 */     double d2 = 2053.236D;
/*     */ 
/*     */     
/* 217 */     this.f = this.a.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 1.0D, 0.0D, 1.0D);
/* 218 */     this.g = this.b.a(this.g, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 100.0D, 0.0D, 100.0D);
/*     */     
/* 220 */     this.c = this.k.a(this.c, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 60.0D, d1 / 80.0D);
/* 221 */     this.d = this.i.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/* 222 */     this.e = this.j.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/*     */     
/* 224 */     byte b1 = 0;
/* 225 */     byte b2 = 0;
/* 226 */     double[] arrayOfDouble = new double[paramInt5]; int i1;
/* 227 */     for (i1 = 0; i1 < paramInt5; i1++) {
/* 228 */       arrayOfDouble[i1] = Math.cos(i1 * Math.PI * 6.0D / paramInt5) * 2.0D;
/*     */       
/* 230 */       double d3 = i1;
/* 231 */       if (i1 > paramInt5 / 2) {
/* 232 */         d3 = (paramInt5 - 1 - i1);
/*     */       }
/* 234 */       if (d3 < 4.0D) {
/* 235 */         d3 = 4.0D - d3;
/* 236 */         arrayOfDouble[i1] = arrayOfDouble[i1] - d3 * d3 * d3 * 10.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     for (i1 = 0; i1 < paramInt4; i1++) {
/*     */       
/* 242 */       for (byte b3 = 0; b3 < paramInt6; b3++) {
/*     */         
/* 244 */         double d3 = (this.f[b2] + 256.0D) / 512.0D;
/* 245 */         if (d3 > 1.0D) d3 = 1.0D;
/*     */         
/* 247 */         double d4 = 0.0D;
/*     */         
/* 249 */         double d5 = this.g[b2] / 8000.0D;
/* 250 */         if (d5 < 0.0D) d5 = -d5;
/*     */ 
/*     */         
/* 253 */         d5 = d5 * 3.0D - 3.0D;
/*     */ 
/*     */         
/* 256 */         if (d5 < 0.0D) {
/* 257 */           d5 /= 2.0D;
/* 258 */           if (d5 < -1.0D) d5 = -1.0D; 
/* 259 */           d5 /= 1.4D;
/* 260 */           d5 /= 2.0D;
/* 261 */           d3 = 0.0D;
/*     */         } else {
/* 263 */           if (d5 > 1.0D) d5 = 1.0D; 
/* 264 */           d5 /= 6.0D;
/*     */         } 
/* 266 */         d3 += 0.5D;
/* 267 */         d5 = d5 * paramInt5 / 16.0D;
/* 268 */         b2++;
/*     */         
/* 270 */         for (int i2 = 0; i2 < paramInt5; i2++) {
/* 271 */           double d6 = 0.0D;
/*     */           
/* 273 */           double d7 = arrayOfDouble[i2];
/*     */           
/* 275 */           double d8 = this.d[b1] / 512.0D;
/* 276 */           double d9 = this.e[b1] / 512.0D;
/*     */           
/* 278 */           double d10 = (this.c[b1] / 10.0D + 1.0D) / 2.0D;
/* 279 */           if (d10 < 0.0D) { d6 = d8; }
/* 280 */           else if (d10 > 1.0D) { d6 = d9; }
/* 281 */           else { d6 = d8 + (d9 - d8) * d10; }
/* 282 */            d6 -= d7;
/*     */           
/* 284 */           if (i2 > paramInt5 - 4) {
/* 285 */             double d11 = ((i2 - paramInt5 - 4) / 3.0F);
/* 286 */             d6 = d6 * (1.0D - d11) + -10.0D * d11;
/*     */           } 
/*     */           
/* 289 */           if (i2 < d4) {
/* 290 */             double d11 = (d4 - i2) / 4.0D;
/* 291 */             if (d11 < 0.0D) d11 = 0.0D; 
/* 292 */             if (d11 > 1.0D) d11 = 1.0D; 
/* 293 */             d6 = d6 * (1.0D - d11) + -10.0D * d11;
/*     */           } 
/*     */           
/* 296 */           paramArrayOfDouble[b1] = d6;
/* 297 */           b1++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 301 */     return paramArrayOfDouble;
/*     */   } public void b(int paramInt1, int paramInt2, byte[] paramArrayOfByte) { byte b1 = 64; double d1 = 0.03125D; this.p = this.l.a(this.p, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1, d1, 1.0D); this.q = this.l.a(this.q, (paramInt1 * 16), 109.0134D, (paramInt2 * 16), 16, 1, 16, d1, 1.0D, d1); this.r = this.m.a(this.r, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D); for (byte b2 = 0; b2 < 16; b2++) { for (byte b3 = 0; b3 < 16; b3++) { boolean bool1 = (this.p[b2 + b3 * 16] + this.h.nextDouble() * 0.2D > 0.0D) ? 1 : 0; boolean bool2 = (this.q[b2 + b3 * 16] + this.h.nextDouble() * 0.2D > 0.0D) ? 1 : 0; int i1 = (int)(this.r[b2 + b3 * 16] / 3.0D + 3.0D + this.h.nextDouble() * 0.25D); int i2 = -1; byte b4 = (byte)Block.NETHERRACK.id; byte b5 = (byte)Block.NETHERRACK.id; for (char c1 = ''; c1 >= Character.MIN_VALUE; c1--) { char c2 = (b3 * 16 + b2) * '' + c1; if (c1 >= 127 - this.h.nextInt(5)) { paramArrayOfByte[c2] = (byte)Block.BEDROCK.id; } else if (c1 <= 0 + this.h.nextInt(5)) { paramArrayOfByte[c2] = (byte)Block.BEDROCK.id; } else { byte b6 = paramArrayOfByte[c2]; if (b6 == 0) { i2 = -1; } else if (b6 == Block.NETHERRACK.id) { if (i2 == -1) { if (i1 <= 0) { b4 = 0; b5 = (byte)Block.NETHERRACK.id; } else if (c1 >= b1 - 4 && c1 <= b1 + 1) { b4 = (byte)Block.NETHERRACK.id; b5 = (byte)Block.NETHERRACK.id; if (bool2) b4 = (byte)Block.GRAVEL.id;  if (bool2) b5 = (byte)Block.NETHERRACK.id;  if (bool1)
/*     */                     b4 = (byte)Block.SOUL_SAND.id;  if (bool1)
/*     */                     b5 = (byte)Block.SOUL_SAND.id;  }  if (c1 < b1 && b4 == 0)
/* 305 */                   b4 = (byte)Block.STATIONARY_LAVA.id;  i2 = i1; if (c1 >= b1 - 1) { paramArrayOfByte[c2] = b4; } else { paramArrayOfByte[c2] = b5; }  } else if (i2 > 0) { i2--; paramArrayOfByte[c2] = b5; }  }  }  }  }  }  } public boolean isChunkLoaded(int paramInt1, int paramInt2) { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) {
/* 310 */     BlockSand.instaFall = true;
/* 311 */     int i1 = paramInt1 * 16;
/* 312 */     int i2 = paramInt2 * 16;
/*     */     
/*     */     int i3;
/* 315 */     for (i3 = 0; i3 < 8; i3++) {
/* 316 */       int i5 = i1 + this.h.nextInt(16) + 8;
/* 317 */       int i6 = this.h.nextInt(120) + 4;
/* 318 */       int i7 = i2 + this.h.nextInt(16) + 8;
/* 319 */       (new WorldGenHellLava(Block.LAVA.id)).a(this.n, this.h, i5, i6, i7);
/*     */     } 
/*     */     
/* 322 */     i3 = this.h.nextInt(this.h.nextInt(10) + 1) + 1;
/*     */     int i4;
/* 324 */     for (i4 = 0; i4 < i3; i4++) {
/* 325 */       int i5 = i1 + this.h.nextInt(16) + 8;
/* 326 */       int i6 = this.h.nextInt(120) + 4;
/* 327 */       int i7 = i2 + this.h.nextInt(16) + 8;
/* 328 */       (new WorldGenFire()).a(this.n, this.h, i5, i6, i7);
/*     */     } 
/*     */     
/* 331 */     i3 = this.h.nextInt(this.h.nextInt(10) + 1);
/* 332 */     for (i4 = 0; i4 < i3; i4++) {
/* 333 */       int i5 = i1 + this.h.nextInt(16) + 8;
/* 334 */       int i6 = this.h.nextInt(120) + 4;
/* 335 */       int i7 = i2 + this.h.nextInt(16) + 8;
/* 336 */       (new WorldGenLightStone2()).a(this.n, this.h, i5, i6, i7);
/*     */     } 
/*     */     
/* 339 */     for (i4 = 0; i4 < 10; i4++) {
/* 340 */       int i5 = i1 + this.h.nextInt(16) + 8;
/* 341 */       int i6 = this.h.nextInt(128);
/* 342 */       int i7 = i2 + this.h.nextInt(16) + 8;
/* 343 */       (new WorldGenLightStone1()).a(this.n, this.h, i5, i6, i7);
/*     */     } 
/*     */     
/* 346 */     if (this.h.nextInt(1) == 0) {
/* 347 */       i4 = i1 + this.h.nextInt(16) + 8;
/* 348 */       int i5 = this.h.nextInt(128);
/* 349 */       int i6 = i2 + this.h.nextInt(16) + 8;
/* 350 */       (new WorldGenFlowers(Block.BROWN_MUSHROOM.id)).a(this.n, this.h, i4, i5, i6);
/*     */     } 
/*     */     
/* 353 */     if (this.h.nextInt(1) == 0) {
/* 354 */       i4 = i1 + this.h.nextInt(16) + 8;
/* 355 */       int i5 = this.h.nextInt(128);
/* 356 */       int i6 = i2 + this.h.nextInt(16) + 8;
/* 357 */       (new WorldGenFlowers(Block.RED_MUSHROOM.id)).a(this.n, this.h, i4, i5, i6);
/*     */     } 
/*     */     
/* 360 */     BlockSand.instaFall = false;
/*     */   }
/*     */ 
/*     */   
/* 364 */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) { return true; }
/*     */ 
/*     */ 
/*     */   
/* 368 */   public boolean unloadChunks() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 372 */   public boolean canSave() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkProviderHell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */