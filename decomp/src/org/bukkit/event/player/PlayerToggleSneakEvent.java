/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerToggleSneakEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   private boolean isSneaking;
/*    */   private boolean cancel = false;
/*    */   
/*    */   public PlayerToggleSneakEvent(Player player, boolean isSneaking) {
/* 14 */     super(Event.Type.PLAYER_TOGGLE_SNEAK, player);
/* 15 */     this.isSneaking = isSneaking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public boolean isSneaking() { return this.isSneaking; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerToggleSneakEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */