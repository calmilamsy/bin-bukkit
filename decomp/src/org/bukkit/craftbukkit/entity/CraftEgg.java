/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityEgg;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Egg;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftEgg
/*    */   extends AbstractProjectile
/*    */   implements Egg
/*    */ {
/* 13 */   public CraftEgg(CraftServer server, EntityEgg entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   public String toString() { return "CraftEgg"; }
/*    */ 
/*    */   
/*    */   public LivingEntity getShooter() {
/* 22 */     if (((EntityEgg)getHandle()).thrower != null) {
/* 23 */       return (LivingEntity)((EntityEgg)getHandle()).thrower.getBukkitEntity();
/*    */     }
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setShooter(LivingEntity shooter) {
/* 31 */     if (shooter instanceof CraftLivingEntity)
/* 32 */       ((EntityEgg)getHandle()).thrower = (EntityLiving)((CraftLivingEntity)shooter).entity; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftEgg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */