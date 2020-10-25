/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockFlower
/*     */ {
/*     */   protected BlockCrops(int paramInt1, int paramInt2) {
/*  12 */     super(paramInt1, paramInt2);
/*  13 */     this.textureId = paramInt2;
/*     */     
/*  15 */     a(true);
/*  16 */     float f = 0.5F;
/*  17 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */ 
/*     */   
/*  21 */   protected boolean c(int paramInt) { return (paramInt == Block.SOIL.id); }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/*  25 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/*  26 */     if (paramWorld.getLightLevel(paramInt1, paramInt2 + 1, paramInt3) >= 9) {
/*     */       
/*  28 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  29 */       if (i < 7) {
/*  30 */         float f = h(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */         
/*  32 */         if (paramRandom.nextInt((int)(100.0F / f)) == 0) {
/*  33 */           i++;
/*  34 */           paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public void d_(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { paramWorld.setData(paramInt1, paramInt2, paramInt3, 7); }
/*     */ 
/*     */ 
/*     */   
/*     */   private float h(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  47 */     float f = 1.0F;
/*     */     
/*  49 */     int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1);
/*  50 */     int j = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1);
/*  51 */     int k = paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3);
/*  52 */     int m = paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  54 */     int n = paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3 - 1);
/*  55 */     int i1 = paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3 - 1);
/*  56 */     int i2 = paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3 + 1);
/*  57 */     int i3 = paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3 + 1);
/*     */     
/*  59 */     boolean bool1 = (k == this.id || m == this.id) ? 1 : 0;
/*  60 */     boolean bool2 = (i == this.id || j == this.id) ? 1 : 0;
/*  61 */     boolean bool3 = (n == this.id || i1 == this.id || i2 == this.id || i3 == this.id) ? 1 : 0;
/*     */     
/*  63 */     for (int i4 = paramInt1 - 1; i4 <= paramInt1 + 1; i4++) {
/*  64 */       for (int i5 = paramInt3 - 1; i5 <= paramInt3 + 1; i5++) {
/*  65 */         int i6 = paramWorld.getTypeId(i4, paramInt2 - 1, i5);
/*     */         
/*  67 */         float f1 = 0.0F;
/*  68 */         if (i6 == Block.SOIL.id) {
/*  69 */           f1 = 1.0F;
/*  70 */           if (paramWorld.getData(i4, paramInt2 - 1, i5) > 0) f1 = 3.0F;
/*     */         
/*     */         } 
/*  73 */         if (i4 != paramInt1 || i5 != paramInt3) f1 /= 4.0F;
/*     */         
/*  75 */         f += f1;
/*     */       } 
/*     */     } 
/*  78 */     if (bool3 || (bool1 && bool2)) f /= 2.0F;
/*     */     
/*  80 */     return f;
/*     */   }
/*     */   
/*     */   public int a(int paramInt1, int paramInt2) {
/*  84 */     if (paramInt2 < 0) paramInt2 = 7; 
/*  85 */     return this.textureId + paramInt2;
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
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) {
/*  98 */     super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat);
/*     */     
/* 100 */     if (paramWorld.isStatic) {
/*     */       return;
/*     */     }
/* 103 */     for (byte b = 0; b < 3; b++) {
/* 104 */       if (paramWorld.random.nextInt(15) <= paramInt4) {
/* 105 */         float f1 = 0.7F;
/* 106 */         float f2 = paramWorld.random.nextFloat() * f1 + (1.0F - f1) * 0.5F;
/* 107 */         float f3 = paramWorld.random.nextFloat() * f1 + (1.0F - f1) * 0.5F;
/* 108 */         float f4 = paramWorld.random.nextFloat() * f1 + (1.0F - f1) * 0.5F;
/* 109 */         EntityItem entityItem = new EntityItem(paramWorld, (paramInt1 + f2), (paramInt2 + f3), (paramInt3 + f4), new ItemStack(Item.SEEDS));
/* 110 */         entityItem.pickupDelay = 10;
/* 111 */         paramWorld.addEntity(entityItem);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public int a(int paramInt, Random paramRandom) {
/* 116 */     if (paramInt == 7) {
/* 117 */       return Item.WHEAT.id;
/*     */     }
/*     */     
/* 120 */     return -1;
/*     */   }
/*     */ 
/*     */   
/* 124 */   public int a(Random paramRandom) { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockCrops.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */