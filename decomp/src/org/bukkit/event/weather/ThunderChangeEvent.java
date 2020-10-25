/*    */ package org.bukkit.event.weather;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ThunderChangeEvent
/*    */   extends WeatherEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean canceled;
/*    */   private boolean to;
/*    */   
/*    */   public ThunderChangeEvent(World world, boolean to) {
/* 15 */     super(Event.Type.THUNDER_CHANGE, world);
/* 16 */     this.to = to;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean toThunderState() { return this.to; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\weather\ThunderChangeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */