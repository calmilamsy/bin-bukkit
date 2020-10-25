package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface PagingList<T> {
  void refresh();
  
  PagingList<T> setFetchAhead(boolean paramBoolean);
  
  Future<Integer> getFutureRowCount();
  
  List<T> getAsList();
  
  int getPageSize();
  
  int getTotalRowCount();
  
  int getTotalPageCount();
  
  Page<T> getPage(int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\PagingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */