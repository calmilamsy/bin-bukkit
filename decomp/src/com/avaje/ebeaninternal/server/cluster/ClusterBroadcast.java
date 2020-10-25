package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;

public interface ClusterBroadcast {
  void startup(ClusterManager paramClusterManager);
  
  void shutdown();
  
  void broadcast(RemoteTransactionEvent paramRemoteTransactionEvent);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\ClusterBroadcast.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */