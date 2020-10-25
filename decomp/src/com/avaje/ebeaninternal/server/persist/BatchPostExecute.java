package com.avaje.ebeaninternal.server.persist;

import java.sql.SQLException;

public interface BatchPostExecute {
  void checkRowCount(int paramInt) throws SQLException;
  
  void setGeneratedKey(Object paramObject);
  
  void postExecute() throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BatchPostExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */