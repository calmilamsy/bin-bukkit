/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PigZapEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean canceled;
/*    */   private Entity pig;
/*    */   private Entity pigzombie;
/*    */   private Entity bolt;
/*    */   
/*    */   public PigZapEvent(Entity pig, Entity bolt, Entity pigzombie) {
/* 17 */     super(Event.Type.PIG_ZAP, pig);
/* 18 */     this.pig = pig;
/* 19 */     this.bolt = bolt;
/* 20 */     this.pigzombie = pigzombie;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Entity getLightning() { return this.bolt; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Entity getPigZombie() { return this.pigzombie; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\PigZapEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */