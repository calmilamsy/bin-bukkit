package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

public interface ScalarType<T> extends StringParser, StringFormatter, ScalarDataReader<T> {
  int getLength();
  
  boolean isJdbcNative();
  
  int getJdbcType();
  
  Class<T> getType();
  
  T read(DataReader paramDataReader) throws SQLException;
  
  void loadIgnore(DataReader paramDataReader);
  
  void bind(DataBind paramDataBind, T paramT) throws SQLException;
  
  Object toJdbcType(Object paramObject);
  
  T toBeanType(Object paramObject);
  
  String formatValue(T paramT);
  
  String format(Object paramObject);
  
  T parse(String paramString);
  
  T parseDateTime(long paramLong);
  
  boolean isDateTimeCapable();
  
  String jsonToString(T paramT, JsonValueAdapter paramJsonValueAdapter);
  
  T jsonFromString(String paramString, JsonValueAdapter paramJsonValueAdapter);
  
  Object readData(DataInput paramDataInput) throws IOException;
  
  void writeData(DataOutput paramDataOutput, Object paramObject) throws IOException;
  
  Object luceneToIndexValue(Object paramObject);
  
  Object luceneFromIndexValue(Object paramObject);
  
  int getLuceneType();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */