/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class VehicleDamageEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable {
/*    */   private Entity attacker;
/*    */   private int damage;
/*    */   private boolean cancelled;
/*    */   
/*    */   public VehicleDamageEvent(Vehicle vehicle, Entity attacker, int damage) {
/* 16 */     super(Event.Type.VEHICLE_DAMAGE, vehicle);
/* 17 */     this.attacker = attacker;
/* 18 */     this.damage = damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Entity getAttacker() { return this.attacker; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public int getDamage() { return this.damage; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void setDamage(int damage) { this.damage = damage; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\vehicle\VehicleDamageEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */