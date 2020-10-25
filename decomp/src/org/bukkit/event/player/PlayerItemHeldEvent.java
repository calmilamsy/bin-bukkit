/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerItemHeldEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   private int previous;
/*    */   private int current;
/*    */   
/*    */   public PlayerItemHeldEvent(Player player, int previous, int current) {
/* 13 */     super(Event.Type.PLAYER_ITEM_HELD, player);
/* 14 */     this.previous = previous;
/* 15 */     this.current = current;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public int getPreviousSlot() { return this.previous; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public int getNewSlot() { return this.current; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerItemHeldEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */