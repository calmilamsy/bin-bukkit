/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ChunkEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   protected Chunk chunk;
/*    */   
/*    */   protected ChunkEvent(Event.Type type, Chunk chunk) {
/* 12 */     super(type, chunk.getWorld());
/* 13 */     this.chunk = chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public Chunk getChunk() { return this.chunk; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\ChunkEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */