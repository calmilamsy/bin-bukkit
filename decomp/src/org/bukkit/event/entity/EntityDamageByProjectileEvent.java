/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class EntityDamageByProjectileEvent
/*    */   extends EntityDamageByEntityEvent
/*    */ {
/*    */   private Projectile projectile;
/*    */   
/* 17 */   public EntityDamageByProjectileEvent(Entity damagee, Projectile projectile, EntityDamageEvent.DamageCause cause, int damage) { this(projectile.getShooter(), damagee, projectile, cause, damage); }
/*    */ 
/*    */   
/*    */   public EntityDamageByProjectileEvent(Entity damager, Entity damagee, Projectile projectile, EntityDamageEvent.DamageCause cause, int damage) {
/* 21 */     super(damager, projectile, EntityDamageEvent.DamageCause.PROJECTILE, damage);
/* 22 */     this.projectile = projectile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public Projectile getProjectile() { return this.projectile; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setBounce(boolean bounce) { this.projectile.setBounce(bounce); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public boolean getBounce() { return this.projectile.doesBounce(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityDamageByProjectileEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */