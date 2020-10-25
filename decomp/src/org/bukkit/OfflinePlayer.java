package org.bukkit;

import org.bukkit.permissions.ServerOperator;

public interface OfflinePlayer extends ServerOperator {
  boolean isOnline();
  
  String getName();
  
  boolean isBanned();
  
  void setBanned(boolean paramBoolean);
  
  boolean isWhitelisted();
  
  void setWhitelisted(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\OfflinePlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */