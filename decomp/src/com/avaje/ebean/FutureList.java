package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface FutureList<T> extends Future<List<T>> {
  Query<T> getQuery();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\FutureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */