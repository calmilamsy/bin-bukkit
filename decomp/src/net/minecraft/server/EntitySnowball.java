/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.entity.CraftLivingEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ 
/*     */ 
/*     */ public class EntitySnowball
/*     */   extends Entity
/*     */ {
/*  15 */   private int b = -1;
/*  16 */   private int c = -1;
/*  17 */   private int d = -1;
/*  18 */   private int e = 0;
/*     */   private boolean f = false;
/*  20 */   public int a = 0;
/*     */   public EntityLiving shooter;
/*     */   private int h;
/*  23 */   private int i = 0;
/*     */   
/*     */   public EntitySnowball(World world) {
/*  26 */     super(world);
/*  27 */     b(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public EntitySnowball(World world, EntityLiving entityliving) {
/*  33 */     super(world);
/*  34 */     this.shooter = entityliving;
/*  35 */     b(0.25F, 0.25F);
/*  36 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  37 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  38 */     this.locY -= 0.10000000149011612D;
/*  39 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  40 */     setPosition(this.locX, this.locY, this.locZ);
/*  41 */     this.height = 0.0F;
/*  42 */     float f = 0.4F;
/*     */     
/*  44 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  45 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  46 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
/*  47 */     a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public EntitySnowball(World world, double d0, double d1, double d2) {
/*  51 */     super(world);
/*  52 */     this.h = 0;
/*  53 */     b(0.25F, 0.25F);
/*  54 */     setPosition(d0, d1, d2);
/*  55 */     this.height = 0.0F;
/*     */   }
/*     */   
/*     */   public void a(double d0, double d1, double d2, float f, float f1) {
/*  59 */     float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  61 */     d0 /= f2;
/*  62 */     d1 /= f2;
/*  63 */     d2 /= f2;
/*  64 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  65 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  66 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  67 */     d0 *= f;
/*  68 */     d1 *= f;
/*  69 */     d2 *= f;
/*  70 */     this.motX = d0;
/*  71 */     this.motY = d1;
/*  72 */     this.motZ = d2;
/*  73 */     float f3 = MathHelper.a(d0 * d0 + d2 * d2);
/*     */     
/*  75 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/*  76 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/*  77 */     this.h = 0;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  81 */     this.bo = this.locX;
/*  82 */     this.bp = this.locY;
/*  83 */     this.bq = this.locZ;
/*  84 */     super.m_();
/*  85 */     if (this.a > 0) {
/*  86 */       this.a--;
/*     */     }
/*     */     
/*  89 */     if (this.f) {
/*  90 */       int i = this.world.getTypeId(this.b, this.c, this.d);
/*     */       
/*  92 */       if (i == this.e) {
/*  93 */         this.h++;
/*  94 */         if (this.h == 1200) {
/*  95 */           die();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 101 */       this.f = false;
/* 102 */       this.motX *= (this.random.nextFloat() * 0.2F);
/* 103 */       this.motY *= (this.random.nextFloat() * 0.2F);
/* 104 */       this.motZ *= (this.random.nextFloat() * 0.2F);
/* 105 */       this.h = 0;
/* 106 */       this.i = 0;
/*     */     } else {
/* 108 */       this.i++;
/*     */     } 
/*     */     
/* 111 */     Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 112 */     Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 113 */     MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */     
/* 115 */     vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 116 */     vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 117 */     if (movingobjectposition != null) {
/* 118 */       vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
/*     */     }
/*     */     
/* 121 */     if (!this.world.isStatic) {
/* 122 */       Entity entity = null;
/* 123 */       List list = this.world.b(this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
/* 124 */       double d0 = 0.0D;
/*     */       
/* 126 */       for (int j = 0; j < list.size(); j++) {
/* 127 */         Entity entity1 = (Entity)list.get(j);
/*     */         
/* 129 */         if (entity1.l_() && (entity1 != this.shooter || this.i >= 5)) {
/* 130 */           float f = 0.3F;
/* 131 */           AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f, f, f);
/* 132 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 134 */           if (movingobjectposition1 != null) {
/* 135 */             double d1 = vec3d.a(movingobjectposition1.f);
/*     */             
/* 137 */             if (d1 < d0 || d0 == 0.0D) {
/* 138 */               entity = entity1;
/* 139 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       if (entity != null) {
/* 146 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */     
/* 150 */     if (movingobjectposition != null) {
/*     */       
/* 152 */       ProjectileHitEvent phe = new ProjectileHitEvent((Projectile)getBukkitEntity());
/* 153 */       this.world.getServer().getPluginManager().callEvent(phe);
/*     */       
/* 155 */       if (movingobjectposition.entity != null) {
/*     */         boolean stick;
/* 157 */         if (movingobjectposition.entity instanceof EntityLiving) {
/* 158 */           Entity damagee = movingobjectposition.entity.getBukkitEntity();
/* 159 */           Projectile projectile = (Projectile)getBukkitEntity();
/*     */ 
/*     */           
/* 162 */           EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, false);
/* 163 */           this.world.getServer().getPluginManager().callEvent(event);
/* 164 */           this.shooter = (projectile.getShooter() == null) ? null : ((CraftLivingEntity)projectile.getShooter()).getHandle();
/*     */           
/* 166 */           if (event.isCancelled()) {
/* 167 */             stick = !projectile.doesBounce();
/*     */           } else {
/*     */             
/* 170 */             stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
/*     */           } 
/*     */         } else {
/* 173 */           stick = movingobjectposition.entity.damageEntity(this.shooter, 0);
/*     */         } 
/* 175 */         if (stick);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       for (int k = 0; k < 8; k++) {
/* 182 */         this.world.a("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */       
/* 185 */       die();
/*     */     } 
/*     */     
/* 188 */     this.locX += this.motX;
/* 189 */     this.locY += this.motY;
/* 190 */     this.locZ += this.motZ;
/* 191 */     float f1 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 193 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */     
/* 195 */     for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 199 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 200 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 203 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 204 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 207 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 208 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 211 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 212 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 213 */     float f2 = 0.99F;
/* 214 */     float f3 = 0.03F;
/*     */     
/* 216 */     if (ad()) {
/* 217 */       for (int l = 0; l < 4; l++) {
/* 218 */         float f4 = 0.25F;
/*     */         
/* 220 */         this.world.a("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
/*     */       } 
/*     */       
/* 223 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 226 */     this.motX *= f2;
/* 227 */     this.motY *= f2;
/* 228 */     this.motZ *= f2;
/* 229 */     this.motY -= f3;
/* 230 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 234 */     nbttagcompound.a("xTile", (short)this.b);
/* 235 */     nbttagcompound.a("yTile", (short)this.c);
/* 236 */     nbttagcompound.a("zTile", (short)this.d);
/* 237 */     nbttagcompound.a("inTile", (byte)this.e);
/* 238 */     nbttagcompound.a("shake", (byte)this.a);
/* 239 */     nbttagcompound.a("inGround", (byte)(this.f ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 243 */     this.b = nbttagcompound.d("xTile");
/* 244 */     this.c = nbttagcompound.d("yTile");
/* 245 */     this.d = nbttagcompound.d("zTile");
/* 246 */     this.e = nbttagcompound.c("inTile") & 0xFF;
/* 247 */     this.a = nbttagcompound.c("shake") & 0xFF;
/* 248 */     this.f = (nbttagcompound.c("inGround") == 1);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 252 */     if (this.f && this.shooter == entityhuman && this.a <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, true))) {
/* 253 */       this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 254 */       entityhuman.receive(this, 1);
/* 255 */       die();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySnowball.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */