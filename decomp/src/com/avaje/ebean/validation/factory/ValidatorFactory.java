package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public interface ValidatorFactory {
  Validator create(Annotation paramAnnotation, Class<?> paramClass);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\ValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */