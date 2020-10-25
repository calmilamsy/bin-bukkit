package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;

public interface PersistExecute {
  BatchControl createBatchControl(SpiTransaction paramSpiTransaction);
  
  <T> void executeInsertBean(PersistRequestBean<T> paramPersistRequestBean);
  
  <T> void executeUpdateBean(PersistRequestBean<T> paramPersistRequestBean);
  
  <T> void executeDeleteBean(PersistRequestBean<T> paramPersistRequestBean);
  
  int executeOrmUpdate(PersistRequestOrmUpdate paramPersistRequestOrmUpdate);
  
  int executeSqlCallable(PersistRequestCallableSql paramPersistRequestCallableSql);
  
  int executeSqlUpdate(PersistRequestUpdateSql paramPersistRequestUpdateSql);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\PersistExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */