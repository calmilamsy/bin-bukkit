/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PaintingBreakByWorldEvent
/*    */   extends PaintingBreakEvent
/*    */ {
/* 10 */   public PaintingBreakByWorldEvent(Painting painting) { super(painting, PaintingBreakEvent.RemoveCause.WORLD); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\painting\PaintingBreakByWorldEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */