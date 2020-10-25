package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public interface CommandSender extends Permissible {
  void sendMessage(String paramString);
  
  Server getServer();
  
  String getName();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\CommandSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */