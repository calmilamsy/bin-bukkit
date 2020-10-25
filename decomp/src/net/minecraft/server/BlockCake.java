/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockCake
/*     */   extends Block
/*     */ {
/*     */   protected BlockCake(int paramInt1, int paramInt2) {
/*  13 */     super(paramInt1, paramInt2, Material.CAKE);
/*  14 */     a(true);
/*     */   }
/*     */   
/*     */   public void a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  18 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/*  19 */     float f1 = 0.0625F;
/*  20 */     float f2 = (1 + i * 2) / 16.0F;
/*  21 */     float f3 = 0.5F;
/*  22 */     a(f2, 0.0F, f1, 1.0F - f1, f3, 1.0F - f1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  32 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  33 */     float f1 = 0.0625F;
/*  34 */     float f2 = (1 + i * 2) / 16.0F;
/*  35 */     float f3 = 0.5F;
/*  36 */     return AxisAlignedBB.b((paramInt1 + f2), paramInt2, (paramInt3 + f1), ((paramInt1 + 1) - f1), (paramInt2 + f3 - f1), ((paramInt3 + 1) - f1));
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
/*     */   public int a(int paramInt1, int paramInt2) {
/*  48 */     if (paramInt1 == 1) return this.textureId; 
/*  49 */     if (paramInt1 == 0) return this.textureId + 3; 
/*  50 */     if (paramInt2 > 0 && paramInt1 == 4) return this.textureId + 2; 
/*  51 */     return this.textureId + 1;
/*     */   }
/*     */   
/*     */   public int a(int paramInt) {
/*  55 */     if (paramInt == 1) return this.textureId; 
/*  56 */     if (paramInt == 0) return this.textureId + 3; 
/*  57 */     return this.textureId + 1;
/*     */   }
/*     */ 
/*     */   
/*  61 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public boolean a() { return false; }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/*  69 */     c(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) { c(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman); }
/*     */ 
/*     */   
/*     */   private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/*  78 */     if (paramEntityHuman.health < 20) {
/*  79 */       paramEntityHuman.b(3);
/*  80 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3) + 1;
/*  81 */       if (i >= 6) {
/*  82 */         paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */       } else {
/*  84 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*  85 */         paramWorld.i(paramInt1, paramInt2, paramInt3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  91 */     if (!super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) return false;
/*     */     
/*  93 */     return f(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  97 */     if (!f(paramWorld, paramInt1, paramInt2, paramInt3)) {
/*  98 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/*  99 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 104 */   public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3).isBuildable(); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public int a(Random paramRandom) { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public int a(int paramInt, Random paramRandom) { return 0; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockCake.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */