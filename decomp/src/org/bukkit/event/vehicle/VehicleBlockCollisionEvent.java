/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class VehicleBlockCollisionEvent
/*    */   extends VehicleCollisionEvent
/*    */ {
/*    */   private Block block;
/*    */   
/*    */   public VehicleBlockCollisionEvent(Vehicle vehicle, Block block) {
/* 13 */     super(Event.Type.VEHICLE_COLLISION_BLOCK, vehicle);
/* 14 */     this.block = block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Block getBlock() { return this.block; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleBlockCollisionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */