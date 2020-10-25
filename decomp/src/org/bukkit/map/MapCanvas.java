package org.bukkit.map;

import java.awt.Image;

public interface MapCanvas {
  MapView getMapView();
  
  MapCursorCollection getCursors();
  
  void setCursors(MapCursorCollection paramMapCursorCollection);
  
  void setPixel(int paramInt1, int paramInt2, byte paramByte);
  
  byte getPixel(int paramInt1, int paramInt2);
  
  byte getBasePixel(int paramInt1, int paramInt2);
  
  void drawImage(int paramInt1, int paramInt2, Image paramImage);
  
  void drawText(int paramInt1, int paramInt2, MapFont paramMapFont, String paramString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */