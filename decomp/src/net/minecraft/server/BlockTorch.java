/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockTorch
/*     */   extends Block
/*     */ {
/*     */   protected BlockTorch(int paramInt1, int paramInt2) {
/*  11 */     super(paramInt1, paramInt2, Material.ORIENTABLE);
/*  12 */     a(true);
/*     */   }
/*     */ 
/*     */   
/*  16 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  20 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private boolean g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return (paramWorld.e(paramInt1, paramInt2, paramInt3) || paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) == Block.FENCE.id); }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  36 */     if (paramWorld.e(paramInt1 - 1, paramInt2, paramInt3))
/*  37 */       return true; 
/*  38 */     if (paramWorld.e(paramInt1 + 1, paramInt2, paramInt3))
/*  39 */       return true; 
/*  40 */     if (paramWorld.e(paramInt1, paramInt2, paramInt3 - 1))
/*  41 */       return true; 
/*  42 */     if (paramWorld.e(paramInt1, paramInt2, paramInt3 + 1))
/*  43 */       return true; 
/*  44 */     if (g(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) {
/*  45 */       return true;
/*     */     }
/*     */     
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  52 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  54 */     if (paramInt4 == 1 && g(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) i = 5; 
/*  55 */     if (paramInt4 == 2 && paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) i = 4; 
/*  56 */     if (paramInt4 == 3 && paramWorld.e(paramInt1, paramInt2, paramInt3 - 1)) i = 3; 
/*  57 */     if (paramInt4 == 4 && paramWorld.e(paramInt1 + 1, paramInt2, paramInt3)) i = 2; 
/*  58 */     if (paramInt4 == 5 && paramWorld.e(paramInt1 - 1, paramInt2, paramInt3)) i = 1;
/*     */     
/*  60 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/*  64 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/*  65 */     if (paramWorld.getData(paramInt1, paramInt2, paramInt3) == 0) c(paramWorld, paramInt1, paramInt2, paramInt3); 
/*     */   }
/*     */   
/*     */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  69 */     if (paramWorld.e(paramInt1 - 1, paramInt2, paramInt3)) {
/*  70 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, 1);
/*  71 */     } else if (paramWorld.e(paramInt1 + 1, paramInt2, paramInt3)) {
/*  72 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, 2);
/*  73 */     } else if (paramWorld.e(paramInt1, paramInt2, paramInt3 - 1)) {
/*  74 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, 3);
/*  75 */     } else if (paramWorld.e(paramInt1, paramInt2, paramInt3 + 1)) {
/*  76 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, 4);
/*  77 */     } else if (g(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) {
/*  78 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, 5);
/*     */     } 
/*  80 */     h(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  84 */     if (h(paramWorld, paramInt1, paramInt2, paramInt3)) {
/*  85 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  86 */       boolean bool = false;
/*     */       
/*  88 */       if (!paramWorld.e(paramInt1 - 1, paramInt2, paramInt3) && i == 1) bool = true; 
/*  89 */       if (!paramWorld.e(paramInt1 + 1, paramInt2, paramInt3) && i == 2) bool = true; 
/*  90 */       if (!paramWorld.e(paramInt1, paramInt2, paramInt3 - 1) && i == 3) bool = true; 
/*  91 */       if (!paramWorld.e(paramInt1, paramInt2, paramInt3 + 1) && i == 4) bool = true; 
/*  92 */       if (!g(paramWorld, paramInt1, paramInt2 - 1, paramInt3) && i == 5) bool = true;
/*     */       
/*  94 */       if (bool) {
/*  95 */         g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/*  96 */         paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean h(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 102 */     if (!canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 103 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 104 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/* 105 */       return false;
/*     */     } 
/* 107 */     return true;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Vec3D paramVec3D1, Vec3D paramVec3D2) {
/* 111 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x7;
/*     */     
/* 113 */     float f = 0.15F;
/* 114 */     if (i == 1) {
/* 115 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 116 */     } else if (i == 2) {
/* 117 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 118 */     } else if (i == 3) {
/* 119 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 120 */     } else if (i == 4) {
/* 121 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } else {
/* 123 */       f = 0.1F;
/* 124 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */     
/* 127 */     return super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramVec3D1, paramVec3D2);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockTorch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */