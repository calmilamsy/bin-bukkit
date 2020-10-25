/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PortalCreateEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable {
/*    */   private boolean cancel = false;
/* 14 */   private ArrayList<Block> blocks = new ArrayList();
/*    */   
/*    */   public PortalCreateEvent(Collection<Block> blocks, World world) {
/* 17 */     super(Event.Type.PORTAL_CREATE, world);
/* 18 */     this.blocks.addAll(blocks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public ArrayList<Block> getBlocks() { return this.blocks; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\PortalCreateEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */