/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import net.minecraft.server.BiomeBase;
/*     */ import org.bukkit.ChunkSnapshot;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.craftbukkit.block.CraftBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftChunkSnapshot
/*     */   implements ChunkSnapshot
/*     */ {
/*     */   private final int x;
/*     */   private final int z;
/*     */   private final String worldname;
/*     */   private final byte[] buf;
/*     */   private final byte[] hmap;
/*     */   private final long captureFulltime;
/*     */   private final BiomeBase[] biome;
/*     */   private final double[] biomeTemp;
/*     */   private final double[] biomeRain;
/*     */   private static final int BLOCKDATA_OFF = 32768;
/*     */   private static final int BLOCKLIGHT_OFF = 49152;
/*     */   private static final int SKYLIGHT_OFF = 65536;
/*     */   
/*     */   CraftChunkSnapshot(int x, int z, String wname, long wtime, byte[] buf, byte[] hmap, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) {
/*  30 */     this.x = x;
/*  31 */     this.z = z;
/*  32 */     this.worldname = wname;
/*  33 */     this.captureFulltime = wtime;
/*  34 */     this.buf = buf;
/*  35 */     this.hmap = hmap;
/*  36 */     this.biome = biome;
/*  37 */     this.biomeTemp = biomeTemp;
/*  38 */     this.biomeRain = biomeRain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public int getX() { return this.x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public int getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public String getWorldName() { return this.worldname; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public int getBlockTypeId(int x, int y, int z) { return this.buf[x << 11 | z << 7 | y] & 0xFF; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockData(int x, int y, int z) {
/*  89 */     int off = (x << 10 | z << 6 | y >> 1) + 32768;
/*     */     
/*  91 */     return ((y & true) == 0) ? (this.buf[off] & 0xF) : (this.buf[off] >> 4 & 0xF);
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
/*     */   public int getBlockSkyLight(int x, int y, int z) {
/* 103 */     int off = (x << 10 | z << 6 | y >> 1) + 65536;
/*     */     
/* 105 */     return ((y & true) == 0) ? (this.buf[off] & 0xF) : (this.buf[off] >> 4 & 0xF);
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
/*     */   public int getBlockEmittedLight(int x, int y, int z) {
/* 117 */     int off = (x << 10 | z << 6 | y >> 1) + 49152;
/*     */     
/* 119 */     return ((y & true) == 0) ? (this.buf[off] & 0xF) : (this.buf[off] >> 4 & 0xF);
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
/* 130 */   public int getHighestBlockYAt(int x, int z) { return this.hmap[z << 4 | x] & 0xFF; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public Biome getBiome(int x, int z) { return CraftBlock.biomeBaseToBiome(this.biome[x << 4 | z]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public double getRawBiomeTemperature(int x, int z) { return this.biomeTemp[x << 4 | z]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public double getRawBiomeRainfall(int x, int z) { return this.biomeRain[x << 4 | z]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public long getCaptureFullTime() { return this.captureFulltime; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\CraftChunkSnapshot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */