package org.bukkit;

import com.avaje.ebean.config.ServerConfig;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;

public interface Server {
  public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
  
  public static final String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";
  
  String getName();
  
  String getVersion();
  
  Player[] getOnlinePlayers();
  
  int getMaxPlayers();
  
  int getPort();
  
  int getViewDistance();
  
  String getIp();
  
  String getServerName();
  
  String getServerId();
  
  boolean getAllowNether();
  
  boolean hasWhitelist();
  
  void setWhitelist(boolean paramBoolean);
  
  Set<OfflinePlayer> getWhitelistedPlayers();
  
  void reloadWhitelist();
  
  int broadcastMessage(String paramString);
  
  String getUpdateFolder();
  
  Player getPlayer(String paramString);
  
  Player getPlayerExact(String paramString);
  
  List<Player> matchPlayer(String paramString);
  
  PluginManager getPluginManager();
  
  BukkitScheduler getScheduler();
  
  ServicesManager getServicesManager();
  
  List<World> getWorlds();
  
  World createWorld(String paramString, World.Environment paramEnvironment);
  
  World createWorld(String paramString, World.Environment paramEnvironment, long paramLong);
  
  World createWorld(String paramString, World.Environment paramEnvironment, ChunkGenerator paramChunkGenerator);
  
  World createWorld(String paramString, World.Environment paramEnvironment, long paramLong, ChunkGenerator paramChunkGenerator);
  
  boolean unloadWorld(String paramString, boolean paramBoolean);
  
  boolean unloadWorld(World paramWorld, boolean paramBoolean);
  
  World getWorld(String paramString);
  
  World getWorld(UUID paramUUID);
  
  MapView getMap(short paramShort);
  
  MapView createMap(World paramWorld);
  
  void reload();
  
  Logger getLogger();
  
  PluginCommand getPluginCommand(String paramString);
  
  void savePlayers();
  
  boolean dispatchCommand(CommandSender paramCommandSender, String paramString);
  
  void configureDbConfig(ServerConfig paramServerConfig);
  
  boolean addRecipe(Recipe paramRecipe);
  
  Map<String, String[]> getCommandAliases();
  
  int getSpawnRadius();
  
  void setSpawnRadius(int paramInt);
  
  boolean getOnlineMode();
  
  boolean getAllowFlight();
  
  void shutdown();
  
  int broadcast(String paramString1, String paramString2);
  
  OfflinePlayer getOfflinePlayer(String paramString);
  
  Set<String> getIPBans();
  
  void banIP(String paramString);
  
  void unbanIP(String paramString);
  
  Set<OfflinePlayer> getBannedPlayers();
  
  GameMode getDefaultGameMode();
  
  void setDefaultGameMode(GameMode paramGameMode);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Server.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */