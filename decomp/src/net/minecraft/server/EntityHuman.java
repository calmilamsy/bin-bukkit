/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.TrigMath;
/*     */ import org.bukkit.craftbukkit.entity.CraftItem;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.player.PlayerBedEnterEvent;
/*     */ import org.bukkit.event.player.PlayerBedLeaveEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class EntityHuman
/*     */   extends EntityLiving {
/*  22 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*     */   public Container defaultContainer;
/*     */   public Container activeContainer;
/*  25 */   public byte l = 0;
/*  26 */   public int m = 0;
/*     */   public float n;
/*     */   public float o;
/*     */   public boolean p = false;
/*  30 */   public int q = 0;
/*     */   
/*     */   public String name;
/*     */   public int dimension;
/*     */   public double t;
/*     */   public double u;
/*     */   public double v;
/*     */   public double w;
/*     */   public double x;
/*     */   public double y;
/*     */   public boolean sleeping;
/*     */   public boolean fauxSleeping;
/*  42 */   public String spawnWorld = "";
/*     */   
/*     */   public ChunkCoordinates A;
/*     */   public int sleepTicks;
/*     */   public float B;
/*     */   public float C;
/*     */   private ChunkCoordinates b;
/*     */   private ChunkCoordinates c;
/*  50 */   public int D = 20;
/*     */   protected boolean E = false;
/*     */   public float F;
/*  53 */   private int d = 0;
/*  54 */   public EntityFish hookedFish = null;
/*     */   
/*     */   public EntityHuman(World world) {
/*  57 */     super(world);
/*  58 */     this.defaultContainer = new ContainerPlayer(this.inventory, !world.isStatic ? 1 : 0);
/*  59 */     this.activeContainer = this.defaultContainer;
/*  60 */     this.height = 1.62F;
/*  61 */     ChunkCoordinates chunkcoordinates = world.getSpawn();
/*     */     
/*  63 */     setPositionRotation(chunkcoordinates.x + 0.5D, (chunkcoordinates.y + 1), chunkcoordinates.z + 0.5D, 0.0F, 0.0F);
/*  64 */     this.health = 20;
/*  65 */     this.U = "humanoid";
/*  66 */     this.T = 180.0F;
/*  67 */     this.maxFireTicks = 20;
/*  68 */     this.texture = "/mob/char.png";
/*     */   }
/*     */   
/*     */   protected void b() {
/*  72 */     super.b();
/*  73 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void m_() {
/*  77 */     if (isSleeping()) {
/*  78 */       this.sleepTicks++;
/*  79 */       if (this.sleepTicks > 100) {
/*  80 */         this.sleepTicks = 100;
/*     */       }
/*     */       
/*  83 */       if (!this.world.isStatic) {
/*  84 */         if (!o()) {
/*  85 */           a(true, true, false);
/*  86 */         } else if (this.world.d()) {
/*  87 */           a(false, true, true);
/*     */         } 
/*     */       }
/*  90 */     } else if (this.sleepTicks > 0) {
/*  91 */       this.sleepTicks++;
/*  92 */       if (this.sleepTicks >= 110) {
/*  93 */         this.sleepTicks = 0;
/*     */       }
/*     */     } 
/*     */     
/*  97 */     super.m_();
/*  98 */     if (!this.world.isStatic && this.activeContainer != null && !this.activeContainer.b(this)) {
/*  99 */       y();
/* 100 */       this.activeContainer = this.defaultContainer;
/*     */     } 
/*     */     
/* 103 */     this.t = this.w;
/* 104 */     this.u = this.x;
/* 105 */     this.v = this.y;
/* 106 */     double d0 = this.locX - this.w;
/* 107 */     double d1 = this.locY - this.x;
/* 108 */     double d2 = this.locZ - this.y;
/* 109 */     double d3 = 10.0D;
/*     */     
/* 111 */     if (d0 > d3) {
/* 112 */       this.t = this.w = this.locX;
/*     */     }
/*     */     
/* 115 */     if (d2 > d3) {
/* 116 */       this.v = this.y = this.locZ;
/*     */     }
/*     */     
/* 119 */     if (d1 > d3) {
/* 120 */       this.u = this.x = this.locY;
/*     */     }
/*     */     
/* 123 */     if (d0 < -d3) {
/* 124 */       this.t = this.w = this.locX;
/*     */     }
/*     */     
/* 127 */     if (d2 < -d3) {
/* 128 */       this.v = this.y = this.locZ;
/*     */     }
/*     */     
/* 131 */     if (d1 < -d3) {
/* 132 */       this.u = this.x = this.locY;
/*     */     }
/*     */     
/* 135 */     this.w += d0 * 0.25D;
/* 136 */     this.y += d2 * 0.25D;
/* 137 */     this.x += d1 * 0.25D;
/* 138 */     a(StatisticList.k, 1);
/* 139 */     if (this.vehicle == null) {
/* 140 */       this.c = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 145 */   protected boolean D() { return (this.health <= 0 || isSleeping()); }
/*     */ 
/*     */ 
/*     */   
/* 149 */   protected void y() { this.activeContainer = this.defaultContainer; }
/*     */ 
/*     */   
/*     */   public void E() {
/* 153 */     double d0 = this.locX;
/* 154 */     double d1 = this.locY;
/* 155 */     double d2 = this.locZ;
/*     */     
/* 157 */     super.E();
/* 158 */     this.n = this.o;
/* 159 */     this.o = 0.0F;
/* 160 */     i(this.locX - d0, this.locY - d1, this.locZ - d2);
/*     */   }
/*     */   
/*     */   protected void c_() {
/* 164 */     if (this.p) {
/* 165 */       this.q++;
/* 166 */       if (this.q >= 8) {
/* 167 */         this.q = 0;
/* 168 */         this.p = false;
/*     */       } 
/*     */     } else {
/* 171 */       this.q = 0;
/*     */     } 
/*     */     
/* 174 */     this.aa = this.q / 8.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void v() {
/* 179 */     if (!this.world.allowMonsters && this.health < 20 && this.ticksLived % 20 * 12 == 0) {
/* 180 */       b(1, EntityRegainHealthEvent.RegainReason.REGEN);
/*     */     }
/*     */     
/* 183 */     this.inventory.f();
/* 184 */     this.n = this.o;
/* 185 */     super.v();
/* 186 */     float f = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 188 */     float f1 = (float)TrigMath.atan(-this.motY * 0.20000000298023224D) * 15.0F;
/*     */     
/* 190 */     if (f > 0.1F) {
/* 191 */       f = 0.1F;
/*     */     }
/*     */     
/* 194 */     if (!this.onGround || this.health <= 0) {
/* 195 */       f = 0.0F;
/*     */     }
/*     */     
/* 198 */     if (this.onGround || this.health <= 0) {
/* 199 */       f1 = 0.0F;
/*     */     }
/*     */     
/* 202 */     this.o += (f - this.o) * 0.4F;
/* 203 */     this.aj += (f1 - this.aj) * 0.8F;
/* 204 */     if (this.health > 0) {
/* 205 */       List list = this.world.b(this, this.boundingBox.b(1.0D, 0.0D, 1.0D));
/*     */       
/* 207 */       if (list != null) {
/* 208 */         for (int i = 0; i < list.size(); i++) {
/* 209 */           Entity entity = (Entity)list.get(i);
/*     */           
/* 211 */           if (!entity.dead) {
/* 212 */             i(entity);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 220 */   private void i(Entity entity) { entity.b(this); }
/*     */ 
/*     */   
/*     */   public void die(Entity entity) {
/* 224 */     super.die(entity);
/* 225 */     b(0.2F, 0.2F);
/* 226 */     setPosition(this.locX, this.locY, this.locZ);
/* 227 */     this.motY = 0.10000000149011612D;
/* 228 */     if (this.name.equals("Notch")) {
/* 229 */       a(new ItemStack(Item.APPLE, true), true);
/*     */     }
/*     */     
/* 232 */     this.inventory.h();
/* 233 */     if (entity != null) {
/* 234 */       this.motX = (-MathHelper.cos((this.af + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/* 235 */       this.motZ = (-MathHelper.sin((this.af + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/*     */     } else {
/* 237 */       this.motX = this.motZ = 0.0D;
/*     */     } 
/*     */     
/* 240 */     this.height = 0.1F;
/* 241 */     a(StatisticList.y, 1);
/*     */   }
/*     */   
/*     */   public void c(Entity entity, int i) {
/* 245 */     this.m += i;
/* 246 */     if (entity instanceof EntityHuman) {
/* 247 */       a(StatisticList.A, 1);
/*     */     } else {
/* 249 */       a(StatisticList.z, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 254 */   public void F() { a(this.inventory.splitStack(this.inventory.itemInHandIndex, 1), false); }
/*     */ 
/*     */ 
/*     */   
/* 258 */   public void b(ItemStack itemstack) { a(itemstack, false); }
/*     */ 
/*     */   
/*     */   public void a(ItemStack itemstack, boolean flag) {
/* 262 */     if (itemstack != null) {
/* 263 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY - 0.30000001192092896D + t(), this.locZ, itemstack);
/*     */       
/* 265 */       entityitem.pickupDelay = 40;
/* 266 */       float f = 0.1F;
/*     */ 
/*     */       
/* 269 */       if (flag) {
/* 270 */         float f1 = this.random.nextFloat() * 0.5F;
/* 271 */         float f2 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*     */         
/* 273 */         entityitem.motX = (-MathHelper.sin(f2) * f1);
/* 274 */         entityitem.motZ = (MathHelper.cos(f2) * f1);
/* 275 */         entityitem.motY = 0.20000000298023224D;
/*     */       } else {
/* 277 */         f = 0.3F;
/* 278 */         entityitem.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/* 279 */         entityitem.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/* 280 */         entityitem.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f + 0.1F);
/* 281 */         f = 0.02F;
/* 282 */         float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
/* 283 */         f *= this.random.nextFloat();
/* 284 */         entityitem.motX += Math.cos(f1) * f;
/* 285 */         entityitem.motY += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/* 286 */         entityitem.motZ += Math.sin(f1) * f;
/*     */       } 
/*     */ 
/*     */       
/* 290 */       Player player = (Player)getBukkitEntity();
/* 291 */       CraftItem drop = new CraftItem(this.world.getServer(), entityitem);
/*     */       
/* 293 */       PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
/* 294 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 296 */       if (event.isCancelled()) {
/* 297 */         player.getInventory().addItem(new ItemStack[] { drop.getItemStack() });
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 302 */       a(entityitem);
/* 303 */       a(StatisticList.v, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 308 */   protected void a(EntityItem entityitem) { this.world.addEntity(entityitem); }
/*     */ 
/*     */   
/*     */   public float a(Block block) {
/* 312 */     float f = this.inventory.a(block);
/*     */     
/* 314 */     if (a(Material.WATER)) {
/* 315 */       f /= 5.0F;
/*     */     }
/*     */     
/* 318 */     if (!this.onGround) {
/* 319 */       f /= 5.0F;
/*     */     }
/*     */     
/* 322 */     return f;
/*     */   }
/*     */ 
/*     */   
/* 326 */   public boolean b(Block block) { return this.inventory.b(block); }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 330 */     super.a(nbttagcompound);
/* 331 */     NBTTagList nbttaglist = nbttagcompound.l("Inventory");
/*     */     
/* 333 */     this.inventory.b(nbttaglist);
/* 334 */     this.dimension = nbttagcompound.e("Dimension");
/* 335 */     this.sleeping = nbttagcompound.m("Sleeping");
/* 336 */     this.sleepTicks = nbttagcompound.d("SleepTimer");
/* 337 */     if (this.sleeping) {
/* 338 */       this.A = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/* 339 */       a(true, true, false);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     this.spawnWorld = nbttagcompound.getString("SpawnWorld");
/* 344 */     if (this.spawnWorld == "") {
/* 345 */       this.spawnWorld = ((World)this.world.getServer().getWorlds().get(0)).getName();
/*     */     }
/*     */ 
/*     */     
/* 349 */     if (nbttagcompound.hasKey("SpawnX") && nbttagcompound.hasKey("SpawnY") && nbttagcompound.hasKey("SpawnZ")) {
/* 350 */       this.b = new ChunkCoordinates(nbttagcompound.e("SpawnX"), nbttagcompound.e("SpawnY"), nbttagcompound.e("SpawnZ"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 355 */     super.b(nbttagcompound);
/* 356 */     nbttagcompound.a("Inventory", this.inventory.a(new NBTTagList()));
/* 357 */     nbttagcompound.a("Dimension", this.dimension);
/* 358 */     nbttagcompound.a("Sleeping", this.sleeping);
/* 359 */     nbttagcompound.a("SleepTimer", (short)this.sleepTicks);
/* 360 */     if (this.b != null) {
/* 361 */       nbttagcompound.a("SpawnX", this.b.x);
/* 362 */       nbttagcompound.a("SpawnY", this.b.y);
/* 363 */       nbttagcompound.a("SpawnZ", this.b.z);
/* 364 */       nbttagcompound.setString("SpawnWorld", this.spawnWorld);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IInventory iinventory) {}
/*     */   
/*     */   public void b(int i, int j, int k) {}
/*     */   
/*     */   public void receive(Entity entity, int i) {}
/*     */   
/* 375 */   public float t() { return 0.12F; }
/*     */ 
/*     */ 
/*     */   
/* 379 */   protected void s() { this.height = 1.62F; }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 383 */     this.ay = 0;
/* 384 */     if (this.health <= 0) {
/* 385 */       return false;
/*     */     }
/* 387 */     if (isSleeping() && !this.world.isStatic) {
/* 388 */       a(true, true, false);
/*     */     }
/*     */     
/* 391 */     if (entity instanceof EntityMonster || entity instanceof EntityArrow) {
/* 392 */       if (this.world.spawnMonsters == 0) {
/* 393 */         i = 0;
/*     */       }
/*     */       
/* 396 */       if (this.world.spawnMonsters == 1) {
/* 397 */         i = i / 3 + 1;
/*     */       }
/*     */       
/* 400 */       if (this.world.spawnMonsters == 3) {
/* 401 */         i = i * 3 / 2;
/*     */       }
/*     */     } 
/*     */     
/* 405 */     if (i == 0) {
/* 406 */       return false;
/*     */     }
/* 408 */     Object object = entity;
/*     */     
/* 410 */     if (entity instanceof EntityArrow && ((EntityArrow)entity).shooter != null) {
/* 411 */       object = ((EntityArrow)entity).shooter;
/*     */     }
/*     */     
/* 414 */     if (object instanceof EntityLiving) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 419 */       if (!(entity.getBukkitEntity() instanceof org.bukkit.entity.Projectile)) {
/* 420 */         Entity damager = ((Entity)object).getBukkitEntity();
/* 421 */         Entity damagee = getBukkitEntity();
/*     */         
/* 423 */         EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, i);
/* 424 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 426 */         if (event.isCancelled() || event.getDamage() == 0) {
/* 427 */           return false;
/*     */         }
/*     */         
/* 430 */         i = event.getDamage();
/*     */       } 
/*     */ 
/*     */       
/* 434 */       a((EntityLiving)object, false);
/*     */     } 
/*     */     
/* 437 */     a(StatisticList.x, i);
/* 438 */     return super.damageEntity(entity, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 444 */   protected boolean j_() { return false; }
/*     */ 
/*     */   
/*     */   protected void a(EntityLiving entityliving, boolean flag) {
/* 448 */     if (!(entityliving instanceof EntityCreeper) && !(entityliving instanceof EntityGhast)) {
/* 449 */       if (entityliving instanceof EntityWolf) {
/* 450 */         EntityWolf entitywolf = (EntityWolf)entityliving;
/*     */         
/* 452 */         if (entitywolf.isTamed() && this.name.equals(entitywolf.getOwnerName())) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 457 */       if (!(entityliving instanceof EntityHuman) || j_()) {
/* 458 */         List list = this.world.a(EntityWolf.class, AxisAlignedBB.b(this.locX, this.locY, this.locZ, this.locX + 1.0D, this.locY + 1.0D, this.locZ + 1.0D).b(16.0D, 4.0D, 16.0D));
/* 459 */         Iterator iterator = list.iterator();
/*     */         
/* 461 */         while (iterator.hasNext()) {
/* 462 */           Entity entity = (Entity)iterator.next();
/* 463 */           EntityWolf entitywolf1 = (EntityWolf)entity;
/*     */           
/* 465 */           if (entitywolf1.isTamed() && entitywolf1.F() == null && this.name.equals(entitywolf1.getOwnerName()) && (!flag || !entitywolf1.isSitting())) {
/*     */             EntityTargetEvent event;
/* 467 */             Entity bukkitTarget = (entity == null) ? null : entityliving.getBukkitEntity();
/*     */ 
/*     */             
/* 470 */             if (flag) {
/* 471 */               event = new EntityTargetEvent(entitywolf1.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET);
/*     */             } else {
/* 473 */               event = new EntityTargetEvent(entitywolf1.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER);
/*     */             } 
/* 475 */             this.world.getServer().getPluginManager().callEvent(event);
/*     */             
/* 477 */             if (event.isCancelled()) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 482 */             entitywolf1.setSitting(false);
/* 483 */             entitywolf1.setTarget(entityliving);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void c(int i) {
/* 491 */     int j = 25 - this.inventory.g();
/* 492 */     int k = i * j + this.d;
/*     */     
/* 494 */     this.inventory.c(i);
/* 495 */     i = k / 25;
/* 496 */     this.d = k % 25;
/* 497 */     super.c(i);
/*     */   }
/*     */   
/*     */   public void a(TileEntityFurnace tileentityfurnace) {}
/*     */   
/*     */   public void a(TileEntityDispenser tileentitydispenser) {}
/*     */   
/*     */   public void a(TileEntitySign tileentitysign) {}
/*     */   
/*     */   public void c(Entity entity) {
/* 507 */     if (!entity.a(this)) {
/* 508 */       ItemStack itemstack = G();
/*     */       
/* 510 */       if (itemstack != null && entity instanceof EntityLiving) {
/* 511 */         itemstack.a((EntityLiving)entity);
/*     */         
/* 513 */         if (itemstack.count == 0) {
/* 514 */           itemstack.a(this);
/* 515 */           H();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 522 */   public ItemStack G() { return this.inventory.getItemInHand(); }
/*     */ 
/*     */ 
/*     */   
/* 526 */   public void H() { this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack)null); }
/*     */ 
/*     */ 
/*     */   
/* 530 */   public double I() { return (this.height - 0.5F); }
/*     */ 
/*     */   
/*     */   public void w() {
/* 534 */     this.q = -1;
/* 535 */     this.p = true;
/*     */   }
/*     */   
/*     */   public void d(Entity entity) {
/* 539 */     int i = this.inventory.a(entity);
/*     */     
/* 541 */     if (i > 0) {
/* 542 */       if (this.motY < 0.0D) {
/* 543 */         i++;
/*     */       }
/*     */ 
/*     */       
/* 547 */       if (entity instanceof EntityLiving && !(entity instanceof EntityHuman)) {
/* 548 */         Entity damager = getBukkitEntity();
/* 549 */         Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
/*     */         
/* 551 */         EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, i);
/* 552 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 554 */         if (event.isCancelled() || event.getDamage() == 0) {
/*     */           return;
/*     */         }
/*     */         
/* 558 */         i = event.getDamage();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 563 */       if (!entity.damageEntity(this, i)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 568 */       ItemStack itemstack = G();
/*     */       
/* 570 */       if (itemstack != null && entity instanceof EntityLiving) {
/* 571 */         itemstack.a((EntityLiving)entity, this);
/*     */         
/* 573 */         if (itemstack.count == 0) {
/* 574 */           itemstack.a(this);
/* 575 */           H();
/*     */         } 
/*     */       } 
/*     */       
/* 579 */       if (entity instanceof EntityLiving) {
/* 580 */         if (entity.T()) {
/* 581 */           a((EntityLiving)entity, true);
/*     */         }
/*     */         
/* 584 */         a(StatisticList.w, i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack) {}
/*     */   
/*     */   public void die() {
/* 592 */     super.die();
/* 593 */     this.defaultContainer.a(this);
/* 594 */     if (this.activeContainer != null) {
/* 595 */       this.activeContainer.a(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 600 */   public boolean K() { return (!this.sleeping && super.K()); }
/*     */ 
/*     */   
/*     */   public EnumBedError a(int i, int j, int k) {
/* 604 */     if (!this.world.isStatic) {
/* 605 */       if (isSleeping() || !T()) {
/* 606 */         return EnumBedError.OTHER_PROBLEM;
/*     */       }
/*     */       
/* 609 */       if (this.world.worldProvider.c) {
/* 610 */         return EnumBedError.NOT_POSSIBLE_HERE;
/*     */       }
/*     */       
/* 613 */       if (this.world.d()) {
/* 614 */         return EnumBedError.NOT_POSSIBLE_NOW;
/*     */       }
/*     */       
/* 617 */       if (Math.abs(this.locX - i) > 3.0D || Math.abs(this.locY - j) > 2.0D || Math.abs(this.locZ - k) > 3.0D) {
/* 618 */         return EnumBedError.TOO_FAR_AWAY;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 623 */     if (getBukkitEntity() instanceof Player) {
/* 624 */       Player player = (Player)getBukkitEntity();
/* 625 */       Block bed = this.world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 627 */       PlayerBedEnterEvent event = new PlayerBedEnterEvent(player, bed);
/* 628 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 630 */       if (event.isCancelled()) {
/* 631 */         return EnumBedError.OTHER_PROBLEM;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 636 */     b(0.2F, 0.2F);
/* 637 */     this.height = 0.2F;
/* 638 */     if (this.world.isLoaded(i, j, k)) {
/* 639 */       int l = this.world.getData(i, j, k);
/* 640 */       int i1 = BlockBed.c(l);
/* 641 */       float f = 0.5F;
/* 642 */       float f1 = 0.5F;
/*     */       
/* 644 */       switch (i1) {
/*     */         case 0:
/* 646 */           f1 = 0.9F;
/*     */           break;
/*     */         
/*     */         case 1:
/* 650 */           f = 0.1F;
/*     */           break;
/*     */         
/*     */         case 2:
/* 654 */           f1 = 0.1F;
/*     */           break;
/*     */         
/*     */         case 3:
/* 658 */           f = 0.9F;
/*     */           break;
/*     */       } 
/* 661 */       e(i1);
/* 662 */       setPosition((i + f), (j + 0.9375F), (k + f1));
/*     */     } else {
/* 664 */       setPosition((i + 0.5F), (j + 0.9375F), (k + 0.5F));
/*     */     } 
/*     */     
/* 667 */     this.sleeping = true;
/* 668 */     this.sleepTicks = 0;
/* 669 */     this.A = new ChunkCoordinates(i, j, k);
/* 670 */     this.motX = this.motZ = this.motY = 0.0D;
/* 671 */     if (!this.world.isStatic) {
/* 672 */       this.world.everyoneSleeping();
/*     */     }
/*     */     
/* 675 */     return EnumBedError.OK;
/*     */   }
/*     */   
/*     */   private void e(int i) {
/* 679 */     this.B = 0.0F;
/* 680 */     this.C = 0.0F;
/* 681 */     switch (i) {
/*     */       case 0:
/* 683 */         this.C = -1.8F;
/*     */         break;
/*     */       
/*     */       case 1:
/* 687 */         this.B = 1.8F;
/*     */         break;
/*     */       
/*     */       case 2:
/* 691 */         this.C = 1.8F;
/*     */         break;
/*     */       
/*     */       case 3:
/* 695 */         this.B = -1.8F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void a(boolean flag, boolean flag1, boolean flag2) {
/* 700 */     b(0.6F, 1.8F);
/* 701 */     s();
/* 702 */     ChunkCoordinates chunkcoordinates = this.A;
/* 703 */     ChunkCoordinates chunkcoordinates1 = this.A;
/*     */     
/* 705 */     if (chunkcoordinates != null && this.world.getTypeId(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Block.BED.id) {
/* 706 */       BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, false);
/* 707 */       chunkcoordinates1 = BlockBed.f(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
/* 708 */       if (chunkcoordinates1 == null) {
/* 709 */         chunkcoordinates1 = new ChunkCoordinates(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
/*     */       }
/*     */       
/* 712 */       setPosition((chunkcoordinates1.x + 0.5F), (chunkcoordinates1.y + this.height + 0.1F), (chunkcoordinates1.z + 0.5F));
/*     */     } 
/*     */     
/* 715 */     this.sleeping = false;
/* 716 */     if (!this.world.isStatic && flag1) {
/* 717 */       this.world.everyoneSleeping();
/*     */     }
/*     */ 
/*     */     
/* 721 */     if (getBukkitEntity() instanceof Player) {
/* 722 */       Block bed; Player player = (Player)getBukkitEntity();
/*     */ 
/*     */       
/* 725 */       if (chunkcoordinates != null) {
/* 726 */         bed = this.world.getWorld().getBlockAt(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
/*     */       } else {
/* 728 */         bed = this.world.getWorld().getBlockAt(player.getLocation());
/*     */       } 
/*     */       
/* 731 */       PlayerBedLeaveEvent event = new PlayerBedLeaveEvent(player, bed);
/* 732 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */     } 
/*     */ 
/*     */     
/* 736 */     if (flag) {
/* 737 */       this.sleepTicks = 0;
/*     */     } else {
/* 739 */       this.sleepTicks = 100;
/*     */     } 
/*     */     
/* 742 */     if (flag2) {
/* 743 */       a(this.A);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 748 */   private boolean o() { return (this.world.getTypeId(this.A.x, this.A.y, this.A.z) == Block.BED.id); }
/*     */ 
/*     */   
/*     */   public static ChunkCoordinates getBed(World world, ChunkCoordinates chunkcoordinates) {
/* 752 */     IChunkProvider ichunkprovider = world.o();
/*     */     
/* 754 */     ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z - 3 >> 4);
/* 755 */     ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z - 3 >> 4);
/* 756 */     ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z + 3 >> 4);
/* 757 */     ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z + 3 >> 4);
/* 758 */     if (world.getTypeId(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) != Block.BED.id) {
/* 759 */       return null;
/*     */     }
/* 761 */     return BlockBed.f(world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 768 */   public boolean isSleeping() { return this.sleeping; }
/*     */ 
/*     */ 
/*     */   
/* 772 */   public boolean isDeeplySleeping() { return (this.sleeping && this.sleepTicks >= 100); }
/*     */ 
/*     */   
/*     */   public void a(String s) {}
/*     */ 
/*     */   
/* 778 */   public ChunkCoordinates getBed() { return this.b; }
/*     */ 
/*     */   
/*     */   public void a(ChunkCoordinates chunkcoordinates) {
/* 782 */     if (chunkcoordinates != null) {
/* 783 */       this.b = new ChunkCoordinates(chunkcoordinates);
/* 784 */       this.spawnWorld = this.world.worldData.name;
/*     */     } else {
/* 786 */       this.b = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 791 */   public void a(Statistic statistic) { a(statistic, 1); }
/*     */ 
/*     */   
/*     */   public void a(Statistic statistic, int i) {}
/*     */   
/*     */   protected void O() {
/* 797 */     super.O();
/* 798 */     a(StatisticList.u, 1);
/*     */   }
/*     */   
/*     */   public void a(float f, float f1) {
/* 802 */     double d0 = this.locX;
/* 803 */     double d1 = this.locY;
/* 804 */     double d2 = this.locZ;
/*     */     
/* 806 */     super.a(f, f1);
/* 807 */     h(this.locX - d0, this.locY - d1, this.locZ - d2);
/*     */   }
/*     */   
/*     */   private void h(double d0, double d1, double d2) {
/* 811 */     if (this.vehicle == null)
/*     */     {
/*     */       
/* 814 */       if (a(Material.WATER)) {
/* 815 */         int i = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/* 816 */         if (i > 0) {
/* 817 */           a(StatisticList.q, i);
/*     */         }
/* 819 */       } else if (ad()) {
/* 820 */         int i = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
/* 821 */         if (i > 0) {
/* 822 */           a(StatisticList.m, i);
/*     */         }
/* 824 */       } else if (p()) {
/* 825 */         if (d1 > 0.0D) {
/* 826 */           a(StatisticList.o, (int)Math.round(d1 * 100.0D));
/*     */         }
/* 828 */       } else if (this.onGround) {
/* 829 */         int i = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
/* 830 */         if (i > 0) {
/* 831 */           a(StatisticList.l, i);
/*     */         }
/*     */       } else {
/* 834 */         int i = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
/* 835 */         if (i > 25) {
/* 836 */           a(StatisticList.p, i);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void i(double d0, double d1, double d2) {
/* 843 */     if (this.vehicle != null) {
/* 844 */       int i = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/*     */       
/* 846 */       if (i > 0) {
/* 847 */         if (this.vehicle instanceof EntityMinecart) {
/* 848 */           a(StatisticList.r, i);
/* 849 */           if (this.c == null) {
/* 850 */             this.c = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/* 851 */           } else if (this.c.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000.0D) {
/* 852 */             a(AchievementList.q, 1);
/*     */           } 
/* 854 */         } else if (this.vehicle instanceof EntityBoat) {
/* 855 */           a(StatisticList.s, i);
/* 856 */         } else if (this.vehicle instanceof EntityPig) {
/* 857 */           a(StatisticList.t, i);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(float f) {
/* 864 */     if (f >= 2.0F) {
/* 865 */       a(StatisticList.n, (int)Math.round(f * 100.0D));
/*     */     }
/*     */     
/* 868 */     super.a(f);
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving) {
/* 872 */     if (entityliving instanceof EntityMonster) {
/* 873 */       a(AchievementList.s);
/*     */     }
/*     */   }
/*     */   
/*     */   public void P() {
/* 878 */     if (this.D > 0) {
/* 879 */       this.D = 10;
/*     */     } else {
/* 881 */       this.E = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityHuman.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */