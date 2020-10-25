/*    */ package org.bukkit.craftbukkit.entity;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityCreeper;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntityMonster;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Creeper;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.CreeperPowerEvent;
/*    */ 
/*    */ public class CraftCreeper extends CraftMonster implements Creeper {
/* 12 */   public CraftCreeper(CraftServer server, EntityCreeper entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public EntityCreeper getHandle() { return (EntityCreeper)super.getHandle(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public String toString() { return "CraftCreeper"; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean isPowered() { return getHandle().isPowered(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPowered(boolean powered) {
/* 31 */     CraftServer server = this.server;
/* 32 */     Entity entity = getHandle().getBukkitEntity();
/*    */     
/* 34 */     if (powered) {
/* 35 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_ON);
/* 36 */       server.getPluginManager().callEvent(event);
/*    */       
/* 38 */       if (!event.isCancelled()) {
/* 39 */         getHandle().setPowered(true);
/*    */       }
/*    */     } else {
/* 42 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_OFF);
/* 43 */       server.getPluginManager().callEvent(event);
/*    */       
/* 45 */       if (!event.isCancelled())
/* 46 */         getHandle().setPowered(false); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftCreeper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */