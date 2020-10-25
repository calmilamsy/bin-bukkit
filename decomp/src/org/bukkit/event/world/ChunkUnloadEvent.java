/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ChunkUnloadEvent
/*    */   extends ChunkEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   
/* 13 */   public ChunkUnloadEvent(Chunk chunk) { super(Event.Type.CHUNK_UNLOAD, chunk); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\ChunkUnloadEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */