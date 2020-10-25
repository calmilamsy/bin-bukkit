/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EntitySlime;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Slime;
/*    */ 
/*    */ public class CraftSlime
/*    */   extends CraftLivingEntity implements Slime {
/* 11 */   public CraftSlime(CraftServer server, EntitySlime entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftSlime"; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public EntitySlime getHandle() { return (EntitySlime)super.getHandle(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public int getSize() { return getHandle().getSize(); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void setSize(int size) { getHandle().setSize(size); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftSlime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */