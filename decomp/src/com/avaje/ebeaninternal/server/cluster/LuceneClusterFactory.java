package com.avaje.ebeaninternal.server.cluster;

public interface LuceneClusterFactory {
  LuceneClusterListener createListener(ClusterManager paramClusterManager, int paramInt);
  
  LuceneClusterIndexSync createIndexSync();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\LuceneClusterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */