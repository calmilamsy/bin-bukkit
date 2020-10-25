/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Explosive;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExplosionPrimeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/*    */   private boolean cancel;
/*    */   private float radius;
/*    */   private boolean fire;
/*    */   
/*    */   public ExplosionPrimeEvent(Entity what, float radius, boolean fire) {
/* 16 */     super(Event.Type.EXPLOSION_PRIME, what);
/* 17 */     this.cancel = false;
/* 18 */     this.radius = radius;
/* 19 */     this.fire = fire;
/*    */   }
/*    */ 
/*    */   
/* 23 */   public ExplosionPrimeEvent(Explosive explosive) { this(explosive, explosive.getYield(), explosive.isIncendiary()); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public float getRadius() { return this.radius; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public void setRadius(float radius) { this.radius = radius; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public boolean getFire() { return this.fire; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public void setFire(boolean fire) { this.fire = fire; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\ExplosionPrimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */