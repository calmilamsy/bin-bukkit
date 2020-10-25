package com.avaje.ebean.text.csv;

import com.avaje.ebean.text.StringParser;
import java.io.Reader;
import java.util.Locale;

public interface CsvReader<T> {
  void setDefaultLocale(Locale paramLocale);
  
  void setDefaultTimeFormat(String paramString);
  
  void setDefaultDateFormat(String paramString);
  
  void setDefaultTimestampFormat(String paramString);
  
  void setPersistBatchSize(int paramInt);
  
  void setHasHeader(boolean paramBoolean1, boolean paramBoolean2);
  
  void setAddPropertiesFromHeader();
  
  void setIgnoreHeader();
  
  void setLogInfoFrequency(int paramInt);
  
  void addIgnore();
  
  void addProperty(String paramString);
  
  void addReference(String paramString);
  
  void addProperty(String paramString, StringParser paramStringParser);
  
  void addDateTime(String paramString1, String paramString2);
  
  void addDateTime(String paramString1, String paramString2, Locale paramLocale);
  
  void process(Reader paramReader) throws Exception;
  
  void process(Reader paramReader, CsvCallback<T> paramCsvCallback) throws Exception;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\csv\CsvReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */