package com.avaje.ebean.text.json;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

public interface JsonContext {
  <T> T toBean(Class<T> paramClass, String paramString);
  
  <T> T toBean(Class<T> paramClass, Reader paramReader);
  
  <T> T toBean(Class<T> paramClass, String paramString, JsonReadOptions paramJsonReadOptions);
  
  <T> T toBean(Class<T> paramClass, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  <T> List<T> toList(Class<T> paramClass, String paramString);
  
  <T> List<T> toList(Class<T> paramClass, String paramString, JsonReadOptions paramJsonReadOptions);
  
  <T> List<T> toList(Class<T> paramClass, Reader paramReader);
  
  <T> List<T> toList(Class<T> paramClass, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  Object toObject(Type paramType, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  Object toObject(Type paramType, String paramString, JsonReadOptions paramJsonReadOptions);
  
  void toJsonWriter(Object paramObject, Writer paramWriter);
  
  void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean);
  
  void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions);
  
  void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions, String paramString);
  
  String toJsonString(Object paramObject);
  
  String toJsonString(Object paramObject, boolean paramBoolean);
  
  String toJsonString(Object paramObject, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions);
  
  String toJsonString(Object paramObject, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions, String paramString);
  
  boolean isSupportedType(Type paramType);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */