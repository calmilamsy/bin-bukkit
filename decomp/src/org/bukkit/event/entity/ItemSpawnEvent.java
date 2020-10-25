/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ItemSpawnEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Location location;
/*    */   private boolean canceled;
/*    */   
/*    */   public ItemSpawnEvent(Entity spawnee, Location loc) {
/* 16 */     super(Event.Type.ITEM_SPAWN, spawnee);
/* 17 */     this.location = loc;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Location getLocation() { return this.location; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\ItemSpawnEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */