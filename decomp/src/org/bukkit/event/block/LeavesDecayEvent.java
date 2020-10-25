/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeavesDecayEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   
/* 15 */   public LeavesDecayEvent(Block block) { super(Event.Type.LEAVES_DECAY, block); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\LeavesDecayEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */