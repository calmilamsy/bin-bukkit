package com.mysql.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public interface ResultSetInternalMethods extends ResultSet {
  ResultSetInternalMethods copy() throws SQLException;
  
  boolean reallyResult();
  
  Object getObjectStoredProc(int paramInt1, int paramInt2) throws SQLException;
  
  Object getObjectStoredProc(int paramInt1, Map paramMap, int paramInt2) throws SQLException;
  
  Object getObjectStoredProc(String paramString, int paramInt) throws SQLException;
  
  Object getObjectStoredProc(String paramString, Map paramMap, int paramInt) throws SQLException;
  
  String getServerInfo();
  
  long getUpdateCount();
  
  long getUpdateID();
  
  void realClose(boolean paramBoolean) throws SQLException;
  
  void setFirstCharOfQuery(char paramChar);
  
  void setOwningStatement(StatementImpl paramStatementImpl);
  
  char getFirstCharOfQuery();
  
  void clearNextResult();
  
  ResultSetInternalMethods getNextResultSet() throws SQLException;
  
  void setStatementUsedForFetchingRows(PreparedStatement paramPreparedStatement);
  
  void setWrapperStatement(Statement paramStatement);
  
  void buildIndexMapping();
  
  void initializeWithMetadata();
  
  void redefineFieldsForDBMD(Field[] paramArrayOfField);
  
  void populateCachedMetaData(CachedResultSetMetaData paramCachedResultSetMetaData) throws SQLException;
  
  void initializeFromCachedMetaData(CachedResultSetMetaData paramCachedResultSetMetaData) throws SQLException;
  
  int getBytesSize() throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ResultSetInternalMethods.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */