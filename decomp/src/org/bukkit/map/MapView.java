/*    */ package org.bukkit.map;public interface MapView { short getId();
/*    */   boolean isVirtual();
/*    */   Scale getScale();
/*    */   void setScale(Scale paramScale);
/*    */   int getCenterX();
/*    */   int getCenterZ();
/*    */   void setCenterX(int paramInt);
/*    */   void setCenterZ(int paramInt);
/*    */   World getWorld();
/*    */   void setWorld(World paramWorld);
/*    */   List<MapRenderer> getRenderers();
/*    */   void addRenderer(MapRenderer paramMapRenderer);
/*    */   boolean removeRenderer(MapRenderer paramMapRenderer);
/*    */   
/* 15 */   public enum Scale { CLOSEST(false),
/* 16 */     CLOSE(true),
/* 17 */     NORMAL(2),
/* 18 */     FAR(3),
/* 19 */     FARTHEST(4);
/*    */     
/*    */     private byte value;
/*    */ 
/*    */     
/* 24 */     Scale(int value) { this.value = (byte)value; }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     public byte getValue() { return this.value; } }
/*    */    }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */