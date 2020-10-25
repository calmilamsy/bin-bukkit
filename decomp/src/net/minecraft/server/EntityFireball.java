/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.entity.CraftLivingEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Explosive;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ 
/*     */ 
/*     */ public class EntityFireball
/*     */   extends Entity
/*     */ {
/*  18 */   private int f = -1;
/*  19 */   private int g = -1;
/*  20 */   private int h = -1;
/*  21 */   private int i = 0;
/*     */   private boolean j = false;
/*  23 */   public int a = 0;
/*     */   public EntityLiving shooter;
/*     */   private int k;
/*  26 */   private int l = 0;
/*     */   
/*     */   public double c;
/*     */   public double d;
/*     */   public double e;
/*  31 */   public float yield = 1.0F;
/*     */   public boolean isIncendiary = true;
/*     */   
/*     */   public EntityFireball(World world) {
/*  35 */     super(world);
/*  36 */     b(1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public EntityFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/*  42 */     super(world);
/*  43 */     this.shooter = entityliving;
/*  44 */     b(1.0F, 1.0F);
/*  45 */     setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  46 */     setPosition(this.locX, this.locY, this.locZ);
/*  47 */     this.height = 0.0F;
/*  48 */     this.motX = this.motY = this.motZ = 0.0D;
/*     */     
/*  50 */     setDirection(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public void setDirection(double d0, double d1, double d2) {
/*  54 */     d0 += this.random.nextGaussian() * 0.4D;
/*  55 */     d1 += this.random.nextGaussian() * 0.4D;
/*  56 */     d2 += this.random.nextGaussian() * 0.4D;
/*  57 */     double d3 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  59 */     this.c = d0 / d3 * 0.1D;
/*  60 */     this.d = d1 / d3 * 0.1D;
/*  61 */     this.e = d2 / d3 * 0.1D;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  65 */     super.m_();
/*  66 */     this.fireTicks = 10;
/*  67 */     if (this.a > 0) {
/*  68 */       this.a--;
/*     */     }
/*     */     
/*  71 */     if (this.j) {
/*  72 */       int i = this.world.getTypeId(this.f, this.g, this.h);
/*     */       
/*  74 */       if (i == this.i) {
/*  75 */         this.k++;
/*  76 */         if (this.k == 1200) {
/*  77 */           die();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  83 */       this.j = false;
/*  84 */       this.motX *= (this.random.nextFloat() * 0.2F);
/*  85 */       this.motY *= (this.random.nextFloat() * 0.2F);
/*  86 */       this.motZ *= (this.random.nextFloat() * 0.2F);
/*  87 */       this.k = 0;
/*  88 */       this.l = 0;
/*     */     } else {
/*  90 */       this.l++;
/*     */     } 
/*     */     
/*  93 */     Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/*  94 */     Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*  95 */     MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */     
/*  97 */     vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/*  98 */     vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*  99 */     if (movingobjectposition != null) {
/* 100 */       vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
/*     */     }
/*     */     
/* 103 */     Entity entity = null;
/* 104 */     List list = this.world.b(this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
/* 105 */     double d0 = 0.0D;
/*     */     
/* 107 */     for (int j = 0; j < list.size(); j++) {
/* 108 */       Entity entity1 = (Entity)list.get(j);
/*     */       
/* 110 */       if (entity1.l_() && (entity1 != this.shooter || this.l >= 25)) {
/* 111 */         float f = 0.3F;
/* 112 */         AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f, f, f);
/* 113 */         MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */         
/* 115 */         if (movingobjectposition1 != null) {
/* 116 */           double d1 = vec3d.a(movingobjectposition1.f);
/*     */           
/* 118 */           if (d1 < d0 || d0 == 0.0D) {
/* 119 */             entity = entity1;
/* 120 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (entity != null) {
/* 127 */       movingobjectposition = new MovingObjectPosition(entity);
/*     */     }
/*     */     
/* 130 */     if (movingobjectposition != null) {
/*     */       
/* 132 */       ProjectileHitEvent phe = new ProjectileHitEvent((Projectile)getBukkitEntity());
/* 133 */       this.world.getServer().getPluginManager().callEvent(phe);
/*     */       
/* 135 */       if (!this.world.isStatic) {
/*     */         
/* 137 */         if (movingobjectposition.entity != null) {
/*     */           boolean stick;
/* 139 */           if (movingobjectposition.entity instanceof EntityLiving) {
/* 140 */             Entity damagee = movingobjectposition.entity.getBukkitEntity();
/* 141 */             Projectile projectile = (Projectile)getBukkitEntity();
/*     */ 
/*     */             
/* 144 */             EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, false);
/* 145 */             this.world.getServer().getPluginManager().callEvent(event);
/*     */             
/* 147 */             this.shooter = (projectile.getShooter() == null) ? null : ((CraftLivingEntity)projectile.getShooter()).getHandle();
/*     */             
/* 149 */             if (event.isCancelled()) {
/* 150 */               stick = !projectile.doesBounce();
/*     */             } else {
/*     */               
/* 153 */               stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
/*     */             } 
/*     */           } else {
/* 156 */             stick = movingobjectposition.entity.damageEntity(this.shooter, 0);
/*     */           } 
/* 158 */           if (stick);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 163 */         ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(this.world.getServer(), this));
/* 164 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 166 */         if (!event.isCancelled())
/*     */         {
/* 168 */           this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 173 */       die();
/*     */     } 
/*     */     
/* 176 */     this.locX += this.motX;
/* 177 */     this.locY += this.motY;
/* 178 */     this.locZ += this.motZ;
/* 179 */     float f1 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 181 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */     
/* 183 */     for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 187 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 188 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 191 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 192 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 195 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 196 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 199 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 200 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 201 */     float f2 = 0.95F;
/*     */     
/* 203 */     if (ad()) {
/* 204 */       for (int k = 0; k < 4; k++) {
/* 205 */         float f3 = 0.25F;
/*     */         
/* 207 */         this.world.a("bubble", this.locX - this.motX * f3, this.locY - this.motY * f3, this.locZ - this.motZ * f3, this.motX, this.motY, this.motZ);
/*     */       } 
/*     */       
/* 210 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 213 */     this.motX += this.c;
/* 214 */     this.motY += this.d;
/* 215 */     this.motZ += this.e;
/* 216 */     this.motX *= f2;
/* 217 */     this.motY *= f2;
/* 218 */     this.motZ *= f2;
/* 219 */     this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/* 220 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 224 */     nbttagcompound.a("xTile", (short)this.f);
/* 225 */     nbttagcompound.a("yTile", (short)this.g);
/* 226 */     nbttagcompound.a("zTile", (short)this.h);
/* 227 */     nbttagcompound.a("inTile", (byte)this.i);
/* 228 */     nbttagcompound.a("shake", (byte)this.a);
/* 229 */     nbttagcompound.a("inGround", (byte)(this.j ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 233 */     this.f = nbttagcompound.d("xTile");
/* 234 */     this.g = nbttagcompound.d("yTile");
/* 235 */     this.h = nbttagcompound.d("zTile");
/* 236 */     this.i = nbttagcompound.c("inTile") & 0xFF;
/* 237 */     this.a = nbttagcompound.c("shake") & 0xFF;
/* 238 */     this.j = (nbttagcompound.c("inGround") == 1);
/*     */   }
/*     */ 
/*     */   
/* 242 */   public boolean l_() { return true; }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 246 */     af();
/* 247 */     if (entity != null) {
/* 248 */       Vec3D vec3d = entity.Z();
/*     */       
/* 250 */       if (vec3d != null) {
/* 251 */         this.motX = vec3d.a;
/* 252 */         this.motY = vec3d.b;
/* 253 */         this.motZ = vec3d.c;
/* 254 */         this.c = this.motX * 0.1D;
/* 255 */         this.d = this.motY * 0.1D;
/* 256 */         this.e = this.motZ * 0.1D;
/*     */       } 
/*     */       
/* 259 */       return true;
/*     */     } 
/* 261 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityFireball.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */