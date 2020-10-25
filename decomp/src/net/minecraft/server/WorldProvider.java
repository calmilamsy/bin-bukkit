/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WorldProvider
/*     */ {
/*     */   public World a;
/*     */   public WorldChunkManager b;
/*     */   public boolean c = false;
/*     */   public boolean d = false;
/*     */   public boolean e = false;
/*  21 */   public float[] f = new float[16];
/*  22 */   public int dimension = 0;
/*     */   
/*     */   public final void a(World paramWorld) {
/*  25 */     this.a = paramWorld;
/*  26 */     a();
/*  27 */     c();
/*     */   }
/*     */   
/*     */   protected void c() {
/*  31 */     float f1 = 0.05F;
/*  32 */     for (byte b1 = 0; b1 <= 15; b1++) {
/*  33 */       float f2 = 1.0F - b1 / 15.0F;
/*  34 */       this.f[b1] = (1.0F - f2) / (f2 * 3.0F + 1.0F) * (1.0F - f1) + f1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  39 */   protected void a() { this.b = new WorldChunkManager(this.a); }
/*     */ 
/*     */ 
/*     */   
/*  43 */   public IChunkProvider getChunkProvider() { return new ChunkProviderGenerate(this.a, this.a.getSeed()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawn(int paramInt1, int paramInt2) {
/*  51 */     int i = this.a.a(paramInt1, paramInt2);
/*     */     
/*  53 */     if (i != Block.SAND.id) return false;
/*     */     
/*  55 */     return true;
/*     */   }
/*     */   
/*     */   public float a(long paramLong, float paramFloat) {
/*  59 */     int i = (int)(paramLong % 24000L);
/*  60 */     null = (i + paramFloat) / 24000.0F - 0.25F;
/*  61 */     if (null < 0.0F) null++; 
/*  62 */     if (null > 1.0F) null--; 
/*  63 */     float f1 = null;
/*  64 */     null = 1.0F - (float)((Math.cos(null * Math.PI) + 1.0D) / 2.0D);
/*  65 */     return f1 + (null - f1) / 3.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private float[] h = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public boolean d() { return true; }
/*     */ 
/*     */   
/*     */   public static WorldProvider byDimension(int paramInt) {
/* 112 */     if (paramInt == -1) return new WorldProviderHell(); 
/* 113 */     if (paramInt == 0) return new WorldProviderNormal(); 
/* 114 */     if (paramInt == 1) return new WorldProviderSky(); 
/* 115 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */