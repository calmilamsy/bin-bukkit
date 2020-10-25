/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ChunkPosition {
/*    */   public final int x;
/*    */   
/*    */   public ChunkPosition(int paramInt1, int paramInt2, int paramInt3) {
/*  7 */     this.x = paramInt1;
/*  8 */     this.y = paramInt2;
/*  9 */     this.z = paramInt3;
/*    */   }
/*    */   public final int y; public final int z;
/*    */   public boolean equals(Object paramObject) {
/* 13 */     if (paramObject instanceof ChunkPosition) {
/*    */       
/* 15 */       ChunkPosition chunkPosition = (ChunkPosition)paramObject;
/* 16 */       return (chunkPosition.x == this.x && chunkPosition.y == this.y && chunkPosition.z == this.z);
/*    */     } 
/*    */     
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 23 */   public int hashCode() { return this.x * 8976890 + this.y * 981131 + this.z; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkPosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */