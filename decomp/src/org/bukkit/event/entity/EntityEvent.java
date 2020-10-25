/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class EntityEvent
/*    */   extends Event
/*    */ {
/*    */   protected Entity entity;
/*    */   
/*    */   public EntityEvent(Event.Type type, Entity what) {
/* 13 */     super(type);
/* 14 */     this.entity = what;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final Entity getEntity() { return this.entity; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */