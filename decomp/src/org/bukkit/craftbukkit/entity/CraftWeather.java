/*    */ package org.bukkit.craftbukkit.entity;
/*    */ 
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityWeather;
/*    */ import org.bukkit.craftbukkit.CraftServer;
/*    */ import org.bukkit.entity.Weather;
/*    */ 
/*    */ public class CraftWeather
/*    */   extends CraftEntity implements Weather {
/* 10 */   public CraftWeather(CraftServer server, EntityWeather entity) { super(server, entity); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public EntityWeather getHandle() { return (EntityWeather)super.getHandle(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftWeather.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */