/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.TrigMath;
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class EntityLiving
/*     */   extends Entity
/*     */ {
/*  17 */   public int maxNoDamageTicks = 20;
/*     */   public float I;
/*     */   public float J;
/*  20 */   public float K = 0.0F;
/*  21 */   public float L = 0.0F;
/*     */   protected float M;
/*     */   protected float N;
/*     */   protected float O;
/*     */   protected float P;
/*     */   protected boolean Q = true;
/*  27 */   protected String texture = "/mob/char.png";
/*     */   protected boolean S = true;
/*  29 */   protected float T = 0.0F;
/*  30 */   protected String U = null;
/*  31 */   protected float V = 1.0F;
/*  32 */   protected int W = 0;
/*  33 */   protected float X = 0.0F;
/*     */   public boolean Y = false;
/*     */   public float Z;
/*     */   public float aa;
/*  37 */   public int health = 10;
/*     */   public int ac;
/*     */   private int a;
/*     */   public int hurtTicks;
/*     */   public int ae;
/*  42 */   public float af = 0.0F;
/*  43 */   public int deathTicks = 0;
/*  44 */   public int attackTicks = 0;
/*     */   public float ai;
/*     */   public float aj;
/*     */   protected boolean ak = false;
/*  48 */   public int al = -1;
/*  49 */   public float am = (float)(Math.random() * 0.8999999761581421D + 0.10000000149011612D);
/*     */   public float an;
/*     */   public float ao;
/*     */   public float ap;
/*     */   protected int aq;
/*     */   protected double ar;
/*     */   protected double as;
/*     */   protected double at;
/*     */   protected double au;
/*     */   protected double av;
/*  59 */   float aw = 0.0F;
/*  60 */   public int lastDamage = 0;
/*  61 */   protected int ay = 0;
/*     */   protected float az;
/*     */   protected float aA;
/*     */   protected float aB;
/*     */   protected boolean aC = false;
/*  66 */   protected float aD = 0.0F;
/*  67 */   protected float aE = 0.7F;
/*     */   private Entity b;
/*  69 */   protected int aF = 0;
/*     */   
/*     */   public EntityLiving(World world) {
/*  72 */     super(world);
/*  73 */     this.aI = true;
/*  74 */     this.J = (float)(Math.random() + 1.0D) * 0.01F;
/*  75 */     setPosition(this.locX, this.locY, this.locZ);
/*  76 */     this.I = (float)Math.random() * 12398.0F;
/*  77 */     this.yaw = (float)(Math.random() * 3.1415927410125732D * 2.0D);
/*  78 */     this.bs = 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b() {}
/*     */   
/*  84 */   public boolean e(Entity entity) { return (this.world.a(Vec3D.create(this.locX, this.locY + t(), this.locZ), Vec3D.create(entity.locX, entity.locY + entity.t(), entity.locZ)) == null); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean l_() { return !this.dead; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public boolean d_() { return !this.dead; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public float t() { return this.width * 0.85F; }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public int e() { return 80; }
/*     */ 
/*     */   
/*     */   public void Q() {
/* 104 */     String s = g();
/*     */     
/* 106 */     if (s != null) {
/* 107 */       this.world.makeSound(this, s, k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void R() {
/* 112 */     this.Z = this.aa;
/* 113 */     super.R();
/* 114 */     if (this.random.nextInt(1000) < this.a++) {
/* 115 */       this.a = -e();
/* 116 */       Q();
/*     */     } 
/*     */     
/* 119 */     if (T() && K()) {
/*     */       
/* 121 */       EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), EntityDamageEvent.DamageCause.SUFFOCATION, true);
/* 122 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 124 */       if (!event.isCancelled()) {
/* 125 */         damageEntity((Entity)null, event.getDamage());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 130 */     if (this.fireProof || this.world.isStatic) {
/* 131 */       this.fireTicks = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (T() && a(Material.WATER) && !b_()) {
/* 137 */       this.airTicks--;
/* 138 */       if (this.airTicks == -20) {
/* 139 */         this.airTicks = 0;
/*     */         
/* 141 */         for (int i = 0; i < 8; i++) {
/* 142 */           float f = this.random.nextFloat() - this.random.nextFloat();
/* 143 */           float f1 = this.random.nextFloat() - this.random.nextFloat();
/* 144 */           float f2 = this.random.nextFloat() - this.random.nextFloat();
/*     */           
/* 146 */           this.world.a("bubble", this.locX + f, this.locY + f1, this.locZ + f2, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */ 
/*     */         
/* 150 */         EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), EntityDamageEvent.DamageCause.DROWNING, 2);
/* 151 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 153 */         if (!event.isCancelled() && event.getDamage() != 0) {
/* 154 */           damageEntity((Entity)null, event.getDamage());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 159 */       this.fireTicks = 0;
/*     */     } else {
/* 161 */       this.airTicks = this.maxAirTicks;
/*     */     } 
/*     */     
/* 164 */     this.ai = this.aj;
/* 165 */     if (this.attackTicks > 0) {
/* 166 */       this.attackTicks--;
/*     */     }
/*     */     
/* 169 */     if (this.hurtTicks > 0) {
/* 170 */       this.hurtTicks--;
/*     */     }
/*     */     
/* 173 */     if (this.noDamageTicks > 0) {
/* 174 */       this.noDamageTicks--;
/*     */     }
/*     */     
/* 177 */     if (this.health <= 0) {
/* 178 */       this.deathTicks++;
/* 179 */       if (this.deathTicks > 20) {
/* 180 */         X();
/* 181 */         die();
/*     */         
/* 183 */         for (int i = 0; i < 20; i++) {
/* 184 */           double d0 = this.random.nextGaussian() * 0.02D;
/* 185 */           double d1 = this.random.nextGaussian() * 0.02D;
/* 186 */           double d2 = this.random.nextGaussian() * 0.02D;
/*     */           
/* 188 */           this.world.a("explode", this.locX + (this.random.nextFloat() * this.length * 2.0F) - this.length, this.locY + (this.random.nextFloat() * this.width), this.locZ + (this.random.nextFloat() * this.length * 2.0F) - this.length, d0, d1, d2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     this.P = this.O;
/* 194 */     this.L = this.K;
/* 195 */     this.lastYaw = this.yaw;
/* 196 */     this.lastPitch = this.pitch;
/*     */   }
/*     */   
/*     */   public void S() {
/* 200 */     for (int i = 0; i < 20; i++) {
/* 201 */       double d0 = this.random.nextGaussian() * 0.02D;
/* 202 */       double d1 = this.random.nextGaussian() * 0.02D;
/* 203 */       double d2 = this.random.nextGaussian() * 0.02D;
/* 204 */       double d3 = 10.0D;
/*     */       
/* 206 */       this.world.a("explode", this.locX + (this.random.nextFloat() * this.length * 2.0F) - this.length - d0 * d3, this.locY + (this.random.nextFloat() * this.width) - d1 * d3, this.locZ + (this.random.nextFloat() * this.length * 2.0F) - this.length - d2 * d3, d0, d1, d2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void E() {
/* 211 */     super.E();
/* 212 */     this.M = this.N;
/* 213 */     this.N = 0.0F;
/*     */   }
/*     */   
/*     */   public void m_() {
/* 217 */     super.m_();
/* 218 */     v();
/* 219 */     double d0 = this.locX - this.lastX;
/* 220 */     double d1 = this.locZ - this.lastZ;
/* 221 */     float f = MathHelper.a(d0 * d0 + d1 * d1);
/* 222 */     float f1 = this.K;
/* 223 */     float f2 = 0.0F;
/*     */     
/* 225 */     this.M = this.N;
/* 226 */     float f3 = 0.0F;
/*     */     
/* 228 */     if (f > 0.05F) {
/* 229 */       f3 = 1.0F;
/* 230 */       f2 = f * 3.0F;
/*     */       
/* 232 */       f1 = (float)TrigMath.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*     */     } 
/*     */     
/* 235 */     if (this.aa > 0.0F) {
/* 236 */       f1 = this.yaw;
/*     */     }
/*     */     
/* 239 */     if (!this.onGround) {
/* 240 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 243 */     this.N += (f3 - this.N) * 0.3F;
/*     */     
/*     */     float f4;
/*     */     
/* 247 */     for (f4 = f1 - this.K; f4 < -180.0F; f4 += 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 251 */     while (f4 >= 180.0F) {
/* 252 */       f4 -= 360.0F;
/*     */     }
/*     */     
/* 255 */     this.K += f4 * 0.3F;
/*     */     
/*     */     float f5;
/*     */     
/* 259 */     for (f5 = this.yaw - this.K; f5 < -180.0F; f5 += 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 263 */     while (f5 >= 180.0F) {
/* 264 */       f5 -= 360.0F;
/*     */     }
/*     */     
/* 267 */     boolean flag = (f5 < -90.0F || f5 >= 90.0F);
/*     */     
/* 269 */     if (f5 < -75.0F) {
/* 270 */       f5 = -75.0F;
/*     */     }
/*     */     
/* 273 */     if (f5 >= 75.0F) {
/* 274 */       f5 = 75.0F;
/*     */     }
/*     */     
/* 277 */     this.K = this.yaw - f5;
/* 278 */     if (f5 * f5 > 2500.0F) {
/* 279 */       this.K += f5 * 0.2F;
/*     */     }
/*     */     
/* 282 */     if (flag) {
/* 283 */       f2 *= -1.0F;
/*     */     }
/*     */     
/* 286 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 287 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 290 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 291 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 294 */     while (this.K - this.L < -180.0F) {
/* 295 */       this.L -= 360.0F;
/*     */     }
/*     */     
/* 298 */     while (this.K - this.L >= 180.0F) {
/* 299 */       this.L += 360.0F;
/*     */     }
/*     */     
/* 302 */     while (this.pitch - this.lastPitch < -180.0F) {
/* 303 */       this.lastPitch -= 360.0F;
/*     */     }
/*     */     
/* 306 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 307 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 310 */     this.O += f2;
/*     */   }
/*     */ 
/*     */   
/* 314 */   protected void b(float f, float f1) { super.b(f, f1); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 319 */   public void b(int i) { b(i, EntityRegainHealthEvent.RegainReason.CUSTOM); }
/*     */ 
/*     */   
/*     */   public void b(int i, EntityRegainHealthEvent.RegainReason regainReason) {
/* 323 */     if (this.health > 0) {
/* 324 */       EntityRegainHealthEvent event = new EntityRegainHealthEvent(getBukkitEntity(), i, regainReason);
/* 325 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 327 */       if (!event.isCancelled()) {
/* 328 */         this.health += event.getAmount();
/*     */       }
/*     */       
/* 331 */       if (this.health > 20) {
/* 332 */         this.health = 20;
/*     */       }
/*     */       
/* 335 */       this.noDamageTicks = this.maxNoDamageTicks / 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 340 */     if (this.world.isStatic) {
/* 341 */       return false;
/*     */     }
/* 343 */     this.ay = 0;
/* 344 */     if (this.health <= 0) {
/* 345 */       return false;
/*     */     }
/* 347 */     this.ao = 1.5F;
/* 348 */     boolean flag = true;
/*     */     
/* 350 */     if (this.noDamageTicks > this.maxNoDamageTicks / 2.0F) {
/* 351 */       if (i <= this.lastDamage) {
/* 352 */         return false;
/*     */       }
/*     */       
/* 355 */       c(i - this.lastDamage);
/* 356 */       this.lastDamage = i;
/* 357 */       flag = false;
/*     */     } else {
/* 359 */       this.lastDamage = i;
/* 360 */       this.ac = this.health;
/* 361 */       this.noDamageTicks = this.maxNoDamageTicks;
/* 362 */       c(i);
/* 363 */       this.hurtTicks = this.ae = 10;
/*     */     } 
/*     */     
/* 366 */     this.af = 0.0F;
/* 367 */     if (flag) {
/* 368 */       this.world.a(this, (byte)2);
/* 369 */       af();
/* 370 */       if (entity != null) {
/* 371 */         double d0 = entity.locX - this.locX;
/*     */         
/*     */         double d1;
/*     */         
/* 375 */         for (d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
/* 376 */           d0 = (Math.random() - Math.random()) * 0.01D;
/*     */         }
/*     */         
/* 379 */         this.af = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - this.yaw;
/* 380 */         a(entity, i, d0, d1);
/*     */       } else {
/* 382 */         this.af = ((int)(Math.random() * 2.0D) * 180);
/*     */       } 
/*     */     } 
/*     */     
/* 386 */     if (this.health <= 0) {
/* 387 */       if (flag) {
/* 388 */         this.world.makeSound(this, i(), k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 391 */       die(entity);
/* 392 */     } else if (flag) {
/* 393 */       this.world.makeSound(this, h(), k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */     } 
/*     */     
/* 396 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   protected void c(int i) { this.health -= i; }
/*     */ 
/*     */ 
/*     */   
/* 406 */   protected float k() { return 1.0F; }
/*     */ 
/*     */ 
/*     */   
/* 410 */   protected String g() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 414 */   protected String h() { return "random.hurt"; }
/*     */ 
/*     */ 
/*     */   
/* 418 */   protected String i() { return "random.hurt"; }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, int i, double d0, double d1) {
/* 422 */     float f = MathHelper.a(d0 * d0 + d1 * d1);
/* 423 */     float f1 = 0.4F;
/*     */     
/* 425 */     this.motX /= 2.0D;
/* 426 */     this.motY /= 2.0D;
/* 427 */     this.motZ /= 2.0D;
/* 428 */     this.motX -= d0 / f * f1;
/* 429 */     this.motY += 0.4000000059604645D;
/* 430 */     this.motZ -= d1 / f * f1;
/* 431 */     if (this.motY > 0.4000000059604645D) {
/* 432 */       this.motY = 0.4000000059604645D;
/*     */     }
/*     */   }
/*     */   
/*     */   public void die(Entity entity) {
/* 437 */     if (this.W >= 0 && entity != null) {
/* 438 */       entity.c(this, this.W);
/*     */     }
/*     */     
/* 441 */     if (entity != null) {
/* 442 */       entity.a(this);
/*     */     }
/*     */     
/* 445 */     this.ak = true;
/* 446 */     if (!this.world.isStatic) {
/* 447 */       q();
/*     */     }
/*     */     
/* 450 */     this.world.a(this, (byte)3);
/*     */   }
/*     */   
/*     */   protected void q() {
/* 454 */     int i = j();
/*     */ 
/*     */     
/* 457 */     List<ItemStack> loot = new ArrayList<ItemStack>();
/* 458 */     int count = this.random.nextInt(3);
/*     */     
/* 460 */     if (i > 0 && count > 0) {
/* 461 */       loot.add(new ItemStack(i, count));
/*     */     }
/*     */     
/* 464 */     CraftEntity entity = (CraftEntity)getBukkitEntity();
/* 465 */     EntityDeathEvent event = new EntityDeathEvent(entity, loot);
/* 466 */     CraftWorld craftWorld = this.world.getWorld();
/* 467 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 469 */     for (ItemStack stack : event.getDrops()) {
/* 470 */       craftWorld.dropItemNaturally(entity.getLocation(), stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 476 */   protected int j() { return 0; }
/*     */ 
/*     */   
/*     */   protected void a(float f) {
/* 480 */     super.a(f);
/* 481 */     int i = (int)Math.ceil((f - 3.0F));
/*     */     
/* 483 */     if (i > 0) {
/*     */       
/* 485 */       EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), EntityDamageEvent.DamageCause.FALL, i);
/* 486 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 488 */       if (!event.isCancelled() && event.getDamage() != 0) {
/* 489 */         damageEntity((Entity)null, event.getDamage());
/*     */       }
/*     */ 
/*     */       
/* 493 */       int j = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.20000000298023224D - this.height), MathHelper.floor(this.locZ));
/*     */       
/* 495 */       if (j > 0) {
/* 496 */         StepSound stepsound = (Block.byId[j]).stepSound;
/*     */         
/* 498 */         this.world.makeSound(this, stepsound.getName(), stepsound.getVolume1() * 0.5F, stepsound.getVolume2() * 0.75F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(float f, float f1) {
/* 506 */     if (ad()) {
/* 507 */       double d0 = this.locY;
/* 508 */       a(f, f1, 0.02F);
/* 509 */       move(this.motX, this.motY, this.motZ);
/* 510 */       this.motX *= 0.800000011920929D;
/* 511 */       this.motY *= 0.800000011920929D;
/* 512 */       this.motZ *= 0.800000011920929D;
/* 513 */       this.motY -= 0.02D;
/* 514 */       if (this.positionChanged && d(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ)) {
/* 515 */         this.motY = 0.30000001192092896D;
/*     */       }
/* 517 */     } else if (ae()) {
/* 518 */       double d0 = this.locY;
/* 519 */       a(f, f1, 0.02F);
/* 520 */       move(this.motX, this.motY, this.motZ);
/* 521 */       this.motX *= 0.5D;
/* 522 */       this.motY *= 0.5D;
/* 523 */       this.motZ *= 0.5D;
/* 524 */       this.motY -= 0.02D;
/* 525 */       if (this.positionChanged && d(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ)) {
/* 526 */         this.motY = 0.30000001192092896D;
/*     */       }
/*     */     } else {
/* 529 */       float f2 = 0.91F;
/*     */       
/* 531 */       if (this.onGround) {
/* 532 */         f2 = 0.54600006F;
/* 533 */         int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/*     */         
/* 535 */         if (i > 0) {
/* 536 */           f2 = (Block.byId[i]).frictionFactor * 0.91F;
/*     */         }
/*     */       } 
/*     */       
/* 540 */       float f3 = 0.16277136F / f2 * f2 * f2;
/*     */       
/* 542 */       a(f, f1, this.onGround ? (0.1F * f3) : 0.02F);
/* 543 */       f2 = 0.91F;
/* 544 */       if (this.onGround) {
/* 545 */         f2 = 0.54600006F;
/* 546 */         int j = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/*     */         
/* 548 */         if (j > 0) {
/* 549 */           f2 = (Block.byId[j]).frictionFactor * 0.91F;
/*     */         }
/*     */       } 
/*     */       
/* 553 */       if (p()) {
/* 554 */         float f4 = 0.15F;
/*     */         
/* 556 */         if (this.motX < -f4) {
/* 557 */           this.motX = -f4;
/*     */         }
/*     */         
/* 560 */         if (this.motX > f4) {
/* 561 */           this.motX = f4;
/*     */         }
/*     */         
/* 564 */         if (this.motZ < -f4) {
/* 565 */           this.motZ = -f4;
/*     */         }
/*     */         
/* 568 */         if (this.motZ > f4) {
/* 569 */           this.motZ = f4;
/*     */         }
/*     */         
/* 572 */         this.fallDistance = 0.0F;
/* 573 */         if (this.motY < -0.15D) {
/* 574 */           this.motY = -0.15D;
/*     */         }
/*     */         
/* 577 */         if (isSneaking() && this.motY < 0.0D) {
/* 578 */           this.motY = 0.0D;
/*     */         }
/*     */       } 
/*     */       
/* 582 */       move(this.motX, this.motY, this.motZ);
/* 583 */       if (this.positionChanged && p()) {
/* 584 */         this.motY = 0.2D;
/*     */       }
/*     */       
/* 587 */       this.motY -= 0.08D;
/* 588 */       this.motY *= 0.9800000190734863D;
/* 589 */       this.motX *= f2;
/* 590 */       this.motZ *= f2;
/*     */     } 
/*     */     
/* 593 */     this.an = this.ao;
/* 594 */     double d0 = this.locX - this.lastX;
/* 595 */     double d1 = this.locZ - this.lastZ;
/* 596 */     float f5 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;
/*     */     
/* 598 */     if (f5 > 1.0F) {
/* 599 */       f5 = 1.0F;
/*     */     }
/*     */     
/* 602 */     this.ao += (f5 - this.ao) * 0.4F;
/* 603 */     this.ap += this.ao;
/*     */   }
/*     */   
/*     */   public boolean p() {
/* 607 */     int i = MathHelper.floor(this.locX);
/* 608 */     int j = MathHelper.floor(this.boundingBox.b);
/* 609 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 611 */     return (this.world.getTypeId(i, j, k) == Block.LADDER.id);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 615 */     nbttagcompound.a("Health", (short)this.health);
/* 616 */     nbttagcompound.a("HurtTime", (short)this.hurtTicks);
/* 617 */     nbttagcompound.a("DeathTime", (short)this.deathTicks);
/* 618 */     nbttagcompound.a("AttackTime", (short)this.attackTicks);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 622 */     this.health = nbttagcompound.d("Health");
/* 623 */     if (!nbttagcompound.hasKey("Health")) {
/* 624 */       this.health = 10;
/*     */     }
/*     */     
/* 627 */     this.hurtTicks = nbttagcompound.d("HurtTime");
/* 628 */     this.deathTicks = nbttagcompound.d("DeathTime");
/* 629 */     this.attackTicks = nbttagcompound.d("AttackTime");
/*     */   }
/*     */ 
/*     */   
/* 633 */   public boolean T() { return (!this.dead && this.health > 0); }
/*     */ 
/*     */ 
/*     */   
/* 637 */   public boolean b_() { return false; }
/*     */ 
/*     */   
/*     */   public void v() {
/* 641 */     if (this.aq > 0) {
/* 642 */       double d0 = this.locX + (this.ar - this.locX) / this.aq;
/* 643 */       double d1 = this.locY + (this.as - this.locY) / this.aq;
/* 644 */       double d2 = this.locZ + (this.at - this.locZ) / this.aq;
/*     */       
/*     */       double d3;
/*     */       
/* 648 */       for (d3 = this.au - this.yaw; d3 < -180.0D; d3 += 360.0D);
/*     */ 
/*     */ 
/*     */       
/* 652 */       while (d3 >= 180.0D) {
/* 653 */         d3 -= 360.0D;
/*     */       }
/*     */       
/* 656 */       this.yaw = (float)(this.yaw + d3 / this.aq);
/* 657 */       this.pitch = (float)(this.pitch + (this.av - this.pitch) / this.aq);
/* 658 */       this.aq--;
/* 659 */       setPosition(d0, d1, d2);
/* 660 */       c(this.yaw, this.pitch);
/* 661 */       List list = this.world.getEntities(this, this.boundingBox.shrink(0.03125D, 0.0D, 0.03125D));
/*     */       
/* 663 */       if (list.size() > 0) {
/* 664 */         double d4 = 0.0D;
/*     */         
/* 666 */         for (int i = 0; i < list.size(); i++) {
/* 667 */           AxisAlignedBB axisalignedbb = (AxisAlignedBB)list.get(i);
/*     */           
/* 669 */           if (axisalignedbb.e > d4) {
/* 670 */             d4 = axisalignedbb.e;
/*     */           }
/*     */         } 
/*     */         
/* 674 */         d1 += d4 - this.boundingBox.b;
/* 675 */         setPosition(d0, d1, d2);
/*     */       } 
/*     */     } 
/*     */     
/* 679 */     if (D()) {
/* 680 */       this.aC = false;
/* 681 */       this.az = 0.0F;
/* 682 */       this.aA = 0.0F;
/* 683 */       this.aB = 0.0F;
/* 684 */     } else if (!this.Y) {
/* 685 */       c_();
/*     */     } 
/*     */     
/* 688 */     boolean flag = ad();
/* 689 */     boolean flag1 = ae();
/*     */     
/* 691 */     if (this.aC) {
/* 692 */       if (flag) {
/* 693 */         this.motY += 0.03999999910593033D;
/* 694 */       } else if (flag1) {
/* 695 */         this.motY += 0.03999999910593033D;
/* 696 */       } else if (this.onGround) {
/* 697 */         O();
/*     */       } 
/*     */     }
/*     */     
/* 701 */     this.az *= 0.98F;
/* 702 */     this.aA *= 0.98F;
/* 703 */     this.aB *= 0.9F;
/* 704 */     a(this.az, this.aA);
/* 705 */     List list1 = this.world.b(this, this.boundingBox.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */     
/* 707 */     if (list1 != null && list1.size() > 0) {
/* 708 */       for (int j = 0; j < list1.size(); j++) {
/* 709 */         Entity entity = (Entity)list1.get(j);
/*     */         
/* 711 */         if (entity.d_()) {
/* 712 */           entity.collide(this);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 719 */   protected boolean D() { return (this.health <= 0); }
/*     */ 
/*     */ 
/*     */   
/* 723 */   protected void O() { this.motY = 0.41999998688697815D; }
/*     */ 
/*     */ 
/*     */   
/* 727 */   protected boolean h_() { return true; }
/*     */ 
/*     */   
/*     */   protected void U() {
/* 731 */     EntityHuman entityhuman = this.world.findNearbyPlayer(this, -1.0D);
/*     */     
/* 733 */     if (h_() && entityhuman != null) {
/* 734 */       double d0 = entityhuman.locX - this.locX;
/* 735 */       double d1 = entityhuman.locY - this.locY;
/* 736 */       double d2 = entityhuman.locZ - this.locZ;
/* 737 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */       
/* 739 */       if (d3 > 16384.0D) {
/* 740 */         die();
/*     */       }
/*     */       
/* 743 */       if (this.ay > 600 && this.random.nextInt(800) == 0) {
/* 744 */         if (d3 < 1024.0D) {
/* 745 */           this.ay = 0;
/*     */         } else {
/* 747 */           die();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void c_() {
/* 754 */     this.ay++;
/* 755 */     EntityHuman entityhuman = this.world.findNearbyPlayer(this, -1.0D);
/*     */     
/* 757 */     U();
/* 758 */     this.az = 0.0F;
/* 759 */     this.aA = 0.0F;
/* 760 */     float f = 8.0F;
/*     */     
/* 762 */     if (this.random.nextFloat() < 0.02F) {
/* 763 */       entityhuman = this.world.findNearbyPlayer(this, f);
/* 764 */       if (entityhuman != null) {
/* 765 */         this.b = entityhuman;
/* 766 */         this.aF = 10 + this.random.nextInt(20);
/*     */       } else {
/* 768 */         this.aB = (this.random.nextFloat() - 0.5F) * 20.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 772 */     if (this.b != null) {
/* 773 */       a(this.b, 10.0F, u());
/* 774 */       if (this.aF-- <= 0 || this.b.dead || this.b.g(this) > (f * f)) {
/* 775 */         this.b = null;
/*     */       }
/*     */     } else {
/* 778 */       if (this.random.nextFloat() < 0.05F) {
/* 779 */         this.aB = (this.random.nextFloat() - 0.5F) * 20.0F;
/*     */       }
/*     */       
/* 782 */       this.yaw += this.aB;
/* 783 */       this.pitch = this.aD;
/*     */     } 
/*     */     
/* 786 */     boolean flag = ad();
/* 787 */     boolean flag1 = ae();
/*     */     
/* 789 */     if (flag || flag1) {
/* 790 */       this.aC = (this.random.nextFloat() < 0.8F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 795 */   protected int u() { return 40; }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, float f, float f1) {
/* 799 */     double d2, d0 = entity.locX - this.locX;
/* 800 */     double d1 = entity.locZ - this.locZ;
/*     */ 
/*     */     
/* 803 */     if (entity instanceof EntityLiving) {
/* 804 */       EntityLiving entityliving = (EntityLiving)entity;
/*     */       
/* 806 */       d2 = this.locY + t() - entityliving.locY + entityliving.t();
/*     */     } else {
/* 808 */       d2 = (entity.boundingBox.b + entity.boundingBox.e) / 2.0D - this.locY + t();
/*     */     } 
/*     */     
/* 811 */     double d3 = MathHelper.a(d0 * d0 + d1 * d1);
/* 812 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 813 */     float f3 = (float)-(Math.atan2(d2, d3) * 180.0D / 3.1415927410125732D);
/*     */     
/* 815 */     this.pitch = -b(this.pitch, f3, f1);
/* 816 */     this.yaw = b(this.yaw, f2, f);
/*     */   }
/*     */ 
/*     */   
/* 820 */   public boolean V() { return (this.b != null); }
/*     */ 
/*     */ 
/*     */   
/* 824 */   public Entity W() { return this.b; }
/*     */ 
/*     */ 
/*     */   
/*     */   private float b(float f, float f1, float f2) {
/*     */     float f3;
/* 830 */     for (f3 = f1 - f; f3 < -180.0F; f3 += 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 834 */     while (f3 >= 180.0F) {
/* 835 */       f3 -= 360.0F;
/*     */     }
/*     */     
/* 838 */     if (f3 > f2) {
/* 839 */       f3 = f2;
/*     */     }
/*     */     
/* 842 */     if (f3 < -f2) {
/* 843 */       f3 = -f2;
/*     */     }
/*     */     
/* 846 */     return f + f3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void X() {}
/*     */   
/* 852 */   public boolean d() { return (this.world.containsEntity(this.boundingBox) && this.world.getEntities(this, this.boundingBox).size() == 0 && !this.world.c(this.boundingBox)); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void Y() {
/* 857 */     EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(null, getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, 4);
/* 858 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 860 */     if (event.isCancelled() || event.getDamage() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 864 */     damageEntity((Entity)null, event.getDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 869 */   public Vec3D Z() { return b(1.0F); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3D b(float f) {
/* 878 */     if (f == 1.0F) {
/* 879 */       float f1 = MathHelper.cos(-this.yaw * 0.017453292F - 3.1415927F);
/* 880 */       float f2 = MathHelper.sin(-this.yaw * 0.017453292F - 3.1415927F);
/* 881 */       float f3 = -MathHelper.cos(-this.pitch * 0.017453292F);
/* 882 */       float f4 = MathHelper.sin(-this.pitch * 0.017453292F);
/* 883 */       return Vec3D.create((f2 * f3), f4, (f1 * f3));
/*     */     } 
/* 885 */     float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
/* 886 */     float f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;
/* 887 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 888 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 889 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 890 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*     */     
/* 892 */     return Vec3D.create((f4 * f5), f6, (f3 * f5));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 897 */   public int l() { return 4; }
/*     */ 
/*     */ 
/*     */   
/* 901 */   public boolean isSleeping() { return false; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityLiving.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */