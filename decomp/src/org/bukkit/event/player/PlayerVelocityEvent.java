/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ public class PlayerVelocityEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   private Vector velocity;
/*    */   
/*    */   public PlayerVelocityEvent(Player player, Vector velocity) {
/* 17 */     super(Event.Type.PLAYER_VELOCITY, player);
/* 18 */     this.velocity = velocity;
/*    */   }
/*    */   
/*    */   PlayerVelocityEvent(Event.Type type, Player player, Vector velocity) {
/* 22 */     super(type, player);
/* 23 */     this.velocity = velocity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public Vector getVelocity() { return this.velocity; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public void setVelocity(Vector velocity) { this.velocity = velocity; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerVelocityEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */