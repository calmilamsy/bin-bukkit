/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class BlockEvent
/*    */   extends Event
/*    */ {
/*    */   protected Block block;
/*    */   
/*    */   public BlockEvent(Event.Type type, Block theBlock) {
/* 13 */     super(type);
/* 14 */     this.block = theBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final Block getBlock() { return this.block; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */