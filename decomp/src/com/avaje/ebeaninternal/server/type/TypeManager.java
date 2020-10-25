package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;

public interface TypeManager {
  CheckImmutableResponse checkImmutable(Class<?> paramClass);
  
  ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> paramClass);
  
  ScalarType<?> recursiveCreateScalarTypes(Class<?> paramClass);
  
  void add(ScalarType<?> paramScalarType);
  
  CtCompoundType<?> getCompoundType(Class<?> paramClass);
  
  ScalarType<?> getScalarType(int paramInt);
  
  <T> ScalarType<T> getScalarType(Class<T> paramClass);
  
  <T> ScalarType<T> getScalarType(Class<T> paramClass, int paramInt);
  
  ScalarType<?> createEnumScalarType(Class<?> paramClass);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\TypeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */