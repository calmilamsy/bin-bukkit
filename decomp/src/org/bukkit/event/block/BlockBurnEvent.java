/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class BlockBurnEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public BlockBurnEvent(Block block) {
/* 15 */     super(Event.Type.BLOCK_BURN, block);
/* 16 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockBurnEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */