/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class PaintingBreakEvent
/*    */   extends PaintingEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private RemoveCause cause;
/*    */   
/*    */   public PaintingBreakEvent(Painting painting, RemoveCause cause) {
/* 16 */     super(Event.Type.PAINTING_BREAK, painting);
/* 17 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public RemoveCause getCause() { return this.cause; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum RemoveCause
/*    */   {
/* 44 */     ENTITY,
/*    */ 
/*    */ 
/*    */     
/* 48 */     WORLD;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\painting\PaintingBreakEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */