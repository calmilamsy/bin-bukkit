/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class WorldUnloadEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean isCancelled;
/*    */   
/* 13 */   public WorldUnloadEvent(World world) { super(Event.Type.WORLD_UNLOAD, world); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public boolean isCancelled() { return this.isCancelled; }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void setCancelled(boolean cancel) { this.isCancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\WorldUnloadEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */