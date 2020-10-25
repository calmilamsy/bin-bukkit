/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class BlockPistonExtendEvent extends BlockPistonEvent {
/*    */   private int length;
/*    */   private List<Block> blocks;
/*    */   
/*    */   public BlockPistonExtendEvent(Block block, int length) {
/* 14 */     super(Event.Type.BLOCK_PISTON_EXTEND, block);
/*    */     
/* 16 */     this.length = length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public int getLength() { return this.length; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Block> getBlocks() {
/* 34 */     if (this.blocks == null) {
/* 35 */       ArrayList<Block> tmp = new ArrayList<Block>();
/* 36 */       for (int i = 0; i < getLength(); i++) {
/* 37 */         tmp.add(this.block.getRelative(getDirection(), i + 1));
/*    */       }
/* 39 */       this.blocks = Collections.unmodifiableList(tmp);
/*    */     } 
/* 41 */     return this.blocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockPistonExtendEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */