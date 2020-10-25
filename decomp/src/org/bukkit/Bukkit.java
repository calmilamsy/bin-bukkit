/*     */ package org.bukkit;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.map.MapView;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.ServicesManager;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bukkit
/*     */ {
/*     */   private static Server server;
/*     */   
/*  37 */   public static Server getServer() { return server; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setServer(Server server) {
/*  48 */     if (Bukkit.server != null) {
/*  49 */       throw new UnsupportedOperationException("Cannot redefine singleton Server");
/*     */     }
/*     */     
/*  52 */     Bukkit.server = server;
/*  53 */     server.getLogger().info("This server is running " + getName() + " version " + getVersion());
/*     */   }
/*     */ 
/*     */   
/*  57 */   public static String getName() { return server.getName(); }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static String getVersion() { return server.getVersion(); }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static Player[] getOnlinePlayers() { return server.getOnlinePlayers(); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static int getMaxPlayers() { return server.getMaxPlayers(); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static int getPort() { return server.getPort(); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static int getViewDistance() { return server.getViewDistance(); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static String getIp() { return server.getIp(); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static String getServerName() { return server.getServerName(); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static String getServerId() { return server.getServerId(); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static boolean getAllowNether() { return server.getAllowNether(); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static boolean hasWhitelist() { return server.hasWhitelist(); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static int broadcastMessage(String message) { return server.broadcastMessage(message); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static String getUpdateFolder() { return server.getUpdateFolder(); }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static Player getPlayer(String name) { return server.getPlayer(name); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static List<Player> matchPlayer(String name) { return server.matchPlayer(name); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static PluginManager getPluginManager() { return server.getPluginManager(); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static BukkitScheduler getScheduler() { return server.getScheduler(); }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static ServicesManager getServicesManager() { return server.getServicesManager(); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static List<World> getWorlds() { return server.getWorlds(); }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public static World createWorld(String name, World.Environment environment) { return server.createWorld(name, environment); }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static World createWorld(String name, World.Environment environment, long seed) { return server.createWorld(name, environment, seed); }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public static World createWorld(String name, World.Environment environment, ChunkGenerator generator) { return server.createWorld(name, environment, generator); }
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static World createWorld(String name, World.Environment environment, long seed, ChunkGenerator generator) { return server.createWorld(name, environment, seed, generator); }
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static boolean unloadWorld(String name, boolean save) { return server.unloadWorld(name, save); }
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static boolean unloadWorld(World world, boolean save) { return server.unloadWorld(world, save); }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static World getWorld(String name) { return server.getWorld(name); }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public static World getWorld(UUID uid) { return server.getWorld(uid); }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static MapView getMap(short id) { return server.getMap(id); }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public static MapView createMap(World world) { return server.createMap(world); }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public static void reload() { server.reload(); }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static Logger getLogger() { return server.getLogger(); }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public static PluginCommand getPluginCommand(String name) { return server.getPluginCommand(name); }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public static void savePlayers() { server.savePlayers(); }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public static boolean dispatchCommand(CommandSender sender, String commandLine) { return server.dispatchCommand(sender, commandLine); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static void configureDbConfig(ServerConfig config) { server.configureDbConfig(config); }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public static boolean addRecipe(Recipe recipe) { return server.addRecipe(recipe); }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public static Map<String, String[]> getCommandAliases() { return server.getCommandAliases(); }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public static int getSpawnRadius() { return server.getSpawnRadius(); }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public static void setSpawnRadius(int value) { server.setSpawnRadius(value); }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public static boolean getOnlineMode() { return server.getOnlineMode(); }
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static boolean getAllowFlight() { return server.getAllowFlight(); }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public static void shutdown() { server.shutdown(); }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public static int broadcast(String message, String permission) { return server.broadcast(message, permission); }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public static OfflinePlayer getOfflinePlayer(String name) { return server.getOfflinePlayer(name); }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public static Player getPlayerExact(String name) { return server.getPlayerExact(name); }
/*     */ 
/*     */ 
/*     */   
/* 237 */   public static Set<String> getIPBans() { return server.getIPBans(); }
/*     */ 
/*     */ 
/*     */   
/* 241 */   public static void banIP(String address) { server.banIP(address); }
/*     */ 
/*     */ 
/*     */   
/* 245 */   public static void unbanIP(String address) { server.unbanIP(address); }
/*     */ 
/*     */ 
/*     */   
/* 249 */   public static Set<OfflinePlayer> getBannedPlayers() { return server.getBannedPlayers(); }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public static void setWhitelist(boolean value) { server.setWhitelist(value); }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public static Set<OfflinePlayer> getWhitelistedPlayers() { return server.getWhitelistedPlayers(); }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public static void reloadWhitelist() { server.reloadWhitelist(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Bukkit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */