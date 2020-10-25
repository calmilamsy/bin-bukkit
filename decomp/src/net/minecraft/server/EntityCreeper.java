/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.entity.CraftEntity;
/*     */ import org.bukkit.event.entity.CreeperPowerEvent;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ 
/*     */ public class EntityCreeper
/*     */   extends EntityMonster
/*     */ {
/*     */   int fuseTicks;
/*     */   int b;
/*     */   
/*     */   public EntityCreeper(World world) {
/*  15 */     super(world);
/*  16 */     this.texture = "/mob/creeper.png";
/*     */   }
/*     */   
/*     */   protected void b() {
/*  20 */     super.b();
/*  21 */     this.datawatcher.a(16, Byte.valueOf((byte)-1));
/*  22 */     this.datawatcher.a(17, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  26 */     super.b(nbttagcompound);
/*  27 */     if (this.datawatcher.a(17) == 1) {
/*  28 */       nbttagcompound.a("powered", true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  33 */     super.a(nbttagcompound);
/*  34 */     this.datawatcher.watch(17, Byte.valueOf((byte)(nbttagcompound.m("powered") ? 1 : 0)));
/*     */   }
/*     */   
/*     */   protected void b(Entity entity, float f) {
/*  38 */     if (!this.world.isStatic && 
/*  39 */       this.fuseTicks > 0) {
/*  40 */       e(-1);
/*  41 */       this.fuseTicks--;
/*  42 */       if (this.fuseTicks < 0) {
/*  43 */         this.fuseTicks = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_() {
/*  50 */     this.b = this.fuseTicks;
/*  51 */     if (this.world.isStatic) {
/*  52 */       int i = x();
/*     */       
/*  54 */       if (i > 0 && this.fuseTicks == 0) {
/*  55 */         this.world.makeSound(this, "random.fuse", 1.0F, 0.5F);
/*     */       }
/*     */       
/*  58 */       this.fuseTicks += i;
/*  59 */       if (this.fuseTicks < 0) {
/*  60 */         this.fuseTicks = 0;
/*     */       }
/*     */       
/*  63 */       if (this.fuseTicks >= 30) {
/*  64 */         this.fuseTicks = 30;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     super.m_();
/*  69 */     if (this.target == null && this.fuseTicks > 0) {
/*  70 */       e(-1);
/*  71 */       this.fuseTicks--;
/*  72 */       if (this.fuseTicks < 0) {
/*  73 */         this.fuseTicks = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  79 */   protected String h() { return "mob.creeper"; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected String i() { return "mob.creeperdeath"; }
/*     */ 
/*     */   
/*     */   public void die(Entity entity) {
/*  87 */     super.die(entity);
/*  88 */     if (entity instanceof EntitySkeleton) {
/*  89 */       b(Item.GOLD_RECORD.id + this.random.nextInt(2), 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(Entity entity, float f) {
/*  94 */     if (!this.world.isStatic) {
/*  95 */       int i = x();
/*     */       
/*  97 */       if ((i > 0 || f >= 3.0F) && (i <= 0 || f >= 7.0F)) {
/*  98 */         e(-1);
/*  99 */         this.fuseTicks--;
/* 100 */         if (this.fuseTicks < 0) {
/* 101 */           this.fuseTicks = 0;
/*     */         }
/*     */       } else {
/* 104 */         if (this.fuseTicks == 0) {
/* 105 */           this.world.makeSound(this, "random.fuse", 1.0F, 0.5F);
/*     */         }
/*     */         
/* 108 */         e(1);
/* 109 */         this.fuseTicks++;
/* 110 */         if (this.fuseTicks >= 30) {
/*     */           
/* 112 */           float radius = isPowered() ? 6.0F : 3.0F;
/*     */           
/* 114 */           ExplosionPrimeEvent event = new ExplosionPrimeEvent(CraftEntity.getEntity(this.world.getServer(), this), radius, false);
/* 115 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 117 */           if (!event.isCancelled()) {
/* 118 */             this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire());
/* 119 */             die();
/*     */           } else {
/* 121 */             this.fuseTicks = 0;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 126 */         this.e = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 132 */   public boolean isPowered() { return (this.datawatcher.a(17) == 1); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   protected int j() { return Item.SULPHUR.id; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   private int x() { return this.datawatcher.a(16); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   private void e(int i) { this.datawatcher.watch(16, Byte.valueOf((byte)i)); }
/*     */ 
/*     */   
/*     */   public void a(EntityWeatherStorm entityweatherstorm) {
/* 148 */     super.a(entityweatherstorm);
/*     */ 
/*     */     
/* 151 */     CreeperPowerEvent event = new CreeperPowerEvent(getBukkitEntity(), entityweatherstorm.getBukkitEntity(), CreeperPowerEvent.PowerCause.LIGHTNING);
/* 152 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 154 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     setPowered(true);
/*     */   }
/*     */   
/*     */   public void setPowered(boolean powered) {
/* 162 */     if (!powered) {
/* 163 */       this.datawatcher.watch(17, Byte.valueOf((byte)0));
/*     */     } else {
/*     */       
/* 166 */       this.datawatcher.watch(17, Byte.valueOf((byte)1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityCreeper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */