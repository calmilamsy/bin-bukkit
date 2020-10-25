/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerTeleportEvent
/*    */   extends PlayerMoveEvent
/*    */ {
/* 12 */   public PlayerTeleportEvent(Player player, Location from, Location to) { super(Event.Type.PLAYER_TELEPORT, player, from, to); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public PlayerTeleportEvent(Event.Type type, Player player, Location from, Location to) { super(type, player, from, to); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerTeleportEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */