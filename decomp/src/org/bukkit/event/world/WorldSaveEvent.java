/*   */ package org.bukkit.event.world;
/*   */ 
/*   */ import org.bukkit.World;
/*   */ import org.bukkit.event.Event;
/*   */ 
/*   */ public class WorldSaveEvent extends WorldEvent {
/* 7 */   public WorldSaveEvent(World world) { super(Event.Type.WORLD_SAVE, world); }
/*   */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\WorldSaveEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */