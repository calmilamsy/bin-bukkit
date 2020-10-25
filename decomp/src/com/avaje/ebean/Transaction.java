package com.avaje.ebean;

import java.sql.Connection;
import javax.persistence.PersistenceException;

public interface Transaction {
  public static final int READ_COMMITTED = 2;
  
  public static final int READ_UNCOMMITTED = 1;
  
  public static final int REPEATABLE_READ = 4;
  
  public static final int SERIALIZABLE = 8;
  
  void waitForIndexUpdates();
  
  boolean isReadOnly();
  
  void setReadOnly(boolean paramBoolean);
  
  void log(String paramString);
  
  void setLogLevel(LogLevel paramLogLevel);
  
  LogLevel getLogLevel();
  
  void setLoggingOn(boolean paramBoolean);
  
  void commit();
  
  void rollback();
  
  void rollback(Throwable paramThrowable) throws PersistenceException;
  
  void end();
  
  boolean isActive();
  
  void setPersistCascade(boolean paramBoolean);
  
  void setBatchMode(boolean paramBoolean);
  
  void setBatchSize(int paramInt);
  
  void setBatchGetGeneratedKeys(boolean paramBoolean);
  
  void setBatchFlushOnMixed(boolean paramBoolean);
  
  void setBatchFlushOnQuery(boolean paramBoolean);
  
  boolean isBatchFlushOnQuery();
  
  void flushBatch();
  
  void batchFlush();
  
  Connection getConnection();
  
  void addModification(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Transaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */