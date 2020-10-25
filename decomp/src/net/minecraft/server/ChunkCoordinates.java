/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ public class ChunkCoordinates
/*     */   implements Comparable
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public ChunkCoordinates() {}
/*     */   
/*     */   public ChunkCoordinates(int paramInt1, int paramInt2, int paramInt3) {
/*  14 */     this.x = paramInt1;
/*  15 */     this.y = paramInt2;
/*  16 */     this.z = paramInt3;
/*     */   }
/*     */   
/*     */   public ChunkCoordinates(ChunkCoordinates paramChunkCoordinates) {
/*  20 */     this.x = paramChunkCoordinates.x;
/*  21 */     this.y = paramChunkCoordinates.y;
/*  22 */     this.z = paramChunkCoordinates.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  27 */     if (!(paramObject instanceof ChunkCoordinates)) {
/*  28 */       return false;
/*     */     }
/*     */     
/*  31 */     ChunkCoordinates chunkCoordinates = (ChunkCoordinates)paramObject;
/*  32 */     return (this.x == chunkCoordinates.x && this.y == chunkCoordinates.y && this.z == chunkCoordinates.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public int hashCode() { return this.x + this.z << 8 + this.y << 16; }
/*     */ 
/*     */   
/*     */   public int compareTo(ChunkCoordinates paramChunkCoordinates) {
/*  41 */     if (this.y == paramChunkCoordinates.y) {
/*  42 */       if (this.z == paramChunkCoordinates.z) {
/*  43 */         return this.x - paramChunkCoordinates.x;
/*     */       }
/*  45 */       return this.z - paramChunkCoordinates.z;
/*     */     } 
/*  47 */     return this.y - paramChunkCoordinates.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double a(int paramInt1, int paramInt2, int paramInt3) {
/* 187 */     int i = this.x - paramInt1;
/* 188 */     int j = this.y - paramInt2;
/* 189 */     int k = this.z - paramInt3;
/*     */     
/* 191 */     return Math.sqrt((i * i + j * j + k * k));
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkCoordinates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */