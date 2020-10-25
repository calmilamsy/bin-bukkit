/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class BlockPhysicsEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable {
/*    */   private final int changed;
/*    */   private boolean cancel = false;
/*    */   
/*    */   public BlockPhysicsEvent(Block block, int changed) {
/* 15 */     super(Event.Type.BLOCK_PHYSICS, block);
/* 16 */     this.changed = changed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public int getChangedTypeId() { return this.changed; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Material getChangedType() { return Material.getMaterial(this.changed); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockPhysicsEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */