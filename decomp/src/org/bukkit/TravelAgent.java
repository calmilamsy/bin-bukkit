package org.bukkit;

public interface TravelAgent {
  TravelAgent setSearchRadius(int paramInt);
  
  int getSearchRadius();
  
  TravelAgent setCreationRadius(int paramInt);
  
  int getCreationRadius();
  
  boolean getCanCreatePortal();
  
  void setCanCreatePortal(boolean paramBoolean);
  
  Location findOrCreate(Location paramLocation);
  
  Location findPortal(Location paramLocation);
  
  boolean createPortal(Location paramLocation);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\TravelAgent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */