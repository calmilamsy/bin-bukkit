/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.LightningStrike;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.generator.BlockPopulator;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.Vector;
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
/*     */ public interface World
/*     */ {
/*     */   Block getBlockAt(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   Block getBlockAt(Location paramLocation);
/*     */   
/*     */   int getBlockTypeIdAt(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   int getBlockTypeIdAt(Location paramLocation);
/*     */   
/*     */   int getHighestBlockYAt(int paramInt1, int paramInt2);
/*     */   
/*     */   int getHighestBlockYAt(Location paramLocation);
/*     */   
/*     */   Block getHighestBlockAt(int paramInt1, int paramInt2);
/*     */   
/*     */   Block getHighestBlockAt(Location paramLocation);
/*     */   
/*     */   Chunk getChunkAt(int paramInt1, int paramInt2);
/*     */   
/*     */   Chunk getChunkAt(Location paramLocation);
/*     */   
/*     */   Chunk getChunkAt(Block paramBlock);
/*     */   
/*     */   boolean isChunkLoaded(Chunk paramChunk);
/*     */   
/*     */   Chunk[] getLoadedChunks();
/*     */   
/*     */   void loadChunk(Chunk paramChunk);
/*     */   
/*     */   boolean isChunkLoaded(int paramInt1, int paramInt2);
/*     */   
/*     */   void loadChunk(int paramInt1, int paramInt2);
/*     */   
/*     */   boolean loadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   boolean unloadChunk(Chunk paramChunk);
/*     */   
/*     */   boolean unloadChunk(int paramInt1, int paramInt2);
/*     */   
/*     */   boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   boolean unloadChunkRequest(int paramInt1, int paramInt2);
/*     */   
/*     */   boolean unloadChunkRequest(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   boolean regenerateChunk(int paramInt1, int paramInt2);
/*     */   
/*     */   boolean refreshChunk(int paramInt1, int paramInt2);
/*     */   
/*     */   Item dropItem(Location paramLocation, ItemStack paramItemStack);
/*     */   
/*     */   Item dropItemNaturally(Location paramLocation, ItemStack paramItemStack);
/*     */   
/*     */   Arrow spawnArrow(Location paramLocation, Vector paramVector, float paramFloat1, float paramFloat2);
/*     */   
/*     */   boolean generateTree(Location paramLocation, TreeType paramTreeType);
/*     */   
/*     */   boolean generateTree(Location paramLocation, TreeType paramTreeType, BlockChangeDelegate paramBlockChangeDelegate);
/*     */   
/*     */   LivingEntity spawnCreature(Location paramLocation, CreatureType paramCreatureType);
/*     */   
/*     */   LightningStrike strikeLightning(Location paramLocation);
/*     */   
/*     */   LightningStrike strikeLightningEffect(Location paramLocation);
/*     */   
/*     */   List<Entity> getEntities();
/*     */   
/*     */   List<LivingEntity> getLivingEntities();
/*     */   
/*     */   List<Player> getPlayers();
/*     */   
/*     */   String getName();
/*     */   
/*     */   UUID getUID();
/*     */   
/*     */   @Deprecated
/*     */   long getId();
/*     */   
/*     */   Location getSpawnLocation();
/*     */   
/*     */   boolean setSpawnLocation(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   long getTime();
/*     */   
/*     */   void setTime(long paramLong);
/*     */   
/*     */   long getFullTime();
/*     */   
/*     */   void setFullTime(long paramLong);
/*     */   
/*     */   boolean hasStorm();
/*     */   
/*     */   void setStorm(boolean paramBoolean);
/*     */   
/*     */   int getWeatherDuration();
/*     */   
/*     */   void setWeatherDuration(int paramInt);
/*     */   
/*     */   boolean isThundering();
/*     */   
/*     */   void setThundering(boolean paramBoolean);
/*     */   
/*     */   int getThunderDuration();
/*     */   
/*     */   void setThunderDuration(int paramInt);
/*     */   
/*     */   boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);
/*     */   
/*     */   boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean);
/*     */   
/*     */   boolean createExplosion(Location paramLocation, float paramFloat);
/*     */   
/*     */   boolean createExplosion(Location paramLocation, float paramFloat, boolean paramBoolean);
/*     */   
/*     */   Environment getEnvironment();
/*     */   
/*     */   long getSeed();
/*     */   
/*     */   boolean getPVP();
/*     */   
/*     */   void setPVP(boolean paramBoolean);
/*     */   
/*     */   ChunkGenerator getGenerator();
/*     */   
/*     */   void save();
/*     */   
/*     */   List<BlockPopulator> getPopulators();
/*     */   
/*     */   <T extends Entity> T spawn(Location paramLocation, Class<T> paramClass) throws IllegalArgumentException;
/*     */   
/*     */   void playEffect(Location paramLocation, Effect paramEffect, int paramInt);
/*     */   
/*     */   void playEffect(Location paramLocation, Effect paramEffect, int paramInt1, int paramInt2);
/*     */   
/*     */   ChunkSnapshot getEmptyChunkSnapshot(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   void setSpawnFlags(boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   boolean getAllowAnimals();
/*     */   
/*     */   boolean getAllowMonsters();
/*     */   
/*     */   Biome getBiome(int paramInt1, int paramInt2);
/*     */   
/*     */   double getTemperature(int paramInt1, int paramInt2);
/*     */   
/*     */   double getHumidity(int paramInt1, int paramInt2);
/*     */   
/*     */   int getMaxHeight();
/*     */   
/*     */   boolean getKeepSpawnInMemory();
/*     */   
/*     */   void setKeepSpawnInMemory(boolean paramBoolean);
/*     */   
/*     */   boolean isAutoSave();
/*     */   
/*     */   void setAutoSave(boolean paramBoolean);
/*     */   
/*     */   public enum Environment
/*     */   {
/* 724 */     NORMAL(false),
/*     */ 
/*     */ 
/*     */     
/* 728 */     NETHER(-1),
/*     */ 
/*     */ 
/*     */     
/* 732 */     SKYLANDS(true);
/*     */     
/*     */     static  {
/* 735 */       lookup = new HashMap();
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
/* 755 */       for (Environment env : values())
/* 756 */         lookup.put(Integer.valueOf(env.getId()), env); 
/*     */     }
/*     */     
/*     */     private final int id;
/*     */     private static final Map<Integer, Environment> lookup;
/*     */     
/*     */     Environment(int id) { this.id = id; }
/*     */     
/*     */     public int getId() { return this.id; }
/*     */     
/*     */     public static Environment getEnvironment(int id) { return (Environment)lookup.get(Integer.valueOf(id)); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\World.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */