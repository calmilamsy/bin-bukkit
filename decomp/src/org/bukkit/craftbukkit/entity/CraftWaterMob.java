/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityWaterAnimal;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.WaterMob;
/*    */ 
/*    */ public class CraftWaterMob
/*    */   extends CraftCreature
/*    */   implements WaterMob
/*    */ {
/* 11 */   public CraftWaterMob(CraftServer server, EntityWaterAnimal entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftWaterMob"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftWaterMob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */