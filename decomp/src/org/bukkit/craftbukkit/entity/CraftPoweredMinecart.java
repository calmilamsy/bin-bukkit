/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityMinecart;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.PoweredMinecart;
/*    */ 
/*    */ public class CraftPoweredMinecart
/*    */   extends CraftMinecart
/*    */   implements PoweredMinecart {
/* 10 */   public CraftPoweredMinecart(CraftServer server, EntityMinecart entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftPoweredMinecart"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftPoweredMinecart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */