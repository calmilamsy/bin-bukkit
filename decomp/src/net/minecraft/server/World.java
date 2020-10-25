/*      */ package net.minecraft.server;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.CraftServer;
/*      */ import org.bukkit.craftbukkit.CraftWorld;
/*      */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*      */ import org.bukkit.event.block.BlockCanBuildEvent;
/*      */ import org.bukkit.event.block.BlockFormEvent;
/*      */ import org.bukkit.event.block.BlockPhysicsEvent;
/*      */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*      */ import org.bukkit.event.entity.ItemSpawnEvent;
/*      */ import org.bukkit.event.weather.ThunderChangeEvent;
/*      */ import org.bukkit.event.weather.WeatherChangeEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class World
/*      */   implements IBlockAccess
/*      */ {
/*      */   public boolean a;
/*      */   private List C;
/*      */   public List entityList;
/*      */   private List D;
/*      */   private TreeSet E;
/*      */   private Set F;
/*      */   public List c;
/*      */   private List G;
/*      */   public List players;
/*      */   public List e;
/*      */   private long H;
/*      */   public int f;
/*      */   protected int g;
/*      */   protected final int h = 1013904223;
/*      */   protected float i;
/*      */   protected float j;
/*      */   protected float k;
/*      */   protected float l;
/*      */   protected int m;
/*      */   public int n;
/*      */   public boolean suppressPhysics;
/*      */   private long I;
/*      */   protected int p;
/*      */   public int spawnMonsters;
/*      */   public Random random;
/*      */   public boolean s;
/*      */   public WorldProvider worldProvider;
/*      */   protected List u;
/*      */   public IChunkProvider chunkProvider;
/*      */   protected final IDataManager w;
/*      */   public WorldData worldData;
/*      */   public boolean isLoading;
/*      */   private boolean J;
/*      */   public WorldMapCollection worldMaps;
/*      */   private ArrayList K;
/*      */   private boolean L;
/*      */   private int M;
/*      */   public boolean allowMonsters;
/*      */   public boolean allowAnimals;
/*   73 */   static int A = 0; private Set P; private int Q; private List R; public boolean isStatic; private final CraftWorld world; public boolean pvpMode; public boolean keepSpawnInMemory; public ChunkGenerator generator;
/*      */   Chunk lastChunkAccessed;
/*      */   int lastXAccessed;
/*      */   int lastZAccessed;
/*      */   final Object chunkLock;
/*      */   private List<TileEntity> tileEntitiesToUnload;
/*      */   
/*   80 */   public WorldChunkManager getWorldChunkManager() { return this.worldProvider.b; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canSpawn(int x, int z) {
/*   95 */     if (this.generator != null) {
/*   96 */       return this.generator.canSpawn(getWorld(), x, z);
/*      */     }
/*   98 */     return this.worldProvider.canSpawn(x, z);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  103 */   public CraftWorld getWorld() { return this.world; }
/*      */ 
/*      */ 
/*      */   
/*  107 */   public CraftServer getServer() { return (CraftServer)Bukkit.getServer(); }
/*      */ 
/*      */ 
/*      */   
/*  111 */   public void markForRemoval(TileEntity tileentity) { this.tileEntitiesToUnload.add(tileentity); } public World(IDataManager idatamanager, String s, long i, WorldProvider worldprovider, ChunkGenerator gen, World.Environment env) { this.a = false; this.C = new ArrayList(); this.entityList = new ArrayList(); this.D = new ArrayList(); this.E = new TreeSet(); this.F = new HashSet(); this.c = new ArrayList(); this.G = new ArrayList(); this.players = new ArrayList(); this.e = new ArrayList(); this.H = 16777215L; this.f = 0; this.g = (new Random()).nextInt(); this.h = 1013904223; this.m = 0; this.n = 0; this.suppressPhysics = false; this.I = System.currentTimeMillis(); this.p = 40; this.random = new Random(); this.s = false; this.u = new ArrayList(); this.K = new ArrayList(); this.M = 0; this.allowMonsters = true; this.allowAnimals = true; this.P = new HashSet();
/*      */     this.keepSpawnInMemory = true;
/*      */     this.lastXAccessed = Integer.MIN_VALUE;
/*      */     this.lastZAccessed = Integer.MIN_VALUE;
/*      */     this.chunkLock = new Object();
/*  116 */     this.generator = gen;
/*  117 */     this.world = new CraftWorld((WorldServer)this, gen, env);
/*  118 */     this.tileEntitiesToUnload = new ArrayList();
/*      */ 
/*      */     
/*  121 */     this.Q = this.random.nextInt(12000);
/*  122 */     this.R = new ArrayList();
/*  123 */     this.isStatic = false;
/*  124 */     this.w = idatamanager;
/*  125 */     this.worldMaps = new WorldMapCollection(idatamanager);
/*  126 */     this.worldData = idatamanager.c();
/*  127 */     this.s = (this.worldData == null);
/*  128 */     if (worldprovider != null) {
/*  129 */       this.worldProvider = worldprovider;
/*  130 */     } else if (this.worldData != null && this.worldData.h() == -1) {
/*  131 */       this.worldProvider = WorldProvider.byDimension(-1);
/*      */     } else {
/*  133 */       this.worldProvider = WorldProvider.byDimension(0);
/*      */     } 
/*      */     
/*  136 */     boolean flag = false;
/*      */     
/*  138 */     if (this.worldData == null) {
/*  139 */       this.worldData = new WorldData(i, s);
/*  140 */       flag = true;
/*      */     } else {
/*  142 */       this.worldData.a(s);
/*      */     } 
/*      */     
/*  145 */     this.worldProvider.a(this);
/*  146 */     this.chunkProvider = b();
/*  147 */     if (flag) {
/*  148 */       c();
/*      */     }
/*      */     
/*  151 */     g();
/*  152 */     x();
/*      */     
/*  154 */     getServer().addWorld(this.world); }
/*      */ 
/*      */   
/*      */   protected IChunkProvider b() {
/*  158 */     IChunkLoader ichunkloader = this.w.a(this.worldProvider);
/*      */     
/*  160 */     return new ChunkProviderLoadOrGenerate(this, ichunkloader, this.worldProvider.getChunkProvider());
/*      */   }
/*      */   
/*      */   protected void c() {
/*  164 */     this.isLoading = true;
/*  165 */     int i = 0;
/*  166 */     byte b0 = 64;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  171 */     if (this.generator != null) {
/*  172 */       Random rand = new Random(getSeed());
/*  173 */       Location spawn = this.generator.getFixedSpawnLocation(((WorldServer)this).getWorld(), rand);
/*      */       
/*  175 */       if (spawn != null) {
/*  176 */         if (spawn.getWorld() != ((WorldServer)this).getWorld()) {
/*  177 */           throw new IllegalStateException("Cannot set spawn point for " + this.worldData.name + " to be in another world (" + spawn.getWorld().getName() + ")");
/*      */         }
/*  179 */         this.worldData.setSpawn(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
/*  180 */         this.isLoading = false;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     int j;
/*  186 */     for (j = 0; !canSpawn(i, j); j += this.random.nextInt(64) - this.random.nextInt(64)) {
/*  187 */       i += this.random.nextInt(64) - this.random.nextInt(64);
/*      */     }
/*      */ 
/*      */     
/*  191 */     this.worldData.setSpawn(i, b0, j);
/*  192 */     this.isLoading = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int a(int i, int j) {
/*      */     int k;
/*  198 */     for (k = 63; !isEmpty(i, k + 1, j); k++);
/*      */ 
/*      */ 
/*      */     
/*  202 */     return getTypeId(i, k, j);
/*      */   }
/*      */   
/*      */   public void save(boolean flag, IProgressUpdate iprogressupdate) {
/*  206 */     if (this.chunkProvider.canSave()) {
/*  207 */       if (iprogressupdate != null) {
/*  208 */         iprogressupdate.a("Saving level");
/*      */       }
/*      */       
/*  211 */       w();
/*  212 */       if (iprogressupdate != null) {
/*  213 */         iprogressupdate.b("Saving chunks");
/*      */       }
/*      */       
/*  216 */       this.chunkProvider.saveChunks(flag, iprogressupdate);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void w() {
/*  221 */     k();
/*  222 */     this.w.a(this.worldData, this.players);
/*  223 */     this.worldMaps.a();
/*      */   }
/*      */ 
/*      */   
/*  227 */   public int getTypeId(int i, int j, int k) { return (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) ? ((j < 0) ? 0 : ((j >= 128) ? 0 : getChunkAt(i >> 4, k >> 4).getTypeId(i & 0xF, j, k & 0xF))) : 0; }
/*      */ 
/*      */ 
/*      */   
/*  231 */   public boolean isEmpty(int i, int j, int k) { return (getTypeId(i, j, k) == 0); }
/*      */ 
/*      */ 
/*      */   
/*  235 */   public boolean isLoaded(int i, int j, int k) { return (j >= 0 && j < 128) ? isChunkLoaded(i >> 4, k >> 4) : 0; }
/*      */ 
/*      */ 
/*      */   
/*  239 */   public boolean areChunksLoaded(int i, int j, int k, int l) { return a(i - l, j - l, k - l, i + l, j + l, k + l); }
/*      */ 
/*      */   
/*      */   public boolean a(int i, int j, int k, int l, int i1, int j1) {
/*  243 */     if (i1 >= 0 && j < 128) {
/*  244 */       i >>= 4;
/*  245 */       j >>= 4;
/*  246 */       k >>= 4;
/*  247 */       l >>= 4;
/*  248 */       i1 >>= 4;
/*  249 */       j1 >>= 4;
/*      */       
/*  251 */       for (int k1 = i; k1 <= l; k1++) {
/*  252 */         for (int l1 = k; l1 <= j1; l1++) {
/*  253 */           if (!isChunkLoaded(k1, l1)) {
/*  254 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  259 */       return true;
/*      */     } 
/*  261 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  266 */   private boolean isChunkLoaded(int i, int j) { return this.chunkProvider.isChunkLoaded(i, j); }
/*      */ 
/*      */ 
/*      */   
/*  270 */   public Chunk getChunkAtWorldCoords(int i, int j) { return getChunkAt(i >> 4, j >> 4); }
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkAt(int i, int j) {
/*  275 */     Chunk result = null;
/*  276 */     synchronized (this.chunkLock) {
/*  277 */       if (this.lastChunkAccessed == null || this.lastXAccessed != i || this.lastZAccessed != j) {
/*  278 */         this.lastXAccessed = i;
/*  279 */         this.lastZAccessed = j;
/*  280 */         this.lastChunkAccessed = this.chunkProvider.getOrCreateChunk(i, j);
/*      */       } 
/*  282 */       result = this.lastChunkAccessed;
/*      */     } 
/*  284 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setRawTypeIdAndData(int i, int j, int k, int l, int i1) {
/*  289 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  290 */       if (j < 0)
/*  291 */         return false; 
/*  292 */       if (j >= 128) {
/*  293 */         return false;
/*      */       }
/*  295 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  297 */       return chunk.a(i & 0xF, j, k & 0xF, l, i1);
/*      */     } 
/*      */     
/*  300 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setRawTypeId(int i, int j, int k, int l) {
/*  305 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  306 */       if (j < 0)
/*  307 */         return false; 
/*  308 */       if (j >= 128) {
/*  309 */         return false;
/*      */       }
/*  311 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  313 */       return chunk.a(i & 0xF, j, k & 0xF, l);
/*      */     } 
/*      */     
/*  316 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Material getMaterial(int i, int j, int k) {
/*  321 */     int l = getTypeId(i, j, k);
/*      */     
/*  323 */     return (l == 0) ? Material.AIR : (Block.byId[l]).material;
/*      */   }
/*      */   
/*      */   public int getData(int i, int j, int k) {
/*  327 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  328 */       if (j < 0)
/*  329 */         return 0; 
/*  330 */       if (j >= 128) {
/*  331 */         return 0;
/*      */       }
/*  333 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  335 */       i &= 0xF;
/*  336 */       k &= 0xF;
/*  337 */       return chunk.getData(i, j, k);
/*      */     } 
/*      */     
/*  340 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setData(int i, int j, int k, int l) {
/*  345 */     if (setRawData(i, j, k, l)) {
/*  346 */       int i1 = getTypeId(i, j, k);
/*      */       
/*  348 */       if (Block.t[i1 & 0xFF]) {
/*  349 */         update(i, j, k, i1);
/*      */       } else {
/*  351 */         applyPhysics(i, j, k, i1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean setRawData(int i, int j, int k, int l) {
/*  357 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  358 */       if (j < 0)
/*  359 */         return false; 
/*  360 */       if (j >= 128) {
/*  361 */         return false;
/*      */       }
/*  363 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  365 */       i &= 0xF;
/*  366 */       k &= 0xF;
/*  367 */       chunk.b(i, j, k, l);
/*  368 */       return true;
/*      */     } 
/*      */     
/*  371 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setTypeId(int i, int j, int k, int l) {
/*  377 */     int old = getTypeId(i, j, k);
/*  378 */     if (setRawTypeId(i, j, k, l)) {
/*  379 */       update(i, j, k, (l == 0) ? old : l);
/*  380 */       return true;
/*      */     } 
/*  382 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setTypeIdAndData(int i, int j, int k, int l, int i1) {
/*  389 */     int old = getTypeId(i, j, k);
/*  390 */     if (setRawTypeIdAndData(i, j, k, l, i1)) {
/*  391 */       update(i, j, k, (l == 0) ? old : l);
/*  392 */       return true;
/*      */     } 
/*  394 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void notify(int i, int j, int k) {
/*  400 */     for (int l = 0; l < this.u.size(); l++) {
/*  401 */       ((IWorldAccess)this.u.get(l)).a(i, j, k);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void update(int i, int j, int k, int l) {
/*  406 */     notify(i, j, k);
/*  407 */     applyPhysics(i, j, k, l);
/*      */   }
/*      */   
/*      */   public void g(int i, int j, int k, int l) {
/*  411 */     if (k > l) {
/*  412 */       int i1 = l;
/*      */       
/*  414 */       l = k;
/*  415 */       k = i1;
/*      */     } 
/*      */     
/*  418 */     b(i, k, j, i, l, j);
/*      */   }
/*      */   
/*      */   public void i(int i, int j, int k) {
/*  422 */     for (int l = 0; l < this.u.size(); l++) {
/*  423 */       ((IWorldAccess)this.u.get(l)).a(i, j, k, i, j, k);
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, int l, int i1, int j1) {
/*  428 */     for (int k1 = 0; k1 < this.u.size(); k1++) {
/*  429 */       ((IWorldAccess)this.u.get(k1)).a(i, j, k, l, i1, j1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyPhysics(int i, int j, int k, int l) {
/*  434 */     k(i - 1, j, k, l);
/*  435 */     k(i + 1, j, k, l);
/*  436 */     k(i, j - 1, k, l);
/*  437 */     k(i, j + 1, k, l);
/*  438 */     k(i, j, k - 1, l);
/*  439 */     k(i, j, k + 1, l);
/*      */   }
/*      */   
/*      */   private void k(int i, int j, int k, int l) {
/*  443 */     if (!this.suppressPhysics && !this.isStatic) {
/*  444 */       Block block = Block.byId[getTypeId(i, j, k)];
/*      */       
/*  446 */       if (block != null) {
/*      */         
/*  448 */         CraftWorld world = ((WorldServer)this).getWorld();
/*  449 */         if (world != null) {
/*  450 */           BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(i, j, k), l);
/*  451 */           getServer().getPluginManager().callEvent(event);
/*      */           
/*  453 */           if (event.isCancelled()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  459 */         block.doPhysics(this, i, j, k, l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  465 */   public boolean isChunkLoaded(int i, int j, int k) { return getChunkAt(i >> 4, k >> 4).c(i & 0xF, j, k & 0xF); }
/*      */ 
/*      */   
/*      */   public int k(int i, int j, int k) {
/*  469 */     if (j < 0) {
/*  470 */       return 0;
/*      */     }
/*  472 */     if (j >= 128) {
/*  473 */       j = 127;
/*      */     }
/*      */     
/*  476 */     return getChunkAt(i >> 4, k >> 4).c(i & 0xF, j, k & 0xF, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  481 */   public int getLightLevel(int i, int j, int k) { return a(i, j, k, true); }
/*      */ 
/*      */   
/*      */   public int a(int i, int j, int k, boolean flag) {
/*  485 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  486 */       if (flag) {
/*  487 */         int l = getTypeId(i, j, k);
/*      */         
/*  489 */         if (l == Block.STEP.id || l == Block.SOIL.id || l == Block.COBBLESTONE_STAIRS.id || l == Block.WOOD_STAIRS.id) {
/*  490 */           int i1 = a(i, j + 1, k, false);
/*  491 */           int j1 = a(i + 1, j, k, false);
/*  492 */           int k1 = a(i - 1, j, k, false);
/*  493 */           int l1 = a(i, j, k + 1, false);
/*  494 */           int i2 = a(i, j, k - 1, false);
/*      */           
/*  496 */           if (j1 > i1) {
/*  497 */             i1 = j1;
/*      */           }
/*      */           
/*  500 */           if (k1 > i1) {
/*  501 */             i1 = k1;
/*      */           }
/*      */           
/*  504 */           if (l1 > i1) {
/*  505 */             i1 = l1;
/*      */           }
/*      */           
/*  508 */           if (i2 > i1) {
/*  509 */             i1 = i2;
/*      */           }
/*      */           
/*  512 */           return i1;
/*      */         } 
/*      */       } 
/*      */       
/*  516 */       if (j < 0) {
/*  517 */         return 0;
/*      */       }
/*  519 */       if (j >= 128) {
/*  520 */         j = 127;
/*      */       }
/*      */       
/*  523 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  525 */       i &= 0xF;
/*  526 */       k &= 0xF;
/*  527 */       return chunk.c(i, j, k, this.f);
/*      */     } 
/*      */     
/*  530 */     return 15;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean m(int i, int j, int k) {
/*  535 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  536 */       if (j < 0)
/*  537 */         return false; 
/*  538 */       if (j >= 128)
/*  539 */         return true; 
/*  540 */       if (!isChunkLoaded(i >> 4, k >> 4)) {
/*  541 */         return false;
/*      */       }
/*  543 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  545 */       i &= 0xF;
/*  546 */       k &= 0xF;
/*  547 */       return chunk.c(i, j, k);
/*      */     } 
/*      */     
/*  550 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHighestBlockYAt(int i, int j) {
/*  555 */     if (i >= -32000000 && j >= -32000000 && i < 32000000 && j <= 32000000) {
/*  556 */       if (!isChunkLoaded(i >> 4, j >> 4)) {
/*  557 */         return 0;
/*      */       }
/*  559 */       Chunk chunk = getChunkAt(i >> 4, j >> 4);
/*      */       
/*  561 */       return chunk.b(i & 0xF, j & 0xF);
/*      */     } 
/*      */     
/*  564 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
/*  569 */     if ((!this.worldProvider.e || enumskyblock != EnumSkyBlock.SKY) && 
/*  570 */       isLoaded(i, j, k)) {
/*  571 */       if (enumskyblock == EnumSkyBlock.SKY) {
/*  572 */         if (m(i, j, k)) {
/*  573 */           l = 15;
/*      */         }
/*  575 */       } else if (enumskyblock == EnumSkyBlock.BLOCK) {
/*  576 */         int i1 = getTypeId(i, j, k);
/*      */         
/*  578 */         if (Block.s[i1] > l) {
/*  579 */           l = Block.s[i1];
/*      */         }
/*      */       } 
/*      */       
/*  583 */       if (a(enumskyblock, i, j, k) != l) {
/*  584 */         a(enumskyblock, i, j, k, i, j, k);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int a(EnumSkyBlock enumskyblock, int i, int j, int k) {
/*  591 */     if (j < 0) {
/*  592 */       j = 0;
/*      */     }
/*      */     
/*  595 */     if (j >= 128) {
/*  596 */       j = 127;
/*      */     }
/*      */     
/*  599 */     if (j >= 0 && j < 128 && i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000) {
/*  600 */       int l = i >> 4;
/*  601 */       int i1 = k >> 4;
/*      */       
/*  603 */       if (!isChunkLoaded(l, i1)) {
/*  604 */         return 0;
/*      */       }
/*  606 */       Chunk chunk = getChunkAt(l, i1);
/*      */       
/*  608 */       return chunk.a(enumskyblock, i & 0xF, j, k & 0xF);
/*      */     } 
/*      */     
/*  611 */     return enumskyblock.c;
/*      */   }
/*      */ 
/*      */   
/*      */   public void b(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
/*  616 */     if (i >= -32000000 && k >= -32000000 && i < 32000000 && k <= 32000000 && 
/*  617 */       j >= 0 && 
/*  618 */       j < 128 && 
/*  619 */       isChunkLoaded(i >> 4, k >> 4)) {
/*  620 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/*  622 */       chunk.a(enumskyblock, i & 0xF, j, k & 0xF, l);
/*      */       
/*  624 */       for (int i1 = 0; i1 < this.u.size(); i1++) {
/*  625 */         ((IWorldAccess)this.u.get(i1)).a(i, j, k);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  634 */   public float n(int i, int j, int k) { return this.worldProvider.f[getLightLevel(i, j, k)]; }
/*      */ 
/*      */ 
/*      */   
/*  638 */   public boolean d() { return (this.f < 4); }
/*      */ 
/*      */ 
/*      */   
/*  642 */   public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1) { return rayTrace(vec3d, vec3d1, false, false); }
/*      */ 
/*      */ 
/*      */   
/*  646 */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag) { return rayTrace(vec3d, vec3d1, flag, false); }
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag, boolean flag1) {
/*  650 */     if (!Double.isNaN(vec3d.a) && !Double.isNaN(vec3d.b) && !Double.isNaN(vec3d.c)) {
/*  651 */       if (!Double.isNaN(vec3d1.a) && !Double.isNaN(vec3d1.b) && !Double.isNaN(vec3d1.c)) {
/*  652 */         int i = MathHelper.floor(vec3d1.a);
/*  653 */         int j = MathHelper.floor(vec3d1.b);
/*  654 */         int k = MathHelper.floor(vec3d1.c);
/*  655 */         int l = MathHelper.floor(vec3d.a);
/*  656 */         int i1 = MathHelper.floor(vec3d.b);
/*  657 */         int j1 = MathHelper.floor(vec3d.c);
/*  658 */         int k1 = getTypeId(l, i1, j1);
/*  659 */         int l1 = getData(l, i1, j1);
/*  660 */         Block block = Block.byId[k1];
/*      */         
/*  662 */         if ((!flag1 || block == null || block.e(this, l, i1, j1) != null) && k1 > 0 && block.a(l1, flag)) {
/*  663 */           MovingObjectPosition movingobjectposition = block.a(this, l, i1, j1, vec3d, vec3d1);
/*      */           
/*  665 */           if (movingobjectposition != null) {
/*  666 */             return movingobjectposition;
/*      */           }
/*      */         } 
/*      */         
/*  670 */         k1 = 200;
/*      */         
/*  672 */         while (k1-- >= 0) {
/*  673 */           byte b0; if (Double.isNaN(vec3d.a) || Double.isNaN(vec3d.b) || Double.isNaN(vec3d.c)) {
/*  674 */             return null;
/*      */           }
/*      */           
/*  677 */           if (l == i && i1 == j && j1 == k) {
/*  678 */             return null;
/*      */           }
/*      */           
/*  681 */           boolean flag2 = true;
/*  682 */           boolean flag3 = true;
/*  683 */           boolean flag4 = true;
/*  684 */           double d0 = 999.0D;
/*  685 */           double d1 = 999.0D;
/*  686 */           double d2 = 999.0D;
/*      */           
/*  688 */           if (i > l) {
/*  689 */             d0 = l + 1.0D;
/*  690 */           } else if (i < l) {
/*  691 */             d0 = l + 0.0D;
/*      */           } else {
/*  693 */             flag2 = false;
/*      */           } 
/*      */           
/*  696 */           if (j > i1) {
/*  697 */             d1 = i1 + 1.0D;
/*  698 */           } else if (j < i1) {
/*  699 */             d1 = i1 + 0.0D;
/*      */           } else {
/*  701 */             flag3 = false;
/*      */           } 
/*      */           
/*  704 */           if (k > j1) {
/*  705 */             d2 = j1 + 1.0D;
/*  706 */           } else if (k < j1) {
/*  707 */             d2 = j1 + 0.0D;
/*      */           } else {
/*  709 */             flag4 = false;
/*      */           } 
/*      */           
/*  712 */           double d3 = 999.0D;
/*  713 */           double d4 = 999.0D;
/*  714 */           double d5 = 999.0D;
/*  715 */           double d6 = vec3d1.a - vec3d.a;
/*  716 */           double d7 = vec3d1.b - vec3d.b;
/*  717 */           double d8 = vec3d1.c - vec3d.c;
/*      */           
/*  719 */           if (flag2) {
/*  720 */             d3 = (d0 - vec3d.a) / d6;
/*      */           }
/*      */           
/*  723 */           if (flag3) {
/*  724 */             d4 = (d1 - vec3d.b) / d7;
/*      */           }
/*      */           
/*  727 */           if (flag4) {
/*  728 */             d5 = (d2 - vec3d.c) / d8;
/*      */           }
/*      */           
/*  731 */           boolean flag5 = false;
/*      */ 
/*      */           
/*  734 */           if (d3 < d4 && d3 < d5) {
/*  735 */             if (i > l) {
/*  736 */               b0 = 4;
/*      */             } else {
/*  738 */               b0 = 5;
/*      */             } 
/*      */             
/*  741 */             vec3d.a = d0;
/*  742 */             vec3d.b += d7 * d3;
/*  743 */             vec3d.c += d8 * d3;
/*  744 */           } else if (d4 < d5) {
/*  745 */             if (j > i1) {
/*  746 */               b0 = 0;
/*      */             } else {
/*  748 */               b0 = 1;
/*      */             } 
/*      */             
/*  751 */             vec3d.a += d6 * d4;
/*  752 */             vec3d.b = d1;
/*  753 */             vec3d.c += d8 * d4;
/*      */           } else {
/*  755 */             if (k > j1) {
/*  756 */               b0 = 2;
/*      */             } else {
/*  758 */               b0 = 3;
/*      */             } 
/*      */             
/*  761 */             vec3d.a += d6 * d5;
/*  762 */             vec3d.b += d7 * d5;
/*  763 */             vec3d.c = d2;
/*      */           } 
/*      */           
/*  766 */           Vec3D vec3d2 = Vec3D.create(vec3d.a, vec3d.b, vec3d.c);
/*      */           
/*  768 */           l = (int)(vec3d2.a = MathHelper.floor(vec3d.a));
/*  769 */           if (b0 == 5) {
/*  770 */             l--;
/*  771 */             vec3d2.a++;
/*      */           } 
/*      */           
/*  774 */           i1 = (int)(vec3d2.b = MathHelper.floor(vec3d.b));
/*  775 */           if (b0 == 1) {
/*  776 */             i1--;
/*  777 */             vec3d2.b++;
/*      */           } 
/*      */           
/*  780 */           j1 = (int)(vec3d2.c = MathHelper.floor(vec3d.c));
/*  781 */           if (b0 == 3) {
/*  782 */             j1--;
/*  783 */             vec3d2.c++;
/*      */           } 
/*      */           
/*  786 */           int i2 = getTypeId(l, i1, j1);
/*  787 */           int j2 = getData(l, i1, j1);
/*  788 */           Block block1 = Block.byId[i2];
/*      */           
/*  790 */           if ((!flag1 || block1 == null || block1.e(this, l, i1, j1) != null) && i2 > 0 && block1.a(j2, flag)) {
/*  791 */             MovingObjectPosition movingobjectposition1 = block1.a(this, l, i1, j1, vec3d, vec3d1);
/*      */             
/*  793 */             if (movingobjectposition1 != null) {
/*  794 */               return movingobjectposition1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  799 */         return null;
/*      */       } 
/*  801 */       return null;
/*      */     } 
/*      */     
/*  804 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeSound(Entity entity, String s, float f, float f1) {
/*  809 */     for (int i = 0; i < this.u.size(); i++) {
/*  810 */       ((IWorldAccess)this.u.get(i)).a(s, entity.locX, entity.locY - entity.height, entity.locZ, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void makeSound(double d0, double d1, double d2, String s, float f, float f1) {
/*  815 */     for (int i = 0; i < this.u.size(); i++) {
/*  816 */       ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(String s, int i, int j, int k) {
/*  821 */     for (int l = 0; l < this.u.size(); l++) {
/*  822 */       ((IWorldAccess)this.u.get(l)).a(s, i, j, k);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(String s, double d0, double d1, double d2, double d3, double d4, double d5) {
/*  827 */     for (int i = 0; i < this.u.size(); i++) {
/*  828 */       ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, d3, d4, d5);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean strikeLightning(Entity entity) {
/*  833 */     this.e.add(entity);
/*  834 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  839 */   public boolean addEntity(Entity entity) { return addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
/*  845 */     int i = MathHelper.floor(entity.locX / 16.0D);
/*  846 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/*  847 */     boolean flag = false;
/*      */     
/*  849 */     if (entity instanceof EntityHuman) {
/*  850 */       flag = true;
/*      */     }
/*      */ 
/*      */     
/*  854 */     if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
/*  855 */       CreatureSpawnEvent event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
/*      */       
/*  857 */       if (event.isCancelled()) {
/*  858 */         return false;
/*      */       }
/*  860 */     } else if (entity instanceof EntityItem) {
/*  861 */       ItemSpawnEvent event = CraftEventFactory.callItemSpawnEvent((EntityItem)entity);
/*  862 */       if (event.isCancelled()) {
/*  863 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  868 */     if (!flag && !isChunkLoaded(i, j)) {
/*  869 */       return false;
/*      */     }
/*  871 */     if (entity instanceof EntityHuman) {
/*  872 */       EntityHuman entityhuman = (EntityHuman)entity;
/*      */       
/*  874 */       this.players.add(entityhuman);
/*  875 */       everyoneSleeping();
/*      */     } 
/*      */     
/*  878 */     getChunkAt(i, j).a(entity);
/*  879 */     this.entityList.add(entity);
/*  880 */     c(entity);
/*  881 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void c(Entity entity) {
/*  886 */     for (int i = 0; i < this.u.size(); i++) {
/*  887 */       ((IWorldAccess)this.u.get(i)).a(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void d(Entity entity) {
/*  892 */     for (int i = 0; i < this.u.size(); i++) {
/*  893 */       ((IWorldAccess)this.u.get(i)).b(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void kill(Entity entity) {
/*  898 */     if (entity.passenger != null) {
/*  899 */       entity.passenger.mount((Entity)null);
/*      */     }
/*      */     
/*  902 */     if (entity.vehicle != null) {
/*  903 */       entity.mount((Entity)null);
/*      */     }
/*      */     
/*  906 */     entity.die();
/*  907 */     if (entity instanceof EntityHuman) {
/*  908 */       this.players.remove((EntityHuman)entity);
/*  909 */       everyoneSleeping();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeEntity(Entity entity) {
/*  914 */     entity.die();
/*  915 */     if (entity instanceof EntityHuman) {
/*  916 */       this.players.remove((EntityHuman)entity);
/*  917 */       everyoneSleeping();
/*      */     } 
/*      */     
/*  920 */     int i = entity.bH;
/*  921 */     int j = entity.bJ;
/*      */     
/*  923 */     if (entity.bG && isChunkLoaded(i, j)) {
/*  924 */       getChunkAt(i, j).b(entity);
/*      */     }
/*      */     
/*  927 */     this.entityList.remove(entity);
/*  928 */     d(entity);
/*      */   }
/*      */ 
/*      */   
/*  932 */   public void addIWorldAccess(IWorldAccess iworldaccess) { this.u.add(iworldaccess); }
/*      */ 
/*      */   
/*      */   public List getEntities(Entity entity, AxisAlignedBB axisalignedbb) {
/*  936 */     this.K.clear();
/*  937 */     int i = MathHelper.floor(axisalignedbb.a);
/*  938 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/*  939 */     int k = MathHelper.floor(axisalignedbb.b);
/*  940 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/*  941 */     int i1 = MathHelper.floor(axisalignedbb.c);
/*  942 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/*  944 */     for (int k1 = i; k1 < j; k1++) {
/*  945 */       for (int l1 = i1; l1 < j1; l1++) {
/*  946 */         if (isLoaded(k1, 64, l1)) {
/*  947 */           for (int i2 = k - 1; i2 < l; i2++) {
/*  948 */             Block block = Block.byId[getTypeId(k1, i2, l1)];
/*      */             
/*  950 */             if (block != null) {
/*  951 */               block.a(this, k1, i2, l1, axisalignedbb, this.K);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  958 */     double d0 = 0.25D;
/*  959 */     List list = b(entity, axisalignedbb.b(d0, d0, d0));
/*      */     
/*  961 */     for (int j2 = 0; j2 < list.size(); j2++) {
/*  962 */       AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).e_();
/*      */       
/*  964 */       if (axisalignedbb1 != null && axisalignedbb1.a(axisalignedbb)) {
/*  965 */         this.K.add(axisalignedbb1);
/*      */       }
/*      */       
/*  968 */       axisalignedbb1 = entity.a_((Entity)list.get(j2));
/*  969 */       if (axisalignedbb1 != null && axisalignedbb1.a(axisalignedbb)) {
/*  970 */         this.K.add(axisalignedbb1);
/*      */       }
/*      */     } 
/*      */     
/*  974 */     return this.K;
/*      */   }
/*      */   
/*      */   public int a(float f) {
/*  978 */     float f1 = b(f);
/*  979 */     float f2 = 1.0F - MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/*      */     
/*  981 */     if (f2 < 0.0F) {
/*  982 */       f2 = 0.0F;
/*      */     }
/*      */     
/*  985 */     if (f2 > 1.0F) {
/*  986 */       f2 = 1.0F;
/*      */     }
/*      */     
/*  989 */     f2 = 1.0F - f2;
/*  990 */     f2 = (float)(f2 * (1.0D - (d(f) * 5.0F) / 16.0D));
/*  991 */     f2 = (float)(f2 * (1.0D - (c(f) * 5.0F) / 16.0D));
/*  992 */     f2 = 1.0F - f2;
/*  993 */     return (int)(f2 * 11.0F);
/*      */   }
/*      */ 
/*      */   
/*  997 */   public float b(float f) { return this.worldProvider.a(this.worldData.f(), f); }
/*      */ 
/*      */   
/*      */   public int e(int i, int j) {
/* 1001 */     Chunk chunk = getChunkAtWorldCoords(i, j);
/* 1002 */     int k = 127;
/*      */     
/* 1004 */     i &= 0xF;
/*      */     
/* 1006 */     for (j &= 0xF; k > 0; k--) {
/* 1007 */       int l = chunk.getTypeId(i, k, j);
/* 1008 */       Material material = (l == 0) ? Material.AIR : (Block.byId[l]).material;
/*      */       
/* 1010 */       if (material.isSolid() || material.isLiquid()) {
/* 1011 */         return k + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     return -1;
/*      */   }
/*      */   
/*      */   public int f(int i, int j) {
/* 1019 */     Chunk chunk = getChunkAtWorldCoords(i, j);
/* 1020 */     int k = 127;
/*      */     
/* 1022 */     i &= 0xF;
/*      */     
/* 1024 */     for (j &= 0xF; k > 0; k--) {
/* 1025 */       int l = chunk.getTypeId(i, k, j);
/*      */       
/* 1027 */       if (l != 0 && (Block.byId[l]).material.isSolid()) {
/* 1028 */         return k + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1032 */     return -1;
/*      */   }
/*      */   
/*      */   public void c(int i, int j, int k, int l, int i1) {
/* 1036 */     NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, l);
/* 1037 */     byte b0 = 8;
/*      */     
/* 1039 */     if (this.a) {
/* 1040 */       if (a(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
/* 1041 */         int j1 = getTypeId(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
/*      */         
/* 1043 */         if (j1 == nextticklistentry.d && j1 > 0) {
/* 1044 */           Block.byId[j1].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
/*      */         }
/*      */       }
/*      */     
/* 1048 */     } else if (a(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
/* 1049 */       if (l > 0) {
/* 1050 */         nextticklistentry.a(i1 + this.worldData.f());
/*      */       }
/*      */       
/* 1053 */       if (!this.F.contains(nextticklistentry)) {
/* 1054 */         this.F.add(nextticklistentry);
/* 1055 */         this.E.add(nextticklistentry);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanUp() {
/*      */     int i;
/* 1065 */     for (i = 0; i < this.e.size(); i++) {
/* 1066 */       Entity entity = (Entity)this.e.get(i);
/*      */       
/* 1068 */       if (entity != null) {
/*      */ 
/*      */ 
/*      */         
/* 1072 */         entity.m_();
/* 1073 */         if (entity.dead) {
/* 1074 */           this.e.remove(i--);
/*      */         }
/*      */       } 
/*      */     } 
/* 1078 */     this.entityList.removeAll(this.D);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     for (i = 0; i < this.D.size(); i++) {
/* 1084 */       Entity entity = (Entity)this.D.get(i);
/* 1085 */       int j = entity.bH;
/* 1086 */       int k = entity.bJ;
/* 1087 */       if (entity.bG && isChunkLoaded(j, k)) {
/* 1088 */         getChunkAt(j, k).b(entity);
/*      */       }
/*      */     } 
/*      */     
/* 1092 */     for (i = 0; i < this.D.size(); i++) {
/* 1093 */       d((Entity)this.D.get(i));
/*      */     }
/*      */     
/* 1096 */     this.D.clear();
/*      */     
/* 1098 */     for (i = 0; i < this.entityList.size(); i++) {
/* 1099 */       Entity entity = (Entity)this.entityList.get(i);
/* 1100 */       if (entity.vehicle != null) {
/* 1101 */         if (!entity.vehicle.dead && entity.vehicle.passenger == entity) {
/*      */           continue;
/*      */         }
/*      */         
/* 1105 */         entity.vehicle.passenger = null;
/* 1106 */         entity.vehicle = null;
/*      */       } 
/*      */       
/* 1109 */       if (!entity.dead) {
/* 1110 */         playerJoinedWorld(entity);
/*      */       }
/*      */       
/* 1113 */       if (entity.dead) {
/* 1114 */         int j = entity.bH;
/* 1115 */         int k = entity.bJ;
/* 1116 */         if (entity.bG && isChunkLoaded(j, k)) {
/* 1117 */           getChunkAt(j, k).b(entity);
/*      */         }
/*      */         
/* 1120 */         this.entityList.remove(i--);
/* 1121 */         d(entity);
/*      */       } 
/*      */     } 
/*      */     
/* 1125 */     this.L = true;
/* 1126 */     Iterator iterator = this.c.iterator();
/*      */     
/* 1128 */     while (iterator.hasNext()) {
/* 1129 */       TileEntity tileentity = (TileEntity)iterator.next();
/*      */       
/* 1131 */       if (!tileentity.g()) {
/* 1132 */         tileentity.g_();
/*      */       }
/*      */       
/* 1135 */       if (tileentity.g()) {
/* 1136 */         iterator.remove();
/* 1137 */         Chunk chunk = getChunkAt(tileentity.x >> 4, tileentity.z >> 4);
/*      */         
/* 1139 */         if (chunk != null) {
/* 1140 */           chunk.e(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1145 */     this.L = false;
/*      */ 
/*      */     
/* 1148 */     if (!this.tileEntitiesToUnload.isEmpty()) {
/* 1149 */       this.c.removeAll(this.tileEntitiesToUnload);
/* 1150 */       this.tileEntitiesToUnload.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1154 */     if (!this.G.isEmpty()) {
/* 1155 */       Iterator iterator1 = this.G.iterator();
/*      */       
/* 1157 */       while (iterator1.hasNext()) {
/* 1158 */         TileEntity tileentity1 = (TileEntity)iterator1.next();
/*      */         
/* 1160 */         if (!tileentity1.g()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1166 */           Chunk chunk1 = getChunkAt(tileentity1.x >> 4, tileentity1.z >> 4);
/*      */           
/* 1168 */           if (chunk1 != null) {
/* 1169 */             chunk1.a(tileentity1.x & 0xF, tileentity1.y, tileentity1.z & 0xF, tileentity1);
/*      */             
/* 1171 */             if (!this.c.contains(tileentity1)) {
/* 1172 */               this.c.add(tileentity1);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1177 */           notify(tileentity1.x, tileentity1.y, tileentity1.z);
/*      */         } 
/*      */       } 
/*      */       
/* 1181 */       this.G.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Collection collection) {
/* 1186 */     if (this.L) {
/* 1187 */       this.G.addAll(collection);
/*      */     } else {
/* 1189 */       this.c.addAll(collection);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1194 */   public void playerJoinedWorld(Entity entity) { entityJoinedWorld(entity, true); }
/*      */ 
/*      */   
/*      */   public void entityJoinedWorld(Entity entity, boolean flag) {
/* 1198 */     int i = MathHelper.floor(entity.locX);
/* 1199 */     int j = MathHelper.floor(entity.locZ);
/* 1200 */     byte b0 = 32;
/*      */     
/* 1202 */     if (!flag || a(i - b0, 0, j - b0, i + b0, 128, j + b0)) {
/* 1203 */       entity.bo = entity.locX;
/* 1204 */       entity.bp = entity.locY;
/* 1205 */       entity.bq = entity.locZ;
/* 1206 */       entity.lastYaw = entity.yaw;
/* 1207 */       entity.lastPitch = entity.pitch;
/* 1208 */       if (flag && entity.bG) {
/* 1209 */         if (entity.vehicle != null) {
/* 1210 */           entity.E();
/*      */         } else {
/* 1212 */           entity.m_();
/*      */         } 
/*      */       }
/*      */       
/* 1216 */       if (Double.isNaN(entity.locX) || Double.isInfinite(entity.locX)) {
/* 1217 */         entity.locX = entity.bo;
/*      */       }
/*      */       
/* 1220 */       if (Double.isNaN(entity.locY) || Double.isInfinite(entity.locY)) {
/* 1221 */         entity.locY = entity.bp;
/*      */       }
/*      */       
/* 1224 */       if (Double.isNaN(entity.locZ) || Double.isInfinite(entity.locZ)) {
/* 1225 */         entity.locZ = entity.bq;
/*      */       }
/*      */       
/* 1228 */       if (Double.isNaN(entity.pitch) || Double.isInfinite(entity.pitch)) {
/* 1229 */         entity.pitch = entity.lastPitch;
/*      */       }
/*      */       
/* 1232 */       if (Double.isNaN(entity.yaw) || Double.isInfinite(entity.yaw)) {
/* 1233 */         entity.yaw = entity.lastYaw;
/*      */       }
/*      */       
/* 1236 */       int k = MathHelper.floor(entity.locX / 16.0D);
/* 1237 */       int l = MathHelper.floor(entity.locY / 16.0D);
/* 1238 */       int i1 = MathHelper.floor(entity.locZ / 16.0D);
/*      */       
/* 1240 */       if (!entity.bG || entity.bH != k || entity.bI != l || entity.bJ != i1) {
/* 1241 */         if (entity.bG && isChunkLoaded(entity.bH, entity.bJ)) {
/* 1242 */           getChunkAt(entity.bH, entity.bJ).a(entity, entity.bI);
/*      */         }
/*      */         
/* 1245 */         if (isChunkLoaded(k, i1)) {
/* 1246 */           entity.bG = true;
/* 1247 */           getChunkAt(k, i1).a(entity);
/*      */         } else {
/* 1249 */           entity.bG = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1253 */       if (flag && entity.bG && entity.passenger != null) {
/* 1254 */         if (!entity.passenger.dead && entity.passenger.vehicle == entity) {
/* 1255 */           playerJoinedWorld(entity.passenger);
/*      */         } else {
/* 1257 */           entity.passenger.vehicle = null;
/* 1258 */           entity.passenger = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsEntity(AxisAlignedBB axisalignedbb) {
/* 1265 */     List list = b((Entity)null, axisalignedbb);
/*      */     
/* 1267 */     for (int i = 0; i < list.size(); i++) {
/* 1268 */       Entity entity = (Entity)list.get(i);
/*      */       
/* 1270 */       if (!entity.dead && entity.aI) {
/* 1271 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1275 */     return true;
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb) {
/* 1279 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1280 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1281 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1282 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1283 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1284 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1286 */     if (axisalignedbb.a < 0.0D) {
/* 1287 */       i--;
/*      */     }
/*      */     
/* 1290 */     if (axisalignedbb.b < 0.0D) {
/* 1291 */       k--;
/*      */     }
/*      */     
/* 1294 */     if (axisalignedbb.c < 0.0D) {
/* 1295 */       i1--;
/*      */     }
/*      */     
/* 1298 */     for (int k1 = i; k1 < j; k1++) {
/* 1299 */       for (int l1 = k; l1 < l; l1++) {
/* 1300 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1301 */           Block block = Block.byId[getTypeId(k1, l1, i2)];
/*      */           
/* 1303 */           if (block != null) {
/* 1304 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1310 */     return false;
/*      */   }
/*      */   
/*      */   public boolean c(AxisAlignedBB axisalignedbb) {
/* 1314 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1315 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1316 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1317 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1318 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1319 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1321 */     if (axisalignedbb.a < 0.0D) {
/* 1322 */       i--;
/*      */     }
/*      */     
/* 1325 */     if (axisalignedbb.b < 0.0D) {
/* 1326 */       k--;
/*      */     }
/*      */     
/* 1329 */     if (axisalignedbb.c < 0.0D) {
/* 1330 */       i1--;
/*      */     }
/*      */     
/* 1333 */     for (int k1 = i; k1 < j; k1++) {
/* 1334 */       for (int l1 = k; l1 < l; l1++) {
/* 1335 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1336 */           Block block = Block.byId[getTypeId(k1, l1, i2)];
/*      */           
/* 1338 */           if (block != null && block.material.isLiquid()) {
/* 1339 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1345 */     return false;
/*      */   }
/*      */   
/*      */   public boolean d(AxisAlignedBB axisalignedbb) {
/* 1349 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1350 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1351 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1352 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1353 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1354 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1356 */     if (a(i, k, i1, j, l, j1)) {
/* 1357 */       for (int k1 = i; k1 < j; k1++) {
/* 1358 */         for (int l1 = k; l1 < l; l1++) {
/* 1359 */           for (int i2 = i1; i2 < j1; i2++) {
/* 1360 */             int j2 = getTypeId(k1, l1, i2);
/*      */             
/* 1362 */             if (j2 == Block.FIRE.id || j2 == Block.LAVA.id || j2 == Block.STATIONARY_LAVA.id) {
/* 1363 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1370 */     return false;
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material, Entity entity) {
/* 1374 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1375 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1376 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1377 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1378 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1379 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1381 */     if (!a(i, k, i1, j, l, j1)) {
/* 1382 */       return false;
/*      */     }
/* 1384 */     boolean flag = false;
/* 1385 */     Vec3D vec3d = Vec3D.create(0.0D, 0.0D, 0.0D);
/*      */     
/* 1387 */     for (int k1 = i; k1 < j; k1++) {
/* 1388 */       for (int l1 = k; l1 < l; l1++) {
/* 1389 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1390 */           Block block = Block.byId[getTypeId(k1, l1, i2)];
/*      */           
/* 1392 */           if (block != null && block.material == material) {
/* 1393 */             double d0 = ((l1 + 1) - BlockFluids.c(getData(k1, l1, i2)));
/*      */             
/* 1395 */             if (l >= d0) {
/* 1396 */               flag = true;
/* 1397 */               block.a(this, k1, l1, i2, entity, vec3d);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1404 */     if (vec3d.c() > 0.0D) {
/* 1405 */       vec3d = vec3d.b();
/* 1406 */       double d1 = 0.014D;
/*      */       
/* 1408 */       entity.motX += vec3d.a * d1;
/* 1409 */       entity.motY += vec3d.b * d1;
/* 1410 */       entity.motZ += vec3d.c * d1;
/*      */     } 
/*      */     
/* 1413 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material) {
/* 1418 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1419 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1420 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1421 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1422 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1423 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1425 */     for (int k1 = i; k1 < j; k1++) {
/* 1426 */       for (int l1 = k; l1 < l; l1++) {
/* 1427 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1428 */           Block block = Block.byId[getTypeId(k1, l1, i2)];
/*      */           
/* 1430 */           if (block != null && block.material == material) {
/* 1431 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1437 */     return false;
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb, Material material) {
/* 1441 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1442 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1443 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1444 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1445 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1446 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1448 */     for (int k1 = i; k1 < j; k1++) {
/* 1449 */       for (int l1 = k; l1 < l; l1++) {
/* 1450 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1451 */           Block block = Block.byId[getTypeId(k1, l1, i2)];
/*      */           
/* 1453 */           if (block != null && block.material == material) {
/* 1454 */             int j2 = getData(k1, l1, i2);
/* 1455 */             double d0 = (l1 + 1);
/*      */             
/* 1457 */             if (j2 < 8) {
/* 1458 */               d0 = (l1 + 1) - j2 / 8.0D;
/*      */             }
/*      */             
/* 1461 */             if (d0 >= axisalignedbb.b) {
/* 1462 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1469 */     return false;
/*      */   }
/*      */ 
/*      */   
/* 1473 */   public Explosion a(Entity entity, double d0, double d1, double d2, float f) { return createExplosion(entity, d0, d1, d2, f, false); }
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
/* 1477 */     Explosion explosion = new Explosion(this, entity, d0, d1, d2, f);
/*      */     
/* 1479 */     explosion.a = flag;
/* 1480 */     explosion.a();
/* 1481 */     explosion.a(true);
/* 1482 */     return explosion;
/*      */   }
/*      */   
/*      */   public float a(Vec3D vec3d, AxisAlignedBB axisalignedbb) {
/* 1486 */     double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
/* 1487 */     double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
/* 1488 */     double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
/* 1489 */     int i = 0;
/* 1490 */     int j = 0;
/*      */     float f;
/* 1492 */     for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 1493 */       float f1; for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 1494 */         float f2; for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 1495 */           double d3 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
/* 1496 */           double d4 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f1;
/* 1497 */           double d5 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f2;
/*      */           
/* 1499 */           if (a(Vec3D.create(d3, d4, d5), vec3d) == null) {
/* 1500 */             i++;
/*      */           }
/*      */           
/* 1503 */           j++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1508 */     return i / j;
/*      */   }
/*      */   
/*      */   public void douseFire(EntityHuman entityhuman, int i, int j, int k, int l) {
/* 1512 */     if (l == 0) {
/* 1513 */       j--;
/*      */     }
/*      */     
/* 1516 */     if (l == 1) {
/* 1517 */       j++;
/*      */     }
/*      */     
/* 1520 */     if (l == 2) {
/* 1521 */       k--;
/*      */     }
/*      */     
/* 1524 */     if (l == 3) {
/* 1525 */       k++;
/*      */     }
/*      */     
/* 1528 */     if (l == 4) {
/* 1529 */       i--;
/*      */     }
/*      */     
/* 1532 */     if (l == 5) {
/* 1533 */       i++;
/*      */     }
/*      */     
/* 1536 */     if (getTypeId(i, j, k) == Block.FIRE.id) {
/* 1537 */       a(entityhuman, 1004, i, j, k, 0);
/* 1538 */       setTypeId(i, j, k, 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public TileEntity getTileEntity(int i, int j, int k) {
/* 1543 */     Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */     
/* 1545 */     return (chunk != null) ? chunk.d(i & 0xF, j, k & 0xF) : null;
/*      */   }
/*      */   
/*      */   public void setTileEntity(int i, int j, int k, TileEntity tileentity) {
/* 1549 */     if (!tileentity.g()) {
/* 1550 */       if (this.L) {
/* 1551 */         tileentity.x = i;
/* 1552 */         tileentity.y = j;
/* 1553 */         tileentity.z = k;
/* 1554 */         this.G.add(tileentity);
/*      */       }
/*      */       else {
/*      */         
/* 1558 */         Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */         
/* 1560 */         if (chunk != null) {
/* 1561 */           chunk.a(i & 0xF, j, k & 0xF, tileentity);
/* 1562 */           this.c.add(tileentity);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void o(int i, int j, int k) {
/* 1569 */     TileEntity tileentity = getTileEntity(i, j, k);
/*      */     
/* 1571 */     if (tileentity != null && this.L) {
/* 1572 */       tileentity.h();
/*      */     } else {
/* 1574 */       if (tileentity != null) {
/* 1575 */         this.c.remove(tileentity);
/*      */       }
/*      */       
/* 1578 */       Chunk chunk = getChunkAt(i >> 4, k >> 4);
/*      */       
/* 1580 */       if (chunk != null) {
/* 1581 */         chunk.e(i & 0xF, j, k & 0xF);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean p(int i, int j, int k) {
/* 1587 */     Block block = Block.byId[getTypeId(i, j, k)];
/*      */     
/* 1589 */     return (block == null) ? false : block.a();
/*      */   }
/*      */   
/*      */   public boolean e(int i, int j, int k) {
/* 1593 */     Block block = Block.byId[getTypeId(i, j, k)];
/*      */     
/* 1595 */     return (block == null) ? false : ((block.material.h() && block.b()));
/*      */   }
/*      */   public boolean doLighting() {
/*      */     boolean flag;
/* 1599 */     if (this.M >= 50) {
/* 1600 */       return false;
/*      */     }
/* 1602 */     this.M++;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1607 */       int i = 500;
/*      */       
/* 1609 */       while (this.C.size() > 0) {
/* 1610 */         i--;
/* 1611 */         if (i <= 0) {
/* 1612 */           boolean flag = true;
/* 1613 */           return flag;
/*      */         } 
/*      */         
/* 1616 */         ((MetadataChunkBlock)this.C.remove(this.C.size() - 1)).a(this);
/*      */       } 
/*      */       
/* 1619 */       flag = false;
/*      */     } finally {
/* 1621 */       this.M--;
/*      */     } 
/*      */     
/* 1624 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1629 */   public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1) { a(enumskyblock, i, j, k, l, i1, j1, true); }
/*      */ 
/*      */   
/*      */   public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1, boolean flag) {
/* 1633 */     if (!this.worldProvider.e || enumskyblock != EnumSkyBlock.SKY) {
/* 1634 */       A++;
/*      */       
/*      */       try {
/* 1637 */         if (A == 50) {
/*      */           return;
/*      */         }
/*      */         
/* 1641 */         int k1 = (l + i) / 2;
/* 1642 */         int l1 = (j1 + k) / 2;
/*      */         
/* 1644 */         if (isLoaded(k1, 64, l1)) {
/* 1645 */           if (getChunkAtWorldCoords(k1, l1).isEmpty()) {
/*      */             return;
/*      */           }
/*      */           
/* 1649 */           int i2 = this.C.size();
/*      */ 
/*      */           
/* 1652 */           if (flag) {
/* 1653 */             int j2 = 5;
/* 1654 */             if (j2 > i2) {
/* 1655 */               j2 = i2;
/*      */             }
/*      */             
/* 1658 */             for (int k2 = 0; k2 < j2; k2++) {
/* 1659 */               MetadataChunkBlock metadatachunkblock = (MetadataChunkBlock)this.C.get(this.C.size() - k2 - 1);
/*      */               
/* 1661 */               if (metadatachunkblock.a == enumskyblock && metadatachunkblock.a(i, j, k, l, i1, j1)) {
/*      */                 return;
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/* 1667 */           this.C.add(new MetadataChunkBlock(enumskyblock, i, j, k, l, i1, j1));
/* 1668 */           int j2 = 1000000;
/* 1669 */           if (this.C.size() > 1000000) {
/* 1670 */             System.out.println("More than " + j2 + " updates, aborting lighting updates");
/* 1671 */             this.C.clear();
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } finally {
/* 1677 */         A--;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void g() {
/* 1683 */     int i = a(1.0F);
/*      */     
/* 1685 */     if (i != this.f) {
/* 1686 */       this.f = i;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSpawnFlags(boolean flag, boolean flag1) {
/* 1691 */     this.allowMonsters = flag;
/* 1692 */     this.allowAnimals = flag1;
/*      */   }
/*      */   
/*      */   public void doTick() {
/* 1696 */     i();
/*      */ 
/*      */     
/* 1699 */     if (everyoneDeeplySleeping()) {
/* 1700 */       boolean flag = false;
/*      */       
/* 1702 */       if (this.allowMonsters && this.spawnMonsters >= 1) {
/* 1703 */         flag = SpawnerCreature.a(this, this.players);
/*      */       }
/*      */       
/* 1706 */       if (!flag) {
/* 1707 */         long i = this.worldData.f() + 24000L;
/* 1708 */         this.worldData.a(i - i % 24000L);
/* 1709 */         s();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1714 */     if ((this.allowMonsters || this.allowAnimals) && this instanceof WorldServer && (getServer().getHandle()).players.size() > 0) {
/* 1715 */       SpawnerCreature.spawnEntities(this, this.allowMonsters, this.allowAnimals);
/*      */     }
/*      */ 
/*      */     
/* 1719 */     this.chunkProvider.unloadChunks();
/* 1720 */     int j = a(1.0F);
/*      */     
/* 1722 */     if (j != this.f) {
/* 1723 */       this.f = j;
/*      */       
/* 1725 */       for (int k = 0; k < this.u.size(); k++) {
/* 1726 */         ((IWorldAccess)this.u.get(k)).a();
/*      */       }
/*      */     } 
/*      */     
/* 1730 */     long i = this.worldData.f() + 1L;
/* 1731 */     if (i % this.p == 0L) {
/* 1732 */       save(false, (IProgressUpdate)null);
/*      */     }
/*      */     
/* 1735 */     this.worldData.a(i);
/* 1736 */     a(false);
/* 1737 */     j();
/*      */   }
/*      */   
/*      */   private void x() {
/* 1741 */     if (this.worldData.hasStorm()) {
/* 1742 */       this.j = 1.0F;
/* 1743 */       if (this.worldData.isThundering()) {
/* 1744 */         this.l = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void i() {
/* 1750 */     if (!this.worldProvider.e) {
/* 1751 */       if (this.m > 0) {
/* 1752 */         this.m--;
/*      */       }
/*      */       
/* 1755 */       int i = this.worldData.getThunderDuration();
/*      */       
/* 1757 */       if (i <= 0) {
/* 1758 */         if (this.worldData.isThundering()) {
/* 1759 */           this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
/*      */         } else {
/* 1761 */           this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 1764 */         i--;
/* 1765 */         this.worldData.setThunderDuration(i);
/* 1766 */         if (i <= 0) {
/*      */           
/* 1768 */           ThunderChangeEvent thunder = new ThunderChangeEvent(getWorld(), !this.worldData.isThundering() ? 1 : 0);
/* 1769 */           getServer().getPluginManager().callEvent(thunder);
/* 1770 */           if (!thunder.isCancelled()) {
/* 1771 */             this.worldData.setThundering(!this.worldData.isThundering());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1777 */       int j = this.worldData.getWeatherDuration();
/*      */       
/* 1779 */       if (j <= 0) {
/* 1780 */         if (this.worldData.hasStorm()) {
/* 1781 */           this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
/*      */         } else {
/* 1783 */           this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 1786 */         j--;
/* 1787 */         this.worldData.setWeatherDuration(j);
/* 1788 */         if (j <= 0) {
/*      */           
/* 1790 */           WeatherChangeEvent weather = new WeatherChangeEvent(getWorld(), !this.worldData.hasStorm() ? 1 : 0);
/* 1791 */           getServer().getPluginManager().callEvent(weather);
/*      */           
/* 1793 */           if (!weather.isCancelled()) {
/* 1794 */             this.worldData.setStorm(!this.worldData.hasStorm());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1800 */       this.i = this.j;
/* 1801 */       if (this.worldData.hasStorm()) {
/* 1802 */         this.j = (float)(this.j + 0.01D);
/*      */       } else {
/* 1804 */         this.j = (float)(this.j - 0.01D);
/*      */       } 
/*      */       
/* 1807 */       if (this.j < 0.0F) {
/* 1808 */         this.j = 0.0F;
/*      */       }
/*      */       
/* 1811 */       if (this.j > 1.0F) {
/* 1812 */         this.j = 1.0F;
/*      */       }
/*      */       
/* 1815 */       this.k = this.l;
/* 1816 */       if (this.worldData.isThundering()) {
/* 1817 */         this.l = (float)(this.l + 0.01D);
/*      */       } else {
/* 1819 */         this.l = (float)(this.l - 0.01D);
/*      */       } 
/*      */       
/* 1822 */       if (this.l < 0.0F) {
/* 1823 */         this.l = 0.0F;
/*      */       }
/*      */       
/* 1826 */       if (this.l > 1.0F) {
/* 1827 */         this.l = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void y() {
/* 1834 */     WeatherChangeEvent weather = new WeatherChangeEvent(getWorld(), false);
/* 1835 */     getServer().getPluginManager().callEvent(weather);
/*      */     
/* 1837 */     ThunderChangeEvent thunder = new ThunderChangeEvent(getWorld(), false);
/* 1838 */     getServer().getPluginManager().callEvent(thunder);
/* 1839 */     if (!weather.isCancelled()) {
/* 1840 */       this.worldData.setWeatherDuration(0);
/* 1841 */       this.worldData.setStorm(false);
/*      */     } 
/* 1843 */     if (!thunder.isCancelled()) {
/* 1844 */       this.worldData.setThunderDuration(0);
/* 1845 */       this.worldData.setThundering(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void j() {
/* 1851 */     this.P.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1858 */     for (i1 = 0; i1 < this.players.size(); i1++) {
/* 1859 */       EntityHuman entityhuman = (EntityHuman)this.players.get(i1);
/*      */       
/* 1861 */       int i = MathHelper.floor(entityhuman.locX / 16.0D);
/* 1862 */       int j = MathHelper.floor(entityhuman.locZ / 16.0D);
/* 1863 */       byte b0 = 9;
/*      */       
/* 1865 */       for (int k = -b0; k <= b0; k++) {
/* 1866 */         for (int l = -b0; l <= b0; l++) {
/* 1867 */           this.P.add(new ChunkCoordIntPair(k + i, l + j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1872 */     if (this.Q > 0) {
/* 1873 */       this.Q--;
/*      */     }
/*      */     
/* 1876 */     Iterator iterator = this.P.iterator();
/*      */     
/* 1878 */     while (iterator.hasNext()) {
/* 1879 */       ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
/*      */       
/* 1881 */       int i = chunkcoordintpair.x * 16;
/* 1882 */       int j = chunkcoordintpair.z * 16;
/* 1883 */       Chunk chunk = getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1888 */       if (this.Q == 0) {
/* 1889 */         this.g = this.g * 3 + 1013904223;
/* 1890 */         int k = this.g >> 2;
/* 1891 */         int l = k & 0xF;
/* 1892 */         int j1 = k >> 8 & 0xF;
/* 1893 */         int k1 = k >> 16 & 0x7F;
/* 1894 */         int l1 = chunk.getTypeId(l, k1, j1);
/* 1895 */         l += i;
/* 1896 */         j1 += j;
/* 1897 */         if (l1 == 0 && k(l, k1, j1) <= this.random.nextInt(8) && a(EnumSkyBlock.SKY, l, k1, j1) <= 0) {
/* 1898 */           EntityHuman entityhuman1 = a(l + 0.5D, k1 + 0.5D, j1 + 0.5D, 8.0D);
/*      */           
/* 1900 */           if (entityhuman1 != null && entityhuman1.e(l + 0.5D, k1 + 0.5D, j1 + 0.5D) > 4.0D) {
/* 1901 */             makeSound(l + 0.5D, k1 + 0.5D, j1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
/* 1902 */             this.Q = this.random.nextInt(12000) + 6000;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1907 */       if (this.random.nextInt(100000) == 0 && v() && u()) {
/* 1908 */         this.g = this.g * 3 + 1013904223;
/* 1909 */         int k = this.g >> 2;
/* 1910 */         int l = i + (k & 0xF);
/* 1911 */         int j1 = j + (k >> 8 & 0xF);
/* 1912 */         int k1 = e(l, j1);
/* 1913 */         if (s(l, k1, j1)) {
/* 1914 */           strikeLightning(new EntityWeatherStorm(this, l, k1, j1));
/* 1915 */           this.m = 2;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1921 */       if (this.random.nextInt(16) == 0) {
/* 1922 */         this.g = this.g * 3 + 1013904223;
/* 1923 */         int k = this.g >> 2;
/* 1924 */         int l = k & 0xF;
/* 1925 */         int j1 = k >> 8 & 0xF;
/* 1926 */         int k1 = e(l + i, j1 + j);
/* 1927 */         if (getWorldChunkManager().getBiome(l + i, j1 + j).c() && k1 >= 0 && k1 < 128 && chunk.a(EnumSkyBlock.BLOCK, l, k1, j1) < 10) {
/* 1928 */           int l1 = chunk.getTypeId(l, k1 - 1, j1);
/* 1929 */           int i2 = chunk.getTypeId(l, k1, j1);
/* 1930 */           if (v() && i2 == 0 && Block.SNOW.canPlace(this, l + i, k1, j1 + j) && l1 != 0 && l1 != Block.ICE.id && (Block.byId[l1]).material.isSolid()) {
/*      */             
/* 1932 */             BlockState blockState = getWorld().getBlockAt(l + i, k1, j1 + j).getState();
/* 1933 */             blockState.setTypeId(Block.SNOW.id);
/*      */             
/* 1935 */             BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
/* 1936 */             getServer().getPluginManager().callEvent(snow);
/* 1937 */             if (!snow.isCancelled()) {
/* 1938 */               blockState.update(true);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1944 */           if (l1 == Block.STATIONARY_WATER.id && chunk.getData(l, k1 - 1, j1) == 0) {
/* 1945 */             BlockState blockState = getWorld().getBlockAt(l + i, k1 - 1, j1 + j).getState();
/* 1946 */             blockState.setTypeId(Block.ICE.id);
/*      */             
/* 1948 */             BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
/* 1949 */             getServer().getPluginManager().callEvent(iceBlockForm);
/* 1950 */             if (!iceBlockForm.isCancelled()) {
/* 1951 */               blockState.update(true);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1958 */       for (int k = 0; k < 80; k++) {
/* 1959 */         this.g = this.g * 3 + 1013904223;
/* 1960 */         int l = this.g >> 2;
/* 1961 */         int j1 = l & 0xF;
/* 1962 */         int k1 = l >> 8 & 0xF;
/* 1963 */         int l1 = l >> 16 & 0x7F;
/* 1964 */         int i2 = chunk.b[j1 << 11 | k1 << 7 | l1] & 0xFF;
/* 1965 */         if (Block.n[i2]) {
/* 1966 */           Block.byId[i2].a(this, j1 + i, l1, k1 + j, this.random);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag) {
/* 1973 */     int i = this.E.size();
/*      */     
/* 1975 */     if (i != this.F.size()) {
/* 1976 */       throw new IllegalStateException("TickNextTick list out of synch");
/*      */     }
/* 1978 */     if (i > 1000) {
/* 1979 */       i = 1000;
/*      */     }
/*      */     
/* 1982 */     for (int j = 0; j < i; j++) {
/* 1983 */       NextTickListEntry nextticklistentry = (NextTickListEntry)this.E.first();
/*      */       
/* 1985 */       if (!flag && nextticklistentry.e > this.worldData.f()) {
/*      */         break;
/*      */       }
/*      */       
/* 1989 */       this.E.remove(nextticklistentry);
/* 1990 */       this.F.remove(nextticklistentry);
/* 1991 */       byte b0 = 8;
/*      */       
/* 1993 */       if (a(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
/* 1994 */         int k = getTypeId(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
/*      */         
/* 1996 */         if (k == nextticklistentry.d && k > 0) {
/* 1997 */           Block.byId[k].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2002 */     return (this.E.size() != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public List b(Entity entity, AxisAlignedBB axisalignedbb) {
/* 2007 */     this.R.clear();
/* 2008 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2009 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2010 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2011 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/*      */     
/* 2013 */     for (int i1 = i; i1 <= j; i1++) {
/* 2014 */       for (int j1 = k; j1 <= l; j1++) {
/* 2015 */         if (isChunkLoaded(i1, j1)) {
/* 2016 */           getChunkAt(i1, j1).a(entity, axisalignedbb, this.R);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2021 */     return this.R;
/*      */   }
/*      */   
/*      */   public List a(Class oclass, AxisAlignedBB axisalignedbb) {
/* 2025 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2026 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2027 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2028 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/* 2029 */     ArrayList arraylist = new ArrayList();
/*      */     
/* 2031 */     for (int i1 = i; i1 <= j; i1++) {
/* 2032 */       for (int j1 = k; j1 <= l; j1++) {
/* 2033 */         if (isChunkLoaded(i1, j1)) {
/* 2034 */           getChunkAt(i1, j1).a(oclass, axisalignedbb, arraylist);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2039 */     return arraylist;
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, TileEntity tileentity) {
/* 2043 */     if (isLoaded(i, j, k)) {
/* 2044 */       getChunkAtWorldCoords(i, k).f();
/*      */     }
/*      */     
/* 2047 */     for (int l = 0; l < this.u.size(); l++) {
/* 2048 */       ((IWorldAccess)this.u.get(l)).a(i, j, k, tileentity);
/*      */     }
/*      */   }
/*      */   
/*      */   public int a(Class oclass) {
/* 2053 */     int i = 0;
/*      */     
/* 2055 */     for (int j = 0; j < this.entityList.size(); j++) {
/* 2056 */       Entity entity = (Entity)this.entityList.get(j);
/*      */       
/* 2058 */       if (oclass.isAssignableFrom(entity.getClass())) {
/* 2059 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 2063 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(List list) {
/* 2068 */     Entity entity = null;
/* 2069 */     for (int i = 0; i < list.size(); i++) {
/* 2070 */       entity = (Entity)list.get(i);
/*      */       
/* 2072 */       if (entity != null) {
/*      */ 
/*      */ 
/*      */         
/* 2076 */         this.entityList.add(entity);
/*      */         
/* 2078 */         c((Entity)list.get(i));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/* 2083 */   public void b(List list) { this.D.addAll(list); }
/*      */   
/*      */   public boolean a(int i, int j, int k, int l, boolean flag, int i1) {
/*      */     boolean defaultReturn;
/* 2087 */     int j1 = getTypeId(j, k, l);
/* 2088 */     Block block = Block.byId[j1];
/* 2089 */     Block block1 = Block.byId[i];
/* 2090 */     AxisAlignedBB axisalignedbb = block1.e(this, j, k, l);
/*      */     
/* 2092 */     if (flag) {
/* 2093 */       axisalignedbb = null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2098 */     if (axisalignedbb != null && !containsEntity(axisalignedbb)) {
/* 2099 */       defaultReturn = false;
/*      */     } else {
/* 2101 */       if (block == Block.WATER || block == Block.STATIONARY_WATER || block == Block.LAVA || block == Block.STATIONARY_LAVA || block == Block.FIRE || block == Block.SNOW) {
/* 2102 */         block = null;
/*      */       }
/*      */       
/* 2105 */       defaultReturn = (i > 0 && block == null && block1.canPlace(this, j, k, l, i1));
/*      */     } 
/*      */ 
/*      */     
/* 2109 */     BlockCanBuildEvent event = new BlockCanBuildEvent(getWorld().getBlockAt(j, k, l), i, defaultReturn);
/* 2110 */     getServer().getPluginManager().callEvent(event);
/*      */     
/* 2112 */     return event.isBuildable();
/*      */   }
/*      */ 
/*      */   
/*      */   public PathEntity findPath(Entity entity, Entity entity1, float f) {
/* 2117 */     int i = MathHelper.floor(entity.locX);
/* 2118 */     int j = MathHelper.floor(entity.locY);
/* 2119 */     int k = MathHelper.floor(entity.locZ);
/* 2120 */     int l = (int)(f + 16.0F);
/* 2121 */     int i1 = i - l;
/* 2122 */     int j1 = j - l;
/* 2123 */     int k1 = k - l;
/* 2124 */     int l1 = i + l;
/* 2125 */     int i2 = j + l;
/* 2126 */     int j2 = k + l;
/* 2127 */     ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2);
/*      */     
/* 2129 */     return (new Pathfinder(chunkcache)).a(entity, entity1, f);
/*      */   }
/*      */   
/*      */   public PathEntity a(Entity entity, int i, int j, int k, float f) {
/* 2133 */     int l = MathHelper.floor(entity.locX);
/* 2134 */     int i1 = MathHelper.floor(entity.locY);
/* 2135 */     int j1 = MathHelper.floor(entity.locZ);
/* 2136 */     int k1 = (int)(f + 8.0F);
/* 2137 */     int l1 = l - k1;
/* 2138 */     int i2 = i1 - k1;
/* 2139 */     int j2 = j1 - k1;
/* 2140 */     int k2 = l + k1;
/* 2141 */     int l2 = i1 + k1;
/* 2142 */     int i3 = j1 + k1;
/* 2143 */     ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3);
/*      */     
/* 2145 */     return (new Pathfinder(chunkcache)).a(entity, i, j, k, f);
/*      */   }
/*      */   
/*      */   public boolean isBlockFacePowered(int i, int j, int k, int l) {
/* 2149 */     int i1 = getTypeId(i, j, k);
/*      */     
/* 2151 */     return (i1 == 0) ? false : Block.byId[i1].d(this, i, j, k, l);
/*      */   }
/*      */ 
/*      */   
/* 2155 */   public boolean isBlockPowered(int i, int j, int k) { return isBlockFacePowered(i, j - 1, k, 0) ? true : (isBlockFacePowered(i, j + 1, k, 1) ? true : (isBlockFacePowered(i, j, k - 1, 2) ? true : (isBlockFacePowered(i, j, k + 1, 3) ? true : (isBlockFacePowered(i - 1, j, k, 4) ? true : isBlockFacePowered(i + 1, j, k, 5))))); }
/*      */ 
/*      */   
/*      */   public boolean isBlockFaceIndirectlyPowered(int i, int j, int k, int l) {
/* 2159 */     if (e(i, j, k)) {
/* 2160 */       return isBlockPowered(i, j, k);
/*      */     }
/* 2162 */     int i1 = getTypeId(i, j, k);
/*      */     
/* 2164 */     return (i1 == 0) ? false : Block.byId[i1].a(this, i, j, k, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2169 */   public boolean isBlockIndirectlyPowered(int i, int j, int k) { return isBlockFaceIndirectlyPowered(i, j - 1, k, 0) ? true : (isBlockFaceIndirectlyPowered(i, j + 1, k, 1) ? true : (isBlockFaceIndirectlyPowered(i, j, k - 1, 2) ? true : (isBlockFaceIndirectlyPowered(i, j, k + 1, 3) ? true : (isBlockFaceIndirectlyPowered(i - 1, j, k, 4) ? true : isBlockFaceIndirectlyPowered(i + 1, j, k, 5))))); }
/*      */ 
/*      */ 
/*      */   
/* 2173 */   public EntityHuman findNearbyPlayer(Entity entity, double d0) { return a(entity.locX, entity.locY, entity.locZ, d0); }
/*      */ 
/*      */   
/*      */   public EntityHuman a(double d0, double d1, double d2, double d3) {
/* 2177 */     double d4 = -1.0D;
/* 2178 */     EntityHuman entityhuman = null;
/*      */     
/* 2180 */     for (int i = 0; i < this.players.size(); i++) {
/* 2181 */       EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
/*      */       
/* 2183 */       if (entityhuman1 != null && !entityhuman1.dead) {
/*      */ 
/*      */ 
/*      */         
/* 2187 */         double d5 = entityhuman1.e(d0, d1, d2);
/*      */         
/* 2189 */         if ((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1.0D || d5 < d4)) {
/* 2190 */           d4 = d5;
/* 2191 */           entityhuman = entityhuman1;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2195 */     return entityhuman;
/*      */   }
/*      */   
/*      */   public EntityHuman a(String s) {
/* 2199 */     for (int i = 0; i < this.players.size(); i++) {
/* 2200 */       if (s.equals(((EntityHuman)this.players.get(i)).name)) {
/* 2201 */         return (EntityHuman)this.players.get(i);
/*      */       }
/*      */     } 
/*      */     
/* 2205 */     return null;
/*      */   }
/*      */   
/*      */   public byte[] getMultiChunkData(int i, int j, int k, int l, int i1, int j1) {
/* 2209 */     byte[] abyte = new byte[l * i1 * j1 * 5 / 2];
/* 2210 */     int k1 = i >> 4;
/* 2211 */     int l1 = k >> 4;
/* 2212 */     int i2 = i + l - 1 >> 4;
/* 2213 */     int j2 = k + j1 - 1 >> 4;
/* 2214 */     int k2 = 0;
/* 2215 */     int l2 = j;
/* 2216 */     int i3 = j + i1;
/*      */     
/* 2218 */     if (j < 0) {
/* 2219 */       l2 = 0;
/*      */     }
/*      */     
/* 2222 */     if (i3 > 128) {
/* 2223 */       i3 = 128;
/*      */     }
/*      */     
/* 2226 */     for (int j3 = k1; j3 <= i2; j3++) {
/* 2227 */       int k3 = i - j3 * 16;
/* 2228 */       int l3 = i + l - j3 * 16;
/*      */       
/* 2230 */       if (k3 < 0) {
/* 2231 */         k3 = 0;
/*      */       }
/*      */       
/* 2234 */       if (l3 > 16) {
/* 2235 */         l3 = 16;
/*      */       }
/*      */       
/* 2238 */       for (int i4 = l1; i4 <= j2; i4++) {
/* 2239 */         int j4 = k - i4 * 16;
/* 2240 */         int k4 = k + j1 - i4 * 16;
/*      */         
/* 2242 */         if (j4 < 0) {
/* 2243 */           j4 = 0;
/*      */         }
/*      */         
/* 2246 */         if (k4 > 16) {
/* 2247 */           k4 = 16;
/*      */         }
/*      */         
/* 2250 */         k2 = getChunkAt(j3, i4).getData(abyte, k3, l2, j4, l3, i3, k4, k2);
/*      */       } 
/*      */     } 
/*      */     
/* 2254 */     return abyte;
/*      */   }
/*      */ 
/*      */   
/* 2258 */   public void k() { this.w.b(); }
/*      */ 
/*      */ 
/*      */   
/* 2262 */   public void setTime(long i) { this.worldData.a(i); }
/*      */ 
/*      */   
/*      */   public void setTimeAndFixTicklists(long i) {
/* 2266 */     long j = i - this.worldData.f();
/*      */ 
/*      */ 
/*      */     
/* 2270 */     for (Iterator iterator = this.F.iterator(); iterator.hasNext(); nextticklistentry.e += j) {
/* 2271 */       NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
/*      */     }
/*      */     
/* 2274 */     setTime(i);
/*      */   }
/*      */ 
/*      */   
/* 2278 */   public long getSeed() { return this.worldData.getSeed(); }
/*      */ 
/*      */ 
/*      */   
/* 2282 */   public long getTime() { return this.worldData.f(); }
/*      */ 
/*      */ 
/*      */   
/* 2286 */   public ChunkCoordinates getSpawn() { return new ChunkCoordinates(this.worldData.c(), this.worldData.d(), this.worldData.e()); }
/*      */ 
/*      */ 
/*      */   
/* 2290 */   public boolean a(EntityHuman entityhuman, int i, int j, int k) { return true; }
/*      */ 
/*      */   
/*      */   public void a(Entity entity, byte b0) {}
/*      */ 
/*      */   
/* 2296 */   public IChunkProvider o() { return this.chunkProvider; }
/*      */ 
/*      */   
/*      */   public void playNote(int i, int j, int k, int l, int i1) {
/* 2300 */     int j1 = getTypeId(i, j, k);
/*      */     
/* 2302 */     if (j1 > 0) {
/* 2303 */       Block.byId[j1].a(this, i, j, k, l, i1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 2308 */   public IDataManager p() { return this.w; }
/*      */ 
/*      */ 
/*      */   
/* 2312 */   public WorldData q() { return this.worldData; }
/*      */ 
/*      */   
/*      */   public void everyoneSleeping() {
/* 2316 */     this.J = !this.players.isEmpty();
/* 2317 */     Iterator iterator = this.players.iterator();
/*      */     
/* 2319 */     while (iterator.hasNext()) {
/* 2320 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*      */ 
/*      */       
/* 2323 */       if (!entityhuman.isSleeping() && !entityhuman.fauxSleeping) {
/* 2324 */         this.J = false;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSleepStatus() {
/* 2334 */     if (!this.isStatic) {
/* 2335 */       everyoneSleeping();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void s() {
/* 2341 */     this.J = false;
/* 2342 */     Iterator iterator = this.players.iterator();
/*      */     
/* 2344 */     while (iterator.hasNext()) {
/* 2345 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*      */       
/* 2347 */       if (entityhuman.isSleeping()) {
/* 2348 */         entityhuman.a(false, false, true);
/*      */       }
/*      */     } 
/*      */     
/* 2352 */     y();
/*      */   }
/*      */   
/*      */   public boolean everyoneDeeplySleeping() {
/* 2356 */     if (this.J && !this.isStatic) {
/* 2357 */       EntityHuman entityhuman; Iterator iterator = this.players.iterator();
/*      */ 
/*      */       
/* 2360 */       boolean foundActualSleepers = false;
/*      */ 
/*      */ 
/*      */       
/*      */       do {
/* 2365 */         if (!iterator.hasNext())
/*      */         {
/* 2367 */           return foundActualSleepers;
/*      */         }
/*      */         
/* 2370 */         entityhuman = (EntityHuman)iterator.next();
/*      */         
/* 2372 */         if (!entityhuman.isDeeplySleeping())
/* 2373 */           continue;  foundActualSleepers = true;
/*      */       }
/* 2375 */       while (entityhuman.isDeeplySleeping() || entityhuman.fauxSleeping);
/*      */ 
/*      */       
/* 2378 */       return false;
/*      */     } 
/* 2380 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2385 */   public float c(float f) { return (this.k + (this.l - this.k) * f) * d(f); }
/*      */ 
/*      */ 
/*      */   
/* 2389 */   public float d(float f) { return this.i + (this.j - this.i) * f; }
/*      */ 
/*      */ 
/*      */   
/* 2393 */   public boolean u() { return (c(1.0F) > 0.9D); }
/*      */ 
/*      */ 
/*      */   
/* 2397 */   public boolean v() { return (d(1.0F) > 0.2D); }
/*      */ 
/*      */   
/*      */   public boolean s(int i, int j, int k) {
/* 2401 */     if (!v())
/* 2402 */       return false; 
/* 2403 */     if (!isChunkLoaded(i, j, k))
/* 2404 */       return false; 
/* 2405 */     if (e(i, k) > j) {
/* 2406 */       return false;
/*      */     }
/* 2408 */     BiomeBase biomebase = getWorldChunkManager().getBiome(i, k);
/*      */     
/* 2410 */     return biomebase.c() ? false : biomebase.d();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2415 */   public void a(String s, WorldMapBase worldmapbase) { this.worldMaps.a(s, worldmapbase); }
/*      */ 
/*      */ 
/*      */   
/* 2419 */   public WorldMapBase a(Class oclass, String s) { return this.worldMaps.a(oclass, s); }
/*      */ 
/*      */ 
/*      */   
/* 2423 */   public int b(String s) { return this.worldMaps.a(s); }
/*      */ 
/*      */ 
/*      */   
/* 2427 */   public void e(int i, int j, int k, int l, int i1) { a((EntityHuman)null, i, j, k, l, i1); }
/*      */ 
/*      */   
/*      */   public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
/* 2431 */     for (int j1 = 0; j1 < this.u.size(); j1++) {
/* 2432 */       ((IWorldAccess)this.u.get(j1)).a(entityhuman, i, j, k, l, i1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2438 */   public UUID getUUID() { return this.w.getUUID(); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\World.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */