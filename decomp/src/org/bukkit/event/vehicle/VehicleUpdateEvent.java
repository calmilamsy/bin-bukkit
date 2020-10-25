/*   */ package org.bukkit.event.vehicle;
/*   */ 
/*   */ import org.bukkit.entity.Vehicle;
/*   */ import org.bukkit.event.Event;
/*   */ 
/*   */ public class VehicleUpdateEvent extends VehicleEvent {
/* 7 */   public VehicleUpdateEvent(Vehicle vehicle) { super(Event.Type.VEHICLE_UPDATE, vehicle); }
/*   */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleUpdateEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */