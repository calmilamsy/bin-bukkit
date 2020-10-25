/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class SpawnChangeEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   private Location previousLocation;
/*    */   
/*    */   public SpawnChangeEvent(World world, Location previousLocation) {
/* 14 */     super(Event.Type.SPAWN_CHANGE, world);
/* 15 */     this.previousLocation = previousLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Location getPreviousLocation() { return this.previousLocation; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\SpawnChangeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */