/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.PluginCommandYamlParser;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
/*     */ import org.bukkit.util.FileUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SimplePluginManager
/*     */   implements PluginManager
/*     */ {
/*     */   private final Server server;
/*     */   private final Map<Pattern, PluginLoader> fileAssociations;
/*     */   private final List<Plugin> plugins;
/*     */   private final Map<String, Plugin> lookupNames;
/*     */   private final Map<Event.Type, SortedSet<RegisteredListener>> listeners;
/*  47 */   private static File updateDirectory = null; public SimplePluginManager(Server instance, SimpleCommandMap commandMap) { this.fileAssociations = new HashMap(); this.plugins = new ArrayList(); this.lookupNames = new HashMap();
/*     */     this.listeners = new EnumMap(Event.Type.class);
/*  49 */     this.permissions = new HashMap();
/*  50 */     this.defaultPerms = new LinkedHashMap();
/*  51 */     this.permSubs = new HashMap();
/*  52 */     this.defSubs = new HashMap();
/*  53 */     this.comparer = new Comparator<RegisteredListener>() {
/*     */         public int compare(RegisteredListener i, RegisteredListener j) {
/*  55 */           int result = i.getPriority().compareTo(j.getPriority());
/*     */           
/*  57 */           if (result == 0 && i != j) {
/*  58 */             result = 1;
/*     */           }
/*     */           
/*  61 */           return result;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  66 */     this.server = instance;
/*  67 */     this.commandMap = commandMap;
/*     */     
/*  69 */     this.defaultPerms.put(Boolean.valueOf(true), new HashSet());
/*  70 */     this.defaultPerms.put(Boolean.valueOf(false), new HashSet()); }
/*     */ 
/*     */   
/*     */   private final SimpleCommandMap commandMap;
/*     */   private final Map<String, Permission> permissions;
/*     */   private final Map<Boolean, Set<Permission>> defaultPerms;
/*     */   private final Map<String, Map<Permissible, Boolean>> permSubs;
/*     */   private final Map<Boolean, Map<Permissible, Boolean>> defSubs;
/*     */   private final Comparator<RegisteredListener> comparer;
/*     */   
/*     */   public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException {
/*     */     PluginLoader instance;
/*  82 */     if (PluginLoader.class.isAssignableFrom(loader)) {
/*     */ 
/*     */       
/*     */       try {
/*  86 */         Constructor<? extends PluginLoader> constructor = loader.getConstructor(new Class[] { Server.class });
/*  87 */         instance = (PluginLoader)constructor.newInstance(new Object[] { this.server });
/*  88 */       } catch (NoSuchMethodException ex) {
/*  89 */         String className = loader.getName();
/*     */         
/*  91 */         throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", new Object[] { className, className }), ex);
/*  92 */       } catch (Exception ex) {
/*  93 */         throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", new Object[] { ex.getClass().getName(), loader.getName() }), ex);
/*     */       } 
/*     */     } else {
/*  96 */       throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", new Object[] { loader.getName() }));
/*     */     } 
/*     */     
/*  99 */     Pattern[] patterns = instance.getPluginFileFilters();
/*     */     
/* 101 */     synchronized (this) {
/* 102 */       for (Pattern pattern : patterns) {
/* 103 */         this.fileAssociations.put(pattern, instance);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plugin[] loadPlugins(File directory) {
/* 115 */     List<Plugin> result = new ArrayList<Plugin>();
/* 116 */     File[] files = directory.listFiles();
/*     */     
/* 118 */     boolean allFailed = false;
/* 119 */     boolean finalPass = false;
/*     */     
/* 121 */     LinkedList<File> filesList = new LinkedList<File>(Arrays.asList(files));
/*     */     
/* 123 */     if (!this.server.getUpdateFolder().equals("")) {
/* 124 */       updateDirectory = new File(directory, this.server.getUpdateFolder());
/*     */     }
/*     */     
/* 127 */     while (!allFailed || finalPass) {
/* 128 */       allFailed = true;
/* 129 */       Iterator<File> itr = filesList.iterator();
/*     */       
/* 131 */       while (itr.hasNext()) {
/* 132 */         File file = (File)itr.next();
/* 133 */         Plugin plugin = null;
/*     */         
/*     */         try {
/* 136 */           plugin = loadPlugin(file, finalPass);
/* 137 */           itr.remove();
/* 138 */         } catch (UnknownDependencyException ex) {
/* 139 */           if (finalPass) {
/* 140 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': " + ex.getMessage(), ex);
/* 141 */             itr.remove();
/*     */           } else {
/* 143 */             plugin = null;
/*     */           } 
/* 145 */         } catch (InvalidPluginException ex) {
/* 146 */           this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': ", ex.getCause());
/* 147 */           itr.remove();
/* 148 */         } catch (InvalidDescriptionException ex) {
/* 149 */           this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': " + ex.getMessage(), ex);
/* 150 */           itr.remove();
/*     */         } 
/*     */         
/* 153 */         if (plugin != null) {
/* 154 */           result.add(plugin);
/* 155 */           allFailed = false;
/* 156 */           finalPass = false;
/*     */         } 
/*     */       } 
/* 159 */       if (finalPass)
/*     */         break; 
/* 161 */       if (allFailed) {
/* 162 */         finalPass = true;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return (Plugin[])result.toArray(new Plugin[result.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException { return loadPlugin(file, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plugin loadPlugin(File file, boolean ignoreSoftDependencies) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
/* 195 */     File updateFile = null;
/*     */     
/* 197 */     if (updateDirectory != null && updateDirectory.isDirectory() && (updateFile = new File(updateDirectory, file.getName())).isFile() && 
/* 198 */       FileUtil.copy(updateFile, file)) {
/* 199 */       updateFile.delete();
/*     */     }
/*     */ 
/*     */     
/* 203 */     Set<Pattern> filters = this.fileAssociations.keySet();
/* 204 */     Plugin result = null;
/*     */     
/* 206 */     for (Pattern filter : filters) {
/* 207 */       String name = file.getName();
/* 208 */       Matcher match = filter.matcher(name);
/*     */       
/* 210 */       if (match.find()) {
/* 211 */         PluginLoader loader = (PluginLoader)this.fileAssociations.get(filter);
/*     */         
/* 213 */         result = loader.loadPlugin(file, ignoreSoftDependencies);
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     if (result != null) {
/* 218 */       this.plugins.add(result);
/* 219 */       this.lookupNames.put(result.getDescription().getName(), result);
/*     */     } 
/*     */     
/* 222 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   public Plugin getPlugin(String name) { return (Plugin)this.lookupNames.get(name); }
/*     */ 
/*     */ 
/*     */   
/* 238 */   public Plugin[] getPlugins() { return (Plugin[])this.plugins.toArray(new Plugin[0]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled(String name) {
/* 250 */     Plugin plugin = getPlugin(name);
/*     */     
/* 252 */     return isPluginEnabled(plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled(Plugin plugin) {
/* 262 */     if (plugin != null && this.plugins.contains(plugin)) {
/* 263 */       return plugin.isEnabled();
/*     */     }
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enablePlugin(Plugin plugin) {
/* 270 */     if (!plugin.isEnabled()) {
/* 271 */       List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
/*     */       
/* 273 */       if (!pluginCommands.isEmpty()) {
/* 274 */         this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
/*     */       }
/*     */       
/*     */       try {
/* 278 */         plugin.getPluginLoader().enablePlugin(plugin);
/* 279 */       } catch (Throwable ex) {
/* 280 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disablePlugins() {
/* 286 */     for (Plugin plugin : getPlugins()) {
/* 287 */       disablePlugin(plugin);
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 292 */     if (plugin.isEnabled()) {
/*     */       try {
/* 294 */         plugin.getPluginLoader().disablePlugin(plugin);
/* 295 */       } catch (Throwable ex) {
/* 296 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */       
/*     */       try {
/* 300 */         this.server.getScheduler().cancelTasks(plugin);
/* 301 */       } catch (Throwable ex) {
/* 302 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */       
/*     */       try {
/* 306 */         this.server.getServicesManager().unregisterAll(plugin);
/* 307 */       } catch (Throwable ex) {
/* 308 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearPlugins() {
/* 314 */     synchronized (this) {
/* 315 */       disablePlugins();
/* 316 */       this.plugins.clear();
/* 317 */       this.lookupNames.clear();
/* 318 */       this.listeners.clear();
/* 319 */       this.fileAssociations.clear();
/* 320 */       this.permissions.clear();
/* 321 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).clear();
/* 322 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callEvent(Event event) {
/* 333 */     SortedSet<RegisteredListener> eventListeners = (SortedSet)this.listeners.get(event.getType());
/*     */     
/* 335 */     if (eventListeners != null) {
/* 336 */       for (RegisteredListener registration : eventListeners) {
/*     */         try {
/* 338 */           registration.callEvent(event);
/* 339 */         } catch (AuthorNagException ex) {
/* 340 */           Plugin plugin = registration.getPlugin();
/*     */           
/* 342 */           if (plugin.isNaggable()) {
/* 343 */             plugin.setNaggable(false);
/*     */             
/* 345 */             String author = "<NoAuthorGiven>";
/*     */             
/* 347 */             if (plugin.getDescription().getAuthors().size() > 0) {
/* 348 */               author = (String)plugin.getDescription().getAuthors().get(0);
/*     */             }
/* 350 */             this.server.getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", new Object[] { author, plugin.getDescription().getName(), ex.getMessage() }));
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 357 */         catch (Throwable ex) {
/* 358 */           this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getType() + " to " + registration.getPlugin().getDescription().getName(), ex);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvent(Event.Type type, Listener listener, Event.Priority priority, Plugin plugin) {
/* 373 */     if (!plugin.isEnabled()) {
/* 374 */       throw new IllegalPluginAccessException("Plugin attempted to register " + type + " while not enabled");
/*     */     }
/*     */     
/* 377 */     getEventListeners(type).add(new RegisteredListener(listener, priority, plugin, type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvent(Event.Type type, Listener listener, EventExecutor executor, Event.Priority priority, Plugin plugin) {
/* 390 */     if (!plugin.isEnabled()) {
/* 391 */       throw new IllegalPluginAccessException("Plugin attempted to register " + type + " while not enabled");
/*     */     }
/*     */     
/* 394 */     getEventListeners(type).add(new RegisteredListener(listener, executor, priority, plugin));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SortedSet<RegisteredListener> getEventListeners(Event.Type type) {
/* 404 */     SortedSet<RegisteredListener> eventListeners = (SortedSet)this.listeners.get(type);
/*     */     
/* 406 */     if (eventListeners != null) {
/* 407 */       return eventListeners;
/*     */     }
/*     */     
/* 410 */     eventListeners = new TreeSet<RegisteredListener>(this.comparer);
/* 411 */     this.listeners.put(type, eventListeners);
/* 412 */     return eventListeners;
/*     */   }
/*     */ 
/*     */   
/* 416 */   public Permission getPermission(String name) { return (Permission)this.permissions.get(name.toLowerCase()); }
/*     */ 
/*     */   
/*     */   public void addPermission(Permission perm) {
/* 420 */     String name = perm.getName().toLowerCase();
/*     */     
/* 422 */     if (this.permissions.containsKey(name)) {
/* 423 */       throw new IllegalArgumentException("The permission " + name + " is already defined!");
/*     */     }
/*     */     
/* 426 */     this.permissions.put(name, perm);
/* 427 */     calculatePermissionDefault(perm);
/*     */   }
/*     */ 
/*     */   
/* 431 */   public Set<Permission> getDefaultPermissions(boolean op) { return ImmutableSet.copyOf((Iterable)this.defaultPerms.get(Boolean.valueOf(op))); }
/*     */ 
/*     */ 
/*     */   
/* 435 */   public void removePermission(Permission perm) { removePermission(perm.getName().toLowerCase()); }
/*     */ 
/*     */ 
/*     */   
/* 439 */   public void removePermission(String name) { this.permissions.remove(name); }
/*     */ 
/*     */   
/*     */   public void recalculatePermissionDefaults(Permission perm) {
/* 443 */     if (this.permissions.containsValue(perm)) {
/* 444 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).remove(perm);
/* 445 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).remove(perm);
/*     */       
/* 447 */       calculatePermissionDefault(perm);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void calculatePermissionDefault(Permission perm) {
/* 452 */     if (perm.getDefault() == PermissionDefault.OP || perm.getDefault() == PermissionDefault.TRUE) {
/* 453 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).add(perm);
/* 454 */       dirtyPermissibles(true);
/*     */     } 
/* 456 */     if (perm.getDefault() == PermissionDefault.NOT_OP || perm.getDefault() == PermissionDefault.TRUE) {
/* 457 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).add(perm);
/* 458 */       dirtyPermissibles(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dirtyPermissibles(boolean op) {
/* 463 */     Set<Permissible> permissibles = getDefaultPermSubscriptions(op);
/*     */     
/* 465 */     for (Permissible p : permissibles) {
/* 466 */       p.recalculatePermissions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void subscribeToPermission(String permission, Permissible permissible) {
/* 471 */     String name = permission.toLowerCase();
/* 472 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 474 */     if (map == null) {
/* 475 */       map = (new MapMaker()).weakKeys().makeMap();
/* 476 */       this.permSubs.put(name, map);
/*     */     } 
/*     */     
/* 479 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromPermission(String permission, Permissible permissible) {
/* 483 */     String name = permission.toLowerCase();
/* 484 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 486 */     if (map != null) {
/* 487 */       map.remove(permissible);
/*     */       
/* 489 */       if (map.isEmpty()) {
/* 490 */         this.permSubs.remove(name);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Permissible> getPermissionSubscriptions(String permission) {
/* 496 */     String name = permission.toLowerCase();
/* 497 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 499 */     if (map == null) {
/* 500 */       return ImmutableSet.of();
/*     */     }
/* 502 */     return ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void subscribeToDefaultPerms(boolean op, Permissible permissible) {
/* 507 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 509 */     if (map == null) {
/* 510 */       map = (new MapMaker()).weakKeys().makeMap();
/* 511 */       this.defSubs.put(Boolean.valueOf(op), map);
/*     */     } 
/*     */     
/* 514 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
/* 518 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 520 */     if (map != null) {
/* 521 */       map.remove(permissible);
/*     */       
/* 523 */       if (map.isEmpty()) {
/* 524 */         this.defSubs.remove(Boolean.valueOf(op));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
/* 530 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 532 */     if (map == null) {
/* 533 */       return ImmutableSet.of();
/*     */     }
/* 535 */     return ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 540 */   public Set<Permission> getPermissions() { return new HashSet(this.permissions.values()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\SimplePluginManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */