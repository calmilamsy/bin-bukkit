/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCommandPreprocessEvent
/*    */   extends PlayerChatEvent
/*    */ {
/* 11 */   public PlayerCommandPreprocessEvent(Player player, String message) { super(Event.Type.PLAYER_COMMAND_PREPROCESS, player, message); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerCommandPreprocessEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */