/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.CraftChunk;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Chunk
/*     */ {
/*     */   public static boolean a;
/*     */   public byte[] b;
/*     */   public boolean c;
/*     */   public World world;
/*     */   public NibbleArray e;
/*     */   public NibbleArray f;
/*     */   public NibbleArray g;
/*     */   public byte[] heightMap;
/*     */   public int i;
/*     */   
/*     */   public Chunk(World world, int i, int j) {
/*  32 */     this.tileEntities = new HashMap();
/*  33 */     this.entitySlices = new List[8];
/*  34 */     this.done = false;
/*  35 */     this.o = false;
/*  36 */     this.q = false;
/*  37 */     this.r = 0L;
/*  38 */     this.world = world;
/*  39 */     this.x = i;
/*  40 */     this.z = j;
/*  41 */     this.heightMap = new byte[256];
/*     */     
/*  43 */     for (int k = 0; k < this.entitySlices.length; k++) {
/*  44 */       this.entitySlices[k] = new ArrayList();
/*     */     }
/*     */ 
/*     */     
/*  48 */     CraftWorld cworld = this.world.getWorld();
/*  49 */     this.bukkitChunk = (cworld == null) ? null : cworld.popPreservedChunk(i, j);
/*  50 */     if (this.bukkitChunk == null)
/*  51 */       this.bukkitChunk = new CraftChunk(this); 
/*     */   }
/*     */   public final int x; public final int z; public Map tileEntities; public List[] entitySlices; public boolean done; public boolean o; public boolean p;
/*     */   public boolean q;
/*     */   public long r;
/*     */   public Chunk bukkitChunk;
/*     */   
/*     */   public Chunk(World world, byte[] abyte, int i, int j) {
/*  59 */     this(world, i, j);
/*  60 */     this.b = abyte;
/*  61 */     this.e = new NibbleArray(abyte.length);
/*  62 */     this.f = new NibbleArray(abyte.length);
/*  63 */     this.g = new NibbleArray(abyte.length);
/*     */   }
/*     */ 
/*     */   
/*  67 */   public boolean a(int i, int j) { return (i == this.x && j == this.z); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public int b(int i, int j) { return this.heightMap[j << 4 | i] & 0xFF; }
/*     */ 
/*     */   
/*     */   public void a() {}
/*     */   
/*     */   public void initLighting() {
/*  77 */     int i = 127;
/*     */ 
/*     */     
/*     */     int j;
/*     */     
/*  82 */     for (j = 0; j < 16; j++) {
/*  83 */       for (int k = 0; k < 16; k++) {
/*  84 */         int l = 127;
/*     */         
/*     */         int i1;
/*     */         
/*  88 */         for (i1 = j << 11 | k << 7; l > 0 && Block.q[this.b[i1 + l - 1] & 0xFF] == 0; l--);
/*     */ 
/*     */ 
/*     */         
/*  92 */         this.heightMap[k << 4 | j] = (byte)l;
/*  93 */         if (l < i) {
/*  94 */           i = l;
/*     */         }
/*     */         
/*  97 */         if (!this.world.worldProvider.e) {
/*  98 */           int j1 = 15;
/*  99 */           int k1 = 127;
/*     */           
/*     */           do {
/* 102 */             j1 -= Block.q[this.b[i1 + k1] & 0xFF];
/* 103 */             if (j1 <= 0)
/* 104 */               continue;  this.f.a(j, k1, k, j1);
/*     */ 
/*     */             
/* 107 */             --k1;
/* 108 */           } while (k1 > 0 && j1 > 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     this.i = i;
/*     */     
/* 115 */     for (j = 0; j < 16; j++) {
/* 116 */       for (int k = 0; k < 16; k++) {
/* 117 */         c(j, k);
/*     */       }
/*     */     } 
/*     */     
/* 121 */     this.o = true;
/*     */   }
/*     */   
/*     */   public void loadNOP() {}
/*     */   
/*     */   private void c(int i, int j) {
/* 127 */     int k = b(i, j);
/* 128 */     int l = this.x * 16 + i;
/* 129 */     int i1 = this.z * 16 + j;
/*     */     
/* 131 */     f(l - 1, i1, k);
/* 132 */     f(l + 1, i1, k);
/* 133 */     f(l, i1 - 1, k);
/* 134 */     f(l, i1 + 1, k);
/*     */   }
/*     */   
/*     */   private void f(int i, int j, int k) {
/* 138 */     int l = this.world.getHighestBlockYAt(i, j);
/*     */     
/* 140 */     if (l > k) {
/* 141 */       this.world.a(EnumSkyBlock.SKY, i, k, j, i, l, j);
/* 142 */       this.o = true;
/* 143 */     } else if (l < k) {
/* 144 */       this.world.a(EnumSkyBlock.SKY, i, l, j, i, k, j);
/* 145 */       this.o = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void g(int i, int j, int k) {
/* 150 */     int l = this.heightMap[k << 4 | i] & 0xFF;
/* 151 */     int i1 = l;
/*     */     
/* 153 */     if (j > l) {
/* 154 */       i1 = j;
/*     */     }
/*     */     
/* 157 */     for (int j1 = i << 11 | k << 7; i1 > 0 && Block.q[this.b[j1 + i1 - 1] & 0xFF] == 0; i1--);
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (i1 != l) {
/* 162 */       this.world.g(i, k, i1, l);
/* 163 */       this.heightMap[k << 4 | i] = (byte)i1;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 168 */       if (i1 < this.i) {
/* 169 */         this.i = i1;
/*     */       } else {
/* 171 */         int k1 = 127;
/*     */         
/* 173 */         for (int l1 = 0; l1 < 16; l1++) {
/* 174 */           for (int i2 = 0; i2 < 16; i2++) {
/* 175 */             if ((this.heightMap[i2 << 4 | l1] & 0xFF) < k1) {
/* 176 */               k1 = this.heightMap[i2 << 4 | l1] & 0xFF;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 181 */         this.i = k1;
/*     */       } 
/*     */       
/* 184 */       int k1 = this.x * 16 + i;
/* 185 */       int l1 = this.z * 16 + k;
/* 186 */       if (i1 < l) {
/* 187 */         for (int i2 = i1; i2 < l; i2++) {
/* 188 */           this.f.a(i, i2, k, 15);
/*     */         }
/*     */       } else {
/* 191 */         this.world.a(EnumSkyBlock.SKY, k1, l, l1, k1, i1, l1);
/*     */         
/* 193 */         for (int i2 = l; i2 < i1; i2++) {
/* 194 */           this.f.a(i, i2, k, 0);
/*     */         }
/*     */       } 
/*     */       
/* 198 */       int m = 15;
/*     */       
/*     */       int j2;
/*     */       
/* 202 */       for (j2 = i1; i1 > 0 && m > 0; this.f.a(i, i1, k, m)) {
/* 203 */         i1--;
/* 204 */         int k2 = Block.q[getTypeId(i, i1, k)];
/*     */         
/* 206 */         if (k2 == 0) {
/* 207 */           k2 = 1;
/*     */         }
/*     */         
/* 210 */         m -= k2;
/* 211 */         if (m < 0) {
/* 212 */           m = 0;
/*     */         }
/*     */       } 
/*     */       
/* 216 */       while (i1 > 0 && Block.q[getTypeId(i, i1 - 1, k)] == 0) {
/* 217 */         i1--;
/*     */       }
/*     */       
/* 220 */       if (i1 != j2) {
/* 221 */         this.world.a(EnumSkyBlock.SKY, k1 - 1, i1, l1 - 1, k1 + 1, j2, l1 + 1);
/*     */       }
/*     */       
/* 224 */       this.o = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 229 */   public int getTypeId(int i, int j, int k) { return this.b[i << 11 | k << 7 | j] & 0xFF; }
/*     */ 
/*     */   
/*     */   public boolean a(int i, int j, int k, int l, int i1) {
/* 233 */     byte b0 = (byte)l;
/* 234 */     int j1 = this.heightMap[k << 4 | i] & 0xFF;
/* 235 */     int k1 = this.b[i << 11 | k << 7 | j] & 0xFF;
/*     */     
/* 237 */     if (k1 == l && this.e.a(i, j, k) == i1) {
/* 238 */       return false;
/*     */     }
/* 240 */     int l1 = this.x * 16 + i;
/* 241 */     int i2 = this.z * 16 + k;
/*     */     
/* 243 */     this.b[i << 11 | k << 7 | j] = (byte)(b0 & 0xFF);
/* 244 */     if (k1 != 0 && !this.world.isStatic) {
/* 245 */       Block.byId[k1].remove(this.world, l1, j, i2);
/*     */     }
/*     */     
/* 248 */     this.e.a(i, j, k, i1);
/* 249 */     if (!this.world.worldProvider.e) {
/* 250 */       if (Block.q[b0 & 0xFF] != 0) {
/* 251 */         if (j >= j1) {
/* 252 */           g(i, j + 1, k);
/*     */         }
/* 254 */       } else if (j == j1 - 1) {
/* 255 */         g(i, j, k);
/*     */       } 
/*     */       
/* 258 */       this.world.a(EnumSkyBlock.SKY, l1, j, i2, l1, j, i2);
/*     */     } 
/*     */     
/* 261 */     this.world.a(EnumSkyBlock.BLOCK, l1, j, i2, l1, j, i2);
/* 262 */     c(i, k);
/* 263 */     this.e.a(i, j, k, i1);
/* 264 */     if (l != 0) {
/* 265 */       Block.byId[l].c(this.world, l1, j, i2);
/*     */     }
/*     */     
/* 268 */     this.o = true;
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int i, int j, int k, int l) {
/* 274 */     byte b0 = (byte)l;
/* 275 */     int i1 = this.heightMap[k << 4 | i] & 0xFF;
/* 276 */     int j1 = this.b[i << 11 | k << 7 | j] & 0xFF;
/*     */     
/* 278 */     if (j1 == l) {
/* 279 */       return false;
/*     */     }
/* 281 */     int k1 = this.x * 16 + i;
/* 282 */     int l1 = this.z * 16 + k;
/*     */     
/* 284 */     this.b[i << 11 | k << 7 | j] = (byte)(b0 & 0xFF);
/* 285 */     if (j1 != 0) {
/* 286 */       Block.byId[j1].remove(this.world, k1, j, l1);
/*     */     }
/*     */     
/* 289 */     this.e.a(i, j, k, 0);
/* 290 */     if (Block.q[b0 & 0xFF] != 0) {
/* 291 */       if (j >= i1) {
/* 292 */         g(i, j + 1, k);
/*     */       }
/* 294 */     } else if (j == i1 - 1) {
/* 295 */       g(i, j, k);
/*     */     } 
/*     */     
/* 298 */     this.world.a(EnumSkyBlock.SKY, k1, j, l1, k1, j, l1);
/* 299 */     this.world.a(EnumSkyBlock.BLOCK, k1, j, l1, k1, j, l1);
/* 300 */     c(i, k);
/* 301 */     if (l != 0 && !this.world.isStatic) {
/* 302 */       Block.byId[l].c(this.world, k1, j, l1);
/*     */     }
/*     */     
/* 305 */     this.o = true;
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 311 */   public int getData(int i, int j, int k) { return this.e.a(i, j, k); }
/*     */ 
/*     */   
/*     */   public void b(int i, int j, int k, int l) {
/* 315 */     this.o = true;
/* 316 */     this.e.a(i, j, k, l);
/*     */   }
/*     */ 
/*     */   
/* 320 */   public int a(EnumSkyBlock enumskyblock, int i, int j, int k) { return (enumskyblock == EnumSkyBlock.SKY) ? this.f.a(i, j, k) : ((enumskyblock == EnumSkyBlock.BLOCK) ? this.g.a(i, j, k) : 0); }
/*     */ 
/*     */   
/*     */   public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
/* 324 */     this.o = true;
/* 325 */     if (enumskyblock == EnumSkyBlock.SKY) {
/* 326 */       this.f.a(i, j, k, l);
/*     */     } else {
/* 328 */       if (enumskyblock != EnumSkyBlock.BLOCK) {
/*     */         return;
/*     */       }
/*     */       
/* 332 */       this.g.a(i, j, k, l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int c(int i, int j, int k, int l) {
/* 337 */     int i1 = this.f.a(i, j, k);
/*     */     
/* 339 */     if (i1 > 0) {
/* 340 */       a = true;
/*     */     }
/*     */     
/* 343 */     i1 -= l;
/* 344 */     int j1 = this.g.a(i, j, k);
/*     */     
/* 346 */     if (j1 > i1) {
/* 347 */       i1 = j1;
/*     */     }
/*     */     
/* 350 */     return i1;
/*     */   }
/*     */   
/*     */   public void a(Entity entity) {
/* 354 */     this.q = true;
/* 355 */     int i = MathHelper.floor(entity.locX / 16.0D);
/* 356 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/*     */     
/* 358 */     if (i != this.x || j != this.z) {
/* 359 */       System.out.println("Wrong location! " + entity);
/*     */ 
/*     */       
/* 362 */       System.out.println("" + entity.locX + "," + entity.locZ + "(" + i + "," + j + ") vs " + this.x + "," + this.z);
/*     */     } 
/*     */     
/* 365 */     int k = MathHelper.floor(entity.locY / 16.0D);
/*     */     
/* 367 */     if (k < 0) {
/* 368 */       k = 0;
/*     */     }
/*     */     
/* 371 */     if (k >= this.entitySlices.length) {
/* 372 */       k = this.entitySlices.length - 1;
/*     */     }
/*     */     
/* 375 */     entity.bG = true;
/* 376 */     entity.bH = this.x;
/* 377 */     entity.bI = k;
/* 378 */     entity.bJ = this.z;
/* 379 */     this.entitySlices[k].add(entity);
/*     */   }
/*     */ 
/*     */   
/* 383 */   public void b(Entity entity) { a(entity, entity.bI); }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, int i) {
/* 387 */     if (i < 0) {
/* 388 */       i = 0;
/*     */     }
/*     */     
/* 391 */     if (i >= this.entitySlices.length) {
/* 392 */       i = this.entitySlices.length - 1;
/*     */     }
/*     */     
/* 395 */     this.entitySlices[i].remove(entity);
/*     */   }
/*     */ 
/*     */   
/* 399 */   public boolean c(int i, int j, int k) { return (j >= (this.heightMap[k << 4 | i] & 0xFF)); }
/*     */ 
/*     */   
/*     */   public TileEntity d(int i, int j, int k) {
/* 403 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/* 404 */     TileEntity tileentity = (TileEntity)this.tileEntities.get(chunkposition);
/*     */     
/* 406 */     if (tileentity == null) {
/* 407 */       int l = getTypeId(i, j, k);
/*     */       
/* 409 */       if (!Block.isTileEntity[l]) {
/* 410 */         return null;
/*     */       }
/*     */       
/* 413 */       BlockContainer blockcontainer = (BlockContainer)Block.byId[l];
/*     */       
/* 415 */       blockcontainer.c(this.world, this.x * 16 + i, j, this.z * 16 + k);
/* 416 */       tileentity = (TileEntity)this.tileEntities.get(chunkposition);
/*     */     } 
/*     */     
/* 419 */     if (tileentity != null && tileentity.g()) {
/* 420 */       this.tileEntities.remove(chunkposition);
/* 421 */       return null;
/*     */     } 
/* 423 */     return tileentity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(TileEntity tileentity) {
/* 428 */     int i = tileentity.x - this.x * 16;
/* 429 */     int j = tileentity.y;
/* 430 */     int k = tileentity.z - this.z * 16;
/*     */     
/* 432 */     a(i, j, k, tileentity);
/* 433 */     if (this.c) {
/* 434 */       this.world.c.add(tileentity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, TileEntity tileentity) {
/* 439 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/*     */     
/* 441 */     tileentity.world = this.world;
/* 442 */     tileentity.x = this.x * 16 + i;
/* 443 */     tileentity.y = j;
/* 444 */     tileentity.z = this.z * 16 + k;
/* 445 */     if (getTypeId(i, j, k) != 0 && Block.byId[getTypeId(i, j, k)] instanceof BlockContainer) {
/* 446 */       tileentity.j();
/* 447 */       this.tileEntities.put(chunkposition, tileentity);
/*     */     } else {
/* 449 */       System.out.println("Attempted to place a tile entity where there was no entity tile!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void e(int i, int j, int k) {
/* 454 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/*     */     
/* 456 */     if (this.c) {
/* 457 */       TileEntity tileentity = (TileEntity)this.tileEntities.remove(chunkposition);
/*     */       
/* 459 */       if (tileentity != null) {
/* 460 */         tileentity.h();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEntities() {
/* 466 */     this.c = true;
/* 467 */     this.world.a(this.tileEntities.values());
/*     */     
/* 469 */     for (int i = 0; i < this.entitySlices.length; i++) {
/* 470 */       this.world.a(this.entitySlices[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeEntities() {
/* 475 */     this.c = false;
/* 476 */     Iterator iterator = this.tileEntities.values().iterator();
/*     */     
/* 478 */     while (iterator.hasNext()) {
/* 479 */       TileEntity tileentity = (TileEntity)iterator.next();
/*     */       
/* 481 */       this.world.markForRemoval(tileentity);
/*     */     } 
/*     */     
/* 484 */     for (int i = 0; i < this.entitySlices.length; i++) {
/*     */       
/* 486 */       Iterator<Object> iter = this.entitySlices[i].iterator();
/* 487 */       while (iter.hasNext()) {
/* 488 */         Entity entity = (Entity)iter.next();
/* 489 */         int cx = Location.locToBlock(entity.locX) >> 4;
/* 490 */         int cz = Location.locToBlock(entity.locZ) >> 4;
/*     */ 
/*     */ 
/*     */         
/* 494 */         if (entity instanceof EntityPlayer && (cx != this.x || cz != this.z)) {
/* 495 */           iter.remove();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 500 */       this.world.b(this.entitySlices[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 505 */   public void f() { this.o = true; }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, AxisAlignedBB axisalignedbb, List list) {
/* 509 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/* 510 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*     */     
/* 512 */     if (i < 0) {
/* 513 */       i = 0;
/*     */     }
/*     */     
/* 516 */     if (j >= this.entitySlices.length) {
/* 517 */       j = this.entitySlices.length - 1;
/*     */     }
/*     */     
/* 520 */     for (int k = i; k <= j; k++) {
/* 521 */       List list1 = this.entitySlices[k];
/*     */       
/* 523 */       for (int l = 0; l < list1.size(); l++) {
/* 524 */         Entity entity1 = (Entity)list1.get(l);
/*     */         
/* 526 */         if (entity1 != entity && entity1.boundingBox.a(axisalignedbb)) {
/* 527 */           list.add(entity1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Class oclass, AxisAlignedBB axisalignedbb, List list) {
/* 534 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/* 535 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*     */     
/* 537 */     if (i < 0) {
/* 538 */       i = 0;
/*     */     }
/*     */     
/* 541 */     if (j >= this.entitySlices.length) {
/* 542 */       j = this.entitySlices.length - 1;
/*     */     }
/*     */     
/* 545 */     for (int k = i; k <= j; k++) {
/* 546 */       List list1 = this.entitySlices[k];
/*     */       
/* 548 */       for (int l = 0; l < list1.size(); l++) {
/* 549 */         Entity entity = (Entity)list1.get(l);
/*     */         
/* 551 */         if (oclass.isAssignableFrom(entity.getClass()) && entity.boundingBox.a(axisalignedbb)) {
/* 552 */           list.add(entity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(boolean flag) {
/* 559 */     if (this.p) {
/* 560 */       return false;
/*     */     }
/* 562 */     if (flag) {
/* 563 */       if (this.q && this.world.getTime() != this.r) {
/* 564 */         return true;
/*     */       }
/* 566 */     } else if (this.q && this.world.getTime() >= this.r + 600L) {
/* 567 */       return true;
/*     */     } 
/*     */     
/* 570 */     return this.o;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getData(byte[] abyte, int i, int j, int k, int l, int i1, int j1, int k1) {
/* 575 */     int l1 = l - i;
/* 576 */     int i2 = i1 - j;
/* 577 */     int j2 = j1 - k;
/*     */     
/* 579 */     if (l1 * i2 * j2 == this.b.length) {
/* 580 */       System.arraycopy(this.b, 0, abyte, k1, this.b.length);
/* 581 */       k1 += this.b.length;
/* 582 */       System.arraycopy(this.e.a, 0, abyte, k1, this.e.a.length);
/* 583 */       k1 += this.e.a.length;
/* 584 */       System.arraycopy(this.g.a, 0, abyte, k1, this.g.a.length);
/* 585 */       k1 += this.g.a.length;
/* 586 */       System.arraycopy(this.f.a, 0, abyte, k1, this.f.a.length);
/* 587 */       return this.f.a.length;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     int k2;
/*     */ 
/*     */     
/* 595 */     for (k2 = i; k2 < l; k2++) {
/* 596 */       for (int l2 = k; l2 < j1; l2++) {
/* 597 */         int i3 = k2 << 11 | l2 << 7 | j;
/* 598 */         int j3 = i1 - j;
/* 599 */         System.arraycopy(this.b, i3, abyte, k1, j3);
/* 600 */         k1 += j3;
/*     */       } 
/*     */     } 
/*     */     
/* 604 */     for (k2 = i; k2 < l; k2++) {
/* 605 */       for (int l2 = k; l2 < j1; l2++) {
/* 606 */         int i3 = (k2 << 11 | l2 << 7 | j) >> 1;
/* 607 */         int j3 = (i1 - j) / 2;
/* 608 */         System.arraycopy(this.e.a, i3, abyte, k1, j3);
/* 609 */         k1 += j3;
/*     */       } 
/*     */     } 
/*     */     
/* 613 */     for (k2 = i; k2 < l; k2++) {
/* 614 */       for (int l2 = k; l2 < j1; l2++) {
/* 615 */         int i3 = (k2 << 11 | l2 << 7 | j) >> 1;
/* 616 */         int j3 = (i1 - j) / 2;
/* 617 */         System.arraycopy(this.g.a, i3, abyte, k1, j3);
/* 618 */         k1 += j3;
/*     */       } 
/*     */     } 
/*     */     
/* 622 */     for (k2 = i; k2 < l; k2++) {
/* 623 */       for (int l2 = k; l2 < j1; l2++) {
/* 624 */         int i3 = (k2 << 11 | l2 << 7 | j) >> 1;
/* 625 */         int j3 = (i1 - j) / 2;
/* 626 */         System.arraycopy(this.f.a, i3, abyte, k1, j3);
/* 627 */         k1 += j3;
/*     */       } 
/*     */     } 
/*     */     
/* 631 */     return k1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 636 */   public Random a(long i) { return new Random(this.world.getSeed() + (this.x * this.x * 4987142) + (this.x * 5947611) + (this.z * this.z) * 4392871L + (this.z * 389711) ^ i); }
/*     */ 
/*     */ 
/*     */   
/* 640 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/* 644 */   public void h() { BlockRegister.a(this.b); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Chunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */