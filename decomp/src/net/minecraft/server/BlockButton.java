/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockButton
/*     */   extends Block {
/*     */   protected BlockButton(int i, int j) {
/*  10 */     super(i, j, Material.ORIENTABLE);
/*  11 */     a(true);
/*     */   }
/*     */ 
/*     */   
/*  15 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public int c() { return 20; }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  31 */   public boolean canPlace(World world, int i, int j, int k, int l) { return (l == 2 && world.e(i, j, k + 1)) ? true : ((l == 3 && world.e(i, j, k - 1)) ? true : ((l == 4 && world.e(i + 1, j, k)) ? true : ((l == 5 && world.e(i - 1, j, k))))); }
/*     */ 
/*     */ 
/*     */   
/*  35 */   public boolean canPlace(World world, int i, int j, int k) { return world.e(i - 1, j, k) ? true : (world.e(i + 1, j, k) ? true : (world.e(i, j, k - 1) ? true : world.e(i, j, k + 1))); }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {
/*  39 */     int i1 = world.getData(i, j, k);
/*  40 */     int j1 = i1 & 0x8;
/*     */     
/*  42 */     i1 &= 0x7;
/*  43 */     if (l == 2 && world.e(i, j, k + 1)) {
/*  44 */       i1 = 4;
/*  45 */     } else if (l == 3 && world.e(i, j, k - 1)) {
/*  46 */       i1 = 3;
/*  47 */     } else if (l == 4 && world.e(i + 1, j, k)) {
/*  48 */       i1 = 2;
/*  49 */     } else if (l == 5 && world.e(i - 1, j, k)) {
/*  50 */       i1 = 1;
/*     */     } else {
/*  52 */       i1 = g(world, i, j, k);
/*     */     } 
/*     */     
/*  55 */     world.setData(i, j, k, i1 + j1);
/*     */   }
/*     */ 
/*     */   
/*  59 */   private int g(World world, int i, int j, int k) { return world.e(i - 1, j, k) ? 1 : (world.e(i + 1, j, k) ? 2 : (world.e(i, j, k - 1) ? 3 : (world.e(i, j, k + 1) ? 4 : 1))); }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  63 */     if (h(world, i, j, k)) {
/*  64 */       int i1 = world.getData(i, j, k) & 0x7;
/*  65 */       boolean flag = false;
/*     */       
/*  67 */       if (!world.e(i - 1, j, k) && i1 == 1) {
/*  68 */         flag = true;
/*     */       }
/*     */       
/*  71 */       if (!world.e(i + 1, j, k) && i1 == 2) {
/*  72 */         flag = true;
/*     */       }
/*     */       
/*  75 */       if (!world.e(i, j, k - 1) && i1 == 3) {
/*  76 */         flag = true;
/*     */       }
/*     */       
/*  79 */       if (!world.e(i, j, k + 1) && i1 == 4) {
/*  80 */         flag = true;
/*     */       }
/*     */       
/*  83 */       if (flag) {
/*  84 */         g(world, i, j, k, world.getData(i, j, k));
/*  85 */         world.setTypeId(i, j, k, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean h(World world, int i, int j, int k) {
/*  91 */     if (!canPlace(world, i, j, k)) {
/*  92 */       g(world, i, j, k, world.getData(i, j, k));
/*  93 */       world.setTypeId(i, j, k, 0);
/*  94 */       return false;
/*     */     } 
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 101 */     int l = iblockaccess.getData(i, j, k);
/* 102 */     int i1 = l & 0x7;
/* 103 */     boolean flag = ((l & 0x8) > 0);
/* 104 */     float f = 0.375F;
/* 105 */     float f1 = 0.625F;
/* 106 */     float f2 = 0.1875F;
/* 107 */     float f3 = 0.125F;
/*     */     
/* 109 */     if (flag) {
/* 110 */       f3 = 0.0625F;
/*     */     }
/*     */     
/* 113 */     if (i1 == 1) {
/* 114 */       a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
/* 115 */     } else if (i1 == 2) {
/* 116 */       a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
/* 117 */     } else if (i1 == 3) {
/* 118 */       a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
/* 119 */     } else if (i1 == 4) {
/* 120 */       a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 125 */   public void b(World world, int i, int j, int k, EntityHuman entityhuman) { interact(world, i, j, k, entityhuman); }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 129 */     int l = world.getData(i, j, k);
/* 130 */     int i1 = l & 0x7;
/* 131 */     int j1 = 8 - (l & 0x8);
/*     */     
/* 133 */     if (j1 == 0) {
/* 134 */       return true;
/*     */     }
/*     */     
/* 137 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 138 */     int old = (j1 != 8) ? 1 : 0;
/* 139 */     int current = (j1 == 8) ? 1 : 0;
/*     */     
/* 141 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 142 */     world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     
/* 144 */     if (((eventRedstone.getNewCurrent() > 0) ? 1 : 0) != ((j1 == 8) ? 1 : 0)) {
/* 145 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 149 */     world.setData(i, j, k, i1 + j1);
/* 150 */     world.b(i, j, k, i, j, k);
/* 151 */     world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
/* 152 */     world.applyPhysics(i, j, k, this.id);
/* 153 */     if (i1 == 1) {
/* 154 */       world.applyPhysics(i - 1, j, k, this.id);
/* 155 */     } else if (i1 == 2) {
/* 156 */       world.applyPhysics(i + 1, j, k, this.id);
/* 157 */     } else if (i1 == 3) {
/* 158 */       world.applyPhysics(i, j, k - 1, this.id);
/* 159 */     } else if (i1 == 4) {
/* 160 */       world.applyPhysics(i, j, k + 1, this.id);
/*     */     } else {
/* 162 */       world.applyPhysics(i, j - 1, k, this.id);
/*     */     } 
/*     */     
/* 165 */     world.c(i, j, k, this.id, c());
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 171 */     int l = world.getData(i, j, k);
/*     */     
/* 173 */     if ((l & 0x8) > 0) {
/* 174 */       world.applyPhysics(i, j, k, this.id);
/* 175 */       int i1 = l & 0x7;
/*     */       
/* 177 */       if (i1 == 1) {
/* 178 */         world.applyPhysics(i - 1, j, k, this.id);
/* 179 */       } else if (i1 == 2) {
/* 180 */         world.applyPhysics(i + 1, j, k, this.id);
/* 181 */       } else if (i1 == 3) {
/* 182 */         world.applyPhysics(i, j, k - 1, this.id);
/* 183 */       } else if (i1 == 4) {
/* 184 */         world.applyPhysics(i, j, k + 1, this.id);
/*     */       } else {
/* 186 */         world.applyPhysics(i, j - 1, k, this.id);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     super.remove(world, i, j, k);
/*     */   }
/*     */ 
/*     */   
/* 194 */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) { return ((iblockaccess.getData(i, j, k) & 0x8) > 0); }
/*     */ 
/*     */   
/*     */   public boolean d(World world, int i, int j, int k, int l) {
/* 198 */     int i1 = world.getData(i, j, k);
/*     */     
/* 200 */     if ((i1 & 0x8) == 0) {
/* 201 */       return false;
/*     */     }
/* 203 */     int j1 = i1 & 0x7;
/*     */     
/* 205 */     return (j1 == 5 && l == 1) ? true : ((j1 == 4 && l == 2) ? true : ((j1 == 3 && l == 3) ? true : ((j1 == 2 && l == 4) ? true : ((j1 == 1 && l == 5)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public boolean isPowerSource() { return true; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 214 */     if (!world.isStatic) {
/* 215 */       int l = world.getData(i, j, k);
/*     */       
/* 217 */       if ((l & 0x8) != 0) {
/*     */         
/* 219 */         Block block = world.getWorld().getBlockAt(i, j, k);
/*     */         
/* 221 */         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, true, false);
/* 222 */         world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */         
/* 224 */         if (eventRedstone.getNewCurrent() > 0) {
/*     */           return;
/*     */         }
/* 227 */         world.setData(i, j, k, l & 0x7);
/* 228 */         world.applyPhysics(i, j, k, this.id);
/* 229 */         int i1 = l & 0x7;
/*     */         
/* 231 */         if (i1 == 1) {
/* 232 */           world.applyPhysics(i - 1, j, k, this.id);
/* 233 */         } else if (i1 == 2) {
/* 234 */           world.applyPhysics(i + 1, j, k, this.id);
/* 235 */         } else if (i1 == 3) {
/* 236 */           world.applyPhysics(i, j, k - 1, this.id);
/* 237 */         } else if (i1 == 4) {
/* 238 */           world.applyPhysics(i, j, k + 1, this.id);
/*     */         } else {
/* 240 */           world.applyPhysics(i, j - 1, k, this.id);
/*     */         } 
/*     */         
/* 243 */         world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.5F);
/* 244 */         world.b(i, j, k, i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */