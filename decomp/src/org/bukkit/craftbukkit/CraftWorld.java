/*     */ package org.bukkit.craftbukkit;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.BiomeBase;
/*     */ import net.minecraft.server.Chunk;
/*     */ import net.minecraft.server.ChunkCoordinates;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityArrow;
/*     */ import net.minecraft.server.EntityFireball;
/*     */ import net.minecraft.server.EntityFish;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityMinecart;
/*     */ import net.minecraft.server.EntitySnowball;
/*     */ import net.minecraft.server.EntityWeatherStorm;
/*     */ import net.minecraft.server.EntityZombie;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Packet61;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.BlockChangeDelegate;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.entity.CraftMinecart;
/*     */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.weather.ThunderChangeEvent;
/*     */ import org.bukkit.event.weather.WeatherChangeEvent;
/*     */ import org.bukkit.event.world.SpawnChangeEvent;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CraftWorld implements World {
/*     */   private final WorldServer world;
/*     */   private World.Environment environment;
/*     */   private final CraftServer server;
/*  45 */   private static final Random rand = new Random(); private ConcurrentMap<Integer, CraftChunk> unloadedChunks; private final ChunkGenerator generator; private final List<BlockPopulator> populators; public CraftWorld(WorldServer world, ChunkGenerator gen, World.Environment env) { this.server = (CraftServer)Bukkit.getServer();
/*     */     this.unloadedChunks = (new MapMaker()).weakValues().makeMap();
/*     */     this.populators = new ArrayList();
/*  48 */     this.world = world;
/*  49 */     this.generator = gen;
/*     */     
/*  51 */     this.environment = env; }
/*     */ 
/*     */   
/*     */   public void preserveChunk(CraftChunk chunk) {
/*  55 */     chunk.breakLink();
/*  56 */     this.unloadedChunks.put(Integer.valueOf((chunk.getX() << 16) + chunk.getZ()), chunk);
/*     */   }
/*     */ 
/*     */   
/*  60 */   public Chunk popPreservedChunk(int x, int z) { return (Chunk)this.unloadedChunks.remove(Integer.valueOf((x << 16) + z)); }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public Block getBlockAt(int x, int y, int z) { return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0x7F, z & 0xF); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public int getBlockTypeIdAt(int x, int y, int z) { return this.world.getTypeId(x, y, z); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public int getHighestBlockYAt(int x, int z) { return this.world.getHighestBlockYAt(x, z); }
/*     */ 
/*     */   
/*     */   public Location getSpawnLocation() {
/*  76 */     ChunkCoordinates spawn = this.world.getSpawn();
/*  77 */     return new Location(this, spawn.x, spawn.y, spawn.z);
/*     */   }
/*     */   
/*     */   public boolean setSpawnLocation(int x, int y, int z) {
/*     */     try {
/*  82 */       Location previousLocation = getSpawnLocation();
/*  83 */       this.world.worldData.setSpawn(x, y, z);
/*     */ 
/*     */       
/*  86 */       SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
/*  87 */       this.server.getPluginManager().callEvent(event);
/*     */       
/*  89 */       return true;
/*  90 */     } catch (Exception e) {
/*  91 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  96 */   public Chunk getChunkAt(int x, int z) { return (this.world.chunkProviderServer.getChunkAt(x, z)).bukkitChunk; }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public Chunk getChunkAt(Block block) { return getChunkAt(block.getX() >> 4, block.getZ() >> 4); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public boolean isChunkLoaded(int x, int z) { return this.world.chunkProviderServer.isChunkLoaded(x, z); }
/*     */ 
/*     */   
/*     */   public Chunk[] getLoadedChunks() {
/* 108 */     Object[] chunks = this.world.chunkProviderServer.chunks.values().toArray();
/* 109 */     CraftChunk[] arrayOfCraftChunk = new CraftChunk[chunks.length];
/*     */     
/* 111 */     for (int i = 0; i < chunks.length; i++) {
/* 112 */       Chunk chunk = (Chunk)chunks[i];
/* 113 */       arrayOfCraftChunk[i] = chunk.bukkitChunk;
/*     */     } 
/*     */     
/* 116 */     return arrayOfCraftChunk;
/*     */   }
/*     */ 
/*     */   
/* 120 */   public void loadChunk(int x, int z) { loadChunk(x, z, true); }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public boolean unloadChunk(Chunk chunk) { return unloadChunk(chunk.getX(), chunk.getZ()); }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public boolean unloadChunk(int x, int z) { return unloadChunk(x, z, true); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean unloadChunk(int x, int z, boolean save) { return unloadChunk(x, z, save, false); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public boolean unloadChunkRequest(int x, int z) { return unloadChunkRequest(x, z, true); }
/*     */ 
/*     */   
/*     */   public boolean unloadChunkRequest(int x, int z, boolean safe) {
/* 140 */     if (safe && isChunkInUse(x, z)) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     this.world.chunkProviderServer.queueUnload(x, z);
/*     */     
/* 146 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
/* 150 */     if (safe && isChunkInUse(x, z)) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     Chunk chunk = this.world.chunkProviderServer.getOrCreateChunk(x, z);
/*     */     
/* 156 */     if (save && !chunk.isEmpty()) {
/* 157 */       chunk.removeEntities();
/* 158 */       this.world.chunkProviderServer.saveChunk(chunk);
/* 159 */       this.world.chunkProviderServer.saveChunkNOP(chunk);
/*     */     } 
/*     */     
/* 162 */     preserveChunk((CraftChunk)chunk.bukkitChunk);
/* 163 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/* 164 */     this.world.chunkProviderServer.chunks.remove(x, z);
/* 165 */     this.world.chunkProviderServer.chunkList.remove(chunk);
/*     */     
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   public boolean regenerateChunk(int x, int z) {
/* 171 */     unloadChunk(x, z, false, false);
/*     */     
/* 173 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*     */     
/* 175 */     Chunk chunk = null;
/*     */     
/* 177 */     if (this.world.chunkProviderServer.chunkProvider == null) {
/* 178 */       chunk = this.world.chunkProviderServer.emptyChunk;
/*     */     } else {
/* 180 */       chunk = this.world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);
/*     */     } 
/*     */     
/* 183 */     chunkLoadPostProcess(chunk, x, z);
/*     */     
/* 185 */     refreshChunk(x, z);
/*     */     
/* 187 */     return (chunk != null);
/*     */   }
/*     */   
/*     */   public boolean refreshChunk(int x, int z) {
/* 191 */     if (!isChunkLoaded(x, z)) {
/* 192 */       return false;
/*     */     }
/*     */     
/* 195 */     int px = x << 4;
/* 196 */     int pz = z << 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     for (int xx = px; xx < px + 16; xx++) {
/* 204 */       this.world.notify(xx, 0, pz);
/*     */     }
/* 206 */     this.world.notify(px, 127, pz + 15);
/*     */     
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChunkInUse(int x, int z) {
/* 213 */     Player[] players = this.server.getOnlinePlayers();
/*     */     
/* 215 */     for (Player player : players) {
/* 216 */       Location loc = player.getLocation();
/* 217 */       if (loc.getWorld() == this.world.chunkProviderServer.world.getWorld())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 224 */         if (Math.abs(loc.getBlockX() - (x << 4)) <= 256 && Math.abs(loc.getBlockZ() - (z << 4)) <= 256)
/* 225 */           return true; 
/*     */       }
/*     */     } 
/* 228 */     return false;
/*     */   }
/*     */   
/*     */   public boolean loadChunk(int x, int z, boolean generate) {
/* 232 */     if (generate)
/*     */     {
/* 234 */       return (this.world.chunkProviderServer.getChunkAt(x, z) != null);
/*     */     }
/*     */     
/* 237 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/* 238 */     Chunk chunk = (Chunk)this.world.chunkProviderServer.chunks.get(x, z);
/*     */     
/* 240 */     if (chunk == null) {
/* 241 */       chunk = this.world.chunkProviderServer.loadChunk(x, z);
/*     */       
/* 243 */       chunkLoadPostProcess(chunk, x, z);
/*     */     } 
/* 245 */     return (chunk != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void chunkLoadPostProcess(Chunk chunk, int x, int z) {
/* 250 */     if (chunk != null) {
/* 251 */       this.world.chunkProviderServer.chunks.put(x, z, chunk);
/* 252 */       this.world.chunkProviderServer.chunkList.add(chunk);
/*     */       
/* 254 */       chunk.loadNOP();
/* 255 */       chunk.addEntities();
/*     */       
/* 257 */       if (!chunk.done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
/* 258 */         this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z);
/*     */       }
/*     */       
/* 261 */       if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z) && !(this.world.chunkProviderServer.getOrCreateChunk(x - 1, z)).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
/* 262 */         this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z);
/*     */       }
/*     */       
/* 265 */       if (this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && !(this.world.chunkProviderServer.getOrCreateChunk(x, z - 1)).done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
/* 266 */         this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z - 1);
/*     */       }
/*     */       
/* 269 */       if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && !(this.world.chunkProviderServer.getOrCreateChunk(x - 1, z - 1)).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
/* 270 */         this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 276 */   public boolean isChunkLoaded(Chunk chunk) { return isChunkLoaded(chunk.getX(), chunk.getZ()); }
/*     */ 
/*     */   
/*     */   public void loadChunk(Chunk chunk) {
/* 280 */     loadChunk(chunk.getX(), chunk.getZ());
/* 281 */     (((CraftChunk)getChunkAt(chunk.getX(), chunk.getZ())).getHandle()).bukkitChunk = chunk;
/*     */   }
/*     */ 
/*     */   
/* 285 */   public WorldServer getHandle() { return this.world; }
/*     */ 
/*     */   
/*     */   public Item dropItem(Location loc, ItemStack item) {
/* 289 */     ItemStack stack = new ItemStack(item.getTypeId(), item.getAmount(), item.getDurability());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     EntityItem entity = new EntityItem(this.world, loc.getX(), loc.getY(), loc.getZ(), stack);
/* 295 */     entity.pickupDelay = 10;
/* 296 */     this.world.addEntity(entity);
/*     */ 
/*     */     
/* 299 */     return new CraftItem(this.world.getServer(), entity);
/*     */   }
/*     */   
/*     */   public Item dropItemNaturally(Location loc, ItemStack item) {
/* 303 */     double xs = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/* 304 */     double ys = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/* 305 */     double zs = (this.world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
/* 306 */     loc = loc.clone();
/* 307 */     loc.setX(loc.getX() + xs);
/* 308 */     loc.setY(loc.getY() + ys);
/* 309 */     loc.setZ(loc.getZ() + zs);
/* 310 */     return dropItem(loc, item);
/*     */   }
/*     */   
/*     */   public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
/* 314 */     EntityArrow arrow = new EntityArrow(this.world);
/* 315 */     arrow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), 0.0F, 0.0F);
/* 316 */     this.world.addEntity(arrow);
/* 317 */     arrow.a(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
/* 318 */     return (Arrow)arrow.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public LivingEntity spawnCreature(Location loc, CreatureType creatureType) {
/*     */     LivingEntity livingEntity;
/*     */     try {
/* 324 */       EntityLiving entityCreature = (EntityLiving)EntityTypes.a(creatureType.getName(), this.world);
/* 325 */       entityCreature.setPosition(loc.getX(), loc.getY(), loc.getZ());
/* 326 */       livingEntity = (LivingEntity)CraftEntity.getEntity(this.server, entityCreature);
/* 327 */       this.world.addEntity(entityCreature, CreatureSpawnEvent.SpawnReason.CUSTOM);
/* 328 */     } catch (Exception e) {
/*     */       
/* 330 */       livingEntity = null;
/*     */     } 
/* 332 */     return livingEntity;
/*     */   }
/*     */   
/*     */   public LightningStrike strikeLightning(Location loc) {
/* 336 */     EntityWeatherStorm lightning = new EntityWeatherStorm(this.world, loc.getX(), loc.getY(), loc.getZ());
/* 337 */     this.world.strikeLightning(lightning);
/* 338 */     return new CraftLightningStrike(this.server, lightning);
/*     */   }
/*     */   
/*     */   public LightningStrike strikeLightningEffect(Location loc) {
/* 342 */     EntityWeatherStorm lightning = new EntityWeatherStorm(this.world, loc.getX(), loc.getY(), loc.getZ(), true);
/* 343 */     this.world.strikeLightning(lightning);
/* 344 */     return new CraftLightningStrike(this.server, lightning);
/*     */   }
/*     */ 
/*     */   
/* 348 */   public boolean generateTree(Location loc, TreeType type) { return generateTree(loc, type, this.world); }
/*     */ 
/*     */   
/*     */   public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
/* 352 */     switch (type) {
/*     */       case BIG_TREE:
/* 354 */         return (new WorldGenBigTree()).generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
/*     */       case BIRCH:
/* 356 */         return (new WorldGenForest()).generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
/*     */       case REDWOOD:
/* 358 */         return (new WorldGenTaiga2()).generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
/*     */       case TALL_REDWOOD:
/* 360 */         return (new WorldGenTaiga1()).generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
/*     */     } 
/*     */     
/* 363 */     return (new WorldGenTrees()).generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 368 */   public TileEntity getTileEntityAt(int x, int y, int z) { return this.world.getTileEntity(x, y, z); }
/*     */ 
/*     */ 
/*     */   
/* 372 */   public String getName() { return this.world.worldData.name; }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 377 */   public long getId() { return this.world.worldData.getSeed(); }
/*     */ 
/*     */ 
/*     */   
/* 381 */   public UUID getUID() { return this.world.getUUID(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 386 */   public String toString() { return "CraftWorld{name=" + getName() + '}'; }
/*     */ 
/*     */   
/*     */   public long getTime() {
/* 390 */     long time = getFullTime() % 24000L;
/* 391 */     if (time < 0L) time += 24000L; 
/* 392 */     return time;
/*     */   }
/*     */   
/*     */   public void setTime(long time) {
/* 396 */     long margin = (time - getFullTime()) % 24000L;
/* 397 */     if (margin < 0L) margin += 24000L; 
/* 398 */     setFullTime(getFullTime() + margin);
/*     */   }
/*     */ 
/*     */   
/* 402 */   public long getFullTime() { return this.world.getTime(); }
/*     */ 
/*     */   
/*     */   public void setFullTime(long time) {
/* 406 */     this.world.setTime(time);
/*     */ 
/*     */     
/* 409 */     for (Player p : getPlayers()) {
/* 410 */       CraftPlayer cp = (CraftPlayer)p;
/* 411 */       (cp.getHandle()).netServerHandler.sendPacket(new Packet4UpdateTime(cp.getHandle().getPlayerTime()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 416 */   public boolean createExplosion(double x, double y, double z, float power) { return createExplosion(x, y, z, power, false); }
/*     */ 
/*     */ 
/*     */   
/* 420 */   public boolean createExplosion(double x, double y, double z, float power, boolean setFire) { return !(this.world.createExplosion(null, x, y, z, power, setFire)).wasCanceled; }
/*     */ 
/*     */ 
/*     */   
/* 424 */   public boolean createExplosion(Location loc, float power) { return createExplosion(loc, power, false); }
/*     */ 
/*     */ 
/*     */   
/* 428 */   public boolean createExplosion(Location loc, float power, boolean setFire) { return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire); }
/*     */ 
/*     */ 
/*     */   
/* 432 */   public World.Environment getEnvironment() { return this.environment; }
/*     */ 
/*     */   
/*     */   public void setEnvironment(World.Environment env) {
/* 436 */     if (this.environment != env) {
/* 437 */       this.environment = env;
/* 438 */       this.world.worldProvider = WorldProvider.byDimension(this.environment.getId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 443 */   public Block getBlockAt(Location location) { return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()); }
/*     */ 
/*     */ 
/*     */   
/* 447 */   public int getBlockTypeIdAt(Location location) { return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()); }
/*     */ 
/*     */ 
/*     */   
/* 451 */   public int getHighestBlockYAt(Location location) { return getHighestBlockYAt(location.getBlockX(), location.getBlockZ()); }
/*     */ 
/*     */ 
/*     */   
/* 455 */   public Chunk getChunkAt(Location location) { return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4); }
/*     */ 
/*     */ 
/*     */   
/* 459 */   public ChunkGenerator getGenerator() { return this.generator; }
/*     */ 
/*     */ 
/*     */   
/* 463 */   public List<BlockPopulator> getPopulators() { return this.populators; }
/*     */ 
/*     */ 
/*     */   
/* 467 */   public Block getHighestBlockAt(int x, int z) { return getBlockAt(x, getHighestBlockYAt(x, z), z); }
/*     */ 
/*     */ 
/*     */   
/* 471 */   public Block getHighestBlockAt(Location location) { return getHighestBlockAt(location.getBlockX(), location.getBlockZ()); }
/*     */ 
/*     */   
/*     */   public Biome getBiome(int x, int z) {
/* 475 */     BiomeBase base = getHandle().getWorldChunkManager().getBiome(x, z);
/*     */     
/* 477 */     if (base == BiomeBase.RAINFOREST)
/* 478 */       return Biome.RAINFOREST; 
/* 479 */     if (base == BiomeBase.SWAMPLAND)
/* 480 */       return Biome.SWAMPLAND; 
/* 481 */     if (base == BiomeBase.SEASONAL_FOREST)
/* 482 */       return Biome.SEASONAL_FOREST; 
/* 483 */     if (base == BiomeBase.FOREST)
/* 484 */       return Biome.FOREST; 
/* 485 */     if (base == BiomeBase.SAVANNA)
/* 486 */       return Biome.SAVANNA; 
/* 487 */     if (base == BiomeBase.SHRUBLAND)
/* 488 */       return Biome.SHRUBLAND; 
/* 489 */     if (base == BiomeBase.TAIGA)
/* 490 */       return Biome.TAIGA; 
/* 491 */     if (base == BiomeBase.DESERT)
/* 492 */       return Biome.DESERT; 
/* 493 */     if (base == BiomeBase.PLAINS)
/* 494 */       return Biome.PLAINS; 
/* 495 */     if (base == BiomeBase.ICE_DESERT)
/* 496 */       return Biome.ICE_DESERT; 
/* 497 */     if (base == BiomeBase.TUNDRA)
/* 498 */       return Biome.TUNDRA; 
/* 499 */     if (base == BiomeBase.HELL)
/* 500 */       return Biome.HELL; 
/* 501 */     if (base == BiomeBase.SKY) {
/* 502 */       return Biome.SKY;
/*     */     }
/*     */     
/* 505 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 509 */   public double getTemperature(int x, int z) { return getHandle().getWorldChunkManager().a((double[])null, x, z, 1, 1)[0]; }
/*     */ 
/*     */ 
/*     */   
/* 513 */   public double getHumidity(int x, int z) { return getHandle().getWorldChunkManager().getHumidity(x, z); }
/*     */ 
/*     */   
/*     */   public List<Entity> getEntities() {
/* 517 */     List<Entity> list = new ArrayList<Entity>();
/*     */     
/* 519 */     for (Object o : this.world.entityList) {
/* 520 */       if (o instanceof Entity) {
/* 521 */         Entity mcEnt = (Entity)o;
/* 522 */         Entity bukkitEntity = mcEnt.getBukkitEntity();
/*     */ 
/*     */         
/* 525 */         if (bukkitEntity != null) {
/* 526 */           list.add(bukkitEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     return list;
/*     */   }
/*     */   
/*     */   public List<LivingEntity> getLivingEntities() {
/* 535 */     List<LivingEntity> list = new ArrayList<LivingEntity>();
/*     */     
/* 537 */     for (Object o : this.world.entityList) {
/* 538 */       if (o instanceof Entity) {
/* 539 */         Entity mcEnt = (Entity)o;
/* 540 */         Entity bukkitEntity = mcEnt.getBukkitEntity();
/*     */ 
/*     */         
/* 543 */         if (bukkitEntity != null && bukkitEntity instanceof LivingEntity) {
/* 544 */           list.add((LivingEntity)bukkitEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 549 */     return list;
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/* 553 */     List<Player> list = new ArrayList<Player>();
/*     */     
/* 555 */     for (Object o : this.world.entityList) {
/* 556 */       if (o instanceof Entity) {
/* 557 */         Entity mcEnt = (Entity)o;
/* 558 */         Entity bukkitEntity = mcEnt.getBukkitEntity();
/*     */         
/* 560 */         if (bukkitEntity != null && bukkitEntity instanceof Player) {
/* 561 */           list.add((Player)bukkitEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 566 */     return list;
/*     */   }
/*     */   
/*     */   public void save() {
/* 570 */     boolean oldSave = this.world.canSave;
/*     */     
/* 572 */     this.world.canSave = false;
/* 573 */     this.world.save(true, null);
/*     */     
/* 575 */     this.world.canSave = oldSave;
/*     */   }
/*     */ 
/*     */   
/* 579 */   public boolean isAutoSave() { return !this.world.canSave; }
/*     */ 
/*     */ 
/*     */   
/* 583 */   public void setAutoSave(boolean value) { this.world.canSave = !value; }
/*     */ 
/*     */ 
/*     */   
/* 587 */   public boolean hasStorm() { return this.world.worldData.hasStorm(); }
/*     */ 
/*     */   
/*     */   public void setStorm(boolean hasStorm) {
/* 591 */     CraftServer server = this.world.getServer();
/*     */     
/* 593 */     WeatherChangeEvent weather = new WeatherChangeEvent(this, hasStorm);
/* 594 */     server.getPluginManager().callEvent(weather);
/* 595 */     if (!weather.isCancelled()) {
/* 596 */       this.world.worldData.setStorm(hasStorm);
/*     */ 
/*     */       
/* 599 */       if (hasStorm) {
/* 600 */         setWeatherDuration(rand.nextInt(12000) + 12000);
/*     */       } else {
/* 602 */         setWeatherDuration(rand.nextInt(168000) + 12000);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 608 */   public int getWeatherDuration() { return this.world.worldData.getWeatherDuration(); }
/*     */ 
/*     */ 
/*     */   
/* 612 */   public void setWeatherDuration(int duration) { this.world.worldData.setWeatherDuration(duration); }
/*     */ 
/*     */ 
/*     */   
/* 616 */   public boolean isThundering() { return this.world.worldData.isThundering(); }
/*     */ 
/*     */   
/*     */   public void setThundering(boolean thundering) {
/* 620 */     CraftServer server = this.world.getServer();
/*     */     
/* 622 */     ThunderChangeEvent thunder = new ThunderChangeEvent(this, thundering);
/* 623 */     server.getPluginManager().callEvent(thunder);
/* 624 */     if (!thunder.isCancelled()) {
/* 625 */       this.world.worldData.setThundering(thundering);
/*     */ 
/*     */       
/* 628 */       if (thundering) {
/* 629 */         setThunderDuration(rand.nextInt(12000) + 3600);
/*     */       } else {
/* 631 */         setThunderDuration(rand.nextInt(168000) + 12000);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 637 */   public int getThunderDuration() { return this.world.worldData.getThunderDuration(); }
/*     */ 
/*     */ 
/*     */   
/* 641 */   public void setThunderDuration(int duration) { this.world.worldData.setThunderDuration(duration); }
/*     */ 
/*     */ 
/*     */   
/* 645 */   public long getSeed() { return this.world.worldData.getSeed(); }
/*     */ 
/*     */ 
/*     */   
/* 649 */   public boolean getPVP() { return this.world.pvpMode; }
/*     */ 
/*     */ 
/*     */   
/* 653 */   public void setPVP(boolean pvp) { this.world.pvpMode = pvp; }
/*     */ 
/*     */ 
/*     */   
/* 657 */   public void playEffect(Player player, Effect effect, int data) { playEffect(player.getLocation(), effect, data, 0); }
/*     */ 
/*     */ 
/*     */   
/* 661 */   public void playEffect(Location location, Effect effect, int data) { playEffect(location, effect, data, 64); }
/*     */ 
/*     */   
/*     */   public void playEffect(Location location, Effect effect, int data, int radius) {
/* 665 */     int packetData = effect.getId();
/* 666 */     Packet61 packet = new Packet61(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), data);
/*     */     
/* 668 */     for (Player player : getPlayers()) {
/* 669 */       int distance = (int)player.getLocation().distance(location);
/* 670 */       if (distance <= radius) {
/* 671 */         (((CraftPlayer)player).getHandle()).netServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
/* 678 */     if (location == null || clazz == null) {
/* 679 */       throw new IllegalArgumentException("Location or entity class cannot be null");
/*     */     }
/*     */     
/* 682 */     EntityFish entityFish = null;
/*     */     
/* 684 */     double x = location.getX();
/* 685 */     double y = location.getY();
/* 686 */     double z = location.getZ();
/* 687 */     float pitch = location.getPitch();
/* 688 */     float yaw = location.getYaw();
/*     */ 
/*     */     
/* 691 */     if (org.bukkit.entity.Boat.class.isAssignableFrom(clazz)) {
/* 692 */       EntityBoat entityBoat = new EntityBoat(this.world, x, y, z);
/* 693 */     } else if (org.bukkit.entity.Egg.class.isAssignableFrom(clazz)) {
/* 694 */       EntityEgg entityEgg = new EntityEgg(this.world, x, y, z);
/* 695 */     } else if (org.bukkit.entity.FallingSand.class.isAssignableFrom(clazz)) {
/* 696 */       EntityFallingSand entityFallingSand = new EntityFallingSand(this.world, x, y, z, false);
/* 697 */     } else if (org.bukkit.entity.Fireball.class.isAssignableFrom(clazz)) {
/* 698 */       EntityFireball entityFireball = new EntityFireball(this.world);
/* 699 */       ((EntityFireball)entityFireball).setPositionRotation(x, y, z, yaw, pitch);
/* 700 */       Vector direction = location.getDirection().multiply(10);
/* 701 */       ((EntityFireball)entityFireball).setDirection(direction.getX(), direction.getY(), direction.getZ());
/* 702 */     } else if (org.bukkit.entity.Snowball.class.isAssignableFrom(clazz)) {
/* 703 */       EntitySnowball entitySnowball = new EntitySnowball(this.world, x, y, z);
/* 704 */     } else if (org.bukkit.entity.Minecart.class.isAssignableFrom(clazz)) {
/*     */       
/* 706 */       if (org.bukkit.entity.PoweredMinecart.class.isAssignableFrom(clazz)) {
/* 707 */         EntityMinecart entityMinecart = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.PoweredMinecart.getId());
/* 708 */       } else if (org.bukkit.entity.StorageMinecart.class.isAssignableFrom(clazz)) {
/* 709 */         EntityMinecart entityMinecart = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.StorageMinecart.getId());
/*     */       } else {
/* 711 */         EntityMinecart entityMinecart = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.Minecart.getId());
/*     */       }
/*     */     
/* 714 */     } else if (Arrow.class.isAssignableFrom(clazz)) {
/* 715 */       EntityArrow entityArrow = new EntityArrow(this.world);
/* 716 */       entityArrow.setPositionRotation(x, y, z, 0.0F, 0.0F);
/* 717 */     } else if (LivingEntity.class.isAssignableFrom(clazz)) {
/*     */       EntityZombie entityZombie;
/* 719 */       if (org.bukkit.entity.Chicken.class.isAssignableFrom(clazz)) {
/* 720 */         entityZombie = new EntityChicken(this.world);
/* 721 */       } else if (org.bukkit.entity.Cow.class.isAssignableFrom(clazz)) {
/* 722 */         entityZombie = new EntityCow(this.world);
/* 723 */       } else if (org.bukkit.entity.Creeper.class.isAssignableFrom(clazz)) {
/* 724 */         entityZombie = new EntityCreeper(this.world);
/* 725 */       } else if (org.bukkit.entity.Ghast.class.isAssignableFrom(clazz)) {
/* 726 */         entityZombie = new EntityGhast(this.world);
/* 727 */       } else if (org.bukkit.entity.Pig.class.isAssignableFrom(clazz)) {
/* 728 */         entityZombie = new EntityPig(this.world);
/* 729 */       } else if (!Player.class.isAssignableFrom(clazz)) {
/*     */         
/* 731 */         if (org.bukkit.entity.Sheep.class.isAssignableFrom(clazz)) {
/* 732 */           entityZombie = new EntitySheep(this.world);
/* 733 */         } else if (org.bukkit.entity.Skeleton.class.isAssignableFrom(clazz)) {
/* 734 */           entityZombie = new EntitySkeleton(this.world);
/* 735 */         } else if (org.bukkit.entity.Slime.class.isAssignableFrom(clazz)) {
/* 736 */           entityZombie = new EntitySlime(this.world);
/* 737 */         } else if (org.bukkit.entity.Spider.class.isAssignableFrom(clazz)) {
/* 738 */           entityZombie = new EntitySpider(this.world);
/* 739 */         } else if (org.bukkit.entity.Squid.class.isAssignableFrom(clazz)) {
/* 740 */           entityZombie = new EntitySquid(this.world);
/* 741 */         } else if (org.bukkit.entity.Wolf.class.isAssignableFrom(clazz)) {
/* 742 */           entityZombie = new EntityWolf(this.world);
/* 743 */         } else if (org.bukkit.entity.PigZombie.class.isAssignableFrom(clazz)) {
/* 744 */           entityZombie = new EntityPigZombie(this.world);
/* 745 */         } else if (org.bukkit.entity.Zombie.class.isAssignableFrom(clazz)) {
/* 746 */           entityZombie = new EntityZombie(this.world);
/*     */         } 
/*     */       } 
/* 749 */       if (entityZombie != null) {
/* 750 */         entityZombie.setLocation(x, y, z, pitch, yaw);
/*     */       }
/*     */     }
/* 753 */     else if (!org.bukkit.entity.Painting.class.isAssignableFrom(clazz)) {
/*     */       
/* 755 */       if (org.bukkit.entity.TNTPrimed.class.isAssignableFrom(clazz)) {
/* 756 */         EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(this.world, x, y, z);
/* 757 */       } else if (org.bukkit.entity.Weather.class.isAssignableFrom(clazz)) {
/*     */         
/* 759 */         EntityWeatherStorm entityWeatherStorm = new EntityWeatherStorm(this.world, x, y, z);
/* 760 */       } else if (!LightningStrike.class.isAssignableFrom(clazz)) {
/*     */         
/* 762 */         if (org.bukkit.entity.Fish.class.isAssignableFrom(clazz)) {
/*     */           
/* 764 */           entityFish = new EntityFish(this.world);
/* 765 */           entityFish.setLocation(x, y, z, pitch, yaw);
/*     */         } 
/*     */       } 
/* 768 */     }  if (entityFish != null) {
/* 769 */       this.world.addEntity(entityFish);
/* 770 */       return (T)entityFish.getBukkitEntity();
/*     */     } 
/*     */     
/* 773 */     throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
/*     */   }
/*     */ 
/*     */   
/* 777 */   public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) { return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain); }
/*     */ 
/*     */ 
/*     */   
/* 781 */   public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) { this.world.setSpawnFlags(allowMonsters, allowAnimals); }
/*     */ 
/*     */ 
/*     */   
/* 785 */   public boolean getAllowAnimals() { return this.world.allowAnimals; }
/*     */ 
/*     */ 
/*     */   
/* 789 */   public boolean getAllowMonsters() { return this.world.allowMonsters; }
/*     */ 
/*     */ 
/*     */   
/* 793 */   public int getMaxHeight() { return 128; }
/*     */ 
/*     */ 
/*     */   
/* 797 */   public boolean getKeepSpawnInMemory() { return this.world.keepSpawnInMemory; }
/*     */ 
/*     */   
/*     */   public void setKeepSpawnInMemory(boolean keepLoaded) {
/* 801 */     this.world.keepSpawnInMemory = keepLoaded;
/*     */     
/* 803 */     ChunkCoordinates chunkcoordinates = this.world.getSpawn();
/* 804 */     int chunkCoordX = chunkcoordinates.x >> 4;
/* 805 */     int chunkCoordZ = chunkcoordinates.z >> 4;
/*     */     
/* 807 */     for (int x = -12; x <= 12; x++) {
/* 808 */       for (int z = -12; z <= 12; z++) {
/* 809 */         if (keepLoaded) {
/* 810 */           loadChunk(chunkCoordX + x, chunkCoordZ + z);
/*     */         }
/* 812 */         else if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
/* 813 */           if (getHandle().getChunkAt(chunkCoordX + x, chunkCoordZ + z).isEmpty()) {
/* 814 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
/*     */           } else {
/* 816 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\CraftWorld.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */