/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class WorldEvent
/*    */   extends Event
/*    */ {
/*    */   private final World world;
/*    */   
/*    */   public WorldEvent(Event.Type type, World world) {
/* 13 */     super(type);
/*    */     
/* 15 */     this.world = world;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public World getWorld() { return this.world; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\WorldEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */