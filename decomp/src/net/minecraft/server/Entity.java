/*      */ package net.minecraft.server;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.craftbukkit.CraftServer;
/*      */ import org.bukkit.craftbukkit.CraftWorld;
/*      */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*      */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Vehicle;
/*      */ import org.bukkit.event.entity.EntityCombustEvent;
/*      */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*      */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*      */ import org.bukkit.event.entity.EntityDamageEvent;
/*      */ import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
/*      */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*      */ 
/*      */ public abstract class Entity {
/*   25 */   private static int entityCount = 0;
/*      */   
/*      */   public int id;
/*      */   
/*      */   public double aH;
/*      */   
/*      */   public boolean aI;
/*      */   
/*      */   public Entity passenger;
/*      */   
/*      */   public Entity vehicle;
/*      */   
/*      */   public World world;
/*      */   
/*      */   public double lastX;
/*      */   
/*      */   public double lastY;
/*      */   
/*      */   public double lastZ;
/*      */   
/*      */   public double locX;
/*      */   
/*      */   public double locY;
/*      */   
/*      */   public double locZ;
/*      */   
/*      */   public double motX;
/*      */   
/*      */   public double motY;
/*      */   
/*      */   public double motZ;
/*      */   
/*      */   public float yaw;
/*      */   
/*      */   public float pitch;
/*      */   
/*      */   public float lastYaw;
/*      */   
/*      */   public float lastPitch;
/*      */   
/*      */   public final AxisAlignedBB boundingBox;
/*      */   
/*      */   public boolean onGround;
/*      */   
/*      */   public boolean positionChanged;
/*      */   
/*      */   public boolean bc;
/*      */   
/*      */   public boolean bd;
/*      */   
/*      */   public boolean velocityChanged;
/*      */   
/*      */   public boolean bf;
/*      */   
/*      */   public boolean bg;
/*      */   
/*      */   public boolean dead;
/*      */   public float height;
/*      */   public float length;
/*      */   public float width;
/*      */   
/*      */   public Entity(World world) {
/*   87 */     this.uniqueId = UUID.randomUUID();
/*      */ 
/*      */     
/*   90 */     this.id = entityCount++;
/*   91 */     this.aH = 1.0D;
/*   92 */     this.aI = false;
/*   93 */     this.boundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*   94 */     this.onGround = false;
/*   95 */     this.bd = false;
/*   96 */     this.velocityChanged = false;
/*   97 */     this.bg = true;
/*   98 */     this.dead = false;
/*   99 */     this.height = 0.0F;
/*  100 */     this.length = 0.6F;
/*  101 */     this.width = 1.8F;
/*  102 */     this.bl = 0.0F;
/*  103 */     this.bm = 0.0F;
/*  104 */     this.fallDistance = 0.0F;
/*  105 */     this.b = 1;
/*  106 */     this.br = 0.0F;
/*  107 */     this.bs = 0.0F;
/*  108 */     this.bt = false;
/*  109 */     this.bu = 0.0F;
/*  110 */     this.random = new Random();
/*  111 */     this.ticksLived = 0;
/*  112 */     this.maxFireTicks = 1;
/*  113 */     this.fireTicks = 0;
/*  114 */     this.maxAirTicks = 300;
/*  115 */     this.bA = false;
/*  116 */     this.noDamageTicks = 0;
/*  117 */     this.airTicks = 300;
/*  118 */     this.justCreated = true;
/*  119 */     this.fireProof = false;
/*  120 */     this.datawatcher = new DataWatcher();
/*  121 */     this.bF = 0.0F;
/*  122 */     this.bG = false;
/*  123 */     this.world = world;
/*  124 */     setPosition(0.0D, 0.0D, 0.0D);
/*  125 */     this.datawatcher.a(0, Byte.valueOf((byte)0));
/*  126 */     b();
/*      */   }
/*      */   public float bl; public float bm; public float fallDistance; private int b; public double bo; public double bp; public double bq; public float br; public float bs; public boolean bt; public float bu; protected Random random; public int ticksLived; public int maxFireTicks; public int fireTicks; public int maxAirTicks; protected boolean bA; public int noDamageTicks; public int airTicks; private boolean justCreated; protected boolean fireProof; protected DataWatcher datawatcher; public float bF; private double d; private double e; public boolean bG; public int bH; public int bI; public int bJ; public boolean bK; public UUID uniqueId; protected Entity bukkitEntity;
/*      */   
/*      */   protected abstract void b();
/*      */   
/*  132 */   public DataWatcher aa() { return this.datawatcher; }
/*      */ 
/*      */ 
/*      */   
/*  136 */   public boolean equals(Object object) { return (object instanceof Entity) ? ((((Entity)object).id == this.id)) : false; }
/*      */ 
/*      */ 
/*      */   
/*  140 */   public int hashCode() { return this.id; }
/*      */ 
/*      */ 
/*      */   
/*  144 */   public void die() { this.dead = true; }
/*      */ 
/*      */   
/*      */   protected void b(float f, float f1) {
/*  148 */     this.length = f;
/*  149 */     this.width = f1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void c(float f, float f1) {
/*  154 */     if (Float.isNaN(f)) {
/*  155 */       f = 0.0F;
/*      */     }
/*      */     
/*  158 */     if (f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
/*  159 */       if (this instanceof EntityPlayer) {
/*  160 */         System.err.println(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
/*  161 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Nope");
/*      */       } 
/*  163 */       f = 0.0F;
/*      */     } 
/*      */ 
/*      */     
/*  167 */     if (Float.isNaN(f1)) {
/*  168 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  171 */     if (f1 == Float.POSITIVE_INFINITY || f1 == Float.NEGATIVE_INFINITY) {
/*  172 */       if (this instanceof EntityPlayer) {
/*  173 */         System.err.println(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
/*  174 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Nope");
/*      */       } 
/*  176 */       f1 = 0.0F;
/*      */     } 
/*      */ 
/*      */     
/*  180 */     this.yaw = f % 360.0F;
/*  181 */     this.pitch = f1 % 360.0F;
/*      */   }
/*      */   
/*      */   public void setPosition(double d0, double d1, double d2) {
/*  185 */     this.locX = d0;
/*  186 */     this.locY = d1;
/*  187 */     this.locZ = d2;
/*  188 */     float f = this.length / 2.0F;
/*  189 */     float f1 = this.width;
/*      */     
/*  191 */     this.boundingBox.c(d0 - f, d1 - this.height + this.br, d2 - f, d0 + f, d1 - this.height + this.br + f1, d2 + f);
/*      */   }
/*      */ 
/*      */   
/*  195 */   public void m_() { R(); }
/*      */ 
/*      */   
/*      */   public void R() {
/*  199 */     if (this.vehicle != null && this.vehicle.dead) {
/*  200 */       this.vehicle = null;
/*      */     }
/*      */     
/*  203 */     this.ticksLived++;
/*  204 */     this.bl = this.bm;
/*  205 */     this.lastX = this.locX;
/*  206 */     this.lastY = this.locY;
/*  207 */     this.lastZ = this.locZ;
/*  208 */     this.lastPitch = this.pitch;
/*  209 */     this.lastYaw = this.yaw;
/*  210 */     if (f_()) {
/*  211 */       if (!this.bA && !this.justCreated) {
/*  212 */         float f = MathHelper.a(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;
/*      */         
/*  214 */         if (f > 1.0F) {
/*  215 */           f = 1.0F;
/*      */         }
/*      */         
/*  218 */         this.world.makeSound(this, "random.splash", f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  219 */         float f1 = MathHelper.floor(this.boundingBox.b);
/*      */ 
/*      */         
/*      */         int i;
/*      */ 
/*      */         
/*  225 */         for (i = 0; i < 1.0F + this.length * 20.0F; i++) {
/*  226 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/*  227 */           float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/*  228 */           this.world.a("bubble", this.locX + f2, (f1 + 1.0F), this.locZ + f3, this.motX, this.motY - (this.random.nextFloat() * 0.2F), this.motZ);
/*      */         } 
/*      */         
/*  231 */         for (i = 0; i < 1.0F + this.length * 20.0F; i++) {
/*  232 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/*  233 */           float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/*  234 */           this.world.a("splash", this.locX + f2, (f1 + 1.0F), this.locZ + f3, this.motX, this.motY, this.motZ);
/*      */         } 
/*      */       } 
/*      */       
/*  238 */       this.fallDistance = 0.0F;
/*  239 */       this.bA = true;
/*  240 */       this.fireTicks = 0;
/*      */     } else {
/*  242 */       this.bA = false;
/*      */     } 
/*      */     
/*  245 */     if (this.world.isStatic) {
/*  246 */       this.fireTicks = 0;
/*  247 */     } else if (this.fireTicks > 0) {
/*  248 */       if (this.fireProof) {
/*  249 */         this.fireTicks -= 4;
/*  250 */         if (this.fireTicks < 0) {
/*  251 */           this.fireTicks = 0;
/*      */         }
/*      */       } else {
/*  254 */         if (this.fireTicks % 20 == 0)
/*      */         {
/*  256 */           if (this instanceof EntityLiving) {
/*  257 */             EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE_TICK, true);
/*  258 */             this.world.getServer().getPluginManager().callEvent(event);
/*      */             
/*  260 */             if (!event.isCancelled()) {
/*  261 */               damageEntity((Entity)null, event.getDamage());
/*      */             }
/*      */           } else {
/*  264 */             damageEntity((Entity)null, 1);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  269 */         this.fireTicks--;
/*      */       } 
/*      */     } 
/*      */     
/*  273 */     if (ae()) {
/*  274 */       ab();
/*      */     }
/*      */     
/*  277 */     if (this.locY < -64.0D) {
/*  278 */       Y();
/*      */     }
/*      */     
/*  281 */     if (!this.world.isStatic) {
/*  282 */       a(0, (this.fireTicks > 0));
/*  283 */       a(2, (this.vehicle != null));
/*      */     } 
/*      */     
/*  286 */     this.justCreated = false;
/*      */   }
/*      */   
/*      */   protected void ab() {
/*  290 */     if (!this.fireProof) {
/*      */       
/*  292 */       if (this instanceof EntityLiving) {
/*  293 */         CraftServer craftServer = this.world.getServer();
/*      */ 
/*      */         
/*  296 */         Block damager = null;
/*  297 */         Entity damagee = getBukkitEntity();
/*      */         
/*  299 */         EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.LAVA, 4);
/*  300 */         craftServer.getPluginManager().callEvent(event);
/*      */         
/*  302 */         if (!event.isCancelled()) {
/*  303 */           damageEntity((Entity)null, event.getDamage());
/*      */         }
/*      */         
/*  306 */         if (this.fireTicks <= 0) {
/*      */           
/*  308 */           EntityCombustEvent combustEvent = new EntityCombustEvent(damagee);
/*  309 */           craftServer.getPluginManager().callEvent(combustEvent);
/*      */           
/*  311 */           if (!combustEvent.isCancelled()) {
/*  312 */             this.fireTicks = 600;
/*      */           }
/*      */         } else {
/*      */           
/*  316 */           this.fireTicks = 600;
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  322 */       damageEntity((Entity)null, 4);
/*  323 */       this.fireTicks = 600;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  328 */   protected void Y() { die(); }
/*      */ 
/*      */   
/*      */   public boolean d(double d0, double d1, double d2) {
/*  332 */     AxisAlignedBB axisalignedbb = this.boundingBox.c(d0, d1, d2);
/*  333 */     List list = this.world.getEntities(this, axisalignedbb);
/*      */     
/*  335 */     return (list.size() > 0) ? false : (!this.world.c(axisalignedbb));
/*      */   }
/*      */   
/*      */   public void move(double d0, double d1, double d2) {
/*  339 */     if (this.bt) {
/*  340 */       this.boundingBox.d(d0, d1, d2);
/*  341 */       this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
/*  342 */       this.locY = this.boundingBox.b + this.height - this.br;
/*  343 */       this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
/*      */     } else {
/*  345 */       this.br *= 0.4F;
/*  346 */       double d3 = this.locX;
/*  347 */       double d4 = this.locZ;
/*      */       
/*  349 */       if (this.bf) {
/*  350 */         this.bf = false;
/*  351 */         d0 *= 0.25D;
/*  352 */         d1 *= 0.05000000074505806D;
/*  353 */         d2 *= 0.25D;
/*  354 */         this.motX = 0.0D;
/*  355 */         this.motY = 0.0D;
/*  356 */         this.motZ = 0.0D;
/*      */       } 
/*      */       
/*  359 */       double d5 = d0;
/*  360 */       double d6 = d1;
/*  361 */       double d7 = d2;
/*  362 */       AxisAlignedBB axisalignedbb = this.boundingBox.clone();
/*  363 */       boolean flag = (this.onGround && isSneaking());
/*      */       
/*  365 */       if (flag) {
/*      */         double d8;
/*      */         
/*  368 */         for (d8 = 0.05D; d0 != 0.0D && this.world.getEntities(this, this.boundingBox.c(d0, -1.0D, 0.0D)).size() == 0; d5 = d0) {
/*  369 */           if (d0 < d8 && d0 >= -d8) {
/*  370 */             d0 = 0.0D;
/*  371 */           } else if (d0 > 0.0D) {
/*  372 */             d0 -= d8;
/*      */           } else {
/*  374 */             d0 += d8;
/*      */           } 
/*      */         } 
/*      */         
/*  378 */         for (; d2 != 0.0D && this.world.getEntities(this, this.boundingBox.c(0.0D, -1.0D, d2)).size() == 0; d7 = d2) {
/*  379 */           if (d2 < d8 && d2 >= -d8) {
/*  380 */             d2 = 0.0D;
/*  381 */           } else if (d2 > 0.0D) {
/*  382 */             d2 -= d8;
/*      */           } else {
/*  384 */             d2 += d8;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  389 */       List list = this.world.getEntities(this, this.boundingBox.a(d0, d1, d2));
/*      */       
/*  391 */       for (int i = 0; i < list.size(); i++) {
/*  392 */         d1 = ((AxisAlignedBB)list.get(i)).b(this.boundingBox, d1);
/*      */       }
/*      */       
/*  395 */       this.boundingBox.d(0.0D, d1, 0.0D);
/*  396 */       if (!this.bg && d6 != d1) {
/*  397 */         d2 = 0.0D;
/*  398 */         d1 = 0.0D;
/*  399 */         d0 = 0.0D;
/*      */       } 
/*      */       
/*  402 */       boolean flag1 = (this.onGround || (d6 != d1 && d6 < 0.0D));
/*      */       
/*      */       int j;
/*      */       
/*  406 */       for (j = 0; j < list.size(); j++) {
/*  407 */         d0 = ((AxisAlignedBB)list.get(j)).a(this.boundingBox, d0);
/*      */       }
/*      */       
/*  410 */       this.boundingBox.d(d0, 0.0D, 0.0D);
/*  411 */       if (!this.bg && d5 != d0) {
/*  412 */         d2 = 0.0D;
/*  413 */         d1 = 0.0D;
/*  414 */         d0 = 0.0D;
/*      */       } 
/*      */       
/*  417 */       for (j = 0; j < list.size(); j++) {
/*  418 */         d2 = ((AxisAlignedBB)list.get(j)).c(this.boundingBox, d2);
/*      */       }
/*      */       
/*  421 */       this.boundingBox.d(0.0D, 0.0D, d2);
/*  422 */       if (!this.bg && d7 != d2) {
/*  423 */         d2 = 0.0D;
/*  424 */         d1 = 0.0D;
/*  425 */         d0 = 0.0D;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       if (this.bs > 0.0F && flag1 && (flag || this.br < 0.05F) && (d5 != d0 || d7 != d2)) {
/*  433 */         double d9 = d0;
/*  434 */         double d10 = d1;
/*  435 */         double d11 = d2;
/*      */         
/*  437 */         d0 = d5;
/*  438 */         d1 = this.bs;
/*  439 */         d2 = d7;
/*  440 */         AxisAlignedBB axisalignedbb1 = this.boundingBox.clone();
/*      */         
/*  442 */         this.boundingBox.b(axisalignedbb);
/*  443 */         list = this.world.getEntities(this, this.boundingBox.a(d5, d1, d7));
/*      */         int k;
/*  445 */         for (k = 0; k < list.size(); k++) {
/*  446 */           d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
/*      */         }
/*      */         
/*  449 */         this.boundingBox.d(0.0D, d1, 0.0D);
/*  450 */         if (!this.bg && d6 != d1) {
/*  451 */           d2 = 0.0D;
/*  452 */           d1 = 0.0D;
/*  453 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  456 */         for (k = 0; k < list.size(); k++) {
/*  457 */           d0 = ((AxisAlignedBB)list.get(k)).a(this.boundingBox, d0);
/*      */         }
/*      */         
/*  460 */         this.boundingBox.d(d0, 0.0D, 0.0D);
/*  461 */         if (!this.bg && d5 != d0) {
/*  462 */           d2 = 0.0D;
/*  463 */           d1 = 0.0D;
/*  464 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  467 */         for (k = 0; k < list.size(); k++) {
/*  468 */           d2 = ((AxisAlignedBB)list.get(k)).c(this.boundingBox, d2);
/*      */         }
/*      */         
/*  471 */         this.boundingBox.d(0.0D, 0.0D, d2);
/*  472 */         if (!this.bg && d7 != d2) {
/*  473 */           d2 = 0.0D;
/*  474 */           d1 = 0.0D;
/*  475 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  478 */         if (!this.bg && d6 != d1) {
/*  479 */           d2 = 0.0D;
/*  480 */           d1 = 0.0D;
/*  481 */           d0 = 0.0D;
/*      */         } else {
/*  483 */           d1 = -this.bs;
/*      */           
/*  485 */           for (k = 0; k < list.size(); k++) {
/*  486 */             d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
/*      */           }
/*      */           
/*  489 */           this.boundingBox.d(0.0D, d1, 0.0D);
/*      */         } 
/*      */         
/*  492 */         if (d9 * d9 + d11 * d11 >= d0 * d0 + d2 * d2) {
/*  493 */           d0 = d9;
/*  494 */           d1 = d10;
/*  495 */           d2 = d11;
/*  496 */           this.boundingBox.b(axisalignedbb1);
/*      */         } else {
/*  498 */           double d12 = this.boundingBox.b - (int)this.boundingBox.b;
/*      */           
/*  500 */           if (d12 > 0.0D) {
/*  501 */             this.br = (float)(this.br + d12 + 0.01D);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  506 */       this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
/*  507 */       this.locY = this.boundingBox.b + this.height - this.br;
/*  508 */       this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
/*  509 */       this.positionChanged = (d5 != d0 || d7 != d2);
/*  510 */       this.bc = (d6 != d1);
/*  511 */       this.onGround = (d6 != d1 && d6 < 0.0D);
/*  512 */       this.bd = (this.positionChanged || this.bc);
/*  513 */       a(d1, this.onGround);
/*  514 */       if (d5 != d0) {
/*  515 */         this.motX = 0.0D;
/*      */       }
/*      */       
/*  518 */       if (d6 != d1) {
/*  519 */         this.motY = 0.0D;
/*      */       }
/*      */       
/*  522 */       if (d7 != d2) {
/*  523 */         this.motZ = 0.0D;
/*      */       }
/*      */       
/*  526 */       double d9 = this.locX - d3;
/*  527 */       double d10 = this.locZ - d4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  533 */       if (this.positionChanged && getBukkitEntity() instanceof Vehicle) {
/*  534 */         Vehicle vehicle = (Vehicle)getBukkitEntity();
/*  535 */         Block block = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.20000000298023224D - this.height), MathHelper.floor(this.locZ));
/*      */         
/*  537 */         if (d5 > d0) {
/*  538 */           block = block.getRelative(BlockFace.SOUTH);
/*  539 */         } else if (d5 < d0) {
/*  540 */           block = block.getRelative(BlockFace.NORTH);
/*  541 */         } else if (d7 > d2) {
/*  542 */           block = block.getRelative(BlockFace.WEST);
/*  543 */         } else if (d7 < d2) {
/*  544 */           block = block.getRelative(BlockFace.EAST);
/*      */         } 
/*      */         
/*  547 */         VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
/*  548 */         this.world.getServer().getPluginManager().callEvent(event);
/*      */       } 
/*      */ 
/*      */       
/*  552 */       if (n() && !flag && this.vehicle == null) {
/*  553 */         this.bm = (float)(this.bm + MathHelper.a(d9 * d9 + d10 * d10) * 0.6D);
/*  554 */         int l = MathHelper.floor(this.locX);
/*  555 */         int i1 = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  556 */         int j1 = MathHelper.floor(this.locZ);
/*  557 */         int k = this.world.getTypeId(l, i1, j1);
/*  558 */         if (this.world.getTypeId(l, i1 - 1, j1) == Block.FENCE.id) {
/*  559 */           k = this.world.getTypeId(l, i1 - 1, j1);
/*      */         }
/*      */         
/*  562 */         if (this.bm > this.b && k > 0) {
/*  563 */           this.b++;
/*  564 */           StepSound stepsound = (Block.byId[k]).stepSound;
/*      */           
/*  566 */           if (this.world.getTypeId(l, i1 + 1, j1) == Block.SNOW.id) {
/*  567 */             stepsound = Block.SNOW.stepSound;
/*  568 */             this.world.makeSound(this, stepsound.getName(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*  569 */           } else if (!(Block.byId[k]).material.isLiquid()) {
/*  570 */             this.world.makeSound(this, stepsound.getName(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*      */           } 
/*      */           
/*  573 */           Block.byId[k].b(this.world, l, i1, j1, this);
/*      */         } 
/*      */       } 
/*      */       
/*  577 */       int l = MathHelper.floor(this.boundingBox.a + 0.001D);
/*  578 */       int i1 = MathHelper.floor(this.boundingBox.b + 0.001D);
/*  579 */       int j1 = MathHelper.floor(this.boundingBox.c + 0.001D);
/*  580 */       int k = MathHelper.floor(this.boundingBox.d - 0.001D);
/*  581 */       int k1 = MathHelper.floor(this.boundingBox.e - 0.001D);
/*  582 */       int l1 = MathHelper.floor(this.boundingBox.f - 0.001D);
/*      */       
/*  584 */       if (this.world.a(l, i1, j1, k, k1, l1)) {
/*  585 */         for (int i2 = l; i2 <= k; i2++) {
/*  586 */           for (int j2 = i1; j2 <= k1; j2++) {
/*  587 */             for (int k2 = j1; k2 <= l1; k2++) {
/*  588 */               int l2 = this.world.getTypeId(i2, j2, k2);
/*      */               
/*  590 */               if (l2 > 0) {
/*  591 */                 Block.byId[l2].a(this.world, i2, j2, k2, this);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  598 */       boolean flag2 = ac();
/*      */       
/*  600 */       if (this.world.d(this.boundingBox.shrink(0.001D, 0.001D, 0.001D))) {
/*  601 */         burn(1);
/*  602 */         if (!flag2) {
/*  603 */           this.fireTicks++;
/*      */           
/*  605 */           if (this.fireTicks <= 0) {
/*  606 */             EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity());
/*  607 */             this.world.getServer().getPluginManager().callEvent(event);
/*      */             
/*  609 */             if (!event.isCancelled()) {
/*  610 */               this.fireTicks = 300;
/*      */             }
/*      */           } else {
/*      */             
/*  614 */             this.fireTicks = 300;
/*      */           } 
/*      */         } 
/*  617 */       } else if (this.fireTicks <= 0) {
/*  618 */         this.fireTicks = -this.maxFireTicks;
/*      */       } 
/*      */       
/*  621 */       if (flag2 && this.fireTicks > 0) {
/*  622 */         this.world.makeSound(this, "random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  623 */         this.fireTicks = -this.maxFireTicks;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  629 */   protected boolean n() { return true; }
/*      */ 
/*      */   
/*      */   protected void a(double d0, boolean flag) {
/*  633 */     if (flag) {
/*  634 */       if (this.fallDistance > 0.0F) {
/*  635 */         a(this.fallDistance);
/*  636 */         this.fallDistance = 0.0F;
/*      */       } 
/*  638 */     } else if (d0 < 0.0D) {
/*  639 */       this.fallDistance = (float)(this.fallDistance - d0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  644 */   public AxisAlignedBB e_() { return null; }
/*      */ 
/*      */   
/*      */   protected void burn(int i) {
/*  648 */     if (!this.fireProof) {
/*      */       
/*  650 */       if (this instanceof EntityLiving) {
/*  651 */         EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE, i);
/*  652 */         this.world.getServer().getPluginManager().callEvent(event);
/*      */         
/*  654 */         if (event.isCancelled()) {
/*      */           return;
/*      */         }
/*      */         
/*  658 */         i = event.getDamage();
/*      */       } 
/*      */       
/*  661 */       damageEntity((Entity)null, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void a(float f) {
/*  666 */     if (this.passenger != null) {
/*  667 */       this.passenger.a(f);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  672 */   public boolean ac() { return (this.bA || this.world.s(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))); }
/*      */ 
/*      */ 
/*      */   
/*  676 */   public boolean ad() { return this.bA; }
/*      */ 
/*      */ 
/*      */   
/*  680 */   public boolean f_() { return this.world.a(this.boundingBox.b(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D, 0.001D, 0.001D), Material.WATER, this); }
/*      */ 
/*      */   
/*      */   public boolean a(Material material) {
/*  684 */     double d0 = this.locY + t();
/*  685 */     int i = MathHelper.floor(this.locX);
/*  686 */     int j = MathHelper.d(MathHelper.floor(d0));
/*  687 */     int k = MathHelper.floor(this.locZ);
/*  688 */     int l = this.world.getTypeId(i, j, k);
/*      */     
/*  690 */     if (l != 0 && (Block.byId[l]).material == material) {
/*  691 */       float f = BlockFluids.c(this.world.getData(i, j, k)) - 0.11111111F;
/*  692 */       float f1 = (j + 1) - f;
/*      */       
/*  694 */       return (d0 < f1);
/*      */     } 
/*  696 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  701 */   public float t() { return 0.0F; }
/*      */ 
/*      */ 
/*      */   
/*  705 */   public boolean ae() { return this.world.a(this.boundingBox.b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA); }
/*      */ 
/*      */   
/*      */   public void a(float f, float f1, float f2) {
/*  709 */     float f3 = MathHelper.c(f * f + f1 * f1);
/*      */     
/*  711 */     if (f3 >= 0.01F) {
/*  712 */       if (f3 < 1.0F) {
/*  713 */         f3 = 1.0F;
/*      */       }
/*      */       
/*  716 */       f3 = f2 / f3;
/*  717 */       f *= f3;
/*  718 */       f1 *= f3;
/*  719 */       float f4 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
/*  720 */       float f5 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/*      */       
/*  722 */       this.motX += (f * f5 - f1 * f4);
/*  723 */       this.motZ += (f1 * f5 + f * f4);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float c(float f) {
/*  728 */     int i = MathHelper.floor(this.locX);
/*  729 */     double d0 = (this.boundingBox.e - this.boundingBox.b) * 0.66D;
/*  730 */     int j = MathHelper.floor(this.locY - this.height + d0);
/*  731 */     int k = MathHelper.floor(this.locZ);
/*      */     
/*  733 */     if (this.world.a(MathHelper.floor(this.boundingBox.a), MathHelper.floor(this.boundingBox.b), MathHelper.floor(this.boundingBox.c), MathHelper.floor(this.boundingBox.d), MathHelper.floor(this.boundingBox.e), MathHelper.floor(this.boundingBox.f))) {
/*  734 */       float f1 = this.world.n(i, j, k);
/*      */       
/*  736 */       if (f1 < this.bF) {
/*  737 */         f1 = this.bF;
/*      */       }
/*      */       
/*  740 */       return f1;
/*      */     } 
/*  742 */     return this.bF;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnIn(World world) {
/*  748 */     if (world == null) {
/*  749 */       die();
/*  750 */       this.world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
/*      */       
/*      */       return;
/*      */     } 
/*  754 */     this.world = world;
/*      */   }
/*      */   
/*      */   public void setLocation(double d0, double d1, double d2, float f, float f1) {
/*  758 */     this.lastX = this.locX = d0;
/*  759 */     this.lastY = this.locY = d1;
/*  760 */     this.lastZ = this.locZ = d2;
/*  761 */     this.lastYaw = this.yaw = f;
/*  762 */     this.lastPitch = this.pitch = f1;
/*  763 */     this.br = 0.0F;
/*  764 */     double d3 = (this.lastYaw - f);
/*      */     
/*  766 */     if (d3 < -180.0D) {
/*  767 */       this.lastYaw += 360.0F;
/*      */     }
/*      */     
/*  770 */     if (d3 >= 180.0D) {
/*  771 */       this.lastYaw -= 360.0F;
/*      */     }
/*      */     
/*  774 */     setPosition(this.locX, this.locY, this.locZ);
/*  775 */     c(f, f1);
/*      */   }
/*      */   
/*      */   public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
/*  779 */     this.bo = this.lastX = this.locX = d0;
/*  780 */     this.bp = this.lastY = this.locY = d1 + this.height;
/*  781 */     this.bq = this.lastZ = this.locZ = d2;
/*  782 */     this.yaw = f;
/*  783 */     this.pitch = f1;
/*  784 */     setPosition(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public float f(Entity entity) {
/*  788 */     float f = (float)(this.locX - entity.locX);
/*  789 */     float f1 = (float)(this.locY - entity.locY);
/*  790 */     float f2 = (float)(this.locZ - entity.locZ);
/*      */     
/*  792 */     return MathHelper.c(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */   
/*      */   public double e(double d0, double d1, double d2) {
/*  796 */     double d3 = this.locX - d0;
/*  797 */     double d4 = this.locY - d1;
/*  798 */     double d5 = this.locZ - d2;
/*      */     
/*  800 */     return d3 * d3 + d4 * d4 + d5 * d5;
/*      */   }
/*      */   
/*      */   public double f(double d0, double d1, double d2) {
/*  804 */     double d3 = this.locX - d0;
/*  805 */     double d4 = this.locY - d1;
/*  806 */     double d5 = this.locZ - d2;
/*      */     
/*  808 */     return MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
/*      */   }
/*      */   
/*      */   public double g(Entity entity) {
/*  812 */     double d0 = this.locX - entity.locX;
/*  813 */     double d1 = this.locY - entity.locY;
/*  814 */     double d2 = this.locZ - entity.locZ;
/*      */     
/*  816 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */   
/*      */   public void b(EntityHuman entityhuman) {}
/*      */   
/*      */   public void collide(Entity entity) {
/*  822 */     if (entity.passenger != this && entity.vehicle != this) {
/*  823 */       double d0 = entity.locX - this.locX;
/*  824 */       double d1 = entity.locZ - this.locZ;
/*  825 */       double d2 = MathHelper.a(d0, d1);
/*      */       
/*  827 */       if (d2 >= 0.009999999776482582D) {
/*  828 */         d2 = MathHelper.a(d2);
/*  829 */         d0 /= d2;
/*  830 */         d1 /= d2;
/*  831 */         double d3 = 1.0D / d2;
/*      */         
/*  833 */         if (d3 > 1.0D) {
/*  834 */           d3 = 1.0D;
/*      */         }
/*      */         
/*  837 */         d0 *= d3;
/*  838 */         d1 *= d3;
/*  839 */         d0 *= 0.05000000074505806D;
/*  840 */         d1 *= 0.05000000074505806D;
/*  841 */         d0 *= (1.0F - this.bu);
/*  842 */         d1 *= (1.0F - this.bu);
/*  843 */         b(-d0, 0.0D, -d1);
/*  844 */         entity.b(d0, 0.0D, d1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void b(double d0, double d1, double d2) {
/*  850 */     this.motX += d0;
/*  851 */     this.motY += d1;
/*  852 */     this.motZ += d2;
/*      */   }
/*      */ 
/*      */   
/*  856 */   protected void af() { this.velocityChanged = true; }
/*      */ 
/*      */   
/*      */   public boolean damageEntity(Entity entity, int i) {
/*  860 */     af();
/*  861 */     return false;
/*      */   }
/*      */ 
/*      */   
/*  865 */   public boolean l_() { return false; }
/*      */ 
/*      */ 
/*      */   
/*  869 */   public boolean d_() { return false; }
/*      */ 
/*      */   
/*      */   public void c(Entity entity, int i) {}
/*      */   
/*      */   public boolean c(NBTTagCompound nbttagcompound) {
/*  875 */     String s = ag();
/*      */     
/*  877 */     if (!this.dead && s != null) {
/*  878 */       nbttagcompound.setString("id", s);
/*  879 */       d(nbttagcompound);
/*  880 */       return true;
/*      */     } 
/*  882 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void d(NBTTagCompound nbttagcompound) {
/*  887 */     nbttagcompound.a("Pos", a(new double[] { this.locX, this.locY + this.br, this.locZ }));
/*  888 */     nbttagcompound.a("Motion", a(new double[] { this.motX, this.motY, this.motZ }));
/*      */ 
/*      */ 
/*      */     
/*  892 */     if (Float.isNaN(this.yaw)) {
/*  893 */       this.yaw = 0.0F;
/*      */     }
/*      */     
/*  896 */     if (Float.isNaN(this.pitch)) {
/*  897 */       this.pitch = 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  901 */     nbttagcompound.a("Rotation", a(new float[] { this.yaw, this.pitch }));
/*  902 */     nbttagcompound.a("FallDistance", this.fallDistance);
/*  903 */     nbttagcompound.a("Fire", (short)this.fireTicks);
/*  904 */     nbttagcompound.a("Air", (short)this.airTicks);
/*  905 */     nbttagcompound.a("OnGround", this.onGround);
/*      */     
/*  907 */     nbttagcompound.setLong("WorldUUIDLeast", this.world.getUUID().getLeastSignificantBits());
/*  908 */     nbttagcompound.setLong("WorldUUIDMost", this.world.getUUID().getMostSignificantBits());
/*  909 */     nbttagcompound.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
/*  910 */     nbttagcompound.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
/*      */     
/*  912 */     b(nbttagcompound);
/*      */   }
/*      */   
/*      */   public void e(NBTTagCompound nbttagcompound) {
/*  916 */     NBTTagList nbttaglist = nbttagcompound.l("Pos");
/*  917 */     NBTTagList nbttaglist1 = nbttagcompound.l("Motion");
/*  918 */     NBTTagList nbttaglist2 = nbttagcompound.l("Rotation");
/*      */     
/*  920 */     this.motX = ((NBTTagDouble)nbttaglist1.a(0)).a;
/*  921 */     this.motY = ((NBTTagDouble)nbttaglist1.a(1)).a;
/*  922 */     this.motZ = ((NBTTagDouble)nbttaglist1.a(2)).a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     this.lastX = this.bo = this.locX = ((NBTTagDouble)nbttaglist.a(0)).a;
/*  938 */     this.lastY = this.bp = this.locY = ((NBTTagDouble)nbttaglist.a(1)).a;
/*  939 */     this.lastZ = this.bq = this.locZ = ((NBTTagDouble)nbttaglist.a(2)).a;
/*  940 */     this.lastYaw = this.yaw = ((NBTTagFloat)nbttaglist2.a(0)).a;
/*  941 */     this.lastPitch = this.pitch = ((NBTTagFloat)nbttaglist2.a(1)).a;
/*  942 */     this.fallDistance = nbttagcompound.g("FallDistance");
/*  943 */     this.fireTicks = nbttagcompound.d("Fire");
/*  944 */     this.airTicks = nbttagcompound.d("Air");
/*  945 */     this.onGround = nbttagcompound.m("OnGround");
/*  946 */     setPosition(this.locX, this.locY, this.locZ);
/*      */ 
/*      */     
/*  949 */     long least = nbttagcompound.getLong("UUIDLeast");
/*  950 */     long most = nbttagcompound.getLong("UUIDMost");
/*      */     
/*  952 */     if (least != 0L && most != 0L) {
/*  953 */       this.uniqueId = new UUID(most, least);
/*      */     }
/*      */ 
/*      */     
/*  957 */     c(this.yaw, this.pitch);
/*  958 */     a(nbttagcompound);
/*      */ 
/*      */     
/*  961 */     if (!(getBukkitEntity() instanceof Vehicle)) {
/*  962 */       if (Math.abs(this.motX) > 10.0D) {
/*  963 */         this.motX = 0.0D;
/*      */       }
/*      */       
/*  966 */       if (Math.abs(this.motY) > 10.0D) {
/*  967 */         this.motY = 0.0D;
/*      */       }
/*      */       
/*  970 */       if (Math.abs(this.motZ) > 10.0D) {
/*  971 */         this.motZ = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  977 */     if (this instanceof EntityPlayer) {
/*  978 */       Server server = Bukkit.getServer();
/*  979 */       CraftWorld craftWorld = null;
/*      */ 
/*      */       
/*  982 */       String worldName = nbttagcompound.getString("World");
/*      */       
/*  984 */       if (nbttagcompound.hasKey("WorldUUIDMost") && nbttagcompound.hasKey("WorldUUIDLeast")) {
/*  985 */         UUID uid = new UUID(nbttagcompound.getLong("WorldUUIDMost"), nbttagcompound.getLong("WorldUUIDLeast"));
/*  986 */         craftWorld = server.getWorld(uid);
/*      */       } else {
/*  988 */         craftWorld = server.getWorld(worldName);
/*      */       } 
/*  990 */       if (craftWorld == null) {
/*  991 */         EntityPlayer entityPlayer = (EntityPlayer)this;
/*  992 */         craftWorld = ((CraftServer)server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
/*      */       } 
/*      */       
/*  995 */       spawnIn((craftWorld == null) ? null : ((CraftWorld)craftWorld).getHandle());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1001 */   protected final String ag() { return EntityTypes.b(this); }
/*      */ 
/*      */   
/*      */   protected abstract void a(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   protected abstract void b(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   protected NBTTagList a(double... adouble) {
/* 1009 */     NBTTagList nbttaglist = new NBTTagList();
/* 1010 */     double[] adouble1 = adouble;
/* 1011 */     int i = adouble.length;
/*      */     
/* 1013 */     for (int j = 0; j < i; j++) {
/* 1014 */       double d0 = adouble1[j];
/*      */       
/* 1016 */       nbttaglist.a(new NBTTagDouble(d0));
/*      */     } 
/*      */     
/* 1019 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   protected NBTTagList a(float... afloat) {
/* 1023 */     NBTTagList nbttaglist = new NBTTagList();
/* 1024 */     float[] afloat1 = afloat;
/* 1025 */     int i = afloat.length;
/*      */     
/* 1027 */     for (int j = 0; j < i; j++) {
/* 1028 */       float f = afloat1[j];
/*      */       
/* 1030 */       nbttaglist.a(new NBTTagFloat(f));
/*      */     } 
/*      */     
/* 1033 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */   
/* 1037 */   public EntityItem b(int i, int j) { return a(i, j, 0.0F); }
/*      */ 
/*      */ 
/*      */   
/* 1041 */   public EntityItem a(int i, int j, float f) { return a(new ItemStack(i, j, false), f); }
/*      */ 
/*      */   
/*      */   public EntityItem a(ItemStack itemstack, float f) {
/* 1045 */     EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + f, this.locZ, itemstack);
/*      */     
/* 1047 */     entityitem.pickupDelay = 10;
/* 1048 */     this.world.addEntity(entityitem);
/* 1049 */     return entityitem;
/*      */   }
/*      */ 
/*      */   
/* 1053 */   public boolean T() { return !this.dead; }
/*      */ 
/*      */   
/*      */   public boolean K() {
/* 1057 */     for (int i = 0; i < 8; i++) {
/* 1058 */       float f = (((i >> 0) % 2) - 0.5F) * this.length * 0.9F;
/* 1059 */       float f1 = (((i >> 1) % 2) - 0.5F) * 0.1F;
/* 1060 */       float f2 = (((i >> 2) % 2) - 0.5F) * this.length * 0.9F;
/* 1061 */       int j = MathHelper.floor(this.locX + f);
/* 1062 */       int k = MathHelper.floor(this.locY + t() + f1);
/* 1063 */       int l = MathHelper.floor(this.locZ + f2);
/*      */       
/* 1065 */       if (this.world.e(j, k, l)) {
/* 1066 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1070 */     return false;
/*      */   }
/*      */ 
/*      */   
/* 1074 */   public boolean a(EntityHuman entityhuman) { return false; }
/*      */ 
/*      */ 
/*      */   
/* 1078 */   public AxisAlignedBB a_(Entity entity) { return null; }
/*      */ 
/*      */   
/*      */   public void E() {
/* 1082 */     if (this.vehicle.dead) {
/* 1083 */       this.vehicle = null;
/*      */     } else {
/* 1085 */       this.motX = 0.0D;
/* 1086 */       this.motY = 0.0D;
/* 1087 */       this.motZ = 0.0D;
/* 1088 */       m_();
/* 1089 */       if (this.vehicle != null) {
/* 1090 */         this.vehicle.f();
/* 1091 */         this.e += (this.vehicle.yaw - this.vehicle.lastYaw);
/*      */         
/* 1093 */         for (this.d += (this.vehicle.pitch - this.vehicle.lastPitch); this.e >= 180.0D; this.e -= 360.0D);
/*      */ 
/*      */ 
/*      */         
/* 1097 */         while (this.e < -180.0D) {
/* 1098 */           this.e += 360.0D;
/*      */         }
/*      */         
/* 1101 */         while (this.d >= 180.0D) {
/* 1102 */           this.d -= 360.0D;
/*      */         }
/*      */         
/* 1105 */         while (this.d < -180.0D) {
/* 1106 */           this.d += 360.0D;
/*      */         }
/*      */         
/* 1109 */         double d0 = this.e * 0.5D;
/* 1110 */         double d1 = this.d * 0.5D;
/* 1111 */         float f = 10.0F;
/*      */         
/* 1113 */         if (d0 > f) {
/* 1114 */           d0 = f;
/*      */         }
/*      */         
/* 1117 */         if (d0 < -f) {
/* 1118 */           d0 = -f;
/*      */         }
/*      */         
/* 1121 */         if (d1 > f) {
/* 1122 */           d1 = f;
/*      */         }
/*      */         
/* 1125 */         if (d1 < -f) {
/* 1126 */           d1 = -f;
/*      */         }
/*      */         
/* 1129 */         this.e -= d0;
/* 1130 */         this.d -= d1;
/* 1131 */         this.yaw = (float)(this.yaw + d0);
/* 1132 */         this.pitch = (float)(this.pitch + d1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1138 */   public void f() { this.passenger.setPosition(this.locX, this.locY + m() + this.passenger.I(), this.locZ); }
/*      */ 
/*      */ 
/*      */   
/* 1142 */   public double I() { return this.height; }
/*      */ 
/*      */ 
/*      */   
/* 1146 */   public double m() { return this.width * 0.75D; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1151 */   public void mount(Entity entity) { setPassengerOf(entity); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getBukkitEntity() {
/* 1157 */     if (this.bukkitEntity == null) {
/* 1158 */       this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
/*      */     }
/* 1160 */     return this.bukkitEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPassengerOf(Entity entity) {
/* 1168 */     this.d = 0.0D;
/* 1169 */     this.e = 0.0D;
/* 1170 */     if (entity == null) {
/* 1171 */       if (this.vehicle != null) {
/*      */         
/* 1173 */         if (getBukkitEntity() instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
/* 1174 */           VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)getBukkitEntity());
/* 1175 */           this.world.getServer().getPluginManager().callEvent(event);
/*      */         } 
/*      */ 
/*      */         
/* 1179 */         setPositionRotation(this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.width, this.vehicle.locZ, this.yaw, this.pitch);
/* 1180 */         this.vehicle.passenger = null;
/*      */       } 
/*      */       
/* 1183 */       this.vehicle = null;
/* 1184 */     } else if (this.vehicle == entity) {
/*      */       
/* 1186 */       if (getBukkitEntity() instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
/* 1187 */         VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)getBukkitEntity());
/* 1188 */         this.world.getServer().getPluginManager().callEvent(event);
/*      */       } 
/*      */ 
/*      */       
/* 1192 */       this.vehicle.passenger = null;
/* 1193 */       this.vehicle = null;
/* 1194 */       setPositionRotation(entity.locX, entity.boundingBox.b + entity.width, entity.locZ, this.yaw, this.pitch);
/*      */     } else {
/* 1196 */       if (this.vehicle != null) {
/* 1197 */         this.vehicle.passenger = null;
/*      */       }
/*      */       
/* 1200 */       if (entity.passenger != null) {
/* 1201 */         entity.passenger.vehicle = null;
/*      */       }
/*      */       
/* 1204 */       this.vehicle = entity;
/* 1205 */       entity.passenger = this;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1210 */   public Vec3D Z() { return null; }
/*      */ 
/*      */   
/*      */   public void P() {}
/*      */ 
/*      */   
/* 1216 */   public ItemStack[] getEquipment() { return null; }
/*      */ 
/*      */ 
/*      */   
/* 1220 */   public boolean isSneaking() { return d(1); }
/*      */ 
/*      */ 
/*      */   
/* 1224 */   public void setSneak(boolean flag) { a(1, flag); }
/*      */ 
/*      */ 
/*      */   
/* 1228 */   protected boolean d(int i) { return ((this.datawatcher.a(0) & 1 << i) != 0); }
/*      */ 
/*      */   
/*      */   protected void a(int i, boolean flag) {
/* 1232 */     byte b0 = this.datawatcher.a(0);
/*      */     
/* 1234 */     if (flag) {
/* 1235 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 | 1 << i)));
/*      */     } else {
/* 1237 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 & (1 << i ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EntityWeatherStorm entityweatherstorm) {
/* 1243 */     EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(entityweatherstorm.getBukkitEntity(), getBukkitEntity(), EntityDamageEvent.DamageCause.LIGHTNING, 5);
/* 1244 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*      */     
/* 1246 */     if (event.isCancelled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1250 */     burn(event.getDamage());
/*      */ 
/*      */     
/* 1253 */     this.fireTicks++;
/* 1254 */     if (this.fireTicks == 0) {
/* 1255 */       this.fireTicks = 300;
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(EntityLiving entityliving) {}
/*      */   
/*      */   protected boolean g(double d0, double d1, double d2) {
/* 1262 */     int i = MathHelper.floor(d0);
/* 1263 */     int j = MathHelper.floor(d1);
/* 1264 */     int k = MathHelper.floor(d2);
/* 1265 */     double d3 = d0 - i;
/* 1266 */     double d4 = d1 - j;
/* 1267 */     double d5 = d2 - k;
/*      */     
/* 1269 */     if (this.world.e(i, j, k)) {
/* 1270 */       boolean flag = !this.world.e(i - 1, j, k);
/* 1271 */       boolean flag1 = !this.world.e(i + 1, j, k);
/* 1272 */       boolean flag2 = !this.world.e(i, j - 1, k);
/* 1273 */       boolean flag3 = !this.world.e(i, j + 1, k);
/* 1274 */       boolean flag4 = !this.world.e(i, j, k - 1);
/* 1275 */       boolean flag5 = !this.world.e(i, j, k + 1);
/* 1276 */       byte b0 = -1;
/* 1277 */       double d6 = 9999.0D;
/*      */       
/* 1279 */       if (flag && d3 < d6) {
/* 1280 */         d6 = d3;
/* 1281 */         b0 = 0;
/*      */       } 
/*      */       
/* 1284 */       if (flag1 && 1.0D - d3 < d6) {
/* 1285 */         d6 = 1.0D - d3;
/* 1286 */         b0 = 1;
/*      */       } 
/*      */       
/* 1289 */       if (flag2 && d4 < d6) {
/* 1290 */         d6 = d4;
/* 1291 */         b0 = 2;
/*      */       } 
/*      */       
/* 1294 */       if (flag3 && 1.0D - d4 < d6) {
/* 1295 */         d6 = 1.0D - d4;
/* 1296 */         b0 = 3;
/*      */       } 
/*      */       
/* 1299 */       if (flag4 && d5 < d6) {
/* 1300 */         d6 = d5;
/* 1301 */         b0 = 4;
/*      */       } 
/*      */       
/* 1304 */       if (flag5 && 1.0D - d5 < d6) {
/* 1305 */         d6 = 1.0D - d5;
/* 1306 */         b0 = 5;
/*      */       } 
/*      */       
/* 1309 */       float f = this.random.nextFloat() * 0.2F + 0.1F;
/*      */       
/* 1311 */       if (b0 == 0) {
/* 1312 */         this.motX = -f;
/*      */       }
/*      */       
/* 1315 */       if (b0 == 1) {
/* 1316 */         this.motX = f;
/*      */       }
/*      */       
/* 1319 */       if (b0 == 2) {
/* 1320 */         this.motY = -f;
/*      */       }
/*      */       
/* 1323 */       if (b0 == 3) {
/* 1324 */         this.motY = f;
/*      */       }
/*      */       
/* 1327 */       if (b0 == 4) {
/* 1328 */         this.motZ = -f;
/*      */       }
/*      */       
/* 1331 */       if (b0 == 5) {
/* 1332 */         this.motZ = f;
/*      */       }
/*      */     } 
/*      */     
/* 1336 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Entity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */