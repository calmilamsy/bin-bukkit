/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PaintingEvent
/*    */   extends Event
/*    */ {
/*    */   protected Painting painting;
/*    */   
/*    */   protected PaintingEvent(Event.Type type, Painting painting) {
/* 14 */     super(type);
/* 15 */     this.painting = painting;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Painting getPainting() { return this.painting; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\painting\PaintingEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */