/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.EntityPainting;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ public class CraftPainting
/*    */   extends CraftEntity
/*    */   implements Painting
/*    */ {
/* 11 */   public CraftPainting(CraftServer server, EntityPainting entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftPainting"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftPainting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */