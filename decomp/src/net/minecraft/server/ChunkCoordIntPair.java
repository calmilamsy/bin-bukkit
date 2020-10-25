/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class ChunkCoordIntPair
/*    */ {
/*    */   public final int x;
/*    */   public final int z;
/*    */   
/*    */   public ChunkCoordIntPair(int paramInt1, int paramInt2) {
/*  9 */     this.x = paramInt1;
/* 10 */     this.z = paramInt2;
/*    */   }
/*    */ 
/*    */   
/* 14 */   public static int a(int paramInt1, int paramInt2) { return ((paramInt1 < 0) ? Integer.MIN_VALUE : 0) | (paramInt1 & 0x7FFF) << 16 | ((paramInt2 < 0) ? 32768 : 0) | paramInt2 & 0x7FFF; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int hashCode() { return a(this.x, this.z); }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 22 */     ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)paramObject;
/* 23 */     return (chunkCoordIntPair.x == this.x && chunkCoordIntPair.z == this.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkCoordIntPair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */