package com.avaje.ebean;

public interface SqlUpdate {
  int execute();
  
  boolean isAutoTableMod();
  
  SqlUpdate setAutoTableMod(boolean paramBoolean);
  
  String getLabel();
  
  SqlUpdate setLabel(String paramString);
  
  String getSql();
  
  int getTimeout();
  
  SqlUpdate setTimeout(int paramInt);
  
  SqlUpdate setParameter(int paramInt, Object paramObject);
  
  SqlUpdate setNull(int paramInt1, int paramInt2);
  
  SqlUpdate setNullParameter(int paramInt1, int paramInt2);
  
  SqlUpdate setParameter(String paramString, Object paramObject);
  
  SqlUpdate setNull(String paramString, int paramInt);
  
  SqlUpdate setNullParameter(String paramString, int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\SqlUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */