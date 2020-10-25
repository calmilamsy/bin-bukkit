/*    */ package org.bukkit.craftbukkit.generator;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.Chunk;
/*    */ import net.minecraft.server.IChunkProvider;
/*    */ import net.minecraft.server.IProgressUpdate;
/*    */ import net.minecraft.server.World;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ import org.bukkit.generator.BlockPopulator;
/*    */ 
/*    */ public class NormalChunkGenerator extends InternalChunkGenerator {
/*    */   private final IChunkProvider provider;
/*    */   
/* 17 */   public NormalChunkGenerator(World world, long seed) { this.provider = world.worldProvider.getChunkProvider(); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public byte[] generate(World world, Random random, int x, int z) { throw new UnsupportedOperationException("Not supported."); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public boolean canSpawn(World world, int x, int z) { return (((CraftWorld)world).getHandle()).worldProvider.canSpawn(x, z); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public List<BlockPopulator> getDefaultPopulators(World world) { return new ArrayList(); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean isChunkLoaded(int i, int i1) { return this.provider.isChunkLoaded(i, i1); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Chunk getOrCreateChunk(int i, int i1) { return this.provider.getOrCreateChunk(i, i1); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public Chunk getChunkAt(int i, int i1) { return this.provider.getChunkAt(i, i1); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void getChunkAt(IChunkProvider icp, int i, int i1) { this.provider.getChunkAt(icp, i, i1); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean saveChunks(boolean bln, IProgressUpdate ipu) { return this.provider.saveChunks(bln, ipu); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean unloadChunks() { return this.provider.unloadChunks(); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean canSave() { return this.provider.canSave(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\generator\NormalChunkGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */