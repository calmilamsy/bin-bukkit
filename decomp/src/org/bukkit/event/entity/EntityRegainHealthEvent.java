/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class EntityRegainHealthEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private int amount;
/*    */   private RegainReason regainReason;
/*    */   
/*    */   public EntityRegainHealthEvent(Entity entity, int amount, RegainReason regainReason) {
/* 17 */     super(Event.Type.ENTITY_REGAIN_HEALTH, entity);
/* 18 */     this.amount = amount;
/* 19 */     this.regainReason = regainReason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public int getAmount() { return this.amount; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void setAmount(int amount) { this.amount = amount; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public RegainReason getRegainReason() { return this.regainReason; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum RegainReason
/*    */   {
/* 65 */     REGEN,
/*    */ 
/*    */ 
/*    */     
/* 69 */     EATING,
/*    */ 
/*    */ 
/*    */     
/* 73 */     CUSTOM;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityRegainHealthEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */