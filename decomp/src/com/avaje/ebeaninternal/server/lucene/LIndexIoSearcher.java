package com.avaje.ebeaninternal.server.lucene;

public interface LIndexIoSearcher {
  void postCommit();
  
  void refresh(boolean paramBoolean);
  
  LIndexSearch getIndexSearch();
  
  LIndexVersion getLastestVersion();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexIoSearcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */