/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkPopulateEvent
/*    */   extends ChunkEvent
/*    */ {
/* 13 */   public ChunkPopulateEvent(Chunk chunk) { super(Event.Type.CHUNK_POPULATED, chunk); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\ChunkPopulateEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */