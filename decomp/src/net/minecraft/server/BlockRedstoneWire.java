/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockRedstoneWire
/*     */   extends Block
/*     */ {
/*     */   private boolean a = true;
/*  13 */   private Set b = new HashSet();
/*     */   
/*     */   public BlockRedstoneWire(int i, int j) {
/*  16 */     super(i, j, Material.ORIENTABLE);
/*  17 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*  21 */   public int a(int i, int j) { return this.textureId; }
/*     */ 
/*     */ 
/*     */   
/*  25 */   public AxisAlignedBB e(World world, int i, int j, int k) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  29 */   public boolean a() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public boolean b() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean canPlace(World world, int i, int j, int k) { return world.e(i, j - 1, k); }
/*     */ 
/*     */   
/*     */   private void g(World world, int i, int j, int k) {
/*  41 */     a(world, i, j, k, i, j, k);
/*  42 */     ArrayList arraylist = new ArrayList(this.b);
/*     */     
/*  44 */     this.b.clear();
/*     */     
/*  46 */     for (int l = 0; l < arraylist.size(); l++) {
/*  47 */       ChunkPosition chunkposition = (ChunkPosition)arraylist.get(l);
/*     */       
/*  49 */       world.applyPhysics(chunkposition.x, chunkposition.y, chunkposition.z, this.id);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l, int i1, int j1) {
/*  54 */     int k1 = world.getData(i, j, k);
/*  55 */     int l1 = 0;
/*     */     
/*  57 */     this.a = false;
/*  58 */     boolean flag = world.isBlockIndirectlyPowered(i, j, k);
/*     */     
/*  60 */     this.a = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     if (flag) {
/*  66 */       l1 = 15;
/*     */     } else {
/*  68 */       for (int i2 = 0; i2 < 4; i2++) {
/*  69 */         int j2 = i;
/*  70 */         int k2 = k;
/*  71 */         if (i2 == 0) {
/*  72 */           j2 = i - 1;
/*     */         }
/*     */         
/*  75 */         if (i2 == 1) {
/*  76 */           j2++;
/*     */         }
/*     */         
/*  79 */         if (i2 == 2) {
/*  80 */           k2 = k - 1;
/*     */         }
/*     */         
/*  83 */         if (i2 == 3) {
/*  84 */           k2++;
/*     */         }
/*     */         
/*  87 */         if (j2 != l || j != i1 || k2 != j1) {
/*  88 */           l1 = getPower(world, j2, j, k2, l1);
/*     */         }
/*     */         
/*  91 */         if (world.e(j2, j, k2) && !world.e(i, j + 1, k)) {
/*  92 */           if (j2 != l || j + 1 != i1 || k2 != j1) {
/*  93 */             l1 = getPower(world, j2, j + 1, k2, l1);
/*     */           }
/*  95 */         } else if (!world.e(j2, j, k2) && (j2 != l || j - 1 != i1 || k2 != j1)) {
/*  96 */           l1 = getPower(world, j2, j - 1, k2, l1);
/*     */         } 
/*     */       } 
/*     */       
/* 100 */       if (l1 > 0) {
/* 101 */         l1--;
/*     */       } else {
/* 103 */         l1 = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 108 */     if (k1 != l1) {
/* 109 */       BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(i, j, k), k1, l1);
/* 110 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 112 */       l1 = event.getNewCurrent();
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (k1 != l1) {
/* 117 */       world.suppressPhysics = true;
/* 118 */       world.setData(i, j, k, l1);
/* 119 */       world.b(i, j, k, i, j, k);
/* 120 */       world.suppressPhysics = false;
/*     */       
/* 122 */       for (int i2 = 0; i2 < 4; i2++) {
/* 123 */         int j2 = i;
/* 124 */         int k2 = k;
/* 125 */         int l2 = j - 1;
/*     */         
/* 127 */         if (i2 == 0) {
/* 128 */           j2 = i - 1;
/*     */         }
/*     */         
/* 131 */         if (i2 == 1) {
/* 132 */           j2++;
/*     */         }
/*     */         
/* 135 */         if (i2 == 2) {
/* 136 */           k2 = k - 1;
/*     */         }
/*     */         
/* 139 */         if (i2 == 3) {
/* 140 */           k2++;
/*     */         }
/*     */         
/* 143 */         if (world.e(j2, j, k2)) {
/* 144 */           l2 += 2;
/*     */         }
/*     */         
/* 147 */         boolean flag1 = false;
/* 148 */         int i3 = getPower(world, j2, j, k2, -1);
/*     */         
/* 150 */         l1 = world.getData(i, j, k);
/* 151 */         if (l1 > 0) {
/* 152 */           l1--;
/*     */         }
/*     */         
/* 155 */         if (i3 >= 0 && i3 != l1) {
/* 156 */           a(world, j2, j, k2, i, j, k);
/*     */         }
/*     */         
/* 159 */         i3 = getPower(world, j2, l2, k2, -1);
/* 160 */         l1 = world.getData(i, j, k);
/* 161 */         if (l1 > 0) {
/* 162 */           l1--;
/*     */         }
/*     */         
/* 165 */         if (i3 >= 0 && i3 != l1) {
/* 166 */           a(world, j2, l2, k2, i, j, k);
/*     */         }
/*     */       } 
/*     */       
/* 170 */       if (k1 == 0 || l1 == 0) {
/* 171 */         this.b.add(new ChunkPosition(i, j, k));
/* 172 */         this.b.add(new ChunkPosition(i - 1, j, k));
/* 173 */         this.b.add(new ChunkPosition(i + 1, j, k));
/* 174 */         this.b.add(new ChunkPosition(i, j - 1, k));
/* 175 */         this.b.add(new ChunkPosition(i, j + 1, k));
/* 176 */         this.b.add(new ChunkPosition(i, j, k - 1));
/* 177 */         this.b.add(new ChunkPosition(i, j, k + 1));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void h(World world, int i, int j, int k) {
/* 183 */     if (world.getTypeId(i, j, k) == this.id) {
/* 184 */       world.applyPhysics(i, j, k, this.id);
/* 185 */       world.applyPhysics(i - 1, j, k, this.id);
/* 186 */       world.applyPhysics(i + 1, j, k, this.id);
/* 187 */       world.applyPhysics(i, j, k - 1, this.id);
/* 188 */       world.applyPhysics(i, j, k + 1, this.id);
/* 189 */       world.applyPhysics(i, j - 1, k, this.id);
/* 190 */       world.applyPhysics(i, j + 1, k, this.id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void c(World world, int i, int j, int k) {
/* 195 */     super.c(world, i, j, k);
/* 196 */     if (!world.isStatic) {
/* 197 */       g(world, i, j, k);
/* 198 */       world.applyPhysics(i, j + 1, k, this.id);
/* 199 */       world.applyPhysics(i, j - 1, k, this.id);
/* 200 */       h(world, i - 1, j, k);
/* 201 */       h(world, i + 1, j, k);
/* 202 */       h(world, i, j, k - 1);
/* 203 */       h(world, i, j, k + 1);
/* 204 */       if (world.e(i - 1, j, k)) {
/* 205 */         h(world, i - 1, j + 1, k);
/*     */       } else {
/* 207 */         h(world, i - 1, j - 1, k);
/*     */       } 
/*     */       
/* 210 */       if (world.e(i + 1, j, k)) {
/* 211 */         h(world, i + 1, j + 1, k);
/*     */       } else {
/* 213 */         h(world, i + 1, j - 1, k);
/*     */       } 
/*     */       
/* 216 */       if (world.e(i, j, k - 1)) {
/* 217 */         h(world, i, j + 1, k - 1);
/*     */       } else {
/* 219 */         h(world, i, j - 1, k - 1);
/*     */       } 
/*     */       
/* 222 */       if (world.e(i, j, k + 1)) {
/* 223 */         h(world, i, j + 1, k + 1);
/*     */       } else {
/* 225 */         h(world, i, j - 1, k + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {
/* 231 */     super.remove(world, i, j, k);
/* 232 */     if (!world.isStatic) {
/* 233 */       world.applyPhysics(i, j + 1, k, this.id);
/* 234 */       world.applyPhysics(i, j - 1, k, this.id);
/* 235 */       g(world, i, j, k);
/* 236 */       h(world, i - 1, j, k);
/* 237 */       h(world, i + 1, j, k);
/* 238 */       h(world, i, j, k - 1);
/* 239 */       h(world, i, j, k + 1);
/* 240 */       if (world.e(i - 1, j, k)) {
/* 241 */         h(world, i - 1, j + 1, k);
/*     */       } else {
/* 243 */         h(world, i - 1, j - 1, k);
/*     */       } 
/*     */       
/* 246 */       if (world.e(i + 1, j, k)) {
/* 247 */         h(world, i + 1, j + 1, k);
/*     */       } else {
/* 249 */         h(world, i + 1, j - 1, k);
/*     */       } 
/*     */       
/* 252 */       if (world.e(i, j, k - 1)) {
/* 253 */         h(world, i, j + 1, k - 1);
/*     */       } else {
/* 255 */         h(world, i, j - 1, k - 1);
/*     */       } 
/*     */       
/* 258 */       if (world.e(i, j, k + 1)) {
/* 259 */         h(world, i, j + 1, k + 1);
/*     */       } else {
/* 261 */         h(world, i, j - 1, k + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPower(World world, int i, int j, int k, int l) {
/* 268 */     if (world.getTypeId(i, j, k) != this.id) {
/* 269 */       return l;
/*     */     }
/* 271 */     int i1 = world.getData(i, j, k);
/*     */     
/* 273 */     return (i1 > l) ? i1 : l;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 278 */     if (!world.isStatic) {
/* 279 */       int i1 = world.getData(i, j, k);
/* 280 */       boolean flag = canPlace(world, i, j, k);
/*     */       
/* 282 */       if (!flag) {
/* 283 */         g(world, i, j, k, i1);
/* 284 */         world.setTypeId(i, j, k, 0);
/*     */       } else {
/* 286 */         g(world, i, j, k);
/*     */       } 
/*     */       
/* 289 */       super.doPhysics(world, i, j, k, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 294 */   public int a(int i, Random random) { return Item.REDSTONE.id; }
/*     */ 
/*     */ 
/*     */   
/* 298 */   public boolean d(World world, int i, int j, int k, int l) { return !this.a ? false : a(world, i, j, k, l); }
/*     */ 
/*     */   
/*     */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 302 */     if (!this.a)
/* 303 */       return false; 
/* 304 */     if (iblockaccess.getData(i, j, k) == 0)
/* 305 */       return false; 
/* 306 */     if (l == 1) {
/* 307 */       return true;
/*     */     }
/* 309 */     boolean flag = (c(iblockaccess, i - 1, j, k, 1) || (!iblockaccess.e(i - 1, j, k) && c(iblockaccess, i - 1, j - 1, k, -1)));
/* 310 */     boolean flag1 = (c(iblockaccess, i + 1, j, k, 3) || (!iblockaccess.e(i + 1, j, k) && c(iblockaccess, i + 1, j - 1, k, -1)));
/* 311 */     boolean flag2 = (c(iblockaccess, i, j, k - 1, 2) || (!iblockaccess.e(i, j, k - 1) && c(iblockaccess, i, j - 1, k - 1, -1)));
/* 312 */     boolean flag3 = (c(iblockaccess, i, j, k + 1, 0) || (!iblockaccess.e(i, j, k + 1) && c(iblockaccess, i, j - 1, k + 1, -1)));
/*     */     
/* 314 */     if (!iblockaccess.e(i, j + 1, k)) {
/* 315 */       if (iblockaccess.e(i - 1, j, k) && c(iblockaccess, i - 1, j + 1, k, -1)) {
/* 316 */         flag = true;
/*     */       }
/*     */       
/* 319 */       if (iblockaccess.e(i + 1, j, k) && c(iblockaccess, i + 1, j + 1, k, -1)) {
/* 320 */         flag1 = true;
/*     */       }
/*     */       
/* 323 */       if (iblockaccess.e(i, j, k - 1) && c(iblockaccess, i, j + 1, k - 1, -1)) {
/* 324 */         flag2 = true;
/*     */       }
/*     */       
/* 327 */       if (iblockaccess.e(i, j, k + 1) && c(iblockaccess, i, j + 1, k + 1, -1)) {
/* 328 */         flag3 = true;
/*     */       }
/*     */     } 
/*     */     
/* 332 */     return (!flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5) ? true : ((l == 2 && flag2 && !flag && !flag1) ? true : ((l == 3 && flag3 && !flag && !flag1) ? true : ((l == 4 && flag && !flag2 && !flag3) ? true : ((l == 5 && flag1 && !flag2 && !flag3)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 337 */   public boolean isPowerSource() { return this.a; }
/*     */ 
/*     */   
/*     */   public static boolean c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 341 */     int i1 = iblockaccess.getTypeId(i, j, k);
/*     */     
/* 343 */     if (i1 == Block.REDSTONE_WIRE.id)
/* 344 */       return true; 
/* 345 */     if (i1 == 0)
/* 346 */       return false; 
/* 347 */     if (Block.byId[i1].isPowerSource())
/* 348 */       return true; 
/* 349 */     if (i1 != Block.DIODE_OFF.id && i1 != Block.DIODE_ON.id) {
/* 350 */       return false;
/*     */     }
/* 352 */     int j1 = iblockaccess.getData(i, j, k);
/*     */     
/* 354 */     return (l == BedBlockTextures.b[j1 & 0x3]);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockRedstoneWire.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */