package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceAlertListener {
  void dataSourceDown(String paramString);
  
  void dataSourceUp(String paramString);
  
  void warning(String paramString1, String paramString2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceAlertListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */