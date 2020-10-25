/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.CraftServer;
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
/*     */ 
/*     */ public class EntityBoat
/*     */   extends Entity
/*     */ {
/*     */   public int damage;
/*     */   public int b;
/*     */   public int c;
/*     */   private int d;
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */   private double h;
/*     */   private double i;
/*  30 */   public double maxSpeed = 0.4D;
/*     */ 
/*     */   
/*     */   public void collide(Entity entity) {
/*  34 */     Entity hitEntity = (entity == null) ? null : entity.getBukkitEntity();
/*     */     
/*  36 */     VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)getBukkitEntity(), hitEntity);
/*  37 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/*  39 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  43 */     super.collide(entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityBoat(World world) {
/*  48 */     super(world);
/*  49 */     this.damage = 0;
/*  50 */     this.b = 0;
/*  51 */     this.c = 1;
/*  52 */     this.aI = true;
/*  53 */     b(1.5F, 0.6F);
/*  54 */     this.height = this.width / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*  58 */   protected boolean n() { return false; }
/*     */ 
/*     */   
/*     */   protected void b() {}
/*     */ 
/*     */   
/*  64 */   public AxisAlignedBB a_(Entity entity) { return entity.boundingBox; }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public AxisAlignedBB e_() { return this.boundingBox; }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean d_() { return true; }
/*     */ 
/*     */   
/*     */   public EntityBoat(World world, double d0, double d1, double d2) {
/*  76 */     this(world);
/*  77 */     setPosition(d0, d1 + this.height, d2);
/*  78 */     this.motX = 0.0D;
/*  79 */     this.motY = 0.0D;
/*  80 */     this.motZ = 0.0D;
/*  81 */     this.lastX = d0;
/*  82 */     this.lastY = d1;
/*  83 */     this.lastZ = d2;
/*     */     
/*  85 */     this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */ 
/*     */   
/*  89 */   public double m() { return this.width * 0.0D - 0.30000001192092896D; }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/*  93 */     if (!this.world.isStatic && !this.dead) {
/*     */       
/*  95 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*  96 */       Entity attacker = (entity == null) ? null : entity.getBukkitEntity();
/*     */       
/*  98 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, i);
/*  99 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 101 */       if (event.isCancelled()) {
/* 102 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 107 */       this.c = -this.c;
/* 108 */       this.b = 10;
/* 109 */       this.damage += i * 10;
/* 110 */       af();
/* 111 */       if (this.damage > 40) {
/*     */ 
/*     */         
/* 114 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
/* 115 */         this.world.getServer().getPluginManager().callEvent(destroyEvent);
/*     */         
/* 117 */         if (destroyEvent.isCancelled()) {
/* 118 */           this.damage = 40;
/* 119 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 123 */         if (this.passenger != null) {
/* 124 */           this.passenger.mount(this);
/*     */         }
/*     */         
/*     */         int j;
/*     */         
/* 129 */         for (j = 0; j < 3; j++) {
/* 130 */           a(Block.WOOD.id, 1, 0.0F);
/*     */         }
/*     */         
/* 133 */         for (j = 0; j < 2; j++) {
/* 134 */           a(Item.STICK.id, 1, 0.0F);
/*     */         }
/*     */         
/* 137 */         die();
/*     */       } 
/*     */       
/* 140 */       return true;
/*     */     } 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public boolean l_() { return !this.dead; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_() {
/* 152 */     double prevX = this.locX;
/* 153 */     double prevY = this.locY;
/* 154 */     double prevZ = this.locZ;
/* 155 */     float prevYaw = this.yaw;
/* 156 */     float prevPitch = this.pitch;
/*     */ 
/*     */     
/* 159 */     super.m_();
/* 160 */     if (this.b > 0) {
/* 161 */       this.b--;
/*     */     }
/*     */     
/* 164 */     if (this.damage > 0) {
/* 165 */       this.damage--;
/*     */     }
/*     */     
/* 168 */     this.lastX = this.locX;
/* 169 */     this.lastY = this.locY;
/* 170 */     this.lastZ = this.locZ;
/* 171 */     byte b0 = 5;
/* 172 */     double d0 = 0.0D;
/*     */     
/* 174 */     for (int i = 0; i < b0; i++) {
/* 175 */       double d1 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 0) / b0 - 0.125D;
/* 176 */       double d2 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 1) / b0 - 0.125D;
/* 177 */       AxisAlignedBB axisalignedbb = AxisAlignedBB.b(this.boundingBox.a, d1, this.boundingBox.c, this.boundingBox.d, d2, this.boundingBox.f);
/*     */       
/* 179 */       if (this.world.b(axisalignedbb, Material.WATER)) {
/* 180 */         d0 += 1.0D / b0;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (this.world.isStatic) {
/* 190 */       if (this.d > 0) {
/* 191 */         double d3 = this.locX + (this.e - this.locX) / this.d;
/* 192 */         double d4 = this.locY + (this.f - this.locY) / this.d;
/* 193 */         double d5 = this.locZ + (this.g - this.locZ) / this.d;
/*     */         double d6;
/* 195 */         for (d6 = this.h - this.yaw; d6 < -180.0D; d6 += 360.0D);
/*     */ 
/*     */ 
/*     */         
/* 199 */         while (d6 >= 180.0D) {
/* 200 */           d6 -= 360.0D;
/*     */         }
/*     */         
/* 203 */         this.yaw = (float)(this.yaw + d6 / this.d);
/* 204 */         this.pitch = (float)(this.pitch + (this.i - this.pitch) / this.d);
/* 205 */         this.d--;
/* 206 */         setPosition(d3, d4, d5);
/* 207 */         c(this.yaw, this.pitch);
/*     */       } else {
/* 209 */         double d3 = this.locX + this.motX;
/* 210 */         double d4 = this.locY + this.motY;
/* 211 */         double d5 = this.locZ + this.motZ;
/* 212 */         setPosition(d3, d4, d5);
/* 213 */         if (this.onGround) {
/* 214 */           this.motX *= 0.5D;
/* 215 */           this.motY *= 0.5D;
/* 216 */           this.motZ *= 0.5D;
/*     */         } 
/*     */         
/* 219 */         this.motX *= 0.9900000095367432D;
/* 220 */         this.motY *= 0.949999988079071D;
/* 221 */         this.motZ *= 0.9900000095367432D;
/*     */       } 
/*     */     } else {
/* 224 */       if (d0 < 1.0D) {
/* 225 */         double d3 = d0 * 2.0D - 1.0D;
/* 226 */         this.motY += 0.03999999910593033D * d3;
/*     */       } else {
/* 228 */         if (this.motY < 0.0D) {
/* 229 */           this.motY /= 2.0D;
/*     */         }
/*     */         
/* 232 */         this.motY += 0.007000000216066837D;
/*     */       } 
/*     */       
/* 235 */       if (this.passenger != null) {
/* 236 */         this.motX += this.passenger.motX * 0.2D;
/* 237 */         this.motZ += this.passenger.motZ * 0.2D;
/*     */       } 
/*     */ 
/*     */       
/* 241 */       double d3 = this.maxSpeed;
/* 242 */       if (this.motX < -d3) {
/* 243 */         this.motX = -d3;
/*     */       }
/*     */       
/* 246 */       if (this.motX > d3) {
/* 247 */         this.motX = d3;
/*     */       }
/*     */       
/* 250 */       if (this.motZ < -d3) {
/* 251 */         this.motZ = -d3;
/*     */       }
/*     */       
/* 254 */       if (this.motZ > d3) {
/* 255 */         this.motZ = d3;
/*     */       }
/*     */       
/* 258 */       if (this.onGround) {
/* 259 */         this.motX *= 0.5D;
/* 260 */         this.motY *= 0.5D;
/* 261 */         this.motZ *= 0.5D;
/*     */       } 
/*     */       
/* 264 */       move(this.motX, this.motY, this.motZ);
/* 265 */       double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 266 */       if (d4 > 0.15D) {
/* 267 */         double d5 = Math.cos(this.yaw * Math.PI / 180.0D);
/* 268 */         double d6 = Math.sin(this.yaw * Math.PI / 180.0D);
/*     */         
/* 270 */         for (int j = 0; j < 1.0D + d4 * 60.0D; j++) {
/* 271 */           double d7 = (this.random.nextFloat() * 2.0F - 1.0F);
/* 272 */           double d8 = (this.random.nextInt(2) * 2 - 1) * 0.7D;
/*     */ 
/*     */ 
/*     */           
/* 276 */           if (this.random.nextBoolean()) {
/* 277 */             double d9 = this.locX - d5 * d7 * 0.8D + d6 * d8;
/* 278 */             double d10 = this.locZ - d6 * d7 * 0.8D - d5 * d8;
/* 279 */             this.world.a("splash", d9, this.locY - 0.125D, d10, this.motX, this.motY, this.motZ);
/*     */           } else {
/* 281 */             double d9 = this.locX + d5 + d6 * d7 * 0.7D;
/* 282 */             double d10 = this.locZ + d6 - d5 * d7 * 0.7D;
/* 283 */             this.world.a("splash", d9, this.locY - 0.125D, d10, this.motX, this.motY, this.motZ);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 288 */       if (this.positionChanged && d4 > 0.15D) {
/* 289 */         if (!this.world.isStatic) {
/* 290 */           die();
/*     */           
/*     */           int k;
/*     */           
/* 294 */           for (k = 0; k < 3; k++) {
/* 295 */             a(Block.WOOD.id, 1, 0.0F);
/*     */           }
/*     */           
/* 298 */           for (k = 0; k < 2; k++) {
/* 299 */             a(Item.STICK.id, 1, 0.0F);
/*     */           }
/*     */         } 
/*     */       } else {
/* 303 */         this.motX *= 0.9900000095367432D;
/* 304 */         this.motY *= 0.949999988079071D;
/* 305 */         this.motZ *= 0.9900000095367432D;
/*     */       } 
/*     */       
/* 308 */       this.pitch = 0.0F;
/* 309 */       double d5 = this.yaw;
/* 310 */       double d6 = this.lastX - this.locX;
/* 311 */       double d11 = this.lastZ - this.locZ;
/*     */       
/* 313 */       if (d6 * d6 + d11 * d11 > 0.001D) {
/* 314 */         d5 = (float)(Math.atan2(d11, d6) * 180.0D / Math.PI);
/*     */       }
/*     */       
/*     */       double d12;
/*     */       
/* 319 */       for (d12 = d5 - this.yaw; d12 >= 180.0D; d12 -= 360.0D);
/*     */ 
/*     */ 
/*     */       
/* 323 */       while (d12 < -180.0D) {
/* 324 */         d12 += 360.0D;
/*     */       }
/*     */       
/* 327 */       if (d12 > 20.0D) {
/* 328 */         d12 = 20.0D;
/*     */       }
/*     */       
/* 331 */       if (d12 < -20.0D) {
/* 332 */         d12 = -20.0D;
/*     */       }
/*     */       
/* 335 */       this.yaw = (float)(this.yaw + d12);
/* 336 */       c(this.yaw, this.pitch);
/*     */ 
/*     */       
/* 339 */       CraftServer craftServer = this.world.getServer();
/* 340 */       CraftWorld craftWorld = this.world.getWorld();
/*     */       
/* 342 */       Location from = new Location(craftWorld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 343 */       Location to = new Location(craftWorld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 344 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 346 */       craftServer.getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
/*     */       
/* 348 */       if (!from.equals(to)) {
/* 349 */         VehicleMoveEvent event = new VehicleMoveEvent(vehicle, from, to);
/* 350 */         craftServer.getPluginManager().callEvent(event);
/*     */       } 
/*     */ 
/*     */       
/* 354 */       List list = this.world.b(this, this.boundingBox.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */ 
/*     */       
/* 357 */       if (list != null && list.size() > 0) {
/* 358 */         for (int l = 0; l < list.size(); l++) {
/* 359 */           Entity entity = (Entity)list.get(l);
/*     */           
/* 361 */           if (entity != this.passenger && entity.d_() && entity instanceof EntityBoat) {
/* 362 */             entity.collide(this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 367 */       for (int l = 0; l < 4; l++) {
/* 368 */         int i1 = MathHelper.floor(this.locX + ((l % 2) - 0.5D) * 0.8D);
/* 369 */         int j1 = MathHelper.floor(this.locY);
/* 370 */         int k1 = MathHelper.floor(this.locZ + ((l / 2) - 0.5D) * 0.8D);
/*     */         
/* 372 */         if (this.world.getTypeId(i1, j1, k1) == Block.SNOW.id) {
/* 373 */           this.world.setTypeId(i1, j1, k1, 0);
/*     */         }
/*     */       } 
/*     */       
/* 377 */       if (this.passenger != null && this.passenger.dead) {
/* 378 */         this.passenger.vehicle = null;
/* 379 */         this.passenger = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void f() {
/* 385 */     if (this.passenger != null) {
/* 386 */       double d0 = Math.cos(this.yaw * Math.PI / 180.0D) * 0.4D;
/* 387 */       double d1 = Math.sin(this.yaw * Math.PI / 180.0D) * 0.4D;
/*     */       
/* 389 */       this.passenger.setPosition(this.locX + d0, this.locY + m() + this.passenger.I(), this.locZ + d1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 398 */     if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityhuman) {
/* 399 */       return true;
/*     */     }
/* 401 */     if (!this.world.isStatic) {
/*     */       
/* 403 */       VehicleEnterEvent event = new VehicleEnterEvent((Vehicle)getBukkitEntity(), entityhuman.getBukkitEntity());
/* 404 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 406 */       if (event.isCancelled()) {
/* 407 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 411 */       entityhuman.mount(this);
/*     */     } 
/*     */     
/* 414 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityBoat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */