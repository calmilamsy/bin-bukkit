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
/*     */ public class BlockBed
/*     */   extends Block
/*     */ {
/*  20 */   public static final int[][] a = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBed(int paramInt) {
/*  34 */     super(paramInt, 134, Material.CLOTH);
/*     */     
/*  36 */     o();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/*  41 */     if (paramWorld.isStatic) return true;
/*     */     
/*  43 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  45 */     if (!d(i)) {
/*     */       
/*  47 */       int j = c(i);
/*  48 */       paramInt1 += a[j][0];
/*  49 */       paramInt3 += a[j][1];
/*  50 */       if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != this.id) {
/*  51 */         return true;
/*     */       }
/*  53 */       i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     } 
/*     */     
/*  56 */     if (!paramWorld.worldProvider.d()) {
/*  57 */       double d1 = paramInt1 + 0.5D;
/*  58 */       double d2 = paramInt2 + 0.5D;
/*  59 */       double d3 = paramInt3 + 0.5D;
/*  60 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*  61 */       int j = c(i);
/*  62 */       paramInt1 += a[j][0];
/*  63 */       paramInt3 += a[j][1];
/*  64 */       if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) == this.id) {
/*  65 */         paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*  66 */         d1 = (d1 + paramInt1 + 0.5D) / 2.0D;
/*  67 */         d2 = (d2 + paramInt2 + 0.5D) / 2.0D;
/*  68 */         d3 = (d3 + paramInt3 + 0.5D) / 2.0D;
/*     */       } 
/*  70 */       paramWorld.createExplosion(null, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), 5.0F, true);
/*  71 */       return true;
/*     */     } 
/*     */     
/*  74 */     if (e(i)) {
/*  75 */       EntityHuman entityHuman = null;
/*  76 */       for (EntityHuman entityHuman1 : paramWorld.players) {
/*  77 */         if (entityHuman1.isSleeping()) {
/*  78 */           ChunkCoordinates chunkCoordinates = entityHuman1.A;
/*  79 */           if (chunkCoordinates.x == paramInt1 && chunkCoordinates.y == paramInt2 && chunkCoordinates.z == paramInt3) {
/*  80 */             entityHuman = entityHuman1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       if (entityHuman == null) {
/*  86 */         a(paramWorld, paramInt1, paramInt2, paramInt3, false);
/*     */       } else {
/*  88 */         paramEntityHuman.a("tile.bed.occupied");
/*  89 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     EnumBedError enumBedError = paramEntityHuman.a(paramInt1, paramInt2, paramInt3);
/*  94 */     if (enumBedError == EnumBedError.OK) {
/*  95 */       a(paramWorld, paramInt1, paramInt2, paramInt3, true);
/*  96 */       return true;
/*     */     } 
/*     */     
/*  99 */     if (enumBedError == EnumBedError.NOT_POSSIBLE_NOW) {
/* 100 */       paramEntityHuman.a("tile.bed.noSleep");
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(int paramInt1, int paramInt2) {
/* 108 */     if (paramInt1 == 0) {
/* 109 */       return Block.WOOD.textureId;
/*     */     }
/*     */     
/* 112 */     int i = c(paramInt2);
/* 113 */     int j = BedBlockTextures.c[i][paramInt1];
/*     */     
/* 115 */     if (d(paramInt2)) {
/*     */       
/* 117 */       if (j == 2) {
/* 118 */         return this.textureId + 2 + 16;
/*     */       }
/* 120 */       if (j == 5 || j == 4) {
/* 121 */         return this.textureId + 1 + 16;
/*     */       }
/* 123 */       return this.textureId + 1;
/*     */     } 
/* 125 */     if (j == 3) {
/* 126 */       return this.textureId - 1 + 16;
/*     */     }
/* 128 */     if (j == 5 || j == 4) {
/* 129 */       return this.textureId + 16;
/*     */     }
/* 131 */     return this.textureId;
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
/* 142 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public void a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) { o(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 157 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 158 */     int j = c(i);
/*     */     
/* 160 */     if (d(i)) {
/* 161 */       if (paramWorld.getTypeId(paramInt1 - a[j][0], paramInt2, paramInt3 - a[j][1]) != this.id) {
/* 162 */         paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */       }
/*     */     }
/* 165 */     else if (paramWorld.getTypeId(paramInt1 + a[j][0], paramInt2, paramInt3 + a[j][1]) != this.id) {
/* 166 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/* 167 */       if (!paramWorld.isStatic) {
/* 168 */         g(paramWorld, paramInt1, paramInt2, paramInt3, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(int paramInt, Random paramRandom) {
/* 176 */     if (d(paramInt)) {
/* 177 */       return 0;
/*     */     }
/* 179 */     return Item.BED.id;
/*     */   }
/*     */ 
/*     */   
/* 183 */   private void o() { a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F); }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static int c(int paramInt) { return paramInt & 0x3; }
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static boolean d(int paramInt) { return ((paramInt & 0x8) != 0); }
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static boolean e(int paramInt) { return ((paramInt & 0x4) != 0); }
/*     */ 
/*     */   
/*     */   public static void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 199 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 200 */     if (paramBoolean) {
/* 201 */       i |= 0x4;
/*     */     } else {
/* 203 */       i &= 0xFFFFFFFB;
/*     */     } 
/* 205 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*     */   }
/*     */   
/*     */   public static ChunkCoordinates f(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 209 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 210 */     int j = c(i);
/*     */ 
/*     */     
/* 213 */     for (int k = 0; k <= 1; k++) {
/* 214 */       int m = paramInt1 - a[j][0] * k - 1;
/* 215 */       int n = paramInt3 - a[j][1] * k - 1;
/* 216 */       int i1 = m + 2;
/* 217 */       int i2 = n + 2;
/*     */       
/* 219 */       for (int i3 = m; i3 <= i1; i3++) {
/* 220 */         for (int i4 = n; i4 <= i2; i4++) {
/* 221 */           if (paramWorld.e(i3, paramInt2 - 1, i4) && paramWorld.isEmpty(i3, paramInt2, i4) && paramWorld.isEmpty(i3, paramInt2 + 1, i4)) {
/* 222 */             if (paramInt4 > 0) {
/* 223 */               paramInt4--;
/*     */             } else {
/*     */               
/* 226 */               return new ChunkCoordinates(i3, paramInt2, i4);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) {
/* 238 */     if (!d(paramInt4)) {
/* 239 */       super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 245 */   public int e() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockBed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */