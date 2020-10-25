/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class PlayerInventoryEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   protected Inventory inventory;
/*    */   
/*    */   public PlayerInventoryEvent(Player player, Inventory inventory) {
/* 13 */     super(Event.Type.PLAYER_INVENTORY, player);
/* 14 */     this.inventory = inventory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Inventory getInventory() { return this.inventory; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerInventoryEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */