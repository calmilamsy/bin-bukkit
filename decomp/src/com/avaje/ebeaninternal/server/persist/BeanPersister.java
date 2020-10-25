package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import javax.persistence.PersistenceException;

public interface BeanPersister {
  void insert(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
  
  void update(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
  
  void delete(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BeanPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */