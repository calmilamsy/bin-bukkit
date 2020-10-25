package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public interface ElComparator<T> extends Comparator<T> {
  int compare(T paramT1, T paramT2);
  
  int compareValue(Object paramObject, T paramT);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\el\ElComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */