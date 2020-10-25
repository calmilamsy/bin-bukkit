/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class VehicleEntityCollisionEvent
/*    */   extends VehicleCollisionEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Entity entity;
/*    */   private boolean cancelled = false;
/*    */   private boolean cancelledPickup = false;
/*    */   private boolean cancelledCollision = false;
/*    */   
/*    */   public VehicleEntityCollisionEvent(Vehicle vehicle, Entity entity) {
/* 19 */     super(Event.Type.VEHICLE_COLLISION_ENTITY, vehicle);
/* 20 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public Entity getEntity() { return this.entity; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isPickupCancelled() { return this.cancelledPickup; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void setPickupCancelled(boolean cancel) { this.cancelledPickup = cancel; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean isCollisionCancelled() { return this.cancelledCollision; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void setCollisionCancelled(boolean cancel) { this.cancelledCollision = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleEntityCollisionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */