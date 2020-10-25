/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PaintingPlaceEvent
/*    */   extends PaintingEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private Player player;
/*    */   private Block block;
/*    */   private BlockFace blockFace;
/*    */   
/*    */   public PaintingPlaceEvent(Painting painting, Player player, Block block, BlockFace blockFace) {
/* 23 */     super(Event.Type.PAINTING_PLACE, painting);
/* 24 */     this.player = player;
/* 25 */     this.block = block;
/* 26 */     this.blockFace = blockFace;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public Player getPlayer() { return this.player; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public Block getBlock() { return this.block; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public BlockFace getBlockFace() { return this.blockFace; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\painting\PaintingPlaceEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */