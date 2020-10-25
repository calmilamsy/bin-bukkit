package org.bukkit.entity;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public interface Entity {
  Location getLocation();
  
  void setVelocity(Vector paramVector);
  
  Vector getVelocity();
  
  World getWorld();
  
  boolean teleport(Location paramLocation);
  
  boolean teleport(Entity paramEntity);
  
  List<Entity> getNearbyEntities(double paramDouble1, double paramDouble2, double paramDouble3);
  
  int getEntityId();
  
  int getFireTicks();
  
  int getMaxFireTicks();
  
  void setFireTicks(int paramInt);
  
  void remove();
  
  boolean isDead();
  
  Server getServer();
  
  Entity getPassenger();
  
  boolean setPassenger(Entity paramEntity);
  
  boolean isEmpty();
  
  boolean eject();
  
  float getFallDistance();
  
  void setFallDistance(float paramFloat);
  
  void setLastDamageCause(EntityDamageEvent paramEntityDamageEvent);
  
  EntityDamageEvent getLastDamageCause();
  
  UUID getUniqueId();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\Entity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */