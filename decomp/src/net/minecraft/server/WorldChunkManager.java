/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private NoiseGeneratorOctaves2 e;
/*     */   private NoiseGeneratorOctaves2 f;
/*     */   private NoiseGeneratorOctaves2 g;
/*     */   public double[] temperature;
/*     */   public double[] rain;
/*     */   public double[] c;
/*     */   public BiomeBase[] d;
/*     */   
/*     */   protected WorldChunkManager() {}
/*     */   
/*     */   public WorldChunkManager(World world) {
/*  18 */     this.e = new NoiseGeneratorOctaves2(new Random(world.getSeed() * 9871L), 4);
/*  19 */     this.f = new NoiseGeneratorOctaves2(new Random(world.getSeed() * 39811L), 4);
/*  20 */     this.g = new NoiseGeneratorOctaves2(new Random(world.getSeed() * 543321L), 2);
/*     */   }
/*     */ 
/*     */   
/*  24 */   public BiomeBase a(ChunkCoordIntPair chunkcoordintpair) { return getBiome(chunkcoordintpair.x << 4, chunkcoordintpair.z << 4); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public BiomeBase getBiome(int i, int j) { return getBiomeData(i, j, 1, 1)[0]; }
/*     */ 
/*     */   
/*     */   public BiomeBase[] getBiomeData(int i, int j, int k, int l) {
/*  32 */     this.d = a(this.d, i, j, k, l);
/*  33 */     return this.d;
/*     */   }
/*     */   
/*     */   public double[] a(double[] adouble, int i, int j, int k, int l) {
/*  37 */     if (adouble == null || adouble.length < k * l) {
/*  38 */       adouble = new double[k * l];
/*     */     }
/*     */     
/*  41 */     adouble = this.e.a(adouble, i, j, k, l, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
/*  42 */     this.c = this.g.a(this.c, i, j, k, l, 0.25D, 0.25D, 0.5882352941176471D);
/*  43 */     int i1 = 0;
/*     */     
/*  45 */     for (int j1 = 0; j1 < k; j1++) {
/*  46 */       for (int k1 = 0; k1 < l; k1++) {
/*  47 */         double d0 = this.c[i1] * 1.1D + 0.5D;
/*  48 */         double d1 = 0.01D;
/*  49 */         double d2 = 1.0D - d1;
/*  50 */         double d3 = (adouble[i1] * 0.15D + 0.7D) * d2 + d0 * d1;
/*     */         
/*  52 */         d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
/*  53 */         if (d3 < 0.0D) {
/*  54 */           d3 = 0.0D;
/*     */         }
/*     */         
/*  57 */         if (d3 > 1.0D) {
/*  58 */           d3 = 1.0D;
/*     */         }
/*     */         
/*  61 */         adouble[i1] = d3;
/*  62 */         i1++;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return adouble;
/*     */   }
/*     */   
/*     */   public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
/*  70 */     if (abiomebase == null || abiomebase.length < k * l) {
/*  71 */       abiomebase = new BiomeBase[k * l];
/*     */     }
/*     */     
/*  74 */     this.temperature = this.e.a(this.temperature, i, j, k, k, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
/*  75 */     this.rain = this.f.a(this.rain, i, j, k, k, 0.05000000074505806D, 0.05000000074505806D, 0.3333333333333333D);
/*  76 */     this.c = this.g.a(this.c, i, j, k, k, 0.25D, 0.25D, 0.5882352941176471D);
/*  77 */     int i1 = 0;
/*     */     
/*  79 */     for (int j1 = 0; j1 < k; j1++) {
/*  80 */       for (int k1 = 0; k1 < l; k1++) {
/*  81 */         double d0 = this.c[i1] * 1.1D + 0.5D;
/*  82 */         double d1 = 0.01D;
/*  83 */         double d2 = 1.0D - d1;
/*  84 */         double d3 = (this.temperature[i1] * 0.15D + 0.7D) * d2 + d0 * d1;
/*     */         
/*  86 */         d1 = 0.002D;
/*  87 */         d2 = 1.0D - d1;
/*  88 */         double d4 = (this.rain[i1] * 0.15D + 0.5D) * d2 + d0 * d1;
/*     */         
/*  90 */         d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
/*  91 */         if (d3 < 0.0D) {
/*  92 */           d3 = 0.0D;
/*     */         }
/*     */         
/*  95 */         if (d4 < 0.0D) {
/*  96 */           d4 = 0.0D;
/*     */         }
/*     */         
/*  99 */         if (d3 > 1.0D) {
/* 100 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 103 */         if (d4 > 1.0D) {
/* 104 */           d4 = 1.0D;
/*     */         }
/*     */         
/* 107 */         this.temperature[i1] = d3;
/* 108 */         this.rain[i1] = d4;
/* 109 */         abiomebase[i1++] = BiomeBase.a(d3, d4);
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return abiomebase;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public double getHumidity(int x, int z) { return this.f.a(this.rain, x, z, 1, 1, 0.05000000074505806D, 0.05000000074505806D, 0.3333333333333333D)[0]; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldChunkManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */