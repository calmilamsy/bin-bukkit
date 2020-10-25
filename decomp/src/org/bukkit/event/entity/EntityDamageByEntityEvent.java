/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EntityDamageByEntityEvent
/*    */   extends EntityDamageEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Entity damager;
/*    */   
/*    */   public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
/* 14 */     super(Event.Type.ENTITY_DAMAGE, damagee, cause, damage);
/* 15 */     this.damager = damager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Entity getDamager() { return this.damager; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityDamageByEntityEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */