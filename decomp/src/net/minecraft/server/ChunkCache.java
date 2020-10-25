/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCache
/*     */   implements IBlockAccess
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private Chunk[][] c;
/*     */   private World d;
/*     */   
/*     */   public ChunkCache(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  17 */     this.d = paramWorld;
/*     */     
/*  19 */     this.a = paramInt1 >> 4;
/*  20 */     this.b = paramInt3 >> 4;
/*  21 */     int i = paramInt4 >> 4;
/*  22 */     int j = paramInt6 >> 4;
/*     */     
/*  24 */     this.c = new Chunk[i - this.a + 1][j - this.b + 1];
/*     */     
/*  26 */     for (int k = this.a; k <= i; k++) {
/*  27 */       for (int m = this.b; m <= j; m++) {
/*  28 */         this.c[k - this.a][m - this.b] = paramWorld.getChunkAt(k, m);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTypeId(int paramInt1, int paramInt2, int paramInt3) {
/*  34 */     if (paramInt2 < 0) return 0; 
/*  35 */     if (paramInt2 >= 128) return 0;
/*     */     
/*  37 */     int i = (paramInt1 >> 4) - this.a;
/*  38 */     int j = (paramInt3 >> 4) - this.b;
/*     */     
/*  40 */     if (i < 0 || i >= this.c.length || j < 0 || j >= this.c[i].length) {
/*  41 */       return 0;
/*     */     }
/*     */     
/*  44 */     Chunk chunk = this.c[i][j];
/*  45 */     if (chunk == null) return 0;
/*     */     
/*  47 */     return chunk.getTypeId(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
/*     */   }
/*     */   
/*     */   public TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3) {
/*  51 */     int i = (paramInt1 >> 4) - this.a;
/*  52 */     int j = (paramInt3 >> 4) - this.b;
/*     */     
/*  54 */     return this.c[i][j].d(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData(int paramInt1, int paramInt2, int paramInt3) {
/* 106 */     if (paramInt2 < 0) return 0; 
/* 107 */     if (paramInt2 >= 128) return 0; 
/* 108 */     int i = (paramInt1 >> 4) - this.a;
/* 109 */     int j = (paramInt3 >> 4) - this.b;
/*     */     
/* 111 */     return this.c[i][j].getData(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
/*     */   }
/*     */   
/*     */   public Material getMaterial(int paramInt1, int paramInt2, int paramInt3) {
/* 115 */     int i = getTypeId(paramInt1, paramInt2, paramInt3);
/* 116 */     if (i == 0) return Material.AIR; 
/* 117 */     return (Block.byId[i]).material;
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
/*     */   public boolean e(int paramInt1, int paramInt2, int paramInt3) {
/* 131 */     Block block = Block.byId[getTypeId(paramInt1, paramInt2, paramInt3)];
/* 132 */     if (block == null) return false; 
/* 133 */     return (block.material.isSolid() && block.b());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */