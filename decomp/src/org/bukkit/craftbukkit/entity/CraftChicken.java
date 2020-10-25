/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityChicken;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Chicken;
/*    */ 
/*    */ public class CraftChicken
/*    */   extends CraftAnimals
/*    */   implements Chicken {
/* 10 */   public CraftChicken(CraftServer server, EntityChicken entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftChicken"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftChicken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */