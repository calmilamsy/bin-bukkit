/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockIgniteEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private IgniteCause cause;
/*    */   private boolean cancel;
/*    */   private Player thePlayer;
/*    */   
/*    */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Player thePlayer) {
/* 19 */     super(Event.Type.BLOCK_IGNITE, theBlock);
/* 20 */     this.cause = cause;
/* 21 */     this.thePlayer = thePlayer;
/* 22 */     this.cancel = false;
/*    */   }
/*    */ 
/*    */   
/* 26 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public IgniteCause getCause() { return this.cause; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public Player getPlayer() { return this.thePlayer; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum IgniteCause
/*    */   {
/* 59 */     LAVA,
/*    */ 
/*    */ 
/*    */     
/* 63 */     FLINT_AND_STEEL,
/*    */ 
/*    */ 
/*    */     
/* 67 */     SPREAD,
/*    */ 
/*    */ 
/*    */     
/* 71 */     LIGHTNING;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockIgniteEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */