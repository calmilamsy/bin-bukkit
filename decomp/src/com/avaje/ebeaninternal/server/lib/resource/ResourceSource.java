package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;

public interface ResourceSource {
  String getRealPath();
  
  ResourceContent getContent(String paramString);
  
  String readString(ResourceContent paramResourceContent, int paramInt) throws IOException;
  
  byte[] readBytes(ResourceContent paramResourceContent, int paramInt) throws IOException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */