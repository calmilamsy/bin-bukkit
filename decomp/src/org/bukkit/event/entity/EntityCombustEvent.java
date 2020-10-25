/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class EntityCombustEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel;
/*    */   
/*    */   public EntityCombustEvent(Entity what) {
/* 15 */     super(Event.Type.ENTITY_COMBUST, what);
/* 16 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityCombustEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */