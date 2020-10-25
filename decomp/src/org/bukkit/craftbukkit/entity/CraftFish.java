/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityFish;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Fish;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftFish
/*    */   extends AbstractProjectile
/*    */   implements Fish {
/* 12 */   public CraftFish(CraftServer server, EntityFish entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public String toString() { return "CraftFish"; }
/*    */ 
/*    */   
/*    */   public LivingEntity getShooter() {
/* 21 */     if (((EntityFish)getHandle()).owner != null) {
/* 22 */       return (LivingEntity)((EntityFish)getHandle()).owner.getBukkitEntity();
/*    */     }
/*    */     
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setShooter(LivingEntity shooter) {
/* 30 */     if (shooter instanceof CraftHumanEntity)
/* 31 */       ((EntityFish)getHandle()).owner = (EntityHuman)((CraftHumanEntity)shooter).entity; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftFish.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */