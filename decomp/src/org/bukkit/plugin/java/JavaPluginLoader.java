/*     */ package org.bukkit.plugin.java;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockListener;
/*     */ import org.bukkit.event.entity.EntityListener;
/*     */ import org.bukkit.event.player.PlayerListener;
/*     */ import org.bukkit.event.server.PluginEnableEvent;
/*     */ import org.bukkit.event.server.ServerListener;
/*     */ import org.bukkit.event.vehicle.VehicleListener;
/*     */ import org.bukkit.event.weather.WeatherListener;
/*     */ import org.bukkit.event.world.WorldListener;
/*     */ import org.bukkit.plugin.EventExecutor;
/*     */ import org.bukkit.plugin.InvalidDescriptionException;
/*     */ import org.bukkit.plugin.InvalidPluginException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.UnknownDependencyException;
/*     */ import org.bukkit.plugin.UnknownSoftDependencyException;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ 
/*     */ public class JavaPluginLoader implements PluginLoader {
/*     */   private final Server server;
/*     */   protected final Pattern[] fileFilters;
/*     */   
/*     */   public JavaPluginLoader(Server instance) {
/*  38 */     this.fileFilters = new Pattern[] { Pattern.compile("\\.jar$") };
/*     */ 
/*     */     
/*  41 */     this.classes = new HashMap();
/*  42 */     this.loaders = new HashMap();
/*     */ 
/*     */     
/*  45 */     this.server = instance;
/*     */   }
/*     */   protected final Map<String, Class<?>> classes; protected final Map<String, PluginClassLoader> loaders;
/*     */   
/*  49 */   public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException { return loadPlugin(file, false); }
/*     */   
/*     */   public Plugin loadPlugin(File file, boolean ignoreSoftDependencies) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
/*     */     ArrayList<String> depend;
/*  53 */     JavaPlugin result = null;
/*  54 */     PluginDescriptionFile description = null;
/*     */     
/*  56 */     if (!file.exists()) {
/*  57 */       throw new InvalidPluginException(new FileNotFoundException(String.format("%s does not exist", new Object[] { file.getPath() })));
/*     */     }
/*     */     try {
/*  60 */       JarFile jar = new JarFile(file);
/*  61 */       JarEntry entry = jar.getJarEntry("plugin.yml");
/*     */       
/*  63 */       if (entry == null) {
/*  64 */         throw new InvalidPluginException(new FileNotFoundException("Jar does not contain plugin.yml"));
/*     */       }
/*     */       
/*  67 */       depend = jar.getInputStream(entry);
/*     */       
/*  69 */       description = new PluginDescriptionFile(depend);
/*     */       
/*  71 */       depend.close();
/*  72 */       jar.close();
/*  73 */     } catch (IOException ex) {
/*  74 */       throw new InvalidPluginException(ex);
/*  75 */     } catch (YAMLException ex) {
/*  76 */       throw new InvalidPluginException(ex);
/*     */     } 
/*     */     
/*  79 */     File dataFolder = new File(file.getParentFile(), description.getName());
/*  80 */     File oldDataFolder = getDataFolder(file);
/*     */ 
/*     */     
/*  83 */     if (!dataFolder.equals(oldDataFolder))
/*     */     {
/*  85 */       if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
/*  86 */         this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) found old-data folder: %s next to the new one: %s", new Object[] { description.getName(), file, oldDataFolder, dataFolder }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  93 */       else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
/*  94 */         if (!oldDataFolder.renameTo(dataFolder)) {
/*  95 */           throw new InvalidPluginException(new Exception("Unable to rename old data folder: '" + oldDataFolder + "' to: '" + dataFolder + "'"));
/*     */         }
/*  97 */         this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: '%s' to '%s'", new Object[] { description.getName(), file, oldDataFolder, dataFolder }));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (dataFolder.exists() && !dataFolder.isDirectory()) {
/* 107 */       throw new InvalidPluginException(new Exception(String.format("Projected datafolder: '%s' for %s (%s) exists and is not a directory", new Object[] { dataFolder, description.getName(), file })));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 118 */       depend = (ArrayList)description.getDepend();
/* 119 */       if (depend == null) {
/* 120 */         depend = new ArrayList<String>();
/*     */       }
/* 122 */     } catch (ClassCastException ex) {
/* 123 */       throw new InvalidPluginException(ex);
/*     */     } 
/*     */     
/* 126 */     for (String pluginName : depend) {
/* 127 */       if (this.loaders == null) {
/* 128 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/* 130 */       PluginClassLoader current = (PluginClassLoader)this.loaders.get(pluginName);
/*     */       
/* 132 */       if (current == null) {
/* 133 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/*     */     } 
/*     */     
/* 137 */     if (!ignoreSoftDependencies) {
/*     */       ArrayList<String> softDepend;
/*     */       
/*     */       try {
/* 141 */         softDepend = (ArrayList)description.getSoftDepend();
/* 142 */         if (softDepend == null) {
/* 143 */           softDepend = new ArrayList<String>();
/*     */         }
/* 145 */       } catch (ClassCastException ex) {
/* 146 */         throw new InvalidPluginException(ex);
/*     */       } 
/*     */       
/* 149 */       for (String pluginName : softDepend) {
/* 150 */         if (this.loaders == null) {
/* 151 */           throw new UnknownSoftDependencyException(pluginName);
/*     */         }
/* 153 */         PluginClassLoader current = (PluginClassLoader)this.loaders.get(pluginName);
/*     */         
/* 155 */         if (current == null) {
/* 156 */           throw new UnknownSoftDependencyException(pluginName);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     PluginClassLoader loader = null;
/*     */     
/*     */     try {
/* 164 */       URL[] urls = new URL[1];
/*     */       
/* 166 */       urls[0] = file.toURI().toURL();
/* 167 */       loader = new PluginClassLoader(this, urls, getClass().getClassLoader());
/* 168 */       Class<?> jarClass = Class.forName(description.getMain(), true, loader);
/* 169 */       Class<? extends JavaPlugin> plugin = jarClass.asSubclass(JavaPlugin.class);
/*     */       
/* 171 */       Constructor<? extends JavaPlugin> constructor = plugin.getConstructor(new Class[0]);
/*     */       
/* 173 */       result = (JavaPlugin)constructor.newInstance(new Object[0]);
/*     */       
/* 175 */       result.initialize(this, this.server, description, dataFolder, file, loader);
/* 176 */     } catch (Throwable ex) {
/* 177 */       throw new InvalidPluginException(ex);
/*     */     } 
/*     */     
/* 180 */     this.loaders.put(description.getName(), loader);
/*     */     
/* 182 */     return result;
/*     */   }
/*     */   
/*     */   protected File getDataFolder(File file) {
/* 186 */     File dataFolder = null;
/*     */     
/* 188 */     String filename = file.getName();
/* 189 */     int index = file.getName().lastIndexOf(".");
/*     */     
/* 191 */     if (index != -1) {
/* 192 */       String name = filename.substring(0, index);
/*     */       
/* 194 */       dataFolder = new File(file.getParentFile(), name);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 199 */       dataFolder = new File(file.getParentFile(), filename + "_");
/*     */     } 
/*     */     
/* 202 */     return dataFolder;
/*     */   }
/*     */ 
/*     */   
/* 206 */   public Pattern[] getPluginFileFilters() { return this.fileFilters; }
/*     */ 
/*     */   
/*     */   public Class<?> getClassByName(String name) {
/* 210 */     Class<?> cachedClass = (Class)this.classes.get(name);
/*     */     
/* 212 */     if (cachedClass != null) {
/* 213 */       return cachedClass;
/*     */     }
/* 215 */     for (String current : this.loaders.keySet()) {
/* 216 */       PluginClassLoader loader = (PluginClassLoader)this.loaders.get(current);
/*     */       
/*     */       try {
/* 219 */         cachedClass = loader.findClass(name, false);
/* 220 */       } catch (ClassNotFoundException cnfe) {}
/* 221 */       if (cachedClass != null) {
/* 222 */         return cachedClass;
/*     */       }
/*     */     } 
/*     */     
/* 226 */     return null;
/*     */   }
/*     */   
/*     */   public void setClass(String name, Class<?> clazz) {
/* 230 */     if (!this.classes.containsKey(name)) {
/* 231 */       this.classes.put(name, clazz);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EventExecutor createExecutor(Event.Type type, Listener listener) {
/* 238 */     switch (type) {
/*     */ 
/*     */       
/*     */       case PLAYER_JOIN:
/* 242 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 244 */               ((PlayerListener)listener).onPlayerJoin((PlayerJoinEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_QUIT:
/* 249 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 251 */               ((PlayerListener)listener).onPlayerQuit((PlayerQuitEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_RESPAWN:
/* 256 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 258 */               ((PlayerListener)listener).onPlayerRespawn((PlayerRespawnEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_KICK:
/* 263 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 265 */               ((PlayerListener)listener).onPlayerKick((PlayerKickEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_COMMAND_PREPROCESS:
/* 270 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 272 */               ((PlayerListener)listener).onPlayerCommandPreprocess((PlayerCommandPreprocessEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_CHAT:
/* 277 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 279 */               ((PlayerListener)listener).onPlayerChat((PlayerChatEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_MOVE:
/* 284 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 286 */               ((PlayerListener)listener).onPlayerMove((PlayerMoveEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_VELOCITY:
/* 291 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 293 */               ((PlayerListener)listener).onPlayerVelocity((PlayerVelocityEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_TELEPORT:
/* 298 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 300 */               ((PlayerListener)listener).onPlayerTeleport((PlayerTeleportEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_PORTAL:
/* 305 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 307 */               ((PlayerListener)listener).onPlayerPortal((PlayerPortalEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_INTERACT:
/* 312 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 314 */               ((PlayerListener)listener).onPlayerInteract((PlayerInteractEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_INTERACT_ENTITY:
/* 319 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 321 */               ((PlayerListener)listener).onPlayerInteractEntity((PlayerInteractEntityEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_LOGIN:
/* 326 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 328 */               ((PlayerListener)listener).onPlayerLogin((PlayerLoginEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_PRELOGIN:
/* 333 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 335 */               ((PlayerListener)listener).onPlayerPreLogin((PlayerPreLoginEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_EGG_THROW:
/* 340 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 342 */               ((PlayerListener)listener).onPlayerEggThrow((PlayerEggThrowEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_ANIMATION:
/* 347 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 349 */               ((PlayerListener)listener).onPlayerAnimation((PlayerAnimationEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case INVENTORY_OPEN:
/* 354 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 356 */               ((PlayerListener)listener).onInventoryOpen((PlayerInventoryEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_ITEM_HELD:
/* 361 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 363 */               ((PlayerListener)listener).onItemHeldChange((PlayerItemHeldEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_DROP_ITEM:
/* 368 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 370 */               ((PlayerListener)listener).onPlayerDropItem((PlayerDropItemEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_PICKUP_ITEM:
/* 375 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 377 */               ((PlayerListener)listener).onPlayerPickupItem((PlayerPickupItemEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_TOGGLE_SNEAK:
/* 382 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 384 */               ((PlayerListener)listener).onPlayerToggleSneak((PlayerToggleSneakEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_BUCKET_EMPTY:
/* 389 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 391 */               ((PlayerListener)listener).onPlayerBucketEmpty((PlayerBucketEmptyEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_BUCKET_FILL:
/* 396 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 398 */               ((PlayerListener)listener).onPlayerBucketFill((PlayerBucketFillEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_BED_ENTER:
/* 403 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 405 */               ((PlayerListener)listener).onPlayerBedEnter((PlayerBedEnterEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_BED_LEAVE:
/* 410 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 412 */               ((PlayerListener)listener).onPlayerBedLeave((PlayerBedLeaveEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLAYER_FISH:
/* 417 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 419 */               ((PlayerListener)listener).onPlayerFish((PlayerFishEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case BLOCK_PHYSICS:
/* 425 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 427 */               ((BlockListener)listener).onBlockPhysics((BlockPhysicsEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_CANBUILD:
/* 432 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 434 */               ((BlockListener)listener).onBlockCanBuild((BlockCanBuildEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_PLACE:
/* 439 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 441 */               ((BlockListener)listener).onBlockPlace((BlockPlaceEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_DAMAGE:
/* 446 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 448 */               ((BlockListener)listener).onBlockDamage((BlockDamageEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_FROMTO:
/* 453 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 455 */               ((BlockListener)listener).onBlockFromTo((BlockFromToEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case LEAVES_DECAY:
/* 460 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 462 */               ((BlockListener)listener).onLeavesDecay((LeavesDecayEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case SIGN_CHANGE:
/* 467 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 469 */               ((BlockListener)listener).onSignChange((SignChangeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_IGNITE:
/* 474 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 476 */               ((BlockListener)listener).onBlockIgnite((BlockIgniteEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case REDSTONE_CHANGE:
/* 481 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 483 */               ((BlockListener)listener).onBlockRedstoneChange((BlockRedstoneEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_BURN:
/* 488 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 490 */               ((BlockListener)listener).onBlockBurn((BlockBurnEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_BREAK:
/* 495 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 497 */               ((BlockListener)listener).onBlockBreak((BlockBreakEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_FORM:
/* 502 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 504 */               ((BlockListener)listener).onBlockForm((BlockFormEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_SPREAD:
/* 509 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 511 */               ((BlockListener)listener).onBlockSpread((BlockSpreadEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case BLOCK_FADE:
/* 517 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 519 */               ((BlockListener)listener).onBlockFade((BlockFadeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_DISPENSE:
/* 524 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 526 */               ((BlockListener)listener).onBlockDispense((BlockDispenseEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_PISTON_RETRACT:
/* 531 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 533 */               ((BlockListener)listener).onBlockPistonRetract((BlockPistonRetractEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case BLOCK_PISTON_EXTEND:
/* 538 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 540 */               ((BlockListener)listener).onBlockPistonExtend((BlockPistonExtendEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case PLUGIN_ENABLE:
/* 546 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 548 */               ((ServerListener)listener).onPluginEnable((PluginEnableEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PLUGIN_DISABLE:
/* 553 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 555 */               ((ServerListener)listener).onPluginDisable((PluginDisableEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case SERVER_COMMAND:
/* 560 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 562 */               ((ServerListener)listener).onServerCommand((ServerCommandEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case MAP_INITIALIZE:
/* 567 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 569 */               ((ServerListener)listener).onMapInitialize((MapInitializeEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case CHUNK_LOAD:
/* 575 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 577 */               ((WorldListener)listener).onChunkLoad((ChunkLoadEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case CHUNK_POPULATED:
/* 582 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 584 */               ((WorldListener)listener).onChunkPopulate((ChunkPopulateEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case CHUNK_UNLOAD:
/* 589 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 591 */               ((WorldListener)listener).onChunkUnload((ChunkUnloadEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case SPAWN_CHANGE:
/* 596 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 598 */               ((WorldListener)listener).onSpawnChange((SpawnChangeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case WORLD_SAVE:
/* 603 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 605 */               ((WorldListener)listener).onWorldSave((WorldSaveEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case WORLD_INIT:
/* 610 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 612 */               ((WorldListener)listener).onWorldInit((WorldInitEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case WORLD_LOAD:
/* 617 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 619 */               ((WorldListener)listener).onWorldLoad((WorldLoadEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case WORLD_UNLOAD:
/* 624 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 626 */               ((WorldListener)listener).onWorldUnload((WorldUnloadEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PORTAL_CREATE:
/* 631 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 633 */               ((WorldListener)listener).onPortalCreate((PortalCreateEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case PAINTING_PLACE:
/* 639 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 641 */               ((EntityListener)listener).onPaintingPlace((PaintingPlaceEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PAINTING_BREAK:
/* 646 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 648 */               ((EntityListener)listener).onPaintingBreak((PaintingBreakEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case ENTITY_DAMAGE:
/* 654 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 656 */               ((EntityListener)listener).onEntityDamage((EntityDamageEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_DEATH:
/* 661 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 663 */               ((EntityListener)listener).onEntityDeath((EntityDeathEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_COMBUST:
/* 668 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 670 */               ((EntityListener)listener).onEntityCombust((EntityCombustEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_EXPLODE:
/* 675 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 677 */               ((EntityListener)listener).onEntityExplode((EntityExplodeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case EXPLOSION_PRIME:
/* 682 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 684 */               ((EntityListener)listener).onExplosionPrime((ExplosionPrimeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_TARGET:
/* 689 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 691 */               ((EntityListener)listener).onEntityTarget((EntityTargetEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_INTERACT:
/* 696 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 698 */               ((EntityListener)listener).onEntityInteract((EntityInteractEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_PORTAL_ENTER:
/* 703 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 705 */               ((EntityListener)listener).onEntityPortalEnter((EntityPortalEnterEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case CREATURE_SPAWN:
/* 710 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 712 */               ((EntityListener)listener).onCreatureSpawn((CreatureSpawnEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ITEM_SPAWN:
/* 717 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 719 */               ((EntityListener)listener).onItemSpawn((ItemSpawnEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PIG_ZAP:
/* 724 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 726 */               ((EntityListener)listener).onPigZap((PigZapEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case CREEPER_POWER:
/* 731 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 733 */               ((EntityListener)listener).onCreeperPower((CreeperPowerEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_TAME:
/* 738 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 740 */               ((EntityListener)listener).onEntityTame((EntityTameEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case ENTITY_REGAIN_HEALTH:
/* 745 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 747 */               ((EntityListener)listener).onEntityRegainHealth((EntityRegainHealthEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case PROJECTILE_HIT:
/* 752 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 754 */               ((EntityListener)listener).onProjectileHit((ProjectileHitEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case VEHICLE_CREATE:
/* 760 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 762 */               ((VehicleListener)listener).onVehicleCreate((VehicleCreateEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_DAMAGE:
/* 767 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 769 */               ((VehicleListener)listener).onVehicleDamage((VehicleDamageEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_DESTROY:
/* 774 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 776 */               ((VehicleListener)listener).onVehicleDestroy((VehicleDestroyEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_COLLISION_BLOCK:
/* 781 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 783 */               ((VehicleListener)listener).onVehicleBlockCollision((VehicleBlockCollisionEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_COLLISION_ENTITY:
/* 788 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 790 */               ((VehicleListener)listener).onVehicleEntityCollision((VehicleEntityCollisionEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_ENTER:
/* 795 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 797 */               ((VehicleListener)listener).onVehicleEnter((VehicleEnterEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_EXIT:
/* 802 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 804 */               ((VehicleListener)listener).onVehicleExit((VehicleExitEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_MOVE:
/* 809 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 811 */               ((VehicleListener)listener).onVehicleMove((VehicleMoveEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case VEHICLE_UPDATE:
/* 816 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 818 */               ((VehicleListener)listener).onVehicleUpdate((VehicleUpdateEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case WEATHER_CHANGE:
/* 824 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 826 */               ((WeatherListener)listener).onWeatherChange((WeatherChangeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case THUNDER_CHANGE:
/* 831 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 833 */               ((WeatherListener)listener).onThunderChange((ThunderChangeEvent)event);
/*     */             }
/*     */           };
/*     */       
/*     */       case LIGHTNING_STRIKE:
/* 838 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 840 */               ((WeatherListener)listener).onLightningStrike((LightningStrikeEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case FURNACE_SMELT:
/* 846 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 848 */               ((InventoryListener)listener).onFurnaceSmelt((FurnaceSmeltEvent)event);
/*     */             }
/*     */           };
/*     */       case FURNACE_BURN:
/* 852 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 854 */               ((InventoryListener)listener).onFurnaceBurn((FurnaceBurnEvent)event);
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case CUSTOM_EVENT:
/* 860 */         return new EventExecutor() {
/*     */             public void execute(Listener listener, Event event) {
/* 862 */               ((CustomEventListener)listener).onCustomEvent(event);
/*     */             }
/*     */           };
/*     */     } 
/*     */     
/* 867 */     throw new IllegalArgumentException("Event " + type + " is not supported");
/*     */   }
/*     */   
/*     */   public void enablePlugin(Plugin plugin) {
/* 871 */     if (!(plugin instanceof JavaPlugin)) {
/* 872 */       throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
/*     */     }
/*     */     
/* 875 */     if (!plugin.isEnabled()) {
/* 876 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/*     */       
/* 878 */       String pluginName = jPlugin.getDescription().getName();
/*     */       
/* 880 */       if (!this.loaders.containsKey(pluginName)) {
/* 881 */         this.loaders.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
/*     */       }
/*     */       
/*     */       try {
/* 885 */         jPlugin.setEnabled(true);
/* 886 */       } catch (Throwable ex) {
/* 887 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 892 */       this.server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 897 */     if (!(plugin instanceof JavaPlugin)) {
/* 898 */       throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
/*     */     }
/*     */     
/* 901 */     if (plugin.isEnabled()) {
/* 902 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/* 903 */       ClassLoader cloader = jPlugin.getClassLoader();
/*     */       
/*     */       try {
/* 906 */         jPlugin.setEnabled(false);
/* 907 */       } catch (Throwable ex) {
/* 908 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */       
/* 911 */       this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));
/*     */       
/* 913 */       this.loaders.remove(jPlugin.getDescription().getName());
/*     */       
/* 915 */       if (cloader instanceof PluginClassLoader) {
/* 916 */         PluginClassLoader loader = (PluginClassLoader)cloader;
/* 917 */         Set<String> names = loader.getClasses();
/*     */         
/* 919 */         for (String name : names)
/* 920 */           this.classes.remove(name); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\java\JavaPluginLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */