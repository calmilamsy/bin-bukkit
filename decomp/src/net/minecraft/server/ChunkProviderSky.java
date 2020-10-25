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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderSky
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGeneratorOctaves l;
/*     */   private NoiseGeneratorOctaves m;
/*     */   private NoiseGeneratorOctaves n;
/*     */   private NoiseGeneratorOctaves o;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   public NoiseGeneratorOctaves c;
/*     */   private World p;
/*     */   private double[] q;
/*     */   private double[] r;
/*     */   private double[] s;
/*     */   private double[] t;
/*     */   private MapGenBase u;
/*     */   private BiomeBase[] v;
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   double[] h;
/*     */   int[][] i;
/*     */   private double[] w;
/*     */   
/*     */   public ChunkProviderSky(World paramWorld, long paramLong) {
/* 136 */     this.r = new double[256];
/* 137 */     this.s = new double[256];
/* 138 */     this.t = new double[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     this.u = new MapGenCaves();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     this.i = new int[32][32]; this.p = paramWorld; this.j = new Random(paramLong); this.k = new NoiseGeneratorOctaves(this.j, 16); this.l = new NoiseGeneratorOctaves(this.j, 16); this.m = new NoiseGeneratorOctaves(this.j, 8); this.n = new NoiseGeneratorOctaves(this.j, 4); this.o = new NoiseGeneratorOctaves(this.j, 4); this.a = new NoiseGeneratorOctaves(this.j, 10); this.b = new NoiseGeneratorOctaves(this.j, 16); this.c = new NoiseGeneratorOctaves(this.j, 8);
/*     */   } public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase, double[] paramArrayOfDouble) { int i1 = 2; byte b1 = i1 + 1; byte b2 = 33; byte b3 = i1 + 1; this.q = a(this.q, paramInt1 * i1, 0, paramInt2 * i1, b1, b2, b3); for (byte b4 = 0; b4 < i1; b4++) {
/*     */       for (byte b5 = 0; b5 < i1; b5++) {
/*     */         for (byte b6 = 0; b6 < 32; b6++) {
/*     */           double d1 = 0.25D; double d2 = this.q[((b4 + false) * b3 + b5 + false) * b2 + b6 + false]; double d3 = this.q[((b4 + false) * b3 + b5 + true) * b2 + b6 + false]; double d4 = this.q[((b4 + true) * b3 + b5 + false) * b2 + b6 + false]; double d5 = this.q[((b4 + true) * b3 + b5 + true) * b2 + b6 + false]; double d6 = (this.q[((b4 + false) * b3 + b5 + false) * b2 + b6 + true] - d2) * d1; double d7 = (this.q[((b4 + false) * b3 + b5 + true) * b2 + b6 + true] - d3) * d1; double d8 = (this.q[((b4 + true) * b3 + b5 + false) * b2 + b6 + true] - d4) * d1; double d9 = (this.q[((b4 + true) * b3 + b5 + true) * b2 + b6 + true] - d5) * d1; for (byte b7 = 0; b7 < 4; b7++) {
/*     */             double d10 = 0.125D; double d11 = d2; double d12 = d3; double d13 = (d4 - d2) * d10; double d14 = (d5 - d3) * d10; for (byte b8 = 0; b8 < 8; b8++) {
/*     */               char c1 = b8 + b4 * 8 << 11 | 0 + b5 * 8 << 7 | b6 * 4 + b7; char c2 = ''; double d15 = 0.125D; double d16 = d11; double d17 = (d12 - d11) * d15; for (byte b9 = 0; b9 < 8; b9++) {
/*     */                 int i2 = 0; if (d16 > 0.0D)
/*     */                   i2 = Block.STONE.id;  paramArrayOfByte[c1] = (byte)i2; c1 += c2; d16 += d17;
/*     */               }  d11 += d13; d12 += d14;
/*     */             }  d2 += d6; d3 += d7; d4 += d8; d5 += d9;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase) { double d1 = 0.03125D; this.r = this.n.a(this.r, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1, d1, 1.0D); this.s = this.n.a(this.s, (paramInt1 * 16), 109.0134D, (paramInt2 * 16), 16, 1, 16, d1, 1.0D, d1); this.t = this.o.a(this.t, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D); for (byte b1 = 0; b1 < 16; b1++) {
/*     */       for (byte b2 = 0; b2 < 16; b2++) {
/*     */         BiomeBase biomeBase = paramArrayOfBiomeBase[b1 + b2 * 16];
/*     */         int i1 = (int)(this.t[b1 + b2 * 16] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
/*     */         int i2 = -1;
/*     */         byte b3 = biomeBase.p;
/*     */         byte b4 = biomeBase.q;
/*     */         for (char c1 = ''; c1 >= Character.MIN_VALUE; c1--) {
/*     */           char c2 = (b2 * 16 + b1) * '' + c1;
/*     */           byte b5 = paramArrayOfByte[c2];
/*     */           if (b5 == 0) {
/*     */             i2 = -1;
/*     */           } else if (b5 == Block.STONE.id) {
/*     */             if (i2 == -1) {
/*     */               if (i1 <= 0) {
/*     */                 b3 = 0;
/*     */                 b4 = (byte)Block.STONE.id;
/*     */               } 
/*     */               i2 = i1;
/*     */               if (c1 >= '\000') {
/*     */                 paramArrayOfByte[c2] = b3;
/*     */               } else {
/*     */                 paramArrayOfByte[c2] = b4;
/*     */               } 
/*     */             } else if (i2 > 0) {
/*     */               i2--;
/*     */               paramArrayOfByte[c2] = b4;
/*     */               if (i2 == 0 && b4 == Block.SAND.id) {
/*     */                 i2 = this.j.nextInt(4);
/*     */                 b4 = (byte)Block.SANDSTONE.id;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
/* 392 */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) { BlockSand.instaFall = true;
/* 393 */     int i1 = paramInt1 * 16;
/* 394 */     int i2 = paramInt2 * 16;
/*     */     
/* 396 */     BiomeBase biomeBase = this.p.getWorldChunkManager().getBiome(i1 + 16, i2 + 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 404 */     this.j.setSeed(this.p.getSeed());
/* 405 */     long l1 = this.j.nextLong() / 2L * 2L + 1L;
/* 406 */     long l2 = this.j.nextLong() / 2L * 2L + 1L;
/* 407 */     this.j.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.p.getSeed());
/* 408 */     double d1 = 0.25D;
/*     */     
/* 410 */     if (this.j.nextInt(4) == 0) {
/* 411 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 412 */       int i8 = this.j.nextInt(128);
/* 413 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 414 */       (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 417 */     if (this.j.nextInt(8) == 0) {
/* 418 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 419 */       int i8 = this.j.nextInt(this.j.nextInt(120) + 8);
/* 420 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 421 */       if (i8 < 64 || this.j.nextInt(10) == 0) (new WorldGenLakes(Block.STATIONARY_LAVA.id)).a(this.p, this.j, i7, i8, i9); 
/*     */     } 
/*     */     int i3;
/* 424 */     for (i3 = 0; i3 < 8; i3++) {
/* 425 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 426 */       int i8 = this.j.nextInt(128);
/* 427 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 428 */       (new WorldGenDungeons()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 431 */     for (i3 = 0; i3 < 10; i3++) {
/* 432 */       int i7 = i1 + this.j.nextInt(16);
/* 433 */       int i8 = this.j.nextInt(128);
/* 434 */       int i9 = i2 + this.j.nextInt(16);
/* 435 */       (new WorldGenClay(32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 438 */     for (i3 = 0; i3 < 20; i3++) {
/* 439 */       int i7 = i1 + this.j.nextInt(16);
/* 440 */       int i8 = this.j.nextInt(128);
/* 441 */       int i9 = i2 + this.j.nextInt(16);
/* 442 */       (new WorldGenMinable(Block.DIRT.id, 32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 445 */     for (i3 = 0; i3 < 10; i3++) {
/* 446 */       int i7 = i1 + this.j.nextInt(16);
/* 447 */       int i8 = this.j.nextInt(128);
/* 448 */       int i9 = i2 + this.j.nextInt(16);
/* 449 */       (new WorldGenMinable(Block.GRAVEL.id, 32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 452 */     for (i3 = 0; i3 < 20; i3++) {
/* 453 */       int i7 = i1 + this.j.nextInt(16);
/* 454 */       int i8 = this.j.nextInt(128);
/* 455 */       int i9 = i2 + this.j.nextInt(16);
/* 456 */       (new WorldGenMinable(Block.COAL_ORE.id, 16)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 459 */     for (i3 = 0; i3 < 20; i3++) {
/* 460 */       int i7 = i1 + this.j.nextInt(16);
/* 461 */       int i8 = this.j.nextInt(64);
/* 462 */       int i9 = i2 + this.j.nextInt(16);
/* 463 */       (new WorldGenMinable(Block.IRON_ORE.id, 8)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 466 */     for (i3 = 0; i3 < 2; i3++) {
/* 467 */       int i7 = i1 + this.j.nextInt(16);
/* 468 */       int i8 = this.j.nextInt(32);
/* 469 */       int i9 = i2 + this.j.nextInt(16);
/* 470 */       (new WorldGenMinable(Block.GOLD_ORE.id, 8)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 473 */     for (i3 = 0; i3 < 8; i3++) {
/* 474 */       int i7 = i1 + this.j.nextInt(16);
/* 475 */       int i8 = this.j.nextInt(16);
/* 476 */       int i9 = i2 + this.j.nextInt(16);
/* 477 */       (new WorldGenMinable(Block.REDSTONE_ORE.id, 7)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 480 */     for (i3 = 0; i3 < 1; i3++) {
/* 481 */       int i7 = i1 + this.j.nextInt(16);
/* 482 */       int i8 = this.j.nextInt(16);
/* 483 */       int i9 = i2 + this.j.nextInt(16);
/* 484 */       (new WorldGenMinable(Block.DIAMOND_ORE.id, 7)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */ 
/*     */     
/* 488 */     for (i3 = 0; i3 < 1; i3++) {
/* 489 */       int i7 = i1 + this.j.nextInt(16);
/* 490 */       int i8 = this.j.nextInt(16) + this.j.nextInt(16);
/* 491 */       int i9 = i2 + this.j.nextInt(16);
/* 492 */       (new WorldGenMinable(Block.LAPIS_ORE.id, 6)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 495 */     d1 = 0.5D;
/* 496 */     i3 = (int)((this.c.a(i1 * d1, i2 * d1) / 8.0D + this.j.nextDouble() * 4.0D + 4.0D) / 3.0D);
/*     */     
/* 498 */     int i4 = 0;
/* 499 */     if (this.j.nextInt(10) == 0) i4++;
/*     */     
/* 501 */     if (biomeBase == BiomeBase.FOREST) i4 += i3 + 5; 
/* 502 */     if (biomeBase == BiomeBase.RAINFOREST) i4 += i3 + 5; 
/* 503 */     if (biomeBase == BiomeBase.SEASONAL_FOREST) i4 += i3 + 2; 
/* 504 */     if (biomeBase == BiomeBase.TAIGA) i4 += i3 + 5;
/*     */     
/* 506 */     if (biomeBase == BiomeBase.DESERT) i4 -= 20; 
/* 507 */     if (biomeBase == BiomeBase.TUNDRA) i4 -= 20; 
/* 508 */     if (biomeBase == BiomeBase.PLAINS) i4 -= 20; 
/*     */     int i5;
/* 510 */     for (i5 = 0; i5 < i4; i5++) {
/* 511 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 512 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 513 */       WorldGenerator worldGenerator = biomeBase.a(this.j);
/* 514 */       worldGenerator.a(1.0D, 1.0D, 1.0D);
/* 515 */       worldGenerator.a(this.p, this.j, i7, this.p.getHighestBlockYAt(i7, i8), i8);
/*     */     } 
/*     */     
/* 518 */     for (i5 = 0; i5 < 2; i5++) {
/* 519 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 520 */       int i8 = this.j.nextInt(128);
/* 521 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 522 */       (new WorldGenFlowers(Block.YELLOW_FLOWER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 525 */     if (this.j.nextInt(2) == 0) {
/* 526 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 527 */       int i7 = this.j.nextInt(128);
/* 528 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 529 */       (new WorldGenFlowers(Block.RED_ROSE.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 532 */     if (this.j.nextInt(4) == 0) {
/* 533 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 534 */       int i7 = this.j.nextInt(128);
/* 535 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 536 */       (new WorldGenFlowers(Block.BROWN_MUSHROOM.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 539 */     if (this.j.nextInt(8) == 0) {
/* 540 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 541 */       int i7 = this.j.nextInt(128);
/* 542 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 543 */       (new WorldGenFlowers(Block.RED_MUSHROOM.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 546 */     for (i5 = 0; i5 < 10; i5++) {
/* 547 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 548 */       int i8 = this.j.nextInt(128);
/* 549 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 550 */       (new WorldGenReed()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 553 */     if (this.j.nextInt(32) == 0) {
/* 554 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 555 */       int i7 = this.j.nextInt(128);
/* 556 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 557 */       (new WorldGenPumpkin()).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 560 */     i5 = 0;
/* 561 */     if (biomeBase == BiomeBase.DESERT) i5 += 10; 
/*     */     int i6;
/* 563 */     for (i6 = 0; i6 < i5; i6++) {
/* 564 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 565 */       int i8 = this.j.nextInt(128);
/* 566 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 567 */       (new WorldGenCactus()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */ 
/*     */     
/* 571 */     for (i6 = 0; i6 < 50; i6++) {
/* 572 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 573 */       int i8 = this.j.nextInt(this.j.nextInt(120) + 8);
/* 574 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 575 */       (new WorldGenLiquids(Block.WATER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 578 */     for (i6 = 0; i6 < 20; i6++) {
/* 579 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 580 */       int i8 = this.j.nextInt(this.j.nextInt(this.j.nextInt(112) + 8) + 8);
/* 581 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 582 */       (new WorldGenLiquids(Block.LAVA.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 585 */     this.w = this.p.getWorldChunkManager().a(this.w, i1 + 8, i2 + 8, 16, 16);
/* 586 */     for (i6 = i1 + 8; i6 < i1 + 8 + 16; i6++) {
/* 587 */       for (int i7 = i2 + 8; i7 < i2 + 8 + 16; i7++) {
/* 588 */         int i8 = i6 - i1 + 8;
/* 589 */         int i9 = i7 - i2 + 8;
/* 590 */         int i10 = this.p.e(i6, i7);
/* 591 */         double d2 = this.w[i8 * 16 + i9] - (i10 - 64) / 64.0D * 0.3D;
/* 592 */         if (d2 < 0.5D && 
/* 593 */           i10 > 0 && i10 < 128 && this.p.isEmpty(i6, i10, i7) && this.p.getMaterial(i6, i10 - 1, i7).isSolid() && 
/* 594 */           this.p.getMaterial(i6, i10 - true, i7) != Material.ICE) this.p.setTypeId(i6, i10, i7, Block.SNOW.id);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 599 */     BlockSand.instaFall = false; } public Chunk getOrCreateChunk(int paramInt1, int paramInt2) { this.j.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L); byte[] arrayOfByte = new byte[32768]; Chunk chunk = new Chunk(this.p, arrayOfByte, paramInt1, paramInt2); this.v = this.p.getWorldChunkManager().a(this.v, paramInt1 * 16, paramInt2 * 16, 16, 16); double[] arrayOfDouble = (this.p.getWorldChunkManager()).temperature; a(paramInt1, paramInt2, arrayOfByte, this.v, arrayOfDouble); a(paramInt1, paramInt2, arrayOfByte, this.v);
/*     */     this.u.a(this, this.p, paramInt1, paramInt2, arrayOfByte);
/*     */     chunk.initLighting();
/*     */     return chunk; }
/* 603 */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) { return true; }
/*     */ 
/*     */ 
/*     */   
/* 607 */   public boolean unloadChunks() { return false; }
/*     */   private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (paramArrayOfDouble == null) paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];  double d1 = 684.412D; double d2 = 684.412D; double[] arrayOfDouble1 = (this.p.getWorldChunkManager()).temperature; double[] arrayOfDouble2 = (this.p.getWorldChunkManager()).rain; this.g = this.a.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D); this.h = this.b.a(this.h, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D); d1 *= 2.0D; this.d = this.m.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D); this.e = this.k.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); this.f = this.l.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); byte b1 = 0; byte b2 = 0; int i1 = 16 / paramInt4; for (int i2 = 0; i2 < paramInt4; i2++) { int i3 = i2 * i1 + i1 / 2; for (int i4 = 0; i4 < paramInt6; i4++) { int i5 = i4 * i1 + i1 / 2; double d3 = arrayOfDouble1[i3 * 16 + i5]; double d4 = arrayOfDouble2[i3 * 16 + i5] * d3; double d5 = 1.0D - d4; d5 *= d5; d5 *= d5; d5 = 1.0D - d5; double d6 = (this.g[b2] + 256.0D) / 512.0D; d6 *= d5; if (d6 > 1.0D) d6 = 1.0D;  double d7 = this.h[b2] / 8000.0D; if (d7 < 0.0D) d7 = -d7 * 0.3D;  d7 = d7 * 3.0D - 2.0D; if (d7 > 1.0D)
/*     */           d7 = 1.0D;  d7 /= 8.0D; d7 = 0.0D; if (d6 < 0.0D)
/*     */           d6 = 0.0D;  d6 += 0.5D; d7 = d7 * paramInt5 / 16.0D; b2++; double d8 = paramInt5 / 2.0D; for (int i6 = 0; i6 < paramInt5; i6++) { double d9 = 0.0D; double d10 = (i6 - d8) * 8.0D / d6; if (d10 < 0.0D)
/* 611 */             d10 *= -1.0D;  double d11 = this.e[b1] / 512.0D; double d12 = this.f[b1] / 512.0D; double d13 = (this.d[b1] / 10.0D + 1.0D) / 2.0D; if (d13 < 0.0D) { d9 = d11; } else if (d13 > 1.0D) { d9 = d12; } else { d9 = d11 + (d12 - d11) * d13; }  d9 -= 8.0D; int i7 = 32; if (i6 > paramInt5 - i7) { double d14 = ((i6 - paramInt5 - i7) / (i7 - 1.0F)); d9 = d9 * (1.0D - d14) + -30.0D * d14; }  i7 = 8; if (i6 < i7) { double d14 = ((i7 - i6) / (i7 - 1.0F)); d9 = d9 * (1.0D - d14) + -30.0D * d14; }  paramArrayOfDouble[b1] = d9; b1++; }  }  }  return paramArrayOfDouble; } public boolean isChunkLoaded(int paramInt1, int paramInt2) { return true; } public boolean canSave() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkProviderSky.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */