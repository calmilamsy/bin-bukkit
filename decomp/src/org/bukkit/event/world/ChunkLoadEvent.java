/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ChunkLoadEvent
/*    */   extends ChunkEvent
/*    */ {
/*    */   private final boolean newChunk;
/*    */   
/*    */   public ChunkLoadEvent(Chunk chunk, boolean newChunk) {
/* 12 */     super(Event.Type.CHUNK_LOAD, chunk);
/* 13 */     this.newChunk = newChunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public boolean isNewChunk() { return this.newChunk; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\world\ChunkLoadEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */