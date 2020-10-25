/*    */ package org.bukkit.map;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MapCursorCollection
/*    */ {
/* 12 */   private List<MapCursor> cursors = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 19 */   public int size() { return this.cursors.size(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public MapCursor getCursor(int index) { return (MapCursor)this.cursors.get(index); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean removeCursor(MapCursor cursor) { return this.cursors.remove(cursor); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapCursor addCursor(MapCursor cursor) {
/* 46 */     this.cursors.add(cursor);
/* 47 */     return cursor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public MapCursor addCursor(int x, int y, byte direction) { return addCursor(x, y, direction, (byte)0, true); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public MapCursor addCursor(int x, int y, byte direction, byte type) { return addCursor(x, y, direction, type, true); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 83 */   public MapCursor addCursor(int x, int y, byte direction, byte type, boolean visible) { return addCursor(new MapCursor((byte)x, (byte)y, direction, type, visible)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapCursorCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */