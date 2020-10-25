package com.avaje.ebean.config.dbplatform;

public interface SqlLimitRequest {
  boolean isDistinct();
  
  int getFirstRow();
  
  int getMaxRows();
  
  String getDbSql();
  
  String getDbOrderBy();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\SqlLimitRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */