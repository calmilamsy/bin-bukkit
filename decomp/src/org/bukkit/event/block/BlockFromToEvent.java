/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class BlockFromToEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   protected Block to;
/*    */   protected BlockFace face;
/*    */   protected boolean cancel;
/*    */   
/*    */   public BlockFromToEvent(Block block, BlockFace face) {
/* 18 */     super(Event.Type.BLOCK_FROMTO, block);
/* 19 */     this.face = face;
/* 20 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public BlockFace getFace() { return this.face; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getToBlock() {
/* 38 */     if (this.to == null) {
/* 39 */       this.to = this.block.getRelative(this.face.getModX(), this.face.getModY(), this.face.getModZ());
/*    */     }
/* 41 */     return this.to;
/*    */   }
/*    */ 
/*    */   
/* 45 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockFromToEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */