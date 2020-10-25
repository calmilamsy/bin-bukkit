/*     */ package org.bukkit.craftbukkit;
/*     */ import java.lang.ref.WeakReference;
/*     */ import net.minecraft.server.BiomeBase;
/*     */ import net.minecraft.server.Chunk;
/*     */ import net.minecraft.server.ChunkPosition;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.WorldChunkManager;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.ChunkSnapshot;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.block.CraftBlock;
/*     */ import org.bukkit.entity.Entity;
/*     */ 
/*     */ public class CraftChunk implements Chunk {
/*     */   private WeakReference<Chunk> weakChunk;
/*     */   private final ConcurrentMap<Integer, Block> cache;
/*     */   
/*     */   public CraftChunk(Chunk chunk) {
/*  22 */     this.cache = (new MapMaker()).softValues().makeMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  28 */     this.weakChunk = new WeakReference(chunk);
/*  29 */     this.worldServer = (WorldServer)(getHandle()).world;
/*  30 */     this.x = (getHandle()).x;
/*  31 */     this.z = (getHandle()).z;
/*     */   }
/*     */   private WorldServer worldServer; private int x; private int z;
/*     */   
/*  35 */   public World getWorld() { return this.worldServer.getWorld(); }
/*     */ 
/*     */   
/*     */   public Chunk getHandle() {
/*  39 */     Chunk c = (Chunk)this.weakChunk.get();
/*  40 */     if (c == null) {
/*  41 */       c = this.worldServer.getChunkAt(this.x, this.z);
/*  42 */       this.weakChunk = new WeakReference(c);
/*     */     } 
/*  44 */     return c;
/*     */   }
/*     */ 
/*     */   
/*  48 */   void breakLink() { this.weakChunk.clear(); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public int getX() { return this.x; }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public int getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public String toString() { return "CraftChunk{x=" + getX() + "z=" + getZ() + '}'; }
/*     */ 
/*     */   
/*     */   public Block getBlock(int x, int y, int z) {
/*  65 */     int pos = (x & 0xF) << 11 | (z & 0xF) << 7 | y & 0x7F;
/*  66 */     Block block = (Block)this.cache.get(Integer.valueOf(pos));
/*  67 */     if (block == null) {
/*  68 */       CraftBlock craftBlock = new CraftBlock(this, getX() << 4 | x & 0xF, y & 0x7F, getZ() << 4 | z & 0xF);
/*  69 */       Block oldBlock = (Block)this.cache.put(Integer.valueOf(pos), craftBlock);
/*  70 */       if (oldBlock == null) {
/*  71 */         CraftBlock craftBlock1 = craftBlock;
/*     */       } else {
/*  73 */         block = oldBlock;
/*     */       } 
/*     */     } 
/*  76 */     return block;
/*     */   }
/*     */   
/*     */   public Entity[] getEntities() {
/*  80 */     int count = 0, index = 0;
/*  81 */     Chunk chunk = getHandle();
/*  82 */     for (i = 0; i < 8; i++) {
/*  83 */       count += chunk.entitySlices[i].size();
/*     */     }
/*     */     
/*  86 */     Entity[] entities = new Entity[count];
/*  87 */     for (int i = 0; i < 8; i++) {
/*  88 */       for (Object obj : chunk.entitySlices[i].toArray()) {
/*  89 */         if (obj instanceof Entity)
/*     */         {
/*     */           
/*  92 */           entities[index++] = ((Entity)obj).getBukkitEntity(); } 
/*     */       } 
/*     */     } 
/*  95 */     return entities;
/*     */   }
/*     */   
/*     */   public BlockState[] getTileEntities() {
/*  99 */     int index = 0;
/* 100 */     Chunk chunk = getHandle();
/* 101 */     BlockState[] entities = new BlockState[chunk.tileEntities.size()];
/* 102 */     for (Object obj : chunk.tileEntities.keySet().toArray()) {
/* 103 */       if (obj instanceof ChunkPosition) {
/*     */ 
/*     */         
/* 106 */         ChunkPosition position = (ChunkPosition)obj;
/* 107 */         entities[index++] = this.worldServer.getWorld().getBlockAt(position.x + (chunk.x << 4), position.y, position.z + (chunk.z << 4)).getState();
/*     */       } 
/* 109 */     }  return entities;
/*     */   }
/*     */ 
/*     */   
/* 113 */   public boolean isLoaded() { return getWorld().isChunkLoaded(this); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public boolean load() { return getWorld().loadChunk(getX(), getZ(), true); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public boolean load(boolean generate) { return getWorld().loadChunk(getX(), getZ(), generate); }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public boolean unload() { return getWorld().unloadChunk(getX(), getZ()); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   public boolean unload(boolean save) { return getWorld().unloadChunk(getX(), getZ(), save); }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public boolean unload(boolean save, boolean safe) { return getWorld().unloadChunk(getX(), getZ(), save, safe); }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public ChunkSnapshot getChunkSnapshot() { return getChunkSnapshot(true, false, false); }
/*     */ 
/*     */   
/*     */   public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain) {
/* 141 */     Chunk chunk = getHandle();
/* 142 */     byte[] buf = new byte[81920];
/* 143 */     chunk.getData(buf, 0, 0, 0, 16, 128, 16, 0);
/* 144 */     byte[] hmap = null;
/*     */     
/* 146 */     if (includeMaxblocky) {
/* 147 */       hmap = new byte[256];
/* 148 */       System.arraycopy(chunk.heightMap, 0, hmap, 0, 256);
/*     */     } 
/*     */     
/* 151 */     BiomeBase[] biome = null;
/* 152 */     double[] biomeTemp = null;
/* 153 */     double[] biomeRain = null;
/*     */     
/* 155 */     if (includeBiome || includeBiomeTempRain) {
/* 156 */       WorldChunkManager wcm = chunk.world.getWorldChunkManager();
/* 157 */       BiomeBase[] biomeBase = wcm.getBiomeData(getX() << 4, getZ() << 4, 16, 16);
/*     */       
/* 159 */       if (includeBiome) {
/* 160 */         biome = new BiomeBase[256];
/* 161 */         System.arraycopy(biomeBase, 0, biome, 0, biome.length);
/*     */       } 
/*     */       
/* 164 */       if (includeBiomeTempRain) {
/* 165 */         biomeTemp = new double[256];
/* 166 */         biomeRain = new double[256];
/* 167 */         System.arraycopy(wcm.temperature, 0, biomeTemp, 0, biomeTemp.length);
/* 168 */         System.arraycopy(wcm.rain, 0, biomeRain, 0, biomeRain.length);
/*     */       } 
/*     */     } 
/* 171 */     World world = getWorld();
/* 172 */     return new CraftChunkSnapshot(getX(), getZ(), world.getName(), world.getFullTime(), buf, hmap, biome, biomeTemp, biomeRain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EmptyChunkSnapshot
/*     */     extends CraftChunkSnapshot
/*     */   {
/* 180 */     EmptyChunkSnapshot(int x, int z, String worldName, long time, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) { super(x, z, worldName, time, null, null, biome, biomeTemp, biomeRain); }
/*     */ 
/*     */ 
/*     */     
/* 184 */     public final int getBlockTypeId(int x, int y, int z) { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 188 */     public final int getBlockData(int x, int y, int z) { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 192 */     public final int getBlockSkyLight(int x, int y, int z) { return 15; }
/*     */ 
/*     */ 
/*     */     
/* 196 */     public final int getBlockEmittedLight(int x, int y, int z) { return 0; }
/*     */ 
/*     */ 
/*     */     
/* 200 */     public final int getHighestBlockYAt(int x, int z) { return 0; }
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
/* 205 */     BiomeBase[] biome = null;
/* 206 */     double[] biomeTemp = null;
/* 207 */     double[] biomeRain = null;
/*     */     
/* 209 */     if (includeBiome || includeBiomeTempRain) {
/* 210 */       WorldChunkManager wcm = world.getHandle().getWorldChunkManager();
/* 211 */       BiomeBase[] biomeBase = wcm.getBiomeData(x << 4, z << 4, 16, 16);
/*     */       
/* 213 */       if (includeBiome) {
/* 214 */         biome = new BiomeBase[256];
/* 215 */         System.arraycopy(biomeBase, 0, biome, 0, biome.length);
/*     */       } 
/*     */       
/* 218 */       if (includeBiomeTempRain) {
/* 219 */         biomeTemp = new double[256];
/* 220 */         biomeRain = new double[256];
/* 221 */         System.arraycopy(wcm.temperature, 0, biomeTemp, 0, biomeTemp.length);
/* 222 */         System.arraycopy(wcm.rain, 0, biomeRain, 0, biomeRain.length);
/*     */       } 
/*     */     } 
/* 225 */     return new EmptyChunkSnapshot(x, z, world.getName(), world.getFullTime(), biome, biomeTemp, biomeRain);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\CraftChunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */