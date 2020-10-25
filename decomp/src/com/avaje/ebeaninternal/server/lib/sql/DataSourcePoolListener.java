package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.Connection;

public interface DataSourcePoolListener {
  void onAfterBorrowConnection(Connection paramConnection);
  
  void onBeforeReturnConnection(Connection paramConnection);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourcePoolListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */