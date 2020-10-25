/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockFurnace
/*     */   extends BlockContainer {
/*   7 */   private Random a = new Random();
/*     */   private final boolean b;
/*     */   private static boolean c = false;
/*     */   
/*     */   protected BlockFurnace(int i, boolean flag) {
/*  12 */     super(i, Material.STONE);
/*  13 */     this.b = flag;
/*  14 */     this.textureId = 45;
/*     */   }
/*     */ 
/*     */   
/*  18 */   public int a(int i, Random random) { return Block.FURNACE.id; }
/*     */ 
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/*  22 */     super.c(world, i, j, k);
/*  23 */     g(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/*  27 */     if (!world.isStatic) {
/*  28 */       int l = world.getTypeId(i, j, k - 1);
/*  29 */       int i1 = world.getTypeId(i, j, k + 1);
/*  30 */       int j1 = world.getTypeId(i - 1, j, k);
/*  31 */       int k1 = world.getTypeId(i + 1, j, k);
/*  32 */       byte b0 = 3;
/*     */       
/*  34 */       if (Block.o[l] && !Block.o[i1]) {
/*  35 */         b0 = 3;
/*     */       }
/*     */       
/*  38 */       if (Block.o[i1] && !Block.o[l]) {
/*  39 */         b0 = 2;
/*     */       }
/*     */       
/*  42 */       if (Block.o[j1] && !Block.o[k1]) {
/*  43 */         b0 = 5;
/*     */       }
/*     */       
/*  46 */       if (Block.o[k1] && !Block.o[j1]) {
/*  47 */         b0 = 4;
/*     */       }
/*     */       
/*  50 */       world.setData(i, j, k, b0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  55 */   public int a(int i) { return (i == 1) ? (this.textureId + 17) : ((i == 0) ? (this.textureId + 17) : ((i == 3) ? (this.textureId - 1) : this.textureId)); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  59 */     if (world.isStatic) {
/*  60 */       return true;
/*     */     }
/*  62 */     TileEntityFurnace tileentityfurnace = (TileEntityFurnace)world.getTileEntity(i, j, k);
/*     */     
/*  64 */     entityhuman.a(tileentityfurnace);
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(boolean flag, World world, int i, int j, int k) {
/*  70 */     int l = world.getData(i, j, k);
/*  71 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*  72 */     if (tileentity == null)
/*     */       return; 
/*  74 */     c = true;
/*  75 */     if (flag) {
/*  76 */       world.setTypeId(i, j, k, Block.BURNING_FURNACE.id);
/*     */     } else {
/*  78 */       world.setTypeId(i, j, k, Block.FURNACE.id);
/*     */     } 
/*     */     
/*  81 */     c = false;
/*  82 */     world.setData(i, j, k, l);
/*  83 */     tileentity.j();
/*  84 */     world.setTileEntity(i, j, k, tileentity);
/*     */   }
/*     */ 
/*     */   
/*  88 */   protected TileEntity a_() { return new TileEntityFurnace(); }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
/*  92 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/*  94 */     if (l == 0) {
/*  95 */       world.setData(i, j, k, 2);
/*     */     }
/*     */     
/*  98 */     if (l == 1) {
/*  99 */       world.setData(i, j, k, 5);
/*     */     }
/*     */     
/* 102 */     if (l == 2) {
/* 103 */       world.setData(i, j, k, 3);
/*     */     }
/*     */     
/* 106 */     if (l == 3) {
/* 107 */       world.setData(i, j, k, 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 112 */     if (!c) {
/* 113 */       TileEntityFurnace tileentityfurnace = (TileEntityFurnace)world.getTileEntity(i, j, k);
/*     */       
/* 115 */       if (tileentityfurnace == null)
/* 116 */         return;  for (int l = 0; l < tileentityfurnace.getSize(); l++) {
/* 117 */         ItemStack itemstack = tileentityfurnace.getItem(l);
/*     */         
/* 119 */         if (itemstack != null) {
/* 120 */           float f = this.a.nextFloat() * 0.8F + 0.1F;
/* 121 */           float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/* 122 */           float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 124 */           while (itemstack.count > 0) {
/* 125 */             int i1 = this.a.nextInt(21) + 10;
/*     */             
/* 127 */             if (i1 > itemstack.count) {
/* 128 */               i1 = itemstack.count;
/*     */             }
/*     */             
/* 131 */             itemstack.count -= i1;
/* 132 */             EntityItem entityitem = new EntityItem(world, (i + f), (j + f1), (k + f2), new ItemStack(itemstack.id, i1, itemstack.getData()));
/* 133 */             float f3 = 0.05F;
/*     */             
/* 135 */             entityitem.motX = ((float)this.a.nextGaussian() * f3);
/* 136 */             entityitem.motY = ((float)this.a.nextGaussian() * f3 + 0.2F);
/* 137 */             entityitem.motZ = ((float)this.a.nextGaussian() * f3);
/* 138 */             world.addEntity(entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     super.remove(world, i, j, k);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFurnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */