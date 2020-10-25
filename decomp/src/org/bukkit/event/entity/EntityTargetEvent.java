/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ 
/*     */ public class EntityTargetEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable {
/*     */   private boolean cancel;
/*     */   private Entity target;
/*     */   private TargetReason reason;
/*     */   
/*     */   public EntityTargetEvent(Entity entity, Entity target, TargetReason reason) {
/*  15 */     super(Event.Type.ENTITY_TARGET, entity);
/*  16 */     this.target = target;
/*  17 */     this.cancel = false;
/*  18 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   
/*  22 */   public boolean isCancelled() { return this.cancel; }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public TargetReason getReason() { return this.reason; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public Entity getTarget() { return this.target; }
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
/*  57 */   public void setTarget(Entity target) { this.target = target; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TargetReason
/*     */   {
/*  68 */     TARGET_DIED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     CLOSEST_PLAYER,
/*     */ 
/*     */ 
/*     */     
/*  77 */     TARGET_ATTACKED_ENTITY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     PIG_ZOMBIE_TARGET,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     FORGOT_TARGET,
/*     */ 
/*     */ 
/*     */     
/*  91 */     TARGET_ATTACKED_OWNER,
/*     */ 
/*     */ 
/*     */     
/*  95 */     OWNER_ATTACKED_TARGET,
/*     */ 
/*     */ 
/*     */     
/*  99 */     RANDOM_TARGET,
/*     */ 
/*     */ 
/*     */     
/* 103 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityTargetEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */