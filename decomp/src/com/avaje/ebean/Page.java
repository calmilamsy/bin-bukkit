package com.avaje.ebean;

import java.util.List;

public interface Page<T> {
  List<T> getList();
  
  int getTotalRowCount();
  
  int getTotalPageCount();
  
  int getPageIndex();
  
  boolean hasNext();
  
  boolean hasPrev();
  
  Page<T> next();
  
  Page<T> prev();
  
  String getDisplayXtoYofZ(String paramString1, String paramString2);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Page.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */