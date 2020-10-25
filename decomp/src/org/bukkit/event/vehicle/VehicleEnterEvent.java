/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class VehicleEnterEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable {
/*    */   private boolean cancelled;
/*    */   private Entity entered;
/*    */   
/*    */   public VehicleEnterEvent(Vehicle vehicle, Entity entered) {
/* 15 */     super(Event.Type.VEHICLE_ENTER, vehicle);
/* 16 */     this.entered = entered;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public Entity getEntered() { return this.entered; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleEnterEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */