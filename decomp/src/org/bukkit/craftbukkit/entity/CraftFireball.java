/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityFireball;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Fireball;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class CraftFireball
/*    */   extends AbstractProjectile
/*    */   implements Fireball {
/* 13 */   public CraftFireball(CraftServer server, EntityFireball entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   public String toString() { return "CraftFireball"; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public float getYield() { return ((EntityFireball)getHandle()).yield; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean isIncendiary() { return ((EntityFireball)getHandle()).isIncendiary; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public void setIsIncendiary(boolean isIncendiary) { ((EntityFireball)getHandle()).isIncendiary = isIncendiary; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void setYield(float yield) { ((EntityFireball)getHandle()).yield = yield; }
/*    */ 
/*    */   
/*    */   public LivingEntity getShooter() {
/* 38 */     if (((EntityFireball)getHandle()).shooter != null) {
/* 39 */       return (LivingEntity)((EntityFireball)getHandle()).shooter.getBukkitEntity();
/*    */     }
/*    */     
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setShooter(LivingEntity shooter) {
/* 47 */     if (shooter instanceof CraftLivingEntity) {
/* 48 */       ((EntityFireball)getHandle()).shooter = (EntityLiving)((CraftLivingEntity)shooter).entity;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 53 */   public Vector getDirection() { return new Vector(((EntityFireball)getHandle()).c, ((EntityFireball)getHandle()).d, ((EntityFireball)getHandle()).e); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void setDirection(Vector direction) { ((EntityFireball)getHandle()).setDirection(direction.getX(), direction.getY(), direction.getZ()); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftFireball.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */