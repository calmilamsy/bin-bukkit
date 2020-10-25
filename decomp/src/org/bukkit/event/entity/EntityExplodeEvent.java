/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EntityExplodeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable {
/*    */   private boolean cancel;
/*    */   private Location location;
/*    */   private List<Block> blocks;
/* 16 */   private float yield = 0.3F;
/*    */   
/*    */   public EntityExplodeEvent(Entity what, Location location, List<Block> blocks) {
/* 19 */     super(Event.Type.ENTITY_EXPLODE, what);
/* 20 */     this.location = location;
/* 21 */     this.cancel = false;
/* 22 */     this.blocks = blocks;
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
/* 38 */   public List<Block> blockList() { return this.blocks; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Location getLocation() { return this.location; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public float getYield() { return this.yield; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public void setYield(float yield) { this.yield = yield; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityExplodeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */