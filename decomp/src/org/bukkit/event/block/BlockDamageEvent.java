/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class BlockDamageEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Player player;
/*    */   private boolean instaBreak;
/*    */   private boolean cancel;
/*    */   private ItemStack itemstack;
/*    */   
/*    */   public BlockDamageEvent(Player player, Block block, ItemStack itemInHand, boolean instaBreak) {
/* 20 */     super(Event.Type.BLOCK_DAMAGE, block);
/* 21 */     this.instaBreak = instaBreak;
/* 22 */     this.player = player;
/* 23 */     this.itemstack = itemInHand;
/* 24 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Player getPlayer() { return this.player; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean getInstaBreak() { return this.instaBreak; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public void setInstaBreak(boolean bool) { this.instaBreak = bool; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public ItemStack getItemInHand() { return this.itemstack; }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockDamageEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */