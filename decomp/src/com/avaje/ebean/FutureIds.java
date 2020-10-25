package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface FutureIds<T> extends Future<List<Object>> {
  Query<T> getQuery();
  
  List<Object> getPartialIds();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\FutureIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */