/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerPickupItemEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   private final Item item;
/*    */   private boolean cancel = false;
/*    */   private int remaining;
/*    */   
/*    */   public PlayerPickupItemEvent(Player player, Item item, int remaining) {
/* 16 */     super(Event.Type.PLAYER_PICKUP_ITEM, player);
/* 17 */     this.item = item;
/* 18 */     this.remaining = remaining;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Item getItem() { return this.item; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public int getRemaining() { return this.remaining; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerPickupItemEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */