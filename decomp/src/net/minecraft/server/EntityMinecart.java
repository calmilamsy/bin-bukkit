/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.vehicle.VehicleCreateEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEnterEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
/*     */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*     */ import org.bukkit.event.vehicle.VehicleUpdateEvent;
/*     */ 
/*     */ public class EntityMinecart
/*     */   extends Entity
/*     */   implements IInventory {
/*     */   private ItemStack[] items;
/*     */   public int damage;
/*     */   public int b;
/*     */   public int c;
/*     */   private boolean i;
/*     */   public int type;
/*     */   public int e;
/*     */   public double f;
/*     */   public double g;
/*  28 */   private static final int[][][] matrix = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
/*     */   
/*     */   private int k;
/*     */   
/*     */   private double l;
/*     */   private double m;
/*     */   private double n;
/*     */   private double o;
/*     */   private double p;
/*     */   public boolean slowWhenEmpty = true;
/*  38 */   public double derailedX = 0.5D;
/*  39 */   public double derailedY = 0.5D;
/*  40 */   public double derailedZ = 0.5D;
/*  41 */   public double flyingX = 0.95D;
/*  42 */   public double flyingY = 0.95D;
/*  43 */   public double flyingZ = 0.95D;
/*  44 */   public double maxSpeed = 0.4D;
/*     */ 
/*     */   
/*  47 */   public ItemStack[] getContents() { return this.items; }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityMinecart(World world) {
/*  52 */     super(world);
/*  53 */     this.items = new ItemStack[27];
/*  54 */     this.damage = 0;
/*  55 */     this.b = 0;
/*  56 */     this.c = 1;
/*  57 */     this.i = false;
/*  58 */     this.aI = true;
/*  59 */     b(0.98F, 0.7F);
/*  60 */     this.height = this.width / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*  64 */   protected boolean n() { return false; }
/*     */ 
/*     */   
/*     */   protected void b() {}
/*     */ 
/*     */   
/*  70 */   public AxisAlignedBB a_(Entity entity) { return entity.boundingBox; }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public AxisAlignedBB e_() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public boolean d_() { return true; }
/*     */ 
/*     */   
/*     */   public EntityMinecart(World world, double d0, double d1, double d2, int i) {
/*  82 */     this(world);
/*  83 */     setPosition(d0, d1 + this.height, d2);
/*  84 */     this.motX = 0.0D;
/*  85 */     this.motY = 0.0D;
/*  86 */     this.motZ = 0.0D;
/*  87 */     this.lastX = d0;
/*  88 */     this.lastY = d1;
/*  89 */     this.lastZ = d2;
/*  90 */     this.type = i;
/*     */     
/*  92 */     this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */ 
/*     */   
/*  96 */   public double m() { return this.width * 0.0D - 0.30000001192092896D; }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 100 */     if (!this.world.isStatic && !this.dead) {
/*     */       
/* 102 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 103 */       Entity passenger = (entity == null) ? null : entity.getBukkitEntity();
/*     */       
/* 105 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, i);
/* 106 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 108 */       if (event.isCancelled()) {
/* 109 */         return true;
/*     */       }
/*     */       
/* 112 */       i = event.getDamage();
/*     */ 
/*     */       
/* 115 */       this.c = -this.c;
/* 116 */       this.b = 10;
/* 117 */       af();
/* 118 */       this.damage += i * 10;
/* 119 */       if (this.damage > 40) {
/* 120 */         if (this.passenger != null) {
/* 121 */           this.passenger.mount(this);
/*     */         }
/*     */ 
/*     */         
/* 125 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
/* 126 */         this.world.getServer().getPluginManager().callEvent(destroyEvent);
/*     */         
/* 128 */         if (destroyEvent.isCancelled()) {
/* 129 */           this.damage = 40;
/* 130 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 134 */         die();
/* 135 */         a(Item.MINECART.id, 1, 0.0F);
/* 136 */         if (this.type == 1) {
/* 137 */           EntityMinecart entityminecart = this;
/*     */           
/* 139 */           for (int j = 0; j < entityminecart.getSize(); j++) {
/* 140 */             ItemStack itemstack = entityminecart.getItem(j);
/*     */             
/* 142 */             if (itemstack != null) {
/* 143 */               float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 144 */               float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 145 */               float f2 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */               
/* 147 */               while (itemstack.count > 0) {
/* 148 */                 int k = this.random.nextInt(21) + 10;
/*     */                 
/* 150 */                 if (k > itemstack.count) {
/* 151 */                   k = itemstack.count;
/*     */                 }
/*     */                 
/* 154 */                 itemstack.count -= k;
/* 155 */                 EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f1, this.locZ + f2, new ItemStack(itemstack.id, k, itemstack.getData()));
/* 156 */                 float f3 = 0.05F;
/*     */                 
/* 158 */                 entityitem.motX = ((float)this.random.nextGaussian() * f3);
/* 159 */                 entityitem.motY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 160 */                 entityitem.motZ = ((float)this.random.nextGaussian() * f3);
/* 161 */                 this.world.addEntity(entityitem);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 166 */           a(Block.CHEST.id, 1, 0.0F);
/* 167 */         } else if (this.type == 2) {
/* 168 */           a(Block.FURNACE.id, 1, 0.0F);
/*     */         } 
/*     */       } 
/*     */       
/* 172 */       return true;
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 179 */   public boolean l_() { return !this.dead; }
/*     */ 
/*     */   
/*     */   public void die() {
/* 183 */     for (int i = 0; i < getSize(); i++) {
/* 184 */       ItemStack itemstack = getItem(i);
/*     */       
/* 186 */       if (itemstack != null) {
/* 187 */         float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 188 */         float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 189 */         float f2 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */         
/* 191 */         while (itemstack.count > 0) {
/* 192 */           int j = this.random.nextInt(21) + 10;
/*     */           
/* 194 */           if (j > itemstack.count) {
/* 195 */             j = itemstack.count;
/*     */           }
/*     */           
/* 198 */           itemstack.count -= j;
/* 199 */           EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f1, this.locZ + f2, new ItemStack(itemstack.id, j, itemstack.getData()));
/* 200 */           float f3 = 0.05F;
/*     */           
/* 202 */           entityitem.motX = ((float)this.random.nextGaussian() * f3);
/* 203 */           entityitem.motY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 204 */           entityitem.motZ = ((float)this.random.nextGaussian() * f3);
/* 205 */           this.world.addEntity(entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     super.die();
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_() {
/* 215 */     double prevX = this.locX;
/* 216 */     double prevY = this.locY;
/* 217 */     double prevZ = this.locZ;
/* 218 */     float prevYaw = this.yaw;
/* 219 */     float prevPitch = this.pitch;
/*     */ 
/*     */     
/* 222 */     if (this.b > 0) {
/* 223 */       this.b--;
/*     */     }
/*     */     
/* 226 */     if (this.damage > 0) {
/* 227 */       this.damage--;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (this.world.isStatic && this.k > 0) {
/* 233 */       if (this.k > 0) {
/* 234 */         double d1 = this.locX + (this.l - this.locX) / this.k;
/* 235 */         double d2 = this.locY + (this.m - this.locY) / this.k;
/* 236 */         double d3 = this.locZ + (this.n - this.locZ) / this.k;
/*     */         double d0;
/* 238 */         for (d0 = this.o - this.yaw; d0 < -180.0D; d0 += 360.0D);
/*     */ 
/*     */ 
/*     */         
/* 242 */         while (d0 >= 180.0D) {
/* 243 */           d0 -= 360.0D;
/*     */         }
/*     */         
/* 246 */         this.yaw = (float)(this.yaw + d0 / this.k);
/* 247 */         this.pitch = (float)(this.pitch + (this.p - this.pitch) / this.k);
/* 248 */         this.k--;
/* 249 */         setPosition(d1, d2, d3);
/* 250 */         c(this.yaw, this.pitch);
/*     */       } else {
/* 252 */         setPosition(this.locX, this.locY, this.locZ);
/* 253 */         c(this.yaw, this.pitch);
/*     */       } 
/*     */     } else {
/* 256 */       this.lastX = this.locX;
/* 257 */       this.lastY = this.locY;
/* 258 */       this.lastZ = this.locZ;
/* 259 */       this.motY -= 0.03999999910593033D;
/* 260 */       int i = MathHelper.floor(this.locX);
/* 261 */       int j = MathHelper.floor(this.locY);
/* 262 */       int k = MathHelper.floor(this.locZ);
/*     */       
/* 264 */       if (BlockMinecartTrack.g(this.world, i, j - 1, k)) {
/* 265 */         j--;
/*     */       }
/*     */ 
/*     */       
/* 269 */       double d4 = this.maxSpeed;
/* 270 */       boolean flag = false;
/*     */       
/* 272 */       double d0 = 0.0078125D;
/* 273 */       int l = this.world.getTypeId(i, j, k);
/*     */       
/* 275 */       if (BlockMinecartTrack.c(l)) {
/* 276 */         Vec3D vec3d = h(this.locX, this.locY, this.locZ);
/* 277 */         int i1 = this.world.getData(i, j, k);
/*     */         
/* 279 */         this.locY = j;
/* 280 */         boolean flag1 = false;
/* 281 */         boolean flag2 = false;
/*     */         
/* 283 */         if (l == Block.GOLDEN_RAIL.id) {
/* 284 */           flag1 = ((i1 & 0x8) != 0);
/* 285 */           flag2 = !flag1;
/*     */         } 
/*     */         
/* 288 */         if (((BlockMinecartTrack)Block.byId[l]).f()) {
/* 289 */           i1 &= 0x7;
/*     */         }
/*     */         
/* 292 */         if (i1 >= 2 && i1 <= 5) {
/* 293 */           this.locY = (j + 1);
/*     */         }
/*     */         
/* 296 */         if (i1 == 2) {
/* 297 */           this.motX -= d0;
/*     */         }
/*     */         
/* 300 */         if (i1 == 3) {
/* 301 */           this.motX += d0;
/*     */         }
/*     */         
/* 304 */         if (i1 == 4) {
/* 305 */           this.motZ += d0;
/*     */         }
/*     */         
/* 308 */         if (i1 == 5) {
/* 309 */           this.motZ -= d0;
/*     */         }
/*     */         
/* 312 */         int[][] aint = matrix[i1];
/* 313 */         double d5 = (aint[1][0] - aint[0][0]);
/* 314 */         double d6 = (aint[1][2] - aint[0][2]);
/* 315 */         double d7 = Math.sqrt(d5 * d5 + d6 * d6);
/* 316 */         double d8 = this.motX * d5 + this.motZ * d6;
/*     */         
/* 318 */         if (d8 < 0.0D) {
/* 319 */           d5 = -d5;
/* 320 */           d6 = -d6;
/*     */         } 
/*     */         
/* 323 */         double d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */         
/* 325 */         this.motX = d9 * d5 / d7;
/* 326 */         this.motZ = d9 * d6 / d7;
/*     */ 
/*     */         
/* 329 */         if (flag2) {
/* 330 */           double d10 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 331 */           if (d10 < 0.03D) {
/* 332 */             this.motX *= 0.0D;
/* 333 */             this.motY *= 0.0D;
/* 334 */             this.motZ *= 0.0D;
/*     */           } else {
/* 336 */             this.motX *= 0.5D;
/* 337 */             this.motY *= 0.0D;
/* 338 */             this.motZ *= 0.5D;
/*     */           } 
/*     */         } 
/*     */         
/* 342 */         double d10 = 0.0D;
/* 343 */         double d11 = i + 0.5D + aint[0][0] * 0.5D;
/* 344 */         double d12 = k + 0.5D + aint[0][2] * 0.5D;
/* 345 */         double d13 = i + 0.5D + aint[1][0] * 0.5D;
/* 346 */         double d14 = k + 0.5D + aint[1][2] * 0.5D;
/*     */         
/* 348 */         d5 = d13 - d11;
/* 349 */         d6 = d14 - d12;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 354 */         if (d5 == 0.0D) {
/* 355 */           this.locX = i + 0.5D;
/* 356 */           d10 = this.locZ - k;
/* 357 */         } else if (d6 == 0.0D) {
/* 358 */           this.locZ = k + 0.5D;
/* 359 */           d10 = this.locX - i;
/*     */         } else {
/* 361 */           double d16 = this.locX - d11;
/* 362 */           double d15 = this.locZ - d12;
/* 363 */           double d17 = (d16 * d5 + d15 * d6) * 2.0D;
/* 364 */           d10 = d17;
/*     */         } 
/*     */         
/* 367 */         this.locX = d11 + d5 * d10;
/* 368 */         this.locZ = d12 + d6 * d10;
/* 369 */         setPosition(this.locX, this.locY + this.height, this.locZ);
/* 370 */         double d16 = this.motX;
/* 371 */         double d15 = this.motZ;
/* 372 */         if (this.passenger != null) {
/* 373 */           d16 *= 0.75D;
/* 374 */           d15 *= 0.75D;
/*     */         } 
/*     */         
/* 377 */         if (d16 < -d4) {
/* 378 */           d16 = -d4;
/*     */         }
/*     */         
/* 381 */         if (d16 > d4) {
/* 382 */           d16 = d4;
/*     */         }
/*     */         
/* 385 */         if (d15 < -d4) {
/* 386 */           d15 = -d4;
/*     */         }
/*     */         
/* 389 */         if (d15 > d4) {
/* 390 */           d15 = d4;
/*     */         }
/*     */         
/* 393 */         move(d16, 0.0D, d15);
/* 394 */         if (aint[0][1] != 0 && MathHelper.floor(this.locX) - i == aint[0][0] && MathHelper.floor(this.locZ) - k == aint[0][2]) {
/* 395 */           setPosition(this.locX, this.locY + aint[0][1], this.locZ);
/* 396 */         } else if (aint[1][1] != 0 && MathHelper.floor(this.locX) - i == aint[1][0] && MathHelper.floor(this.locZ) - k == aint[1][2]) {
/* 397 */           setPosition(this.locX, this.locY + aint[1][1], this.locZ);
/*     */         } 
/*     */ 
/*     */         
/* 401 */         if (this.passenger != null || !this.slowWhenEmpty) {
/* 402 */           this.motX *= 0.996999979019165D;
/* 403 */           this.motY *= 0.0D;
/* 404 */           this.motZ *= 0.996999979019165D;
/*     */         } else {
/* 406 */           if (this.type == 2) {
/* 407 */             double d17 = MathHelper.a(this.f * this.f + this.g * this.g);
/* 408 */             if (d17 > 0.01D) {
/* 409 */               flag = true;
/* 410 */               this.f /= d17;
/* 411 */               this.g /= d17;
/* 412 */               double d18 = 0.04D;
/*     */               
/* 414 */               this.motX *= 0.800000011920929D;
/* 415 */               this.motY *= 0.0D;
/* 416 */               this.motZ *= 0.800000011920929D;
/* 417 */               this.motX += this.f * d18;
/* 418 */               this.motZ += this.g * d18;
/*     */             } else {
/* 420 */               this.motX *= 0.8999999761581421D;
/* 421 */               this.motY *= 0.0D;
/* 422 */               this.motZ *= 0.8999999761581421D;
/*     */             } 
/*     */           } 
/*     */           
/* 426 */           this.motX *= 0.9599999785423279D;
/* 427 */           this.motY *= 0.0D;
/* 428 */           this.motZ *= 0.9599999785423279D;
/*     */         } 
/*     */         
/* 431 */         Vec3D vec3d1 = h(this.locX, this.locY, this.locZ);
/*     */         
/* 433 */         if (vec3d1 != null && vec3d != null) {
/* 434 */           double d19 = (vec3d.b - vec3d1.b) * 0.05D;
/*     */           
/* 436 */           d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 437 */           if (d9 > 0.0D) {
/* 438 */             this.motX = this.motX / d9 * (d9 + d19);
/* 439 */             this.motZ = this.motZ / d9 * (d9 + d19);
/*     */           } 
/*     */           
/* 442 */           setPosition(this.locX, vec3d1.b, this.locZ);
/*     */         } 
/*     */         
/* 445 */         int j1 = MathHelper.floor(this.locX);
/* 446 */         int k1 = MathHelper.floor(this.locZ);
/*     */         
/* 448 */         if (j1 != i || k1 != k) {
/* 449 */           d9 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 450 */           this.motX = d9 * (j1 - i);
/* 451 */           this.motZ = d9 * (k1 - k);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 456 */         if (this.type == 2) {
/* 457 */           double d20 = MathHelper.a(this.f * this.f + this.g * this.g);
/* 458 */           if (d20 > 0.01D && this.motX * this.motX + this.motZ * this.motZ > 0.001D) {
/* 459 */             this.f /= d20;
/* 460 */             this.g /= d20;
/* 461 */             if (this.f * this.motX + this.g * this.motZ < 0.0D) {
/* 462 */               this.f = 0.0D;
/* 463 */               this.g = 0.0D;
/*     */             } else {
/* 465 */               this.f = this.motX;
/* 466 */               this.g = this.motZ;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 471 */         if (flag1) {
/* 472 */           double d20 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 473 */           if (d20 > 0.01D) {
/* 474 */             double d21 = 0.06D;
/*     */             
/* 476 */             this.motX += this.motX / d20 * d21;
/* 477 */             this.motZ += this.motZ / d20 * d21;
/* 478 */           } else if (i1 == 1) {
/* 479 */             if (this.world.e(i - 1, j, k)) {
/* 480 */               this.motX = 0.02D;
/* 481 */             } else if (this.world.e(i + 1, j, k)) {
/* 482 */               this.motX = -0.02D;
/*     */             } 
/* 484 */           } else if (i1 == 0) {
/* 485 */             if (this.world.e(i, j, k - 1)) {
/* 486 */               this.motZ = 0.02D;
/* 487 */             } else if (this.world.e(i, j, k + 1)) {
/* 488 */               this.motZ = -0.02D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 493 */         if (this.motX < -d4) {
/* 494 */           this.motX = -d4;
/*     */         }
/*     */         
/* 497 */         if (this.motX > d4) {
/* 498 */           this.motX = d4;
/*     */         }
/*     */         
/* 501 */         if (this.motZ < -d4) {
/* 502 */           this.motZ = -d4;
/*     */         }
/*     */         
/* 505 */         if (this.motZ > d4) {
/* 506 */           this.motZ = d4;
/*     */         }
/*     */         
/* 509 */         if (this.onGround) {
/*     */           
/* 511 */           this.motX *= this.derailedX;
/* 512 */           this.motY *= this.derailedY;
/* 513 */           this.motZ *= this.derailedZ;
/*     */         } 
/*     */ 
/*     */         
/* 517 */         move(this.motX, this.motY, this.motZ);
/* 518 */         if (!this.onGround) {
/*     */           
/* 520 */           this.motX *= this.flyingX;
/* 521 */           this.motY *= this.flyingY;
/* 522 */           this.motZ *= this.flyingZ;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 527 */       this.pitch = 0.0F;
/* 528 */       double d22 = this.lastX - this.locX;
/* 529 */       double d23 = this.lastZ - this.locZ;
/*     */       
/* 531 */       if (d22 * d22 + d23 * d23 > 0.001D) {
/* 532 */         this.yaw = (float)(Math.atan2(d23, d22) * 180.0D / Math.PI);
/* 533 */         if (this.i) {
/* 534 */           this.yaw += 180.0F;
/*     */         }
/*     */       } 
/*     */       
/*     */       double d24;
/*     */       
/* 540 */       for (d24 = (this.yaw - this.lastYaw); d24 >= 180.0D; d24 -= 360.0D);
/*     */ 
/*     */ 
/*     */       
/* 544 */       while (d24 < -180.0D) {
/* 545 */         d24 += 360.0D;
/*     */       }
/*     */       
/* 548 */       if (d24 < -170.0D || d24 >= 170.0D) {
/* 549 */         this.yaw += 180.0F;
/* 550 */         this.i = !this.i;
/*     */       } 
/*     */       
/* 553 */       c(this.yaw, this.pitch);
/*     */ 
/*     */       
/* 556 */       CraftWorld craftWorld = this.world.getWorld();
/* 557 */       Location from = new Location(craftWorld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 558 */       Location to = new Location(craftWorld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 559 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 561 */       this.world.getServer().getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
/*     */       
/* 563 */       if (!from.equals(to)) {
/* 564 */         this.world.getServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, from, to));
/*     */       }
/*     */ 
/*     */       
/* 568 */       List list = this.world.b(this, this.boundingBox.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */       
/* 570 */       if (list != null && list.size() > 0) {
/* 571 */         for (int l1 = 0; l1 < list.size(); l1++) {
/* 572 */           Entity entity = (Entity)list.get(l1);
/*     */           
/* 574 */           if (entity != this.passenger && entity.d_() && entity instanceof EntityMinecart) {
/* 575 */             entity.collide(this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 580 */       if (this.passenger != null && this.passenger.dead) {
/* 581 */         this.passenger.vehicle = null;
/* 582 */         this.passenger = null;
/*     */       } 
/*     */       
/* 585 */       if (flag && this.random.nextInt(4) == 0) {
/* 586 */         this.e--;
/* 587 */         if (this.e < 0) {
/* 588 */           this.f = this.g = 0.0D;
/*     */         }
/*     */         
/* 591 */         this.world.a("largesmoke", this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vec3D h(double d0, double d1, double d2) {
/* 597 */     int i = MathHelper.floor(d0);
/* 598 */     int j = MathHelper.floor(d1);
/* 599 */     int k = MathHelper.floor(d2);
/*     */     
/* 601 */     if (BlockMinecartTrack.g(this.world, i, j - 1, k)) {
/* 602 */       j--;
/*     */     }
/*     */     
/* 605 */     int l = this.world.getTypeId(i, j, k);
/*     */     
/* 607 */     if (BlockMinecartTrack.c(l)) {
/* 608 */       int i1 = this.world.getData(i, j, k);
/*     */       
/* 610 */       d1 = j;
/* 611 */       if (((BlockMinecartTrack)Block.byId[l]).f()) {
/* 612 */         i1 &= 0x7;
/*     */       }
/*     */       
/* 615 */       if (i1 >= 2 && i1 <= 5) {
/* 616 */         d1 = (j + 1);
/*     */       }
/*     */       
/* 619 */       int[][] aint = matrix[i1];
/* 620 */       double d3 = 0.0D;
/* 621 */       double d4 = i + 0.5D + aint[0][0] * 0.5D;
/* 622 */       double d5 = j + 0.5D + aint[0][1] * 0.5D;
/* 623 */       double d6 = k + 0.5D + aint[0][2] * 0.5D;
/* 624 */       double d7 = i + 0.5D + aint[1][0] * 0.5D;
/* 625 */       double d8 = j + 0.5D + aint[1][1] * 0.5D;
/* 626 */       double d9 = k + 0.5D + aint[1][2] * 0.5D;
/* 627 */       double d10 = d7 - d4;
/* 628 */       double d11 = (d8 - d5) * 2.0D;
/* 629 */       double d12 = d9 - d6;
/*     */       
/* 631 */       if (d10 == 0.0D) {
/* 632 */         d0 = i + 0.5D;
/* 633 */         d3 = d2 - k;
/* 634 */       } else if (d12 == 0.0D) {
/* 635 */         d2 = k + 0.5D;
/* 636 */         d3 = d0 - i;
/*     */       } else {
/* 638 */         double d13 = d0 - d4;
/* 639 */         double d14 = d2 - d6;
/* 640 */         double d15 = (d13 * d10 + d14 * d12) * 2.0D;
/*     */         
/* 642 */         d3 = d15;
/*     */       } 
/*     */       
/* 645 */       d0 = d4 + d10 * d3;
/* 646 */       d1 = d5 + d11 * d3;
/* 647 */       d2 = d6 + d12 * d3;
/* 648 */       if (d11 < 0.0D) {
/* 649 */         d1++;
/*     */       }
/*     */       
/* 652 */       if (d11 > 0.0D) {
/* 653 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 656 */       return Vec3D.create(d0, d1, d2);
/*     */     } 
/* 658 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 663 */     nbttagcompound.a("Type", this.type);
/* 664 */     if (this.type == 2) {
/* 665 */       nbttagcompound.a("PushX", this.f);
/* 666 */       nbttagcompound.a("PushZ", this.g);
/* 667 */       nbttagcompound.a("Fuel", (short)this.e);
/* 668 */     } else if (this.type == 1) {
/* 669 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 671 */       for (int i = 0; i < this.items.length; i++) {
/* 672 */         if (this.items[i] != null) {
/* 673 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */           
/* 675 */           nbttagcompound1.a("Slot", (byte)i);
/* 676 */           this.items[i].a(nbttagcompound1);
/* 677 */           nbttaglist.a(nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */       
/* 681 */       nbttagcompound.a("Items", nbttaglist);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 686 */     this.type = nbttagcompound.e("Type");
/* 687 */     if (this.type == 2) {
/* 688 */       this.f = nbttagcompound.h("PushX");
/* 689 */       this.g = nbttagcompound.h("PushZ");
/* 690 */       this.e = nbttagcompound.d("Fuel");
/* 691 */     } else if (this.type == 1) {
/* 692 */       NBTTagList nbttaglist = nbttagcompound.l("Items");
/*     */       
/* 694 */       this.items = new ItemStack[getSize()];
/*     */       
/* 696 */       for (int i = 0; i < nbttaglist.c(); i++) {
/* 697 */         NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
/* 698 */         int j = nbttagcompound1.c("Slot") & 0xFF;
/*     */         
/* 700 */         if (j >= 0 && j < this.items.length) {
/* 701 */           this.items[j] = new ItemStack(nbttagcompound1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void collide(Entity entity) {
/* 708 */     if (!this.world.isStatic && 
/* 709 */       entity != this.passenger) {
/*     */       
/* 711 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 712 */       Entity hitEntity = (entity == null) ? null : entity.getBukkitEntity();
/*     */       
/* 714 */       VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
/* 715 */       this.world.getServer().getPluginManager().callEvent(collisionEvent);
/*     */       
/* 717 */       if (collisionEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/* 721 */       if (entity instanceof EntityLiving && !(entity instanceof EntityHuman) && this.type == 0 && this.motX * this.motX + this.motZ * this.motZ > 0.01D && this.passenger == null && entity.vehicle == null && 
/* 722 */         !collisionEvent.isPickupCancelled()) {
/* 723 */         VehicleEnterEvent enterEvent = new VehicleEnterEvent(vehicle, hitEntity);
/* 724 */         this.world.getServer().getPluginManager().callEvent(enterEvent);
/*     */         
/* 726 */         if (!enterEvent.isCancelled()) {
/* 727 */           entity.mount(this);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 733 */       double d0 = entity.locX - this.locX;
/* 734 */       double d1 = entity.locZ - this.locZ;
/* 735 */       double d2 = d0 * d0 + d1 * d1;
/*     */ 
/*     */       
/* 738 */       if (d2 >= 9.999999747378752E-5D && !collisionEvent.isCollisionCancelled()) {
/* 739 */         d2 = MathHelper.a(d2);
/* 740 */         d0 /= d2;
/* 741 */         d1 /= d2;
/* 742 */         double d3 = 1.0D / d2;
/*     */         
/* 744 */         if (d3 > 1.0D) {
/* 745 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 748 */         d0 *= d3;
/* 749 */         d1 *= d3;
/* 750 */         d0 *= 0.10000000149011612D;
/* 751 */         d1 *= 0.10000000149011612D;
/* 752 */         d0 *= (1.0F - this.bu);
/* 753 */         d1 *= (1.0F - this.bu);
/* 754 */         d0 *= 0.5D;
/* 755 */         d1 *= 0.5D;
/* 756 */         if (entity instanceof EntityMinecart) {
/* 757 */           double d4 = entity.locX - this.locX;
/* 758 */           double d5 = entity.locZ - this.locZ;
/* 759 */           double d6 = d4 * entity.motZ + d5 * entity.lastX;
/*     */           
/* 761 */           d6 *= d6;
/* 762 */           if (d6 > 5.0D) {
/*     */             return;
/*     */           }
/*     */           
/* 766 */           double d7 = entity.motX + this.motX;
/* 767 */           double d8 = entity.motZ + this.motZ;
/*     */           
/* 769 */           if (((EntityMinecart)entity).type == 2 && this.type != 2) {
/* 770 */             this.motX *= 0.20000000298023224D;
/* 771 */             this.motZ *= 0.20000000298023224D;
/* 772 */             b(entity.motX - d0, 0.0D, entity.motZ - d1);
/* 773 */             entity.motX *= 0.699999988079071D;
/* 774 */             entity.motZ *= 0.699999988079071D;
/* 775 */           } else if (((EntityMinecart)entity).type != 2 && this.type == 2) {
/* 776 */             entity.motX *= 0.20000000298023224D;
/* 777 */             entity.motZ *= 0.20000000298023224D;
/* 778 */             entity.b(this.motX + d0, 0.0D, this.motZ + d1);
/* 779 */             this.motX *= 0.699999988079071D;
/* 780 */             this.motZ *= 0.699999988079071D;
/*     */           } else {
/* 782 */             d7 /= 2.0D;
/* 783 */             d8 /= 2.0D;
/* 784 */             this.motX *= 0.20000000298023224D;
/* 785 */             this.motZ *= 0.20000000298023224D;
/* 786 */             b(d7 - d0, 0.0D, d8 - d1);
/* 787 */             entity.motX *= 0.20000000298023224D;
/* 788 */             entity.motZ *= 0.20000000298023224D;
/* 789 */             entity.b(d7 + d0, 0.0D, d8 + d1);
/*     */           } 
/*     */         } else {
/* 792 */           b(-d0, 0.0D, -d1);
/* 793 */           entity.b(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 801 */   public int getSize() { return 27; }
/*     */ 
/*     */ 
/*     */   
/* 805 */   public ItemStack getItem(int i) { return this.items[i]; }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 809 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/* 812 */       if ((this.items[i]).count <= j) {
/* 813 */         ItemStack itemstack = this.items[i];
/* 814 */         this.items[i] = null;
/* 815 */         return itemstack;
/*     */       } 
/* 817 */       ItemStack itemstack = this.items[i].a(j);
/* 818 */       if ((this.items[i]).count == 0) {
/* 819 */         this.items[i] = null;
/*     */       }
/*     */       
/* 822 */       return itemstack;
/*     */     } 
/*     */     
/* 825 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 830 */     this.items[i] = itemstack;
/* 831 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/* 832 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 837 */   public String getName() { return "Minecart"; }
/*     */ 
/*     */ 
/*     */   
/* 841 */   public int getMaxStackSize() { return 64; }
/*     */ 
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 847 */     if (this.type == 0) {
/* 848 */       if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityhuman) {
/* 849 */         return true;
/*     */       }
/*     */       
/* 852 */       if (!this.world.isStatic) {
/*     */         
/* 854 */         Entity player = (entityhuman == null) ? null : entityhuman.getBukkitEntity();
/*     */         
/* 856 */         VehicleEnterEvent event = new VehicleEnterEvent((Vehicle)getBukkitEntity(), player);
/* 857 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 859 */         if (event.isCancelled()) {
/* 860 */           return true;
/*     */         }
/*     */ 
/*     */         
/* 864 */         entityhuman.mount(this);
/*     */       } 
/* 866 */     } else if (this.type == 1) {
/* 867 */       if (!this.world.isStatic) {
/* 868 */         entityhuman.a(this);
/*     */       }
/* 870 */     } else if (this.type == 2) {
/* 871 */       ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */       
/* 873 */       if (itemstack != null && itemstack.id == Item.COAL.id) {
/* 874 */         if (--itemstack.count == 0) {
/* 875 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */         }
/*     */         
/* 878 */         this.e += 1200;
/*     */       } 
/*     */       
/* 881 */       this.f = this.locX - entityhuman.locX;
/* 882 */       this.g = this.locZ - entityhuman.locZ;
/*     */     } 
/*     */     
/* 885 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 889 */   public boolean a_(EntityHuman entityhuman) { return this.dead ? false : ((entityhuman.g(this) <= 64.0D)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityMinecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */