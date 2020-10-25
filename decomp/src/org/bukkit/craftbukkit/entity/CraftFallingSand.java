/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityFallingSand;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.FallingSand;
/*    */ 
/*    */ 
/*    */ public class CraftFallingSand
/*    */   extends CraftEntity
/*    */   implements FallingSand
/*    */ {
/* 12 */   public CraftFallingSand(CraftServer server, EntityFallingSand entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public String toString() { return "CraftFallingSand"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftFallingSand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */