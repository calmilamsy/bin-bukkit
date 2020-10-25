package net.minecraft.server;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IDataManager {
  WorldData c();
  
  void b();
  
  IChunkLoader a(WorldProvider paramWorldProvider);
  
  void a(WorldData paramWorldData, List paramList);
  
  void a(WorldData paramWorldData);
  
  PlayerFileData d();
  
  void e();
  
  File b(String paramString);
  
  UUID getUUID();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\IDataManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */