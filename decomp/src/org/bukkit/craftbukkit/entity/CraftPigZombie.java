/*    */ package org.bukkit.craftbukkit.entity;
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntityMonster;
/*    */ import net.minecraft.server.EntityPigZombie;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.PigZombie;
/*    */ 
/*    */ public class CraftPigZombie extends CraftZombie implements PigZombie {
/* 11 */   public CraftPigZombie(CraftServer server, EntityPigZombie entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public EntityPigZombie getHandle() { return (EntityPigZombie)super.getHandle(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public String toString() { return "CraftPigZombie"; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public int getAnger() { return (getHandle()).angerLevel; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void setAnger(int level) { (getHandle()).angerLevel = level; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setAngry(boolean angry) { setAnger(angry ? 400 : 0); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isAngry() { return (getAnger() > 0); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftPigZombie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */