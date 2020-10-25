/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VehicleMoveEvent
/*    */   extends VehicleEvent
/*    */ {
/*    */   private Location from;
/*    */   private Location to;
/*    */   
/*    */   public VehicleMoveEvent(Vehicle vehicle, Location from, Location to) {
/* 16 */     super(Event.Type.VEHICLE_MOVE, vehicle);
/*    */     
/* 18 */     this.from = from;
/* 19 */     this.to = to;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Location getFrom() { return this.from; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Location getTo() { return this.to; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleMoveEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */