/*    */ package org.bukkit.craftbukkit.generator;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.Chunk;
/*    */ import net.minecraft.server.IChunkProvider;
/*    */ import net.minecraft.server.IProgressUpdate;
/*    */ import net.minecraft.server.World;
/*    */ import net.minecraft.server.WorldServer;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.generator.BlockPopulator;
/*    */ import org.bukkit.generator.ChunkGenerator;
/*    */ 
/*    */ public class CustomChunkGenerator
/*    */   extends InternalChunkGenerator {
/*    */   private final ChunkGenerator generator;
/*    */   
/*    */   public CustomChunkGenerator(World world, long seed, ChunkGenerator generator) {
/* 19 */     this.world = (WorldServer)world;
/* 20 */     this.generator = generator;
/*    */     
/* 22 */     this.random = new Random(seed);
/*    */   }
/*    */   private final WorldServer world; private final Random random;
/*    */   
/* 26 */   public boolean isChunkLoaded(int x, int z) { return true; }
/*    */ 
/*    */   
/*    */   public Chunk getOrCreateChunk(int x, int z) {
/* 30 */     this.random.setSeed(x * 341873128712L + z * 132897987541L);
/* 31 */     byte[] types = this.generator.generate(this.world.getWorld(), this.random, x, z);
/*    */     
/* 33 */     Chunk chunk = new Chunk(this.world, types, x, z);
/*    */     
/* 35 */     chunk.initLighting();
/*    */     
/* 37 */     return chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void getChunkAt(IChunkProvider icp, int i, int i1) {}
/*    */ 
/*    */   
/* 45 */   public boolean saveChunks(boolean bln, IProgressUpdate ipu) { return true; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean unloadChunks() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean canSave() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public byte[] generate(World world, Random random, int x, int z) { return this.generator.generate(world, random, x, z); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Chunk getChunkAt(int x, int z) { return getOrCreateChunk(x, z); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public boolean canSpawn(World world, int x, int z) { return this.generator.canSpawn(world, x, z); }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public List<BlockPopulator> getDefaultPopulators(World world) { return this.generator.getDefaultPopulators(world); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\generator\CustomChunkGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */