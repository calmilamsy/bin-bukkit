package org.bukkit.entity;

public interface Projectile extends Entity {
  LivingEntity getShooter();
  
  void setShooter(LivingEntity paramLivingEntity);
  
  boolean doesBounce();
  
  void setBounce(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\Projectile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */