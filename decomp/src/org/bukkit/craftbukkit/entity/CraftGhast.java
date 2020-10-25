/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityGhast;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Ghast;
/*    */ 
/*    */ public class CraftGhast
/*    */   extends CraftFlying
/*    */   implements Ghast {
/* 10 */   public CraftGhast(CraftServer server, EntityGhast entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftGhast"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftGhast.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */