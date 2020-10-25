package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import java.util.List;

public interface BeanFinderManager {
  int getRegisterCount();
  
  int createBeanFinders(List<Class<?>> paramList);
  
  <T> BeanFinder<T> getBeanFinder(Class<T> paramClass);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanFinderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */