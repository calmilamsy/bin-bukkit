/*    */ package org.bukkit.map;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MapRenderer
/*    */ {
/*    */   private boolean contextual;
/*    */   
/* 16 */   public MapRenderer() { this(false); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public MapRenderer(boolean contextual) { this.contextual = contextual; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public final boolean isContextual() { return this.contextual; }
/*    */   
/*    */   public void initialize(MapView map) {}
/*    */   
/*    */   public abstract void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer);
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */