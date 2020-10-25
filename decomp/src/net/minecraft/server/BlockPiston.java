/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.block.BlockPistonExtendEvent;
/*     */ import org.bukkit.event.block.BlockPistonRetractEvent;
/*     */ 
/*     */ 
/*     */ public class BlockPiston
/*     */   extends Block
/*     */ {
/*     */   private boolean a;
/*     */   private boolean b;
/*     */   
/*     */   public BlockPiston(int i, int j, boolean flag) {
/*  16 */     super(i, j, Material.PISTON);
/*  17 */     this.a = flag;
/*  18 */     a(h);
/*  19 */     c(0.5F);
/*     */   }
/*     */   
/*     */   public int a(int i, int j) {
/*  23 */     int k = c(j);
/*     */     
/*  25 */     return (k > 5) ? this.textureId : ((i == k) ? ((!d(j) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D) ? this.textureId : 110) : ((i == PistonBlockTextures.a[k]) ? 109 : 108));
/*     */   }
/*     */ 
/*     */   
/*  29 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) { return false; }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
/*  37 */     int l = c(world, i, j, k, (EntityHuman)entityliving);
/*     */     
/*  39 */     world.setData(i, j, k, l);
/*  40 */     if (!world.isStatic) {
/*  41 */       g(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/*  46 */     if (!world.isStatic && !this.b) {
/*  47 */       g(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/*  52 */     if (!world.isStatic && world.getTileEntity(i, j, k) == null) {
/*  53 */       g(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/*  58 */     int l = world.getData(i, j, k);
/*  59 */     int i1 = c(l);
/*  60 */     boolean flag = f(world, i, j, k, i1);
/*     */     
/*  62 */     if (l != 7) {
/*  63 */       if (flag && !d(l)) {
/*     */         
/*  65 */         int length = h(world, i, j, k, i1);
/*  66 */         if (length >= 0) {
/*  67 */           Block block = world.getWorld().getBlockAt(i, j, k);
/*     */           
/*  69 */           BlockPistonExtendEvent event = new BlockPistonExtendEvent(block, length);
/*  70 */           world.getServer().getPluginManager().callEvent(event);
/*     */           
/*  72 */           if (event.isCancelled()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  77 */           world.setRawData(i, j, k, i1 | 0x8);
/*  78 */           world.playNote(i, j, k, 0, i1);
/*     */         } 
/*  80 */       } else if (!flag && d(l)) {
/*     */         
/*  82 */         Block block = world.getWorld().getBlockAt(i, j, k);
/*     */         
/*  84 */         BlockPistonRetractEvent event = new BlockPistonRetractEvent(block);
/*  85 */         world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  87 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  92 */         world.setRawData(i, j, k, i1);
/*  93 */         world.playNote(i, j, k, 1, i1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  99 */   private boolean f(World world, int i, int j, int k, int l) { return (l != 0 && world.isBlockFaceIndirectlyPowered(i, j - 1, k, 0)) ? true : ((l != 1 && world.isBlockFaceIndirectlyPowered(i, j + 1, k, 1)) ? true : ((l != 2 && world.isBlockFaceIndirectlyPowered(i, j, k - 1, 2)) ? true : ((l != 3 && world.isBlockFaceIndirectlyPowered(i, j, k + 1, 3)) ? true : ((l != 5 && world.isBlockFaceIndirectlyPowered(i + 1, j, k, 5)) ? true : ((l != 4 && world.isBlockFaceIndirectlyPowered(i - 1, j, k, 4)) ? true : (world.isBlockFaceIndirectlyPowered(i, j, k, 0) ? true : (world.isBlockFaceIndirectlyPowered(i, j + 2, k, 1) ? true : (world.isBlockFaceIndirectlyPowered(i, j + 1, k - 1, 2) ? true : (world.isBlockFaceIndirectlyPowered(i, j + 1, k + 1, 3) ? true : (world.isBlockFaceIndirectlyPowered(i - 1, j + 1, k, 4) ? true : world.isBlockFaceIndirectlyPowered(i + 1, j + 1, k, 5))))))))))); }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, int i1) {
/* 103 */     this.b = true;
/* 104 */     if (l == 0) {
/* 105 */       if (i(world, i, j, k, i1)) {
/* 106 */         world.setData(i, j, k, i1 | 0x8);
/* 107 */         world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.out", 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
/*     */       } 
/* 109 */     } else if (l == 1) {
/* 110 */       TileEntity tileentity = world.getTileEntity(i + PistonBlockTextures.b[i1], j + PistonBlockTextures.c[i1], k + PistonBlockTextures.d[i1]);
/*     */       
/* 112 */       if (tileentity != null && tileentity instanceof TileEntityPiston) {
/* 113 */         ((TileEntityPiston)tileentity).k();
/*     */       }
/*     */       
/* 116 */       world.setRawTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, i1);
/* 117 */       world.setTileEntity(i, j, k, BlockPistonMoving.a(this.id, i1, i1, false, true));
/* 118 */       if (this.a) {
/* 119 */         int j1 = i + PistonBlockTextures.b[i1] * 2;
/* 120 */         int k1 = j + PistonBlockTextures.c[i1] * 2;
/* 121 */         int l1 = k + PistonBlockTextures.d[i1] * 2;
/* 122 */         int i2 = world.getTypeId(j1, k1, l1);
/* 123 */         int j2 = world.getData(j1, k1, l1);
/* 124 */         boolean flag = false;
/*     */         
/* 126 */         if (i2 == Block.PISTON_MOVING.id) {
/* 127 */           TileEntity tileentity1 = world.getTileEntity(j1, k1, l1);
/*     */           
/* 129 */           if (tileentity1 != null && tileentity1 instanceof TileEntityPiston) {
/* 130 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity1;
/*     */             
/* 132 */             if (tileentitypiston.d() == i1 && tileentitypiston.c()) {
/* 133 */               tileentitypiston.k();
/* 134 */               i2 = tileentitypiston.a();
/* 135 */               j2 = tileentitypiston.e();
/* 136 */               flag = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 141 */         if (!flag && i2 > 0 && a(i2, world, j1, k1, l1, false) && (Block.byId[i2].e() == 0 || i2 == Block.PISTON.id || i2 == Block.PISTON_STICKY.id)) {
/* 142 */           this.b = false;
/* 143 */           world.setTypeId(j1, k1, l1, 0);
/* 144 */           this.b = true;
/* 145 */           i += PistonBlockTextures.b[i1];
/* 146 */           j += PistonBlockTextures.c[i1];
/* 147 */           k += PistonBlockTextures.d[i1];
/* 148 */           world.setRawTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, j2);
/* 149 */           world.setTileEntity(i, j, k, BlockPistonMoving.a(i2, j2, i1, false, false));
/* 150 */         } else if (!flag) {
/* 151 */           this.b = false;
/* 152 */           world.setTypeId(i + PistonBlockTextures.b[i1], j + PistonBlockTextures.c[i1], k + PistonBlockTextures.d[i1], 0);
/* 153 */           this.b = true;
/*     */         } 
/*     */       } else {
/* 156 */         this.b = false;
/* 157 */         world.setTypeId(i + PistonBlockTextures.b[i1], j + PistonBlockTextures.c[i1], k + PistonBlockTextures.d[i1], 0);
/* 158 */         this.b = true;
/*     */       } 
/*     */       
/* 161 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.in", 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 164 */     this.b = false;
/*     */   }
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {
/* 168 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/* 170 */     if (d(l)) {
/* 171 */       switch (c(l)) {
/*     */         case 0:
/* 173 */           a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 1:
/* 177 */           a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 2:
/* 181 */           a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 3:
/* 185 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/*     */           break;
/*     */         
/*     */         case 4:
/* 189 */           a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 5:
/* 193 */           a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F); break;
/*     */       } 
/*     */     } else {
/* 196 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
/* 201 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 202 */     super.a(world, i, j, k, axisalignedbb, arraylist);
/*     */   }
/*     */ 
/*     */   
/* 206 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public static int c(int i) { return i & 0x7; }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public static boolean d(int i) { return ((i & 0x8) != 0); }
/*     */ 
/*     */   
/*     */   private static int c(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 218 */     if (MathHelper.abs((float)entityhuman.locX - i) < 2.0F && MathHelper.abs((float)entityhuman.locZ - k) < 2.0F) {
/* 219 */       double d0 = entityhuman.locY + 1.82D - entityhuman.height;
/*     */       
/* 221 */       if (d0 - j > 2.0D) {
/* 222 */         return 1;
/*     */       }
/*     */       
/* 225 */       if (j - d0 > 0.0D) {
/* 226 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 230 */     int l = MathHelper.floor((entityhuman.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/* 232 */     return (l == 0) ? 2 : ((l == 1) ? 5 : ((l == 2) ? 3 : ((l == 3) ? 4 : 0)));
/*     */   }
/*     */   
/*     */   private static boolean a(int i, World world, int j, int k, int l, boolean flag) {
/* 236 */     if (i == Block.OBSIDIAN.id) {
/* 237 */       return false;
/*     */     }
/* 239 */     if (i != Block.PISTON.id && i != Block.PISTON_STICKY.id) {
/* 240 */       if (Block.byId[i].j() == -1.0F) {
/* 241 */         return false;
/*     */       }
/*     */       
/* 244 */       if (Block.byId[i].e() == 2) {
/* 245 */         return false;
/*     */       }
/*     */       
/* 248 */       if (!flag && Block.byId[i].e() == 1) {
/* 249 */         return false;
/*     */       }
/* 251 */     } else if (d(world.getData(j, k, l))) {
/* 252 */       return false;
/*     */     } 
/*     */     
/* 255 */     TileEntity tileentity = world.getTileEntity(j, k, l);
/*     */     
/* 257 */     return (tileentity == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int h(World world, int i, int j, int k, int l) {
/* 263 */     int i1 = i + PistonBlockTextures.b[l];
/* 264 */     int j1 = j + PistonBlockTextures.c[l];
/* 265 */     int k1 = k + PistonBlockTextures.d[l];
/* 266 */     int l1 = 0;
/*     */ 
/*     */     
/* 269 */     while (l1 < 13) {
/* 270 */       if (j1 <= 0 || j1 >= 127) {
/* 271 */         return -1;
/*     */       }
/*     */       
/* 274 */       int i2 = world.getTypeId(i1, j1, k1);
/*     */       
/* 276 */       if (i2 != 0) {
/* 277 */         if (!a(i2, world, i1, j1, k1, true)) {
/* 278 */           return -1;
/*     */         }
/*     */         
/* 281 */         if (Block.byId[i2].e() != 1) {
/* 282 */           if (l1 == 12) {
/* 283 */             return -1;
/*     */           }
/*     */           
/* 286 */           i1 += PistonBlockTextures.b[l];
/* 287 */           j1 += PistonBlockTextures.c[l];
/* 288 */           k1 += PistonBlockTextures.d[l];
/* 289 */           l1++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 295 */     return l1;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean i(World world, int i, int j, int k, int l) {
/* 300 */     int i1 = i + PistonBlockTextures.b[l];
/* 301 */     int j1 = j + PistonBlockTextures.c[l];
/* 302 */     int k1 = k + PistonBlockTextures.d[l];
/* 303 */     int l1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     while (l1 < 13) {
/* 309 */       if (j1 <= 0 || j1 >= 127) {
/* 310 */         return false;
/*     */       }
/*     */       
/* 313 */       int i2 = world.getTypeId(i1, j1, k1);
/* 314 */       if (i2 != 0) {
/* 315 */         if (!a(i2, world, i1, j1, k1, true)) {
/* 316 */           return false;
/*     */         }
/*     */         
/* 319 */         if (Block.byId[i2].e() != 1) {
/* 320 */           if (l1 == 12) {
/* 321 */             return false;
/*     */           }
/*     */           
/* 324 */           i1 += PistonBlockTextures.b[l];
/* 325 */           j1 += PistonBlockTextures.c[l];
/* 326 */           k1 += PistonBlockTextures.d[l];
/* 327 */           l1++;
/*     */           
/*     */           continue;
/*     */         } 
/* 331 */         Block.byId[i2].g(world, i1, j1, k1, world.getData(i1, j1, k1));
/* 332 */         world.setTypeId(i1, j1, k1, 0);
/*     */       } 
/*     */     } 
/*     */     
/* 336 */     while (i1 != i || j1 != j || k1 != k) {
/* 337 */       l1 = i1 - PistonBlockTextures.b[l];
/* 338 */       int i2 = j1 - PistonBlockTextures.c[l];
/* 339 */       int j2 = k1 - PistonBlockTextures.d[l];
/* 340 */       int k2 = world.getTypeId(l1, i2, j2);
/* 341 */       int l2 = world.getData(l1, i2, j2);
/*     */       
/* 343 */       if (k2 == this.id && l1 == i && i2 == j && j2 == k) {
/* 344 */         world.setRawTypeIdAndData(i1, j1, k1, Block.PISTON_MOVING.id, l | (this.a ? 8 : 0));
/* 345 */         world.setTileEntity(i1, j1, k1, BlockPistonMoving.a(Block.PISTON_EXTENSION.id, l | (this.a ? 8 : 0), l, true, false));
/*     */       } else {
/* 347 */         world.setRawTypeIdAndData(i1, j1, k1, Block.PISTON_MOVING.id, l2);
/* 348 */         world.setTileEntity(i1, j1, k1, BlockPistonMoving.a(k2, l2, l, true, false));
/*     */       } 
/*     */       
/* 351 */       i1 = l1;
/* 352 */       j1 = i2;
/* 353 */       k1 = j2;
/*     */     } 
/*     */     
/* 356 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockPiston.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */