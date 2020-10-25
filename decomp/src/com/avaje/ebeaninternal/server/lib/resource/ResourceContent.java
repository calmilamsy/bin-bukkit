package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceContent {
  String getName();
  
  long size();
  
  long lastModified();
  
  InputStream getInputStream() throws IOException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */