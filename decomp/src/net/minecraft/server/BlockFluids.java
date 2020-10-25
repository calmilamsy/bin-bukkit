/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockFluids
/*     */   extends Block
/*     */ {
/*     */   protected BlockFluids(int paramInt, Material paramMaterial) {
/*  14 */     super(paramInt, ((paramMaterial == Material.LAVA) ? 14 : 12) * 16 + 13, paramMaterial);
/*  15 */     float f1 = 0.0F;
/*  16 */     float f2 = 0.0F;
/*     */     
/*  18 */     a(0.0F + f2, 0.0F + f1, 0.0F + f2, 1.0F + f2, 1.0F + f1, 1.0F + f2);
/*  19 */     a(true);
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
/*     */   public static float c(int paramInt) {
/*  32 */     if (paramInt >= 8) paramInt = 0; 
/*  33 */     return (paramInt + 1) / 9.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(int paramInt) {
/*  38 */     if (paramInt == 0 || paramInt == 1) {
/*  39 */       return this.textureId;
/*     */     }
/*  41 */     return this.textureId + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  46 */     if (paramWorld.getMaterial(paramInt1, paramInt2, paramInt3) != this.material) return -1; 
/*  47 */     return paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   protected int b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  51 */     if (paramIBlockAccess.getMaterial(paramInt1, paramInt2, paramInt3) != this.material) return -1; 
/*  52 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/*  53 */     if (i >= 8) i = 0; 
/*  54 */     return i;
/*     */   }
/*     */ 
/*     */   
/*  58 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public boolean a(int paramInt, boolean paramBoolean) { return (paramBoolean && paramInt == 0); }
/*     */ 
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  70 */     Material material = paramIBlockAccess.getMaterial(paramInt1, paramInt2, paramInt3);
/*  71 */     if (material == this.material) return false; 
/*  72 */     if (material == Material.ICE) return false; 
/*  73 */     if (paramInt4 == 1) return true; 
/*  74 */     return super.b(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*  86 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int a(int paramInt, Random paramRandom) { return 0; }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public int a(Random paramRandom) { return 0; }
/*     */ 
/*     */   
/*     */   private Vec3D c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 102 */     null = Vec3D.create(0.0D, 0.0D, 0.0D);
/* 103 */     int i = b(paramIBlockAccess, paramInt1, paramInt2, paramInt3); byte b;
/* 104 */     for (b = 0; b < 4; b++) {
/*     */       
/* 106 */       int j = paramInt1;
/* 107 */       int k = paramInt2;
/* 108 */       int m = paramInt3;
/*     */       
/* 110 */       if (!b) j--; 
/* 111 */       if (b == 1) m--; 
/* 112 */       if (b == 2) j++; 
/* 113 */       if (b == 3) m++;
/*     */       
/* 115 */       int n = b(paramIBlockAccess, j, k, m);
/* 116 */       if (n < 0) {
/* 117 */         if (!paramIBlockAccess.getMaterial(j, k, m).isSolid()) {
/* 118 */           n = b(paramIBlockAccess, j, k - 1, m);
/* 119 */           if (n >= 0) {
/* 120 */             int i1 = n - i - 8;
/* 121 */             null = null.add(((j - paramInt1) * i1), ((k - paramInt2) * i1), ((m - paramInt3) * i1));
/*     */           }
/*     */         
/*     */         } 
/* 125 */       } else if (n >= 0) {
/* 126 */         int i1 = n - i;
/* 127 */         null = null.add(((j - paramInt1) * i1), ((k - paramInt2) * i1), ((m - paramInt3) * i1));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if (paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) >= 8) {
/* 133 */       b = 0;
/* 134 */       if (b != 0 || b(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, 2)) b = 1; 
/* 135 */       if (b != 0 || b(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, 3)) b = 1; 
/* 136 */       if (b != 0 || b(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, 4)) b = 1; 
/* 137 */       if (b != 0 || b(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, 5)) b = 1; 
/* 138 */       if (b != 0 || b(paramIBlockAccess, paramInt1, paramInt2 + 1, paramInt3 - 1, 2)) b = 1; 
/* 139 */       if (b != 0 || b(paramIBlockAccess, paramInt1, paramInt2 + 1, paramInt3 + 1, 3)) b = 1; 
/* 140 */       if (b != 0 || b(paramIBlockAccess, paramInt1 - 1, paramInt2 + 1, paramInt3, 4)) b = 1; 
/* 141 */       if (b != 0 || b(paramIBlockAccess, paramInt1 + 1, paramInt2 + 1, paramInt3, 5)) b = 1; 
/* 142 */       if (b != 0) null = null.b().add(0.0D, -6.0D, 0.0D); 
/*     */     } 
/* 144 */     return null.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D) {
/* 149 */     Vec3D vec3D = c(paramWorld, paramInt1, paramInt2, paramInt3);
/* 150 */     paramVec3D.a += vec3D.a;
/* 151 */     paramVec3D.b += vec3D.b;
/* 152 */     paramVec3D.c += vec3D.c;
/*     */   }
/*     */   
/*     */   public int c() {
/* 156 */     if (this.material == Material.WATER) return 5; 
/* 157 */     if (this.material == Material.LAVA) return 30; 
/* 158 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) { super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom); }
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
/* 203 */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { i(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */ 
/*     */   
/* 207 */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { i(paramWorld, paramInt1, paramInt2, paramInt3); }
/*     */ 
/*     */   
/*     */   private void i(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 211 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != this.id)
/* 212 */       return;  if (this.material == Material.LAVA) {
/* 213 */       boolean bool = false;
/* 214 */       if (bool || paramWorld.getMaterial(paramInt1, paramInt2, paramInt3 - true) == Material.WATER) bool = true; 
/* 215 */       if (bool || paramWorld.getMaterial(paramInt1, paramInt2, paramInt3 + true) == Material.WATER) bool = true; 
/* 216 */       if (bool || paramWorld.getMaterial(paramInt1 - true, paramInt2, paramInt3) == Material.WATER) bool = true; 
/* 217 */       if (bool || paramWorld.getMaterial(paramInt1 + true, paramInt2, paramInt3) == Material.WATER) bool = true;
/*     */ 
/*     */       
/* 220 */       if (bool || paramWorld.getMaterial(paramInt1, paramInt2 + true, paramInt3) == Material.WATER) bool = true; 
/* 221 */       if (bool) {
/* 222 */         int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 223 */         if (i == 0) {
/* 224 */           paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.OBSIDIAN.id);
/* 225 */         } else if (i <= 4) {
/* 226 */           paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.COBBLESTONE.id);
/*     */         } 
/* 228 */         h(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void h(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 234 */     paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), "random.fizz", 0.5F, 2.6F + (paramWorld.random.nextFloat() - paramWorld.random.nextFloat()) * 0.8F);
/* 235 */     for (byte b = 0; b < 8; b++)
/* 236 */       paramWorld.a("largesmoke", paramInt1 + Math.random(), paramInt2 + 1.2D, paramInt3 + Math.random(), 0.0D, 0.0D, 0.0D); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFluids.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */