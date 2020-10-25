/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ 
/*    */ public class PaintingBreakByEntityEvent
/*    */   extends PaintingBreakEvent
/*    */ {
/*    */   private Entity remover;
/*    */   
/*    */   public PaintingBreakByEntityEvent(Painting painting, Entity remover) {
/* 13 */     super(painting, PaintingBreakEvent.RemoveCause.ENTITY);
/* 14 */     this.remover = remover;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Entity getRemover() { return this.remover; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\painting\PaintingBreakByEntityEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */