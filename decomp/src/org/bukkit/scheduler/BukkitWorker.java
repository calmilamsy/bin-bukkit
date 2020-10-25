package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitWorker {
  int getTaskId();
  
  Plugin getOwner();
  
  Thread getThread();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\scheduler\BukkitWorker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */