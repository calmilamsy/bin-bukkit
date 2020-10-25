/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreeperPowerEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean canceled;
/*    */   private Entity creeper;
/*    */   private PowerCause cause;
/*    */   private Entity bolt;
/*    */   
/*    */   public CreeperPowerEvent(Entity creeper, Entity bolt, PowerCause cause) {
/* 19 */     super(Event.Type.CREEPER_POWER, creeper);
/* 20 */     this.creeper = creeper;
/* 21 */     this.bolt = bolt;
/* 22 */     this.cause = cause;
/*    */   }
/*    */   
/*    */   public CreeperPowerEvent(Entity creeper, PowerCause cause) {
/* 26 */     super(Event.Type.CREEPER_POWER, creeper);
/* 27 */     this.creeper = creeper;
/* 28 */     this.cause = cause;
/* 29 */     this.bolt = null;
/*    */   }
/*    */ 
/*    */   
/* 33 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public Entity getLightning() { return this.bolt; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public PowerCause getCause() { return this.cause; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum PowerCause
/*    */   {
/* 67 */     LIGHTNING,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 72 */     SET_ON,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     SET_OFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\CreeperPowerEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */