package com.avaje.ebeaninternal.api;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlQueryListener;
import java.sql.PreparedStatement;

public interface SpiSqlQuery extends SqlQuery {
  BindParams getBindParams();
  
  String getQuery();
  
  SqlQueryListener getListener();
  
  int getFirstRow();
  
  int getMaxRows();
  
  int getBackgroundFetchAfter();
  
  String getMapKey();
  
  int getTimeout();
  
  int getBufferFetchSizeHint();
  
  boolean isFutureFetch();
  
  void setFutureFetch(boolean paramBoolean);
  
  void setPreparedStatement(PreparedStatement paramPreparedStatement);
  
  boolean isCancelled();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiSqlQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */