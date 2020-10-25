package com.avaje.ebean;

public interface AdminLogging {
  void setLogLevel(LogLevel paramLogLevel);
  
  LogLevel getLogLevel();
  
  boolean isDebugGeneratedSql();
  
  void setDebugGeneratedSql(boolean paramBoolean);
  
  boolean isDebugLazyLoad();
  
  void setDebugLazyLoad(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\AdminLogging.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */