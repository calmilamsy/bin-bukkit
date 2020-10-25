/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDamageEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*     */   private int damage;
/*     */   private boolean cancelled;
/*     */   private DamageCause cause;
/*     */   
/*  17 */   public EntityDamageEvent(Entity damagee, DamageCause cause, int damage) { this(Event.Type.ENTITY_DAMAGE, damagee, cause, damage); }
/*     */ 
/*     */   
/*     */   protected EntityDamageEvent(Event.Type type, Entity damagee, DamageCause cause, int damage) {
/*  21 */     super(type, damagee);
/*  22 */     this.cause = cause;
/*  23 */     this.damage = damage;
/*     */     
/*  25 */     damagee.setLastDamageCause(this);
/*     */   }
/*     */ 
/*     */   
/*  29 */   public boolean isCancelled() { return this.cancelled; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public int getDamage() { return this.damage; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public void setDamage(int damage) { this.damage = damage; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public DamageCause getCause() { return this.cause; }
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
/*     */   public enum DamageCause
/*     */   {
/*  73 */     CONTACT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     ENTITY_ATTACK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     PROJECTILE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     SUFFOCATION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     FALL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     FIRE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     FIRE_TICK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     LAVA,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     DROWNING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     BLOCK_EXPLOSION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     ENTITY_EXPLOSION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     VOID,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     LIGHTNING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     SUICIDE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityDamageEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */