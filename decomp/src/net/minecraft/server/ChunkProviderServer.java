/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.CraftChunk;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.util.LongHashset;
/*     */ import org.bukkit.craftbukkit.util.LongHashtable;
/*     */ import org.bukkit.event.world.ChunkLoadEvent;
/*     */ import org.bukkit.event.world.ChunkPopulateEvent;
/*     */ import org.bukkit.event.world.ChunkUnloadEvent;
/*     */ import org.bukkit.generator.BlockPopulator;
/*     */ 
/*     */ public class ChunkProviderServer
/*     */   implements IChunkProvider
/*     */ {
/*     */   public LongHashset unloadQueue;
/*     */   public Chunk emptyChunk;
/*     */   public IChunkProvider chunkProvider;
/*     */   private IChunkLoader e;
/*     */   
/*     */   public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
/*  25 */     this.unloadQueue = new LongHashset();
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.forceChunkLoad = false;
/*  30 */     this.chunks = new LongHashtable();
/*  31 */     this.chunkList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     this.emptyChunk = new EmptyChunk(worldserver, new byte[32768], false, false);
/*  37 */     this.world = worldserver;
/*  38 */     this.e = ichunkloader;
/*  39 */     this.chunkProvider = ichunkprovider;
/*     */   }
/*     */   public boolean forceChunkLoad; public LongHashtable<Chunk> chunks; public List chunkList; public WorldServer world;
/*     */   
/*  43 */   public boolean isChunkLoaded(int i, int j) { return this.chunks.containsKey(i, j); }
/*     */ 
/*     */   
/*     */   public void queueUnload(int i, int j) {
/*  47 */     ChunkCoordinates chunkcoordinates = this.world.getSpawn();
/*  48 */     int k = i * 16 + 8 - chunkcoordinates.x;
/*  49 */     int l = j * 16 + 8 - chunkcoordinates.z;
/*  50 */     short short1 = 128;
/*     */     
/*  52 */     if (k < -short1 || k > short1 || l < -short1 || l > short1 || !this.world.keepSpawnInMemory) {
/*  53 */       this.unloadQueue.add(i, j);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getChunkAt(int i, int j) {
/*  59 */     this.unloadQueue.remove(i, j);
/*  60 */     Chunk chunk = (Chunk)this.chunks.get(i, j);
/*  61 */     boolean newChunk = false;
/*     */ 
/*     */     
/*  64 */     if (chunk == null) {
/*  65 */       chunk = loadChunk(i, j);
/*  66 */       if (chunk == null) {
/*  67 */         if (this.chunkProvider == null) {
/*  68 */           chunk = this.emptyChunk;
/*     */         } else {
/*  70 */           chunk = this.chunkProvider.getOrCreateChunk(i, j);
/*     */         } 
/*  72 */         newChunk = true;
/*     */       } 
/*     */       
/*  75 */       this.chunks.put(i, j, chunk);
/*  76 */       this.chunkList.add(chunk);
/*  77 */       if (chunk != null) {
/*  78 */         chunk.loadNOP();
/*  79 */         chunk.addEntities();
/*     */       } 
/*     */ 
/*     */       
/*  83 */       CraftServer craftServer = this.world.getServer();
/*  84 */       if (craftServer != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  90 */         craftServer.getPluginManager().callEvent(new ChunkLoadEvent(chunk.bukkitChunk, newChunk));
/*     */       }
/*     */ 
/*     */       
/*  94 */       if (!chunk.done && isChunkLoaded(i + 1, j + 1) && isChunkLoaded(i, j + 1) && isChunkLoaded(i + 1, j)) {
/*  95 */         getChunkAt(this, i, j);
/*     */       }
/*     */       
/*  98 */       if (isChunkLoaded(i - 1, j) && !(getOrCreateChunk(i - 1, j)).done && isChunkLoaded(i - 1, j + 1) && isChunkLoaded(i, j + 1) && isChunkLoaded(i - 1, j)) {
/*  99 */         getChunkAt(this, i - 1, j);
/*     */       }
/*     */       
/* 102 */       if (isChunkLoaded(i, j - 1) && !(getOrCreateChunk(i, j - 1)).done && isChunkLoaded(i + 1, j - 1) && isChunkLoaded(i, j - 1) && isChunkLoaded(i + 1, j)) {
/* 103 */         getChunkAt(this, i, j - 1);
/*     */       }
/*     */       
/* 106 */       if (isChunkLoaded(i - 1, j - 1) && !(getOrCreateChunk(i - 1, j - 1)).done && isChunkLoaded(i - 1, j - 1) && isChunkLoaded(i, j - 1) && isChunkLoaded(i - 1, j)) {
/* 107 */         getChunkAt(this, i - 1, j - 1);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getOrCreateChunk(int i, int j) {
/* 116 */     Chunk chunk = (Chunk)this.chunks.get(i, j);
/*     */     
/* 118 */     chunk = (chunk == null) ? ((!this.world.isLoading && !this.forceChunkLoad) ? this.emptyChunk : getChunkAt(i, j)) : chunk;
/* 119 */     if (chunk == this.emptyChunk) return chunk; 
/* 120 */     if (i != chunk.x || j != chunk.z) {
/* 121 */       MinecraftServer.log.info("Chunk (" + chunk.x + ", " + chunk.z + ") stored at  (" + i + ", " + j + ")");
/* 122 */       MinecraftServer.log.info(chunk.getClass().getName());
/* 123 */       Throwable ex = new Throwable();
/* 124 */       ex.fillInStackTrace();
/* 125 */       ex.printStackTrace();
/*     */     } 
/* 127 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int i, int j) {
/* 132 */     if (this.e == null) {
/* 133 */       return null;
/*     */     }
/*     */     try {
/* 136 */       Chunk chunk = this.e.a(this.world, i, j);
/*     */       
/* 138 */       if (chunk != null) {
/* 139 */         chunk.r = this.world.getTime();
/*     */       }
/*     */       
/* 142 */       return chunk;
/* 143 */     } catch (Exception exception) {
/* 144 */       exception.printStackTrace();
/* 145 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveChunkNOP(Chunk chunk) {
/* 151 */     if (this.e != null) {
/*     */       try {
/* 153 */         this.e.b(this.world, chunk);
/* 154 */       } catch (Exception exception) {
/* 155 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveChunk(Chunk chunk) {
/* 161 */     if (this.e != null) {
/*     */       try {
/* 163 */         chunk.r = this.world.getTime();
/* 164 */         this.e.a(this.world, chunk);
/* 165 */       } catch (Exception ioexception) {
/* 166 */         ioexception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
/* 172 */     Chunk chunk = getOrCreateChunk(i, j);
/*     */     
/* 174 */     if (!chunk.done) {
/* 175 */       chunk.done = true;
/* 176 */       if (this.chunkProvider != null) {
/* 177 */         this.chunkProvider.getChunkAt(ichunkprovider, i, j);
/*     */ 
/*     */         
/* 180 */         BlockSand.instaFall = true;
/* 181 */         Random random = new Random();
/* 182 */         random.setSeed(this.world.getSeed());
/* 183 */         long xRand = random.nextLong() / 2L * 2L + 1L;
/* 184 */         long zRand = random.nextLong() / 2L * 2L + 1L;
/* 185 */         random.setSeed(i * xRand + j * zRand ^ this.world.getSeed());
/*     */         
/* 187 */         CraftWorld craftWorld = this.world.getWorld();
/* 188 */         if (craftWorld != null) {
/* 189 */           for (BlockPopulator populator : craftWorld.getPopulators()) {
/* 190 */             populator.populate(craftWorld, random, chunk.bukkitChunk);
/*     */           }
/*     */         }
/* 193 */         BlockSand.instaFall = false;
/* 194 */         this.world.getServer().getPluginManager().callEvent(new ChunkPopulateEvent(chunk.bukkitChunk));
/*     */ 
/*     */         
/* 197 */         chunk.f();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
/* 203 */     int i = 0;
/*     */     
/* 205 */     for (int j = 0; j < this.chunkList.size(); j++) {
/* 206 */       Chunk chunk = (Chunk)this.chunkList.get(j);
/*     */       
/* 208 */       if (flag && !chunk.p) {
/* 209 */         saveChunkNOP(chunk);
/*     */       }
/*     */       
/* 212 */       if (chunk.a(flag)) {
/* 213 */         saveChunk(chunk);
/* 214 */         chunk.o = false;
/* 215 */         i++;
/* 216 */         if (i == 24 && !flag) {
/* 217 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     if (flag) {
/* 223 */       if (this.e == null) {
/* 224 */         return true;
/*     */       }
/*     */       
/* 227 */       this.e.b();
/*     */     } 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unloadChunks() {
/* 234 */     if (!this.world.canSave) {
/*     */       
/* 236 */       CraftServer craftServer = this.world.getServer();
/* 237 */       for (int i = 0; i < 50 && !this.unloadQueue.isEmpty(); i++) {
/* 238 */         long chunkcoordinates = this.unloadQueue.popFirst();
/* 239 */         Chunk chunk = (Chunk)this.chunks.get(chunkcoordinates);
/* 240 */         if (chunk != null) {
/*     */           
/* 242 */           ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
/* 243 */           craftServer.getPluginManager().callEvent(event);
/* 244 */           if (!event.isCancelled()) {
/* 245 */             this.world.getWorld().preserveChunk((CraftChunk)chunk.bukkitChunk);
/*     */             
/* 247 */             chunk.removeEntities();
/* 248 */             saveChunk(chunk);
/* 249 */             saveChunkNOP(chunk);
/*     */             
/* 251 */             this.chunks.remove(chunkcoordinates);
/* 252 */             this.chunkList.remove(chunk);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       if (this.e != null) {
/* 258 */         this.e.a();
/*     */       }
/*     */     } 
/*     */     
/* 262 */     return this.chunkProvider.unloadChunks();
/*     */   }
/*     */ 
/*     */   
/* 266 */   public boolean canSave() { return !this.world.canSave; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ChunkProviderServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */