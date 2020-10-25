/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ 
/*     */ public class BlockFlowing
/*     */   extends BlockFluids {
/*  12 */   int a = 0;
/*  13 */   boolean[] b = new boolean[4];
/*  14 */   int[] c = new int[4];
/*     */ 
/*     */   
/*  17 */   protected BlockFlowing(int i, Material material) { super(i, material); }
/*     */ 
/*     */   
/*     */   private void i(World world, int i, int j, int k) {
/*  21 */     int l = world.getData(i, j, k);
/*     */     
/*  23 */     world.setRawTypeIdAndData(i, j, k, this.id + 1, l);
/*  24 */     world.b(i, j, k, i, j, k);
/*  25 */     world.notify(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  30 */     CraftWorld craftWorld = world.getWorld();
/*  31 */     CraftServer craftServer = world.getServer();
/*  32 */     Block source = (craftWorld == null) ? null : craftWorld.getBlockAt(i, j, k);
/*     */ 
/*     */     
/*  35 */     int l = g(world, i, j, k);
/*  36 */     byte b0 = 1;
/*     */     
/*  38 */     if (this.material == Material.LAVA && !world.worldProvider.d) {
/*  39 */       b0 = 2;
/*     */     }
/*     */     
/*  42 */     boolean flag = true;
/*     */ 
/*     */     
/*  45 */     if (l > 0) {
/*  46 */       byte b1 = -100;
/*     */       
/*  48 */       this.a = 0;
/*  49 */       int j1 = f(world, i - 1, j, k, b1);
/*     */       
/*  51 */       j1 = f(world, i + 1, j, k, j1);
/*  52 */       j1 = f(world, i, j, k - 1, j1);
/*  53 */       j1 = f(world, i, j, k + 1, j1);
/*  54 */       int i1 = j1 + b0;
/*  55 */       if (i1 >= 8 || j1 < 0) {
/*  56 */         i1 = -1;
/*     */       }
/*     */       
/*  59 */       if (g(world, i, j + 1, k) >= 0) {
/*  60 */         int k1 = g(world, i, j + 1, k);
/*     */         
/*  62 */         if (k1 >= 8) {
/*  63 */           i1 = k1;
/*     */         } else {
/*  65 */           i1 = k1 + 8;
/*     */         } 
/*     */       } 
/*     */       
/*  69 */       if (this.a >= 2 && this.material == Material.WATER) {
/*  70 */         if (world.getMaterial(i, j - 1, k).isBuildable()) {
/*  71 */           i1 = 0;
/*  72 */         } else if (world.getMaterial(i, j - true, k) == this.material && world.getData(i, j, k) == 0) {
/*  73 */           i1 = 0;
/*     */         } 
/*     */       }
/*     */       
/*  77 */       if (this.material == Material.LAVA && l < 8 && i1 < 8 && i1 > l && random.nextInt(4) != 0) {
/*  78 */         i1 = l;
/*  79 */         flag = false;
/*     */       } 
/*     */       
/*  82 */       if (i1 != l) {
/*  83 */         l = i1;
/*  84 */         if (i1 < 0) {
/*  85 */           world.setTypeId(i, j, k, 0);
/*     */         } else {
/*  87 */           world.setData(i, j, k, i1);
/*  88 */           world.c(i, j, k, this.id, c());
/*  89 */           world.applyPhysics(i, j, k, this.id);
/*     */         } 
/*  91 */       } else if (flag) {
/*  92 */         i(world, i, j, k);
/*     */       } 
/*     */     } else {
/*  95 */       i(world, i, j, k);
/*     */     } 
/*     */     
/*  98 */     if (l(world, i, j - 1, k)) {
/*     */       
/* 100 */       BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
/* 101 */       if (craftServer != null) {
/* 102 */         craftServer.getPluginManager().callEvent(event);
/*     */       }
/*     */       
/* 105 */       if (!event.isCancelled()) {
/* 106 */         if (l >= 8) {
/* 107 */           world.setTypeIdAndData(i, j - 1, k, this.id, l);
/*     */         } else {
/* 109 */           world.setTypeIdAndData(i, j - 1, k, this.id, l + 8);
/*     */         }
/*     */       
/*     */       }
/* 113 */     } else if (l >= 0 && (l == 0 || k(world, i, j - 1, k))) {
/* 114 */       boolean[] aboolean = j(world, i, j, k);
/*     */       
/* 116 */       int i1 = l + b0;
/* 117 */       if (l >= 8) {
/* 118 */         i1 = 1;
/*     */       }
/*     */       
/* 121 */       if (i1 >= 8) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 126 */       BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
/* 127 */       int index = 0;
/*     */       
/* 129 */       for (BlockFace currentFace : faces) {
/* 130 */         if (aboolean[index]) {
/* 131 */           BlockFromToEvent event = new BlockFromToEvent(source, currentFace);
/*     */           
/* 133 */           if (craftServer != null) {
/* 134 */             craftServer.getPluginManager().callEvent(event);
/*     */           }
/*     */           
/* 137 */           if (!event.isCancelled()) {
/* 138 */             flow(world, i + currentFace.getModX(), j, k + currentFace.getModZ(), i1);
/*     */           }
/*     */         } 
/* 141 */         index++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void flow(World world, int i, int j, int k, int l) {
/* 148 */     if (l(world, i, j, k)) {
/* 149 */       int i1 = world.getTypeId(i, j, k);
/*     */       
/* 151 */       if (i1 > 0) {
/* 152 */         if (this.material == Material.LAVA) {
/* 153 */           h(world, i, j, k);
/*     */         } else {
/* 155 */           Block.byId[i1].g(world, i, j, k, world.getData(i, j, k));
/*     */         } 
/*     */       }
/*     */       
/* 159 */       world.setTypeIdAndData(i, j, k, this.id, l);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int b(World world, int i, int j, int k, int l, int i1) {
/* 164 */     int j1 = 1000;
/*     */     
/* 166 */     for (int k1 = 0; k1 < 4; k1++) {
/* 167 */       if ((k1 != 0 || i1 != 1) && (k1 != 1 || i1 != 0) && (k1 != 2 || i1 != 3) && (k1 != 3 || i1 != 2)) {
/* 168 */         int l1 = i;
/* 169 */         int i2 = k;
/*     */         
/* 171 */         if (k1 == 0) {
/* 172 */           l1 = i - 1;
/*     */         }
/*     */         
/* 175 */         if (k1 == 1) {
/* 176 */           l1++;
/*     */         }
/*     */         
/* 179 */         if (k1 == 2) {
/* 180 */           i2 = k - 1;
/*     */         }
/*     */         
/* 183 */         if (k1 == 3) {
/* 184 */           i2++;
/*     */         }
/*     */         
/* 187 */         if (!k(world, l1, j, i2) && (world.getMaterial(l1, j, i2) != this.material || world.getData(l1, j, i2) != 0)) {
/* 188 */           if (!k(world, l1, j - 1, i2)) {
/* 189 */             return l;
/*     */           }
/*     */           
/* 192 */           if (l < 4) {
/* 193 */             int j2 = b(world, l1, j, i2, l + 1, k1);
/*     */             
/* 195 */             if (j2 < j1) {
/* 196 */               j1 = j2;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return j1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] j(World world, int i, int j, int k) {
/*     */     int l;
/* 210 */     for (l = 0; l < 4; l++) {
/* 211 */       this.c[l] = 1000;
/* 212 */       int i1 = i;
/* 213 */       int j1 = k;
/*     */       
/* 215 */       if (l == 0) {
/* 216 */         i1 = i - 1;
/*     */       }
/*     */       
/* 219 */       if (l == 1) {
/* 220 */         i1++;
/*     */       }
/*     */       
/* 223 */       if (l == 2) {
/* 224 */         j1 = k - 1;
/*     */       }
/*     */       
/* 227 */       if (l == 3) {
/* 228 */         j1++;
/*     */       }
/*     */       
/* 231 */       if (!k(world, i1, j, j1) && (world.getMaterial(i1, j, j1) != this.material || world.getData(i1, j, j1) != 0)) {
/* 232 */         if (!k(world, i1, j - 1, j1)) {
/* 233 */           this.c[l] = 0;
/*     */         } else {
/* 235 */           this.c[l] = b(world, i1, j, j1, 1, l);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 240 */     l = this.c[0];
/*     */     int i1;
/* 242 */     for (i1 = 1; i1 < 4; i1++) {
/* 243 */       if (this.c[i1] < l) {
/* 244 */         l = this.c[i1];
/*     */       }
/*     */     } 
/*     */     
/* 248 */     for (i1 = 0; i1 < 4; i1++) {
/* 249 */       this.b[i1] = (this.c[i1] == l);
/*     */     }
/*     */     
/* 252 */     return this.b;
/*     */   }
/*     */   
/*     */   private boolean k(World world, int i, int j, int k) {
/* 256 */     int l = world.getTypeId(i, j, k);
/*     */     
/* 258 */     if (l != Block.WOODEN_DOOR.id && l != Block.IRON_DOOR_BLOCK.id && l != Block.SIGN_POST.id && l != Block.LADDER.id && l != Block.SUGAR_CANE_BLOCK.id) {
/* 259 */       if (l == 0) {
/* 260 */         return false;
/*     */       }
/* 262 */       Material material = (Block.byId[l]).material;
/*     */       
/* 264 */       return material.isSolid();
/*     */     } 
/*     */     
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int f(World world, int i, int j, int k, int l) {
/* 272 */     int i1 = g(world, i, j, k);
/*     */     
/* 274 */     if (i1 < 0) {
/* 275 */       return l;
/*     */     }
/* 277 */     if (i1 == 0) {
/* 278 */       this.a++;
/*     */     }
/*     */     
/* 281 */     if (i1 >= 8) {
/* 282 */       i1 = 0;
/*     */     }
/*     */     
/* 285 */     return (l >= 0 && i1 >= l) ? l : i1;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean l(World world, int i, int j, int k) {
/* 290 */     Material material = world.getMaterial(i, j, k);
/*     */     
/* 292 */     return (material == this.material) ? false : ((material == Material.LAVA) ? false : (!k(world, i, j, k)));
/*     */   }
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/* 296 */     super.c(world, i, j, k);
/* 297 */     if (world.getTypeId(i, j, k) == this.id)
/* 298 */       world.c(i, j, k, this.id, c()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockFlowing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */