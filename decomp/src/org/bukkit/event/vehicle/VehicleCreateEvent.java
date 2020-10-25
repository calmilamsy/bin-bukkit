/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VehicleCreateEvent
/*    */   extends VehicleEvent
/*    */ {
/* 12 */   public VehicleCreateEvent(Vehicle vehicle) { super(Event.Type.VEHICLE_CREATE, vehicle); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleCreateEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */