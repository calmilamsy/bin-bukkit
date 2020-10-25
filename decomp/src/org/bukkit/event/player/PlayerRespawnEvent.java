/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerRespawnEvent extends PlayerEvent {
/*    */   private Location respawnLocation;
/*    */   
/*    */   public PlayerRespawnEvent(Player respawnPlayer, Location respawnLocation, boolean isBedSpawn) {
/* 11 */     super(Event.Type.PLAYER_RESPAWN, respawnPlayer);
/* 12 */     this.respawnLocation = respawnLocation;
/* 13 */     this.isBedSpawn = isBedSpawn;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isBedSpawn;
/*    */ 
/*    */ 
/*    */   
/* 22 */   public Location getRespawnLocation() { return this.respawnLocation; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public void setRespawnLocation(Location respawnLocation) { this.respawnLocation = respawnLocation; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean isBedSpawn() { return this.isBedSpawn; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerRespawnEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */