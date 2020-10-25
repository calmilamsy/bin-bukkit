package net.minecraft.server;

public interface IChunkProvider {
  boolean isChunkLoaded(int paramInt1, int paramInt2);
  
  Chunk getOrCreateChunk(int paramInt1, int paramInt2);
  
  Chunk getChunkAt(int paramInt1, int paramInt2);
  
  void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2);
  
  boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate);
  
  boolean unloadChunks();
  
  boolean canSave();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\IChunkProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */