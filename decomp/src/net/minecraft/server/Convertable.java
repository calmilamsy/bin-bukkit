package net.minecraft.server;

public interface Convertable {
  boolean isConvertable(String paramString);
  
  boolean convert(String paramString, IProgressUpdate paramIProgressUpdate);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Convertable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */