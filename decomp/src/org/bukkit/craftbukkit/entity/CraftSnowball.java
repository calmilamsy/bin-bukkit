/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntitySnowball;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Snowball;
/*    */ 
/*    */ public class CraftSnowball
/*    */   extends AbstractProjectile
/*    */   implements Snowball {
/* 12 */   public CraftSnowball(CraftServer server, EntitySnowball entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public String toString() { return "CraftSnowball"; }
/*    */ 
/*    */   
/*    */   public LivingEntity getShooter() {
/* 21 */     if (((EntitySnowball)getHandle()).shooter != null) {
/* 22 */       return (LivingEntity)((EntitySnowball)getHandle()).shooter.getBukkitEntity();
/*    */     }
/*    */     
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public void setShooter(LivingEntity shooter) {
/* 29 */     if (shooter instanceof CraftLivingEntity)
/* 30 */       ((EntitySnowball)getHandle()).shooter = (EntityLiving)((CraftLivingEntity)shooter).entity; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSnowball.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */