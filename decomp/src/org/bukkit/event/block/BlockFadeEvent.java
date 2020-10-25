/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFadeEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private BlockState newState;
/*    */   
/*    */   public BlockFadeEvent(Block block, BlockState newState) {
/* 22 */     super(Event.Type.BLOCK_FADE, block);
/* 23 */     this.newState = newState;
/* 24 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public BlockState getNewState() { return this.newState; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockFadeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */