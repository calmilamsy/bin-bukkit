package com.avaje.ebean.config;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;

public interface NamingConvention {
  void setDatabasePlatform(DatabasePlatform paramDatabasePlatform);
  
  TableName getTableName(Class<?> paramClass);
  
  TableName getM2MJoinTableName(TableName paramTableName1, TableName paramTableName2);
  
  String getColumnFromProperty(Class<?> paramClass, String paramString);
  
  String getPropertyFromColumn(Class<?> paramClass, String paramString);
  
  String getSequenceName(String paramString1, String paramString2);
  
  boolean isUseForeignKeyPrefix();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\NamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */