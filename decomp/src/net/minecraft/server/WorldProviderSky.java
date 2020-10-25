/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderSky
/*    */   extends WorldProvider
/*    */ {
/*    */   public void a() {
/* 14 */     this.b = new WorldChunkManagerHell(BiomeBase.SKY, 0.5D, 0.0D);
/* 15 */     this.dimension = 1;
/*    */   }
/*    */ 
/*    */   
/* 19 */   public IChunkProvider getChunkProvider() { return new ChunkProviderSky(this.a, this.a.getSeed()); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public float a(long paramLong, float paramFloat) { return 0.0F; }
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
/*    */   
/*    */   public boolean canSpawn(int paramInt1, int paramInt2) {
/* 56 */     int i = this.a.a(paramInt1, paramInt2);
/*    */     
/* 58 */     if (i == 0) return false;
/*    */     
/* 60 */     return (Block.byId[i]).material.isSolid();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldProviderSky.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */