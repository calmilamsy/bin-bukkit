/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class VehicleDestroyEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable {
/*    */   private Entity attacker;
/*    */   private boolean cancelled;
/*    */   
/*    */   public VehicleDestroyEvent(Vehicle vehicle, Entity attacker) {
/* 15 */     super(Event.Type.VEHICLE_DESTROY, vehicle);
/* 16 */     this.attacker = attacker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public Entity getAttacker() { return this.attacker; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleDestroyEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */