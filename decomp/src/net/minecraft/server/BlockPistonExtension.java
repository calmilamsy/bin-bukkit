/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends Block {
/*   8 */   private int a = -1;
/*     */   
/*     */   public BlockPistonExtension(int i, int j) {
/*  11 */     super(i, j, Material.PISTON);
/*  12 */     a(h);
/*  13 */     c(0.5F);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/*  17 */     super.remove(world, i, j, k);
/*  18 */     int l = world.getData(i, j, k);
/*  19 */     if (l > 5 || l < 0)
/*  20 */       return;  int i1 = PistonBlockTextures.a[b(l)];
/*     */     
/*  22 */     i += PistonBlockTextures.b[i1];
/*  23 */     j += PistonBlockTextures.c[i1];
/*  24 */     k += PistonBlockTextures.d[i1];
/*  25 */     int j1 = world.getTypeId(i, j, k);
/*     */     
/*  27 */     if (j1 == Block.PISTON.id || j1 == Block.PISTON_STICKY.id) {
/*  28 */       l = world.getData(i, j, k);
/*  29 */       if (BlockPiston.d(l)) {
/*  30 */         Block.byId[j1].g(world, i, j, k, l);
/*  31 */         world.setTypeId(i, j, k, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(int i, int j) {
/*  37 */     int k = b(j);
/*     */     
/*  39 */     return (i == k) ? ((this.a >= 0) ? this.a : (((j & 0x8) != 0) ? (this.textureId - 1) : this.textureId)) : ((i == PistonBlockTextures.a[k]) ? 107 : 108);
/*     */   }
/*     */ 
/*     */   
/*  43 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public boolean canPlace(World world, int i, int j, int k) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public boolean canPlace(World world, int i, int j, int k, int l) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int a(Random random) { return 0; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
/*  63 */     int l = world.getData(i, j, k);
/*     */     
/*  65 */     switch (b(l)) {
/*     */       case 0:
/*  67 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*  68 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*  69 */         a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/*  70 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */       
/*     */       case 1:
/*  74 */         a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  75 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*  76 */         a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/*  77 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */       
/*     */       case 2:
/*  81 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*  82 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*  83 */         a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/*  84 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */       
/*     */       case 3:
/*  88 */         a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*  89 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*  90 */         a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/*  91 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */       
/*     */       case 4:
/*  95 */         a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*  96 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*  97 */         a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/*  98 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */       
/*     */       case 5:
/* 102 */         a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 103 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/* 104 */         a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/* 105 */         super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */         break;
/*     */     } 
/* 108 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 112 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/* 114 */     switch (b(l)) {
/*     */       case 0:
/* 116 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 1:
/* 120 */         a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 2:
/* 124 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*     */         break;
/*     */       
/*     */       case 3:
/* 128 */         a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 4:
/* 132 */         a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 5:
/* 136 */         a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 141 */     int i1 = b(world.getData(i, j, k));
/* 142 */     if (i1 > 5 || i1 < 0)
/* 143 */       return;  int j1 = world.getTypeId(i - PistonBlockTextures.b[i1], j - PistonBlockTextures.c[i1], k - PistonBlockTextures.d[i1]);
/*     */     
/* 145 */     if (j1 != Block.PISTON.id && j1 != Block.PISTON_STICKY.id) {
/* 146 */       world.setTypeId(i, j, k, 0);
/*     */     } else {
/* 148 */       Block.byId[j1].doPhysics(world, i - PistonBlockTextures.b[i1], j - PistonBlockTextures.c[i1], k - PistonBlockTextures.d[i1], l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 153 */   public static int b(int i) { return i & 0x7; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPistonExtension.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */