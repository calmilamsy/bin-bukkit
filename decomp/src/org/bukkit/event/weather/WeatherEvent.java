/*    */ package org.bukkit.event.weather;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class WeatherEvent
/*    */   extends Event
/*    */ {
/*    */   protected World world;
/*    */   
/*    */   public WeatherEvent(Event.Type type, World where) {
/* 13 */     super(type);
/* 14 */     this.world = where;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final World getWorld() { return this.world; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\weather\WeatherEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */