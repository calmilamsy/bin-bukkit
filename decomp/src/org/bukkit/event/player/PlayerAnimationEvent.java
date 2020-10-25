/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerAnimationEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private PlayerAnimationType animationType;
/*    */   private boolean isCancelled = false;
/*    */   
/*    */   public PlayerAnimationEvent(Player player) {
/* 20 */     super(Event.Type.PLAYER_ANIMATION, player);
/*    */ 
/*    */     
/* 23 */     this.animationType = PlayerAnimationType.ARM_SWING;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public PlayerAnimationType getAnimationType() { return this.animationType; }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isCancelled() { return this.isCancelled; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void setCancelled(boolean cancel) { this.isCancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerAnimationEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */