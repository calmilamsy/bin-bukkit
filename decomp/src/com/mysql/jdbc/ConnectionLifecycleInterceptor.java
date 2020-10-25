package com.mysql.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

public interface ConnectionLifecycleInterceptor extends Extension {
  void close() throws SQLException;
  
  boolean commit() throws SQLException;
  
  boolean rollback() throws SQLException;
  
  boolean rollback(Savepoint paramSavepoint) throws SQLException;
  
  boolean setAutoCommit(boolean paramBoolean) throws SQLException;
  
  boolean setCatalog(String paramString) throws SQLException;
  
  boolean transactionBegun() throws SQLException;
  
  boolean transactionCompleted() throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ConnectionLifecycleInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */