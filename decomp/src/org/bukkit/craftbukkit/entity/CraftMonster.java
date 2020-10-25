/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntityMonster;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Monster;
/*    */ 
/*    */ public class CraftMonster extends CraftCreature implements Monster {
/* 11 */   public CraftMonster(CraftServer server, EntityMonster entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftMonster"; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public EntityMonster getHandle() { return (EntityMonster)this.entity; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftMonster.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */