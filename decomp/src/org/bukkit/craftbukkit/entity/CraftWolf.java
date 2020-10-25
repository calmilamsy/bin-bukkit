/*     */ package org.bukkit.craftbukkit.entity;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityAnimal;
/*     */ import net.minecraft.server.EntityCreature;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityWolf;
/*     */ import net.minecraft.server.PathEntity;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.entity.AnimalTamer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Wolf;
/*     */ 
/*     */ public class CraftWolf extends CraftAnimals implements Wolf {
/*  14 */   public CraftWolf(CraftServer server, EntityWolf wolf) { super(server, wolf); }
/*     */   
/*     */   private AnimalTamer owner;
/*     */   
/*  18 */   public boolean isAngry() { return getHandle().isAngry(); }
/*     */ 
/*     */ 
/*     */   
/*  22 */   public void setAngry(boolean angry) { getHandle().setAngry(angry); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public boolean isSitting() { return getHandle().isSitting(); }
/*     */ 
/*     */   
/*     */   public void setSitting(boolean sitting) {
/*  30 */     getHandle().setSitting(sitting);
/*     */ 
/*     */     
/*  33 */     setPath((PathEntity)null);
/*     */   }
/*     */ 
/*     */   
/*  37 */   public boolean isTamed() { return getHandle().isTamed(); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public void setTamed(boolean tame) { getHandle().setTamed(tame); }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimalTamer getOwner() {
/*  46 */     if (this.owner == null)
/*     */     {
/*  48 */       this.owner = getServer().getPlayer(getOwnerName());
/*     */     }
/*  50 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setOwner(AnimalTamer tamer) {
/*  54 */     this.owner = tamer;
/*  55 */     if (this.owner != null) {
/*  56 */       setTamed(true);
/*  57 */       setPath((PathEntity)null);
/*     */ 
/*     */       
/*  60 */       if (this.owner instanceof Player) {
/*  61 */         setOwnerName(((Player)this.owner).getName());
/*     */       } else {
/*  63 */         setOwnerName("");
/*     */       } 
/*     */     } else {
/*  66 */       setTamed(false);
/*  67 */       setOwnerName("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   String getOwnerName() { return getHandle().getOwnerName(); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   void setOwnerName(String ownerName) { getHandle().setOwnerName(ownerName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private void setPath(PathEntity pathentity) { getHandle().setPathEntity(pathentity); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public EntityWolf getHandle() { return (EntityWolf)this.entity; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public String toString() { return "CraftWolf[anger=" + isAngry() + ",owner=" + getOwner() + ",tame=" + isTamed() + ",sitting=" + isSitting() + "]"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftWolf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */