/*     */ package org.bukkit.craftbukkit;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import jline.ConsoleReader;
/*     */ import net.minecraft.server.ChunkCoordinates;
/*     */ import net.minecraft.server.ConvertProgressUpdater;
/*     */ import net.minecraft.server.EntityPlayer;
/*     */ import net.minecraft.server.EntityTracker;
/*     */ import net.minecraft.server.IProgressUpdate;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.PropertyManager;
/*     */ import net.minecraft.server.ServerCommand;
/*     */ import net.minecraft.server.ServerConfigurationManager;
/*     */ import net.minecraft.server.WorldLoaderServer;
/*     */ import net.minecraft.server.WorldMap;
/*     */ import net.minecraft.server.WorldMapCollection;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.craftbukkit.inventory.CraftFurnaceRecipe;
/*     */ import org.bukkit.craftbukkit.inventory.CraftRecipe;
/*     */ import org.bukkit.craftbukkit.map.CraftMapView;
/*     */ import org.bukkit.craftbukkit.scheduler.CraftScheduler;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.world.WorldInitEvent;
/*     */ import org.bukkit.event.world.WorldLoadEvent;
/*     */ import org.bukkit.event.world.WorldSaveEvent;
/*     */ import org.bukkit.event.world.WorldUnloadEvent;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.inventory.FurnaceRecipe;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.inventory.ShapelessRecipe;
/*     */ import org.bukkit.map.MapView;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginLoadOrder;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.ServicesManager;
/*     */ import org.bukkit.plugin.SimplePluginManager;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitWorker;
/*     */ import org.bukkit.util.config.Configuration;
/*     */ import org.bukkit.util.config.ConfigurationNode;
/*     */ import org.bukkit.util.permissions.DefaultPermissions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*     */ import org.yaml.snakeyaml.error.MarkedYAMLException;
/*     */ 
/*     */ public final class CraftServer implements Server {
/*     */   private final String serverName = "Craftbukkit";
/*     */   private final String serverVersion;
/*     */   private final String protocolVersion = "1.7.3";
/*     */   private final ServicesManager servicesManager;
/*     */   private final BukkitScheduler scheduler;
/*     */   private final SimpleCommandMap commandMap;
/*     */   
/*     */   public CraftServer(MinecraftServer console, ServerConfigurationManager server) {
/*  80 */     this.serverName = "Craftbukkit";
/*     */     
/*  82 */     this.protocolVersion = "1.7.3";
/*  83 */     this.servicesManager = new SimpleServicesManager();
/*  84 */     this.scheduler = new CraftScheduler(this);
/*  85 */     this.commandMap = new SimpleCommandMap(this);
/*  86 */     this.pluginManager = new SimplePluginManager(this, this.commandMap);
/*     */ 
/*     */     
/*  89 */     this.worlds = new LinkedHashMap();
/*     */     
/*  91 */     this.yaml = new Yaml(new SafeConstructor());
/*     */ 
/*     */     
/*  94 */     this.console = console;
/*  95 */     this.server = server;
/*  96 */     this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
/*     */     
/*  98 */     Bukkit.setServer(this);
/*     */     
/* 100 */     this.configuration = new Configuration((File)console.options.valueOf("bukkit-settings"));
/* 101 */     loadConfig();
/* 102 */     loadPlugins();
/* 103 */     enablePlugins(PluginLoadOrder.STARTUP);
/*     */     
/* 105 */     ChunkCompressionThread.startThread();
/*     */   }
/*     */   private final PluginManager pluginManager; protected final MinecraftServer console; protected final ServerConfigurationManager server; private final Map<String, World> worlds; private final Configuration configuration; private final Yaml yaml;
/*     */   private void loadConfig() {
/* 109 */     this.configuration.load();
/* 110 */     this.configuration.getString("database.url", "jdbc:sqlite:{DIR}{NAME}.db");
/* 111 */     this.configuration.getString("database.username", "bukkit");
/* 112 */     this.configuration.getString("database.password", "walrus");
/* 113 */     this.configuration.getString("database.driver", "org.sqlite.JDBC");
/* 114 */     this.configuration.getString("database.isolation", "SERIALIZABLE");
/*     */     
/* 116 */     this.configuration.getString("settings.update-folder", "update");
/* 117 */     this.configuration.getInt("settings.spawn-radius", 16);
/*     */     
/* 119 */     this.configuration.getString("settings.permissions-file", "permissions.yml");
/*     */     
/* 121 */     if (this.configuration.getNode("aliases") == null) {
/* 122 */       List<String> icanhasbukkit = new ArrayList<String>();
/* 123 */       icanhasbukkit.add("version");
/* 124 */       this.configuration.setProperty("aliases.icanhasbukkit", icanhasbukkit);
/*     */     } 
/* 126 */     this.configuration.save();
/*     */   }
/*     */   
/*     */   public void loadPlugins() {
/* 130 */     this.pluginManager.registerInterface(org.bukkit.plugin.java.JavaPluginLoader.class);
/*     */     
/* 132 */     File pluginFolder = (File)this.console.options.valueOf("plugins");
/*     */     
/* 134 */     if (pluginFolder.exists()) {
/* 135 */       Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
/* 136 */       for (Plugin plugin : plugins) {
/*     */         try {
/* 138 */           plugin.onLoad();
/* 139 */         } catch (Throwable ex) {
/* 140 */           Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 144 */       pluginFolder.mkdir();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enablePlugins(PluginLoadOrder type) {
/* 149 */     Plugin[] plugins = this.pluginManager.getPlugins();
/*     */     
/* 151 */     for (Plugin plugin : plugins) {
/* 152 */       if (!plugin.isEnabled() && plugin.getDescription().getLoad() == type) {
/* 153 */         loadPlugin(plugin);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     if (type == PluginLoadOrder.POSTWORLD) {
/* 158 */       this.commandMap.registerServerAliases();
/* 159 */       loadCustomPermissions();
/* 160 */       DefaultPermissions.registerCorePermissions();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 165 */   public void disablePlugins() { this.pluginManager.disablePlugins(); }
/*     */ 
/*     */   
/*     */   private void loadPlugin(Plugin plugin) {
/*     */     try {
/* 170 */       this.pluginManager.enablePlugin(plugin);
/*     */       
/* 172 */       List<Permission> perms = plugin.getDescription().getPermissions();
/*     */       
/* 174 */       for (Permission perm : perms) {
/*     */         try {
/* 176 */           this.pluginManager.addPermission(perm);
/* 177 */         } catch (IllegalArgumentException ex) {
/* 178 */           getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
/*     */         } 
/*     */       } 
/* 181 */     } catch (Throwable ex) {
/* 182 */       Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 187 */   public String getName() { return "Craftbukkit"; }
/*     */ 
/*     */ 
/*     */   
/* 191 */   public String getVersion() { return this.serverVersion + " (MC: " + "1.7.3" + ")"; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player[] getOnlinePlayers() {
/* 196 */     List<EntityPlayer> online = this.server.players;
/* 197 */     Player[] players = new Player[online.size()];
/*     */     
/* 199 */     for (int i = 0; i < players.length; i++) {
/* 200 */       players[i] = ((EntityPlayer)online.get(i)).netServerHandler.getPlayer();
/*     */     }
/*     */     
/* 203 */     return players;
/*     */   }
/*     */   
/*     */   public Player getPlayer(String name) {
/* 207 */     Player[] players = getOnlinePlayers();
/*     */     
/* 209 */     Player found = null;
/* 210 */     String lowerName = name.toLowerCase();
/* 211 */     int delta = Integer.MAX_VALUE;
/* 212 */     for (Player player : players) {
/* 213 */       if (player.getName().toLowerCase().startsWith(lowerName)) {
/* 214 */         int curDelta = player.getName().length() - lowerName.length();
/* 215 */         if (curDelta < delta) {
/* 216 */           found = player;
/* 217 */           delta = curDelta;
/*     */         } 
/* 219 */         if (curDelta == 0)
/*     */           break; 
/*     */       } 
/* 222 */     }  return found;
/*     */   }
/*     */   
/*     */   public Player getPlayerExact(String name) {
/* 226 */     String lname = name.toLowerCase();
/*     */     
/* 228 */     for (Player player : getOnlinePlayers()) {
/* 229 */       if (player.getName().equalsIgnoreCase(lname)) {
/* 230 */         return player;
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 238 */   public int broadcastMessage(String message) { return broadcast(message, "bukkit.broadcast.user"); }
/*     */ 
/*     */ 
/*     */   
/* 242 */   public Player getPlayer(EntityPlayer entity) { return entity.netServerHandler.getPlayer(); }
/*     */ 
/*     */   
/*     */   public List<Player> matchPlayer(String partialName) {
/* 246 */     List<Player> matchedPlayers = new ArrayList<Player>();
/*     */     
/* 248 */     for (Player iterPlayer : getOnlinePlayers()) {
/* 249 */       String iterPlayerName = iterPlayer.getName();
/*     */       
/* 251 */       if (partialName.equalsIgnoreCase(iterPlayerName)) {
/*     */         
/* 253 */         matchedPlayers.clear();
/* 254 */         matchedPlayers.add(iterPlayer);
/*     */         break;
/*     */       } 
/* 257 */       if (iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase()) != -1)
/*     */       {
/* 259 */         matchedPlayers.add(iterPlayer);
/*     */       }
/*     */     } 
/*     */     
/* 263 */     return matchedPlayers;
/*     */   }
/*     */ 
/*     */   
/* 267 */   public int getMaxPlayers() { return this.server.maxPlayers; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public int getPort() { return getConfigInt("server-port", 25565); }
/*     */ 
/*     */ 
/*     */   
/* 277 */   public int getViewDistance() { return getConfigInt("view-distance", 10); }
/*     */ 
/*     */ 
/*     */   
/* 281 */   public String getIp() { return getConfigString("server-ip", ""); }
/*     */ 
/*     */ 
/*     */   
/* 285 */   public String getServerName() { return getConfigString("server-name", "Unknown Server"); }
/*     */ 
/*     */ 
/*     */   
/* 289 */   public String getServerId() { return getConfigString("server-id", "unnamed"); }
/*     */ 
/*     */ 
/*     */   
/* 293 */   public boolean getAllowNether() { return getConfigBoolean("allow-nether", true); }
/*     */ 
/*     */ 
/*     */   
/* 297 */   public boolean hasWhitelist() { return getConfigBoolean("white-list", false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   private String getConfigString(String variable, String defaultValue) { return this.console.propertyManager.getString(variable, defaultValue); }
/*     */ 
/*     */ 
/*     */   
/* 306 */   private int getConfigInt(String variable, int defaultValue) { return this.console.propertyManager.getInt(variable, defaultValue); }
/*     */ 
/*     */ 
/*     */   
/* 310 */   private boolean getConfigBoolean(String variable, boolean defaultValue) { return this.console.propertyManager.getBoolean(variable, defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 316 */   public String getUpdateFolder() { return this.configuration.getString("settings.update-folder", "update"); }
/*     */ 
/*     */ 
/*     */   
/* 320 */   public PluginManager getPluginManager() { return this.pluginManager; }
/*     */ 
/*     */ 
/*     */   
/* 324 */   public BukkitScheduler getScheduler() { return this.scheduler; }
/*     */ 
/*     */ 
/*     */   
/* 328 */   public ServicesManager getServicesManager() { return this.servicesManager; }
/*     */ 
/*     */ 
/*     */   
/* 332 */   public List<World> getWorlds() { return new ArrayList(this.worlds.values()); }
/*     */ 
/*     */ 
/*     */   
/* 336 */   public ServerConfigurationManager getHandle() { return this.server; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public boolean dispatchCommand(CommandSender sender, ServerCommand serverCommand) { return dispatchCommand(sender, serverCommand.command); }
/*     */ 
/*     */   
/*     */   public boolean dispatchCommand(CommandSender sender, String commandLine) {
/* 346 */     if (this.commandMap.dispatch(sender, commandLine)) {
/* 347 */       return true;
/*     */     }
/*     */     
/* 350 */     sender.sendMessage("Unknown command. Type \"help\" for help.");
/*     */     
/* 352 */     return false;
/*     */   }
/*     */   
/*     */   public void reload() {
/* 356 */     loadConfig();
/* 357 */     PropertyManager config = new PropertyManager(this.console.options);
/*     */     
/* 359 */     this.console.propertyManager = config;
/*     */     
/* 361 */     boolean animals = config.getBoolean("spawn-animals", this.console.spawnAnimals);
/* 362 */     boolean monsters = config.getBoolean("spawn-monsters", (((WorldServer)this.console.worlds.get(0)).spawnMonsters > 0));
/*     */     
/* 364 */     this.console.onlineMode = config.getBoolean("online-mode", this.console.onlineMode);
/* 365 */     this.console.spawnAnimals = config.getBoolean("spawn-animals", this.console.spawnAnimals);
/* 366 */     this.console.pvpMode = config.getBoolean("pvp", this.console.pvpMode);
/* 367 */     this.console.allowFlight = config.getBoolean("allow-flight", this.console.allowFlight);
/*     */     
/* 369 */     for (WorldServer world : this.console.worlds) {
/* 370 */       world.spawnMonsters = monsters ? 1 : 0;
/* 371 */       world.setSpawnFlags(monsters, animals);
/*     */     } 
/*     */     
/* 374 */     this.pluginManager.clearPlugins();
/* 375 */     this.commandMap.clearCommands();
/*     */     
/* 377 */     int pollCount = 0;
/*     */ 
/*     */     
/* 380 */     while (pollCount < 50 && getScheduler().getActiveWorkers().size() > 0) {
/*     */       try {
/* 382 */         Thread.sleep(50L);
/* 383 */       } catch (InterruptedException e) {}
/* 384 */       pollCount++;
/*     */     } 
/*     */     
/* 387 */     List<BukkitWorker> overdueWorkers = getScheduler().getActiveWorkers();
/* 388 */     for (BukkitWorker worker : overdueWorkers) {
/* 389 */       Plugin plugin = worker.getOwner();
/* 390 */       String author = "<NoAuthorGiven>";
/* 391 */       if (plugin.getDescription().getAuthors().size() > 0) {
/* 392 */         author = (String)plugin.getDescription().getAuthors().get(0);
/*     */       }
/* 394 */       getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", new Object[] { author, plugin.getDescription().getName(), "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin" }));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     loadPlugins();
/* 402 */     enablePlugins(PluginLoadOrder.STARTUP);
/* 403 */     enablePlugins(PluginLoadOrder.POSTWORLD);
/*     */   }
/*     */   private void loadCustomPermissions() {
/*     */     Map<String, Map<String, Object>> perms;
/* 407 */     file = new File(this.configuration.getString("settings.permissions-file"));
/*     */ 
/*     */     
/*     */     try {
/* 411 */       stream = new FileInputStream(file);
/* 412 */     } catch (FileNotFoundException ex) {
/*     */       try {
/*     */         return;
/*     */       } finally {
/* 416 */         Exception exception = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 423 */       perms = (Map)this.yaml.load(stream);
/* 424 */     } catch (MarkedYAMLException ex) {
/* 425 */       getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
/*     */       return;
/* 427 */     } catch (Throwable ex) {
/* 428 */       getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
/*     */       return;
/*     */     } finally {
/*     */       try {
/* 432 */         stream.close();
/* 433 */       } catch (IOException ex) {}
/*     */     } 
/*     */     
/* 436 */     if (perms == null) {
/* 437 */       getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
/*     */       
/*     */       return;
/*     */     } 
/* 441 */     Set<String> keys = perms.keySet();
/*     */     
/* 443 */     for (String name : keys) {
/*     */       try {
/* 445 */         this.pluginManager.addPermission(Permission.loadPermission(name, (Map)perms.get(name)));
/* 446 */       } catch (Throwable ex) {
/* 447 */         Bukkit.getServer().getLogger().log(Level.SEVERE, "Permission node '" + name + "' in server config is invalid", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 454 */   public String toString() { return "CraftServer{serverName=Craftbukkit,serverVersion=" + this.serverVersion + ",protocolVersion=" + "1.7.3" + '}'; }
/*     */ 
/*     */ 
/*     */   
/* 458 */   public World createWorld(String name, World.Environment environment) { return createWorld(name, environment, (new Random()).nextLong()); }
/*     */ 
/*     */ 
/*     */   
/* 462 */   public World createWorld(String name, World.Environment environment, long seed) { return createWorld(name, environment, seed, null); }
/*     */ 
/*     */ 
/*     */   
/* 466 */   public World createWorld(String name, World.Environment environment, ChunkGenerator generator) { return createWorld(name, environment, (new Random()).nextLong(), generator); }
/*     */ 
/*     */   
/*     */   public World createWorld(String name, World.Environment environment, long seed, ChunkGenerator generator) {
/* 470 */     File folder = new File(name);
/* 471 */     World world = getWorld(name);
/*     */     
/* 473 */     if (world != null) {
/* 474 */       return world;
/*     */     }
/*     */     
/* 477 */     if (folder.exists() && !folder.isDirectory()) {
/* 478 */       throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
/*     */     }
/*     */     
/* 481 */     if (generator == null) {
/* 482 */       generator = getGenerator(name);
/*     */     }
/*     */     
/* 485 */     WorldLoaderServer worldLoaderServer = new WorldLoaderServer(folder);
/* 486 */     if (worldLoaderServer.isConvertable(name)) {
/* 487 */       getLogger().info("Converting world '" + name + "'");
/* 488 */       worldLoaderServer.convert(name, new ConvertProgressUpdater(this.console));
/*     */     } 
/*     */     
/* 491 */     int dimension = 10 + this.console.worlds.size();
/* 492 */     WorldServer internal = new WorldServer(this.console, new ServerNBTManager(new File("."), name, true), name, dimension, seed, environment, generator);
/*     */     
/* 494 */     if (!this.worlds.containsKey(name.toLowerCase())) {
/* 495 */       return null;
/*     */     }
/*     */     
/* 498 */     internal.worldMaps = ((WorldServer)this.console.worlds.get(0)).worldMaps;
/*     */     
/* 500 */     internal.tracker = new EntityTracker(this.console, dimension);
/* 501 */     internal.addIWorldAccess(new WorldManager(this.console, internal));
/* 502 */     internal.spawnMonsters = 1;
/* 503 */     internal.setSpawnFlags(true, true);
/* 504 */     this.console.worlds.add(internal);
/*     */     
/* 506 */     if (generator != null) {
/* 507 */       internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
/*     */     }
/*     */     
/* 510 */     this.pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
/* 511 */     System.out.print("Preparing start region for level " + (this.console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
/*     */     
/* 513 */     if (internal.getWorld().getKeepSpawnInMemory()) {
/* 514 */       short short1 = 196;
/* 515 */       long i = System.currentTimeMillis();
/* 516 */       for (int j = -short1; j <= short1; j += 16) {
/* 517 */         for (int k = -short1; k <= short1; k += 16) {
/* 518 */           long l = System.currentTimeMillis();
/*     */           
/* 520 */           if (l < i) {
/* 521 */             i = l;
/*     */           }
/*     */           
/* 524 */           if (l > i + 1000L) {
/* 525 */             int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
/* 526 */             int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
/*     */             
/* 528 */             System.out.println("Preparing spawn area for " + name + ", " + (j1 * 100 / i1) + "%");
/* 529 */             i = l;
/*     */           } 
/*     */           
/* 532 */           ChunkCoordinates chunkcoordinates = internal.getSpawn();
/* 533 */           internal.chunkProviderServer.getChunkAt(chunkcoordinates.x + j >> 4, chunkcoordinates.z + k >> 4);
/*     */           
/* 535 */           while (internal.doLighting());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 541 */     this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
/* 542 */     return internal.getWorld();
/*     */   }
/*     */ 
/*     */   
/* 546 */   public boolean unloadWorld(String name, boolean save) { return unloadWorld(getWorld(name), save); }
/*     */ 
/*     */   
/*     */   public boolean unloadWorld(World world, boolean save) {
/* 550 */     if (world == null) {
/* 551 */       return false;
/*     */     }
/*     */     
/* 554 */     WorldServer handle = ((CraftWorld)world).getHandle();
/*     */     
/* 556 */     if (!this.console.worlds.contains(handle)) {
/* 557 */       return false;
/*     */     }
/*     */     
/* 560 */     if (handle.dimension <= 1) {
/* 561 */       return false;
/*     */     }
/*     */     
/* 564 */     if (handle.players.size() > 0) {
/* 565 */       return false;
/*     */     }
/*     */     
/* 568 */     WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
/*     */     
/* 570 */     if (e.isCancelled()) {
/* 571 */       return false;
/*     */     }
/*     */     
/* 574 */     if (save) {
/* 575 */       handle.save(true, (IProgressUpdate)null);
/* 576 */       handle.saveLevel();
/* 577 */       WorldSaveEvent event = new WorldSaveEvent(handle.getWorld());
/* 578 */       getPluginManager().callEvent(event);
/*     */     } 
/*     */     
/* 581 */     this.worlds.remove(world.getName().toLowerCase());
/* 582 */     this.console.worlds.remove(this.console.worlds.indexOf(handle));
/*     */     
/* 584 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 588 */   public MinecraftServer getServer() { return this.console; }
/*     */ 
/*     */ 
/*     */   
/* 592 */   public World getWorld(String name) { return (World)this.worlds.get(name.toLowerCase()); }
/*     */ 
/*     */   
/*     */   public World getWorld(UUID uid) {
/* 596 */     for (World world : this.worlds.values()) {
/* 597 */       if (world.getUID().equals(uid)) {
/* 598 */         return world;
/*     */       }
/*     */     } 
/* 601 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWorld(World world) {
/* 606 */     if (getWorld(world.getUID()) != null) {
/* 607 */       System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
/*     */       return;
/*     */     } 
/* 610 */     this.worlds.put(world.getName().toLowerCase(), world);
/*     */   }
/*     */ 
/*     */   
/* 614 */   public Logger getLogger() { return MinecraftServer.log; }
/*     */ 
/*     */ 
/*     */   
/* 618 */   public ConsoleReader getReader() { return this.console.reader; }
/*     */ 
/*     */   
/*     */   public PluginCommand getPluginCommand(String name) {
/* 622 */     Command command = this.commandMap.getCommand(name);
/*     */     
/* 624 */     if (command instanceof PluginCommand) {
/* 625 */       return (PluginCommand)command;
/*     */     }
/* 627 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 632 */   public void savePlayers() { this.server.savePlayers(); }
/*     */ 
/*     */   
/*     */   public void configureDbConfig(ServerConfig config) {
/* 636 */     DataSourceConfig ds = new DataSourceConfig();
/* 637 */     ds.setDriver(this.configuration.getString("database.driver"));
/* 638 */     ds.setUrl(this.configuration.getString("database.url"));
/* 639 */     ds.setUsername(this.configuration.getString("database.username"));
/* 640 */     ds.setPassword(this.configuration.getString("database.password"));
/* 641 */     ds.setIsolationLevel(TransactionIsolation.getLevel(this.configuration.getString("database.isolation")));
/*     */     
/* 643 */     if (ds.getDriver().contains("sqlite")) {
/* 644 */       config.setDatabasePlatform(new SQLitePlatform());
/* 645 */       config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
/*     */     } 
/*     */     
/* 648 */     config.setDataSourceConfig(ds);
/*     */   }
/*     */   
/*     */   public boolean addRecipe(Recipe recipe) {
/*     */     CraftFurnaceRecipe craftFurnaceRecipe;
/* 653 */     if (recipe instanceof CraftRecipe) {
/* 654 */       craftFurnaceRecipe = (CraftRecipe)recipe;
/*     */     }
/* 656 */     else if (recipe instanceof ShapedRecipe) {
/* 657 */       craftFurnaceRecipe = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe);
/* 658 */     } else if (recipe instanceof ShapelessRecipe) {
/* 659 */       craftFurnaceRecipe = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
/* 660 */     } else if (recipe instanceof FurnaceRecipe) {
/* 661 */       craftFurnaceRecipe = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
/*     */     } else {
/* 663 */       return false;
/*     */     } 
/*     */     
/* 666 */     craftFurnaceRecipe.addToCraftingManager();
/* 667 */     return true;
/*     */   }
/*     */   
/*     */   public Map<String, String[]> getCommandAliases() {
/* 671 */     ConfigurationNode node = this.configuration.getNode("aliases");
/* 672 */     Map<String, String[]> result = new LinkedHashMap<String, String[]>();
/*     */     
/* 674 */     if (node != null) {
/* 675 */       for (String key : node.getKeys()) {
/* 676 */         List<String> commands = new ArrayList<String>();
/*     */         
/* 678 */         if (node.getProperty(key) instanceof List) {
/* 679 */           commands = node.getStringList(key, null);
/*     */         } else {
/* 681 */           commands.add(node.getString(key));
/*     */         } 
/*     */         
/* 684 */         result.put(key, commands.toArray(new String[0]));
/*     */       } 
/*     */     }
/*     */     
/* 688 */     return result;
/*     */   }
/*     */ 
/*     */   
/* 692 */   public int getSpawnRadius() { return this.configuration.getInt("settings.spawn-radius", 16); }
/*     */ 
/*     */   
/*     */   public void setSpawnRadius(int value) {
/* 696 */     this.configuration.setProperty("settings.spawn-radius", Integer.valueOf(value));
/* 697 */     this.configuration.save();
/*     */   }
/*     */ 
/*     */   
/* 701 */   public boolean getOnlineMode() { return this.console.onlineMode; }
/*     */ 
/*     */ 
/*     */   
/* 705 */   public boolean getAllowFlight() { return this.console.allowFlight; }
/*     */ 
/*     */   
/*     */   public ChunkGenerator getGenerator(String world) {
/* 709 */     ConfigurationNode node = this.configuration.getNode("worlds");
/* 710 */     ChunkGenerator result = null;
/*     */     
/* 712 */     if (node != null) {
/* 713 */       node = node.getNode(world);
/*     */       
/* 715 */       if (node != null) {
/* 716 */         String name = node.getString("generator");
/*     */         
/* 718 */         if (name != null && !name.equals("")) {
/* 719 */           String[] split = name.split(":", 2);
/* 720 */           String id = (split.length > 1) ? split[1] : null;
/* 721 */           Plugin plugin = this.pluginManager.getPlugin(split[0]);
/*     */           
/* 723 */           if (plugin == null) {
/* 724 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
/* 725 */           } else if (!plugin.isEnabled()) {
/* 726 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' is not enabled yet (is it load:STARTUP?)");
/*     */           } else {
/* 728 */             result = plugin.getDefaultWorldGenerator(world, id);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 734 */     return result;
/*     */   }
/*     */   
/*     */   public CraftMapView getMap(short id) {
/* 738 */     WorldMapCollection collection = ((WorldServer)this.console.worlds.get(0)).worldMaps;
/* 739 */     WorldMap worldmap = (WorldMap)collection.a(WorldMap.class, "map_" + id);
/* 740 */     if (worldmap == null) {
/* 741 */       return null;
/*     */     }
/* 743 */     return worldmap.mapView;
/*     */   }
/*     */   
/*     */   public CraftMapView createMap(World world) {
/* 747 */     ItemStack stack = new ItemStack(Item.MAP, true, -1);
/* 748 */     WorldMap worldmap = Item.MAP.a(stack, ((CraftWorld)world).getHandle());
/* 749 */     return worldmap.mapView;
/*     */   }
/*     */ 
/*     */   
/* 753 */   public void shutdown() { this.console.a(); }
/*     */ 
/*     */   
/*     */   public int broadcast(String message, String permission) {
/* 757 */     int count = 0;
/* 758 */     Set<Permissible> permissibles = getPluginManager().getPermissionSubscriptions(permission);
/*     */     
/* 760 */     for (Permissible permissible : permissibles) {
/* 761 */       if (permissible instanceof CommandSender) {
/* 762 */         CommandSender user = (CommandSender)permissible;
/* 763 */         user.sendMessage(message);
/* 764 */         count++;
/*     */       } 
/*     */     } 
/*     */     
/* 768 */     return count;
/*     */   }
/*     */   
/*     */   public OfflinePlayer getOfflinePlayer(String name) {
/* 772 */     CraftOfflinePlayer craftOfflinePlayer = getPlayerExact(name);
/*     */     
/* 774 */     if (craftOfflinePlayer == null) {
/* 775 */       craftOfflinePlayer = new CraftOfflinePlayer(this, name);
/*     */     }
/*     */     
/* 778 */     return craftOfflinePlayer;
/*     */   }
/*     */ 
/*     */   
/* 782 */   public Set<String> getIPBans() { return new HashSet(this.server.banByIP); }
/*     */ 
/*     */ 
/*     */   
/* 786 */   public void banIP(String address) { this.server.c(address); }
/*     */ 
/*     */ 
/*     */   
/* 790 */   public void unbanIP(String address) { this.server.d(address); }
/*     */ 
/*     */   
/*     */   public Set<OfflinePlayer> getBannedPlayers() {
/* 794 */     Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
/*     */     
/* 796 */     for (Object name : this.server.banByName) {
/* 797 */       result.add(getOfflinePlayer((String)name));
/*     */     }
/*     */     
/* 800 */     return result;
/*     */   }
/*     */   
/*     */   public void setWhitelist(boolean value) {
/* 804 */     this.server.o = value;
/* 805 */     this.console.propertyManager.b("white-list", value);
/* 806 */     this.console.propertyManager.savePropertiesFile();
/*     */   }
/*     */   
/*     */   public Set<OfflinePlayer> getWhitelistedPlayers() {
/* 810 */     Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
/*     */     
/* 812 */     for (Object name : this.server.e()) {
/* 813 */       result.add(getOfflinePlayer((String)name));
/*     */     }
/*     */     
/* 816 */     return result;
/*     */   }
/*     */ 
/*     */   
/* 820 */   public void reloadWhitelist() { this.server.f(); }
/*     */ 
/*     */ 
/*     */   
/* 824 */   public GameMode getDefaultGameMode() { return GameMode.SURVIVAL; }
/*     */ 
/*     */ 
/*     */   
/* 828 */   public void setDefaultGameMode(GameMode mode) { throw new UnsupportedOperationException("Not supported yet."); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\CraftServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */