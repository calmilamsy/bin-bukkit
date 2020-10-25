/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBreakEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Player player;
/*    */   private boolean cancel;
/*    */   
/*    */   public BlockBreakEvent(Block theBlock, Player player) {
/* 22 */     super(Event.Type.BLOCK_BREAK, theBlock);
/* 23 */     this.player = player;
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
/* 37 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockBreakEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */