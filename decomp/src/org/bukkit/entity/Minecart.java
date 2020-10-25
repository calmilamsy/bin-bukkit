package org.bukkit.entity;

import org.bukkit.util.Vector;

public interface Minecart extends Vehicle {
  void setDamage(int paramInt);
  
  int getDamage();
  
  double getMaxSpeed();
  
  void setMaxSpeed(double paramDouble);
  
  boolean isSlowWhenEmpty();
  
  void setSlowWhenEmpty(boolean paramBoolean);
  
  Vector getFlyingVelocityMod();
  
  void setFlyingVelocityMod(Vector paramVector);
  
  Vector getDerailedVelocityMod();
  
  void setDerailedVelocityMod(Vector paramVector);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\Minecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */