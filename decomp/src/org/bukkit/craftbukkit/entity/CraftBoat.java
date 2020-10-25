/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityBoat;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Boat;
/*    */ 
/*    */ public class CraftBoat extends CraftVehicle implements Boat {
/*    */   protected EntityBoat boat;
/*    */   
/*    */   public CraftBoat(CraftServer server, EntityBoat entity) {
/* 11 */     super(server, entity);
/* 12 */     this.boat = entity;
/*    */   }
/*    */ 
/*    */   
/* 16 */   public double getMaxSpeed() { return this.boat.maxSpeed; }
/*    */ 
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 20 */     if (speed >= 0.0D) {
/* 21 */       this.boat.maxSpeed = speed;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public String toString() { return "CraftBoat"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftBoat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */