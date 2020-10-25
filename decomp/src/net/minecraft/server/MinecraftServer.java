/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import jline.ConsoleReader;
/*     */ import joptsimple.OptionSet;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.LoggerOutputStream;
/*     */ import org.bukkit.craftbukkit.command.ColouredConsoleSender;
/*     */ import org.bukkit.craftbukkit.scheduler.CraftScheduler;
/*     */ import org.bukkit.craftbukkit.util.ServerShutdownThread;
/*     */ import org.bukkit.event.server.ServerCommandEvent;
/*     */ import org.bukkit.event.world.WorldInitEvent;
/*     */ import org.bukkit.event.world.WorldLoadEvent;
/*     */ import org.bukkit.event.world.WorldSaveEvent;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.PluginLoadOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinecraftServer
/*     */   implements Runnable, ICommandListener
/*     */ {
/*  37 */   public static Logger log = Logger.getLogger("Minecraft");
/*  38 */   public static HashMap trackerList = new HashMap(); public NetworkListenThread networkListenThread; public PropertyManager propertyManager; public ServerConfigurationManager serverConfigurationManager; public ConsoleCommandHandler consoleCommandHandler; private boolean isRunning; public boolean isStopped; int ticks;
/*     */   public String i;
/*     */   public int j;
/*     */   private List r;
/*     */   
/*     */   public MinecraftServer(OptionSet options) {
/*  44 */     this.isRunning = true;
/*  45 */     this.isStopped = false;
/*  46 */     this.ticks = 0;
/*     */ 
/*     */     
/*  49 */     this.r = new ArrayList();
/*  50 */     this.s = Collections.synchronizedList(new ArrayList());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.worlds = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     new ThreadSleepForever(this);
/*     */ 
/*     */     
/*  70 */     this.options = options;
/*     */     try {
/*  72 */       this.reader = new ConsoleReader();
/*  73 */     } catch (IOException ex) {
/*  74 */       Logger.getLogger(MinecraftServer.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } 
/*  76 */     Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
/*     */   }
/*     */   private List s; public boolean onlineMode; public boolean spawnAnimals; public boolean pvpMode; public boolean allowFlight; public List<WorldServer> worlds; public CraftServer server; public OptionSet options; public ColouredConsoleSender console; public ConsoleReader reader; public static int currentTick;
/*     */   
/*     */   private boolean init() throws UnknownHostException {
/*  81 */     this.consoleCommandHandler = new ConsoleCommandHandler(this);
/*  82 */     ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
/*     */     
/*  84 */     threadcommandreader.setDaemon(true);
/*  85 */     threadcommandreader.start();
/*  86 */     ConsoleLogManager.init(this);
/*     */ 
/*     */     
/*  89 */     System.setOut(new PrintStream(new LoggerOutputStream(log, Level.INFO), true));
/*  90 */     System.setErr(new PrintStream(new LoggerOutputStream(log, Level.SEVERE), true));
/*     */ 
/*     */     
/*  93 */     log.info("Starting minecraft server version Beta 1.7.3");
/*  94 */     if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
/*  95 */       log.warning("**** NOT ENOUGH RAM!");
/*  96 */       log.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
/*     */     } 
/*     */     
/*  99 */     log.info("Loading properties");
/* 100 */     this.propertyManager = new PropertyManager(this.options);
/* 101 */     String s = this.propertyManager.getString("server-ip", "");
/*     */     
/* 103 */     this.onlineMode = this.propertyManager.getBoolean("online-mode", true);
/* 104 */     this.spawnAnimals = this.propertyManager.getBoolean("spawn-animals", true);
/* 105 */     this.pvpMode = this.propertyManager.getBoolean("pvp", true);
/* 106 */     this.allowFlight = this.propertyManager.getBoolean("allow-flight", false);
/* 107 */     InetAddress inetaddress = null;
/*     */     
/* 109 */     if (s.length() > 0) {
/* 110 */       inetaddress = InetAddress.getByName(s);
/*     */     }
/*     */     
/* 113 */     int i = this.propertyManager.getInt("server-port", 25565);
/*     */     
/* 115 */     log.info("Starting Minecraft server on " + ((s.length() == 0) ? "*" : s) + ":" + i);
/*     */     
/*     */     try {
/* 118 */       this.networkListenThread = new NetworkListenThread(this, inetaddress, i);
/* 119 */     } catch (Throwable ioexception) {
/* 120 */       log.warning("**** FAILED TO BIND TO PORT!");
/* 121 */       log.log(Level.WARNING, "The exception was: " + ioexception.toString());
/* 122 */       log.warning("Perhaps a server is already running on that port?");
/* 123 */       return false;
/*     */     } 
/*     */     
/* 126 */     if (!this.onlineMode) {
/* 127 */       log.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
/* 128 */       log.warning("The server will make no attempt to authenticate usernames. Beware.");
/* 129 */       log.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
/* 130 */       log.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
/*     */     } 
/*     */     
/* 133 */     this.serverConfigurationManager = new ServerConfigurationManager(this);
/*     */     
/* 135 */     long j = System.nanoTime();
/* 136 */     String s1 = this.propertyManager.getString("level-name", "world");
/* 137 */     String s2 = this.propertyManager.getString("level-seed", "");
/* 138 */     long k = (new Random()).nextLong();
/*     */     
/* 140 */     if (s2.length() > 0) {
/*     */       try {
/* 142 */         k = Long.parseLong(s2);
/* 143 */       } catch (NumberFormatException numberformatexception) {
/* 144 */         k = s2.hashCode();
/*     */       } 
/*     */     }
/*     */     
/* 148 */     log.info("Preparing level \"" + s1 + "\"");
/* 149 */     a(new WorldLoaderServer(new File(".")), s1, k);
/*     */ 
/*     */     
/* 152 */     long elapsed = System.nanoTime() - j;
/* 153 */     String time = String.format("%.3fs", new Object[] { Double.valueOf(elapsed / 1.0E10D) });
/* 154 */     log.info("Done (" + time + ")! For help, type \"help\" or \"?\"");
/*     */     
/* 156 */     if (this.propertyManager.properties.containsKey("spawn-protection")) {
/* 157 */       log.info("'spawn-protection' in server.properties has been moved to 'settings.spawn-radius' in bukkit.yml. I will move your config for you.");
/* 158 */       this.server.setSpawnRadius(this.propertyManager.getInt("spawn-protection", 16));
/* 159 */       this.propertyManager.properties.remove("spawn-protection");
/* 160 */       this.propertyManager.savePropertiesFile();
/*     */     } 
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   private void a(Convertable convertable, String s, long i) {
/* 166 */     if (convertable.isConvertable(s)) {
/* 167 */       log.info("Converting map!");
/* 168 */       convertable.convert(s, new ConvertProgressUpdater(this));
/*     */     } 
/*     */ 
/*     */     
/* 172 */     for (j = 0; j < (this.propertyManager.getBoolean("allow-nether", true) ? 2 : 1); j++) {
/*     */       WorldServer world;
/* 174 */       int dimension = (j == 0) ? 0 : -1;
/* 175 */       String worldType = World.Environment.getEnvironment(dimension).toString().toLowerCase();
/* 176 */       String name = (dimension == 0) ? s : (s + "_" + worldType);
/*     */       
/* 178 */       ChunkGenerator gen = this.server.getGenerator(name);
/*     */       
/* 180 */       if (j == 0) {
/* 181 */         world = new WorldServer(this, new ServerNBTManager(new File("."), s, true), s, dimension, i, World.Environment.getEnvironment(dimension), gen);
/*     */       } else {
/* 183 */         String dim = "DIM-1";
/*     */         
/* 185 */         File newWorld = new File(new File(name), dim);
/* 186 */         File oldWorld = new File(new File(s), dim);
/*     */         
/* 188 */         if (!newWorld.isDirectory() && oldWorld.isDirectory()) {
/* 189 */           log.info("---- Migration of old " + worldType + " folder required ----");
/* 190 */           log.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
/* 191 */           log.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
/* 192 */           log.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
/*     */           
/* 194 */           if (newWorld.exists()) {
/* 195 */             log.severe("A file or folder already exists at " + newWorld + "!");
/* 196 */             log.info("---- Migration of old " + worldType + " folder failed ----");
/* 197 */           } else if (newWorld.getParentFile().mkdirs()) {
/* 198 */             if (oldWorld.renameTo(newWorld)) {
/* 199 */               log.info("Success! To restore the nether in the future, simply move " + newWorld + " to " + oldWorld);
/* 200 */               log.info("---- Migration of old " + worldType + " folder complete ----");
/*     */             } else {
/* 202 */               log.severe("Could not move folder " + oldWorld + " to " + newWorld + "!");
/* 203 */               log.info("---- Migration of old " + worldType + " folder failed ----");
/*     */             } 
/*     */           } else {
/* 206 */             log.severe("Could not create path for " + newWorld + "!");
/* 207 */             log.info("---- Migration of old " + worldType + " folder failed ----");
/*     */           } 
/*     */         } 
/*     */         
/* 211 */         world = new SecondaryWorldServer(this, new ServerNBTManager(new File("."), name, true), name, dimension, i, (WorldServer)this.worlds.get(0), World.Environment.getEnvironment(dimension), gen);
/*     */       } 
/*     */       
/* 214 */       if (gen != null) {
/* 215 */         world.getWorld().getPopulators().addAll(gen.getDefaultPopulators(world.getWorld()));
/*     */       }
/*     */       
/* 218 */       this.server.getPluginManager().callEvent(new WorldInitEvent(world.getWorld()));
/*     */       
/* 220 */       world.tracker = new EntityTracker(this, dimension);
/* 221 */       world.addIWorldAccess(new WorldManager(this, world));
/* 222 */       world.spawnMonsters = this.propertyManager.getBoolean("spawn-monsters", true) ? 1 : 0;
/* 223 */       world.setSpawnFlags(this.propertyManager.getBoolean("spawn-monsters", true), this.spawnAnimals);
/* 224 */       this.worlds.add(world);
/* 225 */       this.serverConfigurationManager.setPlayerFileData((WorldServer[])this.worlds.toArray(new WorldServer[0]));
/*     */     } 
/*     */ 
/*     */     
/* 229 */     short short1 = 196;
/* 230 */     long k = System.currentTimeMillis();
/*     */ 
/*     */     
/* 233 */     for (l = 0; l < this.worlds.size(); l++) {
/*     */       
/* 235 */       WorldServer worldserver = (WorldServer)this.worlds.get(l);
/* 236 */       log.info("Preparing start region for level " + l + " (Seed: " + worldserver.getSeed() + ")");
/* 237 */       if (worldserver.getWorld().getKeepSpawnInMemory()) {
/*     */         
/* 239 */         ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*     */         
/* 241 */         for (int i1 = -short1; i1 <= short1 && this.isRunning; i1 += 16) {
/* 242 */           for (int j1 = -short1; j1 <= short1 && this.isRunning; j1 += 16) {
/* 243 */             long k1 = System.currentTimeMillis();
/*     */             
/* 245 */             if (k1 < k) {
/* 246 */               k = k1;
/*     */             }
/*     */             
/* 249 */             if (k1 > k + 1000L) {
/* 250 */               int l1 = (short1 * 2 + 1) * (short1 * 2 + 1);
/* 251 */               int i2 = (i1 + short1) * (short1 * 2 + 1) + j1 + 1;
/*     */               
/* 253 */               a("Preparing spawn area", i2 * 100 / l1);
/* 254 */               k = k1;
/*     */             } 
/*     */             
/* 257 */             worldserver.chunkProviderServer.getChunkAt(chunkcoordinates.x + i1 >> 4, chunkcoordinates.z + j1 >> 4);
/*     */             
/* 259 */             while (worldserver.doLighting() && this.isRunning);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     for (World world : this.worlds) {
/* 269 */       this.server.getPluginManager().callEvent(new WorldLoadEvent(world.getWorld()));
/*     */     }
/*     */ 
/*     */     
/* 273 */     e();
/*     */   }
/*     */   
/*     */   private void a(String s, int i) {
/* 277 */     this.i = s;
/* 278 */     this.j = i;
/* 279 */     log.info(s + ": " + i + "%");
/*     */   }
/*     */   
/*     */   private void e() {
/* 283 */     this.i = null;
/* 284 */     this.j = 0;
/*     */     
/* 286 */     this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
/*     */   }
/*     */   
/*     */   void saveChunks() {
/* 290 */     log.info("Saving chunks");
/*     */ 
/*     */     
/* 293 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 294 */       WorldServer worldserver = (WorldServer)this.worlds.get(i);
/*     */       
/* 296 */       worldserver.save(true, (IProgressUpdate)null);
/* 297 */       worldserver.saveLevel();
/*     */       
/* 299 */       WorldSaveEvent event = new WorldSaveEvent(worldserver.getWorld());
/* 300 */       this.server.getPluginManager().callEvent(event);
/*     */     } 
/*     */     
/* 303 */     WorldServer world = (WorldServer)this.worlds.get(0);
/* 304 */     if (!world.canSave) {
/* 305 */       this.serverConfigurationManager.savePlayers();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 311 */     log.info("Stopping server");
/*     */     
/* 313 */     if (this.server != null) {
/* 314 */       this.server.disablePlugins();
/*     */     }
/*     */ 
/*     */     
/* 318 */     if (this.serverConfigurationManager != null) {
/* 319 */       this.serverConfigurationManager.savePlayers();
/*     */     }
/*     */ 
/*     */     
/* 323 */     WorldServer worldserver = (WorldServer)this.worlds.get(0);
/*     */     
/* 325 */     if (worldserver != null) {
/* 326 */       saveChunks();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 332 */   public void a() { this.isRunning = false; }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 337 */       if (init()) {
/* 338 */         long i = System.currentTimeMillis();
/*     */         
/* 340 */         for (long j = 0L; this.isRunning; Thread.sleep(1L)) {
/* 341 */           long k = System.currentTimeMillis();
/* 342 */           long l = k - i;
/*     */           
/* 344 */           if (l > 2000L) {
/* 345 */             log.warning("Can't keep up! Did the system time change, or is the server overloaded?");
/* 346 */             l = 2000L;
/*     */           } 
/*     */           
/* 349 */           if (l < 0L) {
/* 350 */             log.warning("Time ran backwards! Did the system time change?");
/* 351 */             l = 0L;
/*     */           } 
/*     */           
/* 354 */           j += l;
/* 355 */           i = k;
/* 356 */           if (((WorldServer)this.worlds.get(0)).everyoneDeeplySleeping()) {
/* 357 */             h();
/* 358 */             j = 0L;
/*     */           } else {
/* 360 */             while (j > 50L) {
/* 361 */               currentTick = (int)(System.currentTimeMillis() / 50L);
/* 362 */               j -= 50L;
/* 363 */               h();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 368 */         while (this.isRunning) {
/* 369 */           b();
/*     */           
/*     */           try {
/* 372 */             Thread.sleep(10L);
/* 373 */           } catch (InterruptedException interruptedexception) {
/* 374 */             interruptedexception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/* 378 */     } catch (Throwable throwable) {
/* 379 */       throwable.printStackTrace();
/* 380 */       log.log(Level.SEVERE, "Unexpected exception", throwable);
/*     */       
/* 382 */       while (this.isRunning) {
/* 383 */         b();
/*     */         
/*     */         try {
/* 386 */           Thread.sleep(10L);
/* 387 */         } catch (InterruptedException interruptedexception1) {
/* 388 */           interruptedexception1.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 393 */         stop();
/* 394 */         this.isStopped = true;
/* 395 */       } catch (Throwable throwable1) {
/* 396 */         throwable1.printStackTrace();
/*     */       } finally {
/* 398 */         System.exit(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void h() {
/* 404 */     ArrayList arraylist = new ArrayList();
/* 405 */     Iterator iterator = trackerList.keySet().iterator();
/*     */     
/* 407 */     while (iterator.hasNext()) {
/* 408 */       String s = (String)iterator.next();
/* 409 */       int i = ((Integer)trackerList.get(s)).intValue();
/*     */       
/* 411 */       if (i > 0) {
/* 412 */         trackerList.put(s, Integer.valueOf(i - 1)); continue;
/*     */       } 
/* 414 */       arraylist.add(s);
/*     */     } 
/*     */ 
/*     */     
/*     */     int j;
/*     */     
/* 420 */     for (j = 0; j < arraylist.size(); j++) {
/* 421 */       trackerList.remove(arraylist.get(j));
/*     */     }
/*     */     
/* 424 */     AxisAlignedBB.a();
/* 425 */     Vec3D.a();
/* 426 */     this.ticks++;
/*     */     
/* 428 */     ((CraftScheduler)this.server.getScheduler()).mainThreadHeartbeat(this.ticks);
/*     */     
/* 430 */     for (j = 0; j < this.worlds.size(); j++) {
/*     */       
/* 432 */       WorldServer worldserver = (WorldServer)this.worlds.get(j);
/*     */       
/* 434 */       if (this.ticks % 20 == 0)
/*     */       {
/* 436 */         for (int i = 0; i < this.serverConfigurationManager.players.size(); i++) {
/* 437 */           EntityPlayer entityplayer = (EntityPlayer)this.serverConfigurationManager.players.get(i);
/* 438 */           entityplayer.netServerHandler.sendPacket(new Packet4UpdateTime(entityplayer.getPlayerTime()));
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 443 */       worldserver.doTick();
/*     */       
/* 445 */       while (worldserver.doLighting());
/*     */ 
/*     */ 
/*     */       
/* 449 */       worldserver.cleanUp();
/*     */     } 
/*     */ 
/*     */     
/* 453 */     this.networkListenThread.a();
/* 454 */     this.serverConfigurationManager.b();
/*     */ 
/*     */     
/* 457 */     for (j = 0; j < this.worlds.size(); j++) {
/* 458 */       ((WorldServer)this.worlds.get(j)).tracker.updatePlayers();
/*     */     }
/*     */ 
/*     */     
/* 462 */     for (j = 0; j < this.r.size(); j++) {
/* 463 */       ((IUpdatePlayerListBox)this.r.get(j)).a();
/*     */     }
/*     */     
/*     */     try {
/* 467 */       b();
/* 468 */     } catch (Exception exception) {
/* 469 */       log.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 474 */   public void issueCommand(String s, ICommandListener icommandlistener) { this.s.add(new ServerCommand(s, icommandlistener)); }
/*     */ 
/*     */   
/*     */   public void b() {
/* 478 */     while (this.s.size() > 0) {
/* 479 */       ServerCommand servercommand = (ServerCommand)this.s.remove(0);
/*     */ 
/*     */       
/* 482 */       ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.command);
/* 483 */       this.server.getPluginManager().callEvent(event);
/* 484 */       servercommand = new ServerCommand(event.getCommand(), servercommand.b);
/*     */ 
/*     */ 
/*     */       
/* 488 */       this.server.dispatchCommand(this.console, servercommand);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 493 */   public void a(IUpdatePlayerListBox iupdateplayerlistbox) { this.r.add(iupdateplayerlistbox); }
/*     */ 
/*     */   
/*     */   public static void main(OptionSet options) {
/* 497 */     StatisticList.a();
/*     */     
/*     */     try {
/* 500 */       MinecraftServer minecraftserver = new MinecraftServer(options);
/*     */ 
/*     */ 
/*     */       
/* 504 */       (new ThreadServerApplication("Server thread", minecraftserver)).start();
/* 505 */     } catch (Exception exception) {
/* 506 */       log.log(Level.SEVERE, "Failed to start the minecraft server", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 511 */   public File a(String s) { return new File(s); }
/*     */ 
/*     */ 
/*     */   
/* 515 */   public void sendMessage(String s) { log.info(s); }
/*     */ 
/*     */ 
/*     */   
/* 519 */   public void c(String s) { log.warning(s); }
/*     */ 
/*     */ 
/*     */   
/* 523 */   public String getName() { return "CONSOLE"; }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServer getWorldServer(int i) {
/* 528 */     for (WorldServer world : this.worlds) {
/* 529 */       if (world.dimension == i) {
/* 530 */         return world;
/*     */       }
/*     */     } 
/*     */     
/* 534 */     return (WorldServer)this.worlds.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 539 */   public EntityTracker getTracker(int i) { return (getWorldServer(i)).tracker; }
/*     */ 
/*     */ 
/*     */   
/* 543 */   public static boolean isRunning(MinecraftServer minecraftserver) { return minecraftserver.isRunning; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */