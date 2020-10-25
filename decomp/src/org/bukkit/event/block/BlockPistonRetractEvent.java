/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class BlockPistonRetractEvent extends BlockPistonEvent {
/*  8 */   public BlockPistonRetractEvent(Block block) { super(Event.Type.BLOCK_PISTON_RETRACT, block); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   public Location getRetractLocation() { return getBlock().getRelative(getDirection(), 2).getLocation(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockPistonRetractEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */