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
/*     */ 
/*     */ public class BlockDiode
/*     */   extends Block
/*     */ {
/*  21 */   public static final double[] a = { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final int[] b = { 1, 2, 3, 4 };
/*     */ 
/*     */   
/*     */   private final boolean c;
/*     */ 
/*     */   
/*     */   protected BlockDiode(int paramInt, boolean paramBoolean) {
/*  33 */     super(paramInt, 6, Material.ORIENTABLE);
/*  34 */     this.c = paramBoolean;
/*  35 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  45 */     if (!paramWorld.e(paramInt1, paramInt2 - 1, paramInt3)) {
/*  46 */       return false;
/*     */     }
/*  48 */     return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  53 */     if (!paramWorld.e(paramInt1, paramInt2 - 1, paramInt3)) {
/*  54 */       return false;
/*     */     }
/*  56 */     return super.f(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/*  62 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  63 */     boolean bool = f(paramWorld, paramInt1, paramInt2, paramInt3, i);
/*  64 */     if (this.c && !bool) {
/*  65 */       paramWorld.setTypeIdAndData(paramInt1, paramInt2, paramInt3, Block.DIODE_OFF.id, i);
/*  66 */     } else if (!this.c) {
/*     */ 
/*     */       
/*  69 */       paramWorld.setTypeIdAndData(paramInt1, paramInt2, paramInt3, Block.DIODE_ON.id, i);
/*  70 */       if (!bool) {
/*  71 */         int j = (i & 0xC) >> 2;
/*  72 */         paramWorld.c(paramInt1, paramInt2, paramInt3, Block.DIODE_ON.id, b[j] * 2);
/*     */       } 
/*     */     } 
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
/*     */   public int a(int paramInt1, int paramInt2) {
/*  89 */     if (paramInt1 == 0) {
/*  90 */       if (this.c) {
/*  91 */         return 99;
/*     */       }
/*  93 */       return 115;
/*     */     } 
/*  95 */     if (paramInt1 == 1) {
/*  96 */       if (this.c) {
/*  97 */         return 147;
/*     */       }
/*  99 */       return 131;
/*     */     } 
/*     */     
/* 102 */     return 5;
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
/* 121 */   public int a(int paramInt) { return a(paramInt, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public boolean d(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { return a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 131 */     if (!this.c) {
/* 132 */       return false;
/*     */     }
/*     */     
/* 135 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) & 0x3;
/*     */     
/* 137 */     if (i == 0 && paramInt4 == 3) return true; 
/* 138 */     if (i == 1 && paramInt4 == 4) return true; 
/* 139 */     if (i == 2 && paramInt4 == 2) return true; 
/* 140 */     if (i == 3 && paramInt4 == 5) return true;
/*     */     
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 148 */     if (!f(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 149 */       g(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 150 */       paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/* 156 */     boolean bool = f(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 157 */     int j = (i & 0xC) >> 2;
/* 158 */     if (this.c && !bool) {
/* 159 */       paramWorld.c(paramInt1, paramInt2, paramInt3, this.id, b[j] * 2);
/* 160 */     } else if (!this.c && bool) {
/* 161 */       paramWorld.c(paramInt1, paramInt2, paramInt3, this.id, b[j] * 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 166 */     int i = paramInt4 & 0x3;
/* 167 */     switch (i) {
/*     */       case 0:
/* 169 */         return (paramWorld.isBlockFaceIndirectlyPowered(paramInt1, paramInt2, paramInt3 + 1, 3) || (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == Block.REDSTONE_WIRE.id && paramWorld.getData(paramInt1, paramInt2, paramInt3 + 1) > 0));
/*     */       case 2:
/* 171 */         return (paramWorld.isBlockFaceIndirectlyPowered(paramInt1, paramInt2, paramInt3 - 1, 2) || (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == Block.REDSTONE_WIRE.id && paramWorld.getData(paramInt1, paramInt2, paramInt3 - 1) > 0));
/*     */       case 3:
/* 173 */         return (paramWorld.isBlockFaceIndirectlyPowered(paramInt1 + 1, paramInt2, paramInt3, 5) || (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == Block.REDSTONE_WIRE.id && paramWorld.getData(paramInt1 + 1, paramInt2, paramInt3) > 0));
/*     */       case 1:
/* 175 */         return (paramWorld.isBlockFaceIndirectlyPowered(paramInt1 - 1, paramInt2, paramInt3, 4) || (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == Block.REDSTONE_WIRE.id && paramWorld.getData(paramInt1 - 1, paramInt2, paramInt3) > 0));
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 183 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 184 */     int j = (i & 0xC) >> 2;
/* 185 */     j = j + 1 << 2 & 0xC;
/*     */     
/* 187 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, j | i & 0x3);
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public boolean isPowerSource() { return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
/* 198 */     int i = ((MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) + 2) % 4;
/* 199 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
/*     */     
/* 201 */     boolean bool = f(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 202 */     if (bool) {
/* 203 */       paramWorld.c(paramInt1, paramInt2, paramInt3, this.id, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 210 */     paramWorld.applyPhysics(paramInt1 + 1, paramInt2, paramInt3, this.id);
/* 211 */     paramWorld.applyPhysics(paramInt1 - 1, paramInt2, paramInt3, this.id);
/* 212 */     paramWorld.applyPhysics(paramInt1, paramInt2, paramInt3 + 1, this.id);
/* 213 */     paramWorld.applyPhysics(paramInt1, paramInt2, paramInt3 - 1, this.id);
/* 214 */     paramWorld.applyPhysics(paramInt1, paramInt2 - 1, paramInt3, this.id);
/* 215 */     paramWorld.applyPhysics(paramInt1, paramInt2 + 1, paramInt3, this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 220 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public int a(int paramInt, Random paramRandom) { return Item.DIODE.id; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockDiode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */