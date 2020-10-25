/*     */ package org.bukkit.craftbukkit.entity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityArrow;
/*     */ import net.minecraft.server.EntityEgg;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityPlayer;
/*     */ import net.minecraft.server.EntitySnowball;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.util.BlockIterator;
/*     */ 
/*     */ public class CraftLivingEntity
/*     */   extends CraftEntity
/*     */   implements LivingEntity
/*     */ {
/*  29 */   public CraftLivingEntity(CraftServer server, EntityLiving entity) { super(server, entity); }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public int getHealth() { return (getHandle()).health; }
/*     */ 
/*     */   
/*     */   public void setHealth(int health) {
/*  37 */     if (health < 0 || health > 200) {
/*  38 */       throw new IllegalArgumentException("Health must be between 0 and 200");
/*     */     }
/*     */     
/*  41 */     if (this.entity instanceof EntityPlayer && health == 0) {
/*  42 */       ((EntityPlayer)this.entity).die((Entity)null);
/*     */     }
/*     */     
/*  45 */     (getHandle()).health = health;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public EntityLiving getHandle() { return (EntityLiving)this.entity; }
/*     */ 
/*     */   
/*     */   public void setHandle(EntityLiving entity) {
/*  54 */     setHandle(entity);
/*  55 */     this.entity = entity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public String toString() { return "CraftLivingEntity{id=" + getEntityId() + '}'; }
/*     */ 
/*     */   
/*     */   public Egg throwEgg() {
/*  64 */     WorldServer worldServer = ((CraftWorld)getWorld()).getHandle();
/*  65 */     EntityEgg egg = new EntityEgg(worldServer, getHandle());
/*     */     
/*  67 */     worldServer.addEntity(egg);
/*  68 */     return (Egg)egg.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public Snowball throwSnowball() {
/*  72 */     WorldServer worldServer = ((CraftWorld)getWorld()).getHandle();
/*  73 */     EntitySnowball snowball = new EntitySnowball(worldServer, getHandle());
/*     */     
/*  75 */     worldServer.addEntity(snowball);
/*  76 */     return (Snowball)snowball.getBukkitEntity();
/*     */   }
/*     */ 
/*     */   
/*  80 */   public double getEyeHeight() { return 1.0D; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public double getEyeHeight(boolean ignoreSneaking) { return getEyeHeight(); }
/*     */ 
/*     */   
/*     */   private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
/*  88 */     if (maxDistance > 120) {
/*  89 */       maxDistance = 120;
/*     */     }
/*  91 */     ArrayList<Block> blocks = new ArrayList<Block>();
/*  92 */     BlockIterator blockIterator = new BlockIterator(this, maxDistance);
/*  93 */     while (blockIterator.hasNext()) {
/*  94 */       Block block = (Block)blockIterator.next();
/*  95 */       blocks.add(block);
/*  96 */       if (maxLength != 0 && blocks.size() > maxLength) {
/*  97 */         blocks.remove(0);
/*     */       }
/*  99 */       int id = block.getTypeId();
/* 100 */       if ((transparent == null) ? (
/* 101 */         id != 0) : 
/*     */ 
/*     */ 
/*     */         
/* 105 */         !transparent.contains(Byte.valueOf((byte)id))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return blocks;
/*     */   }
/*     */ 
/*     */   
/* 114 */   public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) { return getLineOfSight(transparent, maxDistance, 0); }
/*     */ 
/*     */   
/*     */   public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
/* 118 */     List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
/* 119 */     return (Block)blocks.get(0);
/*     */   }
/*     */ 
/*     */   
/* 123 */   public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) { return getLineOfSight(transparent, maxDistance, 2); }
/*     */ 
/*     */   
/*     */   public Arrow shootArrow() {
/* 127 */     WorldServer worldServer = ((CraftWorld)getWorld()).getHandle();
/* 128 */     EntityArrow arrow = new EntityArrow(worldServer, getHandle());
/*     */     
/* 130 */     worldServer.addEntity(arrow);
/* 131 */     return (Arrow)arrow.getBukkitEntity();
/*     */   }
/*     */ 
/*     */   
/* 135 */   public boolean isInsideVehicle() { return ((getHandle()).vehicle != null); }
/*     */ 
/*     */   
/*     */   public boolean leaveVehicle() {
/* 139 */     if ((getHandle()).vehicle == null) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     getHandle().setPassengerOf(null);
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public Vehicle getVehicle() {
/* 148 */     if ((getHandle()).vehicle == null) {
/* 149 */       return null;
/*     */     }
/*     */     
/* 152 */     Entity vehicle = (getHandle()).vehicle.getBukkitEntity();
/* 153 */     if (vehicle instanceof Vehicle) {
/* 154 */       return (Vehicle)vehicle;
/*     */     }
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 161 */   public int getRemainingAir() { return (getHandle()).airTicks; }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public void setRemainingAir(int ticks) { (getHandle()).airTicks = ticks; }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public int getMaximumAir() { return (getHandle()).maxAirTicks; }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public void setMaximumAir(int ticks) { (getHandle()).maxAirTicks = ticks; }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public void damage(int amount) { this.entity.damageEntity((Entity)null, amount); }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void damage(int amount, Entity source) { this.entity.damageEntity(((CraftEntity)source).getHandle(), amount); }
/*     */ 
/*     */   
/*     */   public Location getEyeLocation() {
/* 185 */     Location loc = getLocation();
/* 186 */     loc.setY(loc.getY() + getEyeHeight());
/* 187 */     return loc;
/*     */   }
/*     */ 
/*     */   
/* 191 */   public int getMaximumNoDamageTicks() { return (getHandle()).maxNoDamageTicks; }
/*     */ 
/*     */ 
/*     */   
/* 195 */   public void setMaximumNoDamageTicks(int ticks) { (getHandle()).maxNoDamageTicks = ticks; }
/*     */ 
/*     */ 
/*     */   
/* 199 */   public int getLastDamage() { return (getHandle()).lastDamage; }
/*     */ 
/*     */ 
/*     */   
/* 203 */   public void setLastDamage(int damage) { (getHandle()).lastDamage = damage; }
/*     */ 
/*     */ 
/*     */   
/* 207 */   public int getNoDamageTicks() { return (getHandle()).noDamageTicks; }
/*     */ 
/*     */ 
/*     */   
/* 211 */   public void setNoDamageTicks(int ticks) { (getHandle()).noDamageTicks = ticks; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftLivingEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */