/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntitySkeleton;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Skeleton;
/*    */ 
/*    */ public class CraftSkeleton
/*    */   extends CraftMonster
/*    */   implements Skeleton
/*    */ {
/* 11 */   public CraftSkeleton(CraftServer server, EntitySkeleton entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftSkeleton"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSkeleton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */