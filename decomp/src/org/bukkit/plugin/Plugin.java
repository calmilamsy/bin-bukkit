package org.bukkit.plugin;

import com.avaje.ebean.EbeanServer;
import java.io.File;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.config.Configuration;

public interface Plugin extends CommandExecutor {
  File getDataFolder();
  
  PluginDescriptionFile getDescription();
  
  Configuration getConfiguration();
  
  PluginLoader getPluginLoader();
  
  Server getServer();
  
  boolean isEnabled();
  
  void onDisable();
  
  void onLoad();
  
  void onEnable();
  
  boolean isNaggable();
  
  void setNaggable(boolean paramBoolean);
  
  EbeanServer getDatabase();
  
  ChunkGenerator getDefaultWorldGenerator(String paramString1, String paramString2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\Plugin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */