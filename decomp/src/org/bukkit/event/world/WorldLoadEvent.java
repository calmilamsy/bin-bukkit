/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class WorldLoadEvent
/*    */   extends WorldEvent
/*    */ {
/* 10 */   public WorldLoadEvent(World world) { super(Event.Type.WORLD_LOAD, world); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\WorldLoadEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */