/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.entity.CraftItem;
/*     */ import org.bukkit.craftbukkit.entity.CraftLivingEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ 
/*     */ public class EntityArrow extends Entity {
/*  16 */   private int d = -1;
/*  17 */   private int e = -1;
/*  18 */   private int f = -1;
/*  19 */   private int g = 0;
/*  20 */   private int h = 0;
/*     */   private boolean inGround = false;
/*     */   public boolean fromPlayer = false;
/*  23 */   public int shake = 0;
/*     */   public EntityLiving shooter;
/*     */   private int j;
/*  26 */   private int k = 0;
/*     */   
/*     */   public EntityArrow(World world) {
/*  29 */     super(world);
/*  30 */     b(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, double d0, double d1, double d2) {
/*  34 */     super(world);
/*  35 */     b(0.5F, 0.5F);
/*  36 */     setPosition(d0, d1, d2);
/*  37 */     this.height = 0.0F;
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, EntityLiving entityliving) {
/*  41 */     super(world);
/*  42 */     this.shooter = entityliving;
/*  43 */     this.fromPlayer = entityliving instanceof EntityHuman;
/*  44 */     b(0.5F, 0.5F);
/*  45 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  46 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  47 */     this.locY -= 0.10000000149011612D;
/*  48 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  49 */     setPosition(this.locX, this.locY, this.locZ);
/*  50 */     this.height = 0.0F;
/*  51 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/*  52 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/*  53 */     this.motY = -MathHelper.sin(this.pitch / 180.0F * 3.1415927F);
/*  54 */     a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public void a(double d0, double d1, double d2, float f, float f1) {
/*  60 */     float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  62 */     d0 /= f2;
/*  63 */     d1 /= f2;
/*  64 */     d2 /= f2;
/*  65 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  66 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  67 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  68 */     d0 *= f;
/*  69 */     d1 *= f;
/*  70 */     d2 *= f;
/*  71 */     this.motX = d0;
/*  72 */     this.motY = d1;
/*  73 */     this.motZ = d2;
/*  74 */     float f3 = MathHelper.a(d0 * d0 + d2 * d2);
/*     */     
/*  76 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/*  77 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/*  78 */     this.j = 0;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  82 */     super.m_();
/*  83 */     if (this.lastPitch == 0.0F && this.lastYaw == 0.0F) {
/*  84 */       float f = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/*  86 */       this.lastYaw = this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*  87 */       this.lastPitch = this.pitch = (float)(Math.atan2(this.motY, f) * 180.0D / 3.1415927410125732D);
/*     */     } 
/*     */     
/*  90 */     int i = this.world.getTypeId(this.d, this.e, this.f);
/*     */     
/*  92 */     if (i > 0) {
/*  93 */       Block.byId[i].a(this.world, this.d, this.e, this.f);
/*  94 */       AxisAlignedBB axisalignedbb = Block.byId[i].e(this.world, this.d, this.e, this.f);
/*     */       
/*  96 */       if (axisalignedbb != null && axisalignedbb.a(Vec3D.create(this.locX, this.locY, this.locZ))) {
/*  97 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     if (this.shake > 0) {
/* 102 */       this.shake--;
/*     */     }
/*     */     
/* 105 */     if (this.inGround) {
/* 106 */       i = this.world.getTypeId(this.d, this.e, this.f);
/* 107 */       int j = this.world.getData(this.d, this.e, this.f);
/*     */       
/* 109 */       if (i == this.g && j == this.h) {
/* 110 */         this.j++;
/* 111 */         if (this.j == 1200) {
/* 112 */           die();
/*     */         }
/*     */       } else {
/* 115 */         this.inGround = false;
/* 116 */         this.motX *= (this.random.nextFloat() * 0.2F);
/* 117 */         this.motY *= (this.random.nextFloat() * 0.2F);
/* 118 */         this.motZ *= (this.random.nextFloat() * 0.2F);
/* 119 */         this.j = 0;
/* 120 */         this.k = 0;
/*     */       } 
/*     */     } else {
/* 123 */       this.k++;
/* 124 */       Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 125 */       Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 126 */       MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, false, true);
/*     */       
/* 128 */       vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 129 */       vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 130 */       if (movingobjectposition != null) {
/* 131 */         vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
/*     */       }
/*     */       
/* 134 */       Entity entity = null;
/* 135 */       List list = this.world.b(this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
/* 136 */       double d0 = 0.0D;
/*     */ 
/*     */ 
/*     */       
/* 140 */       for (int k = 0; k < list.size(); k++) {
/* 141 */         Entity entity1 = (Entity)list.get(k);
/*     */         
/* 143 */         if (entity1.l_() && (entity1 != this.shooter || this.k >= 5)) {
/* 144 */           float f1 = 0.3F;
/* 145 */           AxisAlignedBB axisalignedbb1 = entity1.boundingBox.b(f1, f1, f1);
/* 146 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
/*     */           
/* 148 */           if (movingobjectposition1 != null) {
/* 149 */             double d1 = vec3d.a(movingobjectposition1.f);
/*     */             
/* 151 */             if (d1 < d0 || d0 == 0.0D) {
/* 152 */               entity = entity1;
/* 153 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 159 */       if (entity != null) {
/* 160 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (movingobjectposition != null) {
/*     */         
/* 167 */         ProjectileHitEvent phe = new ProjectileHitEvent((Projectile)getBukkitEntity());
/* 168 */         this.world.getServer().getPluginManager().callEvent(phe);
/*     */         
/* 170 */         if (movingobjectposition.entity != null) {
/*     */           boolean stick;
/*     */           
/* 173 */           if (entity instanceof EntityLiving) {
/* 174 */             CraftServer craftServer = this.world.getServer();
/*     */ 
/*     */ 
/*     */             
/* 178 */             Entity damagee = movingobjectposition.entity.getBukkitEntity();
/* 179 */             Projectile projectile = (Projectile)getBukkitEntity();
/*     */ 
/*     */             
/* 182 */             EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 4);
/* 183 */             craftServer.getPluginManager().callEvent(event);
/* 184 */             this.shooter = (projectile.getShooter() == null) ? null : ((CraftLivingEntity)projectile.getShooter()).getHandle();
/*     */             
/* 186 */             if (event.isCancelled()) {
/* 187 */               stick = !projectile.doesBounce();
/*     */             } else {
/*     */               
/* 190 */               stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
/*     */             } 
/*     */           } else {
/* 193 */             stick = movingobjectposition.entity.damageEntity(this.shooter, 4);
/*     */           } 
/* 195 */           if (stick) {
/*     */             
/* 197 */             this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 198 */             die();
/*     */           } else {
/* 200 */             this.motX *= -0.10000000149011612D;
/* 201 */             this.motY *= -0.10000000149011612D;
/* 202 */             this.motZ *= -0.10000000149011612D;
/* 203 */             this.yaw += 180.0F;
/* 204 */             this.lastYaw += 180.0F;
/* 205 */             this.k = 0;
/*     */           } 
/*     */         } else {
/* 208 */           this.d = movingobjectposition.b;
/* 209 */           this.e = movingobjectposition.c;
/* 210 */           this.f = movingobjectposition.d;
/* 211 */           this.g = this.world.getTypeId(this.d, this.e, this.f);
/* 212 */           this.h = this.world.getData(this.d, this.e, this.f);
/* 213 */           this.motX = (float)(movingobjectposition.f.a - this.locX);
/* 214 */           this.motY = (float)(movingobjectposition.f.b - this.locY);
/* 215 */           this.motZ = (float)(movingobjectposition.f.c - this.locZ);
/* 216 */           float f2 = MathHelper.a(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 217 */           this.locX -= this.motX / f2 * 0.05000000074505806D;
/* 218 */           this.locY -= this.motY / f2 * 0.05000000074505806D;
/* 219 */           this.locZ -= this.motZ / f2 * 0.05000000074505806D;
/* 220 */           this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 221 */           this.inGround = true;
/* 222 */           this.shake = 7;
/*     */         } 
/*     */       } 
/*     */       
/* 226 */       this.locX += this.motX;
/* 227 */       this.locY += this.motY;
/* 228 */       this.locZ += this.motZ;
/* 229 */       float f2 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/* 230 */       this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */       
/* 232 */       for (this.pitch = (float)(Math.atan2(this.motY, f2) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */       
/* 236 */       while (this.pitch - this.lastPitch >= 180.0F) {
/* 237 */         this.lastPitch += 360.0F;
/*     */       }
/*     */       
/* 240 */       while (this.yaw - this.lastYaw < -180.0F) {
/* 241 */         this.lastYaw -= 360.0F;
/*     */       }
/*     */       
/* 244 */       while (this.yaw - this.lastYaw >= 180.0F) {
/* 245 */         this.lastYaw += 360.0F;
/*     */       }
/*     */       
/* 248 */       this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 249 */       this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 250 */       float f3 = 0.99F;
/*     */       
/* 252 */       float f1 = 0.03F;
/* 253 */       if (ad()) {
/* 254 */         for (int l = 0; l < 4; l++) {
/* 255 */           float f4 = 0.25F;
/*     */           
/* 257 */           this.world.a("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */         
/* 260 */         f3 = 0.8F;
/*     */       } 
/*     */       
/* 263 */       this.motX *= f3;
/* 264 */       this.motY *= f3;
/* 265 */       this.motZ *= f3;
/* 266 */       this.motY -= f1;
/* 267 */       setPosition(this.locX, this.locY, this.locZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 272 */     nbttagcompound.a("xTile", (short)this.d);
/* 273 */     nbttagcompound.a("yTile", (short)this.e);
/* 274 */     nbttagcompound.a("zTile", (short)this.f);
/* 275 */     nbttagcompound.a("inTile", (byte)this.g);
/* 276 */     nbttagcompound.a("inData", (byte)this.h);
/* 277 */     nbttagcompound.a("shake", (byte)this.shake);
/* 278 */     nbttagcompound.a("inGround", (byte)(this.inGround ? 1 : 0));
/* 279 */     nbttagcompound.a("player", this.fromPlayer);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 283 */     this.d = nbttagcompound.d("xTile");
/* 284 */     this.e = nbttagcompound.d("yTile");
/* 285 */     this.f = nbttagcompound.d("zTile");
/* 286 */     this.g = nbttagcompound.c("inTile") & 0xFF;
/* 287 */     this.h = nbttagcompound.c("inData") & 0xFF;
/* 288 */     this.shake = nbttagcompound.c("shake") & 0xFF;
/* 289 */     this.inGround = (nbttagcompound.c("inGround") == 1);
/* 290 */     this.fromPlayer = nbttagcompound.m("player");
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 294 */     if (!this.world.isStatic) {
/*     */       
/* 296 */       ItemStack itemstack = new ItemStack(Item.ARROW, true);
/* 297 */       if (this.inGround && this.fromPlayer && this.shake <= 0 && entityhuman.inventory.canHold(itemstack) > 0) {
/* 298 */         EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
/*     */         
/* 300 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), item), false);
/* 301 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 303 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 309 */       if (this.inGround && this.fromPlayer && this.shake <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, true))) {
/* 310 */         this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 311 */         entityhuman.receive(this, 1);
/* 312 */         die();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityArrow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */