/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ 
/*    */ public class MapInitializeEvent
/*    */   extends ServerEvent
/*    */ {
/*    */   private final MapView mapView;
/*    */   
/*    */   public MapInitializeEvent(MapView mapView) {
/* 13 */     super(Event.Type.MAP_INITIALIZE);
/* 14 */     this.mapView = mapView;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public MapView getMap() { return this.mapView; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\server\MapInitializeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */