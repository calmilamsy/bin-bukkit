package com.avaje.ebeaninternal.server.jmx;

import com.avaje.ebean.LogLevel;

public interface MAdminLoggingMBean {
  LogLevel getLogLevel();
  
  void setLogLevel(LogLevel paramLogLevel);
  
  boolean isDebugGeneratedSql();
  
  void setDebugGeneratedSql(boolean paramBoolean);
  
  boolean isDebugLazyLoad();
  
  void setDebugLazyLoad(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\jmx\MAdminLoggingMBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */