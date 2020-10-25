/*    */ package org.bukkit.craftbukkit.entity;
/*    */ import net.minecraft.server.EntityAnimal;
/*    */ import net.minecraft.server.EntityCreature;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntitySheep;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Sheep;
/*    */ 
/*    */ public class CraftSheep extends CraftAnimals implements Sheep {
/* 11 */   public CraftSheep(CraftServer server, EntitySheep entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public EntitySheep getHandle() { return (EntitySheep)this.entity; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public String toString() { return "CraftSheep"; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public DyeColor getColor() { return DyeColor.getByData((byte)getHandle().getColor()); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void setColor(DyeColor color) { getHandle().setColor(color.getData()); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean isSheared() { return getHandle().isSheared(); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void setSheared(boolean flag) { getHandle().setSheared(flag); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSheep.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */