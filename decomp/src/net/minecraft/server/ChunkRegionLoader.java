/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkRegionLoader
/*    */   implements IChunkLoader
/*    */ {
/*    */   private final File a;
/*    */   
/* 20 */   public ChunkRegionLoader(File paramFile) { this.a = paramFile; }
/*    */ 
/*    */   
/*    */   public Chunk a(World paramWorld, int paramInt1, int paramInt2) {
/*    */     NBTTagCompound nBTTagCompound;
/* 25 */     DataInputStream dataInputStream = RegionFileCache.c(this.a, paramInt1, paramInt2);
/*    */ 
/*    */     
/* 28 */     if (dataInputStream != null) {
/* 29 */       nBTTagCompound = CompressedStreamTools.a(dataInputStream);
/*    */     } else {
/* 31 */       return null;
/*    */     } 
/*    */     
/* 34 */     if (!nBTTagCompound.hasKey("Level")) {
/* 35 */       System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is missing level data, skipping");
/* 36 */       return null;
/*    */     } 
/* 38 */     if (!nBTTagCompound.k("Level").hasKey("Blocks")) {
/* 39 */       System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is missing block data, skipping");
/* 40 */       return null;
/*    */     } 
/* 42 */     Chunk chunk = ChunkLoader.a(paramWorld, nBTTagCompound.k("Level"));
/* 43 */     if (!chunk.a(paramInt1, paramInt2)) {
/* 44 */       System.out.println("Chunk file at " + paramInt1 + "," + paramInt2 + " is in the wrong location; relocating. (Expected " + paramInt1 + ", " + paramInt2 + ", got " + chunk.x + ", " + chunk.z + ")");
/* 45 */       nBTTagCompound.a("xPos", paramInt1);
/* 46 */       nBTTagCompound.a("zPos", paramInt2);
/* 47 */       chunk = ChunkLoader.a(paramWorld, nBTTagCompound.k("Level"));
/*    */     } 
/* 49 */     chunk.h();
/* 50 */     return chunk;
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Chunk paramChunk) {
/* 54 */     paramWorld.k();
/*    */ 
/*    */     
/*    */     try {
/* 58 */       DataOutputStream dataOutputStream = RegionFileCache.d(this.a, paramChunk.x, paramChunk.z);
/* 59 */       NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/* 60 */       NBTTagCompound nBTTagCompound2 = new NBTTagCompound();
/* 61 */       nBTTagCompound1.a("Level", nBTTagCompound2);
/* 62 */       ChunkLoader.a(paramChunk, paramWorld, nBTTagCompound2);
/* 63 */       CompressedStreamTools.a(nBTTagCompound1, dataOutputStream);
/* 64 */       dataOutputStream.close();
/*    */       
/* 66 */       WorldData worldData = paramWorld.q();
/* 67 */       worldData.b(worldData.g() + RegionFileCache.b(this.a, paramChunk.x, paramChunk.z));
/* 68 */     } catch (Exception exception) {
/* 69 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, Chunk paramChunk) {}
/*    */   
/*    */   public void a() {}
/*    */   
/*    */   public void b() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkRegionLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */