/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockStairs
/*     */   extends Block
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   protected BlockStairs(int paramInt, Block paramBlock) {
/*  15 */     super(paramInt, paramBlock.textureId, paramBlock.material);
/*  16 */     this.a = paramBlock;
/*  17 */     c(paramBlock.strength);
/*  18 */     b(paramBlock.durability / 3.0F);
/*  19 */     a(paramBlock.stepSound);
/*  20 */     f(255);
/*     */   }
/*     */ 
/*     */   
/*  24 */   public void a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) { a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return super.e(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList) {
/*  49 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  50 */     if (i == 0) {
/*  51 */       a(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
/*  52 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  53 */       a(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  54 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  55 */     } else if (i == 1) {
/*  56 */       a(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
/*  57 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  58 */       a(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  59 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  60 */     } else if (i == 2) {
/*  61 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
/*  62 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  63 */       a(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
/*  64 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  65 */     } else if (i == 3) {
/*  66 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
/*  67 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*  68 */       a(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
/*  69 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
/*     */     } 
/*  71 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
/* 115 */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) { this.a.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman); }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void postBreak(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { this.a.postBreak(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public float a(Entity paramEntity) { return this.a.a(paramEntity); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public int a(int paramInt, Random paramRandom) { return this.a.a(paramInt, paramRandom); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public int a(Random paramRandom) { return this.a.a(paramRandom); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public int a(int paramInt1, int paramInt2) { return this.a.a(paramInt1, paramInt2); }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public int a(int paramInt) { return this.a.a(paramInt); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public int c() { return this.a.c(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D) { this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity, paramVec3D); }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public boolean k_() { return this.a.k_(); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public boolean a(int paramInt, boolean paramBoolean) { return this.a.a(paramInt, paramBoolean); }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return this.a.canPlace(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */   
/*     */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 179 */     doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, 0);
/* 180 */     this.a.c(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/* 184 */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { this.a.remove(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) { this.a.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat); }
/*     */ 
/*     */ 
/*     */   
/* 196 */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) { this.a.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity); }
/*     */ 
/*     */ 
/*     */   
/* 200 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) { this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom); }
/*     */ 
/*     */ 
/*     */   
/* 204 */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) { return this.a.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman); }
/*     */ 
/*     */ 
/*     */   
/* 208 */   public void d(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { this.a.d(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
/* 212 */     int i = MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/* 214 */     if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 2); 
/* 215 */     if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 1); 
/* 216 */     if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 3); 
/* 217 */     if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockStairs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */