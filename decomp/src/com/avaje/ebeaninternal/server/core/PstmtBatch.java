package com.avaje.ebeaninternal.server.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PstmtBatch {
  void setBatchSize(PreparedStatement paramPreparedStatement, int paramInt);
  
  void addBatch(PreparedStatement paramPreparedStatement) throws SQLException;
  
  int executeBatch(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean) throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\PstmtBatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */