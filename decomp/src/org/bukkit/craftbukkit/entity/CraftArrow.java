/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityArrow;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Arrow;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftArrow
/*    */   extends AbstractProjectile
/*    */   implements Arrow
/*    */ {
/* 13 */   public CraftArrow(CraftServer server, EntityArrow entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   public String toString() { return "CraftArrow"; }
/*    */ 
/*    */   
/*    */   public LivingEntity getShooter() {
/* 22 */     if (((EntityArrow)getHandle()).shooter != null) {
/* 23 */       return (LivingEntity)((EntityArrow)getHandle()).shooter.getBukkitEntity();
/*    */     }
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setShooter(LivingEntity shooter) {
/* 31 */     if (shooter instanceof CraftLivingEntity)
/* 32 */       ((EntityArrow)getHandle()).shooter = (EntityLiving)((CraftLivingEntity)shooter).entity; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftArrow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */