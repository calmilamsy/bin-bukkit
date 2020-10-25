package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;

public interface PersistHandler {
  String getBindLog();
  
  void bind() throws SQLException;
  
  void addBatch() throws SQLException;
  
  void execute() throws SQLException;
  
  void close() throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\PersistHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */