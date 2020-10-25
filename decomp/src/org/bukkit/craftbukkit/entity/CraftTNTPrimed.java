/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityTNTPrimed;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.TNTPrimed;
/*    */ 
/*    */ public class CraftTNTPrimed
/*    */   extends CraftEntity
/*    */   implements TNTPrimed {
/* 11 */   public CraftTNTPrimed(CraftServer server, EntityTNTPrimed entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public String toString() { return "CraftTNTPrimed"; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public EntityTNTPrimed getHandle() { return (EntityTNTPrimed)super.getHandle(); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public float getYield() { return (getHandle()).yield; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public boolean isIncendiary() { return (getHandle()).isIncendiary; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void setIsIncendiary(boolean isIncendiary) { (getHandle()).isIncendiary = isIncendiary; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public void setYield(float yield) { (getHandle()).yield = yield; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public int getFuseTicks() { return (getHandle()).fuseTicks; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void setFuseTicks(int fuseTicks) { (getHandle()).fuseTicks = fuseTicks; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftTNTPrimed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */