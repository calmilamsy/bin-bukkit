/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityZombie;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Zombie;
/*    */ 
/*    */ public class CraftZombie
/*    */   extends CraftMonster
/*    */   implements Zombie
/*    */ {
/* 11 */   public CraftZombie(CraftServer server, EntityZombie entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftZombie"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftZombie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */