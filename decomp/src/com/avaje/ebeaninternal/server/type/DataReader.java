package com.avaje.ebeaninternal.server.type;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public interface DataReader {
  void close() throws SQLException;
  
  boolean next() throws SQLException;
  
  void resetColumnPosition() throws SQLException;
  
  void incrementPos(int paramInt);
  
  byte[] getBinaryBytes() throws SQLException;
  
  byte[] getBlobBytes() throws SQLException;
  
  String getStringFromStream() throws SQLException;
  
  String getStringClob() throws SQLException;
  
  String getString() throws SQLException;
  
  Boolean getBoolean() throws SQLException;
  
  Byte getByte() throws SQLException;
  
  Short getShort() throws SQLException;
  
  Integer getInt() throws SQLException;
  
  Long getLong() throws SQLException;
  
  Float getFloat() throws SQLException;
  
  Double getDouble() throws SQLException;
  
  byte[] getBytes() throws SQLException;
  
  Date getDate() throws SQLException;
  
  Time getTime() throws SQLException;
  
  Timestamp getTimestamp() throws SQLException;
  
  BigDecimal getBigDecimal() throws SQLException;
  
  Array getArray() throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\DataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */