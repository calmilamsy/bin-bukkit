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
/*     */ public class BlockPistonMoving
/*     */   extends BlockContainer
/*     */ {
/*     */   public BlockPistonMoving(int paramInt) {
/*  16 */     super(paramInt, Material.PISTON);
/*  17 */     c(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  22 */   protected TileEntity a_() { return null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  31 */     TileEntity tileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*  32 */     if (tileEntity != null && tileEntity instanceof TileEntityPiston) {
/*  33 */       ((TileEntityPiston)tileEntity).k();
/*     */     } else {
/*  35 */       super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/*  67 */     if (!paramWorld.isStatic && paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null) {
/*     */       
/*  69 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*  70 */       return true;
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public int a(int paramInt, Random paramRandom) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) {
/*  82 */     if (paramWorld.isStatic)
/*     */       return; 
/*  84 */     TileEntityPiston tileEntityPiston = b(paramWorld, paramInt1, paramInt2, paramInt3);
/*  85 */     if (tileEntityPiston == null) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     Block.byId[tileEntityPiston.a()].g(paramWorld, paramInt1, paramInt2, paramInt3, tileEntityPiston.e());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  94 */     if (paramWorld.isStatic || paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static TileEntity a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) { return new TileEntityPiston(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2); }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 106 */     TileEntityPiston tileEntityPiston = b(paramWorld, paramInt1, paramInt2, paramInt3);
/* 107 */     if (tileEntityPiston == null) {
/* 108 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 112 */     float f = tileEntityPiston.a(0.0F);
/* 113 */     if (tileEntityPiston.c()) {
/* 114 */       f = 1.0F - f;
/*     */     }
/* 116 */     return a(paramWorld, paramInt1, paramInt2, paramInt3, tileEntityPiston.a(), f, tileEntityPiston.d());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 121 */     TileEntityPiston tileEntityPiston = b(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
/* 122 */     if (tileEntityPiston != null) {
/* 123 */       Block block = Block.byId[tileEntityPiston.a()];
/* 124 */       if (block == null || block == this) {
/*     */         return;
/*     */       }
/* 127 */       block.a(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
/*     */       
/* 129 */       float f = tileEntityPiston.a(0.0F);
/* 130 */       if (tileEntityPiston.c()) {
/* 131 */         f = 1.0F - f;
/*     */       }
/* 133 */       int i = tileEntityPiston.d();
/* 134 */       this.minX = block.minX - (PistonBlockTextures.b[i] * f);
/* 135 */       this.minY = block.minY - (PistonBlockTextures.c[i] * f);
/* 136 */       this.minZ = block.minZ - (PistonBlockTextures.d[i] * f);
/* 137 */       this.maxX = block.maxX - (PistonBlockTextures.b[i] * f);
/* 138 */       this.maxY = block.maxY - (PistonBlockTextures.c[i] * f);
/* 139 */       this.maxZ = block.maxZ - (PistonBlockTextures.d[i] * f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
/* 145 */     if (paramInt4 == 0 || paramInt4 == this.id) {
/* 146 */       return null;
/*     */     }
/* 148 */     AxisAlignedBB axisAlignedBB = Block.byId[paramInt4].e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     
/* 150 */     if (axisAlignedBB == null) {
/* 151 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 155 */     axisAlignedBB.a -= (PistonBlockTextures.b[paramInt5] * paramFloat);
/* 156 */     axisAlignedBB.d -= (PistonBlockTextures.b[paramInt5] * paramFloat);
/* 157 */     axisAlignedBB.b -= (PistonBlockTextures.c[paramInt5] * paramFloat);
/* 158 */     axisAlignedBB.e -= (PistonBlockTextures.c[paramInt5] * paramFloat);
/* 159 */     axisAlignedBB.c -= (PistonBlockTextures.d[paramInt5] * paramFloat);
/* 160 */     axisAlignedBB.f -= (PistonBlockTextures.d[paramInt5] * paramFloat);
/* 161 */     return axisAlignedBB;
/*     */   }
/*     */   
/*     */   private TileEntityPiston b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 165 */     TileEntity tileEntity = paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 166 */     if (tileEntity != null && tileEntity instanceof TileEntityPiston) {
/* 167 */       return (TileEntityPiston)tileEntity;
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPistonMoving.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */