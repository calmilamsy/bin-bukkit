/*    */ package org.bukkit.craftbukkit.entity;
/*    */ import net.minecraft.server.EntityAnimal;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntityPig;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ 
/*    */ public class CraftPig extends CraftAnimals implements Pig {
/*  9 */   public CraftPig(CraftServer server, EntityPig entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */   
/* 13 */   public boolean hasSaddle() { return getHandle().hasSaddle(); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public void setSaddle(boolean saddled) { getHandle().setSaddle(saddled); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public EntityPig getHandle() { return (EntityPig)super.getHandle(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public String toString() { return "CraftPig"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftPig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */