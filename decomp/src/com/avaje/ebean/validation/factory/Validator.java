package com.avaje.ebean.validation.factory;

public interface Validator {
  String getKey();
  
  Object[] getAttributes();
  
  boolean isValid(Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\Validator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */