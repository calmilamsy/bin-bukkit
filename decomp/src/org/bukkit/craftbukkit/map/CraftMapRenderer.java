/*    */ package org.bukkit.craftbukkit.map;
/*    */ 
/*    */ import net.minecraft.server.WorldMap;
/*    */ import net.minecraft.server.WorldMapOrienter;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.map.MapCanvas;
/*    */ import org.bukkit.map.MapCursorCollection;
/*    */ import org.bukkit.map.MapRenderer;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ public class CraftMapRenderer
/*    */   extends MapRenderer
/*    */ {
/*    */   private final CraftMapView mapView;
/*    */   private final WorldMap worldMap;
/*    */   
/*    */   public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
/* 18 */     super(false);
/* 19 */     this.mapView = mapView;
/* 20 */     this.worldMap = worldMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(MapView map, MapCanvas canvas, Player player) {
/* 26 */     for (x = 0; x < 128; x++) {
/* 27 */       for (int y = 0; y < 128; y++) {
/* 28 */         canvas.setPixel(x, y, this.worldMap.f[y * 128 + x]);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 33 */     MapCursorCollection cursors = canvas.getCursors();
/* 34 */     while (cursors.size() > 0) {
/* 35 */       cursors.removeCursor(cursors.getCursor(0));
/*    */     }
/* 37 */     for (int i = 0; i < this.worldMap.i.size(); i++) {
/* 38 */       WorldMapOrienter orienter = (WorldMapOrienter)this.worldMap.i.get(i);
/* 39 */       cursors.addCursor(orienter.b, orienter.c, (byte)(orienter.d & 0xF), orienter.a);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\map\CraftMapRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */