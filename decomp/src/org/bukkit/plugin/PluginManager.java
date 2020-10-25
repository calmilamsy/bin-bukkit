package org.bukkit.plugin;

import java.io.File;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

public interface PluginManager {
  void registerInterface(Class<? extends PluginLoader> paramClass) throws IllegalArgumentException;
  
  Plugin getPlugin(String paramString);
  
  Plugin[] getPlugins();
  
  boolean isPluginEnabled(String paramString);
  
  boolean isPluginEnabled(Plugin paramPlugin);
  
  Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;
  
  Plugin[] loadPlugins(File paramFile);
  
  void disablePlugins();
  
  void clearPlugins();
  
  void callEvent(Event paramEvent);
  
  void registerEvent(Event.Type paramType, Listener paramListener, Event.Priority paramPriority, Plugin paramPlugin);
  
  void registerEvent(Event.Type paramType, Listener paramListener, EventExecutor paramEventExecutor, Event.Priority paramPriority, Plugin paramPlugin);
  
  void enablePlugin(Plugin paramPlugin);
  
  void disablePlugin(Plugin paramPlugin);
  
  Permission getPermission(String paramString);
  
  void addPermission(Permission paramPermission);
  
  void removePermission(Permission paramPermission);
  
  void removePermission(String paramString);
  
  Set<Permission> getDefaultPermissions(boolean paramBoolean);
  
  void recalculatePermissionDefaults(Permission paramPermission);
  
  void subscribeToPermission(String paramString, Permissible paramPermissible);
  
  void unsubscribeFromPermission(String paramString, Permissible paramPermissible);
  
  Set<Permissible> getPermissionSubscriptions(String paramString);
  
  void subscribeToDefaultPerms(boolean paramBoolean, Permissible paramPermissible);
  
  void unsubscribeFromDefaultPerms(boolean paramBoolean, Permissible paramPermissible);
  
  Set<Permissible> getDefaultPermSubscriptions(boolean paramBoolean);
  
  Set<Permission> getPermissions();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\PluginManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */