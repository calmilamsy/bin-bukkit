/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPortalEnterEvent
/*    */   extends EntityEvent
/*    */ {
/*    */   private Location location;
/*    */   
/*    */   public EntityPortalEnterEvent(Entity entity, Location location) {
/* 16 */     super(Event.Type.ENTITY_PORTAL_ENTER, entity);
/* 17 */     this.location = location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public Location getLocation() { return this.location; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\entity\EntityPortalEnterEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */