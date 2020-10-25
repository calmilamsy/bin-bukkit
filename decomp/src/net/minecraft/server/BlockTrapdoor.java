/*     */ package net.minecraft.server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockTrapdoor extends Block {
/*     */   protected BlockTrapdoor(int i, Material material) {
/*   8 */     super(i, material);
/*   9 */     this.textureId = 84;
/*  10 */     if (material == Material.ORE) {
/*  11 */       this.textureId++;
/*     */     }
/*     */     
/*  14 */     float f = 0.5F;
/*  15 */     float f1 = 1.0F;
/*     */     
/*  17 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */ 
/*     */   
/*  21 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  25 */   public boolean b() { return false; }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB e(World world, int i, int j, int k) {
/*  29 */     a(world, i, j, k);
/*  30 */     return super.e(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/*  34 */   public void a(IBlockAccess iblockaccess, int i, int j, int k) { c(iblockaccess.getData(i, j, k)); }
/*     */ 
/*     */   
/*     */   public void c(int i) {
/*  38 */     float f = 0.1875F;
/*     */     
/*  40 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*  41 */     if (d(i)) {
/*  42 */       if ((i & 0x3) == 0) {
/*  43 */         a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/*  46 */       if ((i & 0x3) == 1) {
/*  47 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */       }
/*     */       
/*  50 */       if ((i & 0x3) == 2) {
/*  51 */         a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/*  54 */       if ((i & 0x3) == 3) {
/*  55 */         a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  61 */   public void b(World world, int i, int j, int k, EntityHuman entityhuman) { interact(world, i, j, k, entityhuman); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  65 */     if (this.material == Material.ORE) {
/*  66 */       return true;
/*     */     }
/*  68 */     int l = world.getData(i, j, k);
/*     */     
/*  70 */     world.setData(i, j, k, l ^ 0x4);
/*  71 */     world.a(entityhuman, 1003, i, j, k, 0);
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, boolean flag) {
/*  77 */     int l = world.getData(i, j, k);
/*  78 */     boolean flag1 = ((l & 0x4) > 0);
/*     */     
/*  80 */     if (flag1 != flag) {
/*  81 */       world.setData(i, j, k, l ^ 0x4);
/*  82 */       world.a((EntityHuman)null, 1003, i, j, k, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  87 */     if (!world.isStatic) {
/*  88 */       int i1 = world.getData(i, j, k);
/*  89 */       int j1 = i;
/*  90 */       int k1 = k;
/*     */       
/*  92 */       if ((i1 & 0x3) == 0) {
/*  93 */         k1 = k + 1;
/*     */       }
/*     */       
/*  96 */       if ((i1 & 0x3) == 1) {
/*  97 */         k1--;
/*     */       }
/*     */       
/* 100 */       if ((i1 & 0x3) == 2) {
/* 101 */         j1 = i + 1;
/*     */       }
/*     */       
/* 104 */       if ((i1 & 0x3) == 3) {
/* 105 */         j1--;
/*     */       }
/*     */       
/* 108 */       if (!world.e(j1, j, k1)) {
/* 109 */         world.setTypeId(i, j, k, 0);
/* 110 */         g(world, i, j, k, i1);
/*     */       } 
/*     */ 
/*     */       
/* 114 */       if (l > 0 && Block.byId[l] != null && Block.byId[l].isPowerSource()) {
/* 115 */         CraftWorld craftWorld = world.getWorld();
/* 116 */         Block block = craftWorld.getBlockAt(i, j, k);
/*     */         
/* 118 */         int power = block.getBlockPower();
/* 119 */         int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
/*     */         
/* 121 */         if (((oldPower == 0) ? 1 : 0) ^ ((power == 0) ? 1 : 0)) {
/* 122 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
/* 123 */           world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */           
/* 125 */           a(world, i, j, k, (eventRedstone.getNewCurrent() > 0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 133 */     a(world, i, j, k);
/* 134 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {
/* 138 */     byte b0 = 0;
/*     */     
/* 140 */     if (l == 2) {
/* 141 */       b0 = 0;
/*     */     }
/*     */     
/* 144 */     if (l == 3) {
/* 145 */       b0 = 1;
/*     */     }
/*     */     
/* 148 */     if (l == 4) {
/* 149 */       b0 = 2;
/*     */     }
/*     */     
/* 152 */     if (l == 5) {
/* 153 */       b0 = 3;
/*     */     }
/*     */     
/* 156 */     world.setData(i, j, k, b0);
/* 157 */     doPhysics(world, i, j, k, Block.REDSTONE_WIRE.id);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/* 161 */     if (l == 0)
/* 162 */       return false; 
/* 163 */     if (l == 1) {
/* 164 */       return false;
/*     */     }
/* 166 */     if (l == 2) {
/* 167 */       k++;
/*     */     }
/*     */     
/* 170 */     if (l == 3) {
/* 171 */       k--;
/*     */     }
/*     */     
/* 174 */     if (l == 4) {
/* 175 */       i++;
/*     */     }
/*     */     
/* 178 */     if (l == 5) {
/* 179 */       i--;
/*     */     }
/*     */     
/* 182 */     return world.e(i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static boolean d(int i) { return ((i & 0x4) != 0); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockTrapdoor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */