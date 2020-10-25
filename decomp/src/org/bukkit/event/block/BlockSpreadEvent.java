/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
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
/*    */ public class BlockSpreadEvent
/*    */   extends BlockFormEvent
/*    */ {
/*    */   private Block source;
/*    */   
/*    */   public BlockSpreadEvent(Block block, Block source, BlockState newState) {
/* 22 */     super(Event.Type.BLOCK_SPREAD, block, newState);
/* 23 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Block getSource() { return this.source; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockSpreadEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */