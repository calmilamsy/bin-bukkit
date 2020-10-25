package org.bukkit.entity;

import java.net.InetSocketAddress;
import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.map.MapView;

public interface Player extends HumanEntity, CommandSender, OfflinePlayer {
  String getDisplayName();
  
  void setDisplayName(String paramString);
  
  void setCompassTarget(Location paramLocation);
  
  Location getCompassTarget();
  
  InetSocketAddress getAddress();
  
  void sendRawMessage(String paramString);
  
  void kickPlayer(String paramString);
  
  void chat(String paramString);
  
  boolean performCommand(String paramString);
  
  boolean isSneaking();
  
  void setSneaking(boolean paramBoolean);
  
  void saveData();
  
  void loadData();
  
  void setSleepingIgnored(boolean paramBoolean);
  
  boolean isSleepingIgnored();
  
  void playNote(Location paramLocation, byte paramByte1, byte paramByte2);
  
  void playNote(Location paramLocation, Instrument paramInstrument, Note paramNote);
  
  void playEffect(Location paramLocation, Effect paramEffect, int paramInt);
  
  void sendBlockChange(Location paramLocation, Material paramMaterial, byte paramByte);
  
  boolean sendChunkChange(Location paramLocation, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);
  
  void sendBlockChange(Location paramLocation, int paramInt, byte paramByte);
  
  void sendMap(MapView paramMapView);
  
  @Deprecated
  void updateInventory();
  
  void awardAchievement(Achievement paramAchievement);
  
  void incrementStatistic(Statistic paramStatistic);
  
  void incrementStatistic(Statistic paramStatistic, int paramInt);
  
  void incrementStatistic(Statistic paramStatistic, Material paramMaterial);
  
  void incrementStatistic(Statistic paramStatistic, Material paramMaterial, int paramInt);
  
  void setPlayerTime(long paramLong, boolean paramBoolean);
  
  long getPlayerTime();
  
  long getPlayerTimeOffset();
  
  boolean isPlayerTimeRelative();
  
  void resetPlayerTime();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\Player.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */