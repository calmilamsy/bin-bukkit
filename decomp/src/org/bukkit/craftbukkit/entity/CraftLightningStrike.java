/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityWeatherStorm;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ 
/*    */ public class CraftLightningStrike extends CraftEntity implements LightningStrike {
/*  9 */   public CraftLightningStrike(CraftServer server, EntityWeatherStorm entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   public EntityWeatherStorm getHandle() { return (EntityWeatherStorm)super.getHandle(); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public boolean isEffect() { return ((EntityWeatherStorm)super.getHandle()).isEffect; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftLightningStrike.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */