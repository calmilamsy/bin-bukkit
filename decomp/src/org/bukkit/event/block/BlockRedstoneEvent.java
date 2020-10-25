/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class BlockRedstoneEvent
/*    */   extends BlockEvent
/*    */ {
/*    */   private int oldCurrent;
/*    */   private int newCurrent;
/*    */   
/*    */   public BlockRedstoneEvent(Block block, int oldCurrent, int newCurrent) {
/* 13 */     super(Event.Type.REDSTONE_CHANGE, block);
/* 14 */     this.oldCurrent = oldCurrent;
/* 15 */     this.newCurrent = newCurrent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public int getOldCurrent() { return this.oldCurrent; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public int getNewCurrent() { return this.newCurrent; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public void setNewCurrent(int newCurrent) { this.newCurrent = newCurrent; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockRedstoneEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */