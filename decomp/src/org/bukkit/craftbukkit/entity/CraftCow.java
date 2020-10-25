/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityCow;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Cow;
/*    */ 
/*    */ public class CraftCow
/*    */   extends CraftAnimals
/*    */   implements Cow {
/* 10 */   public CraftCow(CraftServer server, EntityCow entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public String toString() { return "CraftCow"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftCow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */