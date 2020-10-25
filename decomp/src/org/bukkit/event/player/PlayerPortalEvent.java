/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TravelAgent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerPortalEvent
/*    */   extends PlayerTeleportEvent
/*    */ {
/*    */   protected boolean useTravelAgent = true;
/*    */   protected Player player;
/*    */   protected TravelAgent travelAgent;
/*    */   
/*    */   public PlayerPortalEvent(Player player, Location from, Location to, TravelAgent pta) {
/* 18 */     super(Event.Type.PLAYER_PORTAL, player, from, to);
/* 19 */     this.travelAgent = pta;
/*    */   }
/*    */ 
/*    */   
/* 23 */   public void useTravelAgent(boolean useTravelAgent) { this.useTravelAgent = useTravelAgent; }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public boolean useTravelAgent() { return this.useTravelAgent; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public TravelAgent getPortalTravelAgent() { return this.travelAgent; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setPortalTravelAgent(TravelAgent travelAgent) { this.travelAgent = travelAgent; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerPortalEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */