package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public interface StatementInterceptor extends Extension {
  void init(Connection paramConnection, Properties paramProperties) throws SQLException;
  
  ResultSetInternalMethods preProcess(String paramString, Statement paramStatement, Connection paramConnection) throws SQLException;
  
  ResultSetInternalMethods postProcess(String paramString, Statement paramStatement, ResultSetInternalMethods paramResultSetInternalMethods, Connection paramConnection) throws SQLException;
  
  boolean executeTopLevelOnly();
  
  void destroy();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\StatementInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */