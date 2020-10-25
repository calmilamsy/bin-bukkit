/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityGiantZombie;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Giant;
/*    */ 
/*    */ public class CraftGiant
/*    */   extends CraftMonster
/*    */   implements Giant
/*    */ {
/* 11 */   public CraftGiant(CraftServer server, EntityGiantZombie entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftGiant"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftGiant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */