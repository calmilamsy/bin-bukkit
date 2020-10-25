/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.player.PlayerEggThrowEvent;
/*     */ 
/*     */ public class EntityEgg
/*     */   extends Entity {
/*  17 */   private int b = -1;
/*  18 */   private int c = -1;
/*  19 */   private int d = -1;
/*  20 */   private int e = 0;
/*     */   private boolean f = false;
/*  22 */   public int a = 0;
/*     */   public EntityLiving thrower;
/*     */   private int h;
/*  25 */   private int i = 0;
/*     */   
/*     */   public EntityEgg(World world) {
/*  28 */     super(world);
/*  29 */     b(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public EntityEgg(World world, EntityLiving entityliving) {
/*  35 */     super(world);
/*  36 */     this.thrower = entityliving;
/*  37 */     b(0.25F, 0.25F);
/*  38 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  39 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  40 */     this.locY -= 0.10000000149011612D;
/*  41 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  42 */     setPosition(this.locX, this.locY, this.locZ);
/*  43 */     this.height = 0.0F;
/*  44 */     float f = 0.4F;
/*     */     
/*  46 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  47 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  48 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
/*  49 */     a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public EntityEgg(World world, double d0, double d1, double d2) {
/*  53 */     super(world);
/*  54 */     this.h = 0;
/*  55 */     b(0.25F, 0.25F);
/*  56 */     setPosition(d0, d1, d2);
/*  57 */     this.height = 0.0F;
/*     */   }
/*     */   
/*     */   public void a(double d0, double d1, double d2, float f, float f1) {
/*  61 */     float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  63 */     d0 /= f2;
/*  64 */     d1 /= f2;
/*  65 */     d2 /= f2;
/*  66 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  67 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  68 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  69 */     d0 *= f;
/*  70 */     d1 *= f;
/*  71 */     d2 *= f;
/*  72 */     this.motX = d0;
/*  73 */     this.motY = d1;
/*  74 */     this.motZ = d2;
/*  75 */     float f3 = MathHelper.a(d0 * d0 + d2 * d2);
/*     */     
/*  77 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/*  78 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/*  79 */     this.h = 0;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  83 */     this.bo = this.locX;
/*  84 */     this.bp = this.locY;
/*  85 */     this.bq = this.locZ;
/*  86 */     super.m_();
/*  87 */     if (this.a > 0) {
/*  88 */       this.a--;
/*     */     }
/*     */     
/*  91 */     if (this.f) {
/*  92 */       int i = this.world.getTypeId(this.b, this.c, this.d);
/*     */       
/*  94 */       if (i == this.e) {
/*  95 */         this.h++;
/*  96 */         if (this.h == 1200) {
/*  97 */           die();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 103 */       this.f = false;
/* 104 */       this.motX *= (this.random.nextFloat() * 0.2F);
/* 105 */       this.motY *= (this.random.nextFloat() * 0.2F);
/* 106 */       this.motZ *= (this.random.nextFloat() * 0.2F);
/* 107 */       this.h = 0;
/* 108 */       this.i = 0;
/*     */     } else {
/* 110 */       this.i++;
/*     */     } 
/*     */     
/* 113 */     Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 114 */     Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 115 */     MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */     
/* 117 */     vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 118 */     vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 119 */     if (movingobjectposition != null) {
/* 120 */       vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
/*     */     }
/*     */     
/* 123 */     if (!this.world.isStatic) {
/* 124 */       Entity entity = null;
/* 125 */       List list = this.world.b(this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
/* 126 */       double d0 = 0.0D;
/*     */       
/* 128 */       for (int j = 0; j < list.size(); j++) {
/* 129 */         Entity entity1 = (Entity)list.get(j);
/*     */         
/* 131 */         if (entity1.l_() && (entity1 != this.thrower || this.i >= 5)) {
/* 132 */           float f = 0.3F;
/* 133 */           AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f, f, f);
/* 134 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 136 */           if (movingobjectposition1 != null) {
/* 137 */             double d1 = vec3d.a(movingobjectposition1.f);
/*     */             
/* 139 */             if (d1 < d0 || d0 == 0.0D) {
/* 140 */               entity = entity1;
/* 141 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       if (entity != null) {
/* 148 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if (movingobjectposition != null) {
/*     */       
/* 154 */       ProjectileHitEvent phe = new ProjectileHitEvent((Projectile)getBukkitEntity());
/* 155 */       this.world.getServer().getPluginManager().callEvent(phe);
/*     */       
/* 157 */       if (movingobjectposition.entity != null) {
/*     */         boolean stick;
/* 159 */         if (movingobjectposition.entity instanceof EntityLiving) {
/* 160 */           Entity damagee = movingobjectposition.entity.getBukkitEntity();
/* 161 */           Projectile projectile = (Projectile)getBukkitEntity();
/*     */ 
/*     */           
/* 164 */           EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, false);
/* 165 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 167 */           if (event.isCancelled()) {
/* 168 */             stick = !projectile.doesBounce();
/*     */           } else {
/*     */             
/* 171 */             stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
/*     */           } 
/*     */         } else {
/* 174 */           stick = movingobjectposition.entity.damageEntity(this.thrower, 0);
/*     */         } 
/*     */         
/* 177 */         if (stick);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 182 */       boolean hatching = (!this.world.isStatic && this.random.nextInt(8) == 0);
/* 183 */       int numHatching = (this.random.nextInt(32) == 0) ? 4 : 1;
/* 184 */       if (!hatching) {
/* 185 */         numHatching = 0;
/*     */       }
/*     */       
/* 188 */       CreatureType hatchingType = CreatureType.CHICKEN;
/*     */       
/* 190 */       if (this.thrower instanceof EntityPlayer) {
/* 191 */         Player player = (this.thrower == null) ? null : (Player)this.thrower.getBukkitEntity();
/*     */         
/* 193 */         PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (Egg)getBukkitEntity(), hatching, (byte)numHatching, hatchingType);
/* 194 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 196 */         hatching = event.isHatching();
/* 197 */         numHatching = event.getNumHatches();
/* 198 */         hatchingType = event.getHatchType();
/*     */       } 
/*     */       
/* 201 */       if (hatching) {
/* 202 */         for (int k = 0; k < numHatching; k++) {
/* 203 */           Entity entity = null;
/* 204 */           switch (hatchingType) {
/*     */             case CHICKEN:
/* 206 */               entity = new EntityChicken(this.world);
/*     */               break;
/*     */             case COW:
/* 209 */               entity = new EntityCow(this.world);
/*     */               break;
/*     */             case CREEPER:
/* 212 */               entity = new EntityCreeper(this.world);
/*     */               break;
/*     */             case GHAST:
/* 215 */               entity = new EntityGhast(this.world);
/*     */               break;
/*     */             case GIANT:
/* 218 */               entity = new EntityGiantZombie(this.world);
/*     */               break;
/*     */             case PIG:
/* 221 */               entity = new EntityPig(this.world);
/*     */               break;
/*     */             case PIG_ZOMBIE:
/* 224 */               entity = new EntityPigZombie(this.world);
/*     */               break;
/*     */             case SHEEP:
/* 227 */               entity = new EntitySheep(this.world);
/*     */               break;
/*     */             case SKELETON:
/* 230 */               entity = new EntitySkeleton(this.world);
/*     */               break;
/*     */             case SPIDER:
/* 233 */               entity = new EntitySpider(this.world);
/*     */               break;
/*     */             case ZOMBIE:
/* 236 */               entity = new EntityZombie(this.world);
/*     */               break;
/*     */             case SQUID:
/* 239 */               entity = new EntitySquid(this.world);
/*     */               break;
/*     */             case SLIME:
/* 242 */               entity = new EntitySlime(this.world);
/*     */               break;
/*     */             case WOLF:
/* 245 */               entity = new EntityWolf(this.world);
/*     */               break;
/*     */             case MONSTER:
/* 248 */               entity = new EntityMonster(this.world);
/*     */               break;
/*     */             default:
/* 251 */               entity = new EntityChicken(this.world);
/*     */               break;
/*     */           } 
/*     */ 
/*     */           
/* 256 */           boolean isAnimal = (entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal);
/* 257 */           if ((isAnimal && this.world.allowAnimals) || (!isAnimal && this.world.allowMonsters)) {
/* 258 */             entity.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 259 */             this.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.EGG);
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 265 */       for (int l = 0; l < 8; l++) {
/* 266 */         this.world.a("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */       
/* 269 */       die();
/*     */     } 
/*     */     
/* 272 */     this.locX += this.motX;
/* 273 */     this.locY += this.motY;
/* 274 */     this.locZ += this.motZ;
/* 275 */     float f1 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 277 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */     
/* 279 */     for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 283 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 284 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 287 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 288 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 291 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 292 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 295 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 296 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 297 */     float f2 = 0.99F;
/* 298 */     float f3 = 0.03F;
/*     */     
/* 300 */     if (ad()) {
/* 301 */       for (int i1 = 0; i1 < 4; i1++) {
/* 302 */         float f4 = 0.25F;
/*     */         
/* 304 */         this.world.a("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
/*     */       } 
/*     */       
/* 307 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 310 */     this.motX *= f2;
/* 311 */     this.motY *= f2;
/* 312 */     this.motZ *= f2;
/* 313 */     this.motY -= f3;
/* 314 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 318 */     nbttagcompound.a("xTile", (short)this.b);
/* 319 */     nbttagcompound.a("yTile", (short)this.c);
/* 320 */     nbttagcompound.a("zTile", (short)this.d);
/* 321 */     nbttagcompound.a("inTile", (byte)this.e);
/* 322 */     nbttagcompound.a("shake", (byte)this.a);
/* 323 */     nbttagcompound.a("inGround", (byte)(this.f ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 327 */     this.b = nbttagcompound.d("xTile");
/* 328 */     this.c = nbttagcompound.d("yTile");
/* 329 */     this.d = nbttagcompound.d("zTile");
/* 330 */     this.e = nbttagcompound.c("inTile") & 0xFF;
/* 331 */     this.a = nbttagcompound.c("shake") & 0xFF;
/* 332 */     this.f = (nbttagcompound.c("inGround") == 1);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 336 */     if (this.f && this.thrower == entityhuman && this.a <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, true))) {
/* 337 */       this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 338 */       entityhuman.receive(this, 1);
/* 339 */       die();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityEgg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */