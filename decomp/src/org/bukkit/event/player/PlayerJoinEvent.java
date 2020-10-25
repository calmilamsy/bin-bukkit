/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerJoinEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   private String joinMessage;
/*    */   
/*    */   public PlayerJoinEvent(Player playerJoined, String joinMessage) {
/* 12 */     super(Event.Type.PLAYER_JOIN, playerJoined);
/* 13 */     this.joinMessage = joinMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public String getJoinMessage() { return this.joinMessage; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public void setJoinMessage(String joinMessage) { this.joinMessage = joinMessage; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerJoinEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */