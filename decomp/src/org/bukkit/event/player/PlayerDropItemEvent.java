/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerDropItemEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable {
/*    */   private final Item drop;
/*    */   private boolean cancel = false;
/*    */   
/*    */   public PlayerDropItemEvent(Player player, Item drop) {
/* 15 */     super(Event.Type.PLAYER_DROP_ITEM, player);
/* 16 */     this.drop = drop;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public Item getItemDrop() { return this.drop; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerDropItemEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */