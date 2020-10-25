/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LIndexVersion
/*    */ {
/*    */   private final long generation;
/*    */   private final long version;
/*    */   
/*    */   public LIndexVersion(long generation, long version) {
/* 29 */     this.generation = generation;
/* 30 */     this.version = version;
/*    */   }
/*    */ 
/*    */   
/* 34 */   public long getGeneration() { return this.generation; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public long getVersion() { return this.version; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String toString() { return "gen[" + this.generation + "] ver[" + this.version + "]"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */