package com.avaje.ebean.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;

public interface BootupEbeanManager {
  EbeanServer createServer(ServerConfig paramServerConfig);
  
  EbeanServer createServer(String paramString);
  
  void shutdown();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\BootupEbeanManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */