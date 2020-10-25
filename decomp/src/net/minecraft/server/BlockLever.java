/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockLever
/*     */   extends Block {
/*   8 */   protected BlockLever(int i, int j) { super(i, j, Material.ORIENTABLE); }
/*     */ 
/*     */ 
/*     */   
/*  12 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  16 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  20 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public boolean canPlace(World world, int i, int j, int k, int l) { return (l == 1 && world.e(i, j - 1, k)) ? true : ((l == 2 && world.e(i, j, k + 1)) ? true : ((l == 3 && world.e(i, j, k - 1)) ? true : ((l == 4 && world.e(i + 1, j, k)) ? true : ((l == 5 && world.e(i - 1, j, k)))))); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public boolean canPlace(World world, int i, int j, int k) { return world.e(i - 1, j, k) ? true : (world.e(i + 1, j, k) ? true : (world.e(i, j, k - 1) ? true : (world.e(i, j, k + 1) ? true : world.e(i, j - 1, k)))); }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {
/*  32 */     int i1 = world.getData(i, j, k);
/*  33 */     int j1 = i1 & 0x8;
/*     */     
/*  35 */     i1 &= 0x7;
/*  36 */     i1 = -1;
/*  37 */     if (l == 1 && world.e(i, j - 1, k)) {
/*  38 */       i1 = 5 + world.random.nextInt(2);
/*     */     }
/*     */     
/*  41 */     if (l == 2 && world.e(i, j, k + 1)) {
/*  42 */       i1 = 4;
/*     */     }
/*     */     
/*  45 */     if (l == 3 && world.e(i, j, k - 1)) {
/*  46 */       i1 = 3;
/*     */     }
/*     */     
/*  49 */     if (l == 4 && world.e(i + 1, j, k)) {
/*  50 */       i1 = 2;
/*     */     }
/*     */     
/*  53 */     if (l == 5 && world.e(i - 1, j, k)) {
/*  54 */       i1 = 1;
/*     */     }
/*     */     
/*  57 */     if (i1 == -1) {
/*  58 */       g(world, i, j, k, world.getData(i, j, k));
/*  59 */       world.setTypeId(i, j, k, 0);
/*     */     } else {
/*  61 */       world.setData(i, j, k, i1 + j1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  66 */     if (g(world, i, j, k)) {
/*  67 */       int i1 = world.getData(i, j, k) & 0x7;
/*  68 */       boolean flag = false;
/*     */       
/*  70 */       if (!world.e(i - 1, j, k) && i1 == 1) {
/*  71 */         flag = true;
/*     */       }
/*     */       
/*  74 */       if (!world.e(i + 1, j, k) && i1 == 2) {
/*  75 */         flag = true;
/*     */       }
/*     */       
/*  78 */       if (!world.e(i, j, k - 1) && i1 == 3) {
/*  79 */         flag = true;
/*     */       }
/*     */       
/*  82 */       if (!world.e(i, j, k + 1) && i1 == 4) {
/*  83 */         flag = true;
/*     */       }
/*     */       
/*  86 */       if (!world.e(i, j - 1, k) && i1 == 5) {
/*  87 */         flag = true;
/*     */       }
/*     */       
/*  90 */       if (!world.e(i, j - 1, k) && i1 == 6) {
/*  91 */         flag = true;
/*     */       }
/*     */       
/*  94 */       if (flag) {
/*  95 */         g(world, i, j, k, world.getData(i, j, k));
/*  96 */         world.setTypeId(i, j, k, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean g(World world, int i, int j, int k) {
/* 102 */     if (!canPlace(world, i, j, k)) {
/* 103 */       g(world, i, j, k, world.getData(i, j, k));
/* 104 */       world.setTypeId(i, j, k, 0);
/* 105 */       return false;
/*     */     } 
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 112 */     int l = iblockaccess.getData(i, j, k) & 0x7;
/* 113 */     float f = 0.1875F;
/*     */     
/* 115 */     if (l == 1) {
/* 116 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 117 */     } else if (l == 2) {
/* 118 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 119 */     } else if (l == 3) {
/* 120 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 121 */     } else if (l == 4) {
/* 122 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } else {
/* 124 */       f = 0.25F;
/* 125 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 130 */   public void b(World world, int i, int j, int k, EntityHuman entityhuman) { interact(world, i, j, k, entityhuman); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 134 */     if (world.isStatic) {
/* 135 */       return true;
/*     */     }
/* 137 */     int l = world.getData(i, j, k);
/* 138 */     int i1 = l & 0x7;
/* 139 */     int j1 = 8 - (l & 0x8);
/*     */ 
/*     */     
/* 142 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 143 */     int old = (j1 != 8) ? 1 : 0;
/* 144 */     int current = (j1 == 8) ? 1 : 0;
/*     */     
/* 146 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 147 */     world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     
/* 149 */     if (((eventRedstone.getNewCurrent() > 0) ? 1 : 0) != ((j1 == 8) ? 1 : 0)) {
/* 150 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 154 */     world.setData(i, j, k, i1 + j1);
/* 155 */     world.b(i, j, k, i, j, k);
/* 156 */     world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, (j1 > 0) ? 0.6F : 0.5F);
/* 157 */     world.applyPhysics(i, j, k, this.id);
/* 158 */     if (i1 == 1) {
/* 159 */       world.applyPhysics(i - 1, j, k, this.id);
/* 160 */     } else if (i1 == 2) {
/* 161 */       world.applyPhysics(i + 1, j, k, this.id);
/* 162 */     } else if (i1 == 3) {
/* 163 */       world.applyPhysics(i, j, k - 1, this.id);
/* 164 */     } else if (i1 == 4) {
/* 165 */       world.applyPhysics(i, j, k + 1, this.id);
/*     */     } else {
/* 167 */       world.applyPhysics(i, j - 1, k, this.id);
/*     */     } 
/*     */     
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 175 */     int l = world.getData(i, j, k);
/*     */     
/* 177 */     if ((l & 0x8) > 0) {
/* 178 */       world.applyPhysics(i, j, k, this.id);
/* 179 */       int i1 = l & 0x7;
/*     */       
/* 181 */       if (i1 == 1) {
/* 182 */         world.applyPhysics(i - 1, j, k, this.id);
/* 183 */       } else if (i1 == 2) {
/* 184 */         world.applyPhysics(i + 1, j, k, this.id);
/* 185 */       } else if (i1 == 3) {
/* 186 */         world.applyPhysics(i, j, k - 1, this.id);
/* 187 */       } else if (i1 == 4) {
/* 188 */         world.applyPhysics(i, j, k + 1, this.id);
/*     */       } else {
/* 190 */         world.applyPhysics(i, j - 1, k, this.id);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     super.remove(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/* 198 */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) { return ((iblockaccess.getData(i, j, k) & 0x8) > 0); }
/*     */ 
/*     */   
/*     */   public boolean d(World world, int i, int j, int k, int l) {
/* 202 */     int i1 = world.getData(i, j, k);
/*     */     
/* 204 */     if ((i1 & 0x8) == 0) {
/* 205 */       return false;
/*     */     }
/* 207 */     int j1 = i1 & 0x7;
/*     */     
/* 209 */     return (j1 == 6 && l == 1) ? true : ((j1 == 5 && l == 1) ? true : ((j1 == 4 && l == 2) ? true : ((j1 == 3 && l == 3) ? true : ((j1 == 2 && l == 4) ? true : ((j1 == 1 && l == 5))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public boolean isPowerSource() { return true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockLever.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */