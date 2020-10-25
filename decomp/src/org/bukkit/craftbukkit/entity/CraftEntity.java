/*     */ package org.bukkit.craftbukkit.entity;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityAnimal;
/*     */ import net.minecraft.server.EntityChicken;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityMinecart;
/*     */ import net.minecraft.server.EntitySnowball;
/*     */ import net.minecraft.server.EntityWeatherStorm;
/*     */ import net.minecraft.server.EntityWolf;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public abstract class CraftEntity implements Entity {
/*     */   public CraftEntity(CraftServer server, Entity entity) {
/*  22 */     this.server = server;
/*  23 */     this.entity = entity;
/*     */   }
/*     */   protected final CraftServer server;
/*     */   protected Entity entity;
/*     */   private EntityDamageEvent lastDamageEvent;
/*     */   
/*     */   public static CraftEntity getEntity(CraftServer server, Entity entity) {
/*  30 */     if (entity instanceof EntityLiving) {
/*     */       
/*  32 */       if (entity instanceof EntityHuman) {
/*  33 */         if (entity instanceof EntityPlayer) return new CraftPlayer(server, (EntityPlayer)entity); 
/*  34 */         return new CraftHumanEntity(server, (EntityHuman)entity);
/*     */       } 
/*  36 */       if (entity instanceof EntityCreature) {
/*     */         
/*  38 */         if (entity instanceof EntityAnimal) {
/*  39 */           if (entity instanceof EntityChicken) return new CraftChicken(server, (EntityChicken)entity); 
/*  40 */           if (entity instanceof EntityCow) return new CraftCow(server, (EntityCow)entity); 
/*  41 */           if (entity instanceof EntityPig) return new CraftPig(server, (EntityPig)entity); 
/*  42 */           if (entity instanceof EntityWolf) return new CraftWolf(server, (EntityWolf)entity); 
/*  43 */           if (entity instanceof EntitySheep) return new CraftSheep(server, (EntitySheep)entity); 
/*  44 */           return new CraftAnimals(server, (EntityAnimal)entity);
/*     */         } 
/*     */         
/*  47 */         if (entity instanceof EntityMonster) {
/*  48 */           if (entity instanceof EntityZombie) {
/*  49 */             if (entity instanceof EntityPigZombie) return new CraftPigZombie(server, (EntityPigZombie)entity); 
/*  50 */             return new CraftZombie(server, (EntityZombie)entity);
/*     */           } 
/*  52 */           if (entity instanceof EntityCreeper) return new CraftCreeper(server, (EntityCreeper)entity); 
/*  53 */           if (entity instanceof EntityGiantZombie) return new CraftGiant(server, (EntityGiantZombie)entity); 
/*  54 */           if (entity instanceof EntitySkeleton) return new CraftSkeleton(server, (EntitySkeleton)entity); 
/*  55 */           if (entity instanceof EntitySpider) return new CraftSpider(server, (EntitySpider)entity);
/*     */           
/*  57 */           return new CraftMonster(server, (EntityMonster)entity);
/*     */         } 
/*     */         
/*  60 */         if (entity instanceof EntityWaterAnimal) {
/*  61 */           if (entity instanceof EntitySquid) return new CraftSquid(server, (EntitySquid)entity); 
/*  62 */           return new CraftWaterMob(server, (EntityWaterAnimal)entity);
/*     */         } 
/*  64 */         return new CraftCreature(server, (EntityCreature)entity);
/*     */       } 
/*     */       
/*  67 */       if (entity instanceof EntitySlime) return new CraftSlime(server, (EntitySlime)entity);
/*     */       
/*  69 */       if (entity instanceof EntityFlying) {
/*  70 */         if (entity instanceof EntityGhast) return new CraftGhast(server, (EntityGhast)entity); 
/*  71 */         return new CraftFlying(server, (EntityFlying)entity);
/*     */       } 
/*  73 */       return new CraftLivingEntity(server, (EntityLiving)entity);
/*     */     } 
/*  75 */     if (entity instanceof EntityArrow) return new CraftArrow(server, (EntityArrow)entity); 
/*  76 */     if (entity instanceof EntityBoat) return new CraftBoat(server, (EntityBoat)entity); 
/*  77 */     if (entity instanceof EntityEgg) return new CraftEgg(server, (EntityEgg)entity); 
/*  78 */     if (entity instanceof EntityFallingSand) return new CraftFallingSand(server, (EntityFallingSand)entity); 
/*  79 */     if (entity instanceof EntityFireball) return new CraftFireball(server, (EntityFireball)entity); 
/*  80 */     if (entity instanceof EntityFish) return new CraftFish(server, (EntityFish)entity); 
/*  81 */     if (entity instanceof EntityItem) return new CraftItem(server, (EntityItem)entity); 
/*  82 */     if (entity instanceof EntityWeather) {
/*  83 */       if (entity instanceof EntityWeatherStorm) {
/*  84 */         return new CraftLightningStrike(server, (EntityWeatherStorm)entity);
/*     */       }
/*  86 */       return new CraftWeather(server, (EntityWeather)entity);
/*     */     } 
/*     */     
/*  89 */     if (entity instanceof EntityMinecart) {
/*  90 */       EntityMinecart mc = (EntityMinecart)entity;
/*  91 */       if (mc.type == CraftMinecart.Type.StorageMinecart.getId())
/*  92 */         return new CraftStorageMinecart(server, mc); 
/*  93 */       if (mc.type == CraftMinecart.Type.PoweredMinecart.getId()) {
/*  94 */         return new CraftPoweredMinecart(server, mc);
/*     */       }
/*  96 */       return new CraftMinecart(server, mc);
/*     */     } 
/*     */     
/*  99 */     if (entity instanceof EntityPainting) return new CraftPainting(server, (EntityPainting)entity); 
/* 100 */     if (entity instanceof EntitySnowball) return new CraftSnowball(server, (EntitySnowball)entity); 
/* 101 */     if (entity instanceof EntityTNTPrimed) return new CraftTNTPrimed(server, (EntityTNTPrimed)entity); 
/* 102 */     throw new IllegalArgumentException("Unknown entity");
/*     */   }
/*     */ 
/*     */   
/* 106 */   public Location getLocation() { return new Location(getWorld(), this.entity.locX, this.entity.locY, this.entity.locZ, this.entity.yaw, this.entity.pitch); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public Vector getVelocity() { return new Vector(this.entity.motX, this.entity.motY, this.entity.motZ); }
/*     */ 
/*     */   
/*     */   public void setVelocity(Vector vel) {
/* 114 */     this.entity.motX = vel.getX();
/* 115 */     this.entity.motY = vel.getY();
/* 116 */     this.entity.motZ = vel.getZ();
/* 117 */     this.entity.velocityChanged = true;
/*     */   }
/*     */ 
/*     */   
/* 121 */   public World getWorld() { return ((WorldServer)this.entity.world).getWorld(); }
/*     */ 
/*     */   
/*     */   public boolean teleport(Location location) {
/* 125 */     this.entity.world = ((CraftWorld)location.getWorld()).getHandle();
/* 126 */     this.entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 132 */   public boolean teleport(Entity destination) { return teleport(destination.getLocation()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Entity> getNearbyEntities(double x, double y, double z) {
/* 137 */     List<Entity> notchEntityList = this.entity.world.b(this.entity, this.entity.boundingBox.b(x, y, z));
/* 138 */     List<Entity> bukkitEntityList = new ArrayList<Entity>(notchEntityList.size());
/*     */     
/* 140 */     for (Entity e : notchEntityList) {
/* 141 */       bukkitEntityList.add(e.getBukkitEntity());
/*     */     }
/* 143 */     return bukkitEntityList;
/*     */   }
/*     */ 
/*     */   
/* 147 */   public int getEntityId() { return this.entity.id; }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public int getFireTicks() { return this.entity.fireTicks; }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public int getMaxFireTicks() { return this.entity.maxFireTicks; }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public void setFireTicks(int ticks) { this.entity.fireTicks = ticks; }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public void remove() { this.entity.dead = true; }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public boolean isDead() { return this.entity.dead; }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public Entity getHandle() { return this.entity; }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public void setHandle(Entity entity) { this.entity = entity; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 180 */     if (obj == null) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (getClass() != obj.getClass()) {
/* 184 */       return false;
/*     */     }
/* 186 */     CraftEntity other = (CraftEntity)obj;
/* 187 */     if (this.server != other.server && (this.server == null || !this.server.equals(other.server))) {
/* 188 */       return false;
/*     */     }
/* 190 */     if (this.entity != other.entity && (this.entity == null || !this.entity.equals(other.entity))) {
/* 191 */       return false;
/*     */     }
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 198 */     hash = 7;
/* 199 */     hash = 89 * hash + ((this.server != null) ? this.server.hashCode() : 0);
/* 200 */     return 89 * hash + ((this.entity != null) ? this.entity.hashCode() : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public String toString() { return "CraftEntity{id=" + getEntityId() + '}'; }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public Server getServer() { return this.server; }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public Vector getMomentum() { return getVelocity(); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public void setMomentum(Vector value) { setVelocity(value); }
/*     */ 
/*     */ 
/*     */   
/* 222 */   public Entity getPassenger() { return isEmpty() ? null : (CraftEntity)(getHandle()).passenger.getBukkitEntity(); }
/*     */ 
/*     */   
/*     */   public boolean setPassenger(Entity passenger) {
/* 226 */     if (passenger instanceof CraftEntity) {
/* 227 */       ((CraftEntity)passenger).getHandle().setPassengerOf(getHandle());
/* 228 */       return true;
/*     */     } 
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public boolean isEmpty() { return ((getHandle()).passenger == null); }
/*     */ 
/*     */   
/*     */   public boolean eject() {
/* 239 */     if ((getHandle()).passenger == null) {
/* 240 */       return false;
/*     */     }
/*     */     
/* 243 */     (getHandle()).passenger.setPassengerOf(null);
/* 244 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 248 */   public float getFallDistance() { return (getHandle()).fallDistance; }
/*     */ 
/*     */ 
/*     */   
/* 252 */   public void setFallDistance(float distance) { (getHandle()).fallDistance = distance; }
/*     */ 
/*     */ 
/*     */   
/* 256 */   public void setLastDamageCause(EntityDamageEvent event) { this.lastDamageEvent = event; }
/*     */ 
/*     */ 
/*     */   
/* 260 */   public EntityDamageEvent getLastDamageCause() { return this.lastDamageEvent; }
/*     */ 
/*     */ 
/*     */   
/* 264 */   public UUID getUniqueId() { return (getHandle()).uniqueId; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */