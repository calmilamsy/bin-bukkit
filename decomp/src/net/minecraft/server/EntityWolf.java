/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ 
/*     */ public class EntityWolf
/*     */   extends EntityAnimal
/*     */ {
/*     */   private boolean a = false;
/*     */   private float b;
/*     */   private float c;
/*     */   private boolean f;
/*     */   private boolean g;
/*     */   private float h;
/*     */   private float i;
/*     */   
/*     */   public EntityWolf(World world) {
/*  26 */     super(world);
/*  27 */     this.texture = "/mob/wolf.png";
/*  28 */     b(0.8F, 0.8F);
/*  29 */     this.aE = 1.1F;
/*  30 */     this.health = 8;
/*     */   }
/*     */   
/*     */   protected void b() {
/*  34 */     super.b();
/*  35 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*  36 */     this.datawatcher.a(17, "");
/*  37 */     this.datawatcher.a(18, new Integer(this.health));
/*     */   }
/*     */ 
/*     */   
/*  41 */   protected boolean n() { return false; }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  45 */     super.b(nbttagcompound);
/*  46 */     nbttagcompound.a("Angry", isAngry());
/*  47 */     nbttagcompound.a("Sitting", isSitting());
/*  48 */     if (getOwnerName() == null) {
/*  49 */       nbttagcompound.setString("Owner", "");
/*     */     } else {
/*  51 */       nbttagcompound.setString("Owner", getOwnerName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  56 */     super.a(nbttagcompound);
/*  57 */     setAngry(nbttagcompound.m("Angry"));
/*  58 */     setSitting(nbttagcompound.m("Sitting"));
/*  59 */     String s = nbttagcompound.getString("Owner");
/*     */     
/*  61 */     if (s.length() > 0) {
/*  62 */       setOwnerName(s);
/*  63 */       setTamed(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  68 */   protected boolean h_() { return !isTamed(); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   protected String g() { return isAngry() ? "mob.wolf.growl" : ((this.random.nextInt(3) == 0) ? ((isTamed() && this.datawatcher.b(18) < 10) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark"); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   protected String h() { return "mob.wolf.hurt"; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected String i() { return "mob.wolf.death"; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   protected float k() { return 0.4F; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected int j() { return -1; }
/*     */ 
/*     */   
/*     */   protected void c_() {
/*  92 */     super.c_();
/*  93 */     if (!this.e && !C() && isTamed() && this.vehicle == null) {
/*  94 */       EntityHuman entityhuman = this.world.a(getOwnerName());
/*     */       
/*  96 */       if (entityhuman != null) {
/*  97 */         float f = entityhuman.f(this);
/*     */         
/*  99 */         if (f > 5.0F) {
/* 100 */           c(entityhuman, f);
/*     */         }
/* 102 */       } else if (!ad()) {
/* 103 */         setSitting(true);
/*     */       } 
/* 105 */     } else if (this.target == null && !C() && !isTamed() && this.world.random.nextInt(100) == 0) {
/* 106 */       List list = this.world.a(EntitySheep.class, AxisAlignedBB.b(this.locX, this.locY, this.locZ, this.locX + 1.0D, this.locY + 1.0D, this.locZ + 1.0D).b(16.0D, 4.0D, 16.0D));
/*     */       
/* 108 */       if (!list.isEmpty()) {
/*     */         
/* 110 */         Entity entity = (Entity)list.get(this.world.random.nextInt(list.size()));
/* 111 */         Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
/*     */         
/* 113 */         EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.RANDOM_TARGET);
/* 114 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 116 */         if (!event.isCancelled() || event.getTarget() != null) {
/* 117 */           setTarget(entity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (ad()) {
/* 124 */       setSitting(false);
/*     */     }
/*     */     
/* 127 */     if (!this.world.isStatic) {
/* 128 */       this.datawatcher.watch(18, Integer.valueOf(this.health));
/*     */     }
/*     */   }
/*     */   
/*     */   public void v() {
/* 133 */     super.v();
/* 134 */     this.a = false;
/* 135 */     if (V() && !C() && !isAngry()) {
/* 136 */       Entity entity = W();
/*     */       
/* 138 */       if (entity instanceof EntityHuman) {
/* 139 */         EntityHuman entityhuman = (EntityHuman)entity;
/* 140 */         ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */         
/* 142 */         if (itemstack != null) {
/* 143 */           if (!isTamed() && itemstack.id == Item.BONE.id) {
/* 144 */             this.a = true;
/* 145 */           } else if (isTamed() && Item.byId[itemstack.id] instanceof ItemFood) {
/* 146 */             this.a = ((ItemFood)Item.byId[itemstack.id]).l();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     if (!this.Y && this.f && !this.g && !C() && this.onGround) {
/* 153 */       this.g = true;
/* 154 */       this.h = 0.0F;
/* 155 */       this.i = 0.0F;
/* 156 */       this.world.a(this, (byte)8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void m_() {
/* 161 */     super.m_();
/* 162 */     this.c = this.b;
/* 163 */     if (this.a) {
/* 164 */       this.b += (1.0F - this.b) * 0.4F;
/*     */     } else {
/* 166 */       this.b += (0.0F - this.b) * 0.4F;
/*     */     } 
/*     */     
/* 169 */     if (this.a) {
/* 170 */       this.aF = 10;
/*     */     }
/*     */     
/* 173 */     if (ac()) {
/* 174 */       this.f = true;
/* 175 */       this.g = false;
/* 176 */       this.h = 0.0F;
/* 177 */       this.i = 0.0F;
/* 178 */     } else if ((this.f || this.g) && this.g) {
/* 179 */       if (this.h == 0.0F) {
/* 180 */         this.world.makeSound(this, "mob.wolf.shake", k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 183 */       this.i = this.h;
/* 184 */       this.h += 0.05F;
/* 185 */       if (this.i >= 2.0F) {
/* 186 */         this.f = false;
/* 187 */         this.g = false;
/* 188 */         this.i = 0.0F;
/* 189 */         this.h = 0.0F;
/*     */       } 
/*     */       
/* 192 */       if (this.h > 0.4F) {
/* 193 */         float f = (float)this.boundingBox.b;
/* 194 */         int i = (int)(MathHelper.sin((this.h - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 196 */         for (int j = 0; j < i; j++) {
/* 197 */           float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length * 0.5F;
/* 198 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length * 0.5F;
/*     */           
/* 200 */           this.world.a("splash", this.locX + f1, (f + 0.8F), this.locZ + f2, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 207 */   public float t() { return this.width * 0.8F; }
/*     */ 
/*     */ 
/*     */   
/* 211 */   protected int u() { return isSitting() ? 20 : super.u(); }
/*     */ 
/*     */   
/*     */   private void c(Entity entity, float f) {
/* 215 */     PathEntity pathentity = this.world.findPath(this, entity, 16.0F);
/*     */     
/* 217 */     if (pathentity == null && f > 12.0F) {
/* 218 */       int i = MathHelper.floor(entity.locX) - 2;
/* 219 */       int j = MathHelper.floor(entity.locZ) - 2;
/* 220 */       int k = MathHelper.floor(entity.boundingBox.b);
/*     */       
/* 222 */       for (int l = 0; l <= 4; l++) {
/* 223 */         for (int i1 = 0; i1 <= 4; i1++) {
/* 224 */           if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.world.e(i + l, k - 1, j + i1) && !this.world.e(i + l, k, j + i1) && !this.world.e(i + l, k + 1, j + i1)) {
/* 225 */             setPositionRotation(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.yaw, this.pitch);
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 231 */       setPathEntity(pathentity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 236 */   protected boolean w() { return (isSitting() || this.g); }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 240 */     setSitting(false);
/* 241 */     if (entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow)) {
/* 242 */       i = (i + 1) / 2;
/*     */     }
/*     */     
/* 245 */     if (!super.damageEntity(entity, i)) {
/* 246 */       return false;
/*     */     }
/* 248 */     if (!isTamed() && !isAngry()) {
/* 249 */       if (entity instanceof EntityHuman) {
/*     */         
/* 251 */         Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
/*     */         
/* 253 */         EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
/* 254 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 256 */         if (!event.isCancelled()) {
/* 257 */           if (event.getTarget() == null) {
/* 258 */             this.target = null;
/*     */           } else {
/* 260 */             setAngry(true);
/* 261 */             this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 267 */       if (entity instanceof EntityArrow && ((EntityArrow)entity).shooter != null) {
/* 268 */         entity = ((EntityArrow)entity).shooter;
/*     */       }
/*     */       
/* 271 */       if (entity instanceof EntityLiving) {
/* 272 */         List list = this.world.a(EntityWolf.class, AxisAlignedBB.b(this.locX, this.locY, this.locZ, this.locX + 1.0D, this.locY + 1.0D, this.locZ + 1.0D).b(16.0D, 4.0D, 16.0D));
/* 273 */         Iterator iterator = list.iterator();
/*     */         
/* 275 */         while (iterator.hasNext()) {
/* 276 */           Entity entity1 = (Entity)iterator.next();
/* 277 */           EntityWolf entitywolf = (EntityWolf)entity1;
/*     */           
/* 279 */           if (!entitywolf.isTamed() && entitywolf.target == null)
/*     */           {
/* 281 */             Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
/*     */             
/* 283 */             EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
/* 284 */             this.world.getServer().getPluginManager().callEvent(event);
/*     */             
/* 286 */             if (!event.isCancelled()) {
/* 287 */               if (event.getTarget() == null) {
/* 288 */                 this.target = null; continue;
/*     */               } 
/* 290 */               entitywolf.target = entity;
/* 291 */               if (entity instanceof EntityHuman) {
/* 292 */                 entitywolf.setAngry(true);
/*     */               }
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 300 */     } else if (entity != this && entity != null) {
/* 301 */       if (isTamed() && entity instanceof EntityHuman && ((EntityHuman)entity).name.equalsIgnoreCase(getOwnerName())) {
/* 302 */         return true;
/*     */       }
/*     */       
/* 305 */       this.target = entity;
/*     */     } 
/*     */     
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 313 */   protected Entity findTarget() { return isAngry() ? this.world.findNearbyPlayer(this, 16.0D) : null; }
/*     */ 
/*     */   
/*     */   protected void a(Entity entity, float f) {
/* 317 */     if (f > 2.0F && f < 6.0F && this.random.nextInt(10) == 0) {
/* 318 */       if (this.onGround) {
/* 319 */         double d0 = entity.locX - this.locX;
/* 320 */         double d1 = entity.locZ - this.locZ;
/* 321 */         float f1 = MathHelper.a(d0 * d0 + d1 * d1);
/*     */         
/* 323 */         this.motX = d0 / f1 * 0.5D * 0.800000011920929D + this.motX * 0.20000000298023224D;
/* 324 */         this.motZ = d1 / f1 * 0.5D * 0.800000011920929D + this.motZ * 0.20000000298023224D;
/* 325 */         this.motY = 0.4000000059604645D;
/*     */       } 
/* 327 */     } else if (f < 1.5D && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
/* 328 */       this.attackTicks = 20;
/* 329 */       byte b0 = 2;
/*     */       
/* 331 */       if (isTamed()) {
/* 332 */         b0 = 4;
/*     */       }
/*     */       
/* 335 */       Entity damager = getBukkitEntity();
/* 336 */       Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
/*     */       
/* 338 */       EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, b0);
/* 339 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 341 */       if (event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 346 */       entity.damageEntity(this, b0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 351 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 353 */     if (!isTamed()) {
/* 354 */       if (itemstack != null && itemstack.id == Item.BONE.id && !isAngry()) {
/* 355 */         itemstack.count--;
/* 356 */         if (itemstack.count <= 0) {
/* 357 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */         }
/*     */         
/* 360 */         if (!this.world.isStatic)
/*     */         {
/* 362 */           if (this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
/*     */             
/* 364 */             setTamed(true);
/* 365 */             setPathEntity((PathEntity)null);
/* 366 */             setSitting(true);
/* 367 */             this.health = 20;
/* 368 */             setOwnerName(entityhuman.name);
/* 369 */             a(true);
/* 370 */             this.world.a(this, (byte)7);
/*     */           } else {
/* 372 */             a(false);
/* 373 */             this.world.a(this, (byte)6);
/*     */           } 
/*     */         }
/*     */         
/* 377 */         return true;
/*     */       } 
/*     */     } else {
/* 380 */       if (itemstack != null && Item.byId[itemstack.id] instanceof ItemFood) {
/* 381 */         ItemFood itemfood = (ItemFood)Item.byId[itemstack.id];
/*     */         
/* 383 */         if (itemfood.l() && this.datawatcher.b(18) < 20) {
/* 384 */           itemstack.count--;
/* 385 */           if (itemstack.count <= 0) {
/* 386 */             entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */           }
/*     */           
/* 389 */           b(((ItemFood)Item.PORK).k(), EntityRegainHealthEvent.RegainReason.EATING);
/* 390 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 394 */       if (entityhuman.name.equalsIgnoreCase(getOwnerName())) {
/* 395 */         if (!this.world.isStatic) {
/* 396 */           setSitting(!isSitting());
/* 397 */           this.aC = false;
/* 398 */           setPathEntity((PathEntity)null);
/*     */         } 
/*     */         
/* 401 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     return false;
/*     */   }
/*     */   
/*     */   void a(boolean flag) {
/* 409 */     String s = "heart";
/*     */     
/* 411 */     if (!flag) {
/* 412 */       s = "smoke";
/*     */     }
/*     */     
/* 415 */     for (int i = 0; i < 7; i++) {
/* 416 */       double d0 = this.random.nextGaussian() * 0.02D;
/* 417 */       double d1 = this.random.nextGaussian() * 0.02D;
/* 418 */       double d2 = this.random.nextGaussian() * 0.02D;
/*     */       
/* 420 */       this.world.a(s, this.locX + (this.random.nextFloat() * this.length * 2.0F) - this.length, this.locY + 0.5D + (this.random.nextFloat() * this.width), this.locZ + (this.random.nextFloat() * this.length * 2.0F) - this.length, d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 425 */   public int l() { return 8; }
/*     */ 
/*     */ 
/*     */   
/* 429 */   public String getOwnerName() { return this.datawatcher.c(17); }
/*     */ 
/*     */ 
/*     */   
/* 433 */   public void setOwnerName(String s) { this.datawatcher.watch(17, s); }
/*     */ 
/*     */ 
/*     */   
/* 437 */   public boolean isSitting() { return ((this.datawatcher.a(16) & true) != 0); }
/*     */ 
/*     */   
/*     */   public void setSitting(boolean flag) {
/* 441 */     byte b0 = this.datawatcher.a(16);
/*     */     
/* 443 */     if (flag) {
/* 444 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | true)));
/*     */     } else {
/* 446 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 451 */   public boolean isAngry() { return ((this.datawatcher.a(16) & 0x2) != 0); }
/*     */ 
/*     */   
/*     */   public void setAngry(boolean flag) {
/* 455 */     byte b0 = this.datawatcher.a(16);
/*     */     
/* 457 */     if (flag) {
/* 458 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     } else {
/* 460 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 465 */   public boolean isTamed() { return ((this.datawatcher.a(16) & 0x4) != 0); }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean flag) {
/* 469 */     byte b0 = this.datawatcher.a(16);
/*     */     
/* 471 */     if (flag) {
/* 472 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     } else {
/* 474 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityWolf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */