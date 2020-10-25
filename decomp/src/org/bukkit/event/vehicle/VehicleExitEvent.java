/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class VehicleExitEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable {
/*    */   private boolean cancelled;
/*    */   private LivingEntity exited;
/*    */   
/*    */   public VehicleExitEvent(Vehicle vehicle, LivingEntity exited) {
/* 15 */     super(Event.Type.VEHICLE_EXIT, vehicle);
/* 16 */     this.exited = exited;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public LivingEntity getExited() { return this.exited; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleExitEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */