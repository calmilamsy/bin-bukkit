/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityGhast
/*     */   extends EntityFlying
/*     */   implements IMonster
/*     */ {
/*  10 */   public int a = 0;
/*     */   public double b;
/*     */   public double c;
/*     */   public double d;
/*  14 */   private Entity target = null;
/*  15 */   private int h = 0;
/*  16 */   public int e = 0;
/*  17 */   public int f = 0;
/*     */   
/*     */   public EntityGhast(World world) {
/*  20 */     super(world);
/*  21 */     this.texture = "/mob/ghast.png";
/*  22 */     b(4.0F, 4.0F);
/*  23 */     this.fireProof = true;
/*     */   }
/*     */   
/*     */   protected void b() {
/*  27 */     super.b();
/*  28 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void m_() {
/*  32 */     super.m_();
/*  33 */     byte b0 = this.datawatcher.a(16);
/*     */     
/*  35 */     this.texture = (b0 == 1) ? "/mob/ghast_fire.png" : "/mob/ghast.png";
/*     */   }
/*     */   
/*     */   protected void c_() {
/*  39 */     if (!this.world.isStatic && this.world.spawnMonsters == 0) {
/*  40 */       die();
/*     */     }
/*     */     
/*  43 */     U();
/*  44 */     this.e = this.f;
/*  45 */     double d0 = this.b - this.locX;
/*  46 */     double d1 = this.c - this.locY;
/*  47 */     double d2 = this.d - this.locZ;
/*  48 */     double d3 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  50 */     if (d3 < 1.0D || d3 > 60.0D) {
/*  51 */       this.b = this.locX + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  52 */       this.c = this.locY + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  53 */       this.d = this.locZ + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*     */     } 
/*     */     
/*  56 */     if (this.a-- <= 0) {
/*  57 */       this.a += this.random.nextInt(5) + 2;
/*  58 */       if (a(this.b, this.c, this.d, d3)) {
/*  59 */         this.motX += d0 / d3 * 0.1D;
/*  60 */         this.motY += d1 / d3 * 0.1D;
/*  61 */         this.motZ += d2 / d3 * 0.1D;
/*     */       } else {
/*  63 */         this.b = this.locX;
/*  64 */         this.c = this.locY;
/*  65 */         this.d = this.locZ;
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     if (this.target != null && this.target.dead) {
/*     */       
/*  71 */       EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
/*  72 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/*  74 */       if (!event.isCancelled()) {
/*  75 */         if (event.getTarget() == null) {
/*  76 */           this.target = null;
/*     */         } else {
/*  78 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     if (this.target == null || this.h-- <= 0) {
/*     */       
/*  86 */       Entity target = this.world.findNearbyPlayer(this, 100.0D);
/*  87 */       if (target != null) {
/*  88 */         EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/*  89 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  91 */         if (!event.isCancelled()) {
/*  92 */           if (event.getTarget() == null) {
/*  93 */             this.target = null;
/*     */           } else {
/*  95 */             this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 100 */       if (this.target != null) {
/* 101 */         this.h = 20;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     double d4 = 64.0D;
/*     */     
/* 107 */     if (this.target != null && this.target.g(this) < d4 * d4) {
/* 108 */       double d5 = this.target.locX - this.locX;
/* 109 */       double d6 = this.target.boundingBox.b + (this.target.width / 2.0F) - this.locY + (this.width / 2.0F);
/* 110 */       double d7 = this.target.locZ - this.locZ;
/*     */       
/* 112 */       this.K = this.yaw = -((float)Math.atan2(d5, d7)) * 180.0F / 3.1415927F;
/* 113 */       if (e(this.target)) {
/* 114 */         if (this.f == 10) {
/* 115 */           this.world.makeSound(this, "mob.ghast.charge", k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */         }
/*     */         
/* 118 */         this.f++;
/* 119 */         if (this.f == 20) {
/* 120 */           this.world.makeSound(this, "mob.ghast.fireball", k(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 121 */           EntityFireball entityfireball = new EntityFireball(this.world, this, d5, d6, d7);
/* 122 */           double d8 = 4.0D;
/* 123 */           Vec3D vec3d = b(1.0F);
/*     */           
/* 125 */           entityfireball.locX = this.locX + vec3d.a * d8;
/* 126 */           entityfireball.locY = this.locY + (this.width / 2.0F) + 0.5D;
/* 127 */           entityfireball.locZ = this.locZ + vec3d.c * d8;
/* 128 */           this.world.addEntity(entityfireball);
/* 129 */           this.f = -40;
/*     */         } 
/* 131 */       } else if (this.f > 0) {
/* 132 */         this.f--;
/*     */       } 
/*     */     } else {
/* 135 */       this.K = this.yaw = -((float)Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F;
/* 136 */       if (this.f > 0) {
/* 137 */         this.f--;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     if (!this.world.isStatic) {
/* 142 */       byte b0 = this.datawatcher.a(16);
/* 143 */       byte b1 = (byte)((this.f > 10) ? 1 : 0);
/*     */       
/* 145 */       if (b0 != b1) {
/* 146 */         this.datawatcher.watch(16, Byte.valueOf(b1));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(double d0, double d1, double d2, double d3) {
/* 152 */     double d4 = (this.b - this.locX) / d3;
/* 153 */     double d5 = (this.c - this.locY) / d3;
/* 154 */     double d6 = (this.d - this.locZ) / d3;
/* 155 */     AxisAlignedBB axisalignedbb = this.boundingBox.clone();
/*     */     
/* 157 */     for (int i = 1; i < d3; i++) {
/* 158 */       axisalignedbb.d(d4, d5, d6);
/* 159 */       if (this.world.getEntities(this, axisalignedbb).size() > 0) {
/* 160 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 168 */   protected String g() { return "mob.ghast.moan"; }
/*     */ 
/*     */ 
/*     */   
/* 172 */   protected String h() { return "mob.ghast.scream"; }
/*     */ 
/*     */ 
/*     */   
/* 176 */   protected String i() { return "mob.ghast.death"; }
/*     */ 
/*     */ 
/*     */   
/* 180 */   protected int j() { return Item.SULPHUR.id; }
/*     */ 
/*     */ 
/*     */   
/* 184 */   protected float k() { return 10.0F; }
/*     */ 
/*     */ 
/*     */   
/* 188 */   public boolean d() { return (this.random.nextInt(20) == 0 && super.d() && this.world.spawnMonsters > 0); }
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int l() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityGhast.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */