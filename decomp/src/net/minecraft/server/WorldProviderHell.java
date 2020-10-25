/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderHell
/*    */   extends WorldProvider
/*    */ {
/*    */   public void a() {
/* 13 */     this.b = new WorldChunkManagerHell(BiomeBase.HELL, 1.0D, 0.0D);
/* 14 */     this.c = true;
/* 15 */     this.d = true;
/* 16 */     this.e = true;
/* 17 */     this.dimension = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void c() {
/* 25 */     float f = 0.1F;
/* 26 */     for (byte b = 0; b <= 15; b++) {
/* 27 */       float f1 = 1.0F - b / 15.0F;
/* 28 */       this.f[b] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 33 */   public IChunkProvider getChunkProvider() { return new ChunkProviderHell(this.a, this.a.getSeed()); }
/*    */ 
/*    */   
/*    */   public boolean canSpawn(int paramInt1, int paramInt2) {
/* 37 */     int i = this.a.a(paramInt1, paramInt2);
/*    */     
/* 39 */     if (i == Block.BEDROCK.id) return false; 
/* 40 */     if (i == 0) return false; 
/* 41 */     if (!Block.o[i]) return false;
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/* 47 */   public float a(long paramLong, float paramFloat) { return 0.5F; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public boolean d() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldProviderHell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */