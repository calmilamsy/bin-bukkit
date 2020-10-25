/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityFlying;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Flying;
/*    */ 
/*    */ public class CraftFlying
/*    */   extends CraftLivingEntity
/*    */   implements Flying {
/* 10 */   public CraftFlying(CraftServer server, EntityFlying entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftFlying"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftFlying.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */