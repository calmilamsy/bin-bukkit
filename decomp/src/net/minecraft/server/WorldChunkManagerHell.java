/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class WorldChunkManagerHell
/*    */   extends WorldChunkManager {
/*    */   private BiomeBase e;
/*    */   private double f;
/*    */   private double g;
/*    */   
/*    */   public WorldChunkManagerHell(BiomeBase paramBiomeBase, double paramDouble1, double paramDouble2) {
/* 12 */     this.e = paramBiomeBase;
/* 13 */     this.f = paramDouble1;
/* 14 */     this.g = paramDouble2;
/*    */   }
/*    */ 
/*    */   
/* 18 */   public BiomeBase a(ChunkCoordIntPair paramChunkCoordIntPair) { return this.e; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public BiomeBase getBiome(int paramInt1, int paramInt2) { return this.e; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeBase[] getBiomeData(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 30 */     this.d = a(this.d, paramInt1, paramInt2, paramInt3, paramInt4);
/* 31 */     return this.d;
/*    */   }
/*    */   
/*    */   public double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 35 */     if (paramArrayOfDouble == null || paramArrayOfDouble.length < paramInt3 * paramInt4) {
/* 36 */       paramArrayOfDouble = new double[paramInt3 * paramInt4];
/*    */     }
/*    */     
/* 39 */     Arrays.fill(paramArrayOfDouble, 0, paramInt3 * paramInt4, this.f);
/* 40 */     return paramArrayOfDouble;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 53 */     if (paramArrayOfBiomeBase == null || paramArrayOfBiomeBase.length < paramInt3 * paramInt4) {
/* 54 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*    */     }
/*    */     
/* 57 */     if (this.temperature == null || this.temperature.length < paramInt3 * paramInt4) {
/* 58 */       this.temperature = new double[paramInt3 * paramInt4];
/* 59 */       this.rain = new double[paramInt3 * paramInt4];
/*    */     } 
/*    */     
/* 62 */     Arrays.fill(paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.e);
/* 63 */     Arrays.fill(this.rain, 0, paramInt3 * paramInt4, this.g);
/* 64 */     Arrays.fill(this.temperature, 0, paramInt3 * paramInt4, this.f);
/*    */     
/* 66 */     return paramArrayOfBiomeBase;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldChunkManagerHell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */