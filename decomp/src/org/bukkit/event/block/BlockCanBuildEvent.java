/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCanBuildEvent
/*    */   extends BlockEvent
/*    */ {
/*    */   protected boolean buildable;
/*    */   protected int material;
/*    */   
/*    */   public BlockCanBuildEvent(Block block, int id, boolean canBuild) {
/* 20 */     super(Event.Type.BLOCK_CANBUILD, block);
/* 21 */     this.buildable = canBuild;
/* 22 */     this.material = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public boolean isBuildable() { return this.buildable; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void setBuildable(boolean cancel) { this.buildable = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Material getMaterial() { return Material.getMaterial(this.material); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int getMaterialId() { return this.material; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockCanBuildEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */