package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public interface ScalarDataReader<T> {
  T read(DataReader paramDataReader) throws SQLException;
  
  void loadIgnore(DataReader paramDataReader);
  
  void bind(DataBind paramDataBind, T paramT) throws SQLException;
  
  void accumulateScalarTypes(String paramString, CtCompoundTypeScalarList paramCtCompoundTypeScalarList);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarDataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */