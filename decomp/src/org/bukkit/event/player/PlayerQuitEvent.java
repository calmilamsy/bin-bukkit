/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class PlayerQuitEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   private String quitMessage;
/*    */   
/*    */   public PlayerQuitEvent(Player who, String quitMessage) {
/* 13 */     super(Event.Type.PLAYER_QUIT, who);
/* 14 */     this.quitMessage = quitMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public String getQuitMessage() { return this.quitMessage; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setQuitMessage(String quitMessage) { this.quitMessage = quitMessage; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerQuitEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */