package com.mysql.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import java.util.Properties;

public interface JDBC4MySQLConnection extends MySQLConnection {
  SQLXML createSQLXML() throws SQLException;
  
  Array createArrayOf(String paramString, Object[] paramArrayOfObject) throws SQLException;
  
  Struct createStruct(String paramString, Object[] paramArrayOfObject) throws SQLException;
  
  Properties getClientInfo() throws SQLException;
  
  String getClientInfo(String paramString) throws SQLException;
  
  boolean isValid(int paramInt) throws SQLException;
  
  void setClientInfo(Properties paramProperties) throws SQLClientInfoException;
  
  void setClientInfo(String paramString1, String paramString2) throws SQLClientInfoException;
  
  boolean isWrapperFor(Class<?> paramClass) throws SQLException;
  
  <T> T unwrap(Class<T> paramClass) throws SQLException;
  
  Blob createBlob();
  
  Clob createClob();
  
  NClob createNClob();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4MySQLConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */