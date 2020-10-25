/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerFishEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   private final Entity entity;
/*    */   private boolean cancel = false;
/*    */   private State state;
/*    */   
/*    */   public PlayerFishEvent(Player player, Entity entity, State state) {
/* 16 */     super(Event.Type.PLAYER_FISH, player);
/* 17 */     this.entity = entity;
/* 18 */     this.state = state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Entity getCaught() { return this.entity; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public State getState() { return this.state; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum State
/*    */   {
/* 55 */     FISHING,
/*    */ 
/*    */ 
/*    */     
/* 59 */     CAUGHT_FISH,
/*    */ 
/*    */ 
/*    */     
/* 63 */     CAUGHT_ENTITY,
/*    */ 
/*    */ 
/*    */     
/* 67 */     IN_GROUND,
/*    */ 
/*    */ 
/*    */     
/* 71 */     FAILED_ATTEMPT;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerFishEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */