package org.bukkit.plugin;

import java.io.File;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public interface PluginLoader {
  Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;
  
  Plugin loadPlugin(File paramFile, boolean paramBoolean) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;
  
  Pattern[] getPluginFileFilters();
  
  EventExecutor createExecutor(Event.Type paramType, Listener paramListener);
  
  void enablePlugin(Plugin paramPlugin);
  
  void disablePlugin(Plugin paramPlugin);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\PluginLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */