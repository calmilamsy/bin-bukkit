package com.avaje.ebean;

public interface AdminAutofetch {
  boolean isProfiling();
  
  void setProfiling(boolean paramBoolean);
  
  boolean isQueryTuning();
  
  void setQueryTuning(boolean paramBoolean);
  
  double getProfilingRate();
  
  void setProfilingRate(double paramDouble);
  
  int getProfilingBase();
  
  void setProfilingBase(int paramInt);
  
  int getProfilingMin();
  
  void setProfilingMin(int paramInt);
  
  String collectUsageViaGC();
  
  String updateTunedQueryInfo();
  
  int clearTunedQueryInfo();
  
  int clearProfilingInfo();
  
  void clearQueryStatistics();
  
  int getTotalTunedQueryCount();
  
  int getTotalTunedQuerySize();
  
  int getTotalProfileSize();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\AdminAutofetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */