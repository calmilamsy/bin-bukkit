/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EntityInteractEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   protected Block block;
/*    */   private boolean cancelled;
/*    */   
/*    */   public EntityInteractEvent(Entity entity, Block block) {
/* 16 */     super(Event.Type.ENTITY_INTERACT, entity);
/* 17 */     this.block = block;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Block getBlock() { return this.block; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityInteractEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */