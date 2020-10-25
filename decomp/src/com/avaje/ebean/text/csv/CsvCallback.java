package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;

public interface CsvCallback<T> {
  void begin(EbeanServer paramEbeanServer);
  
  void readHeader(String[] paramArrayOfString);
  
  boolean processLine(int paramInt, String[] paramArrayOfString);
  
  void processBean(int paramInt, String[] paramArrayOfString, T paramT);
  
  void end(int paramInt);
  
  void endWithError(int paramInt, Exception paramException);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\csv\CsvCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */