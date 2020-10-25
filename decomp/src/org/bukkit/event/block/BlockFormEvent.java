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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFormEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private BlockState newState;
/*    */   
/*    */   public BlockFormEvent(Block block, BlockState newState) {
/* 25 */     super(Event.Type.BLOCK_FORM, block);
/* 26 */     this.block = block;
/* 27 */     this.newState = newState;
/* 28 */     this.cancelled = false;
/*    */   }
/*    */   
/*    */   public BlockFormEvent(Event.Type type, Block block, BlockState newState) {
/* 32 */     super(type, block);
/* 33 */     this.block = block;
/* 34 */     this.newState = newState;
/* 35 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public BlockState getNewState() { return this.newState; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockFormEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */