package com.avaje.ebean;

public interface Update<T> {
  String getName();
  
  Update<T> setNotifyCache(boolean paramBoolean);
  
  Update<T> setTimeout(int paramInt);
  
  int execute();
  
  Update<T> set(int paramInt, Object paramObject);
  
  Update<T> setParameter(int paramInt, Object paramObject);
  
  Update<T> setNull(int paramInt1, int paramInt2);
  
  Update<T> setNullParameter(int paramInt1, int paramInt2);
  
  Update<T> set(String paramString, Object paramObject);
  
  Update<T> setParameter(String paramString, Object paramObject);
  
  Update<T> setNull(String paramString, int paramInt);
  
  Update<T> setNullParameter(String paramString, int paramInt);
  
  String getGeneratedSql();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Update.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */