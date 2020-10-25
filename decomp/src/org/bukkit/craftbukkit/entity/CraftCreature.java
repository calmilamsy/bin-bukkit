/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Creature;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftCreature extends CraftLivingEntity implements Creature {
/* 11 */   public CraftCreature(CraftServer server, EntityCreature entity) { super(server, entity); }
/*    */ 
/*    */   
/*    */   public void setTarget(LivingEntity target) {
/* 15 */     EntityCreature entity = getHandle();
/* 16 */     if (target == null) {
/* 17 */       entity.target = null;
/* 18 */     } else if (target instanceof CraftLivingEntity) {
/* 19 */       EntityLiving victim = ((CraftLivingEntity)target).getHandle();
/* 20 */       entity.target = victim;
/* 21 */       entity.pathEntity = entity.world.findPath(entity, entity.target, 16.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CraftLivingEntity getTarget() {
/* 26 */     if ((getHandle()).target == null) return null;
/*    */     
/* 28 */     return (CraftLivingEntity)(getHandle()).target.getBukkitEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public EntityCreature getHandle() { return (EntityCreature)this.entity; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public String toString() { return "CraftCreature"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftCreature.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */