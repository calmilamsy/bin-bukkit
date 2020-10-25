/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.material.PistonBaseMaterial;
/*    */ 
/*    */ public abstract class BlockPistonEvent extends BlockEvent implements Cancellable {
/*    */   private boolean cancelled;
/*    */   
/* 13 */   public BlockPistonEvent(Event.Type type, Block block) { super(type, block); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isSticky() { return (this.block.getType() == Material.PISTON_STICKY_BASE); }
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
/* 41 */   public BlockFace getDirection() { return ((PistonBaseMaterial)this.block.getState().getData()).getFacing(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockPistonEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */