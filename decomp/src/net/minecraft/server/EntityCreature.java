/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.TrigMath;
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityCreature
/*     */   extends EntityLiving
/*     */ {
/*     */   public PathEntity pathEntity;
/*     */   public Entity target;
/*     */   protected boolean e = false;
/*     */   
/*  16 */   public EntityCreature(World world) { super(world); }
/*     */ 
/*     */ 
/*     */   
/*  20 */   protected boolean w() { return false; }
/*     */ 
/*     */   
/*     */   protected void c_() {
/*  24 */     this.e = w();
/*  25 */     float f = 16.0F;
/*     */     
/*  27 */     if (this.target == null) {
/*     */       
/*  29 */       Entity target = findTarget();
/*  30 */       if (target != null) {
/*  31 */         EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/*  32 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  34 */         if (!event.isCancelled()) {
/*  35 */           if (event.getTarget() == null) {
/*  36 */             this.target = null;
/*     */           } else {
/*  38 */             this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  44 */       if (this.target != null) {
/*  45 */         this.pathEntity = this.world.findPath(this, this.target, f);
/*     */       }
/*  47 */     } else if (!this.target.T()) {
/*     */       
/*  49 */       EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
/*  50 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/*  52 */       if (!event.isCancelled()) {
/*  53 */         if (event.getTarget() == null) {
/*  54 */           this.target = null;
/*     */         } else {
/*  56 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/*  61 */       float f1 = this.target.f(this);
/*     */       
/*  63 */       if (e(this.target)) {
/*  64 */         a(this.target, f1);
/*     */       } else {
/*  66 */         b(this.target, f1);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     if (!this.e && this.target != null && (this.pathEntity == null || this.random.nextInt(20) == 0)) {
/*  71 */       this.pathEntity = this.world.findPath(this, this.target, f);
/*  72 */     } else if (!this.e && ((this.pathEntity == null && this.random.nextInt(80) == 0) || this.random.nextInt(80) == 0)) {
/*  73 */       B();
/*     */     } 
/*     */     
/*  76 */     int i = MathHelper.floor(this.boundingBox.b + 0.5D);
/*  77 */     boolean flag = ad();
/*  78 */     boolean flag1 = ae();
/*     */     
/*  80 */     this.pitch = 0.0F;
/*  81 */     if (this.pathEntity != null && this.random.nextInt(100) != 0) {
/*  82 */       Vec3D vec3d = this.pathEntity.a(this);
/*  83 */       double d0 = (this.length * 2.0F);
/*     */       
/*  85 */       while (vec3d != null && vec3d.d(this.locX, vec3d.b, this.locZ) < d0 * d0) {
/*  86 */         this.pathEntity.a();
/*  87 */         if (this.pathEntity.b()) {
/*  88 */           vec3d = null;
/*  89 */           this.pathEntity = null; continue;
/*     */         } 
/*  91 */         vec3d = this.pathEntity.a(this);
/*     */       } 
/*     */ 
/*     */       
/*  95 */       this.aC = false;
/*  96 */       if (vec3d != null) {
/*  97 */         double d1 = vec3d.a - this.locX;
/*  98 */         double d2 = vec3d.c - this.locZ;
/*  99 */         double d3 = vec3d.b - i;
/*     */         
/* 101 */         float f2 = (float)(TrigMath.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 102 */         float f3 = f2 - this.yaw;
/*     */         
/* 104 */         for (this.aA = this.aE; f3 < -180.0F; f3 += 360.0F);
/*     */ 
/*     */ 
/*     */         
/* 108 */         while (f3 >= 180.0F) {
/* 109 */           f3 -= 360.0F;
/*     */         }
/*     */         
/* 112 */         if (f3 > 30.0F) {
/* 113 */           f3 = 30.0F;
/*     */         }
/*     */         
/* 116 */         if (f3 < -30.0F) {
/* 117 */           f3 = -30.0F;
/*     */         }
/*     */         
/* 120 */         this.yaw += f3;
/* 121 */         if (this.e && this.target != null) {
/* 122 */           double d4 = this.target.locX - this.locX;
/* 123 */           double d5 = this.target.locZ - this.locZ;
/* 124 */           float f4 = this.yaw;
/*     */           
/* 126 */           this.yaw = (float)(Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 127 */           f3 = (f4 - this.yaw + 90.0F) * 3.1415927F / 180.0F;
/* 128 */           this.az = -MathHelper.sin(f3) * this.aA * 1.0F;
/* 129 */           this.aA = MathHelper.cos(f3) * this.aA * 1.0F;
/*     */         } 
/*     */         
/* 132 */         if (d3 > 0.0D) {
/* 133 */           this.aC = true;
/*     */         }
/*     */       } 
/*     */       
/* 137 */       if (this.target != null) {
/* 138 */         a(this.target, 30.0F, 30.0F);
/*     */       }
/*     */       
/* 141 */       if (this.positionChanged && !C()) {
/* 142 */         this.aC = true;
/*     */       }
/*     */       
/* 145 */       if (this.random.nextFloat() < 0.8F && (flag || flag1)) {
/* 146 */         this.aC = true;
/*     */       }
/*     */     } else {
/* 149 */       super.c_();
/* 150 */       this.pathEntity = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void B() {
/* 155 */     boolean flag = false;
/* 156 */     int i = -1;
/* 157 */     int j = -1;
/* 158 */     int k = -1;
/* 159 */     float f = -99999.0F;
/*     */     
/* 161 */     for (int l = 0; l < 10; l++) {
/* 162 */       int i1 = MathHelper.floor(this.locX + this.random.nextInt(13) - 6.0D);
/* 163 */       int j1 = MathHelper.floor(this.locY + this.random.nextInt(7) - 3.0D);
/* 164 */       int k1 = MathHelper.floor(this.locZ + this.random.nextInt(13) - 6.0D);
/* 165 */       float f1 = a(i1, j1, k1);
/*     */       
/* 167 */       if (f1 > f) {
/* 168 */         f = f1;
/* 169 */         i = i1;
/* 170 */         j = j1;
/* 171 */         k = k1;
/* 172 */         flag = true;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (flag) {
/* 177 */       this.pathEntity = this.world.a(this, i, j, k, 10.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(Entity entity, float f) {}
/*     */   
/*     */   protected void b(Entity entity, float f) {}
/*     */   
/* 186 */   protected float a(int i, int j, int k) { return 0.0F; }
/*     */ 
/*     */ 
/*     */   
/* 190 */   protected Entity findTarget() { return null; }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 194 */     int i = MathHelper.floor(this.locX);
/* 195 */     int j = MathHelper.floor(this.boundingBox.b);
/* 196 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 198 */     return (super.d() && a(i, j, k) >= 0.0F);
/*     */   }
/*     */ 
/*     */   
/* 202 */   public boolean C() { return (this.pathEntity != null); }
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void setPathEntity(PathEntity pathentity) { this.pathEntity = pathentity; }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public Entity F() { return this.target; }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public void setTarget(Entity entity) { this.target = entity; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityCreature.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */