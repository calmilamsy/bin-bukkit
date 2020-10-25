/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerInteractEntityEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   protected Entity clickedEntity;
/*    */   boolean cancelled = false;
/*    */   
/*    */   public PlayerInteractEntityEvent(Player who, Entity clickedEntity) {
/* 15 */     super(Event.Type.PLAYER_INTERACT_ENTITY, who);
/* 16 */     this.clickedEntity = clickedEntity;
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
/* 33 */   public Entity getRightClicked() { return this.clickedEntity; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerInteractEntityEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */