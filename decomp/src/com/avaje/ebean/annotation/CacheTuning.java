package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheTuning {
  int maxSize() default 0;
  
  int maxIdleSecs() default 0;
  
  int maxSecsToLive() default 0;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\annotation\CacheTuning.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */