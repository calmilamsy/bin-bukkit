/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerKickEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   private String leaveMessage;
/*    */   private String kickReason;
/*    */   private Boolean cancel;
/*    */   
/*    */   public PlayerKickEvent(Player playerKicked, String kickReason, String leaveMessage) {
/* 15 */     super(Event.Type.PLAYER_KICK, playerKicked);
/* 16 */     this.kickReason = kickReason;
/* 17 */     this.leaveMessage = leaveMessage;
/* 18 */     this.cancel = Boolean.valueOf(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public String getReason() { return this.kickReason; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public String getLeaveMessage() { return this.leaveMessage; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean isCancelled() { return this.cancel.booleanValue(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void setCancelled(boolean cancel) { this.cancel = Boolean.valueOf(cancel); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public void setReason(String kickReason) { this.kickReason = kickReason; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public void setLeaveMessage(String leaveMessage) { this.leaveMessage = leaveMessage; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerKickEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */