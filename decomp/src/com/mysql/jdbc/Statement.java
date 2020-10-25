package com.mysql.jdbc;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface Statement extends Statement {
  void enableStreamingResults() throws SQLException;
  
  void disableStreamingResults() throws SQLException;
  
  void setLocalInfileInputStream(InputStream paramInputStream);
  
  InputStream getLocalInfileInputStream();
  
  void setPingTarget(PingTarget paramPingTarget);
  
  ExceptionInterceptor getExceptionInterceptor();
  
  void removeOpenResultSet(ResultSet paramResultSet);
  
  int getOpenResultSetCount();
  
  void setHoldResultsOpenOverClose(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Statement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */