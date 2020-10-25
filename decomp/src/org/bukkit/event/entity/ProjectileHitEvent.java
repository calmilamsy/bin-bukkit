/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProjectileHitEvent
/*    */   extends EntityEvent
/*    */ {
/* 11 */   public ProjectileHitEvent(Projectile projectile) { super(Event.Type.PROJECTILE_HIT, projectile); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\ProjectileHitEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */