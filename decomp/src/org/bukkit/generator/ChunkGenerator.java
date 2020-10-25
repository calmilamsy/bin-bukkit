/*    */ package org.bukkit.generator;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
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
/*    */ public abstract class ChunkGenerator
/*    */ {
/*    */   public abstract byte[] generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2);
/*    */   
/*    */   public boolean canSpawn(World world, int x, int z) {
/* 50 */     Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);
/*    */     
/* 52 */     switch (world.getEnvironment()) {
/*    */       case NETHER:
/* 54 */         return true;
/*    */       case SKYLANDS:
/* 56 */         return (highest.getType() != Material.AIR && highest.getType() != Material.WATER && highest.getType() != Material.LAVA);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     return (highest.getType() == Material.SAND || highest.getType() == Material.GRAVEL);
/*    */   }
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
/* 73 */   public List<BlockPopulator> getDefaultPopulators(World world) { return new ArrayList(); }
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
/* 87 */   public Location getFixedSpawnLocation(World world, Random random) { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\generator\ChunkGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */