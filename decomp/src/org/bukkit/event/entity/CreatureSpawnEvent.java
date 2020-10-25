/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatureSpawnEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private Location location;
/*    */   private boolean canceled;
/*    */   private CreatureType creatureType;
/*    */   private SpawnReason spawnReason;
/*    */   
/*    */   public CreatureSpawnEvent(Entity spawnee, CreatureType mobtype, Location loc, SpawnReason spawnReason) {
/* 21 */     super(Event.Type.CREATURE_SPAWN, spawnee);
/* 22 */     this.creatureType = mobtype;
/* 23 */     this.location = loc;
/* 24 */     this.spawnReason = spawnReason;
/*    */   }
/*    */ 
/*    */   
/* 28 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public Location getLocation() { return this.location; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public CreatureType getCreatureType() { return this.creatureType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public SpawnReason getSpawnReason() { return this.spawnReason; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum SpawnReason
/*    */   {
/* 70 */     NATURAL,
/*    */ 
/*    */ 
/*    */     
/* 74 */     SPAWNER,
/*    */ 
/*    */ 
/*    */     
/* 78 */     EGG,
/*    */ 
/*    */ 
/*    */     
/* 82 */     LIGHTNING,
/*    */ 
/*    */ 
/*    */     
/* 86 */     BED,
/*    */ 
/*    */ 
/*    */     
/* 90 */     CUSTOM;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\CreatureSpawnEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */