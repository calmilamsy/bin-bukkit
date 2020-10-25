package com.avaje.ebean;

import java.util.Iterator;

public interface QueryIterator<T> extends Iterator<T> {
  boolean hasNext();
  
  T next();
  
  void remove();
  
  void close();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\QueryIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */