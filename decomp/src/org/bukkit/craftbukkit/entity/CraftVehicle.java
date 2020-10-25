/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ 
/*    */ public abstract class CraftVehicle extends CraftEntity implements Vehicle {
/*  8 */   public CraftVehicle(CraftServer server, Entity entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 13 */   public String toString() { return "CraftVehicle{passenger=" + getPassenger() + '}'; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftVehicle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */