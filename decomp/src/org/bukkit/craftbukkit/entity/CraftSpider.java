/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntitySpider;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Spider;
/*    */ 
/*    */ public class CraftSpider
/*    */   extends CraftMonster
/*    */   implements Spider
/*    */ {
/* 11 */   public CraftSpider(CraftServer server, EntitySpider entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftSpider"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSpider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */