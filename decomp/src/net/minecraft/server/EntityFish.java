/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerFishEvent;
/*     */ 
/*     */ public class EntityFish
/*     */   extends Entity
/*     */ {
/*  14 */   private int d = -1;
/*  15 */   private int e = -1;
/*  16 */   private int f = -1;
/*  17 */   private int g = 0;
/*     */   private boolean h = false;
/*  19 */   public int a = 0;
/*     */   public EntityHuman owner;
/*     */   private int i;
/*  22 */   private int j = 0;
/*  23 */   private int k = 0;
/*  24 */   public Entity c = null;
/*     */   private int l;
/*     */   private double m;
/*     */   private double n;
/*     */   private double o;
/*     */   private double p;
/*     */   private double q;
/*     */   
/*     */   public EntityFish(World world) {
/*  33 */     super(world);
/*  34 */     b(0.25F, 0.25F);
/*  35 */     this.bK = true;
/*     */   }
/*     */   
/*     */   public EntityFish(World world, EntityHuman entityhuman) {
/*  39 */     super(world);
/*  40 */     this.bK = true;
/*  41 */     this.owner = entityhuman;
/*  42 */     this.owner.hookedFish = this;
/*  43 */     b(0.25F, 0.25F);
/*  44 */     setPositionRotation(entityhuman.locX, entityhuman.locY + 1.62D - entityhuman.height, entityhuman.locZ, entityhuman.yaw, entityhuman.pitch);
/*  45 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  46 */     this.locY -= 0.10000000149011612D;
/*  47 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  48 */     setPosition(this.locX, this.locY, this.locZ);
/*  49 */     this.height = 0.0F;
/*  50 */     float f = 0.4F;
/*     */     
/*  52 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  53 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  54 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
/*  55 */     a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void b() {}
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
/*  79 */     this.i = 0;
/*     */   }
/*     */   
/*     */   public void m_() {
/*  83 */     super.m_();
/*  84 */     if (this.l > 0) {
/*  85 */       double d0 = this.locX + (this.m - this.locX) / this.l;
/*  86 */       double d1 = this.locY + (this.n - this.locY) / this.l;
/*  87 */       double d2 = this.locZ + (this.o - this.locZ) / this.l;
/*     */       
/*     */       double d3;
/*     */       
/*  91 */       for (d3 = this.p - this.yaw; d3 < -180.0D; d3 += 360.0D);
/*     */ 
/*     */ 
/*     */       
/*  95 */       while (d3 >= 180.0D) {
/*  96 */         d3 -= 360.0D;
/*     */       }
/*     */       
/*  99 */       this.yaw = (float)(this.yaw + d3 / this.l);
/* 100 */       this.pitch = (float)(this.pitch + (this.q - this.pitch) / this.l);
/* 101 */       this.l--;
/* 102 */       setPosition(d0, d1, d2);
/* 103 */       c(this.yaw, this.pitch);
/*     */     } else {
/* 105 */       if (!this.world.isStatic) {
/* 106 */         ItemStack itemstack = this.owner.G();
/*     */         
/* 108 */         if (this.owner.dead || !this.owner.T() || itemstack == null || itemstack.getItem() != Item.FISHING_ROD || g(this.owner) > 1024.0D) {
/* 109 */           die();
/* 110 */           this.owner.hookedFish = null;
/*     */           
/*     */           return;
/*     */         } 
/* 114 */         if (this.c != null) {
/* 115 */           if (!this.c.dead) {
/* 116 */             this.locX = this.c.locX;
/* 117 */             this.locY = this.c.boundingBox.b + this.c.width * 0.8D;
/* 118 */             this.locZ = this.c.locZ;
/*     */             
/*     */             return;
/*     */           } 
/* 122 */           this.c = null;
/*     */         } 
/*     */       } 
/*     */       
/* 126 */       if (this.a > 0) {
/* 127 */         this.a--;
/*     */       }
/*     */       
/* 130 */       if (this.h) {
/* 131 */         int i = this.world.getTypeId(this.d, this.e, this.f);
/*     */         
/* 133 */         if (i == this.g) {
/* 134 */           this.i++;
/* 135 */           if (this.i == 1200) {
/* 136 */             die();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 142 */         this.h = false;
/* 143 */         this.motX *= (this.random.nextFloat() * 0.2F);
/* 144 */         this.motY *= (this.random.nextFloat() * 0.2F);
/* 145 */         this.motZ *= (this.random.nextFloat() * 0.2F);
/* 146 */         this.i = 0;
/* 147 */         this.j = 0;
/*     */       } else {
/* 149 */         this.j++;
/*     */       } 
/*     */       
/* 152 */       Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 153 */       Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 154 */       MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */       
/* 156 */       vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
/* 157 */       vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 158 */       if (movingobjectposition != null) {
/* 159 */         vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
/*     */       }
/*     */       
/* 162 */       Entity entity = null;
/* 163 */       List list = this.world.b(this, this.boundingBox.a(this.motX, this.motY, this.motZ).b(1.0D, 1.0D, 1.0D));
/* 164 */       double d4 = 0.0D;
/*     */ 
/*     */ 
/*     */       
/* 168 */       for (int j = 0; j < list.size(); j++) {
/* 169 */         Entity entity1 = (Entity)list.get(j);
/*     */         
/* 171 */         if (entity1.l_() && (entity1 != this.owner || this.j >= 5)) {
/* 172 */           float f = 0.3F;
/* 173 */           AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f, f, f);
/* 174 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 176 */           if (movingobjectposition1 != null) {
/* 177 */             double d5 = vec3d.a(movingobjectposition1.f);
/* 178 */             if (d5 < d4 || d4 == 0.0D) {
/* 179 */               entity = entity1;
/* 180 */               d4 = d5;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       if (entity != null) {
/* 187 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 190 */       if (movingobjectposition != null) {
/* 191 */         if (movingobjectposition.entity != null) {
/*     */           boolean stick;
/*     */ 
/*     */           
/* 195 */           if (movingobjectposition.entity instanceof EntityLiving) {
/* 196 */             Entity damagee = movingobjectposition.entity.getBukkitEntity();
/* 197 */             Projectile projectile = (Projectile)getBukkitEntity();
/*     */ 
/*     */             
/* 200 */             EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, false);
/* 201 */             this.world.getServer().getPluginManager().callEvent(event);
/*     */             
/* 203 */             if (event.isCancelled()) {
/* 204 */               stick = !projectile.doesBounce();
/*     */             } else {
/*     */               
/* 207 */               stick = movingobjectposition.entity.damageEntity(this, event.getDamage());
/*     */             } 
/*     */           } else {
/* 210 */             stick = movingobjectposition.entity.damageEntity(this.owner, 0);
/*     */           } 
/* 212 */           if (!stick)
/*     */           {
/* 214 */             this.c = movingobjectposition.entity;
/*     */           }
/*     */         } else {
/* 217 */           this.h = true;
/*     */         } 
/*     */       }
/*     */       
/* 221 */       if (!this.h) {
/* 222 */         move(this.motX, this.motY, this.motZ);
/* 223 */         float f1 = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
/*     */         
/* 225 */         this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */         
/* 227 */         for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */         
/* 231 */         while (this.pitch - this.lastPitch >= 180.0F) {
/* 232 */           this.lastPitch += 360.0F;
/*     */         }
/*     */         
/* 235 */         while (this.yaw - this.lastYaw < -180.0F) {
/* 236 */           this.lastYaw -= 360.0F;
/*     */         }
/*     */         
/* 239 */         while (this.yaw - this.lastYaw >= 180.0F) {
/* 240 */           this.lastYaw += 360.0F;
/*     */         }
/*     */         
/* 243 */         this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 244 */         this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 245 */         float f2 = 0.92F;
/*     */         
/* 247 */         if (this.onGround || this.positionChanged) {
/* 248 */           f2 = 0.5F;
/*     */         }
/*     */         
/* 251 */         byte b0 = 5;
/* 252 */         double d6 = 0.0D;
/*     */         
/* 254 */         for (int k = 0; k < b0; k++) {
/* 255 */           double d7 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 0) / b0 - 0.125D + 0.125D;
/* 256 */           double d8 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 1) / b0 - 0.125D + 0.125D;
/* 257 */           AxisAlignedBB axisalignedbb1 = AxisAlignedBB.b(this.boundingBox.a, d7, this.boundingBox.c, this.boundingBox.d, d8, this.boundingBox.f);
/*     */           
/* 259 */           if (this.world.b(axisalignedbb1, Material.WATER)) {
/* 260 */             d6 += 1.0D / b0;
/*     */           }
/*     */         } 
/*     */         
/* 264 */         if (d6 > 0.0D) {
/* 265 */           if (this.k > 0) {
/* 266 */             this.k--;
/*     */           } else {
/* 268 */             short short1 = 500;
/*     */             
/* 270 */             if (this.world.s(MathHelper.floor(this.locX), MathHelper.floor(this.locY) + 1, MathHelper.floor(this.locZ))) {
/* 271 */               short1 = 300;
/*     */             }
/*     */             
/* 274 */             if (this.random.nextInt(short1) == 0) {
/* 275 */               this.k = this.random.nextInt(30) + 10;
/* 276 */               this.motY -= 0.20000000298023224D;
/* 277 */               this.world.makeSound(this, "random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/* 278 */               float f3 = MathHelper.floor(this.boundingBox.b);
/*     */ 
/*     */               
/*     */               int l;
/*     */ 
/*     */               
/* 284 */               for (l = 0; l < 1.0F + this.length * 20.0F; l++) {
/* 285 */                 float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/* 286 */                 float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/* 287 */                 this.world.a("bubble", this.locX + f5, (f3 + 1.0F), this.locZ + f4, this.motX, this.motY - (this.random.nextFloat() * 0.2F), this.motZ);
/*     */               } 
/*     */               
/* 290 */               for (l = 0; l < 1.0F + this.length * 20.0F; l++) {
/* 291 */                 float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/* 292 */                 float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.length;
/* 293 */                 this.world.a("splash", this.locX + f5, (f3 + 1.0F), this.locZ + f4, this.motX, this.motY, this.motZ);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 299 */         if (this.k > 0) {
/* 300 */           this.motY -= (this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat()) * 0.2D;
/*     */         }
/*     */         
/* 303 */         double d5 = d6 * 2.0D - 1.0D;
/* 304 */         this.motY += 0.03999999910593033D * d5;
/* 305 */         if (d6 > 0.0D) {
/* 306 */           f2 = (float)(f2 * 0.9D);
/* 307 */           this.motY *= 0.8D;
/*     */         } 
/*     */         
/* 310 */         this.motX *= f2;
/* 311 */         this.motY *= f2;
/* 312 */         this.motZ *= f2;
/* 313 */         setPosition(this.locX, this.locY, this.locZ);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 319 */     nbttagcompound.a("xTile", (short)this.d);
/* 320 */     nbttagcompound.a("yTile", (short)this.e);
/* 321 */     nbttagcompound.a("zTile", (short)this.f);
/* 322 */     nbttagcompound.a("inTile", (byte)this.g);
/* 323 */     nbttagcompound.a("shake", (byte)this.a);
/* 324 */     nbttagcompound.a("inGround", (byte)(this.h ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 328 */     this.d = nbttagcompound.d("xTile");
/* 329 */     this.e = nbttagcompound.d("yTile");
/* 330 */     this.f = nbttagcompound.d("zTile");
/* 331 */     this.g = nbttagcompound.c("inTile") & 0xFF;
/* 332 */     this.a = nbttagcompound.c("shake") & 0xFF;
/* 333 */     this.h = (nbttagcompound.c("inGround") == 1);
/*     */   }
/*     */   
/*     */   public int h() {
/* 337 */     byte b0 = 0;
/*     */     
/* 339 */     if (this.c != null) {
/*     */       
/* 341 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), this.c.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
/* 342 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 344 */       if (playerFishEvent.isCancelled()) {
/* 345 */         die();
/* 346 */         this.owner.hookedFish = null;
/* 347 */         return 0;
/*     */       } 
/*     */       
/* 350 */       double d0 = this.owner.locX - this.locX;
/* 351 */       double d1 = this.owner.locY - this.locY;
/* 352 */       double d2 = this.owner.locZ - this.locZ;
/* 353 */       double d3 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/* 354 */       double d4 = 0.1D;
/*     */       
/* 356 */       this.c.motX += d0 * d4;
/* 357 */       this.c.motY += d1 * d4 + MathHelper.a(d3) * 0.08D;
/* 358 */       this.c.motZ += d2 * d4;
/* 359 */       b0 = 3;
/* 360 */     } else if (this.k > 0) {
/* 361 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.RAW_FISH));
/*     */       
/* 363 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), entityitem.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
/* 364 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 366 */       if (playerFishEvent.isCancelled()) {
/* 367 */         die();
/* 368 */         this.owner.hookedFish = null;
/* 369 */         return 0;
/*     */       } 
/*     */       
/* 372 */       double d5 = this.owner.locX - this.locX;
/* 373 */       double d6 = this.owner.locY - this.locY;
/* 374 */       double d7 = this.owner.locZ - this.locZ;
/* 375 */       double d8 = MathHelper.a(d5 * d5 + d6 * d6 + d7 * d7);
/* 376 */       double d9 = 0.1D;
/*     */       
/* 378 */       entityitem.motX = d5 * d9;
/* 379 */       entityitem.motY = d6 * d9 + MathHelper.a(d8) * 0.08D;
/* 380 */       entityitem.motZ = d7 * d9;
/* 381 */       this.world.addEntity(entityitem);
/* 382 */       this.owner.a(StatisticList.B, 1);
/* 383 */       b0 = 1;
/*     */     } 
/*     */     
/* 386 */     if (this.h) {
/*     */       
/* 388 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, PlayerFishEvent.State.IN_GROUND);
/* 389 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 391 */       if (playerFishEvent.isCancelled()) {
/* 392 */         die();
/* 393 */         this.owner.hookedFish = null;
/* 394 */         return 0;
/*     */       } 
/*     */       
/* 397 */       b0 = 2;
/*     */     } 
/*     */ 
/*     */     
/* 401 */     if (b0 == 0) {
/* 402 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, PlayerFishEvent.State.FAILED_ATTEMPT);
/* 403 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */     } 
/*     */     
/* 406 */     die();
/* 407 */     this.owner.hookedFish = null;
/* 408 */     return b0;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityFish.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */