package com.avaje.ebean.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory = com.avaje.ebean.validation.factory.RangeValidatorFactory.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
  long min() default -9223372036854775808L;
  
  long max() default 9223372036854775807L;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\Range.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */