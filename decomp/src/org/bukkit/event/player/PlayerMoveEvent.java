/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerMoveEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   private Location from;
/*    */   private Location to;
/*    */   
/*    */   public PlayerMoveEvent(Player player, Location from, Location to) {
/* 17 */     super(Event.Type.PLAYER_MOVE, player);
/* 18 */     this.from = from;
/* 19 */     this.to = to;
/*    */   }
/*    */   
/*    */   PlayerMoveEvent(Event.Type type, Player player, Location from, Location to) {
/* 23 */     super(type, player);
/* 24 */     this.from = from;
/* 25 */     this.to = to;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public Location getFrom() { return this.from; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public void setFrom(Location from) { this.from = from; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public Location getTo() { return this.to; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 89 */   public void setTo(Location to) { this.to = to; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerMoveEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */