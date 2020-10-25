package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceNotify {
  void notifyDataSourceUp(String paramString);
  
  void notifyDataSourceDown(String paramString);
  
  void notifyWarning(String paramString1, String paramString2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceNotify.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */