package com.avaje.ebean;

import java.util.List;
import java.util.Set;

public interface Filter<T> {
  Filter<T> sort(String paramString);
  
  Filter<T> maxRows(int paramInt);
  
  Filter<T> eq(String paramString, Object paramObject);
  
  Filter<T> ne(String paramString, Object paramObject);
  
  Filter<T> ieq(String paramString1, String paramString2);
  
  Filter<T> between(String paramString, Object paramObject1, Object paramObject2);
  
  Filter<T> gt(String paramString, Object paramObject);
  
  Filter<T> ge(String paramString, Object paramObject);
  
  Filter<T> lt(String paramString, Object paramObject);
  
  Filter<T> le(String paramString, Object paramObject);
  
  Filter<T> isNull(String paramString);
  
  Filter<T> isNotNull(String paramString);
  
  Filter<T> startsWith(String paramString1, String paramString2);
  
  Filter<T> istartsWith(String paramString1, String paramString2);
  
  Filter<T> endsWith(String paramString1, String paramString2);
  
  Filter<T> iendsWith(String paramString1, String paramString2);
  
  Filter<T> contains(String paramString1, String paramString2);
  
  Filter<T> icontains(String paramString1, String paramString2);
  
  Filter<T> in(String paramString, Set<?> paramSet);
  
  List<T> filter(List<T> paramList);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Filter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */