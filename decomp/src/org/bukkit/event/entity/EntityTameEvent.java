/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.AnimalTamer;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EntityTameEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/*    */   private boolean cancelled;
/*    */   private AnimalTamer owner;
/*    */   
/*    */   public EntityTameEvent(Entity entity, AnimalTamer owner) {
/* 15 */     super(Event.Type.ENTITY_TAME, entity);
/* 16 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public AnimalTamer getOwner() { return this.owner; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityTameEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */