/*    */ package org.bukkit.craftbukkit.entity;
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityAnimal;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Animals;
/*    */ 
/*    */ public class CraftAnimals extends CraftCreature implements Animals {
/* 10 */   public CraftAnimals(CraftServer server, EntityAnimal entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftAnimals"; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   public EntityAnimal getHandle() { return (EntityAnimal)this.entity; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftAnimals.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */