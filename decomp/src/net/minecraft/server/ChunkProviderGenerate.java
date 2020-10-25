/*     */ package net.minecraft.server;public class ChunkProviderGenerate implements IChunkProvider { private Random j;
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
/*     */   public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase, double[] paramArrayOfDouble) {
/*     */     int i1 = 4;
/*     */     byte b1 = 64;
/*     */     byte b2 = i1 + 1;
/*     */     byte b3 = 17;
/*     */     byte b4 = i1 + 1;
/*     */     this.q = a(this.q, paramInt1 * i1, 0, paramInt2 * i1, b2, b3, b4);
/*     */     for (byte b5 = 0; b5 < i1; b5++) {
/*     */       for (byte b6 = 0; b6 < i1; b6++) {
/*     */         for (byte b7 = 0; b7 < 16; b7++) {
/*     */           double d1 = 0.125D;
/*     */           double d2 = this.q[((b5 + false) * b4 + b6 + false) * b3 + b7 + false];
/*     */           double d3 = this.q[((b5 + false) * b4 + b6 + true) * b3 + b7 + false];
/*     */           double d4 = this.q[((b5 + true) * b4 + b6 + false) * b3 + b7 + false];
/*     */           double d5 = this.q[((b5 + true) * b4 + b6 + true) * b3 + b7 + false];
/*     */           double d6 = (this.q[((b5 + false) * b4 + b6 + false) * b3 + b7 + true] - d2) * d1;
/*     */           double d7 = (this.q[((b5 + false) * b4 + b6 + true) * b3 + b7 + true] - d3) * d1;
/*     */           double d8 = (this.q[((b5 + true) * b4 + b6 + false) * b3 + b7 + true] - d4) * d1;
/*     */           double d9 = (this.q[((b5 + true) * b4 + b6 + true) * b3 + b7 + true] - d5) * d1;
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
/*     */                 double d18 = paramArrayOfDouble[(b5 * 4 + b9) * 16 + b6 * 4 + b10];
/*     */                 int i2 = 0;
/*     */                 if (b7 * 8 + b8 < b1)
/*     */                   if (d18 < 0.5D && b7 * 8 + b8 >= b1 - 1) {
/*     */                     i2 = Block.ICE.id;
/*     */                   } else {
/*     */                     i2 = Block.STATIONARY_WATER.id;
/*     */                   }  
/*     */                 if (d16 > 0.0D)
/*     */                   i2 = Block.STONE.id; 
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
/*     */   public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase) {
/*     */     byte b1 = 64;
/*     */     double d1 = 0.03125D;
/*     */     this.r = this.n.a(this.r, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1, d1, 1.0D);
/*     */     this.s = this.n.a(this.s, (paramInt1 * 16), 109.0134D, (paramInt2 * 16), 16, 1, 16, d1, 1.0D, d1);
/*     */     this.t = this.o.a(this.t, (paramInt1 * 16), (paramInt2 * 16), 0.0D, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D);
/*     */     for (byte b2 = 0; b2 < 16; b2++) {
/*     */       for (byte b3 = 0; b3 < 16; b3++) {
/*     */         BiomeBase biomeBase = paramArrayOfBiomeBase[b2 + b3 * 16];
/*     */         boolean bool1 = (this.r[b2 + b3 * 16] + this.j.nextDouble() * 0.2D > 0.0D) ? 1 : 0;
/*     */         boolean bool2 = (this.s[b2 + b3 * 16] + this.j.nextDouble() * 0.2D > 3.0D) ? 1 : 0;
/*     */         int i1 = (int)(this.t[b2 + b3 * 16] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
/*     */         int i2 = -1;
/*     */         byte b4 = biomeBase.p;
/*     */         byte b5 = biomeBase.q;
/*     */         for (char c1 = ''; c1 >= Character.MIN_VALUE; c1--) {
/*     */           char c2 = (b3 * 16 + b2) * '' + c1;
/*     */           if (c1 <= 0 + this.j.nextInt(5)) {
/*     */             paramArrayOfByte[c2] = (byte)Block.BEDROCK.id;
/*     */           } else {
/*     */             byte b6 = paramArrayOfByte[c2];
/*     */             if (b6 == 0) {
/*     */               i2 = -1;
/*     */             } else if (b6 == Block.STONE.id) {
/*     */               if (i2 == -1) {
/*     */                 if (i1 <= 0) {
/*     */                   b4 = 0;
/*     */                   b5 = (byte)Block.STONE.id;
/*     */                 } else if (c1 >= b1 - 4 && c1 <= b1 + 1) {
/*     */                   b4 = biomeBase.p;
/*     */                   b5 = biomeBase.q;
/*     */                   if (bool2)
/*     */                     b4 = 0; 
/*     */                   if (bool2)
/*     */                     b5 = (byte)Block.GRAVEL.id; 
/*     */                   if (bool1)
/*     */                     b4 = (byte)Block.SAND.id; 
/*     */                   if (bool1)
/*     */                     b5 = (byte)Block.SAND.id; 
/*     */                 } 
/*     */                 if (c1 < b1 && b4 == 0)
/*     */                   b4 = (byte)Block.STATIONARY_WATER.id; 
/*     */                 i2 = i1;
/*     */                 if (c1 >= b1 - 1) {
/*     */                   paramArrayOfByte[c2] = b4;
/*     */                 } else {
/*     */                   paramArrayOfByte[c2] = b5;
/*     */                 } 
/*     */               } else if (i2 > 0) {
/*     */                 i2--;
/*     */                 paramArrayOfByte[c2] = b5;
/*     */                 if (i2 == 0 && b5 == Block.SAND.id) {
/*     */                   i2 = this.j.nextInt(4);
/*     */                   b5 = (byte)Block.SANDSTONE.id;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChunkProviderGenerate(World paramWorld, long paramLong) {
/* 148 */     this.r = new double[256];
/* 149 */     this.s = new double[256];
/* 150 */     this.t = new double[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     this.u = new MapGenCaves();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     this.i = new int[32][32];
/*     */     this.p = paramWorld;
/*     */     this.j = new Random(paramLong);
/*     */     this.k = new NoiseGeneratorOctaves(this.j, 16);
/*     */     this.l = new NoiseGeneratorOctaves(this.j, 16);
/*     */     this.m = new NoiseGeneratorOctaves(this.j, 8);
/*     */     this.n = new NoiseGeneratorOctaves(this.j, 4);
/*     */     this.o = new NoiseGeneratorOctaves(this.j, 4);
/*     */     this.a = new NoiseGeneratorOctaves(this.j, 10);
/*     */     this.b = new NoiseGeneratorOctaves(this.j, 16);
/*     */     this.c = new NoiseGeneratorOctaves(this.j, 8);
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
/*     */   public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) {
/* 407 */     BlockSand.instaFall = true;
/* 408 */     int i1 = paramInt1 * 16;
/* 409 */     int i2 = paramInt2 * 16;
/*     */     
/* 411 */     BiomeBase biomeBase = this.p.getWorldChunkManager().getBiome(i1 + 16, i2 + 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     this.j.setSeed(this.p.getSeed());
/* 420 */     long l1 = this.j.nextLong() / 2L * 2L + 1L;
/* 421 */     long l2 = this.j.nextLong() / 2L * 2L + 1L;
/* 422 */     this.j.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.p.getSeed());
/* 423 */     double d1 = 0.25D;
/*     */     
/* 425 */     if (this.j.nextInt(4) == 0) {
/* 426 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 427 */       int i8 = this.j.nextInt(128);
/* 428 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 429 */       (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 432 */     if (this.j.nextInt(8) == 0) {
/* 433 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 434 */       int i8 = this.j.nextInt(this.j.nextInt(120) + 8);
/* 435 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 436 */       if (i8 < 64 || this.j.nextInt(10) == 0) (new WorldGenLakes(Block.STATIONARY_LAVA.id)).a(this.p, this.j, i7, i8, i9); 
/*     */     } 
/*     */     int i3;
/* 439 */     for (i3 = 0; i3 < 8; i3++) {
/* 440 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 441 */       int i8 = this.j.nextInt(128);
/* 442 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 443 */       (new WorldGenDungeons()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 446 */     for (i3 = 0; i3 < 10; i3++) {
/* 447 */       int i7 = i1 + this.j.nextInt(16);
/* 448 */       int i8 = this.j.nextInt(128);
/* 449 */       int i9 = i2 + this.j.nextInt(16);
/* 450 */       (new WorldGenClay(32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 453 */     for (i3 = 0; i3 < 20; i3++) {
/* 454 */       int i7 = i1 + this.j.nextInt(16);
/* 455 */       int i8 = this.j.nextInt(128);
/* 456 */       int i9 = i2 + this.j.nextInt(16);
/* 457 */       (new WorldGenMinable(Block.DIRT.id, 32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 460 */     for (i3 = 0; i3 < 10; i3++) {
/* 461 */       int i7 = i1 + this.j.nextInt(16);
/* 462 */       int i8 = this.j.nextInt(128);
/* 463 */       int i9 = i2 + this.j.nextInt(16);
/* 464 */       (new WorldGenMinable(Block.GRAVEL.id, 32)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 467 */     for (i3 = 0; i3 < 20; i3++) {
/* 468 */       int i7 = i1 + this.j.nextInt(16);
/* 469 */       int i8 = this.j.nextInt(128);
/* 470 */       int i9 = i2 + this.j.nextInt(16);
/* 471 */       (new WorldGenMinable(Block.COAL_ORE.id, 16)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 474 */     for (i3 = 0; i3 < 20; i3++) {
/* 475 */       int i7 = i1 + this.j.nextInt(16);
/* 476 */       int i8 = this.j.nextInt(64);
/* 477 */       int i9 = i2 + this.j.nextInt(16);
/* 478 */       (new WorldGenMinable(Block.IRON_ORE.id, 8)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 481 */     for (i3 = 0; i3 < 2; i3++) {
/* 482 */       int i7 = i1 + this.j.nextInt(16);
/* 483 */       int i8 = this.j.nextInt(32);
/* 484 */       int i9 = i2 + this.j.nextInt(16);
/* 485 */       (new WorldGenMinable(Block.GOLD_ORE.id, 8)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 488 */     for (i3 = 0; i3 < 8; i3++) {
/* 489 */       int i7 = i1 + this.j.nextInt(16);
/* 490 */       int i8 = this.j.nextInt(16);
/* 491 */       int i9 = i2 + this.j.nextInt(16);
/* 492 */       (new WorldGenMinable(Block.REDSTONE_ORE.id, 7)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 495 */     for (i3 = 0; i3 < 1; i3++) {
/* 496 */       int i7 = i1 + this.j.nextInt(16);
/* 497 */       int i8 = this.j.nextInt(16);
/* 498 */       int i9 = i2 + this.j.nextInt(16);
/* 499 */       (new WorldGenMinable(Block.DIAMOND_ORE.id, 7)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */ 
/*     */     
/* 503 */     for (i3 = 0; i3 < 1; i3++) {
/* 504 */       int i7 = i1 + this.j.nextInt(16);
/* 505 */       int i8 = this.j.nextInt(16) + this.j.nextInt(16);
/* 506 */       int i9 = i2 + this.j.nextInt(16);
/* 507 */       (new WorldGenMinable(Block.LAPIS_ORE.id, 6)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 510 */     d1 = 0.5D;
/* 511 */     i3 = (int)((this.c.a(i1 * d1, i2 * d1) / 8.0D + this.j.nextDouble() * 4.0D + 4.0D) / 3.0D);
/*     */     
/* 513 */     int i4 = 0;
/* 514 */     if (this.j.nextInt(10) == 0) i4++;
/*     */     
/* 516 */     if (biomeBase == BiomeBase.FOREST) i4 += i3 + 5; 
/* 517 */     if (biomeBase == BiomeBase.RAINFOREST) i4 += i3 + 5; 
/* 518 */     if (biomeBase == BiomeBase.SEASONAL_FOREST) i4 += i3 + 2; 
/* 519 */     if (biomeBase == BiomeBase.TAIGA) i4 += i3 + 5;
/*     */     
/* 521 */     if (biomeBase == BiomeBase.DESERT) i4 -= 20; 
/* 522 */     if (biomeBase == BiomeBase.TUNDRA) i4 -= 20; 
/* 523 */     if (biomeBase == BiomeBase.PLAINS) i4 -= 20; 
/*     */     byte b1;
/* 525 */     for (b1 = 0; b1 < i4; b1++) {
/* 526 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 527 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 528 */       WorldGenerator worldGenerator = biomeBase.a(this.j);
/* 529 */       worldGenerator.a(1.0D, 1.0D, 1.0D);
/* 530 */       worldGenerator.a(this.p, this.j, i7, this.p.getHighestBlockYAt(i7, i8), i8);
/*     */     } 
/*     */     
/* 533 */     b1 = 0;
/* 534 */     if (biomeBase == BiomeBase.FOREST) b1 = 2; 
/* 535 */     if (biomeBase == BiomeBase.SEASONAL_FOREST) b1 = 4; 
/* 536 */     if (biomeBase == BiomeBase.TAIGA) b1 = 2; 
/* 537 */     if (biomeBase == BiomeBase.PLAINS) b1 = 3; 
/*     */     byte b2;
/* 539 */     for (b2 = 0; b2 < b1; b2++) {
/* 540 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 541 */       int i8 = this.j.nextInt(128);
/* 542 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 543 */       (new WorldGenFlowers(Block.YELLOW_FLOWER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 546 */     b2 = 0;
/*     */     
/* 548 */     if (biomeBase == BiomeBase.FOREST) b2 = 2; 
/* 549 */     if (biomeBase == BiomeBase.RAINFOREST) b2 = 10; 
/* 550 */     if (biomeBase == BiomeBase.SEASONAL_FOREST) b2 = 2; 
/* 551 */     if (biomeBase == BiomeBase.TAIGA) b2 = 1; 
/* 552 */     if (biomeBase == BiomeBase.PLAINS) b2 = 10; 
/*     */     int i5;
/* 554 */     for (i5 = 0; i5 < b2; i5++) {
/* 555 */       byte b3 = 1;
/*     */       
/* 557 */       if (biomeBase == BiomeBase.RAINFOREST && this.j.nextInt(3) != 0) b3 = 2;
/*     */       
/* 559 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 560 */       int i8 = this.j.nextInt(128);
/* 561 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 562 */       (new WorldGenGrass(Block.LONG_GRASS.id, b3)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 565 */     b2 = 0;
/* 566 */     if (biomeBase == BiomeBase.DESERT) b2 = 2;
/*     */     
/* 568 */     for (i5 = 0; i5 < b2; i5++) {
/* 569 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 570 */       int i8 = this.j.nextInt(128);
/* 571 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 572 */       (new WorldGenDeadBush(Block.DEAD_BUSH.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 575 */     if (this.j.nextInt(2) == 0) {
/* 576 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 577 */       int i7 = this.j.nextInt(128);
/* 578 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 579 */       (new WorldGenFlowers(Block.RED_ROSE.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 582 */     if (this.j.nextInt(4) == 0) {
/* 583 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 584 */       int i7 = this.j.nextInt(128);
/* 585 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 586 */       (new WorldGenFlowers(Block.BROWN_MUSHROOM.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 589 */     if (this.j.nextInt(8) == 0) {
/* 590 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 591 */       int i7 = this.j.nextInt(128);
/* 592 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 593 */       (new WorldGenFlowers(Block.RED_MUSHROOM.id)).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 596 */     for (i5 = 0; i5 < 10; i5++) {
/* 597 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 598 */       int i8 = this.j.nextInt(128);
/* 599 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 600 */       (new WorldGenReed()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 603 */     if (this.j.nextInt(32) == 0) {
/* 604 */       i5 = i1 + this.j.nextInt(16) + 8;
/* 605 */       int i7 = this.j.nextInt(128);
/* 606 */       int i8 = i2 + this.j.nextInt(16) + 8;
/* 607 */       (new WorldGenPumpkin()).a(this.p, this.j, i5, i7, i8);
/*     */     } 
/*     */     
/* 610 */     i5 = 0;
/* 611 */     if (biomeBase == BiomeBase.DESERT) i5 += 10; 
/*     */     int i6;
/* 613 */     for (i6 = 0; i6 < i5; i6++) {
/* 614 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 615 */       int i8 = this.j.nextInt(128);
/* 616 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 617 */       (new WorldGenCactus()).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */ 
/*     */     
/* 621 */     for (i6 = 0; i6 < 50; i6++) {
/* 622 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 623 */       int i8 = this.j.nextInt(this.j.nextInt(120) + 8);
/* 624 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 625 */       (new WorldGenLiquids(Block.WATER.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 628 */     for (i6 = 0; i6 < 20; i6++) {
/* 629 */       int i7 = i1 + this.j.nextInt(16) + 8;
/* 630 */       int i8 = this.j.nextInt(this.j.nextInt(this.j.nextInt(112) + 8) + 8);
/* 631 */       int i9 = i2 + this.j.nextInt(16) + 8;
/* 632 */       (new WorldGenLiquids(Block.LAVA.id)).a(this.p, this.j, i7, i8, i9);
/*     */     } 
/*     */     
/* 635 */     this.w = this.p.getWorldChunkManager().a(this.w, i1 + 8, i2 + 8, 16, 16);
/* 636 */     for (i6 = i1 + 8; i6 < i1 + 8 + 16; i6++) {
/* 637 */       for (int i7 = i2 + 8; i7 < i2 + 8 + 16; i7++) {
/* 638 */         int i8 = i6 - i1 + 8;
/* 639 */         int i9 = i7 - i2 + 8;
/* 640 */         int i10 = this.p.e(i6, i7);
/* 641 */         double d2 = this.w[i8 * 16 + i9] - (i10 - 64) / 64.0D * 0.3D;
/* 642 */         if (d2 < 0.5D && 
/* 643 */           i10 > 0 && i10 < 128 && this.p.isEmpty(i6, i10, i7) && this.p.getMaterial(i6, i10 - 1, i7).isSolid() && 
/* 644 */           this.p.getMaterial(i6, i10 - true, i7) != Material.ICE) this.p.setTypeId(i6, i10, i7, Block.SNOW.id);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 649 */     BlockSand.instaFall = false; } public Chunk getOrCreateChunk(int paramInt1, int paramInt2) { this.j.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L); byte[] arrayOfByte = new byte[32768]; Chunk chunk = new Chunk(this.p, arrayOfByte, paramInt1, paramInt2); this.v = this.p.getWorldChunkManager().a(this.v, paramInt1 * 16, paramInt2 * 16, 16, 16); double[] arrayOfDouble = (this.p.getWorldChunkManager()).temperature; a(paramInt1, paramInt2, arrayOfByte, this.v, arrayOfDouble); a(paramInt1, paramInt2, arrayOfByte, this.v);
/*     */     this.u.a(this, this.p, paramInt1, paramInt2, arrayOfByte);
/*     */     chunk.initLighting();
/*     */     return chunk; }
/* 653 */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) { return true; }
/*     */ 
/*     */ 
/*     */   
/* 657 */   public boolean unloadChunks() { return false; }
/*     */   private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (paramArrayOfDouble == null) paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];  double d1 = 684.412D; double d2 = 684.412D; double[] arrayOfDouble1 = (this.p.getWorldChunkManager()).temperature; double[] arrayOfDouble2 = (this.p.getWorldChunkManager()).rain; this.g = this.a.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D); this.h = this.b.a(this.h, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D); this.d = this.m.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D); this.e = this.k.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); this.f = this.l.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1); byte b1 = 0; byte b2 = 0; int i1 = 16 / paramInt4; for (int i2 = 0; i2 < paramInt4; i2++) { int i3 = i2 * i1 + i1 / 2; for (int i4 = 0; i4 < paramInt6; i4++) { int i5 = i4 * i1 + i1 / 2; double d3 = arrayOfDouble1[i3 * 16 + i5]; double d4 = arrayOfDouble2[i3 * 16 + i5] * d3; double d5 = 1.0D - d4; d5 *= d5; d5 *= d5; d5 = 1.0D - d5; double d6 = (this.g[b2] + 256.0D) / 512.0D; d6 *= d5; if (d6 > 1.0D) d6 = 1.0D;  double d7 = this.h[b2] / 8000.0D; if (d7 < 0.0D) d7 = -d7 * 0.3D;  d7 = d7 * 3.0D - 2.0D; if (d7 < 0.0D) { d7 /= 2.0D; if (d7 < -1.0D) d7 = -1.0D;  d7 /= 1.4D; d7 /= 2.0D; d6 = 0.0D; } else { if (d7 > 1.0D)
/*     */             d7 = 1.0D;  d7 /= 8.0D; }  if (d6 < 0.0D)
/*     */           d6 = 0.0D;  d6 += 0.5D; d7 = d7 * paramInt5 / 16.0D; double d8 = paramInt5 / 2.0D + d7 * 4.0D; b2++; for (int i6 = 0; i6 < paramInt5; i6++) { double d9 = 0.0D; double d10 = (i6 - d8) * 12.0D / d6; if (d10 < 0.0D)
/* 661 */             d10 *= 4.0D;  double d11 = this.e[b1] / 512.0D; double d12 = this.f[b1] / 512.0D; double d13 = (this.d[b1] / 10.0D + 1.0D) / 2.0D; if (d13 < 0.0D) { d9 = d11; } else if (d13 > 1.0D) { d9 = d12; } else { d9 = d11 + (d12 - d11) * d13; }  d9 -= d10; if (i6 > paramInt5 - 4) { double d14 = ((i6 - paramInt5 - 4) / 3.0F); d9 = d9 * (1.0D - d14) + -10.0D * d14; }  paramArrayOfDouble[b1] = d9; b1++; }  }  }  return paramArrayOfDouble; } public boolean isChunkLoaded(int paramInt1, int paramInt2) { return true; } public boolean canSave() { return true; } }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkProviderGenerate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */