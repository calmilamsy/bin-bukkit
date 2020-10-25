/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntitySquid;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Squid;
/*    */ 
/*    */ public class CraftSquid
/*    */   extends CraftWaterMob
/*    */   implements Squid
/*    */ {
/* 11 */   public CraftSquid(CraftServer server, EntitySquid entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftSquid"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSquid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */