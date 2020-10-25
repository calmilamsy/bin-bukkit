package com.avaje.ebean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SqlQuery extends Serializable {
  void cancel();
  
  List<SqlRow> findList();
  
  Set<SqlRow> findSet();
  
  Map<?, SqlRow> findMap();
  
  SqlRow findUnique();
  
  SqlFutureList findFutureList();
  
  SqlQuery setParameter(String paramString, Object paramObject);
  
  SqlQuery setParameter(int paramInt, Object paramObject);
  
  SqlQuery setListener(SqlQueryListener paramSqlQueryListener);
  
  SqlQuery setFirstRow(int paramInt);
  
  SqlQuery setMaxRows(int paramInt);
  
  SqlQuery setBackgroundFetchAfter(int paramInt);
  
  SqlQuery setMapKey(String paramString);
  
  SqlQuery setTimeout(int paramInt);
  
  SqlQuery setBufferFetchSizeHint(int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\SqlQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */