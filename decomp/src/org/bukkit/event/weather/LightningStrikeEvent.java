/*    */ package org.bukkit.event.weather;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class LightningStrikeEvent
/*    */   extends WeatherEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean canceled;
/*    */   private LightningStrike bolt;
/*    */   private World world;
/*    */   
/*    */   public LightningStrikeEvent(World world, LightningStrike bolt) {
/* 18 */     super(Event.Type.LIGHTNING_STRIKE, world);
/* 19 */     this.bolt = bolt;
/* 20 */     this.world = world;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public boolean isCancelled() { return this.canceled; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void setCancelled(boolean cancel) { this.canceled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public LightningStrike getLightning() { return this.bolt; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\weather\LightningStrikeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */