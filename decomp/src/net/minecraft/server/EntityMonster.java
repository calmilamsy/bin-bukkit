/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityMonster
/*     */   extends EntityCreature
/*     */   implements IMonster {
/*  12 */   protected int damage = 2;
/*     */   
/*     */   public EntityMonster(World world) {
/*  15 */     super(world);
/*  16 */     this.health = 20;
/*     */   }
/*     */   
/*     */   public void v() {
/*  20 */     float f = c(1.0F);
/*     */     
/*  22 */     if (f > 0.5F) {
/*  23 */       this.ay += 2;
/*     */     }
/*     */     
/*  26 */     super.v();
/*     */   }
/*     */   
/*     */   public void m_() {
/*  30 */     super.m_();
/*  31 */     if (!this.world.isStatic && this.world.spawnMonsters == 0) {
/*  32 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  37 */     EntityHuman entityhuman = this.world.findNearbyPlayer(this, 16.0D);
/*     */     
/*  39 */     return (entityhuman != null && e(entityhuman)) ? entityhuman : null;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/*  43 */     if (super.damageEntity(entity, i)) {
/*  44 */       if (this.passenger != entity && this.vehicle != entity) {
/*  45 */         if (entity != this) {
/*     */           
/*  47 */           Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
/*     */           
/*  49 */           EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
/*  50 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/*  52 */           if (!event.isCancelled()) {
/*  53 */             if (event.getTarget() == null) {
/*  54 */               this.target = null;
/*     */             } else {
/*  56 */               this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  62 */         return true;
/*     */       } 
/*  64 */       return true;
/*     */     } 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(Entity entity, float f) {
/*  72 */     if (this.attackTicks <= 0 && f < 2.0F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
/*  73 */       this.attackTicks = 20;
/*     */ 
/*     */ 
/*     */       
/*  77 */       if (entity instanceof EntityLiving && !(entity instanceof EntityHuman)) {
/*  78 */         Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
/*     */         
/*  80 */         EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, this.damage);
/*  81 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  83 */         if (!event.isCancelled()) {
/*  84 */           entity.damageEntity(this, event.getDamage());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  90 */       entity.damageEntity(this, this.damage);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  95 */   protected float a(int i, int j, int k) { return 0.5F - this.world.n(i, j, k); }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void b(NBTTagCompound nbttagcompound) { super.b(nbttagcompound); }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void a(NBTTagCompound nbttagcompound) { super.a(nbttagcompound); }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 107 */     int i = MathHelper.floor(this.locX);
/* 108 */     int j = MathHelper.floor(this.boundingBox.b);
/* 109 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 111 */     if (this.world.a(EnumSkyBlock.SKY, i, j, k) > this.random.nextInt(32)) {
/* 112 */       return false;
/*     */     }
/* 114 */     int l = this.world.getLightLevel(i, j, k);
/*     */     
/* 116 */     if (this.world.u()) {
/* 117 */       int i1 = this.world.f;
/*     */       
/* 119 */       this.world.f = 10;
/* 120 */       l = this.world.getLightLevel(i, j, k);
/* 121 */       this.world.f = i1;
/*     */     } 
/*     */     
/* 124 */     return (l <= this.random.nextInt(8) && super.d());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityMonster.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */