/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VehicleEvent
/*    */   extends Event
/*    */ {
/*    */   protected Vehicle vehicle;
/*    */   
/*    */   public VehicleEvent(Event.Type type, Vehicle vehicle) {
/* 15 */     super(type);
/* 16 */     this.vehicle = vehicle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public final Vehicle getVehicle() { return this.vehicle; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */